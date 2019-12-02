package edu.sysu.sdcs.coupon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping("helloworld")
    public String helloWorld(){

        String key = Math.random() + "";
        String value = Math.random() + "";

        stringRedisTemplate.opsForValue().set(key,value);

        return "Hi!";
    }
}
