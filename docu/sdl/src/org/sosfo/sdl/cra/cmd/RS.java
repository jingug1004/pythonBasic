package org.sosfo.sdl.cra.cmd;

import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.log.Log;
import org.sosfo.sdl.cra.gate.PTGATE;
import org.sosfo.sdl.cra.gate.RSGATE;

/**
 * RS : Prices
 * 
 * @author yunkidon@sosfo.or.kr
 * 
 *         <pre>
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
public class RS extends SDL {

	public RS(String SDL) throws AppException {
		super(SDL);
	}

	/*
	 * 공통정보를 파싱하고 각 타입별 세부 정보를 파싱한다.
	 * 
	 * @see org.sosfo.sdl.cra.cmd.SDLCommand#doProcess()
	 */
	public boolean doProcess() throws AppException {
		try {
			RSGATE gate = new RSGATE(sdlTray);
			return gate.insert(sdlTray);

		} catch (AppException e) {
			Log.error("ERROR", this, "at RS.doProcess - " + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at RS.doProcess - " + ex);
			throw new AppException("", ex);
		}
	}

	public static void main(String args[]) {

		String[] sdl = new String[5];
		
		sdl[0] = "RSCRA - GWANGMYEONG             0826REAL060024QU U01N0203/06000000004001288";
		sdl[1] = "RSCRA - GWANGMYEONG             0826REAL060024EX U01N0203/06000000006201283";
		sdl[2] = "RSCRA - GWANGMYEONG             0826REAL060027TLAU01N0303/05/06000000005701472";
		sdl[3] = "RSCRA - GWANGMYEONG             0826REAL060036PLCU02N01030000000011N0106000000002301924";
		sdl[4] = "RSCRA - GWANGMYEONG             0826REAL060021WINU01N0103000000001401179";
		
		try {

			for (int i = 0; i < sdl.length; i++) {
				RS rs = new RS(sdl[i]);
				rs.doProcess();
			}

			/*
			 * RS rs = new RS(sdl); rs.doProcess();
			 */
		} catch (Exception e) {
			Log.error("ERROR", "", e);
		}
	}

}
