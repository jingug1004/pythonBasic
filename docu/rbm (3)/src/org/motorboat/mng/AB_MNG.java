package org.motorboat.mng;

import org.motorboat.common.PacketM;
import org.motorboat.dto.MeetsDTO;
import org.motorboat.db.*;

public class AB_MNG extends SDL_MNG {
	
	public AB_MNG(DbMNG dbMNG, MeetsDTO meetsDTO, String tokens) {
		super(dbMNG, meetsDTO, tokens);
		setDataType(SDL_MNG.TYPE_AB);
	}

	public void parsePacket() {

	}
	
	public void checkOutParsingResult() {
		
	}
}
