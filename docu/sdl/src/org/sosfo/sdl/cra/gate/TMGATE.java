package org.sosfo.sdl.cra.gate;

import java.sql.Connection;

import org.sosfo.framework.business.BaseBean;
import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.exception.ExceptionManager;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.tray.Tray;
import org.sosfo.sdl.cra.dao.TMDAO;

/**
 * TM : Time Info
 * 
 * @author
 * 
 * <pre>
 * ++ SDL Header
 *          'TM'                               [ 2] Message code
 *          'CRA - GWANGMYEONG             '   [30] Meet name
 *          '0088'                             [ 4] Perf number
 *          'REAL'                           [ 4] Type of Perf
 *          '01'                                 [ 2] Race number
 *          '0022'                             [ 4] Message length
 *  ++ SDL Data
 *          '10082008'                     [ 8] Perf Date MMDDYYYY
 *          '104001'                         [ 6] Perf Time HHMMSS; 48 hours clock may be used
 *          '111600'                         [ 6] Post Time;        48 hours clock may be used
 *          '35'                                 [ 2] mtp;  **:passed or not available
 *  ++ SDL Checksum
 *          '01098'                           [ 5] Checksum
 * 
 * 
 * 
 *  Message example
 * TMCRA - GWANGMYEONG             0088REAL010022100820081039071116003601113
 * TMCRA - GWANGMYEONG             0088REAL01002210062008142159......**01071
 * TMCRA - GWANGMYEONG             0088REAL020022100620083536253602002501115
 * TMCRA - GWANGMYEONG             0088REAL020022100620083558013602000301109
 * 
 * </pre>
 */
public class TMGATE extends BaseBean {
	private TMDAO	dao	= null;

	public TMGATE() {
		dao = new TMDAO();
	}

	public TMGATE(Tray reqTray) {

		/*String meet_cd = reqTray.getString("meet_cd");
		if ("003".equals(meet_cd)) {
			super.setConnectionName("jdbc/mra");
		}*/
		dao = new TMDAO();
	}

	public boolean insert(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.insert(conn, reqTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, "at TMGATE.insert - " + ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
}
