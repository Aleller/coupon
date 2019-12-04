package edu.sysu.sdcs.web.repository;

import edu.sysu.sdcs.web.entity.TicketUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: anan
 * @date: Created in 2019/12/3 10:21
 */
public interface TicketUserRepo extends JpaRepository<TicketUser, Integer> {

  List<TicketUser> findAllByUserId(Integer userId);

}
