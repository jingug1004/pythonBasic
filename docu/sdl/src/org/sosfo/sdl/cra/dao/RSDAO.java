package org.sosfo.sdl.cra.dao;

import java.sql.Connection;

import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.sql.QueryRunner;
import org.sosfo.framework.tray.NewTray;
import org.sosfo.framework.tray.Tray;
import org.sosfo.framework.util.StringUtil;
import org.sosfo.sdl.cra.common.CodeManager;

/**
 * RS : Prices
 * 
 * @author <pre>
 *  Message layout - for WIN
 *         ++ SDL Header
 *             'RS'                                [ 2] Message code
 *             'CRA - GWANGMYEONG             '   [30] Meet name
 *             '0088'                             [ 4] Perf number
 *             'REAL'                          [ 4] Type of Perf
 *             '01'                                 [ 2] Race numbe
 *             '0021'                             [ 4] Message length
 *         ++ SDL Data
 *             'WIN'                             [ 3] Pool
 *             'U'                                  [ 1] Race status; U:unofficial; O:official
 *             '01'                                 [ 2] Number of prices followed
 *             'N'                                  [ 1] Price code; N:nomal; L:refund less commission; R:refund
 *             '01'                                 [ 2] Number of legs/runners
 *             '05'                                 [ 2] Runner string
 *             '0000000053'                 [10] Price; unit=10 Won;
 *         ++ SDL Checksum
 *             '01184'                            [ 5] Checksum
 *  Message layout - for PLC
 *          'RSCRA - GWANGMYEONG             0088REAL01'
 *          '0036'
 *          'PLC U'
 *          '02'
 *          'N 01 05 0000000027'
 *          'N 01 03 0000000024'
 *          '01931'
 *  Message layout - for EXA &amp; QNL
 *          'RSCRA - GWANGMYEONG             0088REAL01'
 *          '0024'
 *          'EX U'
 *          '01'
 *          'N 02 05/03 0000000208'
 *          '01284'
 *  Message layout - for TLA
 *          'RSCRA - GWANGMYEONG             0088REAL01'
 *          '0027'
 *          'TLA U'
 *          '01'
 *          'N 03 01/03/05 0000000128'
 *          '01466'
 * 
 *  Message example - unofficial prices
 * RSCRA - GWANGMYEONG             0088REAL010021WINU01N0105000000005301184
 * RSCRA - GWANGMYEONG             0088REAL010036PLCU02N01050000000027N0103000000002401931
 * RSCRA - GWANGMYEONG             0088REAL010024EX U01N0205/03000000020801284
 * RSCRA - GWANGMYEONG             0088REAL010024QU U01N0203/05000000009301295
 * RSCRA - GWANGMYEONG             0088REAL010027TLAU01N0301/03/05000000012801466
 * 
 * 
 * ** Message example - official prices
 * RSCRA - GWANGMYEONG             0088REAL010021WINO01N0105000000005301178
 * RSCRA - GWANGMYEONG             0088REAL010036PLCO02N01050000000027N0103000000002401925
 * RSCRA - GWANGMYEONG             0088REAL010024EX O01N0205/03000000020801278
 * RSCRA - GWANGMYEONG             0088REAL010024QU O01N0203/05000000009301289
 * RSCRA - GWANGMYEONG             0088REAL010027TLAO01N0301/03/05000000012801460
 * 
 * 
 * </pre>
 */
public class RSDAO {

	/**
	 * <pre>
	 * 0. RS 로직에 맞게 Tray 로딩
	 * 삭제 후 재 입력
	 * </pre>
	 */

