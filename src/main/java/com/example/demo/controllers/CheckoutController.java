package com.example.demo.controllers;

import com.example.demo.services.CheckoutService; // pulls in the business logic needed.
import com.example.demo.services.Purchase; // incoming DTO
import com.example.demo.services.PurchaseResponse; // outgoing DTO
import org.springframework.web.bind.annotation.*;

@RestController
// sets the base URL path for all end points in this controller
@RequestMapping("/api/checkout")
// allows the Angular app running on port 4200 to call this API from the browser (CORS).
@CrossOrigin("http://localhost:4200")

public class CheckoutController {
    // Dependency on the service side layer, final so it must be provided.
    private final CheckoutService checkoutService;


    // Constructor injection, Spring 4.3 automatically injects a single controller, no @Autowired needed.
    // Spring looks for a bean and finds @Service bean from (CheckoutServiceImplementation)and injects it.
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    // Maps HTTP POST requests tp /api/checkout/purchase method
    // Combine with the mapping base api/checkout and "/purchase" = /api/checkout/purchase.
    @PostMapping("/purchase")

    // @RequestBody tells Spring to deserialize the JSON body of the request into a Purchase object.
    // Returns a PurchaseResponse which Spring will serialize back to JSON for the client.
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {
        // Hands the Purchase to Service. Returns what the service returns, in this case a PurchaseResponse with tacking number.
        return checkoutService.placeOrder(purchase);
    }
}
