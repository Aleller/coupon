package edu.sysu.sdcs.coupon.controller;

import com.alibaba.fastjson.JSONObject;
import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.entity.Order;
import edu.sysu.sdcs.coupon.entity.User;
import edu.sysu.sdcs.coupon.service.CouponService;
import edu.sysu.sdcs.coupon.service.SeckillService;
import edu.sysu.sdcs.coupon.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "优惠券Controller")
@RestController
@RequestMapping("/coupons")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private UserService userService;

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

    @ApiOperation("新增优惠券")
    @ApiImplicitParam(name = "coupon", value = "优惠券实体", required = true, dataType = "Coupon")
    @PostMapping("/coupons/add")
    public String addCoupon(String username, String name, String amount, String description, String stock, HttpServletResponse response) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Coupon coupon = new Coupon();
        coupon.setSeller(user);
        coupon.setCouponName(name);
        coupon.setAmount(Integer.getInteger(amount));
        coupon.setDescription(description);
        coupon.setStock(Integer.getInteger(stock));
        coupon.setInitAmount(Integer.getInteger(amount));

        couponService.addCoupon(coupon, user);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("errMsg", "i don't know");
        //设置返回请求头
        response.setContentType("application/json;charset=utf-8");
        return JSONObject.toJSONString(paramMap);
    }

    @ApiOperation("秒杀")
    @PatchMapping("/coupons/seckill")
    public String seckill(String username, String name, HttpServletResponse response) {
        User customer = (User) SecurityUtils.getSubject().getPrincipal();
        seckillService.seckillCoupon(name, username, customer);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("errMsg", "i don't know");
        //设置返回请求头
        response.setContentType("application/json;charset=utf-8");
        return JSONObject.toJSONString(paramMap);
    }

    @ApiOperation("顾客查商家的优惠券")
    @GetMapping("/user/customer/searchForCoupons")
    public String getSellerCoupon(String sellerName, int page, HttpServletResponse response){
        User seller = userService.getUserByName(sellerName);
        List<Coupon> list = userService.getSellerCouponsPage(seller, page-1);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("data", list);
        paramMap.put("errMsg", "");
        response.setContentType("application/json;charset=utf-8");
        //写出流
        return JSONObject.toJSONString(paramMap);
    }

    @ApiOperation("顾客查已经抢到的优惠券")
    @GetMapping("/user/customer/myCoupons")
    public String customerCoupons(int page, HttpServletResponse response){
        User customer = (User) SecurityUtils.getSubject().getPrincipal();
        List<Order> list = userService.getOrdersPage(customer, page-1);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("data", list);
        paramMap.put("errMsg", "");
        response.setContentType("application/json;charset=utf-8");
        //写出流
        return JSONObject.toJSONString(paramMap);
    }

    @ApiOperation("商家查看自己的优惠券")
    @GetMapping("/user/seller/myCoupons")
    public String sellerCoupons(int page, HttpServletResponse response){
        User seller = (User) SecurityUtils.getSubject().getPrincipal();
        List<Coupon> list = userService.getSellerCouponsPage(seller, page-1);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("data", list);
        paramMap.put("errMsg", "");
        response.setContentType("application/json;charset=utf-8");
        //写出流
        return JSONObject.toJSONString(paramMap);
    }
}
