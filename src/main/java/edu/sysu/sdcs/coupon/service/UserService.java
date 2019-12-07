package edu.sysu.sdcs.coupon.service;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;

public interface UserService {
    @RequiresRoles(value={"CUSTOMER","SELLER"},logical= Logical.OR)
    String read();

    @RequiresRoles("SELLER")
    String write();
}
