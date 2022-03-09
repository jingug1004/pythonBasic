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
 * FR : 2Leg Runner Totals : 쌍,복 승식 매출액(선수 조합별 포함)
 * 
 * <pre>
 * Look at the examples
 * 
 * ++ SDL Header
 *          'FR'                          [ 2] Message code
 *          'CRA - GWANGMYEONG             '   [30] Meet name
 *          '0088'                       [ 4] Perf number
 *          'REAL'                    [ 4] Type of Perf
 *          '01'                           [ 2] Race numbe
 *          '0538'                       [ 4] Message length
 * ++ SDL Data
 *          'EX '                         [ 3] Pool
 *          'O'                            [ 1] Pool Status; O:open; F:close
 *          '07'                           [ 2] Number of runner rows followed
 *          '01'                           [ 2] Row #1
 *          '07'                           [ 2] Columns (= Number of runner totals)
 *                                                 followed
 *          '..........'                     [10] xy Runner total; unit=100 Won;
 *          '0000010588'           [..] ....
 *          '0000011861'
 *          '0000012935'
 *          '0000012186'
 *          '0000012637'
 *          '0000012518'
 *          '02'                           [ 2] Row #2
 *          '07'                           [ 2] Columns (= Number of runner totals)
 *                                                 followed
 *          '0000011672'           [10] xy Runner total; unit=100 Won;
 *          '..........'                     [..] ....
 *          '0000012137'
 *          '0000012356'
 *          '0000012199'
 *          '0000011213'
 *          '0000012015'
 *          '03'
 *          '07'
 *          '0000010515'
 *          '0000012019'
 *          '..........'
 *          '0000011702'
 *          '0000010870'
 *          '0000011895'
 *          '0000010905'
 *          '04'
 *          '07'
 *          '0000012541'
 *          '0000011537'
 *          '0000011386'
 *          '..........'
 *          '0000011559'
 *          '0000010934'
 *          '0000011835'
 *          '05'
 *          '07'
 *          '0000012694'
 *          '0000011316'
 *          '0000011949'
 *          '0000011412'
 *          '..........'
 *          '0000011931'
 *          '0000012414'
 *          '06'
 *          '07'
 *          '0000013293'
 *          '0000010929'
 *          '0000012197'
 *          '0000011200'
 *          '0000012924'
 *          '..........'
 *          '0000013397'
 *          '07'
 *          '07'
 *          '0000011782'
 *          '0000011034'
 *          '0000011137'
 *          '0000010432'
 *          '0000010715'
 *          '0000011987'
 *          '..........'                       [..] ....
 *          '00000049475800'     [14] Pool total; unit=1 Won;
 *         ++ SDL Checksum
 *          '26566'                        [ 5] Checksum
 *          
 * Message example - runner totals with betting on
 * FRCRA - GWANGMYEONG             0088REAL010538EX O070107..........0000010588000
 * 0011861000001293500000121860000012637000001251802070000011672..........000001213
 * 70000012356000001219900000112130000012015030700000105150000012019..........00000
 * 117020000010870000001189500000109050407000001254100000115370000011386..........0
 * 0000115590000010934000001183505070000012694000001131600000119490000011412.......
 * ...00000119310000012414060700000132930000010929000001219700000112000000012924...
 * .......0000013397070700000117820000011034000001113700000104320000010715000001198
 * 7..........0000004947580026566
 * 
 * FRCRA - GWANGMYEONG             0088REAL010538QU O070107..........0000013076000
 * 001402800000139440000014065000001416100000131230207....................000001349
 * 000000142690000013699000001438800000131620307..............................00000
 * 154370000015540000001257800000133920407........................................0
 * 000013875000001412300000141100507...............................................
 * ...000001425400000136110607.....................................................
 * .......00000141070707...........................................................
 * ...........0000002924320025810
 * 
 * * Message example - runner totals with betting on; runner 4 scratched 
 * FRCRA - GWANGMYEONG             0088REAL010538EX O070107..........0000010588000
 * 0011861..........00000121860000012637000001251802070000011672..........000001213
 * 7..........000001219900000112130000012015030700000105150000012019...............
 * .....0000010870000001189500000109050407.........................................
 * .............................0507000001269400000113160000011949.................
 * ...000001193100000124140607000001329300000109290000012197..........0000012924...
 * .......00000133970707000001178200000110340000011137..........0000010715000001198
 * 7..........0000003549290026145
 * 
 * FRCRA - GWANGMYEONG             0088REAL010538QU O070107..........0000013076000
 * 0014028..........0000014065000001416100000131230207....................000001349
 * 0..........0000013699000001438800000131620307...................................
 * .....0000015540000001257800000133920407.........................................
 * .............................0507...............................................
 * ...000001425400000136110607.....................................................
 * .......00000141070707...........................................................
 * ...........0000002066740025588
 * 
 * * Message example - runner totals with betting off; runner 4 scratched 
 * FRCRA - GWANGMYEONG             0088REAL010538EX F070107..........0000010588000
 * 0011861..........00000121860000012637000001251802070000011672..........000001213
 * 7..........000001219900000112130000012015030700000105150000012019...............
 * .....0000010870000001189500000109050407.........................................
 * .............................0507000001269400000113160000011949.................
 * ...000001193100000124140607000001329300000109290000012197..........0000012924...
 * .......00000133970707000001178200000110340000011137..........0000010715000001198
 * 7..........0000003549290026136
 * 
 * FRCRA - GWANGMYEONG             0088REAL010538QU F070107..........0000013076000
 * 0014028..........0000014065000001416100000131230207....................000001349
 * 0..........0000013699000001438800000131620307...................................
 * .....0000015540000001257800000133920407.........................................
 * .............................0507...............................................
 * ...000001425400000136110607.....................................................
 * .......00000141070707...........................................................
 * ...........0000002066740025579
 * 
 * 
 * </pre>
 */
