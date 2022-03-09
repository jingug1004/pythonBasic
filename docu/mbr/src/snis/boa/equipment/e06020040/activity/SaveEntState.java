/*================================================================================
 * �ý���			: ��ǰ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06020040.activity.SaveEntState.java
 * ���ϼ���		: �԰��Ͽ� ���� �ֹ��԰���¸� �����Ѵ�.  
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-02-11
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06020040.activity;


import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* �԰��Ͽ� ���� �԰���¸� �����Ѵ�.(�ֹ����̺�)
* @auther ������ 
* @version 1.0
*/
public class SaveEntState extends SnisActivity
{    
    public SaveEntState()
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
    	//����� ���� Ȯ��
    	if ( setUserInfo(ctx)== null || !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
        savePartsNowCntState(ctx);
        saveEntState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * ��ǰ ����� update
     * @param ctx
     */
    public void savePartsNowCntState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
        
    	PosDataset ds;
        int nSize        = 0;
    	
        ds    = (PosDataset) ctx.get("dsOutEnt");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + updatePartsNowCnt(record);       	
        }
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    /**
     * �԰� ���� update
     * @param ctx
     */
    public void saveEntState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
        
    	PosDataset ds;
        int nSize        = 0;
    	
        ds    = (PosDataset) ctx.get("dsOutOrder");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + updateEntState(record);       	
        }
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> ��ǰ���̺� ����� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
//    protected int updatePartsNowCnt(PosRecord record)
//    {
//        PosParameter param = new PosParameter();
//        int i = 0;
//        
//    	param.setValueParamter(i++, record.getAttribute("ENT_CNT")); 
//    	param.setValueParamter(i++, SESSION_USER_ID);
//    	param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
//    	param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
//        param.setValueParamter(i++, record.getAttribute("PARTS_CD"));
//        param.setValueParamter(i++, record.getAttribute("PRICE_STND_YEAR"));
//    	    
//        return this.getDao("boadao").update("tbef_ent_uf003", param);
//    }
    protected int updatePartsNowCnt(PosRecord record)
    {
    	PosParameter param = new PosParameter();
    	int i = 0;
      
    	param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
    	param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
    	param.setValueParamter(i++, record.getAttribute("PARTS_CD"));
    	param.setValueParamter(i++, record.getAttribute("PRICE_STND_YEAR"));
    	param.setValueParamter(i++, record.getAttribute("ENT_CNT")); 
    	param.setValueParamter(i++, SESSION_USER_ID);
  	    
    	return this.getDao("boadao").update("tbef_ent_mf003", param);
    }
    
    
    protected int updateEntState(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ENT_STATE")); 
        param.setValueParamter(i++, SESSION_USER_ID);  
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));  
        param.setValueParamter(i++, record.getAttribute("ORDER_SEQ"));  	
        return this.getDao("boadao").update("tbef_ent_uf002", param);
    }
}
