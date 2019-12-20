package edu.sysu.sdcs.coupon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Coupon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "coupon_name")
    private String couponName;

    private Integer amount;

    @Column(name = "left_amount")
    private Integer left;

    private String description;

    private Integer stock;

    @JoinColumn(name="seller_id")
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
