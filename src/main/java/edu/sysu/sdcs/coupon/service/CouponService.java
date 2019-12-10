package edu.sysu.sdcs.coupon.service;

import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.entity.User;

import javax.transaction.Transactional;

public interface CouponService {
    void addCoupon(Coupon coupon, User user);

    Coupon getCouponByName(String name);

    void decCouponAmount(Integer couponId);
}
