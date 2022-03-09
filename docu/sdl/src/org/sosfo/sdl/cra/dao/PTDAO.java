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
 * PT : Pool Totals
 * 
 * @author <pre>
 * ++ SDL Header
 *          'PT'                                [ 2] Message code
 *          'CRA - GWANGMYEONG             '   [30] Meet name
 *          '0088'                             [ 4] Perf number
 *          'REAL'                          [ 4] Type of Perf
 *          '01'                                [ 2] Race numbe
 *          '0097'                            [ 4] Message length
 * ++ SDL Data
 *          '05'                                  [ 2] Number of pool data followed
 *          'WIN'                              [ 3] Pool name
 *          'O'                                   [ 1] Pool Status; O:open; F:close
 *          '00000015493900'          [14] Pool total; unit=1 Won;
 *          'N'                                   [ 1] Pool Scratched? N:normal; S:scratched;
 *          'PLC O 00000018135000 N'           ......
 *          'EX  O 00000049475800 N'
 *          'QU  O 00000029243200 N'
 *          'TLA O 00000061471300 N'
 * ++ SDL Checksum
 *          '05449'                            [ 5] Checksum
 * 
 * 
 * 
 *  Message example - totals with betting on
 * PTCRA - GWANGMYEONG             0088REAL01009705WINO00000015493900NPLCO00000018135000NEX O00000049475800NQU O00000029243200NTLAO00000061471300N05449
 * 
 *  Message example - totals with betting on; runner 4 scratched
 * PTCRA - GWANGMYEONG             0088REAL01009705WINO00000013206700NPLCO00000015491700NEX O00000035492900NQU O00000020667400NTLAO00000035307400N05444
 * 
 *  Message example - totals with betting off; runner 4 scratched
 * PTCRA - GWANGMYEONG             0088REAL01009705WINF00000013206700NPLCF00000015491700NEX F00000035492900NQU F00000020667400NTLAF00000035307400N05399
 * 
 * 
 * 
 * </pre>
 */
public class PTDAO extends BaseDao {
	/**
	 * <pre>
	 * 0. TR로직에 맞게 Tray 로딩
	 * 1. 조회
	 * 2. 1.에의한 update or insert 결정
	 * </pre>
	 */

	public boolean insert(Connection conn, Tray sdlTray) throws AppException {
		try {

			loadPT(sdlTray);

			Tray rsTray[] = loadTray(sdlTray);

			boolean success = false;

			int count = findPT(conn, sdlTray);

			if (count > 0) {
				this.updatePT(conn, rsTray);
			} else {
				this.insertPT(conn, rsTray);
			}

			this.insertPTS(conn, sdlTray);

			String pool_status = sdlTray.getString("pool_status");
			
			
			// 마감 확정
			if("F".equals(pool_status)){
				updateTM(conn,sdlTray);
			}
			
			return success;

		} catch (AppException e) {
			Log.error("ERROR", this, "at PTDAO.insert() - " + e);
			throw e;
		} catch (Exception e) {
			Log.error("ERROR", this, "at PTDAO.insert()- " + e);
			throw new AppException("", e);
		}
	}

