package edu.sysu.sdcs.coupon.repository;

import edu.sysu.sdcs.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepo extends JpaRepository<Coupon, Integer> {
}
