package com.beauty.repository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Coupon;
import com.beauty.entity.CouponUser;
import com.beauty.entity.User;


@Repository
@Qualifier(value = "couponUserRepository")
public interface CouponUserRepository extends JpaRepository<CouponUser, Long> {
	
	public List<CouponUser> findByUserAndUseYn(User user, String useYn);
	
	public List<CouponUser> findByUser(User user);
	public List<CouponUser> findByUserAndEndDateAfter(User user, Date after);
	public List<CouponUser> findByUserAndEndDateAfterAndUseYn(User user, Date after, String useYn);
	public List<CouponUser> findByUserAndCouponIn(User user, List<Coupon> coupon);
	public List<CouponUser> findByUserAndCouponAndUseYn(User user, Coupon coupon, String useYn);
}
