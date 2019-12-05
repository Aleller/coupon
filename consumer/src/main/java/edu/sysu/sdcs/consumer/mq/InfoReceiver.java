package edu.sysu.sdcs.consumer.mq;

import edu.sysu.sdcs.consumer.mq.entity.Ticket;
import edu.sysu.sdcs.consumer.mq.entity.TicketUser;
import edu.sysu.sdcs.consumer.mq.repository.TicketUserRepo;
import edu.sysu.sdcs.consumer.mq.repository.TicketRepo;
import edu.sysu.sdcs.consumer.mq.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 消息接收者 1
 *
 * 这里一个项目模拟 四个接收者，
 * 消息接收者监听消息队列，发生变化时则执行对应的方法
 *
 * 业务场景：系统日志处理场景
 *
 *                                                      ___________                      #########
 *                                              7----->|_ 队列 Q1 _|---- 短信发送 -----> # 短信服务  #
 *                                             /                                         #########
 *                                            /
 *                        匹配规则 :Fanout    /
 *                                          /
 *     ____________            ########### /            ____________                     ###########
 *    |_ 订单服务 _|--------->  #  交换器  #------------>|_ 队列 Q2 _|---- app push ----> # push 服务 #
 *                             ############                                              ###########
 *
 *
 * @RabbitListener bindings:绑定队列
 * @QueueBinding value: 绑定队列名称
 *               exchange:配置交换器
 *               key:路由键
 *
 * @Queue value: 配置队列名称
 *        autoDelete: 是否是个可删除的临时队列
 *
 * @Exchange value: 为交换器起个名字
 *           type: 指定具体的交换器类型
 *
 * @author anan
 * @created by anan on 2019/3/1 17:20
 */
@Slf4j
@Component
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${mq.config.queue.info.value}",autoDelete ="false"),
                exchange = @Exchange(value = "${mq.config.exchange}",type = ExchangeTypes.DIRECT),
                key = "${mq.config.queue.info.routing-key}" //广播模式不需要路由键
        )
)
public class InfoReceiver {

  @Autowired
  TicketUserRepo ticketUserRepo;
  @Autowired
  TicketRepo ticketRepo;
  @Autowired
  RedisService redisService;

  /**
   * 接收消息方法，采用消息队列监听机制
   * @param ticketUser
   */
  @RabbitHandler
  public void process(TicketUser ticketUser){
// TODO:
//    1. save ticketUser
//    1. delete redis 排队 queue
//    1. add redis ticketUser

    System.out.println("receiver: info -->" + ticketUser);
    Ticket ticket = ticketRepo.findById(ticketUser.getTicket().getId()).get();

    if(ticket.getAmount()<0)

    try{


      redisService.deleteQueueByKey(ticketUser);
      redisService.setTicketUser(ticketUser);
    }catch (Exception e){
      log.error("----------- ticketUser --- = {}",ticketUser);
      log.error("-------------e = {}", e.getMessage());
    }



//    throw new RuntimeException();
  }

}
