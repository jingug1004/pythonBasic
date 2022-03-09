/*================================================================================
 * 시스템			: 공정 관리
 * 소스파일 이름	: snis.boa.fairness.e05070030.activity.SaveBrchRtrn.java
 * 파일설명		: 지점고액환급정보 저장
 * 작성자			: 정민화
 * 버전			: 1.0.0
 * 생성일자		: 2009-12-2
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.fairness.e05070030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SaveBrchRtrn extends SnisActivity
{    
    public SaveBrchRtrn()
    {
    }

    /**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
   	
    	// 사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}

		PosDataset ds;
		int nSize = 0;
		String sDsName = "dsOutBrchRtrn";

		if (ctx.get(sDsName) != null) {
			ds = (PosDataset) ctx.get(sDsName);
			nSize = ds.getRecordCount();

			for (int i = 0; i < nSize; i++) {
				PosRecord record = ds.getRecord(i);
				logger.logInfo(record);
			}
		}

		String raceDt = (String) ctx.get("RACE_DT");
		String raceNo = (String) ctx.get("RACE_NO");
		String poolCd = (String) ctx.get("POOL_CD");

		saveState(ctx, raceDt, raceNo, poolCd);

		return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  SUCCESS	String		sucess 문자열
    * @throws  none
    */
    protected void saveState(PosContext ctx, String raceDt, String raceNo, String poolCd) 
    {
		int nSaveCount = 0;
		//int nDeleteCount = 0;
		int nSize = 0;

		String sDsName = "dsOutBrchRtrn";
		PosDataset ds;

        if (ctx.get(sDsName) != null) {
			ds = (PosDataset) ctx.get(sDsName);
			nSize = ds.getRecordCount();

			for (int i = 0; i < nSize; i++) {
				PosRecord record = ds.getRecord(i);
				nSaveCount = nSaveCount + mergeBrchRtrn(record, raceDt, raceNo, poolCd);
			}
		}

		//nTotalCount = nSaveCount + nDeleteCount;

        Util.setDeleteCount(ctx, nSaveCount);
    }
    
    /**
     * <p> 지점고액환급정보 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	protected int mergeBrchRtrn(PosRecord record, String raceDt, String raceNo, String poolCd)
	{
		PosParameter param = new PosParameter();
		int i = 0;

/*
		double exaWonRate = Double.parseDouble(strnvl((String) record.getAttribute("EXA_RATE")));
		double exaWon1 = Double.parseDouble(strnvl((String) record.getAttribute("EXA_WON1")));
		double exaWon2 = Double.parseDouble(strnvl((String) record.getAttribute("EXA_WON2")));
		double exaWon3 = Double.parseDouble(strnvl((String) record.getAttribute("EXA_WON3")));
		double exaWon4 = Double.parseDouble(strnvl((String) record.getAttribute("EXA_WON4")));
		double exaWon5 = Double.parseDouble(strnvl((String) record.getAttribute("EXA_WON5")));
		double exaNo1 = Double.parseDouble(strnvl((String) record.getAttribute("EXA_NO1")));
		double exaNo2 = Double.parseDouble(strnvl((String) record.getAttribute("EXA_NO2")));
		double exaNo3 = Double.parseDouble(strnvl((String) record.getAttribute("EXA_NO3")));
		double exaNo4 = Double.parseDouble(strnvl((String) record.getAttribute("EXA_NO4")));
		double exaNo5 = Double.parseDouble(strnvl((String) record.getAttribute("EXA_NO5")));
		double quiWonRate = Double.parseDouble(strnvl((String) record.getAttribute("QUI_RATE")));
		double quiWon1 = Double.parseDouble(strnvl((String) record.getAttribute("QUI_WON1")));
		double quiWon2 = Double.parseDouble(strnvl((String) record.getAttribute("QUI_WON2")));
		double quiWon3 = Double.parseDouble(strnvl((String) record.getAttribute("QUI_WON3")));
		double quiWon4 = Double.parseDouble(strnvl((String) record.getAttribute("QUI_WON4")));
		double quiWon5 = Double.parseDouble(strnvl((String) record.getAttribute("QUI_WON5")));
		double quiNo1 = Double.parseDouble(strnvl((String) record.getAttribute("QUI_NO1")));
		double quiNo2 = Double.parseDouble(strnvl((String) record.getAttribute("QUI_NO2")));
		double quiNo3 = Double.parseDouble(strnvl((String) record.getAttribute("QUI_NO3")));
		double quiNo4 = Double.parseDouble(strnvl((String) record.getAttribute("QUI_NO4")));
		double quiNo5 = Double.parseDouble(strnvl((String) record.getAttribute("QUI_NO5")));
		double triWonRate = Double.parseDouble(strnvl((String) record.getAttribute("TRI_RATE")));
		double triWon1 = Double.parseDouble(strnvl((String) record.getAttribute("TRI_WON1")));
		double triWon2 = Double.parseDouble(strnvl((String) record.getAttribute("TRI_WON2")));
		double triWon3 = Double.parseDouble(strnvl((String) record.getAttribute("TRI_WON3")));
		double triWon4 = Double.parseDouble(strnvl((String) record.getAttribute("TRI_WON4")));
		double triWon5 = Double.parseDouble(strnvl((String) record.getAttribute("TRI_WON5")));
		double triNo1 = Double.parseDouble(strnvl((String) record.getAttribute("TRI_NO1")));
		double triNo2 = Double.parseDouble(strnvl((String) record.getAttribute("TRI_NO2")));
		double triNo3 = Double.parseDouble(strnvl((String) record.getAttribute("TRI_NO3")));
		double triNo4 = Double.parseDouble(strnvl((String) record.getAttribute("TRI_NO4")));
		double triNo5 = Double.parseDouble(strnvl((String) record.getAttribute("TRI_NO5")));
		
		double exaVal = (exaWonRate * exaWon1 * exaNo1) + (exaWonRate * exaWon2 * exaNo2) + (exaWonRate * exaWon3 * exaNo3) + (exaWonRate * exaWon4 * exaNo4) + (exaWonRate * exaWon5 * exaNo5);
		double quiVal = (quiWonRate * quiWon1 * quiNo1) + (quiWonRate * quiWon2 * quiNo2) + (quiWonRate * quiWon3 * quiNo3) + (quiWonRate * quiWon4 * quiNo4) + (quiWonRate * quiWon5 * quiNo5);
		double triVal = (triWonRate * triWon1 * triNo1) + (triWonRate * triWon2 * triNo2) + (triWonRate * triWon3 * triNo3) + (triWonRate * triWon4 * triNo4) + (triWonRate * triWon5 * triNo5);
		
		String totalReturn = String.valueOf(exaVal + quiVal + triVal);
*/
		param.setValueParamter(i++, record.getAttribute("SR_NO"));

		param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
		param.setValueParamter(i++, record.getAttribute("TMS"));
		param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
		param.setValueParamter(i++, record.getAttribute("RACE_NO"));
		param.setValueParamter(i++, record.getAttribute("OTB_NM"));
		param.setValueParamter(i++, record.getAttribute("CUST_NM"));
		param.setValueParamter(i++, record.getAttribute("BUY_PLC"));
		param.setValueParamter(i++, record.getAttribute("RETURN_PLC"));
		param.setValueParamter(i++, record.getAttribute("WINNER"));
		param.setValueParamter(i++, record.getAttribute("EXA_RATE"));
		param.setValueParamter(i++, record.getAttribute("EXA_WON1"));
		param.setValueParamter(i++, record.getAttribute("EXA_WON2"));
		param.setValueParamter(i++, record.getAttribute("EXA_WON3"));
		param.setValueParamter(i++, record.getAttribute("EXA_WON4"));
		param.setValueParamter(i++, record.getAttribute("EXA_WON5"));
		param.setValueParamter(i++, record.getAttribute("EXA_NO1"));
		param.setValueParamter(i++, record.getAttribute("EXA_NO2"));
		param.setValueParamter(i++, record.getAttribute("EXA_NO3"));
		param.setValueParamter(i++, record.getAttribute("EXA_NO4"));
		param.setValueParamter(i++, record.getAttribute("EXA_NO5"));
		param.setValueParamter(i++, record.getAttribute("QUI_RATE"));
		param.setValueParamter(i++, record.getAttribute("QUI_WON1"));
		param.setValueParamter(i++, record.getAttribute("QUI_WON2"));
		param.setValueParamter(i++, record.getAttribute("QUI_WON3"));
		param.setValueParamter(i++, record.getAttribute("QUI_WON4"));
		param.setValueParamter(i++, record.getAttribute("QUI_WON5"));
		param.setValueParamter(i++, record.getAttribute("QUI_NO1"));
		param.setValueParamter(i++, record.getAttribute("QUI_NO2"));
		param.setValueParamter(i++, record.getAttribute("QUI_NO3"));
		param.setValueParamter(i++, record.getAttribute("QUI_NO4"));
		param.setValueParamter(i++, record.getAttribute("QUI_NO5"));
		param.setValueParamter(i++, record.getAttribute("TRI_RATE"));
		param.setValueParamter(i++, record.getAttribute("TRI_WON1"));
		param.setValueParamter(i++, record.getAttribute("TRI_WON2"));
		param.setValueParamter(i++, record.getAttribute("TRI_WON3"));
		param.setValueParamter(i++, record.getAttribute("TRI_WON4"));
		param.setValueParamter(i++, record.getAttribute("TRI_WON5"));
		param.setValueParamter(i++, record.getAttribute("TRI_NO1"));
		param.setValueParamter(i++, record.getAttribute("TRI_NO2"));
		param.setValueParamter(i++, record.getAttribute("TRI_NO3"));
		param.setValueParamter(i++, record.getAttribute("TRI_NO4"));
		param.setValueParamter(i++, record.getAttribute("TRI_NO5"));
		//param.setValueParamter(i++, totalReturn);
		param.setValueParamter(i++, record.getAttribute("TOTAL_RETURN"));
		param.setValueParamter(i++, record.getAttribute("VEH_NO"));
		param.setValueParamter(i++, record.getAttribute("VEH_COLOR"));
		param.setValueParamter(i++, record.getAttribute("CUST_TEL"));
		param.setValueParamter(i++, record.getAttribute("CUST_ADDR"));
		param.setValueParamter(i++, record.getAttribute("RMK"));
		param.setValueParamter(i++, SESSION_USER_NM);
		param.setValueParamter(i++, record.getAttribute("REG_IP"));
		param.setValueParamter(i++, record.getAttribute("PHOTO_NM"));
		param.setValueParamter(i++, record.getAttribute("PHOTO_PATH"));
		param.setValueParamter(i++, record.getAttribute("PHOTO_TYPE"));
		param.setValueParamter(i++, record.getAttribute("PHOTO_SIZE"));
		param.setValueParamter(i++, record.getAttribute("FILE_NM"));
		param.setValueParamter(i++, record.getAttribute("FILE_PATH"));
		param.setValueParamter(i++, record.getAttribute("FILE_TYPE"));
		param.setValueParamter(i++, record.getAttribute("FILE_SIZE"));
		param.setValueParamter(i++, SESSION_USER_ID);

		param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
		param.setValueParamter(i++, record.getAttribute("TMS"));
		param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
		param.setValueParamter(i++, record.getAttribute("RACE_NO"));
		param.setValueParamter(i++, record.getAttribute("OTB_NM"));
		param.setValueParamter(i++, record.getAttribute("CUST_NM"));
		param.setValueParamter(i++, record.getAttribute("BUY_PLC"));
		param.setValueParamter(i++, record.getAttribute("RETURN_PLC"));
		param.setValueParamter(i++, record.getAttribute("WINNER"));
		param.setValueParamter(i++, record.getAttribute("EXA_RATE"));
		param.setValueParamter(i++, record.getAttribute("EXA_WON1"));
		param.setValueParamter(i++, record.getAttribute("EXA_WON2"));
		param.setValueParamter(i++, record.getAttribute("EXA_WON3"));
		param.setValueParamter(i++, record.getAttribute("EXA_WON4"));
		param.setValueParamter(i++, record.getAttribute("EXA_WON5"));
		param.setValueParamter(i++, record.getAttribute("EXA_NO1"));
		param.setValueParamter(i++, record.getAttribute("EXA_NO2"));
		param.setValueParamter(i++, record.getAttribute("EXA_NO3"));
		param.setValueParamter(i++, record.getAttribute("EXA_NO4"));
		param.setValueParamter(i++, record.getAttribute("EXA_NO5"));
		param.setValueParamter(i++, record.getAttribute("QUI_RATE"));
		param.setValueParamter(i++, record.getAttribute("QUI_WON1"));
		param.setValueParamter(i++, record.getAttribute("QUI_WON2"));
		param.setValueParamter(i++, record.getAttribute("QUI_WON3"));
		param.setValueParamter(i++, record.getAttribute("QUI_WON4"));
		param.setValueParamter(i++, record.getAttribute("QUI_WON5"));
		param.setValueParamter(i++, record.getAttribute("QUI_NO1"));
		param.setValueParamter(i++, record.getAttribute("QUI_NO2"));
		param.setValueParamter(i++, record.getAttribute("QUI_NO3"));
		param.setValueParamter(i++, record.getAttribute("QUI_NO4"));
		param.setValueParamter(i++, record.getAttribute("QUI_NO5"));
		param.setValueParamter(i++, record.getAttribute("TRI_RATE"));
		param.setValueParamter(i++, record.getAttribute("TRI_WON1"));
		param.setValueParamter(i++, record.getAttribute("TRI_WON2"));
		param.setValueParamter(i++, record.getAttribute("TRI_WON3"));
		param.setValueParamter(i++, record.getAttribute("TRI_WON4"));
		param.setValueParamter(i++, record.getAttribute("TRI_WON5"));
		param.setValueParamter(i++, record.getAttribute("TRI_NO1"));
		param.setValueParamter(i++, record.getAttribute("TRI_NO2"));
		param.setValueParamter(i++, record.getAttribute("TRI_NO3"));
		param.setValueParamter(i++, record.getAttribute("TRI_NO4"));
		param.setValueParamter(i++, record.getAttribute("TRI_NO5"));
		//param.setValueParamter(i++, totalReturn);
		param.setValueParamter(i++, record.getAttribute("TOTAL_RETURN"));
		param.setValueParamter(i++, record.getAttribute("VEH_NO"));
		param.setValueParamter(i++, record.getAttribute("VEH_COLOR"));
		param.setValueParamter(i++, record.getAttribute("CUST_TEL"));
		param.setValueParamter(i++, record.getAttribute("CUST_ADDR"));
		param.setValueParamter(i++, record.getAttribute("RMK"));
		param.setValueParamter(i++, SESSION_USER_NM);
		param.setValueParamter(i++, record.getAttribute("REG_IP"));
		param.setValueParamter(i++, record.getAttribute("PHOTO_NM"));
		param.setValueParamter(i++, record.getAttribute("PHOTO_PATH"));
		param.setValueParamter(i++, record.getAttribute("PHOTO_TYPE"));
		param.setValueParamter(i++, record.getAttribute("PHOTO_SIZE"));
		param.setValueParamter(i++, record.getAttribute("FILE_NM"));
		param.setValueParamter(i++, record.getAttribute("FILE_PATH"));
		param.setValueParamter(i++, record.getAttribute("FILE_TYPE"));
		param.setValueParamter(i++, record.getAttribute("FILE_SIZE"));
		param.setValueParamter(i++, SESSION_USER_ID);

        return this.getDao("boadao").update("tbee_brch_rtrn_ie001", param);
	}

	public String strnvl(String a) {
		return (a == null || "null".equals(a) ? "0" : ("".equals(a) ? "0" : a));
	}
	
}
