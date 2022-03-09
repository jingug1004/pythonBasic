/*================================================================================
 * 시스템			: Payoffs 파일 업로드
 * 소스파일 이름	: snis.rbm.business.rsm1020.activity.Payoffs.java
 * 파일설명		: PC파일 업로드
 * 작성자			: 이기석
 * 버전			: 1.0.0
 * 생성일자		: 2011-10-05
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm1020.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.FileReader;
import snis.rbm.common.util.RbmJdbcDao;
import snis.rbm.common.util.Util;


public class Payoffs {

	private PosContext ctx = null;
	private String sFilePath = "";
	private RbmJdbcDao rbmjdbcdao = null;
	private String user_id = "";
	
	public static String sGtms = "";	// 광명 회차
	public static String sGday = "";	// 광명 일차
	public static String sCtms = "";	// 창원 회차
	public static String sCday = "";	// 창원 일차
	public static String sMtms = "";	// 경정 회차
	public static String sMday = "";	// 경정 일차
	public static String sBtms = "";	// 부산 회차
	public static String sBday = "";	// 부산 일차
	public String[] aMeet = new String[] { "1", "2", "3", "4" };
	public Payoffs() {
	}

	

	public Payoffs(PosContext ctx, String sFilePath, RbmJdbcDao rbmjdbcdao,
			String session_user_id) {
		this.ctx = ctx;
		this.sFilePath = sFilePath;
		this.rbmjdbcdao = rbmjdbcdao;
		this.user_id = session_user_id;
		
		sGtms = (String) ctx.get("G_TMS"); // 광명 회차 Gtms
		sGday = (String) ctx.get("G_DAY"); // 광명 일차 Gday
		sCtms = (String) ctx.get("C_TMS"); // 창원 회차 Ctms
		sCday = (String) ctx.get("C_DAY"); // 창원 일차 Cday
		sMtms = (String) ctx.get("M_TMS"); // 경정 회차 Mtms
		sMday = (String) ctx.get("M_DAY"); // 경정 일차 Mday
		sBtms = (String) ctx.get("B_TMS"); // 부산 회차 Btms
		sBday = (String) ctx.get("B_DAY"); // 부산 일차 Bday

	}

	public int insertPayoffs(ArrayList aList) {
		
		SavePCFileUpload spu=new SavePCFileUpload();
		
		int intResult = 0; // 결과값
		
		String sStndYear = Util.getCtxStr(ctx, "STND_YEAR");
		String sSessionNo = Util.getCtxStr(ctx, "SESSION_NO");
		String sJigeupDt = Util.getCtxStr(ctx, "RACE_DAY");

		HashMap hMap = null;
		

		// 파일을 읽어들여 DB INSERT
		String[] arrQuery = new String[aList.size()];
		
		for (int i = 0; i < aList.size(); i++) {

			hMap = (HashMap) aList.get(i);
			

			arrQuery[i] = " INSERT INTO TBJI_PC_PAYOFFS ( /* Payoffs.insertPayoffs */ \n"+
			"      MEET_CD,         				\n"+
			"      STND_YEAR,         			\n"+
			"      TMS,         						\n"+
			"      DAY_ORD,         				\n"+
			"      SELL_CD,            			\n"+
			"      RACE_NO,         				\n"+
			"      POOL_CD,         				\n"+
			"      SESSION_NO,         			\n"+
			"      PERF_NO,         				\n"+
			"      NET_AMT,         				\n"+
			"      REFUND,         					\n"+
			"      COMMI_1,         				\n"+
			"      COMMI_2,         				\n"+
			"      COMMI_3,         				\n"+
			"      COMMI_4,         				\n"+
			"      COMMI_5,         				\n"+
			"      COMMI_6,         				\n"+
			"      NEGA_BREAK,         			\n"+
			"      POSI_BREAK,         			\n"+
			"      CNCL_AMT,		         		\n"+
			"      INST_ID,         				\n"+
			"      INST_DT,         				\n"+
			"      UPDT_ID,         				\n"+
			"      UPDT_DT          				\n"+
			" 			) VALUES (             	\n"+
			"  '"
					+ SavePCFileUpload.searchMeet((String) hMap.get("column1")) + "' \n"+	// MEET_CD
			", '" + sStndYear + "' "+	// STND_YEAR
			", '"
					+ SavePCFileUpload.searchTms((String) hMap.get("column1")) + "' \n"+	// TMS
			", '"
					+ SavePCFileUpload.searchDay((String) hMap.get("column1")) + "' \n"+
			", '"
					+ SavePCFileUpload.searchSell((String) hMap.get("column0")) + "' \n"+
			", '"
					+ SavePCFileUpload.searchRaceNo((String) hMap.get("column3")) + "' \n"+
			", '" + "00" + hMap.get("column4") + "' \n"+
			", '" + sSessionNo + "' \n"+
			", '" + hMap.get("column2") + "' \n"+
			", '" + hMap.get("column5") + "' \n"+
			", '" + hMap.get("column6") + "' \n"+
			", '" + hMap.get("column8") + "' \n"+
			", '" + hMap.get("column9") + "' \n"+
			", '" + hMap.get("column10") + "' \n"+
			", '" + hMap.get("column11") + "' \n"+
			", '" + hMap.get("column12") + "' \n"+
			", '" + hMap.get("column13") + "' \n"+
			", '" + hMap.get("column19") + "' \n"+
			", '" + hMap.get("column20") + "' \n"+
			", '" + hMap.get("column26") + "' \n"+
			", '" + user_id + "' \n"+
			", SYSDATE"+
			", '" + user_id + "' \n"+
			", SYSDATE ) \n";

//			System.out.println("arrQuery[i] : "+arrQuery[i]);
		}

		int[] insertCounts = rbmjdbcdao.executeBatch(arrQuery);
		int failure_count = 0;

		for (int i = 0; i < insertCounts.length; i++) {
			if (insertCounts[i] == -3) {
				failure_count++;
			}
		}

		if (failure_count == 0) {
			intResult = insertCounts.length;
		} else {
			intResult = 0;
		}

		return intResult;
	}
}
