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
 * TP : TLA Probables , 삼복승 중간 배당률 ( 선수 조합별 포함 )
 * 
 * @author <pre>
 * ++ SDL Header
 *          'TP'                       [ 2] Message code
 *          'CRA - GWANGMYEONG             '   [30] Meet name
 *          '0088'                    [ 4] Perf number
 *          'REAL'                 [ 4] Type of Perf
 *          '01'                        [ 2] Race numbe
 *          '0408'                    [ 4] Message length
 *  ++ SDL Data
 *          'TLA'            [ 3] Pool
 *          'O'                 [ 1] Pool Status; O:open; F:close
 *          '0'                  [ 1] Part number; 0:not used;
 *          '35'                [ 2] Number of combinations
 *                                     (R1 R2 R3 ppppp) followed
 *          '07'                [ 2] Number of runners
 *          '01'                [ 2] R1
 *          '02'                [ 2] R2
 *          '03'                [ 2] R3
 *          '00283'          [ 5] R1/R2/R3 Runner prob
 *          '01 02 04 00247'            [2][2][2][5] ....
 *          '01 02 05 00226'             ....
 *          '01 02 06 00238'
 *          '01 02 07 00245'
 *          '01 03 04 00230'
 *          '01 03 05 00223'
 *          '01 03 06 00242'
 *          '01 03 07 00251'
 *          '01 04 05 00281'
 *          '01 04 06 00264'
 *          '01 04 07 00290'
 *          '01 05 06 00227'
 *          '01 05 07 00240'
 *          '01 06 07 00231'
 *          '02 03 04 00238'
 *          '02 03 05 00260'
 *          '02 03 06 00240'
 *          '02 03 07 00247'
 *          '02 04 05 00253'
 *          '02 04 06 00242'
 *          '02 04 07 00245'
 *          '02 05 06 00262'
 *          '02 05 07 00264'
 *          '02 06 07 00252'
 *          '03 04 05 00231'
 *          '03 04 06 00234'
 *          '03 04 07 00239'
 *          '03 05 06 00237'
 *          '03 05 07 00267'
 *          '03 06 07 00229'
 *          '04 05 06 00241'
 *          '04 05 07 00245'
 *          '04 06 07 00235'
 *          '05 06 07 00231'        ....
 *          '00000061471300'     [14] Pool total; unit=1 Won;
 *         ++ SDL Checksum
 *          '20501'                       [ 5] Checksum
 * 
 * 
 *  Message example - odds/probs with betting on
 * TPCRA - GWANGMYEONG             0088REAL010408TLAO0350701020300283010204002470102050022601020600238010207002450103040023001030500223010306002420103070025101040500281010406002640104070029001050600227010507002400106070023102030400238020305002600203060024002030700247020405002530204060024202040700245020506002620205070026402060700252030405002310304060023403040700239030506002370305070026703060700229040506002410405070024504060700235050607002310000006147130020501
 * 
 *  Message example - odds/probs with betting on; runner 4 scratched
 * TPCRA - GWANGMYEONG             0088REAL010408TLAO0350701020300162010204.....010205001300102060013601020700141010304.....010305001280103060013901030700144010405.....010406.....010407.....010506001300105070013801060700133020304.....020305001490203060013802030700142020405.....020406.....020407.....020506001500205070015202060700145030405.....030406.....030407.....030506001360305070015403060700132040506.....040507.....040607.....050607001320000003530740020177
 * 
 *  Message example - odds/probs with betting off; runner 4 scratched
 * TPCRA - GWANGMYEONG             0088REAL010408TLAF0350701020300162010204.....010205001300102060013601020700141010304.....010305001280103060013901030700144010405.....010406.....010407.....010506001300105070013801060700133020304.....020305001490203060013802030700142020405.....020406.....020407.....020506001500205070015202060700145030405.....030406.....030407.....030506001360305070015403060700132040506.....040507.....040607.....050607001320000003530740020168
 * 
 * </pre>
 */
public class TPDAO extends BaseDao {

