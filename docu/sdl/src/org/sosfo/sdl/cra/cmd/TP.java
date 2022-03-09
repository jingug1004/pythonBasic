package org.sosfo.sdl.cra.cmd;

import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.log.Log;
import org.sosfo.sdl.cra.gate.TPGATE;

/**
 * TP : TLA Probables , 삼복승 중간 배당률 ( 선수 조합별 포함 )
 * 
 * @author yunkidon@sosfo.or.kr
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
public class TP extends SDL {

	public TP(String SDL) throws AppException {
		super(SDL);
	}

	/*
	 * 공통정보를 파싱하고 GATE와 연결한다.
	 * @see org.sosfo.sdl.cra.cmd.SDLCommand#doProcess()
	 */
	public boolean doProcess() throws AppException {
		try {
			TPGATE gate = new TPGATE(sdlTray);
			return gate.insert(sdlTray);
		} catch (AppException e) {
			Log.error("ERROR", this, "at TR.doProcess() - " + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at TR.doProcess() - " + ex);
			throw new AppException("", ex);
		}
	}

	public static void main(String args[]) {
		//String sdl = "TPCRA - GWANGMYEONG             0088REAL010408TLAO0350701020300283010204002470102050022601020600238010207002450103040023001030500223010306002420103070025101040500281010406002640104070029001050600227010507002400106070023102030400238020305002600203060024002030700247020405002530204060024202040700245020506002620205070026402060700252030405002310304060023403040700239030506002370305070026703060700229040506002410405070024504060700235050607002310000006147130020501";
		//String sdl = "TPCRA - GWANGMYEONG             0088REAL010408TLAO0350701020300162010204.....010205001300102060013601020700141010304.....010305001280103060013901030700144010405.....010406.....010407.....010506001300105070013801060700133020304.....020305001490203060013802030700142020405.....020406.....020407.....020506001500205070015202060700145030405.....030406.....030407.....030506001360305070015403060700132040506.....040507.....040607.....050607001320000003530740020177";
		//String sdl = "TPCHA                           0088REAL010408TLAO0350701020300283010204002470102050022601020600238010207002450103040023001030500223010306002420103070025101040500281010406002640104070029001050600227010507002400106070023102030400238020305002600203060024002030700247020405002530204060024202040700245020506002620205070026402060700252030405002310304060023403040700239030506002370305070026703060700229040506002410405070024504060700235050607002310000006147130020501";
		//String sdl = "TPCHA                           0088REAL010408TLAO0350701020300162010204.....010205001300102060013601020700141010304.....010305001280103060013901030700144010405.....010406.....010407.....010506001300105070013801060700133020304.....020305001490203060013802030700142020405.....020406.....020407.....020506001500205070015202060700145030405.....030406.....030407.....030506001360305070015403060700132040506.....040507.....040607.....050607001320000003530740020177";
		//String sdl = "TPBUS                           0088REAL010408TLAO0350701020300283010204002470102050022601020600238010207002450103040023001030500223010306002420103070025101040500281010406002640104070029001050600227010507002400106070023102030400238020305002600203060024002030700247020405002530204060024202040700245020506002620205070026402060700252030405002310304060023403040700239030506002370305070026703060700229040506002410405070024504060700235050607002310000006147130020501";
		//String sdl = "TPBUS                           0088REAL010408TLAO0350701020300162010204.....010205001300102060013601020700141010304.....010305001280103060013901030700144010405.....010406.....010407.....010506001300105070013801060700133020304.....020305001490203060013802030700142020405.....020406.....020407.....020506001500205070015202060700145030405.....030406.....030407.....030506001360305070015403060700132040506.....040507.....040607.....050607001320000003530740020177";
		//String sdl = "TPMRA                           0088REAL010408TLAO0350701020300283010204002470102050022601020600238010207002450103040023001030500223010306002420103070025101040500281010406002640104070029001050600227010507002400106070023102030400238020305002600203060024002030700247020405002530204060024202040700245020506002620205070026402060700252030405002310304060023403040700239030506002370305070026703060700229040506002410405070024504060700235050607002310000006147130020501";
		//String sdl = "TPMRA                           0088REAL010408TLAO0350701020300162010204.....010205001300102060013601020700141010304.....010305001280103060013901030700144010405.....010406.....010407.....010506001300105070013801060700133020304.....020305001490203060013802030700142020405.....020406.....020407.....020506001500205070015202060700145030405.....030406.....030407.....030506001360305070015403060700132040506.....040507.....040607.....050607001320000003530740020177";
		
		String sdl = "TPMRA                           0991REAL020408TLAO0350701020300960010204009600102050096001020600960010207000110103040096001030500960010306009600103070096001040500960010406009600104070096001050600524010507005240106070096002030400524020305005240203060052402030700960020405005240204060052402040700960020506003600205070052402060700960030405005240304060052403040700960030506003600305070052403060700960040506003600405070052404060700960050607009600000000016000020584";

		try {
			TP tp = new TP(sdl);
			tp.doProcess();
		} catch (Exception e) {
			Log.error("ERROR", "", e);
		}
	}
}
