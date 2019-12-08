package edu.sysu.sdcs.coupon.service;

import edu.sysu.sdcs.coupon.entity.Order;

public interface OrderService {
    void createOrder(Integer conponId, Integer userId);

    Order findByUserEqualsAndCouponEquals(Integer userId, Integer couponId);
}
