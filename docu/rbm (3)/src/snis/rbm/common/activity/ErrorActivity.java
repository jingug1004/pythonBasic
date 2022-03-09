package snis.rbm.common.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class ErrorActivity extends SnisActivity {

	/* (non-Javadoc)
	 * @see snis.cra.common.util.SnisActivity#runActivity(com.posdata.glue.context.PosContext)
	 */
	public String runActivity(PosContext ctx) {
		// 사용자 정보 확인
		if (!setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS)) {
			Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
			return PosBizControlConstants.SUCCESS;
		}

		this.rollbackTransaction("tx_rbm");

		Util.clearReturnParam(ctx);
		Util.setReturnParam(ctx, "USER_EXCEP", "1");
		Util.addReturnParam(ctx);

		return PosBizControlConstants.SUCCESS;

	}
}
