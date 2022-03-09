package com.beauty.repository.specification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.beauty.entity.Brand;
import com.beauty.entity.DetailTopContent;
import com.beauty.entity.Product;

public class DetailTopSpecification {

	public static Specification<DetailTopContent> detailTop(Brand brand, Product product, List<Long> category) {
		return new Specification<DetailTopContent>() {
			@Override
			public Predicate toPredicate(Root<DetailTopContent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				if(category == null || category.size() == 0) {
					Predicate p1 = cb.and(cb.equal(root.<Integer>get("topType"), 0), cb.equal(root.<Long>get("contentId"), brand.getBrandId()));
					Predicate p3 = cb.and(cb.equal(root.<Integer>get("topType"), 2), cb.equal(root.<Long>get("contentId"), product.getProductId()));
					return cb.or(p1, p3 );
				} else {
					Predicate p1 = cb.and(cb.equal(root.<Integer>get("topType"), 0), cb.equal(root.<Long>get("contentId"), brand.getBrandId()));
					Predicate p2 = cb.and(cb.equal(root.<Integer>get("topType"), 1), root.<Long>get("contentId").in(category));
					Predicate p3 = cb.and(cb.equal(root.<Integer>get("topType"), 2), cb.equal(root.<Long>get("contentId"), product.getProductId()));
					return cb.or(p1, p2, p3 );
				}
			}
		};
	}
	

}