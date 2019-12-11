package edu.sysu.sdcs.coupon.controller;

import edu.sysu.sdcs.coupon.entity.User;
import edu.sysu.sdcs.coupon.enums.Role;
import edu.sysu.sdcs.coupon.exception.MsgException;
import edu.sysu.sdcs.coupon.service.UserService;
import edu.sysu.sdcs.coupon.utils.ResponseResult;
import edu.sysu.sdcs.coupon.view.RegisterVO;
import edu.sysu.sdcs.coupon.view.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("登录")
    @PostMapping("/auth")
    public ResultVO login(@RequestBody User user, HttpServletResponse response, BindingResult results) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);

        //设置返回请求头
        String sessionId = (String) SecurityUtils.getSubject().getSession().getId();
        response.setHeader("Authorization", sessionId);
        //写出流
        return ResponseResult.success();
    }

    @ApiOperation("登出")
    @GetMapping("/logout")
    public void logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
    }


    @ApiOperation("注册")
    @PostMapping("/users")
    public ResultVO register(@RequestBody @Valid RegisterVO registerVO, BindingResult results) {
        if (results.hasErrors()) {
            var msg = results.getFieldError().getDefaultMessage();
            return ResponseResult.error(msg);
        }

        var user = new User();
        user.setUsername(registerVO.getUserName());
        user.setPassword(registerVO.getPassWord());

        var kind = registerVO.getKind();
        var role = Role.getTypeByName(kind);
        if (null == role) {
            throw new MsgException("kind参数非法");
        }
        user.setRole(role);

        userService.register(user);

        return ResponseResult.success();
    }
}