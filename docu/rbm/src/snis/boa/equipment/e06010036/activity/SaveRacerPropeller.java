/*================================================================================
 * �ý���		: ������
 * �ҽ����� �̸�: snis.boa.equipment.e06010035.activity.SavePropeller.java
 * ���ϼ���		: �����緯 ��� ���
 * �ۼ���			: ����ȭ
 * ����			: 1.0.0
 * ��������		: 2011-03-16
 * ������������	: 
 * ����������	: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06010036.activity;

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
* @version 1.0
*/
public class SaveRacerPropeller extends SnisActivity
{    
	protected String sRacerNo      = "";
	protected String sSessionUserId = "";
	
    public SaveRacerPropeller()
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
    	int nSaveCount   	= 0; 
    	int nDeleteCount 	= 0; 

    	PosDataset ds;
        int nSize        	= 0;
        PosRecord record  	= null;
        
        ds    = (PosDataset) ctx.get("dsOutRacerPropeller");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
                  {
            		nSaveCount = nSaveCount + saveRacerPropeller(record);
                  }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> Propeller Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRacerPropeller(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt =  mergeRacerPropeller(record);
        return effectedRowCnt;
    }

 
    /**
     * <p>Propeller  �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int mergeRacerPropeller(PosRecord record) 
    {
    	//if ( record.getAttribute("RACER_NO") == null || record.getAttribute("RACER_NO").equals("")) return 0;
    	
        PosParameter param = new PosParameter();
        int i = 0;
       
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO   ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PROPELLER1 ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PROPELLER2 ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PROPELLER3 ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PROPELLER4 ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PROPELLER5 ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK        ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("boadao").insert("tbef_propeller_mf002", param);
        return dmlcount;
    }

}


