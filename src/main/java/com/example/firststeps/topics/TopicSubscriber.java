package com.example.firststeps.topics;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TopicSubscriber {
    private static final String EXCHANGE_NAME = "topics";
    private static final String HEALTH_QUEUE = "HealthQ";
    private static final String SPORTS_QUEUE = "SportsQ";
    private static final String EDUCATION_QUEUE = "EducationQ";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        channel.queueDeclare(HEALTH_QUEUE, false, false, false, null);
        channel.queueDeclare(SPORTS_QUEUE, false, false, false, null);
        channel.queueDeclare(EDUCATION_QUEUE, false, false, false, null);

        channel.queueBind(HEALTH_QUEUE, EXCHANGE_NAME, "health.*");
        channel.queueBind(SPORTS_QUEUE, EXCHANGE_NAME, "#.sports.*");
        channel.queueBind(EDUCATION_QUEUE, EXCHANGE_NAME, "#.education");

        channel.basicConsume(HEALTH_QUEUE, true, (consumerTag, message) -> {
            System.out.println("\n=========== Health Queue ==========");
            System.out.println(consumerTag);
            System.out.println("HealthQ: " + new String(message.getBody()));
            System.out.println(message.getEnvelope());
        }, consumerTag -> {
            System.out.println(consumerTag);
        });

        channel.basicConsume(SPORTS_QUEUE, true, (consumerTag, message) -> {
            System.out.println("\n=========== Sports Queue ==========");
            System.out.println(consumerTag);
            System.out.println("SportsQ: " + new String(message.getBody()));
            System.out.println(message.getEnvelope());
        }, consumerTag -> {
            System.out.println(consumerTag);
        });

        channel.basicConsume(EDUCATION_QUEUE, true, (consumerTag, message) -> {
            System.out.println("\n=========== Education Queue ==========");
            System.out.println(consumerTag);
            System.out.println("EducationQ: " + new String(message.getBody()));
            System.out.println(message.getEnvelope());
        }, consumerTag -> {
            System.out.println(consumerTag);
        });

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    }
}
