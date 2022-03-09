/*================================================================================
 * �ý���		: �л����
 * �ҽ����� �̸�	: snis.can.system.d06000002.activity.SaveTotalEduSchd.java
 * ���ϼ���		: ���ձ�������
 * �ۼ���			: �ֹ���
 * ����			: 1.0.0
 * ��������		: 2007-01-03
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.boatstd.d06000002.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �Խù��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �ֹ���
* @version 1.0
*/

public class SaveTotalEduSchd extends SnisActivity 
{
	public SaveTotalEduSchd() { }
	
	/**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	//����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}

    	PosDataset ds1;
		PosDataset ds2;
		
        int    nSize1 = 0;
        int    nSize2 = 0;
        
        String     sDsName1 = "";
        String     sDsName2 = ""; 
        
        sDsName1 = "dsOutEduOutl";
        if ( ctx.get(sDsName1) != null ) {
	        ds1   = (PosDataset)ctx.get(sDsName1);
	        nSize1 = ds1.getRecordCount();
	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            PosRecord record = ds1.getRecord(i);
	            logger.logInfo("dsOutEduOutl------------------->"+record);
	        }
        }
        
        sDsName2 = "dsOutEduAssign";
        if ( ctx.get(sDsName2) != null ) {
	        ds2   = (PosDataset)ctx.get(sDsName2);
	        nSize2= ds2.getRecordCount();
	        for ( int i = 0; i < nSize2; i++ ) 
	        {
	            PosRecord record = ds2.getRecord(i);
	            logger.logInfo("dsOutEduAssign------------------->"+record);
	        }
        }
        
        logger.logInfo("nSize1------------------->"+nSize1);
        logger.logInfo("nSize2------------------->"+nSize2);
    	
        if(nSize1 > 0){
        	saveStateEduOutl(ctx); 
        }
        
        if(nSize2 > 0){
        	saveStateEduAssign(ctx); 
        }        
        
        return PosBizControlConstants.SUCCESS;
    }
    
    /**
     * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
     protected void saveStateEduOutl(PosContext ctx) 
     {
     	int nSaveCount   = 0; 
     	int nDeleteCount = 0; 

     	PosDataset ds;
     	int nSize        = 0;
        int nTempCnt     = 0;
                      
        // �������� ����
        ds    = (PosDataset)ctx.get("dsOutEduOutl");
        nSize = ds.getRecordCount();
               
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
                if ( (nTempCnt = updateEduOutl(record)) == 0 ) {
                	nTempCnt = insertEduOutl(record);
                }

            	nSaveCount = nSaveCount + nTempCnt;
            }
        }
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
     }
     
     
     /**
      * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
      * @param   ctx		PosContext	�����
      * @return  none
      * @throws  none
      */
     protected void saveStateEduAssign(PosContext ctx) 
     {
	     //   �ð�����ǥ ����
	     int nSaveCount = 0;
	     int nDeleteCount = 0;
	     
	     PosDataset ds    = (PosDataset) ctx.get("dsOutEduAssign");
	     int nSize = ds.getRecordCount();
	     
	     for ( int i = 0; i < nSize; i++ ) 
	     {
	     	PosRecord record = ds.getRecord(i);
	     	logger.logInfo("recordType==================>"+record.getType());
	     	logger.logInfo(String.valueOf(com.posdata.glue.miplatform.vo.PosRecord.UPDATE));
	          switch (record.getType())
	          {
	          	case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
	          		updateEduAssign(record);
	          		nSaveCount = nSaveCount + 1;
	          		break;
	          	case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
	          		insertEduAssign(record);
	          		nSaveCount = nSaveCount + 1;
	          		break;
	          	case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
	          		deleteEduAssign(record);
	          		nDeleteCount = nDeleteCount + 1;
	          		break;
	          	
	          }
	     }
	     
	     Util.setSaveCount  (ctx, nSaveCount     );
         Util.setDeleteCount(ctx, nDeleteCount   );
     }  
     
     
     /**
      * <p> �������� �Է�  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertEduOutl(PosRecord record) 
     {
    	 logger.logInfo("==========================  �������� �Է�   ============================");
              
         PosParameter param = new PosParameter();       					
         int i = 0;
                 
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EDU_STR_DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EDU_END_DT")));
         param.setValueParamter(i++, record.getAttribute("EDU_MM"));
         param.setValueParamter(i++, (record.getAttribute("TOT_EDU_TIME")));
         param.setValueParamter(i++, (record.getAttribute("EDU_PERS_NO")));
         param.setValueParamter(i++, (record.getAttribute("EDU_MAN_NO")));
         param.setValueParamter(i++, (record.getAttribute("EDU_WOMAN_NO")));
         param.setValueParamter(i++, SESSION_USER_ID);
                 
         int dmlcount = this.getDao("candao").insert("tbdn_cmpt_edu_outl_d06000002_in001", param);
         
         return dmlcount;
     }
     
     
     /**
      * <p> �ð�����ǥ �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertEduAssign(PosRecord record) 
     {
     	
         PosParameter param = new PosParameter();       					
         int i = 0;
        
         logger.logInfo("===================  �ð�����ǥ �Է�   =====================");
         
         logger.logInfo(record.getAttribute("RACER_PERIO_NO"));
         logger.logInfo(record.getAttribute("SECTN_CD"));
         logger.logInfo(record.getAttribute("CRS_CD"));
         logger.logInfo(record.getAttribute("EDU_TIME"));
         logger.logInfo(record.getAttribute("LECTR"));
         logger.logInfo(record.getAttribute("BLNG"));
         logger.logInfo(record.getAttribute("RMK"));
         logger.logInfo(SESSION_USER_ID);
                           
         logger.logInfo("===================  �ð�����ǥ �Է�   ======================");
                      
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("SECTN_CD")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("CRS_CD")));
         param.setValueParamter(i++, (record.getAttribute("EDU_TIME")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("LECTR")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("BLNG")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
         param.setValueParamter(i++, SESSION_USER_ID);
                  
         int dmlcount = this.getDao("candao").insert("tbdn_cmpt_edu_outl_d06000002_in002", param);
         
         return dmlcount;
     }
     
         
     
     /**
      * <p> �������� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateEduOutl(PosRecord record) 
     {
     	
         PosParameter param = new PosParameter();       					
         int i = 0;
         
         logger.logInfo("=================  �������� ����   ===================");
         
         logger.logInfo(record.getAttribute("EDU_STR_DT"));
         logger.logInfo(record.getAttribute("EDU_END_DT"));
         logger.logInfo(record.getAttribute("EDU_MM"));
         logger.logInfo(record.getAttribute("TOT_EDU_TIME"));
         logger.logInfo(record.getAttribute("EDU_PERS_NO"));
         logger.logInfo(SESSION_USER_ID);
         logger.logInfo(record.getAttribute("RACER_PERIO_NO"));
                  
         logger.logInfo("===================  �������� ����   ====================");
                  
         param.setValueParamter(i++, Util.trim(record.getAttribute("EDU_STR_DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EDU_END_DT")));
         param.setValueParamter(i++, (record.getAttribute("EDU_MM")));
         param.setValueParamter(i++, (record.getAttribute("TOT_EDU_TIME")));
         param.setValueParamter(i++, (record.getAttribute("EDU_PERS_NO")));
         param.setValueParamter(i++, (record.getAttribute("EDU_MAN_NO")));
         param.setValueParamter(i++, (record.getAttribute("EDU_WOMAN_NO")));
         param.setValueParamter(i++, SESSION_USER_ID);
                  
         i = 0;
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
                        
         int dmlcount = this.getDao("candao").update("tbdn_cmpt_edu_outl_d06000002_un001", param);
               
         return dmlcount;
     }
     
     
     /**
      * <p> �ð�����ǥ ���� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateEduAssign(PosRecord record) 
     {
     	
         PosParameter param = new PosParameter();       					
         int i = 0;
         
         logger.logInfo("==================== �ð�����ǥ ���� ============================");
         
         logger.logInfo(record.getAttribute("EDU_TIME"));
         logger.logInfo(record.getAttribute("LECTR"));
         logger.logInfo(record.getAttribute("BLNG"));
         logger.logInfo(record.getAttribute("RMK"));
         logger.logInfo(SESSION_USER_ID);
         logger.logInfo(record.getAttribute("RACER_PERIO_NO"));
         logger.logInfo(record.getAttribute("SECTN_CD"));
         logger.logInfo(record.getAttribute("CRS_CD"));
                  
         logger.logInfo("==================== �ð�����ǥ ���� ============================");
         param.setValueParamter(i++, (record.getAttribute("EDU_TIME")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("LECTR")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("BLNG")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
         param.setValueParamter(i++, SESSION_USER_ID);
         i=0;
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SECTN_CD")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CRS_CD")));
                 
         int dmlcount = this.getDao("candao").update("tbdn_cmpt_edu_outl_d06000002_un002", param);
                
         return dmlcount;
     }
         
     /**
      * <p> �ð�����ǥ  ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteEduAssign(PosRecord record) 
     {
    	 logger.logInfo("====================== �ð�����ǥ  ���� ===========================");
         
    	 logger.logInfo(record.getAttribute("RACER_PERIO_NO"));
         logger.logInfo(record.getAttribute("SECTN_CD"));
         logger.logInfo(record.getAttribute("CRS_CD"));
    	 
    	 logger.logInfo("===================== �ð�����ǥ  ���� ============================");
    	 
    	 PosParameter param = new PosParameter();       					
         int i = 0;
                             
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SECTN_CD")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CRS_CD")));
                 
         int dmlcount = this.getDao("candao").delete("tbdn_cmpt_edu_outl_d06000002_dn001", param);
                  
         return dmlcount;
     }
}
