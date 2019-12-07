package edu.sysu.sdcs.coupon.controller;

import com.alibaba.fastjson.JSONObject;
import edu.sysu.sdcs.coupon.entity.User;
import edu.sysu.sdcs.coupon.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
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
        response.setHeader("Authorization",sessionId);
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
//        String user = (String) SecurityUtils.getSubject().getPrincipal();
        return user.getUsername() + " is " + userService.read();
    }

    @GetMapping("/write")
    public String write() {
        return this.userService.write();
    }


}
