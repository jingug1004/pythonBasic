package org.motorboat.mng;

import java.util.LinkedList;

import org.motorboat.common.PacketM;
import org.motorboat.common.SDL_Common;
import org.motorboat.dto.MeetsDTO;
import org.motorboat.db.*;

public class TR_MNG extends SDL_MNG {
	
	private String poolCD = null;				// 승식
	private int racerCount = 0;				// 선수수
	/*
	 * 배당율(racerCount와 linkedBigRecordList의 element수가 동일해야 )
	 */
//	private LinkedList<BigRecord> linkedBigRecordList = new LinkedList<BigRecord>();
	private LinkedList linkedBigRecordList = new LinkedList();

	public TR_MNG(DbMNG dbMNG, MeetsDTO meetsDTO, String tokens) {
		super(dbMNG, meetsDTO, tokens);
		setDataType(SDL_MNG.TYPE_TR);
	}
	
	public void parsePacket() {
		PacketM trPacketM = new PacketM(tokens);
		trPacketM.seekPos(47);
		
		poolCD = trPacketM.splitPacketToString(3);
		racerCount = trPacketM.splitPacketToInt(3);
		
		int bigRecordCount = (dataLength - 6)/(6 + SDL_MNG.ODDS_DIGIT_LEN);
		
		for (int i = 0; i < bigRecordCount; i++) {
			String runner1ST = String.valueOf(trPacketM.splitPacketToInt(2));
			String runner2ND = String.valueOf(trPacketM.splitPacketToInt(2));
			String runner3RD = String.valueOf(trPacketM.splitPacketToInt(2));
			String odds = trPacketM.splitPacketToString(SDL_MNG.ODDS_DIGIT_LEN);
			
			BigRecord bigRecord = new BigRecord(runner1ST, runner2ND, runner3RD, odds);
			setBigRecordToList(bigRecord);
		}
	}
	
	public void checkOutParsingResult() {
		String _poolCD = getPoolCD();
		int _racerCount = getRacerCount();
		
		StringBuffer _titleBuffer = new StringBuffer();
		
		_titleBuffer.append("[");
		_titleBuffer.append(_poolCD);
		_titleBuffer.append("] : ");
		_titleBuffer.append("선수수 [");
		_titleBuffer.append(_racerCount);
		_titleBuffer.append("]");
		
		int linkedBigRecordcount = linkedBigRecordList.size();
		
		StringBuffer _bigRecordBuffer = null;
		for (int i = 0; i < linkedBigRecordcount; i++) {
			BigRecord bigRecord = getBigRecordFromList(i);
			_bigRecordBuffer = new StringBuffer();
			
			_bigRecordBuffer.append("[");
			_bigRecordBuffer.append(bigRecord.getRunner1ST());
			_bigRecordBuffer.append("-");
			_bigRecordBuffer.append(bigRecord.getRunner2ND());
			_bigRecordBuffer.append("-");
			_bigRecordBuffer.append(bigRecord.getRunner3RD());
			_bigRecordBuffer.append("] : ");
			_bigRecordBuffer.append(bigRecord.getOdds());
			
			SDL_Common.logger.info(new String(_bigRecordBuffer));
		}
	}
	
	public void setRacerCount(int racerCount) {
		this.racerCount = racerCount;
	}
	
	public void setBigRecordToList(BigRecord bigRecord) {
		linkedBigRecordList.add(bigRecord);
	}
	
	public void setPoolCD(String poolCD) {
		this.poolCD = poolCD;
	}
	
	public int getRacerCount() {
		return this.racerCount;
	}
	
	public BigRecord getBigRecordFromList(int index) {
		BigRecord bigRecord = (BigRecord)linkedBigRecordList.get(index);
		return bigRecord;
	}
	
	public String getPoolCD() {
		return this.poolCD;
	}
	
	public String[] getRunnerArray(int index) {
		BigRecord bigRecord = null;
		String[] runners = new String[3];
		try {
			bigRecord = (BigRecord)linkedBigRecordList.get(index);
			runners[0] = bigRecord.getRunner1ST();
			runners[1] = bigRecord.getRunner2ND();
			runners[2] = bigRecord.getRunner3RD();
			
			return runners;
		} catch(Exception ex) {
			return null;
		}
	}
	
	public String getOdds(int index) {
		BigRecord bigRecord = null;
		String odds = null;
		try {
			bigRecord = (BigRecord)linkedBigRecordList.get(index);
			odds = bigRecord.getOdds();
			
			return odds;
		} catch(Exception ex) {
			return null;
		}
	}
	
	public int getBigRecordCount() {
		return linkedBigRecordList.size();
	}

	class BigRecord {
		
		private String runner1ST = null;
		private String runner2ND = null;
		private String runner3RD = null;
		
		private String odds = null;
		
		public BigRecord(String runner1ST, String runner2ND, String runner3RD, String odds) {
			this.runner1ST = runner1ST;
			this.runner2ND = runner2ND;
			this.runner3RD = runner3RD;
			this.odds = odds;
		}
		
		public void setRunner1ST(String runner1ST) {
			this.runner1ST = runner1ST;
		}
		
		public void setRunner2ND(String runner2ND) {
			this.runner2ND = runner2ND;
		}
		
		public void setRunner3RD(String runner3RD) {
			this.runner3RD = runner3RD;
		}
		
		public void setOdds(String odds) {
			this.odds = odds;
		}

		public String getRunner1ST() {
			return runner1ST;
		}

		public String getRunner2ND() {
			return runner2ND;
		}

		public String getRunner3RD() {
			return runner3RD;
		}

		public String getOdds() {
			return odds;
		}
	}
}
