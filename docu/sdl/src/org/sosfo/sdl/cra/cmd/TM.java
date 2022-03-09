package org.sosfo.sdl.cra.cmd;

import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.log.Log;
import org.sosfo.sdl.cra.gate.TMGATE;

/**
 * TM : Time Info
 * 
 * @author Lee junghye
 * 
 * <pre>
 * ++ SDL Header
 *          'TM'                               [ 2] Message code
 *          'CRA - GWANGMYEONG             '   [30] Meet name
 *          '0088'                             [ 4] Perf number
 *          'REAL'                           [ 4] Type of Perf
 *          '01'                                 [ 2] Race number
 *          '0022'                             [ 4] Message length
 *  ++ SDL Data
 *          '10082008'                     [ 8] Perf Date MMDDYYYY
 *          '104001'                         [ 6] Perf Time HHMMSS; 48 hours clock may be used
 *          '111600'                         [ 6] Post Time;        48 hours clock may be used
 *          '35'                                 [ 2] mtp;  **:passed or not available
 *  ++ SDL Checksum
 *          '01098'                           [ 5] Checksum
 * 
 * 
 * 
 *  Message example
 * TMCRA - GWANGMYEONG             0088REAL010022100820081039071116003601113
 * TMCRA - GWANGMYEONG             0088REAL01002210062008142159......**01071
 * TMCRA - GWANGMYEONG             0088REAL020022100620083536253602002501115
 * TMCRA - GWANGMYEONG             0088REAL020022100620083558013602000301109
 * TMCRA - GWANGMYEONG             0991TEST020022122720083959594028002701141
 * 
 * </pre>
 */
public class TM extends SDL {

	public TM(String SDL) throws AppException {
		super(SDL);
	}

	/*
	 * 공통정보를 파싱하고 각 타입별 세부 정보를 파싱한다.
	 * @see org.sosfo.sdl.cra.cmd.SDLCommand#doProcess()
	 */
	public boolean doProcess() throws AppException {
		try {
			TMGATE gate = new TMGATE(sdlTray);
			return gate.insert(sdlTray);

		} catch (AppException e) {
			Log.error("ERROR", this, "at SP.doProcess - " + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at SP.doProcess - " + ex);
			throw new AppException("", ex);
		}
	}

	public static void main(String args[]) {
		//String sdl = "TMCRA - GWANGMYEONG             0088REAL01002201222009353625......**01071";
		//String sdl = "TMCHA                           0088REAL010022012220091039071116003601113";
		//String sdl = "TMBUS                           0088REAL010022012220091039071116003601113";
		//String sdl = "TMMRA                           0088REAL010022012220091039071116003601113";
		
		String sdl = "TMMRA                           0991TEST02002201222009395400394400**01101";
		try {

			TM tm = new TM(sdl);
			Log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> TM RESULT : " + tm.doProcess());

		} catch (Exception e) {
			Log.error("ERROR", "", e);
		}
	}
}
