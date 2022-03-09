package com.beauty.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beauty.response.CommonResponse;
import com.beauty.response.DeviceInfoResponse;
import com.beauty.response.PointResponse;
import com.beauty.response.QnaListResponse;
import com.beauty.response.UserResponse;
import com.beauty.security.auth.JwtAuthenticationToken;
import com.beauty.service.PointService;
import com.beauty.service.QnaService;
import com.beauty.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value="/api/v1/mypage")
public class MyPageV1Controller {
	public final String TAG = "마이페이지 - 로그인 필요";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QnaService qnaService;
	
	@Autowired
	private PointService pointService;
	
	/**
     * 회원정보
     * @return
     */
    @ApiOperation(value = "회원정보", notes = "회원정보를 가져온다", response = UserResponse.class, tags={TAG, })
    @RequestMapping(value="/user_info", method=RequestMethod.POST)
    public @ResponseBody UserResponse getUserInfo(JwtAuthenticationToken token) {
        return userService.getUser(token);
    }    
	
	
    /**
     * 회원정보 수정
     * @return
     */
    @ApiOperation(value = "회원정보 수정", notes = "회원 정보를 수정한다", response = UserResponse.class, tags={TAG, })
    @RequestMapping(value="/join", method=RequestMethod.POST)
    public @ResponseBody UserResponse join(
    		@ApiParam(name="email", value="이메일", required = true) @RequestParam String email,
    		@ApiParam(name="skin", value="0 : 건성,  1 : 중성,  2 : 지성,  3 : 복합성", required = true) @RequestParam int skin,
    		@ApiParam(name="smsagree", value="SMS 수신동의  <br/> Y : 동의함 , N : 미동의", required = false) @RequestParam(name="smsagree", required=false) String smsagree,
    		@ApiParam(name="emailagree", value="이메일 수신동의 <br/> Y : 동의함 , N : 미동의", required = false) @RequestParam(name="emailagree", required=false) String emailagree,
    		JwtAuthenticationToken token
    		) {
    	
        return userService.updateUser(email, skin, smsagree, emailagree, token);
    }
    
	
    /**
     * 회원정보 수정
     * @return
     */
    @ApiOperation(value = "회원정보 수정", notes = "회원 정보를 수정한다", response = UserResponse.class, tags={TAG, })
    @RequestMapping(value="/join_v2", method=RequestMethod.POST)
    public @ResponseBody UserResponse joinV2(
    		@ApiParam(name="password", value="비밀번호", required = false) @RequestParam(name="password", required=false) String password,
    		@ApiParam(name="skin", value="0 : 건성,  1 : 중성,  2 : 지성,  3 : 복합성", required = true) @RequestParam int skin,
    		@ApiParam(name="smsagree", value="SMS 수신동의  <br/> Y : 동의함 , N : 미동의", required = false) @RequestParam(name="smsagree", required=false) String smsagree,
    		@ApiParam(name="emailagree", value="이메일 수신동의 <br/> Y : 동의함 , N : 미동의", required = false) @RequestParam(name="emailagree", required=false) String emailagree,
    		JwtAuthenticationToken token
    		) {
    	
        return userService.updateUserV2(password, skin, smsagree, emailagree, token);
    }
    
    /**
     * 회원정보 수정
     * @return
     */
    @ApiOperation(value = "프로필사진 등록", notes = "프로필 사진을 등록한다", response = CommonResponse.class, tags={TAG, })
    @RequestMapping(value="/profile", method=RequestMethod.POST)
    public @ResponseBody CommonResponse profile(
    		@ApiParam(name="path", value="이미지", required = true) @RequestParam String path,
    		JwtAuthenticationToken token
    		) {
    	
        return userService.updateProfile(path, token);
    }
    
    /**
     * 회원정보 SNS 연결
     * @return
     */
    @ApiOperation(value = "SNS 연결", notes = "연결", response = CommonResponse.class, tags={TAG, })
    @RequestMapping(value="/sns_conn", method=RequestMethod.POST)
    public @ResponseBody CommonResponse snsConnect(
    		@ApiParam(name="sns_id", value="SNSID ", required = false) @RequestParam(name="sns_id", required=false) String id,
    		@ApiParam(name="sns_type", value="N:네이버 F:페이스북 K:카카오톡 ", required = false) @RequestParam(name="sns_type", required=false) String type,
    		JwtAuthenticationToken token
    		) {
    	
        return userService.snsConnect(id, type, token);
    }
    
