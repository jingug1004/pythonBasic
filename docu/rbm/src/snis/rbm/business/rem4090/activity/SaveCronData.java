/*
 * ================================================================================
 * 시스템 : 관리 
 * 소스파일 이름 : snis.rbm.business.rem4090.activity.SaveCronData.java 
 * 파일설명 : 수동배치 현황 관리 
 * 작성자 : gavi
 * 버전 : 1.0.0 
 * 생성일자 : 2017.02.02
 * 최종수정일자 :
 * 최종수정자 : 
 * 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.business.rem4090.activity;

//import com.jitk.commapi.scheduler.Trade;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.kspo.secure.KSPOSecure;

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
	
	String COMM_NO = "";
	String DIV_NO = "";
	StringBuffer sb = new StringBuffer();
	ResultSet rs = null;
	PreparedStatement stmt = null;
	PreparedStatement prest = null;
	Connection connOracle = null;
	Connection connMySQL = null;
	
	String MAX_TRANSITION_SEQ = "";
	String MAX_TRADE_DATE = "";
	
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

    	saveState(ctx);

        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    public void saveState(PosContext ctx)
    {
        logger.debug("DB", this, "자료 전송 시작------------------------------------>");
        System.out.println("자료 전송 시작------------------------------------>");
		try {
			 long startTime = System.currentTimeMillis();
			 System.out.println("TEST 2 startTime " + startTime);
			 //1. 해당 지점의 코드를 환경변수에서 가져온다.
			 COMM_NO = cf.getConfiguration("period.properties").getString("comm.no").trim();
			 if (COMM_NO.length() != 2) {
				 COMM_NO = "00";
			 }
			 System.out.println("TEST 1_1 COMM_NO: " + COMM_NO);
			 DIV_NO = cf.getConfiguration("period.properties").getString("div.no").trim();
			 if (DIV_NO.trim().length() != 1) {
				 DIV_NO = "1";
				 Log.debug("DB", this, "Default setting --> DIV_NO:"+DIV_NO);
			 }
			 System.out.println("TEST 1_2 DIV_NO: " + DIV_NO);
			 PoolManager conOraManger = PoolManager.getInstance();
			 connOracle = conOraManger.getConnection("zenius");
			 System.out.println("TEST 4");

			 // 2. 해당 지점에서 전송한 마지막 연번을 조회한다.
			 long MaxSeq = getLastTradeSeq(connOracle, COMM_NO, DIV_NO);
			 System.out.println("TEST 5 MaxSeq:" + MaxSeq);
						
			 PoolManager conMySqlManger = PoolManager.getInstance();
			 connMySQL = conMySqlManger.getConnection("tmoney");
			 connOracle.setAutoCommit(false);
			 System.out.println("TEST 6");
			 sb.setLength(0);			
			 sb.append("\n SELECT TRANSITION_SEQ    ");
			 sb.append("\n       ,MACHINE_ID        ");
			 sb.append("\n       ,date_format(TRADE_DATE, '%Y%m%d%H%i%S') TRADE_DATE      ");
			 sb.append("\n       ,TERM_ID           ");
			 sb.append("\n       ,CARD_ID           ");
			 sb.append("\n       ,CARD_SERIAL       ");
			 sb.append("\n       ,REQUEST_FEE       ");
			 sb.append("\n       ,CARD_USER_CODE    ");
			 sb.append("\n       ,CARD_USER_TYPE    ");
			 sb.append("\n FROM kspo.t_trade        ");
			 sb.append("\n WHERE TRANSITION_SEQ > ? ");
             sb.append("\n ORDER BY TRANSITION_SEQ  ");

			 logger.debug("DB", this, sb.toString());
			 prest = connMySQL.prepareStatement(sb.toString());
			 prest.setLong(1,  MaxSeq);	
			 rs = prest.executeQuery();
			 System.out.println("TEST 7");
			 ArrayList <Trade> arrTrade = new ArrayList<Trade>();
			 Trade tdItem;
			 Trade trMax = new Trade();	
			 long count_src = 0;
			
			 while (rs.next()) {
				 count_src ++;
				 tdItem = new Trade(
							 rs.getString("TRANSITION_SEQ"),
							 rs.getString("MACHINE_ID"),
							 rs.getString("TRADE_DATE"),
							 rs.getString("TERM_ID"),
							 KSPOSecure.EncryptDB(rs.getString("CARD_ID")),
							 rs.getString("CARD_SERIAL"),
							 rs.getString("REQUEST_FEE"),
							 rs.getString("CARD_USER_CODE"),
							 rs.getString("CARD_USER_TYPE"),
							 COMM_NO,
							 DIV_NO);
				 arrTrade.add(tdItem);
							 
				 if (count_src % 10000 == 0) {	// 10,000건 단위로 DB서버로 전송
		             batchInsertTrade(connOracle, arrTrade);
		             //batchInsertStat(connOracle, arrTrade);
		             trMax = arrTrade.get(arrTrade.size() -1);
		             arrTrade.clear();
				 }
			 }

			 if (arrTrade.size() > 0) {	
				 batchInsertTrade(connOracle, arrTrade);
				 //batchInsertStat(connOracle, arrTrade);
				 trMax = arrTrade.get(arrTrade.size() -1);
			 }
							 
			 // 4. 연동DB서버에 자료 저장
             // 4-1. 트랙잭션 시작
             // 5. 최종 전송 일련번호 저장
             if (count_src > 0) {	// 전송한 자료가 있는 경우
				 UpdateMaxSeq(connOracle, trMax, count_src);
             }
             connOracle.commit();
             
			 long endTime = System.currentTimeMillis();    		 
			 logger.debug("DB", this, "처리건수:"+count_src+"건, 소요시간:"+(endTime - startTime)/1000.000f+"초");
			 System.out.println("TEST 8 처리건수:" + count_src + "건, 소요시간:"+(endTime - startTime)/1000.000f+"초");
			 
			 // 수동배치 현황 테이블에 1차 입력
			 int iCronCnt = InsertCronEvent(connOracle, COMM_NO, DIV_NO, MaxSeq, "Y", count_src, "1");
			 System.out.println("TEST 9 InsertCronEvent 처리건수:" + iCronCnt);
			 
			 // 만약 밤 11시 30분에 도는 프로시저 필요시 추가
			 
			 
			 logger.debug("DB", this, "자료 전송 종료------------------------------------>");
			 System.out.println("자료 전송 종료------------------------------------>");
			 						
		} catch (ClassNotFoundException e) {
			logger.error("ERROR", this, "JDBC 드라이버 로드가 실패하였습니다.");
		} catch (Exception e) {
			logger.error("ERROR", this, "execute : " + "Exception  [" + e.toString()+ "] ");
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) {  }
				stmt = null;
			}
			if (connOracle != null) {
				try { connOracle.close(); } catch (SQLException e) { }
				connOracle = null;
			}
			if (connMySQL != null) {
				try { connMySQL.close(); } catch (SQLException e) { }
				connMySQL = null;
			}
		}

    }
    
    // 마지막 Trade 순번 가져오기
    private long getLastTradeSeq(Connection conn, String CommNo, String DivNo)
	{
		long LastSeq = 0;
		 try {
			// 2-2. 최종 자료 조회			 
			sb.setLength(0);
			sb.append("\n SELECT MAX(TRANSITION_SEQ) AS LAST_TRANSITION_SEQ");
			sb.append("\n FROM TBIF_STATUS     ");
			sb.append("\n WHERE COMM_NO = ? ");
			sb.append("\n   AND DIV_NO  = ? ");
			 
			logger.debug("DB", this, sb.toString());
			stmt = conn.prepareStatement(sb.toString());
			stmt.setString(1,  CommNo);
			stmt.setString(2,  DivNo);
			logger.debug("DB", this, "COMM_NO["+CommNo+"]");
			logger.debug("DB", this, "DIV_NO["+DivNo+"]");
			rs = stmt.executeQuery();
			 
			if (rs.next()) {
				 LastSeq = rs.getLong("LAST_TRANSITION_SEQ");				 
			}
	        rs.close();
			rs = null;
		} catch (SQLException e) {
			logger.error("ERROR", this, "getLastTradeSeq : " + "SQLException  [" + e.toString()+ "] ");
			logger.error("ERROR", this, "SQL String = " + sb.toString());
			return -1;
		}
        return LastSeq;
	}
    
    // Trade Data 입력
	private int batchInsertTrade(Connection conn, ArrayList <Trade> arrTrade) {
		try {
			 // 4-2. Insert 문장  생성
    		sb.setLength(0);
			sb.append("\n  ");
			sb.append("\n INSERT INTO tbif_trade ( ");
			sb.append("\n  COMM_NO, DIV_NO, TRANSITION_SEQ, MACHINE_ID, TRADE_DATE, TERM_ID, ");
			sb.append("\n  CARD_ID, CARD_SERIAL, REQUEST_FEE, CARD_USER_CODE, CARD_USER_TYPE) ");
			sb.append("\n   VALUES(?,?,?,?,?,?,?,?,?,?,?) ");
			
			//Log.debug("DB", this, sb.toString());
			stmt = conn.prepareStatement(sb.toString());
			
			// 4-3. 1,000건씩 DB에 저장
			int ins_cnt = 0;
			for( Trade tr : arrTrade) {
				ins_cnt ++;
				stmt.setString(1,  tr.getCOMM_NO());
				stmt.setString(2,  tr.getDIV_NO());
				stmt.setLong(3,    Long.valueOf(tr.getTRANSITION_SEQ()));
				stmt.setString(4,  tr.getMACHINE_ID());
				stmt.setString(5,  tr.getTRADE_DATE());
				stmt.setString(6,  tr.getTERM_ID());
				stmt.setString(7,  tr.getCARD_ID());
				stmt.setString(8,  tr.getCARD_SERIAL());
				stmt.setString(9,  tr.getREQUEST_FEE());
				stmt.setString(10, tr.getCARD_USER_CODE());
				stmt.setString(11, tr.getCARD_USER_TYPE());
			     
				stmt.addBatch();
				if (ins_cnt % 1000 == 0) {	// 1,000건 단위로 DB Insert
					stmt.executeBatch();
			    }
			}
	        stmt.executeBatch();			
			stmt.close();
		} catch (SQLException e) {
			logger.error("ERROR", this, "batchInsertTrade : " + "SQLException  [" + e.toString()+ "] ");
			logger.error("ERROR", this, "SQL String = " + sb.toString());
		}
		return 0;
	}
	
	// Trade 최종 순번 업데이트
	private int UpdateMaxSeq(Connection conn, Trade trade, Long nTransCnt)
	{	
		int i = 1;
		try {
			sb.setLength(0);
			sb.append("\n  ");
			sb.append("\n INSERT INTO tbif_status ( ");
			sb.append("\n        COMM_NO, DIV_NO, TRANSITION_SEQ, TRADE_DATE, TRANS_CNT, UPDT_DTM) ");
			sb.append("\n  VALUES(?,?,?,?,?,SYSDATE)");
		
			logger.debug("DB", this, sb.toString());
			stmt = conn.prepareStatement(sb.toString());
			stmt.setString(1, trade.getCOMM_NO());
			stmt.setString(2, trade.getDIV_NO());
			stmt.setLong(3,   Long.valueOf(trade.getTRANSITION_SEQ()));
			stmt.setString(4, trade.getTRADE_DATE());
			stmt.setLong(5,   nTransCnt);
			
			stmt.executeUpdate();
     
			stmt.close();
		} catch (SQLException e) {
			logger.error("ERROR", this, "UpdateMaxSeq : " + "SQLException  [" + e.toString()+ "] ");
			logger.error("ERROR", this, "SQL String = " + sb.toString());
			return -1;
		}
		return 0;
	}
    /////////////////////////
	
	///////////////////////////////////////
	// 수동배치현황 1차 입력
	private int InsertCronEvent(Connection conn, String COMM_NO, String DIV_NO, long MaxSeq, String TRAND_STATUS, long count_src, String TYPE_NO)
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
	}
	///////////////////////////////////////

    /**
     * <p> call cron prodecure </p>
     * @param   EventDt	 String		이벤트 날짜
     * @return  successCnt int		성공 레코드 개수
     * @return  none
     * @throws SQLException 
     * @throws  none
     */
     protected void callCronProdecure(PosContext ctx) 
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
     }
     
     
     /**
      * <p> 수동배치현황 수정 </p>
      * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateCronEvent(PosRecord record)
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
     }
     
     /**
      * <p> 수동배치현황 삭제 </p>
      * @param   EventDt	 String		이벤트 날짜
      * @return  dmlcount int		삭제 레코드 개수
      * @throws  none
      */
     protected int deleteCronEvent(PosRecord record)
     {
     	PosParameter param = new PosParameter();
         int i = 0;
         i = 0;
         param.setWhereClauseParameter(i++, record.getAttribute("TRAN_DT" ));

         int dmlcount = this.getDao("tmoneydao").update("rem4040_d01", param);
         return dmlcount;
     }

}
