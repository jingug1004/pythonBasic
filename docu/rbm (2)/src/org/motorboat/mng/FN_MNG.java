package org.motorboat.mng;

import java.util.LinkedList;

import org.motorboat.common.PacketM;
import org.motorboat.common.SDL_Common;
import org.motorboat.dto.MeetsDTO;
import org.motorboat.mng.TR_MNG.BigRecord;
import org.motorboat.db.*;

public class FN_MNG extends SDL_MNG {

//	private LinkedList<RankInfo> linkedRankInfoList = new LinkedList<RankInfo>();
	private LinkedList linkedRankInfoList = new LinkedList();

	public FN_MNG(DbMNG dbMNG, MeetsDTO meetsDTO, String tokens) {
		super(dbMNG, meetsDTO, tokens);
		setDataType(SDL_MNG.TYPE_FN);
	}

	public void parsePacket() {
		int rank = 0;
		int count = 0;
		String racerNumber[] = null;
		
		PacketM fnPacketM = new PacketM(tokens);
		fnPacketM.seekPos(47);
		
		try {
		
			int rankInfoListCount = fnPacketM.splitPacketToInt(2);
			for (int i = 0; i < rankInfoListCount; i++) {
				RankInfo rankInfo = new RankInfo();
				rank = fnPacketM.splitPacketToInt(2);
				count = fnPacketM.splitPacketToInt(2);
				rankInfo.setRank(rank);
				rankInfo.setRacerCount(count);
				
				racerNumber = new String[count]; 
				for (int j = 0; j < count; j++) {
					racerNumber[j] = fnPacketM.splitPacketToString(2);
				}
				rankInfo.setRacerNumber(racerNumber);
				setRankInfoToList(rankInfo);
			}
			
			count = fnPacketM.splitPacketToInt(2);
			racerNumber = new String[1];
			RankInfo rankInfo = new RankInfo();
			racerNumber = new String[count];
			for (int i = 0; i < count; i++) {
				racerNumber[i] = fnPacketM.splitPacketToString(2);
			}
			rankInfo.setRank(0);
			rankInfo.setRacerCount(count);
			rankInfo.setRacerNumber(racerNumber);
			setRankInfoToList(rankInfo);
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		}
	}
	
	public void checkOutParsingResult() {
		try {
			int size = linkedRankInfoList.size();
			for (int i = 0; i < size; i++) {
				RankInfo rankInfo = getRankInfoFromList(i);
				int rank = rankInfo.getRank();
				
				String[] racerNumber = rankInfo.getRacerNumber();
				StringBuffer racerNumberBuffer = new StringBuffer();
				
				for (int j = 0; j < racerNumber.length; j++) {
					if (j > 0)
						racerNumberBuffer.append('/');
					racerNumberBuffer.append('[');
					racerNumberBuffer.append(racerNumber[j]);
					racerNumberBuffer.append(']');
				}
				
				SDL_Common.logger.info("[" + rank + "]위:" + new String(racerNumberBuffer));
			}
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		}
	}

	public void setRankInfoToList(RankInfo rankInfo) {
		linkedRankInfoList.add(rankInfo);
	}
	
	public RankInfo getRankInfoFromList(int index) {
		RankInfo rankInfo = (RankInfo)linkedRankInfoList.get(index);
		return rankInfo;
	}
	
	public int getLinkedRankInfoCount() {
		return linkedRankInfoList.size();
	}
	
	public int getRank(int index) {
		if (getLinkedRankInfoCount() <= index) 
			return -1;
		
		RankInfo rankInfo = getRankInfoFromList(index);
		
		return rankInfo.getRank();
	}
	
	public int getRacerCount(int index) {
		if (getLinkedRankInfoCount() <= index) 
			return -1;
		
		RankInfo rankInfo = getRankInfoFromList(index);
		
		return rankInfo.getRacerCount();
	}
	
	public String[] getRacerNumber(int index) {
		if (getLinkedRankInfoCount() <= index) 
			return null;
		
		RankInfo rankInfo = getRankInfoFromList(index);
		
		return rankInfo.getRacerNumber();
	}

	class RankInfo {

		private int rank = 0;						// 착순(0인 경우 취소선수)
		private int racerCount = 0;				// 동순위자 수
		private String racerNumber[] = null; 

		public RankInfo() {
			
		}

		public void setRank(int rank) {
			this.rank = rank;
		}
		
		public void setRacerCount(int racerCount) {
			this.racerCount = racerCount;
		}
		
		public void setRacerNumber(String[] racerNumber) {
			this.racerNumber = racerNumber;
		}

		public int getRank() {
			return rank;
		}

		public int getRacerCount() {
			return racerCount;
		}

		public String[] getRacerNumber() {
			return racerNumber;
		}
	}
}

