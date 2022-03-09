package org.sosfo.sdl.cra.dao;

import java.sql.Connection;

import org.sosfo.framework.dao.BaseDao;
import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.sql.QueryRunner;
import org.sosfo.framework.tray.Tray;

/**
 * <pre>
 * PT : Pool Totals
 * �߸������� 10�� �̻� ���� SDL�� ���� �Ұ��Ͽ� ���� PT�� �����ϰ� 
 * WR, FR, TR�� ������ PT�� �Է��Ѵ�.
 * 
 * </pre>
 */
public class NewPTDAO extends BaseDao {
	/**
	 * <pre>
	 * 0. TR������ �°� Tray �ε�
	 * 1. ��ȸ
	 * 2. 1.������ update or insert ����
	 * </pre>
	 */

	public boolean insert(Connection conn, Tray sdlTray) throws AppException {
		try {

			boolean success = false;

			int count = findPT(conn, sdlTray);

			if (count > 0) {
				this.updatePT(conn, sdlTray);
			} else {
				this.insertPT(conn, sdlTray);
			}

			this.insertPTS(conn, sdlTray);

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
		sb.append(" \n and pool_cd = :pool_cd    ");

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

	/**
	 * �½ĺ� ����� �Է�
	 * 
	 * @param conn
	 * @param rsTray
	 * @return
	 * @throws AppException
	 */
	private boolean insertPT(Connection conn, Tray rsTray) throws AppException {
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
			runner.setParams(rsTray);
			Log.debug("DB", this, runner.toString());
			return runner.update(conn) > 0;
		} catch (AppException e) {
			Log.error("ERROR", this, "at PTDAO.insertPT()" + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at PTDAO.insertPT()" + ex);
			throw new AppException("", ex);
		}
	}

	/**
	 * �½ĺ� ����� ����
	 * 
	 * @param conn
	 * @param rsTray
	 * @return
	 * @throws AppException
	 */
	private boolean updatePT(Connection conn, Tray rsTray) throws AppException {
		StringBuffer sb = new StringBuffer();
		sb.append(" update tbes_sdl_pt ");
		sb.append(" \n set pool_total = :pool_total     ");
		//sb.append(" \n , refund = pool_total - :pool_total");
		//sb.append(" \n , refund = decode( (pool_total-:pool_total)>0 ,pool_total-:pool_total, 0) ");
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
			runner.setParams(rsTray);
			Log.debug("DB", this, runner.toString());
			return runner.update(conn) > 0;

		} catch (AppException e) {
			Log.error("ERROR", this, "at PTDAO.updatePT()" + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at PTDAO.updatePT()" + ex);
			throw new AppException("", ex);
		}
	}

	private boolean insertPTS(Connection conn, Tray rsTray) throws AppException {

		try {

			// ���� 5������ �̷��� �״´�.
			if (!super.findTM(conn, rsTray))
				return false;

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
			sb.append(" \n 			and pool_cd = :pool_cd   ");
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
			sb.append(" \n and pool_cd = :pool_cd   ");

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

}
