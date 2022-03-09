package com.beauty.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.beauty.common.Coolsms;
import com.beauty.common.DateUtil;
import com.beauty.common.PasswordEncoding;
import com.beauty.common.RandomStringBuilder;
import com.beauty.entity.DeviceInfo;
import com.beauty.entity.Recommend;
import com.beauty.entity.Role;
import com.beauty.entity.SmsCert;
import com.beauty.entity.SmsResult;
import com.beauty.entity.User;
import com.beauty.entity.UserPoint;
import com.beauty.entity.UserRole;
import com.beauty.entity.UserSns;
import com.beauty.entity.Version;
import com.beauty.mail.MailService;
import com.beauty.repository.DeviceInfoRepository;
import com.beauty.repository.RecommendRepository;
import com.beauty.repository.SmsCertRepository;
import com.beauty.repository.SmsResultRepository;
import com.beauty.repository.UserPointRepository;
import com.beauty.repository.UserRepository;
import com.beauty.repository.UserRoleRepository;
import com.beauty.repository.UserSnsRepository;
import com.beauty.repository.VersionRepository;
import com.beauty.response.CommonResponse;
import com.beauty.response.DeviceInfoResponse;
import com.beauty.response.UserResponse;
import com.beauty.response.VersionResponse;
import com.beauty.response.result.ResultCode;
import com.beauty.security.auth.JwtAuthenticationToken;
import com.beauty.security.model.UserContext;

import lombok.Getter;
import lombok.Setter;

@Service
public class UserServiceImpl implements UserService {

	@Value("${coolsms.api}")
	@Getter @Setter
	String coolSmsApi;

	@Value("${coolsms.api.secret}")
	@Getter @Setter
	String coolSmsSecret;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private DeviceInfoRepository deviceInfoRepository;

	@Autowired
	private VersionRepository versionRepository;

	@Autowired
	private RecommendRepository recommendRepository;

	@Autowired
	private UserPointRepository userPointRepository;

	@Autowired
	private CouponService couponService;

	@Autowired
	private SmsCertRepository smsCertRepository;

	@Autowired
	private SmsResultRepository smsResultRepository;

	@Autowired
	private UserSnsRepository userSnsRepository;

	@Autowired
	private MailService mailService;
	
	@Override
	public Optional<UserSns> getById(String id, String joinType) {
		return userSnsRepository.findBySnsIdAndJoinType(id, joinType);
	}

	@Override
	public User getById(String id) {
		return userRepository.findByUserIdOrEmail(id);
	}

	@Override
	public UserResponse findEmail(String name, String phone) {
		User user = userRepository.findByNameAndUserId(name, phone);

		if(user == null) {
			return UserResponse.of("User Not Found", ResultCode.FAIL, null);
		} else {
			return UserResponse.of("success", ResultCode.SUCCESS, user);
		}
	}

	@Override
	public CommonResponse findPassword(String email, String phone) {
		User user = userRepository.findByEmailAndUserId(email, phone);

		if(user == null) {
			return CommonResponse.of("User Not Found", ResultCode.FAIL);
		} else {
			String repasswd =  new RandomStringBuilder().
					putLimitedChar(RandomStringBuilder.ALPHABET).
					setLength(6).build();
			//
			//
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			PasswordEncoding passwordEncoding = new PasswordEncoding(passwordEncoder);
			user.setPassword(passwordEncoding.encode(repasswd));


			String text = "[발그레]\n임시비밀번호가 발급되었습니다.\n[ " + repasswd + " ]\n로그인 후 비밀번호 변경바랍니다.";
			HashMap<String, String> set = new HashMap<String, String>();		
			set.put("to", phone); // 수신번호
			set.put("from", "028557976"); // 발신번호
			set.put("text", text); // 문자내용
			set.put("type", "sms"); // 문자 타입

			SmsResult smsResult = sendSms(set);

			smsResult.setMessage(text);
			smsResult.setSendDate(new Date());
			smsResult.setSendNumber(phone);
			smsResultRepository.save(smsResult);

			set.clear();
			set = null;

			if(smsResult.getResultCode().equals("00")) {
				userRepository.save(user);
				return CommonResponse.of(smsResult.getResultMessage(), ResultCode.SUCCESS);				
			} else {
				return CommonResponse.of(smsResult.getResultMessage(), ResultCode.FAIL);
			}
		}
	}

