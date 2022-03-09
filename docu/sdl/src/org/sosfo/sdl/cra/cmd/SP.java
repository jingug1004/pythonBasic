package org.sosfo.sdl.cra.cmd;

import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.log.Log;
import org.sosfo.sdl.cra.gate.SPGATE;

/**
 * SP : WPS Probables (단,연승식 중간배당율), 선수별자료 포함
 * 
 * @author yunkidon@sosfo.or.kr
 * 
 * <pre>
 * ook at the examples
 * 
 * + SDL Header
 *          'SP'                                &lt; 2&gt; Message code
 *          'CRA - GWANGMYEONG             '   &lt;30&gt; Meet name
 *          '0088'                             &lt; 4&gt; Perf number
 *          'REAL'                           &lt; 4&gt; Type of Perf
 *          '01'                                 &lt; 2&gt; Race numbe
 *          '0110'                             &lt; 4&gt; Message length
 * ++ SDL Data
 *          'WIN'                             [ 3] Pool
 *          'O'                                  [ 1] Pool Status; O:open; F:close
 *          '07'                                 [ 2] Number of runner probs followed
 *          '00056'                           [ 5] Runner odds 1
 *          '00055'                           [..] ....
 *          '00057'
 *          '00055'
 *          '00063'
 *          '00056'
 *          '00056'                           [..] ....
 *          '00000015493900'         [14] Pool total; Unit=1 Won; 15493900=15,493,900 Won
 *          'PLC'                              [ 3] Pool
 *          'O'                                   [ 1] Pool Status; O:open; F:close
 *          '07'                                  [ 2] Number of runner probs followed
 *          '00028'                            [ 5] Runner odds 1
 *          '00028'                            [..] ....
 *          '00028'
 *          '00028'
 *          '00031'
 *          '00027'
 *          '00029'                            [..] ....
 *          '00000018135000'          [14] Pool total; Unit=1 Won;
 * ++ SDL Checksum
 *          '05716'                            &lt; 5&gt; Checksum
 * Message example - odds/probs with betting on
 * SPCRA - GWANGMYEONG             0088REAL010110WINO07000560005500057000550006300
 * 0560005600000015493900PLCO070002800028000280002800031000270002900000018135000057
 * 16 
 * 
 * ** Message example - odds/probs with betting on; runner 4 scratched
 * SPCRA - GWANGMYEONG             0088REAL010110WINO07000480004700048.....0005300
 * 0480004800000013206700PLCO07000240002400024.....00027000230002400000015491700056
 * 60
 * 
 * ** Message example - odds/probs with betting off; runner 4 scratched
 * SPCRA - GWANGMYEONG             0088REAL010110WINF07000480004700048.....0005300
 * 0480004800000013206700PLCF07000240002400024.....00027000230002400000015491700056
 * 42
 * </pre>
 */
public class SP extends SDL {

	public SP(String sdl) throws AppException {
		super(sdl);
	}

	/*
	 * 공통정보를 파싱하고 각 타입별 세부 정보를 파싱한다.
	 * @see org.sosfo.sdl.cra.cmd.SDLCommand#doProcess()
	 */
	public boolean doProcess() {
		try {
			SPGATE gate = new SPGATE(sdlTray);
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
		//String sdl = "SPCRA - GWANGMYEONG             0088REAL010110WINO070005600055000570005500063000560005600000015493900PLCO07000280002800028000280003100027000290000001813500005716";
		//String sdl = "SPCRA - GWANGMYEONG             0088REAL010110WINO070006600065000670006500053000660006600000015493900PLCO07000380003800038000380002100037000390001001813500005716";

		//String sdl = "SPCHA                           0088REAL010110WINO070005600055000570005500063000560005600000015493900PLCO07000280002800028000280003100027000290000001813500005716";
		//String sdl = "SPCHA                           0088REAL010110WINO070006600065000670006500053000660006600000015493900PLCO07000380003800038000380002100037000390001001813500005716";

		//String sdl = "SPBUS                           0088REAL010110WINO070005600055000570005500063000560005600000015493900PLCO07000280002800028000280003100027000290000001813500005716";
		//String sdl = "SPBUS                           0088REAL010110WINO070006600065000670006500053000660006600000015493900PLCO07000380003800038000380002100037000390001001813500005716";

		//String sdl = "SPMRA                           0088REAL010110WINO070005600055000570005500063000560005600000015493900PLCO07000280002800028000280003100027000290000001813500005716";
		//String sdl = "SPMRA                           0088REAL010110WINO070006600065000670006500053000660006600000015493900PLCO07000380003800038000380002100037000390001001813500005716";

		String sdl = "SPMRA                           0991REAL020110WINO070116502328000250232800025000250232800000000036800PLCO07000960001000105000960008200010001050000000002850005694";

		try {
			SP sp = new SP(sdl);
			sp.doProcess();
		} catch (Exception e) {
			Log.error("ERROR", "", e);
		}
	}

}
