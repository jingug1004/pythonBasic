/*================================================================================
 * 시스템			: 공통
 * 소스파일 이름	: snis.rbm.common.util.SearchCode.java
 * 파일설명		: 코드조회
 * 작성자			: 이영상
 * 버전			: 1.0.0
 * 생성일자		: 2011-07-30
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.common.util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

/**
 * 경주사업관리 공통코드를 조회하는 클래스이다.
 * 
 * @auther 이영상
 * @version 1.0
 */
public class SearchCode extends SnisActivity {
	public SearchCode() {
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
	 * 공통코드 조회
	 * </p>
	 * 
	 * @param ctx
	 *            PosContext 저장소
	 * @param record
	 *            PosRecord 코드그룹정보
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

		// 코드그룹값이 존재하면
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
