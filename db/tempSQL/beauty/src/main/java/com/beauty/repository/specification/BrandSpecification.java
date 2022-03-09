package com.beauty.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.beauty.entity.Brand;

public class BrandSpecification {

	public static Specification<Brand> nameLike(String search) {
		return new Specification<Brand>() {
			@Override
			public Predicate toPredicate(Root<Brand> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				String containsLikePattern = getContainsLikePattern(search);
				return cb.like(root.<String>get("brandName"), containsLikePattern);
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