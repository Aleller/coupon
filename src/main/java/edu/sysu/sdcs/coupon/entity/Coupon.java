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

    @NotNull(message = "用户名不能为空")
    private String name;

    @NotNull(message = "优惠券数目不能为空")
    @Min(value=0, message = "优惠券数目必须大于0")
    private Integer amount;

    private String description;

    @NotNull(message = "优惠券面额不能为空")
    @Min(value=0, message = "优惠券面额必须大于0")
    private Integer stock;

    @Column(name = "create_time")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column(name = "seller_id")
    private Integer sellerId;

}
