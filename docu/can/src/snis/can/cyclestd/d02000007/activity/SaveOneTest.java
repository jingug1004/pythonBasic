/*================================================================================
 * �ý���		: �л����
 * �ҽ����� �̸�	: snis.can.cyclestd.d02000008.activity.SaveOneTest.java
 * ���ϼ���		: 1�����߽���
 * �ۼ���		: �ֹ���
 * ����			: 1.0.0
 * ��������		: 2007-01-18
 * ������������	: 
 * ����������		: 
 * ������������	: 
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
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �Խù��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �ֹ���
* @version 1.0
*/


public class SaveOneTest extends SnisActivity 
{
	public SaveOneTest() { }
	
	/**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
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
     * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
     * @param   ctx	PosContext ���񱸺� �����
     * @return  none
     * @throws  none
     */
     protected void saveStatePerio_Exam1(PosContext ctx) 
     {
		int nSaveCount   = 0; 
		PosDataset ds1;

		int nSize        = 0;
		int nTempCnt     = 0;
 	
        //���񱸺� ����       
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
     * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
     * @param   ctx	 PosContext	ȯ�����ǥ �����
     * @return  none
     * @throws  none
     */
     
	protected void saveStatePerio_Exam2(PosContext ctx) 
	{
		int nSaveCount   = 0; 

		PosDataset ds2;

		int nSize        = 0;
		int nTempCnt     = 0;

		//���� ����       
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
      * <p> �����ŽǱ� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
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
      * <p>ü������ �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
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