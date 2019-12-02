package edu.sysu.sdcs.coupon.controller;

import edu.sysu.sdcs.coupon.core.LoginValidateAuthenticationProvider;
import edu.sysu.sdcs.coupon.entity.User;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@RestController
public class LoginController {
    @Resource
    LoginValidateAuthenticationProvider loginValidateAuthenticationProvider;

    @RequestMapping("/toLogin")
    public ModelAndView toLogin(){
        ModelAndView mav = new ModelAndView("toLogin.html");
        return mav;
    }

}
