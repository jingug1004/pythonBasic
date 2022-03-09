package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.PlanGroup;
import com.beauty.entity.PlanProduct;


@Repository
@Qualifier(value = "planProductRepository")
public interface PlanProductRepository extends JpaRepository<PlanProduct, Long> {
	public List<PlanProduct> findByPlanGroup(PlanGroup planGroup);

	public void delete(List<PlanGroup> products);
}
