package com.example.firststeps.exchangetoexchange;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Publisher {
    private static final String HOME_EXCHANGE_NAME = "home-direct-exchange";
    private static final String ROUTING_KEY = "homeAppliance";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(HOME_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            String message = "Turn on the Home Appliances";
            channel.basicPublish(HOME_EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.printf(" [x] Sent '%s': '%s'\n", ROUTING_KEY, message);
        }
    }
}
