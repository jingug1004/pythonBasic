package org.motorboat.db;

import java.sql.*;

import org.motorboat.*;
import org.motorboat.mng.*;
import org.motorboat.dto.*;
import org.motorboat.common.*;
import org.motorboat.existing.*;

public class DbMNG {
	
	private ToteRead toteRead = null;
	
	private DatabaseDTO databaseDTO;
	private Connection connection;
	
	
	DbDAO dbDAO = null;
	
	public DbMNG(ToteRead toteRead, DatabaseDTO databaseDTO) {
		this.toteRead = toteRead;
		this.databaseDTO = databaseDTO;
		try {
			connection = getConnection();
			dbDAO = new DbDAO(toteRead, connection);
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		}
	}
	
	public void applyPacket(SDL_MNG sdl_MNG) {
	
		switch(sdl_MNG.getDataType()) {
		case SDL_MNG.TYPE_TM:
			applyTM((TM_MNG)sdl_MNG);
			break;
		case SDL_MNG.TYPE_PT:
			applyPT((PT_MNG)sdl_MNG);
			break;
		case SDL_MNG.TYPE_PB:
			applyPB((PB_MNG)sdl_MNG);
			break;
		case SDL_MNG.TYPE_TR:
			applyTR((TR_MNG)sdl_MNG);
			break;
		case SDL_MNG.TYPE_FN:
			applyFN((FN_MNG)sdl_MNG);
			break;
		case SDL_MNG.TYPE_RS:
			applyRS((RS_MNG)sdl_MNG);
			break;
		case SDL_MNG.TYPE_AB:
			applyAB((AB_MNG)sdl_MNG);
			break;
		case SDL_MNG.TYPE_SB:
			applySB((SB_MNG)sdl_MNG);
			break;
		case SDL_MNG.TYPE_MT:
			applyMT((MT_MNG)sdl_MNG);
			break;
		case SDL_MNG.TYPE_DT:
			applyDT((DT_MNG)sdl_MNG);
			break;
		default:
				break;
		}
	}
	
	private Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection connection = null;
		try {
			Class.forName(databaseDTO.getJdbc_driver());
		} catch(ClassNotFoundException ex) {
			throw ex;
		}
		
