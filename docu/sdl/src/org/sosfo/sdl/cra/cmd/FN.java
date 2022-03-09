package org.sosfo.sdl.cra.cmd;

import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.log.Log;
import org.sosfo.sdl.cra.gate.FNGATE;

/**
 * FN : Finishers
 * 
 * @author yunkidon@sosfo.or.kr
 * 
 * <pre>
 * ++ SDL Header
 *          'FN'                               &lt; 2&gt; Message code
 *          'CRA - GWANGMYEONG             '   &lt;30&gt; Meet name
 *          '0088'                           &lt; 4&gt; Perf number
 *          'REAL'                         &lt; 4&gt; Type of Perf
 *          '01'                               &lt; 2&gt; Race numbe
 *          '0024'                           &lt; 4&gt; Message length
 * ++ SDL Data
 *          '03'                               [ 2] Position count
 *          '01'                               [ 2] Position x
 *          '01'                               [ 2] Number of runner in position x
 *          '05'                               [ 2] Runner x
 *          '02 01 03'                     [2][2][2]....
 *          '03 01 01'                         ....
 *          '01'                               [ 2] Number of scratched runners
 *          '04'                               [ 2] Scratched runner(s) String
 * ++ SDL Checksum
 *          '01178'                            &lt; 5&gt; Checksum
 *  Message example - unofficial prices
 * FNCRA - GWANGMYEONG             0088REAL01002403010105020103030101010401178
 * 
 * 
 * </pre>
 */
public class FN extends SDL {

	public FN(String SDL) throws AppException {
		super(SDL);
	}

	/*
	 * 공통정보를 파싱하고 각 타입별 세부 정보를 파싱한다.
	 * @see org.sosfo.sdl.cra.cmd.SDLCommand#doProcess()
	 */
	public boolean doProcess() throws AppException {
		try {
			FNGATE gate = new FNGATE(sdlTray);
			return gate.insert(sdlTray);
		} catch (AppException e) {
			Log.error("ERROR", this, "at FN.doProcess() - " + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at FN.doProcess() - " + ex);
			throw new AppException("", ex);
		}
	}

	public static void main(String args[]) {
		//String sdl = "FNCRA - GWANGMYEONG             0088TEST010024020101050202030100";
		//String sdl = "FNCRA - GWANGMYEONG             0088TEST01002403010105020103030201040401178";
		//String sdl = "FNCRA - GWANGMYEONG             0088TEST0100240201020501020103010401178";
		//String sdl = "FNCRA - GWANGMYEONG             0088TEST01002403010105020103030101010401178";
		//String sdl = "FNCHA                           0088TEST01002403010105020103030101010401178";
		//String sdl = "FNBUS                           0088TEST01002403010105020103030101010401178";
		//String sdl = "FNMRA                           0088TEST01002403010105020103030101010401178";
		
		//String sdl = "FNMRA                           0991TEST050022030101010201020301030001074";
		String sdl ="FNMRA                           0642REAL0300260301010102010503010602020301279";
		
		try {
			FN fn = new FN(sdl);
			fn.doProcess();
		} catch (Exception e) {
			Log.error("ERROR", "", e);
		}
	}
}
