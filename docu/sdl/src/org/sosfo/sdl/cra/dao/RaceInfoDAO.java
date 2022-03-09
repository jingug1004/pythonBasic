package org.sosfo.sdl.cra.dao;

import java.sql.Connection;

import org.sosfo.framework.dao.BaseDao;
import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.sql.QueryRunner;
import org.sosfo.framework.tray.Tray;
import org.sosfo.framework.util.StringUtil;

/**
 * 경주 정보 조회
 */
public class RaceInfoDAO extends BaseDao {

	/**
	 * 해당일자의 경주 정보 조회
	 */
	public Tray findByPrimaryKey(Connection conn, Tray reqTray) throws AppException {

		StringBuffer sb = new StringBuffer();
		sb.append("\n select                                       ");
		sb.append("\n      meet_cd,                                ");
		sb.append("\n      stnd_year,                              ");
		sb.append("\n      tms,                                    ");
		sb.append("\n      day_ord,                                ");
		sb.append("\n      race_day,                               ");
		sb.append("\n      race_yoil                               ");
		sb.append("\n from vw_sdl_info                             ");
		sb.append("\n where                                        ");
		sb.append("\n     meet_cd=:meet_cd                         ");
		sb.append("\n     and race_day=to_char(sysdate,'yyyymmdd') ");
		//sb.append("\n     and race_day = '20090133'                ");

		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			runner.setParams(reqTray);
			Log.debug("DB", this, runner.toString());
			Tray tray = (Tray) runner.query(conn);
			// 경기정보가 없을경우
			if (tray.getRowCount()<1) {
				String meet_name = "cra";
				//if (StringUtil.evlTrim(reqTray.getString("meet_nm"), "") == "MRA") meet_name = "mra";
				if("MRA".equals(StringUtil.evlTrim(reqTray.getString("meet_nm"), "") )){
					meet_name = "mra";
				}else{
					meet_name = "cra";
				}
				throw new AppException("sdl." + meet_name + ".status.not_found", new Exception("[VW_SDL_INFO]에 해당경기가 등록되어있지 않습니다."));
			}

			return tray;
			
		} catch (AppException app) {
			throw app;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at RaceInfoDAO.findByPrimaryKey()" + ex);
			throw new AppException("", ex);
		}
	}

}
