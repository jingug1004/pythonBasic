package com.beauty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beauty.entity.Plan;
import com.beauty.service.PlanService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value="/api/plan")
public class PlanController {
	public final String TAG = "기획전";
	
	@Autowired
	private PlanService planService;
	
    @ApiOperation(value = "기획전 상세", notes = "기획전 상세", response = Plan.class, responseContainer="List", tags={TAG, })
    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody Plan getPlan(@ApiParam(name="pid", value="기획전ID", required = true) @RequestParam Long pid) {
        return planService.getPlan(pid);
    }
    
    
    /**
     * 기획전 목록
     * @return
     */
    @ApiOperation(value = "기획전 목록", notes = "기획전 목록", response = Plan.class, responseContainer="List", tags={TAG, })
    @RequestMapping(value="/{page}", method=RequestMethod.GET)
    public @ResponseBody Page<Plan> getCategory(@ApiParam(name="page", value="조회할 페이지  0부터 시작", required = true) @PathVariable int page) {
        return planService.getPlanList(page);
    }
    
    
}
