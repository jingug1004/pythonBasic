package org.sosfo.sdl.cra.gate;

import java.sql.Connection;

import org.sosfo.framework.business.BaseBean;
import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.exception.ExceptionManager;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.tray.Tray;
import org.sosfo.sdl.cra.dao.DTDAO;

/**
 * DT : Divisions Total
 * 
 * @author <pre>
 * ++ SDL Header
 *          'DT'                                &lt; 2&gt; Message code
 *          'CRA - GWANGMYEONG             '   &lt;30&gt; Meet name
 *          '0088'                             &lt; 4&gt; Perf number
 *          'REAL'                           &lt; 4&gt; Type of Perf
 *          '01'                                 &lt; 2&gt; Race numbe
 *          '0048'                             &lt; 4&gt; Message length
 * ++ SDL Data
 *          '01'                                 [ 2] Association
 *          '01'                                 [ 2] Community
 *          '04'                                 [ 2] Division
 *          '00000173819200'         [14] Sales
 *          '00000053653100'         [14] Refund
 *          '00000120166100'         [14] Net Sales
 * ++ SDL Checksum
 *          '02381'                            &lt; 5&gt; Checksum
 * 
 * 
 *  Message example - division totals before race official
 * DTCRA - GWANGMYEONG             0088REAL01004801010100000000000000000000000000000000000000000002307
 * DTCRA - GWANGMYEONG             0088REAL01004801010200000000000000000000000000000000000000000002308
 * DTCRA - GWANGMYEONG             0088REAL01004801010300000000000000000000000000000000000000000002309
 * DTCRA - GWANGMYEONG             0088REAL01004801010400000173819200000000000000000000017381920002372
 * DTCRA - GWANGMYEONG             0088REAL01004801010500000000000000000000000000000000000000000002311
 * DTCRA - GWANGMYEONG             0088REAL01004801010600000000000000000000000000000000000000000002312
 * DTCRA - GWANGMYEONG             0088REAL01004801010700000000000000000000000000000000000000000002313
 * DTCRA - GWANGMYEONG             0088REAL01004801010800000000000000000000000000000000000000000002314
 * DTCRA - GWANGMYEONG             0088REAL01004801010900000000000000000000000000000000000000000002315
 * DTCRA - GWANGMYEONG             0088REAL01004801011000000000000000000000000000000000000000000002307
 * 
 * 
 *  Message example - division totals after race official
 * DTCRA - GWANGMYEONG             0088REAL01004801010100000000000000000000000000000000000000000002307
 * DTCRA - GWANGMYEONG             0088REAL01004801010200000000000000000000000000000000000000000002308
 * DTCRA - GWANGMYEONG             0088REAL01004801010300000000000000000000000000000000000000000002309
 * DTCRA - GWANGMYEONG             0088REAL01004801010400000173819200000000536531000000012016610002381
 * DTCRA - GWANGMYEONG             0088REAL01004801010500000000000000000000000000000000000000000002311
 * DTCRA - GWANGMYEONG             0088REAL01004801010600000000000000000000000000000000000000000002312
 * DTCRA - GWANGMYEONG             0088REAL01004801010700000000000000000000000000000000000000000002313
 * DTCRA - GWANGMYEONG             0088REAL01004801010800000000000000000000000000000000000000000002314
 * DTCRA - GWANGMYEONG             0088REAL01004801010900000000000000000000000000000000000000000002315
 * DTCRA - GWANGMYEONG             0088REAL01004801011000000000000000000000000000000000000000000002307
 * 
 * 
 *</pre>
 */
public class DTGATE extends BaseBean {
	private DTDAO	dao	= null;

	public DTGATE() {
		dao = new DTDAO();
	}

	public DTGATE(Tray reqTray) {
		/*String meet_cd = reqTray.getString("meet_cd");
		if ("003".equals(meet_cd)) {
			super.setConnectionName("jdbc/mra");
		}*/

		dao = new DTDAO();
	}

	/*
	 * DT Processing
	 * @param reqTray 
	 * @param conn
	 * @see org.sosfo.framework.business.BaseBean#insert(java.sql.Connection, org.sosfo.framework.tray.Tray)
	 */
	public boolean insert(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.insert(conn, reqTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, "at DTGATE.insert - " + ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
}
