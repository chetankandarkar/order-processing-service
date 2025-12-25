package com.cashinvoice.orderprocessing.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cashinvoice.orderprocessing.util.LoggerUtil;

@Component
public class OrderFileToQueueRoute extends RouteBuilder {

    @Autowired
    LoggerUtil log;

    @Value("${order.file.input.path}")
    private String inputPath;

    @Value("${order.file.error.path}")
    private String errorPath;

    @Value("${order.created.queue}")
    private String orderCreatedQueue;

    @Override
    public void configure() {

        from("file:" + inputPath + "?noop=true")
            .routeId("file-to-queue-route")
            .doTry()
                .convertBodyTo(String.class)
                .to("activemq:queue:" + orderCreatedQueue)
                .process(exchange -> {
                    String fileName = (String) exchange.getIn().getHeader("CamelFileName");
                    log.doLog(4, "Order file sent to queue | FileName={}", fileName);
                })
            .doCatch(Exception.class)
                .to("file:" + errorPath)
                .process(exchange -> {
                    String fileName = (String) exchange.getIn().getHeader("CamelFileName");
                    log.doLog(2, "Error processing file | FileName={}", fileName);
                })
            .end();
    }
}