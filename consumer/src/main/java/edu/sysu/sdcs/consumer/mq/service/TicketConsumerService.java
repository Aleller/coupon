package edu.sysu.sdcs.consumer.mq.service;

import edu.sysu.sdcs.consumer.mq.entity.Ticket;
import edu.sysu.sdcs.consumer.mq.entity.TicketUser;
import edu.sysu.sdcs.consumer.mq.repository.TicketRepo;
import edu.sysu.sdcs.consumer.mq.repository.TicketUserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: yaoky
 * @date: Created in 2019/12/5 17:30
 */
@Slf4j
@Service
public class TicketConsumerService {

  @Autowired
  TicketUserRepo ticketUserRepo;

  @Autowired
  TicketRepo ticketRepo;

  public TicketUser save(TicketUser ticketUser) {

    Ticket ticket = ticketRepo.findById(ticketUser.getTicket().getId()).get();
    ticket.setAmount(ticket.getAmount()-1);
    if(ticket.getAmount()-1 >= 0){
      TicketUser save = ticketUserRepo.save(ticketUser);
      ticketRepo.save(ticket);
      return save;
    }
    return null;
  }


}
