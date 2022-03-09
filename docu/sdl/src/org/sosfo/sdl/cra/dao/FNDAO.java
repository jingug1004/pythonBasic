package org.sosfo.sdl.cra.dao;

import java.sql.Connection;

import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.sql.QueryRunner;
import org.sosfo.framework.tray.NewTray;
import org.sosfo.framework.tray.Tray;
import org.sosfo.framework.util.StringUtil;

/**
 * FN : Finishers
 * 
 * @author <pre>
 * ++ SDL Header
 *          'FN'                               [ 2] Message code
 *          'CRA - GWANGMYEONG             '   [30] Meet name
 *          '0088'                           [ 4] Perf number
 *          'REAL'                         [ 4] Type of Perf
 *          '01'                               [ 2] Race numbe
 *          '0024'                           [ 4] Message length
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
 *          '01178'                            [ 5] Checksum
 *  Message example - unofficial prices
 * FNCRA - GWANGMYEONG             0088REAL01002403010105020103030101010401178
 * 
 * FNMRA                           0604REAL02 0024 03 010103 020101 030104 0106 01179
 * FNMRA                           0604REAL01 0022 03 010101 020103 030106 00   01078
 * </pre>
 */
public class FNDAO {

    /**
     * <pre>
	 * 0. FN로직에 맞게 Tray 로딩
	 * 1. 삭제
	 * 2. 재입력
	 * </pre>
     */
    public boolean insert(Connection conn, Tray sdlTray) throws AppException {

	try {
	    loadFN(sdlTray);

	    Log.debug("", this, sdlTray.toString());

	    Tray rsTray[] = loadTray(sdlTray);

	    boolean success = false;

	    conn.setAutoCommit(false);
	    // 삭제
	    deleteFN(conn, sdlTray);
	    // 입력
	    success = insertFN(conn, rsTray);

	    conn.commit();

	    return success;

	} catch (AppException e) {
	    Log.error("ERROR", this, "at FNDAO.insert() - " + e);
	    try {
		conn.rollback();
	    } catch (Exception e1) {}
	    throw e;
	} catch (Exception e) {
	    Log.error("ERROR", this, "at FNDAO.insert()- " + e);
	    try {
		conn.rollback();
	    } catch (Exception e1) {}
	    throw new AppException("", e);
	} finally {
	    try {
		conn.setAutoCommit(true);
	    } catch (Exception e) {}
	}
    }

    /**
     * FN로직에 맞게 Tray 로딩
     * 
     * @param conn
     * @param sdlTray
     * @throws AppException
     */
    private void loadFN(Tray sdlTray) throws AppException {
	/*
	 * '03' [ 2] Position count '01' [ 2] Position x '01' [ 2] Number of runner in position x '05' [ 2] Runner x '02 01 03' [2][2][2].... '03 01 01' .... '01' [ 2] Number of scratched runners '04' [ 2] Scratched runner(s) String
	 */

	String sdl = sdlTray.getString("sdl");
	try {
	    // pass header 2,30,4,4,2,4
	    int idx = 46;

	    int position_count = StringUtil.parseInt(StringUtil.substring(sdl, idx, idx += 2));
	    sdlTray.setString("position_count", String.valueOf(position_count));

	    String[] position = new String[position_count];
	    String[] NumOfRunnerInPosition = new String[position_count];

	    for (int i = 0; i < position_count; i++) {
		position[i] = StringUtil.substring(sdl, idx, idx += 2);
		NumOfRunnerInPosition[i] = StringUtil.substring(sdl, idx, idx += 2);

		String[] runner = new String[StringUtil.parseInt(NumOfRunnerInPosition[i], 0)];
		for (int j = 0; j < StringUtil.parseInt(NumOfRunnerInPosition[i], 0); j++) {
		    runner[j] = StringUtil.substring(sdl, idx, idx += 2);
		}
		sdlTray.setString("runner" + i, runner);
	    }

	    sdlTray.setString("position", position);
	    sdlTray.setString("NumOfRunnerInPosition", NumOfRunnerInPosition);

	    int NumOfScratchedRunners = StringUtil.parseInt(StringUtil.substring(sdl, idx, idx += 2));
	    sdlTray.setString("NumOfScratchedRunners", String.valueOf(NumOfScratchedRunners));

	    if (NumOfScratchedRunners > 0) {
		String ScratchedRunner[] = new String[NumOfScratchedRunners];

		for (int i = 0; i < NumOfScratchedRunners; i++) {
		    ScratchedRunner[i] = StringUtil.substring(sdl, idx, idx += 2);
		}
		sdlTray.setString("ScratchedRunner", ScratchedRunner);
	    }

	} catch (Exception ex) {
	    Log.error("ERROR", this, "at FNDAO.loadFN - " + ex);
	    throw new AppException("", ex);
	}
    }

