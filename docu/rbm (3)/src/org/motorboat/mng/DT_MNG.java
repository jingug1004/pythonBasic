package org.motorboat.mng;

import org.motorboat.common.PacketM;
import org.motorboat.common.SDL_Common;
import org.motorboat.dto.MeetsDTO;
import org.motorboat.db.*;

public class DT_MNG extends SDL_MNG {
	
	private String asscNumber = null;			// Association Number
	private String commNumber = null;			// Community Number
	private String divNumber = null;			// Division Number
	private String divTotal = null;				// ¸ÅÃâ¾×

	public DT_MNG(DbMNG dbMNG, MeetsDTO meetsDTO, String tokens) {
		super(dbMNG, meetsDTO, tokens);
		setDataType(SDL_MNG.TYPE_DT);
	}
	
	public void parsePacket() {
		PacketM dtPacketM = new PacketM(tokens);
		dtPacketM.seekPos(47);
		
		asscNumber = dtPacketM.splitPacketToString(2);
		commNumber = dtPacketM.splitPacketToString(2);
		divNumber = dtPacketM.splitPacketToString(2);
		divTotal = dtPacketM.splitPacketToString(11);
	}
	
	public void checkOutParsingResult() {
		SDL_Common.logger.info("asscNumber:[" + asscNumber + "]");
		SDL_Common.logger.info("commNumber:[" + commNumber + "]");
		SDL_Common.logger.info("divNumber:[" + divNumber + "]");
		SDL_Common.logger.info("divTotal:[" + divTotal + "]");
	}
	
	public void setAsscNumber(String asscNumber) {
		this.asscNumber = asscNumber;
	}

	public void setCommNumber(String commNumber) {
		this.commNumber = commNumber;
	}

	public void setDivNumber(String divNumber) {
		this.divNumber = divNumber;
	}
	
	public void setDivTotal(String divTotal) {
		this.divTotal = divTotal;
	}
	
	public String getAsscNumber() {
		return asscNumber;
	}

	public String getCommNumber() {
		return commNumber;
	}

	public String getDivNumber() {
		return divNumber;
	}

	public String getDivTotal() {
		return divTotal;
	}
}
