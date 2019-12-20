package edu.sysu.sdcs.coupon.service.impl;

import edu.sysu.sdcs.coupon.entity.User;
import edu.sysu.sdcs.coupon.exception.MsgException;
import edu.sysu.sdcs.coupon.repository.CouponRepo;
import edu.sysu.sdcs.coupon.repository.OrderRepo;
import edu.sysu.sdcs.coupon.repository.UserRepo;
import edu.sysu.sdcs.coupon.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@CacheConfig(cacheNames = "userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    CouponRepo couponRepo;

    @Autowired
    OrderRepo orderRepo;

    @Override
    @Cacheable(value = "getUserByName",keyGenerator="keyGenerator")
    public User getUserByName (String userName) {
        return userRepo.findUserByUsernameEquals(userName);
    }

    @Override
    public void register(User user) {
        var userRes = getUserByName(user.getUsername());
        if (null != userRes) {
            throw new MsgException("用户名已存在");
        }
        userRepo.save(user);
    }
}
