package com.example.firststeps.headers;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HeaderPublisher {
    private static final String EXCHANGE_NAME = "headers";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.HEADERS);

            String message = "Header Exchange example 1 (with h1 and h3)";
            Map<String, Object> headers = new HashMap<>();
            headers.put("h1", "Header1");
            headers.put("h3", "Header3");
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .headers(headers).build();
            channel.basicPublish(EXCHANGE_NAME, "", properties, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent 'h1, h3':'" + message + "'");

            message = "Header Exchange example 2 (with h2)";
            headers = new HashMap<>();
            headers.put("h2", "Header2");
            properties = new AMQP.BasicProperties.Builder()
                    .headers(headers).build();
            channel.basicPublish(EXCHANGE_NAME, "", properties, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent 'h2':'" + message + "'");

            message = "Header Exchange example 3 (with h1 and h2)";
            headers = new HashMap<>();
            headers.put("h1", "Header1");
            headers.put("h2", "Header2");
            properties = new AMQP.BasicProperties.Builder()
                    .headers(headers).build();
            channel.basicPublish(EXCHANGE_NAME, "", properties, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent 'h1, h2':'" + message + "'");
        }
    }
}
