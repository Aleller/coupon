package edu.sysu.sdcs.coupon.service.impl;

import edu.sysu.sdcs.coupon.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    public void createOrder(Integer couponId) {
        log.info("==>[order] {}", couponId);
//        return true;
    }
}
