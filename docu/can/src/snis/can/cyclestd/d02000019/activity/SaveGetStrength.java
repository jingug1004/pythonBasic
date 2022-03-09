/*================================================================================
 * 시스템		: 학사관리
 * 소스파일 이름	: snis.can.cyclestd.d02000008.activity.SaveOneTest.java
 * 파일설명		: 체력측정
 * 작성자		: 최문규
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-18
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d02000019.activity;

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


public class SaveGetStrength extends SnisActivity 
{
	public SaveGetStrength() { }
	
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
		int    nSize1 = 0;
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
                         
       logger.logInfo("nSize1------------------->"+nSize1);
              
        if(nSize1 > 0){
        	saveStatePerio_Exam1(ctx); 
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
             	 //if ( (nTempCnt = updatePerio_Exam1(record)) == 0 ) {
                  	nTempCnt = insertPerio_Exam1(record);
                 //}                	 
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
      * <p> 체력측정 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */ 
	   protected int insertPerio_Exam1(PosRecord record) 
	   {
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
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	 			param.setValueParamter(i++, "401");
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("GRIP_LEFT_REC"))));	
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			 			 	
	  			dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
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
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	 			param.setValueParamter(i++, "402");
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("GRIP_RIGHT_REC"))));	
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	  			dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
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
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	 			param.setValueParamter(i++, "403");
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("LEG_LEFT_REC"))));	
	 			param.setValueParamter(i++, SESSION_USER_ID);
	  			
	  			dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
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
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	 			param.setValueParamter(i++, "404");
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("LEG_RIGHT_REC"))));	
	 			param.setValueParamter(i++, SESSION_USER_ID);
	  			
	  			dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
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
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	 			param.setValueParamter(i++, "405");
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("LEG_LEFT_REC2"))));	
	 			param.setValueParamter(i++, SESSION_USER_ID);
	  			
	  			dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
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
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	 			param.setValueParamter(i++, "406");
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("LEG_RIGHT_REC2"))));	
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 			dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
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
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	 			param.setValueParamter(i++, "407");
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("BELLY_REC"))));	
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			 			
	  			dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
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
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	 			param.setValueParamter(i++, "408");
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("MAX_PWR_REC"))));	
	 			param.setValueParamter(i++, SESSION_USER_ID);
	   			
	   			dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
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
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	 			param.setValueParamter(i++, "409");
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("AVG_PWR_REC"))));	
	 			param.setValueParamter(i++, SESSION_USER_ID);
	   			
	   			dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
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
	 			param.setValueParamter(i++, SESSION_USER_ID);
	 			
	 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
	 			param.setValueParamter(i++, "410");
	 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
	 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("TIRE_IDX_REC"))));	
	 			param.setValueParamter(i++, SESSION_USER_ID);
	   			
	   			dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
	          }
		   return dmlcount;
	   }

     /**
      * <p> 체력측정 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertPerio_Exam11(PosRecord record) 
     {
    	 logger.logInfo("insertPerio_Exam1 start============================");
         PosParameter param = null;
         int i = 0;
         int dmlcount = 0;
 		 int recCount = 0;
         
         String sPT     = Util.NVL(Util.trim(String.valueOf(record.getAttribute("PT"))));
         String sTW     = Util.NVL(Util.trim(String.valueOf(record.getAttribute("TW"))));
         String sBW     = Util.NVL(Util.trim(String.valueOf(record.getAttribute("BW"))));
         String sD_RATE = Util.NVL(Util.trim(String.valueOf(record.getAttribute("D_RATE"))));
         String sY_RATE = Util.NVL(Util.trim(String.valueOf(record.getAttribute("Y_RATE"))));
         String sTW2    = Util.NVL(Util.trim(String.valueOf(record.getAttribute("TW2"))));
         String sMP     = Util.NVL(Util.trim(String.valueOf(record.getAttribute("MP"))));
         String sPP     = Util.NVL(Util.trim(String.valueOf(record.getAttribute("PP"))));
         String sFI     = Util.NVL(Util.trim(String.valueOf(record.getAttribute("FI"))));
         String sO2     = Util.NVL(Util.trim(String.valueOf(record.getAttribute("O2"))));
         String sRPR    = Util.NVL(Util.trim(String.valueOf(record.getAttribute("RPR"))));
         String sRPE    = Util.NVL(Util.trim(String.valueOf(record.getAttribute("RPE"))));
         String sCHNG   = Util.NVL(Util.trim(String.valueOf(record.getAttribute("CHNG"))));
                  
                                   
         if(!sPT.equals("")){
        	i = 0;
        	recCount ++;
        	logger.logInfo("sPT=====>"+sPT+":");
         	param = new PosParameter();
         	
 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setValueParamter(i++, "001");
 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
 			param.setValueParamter(i++, sPT);	
 			param.setValueParamter(i++, record.getAttribute("PT_SCR"));
 			param.setValueParamter(i++, SESSION_USER_ID);
 			 	
 			dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
         }
                         
         
         if(!sTW.equals("")){
 			i = 0;
 			recCount ++;
 			logger.logInfo("sTW=====>"+sTW+":");
         	param = new PosParameter();			
 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setValueParamter(i++, "002");
 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
 			param.setValueParamter(i++, sTW);	
 			param.setValueParamter(i++, (record.getAttribute("TW_SCR")));
 			param.setValueParamter(i++, SESSION_USER_ID);
 		
 			dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
         }
                         
         
         if(!sBW.equals("")){
 			i = 0;
 			recCount ++;
 			logger.logInfo("sBW=====>"+sBW+":");
         	param = new PosParameter();			
 	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setValueParamter(i++, "003");
 			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
 			param.setValueParamter(i++, sBW);	
 			param.setValueParamter(i++, (record.getAttribute("BW_SCR")));
 			param.setValueParamter(i++, SESSION_USER_ID);
 			
 			dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
         }
                          
         
         if(!sD_RATE.equals("")){
        	 i = 0; 
        	 recCount ++;
        	 logger.logInfo("sD_RATE=====>"+sD_RATE+":");
          	 param = new PosParameter();			
  	         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
  			 param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
  			 param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
  			 param.setValueParamter(i++, "004");
  			 param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
  			 param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
  			 param.setValueParamter(i++, sD_RATE);	
  			 param.setValueParamter(i++, (record.getAttribute("D_RATE_SCR")));
  			 param.setValueParamter(i++, SESSION_USER_ID);
  			
  			 dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
          }
         
                   
         
          if(!sY_RATE.equals("")){
        	  i = 0;
        	  recCount ++;
        	  logger.logInfo("sY_RATE=====>"+sY_RATE+":");
        	  param = new PosParameter();			
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
        	  param.setValueParamter(i++, "005");
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
        	  param.setValueParamter(i++, sY_RATE);	
        	  param.setValueParamter(i++, (record.getAttribute("Y_RATE_SCR")));
        	  param.setValueParamter(i++, SESSION_USER_ID);
  			
  			  dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
          }
                            
         
          if(!sTW2.equals("")){
        	  i = 0;
        	  recCount ++;
        	  logger.logInfo("sTW2=====>"+sTW2+":");
        	  param = new PosParameter();			
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
        	  param.setValueParamter(i++, "006");
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
        	  param.setValueParamter(i++, sTW2);	
        	  param.setValueParamter(i++, (record.getAttribute("TW2_SCR")));
        	  param.setValueParamter(i++, SESSION_USER_ID);
   			
        	  dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
          }
                          
          
          if(!sMP.equals("")){
        	  i = 0;
        	  recCount ++;
        	  logger.logInfo("sMP=====>"+sMP+":");
        	  param = new PosParameter();			
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
        	  param.setValueParamter(i++, "007");
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
        	  param.setValueParamter(i++, sMP);	
        	  param.setValueParamter(i++, (record.getAttribute("MP_SCR")));
        	  param.setValueParamter(i++, SESSION_USER_ID);
   			
        	  dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
          }
                             
          if(!sPP.equals("") ){
        	  i = 0; 
        	  recCount ++;
        	  logger.logInfo("sPP=====>"+sPP+":");
        	  param = new PosParameter();			
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
        	  param.setValueParamter(i++, "008");
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
        	  param.setValueParamter(i++, sPP);	
        	  param.setValueParamter(i++, (record.getAttribute("PP_SCR")));
        	  param.setValueParamter(i++, SESSION_USER_ID);
   			
        	  dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
          }
                             
          
          if(!sFI.equals("")){
        	  i = 0; 
        	  recCount ++;
        	  logger.logInfo("sFI=====>"+sFI+":");
        	  param = new PosParameter();			
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
        	  param.setValueParamter(i++, "009");
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
        	  param.setValueParamter(i++, sFI);	
        	  param.setValueParamter(i++, (record.getAttribute("FI_SCR")));
        	  param.setValueParamter(i++, SESSION_USER_ID);
   			
        	  dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
          }
          
                   
          
          if(!sO2.equals("")){
        	  i = 0;
        	  recCount ++;
        	  logger.logInfo("sO2=====>"+sO2+":");
        	  
        	  param = new PosParameter();			
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
        	  param.setValueParamter(i++, "010");
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
        	  param.setValueParamter(i++, sO2);	
        	  param.setValueParamter(i++, (record.getAttribute("O2_SCR")));
        	  param.setValueParamter(i++, SESSION_USER_ID);
   			
        	  dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
          }
                             
          
          if(!sRPR.equals("")){
        	  i = 0;  
        	  recCount ++;
        	  logger.logInfo("sRPR=====>"+sRPR+":");
        	  param = new PosParameter();			
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
        	  param.setValueParamter(i++, "011");
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
        	  param.setValueParamter(i++, sRPR);	
        	  param.setValueParamter(i++, (record.getAttribute("RPR_SCR")));
        	  param.setValueParamter(i++, SESSION_USER_ID);
   			
        	  dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
          }
                              
          
          if(!sRPE.equals("") ){
        	  i = 0;  
        	  recCount ++;
        	  logger.logInfo("sRPE=====>"+sRPE+":");
        	  param = new PosParameter();			
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
        	  param.setValueParamter(i++, "012");
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
        	  param.setValueParamter(i++, sRPE);	
        	  param.setValueParamter(i++, (record.getAttribute("RPE_SCR")));
        	  param.setValueParamter(i++, SESSION_USER_ID);
   			
        	  dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
          }
                             
          
          if(!sCHNG.equals("")){
        	  i = 0;
        	  recCount ++;
        	  logger.logInfo("sCHNG=====>"+sCHNG+":");
        	  param = new PosParameter();			
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
        	  param.setValueParamter(i++, "013");
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        	  param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
        	  param.setValueParamter(i++, sCHNG);	
        	  param.setValueParamter(i++, (record.getAttribute("CHNG_SCR")));
        	  param.setValueParamter(i++, SESSION_USER_ID);
   			
        	  dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
          }
          
          if(recCount > 0) {
        	  
        	  if(sPT.equals("")){
              	i = 0;
              
              	logger.logInfo("sPT=====>"+sPT+":");
               	param = new PosParameter();
       	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       			param.setValueParamter(i++, "001");
       			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
       			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
       			param.setValueParamter(i++, "0000");	
       			param.setValueParamter(i++, record.getAttribute("PT_SCR"));
       			param.setValueParamter(i++, SESSION_USER_ID);
       			 	
       			dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
               }
                               
               
               if(sTW.equals("")){
       			i = 0;
       			
       			logger.logInfo("sTW=====>"+sTW+":");
               	param = new PosParameter();			
       	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       			param.setValueParamter(i++, "002");
       			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
       			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
       			param.setValueParamter(i++, "0000");	
       			param.setValueParamter(i++, (record.getAttribute("TW_SCR")));
       			param.setValueParamter(i++, SESSION_USER_ID);
       		
       			dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
               }
                               
               
               if(sBW.equals("")){
       			i = 0;
       			
       			logger.logInfo("sBW=====>"+sBW+":");
               	param = new PosParameter();			
       	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       			param.setValueParamter(i++, "003");
       			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
       			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
       			param.setValueParamter(i++, "0000");	
       			param.setValueParamter(i++, (record.getAttribute("BW_SCR")));
       			param.setValueParamter(i++, SESSION_USER_ID);
       			
       			dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
               }
                                
               
               if(sD_RATE.equals("")){
              	 i = 0; 
              	 
              	 logger.logInfo("sD_RATE=====>"+sD_RATE+":");
                 
              	 param = new PosParameter();			
        	     param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        		 param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
        		 param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
        		 param.setValueParamter(i++, "004");
        		 param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        		 param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
        		 param.setValueParamter(i++, "0000");	
        		 param.setValueParamter(i++, (record.getAttribute("D_RATE_SCR")));
        		 param.setValueParamter(i++, SESSION_USER_ID);
        			
        		 dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
               }
               
                         
               
                if(sY_RATE.equals("")){
              	  i = 0;
              	 
              	  logger.logInfo("sY_RATE=====>"+sY_RATE+":");
              	  param = new PosParameter();			
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
              	  param.setValueParamter(i++, "005");
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
              	  param.setValueParamter(i++, "0000");	
              	  param.setValueParamter(i++, (record.getAttribute("Y_RATE_SCR")));
              	  param.setValueParamter(i++, SESSION_USER_ID);
        			
        			  dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
                }
                                  
               
                if(sTW2.equals("")){
              	  i = 0;
              	  
              	  logger.logInfo("sTW2=====>"+sTW2+":");
              	  param = new PosParameter();			
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
              	  param.setValueParamter(i++, "006");
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
              	  param.setValueParamter(i++, "0000");	
              	  param.setValueParamter(i++, (record.getAttribute("TW2_SCR")));
              	  param.setValueParamter(i++, SESSION_USER_ID);
         			
              	  dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
                }
                                
                
                if(sMP.equals("")){
              	  i = 0;
              	  
              	  logger.logInfo("sMP=====>"+sMP+":");
              	  param = new PosParameter();			
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
              	  param.setValueParamter(i++, "007");
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
              	  param.setValueParamter(i++, "000");	
              	  param.setValueParamter(i++, (record.getAttribute("MP_SCR")));
              	  param.setValueParamter(i++, SESSION_USER_ID);
         			
              	  dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
                }
                                   
                if(sPP.equals("") ){
              	  i = 0; 
              	  
              	  logger.logInfo("sPP=====>"+sPP+":");
              	  param = new PosParameter();			
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
              	  param.setValueParamter(i++, "008");
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
              	  param.setValueParamter(i++, "000");	
              	  param.setValueParamter(i++, (record.getAttribute("PP_SCR")));
              	  param.setValueParamter(i++, SESSION_USER_ID);
         			
              	  dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
                }
                                   
                
                if(sFI.equals("")){
              	  i = 0; 
              	  
              	  logger.logInfo("sFI=====>"+sFI+":");
              	  param = new PosParameter();			
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
              	  param.setValueParamter(i++, "009");
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
              	  param.setValueParamter(i++, "000");	
              	  param.setValueParamter(i++, (record.getAttribute("FI_SCR")));
              	  param.setValueParamter(i++, SESSION_USER_ID);
         			
              	  dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
                }
                               
                
                if(sO2.equals("")){
              	  i = 0;
              	  
              	  logger.logInfo("sO2=====>"+sO2+":");
              	  
              	  param = new PosParameter();			
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
              	  param.setValueParamter(i++, "010");
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
              	  param.setValueParamter(i++, "0000");	
              	  param.setValueParamter(i++, (record.getAttribute("O2_SCR")));
              	  param.setValueParamter(i++, SESSION_USER_ID);
         			
              	  dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
                }
                                   
                
                if(sRPR.equals("")){
              	  i = 0;  
              	  
              	  logger.logInfo("sRPR=====>"+sRPR+":");
              	  param = new PosParameter();			
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
              	  param.setValueParamter(i++, "011");
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
              	  param.setValueParamter(i++, "0000");	
              	  param.setValueParamter(i++, (record.getAttribute("RPR_SCR")));
              	  param.setValueParamter(i++, SESSION_USER_ID);
         			
              	  dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
                }
                                    
                
                if(sRPE.equals("") ){
              	  i = 0;  
              	  
              	  logger.logInfo("sRPE=====>"+sRPE+":");
              	  param = new PosParameter();			
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
              	  param.setValueParamter(i++, "012");
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
              	  param.setValueParamter(i++, "0000");	
              	  param.setValueParamter(i++, (record.getAttribute("RPE_SCR")));
              	  param.setValueParamter(i++, SESSION_USER_ID);
         			
              	  dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
                }
                                   
                
                if(sCHNG.equals("")){
              	  i = 0;
              	 
              	  logger.logInfo("sCHNG=====>"+sCHNG+":");
              	  param = new PosParameter();			
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
              	  param.setValueParamter(i++, "013");
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
              	  param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
              	  param.setValueParamter(i++, "0000");	
              	  param.setValueParamter(i++, (record.getAttribute("CHNG_SCR")));
              	  param.setValueParamter(i++, SESSION_USER_ID);
         			
              	  dmlcount += this.getDao("candao").insert("tbdb_strt_mesur_ib001", param);
                }        	  
          }
                                 
 		  logger.logInfo("insertPerio_Exam1 end============================");
 		  return dmlcount;
     }
     
     /**
      * <p> 체력측정 수정  </p>
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
 		
 		String sPT       = Util.trim(String.valueOf(record.getAttribute("PT")));
        String sTW       = Util.trim(String.valueOf(record.getAttribute("TW")));
        String sBW     = Util.trim(String.valueOf(record.getAttribute("BW")));
        String sD_RATE    = Util.trim(String.valueOf(record.getAttribute("D_RATE")));
        String sY_RATE    = Util.trim(String.valueOf(record.getAttribute("Y_RATE")));
        String sTW2   = Util.trim(String.valueOf(record.getAttribute("TW2")));
        String sMP   = Util.trim(String.valueOf(record.getAttribute("MP")));
        String sPP   = Util.trim(String.valueOf(record.getAttribute("PP")));
        String sFI   = Util.trim(String.valueOf(record.getAttribute("FI")));
        String sO2   = Util.trim(String.valueOf(record.getAttribute("O2")));
        String sRPR   = Util.trim(String.valueOf(record.getAttribute("RPR")));
        String sRPE   = Util.trim(String.valueOf(record.getAttribute("RPE")));
        String sCHNG   = Util.trim(String.valueOf(record.getAttribute("CHNG")));
                 
        if(!sPT.equals("")){
         	
        	param = new PosParameter();   
 	         			
 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("PT"))));	
 			param.setValueParamter(i++, SESSION_USER_ID);
 			i = 0;    
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setWhereClauseParameter(i++, "001");
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
 			param.setWhereClauseParameter(i++, record.getAttribute("PT_SCR"));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			
 			dmlcount += this.getDao("candao").update("tbdb_strt_mesur_ub001", param);
        }
        
                  
        if(!sTW.equals("")){
 			i = 0;
         	param = new PosParameter();
 	       
 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("TW"))));	
 			param.setValueParamter(i++, SESSION_USER_ID);
 			i = 0;    
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setWhereClauseParameter(i++, "002");
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
 			param.setWhereClauseParameter(i++, record.getAttribute("TW_SCR"));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			
 			dmlcount += this.getDao("candao").update("tbdb_strt_mesur_ub001", param);
        }
        

             
        if(!sBW.equals("")){
 			i = 0;
         	param = new PosParameter();
 	        
 			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("BW"))));	
 			param.setValueParamter(i++, SESSION_USER_ID);
 			i = 0;    
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 			param.setWhereClauseParameter(i++, "003");
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
 			param.setWhereClauseParameter(i++, record.getAttribute("BW_SCR"));
 			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
 			
 			dmlcount += this.getDao("candao").update("tbdb_strt_mesur_ub001", param);
        }
        
         
        if(!sD_RATE.equals("")){
  			i = 0;
          	param = new PosParameter();
  	        
  			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("D_RATE"))));	
  			param.setValueParamter(i++, SESSION_USER_ID);
  			i = 0;    
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
  			param.setWhereClauseParameter(i++, "004");
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
  			param.setWhereClauseParameter(i++,record.getAttribute("D_RATE_SCR"));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
  			
  			dmlcount += this.getDao("candao").update("tbdb_strt_mesur_ub001", param);
        }
        
         
        if(!sY_RATE.equals("")){
  			i = 0;
          	param = new PosParameter();
  	        
  			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("Y_RATE"))));	
  			param.setValueParamter(i++, SESSION_USER_ID);
  			i = 0;    
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
  			param.setWhereClauseParameter(i++, "005");
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
  			param.setWhereClauseParameter(i++, record.getAttribute("Y_RATE_SCR"));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
  			
  			dmlcount += this.getDao("candao").update("tbdb_strt_mesur_ub001", param);
         }
        
         
        if(!sTW2.equals("")){
  			i = 0;
          	param = new PosParameter();
  	        
  			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("TW2"))));	
  			param.setValueParamter(i++, SESSION_USER_ID);
  			i = 0;    
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
  			param.setWhereClauseParameter(i++, "006");
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
  			param.setWhereClauseParameter(i++, record.getAttribute("TW2_SCR"));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
  			
  			dmlcount += this.getDao("candao").update("tbdb_strt_mesur_ub001", param);
         }
        
        
        if(!sMP.equals("")){
  			i = 0;
          	param = new PosParameter();
  	        
  			param.setValueParamter(i++, Util.trim(record.getAttribute("MP")));	
  			param.setValueParamter(i++, SESSION_USER_ID);
  			i = 0;    
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
  			param.setWhereClauseParameter(i++, "007");
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
  			param.setWhereClauseParameter(i++, record.getAttribute("MP_SCR"));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
  			
  			dmlcount += this.getDao("candao").update("tbdb_strt_mesur_ub001", param);
         }
        
        
        if(!sPP.equals("")){
  			i = 0;
          	param = new PosParameter();
  	        
  			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("PP"))));	
  			param.setValueParamter(i++, SESSION_USER_ID);
  			i = 0;    
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
  			param.setWhereClauseParameter(i++, "008");
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
  			param.setWhereClauseParameter(i++, record.getAttribute("PP_SCR"));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
  			
  			dmlcount += this.getDao("candao").update("tbdb_strt_mesur_ub001", param);
         }
        
        
        if(!sFI.equals("")){
  			i = 0;
          	param = new PosParameter();
  	        
  			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("FI"))));	
  			param.setValueParamter(i++, SESSION_USER_ID);
  			i = 0;    
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
  			param.setWhereClauseParameter(i++, "009");
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
  			param.setWhereClauseParameter(i++, record.getAttribute("FI_SCR"));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
  			
  			dmlcount += this.getDao("candao").update("tbdb_strt_mesur_ub001", param);
         }
         
        
        if(!sO2.equals("")){
  			i = 0;
          	param = new PosParameter();
  	        
  			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("O2"))));	
  			param.setValueParamter(i++, SESSION_USER_ID);
  			i = 0;    
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
  			param.setWhereClauseParameter(i++, "010");
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
  			param.setWhereClauseParameter(i++, record.getAttribute("O2_SCR"));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
  			
  			dmlcount += this.getDao("candao").update("tbdb_strt_mesur_ub001", param);
         }
         
        
        if(!sRPR.equals("")){
  			i = 0;
          	param = new PosParameter();
  	        
  			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("RPR"))));	
  			param.setValueParamter(i++, SESSION_USER_ID);
  			i = 0;    
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
  			param.setWhereClauseParameter(i++, "011");
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
  			param.setWhereClauseParameter(i++, record.getAttribute("RPR_SCR"));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
  			
  			dmlcount += this.getDao("candao").update("tbdb_strt_mesur_ub001", param);
         }
         
        
        if(!sRPE.equals("")){
  			i = 0;
          	param = new PosParameter();
  	        
  			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("RPE"))));	
  			param.setValueParamter(i++, SESSION_USER_ID);
  			i = 0;    
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
  			param.setWhereClauseParameter(i++, "012");
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
  			param.setWhereClauseParameter(i++, record.getAttribute("RPE_SCR"));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
  			
  			dmlcount += this.getDao("candao").update("tbdb_strt_mesur_ub001", param);
         }
         
        
        if(!sCHNG.equals("")){
  			i = 0;
          	param = new PosParameter();
  	        
  			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("CHNG"))));	
  			param.setValueParamter(i++, SESSION_USER_ID);
  			i = 0;    
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
  			param.setWhereClauseParameter(i++, "013");
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
  			param.setWhereClauseParameter(i++, record.getAttribute("CHNG_SCR"));
  			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
  			
  			dmlcount += this.getDao("candao").update("tbdb_strt_mesur_ub001", param);
         }
         
                  
 		 logger.logInfo("updatePerio_Exam1 end============================");
 		 return dmlcount;
     }
     
     /**
      * <p> 체력측정 삭제</p>
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

		int dmlcount = this.getDao("candao").delete("tbdb_strt_mesur_db001", param);

		logger.logInfo("deletePerio_Exam1 end============================");
		return dmlcount;
     }
    
}