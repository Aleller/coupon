package edu.sysu.sdcs.coupon.shiro;


import edu.sysu.sdcs.coupon.entity.User;
import edu.sysu.sdcs.coupon.repository.RoleRepository;
import edu.sysu.sdcs.coupon.repository.UserRepository;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 认证
     * SecurityUtils.getSubject().getPrincipal();
     * 就是你的realms内doGetAuthenticationInfo时new SimpleAuthenticationInfo的第一个构造参数，直接放user对象就行了
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user = this.userRepository.findUserByUsernameEquals(username);
        String password = user.getPassword();
        return new SimpleAuthenticationInfo(user, password, getName());
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) super.getAvailablePrincipal(principalCollection);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<>();
        roles.add(user.getRole());

        authorizationInfo.setRoles(roles);
        Set<String> permissions = new HashSet<>();
        permissions.add("read");
        permissions.add("write");
        authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;
    }
}
