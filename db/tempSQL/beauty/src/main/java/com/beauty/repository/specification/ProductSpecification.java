package com.beauty.repository.specification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.beauty.entity.Brand;
import com.beauty.entity.Product;

public class ProductSpecification {

	public static Specification<Product> likeSearch(String search, Long box, List<Long> id) {
		return new Specification<Product>() {
			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				String containsLikePattern = getContainsLikePattern(search);
				Predicate p1 = cb.or(cb.like(root.<String>get("prodName"), containsLikePattern), cb.like(root.<String>get("prodDesc"), containsLikePattern));
				Predicate p2 = cb.equal(root.<Long>get("box"), box);
				if(id == null) {
					return cb.and(p1, p2);	
				}

				Predicate predicate = root.get("productId").in(id);
				Predicate p3 = cb.not(predicate);

				return cb.and(p1, p2, p3);
			}
		};
	}
	
	public static Specification<Product> likeSearch(String search, List<Brand> brand) {
		return new Specification<Product>() {
			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				String containsLikePattern = getContainsLikePattern(search);
				Predicate p1 = null;
				if(brand == null || brand.size() == 0) {
					p1 = cb.or(cb.like(root.<String>get("prodName"), containsLikePattern), cb.like(root.<String>get("prodDesc"), containsLikePattern));
				} else {
					p1 = cb.or(cb.like(root.<String>get("prodName"), containsLikePattern), cb.like(root.<String>get("prodDesc"), containsLikePattern), root.get("brand").in(brand));
				}
				Predicate p2 = cb.equal(root.<Integer>get("stopSelling"), 0);

				return cb.and(p2, p1 );
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