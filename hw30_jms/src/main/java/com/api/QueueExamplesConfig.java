package com.api;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@SpringBootApplication
@EnableJms
class QueueExamplesConfig {

    @Bean
    public ConnectionFactory jmsFactory() {
        return new ActiveMQConnectionFactory("tcp://localhost:61616");
    }

    @Bean
    public Queue destination() {
        return new ActiveMQQueue("CHAT");
    }
}
