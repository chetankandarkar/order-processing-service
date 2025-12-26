package com.cashinvoice.orderprocessing.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cashinvoice.orderprocessing.dto.CreateOrderRequest;
import com.cashinvoice.orderprocessing.dto.CreateOrderResponse;
import com.cashinvoice.orderprocessing.model.Order;
import com.cashinvoice.orderprocessing.service.OrderService;
import com.cashinvoice.orderprocessing.util.LoggerUtil;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService orderService;
	private final LoggerUtil loggerUtil;

	public OrderController(OrderService orderService, LoggerUtil loggerUtil) {
		this.orderService = orderService;
		this.loggerUtil = loggerUtil;
	}

	// 4.1 Create Order
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<CreateOrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
		loggerUtil.doLog(1, "Create order request received for customerId={}", request.getCustomerId());
		Order savedOrder = orderService.createOrder(request);
		loggerUtil.doLog(1, "Order created successfully with orderId={}", savedOrder.getOrderId());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new CreateOrderResponse(savedOrder.getOrderId(), "CREATED"));
	}

	// 4.2 Get Order by ID
	@GetMapping("/{orderId}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<?> getOrderById(@PathVariable Long orderId, Authentication auth) {
		Order order = orderService.getOrderById(orderId);
		if (order == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
		}

		if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
			if (!order.getCustomerId().equals(auth.getName())) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to view this order");
			}
		}

		return ResponseEntity.ok(order);
	}

	// 4.3 List Orders by Customer
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<List<Order>> getOrdersByCustomer(@RequestParam String customerId, Authentication auth) {
		String loggedInUser = auth.getName();
		if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
			if (!customerId.equals(loggedInUser)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
		}
		loggerUtil.doLog(1, "Listing orders for customerId={}", customerId);
		List<Order> orders = orderService.getOrdersByCustomer(customerId);
		return ResponseEntity.ok(orders);
	}
}
