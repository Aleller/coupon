package edu.sysu.sdcs.coupon.service;

import edu.sysu.sdcs.coupon.entity.User;
import edu.sysu.sdcs.coupon.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class UserService implements UserDetailsService{
    @Resource
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<User> list = userRepository.findAllByUsernameEquals(s);
        return list.get(0);
    }
}
