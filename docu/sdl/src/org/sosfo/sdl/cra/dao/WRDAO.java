package org.sosfo.sdl.cra.dao;

import java.sql.Connection;

import org.sosfo.framework.dao.BaseDao;
import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.tray.NewTray;
import org.sosfo.framework.tray.Tray;
import org.sosfo.framework.util.StringUtil;
import org.sosfo.sdl.cra.common.CodeManager;

/**
 * WR : WPS Runner Totals , 단,연승 ( WIN, PLC )식 매출액(선수별)
 * 
 * <pre>
 * ee the examples
 * 
 * + SDL Header
 *          'WR'                               [ 2] Message code
 *          'CRA - GWANGMYEONG             '   [30] Meet name
 *          '0088'                             [ 4] Perf number
 *          'REAL'                           [ 4] Type of Perf
 *          '01'                                 [ 2] Race numbe
 *          '0180'                             [ 4] Message length
 * ++ SDL Data
 *          'WIN'                             [ 3] Pool
 *          'O'                                  [ 1] Pool Status; O:open; F:close
 *          '07'                                 [ 2] Number of runner totals followed
 *          '0000022446'                 [10] Runner total 1; unit=100 Won; 22446=2,244,600 Won
 *          '0000022658'                 [..] ....
 *          '0000022083'
 *          '0000022872'
 *          '0000020043'
 *          '0000022434'
 *          '0000022403'                 [..] ....
 *          '00000000154939'         [14] Pool total;  unit=100 Won; 154939=15,493,900 Won
 *          'PLC'                              [ 3] Pool
 *          'O'                                  [ 1] Pool Status; O:open; F:close
 *          '07'                                 [ 2] Number of runner totals followed
 *          '0000025820'                 [10] Runner total 1; unit=100 Won;
 *          '0000026604'                 [..] ....
 *          '0000026183'
 *          '0000026433'
 *          '0000023658'
 *          '0000026896'
 *          '0000025756'                 [..] ....
 *          '00000000181350'         [14] Pool total; unit=100 Won;
 * ++ SDL Checksum
 *          '09203'                            [ 5] Checksum
 * 
 * * Message example - runner totals with betting on
 * WRCRA - GWANGMYEONG             0088REAL010180WINO07000002244600000226580000022
 * 083000002287200000200430000022434000002240300000000154939PLCO0700000258200000026
 * 604000002618300000264330000023658000002689600000257560000000018135009203
 * 
 *  Message example - runner totals with betting on; runner 4 scratched
 * WRCRA - GWANGMYEONG             0088REAL010180WINO07000002244600000226580000022
 * 083..........00000200430000022434000002240300000000132067PLCO0700000258200000026
 * 6040000026183..........0000023658000002689600000257560000000015491709121
 * 
 *  Message example - runner totals with betting off; runner 4 scratched
 * WRCRA - GWANGMYEONG             0088REAL010180WINF07000002244600000226580000022
 * 083..........00000200430000022434000002240300000000132067PLCF0700000258200000026
 * 6040000026183..........0000023658000002689600000257560000000015491709103
 * 
 * </pre>
 */
public class WRDAO extends BaseDao {

	/**
	 * Odds가 선 입력되어있다는 가정하에 Amount만 Update 한다.
	 * 
	 * @param conn
	 * @param sdlTray
	 * @return
	 * @throws AppException
	 */
	public boolean insert(Connection conn, Tray sdlTray) throws AppException {

		try {
			loadWR(sdlTray);
			Tray rsTray[] = loadTray(sdlTray);

			boolean success = false;
			
			success = super.updateAmountPB(conn, rsTray);

			success = super.insertPBS(conn, sdlTray);

			return success;

		} catch (AppException e) {
			Log.error("ERROR", this, "at WRDAO.insert() - " + e);
			throw e;
		} catch (Exception e) {
			Log.error("ERROR", this, "at WRDAO.insert()- " + e);

			throw new AppException("", e);
		} 
	}
	
