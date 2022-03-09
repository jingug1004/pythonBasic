package org.motorboat.mng; 

import org.motorboat.db.*;
import org.motorboat.*;
import org.motorboat.dto.*;
import org.motorboat.common.*;
import org.motorboat.existing.*;

public class PT_MNG extends SDL_MNG {
	
	private String poolCD = null;		// ½Â½Ä
	private String poolTotal = null;	// Total Amount
	
	public PT_MNG(DbMNG dbMNG, MeetsDTO meetsDTO, String tokens) {
		super(dbMNG, meetsDTO, tokens);
		setDataType(SDL_MNG.TYPE_PT);
	}
	
	public void parsePacket() {
		PacketM ptPacketM = new PacketM(tokens);
		ptPacketM.seekPos(47);
		
		poolCD = ptPacketM.splitPacketToString(3);
		poolTotal = ptPacketM.splitPacketToString(11);
	}
	
	public void checkOutParsingResult() {
		String _poolCd = getPoolCD();
		String _poolTotal = getPoolTotal();
		
		SDL_Common.logger.info("½Â½Ä:[" + _poolCd + "],ÃÑÅõÇ¥±Ý¾×:[" + _poolTotal + "]");
	}

	public void setPoolCD(String poolCD) {
		this.poolCD = poolCD;
	}
	
	public void setPoolTotal(String poolTotal) {
		this.poolTotal = poolTotal;
	}
	
	public String getPoolCD() {
		return poolCD;
	}

	public String getPoolTotal() {
		return poolTotal;
	}
}
