package com.beauty.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.OrderCoupon;
import com.beauty.entity.User;

@Repository
@Qualifier(value = "OrderCouponRepository")
public interface OrderCouponRepository extends JpaRepository<OrderCoupon, Long> {
	public OrderCoupon findByOrderCpIdAndUser(Long orderCpId, User user);
}
