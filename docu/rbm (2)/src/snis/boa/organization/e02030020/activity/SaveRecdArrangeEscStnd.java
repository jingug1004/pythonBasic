/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02030020.activity.SaveRecdArrangeEscStnd.java
 * 파일설명		: 등급평가주선보류등록
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02030020.activity;

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
* 매핑하여 등급평가주선보류를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SaveRecdArrangeEscStnd extends SnisActivity
{    
    public SaveRecdArrangeEscStnd()
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
        
        sDsName = "dsOutRecdArrangeEscStnd";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
	        
        sDsName = "dsOutEscRacer";
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
/*
        // 주선제외기준 저장
        sDsName = "dsOutRecdArrangeEscStnd";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // 저장
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
            	if ( (nTempCnt = updateRecdArrangeEscStnd(record)) == 0 ) {
                	nTempCnt = insertRecdArrangeEscStnd(record);
                }

            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }
*/
        // 주선제외자 저장
        sDsName = "dsOutEscRacer";
        //deleteEscRacer(ctx);
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        
	        int nSeq = getEscRacerSeq(ctx);
            // 저장
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
            	nTempCnt = insertEscRacer(record, ctx, nSeq++);

            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 주선제외기준 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateRecdArrangeEscStnd(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_RACER_PERIO_NO".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("END_RACER_PERIO_NO".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_COND          ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("END_COND          ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STND_YEAR         ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("QURT_CD           ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RECD_ESC_CLS_CD   ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_recd_arrange_esc_stnd_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> 주선제외기준 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertRecdArrangeEscStnd(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR         ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("QURT_CD           ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RECD_ESC_CLS_CD   ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_RACER_PERIO_NO".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("END_RACER_PERIO_NO".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_COND          ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("END_COND          ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_recd_arrange_esc_stnd_ib001", param);
        return dmlcount;
    }
    
    /**
     * <p> 주선제외자 일련번호 조회 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int getEscRacerSeq(PosContext ctx)
    {
        PosParameter param = new PosParameter();

        PosRowSet rowset = this.getDao("boadao").find("tbec_arrange_esc_fb000", param);
        
        int nSeq = 0;
        if ( rowset.hasNext() ) {
        	PosRow row = rowset.next(); 
        	nSeq = ((BigDecimal) row.getAttribute("SEQ")).intValue();
        }
        return nSeq;
    }

    
    /**
     * <p> 주선제외자 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int insertEscRacer(PosRecord record, PosContext ctx, int nSeq)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO ".trim())));
        param.setValueParamter(i++, Integer.toString(nSeq));
        param.setValueParamter(i++, Util.trim(ctx.get            ("STR_DT   ".trim())));
        param.setValueParamter(i++, Util.trim(ctx.get            ("END_DT   ".trim())));
        
        String sRsnCd = (String) record.getAttribute("RSN_CD   ".trim());
        if ( sRsnCd.equals("201")) {
            param.setValueParamter(i++, Util.trim(record.getAttribute("DESC".trim())) + "("+ Util.trim(record.getAttribute("AVG_RANK_SCR".trim())) + ")");
        } else if ( sRsnCd.equals("202")) {
            param.setValueParamter(i++, Util.trim(record.getAttribute("DESC".trim())) + "("+ Util.trim(record.getAttribute("AVG_ACDNT_SCR".trim())) + ")");
        } else if ( sRsnCd.equals("203")) {
            param.setValueParamter(i++, Util.trim(record.getAttribute("DESC".trim())) + "("+ Util.trim(record.getAttribute("CONT_CNT".trim())) + ")");
        } else if ( sRsnCd.equals("204")) {
            param.setValueParamter(i++, Util.trim(record.getAttribute("DESC".trim())) + "("+ Util.trim(record.getAttribute("FL_CNT".trim())) + ")");
        } else if ( sRsnCd.equals("205")) {
            param.setValueParamter(i++, Util.trim(record.getAttribute("DESC".trim())) + "("+ Util.trim(record.getAttribute("AVG_SCR".trim())) + ")");
        }
        
        param.setValueParamter(i++, Util.trim(record.getAttribute("RSN_CD   ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbec_arrange_esc_ib001", param);
        
        return dmlcount;
    }

    /**
     * <p> 주선제외자 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteEscRacer(PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(ctx.get("STND_YEAR         ".trim())));
        param.setValueParamter(i++, Util.trim(ctx.get("QURT_CD           ".trim())));

        int dmlcount = this.getDao("boadao").update("tbec_arrange_esc_db001", param);
        return dmlcount;
    }
}
