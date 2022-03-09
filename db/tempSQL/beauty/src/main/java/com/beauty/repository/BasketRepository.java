package com.beauty.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.beauty.entity.ProductItem;
import com.beauty.entity.ShoppingBasket;
import com.beauty.entity.User;

@Repository
@Qualifier(value = "basketRepository")
public interface BasketRepository extends JpaRepository<ShoppingBasket, Long> {
	
	public List<ShoppingBasket> findByUserOrderByRegDateDesc(User user);
	public ShoppingBasket findByUserAndProductItem(User user, ProductItem productItem);
	public ShoppingBasket findByUserAndBasketId(User user, Long basketId);
	
	@Modifying
	@Transactional
	@Query(value="delete from ShoppingBasket c where c.user = ?1 and c.basketId = ?2")
	public void deleteByUserAndBasketId(User user, Long basketId);
}
