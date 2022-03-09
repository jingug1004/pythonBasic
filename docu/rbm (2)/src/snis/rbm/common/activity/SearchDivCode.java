package snis.rbm.common.activity;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

public class SearchDivCode extends SnisActivity {

	public String runActivity(PosContext ctx) {
		String sRet = PosBizControlConstants.SUCCESS;
		try {
			String sCodeType = Util.getCtxStr(ctx, "CODE_TYPE");

			switch (Integer.parseInt(sCodeType)) {
			case 1: // 공통코드 조회
				getCommCode(ctx);
				break;
			case 2: // 사원정보 조회
				getEmpCode(ctx);
				break;
			}
		} catch (Exception e) {
			logger.logError(e.getMessage());
		}

		return sRet;
	}

	private void getCommCode(PosContext ctx) throws Exception {
		String sCodeBubun = Util.getCtxStr(ctx, "CODE_GUBUN");
		String sSearchValue = Util.getCtxStr(ctx, "SEARCH_VALUE");
		String sOutDs = Util.getCtxStr(ctx, "DS_OUT");

		PosParameter param = new PosParameter();
		int iParam = 0;
		param.setWhereClauseParameter(iParam++, sCodeBubun);
		param.setWhereClauseParameter(iParam++, sSearchValue);
		param.setWhereClauseParameter(iParam++, sSearchValue);

		PosRowSet prs = null;
		// case 1 : query.xml에 등록된 query 만 사용
		// prs = this.getDao("rbmdao").find("common_div_code", param);

		// case 2 : query.xml에 등록된 query 와 조회조건 query를 조합해서 사용
		StringBuffer query = new StringBuffer();
		query.append(" AND GRP_CD = ? ");
		query.append(" AND (CD LIKE '%' || NVL(?, CD) || '%' OR CD_NM LIKE '%' || NVL(?, CD_NM)  || '%')");
		query.append(" ORDER BY ORD_NO, CD ");

		prs = Util.getRowSet(ctx, this.getDao("rbmdao"), "common_div_code1", query.toString(), param);

		ctx.put(sOutDs, prs);
		Util.addResultKey(ctx, sOutDs);

	}

	private void getEmpCode(PosContext ctx) throws Exception {
		String sSearchValue = Util.getCtxStr(ctx, "SEARCH_VALUE");
		String sOutDs = Util.getCtxStr(ctx, "DS_OUT");

		PosParameter param = new PosParameter();
		int iParam = 0;
		param.setWhereClauseParameter(iParam++, sSearchValue);

		PosRowSet prs = null;

		StringBuffer query = new StringBuffer();
		query.append(" WHERE USER_ID LIKE '%'|| NVL(?, USER_ID) ||'%' ");
		query.append(" ORDER BY USER_ID ");

		prs = Util.getRowSet(ctx, this.getDao("rbmdao"), "common_user1", query.toString(), param);

		ctx.put(sOutDs, prs);
		Util.addResultKey(ctx, sOutDs);

	}
}
