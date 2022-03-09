/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02010020.activity.SaveConfront.java
 * 파일설명		: 대진방식등록
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02010020.activity;

import java.math.BigDecimal;
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
public class SaveConfront extends SnisActivity
{    
	
    public SaveConfront()
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
    	
        PosDataset ds;
        int        nSize        = 0;
        String     sDsName = "";
        
        sDsName = "dsOutCfrntMeth";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
	        
        sDsName = "dsOutRacerArrangeStnd";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for (int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }

        sDsName = "dsOutCfrntDay";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for (int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
	        
        sDsName = "dsOutCfrntRace";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for (int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
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
    protected void saveState(PosContext ctx) 
    {
    	int    nSaveCount   = 0; 
    	int    nDeleteCount = 0; 
    	String sMessage     = "";
        String sDsName      = "";

    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        // 대진방식 저장
        sDsName = "dsOutCfrntMethDelete";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
        	// 삭제
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	if ( getRaceTmsCnt(record) == 0 ) {
	            		nDeleteCount = nDeleteCount + deleteCfrntMeth(record);
	            	} else {
	            		if ( sMessage.equals("") ) {
		            		sMessage = Util.trim(record.getAttribute("CFRNT_CD     ".trim()));
	            		} else {
		            		sMessage = sMessage + ", " + Util.trim(record.getAttribute("CFRNT_CD     ".trim()));
	            		}
	            	}
	            }
	        }

        	if ( sMessage.length() > 0 ) {
            	Util.setSvcMsg(ctx, sMessage + "은 사용중인 대진코드입니다. \n 수정 및 삭제가 불가합니다.");
            	return;
        	}
        }
    	// 저장
        sDsName = "dsOutCfrntMeth";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
  	              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT
	              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.NORMAL)
	            {
	            	if ( getRaceTmsCnt(record) == 0 ) {
		            	if ( (nTempCnt = updateCfrntMeth(record)) == 0 ) {
		                	nTempCnt = insertCfrntMeth(record);
		                }
		            	nSaveCount = nSaveCount + nTempCnt;
	            	} else {
	            		if ( sMessage.equals("") ) {
		            		sMessage = Util.trim(record.getAttribute("CFRNT_CD     ".trim()));
	            		} else {
		            		sMessage = sMessage + ", " + Util.trim(record.getAttribute("CFRNT_CD     ".trim()));
	            		}
	            	}
	            }
	        }

        	if ( sMessage.length() > 0 ) {
            	Util.setSvcMsg(ctx, sMessage + "은 사용중인 대진코드입니다. \n 수정 및 삭제가 불가합니다.");
            	return;
        	}
        }
        
        // 선수주선기준 저장
        sDsName = "dsOutRacerArrangeStnd";
        if ( ctx.get(sDsName) != null ) {
        	
        	String sCfrntCd = (String) ctx.get("CFRNT_CD");
        	int    nSeq     = getRacerArrangeStndSeq(sCfrntCd);

	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
            
	        // 삭제
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	nDeleteCount = nDeleteCount + deleteRacerArrangeStnd(record);
	            }
	        }

            // 저장
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
	            {
	                if ( (nTempCnt = updateRacerArrangeStnd(record)) == 0 ) {
	                	nTempCnt = insertRacerArrangeStnd(record, nSeq++);
	                }
	            	nSaveCount = nSaveCount + nTempCnt;
	            }
	        }
        }

	        
        // 대진일 저장
        sDsName = "dsOutCfrntDay";
        if ( ctx.get(sDsName) != null ) {
        	ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
            
	        // 삭제
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	nDeleteCount = nDeleteCount + deleteCfrntDay(record);
	            }
	        }
	
            // 저장
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
	            {
	                if ( (nTempCnt = updateCfrntDay(record)) == 0 ) {
	                	nTempCnt = insertCfrntDay(record);
	                }
	
	            	nSaveCount = nSaveCount + nTempCnt;
	            }
	        }
        }

	        
        // 대진경주 저장
        sDsName = "dsOutCfrntDay";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get("dsOutCfrntRace");
	        nSize = ds.getRecordCount();
            
	        // 삭제
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	nDeleteCount = nDeleteCount + deleteCfrntRace(record);
	            }
	        }
	
            // 저장
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
	            {
	                if ( (nTempCnt = updateCfrntRace(record)) == 0 ) {
	                	nTempCnt = insertCfrntRace(record);
	                }
	
	            	nSaveCount = nSaveCount + nTempCnt;
	            }
	        }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 대진방식이 사용되었나 조회(대진방식이 사용되었다면 수정/삭제불가)) </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int getRaceTmsCnt(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CFRNT_CD     ".trim())));
        PosRowSet rowset = this.getDao("boadao").find("tbeb_race_tms_fb002", param);

        BigDecimal nCnt = null;
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            nCnt = (BigDecimal) row.getAttribute("CNT".trim());
            logger.logInfo("tbeb_race_tms_fb002.CNT : " + nCnt);
        }
            
        return nCnt.intValue();
    }

    /**
     * <p> 대진방식 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateCfrntMeth(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR    ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_KINDS_CD".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);

        i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CFRNT_CD     ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_cfrnt_meth_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> 대진방식 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertCfrntMeth(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("CFRNT_CD     ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR    ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_KINDS_CD".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);

        int dmlcount = this.getDao("boadao").update("tbeb_cfrnt_meth_ib001", param);
        return dmlcount;
    }

    /**
     * <p> 대진방식 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int deleteCfrntMeth(PosRecord record) 
    {
    	deleteCfrntRace(record);
    	deleteCfrntDay(record);
    	deleteRacerArrangeStnd(record);
    	
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CFRNT_CD    ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_cfrnt_meth_db001", param);
        return dmlcount;
    }
    /**
     * <p> 선수주선기준 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateRacerArrangeStnd(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_GRD_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_CNT   ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SELT_STND_CD".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);

        i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CFRNT_CD     ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SEQ          ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_racer_arrange_stnd_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> 선수주선기준 Seq 구하기 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int getRacerArrangeStndSeq(String sCfrntCd) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sCfrntCd);
        PosRowSet rowset = this.getDao("boadao").find("tbeb_racer_arrange_stnd_fb000", param);

        BigDecimal nSeq = null;
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            nSeq = (BigDecimal) row.getAttribute("SEQ".trim());
            logger.logInfo("tbeb_racer_arrange_stnd_fb000.SEQ : " + nSeq);
        }
            
        return nSeq.intValue();
    }

    /**
     * <p> 선수주선기준 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertRacerArrangeStnd(PosRecord record, int nSeq) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("CFRNT_CD    ".trim())));
        param.setValueParamter(i++, Integer.toString(nSeq));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_GRD_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_CNT   ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SELT_STND_CD".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);

        int dmlcount = this.getDao("boadao").update("tbeb_racer_arrange_stnd_ib001", param);
        return dmlcount;
    }

    /**
     * <p> 선수주선기준 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int deleteRacerArrangeStnd(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CFRNT_CD".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SEQ     ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_racer_arrange_stnd_db001", param);
        return dmlcount;
    }
    /**
     * <p> 대진일 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateCfrntDay(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_CNT".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);

        i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CFRNT_CD".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DAY_ORD ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_cfrnt_day_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> 대진일 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertCfrntDay(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("CFRNT_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_CNT".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("INST_ID ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);

        int dmlcount = this.getDao("boadao").update("tbeb_cfrnt_day_ib001", param);
        return dmlcount;
    }

    /**
     * <p> 대진일 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int deleteCfrntDay(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CFRNT_CD".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DAY_ORD ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_cfrnt_day_db001", param);
        return dmlcount;
    }
    
    /**
     * <p> 대진경주 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateCfrntRace(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_DGRE_CD   ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ST_MTHD_CD     ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STRT_TIME      ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_REG_NO_CNT".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_DIST      ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TURN_CNT       ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);

        i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CFRNT_CD".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DAY_ORD ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_NO ".trim())));
        
        int dmlcount = this.getDao("boadao").update("tbeb_cfrnt_race_ub001", param);
        return dmlcount;
    }

    /**
     * <p> 대진경주 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertCfrntRace(PosRecord record) 
    {
        // insert
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("CFRNT_CD       ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD        ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO        ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_DGRE_CD   ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ST_MTHD_CD     ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STRT_TIME      ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_REG_NO_CNT".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_DIST      ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TURN_CNT       ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("boadao").insert("tbeb_cfrnt_race_ib001", param);
        return dmlcount;
    }

    /**
     * <p> 대진경주 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteCfrntRace(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CFRNT_CD".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DAY_ORD ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_NO ".trim())));
        
        int dmlcount = this.getDao("boadao").delete("tbeb_cfrnt_race_db001", param);
        return dmlcount;
    }
}
