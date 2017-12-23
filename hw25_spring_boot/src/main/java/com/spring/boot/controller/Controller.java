package com.spring.boot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.stereotype.Controller
public class Controller {
    @RequestMapping(value = "/horosho", method = RequestMethod.GET)
    public ResponseEntity<String> exampleResponse() {
        return new ResponseEntity<String>("Horosho", HttpStatus.OK);
    }

}
