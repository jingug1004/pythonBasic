package com.beauty.response;

import java.util.Date;
import java.util.List;

import com.beauty.entity.Plan;
import com.beauty.response.result.ResultCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel
public class PlanResponse {
	
	@ApiModelProperty(value = "200 성공 99 실패")
	@Getter 
	private final ResultCode resultCode;

	@ApiModelProperty(value = "성공시 success 실패시 실패 내용 확인")
	@Getter 
    private final String message;

	@Getter 
    private final Date timestamp;
    
	@ApiModelProperty(value = "기획전")
	@Getter 
	private List<Plan> planList;
	
    protected PlanResponse(final String message, final ResultCode resultCode, final List<Plan> planList) {
        this.message = message;
        this.resultCode = resultCode;
        this.planList = planList;
        this.timestamp = new java.util.Date();
    }

    public static PlanResponse of(final String message, final ResultCode resultCode,  final List<Plan> planList) {
        return new PlanResponse(message, resultCode, planList);
    }
}
