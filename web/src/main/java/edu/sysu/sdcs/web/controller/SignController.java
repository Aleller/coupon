package edu.sysu.sdcs.web.controller;

import edu.sysu.sdcs.web.entity.User;
import edu.sysu.sdcs.web.enums.ResultEnum;
import edu.sysu.sdcs.web.repository.UserRepo;
import edu.sysu.sdcs.web.service.SignService;
import edu.sysu.sdcs.web.service.UserService;
import edu.sysu.sdcs.web.util.Result;
import edu.sysu.sdcs.web.util.ResultVO;
import edu.sysu.sdcs.web.util.SubjectUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author: anan
 * @date: Created in 2019/12/3 10:54
 */
@Slf4j
@RestController
public class SignController {

  @Value("${role.buyer}")
  String buyer;
  @Value("${role.seller}")
  String seller;

  @Autowired
  private SignService signService;

  @Autowired
  private UserService userService;

  @PostMapping("/")
  public ResultVO signIn(@Valid @RequestBody User user,
//                     @PathVariable("cloudId") Integer  cloudId ,
                         BindingResult bindingResult) {
    if (bindingResult.hasErrors() ){
      log.error("==>[SignController.signIn]PARAM_ERROR, user={}, message={}", user, bindingResult.getFieldError().getDefaultMessage());
      return Result.error(ResultEnum.PARAM_ERROR);
    }

    UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), user.getPassword());
    token.setRememberMe(true);
    Subject currentUser = SecurityUtils.getSubject();
    currentUser.login(token);
    return Result.success();
  }

  @PostMapping("/signup")
  public ResultVO signUp(@Valid @RequestBody User user,BindingResult bindingResult) {
    if (bindingResult.hasErrors() ){
      log.error("==>[SignController.signup]PARAM_ERROR, user={}, message={}", user, bindingResult.getFieldError().getDefaultMessage());
      return Result.error(ResultEnum.PARAM_ERROR);
    }else if(user.getRole() != buyer || user.getRole() != seller){
      log.error("==>[SignController.signup]PARAM_ERROR, user={}, message= User.Role is wrong", user);
      return Result.error(ResultEnum.PARAM_ERROR);
    }
    var save = userService.save(user);
    if (save != null)
      return Result.success(save); // signUp success
    return Result.error("signUp failure ... try again ..."); // signUp failure
  }

  @GetMapping("/logout")
  public void logout() {
    Subject currentUser = SecurityUtils.getSubject();
    currentUser.logout();
  }


  /************************************************
   *  has auth
   */

  @GetMapping("/buyer")
  @RequiresPermissions("BUYER")
  public String buyer() {
//    Subject currentUser = SecurityUtils.getSubject();
//    Object primaryPrincipal = currentUser.getPrincipals().getPrimaryPrincipal();
//    Object user = currentUser.getSession().getAttribute("user");
    User profile = SubjectUtils.getProfile();
    return this.signService.buyer();
  }


  @GetMapping("/seller")
  public String seller() {
    return this.signService.seller();
  }
}

