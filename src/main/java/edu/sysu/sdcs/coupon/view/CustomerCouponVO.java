package edu.sysu.sdcs.coupon.view;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomerCouponVO {
    private String name;
    private Integer stock;
    private String description;
}
