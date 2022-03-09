/*================================================================================
 * �ý���			: LIS ���� ������ ��Ƽ ������Ʈ
 * �ҽ����� �̸�	: snis.rbm.business.rsm1010.activity.MultiLISFileSave.java
 * ���ϼ���		: LIS ���� �Ѳ����� ����
 * �ۼ���			: �̱⼮
 * ����			: 1.0.0
 * ��������		: 2011-12-09
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm1010.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.LISFileReader;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class MultiLISFileSave extends SnisActivity {
	public MultiLISFileSave() {
	}

	/**
	 * <p>
	 * SaveStates Activity�� �����Ű�� ���� �޼ҵ�
	 * </p>
	 * 
	 * @param ctx
	 *            PosContext �����
	 * @return SUCCESS String sucess ���ڿ�
	 * @throws none
	 */
	public String runActivity(PosContext ctx) {

		saveState(ctx);

		return PosBizControlConstants.SUCCESS;
	}

	/**
	 * <p>
	 * �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ�
	 * </p>
	 * 
	 * @param ctx
	 *            PosContext �����
	 * @return none
	 * @throws none
	 */
	protected void saveState(PosContext ctx) {
		int nSaveCount = 0;
		String sDsName = "";
		String sFileCd = "002";
		String sResult = "F";
		PosDataset ds;
		PosRow[] pr = null;
		int nSize = 0;
		int nTempCnt = 0;
		PosRowSet prs = null;
		sDsName = "dsLISFile";

		ArrayList aList = null;
		String sFName = "";
		if (ctx.get(sDsName) != null) {
			ds = (PosDataset) ctx.get(sDsName);

			PosRecord record = ds.getRecord(0);

			// 1. LIS ���� ��� �Է�
			String FILE_PATH = (String) record.getAttribute("FILE_PATH"); // ����
			// ���
			// �Էµ�
			File file = null;
			File[] files = new File(FILE_PATH).listFiles();
			String MEET_CD = "";
			String STND_YEAR = "";
			String DT = "";
			HashMap hMap = null;
			for (int fileI = 0; fileI < files.length; fileI++) {
				file = files[fileI];

				sFName = file.getName().substring(0,
						file.getName().indexOf("."));

				// 1. ��� ���� �ڵ� ã��
				PosParameter param = new PosParameter();
				param.setWhereClauseParameter(0, sFName);
				prs = this.getDao("rbmdao").find("rsm1010_s08", param);
				pr = prs.getAllRow();
				MEET_CD = (String) pr[0].getAttribute("MEET_CD");
				STND_YEAR = sFName.substring(0, 4);
				DT = sFName.substring(4, 8);
				
				// 2. �ӽ� ���̺� (TBRD_LIS_FILE_TMP) ���� �ش� ������ LIS ���� ������ ���� ����
				deleteLISFile(this.getDao("rbmdao"), sFName);
				System.out.println("MEET_CD : "+MEET_CD+" , STND_YEAR : "+STND_YEAR+" , DT : "+DT);

				// 3. LIS ���� ������ ȣ��

				LISFileReader lisfReader = new LISFileReader();
				aList = lisfReader.readFile(file + "");

				// 4. �ӽ� ���̺� (TBRD_LIS_FILE_TMP) �� LIS ���� ������ �Է�
				saveLISFile(aList,MEET_CD,STND_YEAR,DT);
				
				// 5. DAS DB �� �� ����
				searchCompareDasAndLIS(this.getDao("rbmdao"), sFName);
				/*
				 * for (int j = 0; j < aList.size(); j++) { aList2 = (ArrayList)
				 * aList.get(j); for (int k = 0; k < aList2.size(); k++) { //
				 * LIS �ش� ������ ������ hMap = (HashMap) aList2.get(k);
				 *  // ��� ���� �ڵ� ã�� System.out.println(hMap); } }
				 */

			}

			// 3. �ش� ������ MEET_CD �� ����

			// 3. �Է��Ϸ��� �⵵, ������ LIS FILE ������ ����

			// int nDeleteCount = lisf.deleteLISFile(this.getDao("rbmdao"),
			// aList2);

			// 4. DAS �����Ϳ��� �ش� LIS ���� �����Ϳ� ���� �ؼ� ����, �Է�

			// int dmlLISFile = 0;
			// int intDmlCount = lisf.saveLISFile(aList2); // LIS ���Ͽ� DAS

			// �Է��Ϸ��� �⵵, ���ڿ� �ش��ϴ� DAS ������ �˻�
			// pr = lisf.searchDasLIS(this.getDao("rbmdao"), record); // �����Ϳ� ��
			// �ϸ鼭
			// ����

			nSaveCount = nSaveCount + nTempCnt;
			// aList = null;

			sResult = "T";

		}

		Util.setReturnParam(ctx, "FILE_CD", sFileCd); // ���ε� ���� ���
		Util.setReturnParam(ctx, "RESULT", sResult); // ���
	}

	// LIS FILE ����

	public int saveLISFile(ArrayList aList, String MEET_CD, String STND_YEAR,
			String DT) {
		int intResult = 0; // �����

		// ������ �о�鿩 DB INSERT

		ArrayList alDBInsert = new ArrayList();
		HashMap hMap = null;
		for (int i = 0; i < aList.size(); i++) {

			hMap = (HashMap) aList.get(i);

			if (!"F".equals(strBrnchCode((String) hMap.get("DIVISION")))) {
				alDBInsert.add(hMap);
			}
		}

		String[] arrQuery = new String[alDBInsert.size()];

		for (int i = 0; i < alDBInsert.size(); i++) {

			hMap = (HashMap) alDBInsert.get(i);

			arrQuery[i] = " INSERT INTO TBRD_LIS_FILE_TMP ( /* MultiLISFileSave.saveLISFile */ \n"
					+ " MEET_CD,	\n"
					+ " STND_YEAR, 	\n"
					+ " DT,		\n"
					+ " BRNC_CD, 	\n"
					+ " DIVISION, 	\n"
					+ " GROSS_SALES,	\n"
					+ " CANCELS, 	\n"
					+ " TOT_SALES, 	\n"
					+ " CASHES,	\n"
					+ " NET_INCOME, 	\n"
					+ " DRAWS, 	\n"
					+ " RETURNS,	\n"
					+ " BALANCE, 	\n"

					+ " VERI, \n"
					+ " VERI_DT, \n"

					+ " FILE_PATH, 	\n"
					+ " FILE_NM, 	\n"
					+ " INST_ID,	\n"
					+ " INST_DT, 	\n"
					+ " UPDT_ID, 	\n"
					+ " UPDT_DT	 \n"
					+ " ) VALUES ( " + 
					" '"+ MEET_CD+ "',	\n"
					+ " '"+ STND_YEAR+ "', 	\n"
					+ " '"+ DT+ "',		\n"
					+ " '"+ strBrnchCode((String) hMap.get("DIVISION"))+ "', 	\n"
					+ "'"
					+ hMap.get("DIVISION")+ "', 	\n"
					+ "'"
					+ hMap.get("GROSS SALES")+ "' ,	\n"
					+ "'"
					+ hMap.get("CANCELS")+ "', 	\n"
					+ "'"
					+ hMap.get("TOT SALES")+ "', 	\n"
					+ "'"
					+ hMap.get("CASHES")+ "',	\n"
					+ "'"
					+ hMap.get("NET INCOME")+ "', 	\n"
					+ "'"
					+ hMap.get("DRAWS")+ "'  , 	\n"
					+ "'"
					+ hMap.get("RETURNS")+ "'  ,	\n"
					+ "'"
					+ hMap.get("BALANCE")+ "',	\n"

					+ " null  , 	\n" // ����
					+ "SYSDATE  , 	\n"
					+ " null, 	\n"
					+ " null, 	\n"
					+ "'SYSTEM',\n"
					+ " SYSDATE, 	\n" 
					+ "'SYSTEM', \n"
					+ " SYSDATE ) \n ";
			
		}


		int[] insertCounts = this.getRbmDao("rbmjdbcdao").executeBatch(arrQuery);
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

	// DAS ������ �� �� ����
	protected int searchCompareDasAndLIS(PosGenericDao dao, String RACE_DAY) {
		PosParameter param = new PosParameter();
		int i = 0;
		
		String STND_YEAR = RACE_DAY.substring(0, 4);
		String DT = RACE_DAY.substring(4, 8);
		
		param.setWhereClauseParameter(i++, STND_YEAR);
		param.setWhereClauseParameter(i++, DT);
		
		param.setWhereClauseParameter(i++, RACE_DAY);
		param.setWhereClauseParameter(i++, RACE_DAY);

		PosRowSet prs = null;
		prs = dao.find("rsm1010_s09", param); // ����Ѵ�.
		PosRow pr[] = prs.getAllRow();
		
		

		String[] arrQuery = new String[pr.length];

		for (int prI = 0; prI < pr.length; prI++) {
			arrQuery[prI] = " UPDATE  TBRD_LIS_FILE_TMP		   \n"+ 
			" SET  VERI = '"+pr[prI].getAttribute("TF")+"'        --  ����		   \n"+ 
			"    ,UPDT_ID      = 'SYSTEM'        -- ������ID  \n"+ 
			"    ,UPDT_DT      = SYSDATE  -- �����Ͻ�  \n"+             
			" WHERE  1=1				   \n"+ 
			" AND  MEET_CD   = '"+pr[prI].getAttribute("MEET_CD")+"'    -- ��� ���� ���� �ڵ�	   \n"+ 
			" AND  STND_YEAR = '"+pr[prI].getAttribute("STND_YEAR")+"'    -- ���س⵵	   \n"+ 
			" AND  DT      = '"+pr[prI].getAttribute("DT")+"'   -- ����		   \n"+ 
			" AND  BRNC_CD = '"+pr[prI].getAttribute("BRNC_CD")+"'   -- ���� �ڵ�	   \n";

		}

		int[] insertCounts = getRbmDao("rbmjdbcdao").executeBatch(arrQuery);
		int intResult = 0; // �����
		int failure_count = 0;

		if (failure_count == 0) {
			intResult = insertCounts.length;
		} else {
			intResult = 0;
		}

		return intResult;

		
	}

	public void deleteLISFile(PosGenericDao dao, String FILENAME) {
		PosParameter param = new PosParameter();
		String STND_YEAR = FILENAME.substring(0, 4);
		String DT = FILENAME.substring(4, 8);

		int i = 0;
		param.setWhereClauseParameter(i++, STND_YEAR);
		param.setWhereClauseParameter(i++, DT);

		dao.update("rsm1010_d02", param); // LIS FILE ������ ����
		// ����Ѵ�.
	}

	public String strBrnchCode(String strBrncNm) {

		String strResult = "";

		/* 2013.5.31 ������Ī����
		String strArrBrnchNm[] = { "SUWON", "SANGBONG", "ILSAN", "BUNDANG",
				"DONGDAEMUN", "JANGAN", "SANBON", "BUCHEON", "GWANAK",
				"GILEUM", "DANGSAN", "YUSEONG", "INCHEON", "SIHEUNG",
				"NONHYEON", "CHEONAN", "OLYMPIC", "UIJEONGBU", "ATL WINDOWS",
				"BOONDANG", "GIREUM", "YUSEONG", "OLYMPIC", "SECOND FLOOR",
				"THIRD FLOOR", "FOURTH FLOOR", "VIP", "SAM", "A PART" };
		*/
		String strArrBrnchNm[] = { "SUWON", "JUNGNANG", "ILSAN", "BUNDANG",
				"DONGDAEMUN", "JANGAN", "SANBON", "BUCHEON", "GWANAK",
				"SEONGBUK", "YEONGDEUNGPO", "DAEJEON", "INCHEON", "SIHEUNG",
				"GANGNAM", "CHEONAN", "OLYMPIC", "UIJEONGBU", "ATL WINDOWS",
				"BOONDANG", "GIREUM", "DAEJEON", "OLYMPIC", "SECOND FLOOR",
				"THIRD FLOOR", "FOURTH FLOOR", "VIP", "SAM", "A PART" };

		String[] strArrBrnchCd = { "11", "12", "13", "14", "15",// "SUWON",
																// "JUNGNANG",
																// "ILSAN",
																// "BUNDANG",
																// "DONGDAEMUN",
				"16", "17", "18", "19", "20",// "JANGAN", "SANBON",
												// "BUCHEON", "GWANAK",
												// "SEONGBUK",
				"21", "22", "23", "24", "25",// "YEONGDEUNGPO", "DAEJEON",
												// "INCHEON", "SIHEUNG",
												// "GANGNAM",
				"26", "27", "28", "06", "14",// "CHEONAN", "OLYMPIC",
												// "UIJEONGBU","ATL WINDOWS",
												// "BOONDANG",
				"20", "22", "27", "01", "02",// "GIREUM", "DAEJEON",
												// "OLYMPIC", "SECOND FLOOR",
												// "THIRD FLOOR",
				"03", "04", "08", "98" }; // "FOURTH FLOOR", "VIP", "SAM", "A
											// PART"};

		int intMachCount = 0;

		for (int i = 0; i < strArrBrnchNm.length; i++) {

			if (strBrncNm.trim().indexOf(strArrBrnchNm[i]) != -1) {

				strResult = strArrBrnchCd[i];
				intMachCount++;
			}
		}
		if (intMachCount == 0) {
			strResult = "F";
		}

		return strResult;
	}

}
