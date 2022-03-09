package com.beauty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.beauty.BeautyConstants;
import com.beauty.entity.User;
import com.beauty.kakao.KakaoClient;
import com.beauty.kakao.response.UserData;
import com.beauty.response.CommonResponse;
import com.beauty.response.DeviceInfoResponse;
import com.beauty.response.UserResponse;
import com.beauty.response.VersionResponse;
import com.beauty.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * End-point for retrieving logged-in user details.
 * 
 * @author vladimir.stankovic
 *
 * Aug 4, 2016
 */
@RestController
@RequestMapping(value="/api/user")
public class UserController {
	public final String TAG = "회원";

	@Autowired
	UserService userService;

	/**
	 * 회원가입
	 * @return
	 */
	@ApiOperation(value = "회원 가입", notes = "회원 가입을 한다", response = UserResponse.class, tags={TAG, })
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public @ResponseBody UserResponse join(
			@ApiParam(name="id", value="고유ID", required = false) @RequestParam(required=false) String id,
			@ApiParam(name="email", value="이메일", required = true) @RequestParam String email,
			@ApiParam(name="name", value="이름", required = true) @RequestParam String name,
			@ApiParam(name="password", value="비밀번호", required = true) @RequestParam String password,
			@ApiParam(name="gender", value="성별   F:여자, M:남자", required = true) @RequestParam String gender,
			@ApiParam(name="birthday", value="생년월일  yyyyMMdd", required = true) @RequestParam int birthday,
			@ApiParam(name="phone", value="번호  010xxxxxxxx", required = true) @RequestParam String phone,
			@ApiParam(name="joinType", value="가입유형 K:카카오톡 | F:페이스북 | N:네이버 | D:기타") @RequestParam(required=false) String joinType,
			@ApiParam(name="skin", value="0 : 건성,  1 : 중성,  2 : 지성,  3 : 복합성", required = true) @RequestParam int skin,
			@ApiParam(name="smsagree", value="SMS 수신동의  <br/> Y : 동의함 , N : 미동의", required = false) @RequestParam(name="smsagree", required=false) String smsagree,
			@ApiParam(name="emailagree", value="이메일 수신동의 <br/> Y : 동의함 , N : 미동의", required = false) @RequestParam(name="emailagree", required=false) String emailagree,
			@ApiParam(name="code", value="추천코드", required = false) @RequestParam(name="code", required=false) String code
			) {

		User user = new User();
		user.setUserId(phone);

		if(joinType == null) {
			user.setJoinType("D");
		} else {
			user.setJoinType(joinType);
		}

		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		user.setGender(gender);
		user.setBirthday(birthday);
		user.setPhone(phone);
		user.setSkinType(skin);
		if(smsagree != null) {
			user.setSmsAgree(smsagree);
		}

		if(emailagree != null) {
			user.setEmailAgree(emailagree);
		}
		return userService.saveUser(user, code, id);
	}


	/**
	 * 회원가입
	 * @return
	 */
	@ApiOperation(value = "회원 가입", notes = "회원 가입을 한다", response = UserResponse.class, tags={TAG, })
	@RequestMapping(value="/join_v2", method=RequestMethod.POST)
	public @ResponseBody UserResponse join_v2(
			@ApiParam(name="email", value="이메일(ID)", required = true) @RequestParam String email,
			@ApiParam(name="name", value="이름", required = true) @RequestParam String name,
			@ApiParam(name="password", value="비밀번호", required = true) @RequestParam String password,
			@ApiParam(name="birthday", value="생년월일  yyyyMMdd", required = true) @RequestParam int birthday,
			@ApiParam(name="phone", value="번호  010xxxxxxxx", required = true) @RequestParam String phone,
			@ApiParam(name="joinType", value="가입유형 K:카카오톡 | F:페이스북 | N:네이버 | D:일반") @RequestParam(required=false) String joinType,
			@ApiParam(name="skin", value="0 : 건성,  1 : 중성,  2 : 지성,  3 : 복합성", required = true) @RequestParam int skin,
			@ApiParam(name="smsagree", value="SMS 수신동의  <br/> Y : 동의함 , N : 미동의", required = false) @RequestParam(name="smsagree", required=false) String smsagree,
			@ApiParam(name="emailagree", value="이메일 수신동의 <br/> Y : 동의함 , N : 미동의", required = false) @RequestParam(name="emailagree", required=false) String emailagree,
			@ApiParam(name="gender", value="성별  A: 알수 없음 F:여자, M:남자", required = false) @RequestParam(name="gender", required=false) String gender,
			@ApiParam(name="code", value="추천코드", required = false) @RequestParam(name="code", required=false) String code,
			@ApiParam(name="sns_id", value="SNSID (SNS 로그인시 사용)", required = false) @RequestParam(required=false) String id
			) {

		User user = new User();
		user.setUserId(phone);

		if(joinType == null) {
			user.setJoinType("D");
		} else {
			user.setJoinType(joinType);
		}

		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		user.setGender(gender);
		user.setBirthday(birthday);
		user.setPhone(phone);
		user.setSkinType(skin);
		if(smsagree != null) {
			user.setSmsAgree(smsagree);
		}

		if(emailagree != null) {
			user.setEmailAgree(emailagree);
		}
		return userService.saveUserV2(user, code, id);
	}
	
