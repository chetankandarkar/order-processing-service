package com.cashinvoice.orderprocessing.service;

import java.util.List;

import com.cashinvoice.orderprocessing.dto.CreateOrderRequest;
import com.cashinvoice.orderprocessing.model.Order;

public interface OrderService {
	
    public Order createOrder(CreateOrderRequest request);

    public Order getOrderById(Long orderId);

    public List<Order> getOrdersByCustomer(String customerId);

}
