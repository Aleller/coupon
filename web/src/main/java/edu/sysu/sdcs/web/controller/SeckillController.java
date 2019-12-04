package edu.sysu.sdcs.web.controller;

//import edu.sysu.sdcs.provider.service.Sendder;
import edu.sysu.sdcs.web.entity.Ticket;
import edu.sysu.sdcs.web.entity.User;
import edu.sysu.sdcs.web.mq.Sender;
import edu.sysu.sdcs.web.util.Result;
import edu.sysu.sdcs.web.util.ResultVO;
import edu.sysu.sdcs.web.util.SubjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: anan
 * @date: Created in 2019/12/3 15:08
 */
@Slf4j
@RestController
@RequestMapping(value = "/seckill")
public class SeckillController {

  /**
   * Send to rabbitmq
   */
  @Autowired
  private Sender sender;

  @PostMapping()
  public void aaa(@RequestBody Ticket ticket) {
    this.sender.send("hello RabbitMQ ");
  }


//  @PostMapping()
//  @RequiresPermissions({"BUYER"})
//  public ResultVO (){
//    /**
//     * 1. get ticket id
//     * 2. get user
//     * 3. find info in redis
//     */
//    User user = SubjectUtils.getProfile();
//    return Result.success(user);
//  }




}
