package com.cashinvoice.orderprocessing.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cashinvoice.orderprocessing.model.Order;
import com.cashinvoice.orderprocessing.util.LoggerUtil;

@Component
public class OrderQueueConsumerRoute extends RouteBuilder {

    @Autowired
    LoggerUtil log;

    @Value("${order.created.queue}")
    private String orderCreatedQueue;

    @Value("${order.queue.consumer.enabled:true}")
    private boolean consumerEnabled;

    @Autowired
    private JacksonDataFormat orderDataFormat;

    @Override
    public void configure() {
        if (!consumerEnabled) {
            log.doLog(4, "OrderQueueConsumerRoute is disabled via properties");
            return;
        }

        from("activemq:queue:" + orderCreatedQueue)
            .routeId("activemq-consumer-route")
            .unmarshal(orderDataFormat)
            .process(exchange -> {
                Order order = exchange.getIn().getBody(Order.class);
                log.doLog(4, "Order processed | OrderId={} | CustomerId={} | Amount={}",
                          order.getOrderId(), order.getCustomerId(), order.getAmount());
            });
    }
}
