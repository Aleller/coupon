package edu.sysu.sdcs.coupon.service.impl;

import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.repository.CouponRepo;
import edu.sysu.sdcs.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponServiceImpl implements CouponService{
    @Autowired
    CouponRepo couponRepo;

    @Override
    public boolean addCoupon(Coupon coupon) {
        var result = couponRepo.save(coupon);

        return result == null;
    }
}
