package edu.sysu.sdcs.coupon.service.impl;

import com.alibaba.fastjson.JSONObject;
import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.entity.Order;
import edu.sysu.sdcs.coupon.entity.User;
import edu.sysu.sdcs.coupon.exception.MsgException;
import edu.sysu.sdcs.coupon.exception.SeckillFailException;
import edu.sysu.sdcs.coupon.ordermq.MQSender;
import edu.sysu.sdcs.coupon.service.CouponService;
import edu.sysu.sdcs.coupon.service.OrderService;
import edu.sysu.sdcs.coupon.service.SeckillService;
import edu.sysu.sdcs.coupon.service.UserService;
import edu.sysu.sdcs.coupon.view.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService{
    @Autowired
    private UserService userService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private DefaultRedisScript<Boolean> defaultRedisScript;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MQSender mqSender;

    @Autowired
    private OrderService orderService;

    private void enqueue(User user, Coupon coupon) {
        var orderVO = new OrderVO();
        orderVO.setUserId(user.getId());
        orderVO.setCouponId(coupon.getId());

        var msg = JSONObject.toJSONString(orderVO);
        var absRes = redisTemplate.opsForValue().setIfAbsent(msg, 1);

        if (!absRes) {
            throw new SeckillFailException("用户Id：" + user.getId() +  "已经抢到了这个优惠券");
        }

        Order order = orderService.findByUserEqualsAndCouponEquals(user, coupon);
        if(order != null){
            throw new SeckillFailException("用户Id：" + user.getId() +  "已经抢到了这个优惠券");
        }

        Boolean curRes = stringRedisTemplate.execute(defaultRedisScript,  Arrays.asList(coupon.getId().toString()));

        if (!curRes) {
            // 先去锁
            redisTemplate.delete(msg);
            throw new SeckillFailException("优惠券" + coupon.getCouponName() + "卖完了");
        }

        mqSender.send(msg);
    }

    @Override
    public void seckillCoupon(String couponName, String sellerName, User user) {


        var seller = userService.getUserByName(sellerName);
        var coupon = couponService.getCouponByName(couponName);

        if (null == seller) {
            throw new SeckillFailException("商家"+ sellerName + "不存在");
        }

        if (null == coupon) {
            throw new SeckillFailException("优惠券"+ couponName + "不存在");
        }

        if (! sellerName.equals(coupon.getSeller().getUsername())) {
            throw new SeckillFailException("商家" + sellerName + "没有优惠券" + couponName);
        }

        enqueue(user, coupon);

        log.info("==> [seckill] 用户{}秒杀商家{}优惠券{}",
                user.getUsername(),
                coupon.getCouponName(),
                seller.getUsername());
    }
}
