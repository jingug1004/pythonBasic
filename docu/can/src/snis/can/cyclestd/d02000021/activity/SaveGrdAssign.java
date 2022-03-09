/*================================================================================
 * �ý���		: �л����
 * �ҽ����� �̸�	: snis.can.cyclestd.d02000024.activity.SaveGrdAssign.java
 * ���ϼ���		: ���ֵ�޺�����
 * �ۼ���			: ��ȫ��
 * ����			: 1.0.0
 * ��������		: 2007-03-10
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/

package snis.can.cyclestd.d02000021.activity;

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



public class SaveGrdAssign extends SnisActivity{

	public SaveGrdAssign(){ }
	
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
       	        
         sDsName = "dsAssignListValue";
         if ( ctx.get(sDsName) != null ) {
 	        ds    = (PosDataset)ctx.get(sDsName);
 	        nSize = ds.getRecordCount();
 	        for ( int i = 0; i < nSize; i++ ) 
 	        {
 	            PosRecord record = ds.getRecord(i);
 	            logger.logInfo(record);
 	        }
         }
   
 	     saveState(ctx);
         
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
     	int nSaveCount   = 0; 
     	int nDeleteCount = 0; 

     	PosDataset ds;
     	     	     	
        int nSize        = 0;
        int nTempCnt     = 0;
               
        // �������� ����
        ds    = (PosDataset)ctx.get("dsAssignListValue");
        nSize = ds.getRecordCount();
               
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) 
            {
            	//nDeleteCount = nDeleteCount + deleteGrdAssign(record);
                nTempCnt = updateGrdAssign(record);
               	nSaveCount = nSaveCount + nTempCnt;
        		//nTempCnt = insertGrdAssign(record);
        		//nSaveCount = nSaveCount + nTempCnt;
            }
            else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	try 
            	{
            		nTempCnt = insertGrdAssign(record);
            		nSaveCount = nSaveCount + nTempCnt;
            
            	}
            	catch(Exception e)
            	{
            		Util.setSvcMsg(ctx, "�̹� ��ϵ� �ڷᰡ �����մϴ�1");
            	}
            }
            else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
                // delete
            	 nDeleteCount = nDeleteCount + deleteGrdAssign(record);
            }
              
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
     }
     /**
      * <p> ���ֵ�޺����� �Է�  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertGrdAssign(PosRecord record) 
     {  
    	 logger.logInfo("==========================  ���ֵ�޺� ���� �Է�   ============================");
    	 
    	 logger.logInfo(record.getAttribute("MAT_CD"));
		 logger.logInfo(record.getAttribute("STLT_RACE_GRD_CD"));
		 logger.logInfo(record.getAttribute("ADV_RACE_GRD_CD"));
		 logger.logInfo(record.getAttribute("STR_RANK"));
		 logger.logInfo(record.getAttribute("END_RANK"));
		 logger.logInfo(record.getAttribute("RACE_NO"));
		 logger.logInfo(record.getAttribute("EQU_RANK"));
		 logger.logInfo(record.getAttribute("EQU_RANK_NO"));
         logger.logInfo(SESSION_USER_ID);
    	 
    	 logger.logInfo("==========================  ���ֵ�޺� ���� �Է�   ============================");
    	 
    	 PosParameter param = new PosParameter();   
    	 int i = 0;
         param.setValueParamter(i++, Util.trim(record.getAttribute("MAT_CD")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("STLT_RACE_GRD_CD")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ADV_RACE_GRD_CD")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("STR_RANK")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("END_RANK")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_RANK")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_RANK_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_RANK2")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_RANK_NO2")));
         param.setValueParamter(i++, SESSION_USER_ID);
         
    	 int dmlcount = this.getDao("candao").insert("tbdb_grd_assign_ib001", param);
         
         return dmlcount;
     }
     /**
      * <p> ���ֵ�޺����� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateGrdAssign(PosRecord record) 
     {
    	 PosParameter param = new PosParameter();       					
         int i = 0;
         
    	 logger.logInfo("==========================  ���ֵ�޺� ���� ����   ============================");
    	 
    	 logger.logInfo(record.getAttribute("MAT_CD"));
		 logger.logInfo(record.getAttribute("STLT_RACE_GRD_CD"));
		 logger.logInfo(record.getAttribute("ADV_RACE_GRD_CD"));
		 logger.logInfo(record.getAttribute("STR_RANK"));
		 logger.logInfo(record.getAttribute("END_RANK"));
		 logger.logInfo(record.getAttribute("RACE_NO"));
		 logger.logInfo(record.getAttribute("EQU_RANK"));
		 logger.logInfo(record.getAttribute("EQU_RANK_NO"));
         logger.logInfo(SESSION_USER_ID);
         logger.logInfo(record.getAttribute("W_MAT_CD"));
		 logger.logInfo(record.getAttribute("W_STLT_RACE_GRD_CD"));
		 logger.logInfo(record.getAttribute("W_ADV_RACE_GRD_CD"));
         
    	 logger.logInfo("==========================  ���ֵ�޺� ���� ����   ============================");
        
    	 param.setValueParamter(i++, Util.trim(record.getAttribute("STLT_RACE_GRD_CD")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ADV_RACE_GRD_CD")));
    	 param.setValueParamter(i++, Util.trim(record.getAttribute("STR_RANK")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("END_RANK")));
    	 param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_RANK")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_RANK_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_RANK2")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_RANK_NO2")));
         param.setValueParamter(i++, SESSION_USER_ID);
         
         i = 0;
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("MAT_CD")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STLT_RACE_GRD_CD")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ADV_RACE_GRD_CD")));
         
    	 int dmlcount = this.getDao("candao").update("tbdb_grd_assign_ub001", param);
         
         return dmlcount;
     }
     /**
      * <p> ���ֵ�޺�����  ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteGrdAssign(PosRecord record) 
     {
    	 logger.logInfo("====================== ������ֵ��  ���� ===========================");
         
    	 logger.logInfo(record.getAttribute("MAT_CD"));
		 logger.logInfo(record.getAttribute("STLT_RACE_GRD_CD"));
		 logger.logInfo(record.getAttribute("ADV_RACE_GRD_CD"));
    	 
    	 logger.logInfo("===================== ������ֵ��  ���� ============================");
    	 
    	 PosParameter param = new PosParameter();       					
         int i = 0;
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("MAT_CD")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STLT_RACE_GRD_CD")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ADV_RACE_GRD_CD")));
         
    	 int dmlcount = this.getDao("candao").delete("tbdb_grd_assign_db001", param);
         
         return dmlcount;
     }
}
