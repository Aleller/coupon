package edu.sysu.sdcs.coupon.service;

import org.apache.shiro.authz.annotation.RequiresPermissions;

public interface UserService {
    @RequiresPermissions("read")
    String read();

    @RequiresPermissions("write")
    String write();
}
