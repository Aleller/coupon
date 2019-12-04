package edu.sysu.sdcs.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication // (exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
//@ComponentScan({"edu.sysu.sdcs.web","edu.sysu.sdcs.provider"}) //,"edu.sysu.sdcs.consumer",})
public class WebApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebApplication.class, args);
  }

}
