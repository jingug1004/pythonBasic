/*================================================================================
 * �ý���			: �ĺ�������
 * �ҽ����� �̸�	: snis.can_boa.boatstd.d09000001.activity.SaveHealth.java
 * ���ϼ���		: �ǰ����
 * �ۼ���			: �ֹ���
 * ����			: 1.0.0
 * ��������		: 2007-01-03
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.boatstd.d08000001.activity;

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

public class SaveEquip extends SnisActivity 
{
	public SaveEquip() { }
	
	/**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	//      ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
    	        return PosBizControlConstants.SUCCESS;
    	}
    	
        PosDataset ds1;
  
        int nSize1 = 0;

        String sDsName = "";

        //���λ���
        sDsName = "dsEquipList";
        if ( ctx.get(sDsName) != null ) {
	        ds1    = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();
	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            PosRecord record = ds1.getRecord(i);
	            logger.logInfo("dsEquipList------------------->"+record);
	        }
        }
        
		if(nSize1 > 0){
			saveEquip(ctx); 
		}
		//saveOil(ctx);
		
        return PosBizControlConstants.SUCCESS;
    }
  
    
    /**
     * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	����� : ���λ���
     * @return  none
     * @throws  none
     */
     protected void saveEquip(PosContext ctx) 
     {
     	int nSaveCount   = 0; 
     	int nDeleteCount = 0; 

     	PosDataset ds;
     	
        int nSize    	= 0;
        int nTempCnt    = 0;
            
        ds   = (PosDataset) ctx.get("dsEquipList");
        nSize = ds.getRecordCount();
        logger.logInfo("nSize------------------->"+nSize);
  
        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {
            	 if ( (nTempCnt = updateEquip(record)) == 0 ) {
                   	nTempCnt = insertEquip(record);
                 }    	 
            	 nSaveCount = nSaveCount + nTempCnt;
             }
             
             if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
             {
                 // delete
             	nDeleteCount = nDeleteCount + deleteEquip(record);
             }
         }
         Util.setSaveCount  (ctx, nSaveCount     );
         Util.setDeleteCount(ctx, nDeleteCount   );
     }
     
        /**
         * <p> ���λ��� �Է� </p>
         * @param   record	PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
         * @return  dmlcount int insert ���ڵ� ����
         * @throws  none
         */
        protected int insertEquip(PosRecord record) 
        {
        	logger.logInfo("insertEquip start ============================");
            PosParameter param = new PosParameter();       					
            int i = 0;
            
            param.setValueParamter(i++, Util.trim(record.getAttribute("EQUIP_GBN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("EQUIP_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ENT_DT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("STATE")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
            param.setValueParamter(i++, SESSION_USER_ID);
            param.setValueParamter(i++, Util.trim(record.getAttribute("USER_GBN")));

            int dmlcount = this.getDao("candao").insert("d08000001_ib001", param);
            
            logger.logInfo("insertEquip end ============================");
            return dmlcount;
        }
        
        /**
         * <p> ���λ��� ����  </p>
         * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
         * @return  dmlcount	int		update ���ڵ� ����
         * @throws  none
         */
        protected int updateEquip(PosRecord record) 
        {
        	logger.logInfo("updateEquip start============================");
        	PosParameter param = new PosParameter();       					
        	int i = 0;

        	param.setValueParamter(i++, Util.trim(record.getAttribute("ENT_DT")));
        	param.setValueParamter(i++, Util.trim(record.getAttribute("STATE")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
	   		param.setValueParamter(i++, SESSION_USER_ID);
	   		param.setValueParamter(i++, Util.trim(record.getAttribute("USER_GBN")));
	
	   		i = 0;
            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EQUIP_GBN")));
            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EQUIP_NO")));
           
	   		int dmlcount = this.getDao("candao").update("d08000001_ub001", param);
	
	   		logger.logInfo("updateEquip end ============================");
	   		return dmlcount;
        }
        
    
        /**
         * <p> ���λ��� ����</p>
         * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
         * @return  dmlcount	int		delete ���ڵ� ����
         * @throws  none
         */
        protected int deleteEquip(PosRecord record) 
        {
       	 	logger.logInfo("deleteEquip start============================");
            PosParameter param = new PosParameter();    
            PosParameter param2 = new PosParameter();
            int i = 0;
                
	   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EQUIP_GBN")));
            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EQUIP_NO")));
                                
            int dmlcount = this.getDao("candao").delete("d08000001_db001", param);
            
            i = 0;

            param2.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EQUIP_GBN")));
            param2.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EQUIP_NO")));
            
            //���񳻿� ����
            this.getDao("candao").delete("d08000001_db005", param2);
            
            logger.logInfo("deleteEquip end============================");
            return dmlcount;
        }        
}