	@Override
	public CommonResponse findEmailPassword(String email, String name) {
		User user = userRepository.findByEmailAndName(email, name);

		if(user == null) {
			return CommonResponse.of("User Not Found", ResultCode.FAIL);
		} else {
			String repasswd =  new RandomStringBuilder().
					putLimitedChar(RandomStringBuilder.ALPHABET).
					setLength(6).build();


			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			PasswordEncoding passwordEncoding = new PasswordEncoding(passwordEncoder);
			user.setPassword(passwordEncoding.encode(repasswd));

			StringBuffer sb = new StringBuffer();
			sb.append("임시비밀번호가 발급되었습니다.<br/><br/>");
			sb.append("<h3><b>" + repasswd + "</b></h3><br/>로그인 후 비밀번호 재설정 바랍니다.");
			boolean send = mailService.sendMail(email, "발그레 임시 비밀번호 발급", sb.toString());
			if(send) {
				userRepository.save(user);
				return CommonResponse.of("임시비밀번호가 해당 이메일로 전송 되었습니다.", ResultCode.SUCCESS);
			} else {
				return CommonResponse.of("이메일 전송에 실패하였습니다.", ResultCode.FAIL);
			}
		}
	}

	@Override
	public UserResponse saveUser(User user, String recommend_user, String id) {
		if(!user.getGender().equals("F") && !user.getGender().equals("M")) {
			return UserResponse.of("Gender F or M", ResultCode.FAIL, user);
		}

		if(user.getEmail() == null || user.getEmail().equals("")) {
			return UserResponse.of("이메일을 입력해 주세요.", ResultCode.FAIL, null);		
		}

		if(user.getPhone() == null || user.getPhone().equals("")) {
			return UserResponse.of("전화번호를 입력해 주세요.", ResultCode.FAIL, null);		
		}

		SmsCert smsCert = smsCertRepository.findOne(user.getPhone());
		if(smsCert == null || smsCert.getCertYn().equals("N")) {
			return UserResponse.of("인증받지 않은 번호입니다.", ResultCode.FAIL, null);		
		}
		//		


		if(userRepository.findOne(user.getUserId()) == null) {
			Optional<UserSns> uSns =userSnsRepository.findBySnsIdAndJoinType(id, user.getJoinType()); 
			if(uSns.isPresent()) {
				return UserResponse.of("이미 가입되어 있는 계정입니다.", ResultCode.FAIL, null);
			}
			try{
				String myCode = "";
				User code = null;
				do {
					myCode =  new RandomStringBuilder().
							putLimitedChar(RandomStringBuilder.ALPHABET_UPPER_CASE).
							setLength(6).build();
					code = userRepository.findByMyCode(myCode);
				} while (code != null);

				PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				PasswordEncoding passwordEncoding = new PasswordEncoding(passwordEncoder);
				user.setPassword(passwordEncoding.encode(user.getPassword()));
				user.setMyCode(myCode);
				user = userRepository.save(user);

				UserSns userSns = new UserSns();
				userSns.setJoinType(user.getJoinType());
				userSns.setUser(user);
				userSns.setSnsId(id);
				userSns.setPassword(passwordEncoding.encode(user.getPassword()));
				userSnsRepository.save(userSns);

				UserRole.Id userRoleId = new UserRole.Id(user.getUserId(), Role.MEMBER);

				UserRole userRole = new UserRole();
				userRole.setRole(Role.MEMBER);
				userRole.setId(userRoleId);
				userRoleRepository.save(userRole);
			} catch (Exception e) {
				return UserResponse.of(e.getMessage(), ResultCode.FAIL, user);
			}

			//추천인 저장
			if(recommend_user != null && !recommend_user.equals("")) {
				User reUser =  userRepository.findByMyCode(recommend_user.toUpperCase());
				if(reUser != null) {
					Recommend recommend = new Recommend();
					recommend.setUser(user);
					recommend.setReCode(recommend_user);

					recommendRepository.save(recommend);
					// 추천인입력 : 5000
					UserPoint up = new UserPoint();
					up.setNote(recommend_user + " 추천인 입력");
					up.setPoint(5000);
					up.setUser(user);
					userPointRepository.save(up);

					// 추천받은사람 : 1000
					if(recommendRepository.countByReCode(recommend_user) <= 50) {
						up =  new UserPoint();
						up.setNote(user.getName() + "님이 추천인으로 등록");
						up.setPoint(1000);
						up.setUser(reUser);
						userPointRepository.save(up);
					}
				}
			}


			UserPoint up = new UserPoint();
			up.setNote("회원가입 포인트");
			up.setPoint(1000);
			up.setUser(user);
			userPointRepository.save(up);

			couponService.autoCoupon(user, 1);
			return UserResponse.of("success", ResultCode.SUCCESS, user);
		} else {
			return UserResponse.of("이미 가입되어 있는 번호입니다.", ResultCode.FAIL, null);
		}

	}

