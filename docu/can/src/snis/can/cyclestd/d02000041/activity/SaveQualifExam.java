/*================================================================================
 * 시스템		: 학사관리
 * 소스파일 이름	: snis.can.cyclestd.d02000008.activity.SaveOneTest.java
 * 파일설명		: 선수자격시험
 * 작성자		: 최문규
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-18
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d02000041.activity;

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


public class SaveQualifExam extends SnisActivity 
{
	public SaveQualifExam() { }
	
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
    	
    	PosDataset ds1;
		PosDataset ds2;
		PosDataset ds3;
		PosDataset ds4;
        int    nSize1 = 0;
        int    nSize2 = 0;
        int    nSize3 = 0;
        int    nSize4 = 0;
        String     sDsName = "";

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
        
        sDsName = "dsPerioExam3";
        if ( ctx.get(sDsName) != null ) {
	        ds3   = (PosDataset)ctx.get(sDsName);
	        nSize3= ds3.getRecordCount();
	        for ( int i = 0; i < nSize3; i++ ) 
	        {
	            PosRecord record = ds3.getRecord(i);
	            logger.logInfo("dsPerioExam3------------------->"+record);
	        }
        }
        
        sDsName = "dsRacerExamTot";
        if ( ctx.get(sDsName) != null ) {
	        ds4    = (PosDataset)ctx.get(sDsName);
	        nSize4 = ds4.getRecordCount();
	        for ( int i = 0; i < nSize4; i++ ) 
	        {
	            PosRecord record = ds4.getRecord(i);
	            logger.logInfo("dsRacerExamTot------------------->"+record);
	        }
        }         
       logger.logInfo("nSize1------------------->"+nSize1);
       logger.logInfo("nSize2------------------->"+nSize2);
       logger.logInfo("nSize3------------------->"+nSize3);
       logger.logInfo("nSize4------------------->"+nSize4);
       
        if(nSize1 > 0){
        	saveStatePerio_Exam1(ctx); 
        }else if(nSize2 > 0){
        	saveStatePerio_Exam2(ctx); 
        }else if(nSize3 > 0){
        	saveStateRacer_Exam3(ctx); 
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
		int nDeleteCount = 0; 

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
             	 if ( (nTempCnt = updatePerio_Exam1(record)) == 0 ) {
                  	nTempCnt = insertPerio_Exam1(record);
                 }                	 
            	 nSaveCount = nSaveCount + nTempCnt;
             }
             
             if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
             {
                 // delete
             	nDeleteCount = nDeleteCount + deletePerio_Exam1(record);
             }
        }
		Util.setSaveCount  (ctx, nSaveCount     );
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
		int nDeleteCount = 0; 

		PosDataset ds2;

		int nSize        = 0;
		int nTempCnt     = 0;

		//종목 저장       
		ds2   = (PosDataset) ctx.get("dsPerioExam2");
		logger.logInfo("ds2------------------->"+ds2);        
		nSize = ds2.getRecordCount();
		for ( int i = 0; i < nSize; i++ ) 
		{
			PosRecord record = ds2.getRecord(i);

			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
		        || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
		    {
		        if ( (nTempCnt = updatePerio_Exam2(record)) == 0 ) {
		            nTempCnt = insertPerio_Exam2(record);
		        }                	 
		        nSaveCount = nSaveCount + nTempCnt;
		    }         
		             

			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
			{
				// delete
				nDeleteCount = nDeleteCount + deletePerio_Exam2(record);
			}
		}
		Util.setSaveCount  (ctx, nSaveCount     );
		Util.setDeleteCount(ctx, nDeleteCount   );
	}
	
	/**
     * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
     * @param   ctx	 PosContext	환산기준표 저장소
     * @return  none
     * @throws  none
     */
     
	protected void saveStateRacer_Exam3(PosContext ctx) 
	{
		int nSaveCount   = 0; 
		int nDeleteCount = 0; 

		PosDataset ds3;

		int nSize        = 0;
		int nTempCnt     = 0;

		//종목 저장       
		ds3   = (PosDataset) ctx.get("dsPerioExam3");
		logger.logInfo("ds3------------------->"+ds3);        
		nSize = ds3.getRecordCount();
		for ( int i = 0; i < nSize; i++ ) 
		{
			PosRecord record = ds3.getRecord(i);

			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
		        || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
		    {
		        if ( (nTempCnt = updatePerio_Exam3(record)) == 0 ) {
		            nTempCnt = insertPerio_Exam3(record);
		        }                	 
		        nSaveCount = nSaveCount + nTempCnt;
		    }         
		             

			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
			{
				// delete
				nDeleteCount = nDeleteCount + deletePerio_Exam3(record);
			}
		}
		Util.setSaveCount  (ctx, nSaveCount     );
		Util.setDeleteCount(ctx, nDeleteCount   );
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
         int recCount = 0;
 		
         String sRec200    = Util.trim(String.valueOf(record.getAttribute("REC_200")));
         String sRec333    = Util.trim(String.valueOf(record.getAttribute("REC_333")));
         String sRec500    = Util.trim(String.valueOf(record.getAttribute("REC_500")));
         String sRec1000   = Util.trim(String.valueOf(record.getAttribute("REC_1000")));
         String sRec2000   = Util.trim(String.valueOf(record.getAttribute("REC_2000")));
         logger.logInfo("REC_200 : " + sRec200);
         logger.logInfo("REC_500 : " + sRec500);
         logger.logInfo("REC_2000 : " + sRec2000);
         if(!sRec200.equals("")){
        	i = 0;
        	recCount ++;
         	
        	param = new PosParameter();
 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setValueParamter(i++, "001");
 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("REC_200"))));	
 			param.setValueParamter(i++, record.getAttribute("SCR_200"));
 			param.setValueParamter(i++, SESSION_USER_ID);
 			 	
 			dmlcount += this.getDao("candao").insert("tbdb_racer_exam_cyc_ib001", param);
         }
                  
         if(!sRec333.equals("")){
 			i = 0;
 			recCount ++;
 			
         	param = new PosParameter();			
 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setValueParamter(i++, "002");
 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("REC_333"))));	
 			param.setValueParamter(i++, record.getAttribute("SCR_333"));
 			param.setValueParamter(i++, SESSION_USER_ID);
 		
 			dmlcount += this.getDao("candao").insert("tbdb_racer_exam_cyc_ib001", param);
         }        
         
         if(!sRec500.equals("")){
  			i = 0;
  			recCount ++;
  			
          	param = new PosParameter();			
  	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
  			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
  			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
  			param.setValueParamter(i++, "003");
  			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
  			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
  			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("REC_500"))));	
  			param.setValueParamter(i++, record.getAttribute("SCR_500"));
  			param.setValueParamter(i++, SESSION_USER_ID);
  		logger.logInfo("500 RACER_PERIO_NO" + Util.trim(record.getAttribute("RACER_PERIO_NO")));
  		logger.logInfo("500 EXAM_CD" + Util.trim(record.getAttribute("EXAM_CD")));
  		logger.logInfo("500 ITEM_GBN_CD" + Util.trim(record.getAttribute("ITEM_GBN_CD")));
  		logger.logInfo("500 003" + "003");
  		logger.logInfo("500 CAND_NO" + Util.trim(record.getAttribute("CAND_NO")));
  		logger.logInfo("500 DT" + Util.trim(record.getAttribute("DT")));
  		logger.logInfo("500 REC_500" + Util.trim(record.getAttribute("REC_500")));
  		logger.logInfo("500 SCR_500" + record.getAttribute("SCR_500"));
  		logger.logInfo("500 SESSION_USER_ID" + SESSION_USER_ID);
  			dmlcount += this.getDao("candao").insert("tbdb_racer_exam_cyc_ib001", param);
          } 
         
         if(!sRec1000.equals("")){
 			i = 0;  
 			recCount ++;
 			
         	param = new PosParameter();			
 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setValueParamter(i++, "004");
 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("REC_1000"))));	
 			param.setValueParamter(i++, record.getAttribute("SCR_1000"));
 			param.setValueParamter(i++, SESSION_USER_ID);
 			
 			dmlcount += this.getDao("candao").insert("tbdb_racer_exam_cyc_ib001", param);
         }
         
         if(!sRec2000.equals("")){
  			i = 0;  
  			recCount ++;
  			
          	param = new PosParameter();			
  	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
  			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
  			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
  			param.setValueParamter(i++, "005");
  			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
  			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
  			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("REC_2000"))));	
  			param.setValueParamter(i++, record.getAttribute("SCR_2000"));
  			param.setValueParamter(i++, SESSION_USER_ID);
  			
  			dmlcount += this.getDao("candao").insert("tbdb_racer_exam_cyc_ib001", param);
          }
         
         if(recCount > 0) {
        	 
        	 if(sRec200.equals("")){
             	i = 0;
             	              	
             	param = new PosParameter();
      	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
      			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
      			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
      			param.setValueParamter(i++, "001");
      			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
      			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
      			param.setValueParamter(i++, "000000");	
      			param.setValueParamter(i++, record.getAttribute("SCR_200"));
      			param.setValueParamter(i++, SESSION_USER_ID);
      			 	
      			dmlcount += this.getDao("candao").insert("tbdb_racer_exam_cyc_ib001", param);
              }
                       
              if(sRec333.equals("")){
      			i = 0;
      			      			
              	param = new PosParameter();			
      	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
      			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
      			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
      			param.setValueParamter(i++, "002");
      			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
      			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
      			param.setValueParamter(i++, "000000");	
      			param.setValueParamter(i++, record.getAttribute("SCR_333"));
      			param.setValueParamter(i++, SESSION_USER_ID);
      		
      			dmlcount += this.getDao("candao").insert("tbdb_racer_exam_cyc_ib001", param);
              }        
              
              if(sRec500.equals("")){
        			i = 0;
        			      			
                	param = new PosParameter();			
        	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
        			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
        			param.setValueParamter(i++, "003");
        			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
        			param.setValueParamter(i++, "000000");	
        			param.setValueParamter(i++, record.getAttribute("SCR_500"));
        			param.setValueParamter(i++, SESSION_USER_ID);
        		
        			dmlcount += this.getDao("candao").insert("tbdb_racer_exam_cyc_ib001", param);
                }   
              
              if(sRec1000.equals("")){
      			i = 0;  
      			recCount ++;
      			
              	param = new PosParameter();			
      	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
      			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
      			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
      			param.setValueParamter(i++, "004");
      			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
      			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
      			param.setValueParamter(i++, "000000");	
      			param.setValueParamter(i++, record.getAttribute("SCR_1000"));
      			param.setValueParamter(i++, SESSION_USER_ID);
      			
      			dmlcount += this.getDao("candao").insert("tbdb_racer_exam_cyc_ib001", param);
              }
              
              if(sRec2000.equals("")){
        			i = 0;  
        			recCount ++;
        			
                	param = new PosParameter();			
        	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
        			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
        			param.setValueParamter(i++, "005");
        			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
        			param.setValueParamter(i++, "000000");	
        			param.setValueParamter(i++, record.getAttribute("SCR_2000"));
        			param.setValueParamter(i++, SESSION_USER_ID);
        			
        			dmlcount += this.getDao("candao").insert("tbdb_racer_exam_cyc_ib001", param);
             }
         }
                       
 		logger.logInfo("insertPerio_Exam1 end============================");
 		return dmlcount;
     }
     
     /**
      * <p> 자전거실기 수정  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updatePerio_Exam1(PosRecord record) 
     {
    	 logger.logInfo("updatePerio_Exam1 start============================");
 		PosParameter param = null;  					
 		int i = 0;
 		int dmlcount = 0;
 		
         String sRec200    = Util.trim(String.valueOf(record.getAttribute("REC_200")));
         String sRec333    = Util.trim(String.valueOf(record.getAttribute("REC_333")));
         String sRec500    = Util.trim(String.valueOf(record.getAttribute("REC_500")));
         String sRec1000   = Util.trim(String.valueOf(record.getAttribute("REC_1000")));
         String sRec2000   = Util.trim(String.valueOf(record.getAttribute("REC_2000")));
         logger.logInfo("REC_200 update : " + sRec200);
         logger.logInfo("REC_500 update : " + sRec500);
         logger.logInfo("REC_2000 update : " + sRec2000);  
         if(!sRec200.equals("")){
        	i = 0; 
         	param = new PosParameter();   
 	         			
 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("REC_200"))));	
 			param.setValueParamter(i++, record.getAttribute("SCR_200")); 
 			param.setValueParamter(i++, SESSION_USER_ID);
 			i = 0;    
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setWhereClauseParameter(i++, "001");
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			
 			dmlcount += this.getDao("candao").update("tbdb_racer_exam_cyc_ub001", param);
         }
         
         if(!sRec333.equals("")){
 			i = 0;
         	param = new PosParameter();
 	       
 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("REC_333"))));	
 			param.setValueParamter(i++, record.getAttribute("SCR_333")); 
 			param.setValueParamter(i++, SESSION_USER_ID);
 			i = 0;    
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setWhereClauseParameter(i++, "002");
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			dmlcount += this.getDao("candao").update("tbdb_racer_exam_cyc_ub001", param);
         }

         if(!sRec500.equals("")){
  			i = 0;
          	param = new PosParameter();
            
  			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("REC_500"))));	
  			param.setValueParamter(i++, record.getAttribute("SCR_500")); 
  			param.setValueParamter(i++, SESSION_USER_ID);
  			i = 0;    
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
  			param.setWhereClauseParameter(i++, "003");
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
  			dmlcount += this.getDao("candao").update("tbdb_racer_exam_cyc_ub001", param);
          }
         
         if(!sRec1000.equals("")){
 			i = 0;
         	param = new PosParameter();
 	        
 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("REC_1000"))));	
 			param.setValueParamter(i++, record.getAttribute("SCR_1000"));
 			param.setValueParamter(i++, SESSION_USER_ID);
 			i = 0;    
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setWhereClauseParameter(i++, "004");
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			dmlcount += this.getDao("candao").update("tbdb_racer_exam_cyc_ub001", param);
         }        
       
         if(!sRec2000.equals("")){
  			i = 0;
          	param = new PosParameter();
  	        
  			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("REC_2000"))));	
  			param.setValueParamter(i++, record.getAttribute("SCR_2000"));
  			param.setValueParamter(i++, SESSION_USER_ID);
  			i = 0;    
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
  			param.setWhereClauseParameter(i++, "005");
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
  			dmlcount += this.getDao("candao").update("tbdb_racer_exam_cyc_ub001", param);
        }
         
 		logger.logInfo("updatePerio_Exam1 end============================");
 		return dmlcount;
     }
     
     /**
      * <p> 자전거실기 삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
     protected int deletePerio_Exam1(PosRecord record) 
     {
		logger.logInfo("deletePerio_Exam1 start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));

		int dmlcount = this.getDao("candao").delete("tbdb_racer_exam_cyc_db001", param);

		logger.logInfo("deletePerio_Exam1 end============================");
		return dmlcount;
     }
     
     /**
      * <p>필기 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertPerio_Exam2(PosRecord record) 
     {
    	 logger.logInfo("insertPerio_Exam2 start============================");
         PosParameter param = null;
         int i = 0;
         int dmlcount = 0;
                  
         String cycRawScr    = Util.trim(String.valueOf(record.getAttribute("CYC_LAW_SCR")));
         String genSenceScr  = Util.trim(String.valueOf(record.getAttribute("GEN_SENCE_SCR")));
         String spoMedScr    = Util.trim(String.valueOf(record.getAttribute("SPO_MED_SCR")));
         String cycTheoryScr = Util.trim(String.valueOf(record.getAttribute("CYC_THEORY_SCR")));
         String assemScr     = Util.trim(String.valueOf(record.getAttribute("ASSEM_SCR")));
                           
         if(!cycRawScr.equals("")){
        	i = 0;
        	
         	param = new PosParameter();
         	
 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setValueParamter(i++, "101");
 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
 			param.setValueParamter(i++,"0");
 			param.setValueParamter(i++, record.getAttribute("CYC_LAW_SCR"));
 			param.setValueParamter(i++, SESSION_USER_ID);
 			 	
 			dmlcount += this.getDao("candao").insert("tbdb_racer_exam_cyc_ib001", param);
         }
         
         if(!genSenceScr.equals("")){
 			i = 0; 
 			
         	param = new PosParameter();	
         	
 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setValueParamter(i++, "105");
 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
 			param.setValueParamter(i++,"0");
 			param.setValueParamter(i++, record.getAttribute("GEN_SENCE_SCR"));
 			param.setValueParamter(i++, SESSION_USER_ID);
 		
 			dmlcount += this.getDao("candao").insert("tbdb_racer_exam_cyc_ib001", param);
         }        
         
         if(!spoMedScr.equals("")){
 			i = 0;     
 			
         	param = new PosParameter();	
         	
 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setValueParamter(i++, "106");
 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
 			param.setValueParamter(i++,"0");
 			param.setValueParamter(i++, record.getAttribute("SPO_MED_SCR"));
 			param.setValueParamter(i++, SESSION_USER_ID);
 			
 			dmlcount += this.getDao("candao").insert("tbdb_racer_exam_cyc_ib001", param);
         }     
         
         if(!cycTheoryScr.equals("")){
 			i = 0; 
 			
         	param = new PosParameter();	
         	
 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setValueParamter(i++, "107");
 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
 			param.setValueParamter(i++,"0");
 			param.setValueParamter(i++, record.getAttribute("CYC_THEORY_SCR"));
 			param.setValueParamter(i++, SESSION_USER_ID);
 			
 			dmlcount += this.getDao("candao").insert("tbdb_racer_exam_cyc_ib001", param);
         }   
         
         if(!assemScr.equals("")){
 			i = 0;
 			
         	param = new PosParameter();	
         	
 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setValueParamter(i++, "108");
 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
 			param.setValueParamter(i++,"0");
 			param.setValueParamter(i++, record.getAttribute("ASSEM_SCR"));
 			param.setValueParamter(i++, SESSION_USER_ID);
 			
 			dmlcount += this.getDao("candao").insert("tbdb_racer_exam_cyc_ib001", param);
        }
                        
 		logger.logInfo("insertPerio_Exam2 end============================");
 		return dmlcount;
     }
     
     /**
      * <p> 필기 수정  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updatePerio_Exam2(PosRecord record) 
     {
        logger.logInfo("updatePerio_Exam2 start============================");
 		PosParameter param = null;  					
 		int i = 0;
 		int dmlcount = 0;
 		
 		String cycRawScr    = Util.trim(String.valueOf(record.getAttribute("CYC_LAW_SCR")));
        String genSenceScr  = Util.trim(String.valueOf(record.getAttribute("GEN_SENCE_SCR")));
        String spoMedScr    = Util.trim(String.valueOf(record.getAttribute("SPO_MED_SCR")));
        String cycTheoryScr = Util.trim(String.valueOf(record.getAttribute("CYC_THEORY_SCR")));
        String assemScr     = Util.trim(String.valueOf(record.getAttribute("ASSEM_SCR")));
             
        if(!cycRawScr.equals("")){
                      
         	param = new PosParameter();   
 	                 		
         	param.setValueParamter(i++,"0");
         	param.setValueParamter(i++, cycRawScr);
         	param.setValueParamter(i++, SESSION_USER_ID);
 			i = 0;  
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setWhereClauseParameter(i++, "101");
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			
 			dmlcount += this.getDao("candao").update("tbdb_racer_exam_cyc_ub001", param);
         }
                 
         if(!genSenceScr.equals("")){
 			i = 0;
         	param = new PosParameter();
 	        
         	param.setValueParamter(i++,"0");
 			param.setValueParamter(i++, genSenceScr);
 			param.setValueParamter(i++, SESSION_USER_ID);
 			i = 0;    
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setWhereClauseParameter(i++, "105");
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			dmlcount += this.getDao("candao").update("tbdb_racer_exam_cyc_ub001", param);
         }

         if(!spoMedScr.equals("")){
 			i = 0;        	
         	param = new PosParameter();			
 	        
         	param.setValueParamter(i++,"0");
 			param.setValueParamter(i++, spoMedScr);
 			param.setValueParamter(i++, SESSION_USER_ID);
 			i = 0;    
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setWhereClauseParameter(i++, "106");
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			dmlcount += this.getDao("candao").update("tbdb_racer_exam_cyc_ub001", param);
         }
     
         if(!cycTheoryScr.equals("")){
 			i = 0;
         	param = new PosParameter();
 	        
         	param.setValueParamter(i++,"0");
 			param.setValueParamter(i++, cycTheoryScr);
 			param.setValueParamter(i++, SESSION_USER_ID);
 			i = 0;    
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setWhereClauseParameter(i++, "107");
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			dmlcount += this.getDao("candao").update("tbdb_racer_exam_cyc_ub001", param);
         }        
            
         if(!assemScr.equals("")){
 			i = 0;
         	param = new PosParameter();
 	        
         	param.setValueParamter(i++,"0");
 			param.setValueParamter(i++, assemScr);
 			param.setValueParamter(i++, SESSION_USER_ID);
 			i = 0;    
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setWhereClauseParameter(i++, "108");
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			dmlcount += this.getDao("candao").update("tbdb_racer_exam_cyc_ub001", param);
         }
                      
 		 logger.logInfo("updatePerio_Exam2 end============================");
 	     return dmlcount;
     }
     
     /**
      * <p> 필기 삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
     protected int deletePerio_Exam2(PosRecord record) 
     {
		logger.logInfo("deletePerio_Exam2 start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));

		int dmlcount = this.getDao("candao").delete("tbdb_racer_exam_cyc_db001", param);

		logger.logInfo("deletePerio_Exam2 end============================");
		return dmlcount;
     }
     
     /**
      * <p>면접 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertPerio_Exam3(PosRecord record) 
     {
         logger.logInfo("insertPerio_Exam3 start============================");
         PosParameter param = null;
         int i = 0;
         int dmlcount = 0;
 		 
         String sPassYn = Util.trim(record.getAttribute("PASS_YN_01"));
                 
         param = new PosParameter();
 	     param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 		 param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 		 param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 		 param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_CD")));
 		 param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
 		 param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
 		 
 		 // 합격일때 REC, SCR 처리
 		if(sPassYn.equals("001")) {
 			 param.setValueParamter(i++, "1");
 			 param.setValueParamter(i++, "100");
 		}	// 불합격일때 REC, SCR 처리
 		else if(sPassYn.equals("002")) {
 			 param.setValueParamter(i++, "2"); 			 
 			 param.setValueParamter(i++, "30");
 		}
 		else {
 			 param.setValueParamter(i++, "0"); 			 
 			 param.setValueParamter(i++, record.getAttribute("SCR"));
 		}
 		  		 
 		 param.setValueParamter(i++, SESSION_USER_ID);
 			 	
 		 dmlcount += this.getDao("candao").insert("tbdb_racer_exam_cyc_ib001", param);
                        
 		 logger.logInfo("insertPerio_Exam3 end============================");
 		 return dmlcount;
     }
     
     /**
      * <p> 면접 수정  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updatePerio_Exam3(PosRecord record) 
     {
        logger.logInfo("updatePerio_Exam3 start============================");
 		PosParameter param = null;  					
 		int i = 0;
 		int dmlcount = 0;
 		
 		String sPassYn = Util.trim(record.getAttribute("PASS_YN_01"));                     
 		logger.logInfo("PASS_YN_01 ========================++++++++++========>"+sPassYn);
 		
        param = new PosParameter();   
 	                 		
        // 합격일때 REC, SCR 처리
		if(sPassYn.equals("001")) {
			 param.setValueParamter(i++, "1");
			 param.setValueParamter(i++, "100");
		}	// 불합격일때 REC, SCR 처리
		else if(sPassYn.equals("002")) {
			 param.setValueParamter(i++, "2"); 			 
			 param.setValueParamter(i++, "30");
		}
		else {
			 param.setValueParamter(i++, "0"); 			 
			 param.setValueParamter(i++, record.getAttribute("SCR"));
		}
		
        param.setValueParamter(i++, SESSION_USER_ID);
 		i = 0;  
  		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_CD")));
 		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
 		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			
 		dmlcount += this.getDao("candao").update("tbdb_racer_exam_cyc_ub001", param);
                   
 		logger.logInfo("updatePerio_Exam3 end============================");
 	    return dmlcount;
     }
     
     /**
      * <p> 면접 삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
     protected int deletePerio_Exam3(PosRecord record) 
     {
		logger.logInfo("deletePerio_Exam3 start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));

		int dmlcount = this.getDao("candao").delete("tbdb_racer_exam_cyc_db001", param);

		logger.logInfo("deletePerio_Exam3 end============================");
		return dmlcount;
     }
}