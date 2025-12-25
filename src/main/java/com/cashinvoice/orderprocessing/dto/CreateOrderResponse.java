package com.cashinvoice.orderprocessing.dto;

import lombok.Data;

@Data
public class CreateOrderResponse {

    private Long orderId;
    private String status;

    public CreateOrderResponse(Long orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }
}
