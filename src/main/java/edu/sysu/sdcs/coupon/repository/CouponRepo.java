package edu.sysu.sdcs.coupon.repository;

import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface CouponRepo extends JpaRepository<Coupon, Integer> {
    Coupon findCouponByCouponNameEquals(String s);

    Page<Coupon> findCouponsBySellerEquals(User seller, Pageable pageable);

}
