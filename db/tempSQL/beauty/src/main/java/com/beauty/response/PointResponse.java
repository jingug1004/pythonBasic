package com.beauty.response;

import java.util.Date;

import org.springframework.data.domain.Page;

import com.beauty.entity.UserPoint;
import com.beauty.response.result.ResultCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel
public class PointResponse {
	
	@ApiModelProperty(value = "200 성공 99 실패")
	@Getter 
	private final ResultCode resultCode;

	@ApiModelProperty(value = "성공시 success 실패시 실패 내용 확인")
	@Getter 
    private final String message;

	@Getter 
    private final Date timestamp;
    
	@ApiModelProperty(value = "현재 포인트")
	@Getter 
	private Long myPoint;
	
	@ApiModelProperty(value = "전체 포인트")
	@Getter 
	private Long totalPoint;
	
	@ApiModelProperty(value = "사용 포인트")
	@Getter 
	private Long usePoint;
	
	
	@ApiModelProperty(value = "포인트 리스트")
	@Getter 
	private Page<UserPoint> pointList;
	
    protected PointResponse(final String message, final ResultCode resultCode, final Long myPoint, final Long totalPoint, final Long usePoint, final Page<UserPoint> pointList) {
        this.message = message;
        this.resultCode = resultCode;
        this.pointList = pointList;
        this.myPoint = myPoint;
        this.totalPoint = totalPoint;
        this.usePoint = usePoint;
        this.timestamp = new java.util.Date();
    }

    public static PointResponse of(final String message, final ResultCode resultCode,  final Long myPoint, final Long totalPoint, final Long usePoint, final Page<UserPoint> pointList) {
        return new PointResponse(message, resultCode, myPoint, totalPoint, usePoint, pointList);
    }
}
