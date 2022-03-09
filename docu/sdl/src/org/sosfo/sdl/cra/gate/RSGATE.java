package org.sosfo.sdl.cra.gate;

import java.sql.Connection;

import org.sosfo.framework.business.BaseBean;
import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.exception.ExceptionManager;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.tray.NewTray;
import org.sosfo.framework.tray.Tray;
import org.sosfo.sdl.cra.dao.RSDAO;

/**
 * RS : Prices
 * 
 * @author
 * 
 * <pre>
 *  Message layout - for WIN
 *         ++ SDL Header
 *             'RS'                                &lt; 2&gt; Message code
 *             'CRA - GWANGMYEONG             '   &lt;30&gt; Meet name
 *             '0088'                             &lt; 4&gt; Perf number
 *             'REAL'                          &lt; 4&gt; Type of Perf
 *             '01'                                 &lt; 2&gt; Race numbe
 *             '0021'                             &lt; 4&gt; Message length
 *         ++ SDL Data
 *             'WIN'                             [ 3] Pool
 *             'U'                                  [ 1] Race status; U:unofficial; O:official
 *             '01'                                 [ 2] Number of prices followed
 *             'N'                                  [ 1] Price code; N:nomal; L:refund less commission; R:refund
 *             '01'                                 [ 2] Number of legs/runners
 *             '05'                                 [ 2] Runner string
 *             '0000000053'                 [14] Price; unit=10 Won;
 *         ++ SDL Checksum
 *             '01184'                            &lt; 5&gt; Checksum
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
public class RSGATE extends BaseBean {
	private RSDAO	dao	= null;

	/**
	 * default Constructor
	 */
	public RSGATE() {
		this(new NewTray());
	}

	public RSGATE(Tray reqTray) {

		/*String meet_cd = reqTray.getString("meet_cd");
		if ("003".equals(meet_cd)) {
			super.setConnectionName("jdbc/mra");
		}*/

		dao = new RSDAO();
	}

	public boolean insert(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.insert(conn, reqTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, "at RSGATE.insert - " + ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
}
