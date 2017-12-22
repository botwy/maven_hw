package com.api;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import javax.jms.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ChatServer {

    private static ConcurrentMap<String, ChatClient> mapChatClient = new ConcurrentHashMap<>();
    private static final Object LOCK = new Object();

    public static void main (String...args) {
        final ConfigurableApplicationContext context = SpringApplication.run(ChatConfig.class, args);

        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = context.getBean(Topic.class);
            MessageConsumer messageConsumer = session.createConsumer(topic, "mode='toback' AND login=true");

            messageConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    TextMessage textMessage = (TextMessage) message;
                    String text = null;
                    try {
                       String userName  = textMessage.getText();
                        if (!mapChatClient.containsKey(userName)) {
                            ChatClientImpl chatClient = context.getBean(ChatClientImpl.class);
                            chatClient.initClient(userName);
                            mapChatClient.put(userName, chatClient);
                            text = " к чату присоединился " + userName;
                            send(text, context);
                        }
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }

                }
            });

            connection.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        synchronized (LOCK) {
            try {
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void send(String msg, ApplicationContext context) throws JMSException {
        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer messageProducer = session.createProducer(context.getBean(Topic.class));
        TextMessage textMessage = session.createTextMessage("                       Message received" + " : " + msg);
        textMessage.setStringProperty("mode","tofront");
        messageProducer.send(textMessage);
        connection.close();
    }
}
