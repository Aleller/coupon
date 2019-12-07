package edu.sysu.sdcs.coupon.service.impl;

import edu.sysu.sdcs.coupon.entity.User;
import edu.sysu.sdcs.coupon.repository.UserRepo;
import edu.sysu.sdcs.coupon.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    public User getUserByName (String userName) {
        return userRepo.findUserByUsernameEquals(userName);
    }

    public String read() {
        return "reading...";
    }

    public String write() {
        return "writing...";
    }
}
