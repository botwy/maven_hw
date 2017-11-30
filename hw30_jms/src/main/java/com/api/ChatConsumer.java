package com.api;

import javax.jms.*;

public class ChatConsumer {

    private Session session;
    private MessageConsumer messageConsumer;
    private Topic topic;

    public ChatConsumer() {


    }

    public void setSession(Session session) {
        this.session = session;
    }

    public ChatConsumer init(Session ss, Topic tp) throws JMSException {
        this.session=ss;
        this.topic=tp;
        messageConsumer = session.createConsumer(topic);
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    System.out.println("получено сообщение: "+ ((TextMessage)message).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        return this;
    }


    public void setMessageConsumer(MessageProducer messageProducer) {
        this.messageConsumer = messageConsumer;
    }


}
