/*================================================================================
 * �ý���			: ��񳻿�  ����
 * �ҽ����� �̸�	: snis.can.system.d03000002.activity.SaveEquipDetl.java
 * ���ϼ���		: ��񳻿� ����
 * �ۼ���			: ���μ�
 * ����			: 1.0.0
 * ��������		: 2008-01-25
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d03000002.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ��񳻿��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���μ�
* @version 1.0
*/
public class SaveEquipDetl extends SnisActivity
{    
	
    public SaveEquipDetl()
    {
    }

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
        String     sDsName      = "";
        
        sDsName = "dsEquipDetl";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("dsEquipDetl============>"+record);
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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        sDsName = "dsEquipDetl";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	                    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
	                  {
	                      if ( (nTempCnt = updateSaveEquipDetl(record)) == 0 ) {
	                      	nTempCnt = insertSaveEquipDetl(record);
	                      }

	                  	nSaveCount = nSaveCount + nTempCnt;
	                  }
	                  
	                  if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	                  {
	                      // delete
	                  	nDeleteCount = nDeleteCount + deleteSaveEquipDetl(record);
	                  }         
	           		         
	        } // end for
        }     // end if

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> ��񳻿� �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertSaveEquipDetl(PosRecord record) 
    {
   	    logger.logInfo("==========================  ��񳻿� �Է�   ============================");
                
        PosParameter param = new PosParameter();       					
        int i = 0;
                
       
        param.setValueParamter(i++, (record.getAttribute("ENT_DD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("EQUIP_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SUPR")));
        param.setValueParamter(i++, record.getAttribute("QTY"));        
        param.setValueParamter(i++, (record.getAttribute("STAT")));
        param.setValueParamter(i++, (record.getAttribute("RMK")));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);        
        int dmlcount = this.getDao("candao").insert("tbdc_equip_detl_ic001", param);
        
        return dmlcount;
    }
    
    
    /**
     * <p> ��񳻿� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateSaveEquipDetl(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("SUPR"));
        param.setValueParamter(i++, record.getAttribute("QTY"));
        param.setValueParamter(i++, record.getAttribute("STAT"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ENT_DD" ));
        param.setWhereClauseParameter(i++, record.getAttribute("EQUIP_CD" ));
       
        int dmlcount = this.getDao("candao").update("tbdc_equip_detl_uc001", param);
        
        return dmlcount;
    }

    
    
    /**
     * <p> ��񳻿� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteSaveEquipDetl(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ENT_DD"));
        param.setWhereClauseParameter(i++, record.getAttribute("EQUIP_CD"));
        
        int dmlcount  = this.getDao("candao").update("tbdc_equip_detl_dc001", param);
        	
        
        return dmlcount;
    }    
}
