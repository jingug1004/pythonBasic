package org.sosfo.sdl.cra.gate;

import java.sql.Connection;

import org.sosfo.framework.business.BaseBean;
import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.exception.ExceptionManager;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.tray.NewTray;
import org.sosfo.framework.tray.Tray;
import org.sosfo.sdl.cra.dao.FNDAO;

/**
 * FN : Finishers
 * 
 * @author
 * 
 * <pre>
 * ++ SDL Header
 *          'FN'                               &lt; 2&gt; Message code
 *          'CRA - GWANGMYEONG             '   &lt;30&gt; Meet name
 *          '0088'                           &lt; 4&gt; Perf number
 *          'REAL'                         &lt; 4&gt; Type of Perf
 *          '01'                               &lt; 2&gt; Race numbe
 *          '0024'                           &lt; 4&gt; Message length
 * ++ SDL Data
 *          '03'                               [ 2] Position count
 *          '01'                               [ 2] Position x
 *          '01'                               [ 2] Number of runner in position x
 *          '05'                               [ 2] Runner x
 *          '02 01 03'                     [2][2][2]....
 *          '03 01 01'                         ....
 *          '01'                               [ 2] Number of scratched runners
 *          '04'                               [ 2] Scratched runner(s) String
 * ++ SDL Checksum
 *          '01178'                            &lt; 5&gt; Checksum
 *  Message example - unofficial prices
 * FNCRA - GWANGMYEONG             0088REAL01002403010105020103030101010401178
 * 
 * 
 * </pre>
 */
public class FNGATE extends BaseBean {
	private FNDAO	dao	= null;

	public FNGATE() {
		this(new NewTray());
	}

	public FNGATE(Tray reqTray) {

		/*String meet_cd = reqTray.getString("meet_cd");
		if ("003".equals(meet_cd)) {
			super.setConnectionName("jdbc/mra");
		}*/
		dao = new FNDAO();
	}

	public boolean insert(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.insert(conn, reqTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, "at FNGATE.insert - " + ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
}
