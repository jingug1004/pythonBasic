package com.beauty.response;

import java.util.Date;

import com.beauty.response.result.ResultCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel
public class CommonResponse {

	@ApiModelProperty(value = "200 성공 99 실패 10 전화번호 중복")
	@Getter 
	private final ResultCode resultCode;

	@ApiModelProperty(value = "성공시 success 실패시 실패 내용 확인")
	@Getter 
    private final String message;

	@Getter 
    private final Date timestamp;
	

	protected CommonResponse(final String message, final ResultCode resultCode) {
        this.message = message;
        this.resultCode = resultCode;
        this.timestamp = new java.util.Date();
    }
	
    public static CommonResponse of(final String message, final ResultCode resultCode) {
        return new CommonResponse(message, resultCode);
    }
}
