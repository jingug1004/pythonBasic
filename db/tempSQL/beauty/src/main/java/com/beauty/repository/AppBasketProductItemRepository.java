package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.AppBasketProduct;
import com.beauty.entity.AppBasketProductItem;
import com.beauty.entity.ProductItem;

@Repository
@Qualifier(value = "AppBasketProductItemRepository")
public interface AppBasketProductItemRepository extends JpaRepository<AppBasketProductItem, Long> {
	public List<AppBasketProductItem> findByBasketProduct(AppBasketProduct basketProduct);
	public AppBasketProductItem findByBasketProductAndProductItem(AppBasketProduct basketProduct, ProductItem item);
}