public class FRDAO extends BaseDao {

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
			loadFR(sdlTray);

			Tray rsTray[] = loadTray(sdlTray);

			boolean success = false;

			success = super.updateAmountPB(conn, rsTray);

			success = super.insertPBS(conn, sdlTray);

			return success;

		} catch (AppException e) {
			Log.error("ERROR", this, "at FRDAO.insert() - " + e);
			throw e;
		} catch (Exception e) {
			Log.error("ERROR", this, "at FRDAO.insert()- " + e);
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
	private void loadFR(Tray sdlTray) throws AppException {
		String sdl = sdlTray.getString("sdl");

		try {
			int idx = 0;
			// pass header 2,30,4,4,2,4
			idx = 46;
			// 경기방식(3) 경기방식 상태(1) 경기선수 수(2) ROW #1 (2) Columns(경기선수)(2) 1번매출액(10), 선수총금액(14)
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
				String[] arrAmount = new String[runner_count];

				idx += 4; // row(2), columns(2)
				for (int i = 0; i < runner_count; i++){
					arrAmount[i] = StringUtil.substring(sdl, idx, idx += 10).replace('.', '0');
				}

				sdlTray.setString("amount" + j, arrAmount);
				Log.debug(sdlTray);
			}
			sdlTray.setString("pool_total", StringUtil.substring(sdl, idx, idx += 14).replace('.', '0'));
			

		} catch (Exception ex) {
			Log.error("ERROR", this, "at FRDAO.loadSP - " + ex);
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
				rsTray[i].setString("amount", String.valueOf( sdlTray.getLong("amount" + k, j++)*100) );
				rsTray[i].setString("odds", String.valueOf(0));
				if (j == runner_count) {
					k += 1;
					j = 0;
				}
				Log.debug("", this, rsTray[i].toString());
			}

			return rsTray;
		} catch (AppException e) {
			Log.error("ERROR", this, "at FRDAO.loadTray - " + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at FRDAO.loadTray - " + ex);
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
			tempTray.setString("pool_cd", sdlTray.getString("pool_cd"));
			tempTray.setString("pool_total", sdlTray.getString("pool_total"));
			success = ptDAO.insert(conn, tempTray);


			return success;
			
		} catch (Exception e) {
			Log.error("ERROR", this, "at FRDAO.insertPT()- " + e);
			throw new AppException("", e);
		}			
	}	
}
