package com.beauty.service;

import java.util.Optional;

import com.beauty.entity.User;
import com.beauty.entity.UserSns;
import com.beauty.response.CommonResponse;
import com.beauty.response.DeviceInfoResponse;
import com.beauty.response.UserResponse;
import com.beauty.response.VersionResponse;
import com.beauty.security.auth.JwtAuthenticationToken;

public interface UserService {
	
	public Optional<UserSns> getById(String id, String joinType);
	public User getById(String id);
    public UserResponse findEmail(String name, String phone);
    public CommonResponse findPassword(String email, String phone);
    public CommonResponse findEmailPassword(String email, String name);
    
    public UserResponse saveUser(User user, String recommend_user, String id);
    public UserResponse saveUserV2(User user, String recommend_user, String id);
    public UserResponse updateUser(String email, int skin, String smsagree, String emailagree, JwtAuthenticationToken token);
    public UserResponse updateUserV2(String password, int skin, String smsagree, String emailagree, JwtAuthenticationToken token);
    public UserResponse getUser(JwtAuthenticationToken token);
    
    public UserResponse memberLeave(String reason, JwtAuthenticationToken token);
    
    public UserResponse leaveCancel(String reason, JwtAuthenticationToken token);
    
    public DeviceInfoResponse saveDeviceInfo(String deviceId, String os, String deviceToken, String osVersion, String modelName, String appVersion, JwtAuthenticationToken token);
    public VersionResponse selectVersion( String os_type, String version_name);
    
    public CommonResponse sendSms(String phone);
    public CommonResponse smsCert(String phone, String cert);
    
    public CommonResponse updateProfile(String path, JwtAuthenticationToken token);
    
    
    public CommonResponse snsConnect(String id, String type, JwtAuthenticationToken token);
    public CommonResponse snsDisConnect( String type, JwtAuthenticationToken token);
    
}
