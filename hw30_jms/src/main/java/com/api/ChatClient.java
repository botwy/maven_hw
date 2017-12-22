package com.api;

import javax.jms.JMSException;

public interface ChatClient {
    void writeMessage(String text) throws JMSException;
    void closeJmsConnection() throws JMSException;
}
