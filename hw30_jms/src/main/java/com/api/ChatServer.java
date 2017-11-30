package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {
    private static Map<String, ChatConsumer> mapConsumer = new HashMap<>();
    private static Map<String, ChatProducer> mapProducer = new HashMap<>();
    private static ConfigurableApplicationContext context;
    private static ConnectionFactory connectionFactory;
    private static Topic topic;
    private static final Object LOCK = new Object();


    public static void main(String[] args) {
        ChatServer.context = SpringApplication.run(ChatConfig.class, args);
        ChatServer.connectionFactory = context.getBean(ConnectionFactory.class);
        ChatServer.topic = context.getBean(Topic.class);

        synchronized (LOCK) {
            try {
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void login(String userName) {
        context = SpringApplication.run(ChatConfig.class);
        connectionFactory = context.getBean(ConnectionFactory.class);
        try (Connection connectionProducer = connectionFactory.createConnection()) {
            Session sessionProducer = connectionProducer.createSession(false, Session.AUTO_ACKNOWLEDGE);
            topic = context.getBean(Topic.class);
            mapProducer.put(userName, new ChatProducer().init(sessionProducer, topic));
        } catch (JMSException e) {
            e.printStackTrace();
        }

        try (Connection connectionConsumer = connectionFactory.createConnection()) {
            Session sessionConsumer = connectionConsumer.createSession(false, Session.AUTO_ACKNOWLEDGE);
            mapConsumer.put(userName,new ChatConsumer().init(sessionConsumer,topic));
        } catch (JMSException e) {
            e.printStackTrace();
        }

        onLogin(userName);
    }

    public static void onLogin(String userName) {
        try (Connection connection = connectionFactory.createConnection()) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(topic);
            Message message = session.createTextMessage("К чату присоединился " + userName);
            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
