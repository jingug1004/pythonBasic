package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Event;
import com.beauty.entity.EventCoupon;


@Repository
@Qualifier(value = "eventCouponRepository")
public interface EventCouponRepository extends JpaRepository<EventCoupon, Long> {
	
	public List<EventCoupon> findByEvent(Event event);
	
}
