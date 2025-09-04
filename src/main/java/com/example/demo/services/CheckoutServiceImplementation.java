package com.example.demo.services;

// NEW IMPORTS
import com.example.demo.dao.CartItemRepository;
import com.example.demo.dao.CartRepository;
import com.example.demo.dao.CustomerRepository;
import com.example.demo.dao.DivisionRepository;
import com.example.demo.entities.Cart;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Division;
import org.springframework.transaction.annotation.Transactional;

// NEW IMPORTS
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CheckoutServiceImplementation implements CheckoutService  {
    // Ordered that mirrors domain hierarchy and save sequence in placeOrder
    private final CustomerRepository customerRepo;
    private final DivisionRepository divisionRepo;
    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;

    public CheckoutServiceImplementation(CustomerRepository customerRepo, DivisionRepository divisionRepo, CartRepository cartRepo, CartItemRepository cartItemRepo) {
        this.customerRepo = customerRepo;
        this.divisionRepo = divisionRepo;
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
    }
    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

            // NEW CODE TO FIX ERRORS
             // Request checks return 400's not 500's
            if (purchase == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Purchase required");
            }
            if (purchase.getCustomer() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer required");
            }
            if (purchase.getCart() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart required");
            }

            // Resolve Customer and Division
            // Load managed Customer from DB when ID provided
            Customer inbound = purchase.getCustomer();
            Customer customer = (inbound.getId() != null)
                ? customerRepo.findById(inbound.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Id not found"))
                : inbound;

            // Commented out code 62-70
//            Long divisionId = null;
//            if (inbound.getId() != null) {
//                // Replace with the managed entity that brings division and divisionId
//                customer = customerRepo.findById(inbound.getId())
//                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "customer.id not found"));
//            } else {
//                // Creating a new customer from inbound JSON
//                customer = inbound;
//            }

            // Ensure the Customer has a valid Division without requiring the frontend to send it
            Long divisionId = null;
            if (customer.getDivision() != null && customer.getDivision().getId() != null) {
                // managed customers division
                divisionId = customer.getDivision().getId();
            } else if (inbound.getDivision() != null && inbound.getDivision().getId() != null) {
                // fallback to what frontend sent
                divisionId = inbound.getDivision().getId();
            }

            if (divisionId == null) {
                // if still can't determine will be a clean 400 instead of 500
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "customer.division.id required");
            }

            Division div = divisionRepo.findById(divisionId)
                    // Ensure managed Customer references a managed Division
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "division.id not found"));
            customer.setDivision(div);

            // Persist customer if new
            if (customer.getId() == null) {
                customerRepo.save(customer);
            }

            // Wire Cart and CartItems relationships
            Cart cart = purchase.getCart();
            // Set cart id to null before saving as done in CartItem
            cart.setId(null);
            cart.setCustomer(customer);

            // Reset children coming from inbound JSON to avoid bad items being cascaded and start clean
            cart.setCartItem(new java.util.HashSet<>());

            // Attach items to both sides and validate vacation
            Set<CartItem> items = purchase.getCartItems();
            if (items != null && !items.isEmpty()) {
                for (CartItem item : items) {
                    // Ensure fresh insert
                    item.setId(null);
                    // Validate vacation
                    if (item.getVacation() == null || item.getVacation().getId() == null) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Each cartItem requires a vacation.id");
                }
                    // Set item.setCart(this) and adds to parent set.
                     cart.addItem(item);
                }
            }
            // Save parent first
            cartRepo.saveAndFlush(cart);
            // Sys Print to check if the id is now > 0
            System.out.println("Cart ID after flushed: " + cart.getId());

//            // Explicitly save children
//            if(items != null && !items.isEmpty()) {
//                cartItemRepo.saveAll(items);
//            }

            // TEMP LOGS TO DEBUG WHILE TESTING
            System.out.println("Cart ID after flush: " + cart.getId());
                if (items != null) {
                for (CartItem it : items) {
                 System.out.println("Item -> cartId? " + (it.getCart() != null ? it.getCart().getId() : null)
                         + " | vacationId? " + (it.getVacation() != null ? it.getVacation().getId() : null));
                }
            }
            // Insert children explicitly
            if (items != null && !items.isEmpty()) {
                cartItemRepo.saveAll(items);
            }

            // Return tracking number
            String trackingNumber = UUID.randomUUID().toString();
            cart.setOrderTrackingNumber(trackingNumber);
            // Update with tracking
            cartRepo.save(cart);

            return new PurchaseResponse(trackingNumber);
    }
}
