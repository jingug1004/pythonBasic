/*================================================================================
 * �ý���			: ��ǰ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06020020.activity.SavePartsCom.java
 * ���ϼ���		: ��ü�� �����Ѵ�.  
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-01-04
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06020020.activity;


import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* ��ü�� ���� ������ ���, ����, ���� �Ѵ�.
* @auther ������ 
* @version 1.0
*/
public class SavePartsCom extends SnisActivity
{    
    public SavePartsCom()
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
        saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

   
    
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutPartsCom");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deletePartsCom(record);
            }else{
            	nSaveCount = nSaveCount + savePartsCom(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
  
    /**
     * <p> ������ Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int savePartsCom(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updatePartsCom(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertPartsCom(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> ��ü���   Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updatePartsCom(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
    	param.setValueParamter(i++, record.getAttribute("COM_NM")); 
    	param.setValueParamter(i++, record.getAttribute("COM_REG_NO")); 
    	param.setValueParamter(i++, record.getAttribute("COM_OWNER")); 
    	param.setValueParamter(i++, record.getAttribute("COM_TEL")); 
    	param.setValueParamter(i++, record.getAttribute("COM_FAX")); 
    	param.setValueParamter(i++, record.getAttribute("POST_CD")); 
    	param.setValueParamter(i++, record.getAttribute("COM_ADDR")); 
    	param.setValueParamter(i++, record.getAttribute("TRADE_STR_DT")); 
    	param.setValueParamter(i++, record.getAttribute("RMK"));
    	param.setValueParamter(i++, SESSION_USER_ID);
    	param.setValueParamter(i++, record.getAttribute("PARTS_COM_CD")); 
    	
    	//logger.logDebug("OLD.STND_YEAR =" +record.getAttribute("OLD.STND_YEAR") );
	        
        return this.getDao("boadao").update("tbef_parts_com_uf001", param);
    }

    /**
     * <p>��ü �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertPartsCom(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
	    param.setValueParamter(i++, record.getAttribute("PARTS_COM_CD"));    
	    param.setValueParamter(i++, record.getAttribute("COM_NM")); 
    	param.setValueParamter(i++, record.getAttribute("COM_REG_NO")); 
    	param.setValueParamter(i++, record.getAttribute("COM_OWNER")); 
    	param.setValueParamter(i++, record.getAttribute("COM_TEL")); 
    	param.setValueParamter(i++, record.getAttribute("COM_FAX")); 
    	param.setValueParamter(i++, record.getAttribute("POST_CD")); 
    	param.setValueParamter(i++, record.getAttribute("COM_ADDR")); 
    	param.setValueParamter(i++, record.getAttribute("TRADE_STR_DT")); 
    	param.setValueParamter(i++, record.getAttribute("RMK"));
    	param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbef_parts_com_if001", param);
    }

    /**
     * <p>��ü ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deletePartsCom(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("PARTS_COM_CD")); 
        
        return  this.getDao("boadao").update("tbef_parts_com_df001", param);
    }
}
