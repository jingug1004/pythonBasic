package com.beauty.admin.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.beauty.entity.User;
import com.beauty.entity.UserPoint;
import com.beauty.repository.UserPointRepository;
import com.beauty.repository.UserRepository;

import lombok.Getter;
import lombok.Setter;

@Service
public class AdminPointServiceImpl implements AdminPointService {

	@Value("${security.key}")
	@Getter @Setter
	String security_key;
	
	@Value("${server.image.url}")
	@Getter @Setter
	String image_url;

	@Autowired
	private UserPointRepository userPointRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public HashMap<String, Object> findByUserPoint(String user_id) {
		Long totalPoint = 0L;
		Long usePoint = 0L;
		Long myPoint = 0L;

		User user = userRepository.findOne(user_id);

		List<UserPoint> userPoint = userPointRepository.findByUser(user);
		for(UserPoint point:userPoint) {
			myPoint += point.getPoint();
			if(point.getPoint() > 0) {
				totalPoint += point.getPoint();
			} else {
				usePoint += point.getPoint();
			}
		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		map.put("userPoint", userPoint);
		map.put("totalPoint", totalPoint);
		map.put("usePoint", usePoint);
		map.put("myPoint", myPoint);
		
		return map;
	}
	

	
}
