package org.sosfo.sdl.cra.gate;

import java.sql.Connection;

import org.sosfo.framework.business.BaseBean;
import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.exception.ExceptionManager;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.tray.NewTray;
import org.sosfo.framework.tray.Tray;
import org.sosfo.sdl.cra.dao.RaceInfoDAO;

/**
 * 경주 정보 조회
 */
public class RaceInfoGATE extends BaseBean {
	private RaceInfoDAO	dao	= null;

	/**
	 * default Constructor
	 */
	public RaceInfoGATE() {
		this(new NewTray());
	}

	/**
	 * use if you want to change JDBC Connection
	 * 
	 * @param reqTray
	 */
	public RaceInfoGATE(Tray reqTray) {
		/*String meet_cd = reqTray.getString("meet_cd");
		if ("003".equals(meet_cd)) {
			super.setConnectionName("jdbc/mra");
		}*/
		dao = new RaceInfoDAO();
	}

	/**
	 * 단,연승식 처리
	 * 
	 * @param Connection conn
	 * @param Tray reqTray
	 * @return boolean status
	 */
	public Tray findByPrimaryKey(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.findByPrimaryKey(conn, reqTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
}
