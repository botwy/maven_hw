package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.jms.*;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class ChatServer {

    private static ConcurrentMap<String, ChatClientImpl> mapChatClient = new ConcurrentHashMap<>();
    private static ConfigurableApplicationContext context;


    public static void main(String... args) {
        context = SpringApplication.run(ChatConfig.class, args);

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
                    send(msg);
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

    public static void login(String userName) throws JMSException {
        if (!mapChatClient.containsKey(userName))
            mapChatClient.put(userName, context.getBean(ChatClientImpl.class));

        send("Подключился " + userName);
    }

    public static void send(String msg) throws JMSException {
        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer messageProducer = session.createProducer(context.getBean(Topic.class));
        TextMessage textMessage = session.createTextMessage("                       Message received" + " : " + msg);
        messageProducer.send(textMessage);
        connection.close();
    }
}
