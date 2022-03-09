package snis.boa.financial.e07050020.activity;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveInsDetlList extends SnisActivity {

	public SaveInsDetlList() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see snis.cra.common.util.SnisActivity#runActivity(com.posdata.glue.context.PosContext)
	 */
	public String runActivity(PosContext ctx) {
		// 사용자 정보 확인
		if (!setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS)) {
			Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
			return PosBizControlConstants.SUCCESS;
		}
		
		String jobType = (String)ctx.get("JOBTYPE");
		
		if ("MAKE".equals(jobType)) // 자료생성
			return create(ctx);
		if ("DELETE".equals(jobType)) // 자료삭제
			return delete(ctx);
		else if ("CONFIRM".equals(jobType)) //확정, 확정취소
			return confirm(ctx);
		else
			return save(ctx);
	}

	/**
	 * <p>
	 * 자료생성
	 * </p>
	 * 
	 * @param ctx
	 * @return
	 */
	private String create(PosContext ctx) {
		PosParameter param = new PosParameter();
		int nIdx = 0;
		param.setValueParamter(nIdx++, (String)ctx.get("PAY_DT"));
		param.setValueParamter(nIdx++, (String)ctx.get("PAY_DT"));
		param.setValueParamter(nIdx++, SESSION_USER_ID);
		param.setValueParamter(nIdx++, SESSION_USER_ID);
		this.getDao("boadao").update("e07050020_i01", param);

		return PosBizControlConstants.SUCCESS;
	}
	
	/**
	 * <p>
	 * 자료삭제
	 * </p>
	 * 
	 * @param ctx
	 * @return
	 */
	private String delete(PosContext ctx) {
		PosParameter param = new PosParameter();
		int nIdx = 0;
		param.setValueParamter(nIdx++, (String)ctx.get("PAY_DT"));
		this.getDao("boadao").update("e07050020_d01", param);

		return PosBizControlConstants.SUCCESS;
	}
	
	/**
	 * <p>
	 * 자료확정
	 * </p>
	 * 
	 * @param ctx
	 * @return
	 */
	private String confirm(PosContext ctx) {
		PosParameter param = new PosParameter();
		int nIdx = 0;
		
		param.setValueParamter(nIdx++, SESSION_USER_ID);
		param.setValueParamter(nIdx++, (String)ctx.get("PAY_DT"));
		this.getDao("boadao").update("e07050020_u01", param);

		return PosBizControlConstants.SUCCESS;
	}
	
	/**
	 * <p>
	 * 저장
	 * </p>
	 * 
	 * @param ctx
	 * @return
	 */
	private String save(PosContext ctx) {
		return "";
	}
}
