package edu.sysu.sdcs.coupon.service.impl;

import com.alibaba.fastjson.JSONObject;
import edu.sysu.sdcs.coupon.ordermq.MQSender;
import edu.sysu.sdcs.coupon.service.SeckillService;
import edu.sysu.sdcs.coupon.view.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService{
    @Autowired
    MQSender mqSender;

    @Override
    public void seckillCoupon(Integer userId, Integer couponId) {
        log.info("==> [seckill] {}", couponId);

        var orderVO = new OrderVO();
        orderVO.setCouponId(couponId);
        orderVO.setUserId(userId);
        mqSender.send(JSONObject.toJSONString(orderVO));
    }
}
