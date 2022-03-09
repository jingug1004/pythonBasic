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
 * TR : TLA Runner Totals ( 선수 조합별 포함 )
 * 
 * <pre>
 * 
 *   ++ SDL Header
 *          'TR'                       [ 2] Message code
 *          'CRA - GWANGMYEONG             '   [30] Meet name
 *          '0088'                    [ 4] Perf number
 *          'REAL'                  [ 4] Type of Perf
 *          '01'                        [ 2] Race numbe
 *          '0583'                    [ 4] Message length
 *         ++ SDL Data
 *          'TLA'                   [ 3] Pool
 *          'O'                        [ 1] Pool Status; O:open; F:close
 *          '0'                         [ 1] Part number; 0:not used;
 *          '35'                       [ 2] Number of combinations 
 *                                             (R1 R2 R3 $$$$$$$$$$) followed
 *          '07'                       [ 2] Number of runners
 *          '01'                       [ 2] R1
 *          '02'                       [ 2] R2
 *          '03'                       [ 2] R3
 *          '0000015212'       [10] R1/R2/R3 Runner total; unit=100 Won;
 *          '01 02 04 0000017415'              [2][2][2][10] ....
 *          '01 02 05 0000019043'              ....
 *          '01 02 06 0000018107'
 *          '01 02 07 0000017558'
 *          '01 03 04 0000018695'
 *          '01 03 05 0000019285'
 *          '01 03 06 0000017802'
 *          '01 03 07 0000017150'
 *          '01 04 05 0000015326'
 *          '01 04 06 0000016303'
 *          '01 04 07 0000014814'
 *          '01 05 06 0000018953'
 *          '01 05 07 0000017934'
 *          '01 06 07 0000018618'
 *          '02 03 04 0000018054'
 *          '02 03 05 0000016559'
 *          '02 03 06 0000017925'
 *          '02 03 07 0000017418'
 *          '02 04 05 0000017026'
 *          '02 04 06 0000017772'
 *          '02 04 07 0000017575'
 *          '02 05 06 0000016434'
 *          '02 05 07 0000016301'
 *          '02 06 07 0000017046'
 *          '03 04 05 0000018604'
 *          '03 04 06 0000018380'
 *          '03 04 07 0000017968'
 *          '03 05 06 0000018177'
 *          '03 05 07 0000016091'
 *          '03 06 07 0000018793'
 *          '04 05 06 0000017890'
 *          '04 05 07 0000017529'
 *          '04 06 07 0000018288'
 *          '05 06 07 0000018668'              ....
 *          '00000061471300'      [14] Pool total; unit=1 Won;
 *         ++ SDL Checksum
 *          '29295'                        [ 5] Checksum 
 *  Message example - runner totals with betting on
 * TRCRA - GWANGMYEONG             0088REAL010583TLAO03507010203000001521201020400
 * 00017415010205000001904301020600000181070102070000017558010304000001869501030500
 * 00019285010306000001780201030700000171500104050000015326010406000001630301040700
 * 00014814010506000001895301050700000179340106070000018618020304000001805402030500
 * 00016559020306000001792502030700000174180204050000017026020406000001777202040700
 * 00017575020506000001643402050700000163010206070000017046030405000001860403040600
 * 00018380030407000001796803050600000181770305070000016091030607000001879304050600
 * 000178900405070000017529040607000001828805060700000186680000006147130029295
 * 
 *  Message example - runner totals with betting on; runner 4 scratched
 * TRCRA - GWANGMYEONG             0088REAL010583TLAO035070102030000015212010204..
 * ........010205000001904301020600000181070102070000017558010304..........01030500
 * 0001928501030600000178020103070000017150010405..........010406..........010407..
 * ........010506000001895301050700000179340106070000018618020304..........02030500
 * 0001655902030600000179250203070000017418020405..........020406..........020407..
 * ........020506000001643402050700000163010206070000017046030405..........030406..
 * ........030407..........030506000001817703050700000160910306070000018793040506..
 * ........040507..........040607..........05060700000186680000003530740028671
 * 
 *  Message example - runner totals with betting off; runner 4 scratched
 * TRCRA - GWANGMYEONG             0088REAL010583TLAF035070102030000015212010204..
 * ........010205000001904301020600000181070102070000017558010304..........01030500
 * 0001928501030600000178020103070000017150010405..........010406..........010407..
 * ........010506000001895301050700000179340106070000018618020304..........02030500
 * 0001655902030600000179250203070000017418020405..........020406..........020407..
 * ........020506000001643402050700000163010206070000017046030405..........030406..
 * ........030407..........030506000001817703050700000160910306070000018793040506..
 * ........040507..........040607..........05060700000186680000003530740028662
 * 
 * 
 * </pre>
 */
public class TRDAO extends BaseDao {

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
			loadTR(sdlTray);
			Tray rsTray[] = loadTray(sdlTray);

			boolean success = false;

			success = super.updateAmountPB(conn, rsTray);

			success = super.insertPBS(conn, sdlTray);
			
			return success;

		} catch (AppException e) {
			Log.error("ERROR", this, "at TRDAO.insert() - " + e);
			try {
				conn.rollback();
			} catch (Exception e1) {}
			throw e;
		} catch (Exception e) {
			Log.error("ERROR", this, "at TRDAO.insert()- " + e);
			try {
				conn.rollback();
			} catch (Exception e1) {}
			throw new AppException("", e);
		} 
	}

	/**
	 * TR로직에 맞게 Tray 로딩
	 * 
	 * @param conn
	 * @param sdlTray
	 * @throws AppException
	 */
	private void loadTR(Tray sdlTray) throws AppException {
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
				sdlTray.setString("amount" + i, StringUtil.substring(sdl, idx, idx += 10).replace('.', '0'));
			}
			sdlTray.setString("pool_total", StringUtil.substring(sdl, idx, idx += 14).replace('.', '0'));
			/*
			Log.debug("", this, "###########################################################################################\n"
								+sdlTray.toString()
								+"\n###########################################################################################");
			*/
		} catch (Exception ex) {
			Log.error("ERROR", this, "at TRDAO.loadSP - " + ex);
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
				rsTray[i].setString("odds", String.valueOf(0));
				rsTray[i].setString("amount", String.valueOf(sdlTray.getLong("amount" + i)*100));

				Log.debug("", this, "###########################################################################################\n" + rsTray[i].toString()
						+ "\n###########################################################################################");
			}

		} catch (AppException e) {
			Log.error("ERROR", this, "at TRDAO.loadTray - " + e);
			throw e;
		} catch (Exception e) {
			Log.error("ERROR", this, "at TRDAO.loadTray - " + e);
			throw new AppException("", e);
		}

		return rsTray;
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
