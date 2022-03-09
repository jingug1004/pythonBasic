package com.beauty.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.beauty.entity.Payments;
import com.beauty.entity.User;

public class PaymentSpecification {

	public static Specification<Payments> payment(User user) {
		return new Specification<Payments>() {
			@Override
			public Predicate toPredicate(Root<Payments> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.<User>get("user"), user);
				Predicate p2 = cb.or(cb.equal(root.<String>get("status"), "paid"), cb.and(cb.equal(root.<String>get("status"), "ready"), cb.isNotNull(root.<String>get("vbank_num"))));
				
				return cb.and(p1, p2);
			}
		};
	}

}