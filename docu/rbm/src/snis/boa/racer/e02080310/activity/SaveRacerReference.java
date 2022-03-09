/*================================================================================
 * 시스템		: 선수관리
 * 소스파일 이름: snis.boa.racer.e03010017.activity.SaveRacerReference.java
 * 파일설명		: 선수 면담정보(인적사항, 경주운영, 선수관계, 면담일지) 저장 및 삭제
 * 작성자		: 정리운
 * 버전			:
 * 생성일자		: 2015-09-12
 * 최종수정일자	: 
 * 최종수정자	: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.racer.e02080310.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 부상선수정보를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 정리운
*/
public class SaveRacerReference extends SnisActivity
{    
	
	protected String sRacerNo      = "";
	protected String sSessionUserId = "";
	
    public SaveRacerReference()
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

        ds    = (PosDataset)ctx.get("dsRacerRefHuman");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if(logger.isDebugEnabled())	logger.logDebug(record);
        }
        
        ds    = (PosDataset)ctx.get("dsRacerRefRace");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
        	PosRecord record = ds.getRecord(i);
        	if(logger.isDebugEnabled())	logger.logDebug(record);
        }
        
        ds    = (PosDataset)ctx.get("dsRacerRefRela");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
        	PosRecord record = ds.getRecord(i);
        	if(logger.isDebugEnabled())	logger.logDebug(record);
        }
        
        
        ds    = (PosDataset)ctx.get("dsRacerRefDial");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
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
         
         ds    = (PosDataset) ctx.get("dsRacerRefHuman");
         nSize = ds.getRecordCount();

         for ( int i = 0; i < nSize; i++ ) 
         {
             PosRecord record = ds.getRecord(i);
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
             {
             	nSaveCount = nSaveCount + saveRacerRef(record);
             }
         }
         
         ds    = (PosDataset) ctx.get("dsRacerRefRace");
         nSize = ds.getRecordCount();
         
         for ( int i = 0; i < nSize; i++ ) 
         {
        	 PosRecord record = ds.getRecord(i);
        	 if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
        			 || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
        	 {
        		 nTempCnt = insertRacerRefRace(record);
        		 nSaveCount = nSaveCount + + nTempCnt;
        	 }
         }
         
         ds    = (PosDataset) ctx.get("dsRacerRefRela");
         nSize = ds.getRecordCount();
         for ( int i = 0; i < nSize; i++ ) 
         {
             PosRecord record = ds.getRecord(i);
             if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
             {
                 // delete
             	nDeleteCount = nDeleteCount + deleteRacerRefRela(record);
             }
         }
         
         for ( int i = 0; i < nSize; i++ ) 
         {
        	 PosRecord record = ds.getRecord(i);
        	 if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
        			 || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
        	 {
        		 nTempCnt = insertRacerRefRela(record);
        		 nSaveCount = nSaveCount + + nTempCnt;
        	 }
         }

         ds    = (PosDataset) ctx.get("dsRacerRefDial");
         nSize = ds.getRecordCount();
         
         for ( int i = 0; i < nSize; i++ ) 
         {
             PosRecord record = ds.getRecord(i);
             if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
             {
                 // delete
             	nDeleteCount = nDeleteCount + deleteRacerRefDial(record);
             }
         }
         for ( int i = 0; i < nSize; i++ ) 
         {
        	 PosRecord record = ds.getRecord(i);
        	 if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
        			 || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
        	 {
        		 nTempCnt = insertRacerRefDial(record);
        		 nSaveCount = nSaveCount + + nTempCnt;
        	 }
         }         
         
         Util.setSaveCount  (ctx, nSaveCount     );
         Util.setDeleteCount(ctx, nDeleteCount   );
     }  

     
     /**
      * <p> 선수 면담정보(인적사항) 저장 </p>
      * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
      * @return  effectedRowCnt int		update 레코드 개수
      * @throws  none
      */
     protected int saveRacerRef(PosRecord record)
     {
     	int effectedRowCnt = 0;
     	effectedRowCnt =insertRacerRefHum(record);

         return effectedRowCnt;    	
     }

     
     /**
      * <p> 선수 면담정보(인적사항) 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int insertRacerRefHum(PosRecord record) 
     {
         PosParameter param = new PosParameter();
         int i = 0;
         
         param.setValueParamter(i++, sRacerNo                           );
         param.setValueParamter(i++, Util.trim(record.getAttribute("HOME_PLACE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("PARENT_TO_YN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("CHILD_M".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("CHILD_W".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("CHILE_AGE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("PARENTING_STYLE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("WIFE_JOB".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ECO_STANDARD".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("LIFE_MAIN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ETC_JOB_YN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TWOJOB_FIELD".trim())));      
         param.setValueParamter(i++, Util.trim(record.getAttribute("TWOJOB_PLACE".trim())));        
         param.setValueParamter(i++, Util.trim(record.getAttribute("PARTNER_YN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TWOJOB_INCOME".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TWOJOB_TM".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TWOJOB_PLAN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DRINKING".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DRINKING_TIME".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DRINKING_HABIT".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("SMOKING".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("SKILL".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("HOBBY".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("WGT_CONTROL_PLAN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DIET".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("LUNCH_YN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("HEALTH_CON".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("MEDICAL_YN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("FAMILY_MED".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("INSURANCE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("INSURANCE_RE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("FAMILY_TEL".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EMERGENCY_TEL".trim())));
         param.setValueParamter(i++, SESSION_USER_ID );

         param.setValueParamter(i++, sRacerNo                           );
         param.setValueParamter(i++, Util.trim(record.getAttribute("HOME_PLACE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("PARENT_TO_YN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("CHILD_M".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("CHILD_W".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("CHILE_AGE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("PARENTING_STYLE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("WIFE_JOB".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ECO_STANDARD".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("LIFE_MAIN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ETC_JOB_YN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TWOJOB_FIELD".trim())));      
         param.setValueParamter(i++, Util.trim(record.getAttribute("TWOJOB_PLACE".trim())));        
         param.setValueParamter(i++, Util.trim(record.getAttribute("PARTNER_YN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TWOJOB_INCOME".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TWOJOB_TM".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TWOJOB_PLAN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DRINKING".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DRINKING_TIME".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DRINKING_HABIT".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("SMOKING".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("SKILL".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("HOBBY".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("WGT_CONTROL_PLAN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DIET".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("LUNCH_YN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("HEALTH_CON".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("MEDICAL_YN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("FAMILY_MED".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("INSURANCE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("INSURANCE_RE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("FAMILY_TEL".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EMERGENCY_TEL".trim())));
         param.setValueParamter(i++, SESSION_USER_ID );

         int dmlcount = this.getDao("boadao").update("tbec_racer_reference_in001", param);
         
         return dmlcount;  
     }     

     
     /**
      * <p> 선수 면담정보(경주운영) 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int insertRacerRefRace(PosRecord record) 
     {
    	 PosParameter param = new PosParameter();
    	 int i = 0;
    	 
         param.setValueParamter(i++, sRacerNo                           );
         param.setValueParamter(i++, Util.trim(record.getAttribute("LANE_LIKE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("LANE_DISLIKE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("LANE_STR_RUN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("START_IN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("START_CENTER".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("START_OUT".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("START_WEA".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("START_ORGAN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TURNING_BEST".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TURNING_START".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TURNING_SCOPE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("STRA_LIKE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("STRA_WEA".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("STRA_LANE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("STRA_MOTOR".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("STRA_PELLER".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TRAIN_POINT".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TRAIN_FRE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TRAIN_CON".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_PELLER".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_ACE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_ETC".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("PAR_LIKE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("PAR_DISLIKE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("PAR_INTER".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("PAR_WEA".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("MOBOT_REP".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("MOBOT_INST".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("MOBOT_H_HAN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("MOBOT_L_HAN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ORGAN_CLOSE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ORGAN_STAB".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ORGAN_LOAD".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ORGAN_LIKE".trim())));
         param.setValueParamter(i++, SESSION_USER_ID );

         param.setValueParamter(i++, sRacerNo                           );
         param.setValueParamter(i++, Util.trim(record.getAttribute("LANE_LIKE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("LANE_DISLIKE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("LANE_STR_RUN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("START_IN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("START_CENTER".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("START_OUT".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("START_WEA".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("START_ORGAN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TURNING_BEST".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TURNING_START".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TURNING_SCOPE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("STRA_LIKE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("STRA_WEA".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("STRA_LANE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("STRA_MOTOR".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("STRA_PELLER".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TRAIN_POINT".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TRAIN_FRE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TRAIN_CON".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_PELLER".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_ACE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_ETC".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("PAR_LIKE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("PAR_DISLIKE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("PAR_INTER".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("PAR_WEA".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("MOBOT_REP".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("MOBOT_INST".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("MOBOT_H_HAN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("MOBOT_L_HAN".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ORGAN_CLOSE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ORGAN_STAB".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ORGAN_LOAD".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ORGAN_LIKE".trim())));
         param.setValueParamter(i++, SESSION_USER_ID );
         
    	 int dmlcount = this.getDao("boadao").update("tbec_racer_reference_in002", param);
    	 
    	 return dmlcount; 
     }
     
     
     /**
      * <p> 선수 면담정보(선수관계) 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int insertRacerRefRela(PosRecord record) 
     {
    	 PosParameter param = new PosParameter();
    	 int i = 0;
    	 
         param.setValueParamter(i++, sRacerNo                           );
         param.setValueParamter(i++, Util.trim(record.getAttribute("RELA_RACER_CD".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("RELA_RACER_NO".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("RELA_RACER_PERIO_NO".trim())));
         param.setValueParamter(i++, SESSION_USER_ID );
    	 
         param.setValueParamter(i++, sRacerNo                           );
         param.setValueParamter(i++, Util.trim(record.getAttribute("RELA_RACER_CD".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("RELA_RACER_NO".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("RELA_RACER_PERIO_NO".trim())));
         param.setValueParamter(i++, SESSION_USER_ID );
         
    	 int dmlcount = this.getDao("boadao").update("tbec_racer_reference_in003", param);
    	 return dmlcount; 
     }


     /**
      * <p> 선수 면담정보(선수관계) 삭제 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int deleteRacerRefRela(PosRecord record) 
     {
         PosParameter param = new PosParameter();
         int i = 0;
         param.setWhereClauseParameter(i++, sRacerNo                  );
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RELA_RACER_CD".trim())));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RELA_RACER_NO".trim())));
         
         int dmlcount = this.getDao("boadao").delete("tbec_racer_reference_de001", param);
         return dmlcount;
     } 
     
     
     
     /**
      * <p> 선수 면담정보(면담일지) 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int insertRacerRefDial(PosRecord record) 
     {
    	 PosParameter param = new PosParameter();
    	 int i = 0;
    	 
         param.setValueParamter(i++, sRacerNo                           );
         param.setValueParamter(i++, Util.trim(record.getAttribute("DIARY_DATE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DIARY_HOUR".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DIARY_MINUTE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DIARY_TITLE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DIARY_CONTENTS".trim())));
         param.setValueParamter(i++, SESSION_USER_ID );

         param.setValueParamter(i++, sRacerNo                           );
         param.setValueParamter(i++, Util.trim(record.getAttribute("DIARY_DATE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DIARY_HOUR".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DIARY_MINUTE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DIARY_TITLE".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DIARY_CONTENTS".trim())));
         param.setValueParamter(i++, SESSION_USER_ID );
    	 
    	 int dmlcount = this.getDao("boadao").update("tbec_racer_reference_in004", param);
    	 return dmlcount; 
     }
     
     
     /**
      * <p> 선수 면담정보(면담일지)삭제 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int deleteRacerRefDial(PosRecord record) 
     {
    	 PosParameter param = new PosParameter();
    	 int i = 0;
    	 param.setWhereClauseParameter(i++, sRacerNo                  );
    	 param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DIARY_DATE".trim())));
    	 param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DIARY_HOUR".trim())));
    	 param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DIARY_MINUTE".trim())));
    	 
    	 int dmlcount = this.getDao("boadao").delete("tbec_racer_reference_de002", param);
    	 return dmlcount;
     } 
     
}    