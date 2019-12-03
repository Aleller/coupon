package edu.sysu.sdcs.web.controller;

import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/hello")
public class HelloWorld {

//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//    @Autowired
//    private RedisTemplate redisTemplate;


  @GetMapping("/world")
  public String helloWorld(){
    String key = Math.random() + "";
    String value = Math.random() + "";
//   stringRedisTemplate.opsForValue().set(key,value);

    return "Hi!";
  }
}
