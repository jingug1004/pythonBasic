package org.motorboat.mng;

import org.motorboat.db.*;
import org.motorboat.*;
import org.motorboat.dto.*;
import org.motorboat.common.*;
import org.motorboat.existing.*;

public class TM_MNG extends SDL_MNG {
	
	private String currentDate = null;			// 현재일자
	private String currentTime = null;			// 현재시간
	private String closingSaleTime = null;		// 발매마감시간
	private String restTime = null;				// 잔여시간

	public TM_MNG(DbMNG dbMNG, MeetsDTO meetsDTO, String tokens) {
		super(dbMNG, meetsDTO, tokens);
		setDataType(SDL_MNG.TYPE_TM);
	}

	public void parsePacket() {
		PacketM tmPacketM = new PacketM(tokens);
		tmPacketM.seekPos(47);
		
		currentDate = tmPacketM.splitPacketToString(8);
		currentTime = tmPacketM.splitPacketToString(6);
		closingSaleTime = tmPacketM.splitPacketToString(6);
		restTime = tmPacketM.splitPacketToString(2);
	}
	
	public void checkOutParsingResult() {
		SDL_Common.logger.info("currentDate : [" + currentDate + "]");
		SDL_Common.logger.info("currentTime : [" + currentTime + "]");
		SDL_Common.logger.info("closingSale : [" + closingSaleTime + "]");
		SDL_Common.logger.info("restTime : [" + restTime + "]");
	}

	private void setClosingSale() {
		closingSaleTime = SDL_Common.getPacketFraction(tokens, 61, 6);
	}
	
	private void setCurrentDate() {
		currentDate = SDL_Common.getPacketFraction(tokens, 47, 8);
	}
	
	private void setCurrentTime() {
		currentTime = SDL_Common.getPacketFraction(tokens, 55, 6);
	}
	
	private void setRestTime() {
		restTime = SDL_Common.getPacketFraction(tokens, 67, 2);
	}

	public String getClosingSaleTime() {
		return closingSaleTime;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public String getRestTime() {
		return restTime;
	}

}
