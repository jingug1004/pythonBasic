/*================================================================================
 * 시스템			: 밸런스 입력
 * 소스파일 이름	: snis.rbm.business.rsm4051.activity.SaveBalanceInsert.java
 * 파일설명		: 밸런스 입력 / 수정
 * 작성자			: 이기석
 * 버전			: 1.0.0
 * 생성일자		: 2011-11-09
 * 최종수정일자	: 2011-11-16
 * 최종수정자		: 
 * 최종수정내용	: 
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
	 * SaveStates Activity를 실행시키기 위한 메소드
	 * </p>
	 * 
	 * @param ctx
	 *            PosContext 저장소
	 * @return SUCCESS String sucess 문자열
	 * @throws none
	 */
	public String runActivity(PosContext ctx) {
		// 사용자 정보 확인
		if (!setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS)) {
			Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
			return PosBizControlConstants.SUCCESS;
		}
		String MODE	= Util.getCtxStr(ctx, "MODE");		// 경륜 경정
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
	 * 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드
	 * </p>
	 * 
	 * @param ctx
	 *            PosContext 저장소
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
				// 레코드 값 모두 수정 입력
				nTempCnt = updatePCBalance(record);
				nSaveCount = nSaveCount + nTempCnt;
			}
		}

		Util.setSaveCount(ctx, nSaveCount);

	}

	/**
	 * <p>
	 * 밸런스 입력 / 수정
	 * </p>
	 * 
	 * @param record
	 *            PosRecord 데이타셋에 대한 하나의 레코드
	 * @return dmlcount int update 레코드 개수
	 * @throws none
	 */
	protected int updatePCBalance(PosRecord record) {
		PosParameter param = new PosParameter();

		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("RACE_DAY")); // 0:경주일
		param.setValueParamter(i++, record.getAttribute("SELL_CD")); // 1:
		param.setValueParamter(i++, record.getAttribute("TYPE_NO")); // 2:
		param.setValueParamter(i++, record.getAttribute("NOTYET_TOT_AMT")); // 3: 전회불 총액
		param.setValueParamter(i++, record.getAttribute("END_AMT")); // 4: 시효만료
		param.setValueParamter(i++, record.getAttribute("GITA_AMT1")); // 5: 환급관련 기타소득세
		param.setValueParamter(i++, record.getAttribute("GITA_AMT2")); // 6: 환급관련 주민세
		param.setValueParamter(i++, record.getAttribute("NOTYET_AMT")); // 7: 당일 미지급액
		param.setValueParamter(i++, record.getAttribute("CYCLE_AMT")); // 8:경륜지급
		param.setValueParamter(i++, record.getAttribute("BOAT_AMT")); // 9:경정지급
		param.setValueParamter(i++, record.getAttribute("IW_AMT")); // 10:구매권
		
		
		param.setValueParamter(i++, SESSION_USER_ID); // 9.로그인 사용자 아이디

		int dmlcount = this.getDao("rbmdao").update("rsm4051_m01", param);

		return dmlcount;
	}
	
	/**
	 * <p>
	 * 밸런스 삭제
	 * </p>
	 * 
	 * @param record
	 *            PosRecord 데이타셋에 대한 하나의 레코드
	 * @return dmlcount int update 레코드 개수
	 * @throws none
	 */
	protected int deletePCBalance(PosContext ctx) {
		PosParameter param = new PosParameter();
		String RACE_DAY	= Util.getCtxStr(ctx, "RACE_DAY");	// 경주일
		int i = 0;
		param.setValueParamter(i++, RACE_DAY); // 0:경주일
		int dmlcount = this.getDao("rbmdao").update("rsm4051_d01", param);

		return dmlcount;
	}
}
