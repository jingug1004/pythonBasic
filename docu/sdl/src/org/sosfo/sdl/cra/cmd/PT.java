package org.sosfo.sdl.cra.cmd;

import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.log.Log;
import org.sosfo.sdl.cra.gate.PTGATE;

/**
 * PT : Pool Totals
 * 
 * @author yunkidon@sosfo.or.kr
 * 
 * <pre>
 * ++ SDL Header
 *          'PT'                                &lt; 2&gt; Message code
 *          'CRA - GWANGMYEONG             '   &lt;30&gt; Meet name
 *          '0088'                             &lt; 4&gt; Perf number
 *          'REAL'                          &lt; 4&gt; Type of Perf
 *          '01'                                &lt; 2&gt; Race numbe
 *          '0097'                            &lt; 4&gt; Message length
 * ++ SDL Data
 *          '05'                                  [ 2] Number of pool data followed
 *          'WIN'                              [ 3] Pool name
 *          'O'                                   [ 1] Pool Status; O:open; F:close
 *          '00000015493900'          [14] Pool total; unit=1 Won;
 *          'N'                                   [ 1] Pool Scratched? N:normal; S:scratched;
 *          'PLC O 00000018135000 N'           ......
 *          'EX  O 00000049475800 N'
 *          'QU  O 00000029243200 N'
 *          'TLA O 00000061471300 N'
 * ++ SDL Checksum
 *          '05449'                            &lt; 5&gt; Checksum
 * 
 * 
 * 
 *  Message example - totals with betting on
 * PTCRA - GWANGMYEONG             0088REAL01009705WINO00000015493900NPLCO00000018135000NEX O00000049475800NQU O00000029243200NTLAO00000061471300N05449
 * 
 *  Message example - totals with betting on; runner 4 scratched
 * PTCRA - GWANGMYEONG             0088REAL01009705WINO00000013206700NPLCO00000015491700NEX O00000035492900NQU O00000020667400NTLAO00000035307400N05444
 * 
 *  Message example - totals with betting off; runner 4 scratched
 * PTCRA - GWANGMYEONG             0088REAL01009705WINF00000013206700NPLCF00000015491700NEX F00000035492900NQU F00000020667400NTLAF00000035307400N05399
 * 
 * 
 * 
 * </pre>
 */
public class PT extends SDL {

	public PT(String SDL) throws AppException {
		super(SDL);
	}

	/*
	 * 공통정보를 파싱하고 각 타입별 세부 정보를 파싱한다.
	 * @see org.sosfo.sdl.cra.cmd.SDLCommand#doProcess()
	 */
	public boolean doProcess() throws AppException {
		try {
			PTGATE gate = new PTGATE(sdlTray);
			return gate.insert(sdlTray);

		} catch (AppException e) {
			Log.error("ERROR", this, "at PT.doProcess - " + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at PT.doProcess - " + ex);
			throw new AppException("", ex);
		}
	}

	public static void main(String args[]) {
		//String sdl = "PTCRA - GWANGMYEONG             0088REAL01009705WINO00000000001000NPLCO00000000002000NEX O00000000004000NQU O00000000005000NTLAO00000000006000N05449";
		//String sdl = "PTCRA - GWANGMYEONG             0088REAL01009705WINO00000000000100NPLCO00000000000200NEX O00000000000400NQU O00000000000500NTLAO00000000000600N05449";
		//String sdl = "PTCRA - GWANGMYEONG             0088REAL01009705WINO00000015493900NPLCO00000018135000NEX O00000049475800NQU O00000029243200NTLAO00000061471300N05449";
		//String sdl = "PBCHA                           0088REAL01009705WINO00000015493900NPLCO00000018135000NEX O00000049475800NQU O00000029243200NTLAO00000061471300N05449";
		//String sdl = "PBBUS                           0088REAL01009705WINO00000015493900NPLCO00000018135000NEX O00000049475800NQU O00000029243200NTLAO00000061471300N05449";
		//String sdl = "PBMRA                           0088REAL01009705WINO00000015493900NPLCO00000018135000NEX O00000049475800NQU O00000029243200NTLAO00000061471300N05449";

		//String sdl = "PTCRA - GWANGMYEONG             0991TEST06009705WINF00000000082000NPLCF00000000116000NEX F00000000406000NQU F00000000254400NTLAF00000000074300N05331";
		String sdl ="PTCRA - GWANGMYEONG             0991TEST06009705WINF00000000034700NPLCF00000000000000SEX F00000000025600NQU F00000000030600NTLAF00000000000100N05316";
		try {
			PT pt = new PT(sdl);
			pt.doProcess();
		} catch (Exception e) {
			Log.error("ERROR", "", e);
		}
	}

}
