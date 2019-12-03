package edu.sysu.sdcs.web.controller;

import edu.sysu.sdcs.web.entity.User;
import edu.sysu.sdcs.web.repository.UserRepo;
import edu.sysu.sdcs.web.service.UserService;
import edu.sysu.sdcs.web.util.Result;
import edu.sysu.sdcs.web.util.ResultVO;
import edu.sysu.sdcs.web.util.SubjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : anan
 * @date: 2019-12-3 17:33:33
 */
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping()
  @RequiresPermissions({"BUYER","SELLER"})
  public ResultVO findYourself(){
    User user = SubjectUtils.getProfile();
    return Result.success(user);
  }

  @GetMapping("/{id}")
  @RequiresPermissions({"BUYER","SELLER"})
  public ResultVO findOneById(@PathVariable("id") Integer  id){
    User user = SubjectUtils.getProfile();
    return Result.success(userService.findOneById(id));
  }


}
