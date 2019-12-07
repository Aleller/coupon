package edu.sysu.sdcs.coupon.service.impl;

import edu.sysu.sdcs.coupon.repository.UserRepository;
import edu.sysu.sdcs.coupon.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    public String read() {
        return "reading...";
    }

    public String write() {
        return "writing...";
    }
}
