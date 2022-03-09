/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02020010.activity.SaveRacerPerioNo.java
 * 파일설명		: 기수별등록기간등록
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02020010.activity;

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
* 매핑하여 기수를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SaveRacerPerioNo extends SnisActivity
{    
    public SaveRacerPerioNo()
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
        int        nSize   = 0;
        String     sDsName = "";
        
        sDsName = "dsOutRegTerm";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
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
        String sDsName      = "";

    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        int nSeq = 0;
        
        // 기수등록기간 저장
        sDsName = "dsOutRegTerm";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // 삭제
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
            		nDeleteCount = nDeleteCount + deleteRegTerm(record);
	            }
	        }
	        
            // 저장
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if ( nSeq == 0 ) nSeq = getRegTerm(record); 
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
  	              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
	  	        {
	            	if ( (nTempCnt = updateRegTerm(record)) == 0 ) {
	                	nTempCnt = insertRegTerm(record, nSeq++);
	                }
	  	        }
	            
            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }

        // 기수 저장
        sDsName = "dsOutRacerPerioNo";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // 삭제
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
            		nDeleteCount = nDeleteCount + deletePerioNo(record);
            		deleteRegTerm(record);
	            }
	        }
	        
            // 저장
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if ( nSeq == 0 ) nSeq = getRegTerm(record); 
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
  	              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
	  	        {
	            	if ( (nTempCnt = updatePerioNo(record)) == 0 ) {
	                	nTempCnt = insertPerioNo(record);
	                }
	  	        }
	            
            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 기수등록기간 일련번호 구히기 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int getRegTerm(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO".trim())));
        PosRowSet rowset = this.getDao("boadao").find("tbeb_racer_perio_no_reg_term_fb000", param);

        BigDecimal nSeq = null;
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            nSeq = (BigDecimal) row.getAttribute("SEQ".trim());
            logger.logInfo("tbeb_racer_perio_no_reg_term_fb000.SEQ : " + nSeq);
        }
            
        return nSeq.intValue();
    }

    /**
     * <p> 기수등록기간 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateRegTerm(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_DT        ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("END_DT        ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK           ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SEQ           ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_racer_perio_no_reg_term_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> 기수등록기간 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertRegTerm(PosRecord record, int nSeq) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO".trim())));
        param.setValueParamter(i++, Integer.toString(nSeq));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_DT        ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("END_DT        ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK           ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_racer_perio_no_reg_term_ib001", param);
        return dmlcount;
    }
    
    /**
     * <p> 기수등록기간 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteRegTerm(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SEQ           ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_racer_perio_no_reg_term_db001", param);
        return dmlcount;
    }
    
    
    /**
     * <p> 기수 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updatePerioNo(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("REG_DT")));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_racer_perio_no_reg_term_ub002", param);
        
        return dmlcount;
    }

    /**
     * <p> 기수 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertPerioNo(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("REG_DT"        )));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_racer_perio_no_reg_term_ib002", param);

        return dmlcount;
    }
    
    /**
     * <p> 기수 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deletePerioNo(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));

        int dmlcount = this.getDao("boadao").update("tbeb_racer_perio_no_reg_term_db002", param);

        deleteRegTerm(record);
        return dmlcount;
    }    
}
