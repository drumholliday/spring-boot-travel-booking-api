package com.example.demo.services;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CheckoutServiceImplementation implements CheckoutService  {
    @Override
        public PurchaseResponse placeOrder(Purchase purchase) {
            // Minimal Part F implementation: just return a tracking number.
            // (We’ll wire up saving Customer/Cart/CartItems in a later step.)
            String trackingNumber = generateTrackingNumber();
            return new PurchaseResponse(trackingNumber);
        }

    private String generateTrackingNumber() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 16)
                .toUpperCase();
    }
}