	public boolean insert(Connection conn, Tray sdlTray) throws AppException {
		try {
			loadRS(sdlTray);
			Tray rsTray[] = loadTray(sdlTray);

			// Official 자료만 입력한다.
			if(!"O".equals(sdlTray.getString("race_status"))){
				return false;
			} 
			
			boolean success = false;

			conn.setAutoCommit(false);

			deleteRS(conn, sdlTray);

			success = insertRS(conn, rsTray);

			conn.commit();

			return success;

		} catch (AppException e) {
			Log.error("ERROR", this, "at RSDAO.insert() - " + e);
			try {
				conn.rollback();
			} catch (Exception e1) {}
			throw e;
		} catch (Exception e) {
			Log.error("ERROR", this, "at RSDAO.insert()- " + e);
			try {
				conn.rollback();
			} catch (Exception e1) {}
			throw new AppException("", e);
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception e) {
				throw new AppException("", e);
			}
		}
	}

	/**
	 * PT로직에 맞게 Tray 로딩
	 * 
	 * @param conn
	 * @param sdlTray
	 * @throws AppException
	 */
	private void loadRS(Tray sdlTray) throws AppException {
		String sdl = sdlTray.getString("sdl");
		try {
			// pass header 2,30,4,4,2,4
			int idx = 46;

			/*
			'WIN'                             [ 3] Pool
			'U'                                  [ 1] Race status; U:unofficial; O:official
			'01'                                 [ 2] Number of prices followed
			'N'                                  [ 1] Price code; N:nomal; L:refund less commission; R:refund
			'01'                                 [ 2] Number of legs/runners
			'05'                                 [ 2] Runner string
			'0000000053'                 [10] Prices(확정배당 5.3)

			 */
			String pool_name = StringUtil.substring(sdl, idx, idx += 3);
			String race_status = StringUtil.substring(sdl, idx, idx += 1);
			String row_count = StringUtil.substring(sdl, idx, idx += 2);

			sdlTray.setString("pool_cd", CodeManager.getPoolCD(pool_name));
			sdlTray.setString("race_status", race_status);
			sdlTray.setString("row_count", row_count);

			for (int i = 0; i < StringUtil.parseInt(row_count); i++) {
				String[] rs = new String[4];
				rs[0] = StringUtil.substring(sdl, idx, idx += 1); // price code
				String runner_count = StringUtil.substring(sdl, idx, idx += 2); // Number of legs/runners
				rs[1] = runner_count;
				int len = StringUtil.parseInt(runner_count) * 3 - 1;
				rs[2] = StringUtil.substring(sdl, idx, idx += len); // Runner string
				rs[3] = StringUtil.substring(sdl, idx, idx += 10); // Prices(확정배당 5.3)

				sdlTray.setString("rs" + i, rs);
			}

		} catch (Exception ex) {
			Log.error("ERROR", this, "at RSDAO.loadTR - " + ex);
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
		int row_count = sdlTray.getInt("row_count");

		Tray[] rsTray = new NewTray[row_count];

		// rank, seq, reg_no
		for (int i = 0; i < row_count; i++) {

			if (rsTray[i] == null) rsTray[i] = new NewTray();

			rsTray[i].setString("meet_cd", sdlTray.getString("meet_cd"));
			rsTray[i].setString("stnd_year", sdlTray.getString("stnd_year"));
			rsTray[i].setString("tms", sdlTray.getString("tms"));
			rsTray[i].setString("day_ord", sdlTray.getString("day_ord"));
			rsTray[i].setString("race_no", sdlTray.getString("race_no"));
			rsTray[i].setString("pool_cd", sdlTray.getString("pool_cd"));
			rsTray[i].setString("rs_seq", String.valueOf(i + 1));

			String price_code = sdlTray.getString("rs" + i, 0);
			rsTray[i].setString("status", price_code);

			String[] runner = sdlTray.getString("rs" + i, 2).split("/");

			for (int a = 0; a < runner.length; a++) {
				// As - is 가 F를 넣어서 추가함.
				if ("R".equals(price_code)) runner[a] = "F";
				else if ("[]".equals(runner[a])) runner[a] = "";
				else runner[a] = String.valueOf(StringUtil.parseInt(runner[a], 0));
			}

			switch (StringUtil.parseInt(sdlTray.getString("pool_cd"), 0)) {
				case 1 :
					rsTray[i].setString("runner_1st", runner[0]);
					rsTray[i].setString("runner_2nd", "");
					rsTray[i].setString("runner_3rd", "");
					break;
				case 2 :
					rsTray[i].setString("runner_1st", runner[0]);
					rsTray[i].setString("runner_2nd", "");
					rsTray[i].setString("runner_3rd", "");
					break;
				case 4 :
					rsTray[i].setString("runner_1st", runner[0]);
					rsTray[i].setString("runner_2nd", runner[1]);
					rsTray[i].setString("runner_3rd", "");
					break;
				case 5 :
					rsTray[i].setString("runner_1st", runner[0]);
					rsTray[i].setString("runner_2nd", runner[1]);
					rsTray[i].setString("runner_3rd", "");
					break;
				case 6 :
					rsTray[i].setString("runner_1st", runner[0]);
					rsTray[i].setString("runner_2nd", runner[1]);
					rsTray[i].setString("runner_3rd", runner[2]);
					break;
				default :
					rsTray[i].setString("runner_1st", "");
					rsTray[i].setString("runner_2nd", "");
					rsTray[i].setString("runner_3rd", "");
			}

			rsTray[i].setString("payoff", sdlTray.getString("rs" + i, 3));

			Log.debug("", this, "\n#########################\n" + rsTray[i].toString() + "\n#########################");
		}

		return rsTray;
	}

	/**
	 * delete
	 * @param conn
	 * @param rsTray
	 * @return
	 * @throws AppException
	 */
	private boolean deleteRS(Connection conn, Tray rsTray) throws AppException {

		StringBuffer sb = new StringBuffer();
		sb.append(" delete from tbes_sdl_rs      ");
		sb.append(" \n where 1=1             ");
		sb.append(" \n and meet_cd = :meet_cd   ");
		sb.append(" \n and stnd_year = :stnd_year ");
		sb.append(" \n and tms = :tms         ");
		sb.append(" \n and day_ord = :day_ord       ");
		sb.append(" \n and race_no = :race_no    ");
		sb.append(" \n and pool_cd = :pool_cd   ");

		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			runner.setParams(rsTray);
			Log.debug("DB", this, runner.toString());
			return runner.update(conn)>0;
		} catch (AppException e) {
			Log.error("ERROR", this, "at RSDAO.deleteRS()" + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at RSDAO.deleteRS()" + ex);
			throw new AppException("", ex);
		} 
	}

	private boolean insertRS(Connection conn, Tray[] rsTray) throws AppException {
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into tbes_sdl_rs ( ");
		sb.append(" \n meet_cd                   ");
		sb.append(" \n , stnd_year               ");
		sb.append(" \n , tms                     ");
		sb.append(" \n , day_ord                 ");
		sb.append(" \n , race_no                 ");
		sb.append(" \n , pool_cd                 ");
		sb.append(" \n , rs_seq                  ");
		sb.append(" \n , runner_1st              ");
		sb.append(" \n , runner_2nd              ");
		sb.append(" \n , runner_3rd              ");
		sb.append(" \n , payoff                  ");
		sb.append(" \n , status                  ");
		sb.append(" \n , inst_id                 ");
		sb.append(" \n , inst_dt                 ");
		sb.append(" \n , updt_id                 ");
		sb.append(" \n , updt_dt                 ");
		sb.append(" \n ) values (                ");
		sb.append(" \n :meet_cd                  ");
		sb.append(" \n , :stnd_year              ");
		sb.append(" \n , :tms                    ");
		sb.append(" \n , :day_ord                ");
		sb.append(" \n , :race_no                ");
		sb.append(" \n , :pool_cd                ");
		sb.append(" \n , :rs_seq                 ");
		sb.append(" \n , nvl(:runner_1st,'0')    ");
		sb.append(" \n , nvl(:runner_2nd,'0')    ");
		sb.append(" \n , nvl(:runner_3rd,'0')    ");
		sb.append(" \n , to_char( to_number(:payoff) / 10 , '9999.9') ");
		sb.append(" \n , :status                 ");
		sb.append(" \n , 'SDL'                   ");
		sb.append(" \n , sysdate                 ");
		sb.append(" \n , 'SDL'                   ");
		sb.append(" \n , sysdate                 ");
		sb.append(" \n )                         ");
		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			// runner.setParams(rsTray);
			Log.debug("DB", this, runner.toString());
			return runner.updateBatch(conn, rsTray);
		} catch (AppException e) {
			Log.error("ERROR", this, "at RSDAO.insertRS()" + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at RSDAO.insertRS()" + ex);
			throw new AppException("", ex);
		}
	}
}
