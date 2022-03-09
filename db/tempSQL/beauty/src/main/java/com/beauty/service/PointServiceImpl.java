package com.beauty.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.beauty.BeautyConstants;
import com.beauty.entity.User;
import com.beauty.entity.UserPoint;
import com.beauty.repository.UserPointRepository;
import com.beauty.repository.UserRepository;
import com.beauty.response.PointResponse;
import com.beauty.response.result.ResultCode;
import com.beauty.security.auth.JwtAuthenticationToken;
import com.beauty.security.model.UserContext;

import lombok.Getter;
import lombok.Setter;

@Service
public class PointServiceImpl implements PointService {
	@Value("${ftp.host}")
	@Getter @Setter
	String ftpHost;

	@Value("${ftp.username}")
	@Getter @Setter
	String ftpUsername;

	@Value("${ftp.userpassword}")
	@Getter @Setter
	String ftpPassword;

	@Autowired
	private UserPointRepository userPointRepository;

	@Autowired
	private UserRepository userRepository;


	@Override
	public PointResponse pointList(int page, JwtAuthenticationToken token) {
		Long totalPoint = 0L;
		Long usePoint = 0L;
		Long myPoint = 0L;

		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {

			List<UserPoint> userPoint = userPointRepository.findByUser(user);
			for(UserPoint point:userPoint) {
				myPoint += point.getPoint();
				if(point.getPoint() > 0) {
					totalPoint += point.getPoint();
				} else {
					usePoint += point.getPoint();
				}
			}

			// Page 사용 코드
			PageRequest pageRequest = new PageRequest(page, BeautyConstants.PAGE_SIZE_DEFAULT, new Sort(Direction.DESC, "regDate")); //현재페이지, 조회할 페이지수, 정렬정보
			Page<UserPoint> result = userPointRepository.findByUser(user, pageRequest);

			return PointResponse.of("success", ResultCode.SUCCESS, myPoint, totalPoint, usePoint, result);
		} else {
			return PointResponse.of("회원이 존재하지 않습니다.", ResultCode.FAIL, null, null, null, null);
		}
	}




}