	@Override
	public UserResponse saveUserV2(User user, String recommend_user, String id) {

		if(user.getEmail() == null || user.getEmail().equals("")) {
			return UserResponse.of("이메일을 입력해 주세요.", ResultCode.FAIL, null);		
		}

		if(user.getPhone() == null || user.getPhone().equals("")) {
			return UserResponse.of("전화번호를 입력해 주세요.", ResultCode.FAIL, null);		
		}

		SmsCert smsCert = smsCertRepository.findOne(user.getPhone());
		if(smsCert == null || smsCert.getCertYn().equals("N")) {
			return UserResponse.of("인증받지 않은 번호입니다.", ResultCode.FAIL, null);		
		}
		//		

		if(userRepository.findOne(user.getUserId()) != null) {
			return UserResponse.of("이미 가입되어 있는 계정입니다.", ResultCode.FAIL, null);
		} else if(userRepository.findOne(user.getPhone()) != null) {
			return UserResponse.of("이미 가입되어 있는 번호입니다.", ResultCode.FAIL, null);
		}

		if(id != null && !id.equals("")) {
			Optional<UserSns> uSns =userSnsRepository.findBySnsIdAndJoinType(id, user.getJoinType()); 
			if(uSns.isPresent()) {
				return UserResponse.of("이미 가입되어 있는 계정입니다.", ResultCode.FAIL, null);
			}
		}
		
		try{
			String myCode = "";
			User code = null;
			do {
				myCode =  new RandomStringBuilder().
						putLimitedChar(RandomStringBuilder.ALPHABET_UPPER_CASE).
						setLength(6).build();
				code = userRepository.findByMyCode(myCode);
			} while (code != null);

			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			PasswordEncoding passwordEncoding = new PasswordEncoding(passwordEncoder);
			user.setPassword(passwordEncoding.encode(user.getPassword()));
			user.setMyCode(myCode);
			user = userRepository.save(user);
			if(id != null && !id.equals("")) {
				UserSns userSns = new UserSns();
				userSns.setJoinType(user.getJoinType());
				userSns.setUser(user);
				userSns.setSnsId(id);
				userSns.setPassword(user.getPassword());
				userSnsRepository.save(userSns);
			}

			UserRole.Id userRoleId = new UserRole.Id(user.getUserId(), Role.MEMBER);

			UserRole userRole = new UserRole();
			userRole.setRole(Role.MEMBER);
			userRole.setId(userRoleId);
			userRoleRepository.save(userRole);
		} catch (Exception e) {
			return UserResponse.of("가입에 실패하였습니다.", ResultCode.FAIL, user);
		}

		//추천인 저장
		if(recommend_user != null && !recommend_user.equals("")) {
			User reUser =  userRepository.findByMyCode(recommend_user.toUpperCase());
			if(reUser != null) {
				Recommend recommend = new Recommend();
				recommend.setUser(user);
				recommend.setReCode(recommend_user);

				recommendRepository.save(recommend);
				// 추천인입력 : 5000
				UserPoint up = new UserPoint();
				up.setNote(recommend_user + " 추천인 입력");
				up.setPoint(5000);
				up.setUser(user);
				userPointRepository.save(up);

				// 추천받은사람 : 1000
				if(recommendRepository.countByReCode(recommend_user) <= 50) {
					up =  new UserPoint();
					up.setNote(user.getName() + "님이 추천인으로 등록");
					up.setPoint(1000);
					up.setUser(reUser);
					userPointRepository.save(up);
				}
			}
		}


		UserPoint up = new UserPoint();
		up.setNote("회원가입 포인트");
		up.setPoint(1000);
		up.setUser(user);
		userPointRepository.save(up);

		couponService.autoCoupon(user, 1);
		return UserResponse.of("success", ResultCode.SUCCESS, user);
	}


