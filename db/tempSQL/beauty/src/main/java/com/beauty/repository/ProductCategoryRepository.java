package com.beauty.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Category;
import com.beauty.entity.Product;
import com.beauty.entity.ProductCategory;


@Repository
@Qualifier(value = "productCategoryRepository")
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long>, JpaSpecificationExecutor<ProductCategory> {
	
	public Page<ProductCategory> findByCategoryIn(List<Category> list, Pageable pageable);
	public List<ProductCategory> findByCategoryIn(List<Category> list);
	public List<ProductCategory> findByCategoryInAndProductIn(List<Category> list, List<Product> pList);
	public List<ProductCategory> findByCategory(Category category);
	public List<ProductCategory> findByProduct(Product product);
	
	public ProductCategory findByCategoryAndProduct(Category category, Product product);
	
	public List<ProductCategory> findByCategoryInAndProductNot(List<Category> category, Product product);
	
	@Modifying
	@Transactional
	@Query(value="delete from ProductCategory p where p.product = ?1")
	public void deleteByProduct(Product product);
}
