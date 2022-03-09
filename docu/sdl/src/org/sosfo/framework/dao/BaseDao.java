package org.sosfo.framework.dao;

import java.sql.Connection;

import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.sql.QueryRunner;
import org.sosfo.framework.tray.Tray;

/**
 * <pre>
 *  Title: WR, SP, FR, PB, TR, TP 전문을 위한 공통 DAO
 *  Description: SDL 테이블별 입력,수정,매출액 수정의 공통 처리 정의
 *  Copyright: Copyright (c) 2009
 * Company: www.cyclerace.or.kr
 * 
 * @author Lee junghye
 * @version 1.1
 * 
 *          </pre>
 */
public class BaseDao {

	/**
	 * 이미 저장되어있는 데이터인지 check.
	 * 
	 * @param conn
	 * @param sdlTray
	 * @return
	 * @throws AppException
	 */
	protected int findInfo(Connection conn, Tray sdlTray) throws AppException {

		StringBuffer sb = new StringBuffer();
		sb.append("\n select                       ");
		sb.append("\n     count(*) as cnt          ");
		sb.append("\n from                         ");
		sb.append("\n     tbes_sdl_pb              ");
		sb.append("\n where                        ");
		sb.append("\n     1=1                      ");
		sb.append("\n     and meet_cd=:meet_cd     ");
		sb.append("\n     and stnd_year=:stnd_year ");
		sb.append("\n     and tms=:tms             ");
		sb.append("\n     and day_ord=:day_ord     ");
		sb.append("\n     and pool_cd = :pool_cd   ");
		sb.append("\n     and race_no=:race_no     ");

		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			runner.setParams(sdlTray);
			Log.debug("DB", this, runner.toString());

			return ((Tray) runner.query(conn)).getInt("cnt");

		} catch (Exception ex) {
			Log.error("ERROR", this, "at SDLDAO.findInfo()" + ex);
			throw new AppException("", ex);
		}
	}

	/**
	 * TBES_SDL_PBS의 이력은 TM의 마감분전 5분전으로 제한
	 * 
	 * @param conn
	 * @param sdlTray
	 * @return
	 * @throws AppException
	 */
	protected boolean findTM(Connection conn, Tray sdlTray) throws AppException {

		StringBuffer sb = new StringBuffer();
//		sb.append("\n select                                        ");
//		sb.append("\n        case                                                  ");
//		sb.append("\n            when post_dt<curr_dt then -1                      ");
//		sb.append("\n            else to_number(decode(rest_mm,'**','-1',rest_mm)) ");
//		sb.append("\n        end rest_mm                                           ");	
		
		
//		sb.append("\n select                                                                            ");
//		sb.append("\n        case                                                                       ");
//		sb.append("\n            when race_end_yn='N' then to_number(decode(rest_mm,'**','-1',rest_mm)) ");
//		sb.append("\n            else 0                                                                 ");
//		sb.append("\n        end rest_mm                                                                ");

		sb.append("\n select                                                                       ");
		sb.append("\n    case                                                                      ");
		sb.append("\n        when race_end_yn='N' then to_number(decode(rest_mm,'**','-1',rest_mm))");
		sb.append("\n        when race_end_yn='Y' and post_dt< (curr_dt-(2/24/60)) then -1         ");
		sb.append("\n        when race_end_yn='Y' then 0                                           ");
		sb.append("\n        else -1                                                               ");
		sb.append("\n    end rest_mm                                                               ");	
		sb.append("\n from tbes_sdl_tm                              ");
		sb.append("\n where                                         ");
		sb.append("\n      1=1                                      ");
		sb.append("\n      and meet_cd=:meet_cd                     ");
		sb.append("\n      and stnd_year=:stnd_year                 ");
		sb.append("\n      and tms=:tms                             ");
		sb.append("\n      and day_ord=:day_ord                     ");
		sb.append("\n      and race_no=:race_no                     ");

		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			runner.setParams(sdlTray);
			Log.debug("DB", this, runner.toString());

			Tray rsTray = ((Tray) runner.query(conn)); 
			
			if(rsTray.getRowCount()<1){
				return false;
			}
			
			int mm = rsTray.getInt("rest_mm");

			return (mm <= 6 && mm >= 0);

		} catch (Exception ex) {
			Log.error("ERROR", this, "at SDLDAO.findTM()" + ex);
			throw new AppException("", ex);
		}
	}

	/**
	 * TBES_SDL_PBS 에 배당률등 전문내용 저장
	 * 
	 * @param type 0 : 최초입력 ODDS_SEQ = 1 1 : 중복입력 ODDS_SEQ 1 증가
	 * @param conn
	 * @param reqTray
	 * @return boolean
	 * @throws AppException
	 */
	protected boolean insertPBS(Connection conn, Tray sdlTray) throws AppException {

		// 마감 5분전만 이력을 쌓는다.
		if (!findTM(conn, sdlTray)) return false;

		StringBuffer sb = new StringBuffer();
		sb.append("\n insert into tbes_sdl_pbs (		");
		sb.append("\n 	meet_cd                           ");
		sb.append("\n 	, stnd_year                       ");
		sb.append("\n 	, tms                             ");
		sb.append("\n 	, day_ord                         ");
		sb.append("\n 	, race_no                         ");
		sb.append("\n 	, pool_cd                         ");
		sb.append("\n 	, runner_1st                      ");
		sb.append("\n 	, runner_2nd                      ");
		sb.append("\n 	, runner_3rd                      ");
		sb.append("\n 	, odds_seq                        ");
		sb.append("\n 	, recv_dt                         ");
		sb.append("\n 	, odds                            ");
		sb.append("\n 	, amount                          ");
		sb.append("\n 	, inst_id                         ");
		sb.append("\n 	, inst_dt                         ");
		sb.append("\n 	, updt_id                         ");
		sb.append("\n 	, updt_dt                         ");
		sb.append("\n )                                  ");
		sb.append("\n select meet_cd                     ");
		sb.append("\n 	, stnd_year                       ");
		sb.append("\n 	, tms                             ");
		sb.append("\n 	, day_ord                         ");
		sb.append("\n 	, race_no                         ");
		sb.append("\n 	, pool_cd                         ");
		sb.append("\n 	, runner_1st                      ");
		sb.append("\n 	, runner_2nd                      ");
		sb.append("\n 	, runner_3rd                      ");
		sb.append("\n 	, (                               ");
		sb.append("\n 			select nvl(max(odds_seq),0)+1 ");
		sb.append("\n 			from tbes_sdl_pbs             ");
		sb.append("\n 			where 1=1                     ");
		sb.append("\n 			and meet_cd=:meet_cd          ");
		sb.append("\n 			and stnd_year=:stnd_year      ");
		sb.append("\n 			and tms=:tms                  ");
		sb.append("\n 			and day_ord=:day_ord          ");
		sb.append("\n 			and pool_cd = :pool_cd  	");
		sb.append("\n 			and race_no=:race_no          ");
		sb.append("\n 		)                               ");
		sb.append("\n 	, updt_dt                         ");
		sb.append("\n 	, odds                            ");
		sb.append("\n 	, amount                          ");
		sb.append("\n 	, inst_id                         ");
		sb.append("\n 	, inst_dt                         ");
		sb.append("\n 	, updt_id                         ");
		sb.append("\n 	, updt_dt                         ");
		sb.append("\n from tbes_sdl_pb                   ");
		sb.append("\n where 1=1                          ");
		sb.append("\n and meet_cd=:meet_cd               ");
		sb.append("\n and stnd_year=:stnd_year           ");
		sb.append("\n and tms=:tms                       ");
		sb.append("\n and day_ord=:day_ord               ");
		sb.append("\n and pool_cd = :pool_cd       ");
		sb.append("\n and race_no=:race_no               ");

		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			runner.setParams(sdlTray);
			Log.debug("DB", this, runner.toString());
			return runner.update(conn) > 0;
		} catch (AppException e) {
			Log.error("ERROR", this, "at BaseDao.insertPBS()" + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at BaseDao.insertPBS()" + ex);
			throw new AppException("", ex);
		}
	}

	/**
	 * TBES_SDL_PBS 에 배당률등 전문내용 저장
	 * 
	 * @param type 0 : 최초입력 ODDS_SEQ = 1 1 : 중복입력 ODDS_SEQ 1 증가
	 * @param conn
	 * @param reqTray
	 * @return boolean
	 * @throws AppException
	 */
	protected boolean insertAmountPBS(Connection conn, Tray[] rsTray) throws AppException {

		// 마감 5분전만 이력을 쌓는다.
		if (!findTM(conn, rsTray[0])) return false;

		StringBuffer sb = new StringBuffer();
		sb.append("\n insert into TBES_SDL_PBS ( ");
		sb.append("\n meet_cd,     ");
		sb.append("\n stnd_year,   ");
		sb.append("\n tms,         ");
		sb.append("\n day_ord,     ");
		sb.append("\n race_no,     ");
		sb.append("\n pool_cd,     ");
		sb.append("\n runner_1st,  ");
		sb.append("\n runner_2nd,  ");
		sb.append("\n runner_3rd,  ");
		sb.append("\n odds_seq,    ");
		sb.append("\n recv_dt, 		");
		sb.append("\n odds,        ");
		sb.append("\n amount,      ");
		sb.append("\n inst_id,     ");
		sb.append("\n inst_dt,     ");
		sb.append("\n updt_id,     ");
		sb.append("\n updt_dt	");
		sb.append("\n ) values ( ");
		sb.append("\n :meet_cd, ");
		sb.append("\n :stnd_year, ");
		sb.append("\n :tms, ");
		sb.append("\n :day_ord, ");
		sb.append("\n :race_no, ");
		sb.append("\n :pool_cd, ");
		sb.append("\n :runner1st, ");
		sb.append("\n :runner2nd, ");
		sb.append("\n :runner3rd, ");

		sb.append("\n     (		                            ");
		sb.append("\n      select                           ");
		sb.append("\n          nvl(max(odds_seq),0)+1       ");
		sb.append("\n      from                             ");
		sb.append("\n          tbes_sdl_pbs                 ");
		sb.append("\n      where                            ");
		sb.append("\n          1=1                          ");
		sb.append("\n          and meet_cd=:meet_cd         ");
		sb.append("\n          and stnd_year=:stnd_year     ");
		sb.append("\n          and tms=:tms                 ");
		sb.append("\n          and day_ord=:day_ord         ");
		sb.append("\n          and pool_cd = :pool_cd 		");
		sb.append("\n          and race_no=:race_no         ");
		sb.append("\n     ) ,                    			");

		sb.append("\n sysdate, ");

		sb.append("\n     (		                            ");
		sb.append("\n      select                           ");
		sb.append("\n          odds                        ");
		sb.append("\n      from                             ");
		sb.append("\n          tbes_sdl_pb                  ");
		sb.append("\n      where                            ");
		sb.append("\n          1=1                          ");
		sb.append("\n          and meet_cd=:meet_cd         ");
		sb.append("\n          and stnd_year=:stnd_year     ");
		sb.append("\n          and tms=:tms                 ");
		sb.append("\n          and day_ord=:day_ord         ");
		sb.append("\n          and pool_cd = :pool_cd 		");
		sb.append("\n          and race_no=:race_no         ");
		sb.append("\n          and runner_1st=:runner1st         ");
		sb.append("\n          and runner_2nd=:runner2nd         ");
		sb.append("\n          and runner_3rd=:runner3rd         ");
		sb.append("\n     ) ,                    			");
		sb.append("\n :amount, ");
		sb.append("\n 'SDL', ");
		sb.append("\n sysdate, ");
		sb.append("\n 'SDL', ");
		sb.append("\n sysdate ");
		sb.append("\n ) ");

		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			return runner.updateBatch(conn, rsTray);
		} catch (AppException e) {
			Log.error("ERROR", this, "at BaseDao.insertPBS()" + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at BaseDao.insertPBS()" + ex);
			throw new AppException("", ex);
		}
	}

	/**
	 * TBES_SDL_PBS 에 배당률등 전문내용 저장
	 * 
	 * @param type 0 : 최초입력 ODDS_SEQ = 1 1 : 중복입력 ODDS_SEQ 1 증가
	 * @param conn
	 * @param reqTray
	 * @return boolean
	 * @throws AppException
	 */
	protected boolean insertPBS(Connection conn, Tray[] rsTray) throws AppException {

		// 마감 5분전만 이력을 쌓는다.
		if (!findTM(conn, rsTray[0])) return false;

		StringBuffer sb = new StringBuffer();
		sb.append("\n insert into TBES_SDL_PBS ( ");
		sb.append("\n meet_cd,     ");
		sb.append("\n stnd_year,   ");
		sb.append("\n tms,         ");
		sb.append("\n day_ord,     ");
		sb.append("\n race_no,     ");
		sb.append("\n pool_cd,     ");
		sb.append("\n runner_1st,  ");
		sb.append("\n runner_2nd,  ");
		sb.append("\n runner_3rd,  ");
		sb.append("\n odds_seq,    ");
		sb.append("\n recv_dt, 		");
		sb.append("\n odds,        ");
		sb.append("\n amount,      ");
		sb.append("\n inst_id,     ");
		sb.append("\n inst_dt,     ");
		sb.append("\n updt_id,     ");
		sb.append("\n updt_dt	");
		sb.append("\n ) values ( ");
		sb.append("\n :meet_cd, ");
		sb.append("\n :stnd_year, ");
		sb.append("\n :tms, ");
		sb.append("\n :day_ord, ");
		sb.append("\n :race_no, ");
		sb.append("\n :pool_cd, ");
		sb.append("\n :runner1st, ");
		sb.append("\n :runner2nd, ");
		sb.append("\n :runner3rd, ");

		sb.append("\n     (		                            ");
		sb.append("\n      select                           ");
		sb.append("\n          nvl(max(odds_seq),0)+1       ");
		sb.append("\n      from                             ");
		sb.append("\n          tbes_sdl_pbs                 ");
		sb.append("\n      where                            ");
		sb.append("\n          1=1                          ");
		sb.append("\n          and meet_cd=:meet_cd         ");
		sb.append("\n          and stnd_year=:stnd_year     ");
		sb.append("\n          and tms=:tms                 ");
		sb.append("\n          and day_ord=:day_ord         ");
		sb.append("\n          and pool_cd = :pool_cd 		");
		sb.append("\n          and race_no=:race_no         ");
		sb.append("\n     ) ,                    			");

		sb.append("\n sysdate, ");
		sb.append("\n to_char( to_number(:odds) / 10 , '9999.9'), ");
		sb.append("\n :amount, ");
		sb.append("\n 'SDL', ");
		sb.append("\n sysdate, ");
		sb.append("\n 'SDL', ");
		sb.append("\n sysdate ");
		sb.append("\n ) ");

		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			return runner.updateBatch(conn, rsTray);
		} catch (AppException e) {
			Log.error("ERROR", this, "at BaseDao.insertPBS()" + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at BaseDao.insertPBS()" + ex);
			throw new AppException("", ex);
		}
	}

	/**
	 * TBES_SDL_PB 에 배당률 & 매출액 insert
	 * 
	 * @param conn
	 * @param rsTray[]
	 * @return boolean status
	 * @throws AppException
	 */
	protected boolean insertPB(Connection conn, Tray[] rsTray) throws AppException {

		StringBuffer sb = new StringBuffer();
		sb.append("\n insert into TBES_SDL_PB ( ");
		sb.append("\n meet_cd,     ");
		sb.append("\n stnd_year,   ");
		sb.append("\n tms,         ");
		sb.append("\n day_ord,     ");
		sb.append("\n race_no,     ");
		sb.append("\n pool_cd,     ");
		sb.append("\n runner_1st,  ");
		sb.append("\n runner_2nd,  ");
		sb.append("\n runner_3rd,  ");
		sb.append("\n odds,        ");
		sb.append("\n amount,      ");
		sb.append("\n inst_id,     ");
		sb.append("\n inst_dt,     ");
		sb.append("\n updt_id,     ");
		sb.append("\n updt_dt  )  values (    ");
		sb.append("\n :meet_cd, "); // meet_cd (meet_nm1)
		sb.append("\n :stnd_year, "); // stnd_year
		sb.append("\n :tms, "); // tms
		sb.append("\n :day_ord, "); // day_ord
		sb.append("\n :race_no, "); // race_no
		sb.append("\n :pool_cd, "); // pool_no (pool_cd01)
		sb.append("\n :runner1st, "); // runner_1st
		sb.append("\n :runner2nd, "); // runner_2nd
		sb.append("\n :runner3rd, "); // runner_3rd
		sb.append("\n to_char( to_number(:odds) / 10 , '9999.9'), "); // odds (runner01_1)
		sb.append("\n :amount, "); // amount
		sb.append("\n 'SDL', "); // inst_id
		sb.append("\n sysdate, "); // inst_dtm
		sb.append("\n 'SDL', "); // updt_id
		sb.append("\n sysdate"); // updt_dtm
		sb.append("\n ) ");

		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			return runner.updateBatch(conn, rsTray);
		} catch (AppException e) {
			Log.error("ERROR", this, "at BaseDao.insertPB()" + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at BaseDao.insertPB()" + ex);
			throw new AppException("", ex);
		}
	}

	/**
	 * TBES_SDL_PB 에 배당률(ODDS) update
	 * 
	 * @param conn
	 * @param rsTray[]
	 * @return boolean status
	 * @throws AppException
	 */
	protected boolean updatePB(Connection conn, Tray[] rsTray) throws AppException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n update TBES_SDL_PB set ");
		sb.append("\n odds = to_char( to_number(:odds) / 10 , '9999.9'), ");
		sb.append("\n updt_id = 'SDL', ");
		sb.append("\n updt_dt = sysdate ");

		sb.append("\n where meet_cd = :meet_cd ");
		sb.append("\n and stnd_year = :stnd_year ");
		sb.append("\n and tms = :tms ");
		sb.append("\n and day_ord = :day_ord ");
		sb.append("\n and race_no = :race_no ");
		sb.append("\n and pool_cd = :pool_cd ");
		sb.append("\n and runner_1st = :runner1st ");
		sb.append("\n and runner_2nd = :runner2nd ");
		sb.append("\n and runner_3rd = :runner3rd ");
		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			return runner.updateBatch(conn, rsTray);
		} catch (AppException e) {
			Log.error("ERROR", this, "at BaseDao.updatePB()" + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at BaseDao.updatePB()" + ex);
			throw new AppException("", ex);
		}
	}

	/**
	 * TBES_SDL_PB 에 매출액(AMOUNT) 업데이트
	 * 
	 * @param conn
	 * @param rsTray[]
	 * @return boolean status
	 * @throws AppException
	 */
	protected boolean updateAmountPB(Connection conn, Tray[] rsTray) throws AppException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n update TBES_SDL_PB set ");
		sb.append("\n amount = :amount, ");
		sb.append("\n updt_id = 'SDL', ");
		sb.append("\n updt_dt = sysdate ");

		sb.append("\n where meet_cd = :meet_cd ");
		sb.append("\n and stnd_year = :stnd_year ");
		sb.append("\n and tms = :tms ");
		sb.append("\n and day_ord = :day_ord ");
		sb.append("\n and race_no = :race_no ");
		sb.append("\n and pool_cd = :pool_cd ");
		sb.append("\n and runner_1st = :runner1st ");
		sb.append("\n and runner_2nd = :runner2nd ");
		sb.append("\n and runner_3rd = :runner3rd ");
		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			return runner.updateBatch(conn, rsTray);

		} catch (AppException e) {
			Log.error("ERROR", this, "at BaseDao.updateAmountPB()" + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at BaseDao.updateAmountPB()" + ex);
			throw new AppException("", ex);
		}
	}

	/**
	 * TBES_SDL_PB 에 매출액(AMOUNT) 삭제
	 * 
	 * @param conn
	 * @param rsTray[]
	 * @return boolean status
	 * @throws AppException
	 */
	protected int deletePB(Connection conn, Tray sdlTray) throws AppException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n delete from tbes_sdl_pb ");
		sb.append("\n where meet_cd = :meet_cd ");
		sb.append("\n and stnd_year = :stnd_year ");
		sb.append("\n and tms = :tms ");
		sb.append("\n and day_ord = :day_ord ");
		sb.append("\n and race_no = :race_no ");
		sb.append("\n and pool_cd = :pool_cd ");
		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			runner.setParams(sdlTray);
			return runner.update(conn);

		} catch (AppException e) {
			Log.error("ERROR", this, "at BaseDao.deletePB()" + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at BaseDao.deletePB()" + ex);
			throw new AppException("", ex);
		}
	}
}
