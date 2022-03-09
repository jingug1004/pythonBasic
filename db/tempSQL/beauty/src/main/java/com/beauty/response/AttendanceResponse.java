package com.beauty.response;

import java.util.Date;
import java.util.List;

import com.beauty.entity.Attendance;
import com.beauty.response.result.ResultCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel
public class AttendanceResponse {
	
	@ApiModelProperty(value = "200 성공 99 실패")
	@Getter 
	private final ResultCode resultCode;

	@ApiModelProperty(value = "성공시 success 실패시 실패 내용 확인")
	@Getter 
    private final String message;

	@Getter 
    private final Date timestamp;
    
	@ApiModelProperty(value = "당월 출석 목록")
	@Getter 
	private List<Attendance> attendanceList;
	
	@ApiModelProperty(value = "남은 스탬프")
	@Getter 
	private int stamp;
	
	
    protected AttendanceResponse(final String message, final ResultCode resultCode, final List<Attendance> attendanceList, final int stamp) {
        this.message = message;
        this.resultCode = resultCode;
        this.attendanceList = attendanceList;
        this.stamp = stamp;
        this.timestamp = new java.util.Date();
    }

    public static AttendanceResponse of(final String message, final ResultCode resultCode,  final List<Attendance> attendanceList, final int stamp) {
        return new AttendanceResponse(message, resultCode, attendanceList, stamp);
    }
}