	/**
	 * 아이디 찾기
	 * @return
	 */
    @ApiOperation(value = "아이디 찾기", notes = "아이디를 찾는다.", response = UserResponse.class, tags={TAG, })
    @RequestMapping(value="/find_id", method=RequestMethod.GET)
    public @ResponseBody UserResponse find_id(
    		@ApiParam(name="name", value="이름", required = true) @RequestParam String name,
    		@ApiParam(name="phone", value="폰번호", required = true) @RequestParam String phone) {
        return userService.findEmail(name, phone);
    }
	    
	/**
	 * 비밀번호 찾기
	 * @return
	 */
    @ApiOperation(value = "번호인증 비밀번호 찾기", notes = "비밀번호를 찾는다.", response = CommonResponse.class, tags={TAG, })
    @RequestMapping(value="/find_pw", method=RequestMethod.GET)
    public @ResponseBody CommonResponse find_pw(
    		@ApiParam(name="email", value="이메일", required = true) @RequestParam String email,
    		@ApiParam(name="phone", value="폰번호", required = true) @RequestParam String phone
    		) {
        return userService.findPassword(email, phone);
    }

	/**
	 * 비밀번호 찾기
	 * @return
	 */
    @ApiOperation(value = "이메일 비밀번호 찾기", notes = "비밀번호를 찾는다.", response = CommonResponse.class, tags={TAG, })
    @RequestMapping(value="/find_email_pw", method=RequestMethod.GET)
    public @ResponseBody CommonResponse find_email_pw(
    		@ApiParam(name="email", value="이메일", required = true) @RequestParam String email,
    		@ApiParam(name="name", value="이름", required = true) @RequestParam String name
    		) {
        return userService.findEmailPassword(email, name);
    }

    
	/**
	 * SMS 인증번호 발송
	 * @return
	 */
	@ApiOperation(value = "SMS 인증", notes = "SMS 인증번호 발송", response = CommonResponse.class, tags={TAG, })
	@RequestMapping(value="/send_sms", method=RequestMethod.GET)
	public @ResponseBody CommonResponse sendSms(
			@ApiParam(name="phone", value="폰번호", required = true) @RequestParam String phone
			) {
		System.out.println(phone);
		return userService.sendSms(phone);
	}

	/**
	 * SMS 인증번호 확인
	 * @return
	 */
	@ApiOperation(value = "SMS 확인", notes = "SMS 인증번호 확인", response = CommonResponse.class, tags={TAG, })
	@RequestMapping(value="/sms_cert", method=RequestMethod.GET)
	public @ResponseBody CommonResponse smsCert(
			@ApiParam(name="phone", value="폰번호", required = true) @RequestParam String phone,
			@ApiParam(name="cert", value="인증번호", required = true) @RequestParam String cert
			) {
		return userService.smsCert(phone, cert);
	}
	
	/**
	 * 디바이스 정보 등록
	 * @return
	 */
	@ApiOperation(value = "디바이스 정보", notes = "디바이스 정보를 저장한다.", response = DeviceInfoResponse.class, tags={TAG, })
	@RequestMapping(value="/device_info", method=RequestMethod.POST)
	public @ResponseBody DeviceInfoResponse insertDevice(
			@ApiParam(name="deviceId", value="디바이스고유ID", required = true) @RequestParam String deviceId,
			@ApiParam(name="os", value="A: 안드로이드 I : IOS", required = true) @RequestParam String os,
			@ApiParam(name="deviceToken", value="디바이스TOKEN", required = true) @RequestParam String deviceToken,
			@ApiParam(name="osVersion", value="OS 버전", required = true) @RequestParam String osVersion,
			@ApiParam(name="modelName", value="모델명", required = true) @RequestParam String modelName,
			@ApiParam(name="appVersion", value="앱버전", required = true) @RequestParam String appVersion) {
		return userService.saveDeviceInfo(deviceId, os, deviceToken, osVersion, modelName, appVersion, null);
	}

	@ApiOperation(value = "앱 버전 정보 확인", notes = "앱 버전 정보를 확인한다.", response = VersionResponse.class, tags={TAG, })
	@RequestMapping(value = "/version/{os_type}", method = RequestMethod.GET, produces = {"application/json", "application/json"})
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody VersionResponse checkVersion(@PathVariable("os_type") String os_type, @RequestParam("versionName") String versionName) {

		return userService.selectVersion(os_type, versionName);
	}
	
	@RequestMapping(value = "/kakao", method = RequestMethod.GET)
	public @ResponseBody UserData token(@RequestParam("access_token") String access_token) {
		KakaoClient client = new KakaoClient(BeautyConstants.KAKO_API_KEY);
		return client.getUserData(access_token);
	}

	
}
