package org.motorboat.dto;

import org.motorboat.db.*;
import org.motorboat.mng.*;
import org.motorboat.*;
import org.motorboat.common.*;
import org.motorboat.existing.*;

public class SerialDeviceDTO {
	String device = "";
	
	public void setDevice(String device) {
		this.device = device;
	}
	
	public String getDevice() {
		return this.device;
	}
}
