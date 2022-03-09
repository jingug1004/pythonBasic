/*================================================================================
 * �ý���			: �ְ���������
 * �ҽ����� �̸�	: snis.can.system.d02000002.activity.SaveCDetailEduSchd.java
 * ���ϼ���		: �ڵ� ����
 * �ۼ���			: �ֹ���
 * ����			: 1.0.0
 * ��������		: 2007-01-18
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000004.activity;

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

public class SaveWeekEduSchd extends SnisActivity 
{
	public SaveWeekEduSchd() { }
	
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
 
        sDsName = "dsWkSche";
        if ( ctx.get(sDsName) != null ) {
        	
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("------------------->"+record);
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
        String sDsName   = "";
 
         
        // �ְ���������ǥ ����       
        ds   = (PosDataset) ctx.get("dsWkSche");
        nSize = ds.getRecordCount();
        
        logger.logInfo("nSize------------------->"+nSize);
        logger.logInfo("nTempCnt1------------------->"+nTempCnt);   
      
        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds.getRecord(i);
             if(record.getAttribute("COPY").equals("0")){  
	             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
	             {
	            	 if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT){
	            		 nTempCnt = insertWk_Sche(record);
	            	 }
	            	 else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE){
	            		 nTempCnt = updateWk_Sche(record);
	                 }              	 
	             }
             }else{
            	 nTempCnt = insertWk_Sche(record);
             }
             nSaveCount = nSaveCount + nTempCnt;
             if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
             {
                 // delete
             	nDeleteCount = nDeleteCount + deleteWk_Sche(record);
             }
         }
                  
         Util.setSaveCount  (ctx, nSaveCount     );
         Util.setDeleteCount(ctx, nDeleteCount   );
     }
     
     
     /**
      * <p> �ְ��������� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertWk_Sche(PosRecord record) 
     {
    	 logger.logInfo("insertWk_Sche ============================");
    	 
    	 PosParameter param = new PosParameter();       					
         int i = 0;
         
         logger.logInfo("RACER_PERIO_NO ==>"+Util.trim(record.getAttribute("RACER_PERIO_NO")));
         logger.logInfo("WK ==>"+record.getAttribute("WK"));
         logger.logInfo("DT ==>"+Util.trim(record.getAttribute("DT")));
         logger.logInfo("STR_END_TIME ==>"+Util.trim(record.getAttribute("STR_END_TIME")));
         logger.logInfo("SECTN_CD ==>"+Util.trim(record.getAttribute("SECTN_CD")));
         logger.logInfo("CRS_CD ==>"+Util.trim(record.getAttribute("CRS_CD")));
         logger.logInfo("DETL_EDU_CRS ==>"+Util.trim(record.getAttribute("DETL_EDU_CRS")));
         logger.logInfo("OBJ ==>"+Util.trim(record.getAttribute("OBJ")));
         logger.logInfo("LECTR ==>"+Util.trim(record.getAttribute("LECTR")));
         logger.logInfo("PLC ==>"+Util.trim(record.getAttribute("PLC")));
         logger.logInfo("TIME ==>"+record.getAttribute("TIME"));
                  
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, record.getAttribute("WK"));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("STR_END_TIME")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("SECTN_CD")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("CRS_CD")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DETL_EDU_CRS")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("OBJ")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("LECTR")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("PLC")));
         param.setValueParamter(i++, record.getAttribute("TIME"));
         param.setValueParamter(i++, SESSION_USER_ID); 
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));	 
	            
 		 int dmlcount = this.getDao("candao").insert("tbdb_wk_sche_ib001", param);
         
         logger.logInfo("insertWk_Sche ============================");
         return dmlcount;
     }
     
     /**
      * <p> �ְ��������� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateWk_Sche(PosRecord record) 
     {
     	logger.logInfo("updateWk_Sche ============================");
        PosParameter param = new PosParameter();       					
        int i = 0;
        
        param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_END_TIME")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("SECTN_CD")));   
		param.setValueParamter(i++, Util.trim(record.getAttribute("CRS_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("DETL_EDU_CRS"))); 
		param.setValueParamter(i++, Util.trim(record.getAttribute("OBJ")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("LECTR")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("PLC")));
		param.setValueParamter(i++, record.getAttribute("TIME")); 
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));

		int dmlcount = this.getDao("candao").update("tbdb_wk_sche_ub001", param);

		logger.logInfo("updateWk_Sche ============================");
		return dmlcount;
     }
     
 
     /**
      * <p> �ְ���������ǥ  ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteWk_Sche(PosRecord record) 
     {
    	 logger.logInfo("deleteWk_Sche start============================");
        
    	 PosParameter param = new PosParameter();       					
         int i = 0;
             
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
                 
         int dmlcount = this.getDao("candao").delete("tbdb_wk_sche_db001", param);
         
         logger.logInfo("deleteWk_Sche end============================");
         return dmlcount;
     }
}