	/**
	 * <pre>
	 * 0. TP 로직에 맞게 Tray 로딩
	 * 1. 조회
	 * 2. 1.에의한 update or insert 결정
	 * </pre>
	 */
	public boolean insert(Connection conn, Tray sdlTray) throws AppException {
		try {
			loadTP(sdlTray);

			Tray rsTray[] = loadTray(sdlTray);

			boolean success = false;

			int count = super.findInfo(conn, sdlTray); // 조회된 건 수

			if (count > 0) {
				super.updatePB(conn, rsTray);
			} else {
				super.insertPB(conn, rsTray);
			}

			//super.insertPBS(conn, sdlTray);

			Log.debug("in PBDAO.insert success : " + success);
			return success;
		} catch (AppException e) {
			Log.error("ERROR", this, "at TPDAO.insert() - " + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at TPDAO.insert()- " + ex);
			throw new AppException("", ex);
		}
	}

	/**
	 * TP로직에 맞게 Tray 로딩
	 * 
	 * @param conn
	 * @param sdlTray
	 * @throws AppException
	 */
	private void loadTP(Tray sdlTray) throws AppException {
		String sdl = sdlTray.getString("sdl");
		Log.debug("\n\nsdl : " + sdl + "\n\n");
		try {
			// pass header 2,30,4,4,2,4
			int idx = 46;
			// 경기방식(3) 경기방식 상태(1) Part Number(1) ? 선수조합 수(2) 경기선수 수(2) R1(2) R2(2) R3(2) 매출액(10) 선수총금액(14)
			// pool_nm, pool_status, part_number, combination_count, runner_count, r1, r2, r3, amount, total
			// body ( 3,1,1,2,2, (loop)2,2,2,10 * 선수조합 수,14 )

			String pool_nm = StringUtil.substring(sdl, idx, idx += 3);
			String pool_cd = CodeManager.getPoolCD(pool_nm);
			String pool_status = StringUtil.substring(sdl, idx, idx += 1);
			
			if (!"O".equals(pool_status)) { 
				//throw new AppException("sdl.cra.sp.status.not_open", new Exception(pool_nm + "의 Pool Status가 오픈 상태가 아닙니다.")); 
			}
			
			String part_number = StringUtil.substring(sdl, idx, idx += 1);
			int combination_count = StringUtil.parseInt(StringUtil.substring(sdl, idx, idx += 2));
			int runner_count = StringUtil.parseInt(StringUtil.substring(sdl, idx, idx += 2));

			sdlTray.setString("pool_cd", pool_cd);
			sdlTray.setString("pool_status", pool_status);
			sdlTray.setString("part_number", part_number);
			sdlTray.setString("combination_count", String.valueOf(combination_count));
			sdlTray.setString("runner_count", String.valueOf(runner_count));

			for (int i = 0; i < combination_count; i++) {
				sdlTray.setString("r1st" + i, StringUtil.substring(sdl, idx, idx += 2));
				sdlTray.setString("r2nd" + i, StringUtil.substring(sdl, idx, idx += 2));
				sdlTray.setString("r3rd" + i, StringUtil.substring(sdl, idx, idx += 2));
				sdlTray.setString("odds" + i, StringUtil.substring(sdl, idx, idx += 5).replace('.', '0'));
			}
			
			sdlTray.setString("total", StringUtil.substring(sdl, idx, idx += 14));
			/*
			Log.debug("", this, "###########################################################################################\n"
								+sdlTray.toString()
								+"\n###########################################################################################");
			*/
		} catch (Exception ex) {
			Log.error("ERROR", this, "at TPDAO.loadSP - " + ex);
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
		int combination_count = sdlTray.getInt("combination_count");

		Log.debug("", this, "combination_count [" + combination_count + "]");

		Tray rsTray[] = new NewTray[combination_count];

		try {
			for (int i = 0; i < combination_count; i++) {
				if (rsTray[i] == null) rsTray[i] = new NewTray();
				rsTray[i].setString("meet_cd", sdlTray.getString("meet_cd"));
				rsTray[i].setString("stnd_year", sdlTray.getString("stnd_year"));
				rsTray[i].setString("tms", sdlTray.getString("tms"));
				rsTray[i].setString("day_ord", sdlTray.getString("day_ord"));
				rsTray[i].setString("race_no", sdlTray.getString("race_no"));
				rsTray[i].setString("pool_cd", sdlTray.getString("pool_cd"));
				rsTray[i].setString("odds_seq", String.valueOf(1));
				rsTray[i].setString("runner1st", String.valueOf(StringUtil.parseInt(sdlTray.getString("r1st" + i), 0)));
				rsTray[i].setString("runner2nd", String.valueOf(StringUtil.parseInt(sdlTray.getString("r2nd" + i), 0)));
				rsTray[i].setString("runner3rd", String.valueOf(StringUtil.parseInt(sdlTray.getString("r3rd" + i), 0)));
				rsTray[i].setString("amount", String.valueOf(0));
				rsTray[i].setString("odds", sdlTray.getString("odds" + i));

				Log.debug("", this, "###########################################################################################\n" + rsTray[i].toString()
						+ "\n###########################################################################################");
			}

		} catch (AppException e) {
			Log.error("ERROR", this, "at TPDAO.loadTray - " + e);
			throw e;
		} catch (Exception e) {
			Log.error("ERROR", this, "at TPDAO.loadTray - " + e);
			throw new AppException("", e);
		}

		return rsTray;
	}
}
