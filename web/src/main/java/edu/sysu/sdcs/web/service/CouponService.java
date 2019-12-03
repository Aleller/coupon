package edu.sysu.sdcs.web.service;

import edu.sysu.sdcs.web.entity.Coupon;
import edu.sysu.sdcs.web.repository.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

  @Autowired
  CouponRepo couponRepo;

  public boolean addCoupon(Coupon coupon) {
    var result = couponRepo.save(coupon);

    return result == null;
  }
}
