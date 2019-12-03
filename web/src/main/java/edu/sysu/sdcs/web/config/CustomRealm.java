package edu.sysu.sdcs.web.config;

import edu.sysu.sdcs.web.entity.User;
import edu.sysu.sdcs.web.repository.UserRepo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @author anan
 * @date: Created in 2019/11/25 16:06
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private AuthConfigSerivce authConfigSerivce;

  @Autowired
  private UserRepo userRepo;


    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
      UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
      String userName = token.getUsername();

      User user = userRepo.findByAccount(token.getUsername());

      if (user == null) {
        throw new AuthenticationException("username or password error.");
      }
//      if (user.getPassword().equals(MD5Utils.MD5(String.valueOf(token.getPassword()) + user.getSalt()))) {
      if (user.getPassword().equals(String.valueOf(token.getPassword()))) {
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("user", userRepo.findById(user.getId()));
        return new SimpleAuthenticationInfo(userName, token.getPassword(), getName());
      } else {
        throw new AuthenticationException("username or password error.");
      }
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
      String userName = (String) super.getAvailablePrincipal(principalCollection);
      User byAccount = this.userRepo.findByAccount(userName);

      /*-- anan ------------------------------------------------------------
      |                         C O N S T A N T S                           |
      |   put the principals into session,  key=userId value=principals     |
      |   Session session = SecurityUtils.getSubject().getSession();        |
      |                                                                     |
      |   debug: session.delegate.httpSession.session.attrubutes.user       |
      =======================================================================*/
      SecurityUtils.getSubject().getSession().setAttribute("user", byAccount);

//      Session session = SecurityUtils.getSubject().getSession();

      SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
      Set<String> roles = authConfigSerivce.getRolesByAccount(userName);
      authorizationInfo.setRoles(roles);
      roles.forEach(role -> {
          Set<String> permissions = this.authConfigSerivce.getPermissionsByRole(role);
          authorizationInfo.addStringPermissions(permissions);
      });
      return authorizationInfo;
    }
}
