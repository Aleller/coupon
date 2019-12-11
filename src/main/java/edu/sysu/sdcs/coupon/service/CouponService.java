package edu.sysu.sdcs.coupon.service;

import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.entity.User;
import org.apache.shiro.authz.annotation.RequiresRoles;

import javax.transaction.Transactional;
import java.util.List;

public interface CouponService {
    @RequiresRoles("SELLER")
    @Transactional
    void addCoupon(Coupon coupon);

    Coupon getCouponByName(String name);

    @Transactional
    void decCouponAmount(Integer couponId);

    List<Coupon> getSellerCouponsPage(User user, Integer page);
}
