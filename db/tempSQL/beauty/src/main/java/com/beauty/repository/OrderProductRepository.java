package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.OrderProduct;
import com.beauty.entity.OrderTotal;

@Repository
@Qualifier(value = "OrderProductRepository")
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
	
	public List<OrderProduct> findByOrderTotal(OrderTotal orderTotal);
}
