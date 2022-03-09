/*================================================================================
 * �ý���		: ��������
 * �ҽ����� �̸�: snis.boa.racer.e03040010.activity.SaveEquipmentArrange.java
 * ���ϼ���		: ����/��Ʈ���� ���
 * �ۼ���			: ���ȭ
 * ����			: 1.0.0
 * ��������		: 2007-12-23
 * ������������	: 
 * ����������	: 
 * ������������	: 
=================================================================================*/
package snis.boa.racer.e03040010.activity;

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
public class SaveEquipmentArrangeRslt extends SnisActivity
{    
	protected String sRacerNo      = "";
	protected String sSessionUserId = "";
	
    public SaveEquipmentArrangeRslt()
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
System.out.println("saveState:::::::");

    	int nSaveCount   	= 0; 

    	PosDataset ds;
        int nSize        	= 0;
        PosRecord record  	= null;

        ds    = (PosDataset) ctx.get("dsLotRslt");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            record = ds.getRecord(i);
    		nSaveCount = nSaveCount + saveRacerArrange(ctx, record);
        }

        Util.setSaveCount  (ctx, nSaveCount);
    }

    /**
     * <p> ����/��Ʈ����  Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRacerArrange(PosContext ctx, PosRecord record)
    {
System.out.println("saveRacerArrange:::::::");
    	PosParameter param = new PosParameter();
        int i = 0;
       
        param.setWhereClauseParameter(i++, (String)ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, (String)ctx.get("MBR_CD"));
        param.setWhereClauseParameter(i++, (String)ctx.get("TMS"));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("PRT_SEQ_001")));
        param.setWhereClauseParameter(i++, (String)ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, (String)ctx.get("MBR_CD"));
        param.setWhereClauseParameter(i++, (String)ctx.get("TMS"));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("PRT_SEQ_002")));
        param.setWhereClauseParameter(i++, (String)ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, (String)ctx.get("MBR_CD"));
        param.setWhereClauseParameter(i++, (String)ctx.get("TMS"));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("PRT_SEQ_003")));
        param.setWhereClauseParameter(i++, SESSION_USER_ID);
        
System.out.println("saveRacerArrange:::::2222::");
        return this.getDao("boadao").update("tbeb_arrange_uc002", param);  
    }

}


