/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02070030.activity.SaveTrngAsk.java
 * 파일설명		: 선수수시훈련등록
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02070030.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 착순점/사고점를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SaveTrngAsk extends SnisActivity
{    
    public SaveTrngAsk()
    {
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
    	
    	/*    	
        PosDataset ds;
        int nSize        = 0;

        ds    = (PosDataset)ctx.get("dsOutTrngAsk");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            logger.logInfo(record);
        }

        ds    = (PosDataset)ctx.get("dsOutTrngAskRacer");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            logger.logInfo(record);
        }
        */
    	
        saveState(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 

    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        // 선수수시훈련 삭제 요청자료 삭제
        ds    = (PosDataset) ctx.get("dsOutTrngAsk");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ )
        {
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
            	int nCount = deleteTrngAsk(record, ctx);
            	if ( nCount < -1 ) return;
            	nDeleteCount = nDeleteCount + nCount;
            }
        }

        String sStrDt = "";
        String sEndDt = "";
        String sAskDd = "";
        
        // 선수수시훈련요청 등록
        String sTrngAskSeq = "";
        for ( int i = 0; i < nSize; i++ )
        {
            PosRecord record = ds.getRecord(i);
            
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.NORMAL)
            {
            	if ( (nTempCnt = updateTrngAsk(record)) == 0 ) {
            		sTrngAskSeq = getNextTrngAskSeq(getTrngAskSeq());
                	nTempCnt = insertTrngAsk(record, sTrngAskSeq);
                } else {
                	sTrngAskSeq = record.getString("TRNG_ASK_SEQ");
                }
            	nSaveCount = nSaveCount + nTempCnt;
            }
            
            sStrDt = (String) record.getAttribute("TRNG_ASK_STR_DT");
            sEndDt = (String) record.getAttribute("TRNG_ASK_END_DT");
            try {
            	sAskDd = (Double) record.getAttribute("TRNG_ASK_DD_NUM") + "";
            } catch ( Exception e ) {
            	sAskDd = (String) record.getAttribute("TRNG_ASK_DD_NUM");
            }
        }
        
//		Calendar calStr = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
 
		int nYearSta  = Integer.parseInt( sStrDt.substring(0,4) );
		int nMonthSta = Integer.parseInt( sStrDt.substring(4,6) ); 
		int nDateSta  = Integer.parseInt( sStrDt.substring(6,8) );

		int nYearEnd  = Integer.parseInt( sEndDt.substring(0,4) );
		int nMonthEnd = Integer.parseInt( sEndDt.substring(4,6) ); 
		int nDateEnd  = Integer.parseInt( sEndDt.substring(6,8) );
		
