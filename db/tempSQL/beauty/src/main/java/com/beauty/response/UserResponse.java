package com.beauty.response;

import java.util.Date;

import com.beauty.entity.User;
import com.beauty.response.result.ResultCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

public class UserResponse {
	@ApiModelProperty(value = "200 성공 99 실패 10 업데이트 필요")
	@Getter 
	private final ResultCode resultCode;

	@ApiModelProperty(value = "성공시 success 실패시 실패 내용 확인")
	@Getter 
    private final String message;

	@Getter 
    private final Date timestamp;
    
	@ApiModelProperty(value = "유저 정보")
	@Getter 
	private User user;
	
    protected UserResponse(final String message, final ResultCode resultCode, final User user) {
        this.message = message;
        this.resultCode = resultCode;
        this.user = user;
        this.timestamp = new java.util.Date();
    }

    public static UserResponse of(final String message, final ResultCode resultCode, final User user) {
        return new UserResponse(message, resultCode, user);
    }
}
