package edu.sysu.sdcs.web.repository;

import edu.sysu.sdcs.web.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: anan
 * @date: Created in 2019/12/3 10:21
 */
public interface TicketRepo extends JpaRepository<Ticket, Integer> {
}
