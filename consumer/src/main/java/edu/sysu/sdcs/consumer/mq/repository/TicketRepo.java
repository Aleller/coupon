package edu.sysu.sdcs.consumer.mq.repository;

import edu.sysu.sdcs.consumer.mq.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: anan
 * @date: Created in 2019/12/3 10:21
 */
public interface TicketRepo extends JpaRepository<Ticket, Integer> {


}
