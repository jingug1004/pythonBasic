package org.sosfo.sdl.cra.gate;

import java.sql.Connection;

import org.sosfo.framework.business.BaseBean;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.exception.ExceptionManager;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.tray.NewTray;
import org.sosfo.framework.tray.Tray;
import org.sosfo.sdl.cra.dao.TRDAO;

/**
 * TR : TLA Runner Totals ( 선수 조합별 포함 )
 * 
 * @author
 * 
 * <pre>
 * 
 *   ++ SDL Header
 *          'TR'                       &lt; 2&gt; Message code
 *          'CRA - GWANGMYEONG             '   &lt;30&gt; Meet name
 *          '0088'                    &lt; 4&gt; Perf number
 *          'REAL'                  &lt; 4&gt; Type of Perf
 *          '01'                        &lt; 2&gt; Race numbe
 *          '0583'                    &lt; 4&gt; Message length
 *         ++ SDL Data
 *          'TLA'                   [ 3] Pool
 *          'O'                        [ 1] Pool Status; O:open; F:close
 *          '0'                         [ 1] Part number; 0:not used;
 *          '35'                       [ 2] Number of combinations 
 *                                             (R1 R2 R3 $$$$$$$$$$) followed
 *          '07'                       [ 2] Number of runners
 *          '01'                       [ 2] R1
 *          '02'                       [ 2] R2
 *          '03'                       [ 2] R3
 *          '0000015212'       [10] R1/R2/R3 Runner total; unit=100 Won;
 *          '01 02 04 0000017415'              [2][2][2][10] ....
 *          '01 02 05 0000019043'              ....
 *          '01 02 06 0000018107'
 *          '01 02 07 0000017558'
 *          '01 03 04 0000018695'
 *          '01 03 05 0000019285'
 *          '01 03 06 0000017802'
 *          '01 03 07 0000017150'
 *          '01 04 05 0000015326'
 *          '01 04 06 0000016303'
 *          '01 04 07 0000014814'
 *          '01 05 06 0000018953'
 *          '01 05 07 0000017934'
 *          '01 06 07 0000018618'
 *          '02 03 04 0000018054'
 *          '02 03 05 0000016559'
 *          '02 03 06 0000017925'
 *          '02 03 07 0000017418'
 *          '02 04 05 0000017026'
 *          '02 04 06 0000017772'
 *          '02 04 07 0000017575'
 *          '02 05 06 0000016434'
 *          '02 05 07 0000016301'
 *          '02 06 07 0000017046'
 *          '03 04 05 0000018604'
 *          '03 04 06 0000018380'
 *          '03 04 07 0000017968'
 *          '03 05 06 0000018177'
 *          '03 05 07 0000016091'
 *          '03 06 07 0000018793'
 *          '04 05 06 0000017890'
 *          '04 05 07 0000017529'
 *          '04 06 07 0000018288'
 *          '05 06 07 0000018668'              ....
 *          '00000061471300'      [14] Pool total; unit=1 Won;
 *         ++ SDL Checksum
 *          '29295'                        &lt; 5&gt; Checksum 
 *  Message example - runner totals with betting on
 * TRCRA - GWANGMYEONG             0088REAL010583TLAO03507010203000001521201020400
 * 00017415010205000001904301020600000181070102070000017558010304000001869501030500
 * 00019285010306000001780201030700000171500104050000015326010406000001630301040700
 * 00014814010506000001895301050700000179340106070000018618020304000001805402030500
 * 00016559020306000001792502030700000174180204050000017026020406000001777202040700
 * 00017575020506000001643402050700000163010206070000017046030405000001860403040600
 * 00018380030407000001796803050600000181770305070000016091030607000001879304050600
 * 000178900405070000017529040607000001828805060700000186680000006147130029295
 * 
 *  Message example - runner totals with betting on; runner 4 scratched
 * TRCRA - GWANGMYEONG             0088REAL010583TLAO035070102030000015212010204..
 * ........010205000001904301020600000181070102070000017558010304..........01030500
 * 0001928501030600000178020103070000017150010405..........010406..........010407..
 * ........010506000001895301050700000179340106070000018618020304..........02030500
 * 0001655902030600000179250203070000017418020405..........020406..........020407..
 * ........020506000001643402050700000163010206070000017046030405..........030406..
 * ........030407..........030506000001817703050700000160910306070000018793040506..
 * ........040507..........040607..........05060700000186680000003530740028671
 * 
 *  Message example - runner totals with betting off; runner 4 scratched
 * TRCRA - GWANGMYEONG             0088REAL010583TLAF035070102030000015212010204..
 * ........010205000001904301020600000181070102070000017558010304..........01030500
 * 0001928501030600000178020103070000017150010405..........010406..........010407..
 * ........010506000001895301050700000179340106070000018618020304..........02030500
 * 0001655902030600000179250203070000017418020405..........020406..........020407..
 * ........020506000001643402050700000163010206070000017046030405..........030406..
 * ........030407..........030506000001817703050700000160910306070000018793040506..
 * ........040507..........040607..........05060700000186680000003530740028662
 * 
 * 
 * </pre>
 */
public class TRGATE extends BaseBean {
	private TRDAO	dao	= null;

	public TRGATE() {
		this(new NewTray());
	}

	public TRGATE(Tray reqTray) {

		/*String meet_cd = reqTray.getString("meet_cd");
		if ("003".equals(meet_cd)) {
			super.setConnectionName("jdbc/mra");
		}*/
		dao = new TRDAO();
	}

	public boolean insert(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.insert(conn, reqTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, "at TRGATE.insert - " + ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
}
