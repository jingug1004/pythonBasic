package org.sosfo.sdl.cra.gate;

import java.sql.Connection;

import org.sosfo.framework.business.BaseBean;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.exception.ExceptionManager;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.tray.NewTray;
import org.sosfo.framework.tray.Tray;
import org.sosfo.framework.connection.DBCPManager;

import org.sosfo.sdl.cra.dao.SPDAO;

/**
 * SP : WPS Probables (단,연승식 중간배당율), 선수별자료 포함
 * 
 * @author
 * 
 * <pre>
 * ook at the examples
 * 
 * + SDL Header
 *          'SP'                                &lt; 2&gt; Message code
 *          'CRA - GWANGMYEONG             '   &lt;30&gt; Meet name
 *          '0088'                             &lt; 4&gt; Perf number
 *          'REAL'                           &lt; 4&gt; Type of Perf
 *          '01'                                 &lt; 2&gt; Race numbe
 *          '0110'                             &lt; 4&gt; Message length
 * ++ SDL Data
 *          'WIN'                             [ 3] Pool
 *          'O'                                  [ 1] Pool Status; O:open; F:close
 *          '07'                                 [ 2] Number of runner probs followed
 *          '00056'                           [ 5] Runner odds 1
 *          '00055'                           [..] ....
 *          '00057'
 *          '00055'
 *          '00063'
 *          '00056'
 *          '00056'                           [..] ....
 *          '00000015493900'         [14] Pool total; Unit=1 Won; 15493900=15,493,900 Won
 *          'PLC'                              [ 3] Pool
 *          'O'                                   [ 1] Pool Status; O:open; F:close
 *          '07'                                  [ 2] Number of runner probs followed
 *          '00028'                            [ 5] Runner odds 1
 *          '00028'                            [..] ....
 *          '00028'
 *          '00028'
 *          '00031'
 *          '00027'
 *          '00029'                            [..] ....
 *          '00000018135000'          [14] Pool total; Unit=1 Won;
 * ++ SDL Checksum
 *          '05716'                            &lt; 5&gt; Checksum
 * Message example - odds/probs with betting on
 * SPCRA - GWANGMYEONG             0088REAL010110WINO07000560005500057000550006300
 * 0560005600000015493900PLCO070002800028000280002800031000270002900000018135000057
 * 16 
 * 
 * ** Message example - odds/probs with betting on; runner 4 scratched
 * SPCRA - GWANGMYEONG             0088REAL010110WINO07000480004700048.....0005300
 * 0480004800000013206700PLCO07000240002400024.....00027000230002400000015491700056
 * 60
 * 
 * ** Message example - odds/probs with betting off; runner 4 scratched
 * SPCRA - GWANGMYEONG             0088REAL010110WINF07000480004700048.....0005300
 * 0480004800000013206700PLCF07000240002400024.....00027000230002400000015491700056
 * 42
 * </pre>
 */
public class SPGATE extends BaseBean {
	private SPDAO	dao	= null;

	/**
	 * default Constructor
	 */
	public SPGATE() {
		this(new NewTray());
	}

	public SPGATE(Tray reqTray) {

		/*String meet_cd = reqTray.getString("meet_cd");
		if ("003".equals(meet_cd)) {
			super.setConnectionName("jdbc/mra");
		}*/
		// super.setConnectionName("jdbc/harry");

		dao = new SPDAO();
	}

	public boolean insert(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.insert(conn, reqTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, "at SPGATE.insert - " + ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
}
