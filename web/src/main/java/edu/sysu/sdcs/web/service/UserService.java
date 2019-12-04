package edu.sysu.sdcs.web.service;

import edu.sysu.sdcs.web.entity.User;
import edu.sysu.sdcs.web.repository.TicketRepo;
import edu.sysu.sdcs.web.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: anan
 * @date: Created in 2019/12/3 10:21
 */
@Service
@Slf4j
@Transactional
public class UserService {

  @Autowired
  UserRepo userRepo;

  @Autowired
  TicketRepo ticketRepo;

  public User save(User user){
    //TODO  password ADD  MD5
    return userRepo.save(user);
  }

  public User findOneById(Integer id){
    var user = userRepo.findOneById(id);
    user.setPassword(null);
    return user;
  }

  public User findOrderById(Integer id){
    var user = userRepo.findOneById(id);
    user.setPassword(null);
    return user;
  }


}
