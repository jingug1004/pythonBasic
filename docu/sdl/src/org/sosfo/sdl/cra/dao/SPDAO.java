package org.sosfo.sdl.cra.dao;

import java.sql.Connection;

import org.sosfo.framework.dao.BaseDao;
import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.sql.QueryRunner;
import org.sosfo.framework.tray.NewTray;
import org.sosfo.framework.tray.Tray;
import org.sosfo.framework.util.StringUtil;
import org.sosfo.sdl.cra.common.CodeManager;

/**
 * SP : WPS Probables (단,연승식 중간배당율), 선수별자료 포함
 * 
 * @author <pre>
 * look at the examples
 * 
 * + SDL Header
 *          'SP'                                [ 2] Message code
 *          'CRA - GWANGMYEONG             '   [30] Meet name
 *          '0088'                             [ 4] Perf number
 *          'REAL'                           [ 4] Type of Perf
 *          '01'                                 [ 2] Race numbe
 *          '0110'                             [ 4] Message length
 * ++ SDL Data
 *          'WIN'                             [ 3] Pool
 *          'O'                                  [ 1] Pool Status; O:open; F:close
 *          '07'                                 [ 2] Number of runner probs followed
 *          '00056'                           [ 5] Runner odds 1
 *          '00055'                           [..] ....
 *          '00057'
 *          '00055'
 *          '00063'
 *          '00056'
 *          '00056'                           [..] ....
 *          '00000015493900'         [14] Pool total; Unit=1 Won; 15493900=15,493,900 Won
 *          'PLC'                              [ 3] Pool
 *          'O'                                   [ 1] Pool Status; O:open; F:close
 *          '07'                                  [ 2] Number of runner probs followed
 *          '00028'                            [ 5] Runner odds 1
 *          '00028'                            [..] ....
 *          '00028'
 *          '00028'
 *          '00031'
 *          '00027'
 *          '00029'                            [..] ....
 *          '00000018135000'          [14] Pool total; Unit=1 Won;
 * ++ SDL Checksum
 *          '05716'                            [ 5] Checksum
 * Message example - odds/probs with betting on
 * SPCRA - GWANGMYEONG             0088REAL010110WINO07000560005500057000550006300
 * 0560005600000015493900PLCO070002800028000280002800031000270002900000018135000057
 * 16 
 * 
 * ** Message example - odds/probs with betting on; runner 4 scratched
 * SPCRA - GWANGMYEONG             0088REAL010110WINO07000480004700048.....0005300
 * 0480004800000013206700PLCO07000240002400024.....00027000230002400000015491700056
 * 60
 * 
 * ** Message example - odds/probs with betting off; runner 4 scratched
 * SPCRA - GWANGMYEONG             0088REAL010110WINF07000480004700048.....0005300
 * 0480004800000013206700PLCF07000240002400024.....00027000230002400000015491700056
 * 42
 * </pre>
 */
public class SPDAO extends BaseDao {

	/**
	 * <pre>
	 * 0. SP로직에 맞게 Tray 로딩
	 * 1. 조회
	 * 2. 1.에의한 update or insert 결정
	 * </pre>
	 */
	public boolean insert(Connection conn, Tray sdlTray) throws AppException {
		try {
			loadSP(sdlTray);
			Tray rsTray[] = loadTray(sdlTray);

			boolean success = false;

			int count = this.findSP(conn, sdlTray); // 조회된 건 수

			if (count > 0) {
				success = super.updatePB(conn, rsTray);
			} else {
				success = super.insertPB(conn, rsTray);
			}

			//super.insertPBS(conn, rsTray);

			return success;
		} catch (AppException e) {
			Log.error("ERROR", this, "at SPDAO.insert() - " + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at SPDAO.insert()- " + ex);
			throw new AppException("", ex);
		}
	}

	/**
	 * 단,연승식 해당경주 조회
	 */
	protected int findSP(Connection conn, Tray sdlTray) throws AppException {

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
		sb.append("\n     and pool_cd in ('001','002') ");
		sb.append("\n     and race_no=:race_no     ");

		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			runner.setParams(sdlTray);
			Log.debug("DB", this, runner.toString());

			return ((Tray) runner.query(conn)).getInt("cnt");

		} catch (Exception ex) {
			Log.error("ERROR", this, "at SPTempDAO.findInfo()" + ex);
			throw new AppException("", ex);
		}
	}

