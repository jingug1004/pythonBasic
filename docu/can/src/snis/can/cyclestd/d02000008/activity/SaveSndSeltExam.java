
/*================================================================================
 * 시스템			: 2차 선발시험    관리
 * 소스파일 이름	: snis.can.system.d02000009.activity.SaveSndSeltExam.java
 * 파일설명		: 2차 선발시험 관리
 * 작성자			: 노인수
 * 버전			: 1.0.0
 * 생성일자		: 2008-02-18
 * 최종수정일자	: 2008-05-26
 * 최종수정자		: 전홍조 
 * 최종수정내용	: 전체적으로 문제발견 전면개편
=================================================================================*/
package snis.can.cyclestd.d02000008.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 2차 선발시험 내역을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 노인수
* @version 1.0
*/
public class SaveSndSeltExam  extends SnisActivity
{    
	
    public SaveSndSeltExam()
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
   	
    	
        PosDataset ds;
        int        nSize        = 0;
        String     sDsName      = "";
		
        if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
	    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
	            return PosBizControlConstants.SUCCESS;
	     }
        sDsName = "dsSndSeltExam";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("dsSndSeltExam============>"+record);
	        }
        }
        sDsName = "dsSndSeltExam1";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("dsSndSeltExam1============>"+record);
	        }
        }
        sDsName = "dsSndSeltExam2";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("dsSndSeltExam1============>"+record);
	        }
        }
        sDsName = "dsSndSeltExam3";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("dsSndSeltExam1============>"+record);
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
    	int nSaveCount   = 0; 
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        sDsName = "dsSndSeltExam";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);         
         
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	    	  	|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) 
	            {
	            	nTempCnt = insertSaveSndSeltExam(record);
		            nSaveCount = nSaveCount + nTempCnt;
	            }
	         } // end for
        }     // end if
        
        sDsName = "dsSndSeltExam1";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	         for ( int i = 0; i < nSize; i++ ) {
	        	 
	            PosRecord record = ds.getRecord(i);         
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	    	    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ){
	            			nTempCnt = insertSaveSndSeltExam1(record);
	            			nSaveCount = nSaveCount + nTempCnt;
	    	     }
	        } // end for
        }     // end if
        
        sDsName = "dsSndSeltExam2";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);         
	                    
	                      
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	    	  	|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) 
	            {
	  			    nTempCnt = insertSaveSndSeltExam2(record);
	  				nSaveCount = nSaveCount + nTempCnt;
	    	    }
	        } // end for
        }     // end if
       
        sDsName = "dsSndSeltExam3";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);         
	                    
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	    	    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT){ 
	  				nTempCnt = insertSaveSndSeltExam3(record);
	  				nSaveCount = nSaveCount + nTempCnt;
	    	    }
	        } // end for
        }     // end if
        Util.setSaveCount  (ctx, nSaveCount     );
    }
    
    
    /**
     * <p> 2차선발시험 자전거입력  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertSaveSndSeltExam(PosRecord record) 
    {
   	 logger.logInfo("==========================  2차 선발시험 자전거 실기   입력   ============================");
                
   	 logger.logInfo("insertPerio_Exam1 start============================");
     PosParameter param = null;
     int i = 0;
     int dmlcount = 0;
		
     String sRec200 = Util.trim(String.valueOf(record.getAttribute("REC_200")));
     String sRec333 = Util.trim(String.valueOf(record.getAttribute("REC_333")));
     String sRec500 = Util.trim(String.valueOf(record.getAttribute("REC_500")));
     String sRec1000 = Util.trim(String.valueOf(record.getAttribute("REC_1000")));
     String sRec2000 = Util.trim(String.valueOf(record.getAttribute("REC_2000")));
     
     if(!sRec200.equals("")){
    	i = 0;
     	param = new PosParameter();
     	
     	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setValueParamter(i++, "301");
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("REC_200"))));	
		param.setValueParamter(i++, record.getAttribute("SCR_200"));
		param.setValueParamter(i++, SESSION_USER_ID);
		
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setValueParamter(i++, "301");
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("REC_200"))));	
		param.setValueParamter(i++, record.getAttribute("SCR_200"));
		param.setValueParamter(i++, SESSION_USER_ID);
     	 			 	
			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
     }
     
     if(!sRec333.equals("")){
		i = 0;  
     	param = new PosParameter();	
     	
     	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setValueParamter(i++, "302");
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("REC_333"))));	
		param.setValueParamter(i++, record.getAttribute("SCR_333"));
		param.setValueParamter(i++, SESSION_USER_ID);
		
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setValueParamter(i++, "302");
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("REC_333"))));	
		param.setValueParamter(i++, record.getAttribute("SCR_333"));
		param.setValueParamter(i++, SESSION_USER_ID);

			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
     }        
     
     if(!sRec500.equals("")){
		i = 0;      
     	param = new PosParameter();	
     	
     	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setValueParamter(i++, "303");
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("REC_500"))));	
		param.setValueParamter(i++, record.getAttribute("SCR_500"));
		param.setValueParamter(i++, SESSION_USER_ID);
		
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setValueParamter(i++, "303");
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("REC_500"))));	
		param.setValueParamter(i++, record.getAttribute("SCR_500"));
		param.setValueParamter(i++, SESSION_USER_ID);

			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
     }     
     
     if(!sRec1000.equals("")){
		i = 0;  
     	param = new PosParameter();			
	       
     	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setValueParamter(i++, "304");
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("REC_1000"))));	
		param.setValueParamter(i++, record.getAttribute("SCR_1000"));
		param.setValueParamter(i++, SESSION_USER_ID);
		
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setValueParamter(i++, "304");
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("REC_1000"))));	
		param.setValueParamter(i++, record.getAttribute("SCR_1000"));
		param.setValueParamter(i++, SESSION_USER_ID);
			
			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
     }   
     
     if(!sRec2000.equals("")){
		i = 0;
     	param = new PosParameter();	

     	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setValueParamter(i++, "305");
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("REC_2000"))));	
		param.setValueParamter(i++, record.getAttribute("SCR_2000"));
		param.setValueParamter(i++, SESSION_USER_ID);
		
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setValueParamter(i++, "305");
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("REC_2000"))));	
		param.setValueParamter(i++, record.getAttribute("SCR_2000"));
		param.setValueParamter(i++, SESSION_USER_ID);
			
			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
    }    
 
		logger.logInfo("insertPerio_Exam1 end============================");
		return dmlcount;
    }
    protected int insertSaveSndSeltExam1(PosRecord record) 
	   {
		 logger.logInfo("==========================  2차 선발시험 체력측정 실기   입력   ============================");
	     PosParameter param = null;
      int i = 0;
      int dmlcount = 0;

      String sRecGripLeft = Util.trim(String.valueOf(record.getAttribute("GRIP_LEFT_REC")));
      String sRecGripRight = Util.trim(String.valueOf(record.getAttribute("GRIP_RIGHT_REC")));
      String sRecLegLeft = Util.trim(String.valueOf(record.getAttribute("LEG_LEFT_REC")));
      String sRecLegRight = Util.trim(String.valueOf(record.getAttribute("LEG_RIGHT_REC")));
      String sRecLegLeft2 = Util.trim(String.valueOf(record.getAttribute("LEG_LEFT_REC2")));
      String sRecLegRight2 = Util.trim(String.valueOf(record.getAttribute("LEG_RIGHT_REC2")));
      String sRecBelly = Util.trim(String.valueOf(record.getAttribute("BELLY_REC")));
      String sRecMaxPwr = Util.trim(String.valueOf(record.getAttribute("MAX_PWR_REC")));
      String sRecAvgPwr    = Util.trim(String.valueOf(record.getAttribute("AVG_PWR_REC")));
      String sRecTireIdx   = Util.trim(String.valueOf(record.getAttribute("TIRE_IDX_REC")));
	         
	         if(!sRecGripLeft.equals("")){
	          	i = 0;
	         	param = new PosParameter();
	         	
	         	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	     		param.setValueParamter(i++, "401");
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("GRIP_LEFT_REC"))));	
	 			param.setValueParamter(i++, record.getAttribute("GRIP_LEFT_SCR"));
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	 			param.setValueParamter(i++, "401");
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("GRIP_LEFT_REC"))));	
	 			param.setValueParamter(i++, record.getAttribute("GRIP_LEFT_SCR"));
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			 			 	
	  			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
	          }
	          
	          if(!sRecGripRight.equals("")){
	  			i = 0;   
	  			
	          	param = new PosParameter();
	          	
	          	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	     		param.setValueParamter(i++, "402");
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("GRIP_RIGHT_REC"))));	
	 			param.setValueParamter(i++, record.getAttribute("GRIP_RIGHT_SCR"));
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	 			param.setValueParamter(i++, "402");
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("GRIP_RIGHT_REC"))));	
	 			param.setValueParamter(i++, record.getAttribute("GRIP_RIGHT_SCR"));
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	  			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
	          }        
	          if(!sRecLegLeft.equals("")){
	  			i = 0;  
	          	param = new PosParameter();	
	          	
	          	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	     		param.setValueParamter(i++, "403");
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("LEG_LEFT_REC"))));	
	 			param.setValueParamter(i++, record.getAttribute("LEG_LEFT_SCR"));
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	 			param.setValueParamter(i++, "403");
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("LEG_LEFT_REC"))));	
	 			param.setValueParamter(i++, record.getAttribute("LEG_LEFT_SCR"));
	 			param.setValueParamter(i++, SESSION_USER_ID);
	  			
	  			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
	          }     
	          if(!sRecLegRight.equals("")){
	  			i = 0;     
	          	param = new PosParameter();	
	          	
	          	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	     		param.setValueParamter(i++, "404");
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("LEG_RIGHT_REC"))));	
	 			param.setValueParamter(i++, record.getAttribute("LEG_RIGHT_SCR"));
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	 			param.setValueParamter(i++, "404");
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("LEG_RIGHT_REC"))));	
	 			param.setValueParamter(i++, record.getAttribute("LEG_RIGHT_SCR"));
	 			param.setValueParamter(i++, SESSION_USER_ID);
	  			
	  			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
	          }   
	          if(!sRecLegLeft2.equals("")){
	   			i = 0;  
	          	param = new PosParameter();	
	          	
	          	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	     		param.setValueParamter(i++, "405");
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("LEG_LEFT_REC2"))));	
	 			param.setValueParamter(i++, record.getAttribute("LEG_LEFT_SCR2"));
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	 			param.setValueParamter(i++, "405");
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("LEG_LEFT_REC2"))));	
	 			param.setValueParamter(i++, record.getAttribute("LEG_LEFT_SCR2"));
	 			param.setValueParamter(i++, SESSION_USER_ID);
	  			
	  			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
	          }
	          if(!sRecLegRight2.equals("")){
	   			
	         	i = 0;     
	          	param = new PosParameter();	
	          	
	          	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	     		param.setValueParamter(i++, "406");
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("LEG_RIGHT_REC2"))));	
	 			param.setValueParamter(i++, record.getAttribute("LEG_RIGHT_SCR2"));
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	 			param.setValueParamter(i++, "406");
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("LEG_RIGHT_REC2"))));	
	 			param.setValueParamter(i++, record.getAttribute("LEG_RIGHT_SCR2"));
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
	          }
	          if(!sRecBelly.equals("")){
	  			
	         	i = 0;
	          	param = new PosParameter();	
	          	
	         	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	     		param.setValueParamter(i++, "407");
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("BELLY_REC"))));	
	 			param.setValueParamter(i++, record.getAttribute("BELLY_SCR"));
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	 			param.setValueParamter(i++, "407");
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("BELLY_REC"))));	
	 			param.setValueParamter(i++, record.getAttribute("BELLY_SCR"));
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			 			
	  			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
	         }
	          
	         if(!sRecMaxPwr.equals("")){
	         	
	  			i = 0;
	          	param = new PosParameter();	
	          	
	         	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	     		param.setValueParamter(i++, "408");
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("MAX_PWR_REC"))));	
	 			param.setValueParamter(i++, record.getAttribute("MAX_PWR_SCR"));
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	 			param.setValueParamter(i++, "408");
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("MAX_PWR_REC"))));	
	 			param.setValueParamter(i++, record.getAttribute("MAX_PWR_SCR"));
	 			param.setValueParamter(i++, SESSION_USER_ID);
	   			
	   			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
	         }
	         
	         if(!sRecAvgPwr.equals("")){
	   			
	         	i = 0;
	           	param = new PosParameter();	
	           	
	         	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	     		param.setValueParamter(i++, "409");
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("AVG_PWR_REC"))));	
	 			param.setValueParamter(i++, record.getAttribute("AVG_PWR_SCR"));
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	 			param.setValueParamter(i++, "409");
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("AVG_PWR_REC"))));	
	 			param.setValueParamter(i++, record.getAttribute("AVG_PWR_SCR"));
	 			param.setValueParamter(i++, SESSION_USER_ID);
	   			
	   			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
	          }
	         
	         if(!sRecTireIdx.equals("")){
	   			i = 0;	
	           	param = new PosParameter();	
	           	
	         	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	     		param.setValueParamter(i++, "410");
	     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("TIRE_IDX_REC"))));	
	 			param.setValueParamter(i++, record.getAttribute("TIRE_IDX_SCR"));
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	 			param.setValueParamter(i++, "410");
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("TIRE_IDX_REC"))));	
	 			param.setValueParamter(i++, record.getAttribute("TIRE_IDX_SCR"));
	 			param.setValueParamter(i++, SESSION_USER_ID);
	   			
	   			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
	          }
		   return dmlcount;
	   }

    protected int insertSaveSndSeltExam2(PosRecord record) 
    {
   	    logger.logInfo("==========================  2차 선발시험 필기   입력   ============================");
                
   	    PosParameter param = null;
        int i = 0;
        int dmlcount = 0;

    	param = new PosParameter();			
		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setValueParamter(i++, "101");
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("TOT_SCR"))));	
		param.setValueParamter(i++, record.getAttribute("TOT_SCR"));
		param.setValueParamter(i++, SESSION_USER_ID);
		
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setValueParamter(i++, "101");
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("TOT_SCR"))));	
		param.setValueParamter(i++, record.getAttribute("TOT_SCR"));
		param.setValueParamter(i++, SESSION_USER_ID);
			 	
		dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
        return dmlcount;
    }
    protected int insertSaveSndSeltExam3(PosRecord record) 
    {
   	    logger.logInfo("==========================  2차 선발시험면접   입력   ============================");
                
        //PosParameter param = new PosParameter();  
   	    PosParameter param = null;
        int i = 0;
        int dmlcount = 0;
			
        String a1_scr = Util.trim(String.valueOf(record.getAttribute("A1_SCR")));
        String a2_scr = Util.trim(String.valueOf(record.getAttribute("A2_SCR")));
        String a3_scr = Util.trim(String.valueOf(record.getAttribute("A3_SCR")));
        String a4_scr = Util.trim(String.valueOf(record.getAttribute("A4_SCR")));
        String a5_scr = Util.trim(String.valueOf(record.getAttribute("A5_SCR")));
        
        String b1_scr = Util.trim(String.valueOf(record.getAttribute("B1_SCR")));
        String b2_scr = Util.trim(String.valueOf(record.getAttribute("B2_SCR")));
        String b3_scr = Util.trim(String.valueOf(record.getAttribute("B3_SCR")));
        String b4_scr = Util.trim(String.valueOf(record.getAttribute("B4_SCR")));
        String b5_scr = Util.trim(String.valueOf(record.getAttribute("B5_SCR")));
        
        String c1_scr = Util.trim(String.valueOf(record.getAttribute("C1_SCR")));
        String c2_scr = Util.trim(String.valueOf(record.getAttribute("C2_SCR")));
        String c3_scr = Util.trim(String.valueOf(record.getAttribute("C3_SCR")));
        String c4_scr = Util.trim(String.valueOf(record.getAttribute("C4_SCR")));
        String c5_scr = Util.trim(String.valueOf(record.getAttribute("C5_SCR")));
        
        String d1_scr = Util.trim(String.valueOf(record.getAttribute("D1_SCR")));
        String d2_scr = Util.trim(String.valueOf(record.getAttribute("D2_SCR")));
        String d3_scr = Util.trim(String.valueOf(record.getAttribute("D3_SCR")));
        String d4_scr = Util.trim(String.valueOf(record.getAttribute("D4_SCR")));
        String d5_scr = Util.trim(String.valueOf(record.getAttribute("D5_SCR")));
        
        String e1_scr = Util.trim(String.valueOf(record.getAttribute("E1_SCR")));
        String e2_scr = Util.trim(String.valueOf(record.getAttribute("E2_SCR")));
        String e3_scr = Util.trim(String.valueOf(record.getAttribute("E3_SCR")));
        String e4_scr = Util.trim(String.valueOf(record.getAttribute("E4_SCR")));
        String e5_scr = Util.trim(String.valueOf(record.getAttribute("E5_SCR")));
        
        
        if(!a1_scr.equals("")){
       	i = 0;
        param = new PosParameter();
        	
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
   		param.setValueParamter(i++, "611");
   		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
   		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("A1_SCR"))));	
   		param.setValueParamter(i++, record.getAttribute("A1_SCR"));
   		param.setValueParamter(i++, SESSION_USER_ID);
   		
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
   		param.setValueParamter(i++, "611");
   		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
   		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("A1_SCR"))));	
   		param.setValueParamter(i++, record.getAttribute("A1_SCR"));
   		param.setValueParamter(i++, SESSION_USER_ID);
        	 			 	
   			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
        }
        
        if(!a2_scr.equals("")){
   		i = 0;  
        param = new PosParameter();	
        	
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
   		param.setValueParamter(i++, "612");
   		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
   		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("A2_SCR"))));	
   		param.setValueParamter(i++, record.getAttribute("A2_SCR"));
   		param.setValueParamter(i++, SESSION_USER_ID);
   		
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
   		param.setValueParamter(i++, "612");
   		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
   		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("A2_SCR"))));	
   		param.setValueParamter(i++, record.getAttribute("A2_SCR"));
   		param.setValueParamter(i++, SESSION_USER_ID);

   			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
        }        
        
        if(!a3_scr.equals("")){
   		i = 0;      
        param = new PosParameter();	
        	
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
   		param.setValueParamter(i++, "613");
   		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
   		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("A3_SCR"))));	
   		param.setValueParamter(i++, record.getAttribute("A3_SCR"));
   		param.setValueParamter(i++, SESSION_USER_ID);
   		
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
   		param.setValueParamter(i++, "613");
   		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
   		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("A3_SCR"))));	
   		param.setValueParamter(i++, record.getAttribute("A3_SCR"));
   		param.setValueParamter(i++, SESSION_USER_ID);

   			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
        }     
        
        if(!a4_scr.equals("")){
   		i = 0;  
        param = new PosParameter();			
   	       
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
   		param.setValueParamter(i++, "614");
   		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
   		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("A4_SCR"))));	
   		param.setValueParamter(i++, record.getAttribute("A4_SCR"));
   		param.setValueParamter(i++, SESSION_USER_ID);
   		
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
   		param.setValueParamter(i++, "614");
   		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
   		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("A4_SCR"))));	
   		param.setValueParamter(i++, record.getAttribute("A4_SCR"));
   		param.setValueParamter(i++, SESSION_USER_ID);
   			
   			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
        }   
        
        if(!a5_scr.equals("")){
   		i = 0;
        param = new PosParameter();	

        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
   		param.setValueParamter(i++, "615");
   		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
   		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("A5_SCR"))));	
   		param.setValueParamter(i++, record.getAttribute("A5_SCR"));
   		param.setValueParamter(i++, SESSION_USER_ID);
   		
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
   		param.setValueParamter(i++, "615");
   		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
   		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("A5_SCR"))));	
   		param.setValueParamter(i++, record.getAttribute("A5_SCR"));
   		param.setValueParamter(i++, SESSION_USER_ID);
   			
   			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
       }    
        if(!b1_scr.equals("")){
           	i = 0;
            param = new PosParameter();
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "621");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("B1_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("B1_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "621");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("B1_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("B1_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
            	 			 	
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }
            
            if(!b2_scr.equals("")){
       		i = 0;  
            param = new PosParameter();	
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "622");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("B2_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("B2_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "622");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("B2_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("B2_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);

       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }        
            
            if(!b3_scr.equals("")){
       		i = 0;      
            param = new PosParameter();	
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "623");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("B3_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("B3_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "623");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("B3_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("B3_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);

       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }     
            
            if(!b4_scr.equals("")){
       		i = 0;  
            param = new PosParameter();			
       	       
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "624");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("B4_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("B4_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "624");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("B4_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("B4_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       			
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }   
            
            if(!b5_scr.equals("")){
       		i = 0;
            param = new PosParameter();	

            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "625");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("B5_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("B5_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "625");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("B5_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("B5_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       			
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
           } 
            if(!c1_scr.equals("")){
           	i = 0;
            param = new PosParameter();
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "631");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("C1_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("C1_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "631");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("C1_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("C1_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
            	 			 	
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }
            
            if(!c2_scr.equals("")){
       		i = 0;  
            param = new PosParameter();	
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "632");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("C2_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("C2_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "632");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("C2_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("C2_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);

       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }        
            
            if(!c3_scr.equals("")){
       		i = 0;      
            param = new PosParameter();	
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "633");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("C3_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("C3_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "633");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("C3_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("C3_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);

       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }     
            
            if(!c4_scr.equals("")){
       		i = 0;  
            param = new PosParameter();			
       	       
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "634");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("C4_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("C4_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "634");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("C4_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("C4_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       			
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }   
            
            if(!c5_scr.equals("")){
       		i = 0;
            param = new PosParameter();	

            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "635");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("C5_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("C5_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "635");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("C5_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("C5_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       			
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
           }
            if(!d1_scr.equals("")){
           	i = 0;
            param = new PosParameter();
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "641");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("D1_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("D1_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "641");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("D1_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("D1_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
            	 			 	
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }
            
            if(!d2_scr.equals("")){
       		i = 0;  
            param = new PosParameter();	
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "642");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("D2_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("D2_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "642");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("D2_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("D2_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);

       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }        
            
            if(!d3_scr.equals("")){
       		i = 0;      
            param = new PosParameter();	
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "643");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("D3_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("D3_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "643");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("D3_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("D3_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);

       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }     
            
            if(!d4_scr.equals("")){
       		i = 0;  
            param = new PosParameter();			
       	       
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "644");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("D4_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("D4_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "644");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("D4_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("D4_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       			
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }   
            
            if(!d5_scr.equals("")){
       		i = 0;
            param = new PosParameter();	

            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "645");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("D5_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("D5_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "645");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("D5_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("D5_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       			
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
           } 
            if(!e1_scr.equals("")){
           	i = 0;
            param = new PosParameter();
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "651");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("E1_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("E1_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "651");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("E1_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("E1_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
            	 			 	
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }
            
            if(!e2_scr.equals("")){
       		i = 0;  
            param = new PosParameter();	
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "652");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("E2_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("E2_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "652");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("E2_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("E2_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);

       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }        
            
            if(!e3_scr.equals("")){
       		i = 0;      
            param = new PosParameter();	
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "653");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("E3_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("E3_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "653");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("E3_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("E3_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);

       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }     
            
            if(!e4_scr.equals("")){
       		i = 0;  
            param = new PosParameter();			
       	       
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "654");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("E4_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("E4_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "654");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("E4_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("E4_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       			
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }   
            
            if(!e5_scr.equals("")){
       		i = 0;
            param = new PosParameter();	

            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "655");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("E5_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("E5_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "655");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("E5_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("E5_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       			
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
           } 
   		logger.logInfo("insertPerio_Exam3 end============================");
   		return dmlcount;
       }
}

