package com.example.firststeps.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Task {
    private static final String QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws Exception {
        Random random = new Random();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            for (int i = 0; i < 10; i++) {
                String message = "Message #" + i + ".".repeat(random.nextInt(5) + 1);
                channel.basicPublish("",
                        QUEUE_NAME,
                        MessageProperties.PERSISTENT_TEXT_PLAIN,
                        message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }
}
