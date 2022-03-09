package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.MainSecondBanner;


@Repository
@Qualifier(value = "mainSecondBannerRepository")
public interface MainSecondBannerRepository extends JpaRepository<MainSecondBanner, Long> {
	public List<MainSecondBanner> findByShowYn(String show);
	public MainSecondBanner findFirstByOrderBySortDesc();
	
	public List<MainSecondBanner> findBySortLessThanEqualAndSortGreaterThan(int neworder, int oldorder);
	public List<MainSecondBanner> findBySortLessThanAndSortGreaterThanEqual(int oldorder, int neworder);
}