	/**
	 * TP로직에 맞게 Tray 로딩
	 * 
	 * @param conn
	 * @param sdlTray
	 * @throws AppException
	 */
	private void loadPT(Tray sdlTray) throws AppException {
		String sdl = sdlTray.getString("sdl");
		try {
			// pass header 2,30,4,4,2,4
			int idx = 46;

			// Number of pool data followed
			int pool_count = StringUtil.parseInt(StringUtil.substring(sdl, idx, idx += 2));
			sdlTray.setString("pool_count", String.valueOf(pool_count));

			int selected_pool_count = 0;
			for (int i = 0; i < pool_count; i++) {
				String pool_name = StringUtil.substring(sdl, idx, idx += 3);
				String pool_status = StringUtil.substring(sdl, idx, idx += 1);
				
				sdlTray.setString("pool_status",pool_status);
				
				String pool_total = StringUtil.substring(sdl, idx, idx += 14);
				String pool_scratched = StringUtil.substring(sdl, idx, idx += 1);

				String[] fn = new String[2];

				Log.debug("pool_status : " + pool_status);

				//if ("O".equals(pool_status)) {
					fn[0] = pool_name;
					if ("S".equals(pool_scratched)) {
						pool_total = "0";
					}

					fn[1] = pool_total;

					sdlTray.setString("fn" + selected_pool_count, fn);

					selected_pool_count++;
				//}
			}

			sdlTray.setString("selected_pool_count", String.valueOf(selected_pool_count));

			Log.debug(sdlTray.toString());
		} catch (Exception ex) {
			Log.error("ERROR", this, "at PTDAO.loadTR - " + ex);
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
		int selected_pool_count = sdlTray.getInt("selected_pool_count");

		Tray[] rsTray = new NewTray[selected_pool_count];

		// rank, seq, reg_no
		for (int i = 0; i < selected_pool_count; i++) {

			if (rsTray[i] == null) rsTray[i] = new NewTray();

			rsTray[i].setString("meet_cd", sdlTray.getString("meet_cd"));
			rsTray[i].setString("stnd_year", sdlTray.getString("stnd_year"));
			rsTray[i].setString("tms", sdlTray.getString("tms"));
			rsTray[i].setString("day_ord", sdlTray.getString("day_ord"));
			rsTray[i].setString("race_no", sdlTray.getString("race_no"));
			rsTray[i].setString("pool_cd", CodeManager.getPoolCD(sdlTray.getString("fn" + i, 0)));
			rsTray[i].setString("pool_total", sdlTray.getString("fn" + i, 1));

			Log.debug("", this, "\n#########################\n" + rsTray[i].toString() + "\n#########################");
		}

		return rsTray;
	}

	/**
	 * Search PT
	 * 
	 * @param conn
	 * @param tray
	 * @return
	 * @throws AppException
	 */
	private int findPT(Connection conn, Tray tray) throws AppException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(*) as cnt ");
		sb.append(" \n from tbes_sdl_pt      ");
		sb.append(" \n where 1=1             ");
		sb.append(" \n and meet_cd = :meet_cd   ");
		sb.append(" \n and stnd_year = :stnd_year ");
		sb.append(" \n and tms = :tms         ");
		sb.append(" \n and day_ord = :day_ord       ");
		sb.append(" \n and race_no = :race_no    ");

		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			runner.setParams(tray);
			Log.debug("DB", this, runner.toString());

			return ((Tray) runner.query(conn)).getInt("cnt");

		} catch (Exception ex) {
			Log.error("ERROR", this, "at PTDAO.findPT()" + ex);
			throw new AppException("", ex);
		}
	}

	private boolean insertPT(Connection conn, Tray rsTray[]) throws AppException {
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into tbes_sdl_pt ( ");
		sb.append(" \n meet_cd                 ");
		sb.append(" \n , stnd_year             ");
		sb.append(" \n , tms                   ");
		sb.append(" \n , day_ord               ");
		sb.append(" \n , race_no               ");
		sb.append(" \n , pool_cd               ");
		sb.append(" \n , pool_total            ");
		sb.append(" \n , refund                ");
		sb.append(" \n , inst_id               ");
		sb.append(" \n , inst_dt               ");
		sb.append(" \n , updt_id               ");
		sb.append(" \n , updt_dt               ");
		sb.append(" \n ) values (              ");
		sb.append(" \n :meet_cd                ");
		sb.append(" \n , :stnd_year            ");
		sb.append(" \n , :tms                  ");
		sb.append(" \n , :day_ord              ");
		sb.append(" \n , :race_no              ");
		sb.append(" \n , :pool_cd              ");
		sb.append(" \n , :pool_total           ");
		sb.append(" \n , :refund               ");
		sb.append(" \n , 'SDL'               	");
		sb.append(" \n , sysdate               ");
		sb.append(" \n , 'SDL'               	");
		sb.append(" \n , sysdate               ");
		sb.append(" \n )                       ");
		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			return runner.updateBatch(conn, rsTray);
		} catch (AppException e) {
			Log.error("ERROR", this, "at PTDAO.insertPT()" + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at PTDAO.insertPT()" + ex);
			throw new AppException("", ex);
		}
	}

	/**
	 * 승식별 매출액 업데이트
	 * @param conn
	 * @param rsTray
	 * @return
	 * @throws AppException
	 */
	private boolean updatePT(Connection conn, Tray rsTray[]) throws AppException {
		StringBuffer sb = new StringBuffer();
		sb.append(" update tbes_sdl_pt ");
		sb.append(" \n set pool_total = :pool_total ");
		//sb.append(" \n , refund = pool_total - :pool_total");
		
		sb.append("\n    , refund =(                                          ");
		sb.append("\n    case                                                 ");
		sb.append("\n        when pool_total > to_number(:pool_total) then  ");
		sb.append("\n        pool_total-to_number(:pool_total)                ");
		sb.append("\n        else 0                                           ");
		sb.append("\n    end                                                  ");
		sb.append("\n    )                                                    ");		
		
		
		sb.append(" \n , updt_id = 'SDL' ");
		sb.append(" \n , updt_dt = sysdate ");
		sb.append(" \n where 1=1             ");
		sb.append(" \n and meet_cd = :meet_cd   ");
		sb.append(" \n and stnd_year = :stnd_year ");
		sb.append(" \n and tms = :tms         ");
		sb.append(" \n and day_ord = :day_ord       ");
		sb.append(" \n and race_no = :race_no    ");
		sb.append(" \n and pool_cd = :pool_cd   ");

		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			return runner.updateBatch(conn, rsTray);

		} catch (AppException e) {
			Log.error("ERROR", this, "at PTDAO.updatePT()" + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at PTDAO.updatePT()" + ex);
			throw new AppException("", ex);
		}
	}

	/**
	 * 승식별 매출액 이력 입력
	 * @param conn
	 * @param rsTray
	 * @return
	 * @throws AppException
	 */
	private boolean insertPTS(Connection conn, Tray rsTray) throws AppException {
		
		// 마감 5분전만 이력을 쌓는다.
		if (!findTM(conn, rsTray)) return false;
		
		StringBuffer sb = new StringBuffer();
		sb.append(" \n insert into tbes_sdl_pts (");
		sb.append(" \n 	meet_cd                 ");
		sb.append(" \n 	, stnd_year             ");
		sb.append(" \n 	, tms                   ");
		sb.append(" \n 	, day_ord               ");
		sb.append(" \n 	, race_no               ");
		sb.append(" \n 	, pool_cd               ");
		sb.append(" \n 	, pool_total_seq        ");
		sb.append(" \n 	, recv_dt               ");
		sb.append(" \n 	, pool_total            ");
		sb.append(" \n 	, refund                ");
		sb.append(" \n 	, inst_id               ");
		sb.append(" \n 	, inst_dt               ");
		sb.append(" \n 	, updt_id               ");
		sb.append(" \n 	, updt_dt               ");
		sb.append(" \n )                         ");
		sb.append(" \n select                    ");
		sb.append(" \n 	meet_cd                 ");
		sb.append(" \n 	, stnd_year             ");
		sb.append(" \n 	, tms                   ");
		sb.append(" \n 	, day_ord               ");
		sb.append(" \n 	, race_no               ");
		sb.append(" \n 	, pool_cd               ");
		sb.append(" \n 	, ( select nvl(max(pool_total_seq),0)+1 ");
		sb.append(" \n 			from tbes_sdl_pts    ");
		sb.append(" \n 			where 1=1             ");
		sb.append(" \n 			and meet_cd = :meet_cd   ");
		sb.append(" \n 			and stnd_year = :stnd_year ");
		sb.append(" \n 			and tms = :tms         ");
		sb.append(" \n 			and day_ord = :day_ord       ");
		sb.append(" \n 			and race_no = :race_no    ");
		// sb.append(" \n 			and pool_cd = :pool_cd   ");
		sb.append(" \n 		) as pool_total_seq ");
		sb.append(" \n 	, updt_dt             ");
		sb.append(" \n 	, pool_total          ");
		sb.append(" \n 	, refund              ");
		sb.append(" \n 	, inst_id             ");
		sb.append(" \n 	, inst_dt             ");
		sb.append(" \n 	, updt_id             ");
		sb.append(" \n 	, updt_dt             ");
		sb.append(" \n from tbes_sdl_pt        ");
		sb.append(" \n where 1=1             ");
		sb.append(" \n and meet_cd = :meet_cd   ");
		sb.append(" \n and stnd_year = :stnd_year ");
		sb.append(" \n and tms = :tms         ");
		sb.append(" \n and day_ord = :day_ord       ");
		sb.append(" \n and race_no = :race_no    ");
		// sb.append(" \n and pool_cd = :pool_cd   ");
		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			runner.setParams(rsTray);
			Log.debug("DB", this, runner.toString());
			return runner.update(conn) > 0;
		} catch (AppException e) {
			Log.error("ERROR", this, "at PTDAO.insertPTS()" + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at PTDAO.insertPTS()" + ex);
			throw new AppException("", ex);
		}
	}
	
	/**
	 * update TM 마감확정 플래그 저장
	 * @param conn
	 * @param rsTray
	 * @return
	 * @throws AppException
	 */
	private boolean updateTM(Connection conn, Tray rsTray) throws AppException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n update tbes_sdl_tm set 	  ");
		
		sb.append("\n race_end_yn = 'Y' ");

		sb.append("\n where meet_cd = :meet_cd    ");
		sb.append("\n and stnd_year = :stnd_year  ");
		sb.append("\n and tms=:tms                ");
		sb.append("\n and day_ord = :day_ord      ");
		sb.append("\n and race_no = :race_no      ");

		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			runner.setParams(rsTray);
			Log.debug("DB", this, runner.toString());
			return runner.update(conn) > 0;
		} catch (AppException e) {
			Log.error("ERROR", this, "at PTDAO.updateTM()" + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at PTDAO.updateTM()" + ex);
			throw new AppException("", ex);
		}
	}	
}
