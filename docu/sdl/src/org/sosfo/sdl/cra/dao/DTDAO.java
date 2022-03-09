package org.sosfo.sdl.cra.dao;

import java.sql.Connection;

import org.sosfo.framework.dao.BaseDao;
import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.sql.QueryRunner;
import org.sosfo.framework.tray.Tray;

/**
 * DT : Divisions Total
 * 
 * @author <pre>
 * ++ SDL Header
 *          'DT'                                [ 2] Message code
 *          'CRA - GWANGMYEONG             '   [30] Meet name
 *          '0088'                             [ 4] Perf number
 *          'REAL'                           [ 4] Type of Perf
 *          '01'                                 [ 2] Race numbe
 *          '0048'                             [ 4] Message length
 * ++ SDL Data
 *          '01'                                 [ 2] Association
 *          '01'                                 [ 2] Community
 *          '04'                                 [ 2] Division
 *          '00000173819200'         [14] Sales
 *          '00000053653100'         [14] Refund
 *          '00000120166100'         [14] Net Sales
 * ++ SDL Checksum
 *          '02381'                            [ 5] Checksum
 * 
 * 
 *  Message example - division totals before race official
 * DTCRA - GWANGMYEONG             0088REAL01004801010100000000000000000000000000000000000000000002307
 * DTCRA - GWANGMYEONG             0088REAL01004801010200000000000000000000000000000000000000000002308
 * DTCRA - GWANGMYEONG             0088REAL01004801010300000000000000000000000000000000000000000002309
 * DTCRA - GWANGMYEONG             0088REAL01004801010400000173819200000000000000000000017381920002372
 * DTCRA - GWANGMYEONG             0088REAL01004801010500000000000000000000000000000000000000000002311
 * DTCRA - GWANGMYEONG             0088REAL01004801010600000000000000000000000000000000000000000002312
 * DTCRA - GWANGMYEONG             0088REAL01004801010700000000000000000000000000000000000000000002313
 * DTCRA - GWANGMYEONG             0088REAL01004801010800000000000000000000000000000000000000000002314
 * DTCRA - GWANGMYEONG             0088REAL01004801010900000000000000000000000000000000000000000002315
 * DTCRA - GWANGMYEONG             0088REAL01004801011000000000000000000000000000000000000000000002307
 * 
 * 
 *  Message example - division totals after race official
 * DTCRA - GWANGMYEONG             0088REAL01004801010100000000000000000000000000000000000000000002307
 * DTCRA - GWANGMYEONG             0088REAL01004801010200000000000000000000000000000000000000000002308
 * DTCRA - GWANGMYEONG             0088REAL01004801010300000000000000000000000000000000000000000002309
 * DTCRA - GWANGMYEONG             0088REAL01004801010400000173819200000000536531000000012016610002381
 * DTCRA - GWANGMYEONG             0088REAL01004801010500000000000000000000000000000000000000000002311
 * DTCRA - GWANGMYEONG             0088REAL01004801010600000000000000000000000000000000000000000002312
 * DTCRA - GWANGMYEONG             0088REAL01004801010700000000000000000000000000000000000000000002313
 * DTCRA - GWANGMYEONG             0088REAL01004801010800000000000000000000000000000000000000000002314
 * DTCRA - GWANGMYEONG             0088REAL01004801010900000000000000000000000000000000000000000002315
 * DTCRA - GWANGMYEONG             0088REAL01004801011000000000000000000000000000000000000000000002307
 * 
 * 
 * </pre>
 */
public class DTDAO {

