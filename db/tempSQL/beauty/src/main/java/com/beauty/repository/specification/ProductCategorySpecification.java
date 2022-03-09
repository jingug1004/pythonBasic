package com.beauty.repository.specification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.beauty.entity.Category;
import com.beauty.entity.Product;
import com.beauty.entity.ProductCategory;

public class ProductCategorySpecification {

	public static Specification<ProductCategory> categoryIn(List<Category> category) {
		return new Specification<ProductCategory>() {
			@Override
			public Predicate toPredicate(Root<ProductCategory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
//				final Subquery<Product> personQuery = query.subquery(Product.class);
				final Join<ProductCategory, Product> ids = root.join("product", JoinType.LEFT);
//				personQuery.select(ids.<Product> get("product"));
//				personQuery.where(cb.equal(ids.<Product> get("stopSelling"), 1));
				
				
				query.groupBy(ids.<Product> get("productId"));
				Predicate p1 = cb.equal(ids.<Product> get("stopSelling"), 0);
				Predicate predicate = root.get("category").in(category);
                				
				return cb.and(p1, predicate);
			}
		};
	}
	

}