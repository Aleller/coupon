package edu.sysu.sdcs.web.controller;

import edu.sysu.sdcs.web.entity.User;
import edu.sysu.sdcs.web.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {

  @Autowired
  UserRepo userRepo;

  @RequestMapping("/index")
  @RequiresPermissions("BUYER")
  public String index(){
//   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//   String name = authentication.getName();
    List<User> all = userRepo.findAll();
    return "you are user: "+ all ;
  }
}
