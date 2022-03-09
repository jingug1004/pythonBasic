package org.sosfo.sdl.cra.gate;

import java.sql.Connection;

import org.sosfo.framework.business.BaseBean;
import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.exception.ExceptionManager;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.tray.NewTray;
import org.sosfo.framework.tray.Tray;
import org.sosfo.sdl.cra.dao.TPDAO;

/**
 * TP : TLA Probables , »ïº¹½Â Áß°£ ¹è´ç·ü ( ¼±¼ö Á¶ÇÕº° Æ÷ÇÔ )
 * 
 * @author
 * 
 * <pre>
 * ++ SDL Header
 *          'TP'                       &lt; 2&gt; Message code
 *          'CRA - GWANGMYEONG             '   &lt;30&gt; Meet name
 *          '0088'                    &lt; 4&gt; Perf number
 *          'REAL'                 &lt; 4&gt; Type of Perf
 *          '01'                        &lt; 2&gt; Race numbe
 *          '0408'                    &lt; 4&gt; Message length
 *  ++ SDL Data
 *          'TLA'            [ 3] Pool
 *          'O'                 [ 1] Pool Status; O:open; F:close
 *          '0'                  [ 1] Part number; 0:not used;
 *          '35'                [ 2] Number of combinations
 *                                     (R1 R2 R3 ppppp) followed
 *          '07'                [ 2] Number of runners
 *          '01'                [ 2] R1
 *          '02'                [ 2] R2
 *          '03'                [ 2] R3
 *          '00283'          [ 5] R1/R2/R3 Runner prob
 *          '01 02 04 00247'            [2][2][2][5] ....
 *          '01 02 05 00226'             ....
 *          '01 02 06 00238'
 *          '01 02 07 00245'
 *          '01 03 04 00230'
 *          '01 03 05 00223'
 *          '01 03 06 00242'
 *          '01 03 07 00251'
 *          '01 04 05 00281'
 *          '01 04 06 00264'
 *          '01 04 07 00290'
 *          '01 05 06 00227'
 *          '01 05 07 00240'
 *          '01 06 07 00231'
 *          '02 03 04 00238'
 *          '02 03 05 00260'
 *          '02 03 06 00240'
 *          '02 03 07 00247'
 *          '02 04 05 00253'
 *          '02 04 06 00242'
 *          '02 04 07 00245'
 *          '02 05 06 00262'
 *          '02 05 07 00264'
 *          '02 06 07 00252'
 *          '03 04 05 00231'
 *          '03 04 06 00234'
 *          '03 04 07 00239'
 *          '03 05 06 00237'
 *          '03 05 07 00267'
 *          '03 06 07 00229'
 *          '04 05 06 00241'
 *          '04 05 07 00245'
 *          '04 06 07 00235'
 *          '05 06 07 00231'        ....
 *          '00000061471300'     [14] Pool total; unit=1 Won;
 *         ++ SDL Checksum
 *          '20501'                       &lt; 5&gt; Checksum
 * 
 * 
 *  Message example - odds/probs with betting on
 * TPCRA - GWANGMYEONG             0088REAL010408TLAO0350701020300283010204002470102050022601020600238010207002450103040023001030500223010306002420103070025101040500281010406002640104070029001050600227010507002400106070023102030400238020305002600203060024002030700247020405002530204060024202040700245020506002620205070026402060700252030405002310304060023403040700239030506002370305070026703060700229040506002410405070024504060700235050607002310000006147130020501
 * 
 *  Message example - odds/probs with betting on; runner 4 scratched
 * TPCRA - GWANGMYEONG             0088REAL010408TLAO0350701020300162010204.....010205001300102060013601020700141010304.....010305001280103060013901030700144010405.....010406.....010407.....010506001300105070013801060700133020304.....020305001490203060013802030700142020405.....020406.....020407.....020506001500205070015202060700145030405.....030406.....030407.....030506001360305070015403060700132040506.....040507.....040607.....050607001320000003530740020177
 * 
 *  Message example - odds/probs with betting off; runner 4 scratched
 * TPCRA - GWANGMYEONG             0088REAL010408TLAF0350701020300162010204.....010205001300102060013601020700141010304.....010305001280103060013901030700144010405.....010406.....010407.....010506001300105070013801060700133020304.....020305001490203060013802030700142020405.....020406.....020407.....020506001500205070015202060700145030405.....030406.....030407.....030506001360305070015403060700132040506.....040507.....040607.....050607001320000003530740020168
 * 
 * </pre>
 */
public class TPGATE extends BaseBean {
	private TPDAO	dao	= null;

	public TPGATE() {
		this(new NewTray());
	}

	public TPGATE(Tray reqTray) {

		/*String meet_cd = reqTray.getString("meet_cd");
		if ("003".equals(meet_cd)) {
			super.setConnectionName("jdbc/mra");
		}*/
		dao = new TPDAO();
	}

	public boolean insert(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.insert(conn, reqTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, "at TPGATE.insert - " + ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
}
