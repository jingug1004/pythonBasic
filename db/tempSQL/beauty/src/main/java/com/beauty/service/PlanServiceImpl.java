package com.beauty.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.beauty.BeautyConstants;
import com.beauty.entity.Plan;
import com.beauty.repository.PlanRepository;

@Service
public class PlanServiceImpl implements PlanService {

	@Autowired
	private PlanRepository planRepository;

	
	@Override
	public Plan getPlan(Long pid) {
		return planRepository.findOne(pid);
	}
	
	
	@Override
	@Transactional
	public Page<Plan> getPlanList(int page) {
		PageRequest pageRequest = new PageRequest(page, BeautyConstants.PAGE_SIZE_PRODUCT, new Sort(Direction.ASC, "sort")); //현재페이지, 조회할 페이지수, 정렬정보
		return planRepository.findByStopPlan(0, pageRequest);
	}




}
