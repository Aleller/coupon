package edu.sysu.sdcs.web.controller;

import edu.sysu.sdcs.web.entity.User;
import edu.sysu.sdcs.web.service.LoginService;
import edu.sysu.sdcs.web.util.SubjectUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: anan
 * @date: Created in 2019/12/3 10:54
 */
@RestController
public class LoginController {

  @Autowired
  private LoginService loginService;

  @GetMapping("/login")
  @ResponseBody
  public void login(String username, String password) {
    UsernamePasswordToken token = new UsernamePasswordToken(username, password);
    token.setRememberMe(true);
    Subject currentUser = SecurityUtils.getSubject();
    currentUser.login(token);
  }

  @GetMapping("/logout")
  public void logout() {
    Subject currentUser = SecurityUtils.getSubject();
    currentUser.logout();
  }

  @GetMapping("/buyer")
  @RequiresPermissions("BUYER")
  public String buyer() {
//    Subject currentUser = SecurityUtils.getSubject();
//    Object primaryPrincipal = currentUser.getPrincipals().getPrimaryPrincipal();
//    Object user = currentUser.getSession().getAttribute("user");
    User profile = SubjectUtils.getProfile();
    return this.loginService.buyer();
  }


  @GetMapping("/seller")
  public String seller() {
    return this.loginService.seller();
  }
}