	/**
	 * <pre>
	 * 0. DT ������ �°� Tray �ε�
	 * 1. ��ȸ
	 * 2. 1.������ update or insert ����
	 * </pre>
	 */
	public boolean insert(Connection conn, Tray sdlTray) throws AppException {
		try {
			//DT��Ŷ�� �뷮�� �����Ͱ� �Ͻÿ� ������ ������ Transaction ó���� ������ �߻��� �� �ִ�.
			//���� DTDAO������ �ƹ��ϵ� ���� �ʰ� ������ Daemon�� ���ؼ� Transaction ó��
			
			int count = 0;
			boolean success  = true;
			boolean success2 = true;
			
			//TBES_SDL_DT ���̺� �����ڵ�: comm_no=>07, div_no=> �������ڵ�� ��ȯ�Ͽ� �Է�
			//�ý��� ���Ϸ� ���Ͽ� �������� ��ü---> �ǽð� ó���� ����
			
			loadDT(sdlTray);
			
			if (!isDataZero(conn, sdlTray)) {

				count = findDT(conn, sdlTray); // true : update, false : insert
				
				if (count > 0) {
					success = updateDT(conn, sdlTray);
				} else {
					success = insertDT(conn, sdlTray);
				}			
			}
			
			// sell_cd = '03', comm_no='02', div_no='01'�� �ڷᰡ �����ÿ��� PT�ڷ� ���� 
			// 2012.10.05 ���缱 ����
			String sSellCd = sdlTray.get("sell_cd");
			String sCommNo = sdlTray.get("comm_no");
			String sDivNo  = sdlTray.get("div_no");
			
			if ("03".equals(sSellCd) && "02".equals(sCommNo) && "01".equals(sDivNo)) {
			
				// ���ֺ��� ���� ����Ÿ������ �Ʒ� ��ƾ�� ȣ���ϵ��� ���� �ʿ�(���缱)
				// �����ڷ� -->sell_cd : 03, comm_no : 02, div_no : 03
							
				///////////////////////// ���� ����� �Է�, ���� /////////////////////////
				// ���: sell_cd=02, comm_no=01,  ���� : sell_cd=04, comm_no=07
				// �ӵ� ������ �ĺ���---> �ǽð� ó���� ����
				Tray etcTray = findETC(conn, sdlTray);
				//etcTray = findETC(conn, sdlTray);
				count = findDT(conn, etcTray); // true : update, false : insert
				if (count > 0) {
					success2 = updateDTETC(conn, etcTray);
				} else {
					success2 = insertDTETC(conn, etcTray);
				}
				///////////////////////// ���� ����� �Է�, ���� /////////////////////////			
			}

			if(success && success2) return true;
			//if(success2) return true;
			else return false;
			//return true;

		} catch (AppException e) {
			Log.error("ERROR", this, "at DTDAO.insert() - " + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at DTDAO.insert()- " + ex);
			throw new AppException("", ex);
		}
	}

	/**
	 * DT �ԷµǾ��ִ��� ��ȸ
	 */
	protected int findDT(Connection conn, Tray sdlTray) throws AppException {

		StringBuffer sb = new StringBuffer();
		sb.append("\n select                       ");
		sb.append("\n     count(*) as cnt          ");
		sb.append("\n from                         ");
		sb.append("\n     tbes_sdl_dt              ");
		sb.append("\n where                        ");
		sb.append("\n     1=1                      ");
		sb.append("\n     and meet_cd=:meet_cd     ");
		sb.append("\n     and stnd_year=:stnd_year ");
		sb.append("\n     and tms=:tms             ");
		sb.append("\n     and day_ord=:day_ord     ");
		sb.append("\n     and race_no=:race_no     ");
		sb.append("\n 	 and sell_cd = :sell_cd    ");
		sb.append("\n 	 and comm_no = :comm_no    ");	//���� comm_no
		sb.append("\n 	 and div_no = :div_no      ");  //���� div_no

		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			runner.setParams(sdlTray);
			Log.debug("DB", this, runner.toString());
//System.out.println(runner.toString());
			return ((Tray) runner.query(conn)).getInt("cnt");

		} catch (Exception ex) {
			Log.error("ERROR", this, "at DTDAO.findInfo()" + ex);
			throw new AppException("", ex);
		}
	}

