package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.beauty.entity.SpecialCoupon;


@Repository
@Qualifier(value = "specialCouponRepository")
public interface SpecialCouponRepository extends JpaRepository<SpecialCoupon, Long>, JpaSpecificationExecutor<SpecialCoupon> {
	public List<SpecialCoupon> findByCpId(String cpId);
}
