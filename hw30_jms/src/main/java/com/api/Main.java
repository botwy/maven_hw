package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.jms.*;

public class Main {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(QueueExamplesConfig.class, args);

        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
        Queue queue = context.getBean(Queue.class);

        MsgListener msgListener = new MsgListener();
        msgListener.listen(connectionFactory, queue);
    }
}
