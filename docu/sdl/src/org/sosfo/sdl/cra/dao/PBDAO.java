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
 * FR : 2Leg Probables : 쌍,복 승식 중간배당률(선수 조합별 포함)
 * 
 * @author <pre>
 * ook at the examples
 * 
 * + SDL Header
 *          'PB'                [ 2] Message code
 *          'CRA - GWANGMYEONG             '   [30] Meet name
 *          '0088'             [ 4] Perf number
 *          'REAL'           [ 4] Type of Perf
 *          '01'                 [ 2] Race numbe
 *          '0293'             [ 4] Message length
 * ++ SDL Data
 *          'EX '         [ 3] Pool
 *          'O'            [ 1] Pool Status; O:open; F:close
 *          '07'           [ 2] Number of runner totals(rows) followed
 *          '01'           [ 2] Row #1
 *          '07'           [ 2] Columns (= Number of runner probs) followed
 *          '.....'          [10] xy Runner prob;
 *          '00327'     [..] ....
 *          '00292'
 *          '00268'
 *          '00284'
 *          '00274'
 *          '00277'
 *          '02'           [ 2] Row #2
 *          '07'           [ 2] Columns (= Number of runner probs) followed
 *          '00297'     [10] xy Runner prob;
 *          '.....'          [..] ....
 *          '00285'
 *          '00280'
 *          '00284'
 *          '00309'
 *          '00288'
 *          '03'
 *          '07'
 *          '00329'
 *          '00288'
 *          '.....'
 *          '00296'
 *          '00319'
 *          '00291'
 *          '00318'
 *          '04'
 *          '07'
 *          '00276'
 *          '00300'
 *          '00304'
 *          '.....'
 *          '00300'
 *          '00317'
 *          '00293'
 *          '05'
 *          '07'
 *          '00273'
 *          '00306'
 *          '00290'
 *          '00303'
 *          '.....'
 *          '00290'
 *          '00279'
 *          '06'
 *          '07'
 *          '00261'
 *          '00317'
 *          '00284'
 *          '00309'
 *          '00268'
 *          '.....'
 *          '00259'
 *          '07'
 *          '07'
 *          '00294'
 *          '00314'
 *          '00311'
 *          '00332'
 *          '00323'
 *          '00289'
 *          '.....'                           [..] ....
 *          '00000049475800'    [14] Pool total; unit=1 Won;
 *         ++ SDL Checksum
 *          '14709'                      [ 5] Checksum
 * 
 *  ** Message example - odds/probs with betting on
 * PBCRA - GWANGMYEONG             0088REAL010293EX O070107.....003270029200268002
 * 840027400277020700297.....002850028000284003090028803070032900288.....0029600319
 * 00291003180407002760030000304.....003000031700293050700273003060029000303.....00
 * 2900027906070026100317002840030900268.....00259070700294003140031100332003230028
 * 9.....0000004947580014709
 * 
 * PBCRA - GWANGMYEONG             0088REAL010293QU O070107.....001570014600147001
 * 4600145001560207..........00152001430014900142001560307...............0013300132
 * 00163001530407....................0014800145001450507.........................00
 * 144001500607..............................001450707.............................
 * ......0000002924320014183
 *  Message example - odds/probs with betting on; runner 4 scratched
 * PBCRA - GWANGMYEONG             0088REAL010293EX O070107.....0023500209.....002
 * 040019700198020700213.....00205.....00204002220020703070023600207..........00229
 * 00209002280407...................................0507001960022000208..........00
 * 208002000607001870022700204.....00192.....001850707002110022500223.....002320020
 * 7.....0000003549290014355
 * 
 * PBCRA - GWANGMYEONG             0088REAL010293QU O070107.....0011100103.....001
 * 0300102001100207..........00107.....0010600101001100307....................00093
 * 00115001080407...................................0507.........................00
 * 101001060607..............................001030707.............................
 * ......0000002066740013994
 *  Message example - odds/probs with betting off; runner 4 scratched 
 * PBCRA - GWANGMYEONG             0088REAL010293EX F070107.....0023500209.....002
 * 040019700198020700213.....00205.....00204002220020703070023600207..........00229
 * 00209002280407...................................0507001960022000208..........00
 * 208002000607001870022700204.....00192.....001850707002110022500223.....002320020
 * 7.....0000003549290014346
 * 
 * PBCRA - GWANGMYEONG             0088REAL010293QU F070107.....0011100103.....001
 * 0300102001100207..........00107.....0010600101001100307....................00093
 * 00115001080407...................................0507.........................00
 * 101001060607..............................001030707.............................
 * ......0000002066740013985
 * 
 * </pre>
 */
