package edu.sysu.sdcs.coupon.service;

import edu.sysu.sdcs.coupon.entity.User;

public interface SeckillService {
    void seckillCoupon(String couponName, String sellerName, User user);
}
