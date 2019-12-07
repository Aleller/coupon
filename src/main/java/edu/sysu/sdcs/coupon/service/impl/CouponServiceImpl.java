package edu.sysu.sdcs.coupon.service.impl;

import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.entity.User;
import edu.sysu.sdcs.coupon.repository.CouponRepo;
import edu.sysu.sdcs.coupon.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CouponServiceImpl implements CouponService{
    @Autowired
    CouponRepo couponRepo;

    @Autowired
    RedisTemplate redisTemplate;

    public void updateRedis (Coupon coupon) {
        redisTemplate.opsForValue().set(coupon.getId(), coupon.getAmount());
    }

    @Override
    public void addCoupon(Coupon coupon, User user) {
        coupon.setSeller(user);

        updateRedis(coupon);
        couponRepo.save(coupon);
    }

    @Override
    public Coupon getCouponByName (String couponName) {
        return couponRepo.findUserByCouponNameEquals(couponName);
    }
}
