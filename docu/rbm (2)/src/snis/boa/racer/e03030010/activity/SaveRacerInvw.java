/*================================================================================
 * �ý���		: ��������
 * �ҽ����� �̸�: snis.boa.racer.e03030010.activity.SaveRacerInvw.java
 * ���ϼ���		: �������������
 * �ۼ���		: ���ȭ
 * ����			: 1.0.0
 * ��������		: 2007-12-13
 * ������������	: 
 * ����������	: 
 * ������������	: 
=================================================================================*/
package snis.boa.racer.e03030010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �λ󼱼������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���ȭ
* @version 1.0
*/
public class SaveRacerInvw extends SnisActivity
{    
	protected String sRacerNo      = "";
	protected String sSessionUserId = "";
	
    public SaveRacerInvw()
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
        
        ds    = (PosDataset) ctx.get("dsOutRacerInvw");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	nSaveCount = nSaveCount + saveRacerInvw(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ���ּ������ Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRacerInvw(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt =  insertRacerInvw(record);
        return effectedRowCnt;
    }

 
    /**
     * <p>���ּ������  �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertRacerInvw(PosRecord record) 
    {
    	
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD")));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO")));
        if (record.getAttribute("ENTRY_BODY_WGHT") != null && Float.parseFloat(String.valueOf(record.getAttribute("ENTRY_BODY_WGHT"))) > 0){
            param.setValueParamter(i++, Util.trim(record.getAttribute("ENTRY_BODY_WGHT"))); 
        } else {
            param.setValueParamter(i++, null); 
        }
        param.setValueParamter(i++, Util.trim(record.getAttribute("HEAL_STAT_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CONDI_CD")));
        if ("".equals(Util.trim(record.getAttribute("TRNG_STAT")))) {
        	param.setValueParamter(i++, null);            
        } else {
        	param.setValueParamter(i++, Util.trim(record.getAttribute("TRNG_STAT")));            
        }
        if ("".equals(Util.trim(record.getAttribute("ETC")))) {
        	param.setValueParamter(i++, null);            
        } else {
        	param.setValueParamter(i++, Util.trim(record.getAttribute("ETC")));            
        }
        
        param.setValueParamter(i++, Util.trim(record.getAttribute("ROOM_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PRIV_TURNNING_CNT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("START_CNT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("FORM_CNT")));
        
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD")));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ENTRY_BODY_WGHT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HEAL_STAT_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CONDI_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TRNG_STAT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ETC")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ROOM_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PRIV_TURNNING_CNT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("START_CNT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("FORM_CNT")));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("boadao").update("tbec_racer_invw_ic001", param);

 		//���������� ������ ����
        if (record.getAttribute("ENTRY_BODY_WGHT") != null && Float.parseFloat(String.valueOf(record.getAttribute("ENTRY_BODY_WGHT"))) > 0){
	        i = 0; 
	    	PosParameter param2 = new PosParameter();    	
	    	param2.setValueParamter(i++, record.getAttribute("ENTRY_BODY_WGHT"));
	    	param2.setValueParamter(i++, SESSION_USER_ID );
	        
	        i = 0;
	        param2.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_NO")));      
	    	dmlcount = this.getDao("boadao").update("tbec_racer_detail_uc002", param2);
        }
        
        return dmlcount;    
    }
}