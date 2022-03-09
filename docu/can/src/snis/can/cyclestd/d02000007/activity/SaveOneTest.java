/*================================================================================
 * 시스템		: 학사관리
 * 소스파일 이름	: snis.can.cyclestd.d02000008.activity.SaveOneTest.java
 * 파일설명		: 1차선발시험
 * 작성자		: 최문규
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-18
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d02000007.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;
/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 게시물을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 최문규
* @version 1.0
*/


public class SaveOneTest extends SnisActivity 
{
	public SaveOneTest() { }
	
	/**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	PosDataset ds1;
		PosDataset ds2;
		PosDataset ds3;
        int    nSize1 = 0;
        int    nSize2 = 0;
        int    nSize3 = 0;
        String     sDsName = "";
        
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
        sDsName = "dsPerioExam1";
        if ( ctx.get(sDsName) != null ) {
	        ds1   = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();
	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            PosRecord record = ds1.getRecord(i);
	            logger.logInfo("dsPerioExam1------------------->"+record);
	        }
        }
        
        sDsName = "dsPerioExam2";
        if ( ctx.get(sDsName) != null ) {
	        ds2   = (PosDataset)ctx.get(sDsName);
	        nSize2= ds2.getRecordCount();
	        for ( int i = 0; i < nSize2; i++ ) 
	        {
	            PosRecord record = ds2.getRecord(i);
	            logger.logInfo("dsPerioExam2------------------->"+record);
	        }
        }   
        
        sDsName = "dsRacerExamTot";
        if ( ctx.get(sDsName) != null ) {
	        ds3    = (PosDataset)ctx.get(sDsName);
	        nSize3 = ds3.getRecordCount();
	        for ( int i = 0; i < nSize3; i++ ) 
	        {
	            PosRecord record = ds3.getRecord(i);
	            logger.logInfo("dsRacerExamTot------------------->"+record);
	        }
        }         
       logger.logInfo("nSize1------------------->"+nSize1);
       logger.logInfo("nSize2------------------->"+nSize2);
       logger.logInfo("nSize3------------------->"+nSize3);
       
        if(nSize1 > 0){
        	saveStatePerio_Exam1(ctx); 
        }else if(nSize2 > 0){
        	saveStatePerio_Exam2(ctx); 
        }else if(nSize3 > 0){
//        	saveStateRacer_Exam_Tot(ctx); 
        }
        
        return PosBizControlConstants.SUCCESS;
    }
   
    
    /**
     * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
     * @param   ctx	PosContext 종목구분 저장소
     * @return  none
     * @throws  none
     */
     protected void saveStatePerio_Exam1(PosContext ctx) 
     {
		int nSaveCount   = 0; 
		PosDataset ds1;

		int nSize        = 0;
		int nTempCnt     = 0;
 	
        //종목구분 저장       
        ds1   = (PosDataset) ctx.get("dsPerioExam1");
        nSize = ds1.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds1.getRecord(i);
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {
             	 
                 nTempCnt = insertPerio_Exam1(record);              	 
            	 nSaveCount = nSaveCount + nTempCnt;
             }
        }
		Util.setSaveCount  (ctx, nSaveCount);
     }
     
     
    /**
     * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
     * @param   ctx	 PosContext	환산기준표 저장소
     * @return  none
     * @throws  none
     */
     
	protected void saveStatePerio_Exam2(PosContext ctx) 
	{
		int nSaveCount   = 0; 

		PosDataset ds2;

		int nSize        = 0;
		int nTempCnt     = 0;

		//종목 저장       
		ds2   = (PosDataset) ctx.get("dsPerioExam2");       
		nSize = ds2.getRecordCount();
		for ( int i = 0; i < nSize; i++ ) 
		{
			PosRecord record = ds2.getRecord(i);
			
			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
			|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
			{     
	            nTempCnt = insertPerio_Exam2(record); 
				nSaveCount = nSaveCount + nTempCnt;
			}
		}
		Util.setSaveCount  (ctx, nSaveCount);
	}
    
     
     /**
      * <p> 자전거실기 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertPerio_Exam1(PosRecord record) 
     {
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
         	 			 	
 			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_fst_ib003", param);
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

 			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_fst_ib003", param);
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

 			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_fst_ib003", param);
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
 			
 			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_fst_ib003", param);
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
 			
 			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_fst_ib003", param);
        }    
     
 		logger.logInfo("insertPerio_Exam1 end============================");
 		return dmlcount;
     }
         
     /**
      * <p>체력측정 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertPerio_Exam2(PosRecord record) 
     {
    	 logger.logInfo("insertPerio_Exam1 start============================");
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
			 			 	
 			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_fst_ib003", param);
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
			
 			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_fst_ib003", param);
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
 			
 			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_fst_ib003", param);
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
 			
 			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_fst_ib003", param);
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
 			
 			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_fst_ib003", param);
 			
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
			
			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_fst_ib003", param);
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
			 			
 			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_fst_ib003", param);
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
  			
  			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_fst_ib003", param);
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
  			
  			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_fst_ib003", param);
         }
        
        if(!sRecTireIdx.equals("")){
  			i = 0;
          	param = new PosParameter();	
          	
        	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
    		param.setValueParamter(i++, "410");
    		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("AVG_PWR_REC"))));	
			param.setValueParamter(i++, record.getAttribute("AVG_PWR_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
			param.setValueParamter(i++, "410");
			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("AVG_PWR_REC"))));	
			param.setValueParamter(i++, record.getAttribute("AVG_PWR_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
  			
  			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_fst_ib003", param);
         }

 		logger.logInfo("insertPerio_Exam1 end============================");
 		return dmlcount;
     }
}