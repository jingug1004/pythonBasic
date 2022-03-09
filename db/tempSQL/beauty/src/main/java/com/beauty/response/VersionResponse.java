package com.beauty.response;

import java.util.Date;

import com.beauty.entity.Version;
import com.beauty.response.result.ResultCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

public class VersionResponse {
	@ApiModelProperty(value = "200 성공 99 업데이트 필요")
	@Getter 
	private final ResultCode resultCode;

	@ApiModelProperty(value = "성공시 success 실패시 실패 내용 확인")
	@Getter 
    private final String message;

	@Getter 
    private final Date timestamp;
    
	@ApiModelProperty(value = "버전 정보")
	@Getter 
	private Version version;
	
    protected VersionResponse(final String message, final ResultCode resultCode, final Version version) {
        this.message = message;
        this.resultCode = resultCode;
        this.version = version;
        this.timestamp = new java.util.Date();
    }

    public static VersionResponse of(final String message, final ResultCode resultCode, final Version version) {
        return new VersionResponse(message, resultCode, version);
    }
}
