package com.cashinvoice.orderprocessing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashinvoice.orderprocessing.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByCustomerId(String customerId);
}
