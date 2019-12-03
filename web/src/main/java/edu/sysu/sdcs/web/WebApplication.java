package edu.sysu.sdcs.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan({"edu.sysu.sdcs.web","edu.sysu.sdcs.provider"}) //,"edu.sysu.sdcs.consumer",})
public class WebApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebApplication.class, args);
  }

}
