package org.motorboat.db;

import java.sql.*;

import org.motorboat.*;
import org.motorboat.mng.*;
import org.motorboat.dto.*;
import org.motorboat.common.*;
import org.motorboat.existing.*;

/**
 * 데이터베이스와 데이터를 연계하는데 있어서 직접적으로 SQL문을 핸들링하며 모든 SQL 핸들링 구문은 DbDAO 클래스에서 담당한다.
 * 
 * @author 김원겸 대상정보기술
 *
 */
public class DbDAO {
	
	private ToteRead toteRead = null;
	private Connection connection = null;
	
	/**
	 * Exception이 발생하였을 경우 printStackTrace()함수를 이용하여 Exception의 
	 * 구체적 경로를 기본출력으로 출력하기 위한 함수
	 * 
	 * @param ex 디스플레이하고자 하는 Exception 객
	 */
	private void displayException(Exception ex) {
		ex.printStackTrace();
	}

	/**
	 * ToteRead 클래스와 Connection 클래스를 입력받아 DbDAO 객체를 생성하기 위한 생성자이다.
	 * 
	 * @param toteRead ToteRead 객체
	 * @param connection Connection 객
	 */
	public DbDAO(ToteRead toteRead, Connection connection) {
		this.toteRead = toteRead;
		this.connection = connection;
	}

