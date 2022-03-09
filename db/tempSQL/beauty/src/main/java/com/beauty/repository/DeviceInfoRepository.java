package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.DeviceInfo;
import com.beauty.entity.User;

@Repository
@Qualifier(value = "DeviceInfoRepository")
public interface DeviceInfoRepository extends JpaRepository<DeviceInfo, String> {
    
	public List<DeviceInfo> findByOs(String os);
	public List<DeviceInfo> findByUser(User user);
	public List<DeviceInfo> findByOsAndUserIn(String os, List<User> users);
}
