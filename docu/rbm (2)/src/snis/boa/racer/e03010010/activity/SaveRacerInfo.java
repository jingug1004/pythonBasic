/*================================================================================
 * 시스템			: 선수관리
 * 소스파일 이름	: snis.boa.racer.e03010010.activity.SaveRacerInfo.java
 * 파일설명		: 선수정보등록
 * 작성자			: 김경화
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.racer.e03010010.activity;

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
* 매핑하여 선수정보를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 김경화
* @version 1.0
*/
public class SaveRacerInfo extends SnisActivity
{    
	protected String sRacerNo      = "";
	protected String sSessionUserId = "";
	private String sQueryID = "";
	
    public SaveRacerInfo()
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
        int nSize        = 0;

        ds    = (PosDataset)ctx.get("dsOutRacer");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if(logger.isDebugEnabled())	logger.logDebug(record);
        }
        
        ds    = (PosDataset)ctx.get("dsOutRacerFam");
        nSize = ds.getRecordCount();
        for (int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if(logger.isDebugEnabled())	logger.logDebug(record);
        }

        ds    = (PosDataset)ctx.get("dsOutRacerAcad");
        nSize = ds.getRecordCount();
        for (int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if(logger.isDebugEnabled())	logger.logDebug(record);
        }        

        ds    = (PosDataset)ctx.get("dsOutRacerRela");
        nSize = ds.getRecordCount();
        for (int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if(logger.isDebugEnabled())	logger.logDebug(record);
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
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 

    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sRacerNo = (String) ctx.get("RACER_NO");
        ds    = (PosDataset) ctx.get("dsOutRacer");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {

            	// update
            	nSaveCount = nSaveCount + saveRacer(record);
            }
        }

        // 가족사항 저장
        ds    = (PosDataset)ctx.get("dsOutRacerFam");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
                // delete
            	nDeleteCount = nDeleteCount + deleteRacerFam(record);
            }
        }
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	nTempCnt = insertRacerFam(record);
            	nSaveCount = nSaveCount + nTempCnt;
            }
        }

        // 학력사항 저장
        ds    = (PosDataset)ctx.get("dsOutRacerAcad");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
                // delete
            	nDeleteCount = nDeleteCount + deleteRacerAcad(record);
            }
        }
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
                nTempCnt = insertRacerAcad(record);
            	nSaveCount = nSaveCount + nTempCnt;
            }
        }        

        
        // 선수관계 저장
        ds    = (PosDataset)ctx.get("dsOutRacerRela");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
                // delete
            	nDeleteCount = nDeleteCount + deleteRacerRela(record);
            }
        }
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
                nTempCnt = insertRacerRela(record);
            	nSaveCount = nSaveCount + nTempCnt;
            }
        }        

        // 선수헬멧 저장
        ds    = (PosDataset)ctx.get("dsOutRacerHelmet");
        for ( int i = 0; i < ds.getRecordCount(); i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
                nTempCnt = insertRacerHelmet(record);
            	nSaveCount = nSaveCount + nTempCnt;
            }
        }        
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 선수상세정보 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveRacer(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt =insertRacer(record);

        return effectedRowCnt;    	
    }

    /**
     * <p> 선수상세정보 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertRacer(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sRacerNo                           );
        param.setValueParamter(i++, Util.trim(record.getAttribute("NM_KOR".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_GRD_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_STAT_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("FAVOR_COURSE_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("EMAIL_ADDR".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, sRacerNo                           );
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_GRD_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("NM_KOR".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_STAT_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("FAVOR_COURSE_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("EMAIL_ADDR".trim())));      
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO".trim())));        
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );
        
        int dmlcount = this.getDao("boadao").update("tbec_racer_master_ic001", param);

        if (dmlcount > 0)	{ //마스터정보가 정상적으로 업데이트 되면..
        	i = 0;
        	
        	PosParameter param2 = new PosParameter();
        	
        	param2.setValueParamter(i++, sRacerNo                           );
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("BIRTH_DT")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("LUNSOL_CD")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("MRRG_CD")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("WEDDING_DT")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("RES_NO")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("CELPON_NO")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("HOMEPAGE_ADDR")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("HOME_PHONE_NO")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("CAR_NO")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("NM_CHN")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("NM_ENG")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("NATV_PLAC_CD")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("NATY_SCHL")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("NATY_UNIV")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("NATY_GRAD")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("POST_NO")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("ADD1_HOME")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("ADD2_HOME")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("BORN_ADDR")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("PTO_NM")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("PTO_PATH")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("CNL_RSN")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("CNL_DT")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("SIGHT_LEFT")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("SIGHT_RIGT")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("BLOD_TPE")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("DRINK")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("SMOKING")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("RELGN_CD")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("HOBY")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("SPA_ABTY")));
        	param2.setValueParamter(i++, record.getAttribute("HGHT"));
        	param2.setValueParamter(i++, record.getAttribute("WGHT"));
        	param2.setValueParamter(i++, record.getAttribute("SHOE_SIZE"));
        	param2.setValueParamter(i++, record.getAttribute("SHOULDER_SIZE"));
        	param2.setValueParamter(i++, record.getAttribute("ARM_SIZE"));
        	param2.setValueParamter(i++, record.getAttribute("BACK_SIZE"));
        	param2.setValueParamter(i++, record.getAttribute("UP_BODY_SIZE"));
        	param2.setValueParamter(i++, record.getAttribute("BREAST_SIZE"));
        	param2.setValueParamter(i++, record.getAttribute("WGHT_SIZE"));
        	param2.setValueParamter(i++, record.getAttribute("ABDOMEN_SIZE"));
        	param2.setValueParamter(i++, record.getAttribute("INNER_SUIT_SIZE"));
        	param2.setValueParamter(i++, record.getAttribute("SUIT_SIZE"));
        	param2.setValueParamter(i++, record.getAttribute("THIGH_SIZE"));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("ARMY_TPE_CD")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("ARMY_CLAS_CD")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("SPEC_INFO")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("NATY_RACER_YN")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("NATY_RACER_ITEM")));
        	param2.setValueParamter(i++, SESSION_USER_ID );        	
        	param2.setValueParamter(i++, sRacerNo                           );
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("BIRTH_DT")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("MRRG_CD")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("WEDDING_DT")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("RES_NO")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("CELPON_NO")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("HOMEPAGE_ADDR")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("HOME_PHONE_NO")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("CAR_NO")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("NM_CHN")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("NM_ENG")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("NATV_PLAC_CD")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("NATY_SCHL")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("NATY_UNIV")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("NATY_GRAD")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("POST_NO")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("ADD1_HOME")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("ADD2_HOME")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("BORN_ADDR")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("PTO_NM")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("PTO_PATH")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("CNL_RSN")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("CNL_DT")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("SIGHT_LEFT")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("SIGHT_RIGT")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("BLOD_TPE")));	
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("DRINK")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("SMOKING")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("RELGN_CD")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("HOBY")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("SPA_ABTY")));
        	param2.setValueParamter(i++, record.getAttribute("HGHT"));
        	param2.setValueParamter(i++, record.getAttribute("WGHT"));
        	param2.setValueParamter(i++, record.getAttribute("SHOE_SIZE"));
        	param2.setValueParamter(i++, record.getAttribute("SHOULDER_SIZE"));
        	param2.setValueParamter(i++, record.getAttribute("ARM_SIZE"));
        	param2.setValueParamter(i++, record.getAttribute("BACK_SIZE"));
        	param2.setValueParamter(i++, record.getAttribute("UP_BODY_SIZE"));
        	param2.setValueParamter(i++, record.getAttribute("BREAST_SIZE"));
        	param2.setValueParamter(i++, record.getAttribute("WGHT_SIZE"));
        	param2.setValueParamter(i++, record.getAttribute("ABDOMEN_SIZE"));               
        	param2.setValueParamter(i++, record.getAttribute("INNER_SUIT_SIZE"));            
        	param2.setValueParamter(i++, record.getAttribute("SUIT_SIZE"));                
        	param2.setValueParamter(i++, record.getAttribute("THIGH_SIZE"));                
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("ARMY_TPE_CD")));                
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("ARMY_CLAS_CD")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("SPEC_INFO"))); 	
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("NATY_RACER_YN")));
        	param2.setValueParamter(i++, Util.trim(record.getAttribute("NATY_RACER_ITEM")));
        	param2.setValueParamter(i++, SESSION_USER_ID );
        	param2.setValueParamter(i++, SESSION_USER_ID );
        	
     		//선수상세정보 입력
        	dmlcount = this.getDao("boadao").update("tbec_racer_detail_ic001", param2);
        }
        
        return dmlcount;  
    }

    /**
     * <p> 선수가족사항 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertRacerFam(PosRecord record) 
    {
        // insert
        PosParameter param = new PosParameter();
        int i = 0;
        sQueryID 		= "tbec_racer_family_fc002";
        int iSeq      	= getSeq(record);
        String tmpSeq = "0";        
        if (record.getAttribute("SEQ".trim()) != null)	tmpSeq = String.valueOf(record.getAttribute("SEQ".trim()));
        
        param.setValueParamter(i++, sRacerNo                  );
        param.setValueParamter(i++, String.valueOf(tmpSeq));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RELN_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("NM".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("BIRTH_DT".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SCHL_TPE_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("JOB_INFO".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);        
        param.setValueParamter(i++, sRacerNo                    );       
        param.setValueParamter(i++, String.valueOf(iSeq));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RELN_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("NM".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("BIRTH_DT".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SCHL_TPE_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("JOB_INFO".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("boadao").insert("tbec_racer_family_ic001", param);
        return dmlcount;
    }

    /**
     * <p> 데이타셋의 한 레코드에 대해 Query의 조건절에 맞는 delete를 하기 위한 메소드 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteRacerFam(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sRacerNo                  );
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SEQ".trim())));
        
        int dmlcount = this.getDao("boadao").delete("tbec_racer_family_dc001", param);
        return dmlcount;
    }    

    /**
     * <p> 선수학력사항 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertRacerAcad(PosRecord record) 
    {
        // insert
        PosParameter param = new PosParameter();
        int i = 0;
        sQueryID 		= "tbec_racer_academic_fc002";
        int iSeq      	= getSeq(record);
        String tmpSeq = "0";        
        if (record.getAttribute("SEQ".trim()) != null)	tmpSeq = String.valueOf(record.getAttribute("SEQ".trim()));

        param.setValueParamter(i++, sRacerNo                  );
        param.setValueParamter(i++, String.valueOf(tmpSeq));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SCHL_TPE_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SCHL_NM".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("GRDU_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MTRIC_YY".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("GRDU_YY".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MJR".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);        
        param.setValueParamter(i++, sRacerNo                    );
        param.setValueParamter(i++, String.valueOf(iSeq));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SCHL_TPE_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SCHL_NM".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("GRDU_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MTRIC_YY".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("GRDU_YY".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MJR".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("boadao").insert("tbec_racer_academic_ic001", param);
        return dmlcount;
    }

    /**
     * <p> 데이타셋의 한 레코드에 대해 Query의 조건절에 맞는 delete를 하기 위한 메소드 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteRacerAcad(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sRacerNo                  );
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SEQ".trim())));
        
        int dmlcount = this.getDao("boadao").delete("tbec_racer_academic_dc001", param);
        return dmlcount;
    } 

    /**
     * <p> 선수관계정보 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertRacerRela(PosRecord record) 
    {
        // insert
        PosParameter param = new PosParameter();
        int i = 0;
        sQueryID		= "tbec_racer_relation_fc002";
        int iSeq      	= getSeq(record);
        String tmpSeq = "0";        
        if (record.getAttribute("SEQ".trim()) != null)	tmpSeq = String.valueOf(record.getAttribute("SEQ".trim()));
        
        param.setValueParamter(i++, sRacerNo                  );
        param.setValueParamter(i++, String.valueOf(tmpSeq));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RELA_RACER_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RELA_RACER_NO".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RELA_GRD_CD".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);    
        param.setValueParamter(i++, sRacerNo                    );
        param.setValueParamter(i++, String.valueOf(iSeq));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RELA_RACER_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RELA_RACER_NO".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RELA_GRD_CD".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("boadao").insert("tbec_racer_relation_ic001", param);
        return dmlcount;
    }

    /**
     * <p> 데이타셋의 한 레코드에 대해 Query의 조건절에 맞는 delete를 하기 위한 메소드 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteRacerRela(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sRacerNo                  );
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SEQ".trim())));
        
        int dmlcount = this.getDao("boadao").delete("tbec_racer_relation_dc001", param);
        return dmlcount;
    } 

    /**
     * <p> 순번가져오기</p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int getSeq(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;        
        param.setWhereClauseParameter(i++, sRacerNo);      
        PosRowSet rowset = this.getDao("boadao").find(sQueryID, param);

        BigDecimal nCnt = null;
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            nCnt = (BigDecimal) row.getAttribute("SEQ".trim());
        }
            
        return nCnt.intValue();
    }    

    /**
     * <p> 선수헬멧정보 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertRacerHelmet(PosRecord record) 
    {
        // insert
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sRacerNo                  );
        param.setValueParamter(i++, Util.trim(record.getAttribute("HELMET_TYPE".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PHOTO_FRONT_FILE".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PHOTO_FRONT_FILE_NM".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PHOTO_BACK_FILE".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PHOTO_BACK_FILE_NM".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("boadao").update("tbec_racer_helmet_ic001", param);
        return dmlcount;
    }
}