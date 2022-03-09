package org.sosfo.sdl.cra.gate;

import java.sql.Connection;

import org.sosfo.framework.business.BaseBean;
import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.exception.ExceptionManager;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.tray.NewTray;
import org.sosfo.framework.tray.Tray;
import org.sosfo.sdl.cra.dao.PBDAO;

/**
 * FR : 2Leg Probables : ½Ö,º¹ ½Â½Ä Áß°£¹è´ç·ü(¼±¼ö Á¶ÇÕº° Æ÷ÇÔ)
 * 
 * @author
 * 
 * <pre>
 * ook at the examples
 * 
 * + SDL Header
 *          'PB'                &lt; 2&gt; Message code
 *          'CRA - GWANGMYEONG             '   &lt;30&gt; Meet name
 *          '0088'             &lt; 4&gt; Perf number
 *          'REAL'           &lt; 4&gt; Type of Perf
 *          '01'                 &lt; 2&gt; Race numbe
 *          '0293'             &lt; 4&gt; Message length
 * ++ SDL Data
 *          'EX '         [ 3] Pool
 *          'O'            [ 1] Pool Status; O:open; F:close
 *          '07'           [ 2] Number of runner totals(rows) followed
 *          '01'           [ 2] Row #1
 *          '07'           [ 2] Columns (= Number of runner probs) followed
 *          '.....'          [10] xy Runner prob;
 *          '00327'     [..] ....
 *          '00292'
 *          '00268'
 *          '00284'
 *          '00274'
 *          '00277'
 *          '02'           [ 2] Row #2
 *          '07'           [ 2] Columns (= Number of runner probs) followed
 *          '00297'     [10] xy Runner prob;
 *          '.....'          [..] ....
 *          '00285'
 *          '00280'
 *          '00284'
 *          '00309'
 *          '00288'
 *          '03'
 *          '07'
 *          '00329'
 *          '00288'
 *          '.....'
 *          '00296'
 *          '00319'
 *          '00291'
 *          '00318'
 *          '04'
 *          '07'
 *          '00276'
 *          '00300'
 *          '00304'
 *          '.....'
 *          '00300'
 *          '00317'
 *          '00293'
 *          '05'
 *          '07'
 *          '00273'
 *          '00306'
 *          '00290'
 *          '00303'
 *          '.....'
 *          '00290'
 *          '00279'
 *          '06'
 *          '07'
 *          '00261'
 *          '00317'
 *          '00284'
 *          '00309'
 *          '00268'
 *          '.....'
 *          '00259'
 *          '07'
 *          '07'
 *          '00294'
 *          '00314'
 *          '00311'
 *          '00332'
 *          '00323'
 *          '00289'
 *          '.....'                           [..] ....
 *          '00000049475800'    [14] Pool total; unit=1 Won;
 *         ++ SDL Checksum
 *          '14709'                      &lt; 5&gt; Checksum
 * 
 *  ** Message example - odds/probs with betting on
 * PBCRA - GWANGMYEONG             0088REAL010293EX O070107.....003270029200268002
 * 840027400277020700297.....002850028000284003090028803070032900288.....0029600319
 * 00291003180407002760030000304.....003000031700293050700273003060029000303.....00
 * 2900027906070026100317002840030900268.....00259070700294003140031100332003230028
 * 9.....0000004947580014709
 * 
 * PBCRA - GWANGMYEONG             0088REAL010293QU O070107.....001570014600147001
 * 4600145001560207..........00152001430014900142001560307...............0013300132
 * 00163001530407....................0014800145001450507.........................00
 * 144001500607..............................001450707.............................
 * ......0000002924320014183
 *  Message example - odds/probs with betting on; runner 4 scratched
 * PBCRA - GWANGMYEONG             0088REAL010293EX O070107.....0023500209.....002
 * 040019700198020700213.....00205.....00204002220020703070023600207..........00229
 * 00209002280407...................................0507001960022000208..........00
 * 208002000607001870022700204.....00192.....001850707002110022500223.....002320020
 * 7.....0000003549290014355
 * 
 * PBCRA - GWANGMYEONG             0088REAL010293QU O070107.....0011100103.....001
 * 0300102001100207..........00107.....0010600101001100307....................00093
 * 00115001080407...................................0507.........................00
 * 101001060607..............................001030707.............................
 * ......0000002066740013994
 *  Message example - odds/probs with betting off; runner 4 scratched 
 * PBCRA - GWANGMYEONG             0088REAL010293EX F070107.....0023500209.....002
 * 040019700198020700213.....00205.....00204002220020703070023600207..........00229
 * 00209002280407...................................0507001960022000208..........00
 * 208002000607001870022700204.....00192.....001850707002110022500223.....002320020
 * 7.....0000003549290014346
 * 
 * PBCRA - GWANGMYEONG             0088REAL010293QU F070107.....0011100103.....001
 * 0300102001100207..........00107.....0010600101001100307....................00093
 * 00115001080407...................................0507.........................00
 * 101001060607..............................001030707.............................
 * ......0000002066740013985
 * 
 * </pre>
 */
public class PBGATE extends BaseBean {
	private PBDAO	dao	= null;

	public PBGATE() {
		this(new NewTray());
	}

	public PBGATE(Tray reqTray) {

		/*String meet_cd = reqTray.getString("meet_cd");
		if ("003".equals(meet_cd)) {
			super.setConnectionName("jdbc/mra");
		}*/
		dao = new PBDAO();
	}

	public boolean insert(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.insert(conn, reqTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, "at PBGATE.insert - " + ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
}
