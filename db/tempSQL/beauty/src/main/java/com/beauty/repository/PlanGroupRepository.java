package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Plan;
import com.beauty.entity.PlanGroup;


@Repository
@Qualifier(value = "planGroupRepository")
public interface PlanGroupRepository extends JpaRepository<PlanGroup, Long> {
	
	public List<PlanGroup> findByPlanOrderBySortAsc(Plan plan);
	public PlanGroup findFirstByOrderBySortDesc();
	
//	public void proddelete();
}
