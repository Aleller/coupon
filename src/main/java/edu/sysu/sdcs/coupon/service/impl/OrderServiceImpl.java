package edu.sysu.sdcs.coupon.service.impl;

import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.entity.Order;
import edu.sysu.sdcs.coupon.entity.User;
import edu.sysu.sdcs.coupon.exception.MsgException;
import edu.sysu.sdcs.coupon.repository.CouponRepo;
import edu.sysu.sdcs.coupon.repository.OrderRepo;
import edu.sysu.sdcs.coupon.repository.UserRepo;
import edu.sysu.sdcs.coupon.service.CouponService;
import edu.sysu.sdcs.coupon.service.OrderService;
import edu.sysu.sdcs.coupon.view.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames = "orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    CouponRepo couponRepo;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    CouponService couponService;

    public void createOrder(Integer couponId, Integer userId) {
        var user = userRepo.findById(userId);
        var coupon = couponRepo.findById(couponId);

        if (!user.isPresent()) {
            throw new MsgException("用户不存在");
        }

        if (!coupon.isPresent()) {
            throw new MsgException("优惠券不存在");
        }

        var order = orderRepo.findByUserEqualsAndCouponEquals(user.get(), coupon.get());
        if (null != order) {
            throw new MsgException("用户已经拥有这张优惠券");
        }

        order = new Order();
        order.setUser(user.get());
        order.setCoupon(coupon.get());
        orderRepo.save(order);
        couponService.decCouponAmount(couponId);


        log.info("==>[order] 用户: {} 获得优惠券 {}",
                user.get().getUsername(),
                coupon.get().getCouponName());

    }

    @Override
    @Cacheable(value = "findByUserEqualsAndCouponEquals",keyGenerator="keyGenerator")
    public Order findByUserEqualsAndCouponEquals(User user, Coupon coupon) {
        return orderRepo.findByUserEqualsAndCouponEquals(user,coupon);
    }

    @Override
    public List<Order> getOrdersPage(User user, int page) {
        Pageable pageable = PageRequest.of(page-1,20);
        Page<Order> orders = orderRepo.findOrdersByUserEquals(user,pageable);

        return orders.toList();
    }
}
