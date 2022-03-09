package com.beauty.service;

import org.springframework.data.domain.Page;

import com.beauty.entity.AttendanceInfo;
import com.beauty.entity.Event;
import com.beauty.response.AttendanceResponse;
import com.beauty.security.auth.JwtAuthenticationToken;

public interface EventService {
	public Event getEvent(Long eid);
	public Page<Event> getEventList(int page);
	public AttendanceResponse attendanceList(JwtAuthenticationToken token);
	public AttendanceResponse attendance(JwtAuthenticationToken token);
	public AttendanceInfo attendanceInfo(JwtAuthenticationToken token);
	public AttendanceResponse stamp(Long stId, JwtAuthenticationToken token);
}
