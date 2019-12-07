package edu.sysu.sdcs.coupon.controller;

import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.service.CouponService;
import edu.sysu.sdcs.coupon.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "优惠券Controller")
@RestController
@RequestMapping("/coupons")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @Autowired
    private SeckillService seckillService;

    @ApiOperation("新增优惠券")
    @ApiImplicitParam(name = "coupon", value = "优惠券实体", required = true, dataType = "Coupon")
    @PostMapping()
    public boolean addCoupon(@Valid @RequestBody Coupon coupon) {
//        couponService.addCoupon(coupon);
        return true;
    }

    @PatchMapping("/{couponName}")
    public boolean seckillCoupon(@PathVariable(value = "couponName") Integer couponId) {
//        seckillService.seckillCoupon(123, couponId);
        return true;
    }
}
