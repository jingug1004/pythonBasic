package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Plan;


@Repository
@Qualifier(value = "planRepository")
public interface PlanRepository extends JpaRepository<Plan, Long> {
	
	public Page<Plan> findByStopPlan(int stop, Pageable pageable);
	public List<Plan> findByStopPlan(int stop);
	public Plan findFirstByOrderBySortDesc();
	
	
	public List<Plan> findBySortLessThanEqualAndSortGreaterThan(int neworder, int oldorder);
	public List<Plan> findBySortLessThanAndSortGreaterThanEqual(int oldorder, int neworder);
}
