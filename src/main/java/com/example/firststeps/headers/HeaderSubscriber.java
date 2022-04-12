package com.example.firststeps.headers;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class HeaderSubscriber {
    private static final String EXCHANGE_NAME = "headers";
    private static final String HEALTH_QUEUE_NAME = "HealthQ";
    private static final String SPORTS_QUEUE_NAME = "SportsQ";
    private static final String EDUCATION_QUEUE_NAME = "EducationQ";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.HEADERS);

        channel.queueDeclare(HEALTH_QUEUE_NAME, false, false, false,null);
        channel.queueDeclare(SPORTS_QUEUE_NAME, false, false, false,null);
        channel.queueDeclare(EDUCATION_QUEUE_NAME, false, false, false,null);

        Map<String, Object> healthArgs = new HashMap<>();
        healthArgs.put("x-match", "any");
        healthArgs.put("h1", "Header1");
        healthArgs.put("h2", "Header2");
        channel.queueBind(HEALTH_QUEUE_NAME, EXCHANGE_NAME, "", healthArgs);

        Map<String, Object> sportsArgs = new HashMap<>();
        sportsArgs.put("x-match", "all");
        sportsArgs.put("h1", "Header1");
        sportsArgs.put("h2", "Header2");
        channel.queueBind(SPORTS_QUEUE_NAME, EXCHANGE_NAME, "", sportsArgs);

        Map<String, Object> educationArgs = new HashMap<>();
        educationArgs.put("x-match", "any");
        educationArgs.put("h1", "Header1");
        educationArgs.put("h2", "Header2");
        channel.queueBind(EDUCATION_QUEUE_NAME, EXCHANGE_NAME, "", educationArgs);

        channel.basicConsume(HEALTH_QUEUE_NAME, true, (consumerTag, message) -> {
            System.out.println("\n=========== Health Queue ==========");
            System.out.println(consumerTag);
            System.out.println(HEALTH_QUEUE_NAME + ": " + new String(message.getBody()));
            System.out.println(message.getEnvelope());
        }, System.out::println);

        channel.basicConsume(SPORTS_QUEUE_NAME, true, (consumerTag, message) -> {
            System.out.println("\n=========== Sports Queue ==========");
            System.out.println(consumerTag);
            System.out.println(SPORTS_QUEUE_NAME + ": " + new String(message.getBody()));
            System.out.println(message.getEnvelope());
        }, System.out::println);

        channel.basicConsume(EDUCATION_QUEUE_NAME, true, (consumerTag, message) -> {
            System.out.println("\n=========== Education Queue ==========");
            System.out.println(consumerTag);
            System.out.println(EDUCATION_QUEUE_NAME + ": " + new String(message.getBody()));
            System.out.println(message.getEnvelope());
        }, System.out::println);
    }
}
