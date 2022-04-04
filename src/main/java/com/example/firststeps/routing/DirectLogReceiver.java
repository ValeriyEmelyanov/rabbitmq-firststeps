package com.example.firststeps.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class DirectLogReceiver {
    private static final String EXCHANGE_NAME = "direct_logs";
    private static final String ERROR_ROUTING_KEY = "error";
    private static final String WARNING_ROUTING_KEY = "warning";
    private static final String INFO_ROUTING_KEY = "info";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        channel.queueDeclare("error_log", false, false, false, null);
        channel.queueDeclare("all_log", false, false, false, null);

        channel.queueBind("error_log", EXCHANGE_NAME, ERROR_ROUTING_KEY);
        channel.queueBind("all_log", EXCHANGE_NAME, ERROR_ROUTING_KEY);
        channel.queueBind("all_log", EXCHANGE_NAME, WARNING_ROUTING_KEY);
        channel.queueBind("all_log", EXCHANGE_NAME, INFO_ROUTING_KEY);

        channel.basicConsume("error_log", true, (consumerTag, message) -> {
            System.out.println(consumerTag);
            System.out.println("error_log: " + new String(message.getBody()));
        }, consumerTag -> {
            System.out.println(consumerTag);
        });

        channel.basicConsume("all_log", true, (consumerTag, message) -> {
            System.out.println(consumerTag);
            System.out.println("all_log: " + new String(message.getBody()));
        }, consumerTag -> {
            System.out.println(consumerTag);
        });
    }
}
