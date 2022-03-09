package snis.rbm.business.rbs3040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;

import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;

import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.dao.vo.PosRowSetImpl;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.common.util.EncryptSHA256;

public class SearchSupplRegi extends SnisActivity {
	public SearchSupplRegi(){
		
	}

	
	/**
	 * <p>
	 * SaveStates Activity를 실행시키기 위한 메소드
	 * </p>
	 * 
	 * @param ctx
	 *            PosContext 저장소
	 * @return SUCCESS String sucess 문자열
	 * @throws none
	 */
	public String runActivity(PosContext ctx) {


		getSignPswd(ctx);

		return PosBizControlConstants.SUCCESS;
	}

	/**
	 * <p>
	 * 사용자정보 조회
	 * </p>
	 * 
	 * @param ctx
	 *            PosContext 저장소
	 * @param record
	 *            PosRecord 코드그룹정보
	 * @return none
	 * @throws none
	 */
	private void getSignPswd(PosContext ctx) {
		PosParameter param = new PosParameter();
		PosRowSet rowset;

		String sUserId = (String) ctx.get("USER_ID".trim());
		String sPswd = (String) ctx.get("PSWD   ".trim());
		String sPswdEn = EncryptSHA256.getEncryptSHA256(sPswd);


		// 코드그룹값이 존재하면
		int i = 0;
		param.setWhereClauseParameter(i++, sUserId);

		rowset = this.getDao("rbmdao").find("rbs3040_s03", param);

		if (rowset.hasNext()) {
			PosRow row = rowset.next();
		
			if (sPswdEn.equals(row.getAttribute("PSWD".trim()))) {
		
				row.setAttribute("ISVALID", "T");
			} else {
				row.setAttribute("ISVALID", "F");
			}
		}

		String sResultKey = "dsOutUser";
		ctx.put(sResultKey, rowset);
		Util.addResultKey(ctx, sResultKey);
	}	
}
