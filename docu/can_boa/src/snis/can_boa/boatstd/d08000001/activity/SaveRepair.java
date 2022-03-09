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

public class SaveRepair extends SnisActivity 
{
	public SaveRepair() { }
	
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
  
        int nSize1 = 0;

        String sDsName = "";

        //���λ���
        sDsName = "dsRepairList";
        if ( ctx.get(sDsName) != null ) {
	        ds1    = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();
	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            PosRecord record = ds1.getRecord(i);
	            logger.logInfo("dsRepairList------------------->"+record);
	        }
        }
        
		if(nSize1 > 0){
			saveRepair(ctx); 
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
     protected void saveRepair(PosContext ctx) 
     {
     	int nSaveCount   = 0; 
     	int nDeleteCount = 0; 

     	PosDataset ds;
     	
        int nSize    	= 0;
        int nTempCnt    = 0;
            
        ds   = (PosDataset) ctx.get("dsRepairList");
        nSize = ds.getRecordCount();
        logger.logInfo("nSize------------------->"+nSize);
  
        for ( int i = 0; i < nSize; i++ ){
        	PosRecord record = ds.getRecord(i);
        	
            if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ){
            	 nSaveCount += insertRepair(record);
            }
            else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE){
            	 nSaveCount += updateRepair(record);
            }
            else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
             	nDeleteCount = nDeleteCount + deleteRepair(record);
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
        protected int insertRepair(PosRecord record){
        	logger.logInfo("insertRepair start ============================");
            PosParameter param = new PosParameter();       					
            int i = 0;
            param.setValueParamter(i++, Util.trim(record.getAttribute("EQUIP_GBN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("EQUIP_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("EQUIP_GBN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("EQUIP_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("MANTC_DT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("MANTC_GBN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("MANTC_MAN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("CHECK_MAN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("USER_GBN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("USER_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("USER_NM")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("MANTC_CMT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SPEC_DESC")));
            param.setValueParamter(i++, SESSION_USER_ID);

            int dmlcount = this.getDao("candao").insert("d08000001_ib002", param);
            
            logger.logInfo("insertRepair end ============================");
            return dmlcount;
        }
        
        /**
         * <p> ���λ��� ����  </p>
         * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
         * @return  dmlcount	int		update ���ڵ� ����
         * @throws  none
         */
        protected int updateRepair(PosRecord record){
        	logger.logInfo("updateRepair start============================");
        	PosParameter param = new PosParameter();       					
        	int i = 0;

        	param.setValueParamter(i++, Util.trim(record.getAttribute("MANTC_DT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("MANTC_GBN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("MANTC_MAN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("CHECK_MAN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("USER_GBN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("USER_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("USER_NM")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("MANTC_CMT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SPEC_DESC")));
	   		param.setValueParamter(i++, SESSION_USER_ID);
	
	   		i = 0;
            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EQUIP_GBN")));
            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EQUIP_NO")));
            param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
	   		int dmlcount = this.getDao("candao").update("d08000001_ub002", param);
	
	   		logger.logInfo("updateRepair end ============================");
	   		return dmlcount;
        }
        
    
        /**
         * <p> ���λ��� ����</p>
         * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
         * @return  dmlcount	int		delete ���ڵ� ����
         * @throws  none
         */
        protected int deleteRepair(PosRecord record){
       	 	logger.logInfo("deleteRepair start============================");
            PosParameter param = new PosParameter();       					
            int i = 0;
                
	   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EQUIP_GBN")));
            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EQUIP_NO")));
            param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
            
            //������
            int dmlcount = this.getDao("candao").delete("d08000001_db002", param);
            
            logger.logInfo("deleteRepair end============================");
            return dmlcount;
        }            
}
