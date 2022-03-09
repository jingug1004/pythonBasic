package com.beauty.admin.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.beauty.common.PasswordEncoding;
import com.beauty.entity.DataTables;
import com.beauty.entity.DeviceInfo;
import com.beauty.entity.Role;
import com.beauty.entity.User;
import com.beauty.entity.UserRole;
import com.beauty.repository.DeviceInfoRepository;
import com.beauty.repository.UserRepository;
import com.beauty.repository.UserRoleRepository;
import com.beauty.repository.specification.UserSpecification;
import com.beauty.response.DataTablesResult;

@Service
public class AdminUserServiceImpl implements AdminUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private DeviceInfoRepository deviceInfoRepository;

	@Override
	public User getUser(String userId) {
		User user = userRepository.findOne(userId);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date;
		try {
			date = format.parse(""+user.getBirthday());
			Calendar birth = new GregorianCalendar();
			Calendar today = new GregorianCalendar();

			birth.setTime(date);
			today.setTime(new Date());

			int factor = 0;
			if (today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
				factor = -1;
			}
			user.setAge(today.get(Calendar.YEAR) - birth.get(Calendar.YEAR) + factor);
		} catch (ParseException e) {
			user.setAge(0);
		}

		return user;
	}


	@Override
	public DataTablesResult list(DataTables input, String type, String role) {
		Specifications<User> specs = Specifications.where(UserSpecification.like("email", input.getSearch().get("value")));
		specs = specs.or(UserSpecification.like("name", input.getSearch().get("value")));

		PageRequest pageRequest = new PageRequest(input.getStart(), input.getLength(), input.getSort("regDate")); //현재페이지, 조회할 페이지수, 정렬정보

		Role roleType = null;
		Page<User> userList = null;
		if(type.equals("u")) {
			if(role.equals("1")) {	//관리자
				roleType = Role.ADMIN;
			} else if(role.equals("2")){ // 업체
				roleType = Role.ADVERTISER;
			} else if(role.equals("3")){ // VIP
				roleType = Role.MEMBER;
			} else {
				roleType = Role.MEMBER;
			}
			specs = specs.and(UserSpecification.equal("delYn", "N"));
			specs = specs.and(UserSpecification.roleEqual(roleType));
		} else {
			specs = specs.and(UserSpecification.equal("delYn", "Y"));
		}

		userList = userRepository.findAll(specs, pageRequest);
		DataTablesResult result = new DataTablesResult();
		result.setDraw(input.getDraw());
		result.setRecordsTotal(userList.getContent().size());
		result.setRecordsFiltered(userList.getTotalElements());
		result.setData(userList.getContent());

		return result;
	}

	@Override
	public String deleteUser(String id) {
		//User user = userRepository.findOne(id);
		// 1:1 문의 (QNA)
		// 계좌정보(UserAccountNum)
		// 포인트정보(UserPoint)
		// 추천인 정보(UserRecommend)
		// 권한정보 (UserRole)
		// 유저정보 (User)
		// 참여정보 (Review)
		return null;
	}

	@Override
	public String updateAuth(String id, String auth) {
		try {
			User user = userRepository.findOne(id);
			userRoleRepository.delete(user.getRoles());


			// ADMIN, ADVERTISER, PREMIUM_MEMBER, MEMBER
			Role role = null;
			if(auth.equals("ADMIN")) {
				role = Role.ADMIN;
			} else if(auth.equals("ADVERTISER")) {
				role = Role.ADVERTISER;
			}  else {
				role = Role.MEMBER;
			}

			UserRole.Id userRoleId = new UserRole.Id(user.getUserId(), role);

			UserRole userRole = new UserRole();
			userRole.setRole(role);
			userRole.setId(userRoleId);
			userRoleRepository.save(userRole);
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
		return "success";
	}


	@Override
	public String updateUser(String userId, String type) {
		try {
			User user = userRepository.findOne(userId);
			if(type.equals("leave_cancel")) {	// 탈퇴 취소
				user.setDelYn("N");
				user.setDelDate(null);
				user.setReason("");
			}

			userRepository.save(user);

		} catch (Exception e) {
			return "failed";
		}
		return "success";
	}


	@Override
	public void regUser(String username, String email, String name, String phone, String password) {
		User user = new User();
		user.setUserId(username);
		user.setEmail(email);
		user.setName(name);
		user.setPhone(phone);
		user.setMyCode("");
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		PasswordEncoding passwordEncoding = new PasswordEncoding(passwordEncoder);
		user.setPassword(passwordEncoding.encode(password));

		if(userRepository.findOne(username) == null) {
			User newUser = userRepository.save(user);

			UserRole.Id userRoleId = new UserRole.Id(newUser.getUserId(), Role.ADVERTISER);

			UserRole userRole = new UserRole();
			userRole.setRole(Role.ADVERTISER);
			userRole.setId(userRoleId);
			userRoleRepository.save(userRole);
		}
	}


	@Override
	public List<DeviceInfo> getDevice(String userId) {
		User user = userRepository.findOne(userId);
		return deviceInfoRepository.findByUser(user);
	}


	@Override
	public List<User> getRoleUser(Role role) {
		return userRepository.findByRole(role);
	}


	@Override
	public String leaveCancel(String user_id) {
		User user = userRepository.findOne(user_id);

		if(user != null) {
			user.setDelDate(new Date());
			user.setDelYn("N");
			user.setReason("");
			userRepository.save(user);
			return "success";				
		}
		return "failed";
	}


}
