package org.sosfo.sdl.cra.cmd;

import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.log.Log;
import org.sosfo.sdl.cra.gate.WRGATE;

/**
 * WR : WPS Runner Totals , 단,연승 ( WIN, PLC )식 매출액(선수별)
 * 
 * @author yunkidon@sosfo.or.kr
 * 
 * <pre>
 * ee the examples
 * 
 * + SDL Header
 *          'WR'                               &lt; 2&gt; Message code
 *          'CRA - GWANGMYEONG             '   &lt;30&gt; Meet name
 *          '0088'                             &lt; 4&gt; Perf number
 *          'REAL'                           &lt; 4&gt; Type of Perf
 *          '01'                                 &lt; 2&gt; Race number
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
public class WR extends SDL {

	public WR(String SDL) throws AppException {
		super(SDL);
	}

	/*
	 * 공통정보를 파싱하고 각 타입별 세부 정보를 파싱한다.
	 * @see org.sosfo.sdl.cra.cmd.SDLCommand#doProcess()
	 */
	public boolean doProcess() throws AppException {
		try {
			WRGATE gate = new WRGATE(sdlTray);
			return gate.insert(sdlTray);
		} catch (AppException e) {
			Log.error("ERROR", this, "at WR.doProcess() - " + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at WR.doProcess() - " + ex);
			throw new AppException("", ex);
		}
	}

	public static void main(String args[]) {
			
		//String sdl = "WRCRA - GWANGMYEONG             0088REAL010180WINO07000002244600000226580000022083000002287200000200430000022434000002240300000000154939PLCO0700000258200000026604000002618300000264330000023658000002689600000257560000000018135009203";
		//String sdl = "WRCHA                           0088REAL010180WINO07000002244600000226580000022083000002287200000200430000022434000002240300000000154939PLCO0700000258200000026604000002618300000264330000023658000002689600000257560000000018135009203";
		//String sdl = "WRBUS                           0088REAL010180WINO07000002244600000226580000022083000002287200000200430000022434000002240300000000154939PLCO0700000258200000026604000002618300000264330000023658000002689600000257560000000018135009203";
		//String sdl = "WRMRA                           0088REAL010180WINO07000002222700000125730000020495000003726100000199020000019204000002304800000000147393PLCO0700000287650000025384000002938600000210980000019882000001950300000278650000000019098009203";
		
		//String sdl = "WRMRA                           0991REAL020180WINO07000000000200000000010000000121000000000100000001210000000121000000000100000000000368PLCO0700000000120000000114000000001100000000120000000014000000011100000000110000000000028508962";

		String sdl ="WRMRA                           0991TEST030080WINF06..............................0000000021000000002100000000210000000000006303920";
		
		try {
			WR wr = new WR(sdl);
			wr.doProcess();
		} catch (Exception e) {
			Log.error("ERROR", "", e);
		}
	}
}
