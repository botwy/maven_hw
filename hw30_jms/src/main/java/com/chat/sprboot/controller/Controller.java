package com.chat.sprboot.controller;

import com.api.SenderMsg;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jms.JMSException;

@org.springframework.stereotype.Controller
public class Controller {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String exampleResponse() throws JMSException {
        SenderMsg.login("denis");
        String responce = "Hello, "+"Denis!";
        return responce;
    }

}
