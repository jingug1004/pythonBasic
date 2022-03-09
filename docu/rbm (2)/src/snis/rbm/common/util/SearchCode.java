/*================================================================================
 * �ý���			: ����
 * �ҽ����� �̸�	: snis.rbm.common.util.SearchCode.java
 * ���ϼ���		: �ڵ���ȸ
 * �ۼ���			: �̿���
 * ����			: 1.0.0
 * ��������		: 2011-07-30
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.common.util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

/**
 * ���ֻ������ �����ڵ带 ��ȸ�ϴ� Ŭ�����̴�.
 * 
 * @auther �̿���
 * @version 1.0
 */
public class SearchCode extends SnisActivity {
	public SearchCode() {
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
		PosDataset ds = (PosDataset) ctx.get("dsInComCd");
		int size = ds.getRecordCount();
		for (int i = 0; i < size; i++) {
			PosRecord record = ds.getRecord(i);
			logger.logInfo(record);

			getCommonCode(ctx, record);
		}

		return PosBizControlConstants.SUCCESS;
	}

	/**
	 * <p>
	 * �����ڵ� ��ȸ
	 * </p>
	 * 
	 * @param ctx
	 *            PosContext �����
	 * @param record
	 *            PosRecord �ڵ�׷�����
	 * @return none
	 * @throws none
	 */
	private void getCommonCode(PosContext ctx, PosRecord record) {
		PosParameter param = new PosParameter();
		PosRowSet rowset = null;

		String sResultKey = (String) record.getAttribute("DSNAME   ".trim());
		String sCDGrpID = (String) record.getAttribute("CD_GRP_ID".trim());
		String sQueryID = (String) record.getAttribute("QUERY_ID ".trim());
		String sTERM1 = (String) record.getAttribute("TERM1 ".trim());
		String sTERM2 = (String) record.getAttribute("TERM2 ".trim());
		String sTERM3 = (String) record.getAttribute("TERM3 ".trim());
		String sTERM4 = (String) record.getAttribute("TERM4 ".trim());
		String sTERM5 = (String) record.getAttribute("TERM5 ".trim());
		String sWhere = "";

		// �ڵ�׷찪�� �����ϸ�
		if (!Util.nullToStr(sCDGrpID).equals("")) {
			int i = 0;
			param.setWhereClauseParameter(i++, record.getAttribute("CD_GRP_ID".trim()));

			if (!Util.nullToStr(sTERM1).equals("")) {
				param.setWhereClauseParameter(i++, sTERM1);
				sWhere += "\n AND TSC.CD_TERM1 = ? \n  ";
			}
			if (!Util.nullToStr(sTERM2).equals("")) {
				param.setWhereClauseParameter(i++, sTERM2);
				sWhere += "\n AND TSC.CD_TERM2 = ? \n  ";
			}
			if (!Util.nullToStr(sTERM3).equals("")) {
				param.setWhereClauseParameter(i++, sTERM3);
				sWhere += "\n AND TSC.CD_TERM3 = ? \n  ";
			}
			if (!Util.nullToStr(sTERM4).equals("")) {
				param.setWhereClauseParameter(i++, sTERM4);
				sWhere += "\n AND TSC.CD_TERM4 = ? \n  ";
			}
			if (!Util.nullToStr(sTERM5).equals("")) {
				param.setWhereClauseParameter(i++, sTERM5);
				sWhere += "\n AND TSC.CD_TERM5 LIKE ? \n  ";
			}

			sWhere += "\n ORDER BY TSC.GRP_CD, TSC.ORD_NO, TSC.CD ";
			sQueryID = "common_code";
		} else if (!Util.nullToStr(sTERM1).equals("")) {

		}

		// rowset = this.getDao("rbmdao").find(sQueryID, param);
		try {
			rowset = Util.getRowSet(ctx, this.getDao("rbmdao"), sQueryID, sWhere, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ctx.put(sResultKey, rowset);

		Util.addResultKey(ctx, sResultKey);
	}
}
