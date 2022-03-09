package com.beauty.response;

import java.util.Date;

import com.beauty.entity.DeviceInfo;
import com.beauty.response.result.ResultCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

public class DeviceInfoResponse {
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
	private DeviceInfo deviceInfo;
	
    protected DeviceInfoResponse(final String message, final ResultCode resultCode, final DeviceInfo deviceInfo) {
        this.message = message;
        this.resultCode = resultCode;
        this.deviceInfo = deviceInfo;
        this.timestamp = new java.util.Date();
    }

    public static DeviceInfoResponse of(final String message, final ResultCode resultCode, final DeviceInfo deviceInfo) {
        return new DeviceInfoResponse(message, resultCode, deviceInfo);
    }
}
