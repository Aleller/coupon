package edu.sysu.sdcs.coupon.service;

import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.entity.Order;
import org.apache.shiro.authz.annotation.Logical;
import edu.sysu.sdcs.coupon.entity.User;
import org.apache.shiro.authz.annotation.RequiresRoles;

import javax.transaction.Transactional;

public interface UserService {
    @RequiresRoles(value={"CUSTOMER","SELLER"},logical= Logical.OR)
    User getUserByName (String name);

    @Transactional
    void register(User user);
}
