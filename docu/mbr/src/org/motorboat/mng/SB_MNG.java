package org.motorboat.mng;

import org.motorboat.common.PacketM;
import org.motorboat.dto.MeetsDTO;
import org.motorboat.db.*;

public class SB_MNG extends SDL_MNG {

	public SB_MNG(DbMNG dbMNG, MeetsDTO meetsDTO, String tokens) {
		super(dbMNG, meetsDTO, tokens);
		setDataType(SDL_MNG.TYPE_SB);
	}

	public void parsePacket() {

	}
	
	public void checkOutParsingResult() {
		
	}
}

