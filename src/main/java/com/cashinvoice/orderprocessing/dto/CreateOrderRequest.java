package com.cashinvoice.orderprocessing.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class CreateOrderRequest {
	
    @NotBlank(message = "customerId must not be null or empty")
    private String customerId;

    @NotBlank(message = "product must not be null or empty")
    private String product;

    @Positive(message = "amount must be greater than 0")
    private double amount;
}
