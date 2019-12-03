package edu.sysu.sdcs.web.controller;

import edu.sysu.sdcs.web.entity.Ticket;
import edu.sysu.sdcs.web.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping(value = "/ticket")
public class TicketController {

  @Autowired
  TicketService ticketService;

  @PostMapping()
  public boolean addCoupon(@Valid @RequestBody Ticket ticket) {
    var res = ticketService.addCoupon(ticket);
    return res;
  }

  //

}
