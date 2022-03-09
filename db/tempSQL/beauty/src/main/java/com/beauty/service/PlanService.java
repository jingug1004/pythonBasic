package com.beauty.service;

import org.springframework.data.domain.Page;

import com.beauty.entity.Plan;

public interface PlanService {
	
	public Plan getPlan(Long pid);
	public Page<Plan> getPlanList(int page);
}
