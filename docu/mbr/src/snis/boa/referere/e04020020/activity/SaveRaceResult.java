/*================================================================================
 * 시스템			: 심판 관리 
 * 소스파일 이름	: snis.boa.referere.e04020020.activity.SaveRaceResult.java
 * 파일설명		: 심판 관리 
 * 작성자			: 정의태 
 * 버전			: 1.0.0
 * 생성일자		: 2007-12-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.referere.e04020020.activity;

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
* 경주결과 정보를 등록, 수정, 삭제한다.
* @auther 정의태
* @version 1.0
*/
public class SaveRaceResult extends SnisActivity
{    
	
    public SaveRaceResult()
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
        //경주테이블 저장(랩타임)
        ds    = (PosDataset)ctx.get("dsOutRace");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
        	nSaveCount = nSaveCount + SaveRaceResult(record);
            if(logger.isDebugEnabled())	logger.logDebug(record);
        }
         //편성테이블 저장(순위)
        nSaveCount   = 0;
        nDeleteCount = 0;
        ds    = (PosDataset) ctx.get("dsOutOrgan");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + SaveOrganResult(record);
        }  
        if ( nSaveCount > 0) {
	        // 편성테이블 저장(시간차)
	        ds    = (PosDataset) ctx.get("dsOutOrgan");
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < 1; i++ ){
	            PosRecord record = ds.getRecord(i);
	            nSaveCount = nSaveCount + SaveOrganTimeDiff(record);
	        }  
        }
        
        //편성테이블 저장(진입코스)
        nSaveCount   = 0;
        nDeleteCount = 0;
        ds    = (PosDataset) ctx.get("dsOutOrgan1");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + SaveOrganResult1(record);
        }
        
        //편성테이블 저장(소개항주)
        nSaveCount   = 0;
        nDeleteCount = 0;
        ds    = (PosDataset) ctx.get("dsOutOrgan2");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + SaveOrganResult2(record);
        }
        
        //위반,사고테이블 저장
        nSaveCount   = 0;
        nDeleteCount = 0;
        ds    = (PosDataset) ctx.get("dsOutVoilAct");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteVoilActResult(record);
            }else{
            	 nSaveCount = nSaveCount + SaveVoilActResult(record);
            }
        }
        
        //첨부파일저저장	
        ds    = (PosDataset) ctx.get("dsUploadFile");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
            	nDeleteCount = nDeleteCount + deleteFile(record,ctx);
	        }

	        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
	        	nSaveCount = nSaveCount + insertFile(record, ctx);
	        } 
         } 
        
        
    } 
    /**
     * <p> 경주테이블 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int SaveRaceResult(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateRaceResult(record);
    	return effectedRowCnt;
    }
    
    /**
     * <p> 경주테이블  Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    
     protected int updateRaceResult(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, Util.trim(record.getAttribute("LAPTIME_1".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("LAPTIME_2".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("LAPTIME_3".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SESSION_USER_ID".trim())));
        
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TMS".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO".trim())));
        
        return this.getDao("boadao").update("tbec_appo_race_result_detail_ud001", param);
    }
    
     /**
      * <p> 편성테이블(순위) Save </p>
      * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount int		update 레코드 개수
      * @throws  none
      */
     protected int SaveOrganResult(PosRecord record)
     {
     	int effectedRowCnt = 0;
     	effectedRowCnt = updateOrganResult(record);
     	return effectedRowCnt;
     }
     /**
      * <p> 편성테이블(순위)  Update </p>
      * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount int		update 레코드 개수
      * @throws  none
      */
      protected int updateOrganResult(PosRecord record)
     {
         PosParameter param = new PosParameter();
         int i = 0;
      
		 param.setValueParamter(i++, record.getAttribute("RANK"));
         param.setValueParamter(i++, record.getAttribute("RACE_TM"));
         param.setValueParamter(i++, record.getAttribute("STRATEGY_CD"));
         param.setValueParamter(i++, SESSION_USER_ID);	    
         
 	     param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
 	     param.setValueParamter(i++, record.getAttribute("MBR_CD"));
 	     param.setValueParamter(i++, record.getAttribute("TMS"));
 	     param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
 	     param.setValueParamter(i++, record.getAttribute("RACE_NO"));
 	     param.setValueParamter(i++, record.getAttribute("RACE_REG_NO"));
 	     
 	     int updcount = this.getDao("boadao").update("tbec_appo_race_result_detail_ud002", param);
         return updcount;
     }
      /**
       * <p> 편성테이블(시간차)  Update </p>
       * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
       * @return  dmlcount int		update 레코드 개수
       * @throws  none
       */
       protected int SaveOrganTimeDiff(PosRecord record)
      {
          PosParameter param = new PosParameter();
          int i = 0;
          param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR".trim())));
          param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD".trim())));
          param.setValueParamter(i++, Util.trim(record.getAttribute("TMS".trim())));
          param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD".trim())));
          param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO".trim())));
          	  
          int updcount = this.getDao("boadao").update("tbec_appo_race_result_detail_ud003", param);
          return updcount;
      } 
      
      /**
       * <p> 편성테이블(진입코스) Save </p>
       * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
       * @return  dmlcount int		update 레코드 개수
       * @throws  none
       */
      protected int SaveOrganResult1(PosRecord record)
      {
      	int effectedRowCnt = 0;
      	effectedRowCnt = updateOrganResult1(record);
      	return effectedRowCnt;
      }
      /**
       * <p> 편성테이블(진입코스)  Update </p>
       * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
       * @return  dmlcount int		update 레코드 개수
       * @throws  none
       */
      protected int updateOrganResult1(PosRecord record)
      {
          PosParameter param = new PosParameter();
          int i = 0;
      
         param.setValueParamter(i++, record.getAttribute("ENTRY_COURSE"));
         param.setValueParamter(i++, record.getAttribute("STAR_TM"));
         param.setValueParamter(i++, record.getAttribute("STAR_TM"));
         param.setValueParamter(i++, record.getAttribute("STAR_TM"));
         param.setValueParamter(i++, record.getAttribute("STAR_TM"));
         param.setValueParamter(i++, record.getAttribute("STAR_TM"));
         param.setValueParamter(i++, record.getAttribute("STAR_TM"));
         param.setValueParamter(i++, record.getAttribute("STAR_TM"));
         param.setValueParamter(i++, record.getAttribute("RMK"));
         param.setValueParamter(i++, SESSION_USER_ID);	    
          
  	     param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
  	     param.setValueParamter(i++, record.getAttribute("MBR_CD"));
  	     param.setValueParamter(i++, record.getAttribute("TMS"));
  	     param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
  	     param.setValueParamter(i++, record.getAttribute("RACE_NO"));
  	     param.setValueParamter(i++, record.getAttribute("RACE_REG_NO"));
  	     
  	     int updcount = this.getDao("boadao").update("tbec_appo_race_result_detail_ud004", param);
         return updcount;
      
      }
      /**
       * <p> 편성테이블(소개항주) Save </p>
       * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
       * @return  dmlcount int		update 레코드 개수
       * @throws  none
       */
      protected int SaveOrganResult2(PosRecord record)
      {
      	int effectedRowCnt = 0;
      	effectedRowCnt = updateOrganResult2(record);
      	return effectedRowCnt;
      }
      /**
       * <p> 편성테이블(소개항주)  Update </p>
       * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
       * @return  dmlcount int		update 레코드 개수
       * @throws  none
       */
      protected int updateOrganResult2(PosRecord record)
      {
          PosParameter param = new PosParameter();
          int i = 0;
          
          param.setValueParamter(i++, record.getAttribute("INTRO_RUN_TM"));
          param.setValueParamter(i++, record.getAttribute("INTRO_RUN_TM"));
          param.setValueParamter(i++, record.getAttribute("INTRO_RUN_TM"));
          param.setValueParamter(i++, record.getAttribute("INTRO_RUN_TM"));
          param.setValueParamter(i++, record.getAttribute("INTRO_RUN_TM"));
          param.setValueParamter(i++, record.getAttribute("INTRO_RUN_TM"));
          param.setValueParamter(i++, SESSION_USER_ID);	    
          
  	     param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
  	     param.setValueParamter(i++, record.getAttribute("MBR_CD"));
  	     param.setValueParamter(i++, record.getAttribute("TMS"));
  	     param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
  	     param.setValueParamter(i++, record.getAttribute("RACE_NO"));
  	     param.setValueParamter(i++, record.getAttribute("RACE_REG_NO"));
  	     
  	     int updcount = this.getDao("boadao").update("tbec_appo_race_result_detail_ud005", param);
         return updcount;
      }
      
      /**
       * <p> 위반테이블 Save </p>
       * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
       * @return  dmlcount int		update 레코드 개수
       * @throws  none
       */
      protected int SaveVoilActResult(PosRecord record)
      {
      	int effectedRowCnt = 0;
      	effectedRowCnt = updateVoilActResult(record);
      	if(effectedRowCnt<1){
      		effectedRowCnt =insertVoilActResult(record);
      		
      		
      	}
          return effectedRowCnt;
      } 
     
      /**
       * <p> 위반행위순번가져오기</p>
       * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
       * @return  dmlcount int		update 레코드 개수
       * @throws  none
       */
      protected int getVoilActSeq(PosRecord record)
      {
      	  PosParameter param = new PosParameter();
          int i = 0;        
          
           
          param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));
          param.setWhereClauseParameter(i++, record.getAttribute("MBR_CD"));
          param.setWhereClauseParameter(i++, record.getAttribute("TMS"));
          param.setWhereClauseParameter(i++, record.getAttribute("DAY_ORD"));
          param.setWhereClauseParameter(i++, record.getAttribute("RACE_NO"));
          param.setWhereClauseParameter(i++, record.getAttribute("RACE_REG_NO"));
          param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO"));  
          
          PosRowSet rowset = this.getDao("boadao").find("tbec_appo_race_result_detail_fd009", param);

          BigDecimal nCnt = null;
          
          if (rowset.hasNext())
          {
              PosRow row = rowset.next();
              nCnt = (BigDecimal) row.getAttribute("SEQ".trim());
          }
          
          return nCnt.intValue();
      }    
      
      /**
       * <p> 위반테이블/사고테이블 Insert </p>
       * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
       * @return  dmlcount int		update 레코드 개수
       * @throws  none
       */
      protected int insertVoilActResult(PosRecord record)
      {
          
           int iSeq      = getVoilActSeq(record);
           
           PosParameter param = new PosParameter();
           int i = 0;
          
          param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
          param.setValueParamter(i++, record.getAttribute("MBR_CD"));
          param.setValueParamter(i++, record.getAttribute("TMS"));
          param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
          param.setValueParamter(i++, record.getAttribute("RACE_NO"));
          param.setValueParamter(i++, record.getAttribute("RACE_REG_NO"));
          param.setValueParamter(i++, record.getAttribute("RACER_NO"));
          param.setValueParamter(i++, String.valueOf(iSeq));
          
          param.setValueParamter(i++, record.getAttribute("RACE_DAY"));
          param.setValueParamter(i++, record.getAttribute("CIRCUIT_CNT_CD"));
          param.setValueParamter(i++, record.getAttribute("ACDNT_LOC_CD"));
          param.setValueParamter(i++, record.getAttribute("VOIL_JO"));
          param.setValueParamter(i++, record.getAttribute("VOIL_CD"));
          param.setValueParamter(i++, record.getAttribute("VOIL_DESC"));
          param.setValueParamter(i++, record.getAttribute("RMK"));
          param.setValueParamter(i++, record.getAttribute("ACDNT_TPE_CD"));
          param.setValueParamter(i++, SESSION_USER_ID);
          param.setValueParamter(i++, record.getAttribute("SANC_ISSUE_NO"));
  	    
          int inscount = this.getDao("boadao").update("tbec_appo_race_result_detail_id001", param);
          //사고행위테이블 입력
          if (record.getAttribute("ACDNT_TPE_CD".trim()) != null && !"".equals(record.getAttribute("ACDNT_TPE_CD".trim()))) {
             int j = 0;
             PosParameter param2 = new PosParameter();
             
             param2.setValueParamter(j++, record.getAttribute("STND_YEAR"));
             param2.setValueParamter(j++, record.getAttribute("MBR_CD"));
             param2.setValueParamter(j++, record.getAttribute("TMS"));
             param2.setValueParamter(j++, record.getAttribute("DAY_ORD"));
             param2.setValueParamter(j++, record.getAttribute("RACE_NO"));
             param2.setValueParamter(j++, record.getAttribute("RACE_REG_NO"));
             param2.setValueParamter(j++, record.getAttribute("RACER_NO"));
            
             param2.setValueParamter(j++, String.valueOf(iSeq));
             
             param2.setValueParamter(j++, record.getAttribute("RACE_DAY"));
             param2.setValueParamter(j++, record.getAttribute("CIRCUIT_CNT_CD"));
             param2.setValueParamter(j++, record.getAttribute("ACDNT_LOC_CD"));
             param2.setValueParamter(j++, record.getAttribute("VOIL_JO"));
             param2.setValueParamter(j++, record.getAttribute("ACDNT_TPE_CD"));
             param2.setValueParamter(j++, record.getAttribute("VOIL_DESC"));
             param2.setValueParamter(j++, record.getAttribute("RMK"));
             param2.setValueParamter(j++, SESSION_USER_ID);
     	     
             int inscountsub = this.getDao("boadao").update("tbec_appo_race_result_detail_id002", param2);
          
          }
          
          return inscount;
          
          
      }
      /**
       * <p> 위반테이블 /사고테이블 Update </p>
       * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
       * @return  dmlcount int		update 레코드 개수
       * @throws  none
       */

      protected int updateVoilActResult(PosRecord record)
      {
          PosParameter param = new PosParameter();
          int i = 0;
          
          param.setValueParamter(i++, record.getAttribute("RACE_DAY"));
          param.setValueParamter(i++, record.getAttribute("CIRCUIT_CNT_CD"));
          param.setValueParamter(i++, record.getAttribute("ACDNT_LOC_CD"));
          param.setValueParamter(i++, record.getAttribute("VOIL_JO"));
          param.setValueParamter(i++, record.getAttribute("VOIL_CD"));
          param.setValueParamter(i++, record.getAttribute("VOIL_DESC"));
          param.setValueParamter(i++, record.getAttribute("RMK"));
          param.setValueParamter(i++, record.getAttribute("ACDNT_TPE_CD"));
          param.setValueParamter(i++, SESSION_USER_ID);
          param.setValueParamter(i++, record.getAttribute("SANC_ISSUE_NO"));
  			    		   
  	      param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
  	      param.setValueParamter(i++, record.getAttribute("MBR_CD"));
  	      param.setValueParamter(i++, record.getAttribute("TMS"));
  	      param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
  	      param.setValueParamter(i++, record.getAttribute("RACE_NO"));
  	      param.setValueParamter(i++, record.getAttribute("RACE_REG_NO"));
  	      param.setValueParamter(i++, record.getAttribute("RACER_NO"));
  	      param.setValueParamter(i++, record.getAttribute("SEQ"));
          
  	     int updcount = this.getDao("boadao").update("tbec_appo_race_result_detail_ud006", param);
          
  	     //사고행위테이블 수정
          if (record.getAttribute("ACDNT_TPE_CD".trim()) != null && !"".equals(record.getAttribute("ACDNT_TPE_CD".trim()))) {
	          int j = 0;
	          PosParameter param2 = new PosParameter();
	          
	          param2.setValueParamter(j++, record.getAttribute("RACE_DAY"));
	          param2.setValueParamter(j++, record.getAttribute("CIRCUIT_CNT_CD"));
	          param2.setValueParamter(j++, record.getAttribute("ACDNT_LOC_CD"));
	          param2.setValueParamter(j++, record.getAttribute("VOIL_JO"));
	          param2.setValueParamter(j++, record.getAttribute("ACDNT_TPE_CD"));
	          param2.setValueParamter(j++, record.getAttribute("VOIL_DESC"));
	          param2.setValueParamter(j++, record.getAttribute("RMK"));
	          param2.setValueParamter(j++, SESSION_USER_ID);
	   			    		   
	   	      param2.setValueParamter(j++, record.getAttribute("STND_YEAR"));
	   	      param2.setValueParamter(j++, record.getAttribute("MBR_CD"));
	   	      param2.setValueParamter(j++, record.getAttribute("TMS"));
	   	      param2.setValueParamter(j++, record.getAttribute("DAY_ORD"));
	   	      param2.setValueParamter(j++, record.getAttribute("RACE_NO"));
	   	      param2.setValueParamter(j++, record.getAttribute("RACE_REG_NO"));
	   	      param2.setValueParamter(j++, record.getAttribute("RACER_NO"));
	   	      param2.setValueParamter(j++, record.getAttribute("SEQ"));
	   	      
	   	      //사고행위테이블 수정
	   	      int updcountsub = this.getDao("boadao").update("tbec_appo_race_result_detail_ud007", param2);
              
	   	      //사고행위테이블 수정한게 없으면 입력
		   	  if(updcountsub < 1 &&  record.getAttribute("SEQ".trim()) != null  ){
		   	    	 int k = 0;
		             PosParameter param3 = new PosParameter();
		             
		             param3.setValueParamter(k++, record.getAttribute("STND_YEAR"));
		             param3.setValueParamter(k++, record.getAttribute("MBR_CD"));
		             param3.setValueParamter(k++, record.getAttribute("TMS"));
		             param3.setValueParamter(k++, record.getAttribute("DAY_ORD"));
		             param3.setValueParamter(k++, record.getAttribute("RACE_NO"));
		             param3.setValueParamter(k++, record.getAttribute("RACE_REG_NO"));
		             param3.setValueParamter(k++, record.getAttribute("RACER_NO"));
		             param3.setValueParamter(k++, record.getAttribute("SEQ"));
		             
		             param3.setValueParamter(k++, record.getAttribute("RACE_DAY"));
		             param3.setValueParamter(k++, record.getAttribute("CIRCUIT_CNT_CD"));
		             param3.setValueParamter(k++, record.getAttribute("ACDNT_LOC_CD"));
		             param3.setValueParamter(k++, record.getAttribute("VOIL_JO"));
		             param3.setValueParamter(k++, record.getAttribute("ACDNT_TPE_CD"));
		             param3.setValueParamter(k++, record.getAttribute("VOIL_DESC"));
		             param3.setValueParamter(k++, record.getAttribute("RMK"));
		             param3.setValueParamter(k++, SESSION_USER_ID);
		     	     
		             int inscountsub = this.getDao("boadao").update("tbec_appo_race_result_detail_id002", param3);
		   	  } 
          } else {  	      
             //사고행위테이블 삭제
        	  int l = 0;
	          PosParameter param4 = new PosParameter();
	          
	          param4.setValueParamter(l++, record.getAttribute("STND_YEAR"));
	   	      param4.setValueParamter(l++, record.getAttribute("MBR_CD"));
	   	      param4.setValueParamter(l++, record.getAttribute("TMS"));
	   	      param4.setValueParamter(l++, record.getAttribute("DAY_ORD"));
	   	      param4.setValueParamter(l++, record.getAttribute("RACE_NO"));
	   	      param4.setValueParamter(l++, record.getAttribute("RACE_REG_NO"));
	   	      param4.setValueParamter(l++, record.getAttribute("RACER_NO"));
	   	      param4.setValueParamter(l++, record.getAttribute("SEQ"));  
	   	      
	   	      int delcount = this.getDao("boadao").update("tbec_appo_race_result_detail_dd002", param4);
          }
  	    
          return updcount;
           
      }
      
      /**
       * <p> 위반테이블/사고테이블  삭제 </p>
       * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
       * @return  dmlcount int		update 레코드 개수
       * @throws  none
       */
      protected int deleteVoilActResult(PosRecord record) 
      {
          PosParameter param = new PosParameter();
          int i = 0;
          
          param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));
  	      param.setWhereClauseParameter(i++, record.getAttribute("MBR_CD"));
  	      param.setWhereClauseParameter(i++, record.getAttribute("TMS"));
  	      param.setWhereClauseParameter(i++, record.getAttribute("DAY_ORD"));
  	      param.setWhereClauseParameter(i++, record.getAttribute("RACE_NO"));
  	      param.setWhereClauseParameter(i++, record.getAttribute("RACE_REG_NO"));
  	      param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO"));
  	      param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
          
  	      int delcount = this.getDao("boadao").update("tbec_appo_race_result_detail_dd001", param);
  	      
  	      
  	    //사고행위테이블 삭제
          if (record.getAttribute("ACDNT_TPE_CD".trim()) != null && !"".equals(record.getAttribute("ACDNT_TPE_CD".trim()))) {
	        int j = 0;
	        PosParameter param2 = new PosParameter();
	        
	        param2.setWhereClauseParameter(j++, record.getAttribute("STND_YEAR"));
	  	    param2.setWhereClauseParameter(j++, record.getAttribute("MBR_CD"));
	  	    param2.setWhereClauseParameter(j++, record.getAttribute("TMS"));
	  	    param2.setWhereClauseParameter(j++, record.getAttribute("DAY_ORD"));
	  	    param2.setWhereClauseParameter(j++, record.getAttribute("RACE_NO"));
	  	    param2.setWhereClauseParameter(j++, record.getAttribute("RACE_REG_NO"));
	  	    param2.setWhereClauseParameter(j++, record.getAttribute("RACER_NO"));
	  	    param2.setWhereClauseParameter(j++, record.getAttribute("SEQ"));   
	        
	  	    delcount = this.getDao("boadao").update("tbec_appo_race_result_detail_dd002", param2);
	   	  	     
         }
  	       return delcount;
       }
       
      /**
       * <p> File 입력 </p>
       * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
       * @return  dmlcount	int		update 레코드 개수
       * @throws  none
       */
      protected int insertFile(PosRecord record, PosContext ctx) 
      {
    	  
          PosParameter param = new PosParameter();
          int i = 0;
          
         param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
         param.setValueParamter(i++, record.getAttribute("MBR_CD"));
         param.setValueParamter(i++, record.getAttribute("TMS"));
         param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
         param.setValueParamter(i++, record.getAttribute("RACE_NO"));
         
         param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
         param.setValueParamter(i++, record.getAttribute("MBR_CD"));
         param.setValueParamter(i++, record.getAttribute("TMS"));
         param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
         param.setValueParamter(i++, record.getAttribute("RACE_NO"));
         
         
         param.setValueParamter(i++, record.getAttribute("FILE_NAME"));
         param.setValueParamter(i++, record.getAttribute("FILE_URL"));
         param.setValueParamter(i++, record.getAttribute("FILE_SIZE"));
         param.setValueParamter(i++, SESSION_USER_ID);
         param.setValueParamter(i++, SESSION_USER_ID);
 	    
         return this.getDao("boadao").update("tbec_appo_race_result_detail_id003", param);
      }
     
     /**
       * <p> File 삭제 </p>
       * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
       * @return  dmlcount	int		update 레코드 개수
       * @throws  none
       */
      protected int deleteFile(PosRecord record,  PosContext ctx) 
      {
    	  PosParameter param = new PosParameter();
          int i = 0;
          
          param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));
  	      param.setWhereClauseParameter(i++, record.getAttribute("MBR_CD"));
  	      param.setWhereClauseParameter(i++, record.getAttribute("TMS"));
  	      param.setWhereClauseParameter(i++, record.getAttribute("DAY_ORD"));
  	      param.setWhereClauseParameter(i++, record.getAttribute("RACE_NO"));
  	      param.setWhereClauseParameter(i++, record.getAttribute("FILE_SEQ"));
     
  	      return this.getDao("boadao").update("tbec_appo_race_result_detail_dd003", param);
   
      }
      
}