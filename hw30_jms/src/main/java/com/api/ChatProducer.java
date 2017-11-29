package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

public class ChatProducer {
    private Session session;
    private MessageProducer messageProducer;
    private Topic topic;



    public ChatProducer() {


    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void init(Session ss, Topic tp) throws JMSException {
        this.session=ss;
        this.topic=tp;
        messageProducer = session.createProducer(topic);
    }


    public void setMessageProducer(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    public void send(String msg) throws JMSException {
        Message message = session.createTextMessage(msg);
        messageProducer.send(message);
    }
}
