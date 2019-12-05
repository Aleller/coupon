package edu.sysu.sdcs.web.controller;

//import edu.sysu.sdcs.provider.service.Sendder;
import edu.sysu.sdcs.web.entity.Ticket;
import edu.sysu.sdcs.web.entity.TicketUser;
import edu.sysu.sdcs.web.entity.User;
import edu.sysu.sdcs.web.enums.RedisEnum;
import edu.sysu.sdcs.web.enums.ResultEnum;
import edu.sysu.sdcs.web.mq.Sender;
import edu.sysu.sdcs.web.service.RedisService;
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
  @Autowired  private Sender sender;

  @Autowired  private RedisService redisService;

  @PostMapping()
  @RequiresPermissions({"BUYER"})
  public ResultVO inMq(@RequestBody TicketUser ticketUser) {
    User user = SubjectUtils.getProfile();

//TODO:
//  1. redis 校验是不是已经秒杀过了
//  1. 查找 redis 排队中的队列是否存在
//  1. add mq

    if (redisService.hasTicketUser(ticketUser.getTicket().getId(), ticketUser.getUserId())) {
      return Result.error("用户名：" + user.getAccount() + "，已经秒杀过");
    }
    if(redisService.hasQueueByKey(ticketUser)){
      return Result.error("用户名：" + user.getAccount() + "，排队中");
    }

    this.sender.send(ticketUser);
    return Result.success();
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
