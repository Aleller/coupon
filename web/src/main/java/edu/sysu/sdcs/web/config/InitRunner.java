package edu.sysu.sdcs.web.config;

import edu.sysu.sdcs.web.entity.TicketUser;
import edu.sysu.sdcs.web.entity.User;
import edu.sysu.sdcs.web.enums.RedisEnum;
import edu.sysu.sdcs.web.repository.TicketUserRepo;
import edu.sysu.sdcs.web.repository.UserRepo;
import edu.sysu.sdcs.web.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * @author: anan
 * @date: Created in 2019/12/4 17:03
 */
@Slf4j
@Component
@Order(value=1)
public class InitRunner implements CommandLineRunner {

  @Autowired
  RedisService redisService;
  @Autowired
  UserRepo userRepo;
  @Autowired
  TicketUserRepo ticketUserRepo;

  @Override
  public void run(String... args) throws Exception {
    log.info("==>[InitRunner.run] 初始化...读取 user 表 写入 redis 开始...");
    var allUser = userRepo.findAll();
    allUser.forEach(x->{
      redisService.set(x.getAccount(), x, RedisEnum.USER);
    });
    log.info("==>[InitRunner.run] 初始化...读取 user 表 写入 redis 结束...");
    log.info("==>[InitRunner.run] 初始化...读取 ticket_user 表 写入 redis 开始...");
    var allTicketUser = ticketUserRepo.findAll();
    allTicketUser.forEach(x->{
      redisService.set(x.getId()+"", x, RedisEnum.TICKET_USER);
    });
    log.info("==>[InitRunner.run] 初始化...读取 ticket_user 表 写入 redis 结束...");
  }
}
