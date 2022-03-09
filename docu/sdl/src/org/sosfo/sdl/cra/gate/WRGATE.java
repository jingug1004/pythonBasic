package org.sosfo.sdl.cra.gate;

import java.sql.Connection;

import org.sosfo.framework.business.BaseBean;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.exception.ExceptionManager;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.tray.NewTray;
import org.sosfo.framework.tray.Tray;
import org.sosfo.sdl.cra.dao.WRDAO;

/**
 * WR : WPS Runner Totals , 단,연승 ( WIN, PLC )식 매출액(선수별)
 * 
 * @author
 * 
 * <pre>
 * ee the examples
 * 
 * + SDL Header
 *          'WR'                               &lt; 2&gt; Message code
 *          'CRA - GWANGMYEONG             '   &lt;30&gt; Meet name
 *          '0088'                             &lt; 4&gt; Perf number
 *          'REAL'                           &lt; 4&gt; Type of Perf
 *          '01'                                 &lt; 2&gt; Race numbe
 *          '0180'                             &lt; 4&gt; Message length
 * ++ SDL Data
 *          'WIN'                             [ 3] Pool
 *          'O'                                  [ 1] Pool Status; O:open; F:close
 *          '07'                                 [ 2] Number of runner totals followed
 *          '0000022446'                 [10] Runner total 1; unit=100 Won; 22446=2,244,600 Won
 *          '0000022658'                 [..] ....
 *          '0000022083'
 *          '0000022872'
 *          '0000020043'
 *          '0000022434'
 *          '0000022403'                 [..] ....
 *          '00000000154939'         [14] Pool total;  unit=100 Won; 154939=15,493,900 Won
 *          'PLC'                              [ 3] Pool
 *          'O'                                  [ 1] Pool Status; O:open; F:close
 *          '07'                                 [ 2] Number of runner totals followed
 *          '0000025820'                 [10] Runner total 1; unit=100 Won;
 *          '0000026604'                 [..] ....
 *          '0000026183'
 *          '0000026433'
 *          '0000023658'
 *          '0000026896'
 *          '0000025756'                 [..] ....
 *          '00000000181350'         [14] Pool total; unit=100 Won;
 * ++ SDL Checksum
 *          '09203'                            &lt; 5&gt; Checksum
 * 
 * * Message example - runner totals with betting on
 * WRCRA - GWANGMYEONG             0088REAL010180WINO07000002244600000226580000022
 * 083000002287200000200430000022434000002240300000000154939PLCO0700000258200000026
 * 604000002618300000264330000023658000002689600000257560000000018135009203
 * 
 *  Message example - runner totals with betting on; runner 4 scratched
 * WRCRA - GWANGMYEONG             0088REAL010180WINO07000002244600000226580000022
 * 083..........00000200430000022434000002240300000000132067PLCO0700000258200000026
 * 6040000026183..........0000023658000002689600000257560000000015491709121
 * 
 *  Message example - runner totals with betting off; runner 4 scratched
 * WRCRA - GWANGMYEONG             0088REAL010180WINF07000002244600000226580000022
 * 083..........00000200430000022434000002240300000000132067PLCF0700000258200000026
 * 6040000026183..........0000023658000002689600000257560000000015491709103
 * 
 * </pre>
 */
public class WRGATE extends BaseBean {
	private WRDAO	dao	= null;

	/**
	 * default Constructor
	 */
	public WRGATE() {
		this(new NewTray());
	}

	public WRGATE(Tray reqTray) {

		/*String meet_cd = reqTray.getString("meet_cd");
		if ("003".equals(meet_cd)) {
			super.setConnectionName("jdbc/mra");
		}*/
		dao = new WRDAO();
	}

	public boolean insert(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.insert(conn, reqTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, "at WRGATE.insert - " + ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
}
