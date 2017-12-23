package com.spring.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AppController {

    @GetMapping("/")
    public ModelAndView index() {
        Map<String,String> map = new HashMap<>();
        map.put("name","Victor");
        System.out.println(map.get("name"));
      //  return new ModelAndView("/resources/templates/index.html",map);
        return new ModelAndView("index.html");

    }
}
