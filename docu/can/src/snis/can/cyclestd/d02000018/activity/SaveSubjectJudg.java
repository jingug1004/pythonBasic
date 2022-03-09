/*================================================================================
 * �ý���			: ���α�������
 * �ҽ����� �̸�	: snis.can.system.d02000002.activity.SaveCDetailEduSchd.java
 * ���ϼ���		: �ڵ� ����
 * �ۼ���			: �ֹ���
 * ����			: 1.0.0
 * ��������		: 2007-01-03
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000018.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;
/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �Խù��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �ֹ���
* @version 1.0
*/

public class SaveSubjectJudg extends SnisActivity 
{
	public SaveSubjectJudg() { }
	
	/**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
      	// ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
        PosDataset ds1;
		PosDataset ds2;
		PosDataset ds3;
        int    nSize1 = 0;
        int    nSize2 = 0;
        String     sDsName = "";

        sDsName = "dsPerioExam1";
        if ( ctx.get(sDsName) != null ) {
	        ds1   = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();
	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            PosRecord record = ds1.getRecord(i);
	            logger.logInfo("dsPerioExam4------------------->"+record);
	        }
        }
        
        sDsName = "dsPerioExam4";
        if ( ctx.get(sDsName) != null ) {
	        ds2   = (PosDataset)ctx.get(sDsName);
	        nSize2= ds2.getRecordCount();
	        for ( int i = 0; i < nSize2; i++ ) 
	        {
	            PosRecord record = ds2.getRecord(i);
	            logger.logInfo("dsPerioExam5------------------->"+record);
	        }
        } 
        sDsName = "dsPerioExam2";
        if ( ctx.get(sDsName) != null ) {
	        ds3   = (PosDataset)ctx.get(sDsName);
	        nSize2= ds3.getRecordCount();
	        for ( int i = 0; i < nSize2; i++ ) 
	        {
	            PosRecord record = ds3.getRecord(i);
	            logger.logInfo("dsPerioExam2------------------->"+record);
	        }
        }   
        saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }
    
    /**
     * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
     * @param   ctx	PosContext ���񱸺� �����
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
            sDsName = "dsPerioExam1";
            if ( ctx.get(sDsName) != null ) {
    	        ds   		 = (PosDataset) ctx.get(sDsName);
    	        nSize 		 = ds.getRecordCount();
    	        
    	         for ( int i = 0; i < nSize; i++ ) {
    	            PosRecord record = ds.getRecord(i);         
             
    	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
    	    	  	|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) 
    	            {
    	            	nTempCnt = insertSavePerioExam1(record);
    		            nSaveCount = nSaveCount + nTempCnt;
    	            }
    	         } // end for
            }     // end if
            
            sDsName = "dsPerioExam4";
            if ( ctx.get(sDsName) != null ) {
    	        ds   		 = (PosDataset) ctx.get(sDsName);
    	        nSize 		 = ds.getRecordCount();
    	         for ( int i = 0; i < nSize; i++ ) {
    	        	 
    	            PosRecord record = ds.getRecord(i);         
    	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
    	    	    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ){
    	            			nTempCnt = insertSavePerioExam4(record);
    	            			nSaveCount = nSaveCount + nTempCnt;
    	    	     }
    	        } // end for
            }     // end if
            
            sDsName = "dsPerioExam2";
            if ( ctx.get(sDsName) != null ) {
    	        ds   		 = (PosDataset) ctx.get(sDsName);
    	        nSize 		 = ds.getRecordCount();

    	         for ( int i = 0; i < nSize; i++ ) {
    	            PosRecord record = ds.getRecord(i);         
    	                    
    	                      
    	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
    	    	  	|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) 
    	            {
    	  			    nTempCnt = insertSavePerioExam2(record);
    	  				nSaveCount = nSaveCount + nTempCnt;
    	    	    }
    	        } // end for
            }     // end if
            Util.setSaveCount  (ctx, nSaveCount     );
     }
     
     protected int insertSavePerioExam1(PosRecord record) 
     {
      PosParameter param = null;
      int i = 0;
      int dmlcount = 0;
 		
      String sRec200 = Util.trim(String.valueOf(record.getAttribute("REC_200")));
      String sRec333 = Util.trim(String.valueOf(record.getAttribute("REC_333")));
      String sRec500 = Util.trim(String.valueOf(record.getAttribute("REC_500")));
      String sRec1000 = Util.trim(String.valueOf(record.getAttribute("REC_1000")));
      String sRec2000 = Util.trim(String.valueOf(record.getAttribute("REC_2000")));
      //LESSON4
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
      	 			 	
 			dmlcount += this.getDao("candao").insert("tbdb_period_exam_ib101", param);
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

 			dmlcount += this.getDao("candao").insert("tbdb_period_exam_ib101", param);
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

 			dmlcount += this.getDao("candao").insert("tbdb_period_exam_ib101", param);
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
 			
 			dmlcount += this.getDao("candao").insert("tbdb_period_exam_ib101", param);
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
 			
 			dmlcount += this.getDao("candao").insert("tbdb_period_exam_ib101", param);
     }    
  
 		logger.logInfo("insertPerio_Exam1 end============================");
 		return dmlcount;
     }
     protected int insertSavePerioExam2(PosRecord record) 
     {
         PosParameter param = null;
         int i = 0;
         int dmlcount = 0;
         
         String sLession1 = Util.trim(String.valueOf(record.getAttribute("LESSON1")));
         String sLession2 = Util.trim(String.valueOf(record.getAttribute("LESSON2")));
         String sLession3 = Util.trim(String.valueOf(record.getAttribute("LESSON3")));
         String sLession4 = Util.trim(String.valueOf(record.getAttribute("LESSON4")));
         
         if(!sLession1.equals("")){
         	i = 0;
          	param = new PosParameter();
          	
          	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
     		param.setValueParamter(i++, "101");
     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
     		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("LESSON1"))));	
     		param.setValueParamter(i++, record.getAttribute("LESSON1"));
     		param.setValueParamter(i++, SESSION_USER_ID);
     		
             param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
     		param.setValueParamter(i++, "101");
     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
     		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("LESSON1"))));	
     		param.setValueParamter(i++, record.getAttribute("LESSON1"));
     		param.setValueParamter(i++, SESSION_USER_ID);
          	 			 	
     		dmlcount += this.getDao("candao").insert("tbdb_period_exam_ib101", param);
          }
         if(!sLession2.equals("")){
         	i = 0;
          	param = new PosParameter();
          	
          	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
     		param.setValueParamter(i++, "102");
     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
     		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("LESSON2"))));	
     		param.setValueParamter(i++, record.getAttribute("LESSON2"));
     		param.setValueParamter(i++, SESSION_USER_ID);
     		
             param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
     		param.setValueParamter(i++, "102");
     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
     		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("LESSON2"))));	
     		param.setValueParamter(i++, record.getAttribute("LESSON2"));
     		param.setValueParamter(i++, SESSION_USER_ID);
          	 			 	
     		dmlcount += this.getDao("candao").insert("tbdb_period_exam_ib101", param);
          }
         if(!sLession3.equals("")){
         	i = 0;
          	param = new PosParameter();
          	
          	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
     		param.setValueParamter(i++, "103");
     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
     		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("LESSON3"))));	
     		param.setValueParamter(i++, record.getAttribute("LESSON3"));
     		param.setValueParamter(i++, SESSION_USER_ID);
     		
             param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
     		param.setValueParamter(i++, "103");
     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
     		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("LESSON3"))));	
     		param.setValueParamter(i++, record.getAttribute("LESSON3"));
     		param.setValueParamter(i++, SESSION_USER_ID);
          	 			 	
     		dmlcount += this.getDao("candao").insert("tbdb_period_exam_ib101", param);
          }
         if(!sLession4.equals("")){
         	i = 0;
          	param = new PosParameter();
          	
          	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
     		param.setValueParamter(i++, "104");
     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
     		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("LESSON4"))));	
     		param.setValueParamter(i++, record.getAttribute("LESSON4"));
     		param.setValueParamter(i++, SESSION_USER_ID);
     		
             param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
     		param.setValueParamter(i++, "104");
     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
     		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("LESSON4"))));	
     		param.setValueParamter(i++, record.getAttribute("LESSON4"));
     		param.setValueParamter(i++, SESSION_USER_ID);
          	 			 	
     		dmlcount += this.getDao("candao").insert("tbdb_period_exam_ib101", param);
          }
         
         return dmlcount;
     }
     protected int insertSavePerioExam4(PosRecord record) 
     {
      PosParameter param = null;
      int i = 0;
      int dmlcount = 0;
 		
      String sTimeRec = Util.trim(String.valueOf(record.getAttribute("TIME_REC")));
      String sErrRec = Util.trim(String.valueOf(record.getAttribute("ERROR_REC")));
      
      if(!sTimeRec.equals("")){
     	i = 0;
      	param = new PosParameter();
      	
      	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 		param.setValueParamter(i++, "306");
 		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
 		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("TIME_REC"))));	
 		param.setValueParamter(i++, record.getAttribute("TIME_SCR"));
 		param.setValueParamter(i++, SESSION_USER_ID);
 		
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 		param.setValueParamter(i++, "306");
 		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
 		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("TIME_REC"))));	
 		param.setValueParamter(i++, record.getAttribute("TIME_SCR"));
 		param.setValueParamter(i++, SESSION_USER_ID);
      	 			 	
 		dmlcount += this.getDao("candao").insert("tbdb_period_exam_ib101", param);
      }
      
      if(!sErrRec.equals("")){
 		i = 0;  
      	param = new PosParameter();	
      	
      	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 		param.setValueParamter(i++, "307");
 		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
 		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("ERROR_REC"))));	
 		param.setValueParamter(i++, "0");
 		param.setValueParamter(i++, SESSION_USER_ID);
 		
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
 		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
 		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
 		param.setValueParamter(i++, "307");
 		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
 		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("ERROR_REC"))));	
 		param.setValueParamter(i++, "0");
 		param.setValueParamter(i++, SESSION_USER_ID);

 		dmlcount += this.getDao("candao").insert("tbdb_period_exam_ib101", param);
      }        
   
 		return dmlcount;
     }
}
