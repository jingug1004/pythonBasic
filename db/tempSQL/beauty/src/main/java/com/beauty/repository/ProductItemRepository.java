package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Product;
import com.beauty.entity.ProductItem;


@Repository
@Qualifier(value = "productItemRepository")
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
	public List<ProductItem> findByProduct(Product product);
	public List<ProductItem> findByProductAndStopSelling(Product product, int stopSelling);
	public List<ProductItem> findByProduct(Product product, Sort sort);
}
