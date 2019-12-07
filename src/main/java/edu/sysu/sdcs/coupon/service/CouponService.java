package edu.sysu.sdcs.coupon.service;

import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.entity.User;

public interface CouponService {
    void addCoupon(Coupon coupon, User user);

    Coupon getCouponByName(String name);
}
