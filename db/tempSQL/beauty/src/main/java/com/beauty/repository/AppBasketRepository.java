package com.beauty.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.AppBasket;
import com.beauty.entity.User;

@Repository
@Qualifier(value = "appBasketRepository")
public interface AppBasketRepository extends JpaRepository<AppBasket, Long> {
	public AppBasket findByUser(User user);
}
