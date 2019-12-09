package edu.sysu.sdcs.coupon.controller;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.tools.json.JSONUtil;
import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.entity.Order;
import edu.sysu.sdcs.coupon.entity.User;
import edu.sysu.sdcs.coupon.enums.Role;
import edu.sysu.sdcs.coupon.exception.MsgException;
import edu.sysu.sdcs.coupon.service.CouponService;
import edu.sysu.sdcs.coupon.service.OrderService;
import edu.sysu.sdcs.coupon.service.SeckillService;
import edu.sysu.sdcs.coupon.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillService seckillService;

    @ApiOperation("登录")
    @PostMapping("/user/login")
    public String login(String username, String password, HttpServletResponse response) throws IOException {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //token.setRememberMe(true);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);

        //登录成功返回
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("kind", "saler or customer");
        paramMap.put("errMsg", "登录成功!");
        //设置返回请求头
        String sessionId = (String) SecurityUtils.getSubject().getSession().getId();
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Authorization", sessionId);
        //写出流
        return JSONObject.toJSONString(paramMap);
    }

    @ApiOperation("登出")
    @GetMapping("/uesr/logout")
    public void logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
    }

    @GetMapping("/user/read")
    public String read(HttpServletRequest request) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user.getUsername() + " is " + userService.read();
    }

    @ApiOperation("注册")
    @PostMapping("/user/register")
    public String register(String username, String password, String kind, HttpServletResponse response) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        if (null == kind) {
            kind = "customer";
        }

        if(kind.equals("customer")){
            user.setRole(Role.CUSTOMER);
        }else if (kind.equals("seller")){
            user.setRole(Role.SELLER);
        } else {
            throw new MsgException("kind参数非法");
        }

        userService.register(user);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("errMsg", "i don't know");
        //设置返回请求头
        response.setContentType("application/json;charset=utf-8");
        return JSONObject.toJSONString(paramMap);
    }


}