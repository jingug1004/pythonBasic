package com.beauty.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import com.beauty.entity.Role;
import com.beauty.entity.User;
import com.beauty.entity.UserRole;

public class UserSpecification {

	public static Specification<User> like(String node, String search) {
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				String containsLikePattern = getContainsLikePattern(search);
				return cb.like(root.<String>get(node), containsLikePattern);
			}
		};
	}

	public static Specification<User> equal(String node, String value) {
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.<String>get(node), value);
			}
		};
	}

	public static Specification<User> roleEqual(Role role) {
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				final Subquery<Role> personQuery = query.subquery(Role.class);
				final Join<User, UserRole> ids = root.join("roles");
				personQuery.select(ids.<Role> get("role"));
				personQuery.where(cb.equal(ids.<Role> get("role"), role));
				return cb.equal(ids.<Role> get("role"), role);
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