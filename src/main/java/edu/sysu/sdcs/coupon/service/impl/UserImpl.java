package edu.sysu.sdcs.coupon.service.impl;

import edu.sysu.sdcs.coupon.repository.UserRepository;
import edu.sysu.sdcs.coupon.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        var list = userRepository.findAllByUsernameEquals(s);
        if (list.isEmpty()) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return list.get(0);
    }
}
