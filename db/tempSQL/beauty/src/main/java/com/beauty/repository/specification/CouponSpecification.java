package com.beauty.repository.specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.beauty.common.DateUtil;
import com.beauty.entity.Coupon;

public class CouponSpecification {

	public static Specification<Coupon> nameLike(String search) {
		return new Specification<Coupon>() {
			@Override
			public Predicate toPredicate(Root<Coupon> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				String containsLikePattern = getContainsLikePattern(search);
				return cb.like(root.<String>get("cpName"), containsLikePattern);
			}
		};
	}
	
	public static Specification<Coupon> startDateLessThanEqual() {
		return new Specification<Coupon>() {
			@Override
			public Predicate toPredicate(Root<Coupon> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return  cb.lessThanOrEqualTo(root.<Date>get("startDate"), DateUtil.getDate());
			}
		};
	}
	
	public static Specification<Coupon> endDateGreaterThanEqual() {
		return new Specification<Coupon>() {
			@Override
			public Predicate toPredicate(Root<Coupon> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return  cb.greaterThanOrEqualTo(root.<Date>get("endDate"), DateUtil.getDate());
			}
		};
	}
	
	
	public static Specification<Coupon> cpCountNotEqual() {
		return new Specification<Coupon>() {
			@Override
			public Predicate toPredicate(Root<Coupon> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return  cb.notEqual(root.<Integer>get("cpCount"), 0);
			}
		};
	}

	private static String getContainsLikePattern(String searchTerm) {
		if (searchTerm == null || searchTerm.isEmpty()) {
			return "%";
		}
		else {
			return "%" + searchTerm.toLowerCase() + "%";
		}
	}
	//
	//    public static Specification<Brand> stylerEq(long stylerId) {
	//        return new Specification<Brand>() {
	//            @Override
	//            public Predicate toPredicate(Root<Brand> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	//                return cb.equal(root.get("id"), stylerId);
	//            }
	//        };
	//    }

}