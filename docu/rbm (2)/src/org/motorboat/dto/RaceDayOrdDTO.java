package org.motorboat.dto;

public class RaceDayOrdDTO {
	
	private boolean isAvailable = false;

	private String stndYear = null;			// 기준년도
	private int tms = 0;					// 회차
	private int dayOrd = 0;					// 일차
	private String meetCode = "000";		// meet code
	
	public RaceDayOrdDTO() {
		this.isAvailable = false;
	}
	
	public void setStndYear(String stndYear) {
		this.stndYear = stndYear;
	}
	
	public void setTms(int tms) {
		this.tms = tms;
	}
	
	public void setDayOrd(int dayOrd) {
		this.dayOrd = dayOrd;
	}
	
	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	public void setMeetCode(String meetCode) {
		this.meetCode = meetCode;
	}
	
	public String getStndYear() {
		return this.stndYear;
	}
	
	public int getTms() {
		return this.tms;
	}
	
	public int getDayOrd() {
		return this.dayOrd = dayOrd;
	}
	
	public boolean isAvailable() {
		return this.isAvailable;
	}
	
	public String getMeetCode() {
		return this.meetCode;
	}
}