    /**
     * sdlTray로 부터 한건식의 Tray로 변형
     * 
     * @param sdlTray
     * @return rsTray[]
     * @throws AppException
     */
    private Tray[] loadTray(Tray sdlTray) throws AppException {
	try {
	
	    int position_count = sdlTray.getInt("position_count");
	    Tray[] rsTray = null;
    
	    if(position_count==0) {	//경주취소시
	    	rsTray = new NewTray[1];
	    	rsTray[0] = new NewTray();
	    	rsTray[0].setString("meet_cd", sdlTray.getString("meet_cd"));
	    	rsTray[0].setString("stnd_year", sdlTray.getString("stnd_year"));
			rsTray[0].setString("tms", sdlTray.getString("tms"));
			rsTray[0].setString("day_ord", sdlTray.getString("day_ord"));
			rsTray[0].setString("race_no", sdlTray.getString("race_no"));
			rsTray[0].setString("rank", "C");	//경주취소
			rsTray[0].setString("seq", "1");
			rsTray[0].setString("reg_no", "0");
	    } else {
		    int row_count = 0;
		    for (int i = 0; i < position_count; i++) {
		    	row_count += sdlTray.getInt("NumOfRunnerInPosition", i);
		    }
		    int NumOfScratchedRunners = sdlTray.getInt("NumOfScratchedRunners");
	
		    rsTray = new NewTray[row_count + NumOfScratchedRunners];
		    // rank, seq, reg_no
		    int idx = 0;
	
		    for (int i = 0; i < position_count; i++, idx++) {
	
				if (rsTray[idx] == null) rsTray[idx] = new NewTray();
		
				rsTray[idx].setString("meet_cd", sdlTray.getString("meet_cd"));
				rsTray[idx].setString("stnd_year", sdlTray.getString("stnd_year"));
				rsTray[idx].setString("tms", sdlTray.getString("tms"));
				rsTray[idx].setString("day_ord", sdlTray.getString("day_ord"));
				rsTray[idx].setString("race_no", sdlTray.getString("race_no"));
				rsTray[idx].setString("rank", String.valueOf(sdlTray.getInt("position", i)));
				rsTray[idx].setString("seq", String.valueOf(1));
				rsTray[idx].setString("reg_no", sdlTray.getString("runner" + i, 0));
		
				for (int j = 1; j < sdlTray.getInt("NumOfRunnerInPosition", i); j++) {
				    idx++;
				    if (rsTray[idx] == null) rsTray[idx] = new NewTray();
		
				    rsTray[idx].setString("meet_cd", sdlTray.getString("meet_cd"));
				    rsTray[idx].setString("stnd_year", sdlTray.getString("stnd_year"));
				    rsTray[idx].setString("tms", sdlTray.getString("tms"));
				    rsTray[idx].setString("day_ord", sdlTray.getString("day_ord"));
				    rsTray[idx].setString("race_no", sdlTray.getString("race_no"));
				    rsTray[idx].setString("rank", String.valueOf(sdlTray.getInt("position", i)));
				    rsTray[idx].setString("seq", String.valueOf(j + 1));
				    rsTray[idx].setString("reg_no", sdlTray.getString("runner" + i, j));
				}
		
				Log.debug("", this, rsTray[idx].toString());
		    }

		    if (NumOfScratchedRunners > 0) {
	
				for (int j = row_count, i = 0; j < (row_count + NumOfScratchedRunners); j++, i++) {
				    if (rsTray[j] == null) rsTray[j] = new NewTray();
		
				    rsTray[j].setString("meet_cd", sdlTray.getString("meet_cd"));
				    rsTray[j].setString("stnd_year", sdlTray.getString("stnd_year"));
				    rsTray[j].setString("tms", sdlTray.getString("tms"));
				    rsTray[j].setString("day_ord", sdlTray.getString("day_ord"));
				    rsTray[j].setString("race_no", sdlTray.getString("race_no"));
				    rsTray[j].setString("rank", "F");
				    rsTray[j].setString("seq", String.valueOf(i + 1));
				    rsTray[j].setString("reg_no", sdlTray.getString("ScratchedRunner", i));
		
				    Log.debug("", this, rsTray[j].toString());
				}
		    }
		    	    
	    }

	    return rsTray;
	} catch (Exception ex) {
	    Log.error("ERROR", this, "at FNDAO.loadTray - " + ex);
	    throw new AppException("", ex);
	}

    }

