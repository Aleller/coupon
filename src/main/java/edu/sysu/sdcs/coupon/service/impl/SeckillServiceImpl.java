package edu.sysu.sdcs.coupon.service.impl;

import com.alibaba.fastjson.JSONObject;
import edu.sysu.sdcs.coupon.entity.User;
import edu.sysu.sdcs.coupon.exception.MsgException;
import edu.sysu.sdcs.coupon.ordermq.MQSender;
import edu.sysu.sdcs.coupon.service.CouponService;
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
    DefaultRedisScript<Boolean> defaultRedisScript;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private MQSender mqSender;

    private void enqueue(Integer userId, Integer couponId) {
        var res = redisTemplate.opsForValue().get(userId);

        if (null != res) {
            throw new MsgException("您在队列中, 不允许重复排队抢券");
        }

        redisTemplate.opsForValue().set(userId, couponId);

        var orderVO = new OrderVO();
        orderVO.setUserId(userId);
        orderVO.setCouponId(couponId);
        mqSender.send(JSONObject.toJSONString(orderVO));
    }

    @Override
    public void seckillCoupon(String couponName, String sellerName, User user) {
        var seller = userService.getUserByName(sellerName);
        var coupon = couponService.getCouponByName(couponName);

        if (null == seller) {
            throw new MsgException("商家"+ sellerName + "不存在");
        }

        if (null == coupon) {
            throw new MsgException("优惠券"+ couponName + "不存在");
        }

        if (coupon.getSeller().getUsername() != sellerName) {
            throw new MsgException("商家" + sellerName + "没有优惠券" + couponName);
        }

        Boolean res = stringRedisTemplate.execute(defaultRedisScript,  Arrays.asList(coupon.getId().toString()));

        if (!res) {
            throw new MsgException("优惠券" + couponName + "卖完了");
        }

        enqueue(user.getId(), coupon.getId());

        log.info("==> [seckill] 用户{}秒杀商家{}优惠券{}",
                user.getUsername(),
                coupon.getCouponName(),
                seller.getUsername());
    }
}
