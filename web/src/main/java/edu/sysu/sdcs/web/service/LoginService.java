package edu.sysu.sdcs.web.service;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Service;

/**
 * just a demo and test
 * @author anan
 * @date: Created in 2019/11/26 15:06
 */
@Service
public class LoginService {

    @RequiresPermissions("BUYER")
    public String buyer() {
        return "BUYER...";
    }

    @RequiresPermissions("SELLER")
    public String seller() {
        return "SELLER...";
    }
}
