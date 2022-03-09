package org.sosfo.sdl.cra.dao;

import java.sql.Connection;

import org.sosfo.framework.config.Config;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.exception.PropNotFoundException;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.sql.QueryRunner;
import org.sosfo.framework.tray.Tray;
import org.sosfo.framework.util.StringUtil;

/**
 * TM : Time Info
 * 
 * @author <pre>
 * ++ SDL Header
 *          'TM'                               [ 2] Message code
 *          'CRA - GWANGMYEONG             '   [30] Meet name
 *          '0088'                             [ 4] Perf number
 *          'REAL'                           [ 4] Type of Perf
 *          '01'                                 [ 2] Race number
 *          '0022'                             [ 4] Message length
 *  ++ SDL Data
 *          '10082008'                     [ 8] Perf Date MMDDYYYY
 *          '104001'                         [ 6] Perf Time HHMMSS; 48 hours clock may be used
 *          '111600'                         [ 6] Post Time;        48 hours clock may be used
 *          '35'                                 [ 2] mtp;  **:passed or not available
 *  ++ SDL Checksum
 *          '01098'                           [ 5] Checksum
 * 
 * 
 * 
 *  Message example
 * TMCRA - GWANGMYEONG             0088REAL010022100820081039071116003601113
 * TMCRA - GWANGMYEONG             0088REAL01002210062008142159......**01071
 * TMCRA - GWANGMYEONG             0088REAL020022100620083536253602002501115
 * TMCRA - GWANGMYEONG             0088REAL020022100620083558013602000301109
 * 
 * </pre>
 */
public class TMDAO {

	protected Config	format_conf	= null; // 전문 포멧 config (properties 객체)

	public TMDAO() {
		try {
			format_conf = ConfigFactory.getInstance().getConfiguration("format.properties");
		} catch (PropNotFoundException e) {
			Log.error("ERROR", this, "TMDAO Constructor : \nsdl : Couldn't find [format.properties] \n" + e);
		}
	}

