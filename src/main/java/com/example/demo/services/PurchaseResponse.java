package com.example.demo.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PurchaseResponse {
    // ADDED @JsonProperty annotation to make the Json name match what Angular reads.
    @JsonProperty("orderTrackingNumber")
    private String orderTrackingNumber;
}
