package edu.sysu.sdcs.web.config;

import edu.sysu.sdcs.web.entity.User;
import edu.sysu.sdcs.web.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author anan
 * @date: Created in 2019/11/25 16:06
 */
@Service
public class AuthConfigSerivce {

  @Autowired
  UserRepo userRepo;

  public Set<String> getRolesByAccount(String account) {
    Set<String> roles = new HashSet<>();
    User byAccount = userRepo.findByAccount(account);
    if(byAccount != null){
      // type just one to one
      roles.add(byAccount.getRole());
    }
    return roles;
  }

  public Set<String> getPermissionsByRole(String role) {
    Set<String> permissions = new HashSet<>();
    switch (role) {
      case "BUYER":
        permissions.add("BUYER");
        break;
      case "SELLER":
        permissions.add("SELLER");
        break;
    }
    return permissions;
  }

  public String getPasswordByAccount(String account) {
    User byAccount = userRepo.findByAccount(account);
    if (byAccount != null)
      return byAccount.getPassword();
    return null;
  }
}