	@Override
	public UserResponse updateUser(String email,  int skin, String smsagree, String emailagree, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			if(email == null || email.equals("")) {
				return UserResponse.of("이메일을 입력해 주세요.", ResultCode.FAIL, null);		
			}

			user.setEmail(email);
			user.setSkinType(skin);
			user.setSmsAgree(smsagree);
			user.setEmailAgree(emailagree);
			userRepository.save(user);
			return UserResponse.of("success", ResultCode.SUCCESS, user);				
		}
		return UserResponse.of("회원이 존재하지 않습니다.", ResultCode.FAIL, null);
	}
	
	@Override
	public UserResponse updateUserV2(String password,  int skin, String smsagree, String emailagree, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			System.out.println(user.getEmail());
			if(password != null && !password.equals("")) {
				PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				PasswordEncoding passwordEncoding = new PasswordEncoding(passwordEncoder);
				user.setPassword(passwordEncoding.encode(password));
			}
			
			user.setSkinType(skin);
			user.setSmsAgree(smsagree);
			user.setEmailAgree(emailagree);
			userRepository.save(user);
			return UserResponse.of("success", ResultCode.SUCCESS, user);				
		}
		return UserResponse.of("회원이 존재하지 않습니다.", ResultCode.FAIL, null);
	}
	
	@Override
	public UserResponse getUser(JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
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
			
			List<UserSns> snsList = userSnsRepository.findByUser(user);
			for(UserSns sns:snsList) {
				if(sns.getJoinType().equals("K")) {
					user.setKakao(true);
				}
				
				if(sns.getJoinType().equals("F")) {
					user.setFacebook(true);
				}
				
				if(sns.getJoinType().equals("N")) {
					user.setNaver(true);
				}
			}
			return UserResponse.of("success", ResultCode.SUCCESS, user);				
		}
		return UserResponse.of("회원이 존재하지 않습니다.", ResultCode.FAIL, null);
	}

	@Override
	public UserResponse memberLeave(String reason, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			user.setDelDate(new Date());
			user.setDelYn("Y");
			user.setReason(reason);
			userRepository.save(user);
			return UserResponse.of("success", ResultCode.SUCCESS, user);				
		}
		return UserResponse.of("회원이 존재하지 않습니다.", ResultCode.FAIL, null);
	}

	@Override
	public UserResponse leaveCancel(String reason, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			user.setDelDate(new Date());
			user.setDelYn("N");
			user.setReason(reason);
			userRepository.save(user);
			return UserResponse.of("success", ResultCode.SUCCESS, user);				
		}
		return UserResponse.of("회원이 존재하지 않습니다.", ResultCode.FAIL, null);
	}	

	@Override
	public DeviceInfoResponse saveDeviceInfo(String deviceId, String os, String deviceToken, String osVersion, String modelName,
			String appVersion, JwtAuthenticationToken token) {
		DeviceInfo deviceInfo = deviceInfoRepository.findOne(deviceId);
		if(deviceInfo == null) {
			deviceInfo = new DeviceInfo();
			deviceInfo.setDeviceId(deviceId);
		}
		if(os.equals("A")) {
			deviceInfo.setOs("Android");
		} else if(os.equals("I")) {
			deviceInfo.setOs("IOS");
		} else {
			deviceInfo.setOs("Others");
		}
		deviceInfo.setToken(deviceToken);
		deviceInfo.setOsVersion(osVersion);
		deviceInfo.setModelName(modelName);
		deviceInfo.setAppVersion(appVersion);
		if(token != null) {
			UserContext userContext = (UserContext)token.getPrincipal();
			User user = userRepository.findOne(userContext.getUserId());

			if(user != null) {
				deviceInfo.setUser(user);				
			}
		}

		deviceInfoRepository.save(deviceInfo);

		return DeviceInfoResponse.of("success", ResultCode.SUCCESS, deviceInfo);
	}

	@Override
	public VersionResponse selectVersion(String os_type, String version_name) {
		Version version = versionRepository.findByOsType(os_type);
		if(version != null) {
			String oldVersion = version_name.replaceAll("[.]", "");
			String newVersion = version.getVersionName().replaceAll("[.]", "");
			if(Integer.parseInt(newVersion) > Integer.parseInt(oldVersion)) {
				return VersionResponse.of("업데이트가 필요합니다.", ResultCode.FAIL, null);
			} else {
				return VersionResponse.of("success", ResultCode.SUCCESS, version);
			}
		} else {
			return VersionResponse.of("success", ResultCode.SUCCESS, version);
		}
	}

	@Override
	public CommonResponse sendSms(String phone) {
		User user = userRepository.findOne(phone);
		if(user != null) {
			return CommonResponse.of("이미 가입된 전화번호 입니다.", ResultCode.FAIL);
		}
		String certNum =  new RandomStringBuilder().
				putLimitedChar(RandomStringBuilder.NUMBER).
				setLength(6).build();
		StringBuffer userSms = new StringBuffer();
		userSms.append("[인증번호]\n\n");
		userSms.append(certNum);

		HashMap<String, String> set = new HashMap<String, String>();		
		set.put("to", phone); // 수신번호
		set.put("from", "028557976"); // 발신번호
		set.put("text", userSms.toString()); // 문자내용
		set.put("type", "sms"); // 문자 타입

		SmsResult smsResult = sendSms(set);

		smsResult.setMessage(userSms.toString());
		smsResult.setSendDate(new Date());
		smsResult.setSendNumber(phone);
		smsResultRepository.save(smsResult);

		set.clear();
		set = null;

		if(smsResult.getResultCode().equals("00")) {
			SmsCert sc = new SmsCert();
			sc.setPhone(phone);
			sc.setCertNumber(certNum);
			sc.setCertYn("N");
			try {
				sc.setEffectiveTime(DateUtil.stringToDate(DateUtil.getAfterMinute(3),"yyyy-MM-dd HH:mm:ss"));
			} catch (ParseException e) {}

			smsCertRepository.save(sc);

			return CommonResponse.of(smsResult.getResultMessage(), ResultCode.SUCCESS);				
		} else {
			return CommonResponse.of("인증번호 전송에 실패 하였습니다.", ResultCode.FAIL);
		}

	}

	@Override
	public CommonResponse smsCert(String phone, String cert) {
		if(StringUtils.isBlank(phone) || StringUtils.isBlank(cert)) {
			return CommonResponse.of("인증 실패", ResultCode.FAIL);
		}

		SmsCert smsCert = smsCertRepository.findByPhoneAndCertNumber(phone, cert);
		if(smsCert == null) {
			return CommonResponse.of("인증 실패", ResultCode.FAIL);
		}

		smsCert.setCertYn("Y");
		smsCertRepository.save(smsCert);

		return CommonResponse.of("success", ResultCode.SUCCESS);
	}

	public SmsResult sendSms(HashMap<String, String> set) {
		/*
		 * 서버에서 받은 API_KEY, API_SECRET를 입력해주세요.
		 */
		Coolsms coolsms = new Coolsms(coolSmsApi, coolSmsSecret);
		JSONObject result = coolsms.send(set); // 보내기&전송결과받기

		SmsResult smsResult = new SmsResult();
		if ((boolean) result.get("status")) {
			// 메시지 보내기 성공 및 전송결과 출력
			/*System.out.println("성공");            
			System.out.println(result.get("group_id")); // 그룹아이디
			System.out.println(result.get("result_code")); // 결과코드
			System.out.println(result.get("result_message"));  // 결과 메시지
			System.out.println(result.get("success_count")); // 성공 수
			System.out.println(result.get("error_count"));  // 여러개 보낼시 오류난 메시지 수
			 */
			smsResult.setGroupId(result.get("group_id").toString());
			smsResult.setResultCode(result.get("result_code").toString());
			smsResult.setResultMessage(result.get("result_message").toString());

		} else {
			// 메시지 보내기 실패
			/*
			System.out.println("실패");
			System.out.println(result.get("code")); // REST API 에러코드
			System.out.println(result.get("message")); // 에러메시지
			 */
			smsResult.setGroupId("");
			smsResult.setResultCode(result.get("code").toString());
			smsResult.setResultMessage(result.get("message").toString());
		}        
		return smsResult;
	}

	@Override
	public CommonResponse updateProfile(String path, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			user.setProfile(path);
			userRepository.save(user);
			return CommonResponse.of("success", ResultCode.SUCCESS);
		}
		return CommonResponse.of("회원이 존재하지 않습니다.", ResultCode.FAIL);
	}

	@Override
	public CommonResponse snsConnect(String id, String type, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());
		if(user != null) {
			Optional<UserSns> sns = userSnsRepository.findBySnsIdAndJoinTypeAndUser(id, type, user);
			if(!sns.isPresent()) {
				UserSns userSns = new UserSns();
				userSns.setJoinType(type);
				userSns.setSnsId(id);
				userSns.setUser(user);
				PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				PasswordEncoding passwordEncoding = new PasswordEncoding(passwordEncoder);
				userSns.setPassword(passwordEncoding.encode(type));
				
				userSnsRepository.save(userSns);
				return CommonResponse.of("success", ResultCode.SUCCESS);
			} else {
				return CommonResponse.of("success", ResultCode.SUCCESS);
			}
		} else {
			return CommonResponse.of("회원이 존재하지 않습니다.", ResultCode.FAIL);
		}
	}
	@Override
	public CommonResponse snsDisConnect(String type, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());
		System.out.println(user.getUserId());
		System.out.println(type);
		if(user != null) {
			UserSns sns = userSnsRepository.findByJoinTypeAndUser(type, user);
			if(sns == null) {
				return CommonResponse.of("success", ResultCode.SUCCESS);
			} else {
				System.out.println(sns.getUser().getUserId());
				userSnsRepository.delete(sns);
				return CommonResponse.of("success", ResultCode.SUCCESS);
			}
		} else {
			return CommonResponse.of("회원이 존재하지 않습니다.", ResultCode.FAIL);
		}
	}

}