//		calStr.set(nYearSta, nMonthSta - 1, nDateSta, 0,0,1);
		calEnd.set(nYearEnd, nMonthEnd - 1, nDateEnd, 0,0,1);

        ds    = (PosDataset) ctx.get("dsOutTrngAskRacer");
        nSize = ds.getRecordCount();
        // 삭제
        for ( int i = 0; i < nSize; i++ )
        {
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
            	nDeleteCount = nDeleteCount + deleteTrngAskRacer(record);
            }
        }
        
        SimpleDateFormat sdFormat = new SimpleDateFormat();
        sdFormat.applyPattern("yyyyMMdd");

        for ( int i = 0; i < nSize; i++ ) 
        {
	        PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
                if ( (nTempCnt = updateTrngAskRacer(record, sTrngAskSeq)) == 0 ) {
                	nTempCnt = insertTrngAskRacer(record, sTrngAskSeq);
                }
                updateTrngExptRacer(record);
                
        		Calendar calStr = Calendar.getInstance();
        		calStr.set(nYearSta, nMonthSta - 1, nDateSta, 0,0,1);

        		deleteTrngAskRacerResult(record);
                for ( int j = 0; j < Double.parseDouble(sAskDd); j++ ) {
                	
                	String sTempDt = sdFormat.format(calStr.getTime());
                	record.setAttribute("TRNG_DD", sTempDt);
                    if ( (nTempCnt = updateTrngAskRacerResult(record, sTrngAskSeq)) == 0 ) {
                    	nTempCnt = insertTrngAskRacerResult(record, sTrngAskSeq);
                    }

                    calStr.add(Calendar.DATE, 1);
                }
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 수시훈련 일련번호 조회 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected String getTrngAskSeq()
    {
        PosParameter param = new PosParameter();
        PosRowSet rowset = this.getDao("boadao").find("tbeb_trng_ask_fb001", param);

        String sTrngAskSeq = null;
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            sTrngAskSeq = (String) row.getAttribute("TRNG_ASK_SEQ".trim());
        }
            
        return sTrngAskSeq;
    }

    /**
     * <p> 수시훈련 일련번호의 다음 일련번호 조회 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected String getNextTrngAskSeq(String sTrngAskSeq)
    {
        String sNextTrngAskSeq = sTrngAskSeq.substring(0, 3);
        int nTemp = Integer.parseInt(sTrngAskSeq.substring(4)) + 1;
        
        return sNextTrngAskSeq = sNextTrngAskSeq + Util.getFormat("0000", Integer.toString(nTemp));
    }

    /**
     * <p> 훈련요청선수상태 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateTrngExptRacer(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, "004");
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("TRNG_EXPT_SEQ"));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_expt_racer_ub002", param);
        
        return dmlcount;
    }

    /**
     * <p> 수시훈련 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateTrngAsk(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_PLC_CD      ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STR_DT  ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_END_DT  ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_DD_NUM  ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STAT_CD ".trim()));
        param.setValueParamter(i++, record.getAttribute("RMK              ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ    ".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_ask_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> 수시훈련 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertTrngAsk(PosRecord record, String sTrngAskSeq) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sTrngAskSeq                                    );
        param.setValueParamter(i++, record.getAttribute("TRNG_PLC_CD      ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STR_DT  ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_END_DT  ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_DD_NUM  ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STAT_CD ".trim()));
        param.setValueParamter(i++, record.getAttribute("RMK              ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_trng_ask_ib001", param);
        return dmlcount;
    }

    /**
     * <p> 수시훈련선수 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateTrngAskRacer(PosRecord record, String sTrngAskSeq)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_EXPT_SEQ    ".trim()));
        param.setValueParamter(i++, record.getAttribute("RMK              ".trim()));
        param.setValueParamter(i++, record.getAttribute("MENTO_GRP_NO     ".trim()));
        param.setValueParamter(i++, record.getAttribute("MENTO_YN         ".trim()));
        param.setValueParamter(i++, record.getAttribute("COMMUTE_YN       ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, sTrngAskSeq);
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO         ".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_ask_racer_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> 수시훈련선수 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertTrngAskRacer(PosRecord record, String sTrngAskSeq) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sTrngAskSeq                                    );
        param.setValueParamter(i++, record.getAttribute("RACER_NO         ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_EXPT_SEQ    ".trim()));
        param.setValueParamter(i++, record.getAttribute("RMK              ".trim()));
        param.setValueParamter(i++, record.getAttribute("MENTO_GRP_NO     ".trim()));
        param.setValueParamter(i++, record.getAttribute("MENTO_YN         ".trim()));
        param.setValueParamter(i++, record.getAttribute("COMMUTE_YN       ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_trng_ask_racer_ib001", param);
        return dmlcount;
    }
    

    /**
     * <p> 수시훈련선수결과 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateTrngAskRacerResult(PosRecord record, String sTrngAskSeq)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_STAT_CD     ".trim()));
        param.setValueParamter(i++, record.getAttribute("RMK              ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, sTrngAskSeq);
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO         ".trim()));
        param.setWhereClauseParameter(i++, record.getAttribute("TRNG_DD          ".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_ask_racer_rslt_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> 수시훈련선수결과 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertTrngAskRacerResult(PosRecord record, String sTrngAskSeq) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        record.setAttribute("TRNG_STAT_CD", "001");
        param.setValueParamter(i++, sTrngAskSeq                                    );
        param.setValueParamter(i++, record.getAttribute("RACER_NO         ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_DD          ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_STAT_CD     ".trim()));
        param.setValueParamter(i++, record.getAttribute("RMK              ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_trng_ask_racer_rslt_ib001", param);
        return dmlcount;
    }
    
    /**
     * <p> 훈련요청 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteTrngAsk(PosRecord record, PosContext ctx) 
    {
        PosParameter paramTrngAsk = new PosParameter();
        int i = 0;
        paramTrngAsk.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ    ".trim()));
    	
        PosRowSet rowset = this.getDao("boadao").find("tbeb_trng_ask_fb003", paramTrngAsk);
         
        if ( rowset.hasNext() ) {
        	PosRow row = rowset.next();
        	
        	if ( !row.getAttribute("TRNG_ASK_STAT_CD").equals("000") ) {
        		Util.setSvcMsg(ctx, record.getAttribute("TRNG_ASK_SEQ") + "은 진행중인 훈련요청입니다. 삭제 하실 수 없습니다.");
        		return -1;
        	}
        }
        
        deleteTrngAskRacerResult(record);
    	deleteTrngAskRacer(record);
        PosParameter param = new PosParameter();
        i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_SEQ    ".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_ask_db001", param);
        return dmlcount;
    }
    
    /**
     * <p> 훈련요청선수 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteTrngAskRacer(PosRecord record) 
    {
    	deleteTrngAskRacerResult(record);
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_SEQ    ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACER_NO        ".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_ask_racer_db001", param);
        return dmlcount;
    }
    
    /**
     * <p> 훈련요청선수결과 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteTrngAskRacerResult(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_SEQ    ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACER_NO        ".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_ask_racer_rslt_db001", param);
        return dmlcount;
    }
}
