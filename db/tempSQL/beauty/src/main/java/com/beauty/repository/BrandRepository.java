package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Brand;


@Repository
@Qualifier(value = "BrandRepository")
public interface BrandRepository extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {
	
	public List<Brand> findByBrandVisibleOrderByBrandNameAsc(String brandVisible);
	public Long countByBrandName(String brandName);

	public List<Brand> findByBrandNameContainingAndBrandVisible(String search, String brandVisible);
}
