package edu.sysu.sdcs.coupon.service;

import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.entity.Order;
import org.apache.shiro.authz.annotation.Logical;
import edu.sysu.sdcs.coupon.entity.User;
import org.apache.shiro.authz.annotation.RequiresRoles;

import java.util.List;

public interface UserService {
    @RequiresRoles(value={"CUSTOMER","SELLER"},logical= Logical.OR)
    User getUserByName (String name);

    void register(User user);

    List<Coupon> getSellerCouponsPage(User user, int page);

    @RequiresRoles("SELLER")
    List<Order> getOrdersPage(User user, int page);
}
