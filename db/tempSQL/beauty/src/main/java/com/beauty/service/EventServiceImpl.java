package com.beauty.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.beauty.BeautyConstants;
import com.beauty.common.DateUtil;
import com.beauty.entity.Attendance;
import com.beauty.entity.AttendanceInfo;
import com.beauty.entity.Event;
import com.beauty.entity.Stamp;
import com.beauty.entity.StampUser;
import com.beauty.entity.User;
import com.beauty.entity.UserPoint;
import com.beauty.repository.AttendanceInfoRepository;
import com.beauty.repository.AttendanceRepository;
import com.beauty.repository.EventRepository;
import com.beauty.repository.StampRepository;
import com.beauty.repository.StampUserRepository;
import com.beauty.repository.UserPointRepository;
import com.beauty.repository.UserRepository;
import com.beauty.response.AttendanceResponse;
import com.beauty.response.result.ResultCode;
import com.beauty.security.auth.JwtAuthenticationToken;
import com.beauty.security.model.UserContext;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private AttendanceInfoRepository attendanceInfoRepository;
	
	@Autowired
	private StampRepository stampRepository;
	
	@Autowired
	private StampUserRepository stampUserRepository;
	
	@Autowired
	private UserPointRepository userPointRepository;
	
	@Autowired
	private CouponService couponService;

	
	@Override
	public Event getEvent(Long eid) {
		return eventRepository.findOne(eid);
	}
	
	@Override
	public Page<Event> getEventList(int page) {
		PageRequest pageRequest = new PageRequest(page, BeautyConstants.PAGE_SIZE_PRODUCT, new Sort(Direction.DESC, "sort")); //현재페이지, 조회할 페이지수, 정렬정보
		return eventRepository.findByStopEvent(0, pageRequest);
	}

	@Override
	public AttendanceResponse attendanceList(JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			Long nowMonth = DateUtil.getDateLong("yyyyMM");
			List<Attendance> attendanceList = attendanceRepository.findByUserAndAttendMonth(user, nowMonth);
			int useStamp = 0;
			List<StampUser> stUser = stampUserRepository.findByUserAndUseMonth(user, nowMonth);
			for(StampUser su:stUser) {
				useStamp += su.getStamp().getStamp();
			}
			int stamp = attendanceList.size() - useStamp;
			
			return AttendanceResponse.of("success", ResultCode.SUCCESS, attendanceList, stamp);
		}
		return AttendanceResponse.of("User Not Found", ResultCode.FAIL, null, 0);
	}

	@Override
	public AttendanceResponse attendance(JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			Long nowMonth = DateUtil.getDateLong("yyyyMM");
			Long nowDay = DateUtil.getDateLong("dd");
			int attCount = attendanceRepository.countByUserAndAttendMonthAndAttendDay(user, nowMonth, nowDay);
			if(attCount > 0) {
				return AttendanceResponse.of("이미 출석체크를 하였습니다.", ResultCode.FAIL, null, 0);
			} else {
				Attendance att = new Attendance();
				att.setUser(user);
				att.setAttendDay(nowDay);
				att.setAttendMonth(nowMonth);
				attendanceRepository.save(att);

				List<Attendance> attendanceList = attendanceRepository.findByUserAndAttendMonth(user, nowMonth);
				int useStamp = 0;
				List<StampUser> stUser = stampUserRepository.findByUserAndUseMonth(user, nowMonth);
				for(StampUser su:stUser) {
					useStamp += su.getStamp().getStamp();
				}
				int stamp = attendanceList.size() - useStamp;
				
				return AttendanceResponse.of("success", ResultCode.SUCCESS, attendanceList, stamp);
			}
		}
		return AttendanceResponse.of("User Not Found", ResultCode.FAIL, null, 0);
	}

	@Override
	public AttendanceInfo attendanceInfo(JwtAuthenticationToken token) {
		Long nowMonth = DateUtil.getDateLong("yyyyMM");
		return attendanceInfoRepository.findOne(nowMonth);
	}

	@Override
	public AttendanceResponse stamp(Long stId, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			Long nowMonth = DateUtil.getDateLong("yyyyMM");
			List<Attendance> attendanceList = attendanceRepository.findByUserAndAttendMonth(user, nowMonth);
			int useStamp = 0;
			List<StampUser> stUser = stampUserRepository.findByUserAndUseMonth(user, nowMonth);
			for(StampUser su:stUser) {
				useStamp += su.getStamp().getStamp();
			}
			int stamp = attendanceList.size() - useStamp;
			System.out.println(stamp);
			
			Stamp st = stampRepository.findOne(stId);
			
			System.out.println(st.getStamp());
			if(stamp >= st.getStamp()) {
				StampUser su = new StampUser();
				su.setStamp(st);
				su.setType(st.getType());
				su.setUseMonth(nowMonth);
				su.setUser(user);
				if(su.getStamp().getType() == 1) { // 쿠폰
					couponService.couponDown(su.getStamp().getStampKey(), token);
				} else if(su.getStamp().getType() == 2 ) { //포인트
					UserPoint userPoint = new UserPoint();
					userPoint.setNote("출석체크 스탬프 교환");
					userPoint.setPoint(Integer.parseInt(su.getStamp().getStampKey()));
					userPoint.setUser(user);
					userPointRepository.save(userPoint);
				} 
				stampUserRepository.save(su);
				stamp -= su.getStamp().getStamp();
				return AttendanceResponse.of("success", ResultCode.SUCCESS, attendanceList, stamp);
			} else {
				return AttendanceResponse.of("스탬프가 부족합니다.", ResultCode.FAIL, null, 0);
			}
		}
		return AttendanceResponse.of("User Not Found", ResultCode.FAIL, null, 0);
	}

}
