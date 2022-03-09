/*================================================================================
 * �ý���			: ������ ���� �Ǽ�
 * �ҽ����� �̸�	: snis.rbm.business.rsm3060.activity.SearchDIVTMSCNT.java
 * ���ϼ���		: ȸ���� �����
 * �ۼ���			: �̱⼮
 * ����			: 1.0.0
 * ��������		: 2011-11-14
 * ������������	: 							   
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm3060.activity;
import java.util.Calendar;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;

public class SearchDivTmsManCnt extends SnisActivity {
	public String runActivity(PosContext ctx) {
		
		String sRet = PosBizControlConstants.SUCCESS;
		try {			
			searchDivTmsCnt(ctx); // ������ �˻�
		} catch (Exception e) {
			logger.logError(e.getMessage());
		}
		
		return sRet;
	}

	private void searchDivTmsCnt(PosContext ctx) throws Exception {

		String MEET_CD      = Util.getCtxStr(ctx, "MEET_CD"); 		// ���:001 ,����:003, â������:002, �λ걳��:004
		String RACE_DAY_ST  = Util.getCtxStr(ctx, "RACE_DAY_ST");	// �˻� ���� ��
		String RACE_DAY_END = Util.getCtxStr(ctx, "RACE_DAY_END"); 	// �˻� ������ ��
		

		PosRowSet prs = null;

		PosParameter param = new PosParameter();
		int nIdx = 0;

		StringBuffer sbQuery = new StringBuffer();
		
		param.setWhereClauseParameter(nIdx++, MEET_CD);
		param.setWhereClauseParameter(nIdx++, MEET_CD);
		param.setWhereClauseParameter(nIdx++, MEET_CD);
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // �˻� ���� ��
		param.setWhereClauseParameter(nIdx++, RACE_DAY_END); // �˻� ������ ��

		sbQuery.append(Util.getQuery(ctx, "rsm3060_select02"));

		// ���س⵵ �� �̷������� �� �ȳ����� ���� 
		sbQuery.append(" \n");
		sbQuery.append(" AND V.STND_YEAR = SUBSTR( '"+RACE_DAY_ST+"' ,1,4)	-- 4:RACE_DAY_ST ���س⵵  \n");
		sbQuery.append(" AND T.STND_YEAR = SUBSTR( '"+RACE_DAY_ST+"' ,1,4)	-- 5:RACE_DAY_ST �˻� �Ⱓ ���� �� \n");
		
		sbQuery.append("\n");
		sbQuery.append(Util.getQuery(ctx, "rsm3060_groupby02"));
		
		prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(),param);
		
		String sOutDs = "dsDivTmsManCnt";
		ctx.put(sOutDs, prs);
		Util.addResultKey(ctx, sOutDs);
		
	}
}
