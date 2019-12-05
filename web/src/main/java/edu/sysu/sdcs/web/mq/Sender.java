package edu.sysu.sdcs.web.mq;

import edu.sysu.sdcs.web.entity.TicketUser;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: anan
 * @create: 2019/3/14 16:33
 * @Description:
 */
@Component
public class Sender {

  @Value("${mq.config.queue.info.routing-key}")
  private String infoRoutingKey;

  @Value("${mq.config.exchange}")
  private String exchange;

  @Autowired
  private AmqpTemplate rabbitAmqpTemplate;

  public void send(String ticketUser) {

    /**
     * 向消息队列发送消息
     * 参数1：交换器名称
     * 参数2：路由键
     * 参数3：消息
     */
    this.rabbitAmqpTemplate.convertAndSend(exchange, infoRoutingKey, ticketUser);
  }

}
