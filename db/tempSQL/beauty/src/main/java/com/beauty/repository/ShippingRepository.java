package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Shipping;
import com.beauty.entity.User;


@Repository
@Qualifier(value = "shippingRepository")
public interface ShippingRepository extends JpaRepository<Shipping, Long> {
	
	public Shipping findByUserAndDefAddr(User user, String def_addr);
	public List<Shipping> findFirst3ByUser(User user, Sort sort);
}
