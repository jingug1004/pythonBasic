package com.beauty.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.AppBasketProduct;
import com.beauty.entity.AppBasketSeller;
import com.beauty.entity.Product;

@Repository
@Qualifier(value = "appBasketProductRepository")
public interface AppBasketProductRepository extends JpaRepository<AppBasketProduct, Long> {
	
	public AppBasketProduct findByProductAndBasketSeller(Product product, AppBasketSeller seller);
}
