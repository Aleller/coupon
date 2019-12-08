package edu.sysu.sdcs.coupon.service.impl;

import edu.sysu.sdcs.coupon.entity.Order;
import edu.sysu.sdcs.coupon.entity.User;
import edu.sysu.sdcs.coupon.exception.MsgException;
import edu.sysu.sdcs.coupon.repository.CouponRepo;
import edu.sysu.sdcs.coupon.repository.OrderRepo;
import edu.sysu.sdcs.coupon.repository.UserRepo;
import edu.sysu.sdcs.coupon.service.OrderService;
import edu.sysu.sdcs.coupon.view.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    CouponRepo couponRepo;

    @Autowired
    OrderRepo orderRepo;

    public void createOrder(Integer conponId, Integer userId) {
        var user = userRepo.findById(userId);
        var conpon = couponRepo.findById(conponId);

        if (user.isPresent()) {
            throw new MsgException("用户不存在");
        }

        if (conpon.isPresent()) {
            throw new MsgException("优惠券不存在");
        }

        var order = orderRepo.findByUserEqualsAndCouponEquals(userId, conponId);
        if (null != order) {
            throw new MsgException("用户已经拥有这张优惠券");
        }

        order = new Order();
        order.setUser(user.get());
        order.setCoupon(conpon.get());
        orderRepo.save(order);

        log.info("==>[order] 用户: {} 获得优惠券 {}",
                user.get().getUsername(),
                conpon.get().getCouponName());

    }
}
