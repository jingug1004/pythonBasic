package snis.rbm.common.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveCopy extends SnisActivity {
	public String runActivity(PosContext ctx) {
		// 사용자 정보 확인
		if (!setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS)) {
			Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
			return PosBizControlConstants.SUCCESS;
		}

		PosParameter param = new PosParameter();
		int iParam = 0;
				
		StringBuffer query = new StringBuffer();
		query.append("DELETE FROM " + Util.getCtxStr(ctx, "TABLE") + "\n");
		query.append(" WHERE STND_YEAR = ? \n");
		query.append("   AND " + (Util.getCtxStr(ctx, "TABLE").equals("TBJB_TM_LEN") ? "RACE_MM" : "TMS") + " = ? \n") ;
		query.append("   AND DAY_ORD   = DECODE(?, '전체', DAY_ORD, ?) \n");

		param.setValueParamter(iParam++, Util.getCtxStr(ctx, "STND_YEAR1"));
		param.setValueParamter(iParam++, Util.getCtxStr(ctx, "TMS1"));
		param.setValueParamter(iParam++, Util.getCtxStr(ctx, "DAY_ORD1"));
		param.setValueParamter(iParam++, Util.getCtxStr(ctx, "DAY_ORD1"));
				
		this.getDao("rbmdao").updateByQueryStatement(query.toString(), param);
		
		query = null;
		query = new StringBuffer();
		
		param = null;
		param = new PosParameter();
		if (Util.getCtxStr(ctx, "TABLE").equals("TBJB_TM_LEN") || Util.getCtxStr(ctx, "TABLE").equals("TBJB_TMS_CROSS")) {
			iParam = 0;
			param.setValueParamter(iParam++, Util.getCtxStr(ctx, "STND_YEAR1"));
			param.setValueParamter(iParam++, Util.getCtxStr(ctx, "TMS1"));
			param.setValueParamter(iParam++, Util.getCtxStr(ctx, "DAY_ORD1"));
			param.setValueParamter(iParam++, Util.getCtxStr(ctx, "DAY_ORD1"));
			param.setValueParamter(iParam++, SESSION_USER_ID);
			param.setValueParamter(iParam++, SESSION_USER_ID);

			param.setValueParamter(iParam++, Util.getCtxStr(ctx, "STND_YEAR"));
			param.setValueParamter(iParam++, Util.getCtxStr(ctx, "TMS"));
			param.setValueParamter(iParam++, Util.getCtxStr(ctx, "DAY_ORD"));
			param.setValueParamter(iParam++, Util.getCtxStr(ctx, "DAY_ORD"));
			this.getDao("rbmdao").update("common_copy_" + Util.getCtxStr(ctx, "TABLE").toLowerCase(), param);
		}
		
		return PosBizControlConstants.SUCCESS;
	}
}
