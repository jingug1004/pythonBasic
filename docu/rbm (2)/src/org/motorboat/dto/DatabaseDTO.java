package org.motorboat.dto;

import org.motorboat.db.*;
import org.motorboat.mng.*;
import org.motorboat.*;
import org.motorboat.common.*;
import org.motorboat.existing.*;

public class DatabaseDTO {
	String ip;
	int port;
	String sid;
	String user;
	String password;
	String jdbc_driver;
	String driver_type;
	
	public String getDriver_type() {
		return driver_type;
	}
	public void setDriver_type(String driver_type) {
		this.driver_type = driver_type;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getJdbc_driver() {
		return jdbc_driver;
	}
	public void setJdbc_driver(String jdbc_driver) {
		this.jdbc_driver = jdbc_driver;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}
