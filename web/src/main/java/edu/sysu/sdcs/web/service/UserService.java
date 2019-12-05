package edu.sysu.sdcs.web.service;

import edu.sysu.sdcs.web.entity.User;
import edu.sysu.sdcs.web.enums.RedisEnum;
import edu.sysu.sdcs.web.repository.TicketRepo;
import edu.sysu.sdcs.web.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: anan
 * @date: Created in 2019/12/3 10:21
 */
@Service
@Slf4j
@Transactional
public class UserService {

  @Autowired  UserRepo userRepo;
  @Autowired  TicketRepo ticketRepo;
  @Autowired  RedisService redisService;

  public User save(User user){
    //TODO  password ADD  MD5

    var save = userRepo.save(user);

    // get user table in db to redis
    List<User> all = userRepo.findAll();
    all.forEach(x->{
      redisService.setUser(x);
    });
    return save;
  }

  public List<User> findAll(){
    return userRepo.findAll();
  }

  public User findOneById(Integer id){
    var user = userRepo.findOneById(id);
    user.setPassword(null);
    return user;
  }

  public User findOneByAccount(String account){
    var user = userRepo.findByAccount(account);
    return user;
  }


  public User findOrderById(Integer id){
    var user = userRepo.findOneById(id);
    user.setPassword(null);
    return user;
  }


}
