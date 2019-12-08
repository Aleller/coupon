package edu.sysu.sdcs.coupon.service;

import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.entity.Order;
import org.apache.shiro.authz.annotation.Logical;
import edu.sysu.sdcs.coupon.entity.User;
import org.apache.shiro.authz.annotation.RequiresRoles;

import java.util.List;

public interface UserService {
    User getUserByName (String name);

    @RequiresRoles(value={"CUSTOMER","SELLER"},logical= Logical.OR)
    String read();

    @RequiresRoles("SELLER")
    String write();

    void register(User user);

    List<Coupon> getSellerCoupons(User seller);

    List<Coupon> getSellerCouponsPage(User user, int page);

    List<Order> getOrdersPage(User user, int page);

    List<Coupon> sellserCouponsLeft(User seller);
}
