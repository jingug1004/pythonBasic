package com.beauty.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.ProductDelivery;


@Repository
@Qualifier(value = "productDeliveryRepository")
public interface ProductDeliveryRepository extends JpaRepository<ProductDelivery, Long> {
	
	
}