	/**
	 * <pre>
	 * 0. TM 로직에 맞게 Tray 로딩
	 * 1. 조회
	 * 2. 1.에의한 update or insert 결정
	 * </pre>
	 */
	public boolean insert(Connection conn, Tray sdlTray) throws AppException {
		try {
			loadTM(sdlTray);

			boolean success = false;
			
			// 수정 또는 추가를 결정하기 위해 데이터 조회를 먼저한다.
			int count = findTM(conn, sdlTray);

			if(count>0){
				success = updateTM(conn, sdlTray);
			}else{
				success = insertTM(conn, sdlTray);
			}

			return success;
		} catch (AppException e) {
			Log.error("ERROR", this, "at TMDAO.insert() - " + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at TMDAO.insert()- " + ex);
			throw new AppException("", ex);
		} 
	}

	/**
	 * TM로직에 맞게 Tray 로딩
	 * 
	 * @param conn
	 * @param sdlTray
	 * @throws AppException
	 */
	private void loadTM(Tray sdlTray) throws AppException {
		try {
			/*
			 * # TM(마감분전) 전문 개별부 컬럼
			 * sdl.body.tm.columns = perf_date, perf_time, post_time, mtp
			 * sdl.body.tm.columns.size = 8,6,6,2
			 */

			// pass header 2,30,4,4,2,4
			int idx = 46;

			String columnArr[] = format_conf.getStringArray("sdl.body.tm.columns");
			String sizeArr[] = format_conf.getStringArray("sdl.body.tm.columns.size");

			int arrCnt = 0;

			if (columnArr.length == sizeArr.length) {
				arrCnt = columnArr.length;
			} else {
				throw new PropNotFoundException("properties 읽기 실패");
			}

			String strSDL = sdlTray.getString("sdl");
			try {
				for (int i = 0; i < arrCnt; i++) {
					// setTray(columnArr[i], strSDL.substring(idx, idx += Integer.parseInt(sizeArr[i])));
					sdlTray.setString(columnArr[i], StringUtil.substring(strSDL, idx, idx += StringUtil.parseInt(sizeArr[i])));
				}

				sdlTray.setString("race_end_yn", "N");
				
				//if ("**".equals(StringUtil.evlTrim(sdlTray.getString("mtp"), ""))) sdlTray.setString("race_end_yn", "Y");

				if ("......".equals(sdlTray.getString("post_time")) || "Y".equals(sdlTray.getString("race_end_yn"))) sdlTray.setString("post_time", sdlTray.getString("perf_time"));

				Log.debug("", this, sdlTray.toString());
			} catch (Exception e) {
				Log.error("ERROR", this, e);
				throw new AppException("sdl.cra.header.parsing", e);
			}

		} catch (Exception ex) {
			Log.error("ERROR", this, "at TMDAO.loadSP - " + ex);
			throw new AppException("", ex);
		}
	}

	/**
	 * 이미 저장되어있는 데이터인지 check.
	 * 
	 * @param conn
	 * @param sdlTray
	 * @return
	 * @throws AppException
	 */
	private int findTM(Connection conn, Tray sdlTray) throws AppException {

		StringBuffer sb = new StringBuffer();
		sb.append("\n select                       ");
		sb.append("\n     count(*) as cnt          ");
		sb.append("\n from                         ");
		sb.append("\n     tbes_sdl_tm              ");
		sb.append("\n where                        ");
		sb.append("\n     1=1                      ");
		sb.append("\n     and meet_cd=:meet_cd     ");
		sb.append("\n     and stnd_year=:stnd_year ");
		sb.append("\n     and tms=:tms             ");
		sb.append("\n     and day_ord=:day_ord     ");
		sb.append("\n     and race_no=:race_no     ");

		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			runner.setParams(sdlTray);
			Log.debug("DB", this, runner.toString());

			return ((Tray) runner.query(conn)).getInt("cnt");

		} catch (Exception ex) {
			Log.error("ERROR", this, "at TMDAO.findInfo()" + ex);
			throw new AppException("", ex);
		}
	}

