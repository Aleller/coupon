package edu.sysu.sdcs.coupon.view;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CouponVO {
    @NotNull(message = "优惠券名称不能为空")
    @Column(name = "coupon_name")
    private String name;

    @NotNull(message = "优惠券数目不能为空")
    @Min(value=0, message = "优惠券总数目必须大于0")
    private Integer amount;

    private String description;

    @NotNull(message = "优惠券面额不能为空")
    @Min(value=0, message = "优惠券面额必须大于0")
    private Integer stock;
}
