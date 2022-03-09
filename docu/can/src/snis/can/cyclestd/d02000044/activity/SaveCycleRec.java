
/*================================================================================
 * 시스템			:학사관리
 * 소스파일 이름	: snis.can.system.d02000044.activity.SaveCycleRec.java
 * 파일설명		: 1,2차 자전거 기록측정
 * 작성자			: 전홍조
 * 버전			: 1.0.0
 * 생성일자		: 2008-05-27
 * 최종수정일자	: 
 * 최종수정자		: 전홍조 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d02000044.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

public class SaveCycleRec extends SnisActivity{

	 public SaveCycleRec(){}
	
	 public String runActivity(PosContext ctx)
	 {
		 	PosDataset ds;
	        int        nSize        = 0;
	        String     sDsName      = "";
			
	        if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
		    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
		            return PosBizControlConstants.SUCCESS;
		     }
	        sDsName = "dsCycleRec";
	        if ( ctx.get(sDsName) != null ) {
		        ds    = (PosDataset)ctx.get(sDsName);
		        nSize = ds.getRecordCount();
		        for ( int i = 0; i < nSize; i++ ) 
		        {
		            PosRecord record = ds.getRecord(i);
		            logger.logInfo("dsCycleRec============>"+record);
		        }
	        }
	        saveState(ctx);
	        return PosBizControlConstants.SUCCESS;
     }
	 
	 protected void saveState(PosContext ctx) 
	 {
	    	int nSaveCount   = 0; 
	    	String sDsName   = "";
	    	
	    	PosDataset ds;
	        int nSize        = 0;
	        int nTempCnt     = 0;
	        sDsName = "dsCycleRec";
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();
		        
		         for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);         
	         
		            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
		    	  	|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) 
		            {
		            	nTempCnt = insertSaveCycRec(record);
			            nSaveCount = nSaveCount + nTempCnt;
		            }
		         } // end for
	        }     // end if
	        Util.setSaveCount  (ctx, nSaveCount     );
	    }
	  
	    protected int insertSaveCycRec(PosRecord record) 
	    {
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
	     	 			 	
				dmlcount += this.getDao("candao").insert("tbdb_cyclerec_i001", param);
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

				dmlcount += this.getDao("candao").insert("tbdb_cyclerec_i001", param);
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

				dmlcount += this.getDao("candao").insert("tbdb_cyclerec_i001", param);
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
				
				dmlcount += this.getDao("candao").insert("tbdb_cyclerec_i001", param);
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
				
				dmlcount += this.getDao("candao").insert("tbdb_cyclerec_i001", param);
	    } 
	     return dmlcount;
	 }
}
