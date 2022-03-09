/*================================================================================
 * �ý���			: ��ǰ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06030050.activity.SavePartsSight.java
 * ���ϼ���		: ������翡 ���� ����� ���� �Ѵ�.
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-01-06
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06030050.activity;


import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* ������翡 ���� ����� ���� �Ѵ�.
* @auther ������ 
* @version 1.0
*/
public class SavePartsSight extends SnisActivity
{    
    public SavePartsSight()
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
        
        ds    = (PosDataset) ctx.get("dsOutParts");
        String	stndYear   = (String) ctx.get("SRCH_DT");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	
            }else{
            	nSaveCount = nSaveCount + saveParts(record,stndYear,mbrCd);
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
    protected int saveParts(PosRecord record, String stndYear, String mbrCd)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateParts(record,stndYear,mbrCd);
    	if(effectedRowCnt<1){    		
    	}
        return effectedRowCnt;
    }

    /**
     * <p> ������翡 ���� �����  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateParts(PosRecord record, String stndYear, String mbrCd)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
    	param.setValueParamter(i++, record.getAttribute("SIGHT_CNT")); 
    	param.setValueParamter(i++, SESSION_USER_ID);
    	param.setValueParamter(i++, stndYear); 
    	param.setValueParamter(i++, stndYear); 
    	param.setValueParamter(i++, mbrCd); 
        param.setValueParamter(i++, record.getAttribute("PARTS_CD"));
        param.setValueParamter(i++, record.getAttribute("PRICE_STND_YEAR"));
		
    	
    	//logger.logDebug("OLD.STND_YEAR =" +record.getAttribute("OLD.STND_YEAR") );
	        
        return this.getDao("boadao").update("tbef_parts_sight_uf001", param);
    }
   
}
