package com.api;

import javax.jms.*;

public class MsgListener {

 //   private static final Object LOCK = new Object();

    public void listen(ConnectionFactory connectionFactory, Queue queue) {
        try (Connection connection = connectionFactory.createConnection()) {
            // Если мы читаем сообщения, то не забываем делать start у соединения
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

            MessageConsumer consumer = session.createConsumer(queue);

            // Смотрим какие бывают receive
         //   TextMessage message = (TextMessage) consumer.receive();

            consumer.setMessageListener(receivedMessage -> {
                try {
                    String command = ((TextMessage) receivedMessage).getText();
                    System.out.println("Message received: " + command);

                 switch (command){
                     case "login":
                         Login.exec();
                         break;

                     case "logout":
                         Logout.exec();
                         break;

                     case "sendMsg":
                         SendMessage.exec();
                         break;

                     case "receiveMsgs":
                         ReceiveMessages.exec();
                         break;
                 }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });


        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
