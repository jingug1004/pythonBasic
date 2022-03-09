package org.motorboat.mng;

import java.util.LinkedList;

import org.motorboat.common.PacketM;
import org.motorboat.common.SDL_Common;
import org.motorboat.dto.MeetsDTO;
import org.motorboat.db.*;

public class RS_MNG extends SDL_MNG {
	
	private String poolCD = null;			// ½Â½Ä
	
	private int winRacerCount = 0;			// ½ÂÀÚ¼ö
	
//	private LinkedList<WinRacer> linkedWinRacerList = new LinkedList<WinRacer>();
	private LinkedList linkedWinRacerList = new LinkedList();

	public RS_MNG(DbMNG dbMNG, MeetsDTO meetsDTO, String tokens) {
		super(dbMNG, meetsDTO, tokens);
		setDataType(SDL_MNG.TYPE_RS);
	}
	
	public void parsePacket() {
		PacketM rsPacketM = new PacketM(tokens);
		rsPacketM.seekPos(47);

		poolCD = rsPacketM.splitPacketToString(3);
		winRacerCount = rsPacketM.splitPacketToInt(2);

		for (int i = 0; i < winRacerCount; i++) {
			WinRacer winRacer = new WinRacer();
			String status = rsPacketM.splitPacketToString(1);
			int racerCount = rsPacketM.splitPacketToInt(2);
			winRacer.setRacerCount(racerCount);
			winRacer.setStatus(status);
			for (int j = 0; j < racerCount; j++) {
				if (j > 0)
					rsPacketM.rollPos(1);

				String runner = rsPacketM.splitPacketToString(2);
				if (runner.equals("[]"))
					runner = "00";

				String.valueOf(Integer.parseInt(runner));

				switch(j) {
				case 0:
					winRacer.setRunner1ST(runner);
					break;
				case 1:
					winRacer.setRunner2ND(runner);
					break;
				case 2:
					winRacer.setRunner3RD(runner);
					break;
				default:
					break;
				}
			}

			String payoff = rsPacketM.splitPacketToString(9);
			winRacer.setPayoff(payoff);

			setWinRacerToList(winRacer);
		}
	}

	public void checkOutParsingResult() {
		String _poolCD = getPoolCD();
		SDL_Common.logger.info("[" + _poolCD + "]");
		int linkedWinRacerCount = linkedWinRacerList.size();
		
		for (int i = 0; i < linkedWinRacerCount; i++) {
			WinRacer winRacer = getWinRacerFromList(i);
			String _status = winRacer.getStatus();
			String _payoff = winRacer.getPayoff();

			StringBuffer _racerBuffer = new StringBuffer();
			_racerBuffer.append(_status);
			_racerBuffer.append(" : [");
			_racerBuffer.append(winRacer.getRunner1ST());
			_racerBuffer.append(']');
			_racerBuffer.append('[');
			_racerBuffer.append(winRacer.getRunner2ND());
			_racerBuffer.append(']');
			_racerBuffer.append('[');
			_racerBuffer.append(winRacer.getRunner3RD());
			_racerBuffer.append("] : ");
			_racerBuffer.append(_payoff);

			SDL_Common.logger.info(new String(_racerBuffer));
		}
	}
	
	public void setPoolCD(String poolCD) {
		this.poolCD = poolCD;
	}
	
	public void setWinRacerToList(WinRacer winRacer) {
		linkedWinRacerList.add(winRacer);
	}
	
	public String getPoolCD() {
		return poolCD;
	}
	
	public WinRacer getWinRacerFromList(int index) {
		WinRacer winRacer = (WinRacer)linkedWinRacerList.get(index);
		return winRacer;
	}
	
	public int getWinRacerCount() {
		return this.winRacerCount;
	}
	
	public String[] getRunners(int index) {
		WinRacer winRacer = getWinRacerFromList(index);
		String[] runners = new String[3];
		runners[0] = winRacer.getRunner1ST();
		runners[1] = winRacer.getRunner2ND();
		runners[2] = winRacer.getRunner3RD();
		
		return runners;
	}
	
	public String getPayoff(int index) {
		WinRacer winRacer = getWinRacerFromList(index);
		return winRacer.getPayoff();
	}
	
	public String getStatus(int index) {
		WinRacer winRacer = getWinRacerFromList(index);
		return winRacer.getStatus();
	}

	class WinRacer {
		
		private String runner1ST = null;
		private String runner2ND = null;
		private String runner3RD = null;
		
		private String payoff = null;			// ¹è´çÀ²
		private String status = null;			// ¹è´çÇüÅÂ
		private int racerCount = 0;				// ½Â½Äº° ½ÂÀÚ¼ö
		
		public WinRacer() {
			runner1ST = "0";
			runner2ND = "0";
			runner3RD = "0";
		}
		
		public void setRunner1ST(String runner1ST) {
			this.runner1ST = String.valueOf(Integer.parseInt(runner1ST));
		}
		
		public void setRunner2ND(String runner2ND) {
			this.runner2ND = String.valueOf(Integer.parseInt(runner2ND));
		}
		
		public void setRunner3RD(String runner3RD) {
			this.runner3RD = String.valueOf(Integer.parseInt(runner3RD));
		}
		
		public void setPayoff(String payoff) {
			this.payoff = payoff;
		}
		
		public void setStatus(String status) {
			this.status = status;
		}
		
		public void setRacerCount(int racerCount) {
			this.racerCount = racerCount;
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

		public String getPayoff() {
			return payoff;
		}

		public String getStatus() {
			return status;
		}
		
		public int getRacerCount() {
			return this.racerCount;
		}
	}
}
