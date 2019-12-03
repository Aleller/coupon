package edu.sysu.sdcs.web.service.impl;

import edu.sysu.sdcs.web.entity.Coupon;
import edu.sysu.sdcs.web.repository.CouponRepo;
import edu.sysu.sdcs.web.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponImpl implements CouponService{
    @Autowired
    CouponRepo couponRepo;

    @Override
    public boolean addCoupon(Coupon coupon) {
        var result = couponRepo.save(coupon);

        return result == null;
    }
}
