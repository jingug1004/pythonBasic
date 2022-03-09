package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.MainFirstBanner;


@Repository
@Qualifier(value = "mainFirstBannerRepository")
public interface MainFirstBannerRepository extends JpaRepository<MainFirstBanner, Long> {
	
	public Page<MainFirstBanner> findByShowType(int showType, Pageable pageRequest);
	public List<MainFirstBanner> findByShowTypeAndShowYn(int showType, String show);
	public MainFirstBanner findFirstByShowTypeOrderBySortDesc(int showType);
	
	public List<MainFirstBanner> findBySortLessThanEqualAndSortGreaterThan(int neworder, int oldorder);
	public List<MainFirstBanner> findBySortLessThanAndSortGreaterThanEqual(int oldorder, int neworder);
}
