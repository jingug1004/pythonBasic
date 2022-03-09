/*================================================================================
 * �ý���			: �뷱�� ��ȸ
 * �ҽ����� �̸�	: snis.rbm.business.rsm4050.activity.SavePCBalance.java
 * ���ϼ���		: �뷱�� ����
 * �ۼ���			: �̱⼮
 * ����			: 1.0.0
 * ��������		: 2011-11-16
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm4050.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SavePCBalance extends SnisActivity {
	public SavePCBalance() {
	}

	/**
	 * <p>
	 * SaveStates Activity�� �����Ű�� ���� �޼ҵ�
	 * </p>
	 * 
	 * @param ctx
	 *            PosContext �����
	 * @return SUCCESS String sucess ���ڿ�
	 * @throws none
	 */
	public String runActivity(PosContext ctx) {
		// ����� ���� Ȯ��
		if (!setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS)) {
			Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
			return PosBizControlConstants.SUCCESS;
		}

		saveState(ctx);

		return PosBizControlConstants.SUCCESS;
	}

	/**
	 * <p>
	 * �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ�
	 * </p>
	 * 
	 * @param ctx
	 *            PosContext �����
	 * @return none
	 * @throws none
	 */
	protected void saveState(PosContext ctx) {
		int nSaveCount = 0;
		int nDeleteCount = 0;
		String sDsName = "";

		PosDataset ds;
		int nSize = 0;
		int nTempCnt = 0;

		sDsName = "dsRaceInfo";

		if (ctx.get(sDsName) != null) {
			ds = (PosDataset) ctx.get(sDsName);
			nSize = ds.getRecordCount();

			for (int i = 0; i < nSize; i++) {
				PosRecord record = ds.getRecord(i);
				if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
					nDeleteCount = nDeleteCount + deletePCBalance(record);
				}
			}
		}

		Util.setDeleteCount(ctx, nDeleteCount);
	}

	/**
	 * <p>
	 * �뷱�� ����
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return dmlcount int update ���ڵ� ����
	 * @throws none
	 */
	protected int deletePCBalance(PosRecord record) {
		PosParameter param = new PosParameter();

		int i = 0;
		param.setValueParamter(i++, record.getAttribute("RACE_DAY")); // 0:������
		int dmlcount = this.getDao("rbmdao").update("rsm4050_d01", param);

		return dmlcount;
	}

}
