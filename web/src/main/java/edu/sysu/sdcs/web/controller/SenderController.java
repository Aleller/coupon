package edu.sysu.sdcs.web.controller;

import edu.sysu.sdcs.provider.mq.Sender;
import edu.sysu.sdcs.web.entity.Coupon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author: yaoky
 * @date: Created in 2019/12/3 15:08
 */
@Slf4j
@RestController
@RequestMapping(value = "/send")
public class SenderController {

//  @Autowired
//  private Sender sender;
//
//  @PostMapping()
//  public void addCoupon(@Valid @RequestBody Coupon coupon) {
//    this.sender.send("hello RabbitMQ ");
//  }
}
