package org.motorboat.mng;

import java.util.*;

import org.motorboat.db.*;
import org.motorboat.*;
import org.motorboat.dto.*;
import org.motorboat.common.*;
import org.motorboat.existing.*;
import org.motorboat.db.*;

public class PB_MNG extends SDL_MNG {
	
	private String poolCD = null;			// 승식
	private int bigRecordCount = 0;		// 테이블 수
	
//	private LinkedList<BigRecord> linkedBigRecordList = new LinkedList<BigRecord>();
	private LinkedList linkedBigRecordList = new LinkedList();
	
	public PB_MNG(DbMNG dbMNG, MeetsDTO meetsDTO, String tokens) {
		super(dbMNG, meetsDTO, tokens);
		setDataType(SDL_MNG.TYPE_PB);
	}
	
	public void parsePacket() {
		PacketM pbPacketM = new PacketM(tokens);
		pbPacketM.seekPos(47);
		poolCD = pbPacketM.splitPacketToString(3);
		bigRecordCount = pbPacketM.splitPacketToInt(2);
		
		int bigRecordIndex = 0;
		int racerCount = 0;
		
		for (int i = 0; i < bigRecordCount; i++) {
			bigRecordIndex = pbPacketM.splitPacketToInt(2);
			racerCount = pbPacketM.splitPacketToInt(2);
			BigRecord bigRecord = new BigRecord(bigRecordIndex, racerCount);

			for (int j = 0; j < racerCount; j++) {
				bigRecord.setOdds(pbPacketM.splitPacketToString(4));
			}
			setBigRecord(bigRecord);
		}
	}

	public void checkOutParsingResult() {
		try {
			String _poolCD = getPoolCD();
			int _bigRecordCount = linkedBigRecordList.size();
			
			SDL_Common.logger.info("승식:[" + _poolCD + "]");
			
			StringBuffer bigRecordBuffer = null;
			for (int i = 0; i < _bigRecordCount; i++) {
				bigRecordBuffer = new StringBuffer();
				BigRecord bigRecord = getBigRecord(i);
				
				for (int j = 0; j < bigRecord.getRacerCount(); j++) {
					String odds = bigRecord.getOdds(j);
					bigRecordBuffer.append('[');
					bigRecordBuffer.append(odds);
					bigRecordBuffer.append(']');
				}

				SDL_Common.logger.info(new String(bigRecordBuffer));
			}
			
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		}
	}
	
	public String getOdds(int runner1ST, int runner2ST) {
		
		BigRecord bigRecord = getBigRecord(runner1ST);
		
		StringBuffer oddsBuffer = new StringBuffer();
		if (bigRecord == null) {
			for (int i = 0; i < SDL_MNG.ODDS_DIGIT_LEN; i++) {
				oddsBuffer.append('X');
			}
			
			return new String(oddsBuffer);
		}
		
		return bigRecord.getOdds(runner2ST);
	}
	
	public int getRacerCount(int runner1ST) {
		BigRecord bigRecord = getBigRecord(runner1ST);
		if (bigRecord == null) {
			return -1;
		}
		return bigRecord.getRacerCount();
	}
	
	public void setBigRecordCount(int bigRecordCount) {
		this.bigRecordCount = bigRecordCount;
	}
	
	public void setBigRecord(BigRecord bigRecord) {
		linkedBigRecordList.add(bigRecord);
	}
	
	public void setPoolCD(String poolCD) {
		this.poolCD = poolCD;
	}
	
	public int getBigRecordCount() {
		return this.bigRecordCount;
	}
	
	public BigRecord getBigRecord(int index) {
		if (index > getBigRecordCount())
			return null;
		return (BigRecord)linkedBigRecordList.get(index);
	}
	
	public String getPoolCD() {
		return this.poolCD;
	}
	
	class BigRecord {

		private int bigRecordIndex = 0;		// 선수번호
		private int racerCount = 0;			// 선수수
		
		/*
		 * 배당율(racerCount와 add된 element의 갯수가 동일해야 함)
		 */
//		private LinkedList<String> linkedOddsList = new LinkedList<String>();
		private LinkedList linkedOddsList = new LinkedList();
										
		public BigRecord(int bigRecordIndex, int racerCount) {
			setRacerCount(racerCount);
		}
		
		public void setBigRecordIndex(int bigRecordIndex) {
			this.bigRecordIndex = bigRecordIndex;
		}
		
		public void setRacerCount(int racerCount) {
			this.racerCount = racerCount;
		}
		
		public void setOdds(String odds) {
			linkedOddsList.add(odds);
		}
		
		public int getBigRecordIndex() {
			return this.bigRecordIndex;
		}
		
		public int getRacerCount() {
			return this.racerCount;
		}
		
		public String getOdds(int index) {
			return (String)linkedOddsList.get(index);
		}
	}
}
