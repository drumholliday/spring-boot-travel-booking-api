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
            // Minimal Part F implementation: just return a tracking number.
            // (We’ll wire up saving Customer/Cart/CartItems in a later step.)

            // Unpack DTO by pulling customer, cart, cartItems out of the Purchase object the controller received.
            Customer customer = purchase.getCustomer();
            Cart cart = purchase.getCart();
            Set<CartItem> items = purchase.getCartItems();

            // Attach a managed Division b/c Angular sends customer.division.id and JPA needs a
            // managed Division entity for the Foreign Key division_id so it must be looked up.
            if (customer.getDivision() == null || customer.getDivision().getId() == null) {
            // If it is missing or invalid throw IllegalArgumentException which replaces a generic 500 error.
            throw new IllegalArgumentException("customer.division.id required");
        }
        //  Setting the managed div back on customer ensures Hibernate writes a valid division_id (managed division reference).
        Division div = divisionRepo.findById(customer.getDivision().getId()).orElseThrow(() -> new IllegalArgumentException("Invalid division id: " + customer.getDivision().getId()));
        customer.setDivision(div);

        // Establishes Patent/Child Links
        // carts.customer_id points to customers_customer_id
        // cart_items.cart_id points to carts.cart_id
        cart.setCustomer(customer);
        if (items != null) {
            for (CartItem it : items) {
                it.setCart(cart);
            }
        }

        // Call all repos explicitly in case entity mappings don't have cascading set up. Writes rows to MySQL
        customerRepo.save(customer); //insert a customer
        cartRepo.save(cart); // insert the cart after customer is known
        if (items != null && items.isEmpty()) {
            cartItemRepo.saveAll(items); //inserts the line items now that cart_id is known.
        }

        // String trackingNumber = generateTrackingNumber();
        // Generate a Unique tracking number per order and return to the frontend
        String trackingNumber = UUID.randomUUID().toString();
        return new PurchaseResponse(trackingNumber);
    }
}

//    private String generateTrackingNumber() {
//        return UUID.randomUUID()
//                .toString()
//                .replace("-", "")
//                .substring(0, 16)
//                .toUpperCase();
//    }
