package edu.sysu.sdcs.coupon.service;

import edu.sysu.sdcs.coupon.entity.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;

public interface UserService {
    User getUserByName (String name);

    @RequiresPermissions("read")
    String read();

    @RequiresPermissions("write")
    String write();
}