	/**
	 * SP로직에 맞게 Tray 로딩
	 * 
	 * @param conn
	 * @param sdlTray
	 * @throws AppException
	 */
	private void loadWR(Tray sdlTray) throws AppException {
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
				//win_odds[i] = (sdl.substring(idx, idx += 10)).replace(".", "0");
				win_odds[i] =  StringUtil.rplc(sdl.substring(idx, idx += 10), ".", "0");
			}
			String win_total = sdl.substring(idx, idx += 14);

			
			/** ********************* PLC ************************** */
			//단승 취소로 인해 문자열 미포함 수신시
			if(sdl.indexOf("PLC")!=-1){ 
				pool_nm[1] = CodeManager.getPoolCD(sdl.substring(idx, idx += 3));
				
				if (!"O".equals(sdl.substring(idx, idx += 1))) { 
					//throw new AppException("sdl.cra.sp.status.not_open", new Exception("SP의 Pool Status가 오픈 상태가 아닙니다.")); 
				}				
				int plc_runner_count = StringUtil.parseInt(sdl.substring(idx, idx += 2));
				String plc_odds[] = new String[plc_runner_count]; // 해당 승식의 배당률
				
				for (int i = 0; i < plc_odds.length; i++) {
					//plc_odds[i] = (sdl.substring(idx, idx += 10)).replace(".", "0");
					plc_odds[i] =  StringUtil.rplc(sdl.substring(idx, idx += 10), ".", "0");
				}
				String plc_total = sdl.substring(idx, idx += 14).replace('.', '0');

				sdlTray.set("pool_cd", pool_nm);
				sdlTray.set("win_runner_count", String.valueOf(win_runner_count));
				sdlTray.set("plc_runner_count", String.valueOf(plc_runner_count));
				sdlTray.set("win_odds", win_odds);
				sdlTray.set("plc_odds", plc_odds);
				sdlTray.set("win_total", win_total);
				sdlTray.set("plc_total", plc_total);
			}else{
				
				pool_nm[1]="002";
				        
				sdlTray.set("pool_cd", pool_nm);
				sdlTray.set("win_runner_count", String.valueOf(win_runner_count));
				sdlTray.set("win_odds", win_odds);
				sdlTray.set("win_total", win_total);
				
				String tmep_amount[] =  new String[win_runner_count];
				for (int i = 0; i < tmep_amount.length; i++) {
					tmep_amount[i]="0";
				}
				
				sdlTray.set("plc_runner_count", String.valueOf(win_runner_count));
				sdlTray.set("plc_odds", tmep_amount);
				sdlTray.set("plc_total", "0");
			}

			Log.debug(sdlTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, "at WRDAO.loadSP - " + ex);
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
				rsTray[i].setString("odds", String.valueOf(0));
				if (i < win_runner_count) {
					rsTray[i].setString("pool_cd", sdlTray.getString("pool_cd", 0));
					rsTray[i].setString("amount", String.valueOf(sdlTray.getLong("win_odds", k++)*100));
					rsTray[i].setString("runner1st", String.valueOf(k));
					rsTray[i].setString("runner2nd", String.valueOf(0));
					rsTray[i].setString("runner3rd", String.valueOf(0));
				} else {
					rsTray[i].setString("pool_cd", sdlTray.getString("pool_cd", 1));
					rsTray[i].setString("amount", String.valueOf(sdlTray.getLong("plc_odds", n++)*100));
					rsTray[i].setString("runner1st", String.valueOf(n));
					rsTray[i].setString("runner2nd", String.valueOf(0));
					rsTray[i].setString("runner3rd", String.valueOf(0));
				}
				Log.debug("", this, rsTray[i].toString());
			}

			return rsTray;
		} catch (AppException e) {
			Log.error("ERROR", this, "at WRDAO.loadTray - " + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at WRDAO.loadTray - " + ex);
			throw new AppException("", ex);
		}
	}
	
	/**
	 * 기존 PT 전문을 사용하지 않고 중간 매출액의 POOL_TOTAL를 가지고 입력한다.
	 * @param conn
	 * @param sdlTray
	 * @return
	 * @throws AppException
	 */
	public boolean insertPT(Connection conn, Tray sdlTray) throws AppException {
		
		boolean success = false;
		
		try {
			// 승식별 매출액 입력
			NewPTDAO ptDAO = new NewPTDAO();
			Tray tempTray = new NewTray();
			
			tempTray.clear();
			tempTray.setString("meet_cd", sdlTray.getString("meet_cd"));
			tempTray.setString("stnd_year", sdlTray.getString("stnd_year"));
			tempTray.setString("tms", sdlTray.getString("tms"));
			tempTray.setString("day_ord", sdlTray.getString("day_ord"));
			tempTray.setString("race_no", sdlTray.getString("race_no"));
			tempTray.setString("pool_cd", "001");
			tempTray.setString("pool_total", String.valueOf(sdlTray.getLong("win_total")*100));
			success = ptDAO.insert(conn, tempTray);
			
			tempTray.clear();
			tempTray.setString("meet_cd", sdlTray.getString("meet_cd"));
			tempTray.setString("stnd_year", sdlTray.getString("stnd_year"));
			tempTray.setString("tms", sdlTray.getString("tms"));
			tempTray.setString("day_ord", sdlTray.getString("day_ord"));
			tempTray.setString("race_no", sdlTray.getString("race_no"));
			tempTray.setString("pool_cd", "002");
			tempTray.setString("pool_total", String.valueOf(sdlTray.getLong("plc_total")*100));
			success = ptDAO.insert(conn, tempTray);

			return success;
			
		} catch (Exception e) {
			Log.error("ERROR", this, "at WRDAO.insertPT()- " + e);
			throw new AppException("", e);
		}			
	}
}
