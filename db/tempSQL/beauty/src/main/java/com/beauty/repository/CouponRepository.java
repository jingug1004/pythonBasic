package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Coupon;


@Repository
@Qualifier(value = "couponRepository")
public interface CouponRepository extends JpaRepository<Coupon, String>, JpaSpecificationExecutor<Coupon> {
	
//	public List<Coupon> findByStartDateBetweenAndCpCountNotAnd(Date startDate, Date endDate, int cpCount);
	public List<Coupon> findByDownloadAndAuto(int download, int auto);
	public List<Coupon> findByAuto(int auto);
}
