package org.motorboat.dto;

import java.util.*;

import org.motorboat.db.*;
import org.motorboat.mng.*;
import org.motorboat.*;
import org.motorboat.common.*;
import org.motorboat.existing.*;

public class MeetsDTO {
	
//	private Hashtable<String, Meet> meets = new Hashtable<String, Meet>();
	private Hashtable meets = new Hashtable();
	
	public void setMeet(String meetCode, String meetName) {
		meets.put(meetCode, new Meet(meetCode, meetName));
	}
	
	public String getMeetCode(String meetName) {
		
		String key = meetName.trim();
		
		for (Enumeration em = meets.elements(); em.hasMoreElements();) {
			Meet meet = (Meet)em.nextElement();
			
			if (meetName.equals(meet.getMeetName())) {
				return meet.getMeetCode(); 
			}
		}

		return null;
	}
	
	public String getMeetName(String meetCode) {
		return "";
	}
	
	public int getSize() {
		return meets.size();
	}
	
	class Meet {
		
		String meetCode;
		String meetName;
		
		public Meet(String meetCode, String meetName) {
			this.meetCode = meetCode;
			this.meetName = meetName;
		}
		
		public String getMeetCode() {
			return meetCode;
		}
		
		public void setMeetCode(String meetCode) {
			this.meetCode = meetCode;
		}
		
		public String getMeetName() {
			return meetName;
		}
		
		public void setMeetName(String meetName) {
			this.meetName = meetName;
		}
	}
}
