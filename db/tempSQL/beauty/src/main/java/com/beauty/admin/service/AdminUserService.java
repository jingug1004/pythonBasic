package com.beauty.admin.service;

import java.util.List;

import com.beauty.entity.DataTables;
import com.beauty.entity.DeviceInfo;
import com.beauty.entity.Role;
import com.beauty.entity.User;
import com.beauty.response.DataTablesResult;

public interface AdminUserService {

	public User getUser(String userId);
	
	// 리스트
	public DataTablesResult list(DataTables input, String type, String role);
	
	public String updateUser(String userId, String type);
	
	// 삭제
	public String deleteUser(String userId);

	// 권한
	public String updateAuth(String userId, String role);
	
	public void regUser(String username, String email, String name, String phone, String password);
		
	public List<DeviceInfo> getDevice(String userId);
	
	public List<User> getRoleUser(Role role);
	
	public String leaveCancel(String user_id);
}
