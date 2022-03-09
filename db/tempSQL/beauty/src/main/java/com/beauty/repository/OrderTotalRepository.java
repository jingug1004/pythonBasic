package com.beauty.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.OrderTotal;
import com.beauty.entity.User;

@Repository
@Qualifier(value = "OrderTotalRepository")
public interface OrderTotalRepository extends JpaRepository<OrderTotal, String> {
	public OrderTotal findByOrderIdAndUser(String orderId, User user);
}