	/**
	 * insert
	 * @param conn
	 * @param rsTray
	 * @return
	 * @throws AppException
	 */
	private boolean insertTM(Connection conn, Tray rsTray) throws AppException {

		StringBuffer sb = new StringBuffer();
		sb.append("\n insert into tbes_sdl_tm (										  ");
		sb.append("\n meet_cd,                                                        ");
		sb.append("\n stnd_year,                                                      ");
		sb.append("\n tms,                                                            ");
		sb.append("\n day_ord,                                                        ");
		sb.append("\n race_no,                                                        ");

		sb.append("\n post_dt,                                                        ");
		sb.append("\n curr_dt,                                                        ");
		sb.append("\n rest_mm,                                                        ");
		sb.append("\n race_end_yn,                                                    ");
		sb.append("\n race_day,                                                       ");

		sb.append("\n perf_no,                                                        ");
		sb.append("\n perf_type,                                                      ");
		sb.append("\n inst_id,                                                        ");
		sb.append("\n inst_dt,                                                        ");
		sb.append("\n updt_id,                                                        ");
		sb.append("\n updt_dt                                                         ");

		sb.append("\n ) values (                                                      ");

		sb.append("\n :meet_cd,                                                       ");
		sb.append("\n :stnd_year,                                       			  ");
		sb.append("\n :tms,                                                           ");
		sb.append("\n :day_ord,                                                       ");
		sb.append("\n :race_no,                                                       ");

		sb.append("\n ( case when to_number( :post_time ) < 240000 																																						");
		sb.append("\n      then to_date( concat( :perf_date, :post_time ), 'MMDDYYYYHH24MISS')                                                ");
		sb.append("\n      else to_date( concat( :perf_date, to_char( (to_number( :post_time ) - 240000) , '000000') ) , 'MMDDYYYYHH24MISS')+1");
		sb.append("\n end ),                                                                                                                   ");

		sb.append("\n ( case when to_number( :perf_time ) < 240000                                                                            ");
		sb.append("\n      then to_date( concat( :perf_date, :perf_time ), 'MMDDYYYYHH24MISS')                                                ");
		sb.append("\n      else to_date( concat( :perf_date, to_char( (to_number( :perf_time ) - 240000) , '000000') ) , 'MMDDYYYYHH24MISS')+1");
		sb.append("\n end ),                                                                                                                   ");

		sb.append("\n :mtp,                                                           ");
		sb.append("\n :race_end_yn,                                                   ");
		sb.append("\n to_char( to_date(:perf_date, 'MMDDYYYY'), 'YYYYMMDD'),          ");

		sb.append("\n :perf_no,                                                       ");
		sb.append("\n :perf_type,                                                     ");
		sb.append("\n 'SDL',                                                          ");
		sb.append("\n sysdate,                                                        ");
		sb.append("\n 'SDL',                                                          ");
		sb.append("\n sysdate                                                         ");
		sb.append("\n )                                                              ");

		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			runner.setParams(rsTray);
			Log.debug("DB", this, runner.toString());
			return runner.update(conn) > 0;
		} catch (AppException e) {
			Log.error("ERROR", this, "at TMDAO.insertTM()" + e);
			throw e;
		} catch (Exception e) {
			Log.error("ERROR", this, "at TMDAO.insertTM()" + e);
			throw new AppException("", e);
		}
	}

	/**
	 * update
	 * @param conn
	 * @param rsTray
	 * @return
	 * @throws AppException
	 */
	private boolean updateTM(Connection conn, Tray rsTray) throws AppException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n update tbes_sdl_tm set 														");
		// sb.append( "\n post_dt = to_date( concat( :perf_date, :post_time), 'MMDDYYYYHH24MISS'), ");
		sb.append("\n post_dt = ( case when to_number( :post_time ) < 240000 	");
		sb.append("\n      			then to_date( concat( :perf_date, :post_time ), 'MMDDYYYYHH24MISS')    ");
		sb.append("\n      			else to_date( concat( :perf_date, to_char( (to_number( :post_time ) - 240000) , '000000') ) , 'MMDDYYYYHH24MISS')+1");
		sb.append("\n 				end ),     ");

		// sb.append( "\n curr_dt = to_date( concat( :perf_date, :perf_time), 'MMDDYYYYHH24MISS'), ");
		sb.append("\n curr_dt = ( case when to_number( :perf_time ) < 240000 ");
		sb.append("\n      			then to_date( concat( :perf_date, :perf_time ), 'MMDDYYYYHH24MISS') ");
		sb.append("\n      			else to_date( concat( :perf_date, to_char( (to_number( :perf_time ) - 240000) , '000000') ) , 'MMDDYYYYHH24MISS')+1");
		sb.append("\n 				end ),  ");

		sb.append("\n rest_mm = :mtp,                                                              ");
		//sb.append("\n race_end_yn = :race_end_yn,                                                  ");
		sb.append("\n updt_id = 'SDL',                                                             ");
		sb.append("\n updt_dt = sysdate                                                            ");
		sb.append("\n where meet_cd = :meet_cd                                                     ");
		sb.append("\n and stnd_year = :stnd_year                                                   ");
		sb.append("\n and tms=:tms                                                                 ");
		sb.append("\n and day_ord = :day_ord                                                       ");
		sb.append("\n and race_no = :race_no                                                       ");
		sb.append("\n and perf_no = :perf_no														");
		sb.append("\n and perf_type = :perf_type													");

		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			runner.setParams(rsTray);
			Log.debug("DB", this, runner.toString());
			return runner.update(conn) > 0;
		} catch (AppException e) {
			Log.error("ERROR", this, "at TMDAO.updateTM()" + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at TMDAO.updateTM()" + ex);
			throw new AppException("", ex);
		}
	}
}
