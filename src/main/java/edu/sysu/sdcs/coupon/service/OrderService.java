package edu.sysu.sdcs.coupon.service;

import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.entity.Order;
import edu.sysu.sdcs.coupon.entity.User;

public interface OrderService {
    void createOrder(Integer conponId, Integer userId);

    Order findByUserEqualsAndCouponEquals(User user, Coupon coupon);
}
