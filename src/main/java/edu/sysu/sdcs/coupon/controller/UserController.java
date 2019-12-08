package edu.sysu.sdcs.coupon.controller;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.tools.json.JSONUtil;
import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.entity.Order;
import edu.sysu.sdcs.coupon.entity.User;
import edu.sysu.sdcs.coupon.enums.Role;
import edu.sysu.sdcs.coupon.service.CouponService;
import edu.sysu.sdcs.coupon.service.OrderService;
import edu.sysu.sdcs.coupon.service.SeckillService;
import edu.sysu.sdcs.coupon.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillService seckillService;

    @PostMapping("/api/auth")
    public String login(String username, String password, HttpServletResponse response) throws IOException {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //token.setRememberMe(true);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);

        //登录成功返回
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("kind", "saler or customer");
        paramMap.put("errMsg", "登录成功!");
        //设置返回请求头
        String sessionId = (String) SecurityUtils.getSubject().getSession().getId();
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Authorization", sessionId);
        //写出流
        return JSONObject.toJSONString(paramMap);
    }

    @GetMapping("/logout")
    public void logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
    }

    @GetMapping("/read")
    public String read(HttpServletRequest request) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user.getUsername() + " is " + userService.read();
    }

    @PostMapping("/api/users")
    public String register(String username, String password, String kind, HttpServletResponse response) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        if(kind.equals("customer")){
            user.setRole(Role.CUSTOMER);
        }else{
            user.setRole(Role.SELLER);
        }

        userService.register(user);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("errMsg", "i don't know");
        //设置返回请求头
        response.setContentType("application/json;charset=utf-8");
        return JSONObject.toJSONString(paramMap);
    }

    @PostMapping("/api/users/{username}/coupons")
    public String addCoupon(@PathVariable String username, String name, String amount, String description, String stock, HttpServletResponse response) {
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

    @GetMapping("/api/users/{username}/coupons")
    public String getCouponInfo(@PathVariable String username, int page, HttpServletResponse response) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Map<String, Object> paramMap = new HashMap<>();

        if(username.equals(user.getUsername())){
            //若url中的username与头部authorization指定的用户名一致，则返回该用户自己/商家剩余的优惠券信息
            List<Object> jsonList = new ArrayList<Object>();
            Map<String, String> map = new HashMap<String, String>();

            if(user.getRole() == Role.SELLER){
                List<Coupon> couponList = userService.getSellerCouponsPage(user, page);
                for (Coupon coupon : couponList
                     ) {
                    map.put("name", coupon.getCouponName());
                    map.put("amount", coupon.getInitAmount().toString());
                    map.put("left", coupon.getAmount().toString());
                    map.put("stock", coupon.getStock().toString());
                    map.put("description", coupon.getDescription());
                    jsonList.add(map);
                    map.clear();
                }
            }else{
                List<Order> orderList = userService.getOrdersPage(user, page);
                for (Order order : orderList
                ) {
                    map.put("name", order.getCoupon().getCouponName());
                    map.put("stock", order.getCoupon().getStock().toString());
                    map.put("description", order.getCoupon().getDescription());
                    jsonList.add(map);
                    map.clear();
                }
            }

            return JSONObject.toJSONString(jsonList);
        }else{
            User user_url = userService.getUserByName(username);
            if(user_url.getRole() == Role.SELLER){
                userService.sellserCouponsLeft(user_url);
                //todo......
                //若url中的username与头部authorization指定的用户名不一致,
                // 且url指定的用户名身份为商家，则获取该商家的优惠券余量
            }else{

                //todo......
                //若url中的username与头部authorization指定的用户名不一致,
                // 且url指定的用户名身份为普通用户，则返回认证失败的错误
            }



        }




        paramMap.put("errMsg", "i don't know");
        //设置返回请求头
        response.setContentType("application/json;charset=utf-8");
        return JSONObject.toJSONString(paramMap);
    }

    @PatchMapping("/api/users/{username}/coupons/{name}")
    public String seckill(@PathVariable String username, @PathVariable String name, HttpServletResponse response) {
        User customer = (User) SecurityUtils.getSubject().getPrincipal();
        seckillService.seckillCoupon(name, username, customer);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("errMsg", "i don't know");
        //设置返回请求头
        response.setContentType("application/json;charset=utf-8");
        return JSONObject.toJSONString(paramMap);
    }
}