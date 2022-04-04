package com.example.firststeps.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class DirectLogEmitter {
    private static final String EXCHANGE_NAME = "direct_logs";
    private static final String ERROR_ROUTING_KEY = "error";
    private static final String WARNING_ROUTING_KEY = "warning";
    private static final String INFO_ROUTING_KEY = "info";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            String message = "Error message";
            channel.basicPublish(EXCHANGE_NAME, ERROR_ROUTING_KEY, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + ERROR_ROUTING_KEY + "':'" + message + "'");

            message = "Warning message";
            channel.basicPublish(EXCHANGE_NAME, WARNING_ROUTING_KEY, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + WARNING_ROUTING_KEY + "':'" + message + "'");

            message = "Info message";
            channel.basicPublish(EXCHANGE_NAME, INFO_ROUTING_KEY, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + INFO_ROUTING_KEY + "':'" + message + "'");
        }
    }
}
