package com.example.firststeps.exchangetoexchange;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Subscriber {
    private static final String LINKED_EXCHANGE_NAME = "linked-direct-exchange";
    private static final String HOME_EXCHANGE_NAME = "home-direct-exchange";
    private static final String MOBILE_QUEUE_NAME = "MobileQ";
    private static final String AC_QUEUE_NAME = "ACQ";
    private static final String LIGHT_QUEUE_NAME = "LightQ";
    private static final String FAN_QUEUE_NAME = "FanQ";
    private static final String LAPTOP_QUEUE_NAME = "LaptopQ";
    private static final String HOME_APPLIANCE_KEY = "homeAppliance";
    private static final String PERSONAL_DEVICE_KEY = "personalDevice";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(LINKED_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(HOME_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        channel.queueDeclare(MOBILE_QUEUE_NAME, false, false, false, null);
        channel.queueDeclare(AC_QUEUE_NAME, false, false, false, null);
        channel.queueDeclare(LIGHT_QUEUE_NAME, false, false, false, null);

        channel.queueDeclare(FAN_QUEUE_NAME, false, false, false, null);
        channel.queueDeclare(LAPTOP_QUEUE_NAME, false, false, false, null);

        channel.queueBind(MOBILE_QUEUE_NAME, LINKED_EXCHANGE_NAME, PERSONAL_DEVICE_KEY);
        channel.queueBind(AC_QUEUE_NAME, LINKED_EXCHANGE_NAME, HOME_APPLIANCE_KEY);
        channel.queueBind(LIGHT_QUEUE_NAME, LINKED_EXCHANGE_NAME, HOME_APPLIANCE_KEY);

        channel.queueBind(FAN_QUEUE_NAME, HOME_EXCHANGE_NAME, HOME_APPLIANCE_KEY);
        channel.queueBind(LAPTOP_QUEUE_NAME, HOME_EXCHANGE_NAME, PERSONAL_DEVICE_KEY);

        channel.exchangeBind(LINKED_EXCHANGE_NAME, HOME_EXCHANGE_NAME, HOME_APPLIANCE_KEY);

        channel.basicConsume(MOBILE_QUEUE_NAME, true, (consumerTag, message) -> {
            System.out.println("\n" + message.getEnvelope());
            System.out.println(MOBILE_QUEUE_NAME + ":" + new String(message.getBody()));
        }, System.out::println);

        channel.basicConsume(AC_QUEUE_NAME, true, (consumerTag, message) -> {
            System.out.println("\n" + message.getEnvelope());
            System.out.println(AC_QUEUE_NAME + ":" + new String(message.getBody()));
        }, System.out::println);

        channel.basicConsume(LIGHT_QUEUE_NAME, true, (consumerTag, message) -> {
            System.out.println("\n" + message.getEnvelope());
            System.out.println(LIGHT_QUEUE_NAME + ":" + new String(message.getBody()));
        }, System.out::println);

        channel.basicConsume(FAN_QUEUE_NAME, true, (consumerTag, message) -> {
            System.out.println("\n" + message.getEnvelope());
            System.out.println(FAN_QUEUE_NAME + ":" + new String(message.getBody()));
        }, System.out::println);

        channel.basicConsume(LAPTOP_QUEUE_NAME, true, (consumerTag, message) -> {
            System.out.println("\n" + message.getEnvelope());
            System.out.println(LAPTOP_QUEUE_NAME + ":" + new String(message.getBody()));
        }, System.out::println);
    }
}
