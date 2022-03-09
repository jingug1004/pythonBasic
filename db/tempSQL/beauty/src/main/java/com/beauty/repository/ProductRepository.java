package com.beauty.repository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Brand;
import com.beauty.entity.Product;
import com.beauty.entity.User;


@Repository
@Qualifier(value = "productRepository")
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
	
	public Page<Product> findBySeller(User user, Pageable pageable);
	
	public Page<Product> findByBrandAndStopSelling(Brand brand, int stopSelling, Pageable pageable);
	public List<Product> findByProductIdNotIn(List<Long> ids);
	public List<Product> findByStopSellingAndProductIdNotIn(int selling, List<Long> ids);
	public List<Product> findByStopSelling(int selling);
	public List<Product> findByRegDateAfterAndBoxAndStopSellingOrderByRegDateDesc(Date beforDate, int box, int stopSelling);

	public List<Product> findTop50ByStopSellingOrderByScoreDesc(int stopSelling);
	
	public List<Product> findTop100ByBoxAndStopSellingOrderByScoreDesc(int box, int stopSelling);
	public List<Product> findTop6ByOrderByScoreDesc();
	
	public List<Product> findByBoxAndStopSellingOrderByRegDateDesc(int box, int stopSelling);
	public List<Product> findByBoxOrderByRegDateDesc(int box);
	public List<Product> findByStopSellingAndProdNameContainingOrProdDescContainingOrBrandIn(int stopSelling, String search, String search2, List<Brand> brand);
	

	public List<Product> findByBrandIn(List<Brand> brand);
	
	public List<Product> findByProductIdInAndBrandIn(List<Long> ids, List<Brand> brand);
}
