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

public class SaveEntOil extends SnisActivity 
{
	public SaveEntOil() { }
	
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
        sDsName = "dsOilEntList";
        if ( ctx.get(sDsName) != null ) {
	        ds1    = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();
	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            PosRecord record = ds1.getRecord(i);
	            logger.logInfo("dsOilEntList------------------->"+record);
	        }
        }
        
		if(nSize1 > 0){
			saveEntOil(ctx); 
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
     protected void saveEntOil(PosContext ctx) 
     {
     	int nSaveCount   = 0; 
     	int nDeleteCount = 0; 

     	PosDataset ds;
     	
        int nSize    	= 0;
            
        ds   = (PosDataset) ctx.get("dsOilEntList");
        nSize = ds.getRecordCount();
        logger.logInfo("nSize------------------->"+nSize);
  
        for ( int i = 0; i < nSize; i++ ){
        	PosRecord record = ds.getRecord(i);
        	
            if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ){
            	 nSaveCount += insertEntOil(record);
            }
            else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE){
            	 nSaveCount += updateEntOil(record);
            }
            else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
             	nDeleteCount = nDeleteCount + deleteEntOil(record);
            }
         }
         Util.setSaveCount  (ctx, nSaveCount  );
         Util.setDeleteCount(ctx, nDeleteCount);
     }
     
        /**
         * <p> ���λ��� �Է� </p>
         * @param   record	PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
         * @return  dmlcount int insert ���ڵ� ����
         * @throws  none
         */
        protected int insertEntOil(PosRecord record){
        	logger.logInfo("insertEntOil start ============================");
            PosParameter param = new PosParameter();       					
            int i = 0;
            
            param.setValueParamter(i++, Util.trim(record.getAttribute("ENT_DT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("OIL_GBN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SUPR")));
			param.setValueParamter(i++, record.getAttribute("ENT_QUANT"));
			param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
            param.setValueParamter(i++, SESSION_USER_ID);

            int dmlcount = this.getDao("candao").insert("d08000001_ib003", param);
            
            logger.logInfo("insertEntOil end ============================");
            return dmlcount;
        }
        
        /**
         * <p> ���λ��� ����  </p>
         * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
         * @return  dmlcount	int		update ���ڵ� ����
         * @throws  none
         */
        protected int updateEntOil(PosRecord record){
        	logger.logInfo("updateEntOil start============================");
        	PosParameter param = new PosParameter();       					
        	int i = 0;
			
			param.setValueParamter(i++, Util.trim(record.getAttribute("SUPR")));
			param.setValueParamter(i++, record.getAttribute("ENT_QUANT"));
			param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
	   		param.setValueParamter(i++, SESSION_USER_ID);
	
	   		i = 0;
            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ENT_DT")));
            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("OIL_GBN")));

	   		int dmlcount = this.getDao("candao").update("d08000001_ub003", param);
	
	   		logger.logInfo("updateEntOil end ============================");
	   		return dmlcount;
        }
        
    
        /**
         * <p> ���λ��� ����</p>
         * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
         * @return  dmlcount	int		delete ���ڵ� ����
         * @throws  none
         */
        protected int deleteEntOil(PosRecord record){
       	 	logger.logInfo("deleteEntOil start============================");
            PosParameter param = new PosParameter();       					
            int i = 0;
                
	   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ENT_DT")));
            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("OIL_GBN")));
                    
            int dmlcount = this.getDao("candao").delete("d08000001_db003", param);
            
            logger.logInfo("deleteEntOil end============================");
            return dmlcount;
        }        
}
