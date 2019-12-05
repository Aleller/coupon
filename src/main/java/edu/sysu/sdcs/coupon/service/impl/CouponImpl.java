package edu.sysu.sdcs.coupon.service.impl;

import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.repository.CouponRepo;
import edu.sysu.sdcs.coupon.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CouponImpl implements CouponService{
    @Autowired
    CouponRepo couponRepo;

    @Override
    public boolean addCoupon(Coupon coupon) {
        var result = couponRepo.save(coupon);

        log.info(result.toString());

        return null != result;
    }
}
