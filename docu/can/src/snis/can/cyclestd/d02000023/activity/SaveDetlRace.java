/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.can.cyclestd.d02000023.activity.SaveDetlRace.java
 * ���ϼ���		: ���༼������
 * �ۼ���			: ��ȫ��
 * ����			: 1.0.0
 * ��������		: 2007-03-12
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/

package snis.can.cyclestd.d02000023.activity;

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
* @auther ��ȫ��
* @version 1.0
*/


public class SaveDetlRace extends SnisActivity {
	
	public SaveDetlRace(){ }

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
    	
    	 PosDataset ds;
         int        nSize        = 0;
         String     sDsName = "";
         sDsName = "dsDetlRaceScheValue";

         logger.logInfo("codemanager start ==============");
         if ( ctx.get(sDsName) != null ) {
 	        ds    = (PosDataset)ctx.get(sDsName);
 	        nSize = ds.getRecordCount();
 	        for ( int i = 0; i < nSize; i++ ) 
 	        {
 	            PosRecord record = ds.getRecord(i);
 	            logger.logInfo(record);
 	        }
 	       logger.logInfo("---------------------------------------");

 	        sDsName = "dsMatGrd";
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
         }
   
 	     saveState(ctx);
 	    logger.logInfo("codemanager end =================");
         return PosBizControlConstants.SUCCESS;
     }
    
    /**
     * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
     protected void saveState(PosContext ctx) 
     {
     	int nSaveCount1   = 0; 
     	int nDeleteCount1 = 0; 
     	int nSaveCount2   = 0; 
     	int nDeleteCount2 = 0; 
     	String sDsName   = "";

     	PosDataset ds;
     	     	     	
        int nSize        = 0;
        int nTempCnt1     = 0;
        int nTempCnt2     = 0;
       
        sDsName = "dsDetlRaceScheValue";       
        // �������� ����           
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        logger.logInfo("nSize ============= " + nSize);
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(" record.getType() ==============" + record.getType());
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) 
	            {
	                nTempCnt1 = updateDetlRace(record);
	               	nSaveCount1 = nSaveCount1 + nTempCnt1;
	            }
	            else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
	            {
	            		try{
	            		nTempCnt1 = insertDetlRace(record);
	            		nSaveCount1 = nSaveCount1 + nTempCnt1;
			        	}catch(Exception e)
		            	{
		            		Util.setSvcMsg(ctx, "�̹� ��ϵ� �ڷᰡ �����մϴ�.");
		            	}
	            }
	            else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	                // delete
	            	 nDeleteCount1 = nDeleteCount1 + deleteDetlRace(record);
	            }
		        logger.logInfo("nTempCnt1 ================= " + nTempCnt1);
		        logger.logInfo("nDeleteCount1 ================= " + nDeleteCount1);
		        logger.logInfo("nSaveCount1 ================= " + nSaveCount1);
		        nSaveCount1 = nSaveCount1 + nTempCnt1;
	        }
        }
        sDsName = "dsMatGrd";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	         logger.logInfo("nSize2 ============= " + nSize);
	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(" record.getType2() ==============" + record.getType());
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
		        	nTempCnt2 = updateDetlMatGrd(record);
		        	nSaveCount2 = nSaveCount2 + nTempCnt2;
		        }
		        
		        else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	try{
		        		nTempCnt2 = insertDetlMatGrd(record);
		        		nSaveCount2 = nSaveCount2 + nTempCnt2;
		        	}catch(Exception e)
	            	{
	            		Util.setSvcMsg(ctx, "B�̹� ��ϵ� �ڷᰡ �����մϴ�.");
	            	}
		        }
		        
	            else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount2 = nDeleteCount2 + deleteDetlMatGrd(record);	            	
	            }		        
		        
		        logger.logInfo("nTempCnt2 ================= " + nTempCnt2);
		        logger.logInfo("nDeleteCount2 ================= " + nDeleteCount2);
		        logger.logInfo("nSaveCount2 ================= " + nSaveCount2);
		        nSaveCount2= nSaveCount2 + nTempCnt2;
	        } // end for
        } // end if
        Util.setSaveCount  (ctx, nSaveCount1     );
        Util.setDeleteCount(ctx, nDeleteCount1   );
        Util.setSaveCount  (ctx, nSaveCount2     );
        Util.setDeleteCount(ctx, nDeleteCount2   );
     }
     protected int insertDetlRace(PosRecord record) 
     {  

    	 logger.logInfo("==========================  ���༼������ ���� �Է�   ============================");
    	 
		 logger.logInfo(record.getAttribute("RACE_YY"));
		 logger.logInfo(record.getAttribute("ROUND"));
		 logger.logInfo(record.getAttribute("MAT_METH"));
		 logger.logInfo(record.getAttribute("RACE_METH"));
		 logger.logInfo(record.getAttribute("RACE_CNT"));
		 logger.logInfo(record.getAttribute("RACE_DT"));
		 logger.logInfo(record.getAttribute("AM_PM_GBN"));
		 logger.logInfo(record.getAttribute("RACER_CNT"));
		 logger.logInfo(record.getAttribute("SELT_ROUND"));
         logger.logInfo(SESSION_USER_ID);
    	 
    	 logger.logInfo("==========================  ���༼������ ���� �Է�   ============================");
    	 
    	 PosParameter param = new PosParameter();   
    	 int i = 0;
      
         param.setValueParamter(i++, record.getAttribute("RACE_YY"));
         param.setValueParamter(i++, record.getAttribute("ROUND"));
         param.setValueParamter(i++, record.getAttribute("MAT_METH"));
         param.setValueParamter(i++, record.getAttribute("RACE_METH"));
         param.setValueParamter(i++, record.getAttribute("RACE_CNT"));
         param.setValueParamter(i++, record.getAttribute("RACE_DT"));
         param.setValueParamter(i++, record.getAttribute("AM_PM_GBN"));
         param.setValueParamter(i++, record.getAttribute("RACER_CNT"));
         param.setValueParamter(i++, record.getAttribute("SELT_ROUND"));
         param.setValueParamter(i++, record.getAttribute("SESSION_USER_ID"));
         
    	 int dmlcount = this.getDao("candao").insert("tbdb_detl_race_ib001", param);
         
         return dmlcount;
     }
     /**
      * <p> ���༼������ ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateDetlRace(PosRecord record) 
     {
    	 PosParameter param = new PosParameter();       					
         int i = 0;
         
    	 logger.logInfo("==========================  ���༼������ ���� ����   ============================");
    	 
		 logger.logInfo(record.getAttribute("MAT_METH"));
		 logger.logInfo(record.getAttribute("RACE_METH"));
		 logger.logInfo(record.getAttribute("RACE_CNT"));
		 logger.logInfo(record.getAttribute("RACE_DT"));
		 logger.logInfo(record.getAttribute("AM_PM_GBN"));
		 logger.logInfo(record.getAttribute("RACER_CNT"));
		 logger.logInfo(record.getAttribute("SELT_ROUND"));
         logger.logInfo(SESSION_USER_ID);
		 logger.logInfo(record.getAttribute("RACER_PERIO_NO"));
		 logger.logInfo(record.getAttribute("ROUND"));
         
    	 logger.logInfo("==========================  ���༼������ ���� ����   ============================");
        
         param.setValueParamter(i++, record.getAttribute("MAT_METH"));
         param.setValueParamter(i++, record.getAttribute("RACE_METH"));
         param.setValueParamter(i++, record.getAttribute("RACE_CNT"));
         param.setValueParamter(i++, record.getAttribute("RACE_DT"));
         param.setValueParamter(i++, record.getAttribute("AM_PM_GBN"));
         param.setValueParamter(i++, record.getAttribute("RACER_CNT"));
         param.setValueParamter(i++, record.getAttribute("SELT_ROUND"));
         param.setValueParamter(i++, SESSION_USER_ID);
         
         i = 0;
         param.setWhereClauseParameter(i++, record.getAttribute("RACE_YY"));
         param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
         
    	 int dmlcount = this.getDao("candao").update("tbdb_detl_race_ub001", param);
         
         return dmlcount;
     }
     /**
      * <p> ���༼������  ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteDetlRace(PosRecord record) 
     {
    	 logger.logInfo("====================== ���༼������  ���� ===========================");
         
    	 logger.logInfo(record.getAttribute("RACE_YY"));
		 logger.logInfo(record.getAttribute("ROUND"));

    	 logger.logInfo("===================== ���༼������  ���� ============================");
    	 
    	 PosParameter param = new PosParameter();       					
         int i = 0;
         param.setWhereClauseParameter(i++, record.getAttribute("RACE_YY"));
         param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
         
    	 int dmlcount = this.getDao("candao").delete("tbdb_detl_race_db001", param);
         
         return dmlcount;
     }
     protected int insertDetlMatGrd(PosRecord record){
    	 
    	 logger.logInfo("==========================  ����ǥ��� �Է�   ============================");
    	 
		 logger.logInfo(record.getAttribute("RACE_YY"));
		 logger.logInfo(record.getAttribute("ROUND"));
		 logger.logInfo(record.getAttribute("MAT_CD"));
		 logger.logInfo(record.getAttribute("GRD"));
         logger.logInfo(SESSION_USER_ID);
    	 
    	 logger.logInfo("==========================  ����ǥ��� ���� �Է�   ========================");
    	 
    	 PosParameter param = new PosParameter();   
    	 int i = 0;
      
         param.setValueParamter(i++, record.getAttribute("RACE_YY"));
         param.setValueParamter(i++, record.getAttribute("ROUND"));
         param.setValueParamter(i++, record.getAttribute("MAT_CD"));
         param.setValueParamter(i++, record.getAttribute("GRD"));
         param.setValueParamter(i++, record.getAttribute("SESSION_USER_ID"));

    	 int dmlcount =  this.getDao("candao").insert("tbdb_detl_race_ib002", param);
    	 return dmlcount;
     }
     protected int updateDetlMatGrd(PosRecord record){
    	 
    	 PosParameter param = new PosParameter();       					
         int i = 0;
         
    	 logger.logInfo("==========================  ����ǥ��� ����    ============================");
    	 
		 logger.logInfo(record.getAttribute("GRD"));
         logger.logInfo(SESSION_USER_ID);
         logger.logInfo(record.getAttribute("MAT_CD"));
         
		 logger.logInfo(record.getAttribute("RACE_YY"));
		 logger.logInfo(record.getAttribute("ROUND"));
    	 
    	 logger.logInfo("==========================  ����ǥ��� ����   ========================");
    	
   	 
         param.setValueParamter(i++, record.getAttribute("GRD"));
         param.setValueParamter(i++, SESSION_USER_ID);
         
         i = 0;
         param.setWhereClauseParameter(i++, record.getAttribute("RACE_YY"));
         param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
         param.setWhereClauseParameter(i++, record.getAttribute("MAT_CD"));
         
    	 int dmlcount =  this.getDao("candao").update("tbdb_detl_race_ub002", param);
    	 return dmlcount;
     }
     protected int deleteDetlMatGrd(PosRecord record){
    	 
    	 logger.logInfo("====================== ���༼������  ���� ===========================");
         
    	 logger.logInfo(record.getAttribute("RACE_YY"));
		 logger.logInfo(record.getAttribute("ROUND"));
		 logger.logInfo(record.getAttribute("MAT_CD"));

    	 logger.logInfo("===================== ���༼������  ���� ============================");
    	 
    	 PosParameter param = new PosParameter();       					
         int i = 0;
         param.setWhereClauseParameter(i++, record.getAttribute("RACE_YY"));
         param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
         param.setWhereClauseParameter(i++, record.getAttribute("MAT_CD"));
         
    	
    	 int dmlcount =  this.getDao("candao").delete("tbdb_detl_race_db002", param);
    	 return dmlcount;
     }
}
