package edu.sysu.sdcs.coupon.service.impl;

import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.entity.Order;
import edu.sysu.sdcs.coupon.entity.User;
import edu.sysu.sdcs.coupon.enums.Role;
import edu.sysu.sdcs.coupon.exception.MsgException;
import edu.sysu.sdcs.coupon.repository.CouponRepo;
import edu.sysu.sdcs.coupon.repository.OrderRepo;
import edu.sysu.sdcs.coupon.repository.UserRepo;
import edu.sysu.sdcs.coupon.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    CouponRepo couponRepo;

    @Autowired
    OrderRepo orderRepo;

    public User getUserByName (String userName) {
        return userRepo.findUserByUsernameEquals(userName);
    }

    @Override
    public void register(User user) {
        var userRes = getUserByName(user.getUsername());
        if (null == userRes) {
            throw new MsgException("用户名已存在");
        }
        userRepo.save(user);
    }

    @Override
    public List<Coupon> getSellerCouponsPage(User user, int page) {
        Pageable pageable = PageRequest.of(page-1,20);

        Page<Coupon> coupons = couponRepo.findCouponsBySellerEquals(user, pageable);

        return coupons.toList();
    }

    @Override
    public List<Order> getOrdersPage(User user, int page) {
        Pageable pageable = PageRequest.of(page-1,20);
        Page<Order> orders = orderRepo.findOrdersByUserEquals(user,pageable);

        return orders.toList();
    }
}
