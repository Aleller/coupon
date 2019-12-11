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
@RequestMapping("/api")
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
    @PostMapping("/auth")
    public String login(@RequestBody Map<String,String> parameters, HttpServletResponse response) throws IOException {
        UsernamePasswordToken token = new UsernamePasswordToken(parameters.get("username"), parameters.get("password"));
        //token.setRememberMe(true);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);

        //登录成功返回
        Map<String, Object> paramMap = new HashMap<>();
        if(SecurityUtils.getSubject().hasRole("SELLER")){
            paramMap.put("kind", "saler");
        }else{
            paramMap.put("kind", "customer");
        }
        paramMap.put("errMsg", "登录成功!");
        //设置返回请求头
        String sessionId = (String) SecurityUtils.getSubject().getSession().getId();
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Authorization", sessionId);
        response.setStatus(200);
        //写出流
        return JSONObject.toJSONString(paramMap);
    }

    @ApiOperation("登出")
    @GetMapping("/logout")
    public void logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
    }

    @GetMapping("/read")
    public String read() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user.getUsername() + " is " + userService.read();
    }

    @ApiOperation("注册")
    @PostMapping("/users")
    public String register(@RequestBody Map<String, String> parameters, HttpServletResponse response) {
        User user = new User();
        user.setUsername(parameters.get("username"));
        user.setPassword(parameters.get("password"));

        String kind = parameters.get("kind");
        if (null == kind) {
            kind = "customer";
        }

        if(kind.equals("customer")){
            user.setRole(Role.CUSTOMER);
        }else if (kind.equals("saler")){
            user.setRole(Role.SELLER);
        } else {
            throw new MsgException("kind参数非法");
        }

        userService.register(user);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("errMsg", "no error");
        //设置返回请求头
        response.setContentType("application/json;charset=utf-8");
        return JSONObject.toJSONString(paramMap);
    }


}