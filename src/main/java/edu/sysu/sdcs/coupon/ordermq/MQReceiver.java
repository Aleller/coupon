package edu.sysu.sdcs.coupon.ordermq;

import com.alibaba.fastjson.JSONObject;
import edu.sysu.sdcs.coupon.service.OrderService;
import edu.sysu.sdcs.coupon.view.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@RabbitListener(
    bindings = @QueueBinding(
        value = @Queue(value = "${ordermq.config.queue.info.name}",autoDelete ="false"),
        exchange = @Exchange(value = "${ordermq.config.exchange}",type = ExchangeTypes.DIRECT),
        key = "${ordermq.config.queue.info.routing.key}" //广播模式不需要路由键
    )
)
@Service
@Slf4j
public class MQReceiver {
    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitHandler
    public void ordermqReceiver(String jsonMsg) {
        var order = JSONObject.parseObject(jsonMsg, OrderVO.class);

        log.info("==> [receiver] user: {} seckill coupon: {}", order.getUserId(), order.getCouponId());

        try {
            orderService.createOrder(order.getCouponId(), order.getUserId());
            redisTemplate.delete(jsonMsg);
        }catch (Exception e){
            log.error(e.toString());
        }

    }
}
