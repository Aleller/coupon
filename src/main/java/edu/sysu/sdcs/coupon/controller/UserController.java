package edu.sysu.sdcs.coupon.controller;

import com.alibaba.fastjson.JSONObject;
import edu.sysu.sdcs.coupon.entity.User;
import edu.sysu.sdcs.coupon.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/api/auth")
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

    @GetMapping("/logout")
    public void logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
    }

    @GetMapping("/read")
    public String read(HttpServletRequest request) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user.getUsername() + " is " + userService.read();
    }

    @PostMapping("/api/users")
    public String register(String username, String password, String kind) {
        return "UserController.register()";
    }

    @PostMapping("/api/users/{username}/coupons")
    public String addCoupon(@PathVariable String username, String name, String amount, String description, String stock) {
        return "UserController.addCoupon";
    }

    @GetMapping("/api/users/{username}/coupons")
    public String getCouponInfo(@PathVariable String username, int page) {
        return "UserController.getCouponInfo";
    }

    @PatchMapping("/api/users/{username}/coupons/{name}")
    public String seckill(@PathVariable String username, @PathVariable String name, HttpServletResponse response) {
        response.setStatus(204);
        return "UserController.seckill()";
    }
}