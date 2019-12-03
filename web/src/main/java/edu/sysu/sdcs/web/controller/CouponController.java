package edu.sysu.sdcs.web.controller;

import edu.sysu.sdcs.web.entity.Coupon;
import edu.sysu.sdcs.web.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping(value = "/coupons")
public class CouponController {

  @Autowired
  CouponService couponService;

  @PostMapping()
  public boolean addCoupon(@Valid @RequestBody Coupon coupon) {
    var res = couponService.addCoupon(coupon);
    return res;
  }

  //

}
