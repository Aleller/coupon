package edu.sysu.sdcs.coupon.controller;

import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.entity.Order;
import edu.sysu.sdcs.coupon.entity.User;
import edu.sysu.sdcs.coupon.enums.Role;
import edu.sysu.sdcs.coupon.service.CouponService;
import edu.sysu.sdcs.coupon.service.OrderService;
import edu.sysu.sdcs.coupon.service.SeckillService;
import edu.sysu.sdcs.coupon.service.UserService;
import edu.sysu.sdcs.coupon.utils.ResponseResult;
import edu.sysu.sdcs.coupon.view.CouponVO;
import edu.sysu.sdcs.coupon.view.CustomerCouponVO;
import edu.sysu.sdcs.coupon.view.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@Api(value = "优惠券Controller")
@RestController
@RequestMapping("/api")
public class CouponController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private UserService userService;

    @ApiOperation("新增优惠券")
    @PostMapping("/users/{username}/coupons")
    public ResultVO addCoupon(@PathVariable String username,
                              @RequestBody @Valid CouponVO couponVO,
                              HttpServletResponse response,
                              BindingResult results) {
        if (results.hasErrors()) {
            var msg = results.getFieldError().getDefaultMessage();
            return ResponseResult.error(msg);
        }

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        var coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setAmount(couponVO.getAmount());
        coupon.setStock(couponVO.getStock());
        coupon.setDescription(couponVO.getDescription());
        coupon.setSeller(user);
        coupon.setLeft(coupon.getAmount());
        couponService.addCoupon(coupon);

        response.setStatus(201);
        return ResponseResult.success();
    }

    @ApiOperation("秒杀")
    @PatchMapping("/users/{username}/coupons/{name}")
    public ResultVO seckill(@PathVariable String username,
                          @PathVariable String name) {
        User customer = (User) SecurityUtils.getSubject().getPrincipal();
        seckillService.seckillCoupon(name, username, customer);

        return ResponseResult.success();
    }

    @ApiOperation("顾客或商家获取优惠券信息")
    @GetMapping("/users/{username}/coupons")
    public ResultVO customerOrSellerGetCouponInfo(@PathVariable String username,
                                                Integer page) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        User urlUser = userService.getUserByName(username);

        if (null == urlUser) {
            return ResponseResult.error("用户不存在");
        }

        if (urlUser.getRole() == Role.CUSTOMER) {
            if (user.getId() != urlUser.getId()) {
                return ResponseResult.error("无权访问");
            }

            List<Order> list = orderService.getOrdersPage(urlUser, page);
            var resList = new ArrayList<CustomerCouponVO>();
            for (Order order : list) {
                resList.add(new CustomerCouponVO(order.getCoupon().getCouponName(),
                        order.getCoupon().getStock(),
                        order.getCoupon().getDescription()));
            }

            return ResponseResult.success(resList);
        }

        if (urlUser.getRole() == Role.SELLER) {
            var couponList = couponService.getSellerCouponsPage(urlUser, page);

            if (user.getId() == urlUser.getId()) {
                return ResponseResult.success(couponList);
            }

            var resList = new ArrayList<CustomerCouponVO>();
            for(Coupon coupon: couponList) {
                resList.add(new CustomerCouponVO(coupon.getCouponName(),
                        coupon.getStock(), coupon.getDescription()));
            }

            return ResponseResult.success(resList);
        }

        return ResponseResult.error("未知角色");
    }
}
