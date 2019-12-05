package edu.sysu.sdcs.coupon.core;

import edu.sysu.sdcs.coupon.entity.User;
import edu.sysu.sdcs.coupon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class LoginValidateAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = (String) authentication.getCredentials();

        User user = (User) userService.loadUserByUsername(userName);

        if(!password.equals(user.getPassword())){
            throw new BadCredentialsException("输入密码错误!");
        }

        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());


    }

    @Override
    public boolean supports(Class<?> authentication) {
        //确保authentication能转成该类
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
