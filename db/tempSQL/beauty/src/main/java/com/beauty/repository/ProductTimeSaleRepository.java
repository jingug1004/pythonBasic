package com.beauty.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Product;
import com.beauty.entity.ProductTimeSale;


@Repository
@Qualifier(value = "productTimeSaleRepository")
public interface ProductTimeSaleRepository extends JpaRepository<ProductTimeSale, Long>, JpaSpecificationExecutor<ProductTimeSale> {
	
	//public List<ProductTimeSale> findByProdCountGreaterThanOrEndDateAfter(int count, Date after);
	public ProductTimeSale findByProductAndTimeType(Product product, int type);
	public ProductTimeSale findByProduct(Product product);
}