		try {
			StringBuffer connStringBuffer = new StringBuffer();
			
			connStringBuffer.append(databaseDTO.getDriver_type());
			connStringBuffer.append('@');
			connStringBuffer.append(databaseDTO.getIp());
			connStringBuffer.append(':');
			connStringBuffer.append(databaseDTO.getPort());
			connStringBuffer.append(':');
			connStringBuffer.append(databaseDTO.getSid());
			
			connection =  DriverManager.getConnection(new String(connStringBuffer), 
					databaseDTO.getUser(), databaseDTO.getPassword());
	
			connection.setAutoCommit(true);
			
			return connection;
		} catch(SQLException ex) {
			throw ex;
		}
	}
	
	public void releaseConnection() throws SQLException {
		try {
			connection.close();
		} catch(SQLException ex) {
			throw ex;
		}
	}

	public void applyTM(TM_MNG tm_MNG) {
		SDL_Common.logger.info("[DbMNG-applyTM],경주번호:[" + tm_MNG.getRaceNumber() + "]\n");
		
		RaceDayOrdDTO raceDayOrdDTO = tm_MNG.getRaceDayOrdDTO();
		if (!raceDayOrdDTO.isAvailable()) {
			SDL_Common.logger.warn("[" + tm_MNG.getMeetCode() + "] 에 대한 금일 경주정보가 TBEB_RACE_DAY_ORD Table에 존재하지 않습니다. ");
			return;
		}

		String raceEndYn = "N";
		if (tm_MNG.getRestTime().equals("**"))
			raceEndYn = "Y";

		int count = dbDAO.selectTMExistCount(tm_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), 
				raceDayOrdDTO.getDayOrd(), tm_MNG.getRaceNumber());
		
		if (count == -1)
			return;
		
		String closingSaleTime = tm_MNG.getClosingSaleTime();
		if (closingSaleTime.equals("......")) {
			closingSaleTime = null;
		}
		
		if (count >= 1) {
			dbDAO.updateTM(tm_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), 
					raceDayOrdDTO.getDayOrd(), tm_MNG.getRaceNumber(), closingSaleTime, 
					tm_MNG.getCurrentTime(), tm_MNG.getRestTime(), raceEndYn, tm_MNG.getPerfNumber(), tm_MNG.getPerfType());
		} else if(count == 0) {
			dbDAO.insertTM(tm_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), 
					raceDayOrdDTO.getDayOrd(), tm_MNG.getRaceNumber(), closingSaleTime, 
					tm_MNG.getCurrentTime(), tm_MNG.getRestTime(), raceEndYn, tm_MNG.getPerfNumber(), tm_MNG.getPerfType());
		} else {
			return;
		}
	}
	
	public void applyPT(PT_MNG pt_MNG) {
		SDL_Common.logger.info("[DbMNG-applyPT],경주번호:[" + pt_MNG.getRaceNumber() + "]\n");
		
		RaceDayOrdDTO raceDayOrdDTO = pt_MNG.getRaceDayOrdDTO();
		if (!raceDayOrdDTO.isAvailable()) {
			SDL_Common.logger.warn("[" + pt_MNG.getMeetCode() + "] 에 대한 금일 경주정보가 TBEB_RACE_DAY_ORD Table에 존재하지 않습니다. ");
			return;
		}
		
		int poolTotal = Integer.parseInt(pt_MNG.getPoolTotal());
		String poolCDCode = SDL_Common.getPoolCDCode(pt_MNG.getPoolCD());
		
		int count = dbDAO.selectPTExistCount(pt_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), 
				raceDayOrdDTO.getDayOrd(), pt_MNG.getRaceNumber(), poolCDCode);
	
		if (count == -1)
			return;
		
		int _poolTotal = dbDAO.selectPTPoolTotal(pt_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), 
				raceDayOrdDTO.getDayOrd(), pt_MNG.getRaceNumber(), poolCDCode);
		
		int refund = dbDAO.selectPTRefund(pt_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), 
				raceDayOrdDTO.getDayOrd(), pt_MNG.getRaceNumber(), poolCDCode);
		
		if ((_poolTotal > poolTotal)) {
			refund = refund + (_poolTotal - poolTotal);
		}
		
		if (count >= 1) {
			dbDAO.updatePT(pt_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(),
					raceDayOrdDTO.getDayOrd(), pt_MNG.getRaceNumber(), poolCDCode, poolTotal, refund);
		} else {
			dbDAO.insertPT(pt_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(),
					raceDayOrdDTO.getDayOrd(), pt_MNG.getRaceNumber(), poolCDCode, poolTotal, refund);			
		}

		int poolTotalSeq = dbDAO.selectPTSPoolTotalSeq(pt_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), 
				raceDayOrdDTO.getDayOrd(), pt_MNG.getRaceNumber(), poolCDCode);
		
		if (poolTotalSeq == -1)
			poolTotalSeq = 1;
		else
			poolTotalSeq++;
		
		dbDAO.insertPTS(pt_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(),
				raceDayOrdDTO.getDayOrd(), pt_MNG.getRaceNumber(), poolCDCode, poolTotalSeq, poolTotal);

	}
	
	public void applyPB(PB_MNG pb_MNG) {
		SDL_Common.logger.info("[DbMNG-applyPB],경주번호:[" + pb_MNG.getRaceNumber() + "]\n");
		
		try {
			RaceDayOrdDTO raceDayOrdDTO = pb_MNG.getRaceDayOrdDTO();
			if (!raceDayOrdDTO.isAvailable()) {
				SDL_Common.logger.warn("[" + pb_MNG.getMeetCode() + "] 에 대한 금일 경주정보가 TBEB_RACE_DAY_ORD Table에 존재하지 않습니다. ");
				return;
			}
			
			int count = 0;
			
			int bigRecordCount = pb_MNG.getBigRecordCount();
			
			String poolCDCode = SDL_Common.getPoolCDCode(pb_MNG.getPoolCD());
			
			String runner1ST = null;
			String runner2ND = null;
			String runner3RD = "0";
			for (int i = 0; i < bigRecordCount; i++) {
				int racerCount = pb_MNG.getRacerCount(i);
				for (int j = 0; j < racerCount; j++) {
					if ((poolCDCode.equals("001")) || (poolCDCode.equals("002"))) {
						runner1ST = String.valueOf(j + 1);
						runner2ND = "0";
					} else {
						runner1ST = String.valueOf(i + 1);
						runner2ND = String.valueOf(j + 1);
					}
					count = dbDAO.selectPBExistCount(pb_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), raceDayOrdDTO.getDayOrd(), 
							pb_MNG.getRaceNumber(), poolCDCode, runner1ST, runner2ND, runner3RD);
					
					String oddsString = pb_MNG.getOdds(i, j);
					float odds = 0;
					if (!oddsString.equals("....")) {
						odds = Float.parseFloat(pb_MNG.getOdds(i, j));
					}
						
					odds /= 10;
					
					if (count == -1)
						continue;
					
					if (count >= 1) {
						dbDAO.updatePB(pb_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(),
								raceDayOrdDTO.getDayOrd(), pb_MNG.getRaceNumber(), poolCDCode, runner1ST, runner2ND, runner3RD, odds);
					} else {
						dbDAO.insertPB(pb_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(),
								raceDayOrdDTO.getDayOrd(), pb_MNG.getRaceNumber(), poolCDCode, runner1ST, runner2ND, runner3RD, odds);
					}
					
					int oddsSeq = dbDAO.selectPBSoddsSeq(pb_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), 
							raceDayOrdDTO.getDayOrd(), pb_MNG.getRaceNumber(), poolCDCode, runner1ST, runner2ND, runner3RD);
					
					if (oddsSeq == -1)
						oddsSeq = 1;
					else
						oddsSeq++;
					
					dbDAO.insertPBS(pb_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(),
							raceDayOrdDTO.getDayOrd(), pb_MNG.getRaceNumber(), poolCDCode, runner1ST, runner2ND, runner3RD, oddsSeq, odds);
				}
			}
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		}
	}
	
	public void applyTR(TR_MNG tr_MNG) {
		SDL_Common.logger.info("[DbMNG-applyTR],경주번호:[" + tr_MNG.getRaceNumber() + "]\n");
		
		RaceDayOrdDTO raceDayOrdDTO = tr_MNG.getRaceDayOrdDTO();
		if (!raceDayOrdDTO.isAvailable()) {
			SDL_Common.logger.warn("[" + tr_MNG.getMeetCode() + "] 에 대한 금일 경주정보가 TBEB_RACE_DAY_ORD Table에 존재하지 않습니다. ");
			return;
		}
		
		String poolCDCode = SDL_Common.getPoolCDCode(tr_MNG.getPoolCD());
		
		int bigRecordCount = tr_MNG.getBigRecordCount();
		String[] runnerArray = null;
		int count = 0;
		
		for (int i = 0; i < bigRecordCount; i++) {
			runnerArray = tr_MNG.getRunnerArray(i);
			
			count = dbDAO.selectTRExistCount(tr_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), raceDayOrdDTO.getDayOrd(), 
					tr_MNG.getRaceNumber(), poolCDCode, runnerArray[0], runnerArray[1], runnerArray[2]);

			String oddsString = tr_MNG.getOdds(i);

			float odds = 0;
			if (!oddsString.equals("....")) {
				odds = Float.parseFloat(tr_MNG.getOdds(i));
			}

			odds /= 10;
			
			if (count == -1)
				continue;
			
			if (count >= 1) {
				dbDAO.updateTR(tr_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(),
						raceDayOrdDTO.getDayOrd(), tr_MNG.getRaceNumber(), poolCDCode, runnerArray[0], runnerArray[1], runnerArray[2], odds);
			} else {
				dbDAO.insertTR(tr_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(),
						raceDayOrdDTO.getDayOrd(), tr_MNG.getRaceNumber(), poolCDCode, runnerArray[0], runnerArray[1], runnerArray[2], odds);
			}

			int oddsSeq = dbDAO.selectTRSoddsSeq(tr_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), 
					raceDayOrdDTO.getDayOrd(), tr_MNG.getRaceNumber(), poolCDCode, runnerArray[0], runnerArray[1], runnerArray[2]);

			if (oddsSeq == -1)
				oddsSeq = 1;
			else
				oddsSeq++;
			
			dbDAO.insertTRS(tr_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(),
					raceDayOrdDTO.getDayOrd(), tr_MNG.getRaceNumber(), poolCDCode, runnerArray[0], runnerArray[1], runnerArray[2], oddsSeq, odds);
		}
	}
	
	public void applyFN(FN_MNG fn_MNG) {
		SDL_Common.logger.info("[DbMNG-applyFN],경주번호:[" + fn_MNG.getRaceNumber() + "]\n");
		
		RaceDayOrdDTO raceDayOrdDTO = fn_MNG.getRaceDayOrdDTO();
		if (!raceDayOrdDTO.isAvailable()) {
			SDL_Common.logger.warn("[" + fn_MNG.getMeetCode() + "] 에 대한 금일 경주정보가 TBEB_RACE_DAY_ORD Table에 존재하지 않습니다. ");
			return;
		}

		int rankInfoCount = fn_MNG.getLinkedRankInfoCount();
		
		/*
		 * 경주취소인 경우
		 */
		if (rankInfoCount == 1) {
			int count = dbDAO.selectFNExistCount(fn_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(),
					raceDayOrdDTO.getDayOrd(), fn_MNG.getRaceNumber(), "1", 1);
			if (count >= 1) {
				dbDAO.updateFN(fn_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), raceDayOrdDTO.getDayOrd(), 
						fn_MNG.getRaceNumber(), "1", 1, 9);
			} else {
				dbDAO.insertFN(fn_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), raceDayOrdDTO.getDayOrd(), 
						fn_MNG.getRaceNumber(), "1", 1, 9);
			}
		}
		
		for (int i = 0; i < rankInfoCount; i++) {
			int racerCount = fn_MNG.getRacerCount(i);
			String rank = String.valueOf(fn_MNG.getRank(i));
			if (rank.equals("0")) {
				rank = "F";
			}
			String[] racerNumber = fn_MNG.getRacerNumber(i);

			for (int j = 0; j < racerCount; j++) {
				int count = dbDAO.selectFNExistCount(fn_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(),
						raceDayOrdDTO.getDayOrd(), fn_MNG.getRaceNumber(), rank, j + 1);
				if (count == -1)
					continue;
				
				int _racerNumber = Integer.parseInt(racerNumber[j]);
				
				if (count >= 1) {
					dbDAO.updateFN(fn_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), raceDayOrdDTO.getDayOrd(), 
							fn_MNG.getRaceNumber(), rank, j + 1, _racerNumber);
				} else {
					dbDAO.insertFN(fn_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), raceDayOrdDTO.getDayOrd(), 
							fn_MNG.getRaceNumber(), rank, j + 1, _racerNumber);
				}
			}
		}
	}
	
	public void applyRS(RS_MNG rs_MNG) {
		SDL_Common.logger.info("[DbMNG-applyRS],경주번호:[" + rs_MNG.getRaceNumber() + "]\n");
		
		RaceDayOrdDTO raceDayOrdDTO = rs_MNG.getRaceDayOrdDTO();
		if (!raceDayOrdDTO.isAvailable()) {
			SDL_Common.logger.warn("[" + rs_MNG.getMeetCode() + "] 에 대한 금일 경주정보가 TBEB_RACE_DAY_ORD Table에 존재하지 않습니다. ");
			return;
		}
		
		String poolCDCode = SDL_Common.getPoolCDCode(rs_MNG.getPoolCD());
		
		int winRacerCount = rs_MNG.getWinRacerCount();
		for (int i = 0; i < winRacerCount; i++) {
			String[] runners = rs_MNG.getRunners(i);
			
			int count = dbDAO.selectRSExistCount(rs_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(),
					raceDayOrdDTO.getDayOrd(), rs_MNG.getRaceNumber(), poolCDCode, i + 1);
			if (count == -1)
				continue;
			
			String payoff = rs_MNG.getPayoff(i);
			float _payoff = Float.parseFloat(payoff);
			_payoff /= 10;
			String status = rs_MNG.getStatus(i);
			
			if (count >= 1) {
				dbDAO.updateRS(rs_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), raceDayOrdDTO.getDayOrd(), 
						rs_MNG.getRaceNumber(), poolCDCode, i + 1, runners[0], runners[1], runners[2], _payoff, status);
			} else {
				dbDAO.insertRS(rs_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), raceDayOrdDTO.getDayOrd(), 
						rs_MNG.getRaceNumber(), poolCDCode, i + 1, runners[0], runners[1], runners[2], _payoff, status);
			}
		}
	}
	
	public void applyAB(AB_MNG ab_MNG) {
		SDL_Common.logger.info("[DbMNG-applyAB],경주번호:[" + ab_MNG.getRaceNumber() + "]\n");
	}
	
	public void applySB(SB_MNG sb_MNG) {
		SDL_Common.logger.info("[DbMNG-applySB],경주번호:[" + sb_MNG.getRaceNumber() + "]\n");
	}
	
	public void applyMT(MT_MNG mt_MNG) {
		SDL_Common.logger.info("[DbMNG-applyMT],경주번호:[" + mt_MNG.getRaceNumber() + "]\n");
	}
	
	public void applyDT(DT_MNG dt_MNG) {
		SDL_Common.logger.info("[DbMNG-applyDT],경주번호:[" + dt_MNG.getRaceNumber() + "]\n");
		
		RaceDayOrdDTO raceDayOrdDTO = dt_MNG.getRaceDayOrdDTO();
		if (!raceDayOrdDTO.isAvailable()) {
			SDL_Common.logger.warn("[" + dt_MNG.getMeetCode() + "] 에 대한 금일 경주정보가 TBEB_RACE_DAY_ORD Table에 존재하지 않습니다. ");
			return;
		}
		
		int divTotal = Integer.parseInt(dt_MNG.getDivTotal());

		int count = dbDAO.selectDTExistCount(dt_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), 
				raceDayOrdDTO.getDayOrd(), dt_MNG.getRaceNumber(), dt_MNG.getAsscNumber(), dt_MNG.getCommNumber(), dt_MNG.getDivNumber());
		
		if (count >= 1) {
			int _divTotal = dbDAO.selectDTDivTotal(dt_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), 
					raceDayOrdDTO.getDayOrd(), dt_MNG.getRaceNumber(), dt_MNG.getAsscNumber(), dt_MNG.getCommNumber(), dt_MNG.getDivNumber());
			int _refund = dbDAO.selectDTRefund(dt_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), 
					raceDayOrdDTO.getDayOrd(), dt_MNG.getRaceNumber(), dt_MNG.getAsscNumber(), dt_MNG.getCommNumber(), dt_MNG.getDivNumber());
			if (_divTotal > divTotal) {
				_refund = _refund + (_divTotal - divTotal);
			}
			
			dbDAO.updateDT(dt_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), raceDayOrdDTO.getDayOrd(), dt_MNG.getRaceNumber(),
					dt_MNG.getAsscNumber(), dt_MNG.getCommNumber(), dt_MNG.getDivNumber(), divTotal, _refund);

		} else {
			dbDAO.insertDT(dt_MNG.getMeetCode(), raceDayOrdDTO.getStndYear(), raceDayOrdDTO.getTms(), raceDayOrdDTO.getDayOrd(), dt_MNG.getRaceNumber(),
					dt_MNG.getAsscNumber(), dt_MNG.getCommNumber(), dt_MNG.getDivNumber(), divTotal, 0);
		}
	}
	
	public RaceDayOrdDTO getRaceDayOrdDTO(String meetCode) {
		return dbDAO.selectRaceDayOrdDTO(meetCode);
	}

	public void initDT(String meetCode, String stndYear, int tms, int day_ord, int race_no) {
		SDL_Common.logger.info("[DbMNG-initDT]" + meetCode + ", " + stndYear + ", " + tms + ", " + day_ord + "\n");
		dbDAO.deleteDT(meetCode, stndYear, tms, day_ord, race_no);
	}
	
	public void initPT(String meetCode, String stndYear, int tms, int day_ord, int race_no) {
		SDL_Common.logger.info("[DbMNG-initPT]" + meetCode + ", " + stndYear + ", " + tms + ", " + day_ord + "\n");
		dbDAO.deletePT(meetCode, stndYear, tms, day_ord, race_no);
	}
	
	public int getRaceCount(String meetCode, String stndYear, int tms, int day_ord) {
		 return dbDAO.selectRaceCnt(meetCode, stndYear, tms, day_ord);
	}
}