    /**
     * 회원정보 SNS 연결
     * @return
     */
    @ApiOperation(value = "SNS 연결 해제", notes = "연결 해제", response = CommonResponse.class, tags={TAG, })
    @RequestMapping(value="/sns_dis", method=RequestMethod.POST)
    public @ResponseBody CommonResponse snsDisConnect(
    		@ApiParam(name="sns_type", value="N:네이버 F:페이스북 K:카카오톡 ", required = true) @RequestParam(name="sns_type", required=true) String type,
    		JwtAuthenticationToken token
    		) {
    	
        return userService.snsDisConnect(type, token);
    }
    
    
    /**
     * 회원탈퇴
     * @return
     */
    @ApiOperation(value = "회원탈퇴", notes = "탈퇴하기", response = UserResponse.class, tags={TAG, })
    @RequestMapping(value="/memberLeave", produces = { "application/json; charset=utf-8" }, method=RequestMethod.PUT)
    public @ResponseBody UserResponse memberLeave(
    		@ApiParam(value="탈티 이유") @RequestParam(name="reason", required=true) String reason,
    		JwtAuthenticationToken token) {
    	
    	return userService.memberLeave(reason, token);
    }
    
    @ApiOperation(value = "회원탈퇴", notes = "탈퇴 취소", response = UserResponse.class, tags={TAG, })
    @RequestMapping(value="/leaveCancel", produces = { "application/json; charset=utf-8" }, method=RequestMethod.PUT)
    public @ResponseBody UserResponse leaveCancel(
    		@ApiParam(value="탈티 이유") @RequestParam(name="reason", required=true) String reason,
    		JwtAuthenticationToken token) {
    	
    	return userService.leaveCancel(reason, token);
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
    		@ApiParam(name="appVersion", value="앱버전", required = true) @RequestParam String appVersion,
    		JwtAuthenticationToken token) {
        return userService.saveDeviceInfo(deviceId, os, deviceToken, osVersion, modelName, appVersion, token);
    }
    
	/**
     * 상담문의 리스트
     * @param	page 	조회 페이지
     * @return
     */
    @ApiOperation(value = "1:1상담", notes = "1:1 상담목록", response = QnaListResponse.class, tags={TAG, })
    @RequestMapping(value="/qna/list", produces = { "application/json; charset=utf-8" }, method=RequestMethod.GET)
    public @ResponseBody QnaListResponse qnaList(JwtAuthenticationToken token) {
    	return qnaService.qnaList(token);
    } 
    
    /**
     * 상담문의 등록
     * 
     * @return
     */
    @ApiOperation(value = "1:1상담", notes = "1:1 상담등록", response = CommonResponse.class, tags={TAG, })
    @RequestMapping(value="/qna", produces = { "application/json; charset=utf-8" }, method=RequestMethod.PUT)
    public @ResponseBody CommonResponse qnaRegist(
    		@ApiParam(value="제목") @RequestParam(name="subject", required=true) String subject,
    		@ApiParam(value="문의분류1") @RequestParam(name="qnaClass1", required=true) String qnaClass1,
    		@ApiParam(value="문의분류2") @RequestParam(name="qnaClass2", required=true) String qnaClass2,
    		@ApiParam(value="내용") @RequestParam(name="content", required=true) String content,
    		@ApiParam(value="이미지 경로[]") @RequestParam(value="files", required=false) String[] files,
    		@ApiParam(value="주문내역 ID") @RequestParam(name="orderId", required=false) Long orderId,
    		JwtAuthenticationToken token) {
    	
    	return qnaService.saveQna(orderId, subject, content, qnaClass1, qnaClass2, files, token);
    } 
    
    /**
     * 상담문의 삭제
     * 
     * @return
     */
    @ApiOperation(value = "1:1상담", notes = "1:1 상담삭제", response = QnaListResponse.class, tags={TAG, })
    @RequestMapping(value="/qna/delete", produces = { "application/json; charset=utf-8" }, method=RequestMethod.DELETE)
    public @ResponseBody QnaListResponse qnaDelete(
    		@ApiParam(value="QNA ID") @RequestParam(name="qnaId", required=true) Long qnaId,
    		JwtAuthenticationToken token) {
    	
    	return qnaService.delete(qnaId, token);
    } 
    
    /**
     * 포인트 리스트
     * @param	page 	조회 페이지
     * @return
     */
    @ApiOperation(value = "포인트 목록", notes = "포인트 목록", response = PointResponse.class, tags={TAG, })
    @RequestMapping(value="/mypoint/{page}", produces = { "application/json; charset=utf-8" }, method=RequestMethod.GET)
    public @ResponseBody PointResponse myPoint(
			JwtAuthenticationToken token,
			@ApiParam(name="page", value="조회할 페이지  0부터 시작", required = true) @PathVariable int page) {
    	return pointService.pointList(page, token);
    } 
}
