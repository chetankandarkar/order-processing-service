package com.cashinvoice.orderprocessing.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cashinvoice.orderprocessing.dto.CreateOrderRequest;
import com.cashinvoice.orderprocessing.model.Order;
import com.cashinvoice.orderprocessing.repository.OrderRepository;
import com.cashinvoice.orderprocessing.util.LoggerUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private LoggerUtil log;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${order.file.input.path}")
	private String inputPath;

	@Transactional
	@Override
	public Order createOrder(CreateOrderRequest request) {

		Order order = null;
		Order savedOrder = null;

		try {
			log.doLog(4, "Create order request received for customerId : {}", request.getCustomerId());

			order = new Order();
			order.setCustomerId(request.getCustomerId());
			order.setProduct(request.getProduct());
			order.setAmount(request.getAmount());

			savedOrder = orderRepository.save(order);

			log.doLog(4, "Order saved successfully. OrderId : {} , Amount : {}", savedOrder.getOrderId(), savedOrder.getAmount());

			writeOrderToFile(savedOrder);

		} catch (Exception e) {
			log.doLog(3, "Exception while creating order for customerId : {}", request.getCustomerId());
			log.doLog(3, "Exception : ", e);
		}

		return savedOrder;
	}

	@Override
	public Order getOrderById(Long orderId) {

		Order order = null;

		try {
			log.doLog(4, "Fetching order details for orderId : {}", orderId);

			order = orderRepository.findById(orderId).orElse(null);

			if (order != null) {
				log.doLog(4, "Order found for orderId : {}", orderId);
			} else {
				log.doLog(4, "Order not found for orderId : {}", orderId);
			}

		} catch (Exception e) {
			log.doLog(3, "Exception while fetching order for orderId : {}", orderId);
			log.doLog(3, "Exception : ", e);
		}

		return order;
	}

	@Override
	public List<Order> getOrdersByCustomer(String customerId) {

		List<Order> orders = null;

		try {
			log.doLog(4, "Fetching orders list for customerId : {}", customerId);

			orders = orderRepository.findByCustomerId(customerId);

			log.doLog(4, "Total orders found for customerId : {} is {}", customerId, orders.size());

		} catch (Exception e) {
			log.doLog(3, "Exception while fetching orders for customerId : {}", customerId);
			log.doLog(3, "Exception : ", e);
		}

		return orders;
	}

	private void writeOrderToFile(Order order) {
		try {
			Path dirPath = Paths.get(inputPath);
			Files.createDirectories(dirPath);

			String fileName = "order-" + order.getOrderId() + ".json";

			Path filePath = dirPath.resolve(fileName);

			log.doLog(4, "Writing order file for orderId : {} at location : {} file : {}", order.getOrderId(), dirPath, fileName);

			objectMapper.writeValue(filePath.toFile(), order);

			log.doLog(4, "Order file written successfully for orderId : {}", order.getOrderId());

		} catch (Exception e) {
			log.doLog(3, "Exception while writing order file for orderId : {}", order.getOrderId());
			log.doLog(3, "Exception : ", e);
		}
	}

}
