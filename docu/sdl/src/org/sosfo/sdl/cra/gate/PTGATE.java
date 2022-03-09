package org.sosfo.sdl.cra.gate;

import java.sql.Connection;

import org.sosfo.framework.business.BaseBean;
import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.exception.ExceptionManager;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.tray.NewTray;
import org.sosfo.framework.tray.Tray;
import org.sosfo.sdl.cra.dao.PTDAO;

/**
 * PT : Pool Totals
 * 
 * @author
 * 
 * <pre>
 * ++ SDL Header
 *          'PT'                                &lt; 2&gt; Message code
 *          'CRA - GWANGMYEONG             '   &lt;30&gt; Meet name
 *          '0088'                             &lt; 4&gt; Perf number
 *          'REAL'                          &lt; 4&gt; Type of Perf
 *          '01'                                &lt; 2&gt; Race numbe
 *          '0097'                            &lt; 4&gt; Message length
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
 *          '05449'                            &lt; 5&gt; Checksum
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
public class PTGATE extends BaseBean {
	private PTDAO	dao	= null;

	/**
	 * default Constructor
	 */
	public PTGATE() {
		this(new NewTray());
	}

	public PTGATE(Tray reqTray) {

		/*String meet_cd = reqTray.getString("meet_cd");
		if ("003".equals(meet_cd)) {
			super.setConnectionName("jdbc/mra");
		}*/
		// super.setConnectionName("jdbc/harry");

		dao = new PTDAO();
	}

	public boolean insert(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.insert(conn, reqTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, "at PTGATE.insert - " + ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
}
