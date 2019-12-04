package edu.sysu.sdcs.web.controller;

//import edu.sysu.sdcs.provider.service.Sendder;
import edu.sysu.sdcs.web.entity.Ticket;
import edu.sysu.sdcs.web.mq.Sendder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author: anan
 * @date: Created in 2019/12/3 15:08
 */
@Slf4j
@RestController
@RequestMapping(value = "/send")
public class SenderController {

  @Autowired
  private Sendder sendder;

  @PostMapping()
  public void aaa(@RequestBody Ticket ticket) {
    this.sendder.send("hello RabbitMQ ");
  }


}
