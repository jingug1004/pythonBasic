package org.motorboat.mng;

import org.motorboat.db.*;
import org.motorboat.*;
import org.motorboat.dto.*;
import org.motorboat.common.*;
import org.motorboat.existing.*;

public abstract class SDL_MNG {
	
	public static final int TYPE_TM = 10001;
	public static final int TYPE_PT = 10002;
	public static final int TYPE_PB = 10003;
	public static final int TYPE_TR = 10004;
	public static final int TYPE_FN = 10005;
	public static final int TYPE_RS = 10006;
	public static final int TYPE_AB = 10007;
	public static final int TYPE_SB = 10008;
	public static final int TYPE_MT = 10009;
	public static final int TYPE_DT = 10010;
	
	public static final int ODDS_DIGIT_LEN = 4;
	
	public boolean isAvailableMNG = true;
	
	public int dataType = 0;
	public String tokens = null;
	
	public RaceDayOrdDTO raceDayOrdDTO = null;
	
	public String raceNumber = null;
	public String meetCode = null;
	public String meetName = null;
	public String perfNumber = null;
	public String perfType = null;
	public int dataLength = 0;
	
	MeetsDTO meetsDTO;
	DbMNG dbMNG;

/*
	public abstract void setRaceNumber();
	public abstract void setMeetName();
	public abstract void setMeetCode();
	public abstract void setPerfNumber();
	public abstract void setPerfType();
*/

	public abstract void parsePacket();
	public abstract void checkOutParsingResult();
//	public abstract void doProcess();
		
	public SDL_MNG(DbMNG dbMNG, MeetsDTO meetsDTO, String tokens) {
		init(dbMNG, meetsDTO, tokens);
	}
	
	private void init(DbMNG dbMNG, MeetsDTO meetsDTO, String tokens) {
		setDbMNG(dbMNG);

		setMeetDTO(meetsDTO);
		setTokens(tokens);
		setRaceNumber();		
		setMeetName();			
		setMeetCode();			
		setPerfNumber();
		setPerfType();
		setDataLength();

		setRaceDayOrdDTO();
	}
	
	public void doProcess() {
		checkOutParsingResult();
		dbMNG.applyPacket(this);
	}
	
	public void setRaceNumber() {
		raceNumber = SDL_Common.getPacketFraction(tokens, 41, 2);
		if (raceNumber == null) {
			SDL_Common.logger.warn("setRaceNumber error");
			isAvailableMNG = false;
		}
	}

	public void setMeetName() {
		meetName = SDL_Common.getPacketFraction(tokens, 3, 30).trim();
		if (meetName == null) {
			SDL_Common.logger.warn("setMeetName error");
			isAvailableMNG = false;
		}
	}
	
	public void setMeetCode() {
		try {
			meetCode = meetsDTO.getMeetCode(meetName);
			if (meetCode == null) {
				isAvailableMNG = false;
			}
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		}
	}

	public void setPerfNumber() {
		perfNumber = SDL_Common.getPacketFraction(tokens, 33, 4);
	}
	
	public void setPerfType() {
		perfType = SDL_Common.getPacketFraction(tokens, 37, 4);
	}
	
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	
	public void setDataLength() {
		dataLength = SDL_Common.getPacketFractionToInt(tokens, 43, 4);
		this.dataLength = dataLength;
	}
	
	public void setRaceDayOrdDTO() {
		this.raceDayOrdDTO = dbMNG.getRaceDayOrdDTO(meetCode);
	}
	
	public void setTokens(String tokens) {
		this.tokens = tokens;
	}
	
	public void setMeetDTO(MeetsDTO meetsDTO) {
		this.meetsDTO = meetsDTO;
	}
	
	public void setDbMNG(DbMNG dbMNG) {
		this.dbMNG = dbMNG;
	}
	
	public void setMeetsDTO(MeetsDTO meetsDTO) {
		this.meetsDTO = meetsDTO;
	}
	
	public int getDataType() {
		return this.dataType;
	}
	
	public String getTokens() {
		return this.tokens;
	}
	
	public String getRaceNumber() {
		return this.raceNumber;
	}
	
	public String getPerfNumber() {
		return perfNumber;
	}

	public String getPerfType() {
		return perfType;
	}
	
	public boolean isAvailableMNG() {
		return this.isAvailableMNG;
	}
	
	public DbMNG getDbMNG() {
		return this.dbMNG;
	}
	
	public int getDataLength() {
		return this.dataLength;
	}
	
	public RaceDayOrdDTO getRaceDayOrdDTO() {
		return this.raceDayOrdDTO;
	}

	public String getMeetCode() {
		return this.meetCode;
	}
	
	public String getMeetName() {
		return this.meetName;
	}
}
