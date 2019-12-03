package edu.sysu.sdcs.web.repository;

import edu.sysu.sdcs.web.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepo extends JpaRepository<Coupon, Integer> {
}