	/**
	 * sdlTray로 부터 한건식의 Tray로 변형
	 * 
	 * @param sdlTray
	 * @return rsTray[]
	 * @throws AppException
	 */
	private Tray[] loadTray(Tray sdlTray) throws AppException {
		try {
			int win_runner_count = sdlTray.getInt("win_runner_count");
			int plc_runner_count = sdlTray.getInt("plc_runner_count");

			int size = win_runner_count + plc_runner_count;

			Tray rsTray[] = new NewTray[size];

			for (int i = 0, k = 0, n = 0; i < size; i++) {

				if (rsTray[i] == null) rsTray[i] = new NewTray();

				rsTray[i].setString("meet_cd", sdlTray.getString("meet_cd"));
				rsTray[i].setString("stnd_year", sdlTray.getString("stnd_year"));
				rsTray[i].setString("tms", sdlTray.getString("tms"));
				rsTray[i].setString("day_ord", sdlTray.getString("day_ord"));
				rsTray[i].setString("race_no", sdlTray.getString("race_no"));
				rsTray[i].setString("odds_seq", "1");
				rsTray[i].setString("amount", String.valueOf(0));
				if (i < win_runner_count) {
					rsTray[i].setString("pool_cd", sdlTray.getString("pool_cd", 0));
					rsTray[i].setString("odds", sdlTray.getString("win_odds", k++));
					rsTray[i].setString("runner1st", String.valueOf(k));
					rsTray[i].setString("runner2nd", String.valueOf(0));
					rsTray[i].setString("runner3rd", String.valueOf(0));
				} else {
					rsTray[i].setString("pool_cd", sdlTray.getString("pool_cd", 1));
					rsTray[i].setString("odds", sdlTray.getString("plc_odds", n++));
					rsTray[i].setString("runner1st", String.valueOf(n));
					rsTray[i].setString("runner2nd", String.valueOf(0));
					rsTray[i].setString("runner3rd", String.valueOf(0));
				}
				Log.debug("", this, rsTray[i].toString());
			}
			return rsTray;
		} catch (AppException e) {
			Log.error("ERROR", this, "at SPDAO.loadTray()" + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at SPDAO.loadTray()" + ex);
			throw new AppException("", ex);
		}
	}

	/**
	 * SP로직에 맞게 Tray 로딩
	 * 
	 * @param conn
	 * @param sdlTray
	 * @throws AppException
	 */
	private void loadSP(Tray sdlTray) throws AppException {
		String sdl = sdlTray.getString("sdl");

		try {
			int idx = 0;
			// pass header 2,30,4,4,2,4
			idx = 46;
			// body ( 3,1,2(loop),5......,14 ) * 2

			/** ********************* WIN ************************** */
			String pool_nm[] = new String[2]; // 단승, 연승 두가지 조합
			pool_nm[0] = CodeManager.getPoolCD(sdl.substring(idx, idx += 3));
			
			if (!"O".equals(sdl.substring(idx, idx += 1))) {
				//throw new AppException("sdl.cra.sp.status.not_open", new Exception("SP의 Pool Status가 오픈 상태가 아닙니다.")); 
			}
			
			int win_runner_count = StringUtil.parseInt(sdl.substring(idx, idx += 2));
			
			String win_odds[] = new String[win_runner_count]; // 해당 승식의 배당률
			
			for (int i = 0; i < win_odds.length; i++) {
				// win_odds[i] = (sdl.substring(idx, idx += 5)).replace(".", "0");
				win_odds[i] = StringUtil.rplc(sdl.substring(idx, idx += 5), ".", "0");
			}
			String win_total = sdl.substring(idx, idx += 14);

			/** ********************* PLC ************************** */
			pool_nm[1] = CodeManager.getPoolCD(sdl.substring(idx, idx += 3));
			
			if (!"O".equals(sdl.substring(idx, idx += 1))) { 
				//throw new AppException("sdl.cra.sp.status.not_open", new Exception("SP의 Pool Status가 오픈 상태가 아닙니다.")); 
			}
			
			int plc_runner_count = StringUtil.parseInt(sdl.substring(idx, idx += 2));
			
			String plc_odds[] = new String[plc_runner_count]; // 해당 승식의 배당률
			
			for (int i = 0; i < plc_odds.length; i++) {
				// plc_odds[i] = (sdl.substring(idx, idx += 5)).replace(".", "0");
				plc_odds[i] = StringUtil.rplc(sdl.substring(idx, idx += 5), ".", "0");
			}
			
			String plc_total = sdl.substring(idx, idx += 14);

			sdlTray.set("pool_cd", pool_nm);
			sdlTray.set("win_runner_count", String.valueOf(win_runner_count));
			sdlTray.set("plc_runner_count", String.valueOf(plc_runner_count));
			sdlTray.set("win_odds", win_odds);
			sdlTray.set("plc_odds", plc_odds);
			sdlTray.set("win_total", win_total);
			sdlTray.set("plc_total", plc_total);

			Log.debug(sdlTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, "at SPDAO.loadSP()" + ex);
			throw new AppException("", ex);
		}
	}
}