	/**
	 * meet_code에 설정된 meet code를 이용하여 현재날자의 STND_YEAR, TMS, DAY_ORD를 추출하여
	 * RaceDayOrdDTO 객체를 이용하여 반환함  
	 * 
	 * @param meet_code meet code
	 * @return RaceDayOrdDTO
	 */
	public RaceDayOrdDTO selectRaceDayOrdDTO(String meet_code) {
		
		if (!toteRead.isSerailDeviceMode()) {
			return toteRead.getRaceDayOrdDTO();
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		RaceDayOrdDTO raceDayOrdDTO = new RaceDayOrdDTO();
		try {
			sqlBuffer.append("select	STND_YEAR,									");
			sqlBuffer.append("			TMS,                                        ");
			sqlBuffer.append("			DAY_ORD                                     ");
			sqlBuffer.append("from  	tbeb_race_day_ord							");
			sqlBuffer.append("where		race_day = to_char(sysdate, 'yyyymmdd')     ");
			sqlBuffer.append("and		mbr_cd = ?									");
			
			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();
			
			ps.setString(1, meet_code);
			
			
			rs = ps.executeQuery();

			if (rs.next()) {
				raceDayOrdDTO.setIsAvailable(true);
				raceDayOrdDTO.setStndYear(SDL_Common.replaceNull(rs.getString("STND_YEAR"), ""));
				raceDayOrdDTO.setTms(rs.getInt("TMS"));
				raceDayOrdDTO.setDayOrd(rs.getInt("DAY_ORD"));
			}
			
			return raceDayOrdDTO;
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		} finally {
			try {
				rs.close();
			} catch(Exception ex) { }
			try {
				ps.close();
			} catch(Exception ex) { }
		}
		return raceDayOrdDTO;
	}
	
	/**
	 * tbes_sdl_tm table에서 primary key와 일치하는 TM 데이터의 갯수를 반환함 
	 * 
	 * @param meet_cd meet번호
	 * @param stnd_year 기준년도
	 * @param tms TMS번호
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @return TM데이터 갯수
	 */
	public int selectTMExistCount(String meet_cd, String stnd_year, int tms, int day_ord, String race_no) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("select	count(*) as COUNT		");
			sqlBuffer.append("from		usrbm.tbes_sdl_tm@USRBM_LINK       ");
			sqlBuffer.append("where		meet_cd = ?             ");
			sqlBuffer.append("and		stnd_year = ?       	");
			sqlBuffer.append("and		tms = ?        	    	");
			sqlBuffer.append("and		day_ord = ?     	    ");
			sqlBuffer.append("and		race_no = ?         	");
			
			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();
			
			ps.setString(1, meet_cd);
			ps.setString(2, stnd_year);
			ps.setInt(3, tms);
			ps.setInt(4, day_ord);
			ps.setString(5, race_no);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("COUNT");
			} else {
				return -1;
			}
			
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
			return -1;
		} finally {
			try {
				rs.close();
			} catch(Exception ex) { }
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}

	/**
	 * TM data 를 tbes_sdl_tm table에 입력함 
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param post_dt 발매마감시간
	 * @param curr_dt 현재시간
	 * @param rest_mm 잔여시간
	 * @param race_end_yn 경주마감여부
	 * @param perf_no Performance No
	 * @param perf_type Performance Type
	 */
	public void insertTM(String meet_cd, String stnd_year, int tms,
		int day_ord, String race_no, String post_dt, String curr_dt, String rest_mm,
		String race_end_yn, String perf_no, String perf_type) {
		
		PreparedStatement ps = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("insert into usrbm.tbes_sdl_tm@USRBM_LINK													");
			sqlBuffer.append("(meet_cd, stnd_year, tms, day_ord, race_no, post_dt, curr_dt, rest_mm, race_end_yn,		");
			sqlBuffer.append("race_day, perf_no, perf_type, inst_id, inst_dt                                      		");
			sqlBuffer.append("values(?, ?, ?, ?, ?,                                                                     ");
			
			if (post_dt == null)
				sqlBuffer.append("null,                       															");
			else
				sqlBuffer.append("to_date(concat(to_char(sysdate, 'yyyymmdd'), ?), 'yyyymmddhh24miss'),                   ");
			sqlBuffer.append("to_date(concat(to_char(sysdate, 'yyyymmdd'), ?), 'yyyymmddhh24miss'),                       ");
			sqlBuffer.append("?, ?, to_char(sysdate, 'yyyymmdd'), ?, ?,                                                 ");
			sqlBuffer.append("'SDL', sysdate)                                                                           ");
			
			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();
			ps.setString(1, meet_cd);
			ps.setString(2, stnd_year);
			ps.setInt(3, tms);
			ps.setInt(4, day_ord);
			ps.setString(5, race_no);
			if (post_dt != null) {
				ps.setString(6, post_dt);
				ps.setString(7, curr_dt);
				ps.setString(8, rest_mm);
				ps.setString(9, race_end_yn);
				ps.setString(10, perf_no);
				ps.setString(11, perf_type);
			} else {
				ps.setString(6, curr_dt);
				ps.setString(7, rest_mm);
				ps.setString(8, race_end_yn);  // *
				ps.setString(9, perf_no);
				ps.setString(10, perf_type);
			}
			
			ps.executeUpdate();
			
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		} finally {
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * TM data를 tbes_sdl_tm table에 업데이트함
	 * 
	 * @param meet_cd Meet Code 
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param post_dt 발매마감시간
	 * @param curr_dt 현재시간
	 * @param rest_mm 잔여시간
	 * @param race_end_yn 경주마감여부
	 * @param perf_no Performance No
	 * @param perf_type Performance Type
	 */
	public void updateTM(String meet_cd, String stnd_year, int tms,
			int day_ord, String race_no, String post_dt, String curr_dt, String rest_mm,
			String race_end_yn, String perf_no, String perf_type) {
		
		PreparedStatement ps = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			
			sqlBuffer.append("update usrbm.tbes_sdl_tm@USRBM_LINK													");
			sqlBuffer.append("set post_dt = to_date(concat(to_char(sysdate, 'yyyymmdd'), ?), 'yyyymmddhh24miss'),   ");
			sqlBuffer.append("curr_dt = to_date(concat(to_char(sysdate, 'yyyymmdd'), ?), 'yyyymmddhh24miss'),       ");
			sqlBuffer.append("rest_mm = ?, race_end_yn = ?, race_day = to_char(sysdate, 'yyyymmdd'),				");
			sqlBuffer.append("perf_no = ?, perf_type = ?, updt_id = 'SDL', updt_dt = sysdate						");
			sqlBuffer.append("where meet_cd = ?                                                                     ");
			sqlBuffer.append("and stnd_year = ?                                                                     ");
			sqlBuffer.append("and tms = ?                                                                           ");
			sqlBuffer.append("and day_ord = ?                                                                       ");
			sqlBuffer.append("and race_no = ?                                                                       ");
			
			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();

			ps.setString(1, post_dt);
			ps.setString(2, curr_dt);
			ps.setString(3, rest_mm);
			ps.setString(4, race_end_yn);
			ps.setString(5, perf_no);
			ps.setString(6, perf_type);
			ps.setString(7, meet_cd);
			ps.setString(8, stnd_year);
			ps.setInt(9, tms);
			ps.setInt(10, day_ord);
			ps.setString(11, race_no);
			
			ps.executeUpdate();
			
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		} finally {
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * primary key가 일치하는 PT data의 갯수를 반환함
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param pool_cd 승식
	 * @return PT data 갯수
	 */
	public int selectPTExistCount(String meet_cd, String stnd_year, int tms, int day_ord, String race_no, String pool_cd) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("select	count(*) as COUNT		");
			sqlBuffer.append("from		usrbm.tbes_sdl_pt@USRBM_LINK ");
			sqlBuffer.append("where		meet_cd = ?             ");
			sqlBuffer.append("and		stnd_year = ?       ");
			sqlBuffer.append("and		tms = ?             ");
			sqlBuffer.append("and		day_ord = ?         ");
			sqlBuffer.append("and		race_no = ?         ");
			sqlBuffer.append("and		pool_cd = ?         ");
			
			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();
			
			ps.setString(1, meet_cd);
			ps.setString(2, stnd_year);
			ps.setInt(3, tms);
			ps.setInt(4, day_ord);
			ps.setString(5, race_no);
			ps.setString(6, pool_cd);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("COUNT");
			} else {
				return -1;
			}
			
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
			return -1;
		} finally {
			try {
				rs.close();
			} catch(Exception ex) { }
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * primary key가 일치하는 PT data의 Total Amount 를 반환함
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param pool_cd 승
	 * @return Total Amount
	 */
	public int selectPTPoolTotal(String meet_cd, String stnd_year, int tms, int day_ord, String race_no, String pool_cd) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("select	pool_total as POOL_TOTAL	");
			sqlBuffer.append("from		usrbm.tbes_sdl_pt@USRBM_LINK           ");
			sqlBuffer.append("where		meet_cd = ?                 ");
			sqlBuffer.append("and		stnd_year = ?           	");
			sqlBuffer.append("and		tms = ?                	 	");
			sqlBuffer.append("and		day_ord = ?            	 	");
			sqlBuffer.append("and		race_no = ?            	 	");
			sqlBuffer.append("and		pool_cd = ?             	");
			
			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();
			
			ps.setString(1, meet_cd);
			ps.setString(2, stnd_year);
			ps.setInt(3, tms);
			ps.setInt(4, day_ord);
			ps.setString(5, race_no);
			ps.setString(6, pool_cd);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("POOL_TOTAL");
			} else {
				return -1;
			}
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
			return -1;
		} finally {
			try {
				rs.close();
			} catch(Exception ex) { }
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * primary key가 일치하는 PT data의 환불액을 반환
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param pool_cd 승식
	 * @return 환불액
	 */
	public int selectPTRefund(String meet_cd, String stnd_year, int tms, int day_ord, String race_no, String pool_cd) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("select	refund as REFUND			");
			sqlBuffer.append("from		usrbm.tbes_sdl_pt@USRBM_LINK           ");
			sqlBuffer.append("where		meet_cd = ?                 ");
			sqlBuffer.append("and		stnd_year = ?           	");
			sqlBuffer.append("and		tms = ?                	 	");
			sqlBuffer.append("and		day_ord = ?            	 	");
			sqlBuffer.append("and		race_no = ?            	 	");
			sqlBuffer.append("and		pool_cd = ?             	");
			
			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();
			
			ps.setString(1, meet_cd);
			ps.setString(2, stnd_year);
			ps.setInt(3, tms);
			ps.setInt(4, day_ord);
			ps.setString(5, race_no);
			ps.setString(6, pool_cd);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("REFUND");
			} else {
				return 0;
			}
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
			return 0;
		} finally {
			try {
				rs.close();
			} catch(Exception ex) { }
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}

	/**
	 * PT 데이터를 tbes_sdl_pt table에 입력함
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param pool_cd 승식
	 * @param pool_total Total Amount
	 * @param refund 환불액
	 */
	public void insertPT(String meet_cd, String stnd_year, int tms,
			int day_ord, String race_no, String pool_cd, int pool_total, int refund) {
		
		PreparedStatement ps = null;
		StringBuffer sqlBuffer = new StringBuffer();

		try {
			sqlBuffer.append("insert into usrbm.tbes_sdl_pt@USRBM_LINK														");
			sqlBuffer.append("(meet_cd, stnd_year, tms, day_ord, race_no, pool_cd, pool_total, refund, inst_id, inst_dt)    ");
			sqlBuffer.append("values (?, ?, ?, ?, ?, ?, ?, ?, 'SDL', sysdate)                                               ");

			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();

			ps.setString(1, meet_cd);
			ps.setString(2, stnd_year);
			ps.setInt(3, tms);
			ps.setInt(4, day_ord);
			ps.setString(5, race_no);
			ps.setString(6, pool_cd);
			ps.setInt(7, pool_total);
			ps.setInt(8, refund);

			ps.executeUpdate();
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		} finally {
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * tbes_sdl_pts table의 primary key가 일치하는 PT data 중 가장 큰값의 pool_total_seq 값을 반환함 
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param pool_cd 승식
	 * @return pool_total_seq
	 */
	public int selectPTSPoolTotalSeq(String meet_cd, String stnd_year, int tms, int day_ord, String race_no, String pool_cd) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("select	max(pool_total_seq) as POOL_TOTAL_SEQ		");
			sqlBuffer.append("from		usrbm.tbes_sdl_pts@USRBM_LINK               ");
			sqlBuffer.append("where		meet_cd = ?                                 ");
			sqlBuffer.append("and		stnd_year = ?                               ");
			sqlBuffer.append("and		tms = ?                                     ");
			sqlBuffer.append("and		day_ord = ?                                 ");
			sqlBuffer.append("and		race_no = ?                                 ");
			sqlBuffer.append("and		pool_cd = ?                                 ");
			
			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();
			
			ps.setString(1, meet_cd);
			ps.setString(2, stnd_year);
			ps.setInt(3, tms);
			ps.setInt(4, day_ord);
			ps.setString(5, race_no);
			ps.setString(6, pool_cd);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("POOL_TOTAL_SEQ");
			} else {
				return -1;
			}
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
			return -1;
		} finally {
			try {
				rs.close();
			} catch(Exception ex) { }
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * PT data를 tbes_sdl_pts table에 입력함
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param pool_cd 승식
	 * @param pool_total_seq 발매총액순서
	 * @param pool_total 발매총액수신시각
	 */
	public void insertPTS(String meet_cd, String stnd_year, int tms,
			int day_ord, String race_no, String pool_cd, int pool_total_seq, int pool_total) {
		
		PreparedStatement ps = null;
		StringBuffer sqlBuffer = new StringBuffer();

		try {
			sqlBuffer.append("insert into usrbm.tbes_sdl_pts@USRBM_LINK																		");
			sqlBuffer.append("(meet_cd, stnd_year, tms, day_ord, race_no, pool_cd, pool_total_seq, recv_dt, pool_total, inst_id, inst_dt) 	");
			sqlBuffer.append("values (?, ?, ?, ?, ?, ?, ?, sysdate, ?, 'SDL', sysdate)                                   	               	");

			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();

			ps.setString(1, meet_cd);
			ps.setString(2, stnd_year);
			ps.setInt(3, tms);
			ps.setInt(4, day_ord);
			ps.setString(5, race_no);
			ps.setString(6, pool_cd);
			ps.setInt(7, pool_total_seq);
			ps.setInt(8, pool_total);

			ps.executeUpdate();
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		} finally {
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * PT data를 pbes_sdl_ps table에 업데이트함
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param pool_cd 승식
	 * @param pool_total 발매총액순서
	 * @param refund 환불액
	 */
	public void updatePT(String meet_cd, String stnd_year, int tms,
			int day_ord, String race_no, String pool_cd, int pool_total, int refund) {
		
		PreparedStatement ps = null;
		StringBuffer sqlBuffer = new StringBuffer();

		try {
			sqlBuffer.append("update usrbm.tbes_sdl_pt@USRBM_LINK	");
			sqlBuffer.append("set pool_total = ?,       ");
			sqlBuffer.append("refund = ?,               ");
			sqlBuffer.append("updt_id = 'SDL',          ");
			sqlBuffer.append("updt_dt = sysdate         ");
			sqlBuffer.append("where meet_cd = ?         ");
			sqlBuffer.append("and stnd_year = ?         ");
			sqlBuffer.append("and tms = ?               ");
			sqlBuffer.append("and day_ord = ?           ");
			sqlBuffer.append("and race_no = ?           ");
			sqlBuffer.append("and pool_cd = ?           ");

			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();

			ps.setInt(1, pool_total);
			ps.setInt(2, refund);
			ps.setString(3, meet_cd);
			ps.setString(4, stnd_year);
			ps.setInt(5, tms);
			ps.setInt(6, day_ord);
			ps.setString(7, race_no);
			ps.setString(8, pool_cd);

			ps.executeUpdate();
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		} finally {
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * tbes_sdl_pb table의 primary key가 일치하는 데이터의 갯수를 반환함
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param pool_cd 승식
	 * @param runner1ST 선수1
	 * @param runner2ND 선수2
	 * @param runner3RD 선수3
	 * @return
	 */
	public int selectPBExistCount(String meet_cd, String stnd_year, int tms, int day_ord, String race_no, String pool_cd, 
			String runner1ST, String runner2ND, String runner3RD) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("select	count(*) as COUNT 		");
			sqlBuffer.append("from		usrbm.tbes_sdl_pb@USRBM_LINK       ");
			sqlBuffer.append("where   	meet_cd = ?             ");
			sqlBuffer.append("and		stnd_year = ?           ");
			sqlBuffer.append("and		tms = ?                 ");
			sqlBuffer.append("and		day_ord = ?             ");
			sqlBuffer.append("and		race_no = ?             ");
			sqlBuffer.append("and		pool_cd = ?             ");
			sqlBuffer.append("and		runner_1st = ?          ");
			sqlBuffer.append("and		runner_2nd = ?          ");
			sqlBuffer.append("and		runner_3rd = ?          ");
			
			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();
			
			int psIndex = 1;
			ps.setString(psIndex++, meet_cd);
			ps.setString(psIndex++, stnd_year);
			ps.setInt(psIndex++, tms);
			ps.setInt(psIndex++, day_ord);
			ps.setString(psIndex++, race_no);
			ps.setString(psIndex++, pool_cd);
			ps.setString(psIndex++, runner1ST);
			ps.setString(psIndex++, runner2ND);
			ps.setString(psIndex++, runner3RD);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("COUNT");
			} else {
				return -1;
			}

		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
			return -1;
		} finally {
			try {
				rs.close();
			} catch(Exception ex) { }
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * tbes_sdl_pbs table에서 primary key가 일치하는 data 중 odds_seq가 가장 큰 값을 반환함
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param pool_cd 승식
	 * @param runner_1st 선수1
	 * @param runner_2nd 선수2
	 * @param runner_3rd 선수3
	 * @return 가장 큰 odds_seq 값
	 */
	public int selectPBSoddsSeq(String meet_cd, String stnd_year, int tms, int day_ord, String race_no, String pool_cd, 
			String runner_1st, String runner_2nd, String runner_3rd) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("select	max(odds_seq) as ODDS_SEQ					");
			sqlBuffer.append("from		usrbm.tbes_sdl_pbs@USRBM_LINK               ");
			sqlBuffer.append("where		meet_cd = ?                                 ");
			sqlBuffer.append("and		stnd_year = ?                               ");
			sqlBuffer.append("and		tms = ?                                     ");
			sqlBuffer.append("and		day_ord = ?                                 ");
			sqlBuffer.append("and		race_no = ?                                 ");
			sqlBuffer.append("and		pool_cd = ?                                 ");
			sqlBuffer.append("and		runner_1st = ?                              ");
			if (!runner_2nd.equals("0"))
				sqlBuffer.append("and		runner_2nd = ?                              ");
			if (!runner_3rd.equals("0"))
				sqlBuffer.append("and		runner_3rd = ?                              ");
			
			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();
			
			int psIndex = 1;
			ps.setString(psIndex++, meet_cd);
			ps.setString(psIndex++, stnd_year);
			ps.setInt(psIndex++, tms);
			ps.setInt(psIndex++, day_ord);
			ps.setString(psIndex++, race_no);
			ps.setString(psIndex++, pool_cd);
			ps.setString(psIndex++, runner_1st);
			if (!runner_2nd.equals("0"))
				ps.setString(psIndex++, runner_2nd);
			if (!runner_3rd.equals("0"))
				ps.setString(psIndex++, runner_3rd);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("ODDS_SEQ");
			} else {
				return -1;
			}
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
			return -1;
		} finally {
			try {
				rs.close();
			} catch(Exception ex) { }
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * PB 데이터를 tbes_sdl_pb table에 입력함
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param pool_cd 승식
	 * @param runner1ST 선수1
	 * @param runner2ND 선수2
	 * @param runner3RD 선수3
	 * @param odds 배당율
	 */
	public void insertPB(String meet_cd, String stnd_year, int tms,
			int day_ord, String race_no, String pool_cd, String runner1ST, String runner2ND, String runner3RD, float odds) {
		
		PreparedStatement ps = null;
		StringBuffer sqlBuffer = new StringBuffer();

		try {
			sqlBuffer.append("insert into usrbm.tbes_sdl_pb@USRBM_LINK						");
			sqlBuffer.append("(meet_cd, stnd_year, tms, day_ord, race_no, pool_cd, runner_1st, ");
			sqlBuffer.append("runner_2nd, ");
			sqlBuffer.append("runner_3rd, ");
			sqlBuffer.append("odds, inst_id, inst_dt)         ");
			sqlBuffer.append("values (?, ?, ?, ?, ?, ?, ?, ");
			sqlBuffer.append("?, ");
			sqlBuffer.append("?, ");
			sqlBuffer.append("?, 'SDL', sysdate)");

			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();
			
			int psIndex = 1;
			ps.setString(psIndex++, meet_cd);
			ps.setString(psIndex++, stnd_year);
			ps.setInt(psIndex++, tms);
			ps.setInt(psIndex++, day_ord);
			ps.setString(psIndex++, race_no);
			ps.setString(psIndex++, pool_cd);
			ps.setString(psIndex++, runner1ST);
			ps.setString(psIndex++, runner2ND);
			ps.setString(psIndex++, runner3RD);
			ps.setFloat(psIndex++, odds);
			
			ps.executeUpdate();
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		} finally {
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * PB data를 tbes_sdl_pbs table에 입력함
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param pool_cd 승식
	 * @param runner1ST 선수1
	 * @param runner2ND 선수2
	 * @param runner3RD 선수3
	 * @param odds_seq 배당율 순서
	 * @param odds 배당율
	 */
	public void insertPBS(String meet_cd, String stnd_year, int tms,
			int day_ord, String race_no, String pool_cd, String runner1ST, String runner2ND, String runner3RD, int odds_seq, float odds) {
		
		PreparedStatement ps = null;
		StringBuffer sqlBuffer = new StringBuffer();

		try {
			sqlBuffer.append("insert into usrbm.tbes_sdl_pbs@USRBM_LINK					");
			sqlBuffer.append("(meet_cd, stnd_year, tms, day_ord, race_no, pool_cd, runner_1st, ");
			sqlBuffer.append("runner_2nd, ");
			sqlBuffer.append("runner_3rd, ");
			sqlBuffer.append("odds_seq, recv_dt, odds, inst_id, inst_dt) ");
			sqlBuffer.append("values (?, ?, ?, ?, ?, ?, ?, ");
			sqlBuffer.append("?, ");
			sqlBuffer.append("?, ");
			sqlBuffer.append("?, sysdate, ?, 'SDL', sysdate) ");

			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();

			int psIndex = 1;
			ps.setString(psIndex++, meet_cd);
			ps.setString(psIndex++, stnd_year);
			ps.setInt(psIndex++, tms);
			ps.setInt(psIndex++, day_ord);
			ps.setString(psIndex++, race_no);
			ps.setString(psIndex++, pool_cd);
			ps.setString(psIndex++, runner1ST);
			ps.setString(psIndex++, runner2ND);
			ps.setString(psIndex++, runner3RD);
			ps.setInt(psIndex++, odds_seq);
			ps.setFloat(psIndex++, odds);

			ps.executeUpdate();
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		} finally {
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * PB data를 tbes_sdl_pb table에 업데이트함
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param pool_cd 승식
	 * @param runner1ST 선수1
	 * @param runner2ND 선수2
	 * @param runner3RD 선수3
	 * @param odds 배당율
	 */
	public void updatePB(String meet_cd, String stnd_year, int tms,
			int day_ord, String race_no, String pool_cd, String runner1ST, String runner2ND, String runner3RD, float odds) {
		
		PreparedStatement ps = null;
		StringBuffer sqlBuffer = new StringBuffer();

		try {
			sqlBuffer.append("update usrbm.tbes_sdl_pb@USRBM_LINK	");
			sqlBuffer.append("set odds = ?,       		");
			sqlBuffer.append("updt_id = 'SDL',          ");
			sqlBuffer.append("updt_dt = sysdate         ");
			sqlBuffer.append("where meet_cd = ?         ");
			sqlBuffer.append("and stnd_year = ?         ");
			sqlBuffer.append("and tms = ?               ");
			sqlBuffer.append("and day_ord = ?           ");
			sqlBuffer.append("and race_no = ?           ");
			sqlBuffer.append("and pool_cd = ?           ");
			sqlBuffer.append("and runner_1st = ?        ");
			sqlBuffer.append("and runner_2nd = ?        ");
			sqlBuffer.append("and runner_3rd = ?        ");

			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();

			int psIndex = 1;
			ps.setFloat(psIndex++, odds);
			ps.setString(psIndex++, meet_cd);
			ps.setString(psIndex++, stnd_year);
			ps.setInt(psIndex++, tms);
			ps.setInt(psIndex++, day_ord);
			ps.setString(psIndex++, race_no);
			ps.setString(psIndex++, pool_cd);
			ps.setString(psIndex++, runner1ST);
			ps.setString(psIndex++, runner2ND);
			ps.setString(psIndex++, runner3RD);

			ps.executeUpdate();
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		} finally {
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * 기본값과 일치하는 중간배당율 데이터의 갯수를 반환한다.
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param pool_cd 승식
	 * @param runner1ST 선수1
	 * @param runner2ND 선수2
	 * @param runner3RD 선수3
	 * @return 기본값과 일치하는 중간배당율 데이터 갯수
	 */
	public int selectTRExistCount(String meet_cd, String stnd_year, int tms, int day_ord, String race_no, String pool_cd, 
			String runner1ST, String runner2ND, String runner3RD) {
		
		
		return selectPBExistCount(meet_cd, stnd_year, tms, day_ord, race_no, pool_cd, runner1ST, runner2ND, runner3RD);

	}
	
	/**
	 * tbes_sdl_pbs table에서 primary key가 일치하는 data 중 odds_seq가 가장 큰 값을 반환함
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param pool_cd 승식
	 * @param runner_1st 선수1
	 * @param runner_2nd 선수2
	 * @param runner_3rd 선수3
	 * @return 가장 큰 Odds_seq 값
	 */
	public int selectTRSoddsSeq(String meet_cd, String stnd_year, int tms, int day_ord, String race_no, String pool_cd, 
			String runner_1st, String runner_2nd, String runner_3rd) {
		
		return selectPBSoddsSeq(meet_cd, stnd_year, tms, day_ord, race_no, pool_cd, runner_1st, runner_2nd, runner_3rd);

	}
	
	/**
	 * PB 데이터를 tbes_sdl_pb table에 입력함
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param pool_cd 승식
	 * @param runner1ST 선수1
	 * @param runner2ND 선수2
	 * @param runner3RD 선수3
	 * @param odds 배당율
	 */
	public void insertTR(String meet_cd, String stnd_year, int tms,
			int day_ord, String race_no, String pool_cd, String runner1ST, String runner2ND, String runner3RD, float odds) {
		
		
		insertPB(meet_cd, stnd_year, tms, day_ord, race_no, pool_cd, runner1ST, runner2ND, runner3RD, odds);

	}
	
	/**
	 * PB data를 tbes_sdl_pbs table에 입력함
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param pool_cd 승식
	 * @param runner1ST 선수1
	 * @param runner2ND 선수2
	 * @param runner3RD 선수3
	 * @param odds_seq 배당율 순서
	 * @param odds 배당율
	 */
	public void insertTRS(String meet_cd, String stnd_year, int tms,
			int day_ord, String race_no, String pool_cd, String runner1ST, String runner2ND, String runner3RD, int odds_seq, float odds) {
		
		insertPBS(meet_cd, stnd_year, tms, day_ord, race_no, pool_cd, runner1ST, runner2ND, runner3RD, odds_seq, odds);

	}
	
	/**
	 * PB data를 tbes_sdl_pb table에 업데이트함
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param pool_cd 승식
	 * @param runner1ST 선수1
	 * @param runner2ND 선수2
	 * @param runner3RD 선수3
	 * @param odds 배당율
	 */
	public void updateTR(String meet_cd, String stnd_year, int tms,
			int day_ord, String race_no, String pool_cd, String runner1ST, String runner2ND, String runner3RD, float odds) {
		
		updatePB(meet_cd, stnd_year, tms, day_ord, race_no, pool_cd, runner1ST, runner2ND, runner3RD, odds);

	}
	
	/**
	 * 기본값과 일치하는 경주확정정보의 갯수를 반환한다.
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param rank 순위
	 * @param fn_seq 순위정보순
	 * @return 기본값과 일치하는 경주확정정보의 갯수
	 */
	public int selectFNExistCount(String meet_cd, String stnd_year, int tms, int day_ord, String race_no, String rank, 
			int fn_seq) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("select	count(*) as COUNT       ");
			sqlBuffer.append("from		usrbm.tbes_sdl_fn@USRBM_LINK       ");
			sqlBuffer.append("where   	meet_cd = ?             ");
			sqlBuffer.append("and		stnd_year = ?           ");
			sqlBuffer.append("and		tms = ?                 ");
			sqlBuffer.append("and		day_ord = ?             ");
			sqlBuffer.append("and		race_no = ?             ");
			sqlBuffer.append("and     	rank = ?                ");
			sqlBuffer.append("and     	fn_seq = ?              ");
			
			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();
			
			ps.setString(1, meet_cd);
			ps.setString(2, stnd_year);
			ps.setInt(3, tms);
			ps.setInt(4, day_ord);
			ps.setString(5, race_no);
			ps.setString(6, rank);
			ps.setInt(7, fn_seq);
	
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("COUNT");
			} else {
				return -1;
			}

		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
			return -1;
		} finally {
			try {
				rs.close();
			} catch(Exception ex) { }
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * 경주확정정보를 tbes_sdl_fn에 입력한다.
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param rank 순위
	 * @param fn_seq 중복순위 순번
	 * @param reg_no 선수정번
	 */
	public void insertFN(String meet_cd, String stnd_year, int tms,
			int day_ord, String race_no, String rank, int fn_seq, int reg_no) {
		
		PreparedStatement ps = null;
		StringBuffer sqlBuffer = new StringBuffer();

		try {
			sqlBuffer.append("insert into usrbm.tbes_sdl_fn@USRBM_LINK																 		");
			sqlBuffer.append("(meet_cd, stnd_year, tms, day_ord, race_no, rank, fn_seq, reg_no, inst_id, inst_dt)         	");
			sqlBuffer.append("values (?, ?, ?, ?, ?, ?, ?, ?, 'SDL', sysdate)                                         		");

			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();

			ps.setString(1, meet_cd);
			ps.setString(2, stnd_year);
			ps.setInt(3, tms);
			ps.setInt(4, day_ord);
			ps.setString(5, race_no);
			ps.setString(6, rank);
			ps.setInt(7, fn_seq);
			ps.setInt(8, reg_no);

			ps.executeUpdate();
			
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		} finally {
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * 경주확정정보를 tbes_sdl_fn에 갱신한다.
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param rank 순위
	 * @param fn_seq 중복순위 순번
	 * @param reg_no 선수정번
	 */
	public void updateFN(String meet_cd, String stnd_year, int tms,
			int day_ord, String race_no, String rank, int fn_seq, int reg_no) {
		
		PreparedStatement ps = null;
		StringBuffer sqlBuffer = new StringBuffer();

		try {
			sqlBuffer.append("update usrbm.tbes_sdl_fn@USRBM_LINK	");
			sqlBuffer.append("set reg_no = ?,       	");
			sqlBuffer.append("updt_id = 'SDL',          ");
			sqlBuffer.append("updt_dt = sysdate         ");
			sqlBuffer.append("where meet_cd = ?         ");
			sqlBuffer.append("and stnd_year = ?         ");
			sqlBuffer.append("and tms = ?               ");
			sqlBuffer.append("and day_ord = ?           ");
			sqlBuffer.append("and race_no = ?           ");
			sqlBuffer.append("and rank = ?           	");
			sqlBuffer.append("and fn_seq = ?        	");

			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();

			ps.setInt(1, reg_no);
			ps.setString(2, meet_cd);
			ps.setString(3, stnd_year);
			ps.setInt(4, tms);
			ps.setInt(5, day_ord);
			ps.setString(6, race_no);
			ps.setString(7, rank);
			ps.setInt(8, fn_seq);

			ps.executeUpdate();
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		} finally {
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * 기본값과 일치하는 확정배당율정보의 갯수를 반환한다.
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param pool_cd 승식
	 * @param rs_seq 결과순서
	 * @return 기본값과 일치하는 확정배당율정보
	 */
	public int selectRSExistCount(String meet_cd, String stnd_year, int tms, int day_ord, String race_no, String pool_cd,
			int rs_seq) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		
		try {
			sqlBuffer.append("select	count(*) as COUNT       ");
			sqlBuffer.append("from		usrbm.tbes_sdl_rs@USRBM_LINK       ");
			sqlBuffer.append("where   	meet_cd = ?             ");
			sqlBuffer.append("and		stnd_year = ?           ");
			sqlBuffer.append("and		tms = ?                 ");
			sqlBuffer.append("and		day_ord = ?             ");
			sqlBuffer.append("and		race_no = ?             ");
			sqlBuffer.append("and     	pool_cd = ?             ");
			sqlBuffer.append("and     	rs_seq = ?              ");
			
			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();
			
			ps.setString(1, meet_cd);
			ps.setString(2, stnd_year);
			ps.setInt(3, tms);
			ps.setInt(4, day_ord);
			ps.setString(5, race_no);
			ps.setString(6, pool_cd);
			ps.setInt(7, rs_seq);
	
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("COUNT");
			} else {
				return -1;
			}

		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
			return -1;
		} finally {
			try {
				rs.close();
			} catch(Exception ex) { }
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * 확정배당율정보를 tbes_sdl_rs에 입력한다.
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param pool_cd 승식
	 * @param rs_seq 결과순서
	 * @param runner_1st 선수1
	 * @param runner_2nd 선수2
	 * @param runner_3rd 선수3
	 * @param payoff 확정배당율
	 * @param status 배당형태
	 */
	public void insertRS(String meet_cd, String stnd_year, int tms, int day_ord, String race_no, String pool_cd,
			int rs_seq, String runner_1st, String runner_2nd, String runner_3rd, float payoff, String status) {
		
		PreparedStatement ps = null;
		StringBuffer sqlBuffer = new StringBuffer();
		
		if (status.equals("R")) {
			if (pool_cd.equals("001")) {
				runner_1st = "F";
			} else if (pool_cd.equals("002")) {
				runner_1st = "F";
			} else if (pool_cd.equals("004")) {
				runner_1st = "F";
				runner_2nd = "F";
			} else if (pool_cd.equals("005")) {
				runner_1st = "F";
				runner_2nd = "F";
			} else if (pool_cd.equals("006")) {
				runner_1st = "F";
				runner_2nd = "F";
				runner_3rd = "F";
			}
		}

		try {
			sqlBuffer.append("insert into usrbm.tbes_sdl_rs@USRBM_LINK	");
			sqlBuffer.append("(meet_cd, stnd_year, tms, day_ord, race_no, pool_cd, rs_seq, ");
			sqlBuffer.append("runner_1st, ");
			sqlBuffer.append("runner_2nd, ");
			sqlBuffer.append("runner_3rd, ");
			sqlBuffer.append("payoff, status, inst_id, inst_dt) ");
			sqlBuffer.append("values (?, ?, ?, ?, ?, ?, ?, ?, ");
			sqlBuffer.append("?, ");
			sqlBuffer.append("?, ");
			sqlBuffer.append("?, ?, 'SDL', sysdate) ");

			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();

			int psIndex = 1;
			ps.setString(psIndex++, meet_cd);
			ps.setString(psIndex++, stnd_year);
			ps.setInt(psIndex++, tms);
			ps.setInt(psIndex++, day_ord);
			ps.setString(psIndex++, race_no);
			ps.setString(psIndex++, pool_cd);
			ps.setInt(psIndex++, rs_seq);
			ps.setString(psIndex++, runner_1st);
			ps.setString(psIndex++, runner_2nd);
			ps.setString(psIndex++, runner_3rd);
			ps.setFloat(psIndex++, payoff);
			ps.setString(psIndex++, status);
			
			ps.executeUpdate();
			
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		} finally {
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * 확정배당율정보를 tbes_sdl_rs에 갱신한다.
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param pool_cd 승식
	 * @param rs_seq 결과순서
	 * @param runner_1st 선수1
	 * @param runner_2nd 선수2
	 * @param runner_3rd 선수3
	 * @param payoff 확정배당율
	 * @param status 배당형태
	 */
	public void updateRS(String meet_cd, String stnd_year, int tms, int day_ord, String race_no, String pool_cd,
			int rs_seq, String runner_1st, String runner_2nd, String runner_3rd, float payoff, String status) {
		
		PreparedStatement ps = null;
		StringBuffer sqlBuffer = new StringBuffer();
		
		if (status.equals("R")) {
			if (pool_cd.equals("001")) {
				runner_1st = "F";
			} else if (pool_cd.equals("002")) {
				runner_1st = "F";
			} else if (pool_cd.equals("004")) {
				runner_1st = "F";
				runner_2nd = "F";
			} else if (pool_cd.equals("005")) {
				runner_1st = "F";
				runner_2nd = "F";
			} else if (pool_cd.equals("006")) {
				runner_1st = "F";
				runner_2nd = "F";
				runner_3rd = "F";
			}
		}

		try {
			sqlBuffer.append("update usrbm.tbes_sdl_rs@USRBM_LINK	");
			sqlBuffer.append("set runner_1st = ?,       ");
			sqlBuffer.append("runner_2nd = ?,           ");
			sqlBuffer.append("runner_3rd = ?,           ");
			sqlBuffer.append("payoff = ?,          		");
			sqlBuffer.append("status = ?,          		");
			sqlBuffer.append("updt_id = 'SDL',          ");
			sqlBuffer.append("updt_dt = sysdate         ");
			sqlBuffer.append("where meet_cd = ?         ");
			sqlBuffer.append("and stnd_year = ?         ");
			sqlBuffer.append("and tms = ?               ");
			sqlBuffer.append("and day_ord = ?           ");
			sqlBuffer.append("and race_no = ?           ");
			sqlBuffer.append("and pool_cd = ?           ");
			sqlBuffer.append("and rs_seq = ?        	");

			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();

			int psIndex = 1;
			ps.setString(psIndex++, runner_1st);
			ps.setString(psIndex++, runner_2nd);
			ps.setString(psIndex++, runner_3rd);
			ps.setFloat(psIndex++, payoff);
			ps.setString(psIndex++, status);
			ps.setString(psIndex++, meet_cd);
			ps.setString(psIndex++, stnd_year);
			ps.setInt(psIndex++, tms);
			ps.setInt(psIndex++, day_ord);
			ps.setString(psIndex++, race_no);
			ps.setString(psIndex++, pool_cd);
			ps.setInt(psIndex++, rs_seq);

			ps.executeUpdate();
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		} finally {
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * 기본정보와 중복되는 Division 정보 갯수를 반환한다.
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param assc_no Association 번호
	 * @param comm_no Community 번호
	 * @param div_no Division 번호
	 * @return 기본값과 일치하는 Division 정보 갯수
	 */
	public int selectDTExistCount(String meet_cd, String stnd_year, int tms, int day_ord, String race_no, 
			String sell_cd, String comm_no, String div_no) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		
		try {
			sqlBuffer.append("select	count(*) as COUNT       ");
			sqlBuffer.append("from		usrbm.tbes_sdl_dt@USRBM_LINK       ");
			sqlBuffer.append("where   	meet_cd = ?             ");
			sqlBuffer.append("and		stnd_year = ?           ");
			sqlBuffer.append("and		tms = ?                 ");
			sqlBuffer.append("and		day_ord = ?             ");
			sqlBuffer.append("and		race_no = ?             ");
			sqlBuffer.append("and		sell_cd = ?             ");
			sqlBuffer.append("and		comm_no = ?             ");
			sqlBuffer.append("and		div_no = ?              ");

			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();

			ps.setString(1, meet_cd);
			ps.setString(2, stnd_year);
			ps.setInt(3, tms);
			ps.setInt(4, day_ord);
			ps.setString(5, race_no);
			ps.setString(6, sell_cd);
			ps.setString(7, comm_no);
			ps.setString(8, div_no);

			rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("COUNT");
			} else {
				return -1;
			}

		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
			return -1;
		} finally {
			try {
				rs.close();
			} catch(Exception ex) { }
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * 특정 Division의 매출액을 반환한다.
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param assc_no Association 번호
	 * @param comm_no Community 번호
	 * @param div_no Division 번호
	 * @return 특정 Division의 매출액
	 */
	public int selectDTDivTotal(String meet_cd, String stnd_year, int tms, int day_ord, String race_no, 
			String sell_cd, String comm_no, String div_no) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		
		try {
			sqlBuffer.append("select	div_total as DIV_TOTAL  ");
			sqlBuffer.append("from		usrbm.tbes_sdl_dt@USRBM_LINK       ");
			sqlBuffer.append("where   	meet_cd = ?             ");
			sqlBuffer.append("and		stnd_year = ?           ");
			sqlBuffer.append("and		tms = ?                 ");
			sqlBuffer.append("and		day_ord = ?             ");
			sqlBuffer.append("and		race_no = ?             ");
			sqlBuffer.append("and		sell_cd = ?             ");
			sqlBuffer.append("and		comm_no = ?             ");
			sqlBuffer.append("and		div_no = ?              ");

			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();

			ps.setString(1, meet_cd);
			ps.setString(2, stnd_year);
			ps.setInt(3, tms);
			ps.setInt(4, day_ord);
			ps.setString(5, race_no);
			ps.setString(6, sell_cd);
			ps.setString(7, comm_no);
			ps.setString(8, div_no);

			rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("DIV_TOTAL");
			} else {
				return -1;
			}

		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
			return -1;
		} finally {
			try {
				rs.close();
			} catch(Exception ex) { }
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * 특정 Division의 환불액을 반환한다. 
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param assc_no Association 번호
	 * @param comm_no Community 번호
	 * @param div_no Division 번호
	 * @return 환불액
	 */
	public int selectDTRefund(String meet_cd, String stnd_year, int tms, int day_ord, String race_no, 
			String sell_cd, String comm_no, String div_no) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		
		try {
			sqlBuffer.append("select	refund as REFUND		");
			sqlBuffer.append("from		usrbm.tbes_sdl_dt@USRBM_LINK       ");
			sqlBuffer.append("where   	meet_cd = ?             ");
			sqlBuffer.append("and		stnd_year = ?           ");
			sqlBuffer.append("and		tms = ?                 ");
			sqlBuffer.append("and		day_ord = ?             ");
			sqlBuffer.append("and		race_no = ?             ");
			sqlBuffer.append("and		sell_cd = ?             ");
			sqlBuffer.append("and		comm_no = ?             ");
			sqlBuffer.append("and		div_no = ?              ");

			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();

			ps.setString(1, meet_cd);
			ps.setString(2, stnd_year);
			ps.setInt(3, tms);
			ps.setInt(4, day_ord);
			ps.setString(5, race_no);
			ps.setString(6, sell_cd);
			ps.setString(7, comm_no);
			ps.setString(8, div_no);

			rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("REFUND");
			} else {
				return -1;
			}

		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
			return -1;
		} finally {
			try {
				rs.close();
			} catch(Exception ex) { }
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * Division 정보를 tbes_sdl_dt에 입력한다.
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param assc_no Association 번호
	 * @param comm_no Community 번호
	 * @param div_no Division 번호
	 * @param div_total Division 매출액
	 * @param refund 환불액
	 */
	public void insertDT(String meet_cd, String stnd_year, int tms, int day_ord, String race_no, 
			String sell_cd, String comm_no, String div_no, int div_total, int refund) {
		
		PreparedStatement ps = null;
		StringBuffer sqlBuffer = new StringBuffer();

		try {
			sqlBuffer.append("insert into usrbm.tbes_sdl_dt@USRBM_LINK																		");
			sqlBuffer.append("(meet_cd, stnd_year, tms, day_ord, race_no, sell_cd, comm_no, div_no, div_total, refund, inst_id, inst_dt)	");
			sqlBuffer.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'SDL', sysdate)                       									");

			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();

			ps.setString(1, meet_cd);
			ps.setString(2, stnd_year);
			ps.setInt(3, tms);
			ps.setInt(4, day_ord);
			ps.setString(5, race_no);
			ps.setString(6, sell_cd);
			ps.setString(7, comm_no);
			ps.setString(8, div_no);
			ps.setInt(9, div_total);
			ps.setInt(10, refund);

			ps.executeUpdate();
			
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		} finally {
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * Division 정보를 tbes_sdl_dt에 갱신한다.
	 * 
	 * @param meet_cd Meet Code
	 * @param stnd_year 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 * @param assc_no Association 번호
	 * @param comm_no Community 번호
	 * @param div_no Division 번호
	 * @param div_total Division 매출액
	 * @param refund 환불액
	 */
	public void updateDT(String meet_cd, String stnd_year, int tms, int day_ord, String race_no, 
			String sell_cd, String comm_no, String div_no, int div_total, int refund) {
		
		PreparedStatement ps = null;
		StringBuffer sqlBuffer = new StringBuffer();

		try {
			sqlBuffer.append("update usrbm.tbes_sdl_dt@USRBM_LINK	");
			sqlBuffer.append("set div_total = ?,       	");
			sqlBuffer.append("refund = ?,	           	");
			sqlBuffer.append("updt_id = 'SDL',          ");
			sqlBuffer.append("updt_dt = sysdate         ");
			sqlBuffer.append("where meet_cd = ?         ");
			sqlBuffer.append("and stnd_year = ?         ");
			sqlBuffer.append("and tms = ?               ");
			sqlBuffer.append("and day_ord = ?           ");
			sqlBuffer.append("and race_no = ?           ");
			sqlBuffer.append("and sell_cd = ?           ");
			sqlBuffer.append("and comm_no = ?        	");
			sqlBuffer.append("and div_no = ?        	");

			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();

			ps.setInt(1, div_total);
			ps.setInt(2, refund);
			
			ps.setString(3, meet_cd);
			ps.setString(4, stnd_year);
			ps.setInt(5, tms);
			ps.setInt(6, day_ord);
			ps.setString(7, race_no);
			ps.setString(8, sell_cd);
			ps.setString(9, comm_no);
			ps.setString(10, div_no);
			

			ps.executeUpdate();
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		} finally {
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}

	/**
	 * 특정 경주의 Division 정보를 제거한다.
	 * 
	 * @param meetCode Meet Code
	 * @param stndYear 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 */
	public void deleteDT(String meetCode, String stndYear, int tms, int day_ord, int race_no) {
		PreparedStatement ps = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("delete from usrbm.tbes_sdl_dt@USRBM_LINK	");
			sqlBuffer.append("where 	meet_cd = ?         ");
			sqlBuffer.append("and		stnd_year = ?       ");
			sqlBuffer.append("and		tms = ?             ");
			sqlBuffer.append("and		day_ord = ?         ");
			sqlBuffer.append("and		race_no = ?         ");

			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();

			ps.setString(1, meetCode);
			ps.setString(2, stndYear);
			ps.setInt(3, tms);
			ps.setInt(4, day_ord);
			ps.setInt(5, race_no);

			ps.executeUpdate();
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		} finally {
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}

	/**
	 * 특정 승식별 발매총액을 제거한다.
	 * 
	 * @param meetCode Meet Code
	 * @param stndYear 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @param race_no 경주번호
	 */
	public void deletePT(String meetCode, String stndYear, int tms, int day_ord, int race_no) {
		PreparedStatement ps = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("delete from usrbm.tbes_sdl_pt@USRBM_LINK	");
			sqlBuffer.append("where 	meet_cd = ?         ");
			sqlBuffer.append("and		stnd_year = ?       ");
			sqlBuffer.append("and		tms = ?             ");
			sqlBuffer.append("and		day_ord = ?         ");
			sqlBuffer.append("and		race_no = ?         ");

			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();

			ps.setString(1, meetCode);
			ps.setString(2, stndYear);
			ps.setInt(3, tms);
			ps.setInt(4, day_ord);
			ps.setInt(5, race_no);

			ps.executeUpdate();
		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
		} finally {
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
	
	/**
	 * 특정일의 경주갯수를 반환한다.
	 * 
	 * @param meetCode Meet Code
	 * @param stndYear 기준년도
	 * @param tms 회차
	 * @param day_ord 일차
	 * @return 경주갯수
	 */
	public int selectRaceCnt(String meetCode, String stndYear, int tms, int day_ord) {
		PreparedStatement ps = null;
		StringBuffer sqlBuffer = new StringBuffer();
		ResultSet rs = null;
		try {
			
			sqlBuffer.append("select	race_cnt				");
			sqlBuffer.append("from		tbeb_race_day_ord       ");
			sqlBuffer.append("where		mbr_cd = ?              ");
			sqlBuffer.append("and		stnd_year = ?       	");
			sqlBuffer.append("and		tms = ?           		");
			sqlBuffer.append("and		day_ord = ?       		");
			
			ps = connection.prepareStatement(new String(sqlBuffer));
			ps.clearParameters();

			ps.setString(1, meetCode);
			ps.setString(2, stndYear);
			ps.setInt(3, tms);
			ps.setInt(4, day_ord);

			rs = ps.executeQuery();
			
			rs.next();
			int raceCount = rs.getInt("race_cnt");
			
			return raceCount;

		} catch(Exception ex) {
			SDL_Common.logger.error(ex.toString());
			return 0;
		} finally {
			try {
				rs.close();
			} catch(Exception ex) { }
			try {
				ps.close();
			} catch(Exception ex) { }
		}
	}
}
