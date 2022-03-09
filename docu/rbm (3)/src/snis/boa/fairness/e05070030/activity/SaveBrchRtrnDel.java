/*================================================================================
 * �ý���			: ���� ����
 * �ҽ����� �̸�	: snis.boa.fairness.e05070030.activity.SaveBrchRtrnDel.java
 * ���ϼ���		: �������ȯ������ ����
 * �ۼ���			: ����ȭ
 * ����			: 1.0.0
 * ��������		: 2009-12-2
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.fairness.e05070030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SaveBrchRtrnDel extends SnisActivity
{    
    public SaveBrchRtrnDel()
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
		int nSize = 0;
		String sDsName = "dsOutBrchRtrn";

		if (ctx.get(sDsName) != null) {
			ds = (PosDataset) ctx.get(sDsName);
			nSize = ds.getRecordCount();

			for (int i = 0; i < nSize; i++) {
				PosRecord record = ds.getRecord(i);
				logger.logInfo(record);
			}
		}

		saveState(ctx);

		return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
    * @param   ctx		PosContext	�����
    * @return  SUCCESS	String		sucess ���ڿ�
    * @throws  none
    */
    protected void saveState(PosContext ctx) 
    {
		int nDeleteCount = 0;
		int nSize = 0;

		String sDsName = "dsOutBrchRtrn";
		PosDataset ds;

        if (ctx.get(sDsName) != null) {
			ds = (PosDataset) ctx.get(sDsName);
			nSize = ds.getRecordCount();

			for (int i = 0; i < nSize; i++) {
				PosRecord record = ds.getRecord(i);
				if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
					nDeleteCount = nDeleteCount + deleteBrchRtrn(record);
				}
			}
		}

        Util.setDeleteCount(ctx, nDeleteCount);
    }
    
    /**
     * <p> �������ȯ���������� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
	protected int deleteBrchRtrn(PosRecord record) {
		PosParameter param = new PosParameter();

		int i = 0;

		param.setWhereClauseParameter(i++, record.getAttribute("SR_NO".trim()));

		int dmlcount = this.getDao("boadao").delete("tbee_brch_rtrn_de001", param);

		return dmlcount;
	}

}
