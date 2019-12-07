package edu.sysu.sdcs.coupon.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "coupon_id")
    private Integer couponId;
}
