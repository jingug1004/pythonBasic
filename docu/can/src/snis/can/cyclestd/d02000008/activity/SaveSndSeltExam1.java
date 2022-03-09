package snis.can.cyclestd.d02000008.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

public class SaveSndSeltExam1  extends SnisActivity
{

	 public SaveSndSeltExam1()
	 {
     }

	 public String runActivity(PosContext ctx)
	 {
	   	
	    	
	        PosDataset ds;
	        int        nSize        = 0;
	        String     sDsName      = "";
	        
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
	        Util.setSaveCount  (ctx, nSaveCount     );
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
}