	/**
	 * DT ������ �°� Tray �ε�
	 * 
	 * @param conn
	 * @param sdlTray
	 * @throws AppException
	 */
	private void loadDT(Tray sdlTray) throws AppException {
		String sdl = sdlTray.getString("sdl");

		try {
			int idx = 0;
			// pass header 2,30,4,4,2,4
			idx = 46;
			// body ( 3,1,2(loop),5......,14 ) * 2

			sdlTray.setString("sell_cd",   sdl.substring(idx, idx += 2));
			sdlTray.setString("comm_no",   sdl.substring(idx, idx+=2));	//comm_no�Է�
			//sdlTray.setString("comm_no_old", searchCommCode(sdl.substring(idx, idx +2))); //comm_no�Է�
			//sdlTray.setString("div_no_old", searchDivCode(sdl.substring(idx, idx +=2), sdl.substring(idx, idx + 2))); //comm_no�Է�
			sdlTray.setString("div_no", sdl.substring(idx, idx += 2));					  //div_no�Է�
			sdlTray.setString("sales",     sdl.substring(idx, idx += 14));
			sdlTray.setString("refund",    sdl.substring(idx, idx += 14));
			sdlTray.setString("div_total", sdl.substring(idx, idx += 14));

			Log.debug(sdlTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, "IN DTDAO.loadSP()" + ex);
			throw new AppException("", ex);
		}
	}
	
	/**
	 * insert DT
	 * 
	 * @param conn
	 * @param sdlTray
	 * @return boolean status
	 * @throws AppException
	 */
	protected boolean insertDT(Connection conn, Tray sdlTray) throws AppException {

		StringBuffer sb = new StringBuffer();
		sb.append("\n insert into tbes_sdl_dt(  ");
		sb.append("\n     meet_cd,              ");
		sb.append("\n     stnd_year,            ");
		sb.append("\n     tms,                  ");
		sb.append("\n     day_ord,              ");
		sb.append("\n     race_no,              ");
		sb.append("\n     sell_cd,              ");
		sb.append("\n     comm_no,              ");
		sb.append("\n     div_no,               ");
		sb.append("\n     div_total,            ");
		sb.append("\n     refund,               ");
		sb.append("\n     inst_id,              ");
		sb.append("\n     inst_dt,              ");
		sb.append("\n     updt_id,              ");
		sb.append("\n     updt_dt               ");
		sb.append("\n ) values(                 ");
		sb.append("\n     :meet_cd,             ");
		sb.append("\n     :stnd_year,           ");
		sb.append("\n     :tms,                 ");
		sb.append("\n     :day_ord,             ");
		sb.append("\n     :race_no,             ");
		sb.append("\n     :sell_cd,             ");
		sb.append("\n     :comm_no,             ");
		sb.append("\n     :div_no,              ");
		sb.append("\n     :div_total,           ");
		sb.append("\n     :refund,              ");
		sb.append("\n     'SDL',                ");
		sb.append("\n      sysdate,             ");
		sb.append("\n     'SDL',                ");
		sb.append("\n      sysdate              ");
		sb.append("\n )                         ");
		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			runner.setParams(sdlTray);
			Log.info("DB", this, runner.toString());
//System.out.println(runner.toString());
			return runner.update(conn) > 0;
		} catch (AppException e) {
			Log.error("ERROR", this, "at DTDAO.insertDT()" + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at DTDAO.insertDT()" + ex);
			throw new AppException("", ex);
		}
	}

