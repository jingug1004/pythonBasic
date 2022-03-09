package com.beauty.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Product;
import com.beauty.entity.User;
import com.beauty.entity.Wishlist;

@Repository
@Qualifier(value = "wishlistRepository")
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
	
	public List<Wishlist> findByUserOrderByRegDateDesc(User user);
	public Wishlist findByUserAndProduct(User user, Product product);
	
	@Modifying
	@Transactional
	@Query(value="delete from Wishlist c where c.user = ?1 and c.wishId = ?2")
	public void deleteByUserAndWishId(User user, Long wishId);
}
