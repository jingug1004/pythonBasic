/*
 * ================================================================================
 * 시스템 : 관리 
 * 소스파일 이름 : snis.rbm.business.rem4091.activity.SaveCronData.java 
 * 파일설명 : 미사리현금입장현화 수동배치 관리 
 * 작성자 : gavi
 * 버전 : 1.0.0 
 * 생성일자 : 2017.02.17
 * 최종수정일자 :
 * 최종수정자 : 
 * 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.business.rem4091.activity;

//import com.jitk.commapi.scheduler.Trade;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.or.kspo.secure.KSPOSecure;

//import org.daemon.kcycle.cash.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.connection.PoolManager;
import org.sosfo.framework.log.Log;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveCronData extends SnisActivity {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	StringBuffer sb = new StringBuffer();
	ResultSet rsAcc241 = null;
	ResultSet rsAcc242 = null;
	ResultSet rs241 = null;
	ResultSet rs242 = null;
	ResultSet rsAccToday241 = null;
	ResultSet rsAccToday242 = null;
	
	PreparedStatement stmt = null;
	PreparedStatement prest = null;
	Connection connOracle = null;
	Connection connAccess241 = null;
	Connection connAccess242 = null;
	Connection connAccessToday241 = null;
	Connection connAccessToday242 = null;
	
	String MAX_TRANSITION_SEQ = "";
	String MAX_TRADE_DATE = "";
	String connName= "";
	
	private static ConfigFactory cf = ConfigFactory.getInstance();
	
	public SaveCronData(){		
		
	}	
	
	/**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	// 사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}

    	execute();

        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
 // 오라클에 해당 데이터가 있는지 조회 
 	public boolean isExist(Connection con, Trade trade) {
 		//SELECT SDATE, LOC AS M_NO, ILNO, RTN, USE_CD, PAY_GBN, QNTY, AMNT, FTIME ");
 		StringBuffer sb = new StringBuffer();
 		sb.append("\n SELECT count(*) ");
 		sb.append("\n   FROM TBRC_T_TRADE_MSR	");
 		sb.append("\n  WHERE SDATE = '"+trade.getSDATE()+"'");
 		sb.append("\n    AND M_NO = '"+trade.getM_NO()+"'");
 		sb.append("\n    AND AMNT = "+trade.getAMNT());
 		sb.append("\n    AND FTIME = '"+trade.getFTIME()+"'");
 		sb.append("\n    AND ILNO = "+trade.getILNO());
 		sb.append("\n    AND PAY_GBN = '"+trade.getPAY_GBN()+"'");
 		sb.append("\n    AND QNTY = "+trade.getQNTY());
 		sb.append("\n    AND RTN = '"+trade.getRTN()+"'");
 		sb.append("\n    AND USE_CD = '"+trade.getUSE_CD()+"'");
 		
 		//System.out.println(sb.toString());
 		
 		boolean isExist = false;
 		Statement stmt = null;
 		ResultSet rs = null;
 		try{
 			
 			stmt = con.createStatement();
 			rs = stmt.executeQuery(sb.toString());
 			if(rs.next()){
 				int count = rs.getInt(1);
 				if(count==1) isExist = true;
 			}
 			
 			rs.close();
 			stmt.close();
 			
 		}catch(Exception e){
 			try{ if(rs!=null) rs.close(); }catch(Exception ee){}
 			try{ if(stmt!=null) stmt.close(); }catch(Exception ee){}
 			
 			isExist = false;
 			throw new RuntimeException("SendTrade class 의 getCountByDataInOracle()에서 DB 오류 발생");
 		}
 		
 		return isExist;
 	}
 	
 	public List<Trade> getTradeData(Connection oraCon, Connection mdbCon, String m_no){
 		StringBuffer sb = new StringBuffer();
 		sb.append("\n SELECT SDATE, LOC AS M_NO, ILNO, RTN, USE_CD, PAY_GBN, QNTY, AMNT, FTIME ");
 		sb.append("\n   FROM MKT_DESC_TB	");
 		sb.append("\n  WHERE SDATE > '20150101'	");
 		sb.append("\n    AND SDATE > NOW() -62	");
 		sb.append("\n    AND LOC = '"+m_no+"'	");
 		System.out.println(sb.toString());
 		
 		ArrayList<Trade> list = new ArrayList<Trade>();
 		Statement stmt = null;
 		ResultSet rs = null;
 		try{
 			
 			stmt = mdbCon.createStatement();
 			rs = stmt.executeQuery(sb.toString());
 			while(rs.next()){
 				Trade tr = new Trade(
 						rs.getString(1),
 						rs.getString(2),
 						rs.getString(3),
 						rs.getString(4),
 						rs.getString(5),
 						new String(rs.getString(6).getBytes("8859_1"), "euc-kr"),
 						rs.getString(7),
 						rs.getString(8),
 						rs.getString(9)
 				);
 				list.add(tr);
 			}
 			
 			rs.close();
 			stmt.close();
 		}catch(Exception e){
 			try{ if(rs!=null) rs.close(); }catch(Exception ee){}
 			try{ if(stmt!=null) stmt.close(); }catch(Exception ee){}
 			
 			throw new RuntimeException("SendTrade class 의 isEqualData()에서 DB 오류 발생");
 		}
 		
 		return list;
 		
 	}
 	
 	
 	public void execute() {
 		Log.debug("DB", this, "자료 전송 시작------------------------------------>");
 		try {
 			long startTime = System.currentTimeMillis();
 						
 			//2. 241mdb 파일을 조회한다.
 			PoolManager conAcc241Manger = PoolManager.getInstance();
 			connAccess241 = conAcc241Manger.getConnection("access241");
 			connAccess242 = conAcc241Manger.getConnection("access242");
 						
 			connAccess241.setAutoCommit(false);
 			connAccess242.setAutoCommit(false);
 			
 			PoolManager conOra241Manger = PoolManager.getInstance();
 			connOracle = conOra241Manger.getConnection("usrbm");
 						
 			List<Trade> data241 = getTradeData(connOracle, connAccess241, "6");
 			List<Trade> data242 = getTradeData(connOracle, connAccess242, "4");
 			
 			for(int i=0 ; i<data241.size() ; i++){
 				boolean exist = isExist(connOracle, data241.get(i));
 				if(exist) continue;
 				else {
 					//삭제후 인서트
 				}
 			}
 			
 			//boolean check = isEqualData(connOracle241, connAccess241, "6");
 			
 			/*
 			
 			rsAcc241 = null;
 			
 			sb.setLength(0);
 			sb.append("\n SELECT SDATE, LOC AS M_NO, ILNO, RTN, USE_CD, PAY_GBN, QNTY, AMNT, FTIME ");
 			sb.append("\n   FROM MKT_DESC_TB	");
 			sb.append("\n  WHERE SDATE > '20150101'	");
 			sb.append("\n    AND SDATE > NOW() -62	");
 			sb.append("\n    AND M_NO = '6'	");

 			Log.debug("DB", this, sb.toString());
 			rsAcc241 = prest.executeQuery();
 			
 			
 			//3. oracle 241mdb 파일을 조회한다.
 			PoolManager conOra241Manger = PoolManager.getInstance();
 			connOracle241 = conOra241Manger.getConnection("usrbm");
 						
 			connOracle241.setAutoCommit(false);
 			
 			rs241 = null;
 			
 			sb.setLength(0);
 			sb.append("\n SELECT SDATE, M_NO, ILNO, RTN, USE_CD, PAY_GBN, QNTY, AMNT, AMNT ");
 			sb.append("\n   FROM TBRC_T_TRADE_MSR	");
 			sb.append("\n  WHERE SDATE > '20150101'	");
 			sb.append("\n    AND SDATE > SYSDATE -62	");
 			sb.append("\n    AND M_NO = '6'	");
 			
 			Log.debug("DB", this, sb.toString());
 			rs241 = prest.executeQuery();
 			
 			*/
 						
 			
 			/*
 			//4. 242mdb 파일을 조회한다.
 			PoolManager conAcc242Manger = PoolManager.getInstance();
 			connAccess242 = conAcc242Manger.getConnection("access242");
 						
 			connAccess242.setAutoCommit(false);
 			
 			rsAcc242 = null;
 			
 			sb.setLength(0);
 			sb.append("\n SELECT SDATE, LOC AS M_NO, ILNO, RTN, USE_CD, PAY_GBN, QNTY, AMNT, FTIME ");
 			sb.append("\n   FROM MKT_DESC_TB	");
 			sb.append("\n  WHERE SDATE > '20150101'	");
 			sb.append("\n    AND SDATE > NOW() -62	");
 			sb.append("\n    AND M_NO = '4'	");

 			Log.debug("DB", this, sb.toString());
 			rsAcc242 = prest.executeQuery();
 			
 			//5. oracle 242mdb 파일을 조회한다.
 			PoolManager conOra242Manger = PoolManager.getInstance();
 			connOracle242 = conOra242Manger.getConnection("usrbm");
 						
 			connOracle242.setAutoCommit(false);
 			
 			rs242 = null;
 			
 			sb.setLength(0);
 			sb.append("\n SELECT SDATE, M_NO, ILNO, RTN, USE_CD, PAY_GBN, QNTY, AMNT, AMNT ");
 			sb.append("\n   FROM TBRC_T_TRADE_MSR	");
 			sb.append("\n  WHERE SDATE > '20150101'	");
 			sb.append("\n    AND SDATE > SYSDATE -62	");
 			sb.append("\n    AND M_NO = '4'	");
 			
 			Log.debug("DB", this, sb.toString());
 			rs242 = prest.executeQuery();
 			*/
 			
 			
 			/*
 			//8. 비교 후 ORACLE에 INSERT할 당일 241mdb 파일을 조회한다.
 			PoolManager conAcc241TodayManger = PoolManager.getInstance();
 			connAccessToday241 = conAcc241TodayManger.getConnection("access241");
 						
 			connAccessToday241.setAutoCommit(false);
 			
 			rsAccToday241 = null;
 			
 			sb.setLength(0);
 			sb.append("\n SELECT SDATE, LOC AS M_NO, ILNO, RTN, USE_CD, PAY_GBN, QNTY, AMNT, FTIME ");
 			sb.append("\n   FROM MKT_DESC_TB	");
 			sb.append("\n  WHERE SDATE = NOW() ");
 			sb.append("\n    AND M_NO = '6'	");

 			Log.debug("DB", this, sb.toString());
 			rsAccToday241 = prest.executeQuery();
 			
 			//9. 비교 후 ORACLE에 INSERT할 당일 242mdb 파일을 조회한다.
 			PoolManager conAcc242TodayManger = PoolManager.getInstance();
 			connAccessToday242 = conAcc242TodayManger.getConnection("access241");
 						
 			connAccessToday242.setAutoCommit(false);
 			
 			rsAccToday242 = null;
 			
 			sb.setLength(0);
 			sb.append("\n SELECT SDATE, LOC AS M_NO, ILNO, RTN, USE_CD, PAY_GBN, QNTY, AMNT, FTIME ");
 			sb.append("\n   FROM MKT_DESC_TB	");
 			sb.append("\n  WHERE SDATE = NOW() ");
 			sb.append("\n    AND M_NO = '4'	");

 			Log.debug("DB", this, sb.toString()); 
 			rsAccToday242 = prest.executeQuery();

 			//10. ORACLE TBRC_T_TRADE_MSR 테이블에 241.mdb 데이터 batchInsert241mdb 실행
 			ArrayList <Trade> arrTrade241 = new ArrayList<Trade>();
 			Trade tdItem241;
 			long count_src241 = 0;
 			
 			while (rsAccToday241.next()) {
 				 count_src241 ++;
 				 tdItem241 = new Trade(
 							 rsAccToday241.getString("SDATE"),
 							 rsAccToday241.getString("M_NO"),
 							 rsAccToday241.getString("ILNO"),
 							 rsAccToday241.getString("RTN"),
 							 rsAccToday241.getString("USE_CD"),
 							 rsAccToday241.getString("PAY_GBN"),
 							 rsAccToday241.getString("QNTY"),
 							 rsAccToday241.getString("AMNT"),
 							 rsAccToday241.getString("FTIME"));
 				 arrTrade241.add(tdItem241);
 							 
 				 if (count_src241 % 10000 == 0) {	// 10,000건 단위로 DB서버로 전송
 					 batchInsert241mdb(connOracle241, arrTrade241);
 		             arrTrade241.clear();
 				 }
 			 }

 			 if (arrTrade241.size() > 0) {	
 				 batchInsert241mdb(connOracle241, arrTrade241);
 			 }
 			
 			connOracle241.commit();
             
 			long endTime241 = System.currentTimeMillis();    		 
 			Log.debug("DB", this, "처리건수:"+count_src241+"건, 소요시간:"+(endTime241 - startTime)/1000.000f+"초");
             Log.debug("DB", this, "자료 전송 종료------------------------------------>");
             */
 			
 			
 			/*
 			//11. ORACLE TBRC_T_TRADE_MSR 테이블에 242.mdb 데이터 batchInsert242mdb 실행
  			ArrayList <Trade> arrTrade242 = new ArrayList<Trade>();
  			Trade tdItem242;
  			long count_src242 = 0;
  			
  			while (rsAccToday242.next()) {
  				 count_src242 ++;
  				 tdItem242 = new Trade(
 	 						rsAccToday242.getString("SDATE"),
 	 						rsAccToday242.getString("M_NO"),
 	 						rsAccToday242.getString("ILNO"),
 	 						rsAccToday242.getString("RTN"),
 	 						rsAccToday242.getString("USE_CD"),
 	 						rsAccToday242.getString("PAY_GBN"),
 	 						rsAccToday242.getString("QNTY"),
 	 						rsAccToday242.getString("AMNT"),
 	 						rsAccToday242.getString("FTIME"));
  				 arrTrade242.add(tdItem242);
  							 
  				 if (count_src242 % 10000 == 0) {	// 10,000건 단위로 DB서버로 전송
  		             batchInsert242mdb(connOracle242, arrTrade242);
  		             arrTrade242.clear();
  				 }
  			 }

  			 if (arrTrade242.size() > 0) {	
  				batchInsert242mdb(connOracle242, arrTrade242);
  			 }
  			
              connOracle242.commit();
               
              
 			 long endTime242 = System.currentTimeMillis();    		 
 			 Log.debug("DB", this, "처리건수:"+count_src242+"건, 소요시간:"+(endTime242 - startTime)/1000.000f+"초");
              Log.debug("DB", this, "자료 전송 종료------------------------------------>");
              
              
              */
 			 
 		} catch (ClassNotFoundException e) {
 			Log.error("ERROR", this, "JDBC 드라이버 로드가 실패하였습니다.");
 		} catch (Exception e) {
 			Log.error("ERROR", this, "execute : " + "Exception  [" + e.toString()+ "] ");
 		} finally {
 			if (rsAcc241 != null) {
 				try { rsAcc241.close(); } catch (SQLException e) { }
 				rsAcc241 = null;
 			}
 			if (rsAcc242 != null) {
 				try { rsAcc242.close(); } catch (SQLException e) { }
 				rsAcc242 = null;
 			}
 			if (rs241 != null) {
 				try { rs241.close(); } catch (SQLException e) { }
 				rs241 = null;
 			}
 			if (rs242 != null) {
 				try { rs242.close(); } catch (SQLException e) { }
 				rs242 = null;
 			}
 			if (rsAccToday241 != null) {
 				try { rsAccToday241.close(); } catch (SQLException e) { }
 				rsAccToday241 = null;
 			}
 			if (rsAccToday242 != null) {
 				try { rsAccToday242.close(); } catch (SQLException e) { }
 				rsAccToday242 = null;
 			}			
 			if (stmt != null) {
 				try { stmt.close(); } catch (SQLException e) {  }
 				stmt = null;
 			}
 			if (connOracle != null) {
 				try { connOracle.close(); } catch (SQLException e) { }
 				connOracle = null;
 			}
 			if (connAccess241 != null) {
 				try { connAccess241.close(); } catch (SQLException e) { }
 				connAccess241 = null;
 			}
 			if (connAccess242 != null) {
 				try { connAccess242.close(); } catch (SQLException e) { }
 				connAccess242 = null;
 			}
 		} 
 	}


 	private int batchInsert241mdb(Connection conn, ArrayList <Trade> arrTrade241) {
 		try {
 			 // 4-2. Insert 문장  생성
     		sb.setLength(0);
 			sb.append("\n  ");
 			sb.append("\n INSERT INTO TBRC_T_TRADE_MSR ( ");
 			sb.append("\n  SDATE, M_NO, ILNO, RTN, USE_CD ");
 			sb.append("\n  PAY_GBN, QNTY, AMNT, FTIME) ");
 			sb.append("\n   VALUES(?,?,?,?,?,?,?,?,?) ");
 			
 			//Log.debug("DB", this, sb.toString());
 			stmt = conn.prepareStatement(sb.toString());
 			
 			// 4-3. 1,000건씩 DB에 저장
 			int ins_cnt = 0;
 			for( Trade tr : arrTrade241) {
 				ins_cnt ++;
 				stmt.setString(1,  tr.getSDATE());
 				stmt.setString(2,  tr.getM_NO());
 				stmt.setString(3,  tr.getILNO());
 				stmt.setString(4,  tr.getRTN());
 				stmt.setString(5,  tr.getUSE_CD());
 				stmt.setString(6,  tr.getPAY_GBN());
 				stmt.setString(7,  tr.getQNTY());
 				stmt.setString(8,  tr.getAMNT());
 				stmt.setString(9,  tr.getFTIME());
 			    				
 				stmt.addBatch();
 				if (ins_cnt % 1000 == 0) {	// 1,000건 단위로 DB Insert
 					stmt.executeBatch();
 			    }
 			}
 	        stmt.executeBatch();			
 			stmt.close();
 			
 		} catch (SQLException e) {
 			Log.error("ERROR", this, "batchInsertTrade : " + "SQLException  [" + e.toString()+ "] ");
 			Log.error("ERROR", this, "SQL String = " + sb.toString());
 		}
 		return 0;
 	}
 	
 	private int batchInsert242mdb(Connection conn, ArrayList <Trade> arrTrade242) {
 		try {
 			 // 4-2. Insert 문장  생성
     		sb.setLength(0);
 			sb.append("\n  ");
 			sb.append("\n INSERT INTO TBRC_T_TRADE_MSR ( ");
 			sb.append("\n  SDATE, M_NO, ILNO, RTN, USE_CD ");
 			sb.append("\n  PAY_GBN, QNTY, AMNT, FTIME) ");
 			sb.append("\n   VALUES(?,?,?,?,?,?,?,?,?) ");
 			
 			//Log.debug("DB", this, sb.toString());
 			stmt = conn.prepareStatement(sb.toString());
 			
 			// 4-3. 1,000건씩 DB에 저장
 			int ins_cnt = 0;
 			for( Trade tr : arrTrade242) {
 				ins_cnt ++;
 				stmt.setString(1,  tr.getSDATE());
 				stmt.setString(2,  tr.getM_NO());
 				stmt.setString(3,  tr.getILNO());
 				stmt.setString(4,  tr.getRTN());
 				stmt.setString(5,  tr.getUSE_CD());
 				stmt.setString(6,  tr.getPAY_GBN());
 				stmt.setString(7,  tr.getQNTY());
 				stmt.setString(8,  tr.getAMNT());
 				stmt.setString(9,  tr.getFTIME());
 			    				
 				stmt.addBatch();
 				if (ins_cnt % 1000 == 0) {	// 1,000건 단위로 DB Insert
 					stmt.executeBatch();
 			    }
 			}
 	        stmt.executeBatch();			
 			stmt.close();
 			
 		} catch (SQLException e) {
 			Log.error("ERROR", this, "batchInsertTrade : " + "SQLException  [" + e.toString()+ "] ");
 			Log.error("ERROR", this, "SQL String = " + sb.toString());
 		}
 		return 0;
 	}
    /////////////////////////
	
	///////////////////////////////////////
	// 수동배치현황 1차 입력
	/*private int InsertCronEvent(Connection conn, String COMM_NO, String DIV_NO, long MaxSeq, String TRAND_STATUS, long count_src, String TYPE_NO)
	{	
		PosParameter param = new PosParameter();
	    int i = 0;
	    param.setValueParamter(i++, COMM_NO);
	    param.setValueParamter(i++, DIV_NO);
	    param.setValueParamter(i++, MaxSeq);
	    param.setValueParamter(i++, TRAND_STATUS);
	    param.setValueParamter(i++, count_src);
	    param.setValueParamter(i++, TYPE_NO);

	    int insertcount = this.getDao("rbmdao").update("rem4090_i01", param);
	    return insertcount;
	}*/
	///////////////////////////////////////

    /**
     * <p> call cron prodecure </p>
     * @param   EventDt	 String		이벤트 날짜
     * @return  successCnt int		성공 레코드 개수
     * @return  none
     * @throws SQLException 
     * @throws  none
     */
     /*protected void callCronProdecure(PosContext ctx) 
     {
     	Connection conn = null; 
     	CallableStatement proc =  null;

     	try {
         	conn = this.getDao("rbmdao").getDBConnection();
         	proc = conn.prepareCall("{call SP_IMPORT_TMONEY_TRADE}");
         	proc.setQueryTimeout(120);
         	proc.execute();
         	//proc.close();
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
     }*/
     
     
     /**
      * <p> 수동배치현황 수정 </p>
      * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount int		update ���ڵ� ����
      * @throws  none
      */
     /*protected int updateCronEvent(PosRecord record)
     {
     	PosParameter param = new PosParameter();
         int i = 0;
         param.setValueParamter(i++, record.getAttribute("TRAN_DT"));		//�������
         param.setValueParamter(i++, record.getAttribute("TERM"));			//����ֱ�
         param.setValueParamter(i++, record.getAttribute("START_TM"));		//���۽ð�
         param.setValueParamter(i++, record.getAttribute("END_TM"));			//����ð�
         param.setValueParamter(i++, record.getAttribute("RMK"));			//���
         param.setValueParamter(i++, SESSION_USER_ID);						//������

         int dmlcount = this.getDao("tmoneydao").update("rem4040_m01", param);
         return dmlcount;
     }*/
     
     /**
      * <p> 수동배치현황 삭제 </p>
      * @param   EventDt	 String		이벤트 날짜
      * @return  dmlcount int		삭제 레코드 개수
      * @throws  none
      */
     /*protected int deleteCronEvent(PosRecord record)
     {
     	PosParameter param = new PosParameter();
         int i = 0;
         i = 0;
         param.setWhereClauseParameter(i++, record.getAttribute("TRAN_DT" ));

         int dmlcount = this.getDao("tmoneydao").update("rem4040_d01", param);
         return dmlcount;
     }*/

}
