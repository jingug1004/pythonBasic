/*================================================================================
 * �ý���			: �뷱�� �Է�
 * �ҽ����� �̸�	: snis.rbm.business.rsm4051.activity.SaveBalanceInsert.java
 * ���ϼ���		: �뷱�� �Է� / ����
 * �ۼ���			: �̱⼮
 * ����			: 1.0.0
 * ��������		: 2011-11-09
 * ������������	: 2011-11-16
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm4051.activity;
import snis.rbm.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveBalanceInsert extends SnisActivity {
	public SaveBalanceInsert() {
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
		String MODE	= Util.getCtxStr(ctx, "MODE");		// ��� ����
		if("D".equals(MODE) )
		{
			deletePCBalance(ctx);
		}
		else
		{
			saveState(ctx);
		}

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

		String sDsName = "";

		PosDataset ds;
		int nSize = 0;
		int nTempCnt = 0;

		sDsName = "dsPCBalance";

		if (ctx.get(sDsName) != null) {
			ds = (PosDataset) ctx.get(sDsName);
			nSize = ds.getRecordCount();

			for (int i = 0; i < nSize; i++) {
				PosRecord record = ds.getRecord(i);
				// ���ڵ� �� ��� ���� �Է�
				nTempCnt = updatePCBalance(record);
				nSaveCount = nSaveCount + nTempCnt;
			}
		}

		Util.setSaveCount(ctx, nSaveCount);

	}

	/**
	 * <p>
	 * �뷱�� �Է� / ����
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return dmlcount int update ���ڵ� ����
	 * @throws none
	 */
	protected int updatePCBalance(PosRecord record) {
		PosParameter param = new PosParameter();

		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("RACE_DAY")); // 0:������
		param.setValueParamter(i++, record.getAttribute("SELL_CD")); // 1:
		param.setValueParamter(i++, record.getAttribute("TYPE_NO")); // 2:
		param.setValueParamter(i++, record.getAttribute("NOTYET_TOT_AMT")); // 3: ��ȸ�� �Ѿ�
		param.setValueParamter(i++, record.getAttribute("END_AMT")); // 4: ��ȿ����
		param.setValueParamter(i++, record.getAttribute("GITA_AMT1")); // 5: ȯ�ް��� ��Ÿ�ҵ漼
		param.setValueParamter(i++, record.getAttribute("GITA_AMT2")); // 6: ȯ�ް��� �ֹμ�
		param.setValueParamter(i++, record.getAttribute("NOTYET_AMT")); // 7: ���� �����޾�
		param.setValueParamter(i++, record.getAttribute("CYCLE_AMT")); // 8:�������
		param.setValueParamter(i++, record.getAttribute("BOAT_AMT")); // 9:��������
		param.setValueParamter(i++, record.getAttribute("IW_AMT")); // 10:���ű�
		
		
		param.setValueParamter(i++, SESSION_USER_ID); // 9.�α��� ����� ���̵�

		int dmlcount = this.getDao("rbmdao").update("rsm4051_m01", param);

		return dmlcount;
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
	protected int deletePCBalance(PosContext ctx) {
		PosParameter param = new PosParameter();
		String RACE_DAY	= Util.getCtxStr(ctx, "RACE_DAY");	// ������
		int i = 0;
		param.setValueParamter(i++, RACE_DAY); // 0:������
		int dmlcount = this.getDao("rbmdao").update("rsm4051_d01", param);

		return dmlcount;
	}
}
