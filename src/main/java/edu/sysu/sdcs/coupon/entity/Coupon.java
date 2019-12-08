package edu.sysu.sdcs.coupon.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "优惠券不能为空")
    @Column(name = "coupon_name")
    private String couponName;

    @NotNull(message = "优惠券剩余数目不能为空")
    @Min(value=0, message = "优惠券剩余数目必须大于0")
    private Integer amount;

    @Column(name = "init_amount")
    @NotNull(message = "优惠券初始数目不能为空")
    @Min(value=0, message = "优惠券初始数目必须大于0")
    private Integer initAmount;

    private String description;

    @NotNull(message = "优惠券面额不能为空")
    @Min(value=0, message = "优惠券面额必须大于0")
    private Integer stock;

    @JoinColumn(name="seller_id")
    @NotNull(message = "商家id不能为空")
    @ManyToOne(cascade = CascadeType.REFRESH, optional = true, fetch = FetchType.EAGER)
    private User seller;

    @Column(name = "create_time")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
}