	/**
	 * update DT
	 * 
	 * @param conn
	 * @param sdlTray
	 * @return boolean status
	 * @throws AppException
	 */
	protected boolean updateDT(Connection conn, Tray sdlTray) throws AppException {

		StringBuffer sb = new StringBuffer();
		
		sb.append("\n update TBES_SDL_DT set        ");
		sb.append("\n 	div_total = :div_total,     ");
		sb.append("\n 	refund=:refund,             ");
		sb.append("\n 	updt_id = 'SDL',            ");
		sb.append("\n 	updt_dt = sysdate           ");
		sb.append("\n where meet_cd = :meet_cd      ");
		sb.append("\n 	and stnd_year = :stnd_year  ");
		sb.append("\n 	and tms = :tms              ");
		sb.append("\n 	and day_ord = :day_ord      ");
		sb.append("\n 	and race_no = :race_no      ");
		sb.append("\n 	and sell_cd = :sell_cd      ");
		sb.append("\n 	and comm_no = :comm_no      ");
		sb.append("\n 	and div_no = :div_no        ");
		
		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			runner.setParams(sdlTray);
			Log.info("DB", this, runner.toString());
//System.out.println(runner.toString());			
			return runner.update(conn) > 0;
		} catch (AppException e) {
			Log.error("ERROR", this, "at DTDAO.updateDT()" + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at DTDAO.updateDT()" + ex);
			throw new AppException("", ex);
		}
	}
	
	
	/**
	 * <pre>
	 * ���� ����� ��ȸ
	 * ����� â������ ���� , ������ �λ����� �����Ѵ�.
	 * ��������� = �½ĺ� ����� - (����+���� �����)
	 * </pre>
	 */
	protected Tray findETC(Connection conn, Tray sdlTray) throws AppException {

		StringBuffer sb = new StringBuffer();
		sb.append("\n select                                              ");
		sb.append("\n     b.meet_cd,b.stnd_year,b.tms,                    ");
		sb.append("\n     b.day_ord,b.race_no,                            ");
		sb.append("\n     a.sell_cd,                                      ");
		sb.append("\n     decode(:meet_cd,'003','07','01') as comm_no,    ");                 
		sb.append("\n     '01' as div_no,                                 ");
		sb.append("\n     '0' as refund,                                  ");
		sb.append("\n     nvl(a.total-b.total,0) as div_total             ");
		sb.append("\n from                                                ");
		sb.append("\n (                                                   ");
		sb.append("\n     select                                          ");
		sb.append("\n         decode(:meet_cd,'003','04','02') as sell_cd,");                    
		sb.append("\n         sum(pool_total) total                       ");
		sb.append("\n     from                                            ");
		sb.append("\n          tbes_sdl_pt                                ");
		sb.append("\n     where                                           ");
		sb.append("\n         meet_cd = :meet_cd                          ");
		sb.append("\n         and stnd_year = :stnd_year                  ");
		sb.append("\n         and tms = :tms                              ");
		sb.append("\n         and day_ord = :day_ord                      ");
		sb.append("\n         and race_no = :race_no                      ");
		sb.append("\n ) a,                                                ");
		sb.append("\n (                                                   ");
		sb.append("\n     select                                          ");
		sb.append("\n        meet_cd,stnd_year,tms,day_ord,race_no,       ");
		sb.append("\n        sum(div_total) as total                      ");
		sb.append("\n     from                                            ");
		sb.append("\n          tbes_sdl_dt                                ");
		sb.append("\n     where                                           ");
		sb.append("\n         meet_cd = :meet_cd                          ");
		sb.append("\n         and stnd_year = :stnd_year                  ");
		sb.append("\n         and tms = :tms                              ");
		sb.append("\n         and day_ord = :day_ord                      ");
		sb.append("\n         and race_no = :race_no                      ");
		sb.append("\n         and sell_cd in('01','03')                   ");
		sb.append("\n     group by                                        ");
		sb.append("\n         meet_cd,stnd_year,tms,day_ord,race_no       ");
		sb.append("\n ) b                                                 ");
		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			runner.setParams(sdlTray);
			Log.debug("DB", this, runner.toString());

			return (Tray) runner.query(conn);

		} catch (Exception ex) {
			Log.error("ERROR", this, "at DTDAO.findETC()" + ex);
			throw new AppException("", ex);
		}
	}
	
	
	/**
	 * insert DT
	 * 
	 * @param conn
	 * @param sdlTray
	 * @return boolean status
	 * @throws AppException
	 */
	protected boolean insertDTETC(Connection conn, Tray sdlTray) throws AppException {

		StringBuffer sb = new StringBuffer();
		sb.append("\n insert into tbes_sdl_dt(  ");
		sb.append("\n     meet_cd,              ");
		sb.append("\n     stnd_year,            ");
		sb.append("\n     tms,                  ");
		sb.append("\n     day_ord,              ");
		sb.append("\n     race_no,              ");
		sb.append("\n     sell_cd,              ");
		sb.append("\n     comm_no,              ");
		sb.append("\n     div_no,               ");
		sb.append("\n     div_total,            ");
		sb.append("\n     refund,               ");
		sb.append("\n     inst_id,              ");
		sb.append("\n     inst_dt,              ");
		sb.append("\n     updt_id,              ");
		sb.append("\n     updt_dt               ");
		sb.append("\n ) values(                 ");
		sb.append("\n     :meet_cd,             ");
		sb.append("\n     :stnd_year,           ");
		sb.append("\n     :tms,                 ");
		sb.append("\n     :day_ord,             ");
		sb.append("\n     :race_no,             ");
		sb.append("\n     :sell_cd,             ");
		sb.append("\n     :comm_no,             ");
		sb.append("\n     :div_no,              ");
		sb.append("\n     :div_total,           ");
		sb.append("\n     :refund,              ");
		sb.append("\n     'SDL',                ");
		sb.append("\n      sysdate,             ");
		sb.append("\n     'SDL',                ");
		sb.append("\n      sysdate              ");
		sb.append("\n )                         ");

		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			runner.setParams(sdlTray);
			Log.info("DB", this, runner.toString());
			return runner.update(conn) > 0;
		} catch (AppException e) {
			Log.error("ERROR", this, "at DTDAO.insertDTETC()" + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at DTDAO.insertDTETC()" + ex);
			throw new AppException("", ex);
		}
	}

	/**
	 * update DT
	 * 
	 * @param conn
	 * @param sdlTray
	 * @return boolean status
	 * @throws AppException
	 */
	protected boolean updateDTETC(Connection conn, Tray sdlTray) throws AppException {

		StringBuffer sb = new StringBuffer();
		sb.append("\n update TBES_SDL_DT set        ");
		sb.append("\n 	div_total = :div_total,     ");
		sb.append("\n 	refund=:refund,             ");
		sb.append("\n 	updt_id = 'SDL',            ");
		sb.append("\n 	updt_dt = sysdate           ");
		sb.append("\n where meet_cd = :meet_cd      ");
		sb.append("\n 	and stnd_year = :stnd_year  ");
		sb.append("\n 	and tms = :tms              ");
		sb.append("\n 	and day_ord = :day_ord      ");
		sb.append("\n 	and race_no = :race_no      ");
		sb.append("\n 	and sell_cd = :sell_cd      ");
		sb.append("\n 	and comm_no = :comm_no      ");
		sb.append("\n 	and div_no = :div_no        ");

		try {
			QueryRunner runner = new QueryRunner(sb.toString());
			runner.setParams(sdlTray);
			Log.info("DB", this, runner.toString());
			return runner.update(conn) > 0;
		} catch (AppException e) {
			Log.error("ERROR", this, "at DTDAO.updateDTETC()" + e);
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, "at DTDAO.updateDTETC()" + ex);
			throw new AppException("", ex);
		} 
	} 
	// �����, ȯ�Ҿ� �� ���� ���� 0�̸� true�� return��
	protected boolean isDataZero(Connection conn, Tray sdlTray) {
		
		//���� �����,ȯ�Ҿ�,���Ǹűݾ׿� ���� 0�� �ڷ�� ���� ó�� ���(���缱)
		int nSales    = Integer.parseInt(sdlTray.get("sales"));
		int nRefund   = Integer.parseInt(sdlTray.get("refund"));
		int nDivTotal = Integer.parseInt(sdlTray.get("div_total"));
		
		if (nSales == 0 && nRefund == 0 && nDivTotal == 0) {
			return true;
		} else {
			return false;
		}
	}
}
