package org.motorboat.mng;

import org.motorboat.common.PacketM;
import org.motorboat.dto.MeetsDTO;
import org.motorboat.db.*;

public class MT_MNG extends SDL_MNG {

	public MT_MNG(DbMNG dbMNG, MeetsDTO meetsDTO, String tokens) {
		super(dbMNG, meetsDTO, tokens);
		setDataType(SDL_MNG.TYPE_MT);
	}

	public void parsePacket() {

	}
	
	public void checkOutParsingResult() {
		
	}
}
