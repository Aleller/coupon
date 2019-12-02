package edu.sysu.sdcs.coupon.controller;

import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.service.CouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "优惠券Controller")
@RestController
@RequestMapping("/coupons")
@Slf4j
public class CouponController {
    @Autowired
    CouponService couponService;

    @ApiOperation("新增优惠券")
    @ApiImplicitParam(name = "coupon", value = "优惠券实体", required = true, dataType = "Coupon")
    @PostMapping()
    public boolean addCoupon(@Valid @RequestBody Coupon coupon) {
        var res = couponService.addCoupon(coupon);

        return res;
    }
}
