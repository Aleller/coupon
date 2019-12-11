package edu.sysu.sdcs.coupon.service.impl;

import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.entity.User;
import edu.sysu.sdcs.coupon.exception.MsgException;
import edu.sysu.sdcs.coupon.repository.CouponRepo;
import edu.sysu.sdcs.coupon.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class CouponServiceImpl implements CouponService{

    @Autowired
    CouponRepo couponRepo;

    @Autowired
    RedisTemplate redisTemplate;

    public void updateRedis (Coupon coupon) {
        if (coupon.getLeft() < 0) {
            throw new MsgException("优惠券"+ coupon.getCouponName() +"数量小于0");
        }
        redisTemplate.opsForValue().set(coupon.getId(), coupon.getLeft());
    }

    @Override
    public void addCoupon(Coupon coupon) {
        coupon = couponRepo.save(coupon);
        updateRedis(coupon);
    }

    @Override
    public Coupon getCouponByName (String couponName) {
        return couponRepo.findCouponByCouponNameEquals(couponName);
    }

    @Override
    @Transactional
    public void decCouponAmount(Integer couponId) {
        Coupon coupon = couponRepo.findById(couponId).get();
        coupon.setAmount(coupon.getLeft() - 1);
        couponRepo.save(coupon);
    }

    @Override
    public List<Coupon> getSellerCouponsPage(User user, int page) {
        Pageable pageable = PageRequest.of(page-1,20);

        Page<Coupon> coupons = couponRepo.findCouponsBySellerEquals(user, pageable);

        return coupons.toList();
    }
}
