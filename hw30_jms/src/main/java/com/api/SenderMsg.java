package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.jms.*;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class SenderMsg {

    private static ConfigurableApplicationContext context;


    public static void main(String... args) {
        context = SpringApplication.run(ChatConfig.class, args);

        registerConsumer("mode='tofront'");

        System.out.println("введите имя пользователя: ");
        String userName;
        //     try( BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));) {
        //       if (( userName = bufferedReader.readLine())!=null){
        Scanner scanner = new Scanner(System.in);
        userName = scanner.nextLine();
        try {
            login(userName);
            while (true) {
                TimeUnit.MILLISECONDS.sleep(500);
                System.out.println("введите сообщение и enter чтобы отправить: ");
                String msg = scanner.nextLine();
                if (msg.equalsIgnoreCase("exit")) {
                    //      chatClient.close(); // close the connection
                    System.exit(0);// exit from program
                } else {
                    sendMsg(userName, msg);

                }

            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //      }
        //  } catch (IOException e) {
        //     e.printStackTrace();
        // }

    }

    private static void registerConsumer(String selector) {
        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = context.getBean(Topic.class);
            MessageConsumer consum = session.createConsumer(topic, selector);
            connection.start();
            consum.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    TextMessage textMessage = (TextMessage) message;
                    String text = null;
                    try {
                        text = textMessage.getText();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                    System.out.println("                 " + text);
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void login(String msg) throws JMSException {
        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer messageProducer = session.createProducer(context.getBean(Topic.class));
        TextMessage textMessage = session.createTextMessage(msg);
        textMessage.setStringProperty("mode", "toback");
        textMessage.setBooleanProperty("login", true);
        messageProducer.send(textMessage);
        connection.close();
    }

    public static void sendMsg(String userNameFrom, String msg) throws JMSException {
        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = context.getBean(Topic.class);
        MessageProducer messageProducer = session.createProducer(topic);
        TextMessage textMessage = session.createTextMessage(msg);
        textMessage.setStringProperty("mode", "toback");
        textMessage.setStringProperty("user", userNameFrom);
        messageProducer.send(textMessage);
        connection.close();
    }
}