public class PBDAO extends BaseDao {

	/**
	 * <pre>
	 * 0. PB 로직에 맞게 Tray 로딩
	 * 1. 조회
	 * 2. 1.에의한 update or insert 결정
	 * </pre>
	 */
	public boolean insert(Connection conn, Tray sdlTray) throws AppException {
		try {
			loadFR(sdlTray);

			Tray rsTray[] = loadTray(sdlTray);

			boolean success = false;

			int count = super.findInfo(conn, sdlTray); // 조회된 건 수

			if (count > 0) {
				success = super.updatePB(conn, rsTray);
			} else {
				success = super.insertPB(conn, rsTray);
			}

			//super.insertPBS(conn, rsTray);
			
			Log.debug("in PBDAO.insert success : " + success);
			return success;
		} catch (AppException e) {
			Log.error("ERROR", this, "at PBDAO.insert() - " + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at PBDAO.insert()- " + ex);
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
	private void loadFR(Tray sdlTray) throws AppException {
		String sdl = sdlTray.getString("sdl");

		try {
			int idx = 0;
			// pass header 2,30,4,4,2,4
			idx = 46;
			// 경기방식(3) 경기방식 상태(1) 경기선수 수(2) ROW #1 (2) Columns(경기선수)(2) 1번배당률(5), 선수총금액(14)
			// body ( 3,1,2,2,2,(loop),10 * 경기선수 수,14 ) * 경기선수 수

			String pool_nm = StringUtil.substring(sdl, idx, idx += 3);
			String pool_cd = CodeManager.getPoolCD(pool_nm);
			String pool_status = StringUtil.substring(sdl, idx, idx += 1);

			if (!"O".equals(pool_status)) { 
				//throw new AppException("sdl.cra.sp.status.not_open", new Exception(pool_nm + "의 Pool Status가 오픈 상태가 아닙니다.")); 
			}
			
			int runner_count = StringUtil.parseInt(StringUtil.substring(sdl, idx, idx += 2));

			sdlTray.setString("pool_cd", pool_cd);
			sdlTray.setString("pool_status", pool_status);
			sdlTray.setString("runner_count", String.valueOf(runner_count));

			for (int j = 1; j <= runner_count; j++) {
				String[] arrOdds = new String[runner_count];

				idx += 4; // row(2), columns(2)
				for (int i = 0; i < runner_count; i++){
					arrOdds[i] = StringUtil.substring(sdl, idx, idx += 5).replace('.', '0');
				}
				sdlTray.setString("odds" + j, arrOdds);
				//Log.debug(sdlTray);
			}
			sdlTray.setString("total", StringUtil.substring(sdl, idx, idx += 14));

		} catch (Exception ex) {
			Log.error("ERROR", this, "at PBDAO.loadSP - " + ex);
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
		int runner_count = sdlTray.getInt("runner_count");
		int size = runner_count * runner_count;
		Tray rsTray[] = new NewTray[size];
		try {
			for (int i = 0, k = 1, j = 0; i < size; i++) {
				if (rsTray[i] == null) rsTray[i] = new NewTray();

				rsTray[i].setString("meet_cd", sdlTray.getString("meet_cd"));
				rsTray[i].setString("stnd_year", sdlTray.getString("stnd_year"));
				rsTray[i].setString("tms", sdlTray.getString("tms"));
				rsTray[i].setString("day_ord", sdlTray.getString("day_ord"));
				rsTray[i].setString("race_no", sdlTray.getString("race_no"));
				rsTray[i].setString("pool_cd", sdlTray.getString("pool_cd"));
				rsTray[i].setString("odds_seq", "1");
				rsTray[i].setString("runner1st", String.valueOf(k));
				rsTray[i].setString("runner2nd", String.valueOf(j + 1));
				rsTray[i].setString("runner3rd", String.valueOf(0));
				rsTray[i].setString("odds", sdlTray.getString("odds" + k, j++));
				rsTray[i].setString("amount", String.valueOf(0));
				if (j == runner_count) {
					k += 1;
					j = 0;
				}
				Log.debug("", this, rsTray[i].toString());
			}

			return rsTray;
		} catch (AppException e) {
			Log.error("ERROR", this, "at PBDAO.loadTray - " + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at PBDAO.loadTray - " + ex);
			throw new AppException("", ex);
		}
	}
}