    private boolean insertFN(Connection conn, Tray[] rsTray) throws AppException {
	StringBuffer sb = new StringBuffer();
	sb.append("\n insert into tbes_sdl_fn ( ");
	sb.append("\n meet_cd                   ");
	sb.append("\n , stnd_year               ");
	sb.append("\n , tms                     ");
	sb.append("\n , day_ord                 ");
	sb.append("\n , race_no                 ");
	sb.append("\n , rank                    ");
	sb.append("\n , fn_seq                   ");
	sb.append("\n , reg_no                  ");
	sb.append("\n , inst_id                 ");
	sb.append("\n , inst_dt                 ");
	sb.append("\n , updt_id                 ");
	sb.append("\n , updt_dt                 ");
	sb.append("\n ) values (                ");
	sb.append("\n :meet_cd                  ");
	sb.append("\n , :stnd_year              ");
	sb.append("\n , :tms                    ");
	sb.append("\n , :day_ord                ");
	sb.append("\n , :race_no                ");
	sb.append("\n , :rank                   ");
	sb.append("\n , :seq                    ");
	sb.append("\n , :reg_no                 ");
	sb.append("\n , 'SDL'                 ");
	sb.append("\n , sysdate                 ");
	sb.append("\n , 'SDL'                 ");
	sb.append("\n , sysdate                 ");
	sb.append("\n )                         ");

	try {
	    QueryRunner runner = new QueryRunner(sb.toString());
System.out.println("call insertFN~~~~");
System.out.println(runner.toString());
	    Log.debug("", this, runner.toString());
	    return runner.updateBatch(conn, rsTray);
	} catch (AppException e) {
	    Log.error("ERROR", this, "at FNDAO.insertFN()" + e);
	    throw e;
	} catch (Exception ex) {
	    Log.error("ERROR", this, "at FNDAO.insertFN()" + ex);
	    throw new AppException("", ex);
	}
    }

    private int deleteFN(Connection conn, Tray sdlTray) throws AppException {
	StringBuffer sb = new StringBuffer();

	sb.append("\n delete from tbes_sdl_fn	   ");
	sb.append("\n where                        ");
	sb.append("\n     1=1                      ");
	sb.append("\n     and meet_cd=:meet_cd     ");
	sb.append("\n     and stnd_year=:stnd_year ");
	sb.append("\n     and tms=:tms             ");
	sb.append("\n     and day_ord=:day_ord     ");
	sb.append("\n     and race_no=:race_no     ");

	try {
	    QueryRunner runner = new QueryRunner(sb.toString());
	    runner.setParams(sdlTray);
	    Log.debug("", this, runner.toString());
	    return runner.update(conn);

	} catch (AppException e) {
	    Log.error("ERROR", this, "at FNDAO.insertFN()" + e);
	    throw e;
	} catch (Exception ex) {
	    Log.error("ERROR", this, "at FNDAO.insertFN()" + ex);
	    throw new AppException("", ex);
	}
    }
}
