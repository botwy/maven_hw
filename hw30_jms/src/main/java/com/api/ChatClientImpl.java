package com.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ChatClientImpl implements MessageListener,ChatClient {
    private final ConnectionFactory connectionFactory;
    private final Topic topic;

    private Session pubSession;
    private Session subSession;
    private MessageProducer producer;
    private MessageConsumer consumer;
    private Connection connection;

    @Autowired
    public ChatClientImpl(ConnectionFactory connectionFactory, Topic topic){
        this.connectionFactory = connectionFactory;
        this.topic = topic;

        // Create a JMS connection

        try {
            connection = connectionFactory.createConnection();

            // Create two JMS session objects
            pubSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            subSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create a JMS publisher and subscriber
            producer = pubSession.createProducer(topic);
            consumer = subSession.createConsumer(topic);

            // Set a JMS message listener
               consumer.setMessageListener(this);

            // Start the JMS connection; allows messages to be delivered
            // Если мы читаем сообщения, то не забываем делать start у соединения
            connection.start();


        } catch (JMSException e) {
            e.printStackTrace();
            try {
                connection.close();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        }


    }


    /* Receive message from topic subscriber */
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            System.out.println(text);
        } catch (JMSException jmse) {
            jmse.printStackTrace();
        }
    }

    /* Create and send message using topic publisher */
    @Override
    public void writeMessage(String text) throws JMSException {
        TextMessage message = pubSession.createTextMessage();
        message.setText("------------------------Message received: " + " : " + text);
        producer.send(message);
    }

    /* Close the JMS connection */
    @Override
    public void closeJmsConnection() throws JMSException {
        connection.close();
    }


}
