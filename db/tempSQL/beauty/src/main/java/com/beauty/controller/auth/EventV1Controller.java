package com.beauty.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beauty.entity.AttendanceInfo;
import com.beauty.response.AttendanceResponse;
import com.beauty.security.auth.JwtAuthenticationToken;
import com.beauty.service.EventService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api/v1/event")
public class EventV1Controller {
	public final String TAG = "이벤트 - 로그인시 해당 API 사용";
	
	@Autowired
	private EventService eventService;
	
    /**
     * 해당 월 출석체크 목록
     * @return
     */
    @ApiOperation(value = "당월 출석체크", notes = "당월 출석체크 목록 ", response = AttendanceResponse.class, tags={TAG, })
    @RequestMapping(value="/attendance_list", method=RequestMethod.GET)
    public @ResponseBody AttendanceResponse attendanceList(JwtAuthenticationToken token) {
        return eventService.attendanceList(token);
    }
    
    /**
     * 해당 월 출석체크 
     * @return
     */
    @ApiOperation(value = "당월 출석체크", notes = "당월 출석체크 ", response = AttendanceResponse.class, tags={TAG, })
    @RequestMapping(value="/attendance", method=RequestMethod.PUT)
    public @ResponseBody AttendanceResponse attendance(JwtAuthenticationToken token) {
        return eventService.attendance(token);
    }
    
    /**
     * 출석체크 정보
     * @param token
     * @return
     */
    @ApiOperation(value = "출석체크 정보", notes = "당월 출석체크 정보 ", response = AttendanceResponse.class, tags={TAG, })
    @RequestMapping(value="/attendance_info", method=RequestMethod.GET)
    public @ResponseBody AttendanceInfo attendanceInfo(JwtAuthenticationToken token) {
        return eventService.attendanceInfo(token);
    }
    
    @ApiOperation(value = "스탬프 교환", notes = "스탬프 교환 ", response = AttendanceResponse.class, tags={TAG, })
    @RequestMapping(value="/stamp", method=RequestMethod.POST)
    public @ResponseBody AttendanceResponse stamp(@RequestParam("st_id") Long st_id, JwtAuthenticationToken token) {
        return eventService.stamp(st_id, token);
    }
}
