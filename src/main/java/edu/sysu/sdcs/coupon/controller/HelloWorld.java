package edu.sysu.sdcs.coupon.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    @RequestMapping("helloworld")
    public String helloWorld(){
        return "Hi!";
    }
}
