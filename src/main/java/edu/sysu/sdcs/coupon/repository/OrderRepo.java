package edu.sysu.sdcs.coupon.repository;

import edu.sysu.sdcs.coupon.entity.Coupon;
import edu.sysu.sdcs.coupon.entity.Order;
import edu.sysu.sdcs.coupon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Integer> {
    Page<Order> findOrdersByUserEquals(User user, Pageable pageable);

    Order findByUserEqualsAndCouponEquals(User user, Coupon coupon);
}
