package edu.sysu.sdcs.coupon.controller;

import com.alibaba.fastjson.JSONObject;
import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.entity.Order;
import edu.sysu.sdcs.coupon.entity.User;
import edu.sysu.sdcs.coupon.enums.Role;
import edu.sysu.sdcs.coupon.service.CouponService;
import edu.sysu.sdcs.coupon.service.SeckillService;
import edu.sysu.sdcs.coupon.service.UserService;
import edu.sysu.sdcs.coupon.view.ControllerSellerCouponVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Api(value = "优惠券Controller")
@RestController
//@RequestMapping("/coupons")
@RequestMapping("/api")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private UserService userService;



//    @ApiOperation("新增优惠券")
//    @ApiImplicitParam(name = "coupon", value = "优惠券实体", required = true, dataType = "Coupon")
//    @PostMapping()
//    public boolean addCoupon(@Valid @RequestBody Coupon coupon) {
////        couponService.addCoupon(coupon);
//        return true;
//    }
//
//    @PatchMapping("/{couponName}")
//    public boolean seckillCoupon(@PathVariable(value = "couponName") Integer couponId) {
////        seckillService.seckillCoupon(123, couponId);
//        return true;
//    }

    @ApiOperation("新增优惠券")
    @PostMapping("/users/{username}/coupons")
    public String addCoupon(@PathVariable String username, String name, String amount, String description, String stock, HttpServletResponse response) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Coupon coupon = new Coupon();
        coupon.setSeller(user);
        coupon.setCouponName(name);
        coupon.setAmount(Integer.parseInt(amount));
        coupon.setDescription(description);
        coupon.setStock(Integer.parseInt(stock));
        coupon.setInitAmount(Integer.parseInt(amount));
        coupon.setCreateTime(Calendar.getInstance().getTime());
        coupon.setUpdateTime(Calendar.getInstance().getTime());

        couponService.addCoupon(coupon, user);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("errMsg", "i don't know");
        //设置返回请求头
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(201);
        return JSONObject.toJSONString(paramMap);
    }

    @ApiOperation("秒杀")
    @PatchMapping("/users/{username}/coupons/{name}")
    public String seckill(@PathVariable String username, @PathVariable String name, HttpServletResponse response) {
        User customer = (User) SecurityUtils.getSubject().getPrincipal();
        seckillService.seckillCoupon(name, username, customer);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("errMsg", "i don't know");
        //设置返回请求头
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(201);
        return JSONObject.toJSONString(paramMap);
    }

    @ApiOperation("顾客或商家获取优惠券信息")
    @GetMapping("/users/{username}/coupons")
    public String customerOrSellerGetCouponInfo(@PathVariable String username, int page, HttpServletResponse response) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        if (username.equals(user.getUsername())) {
            User customer = user;
            if (user.getRole() == Role.CUSTOMER) {
                //返回该顾客自己的优惠券信息
                List<Order> list = userService.getOrdersPage(customer, page);
                List<Map> paramList = new ArrayList<>();
                for (Order order : list
                ) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("name", order.getCoupon().getCouponName());
                    map.put("stock", order.getCoupon().getStock().toString());
                    map.put("description", order.getCoupon().getDescription());
                    paramList.add(map);
                }

                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("data", paramList);
                paramMap.put("errMsg", "");
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(200);
                //写出流
                return JSONObject.toJSONString(paramMap);
            } else {
                //返回该商家剩余的优惠券信息
                List<Coupon> list = userService.getSellerCouponsPage(customer, page);

                List<Map> paramList = new ArrayList<>();
                for (Coupon c : list
                ) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("name", c.getCouponName());
                    map.put("amount", c.getInitAmount().toString());
                    map.put("left", c.getAmount().toString());
                    map.put("stock", c.getStock().toString());
                    map.put("description", c.getDescription());
                    paramList.add(map);
                }

                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("data", paramList);
                paramMap.put("errMsg", "");
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(200);
                //写出流
                return JSONObject.toJSONString(paramMap);
            }
        } else {
            User urlUser = userService.getUserByName(username);
            if (urlUser.getRole() == Role.SELLER) {
                //返回该商家剩余的优惠券信息
                List<Coupon> list = userService.getSellerCouponsPage(urlUser, page);

                List<Map> paramList = new ArrayList<>();
                for (Coupon c : list
                ) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("name", c.getCouponName());
                    map.put("amount", c.getInitAmount().toString());
                    map.put("left", c.getAmount().toString());
                    map.put("stock", c.getStock().toString());
                    map.put("description", c.getDescription());
                    paramList.add(map);
                }

                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("data", paramList);
                paramMap.put("errMsg", "");
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(200);
                //写出流
                return JSONObject.toJSONString(paramMap);
            }

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("errMsg", "");
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(200);
            //写出流
            return JSONObject.toJSONString(paramMap);
        }
    }

    @ApiOperation("顾客查商家的优惠券")
    @GetMapping("/customerSearchForCoupons")
    public String customerGetSellerCoupon(String sellerName, Integer page, HttpServletResponse response){
        User seller = userService.getUserByName(sellerName);
        List<Coupon> list = userService.getSellerCouponsPage(seller, page);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("data", list);
        paramMap.put("errMsg", "");
        response.setContentType("application/json;charset=utf-8");
        //写出流
        return JSONObject.toJSONString(paramMap);
    }

    @ApiOperation("顾客查已经抢到的优惠券")
    @GetMapping("/customerMyCoupons")
    public String customerGetMyCoupons(int page, HttpServletResponse response){
        User customer = (User) SecurityUtils.getSubject().getPrincipal();
        List<Order> list = userService.getOrdersPage(customer, page);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("data", list);
        paramMap.put("errMsg", "");
        response.setContentType("application/json;charset=utf-8");
        //写出流
        return JSONObject.toJSONString(paramMap);
    }

    @ApiOperation("商家查看自己的优惠券")
    @GetMapping("/sellerMyCoupons")
    public String sellerGetMyCoupons(int page, HttpServletResponse response){
        User seller = (User) SecurityUtils.getSubject().getPrincipal();
        List<Coupon> list = userService.getSellerCouponsPage(seller, page);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("data", list);
        paramMap.put("errMsg", "");
        response.setContentType("application/json;charset=utf-8");
        //写出流
        return JSONObject.toJSONString(paramMap);
    }
}
