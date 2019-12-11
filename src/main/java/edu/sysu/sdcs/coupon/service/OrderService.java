package edu.sysu.sdcs.coupon.service;

import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.entity.Order;
import edu.sysu.sdcs.coupon.entity.User;

import java.util.List;

public interface OrderService {
    void createOrder(Integer conponId, Integer userId);

    Order findByUserEqualsAndCouponEquals(User user, Coupon coupon);

    List<Order> getOrdersPage(User user, int page);
}
