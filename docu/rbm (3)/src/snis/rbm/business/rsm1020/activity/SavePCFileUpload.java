/*================================================================================
 * �ý���			: ����Ư�̻���
 * �ҽ����� �̸�	: snis.rbm.business.rsm1010.activity.SavePCFileUpload.java
 * ���ϼ���		: PC���� ���ε�
 * �ۼ���			: �̱⼮
 * ����			: 1.0.0
 * ��������		: 2011-09-28
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm1020.activity;

import java.util.ArrayList;

import com.posdata.glue.bean.PosBeanFactory;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.FileReader;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SavePCFileUpload extends SnisActivity {
	// ���������� �����ͼ¸� ����
	protected String sDsGita = "dsGita";
	protected String sDsKtax = "dsKtax";
	protected String sDsPayoffs = "dsPayoffs";
	protected String sDsResults = "dsResults";

	// �������� �Ķ����
	public static String sStndYear = "";
	public static String sMeetGubun = "";
	public static String sDT = "";

	public static String sRaceDay = "";
	public static String sSessionNo = "";
	public static String sGtms = ""; // ���� ȸ��
	public static String sGday = ""; // ���� ����
	public static String sCtms = ""; // â�� ȸ��
	public static String sCday = ""; // â�� ����
	public static String sMtms = ""; // ���� ȸ��
	public static String sMday = ""; // ���� ����
	public static String sBtms = ""; // �λ� ȸ��
	public static String sBday = ""; // �λ� ����
	public static String sSellCd = "01"; // ����

	// �������� �ڵ� ����
	public static String sGita = "001";
	public static String sKtax = "002";
	public static String sPayoffs = "003";
	public static String sResults = "004";
	public static String sSatlit = "005";
	public static String sSellst = "006";
	public static String sTelmp = "007";
	public static String sWinnmp = "008";

	// New MeetCd ����
	public static String sGmeetCd = "001"; // ����
	public static String sCmeetCd = "002"; // â��
	public static String sMmeetCd = "003"; // ����(�̻縮)
	public static String sBmeetCd = "004"; // �λ�

	// New SellCd ����
	public static String sGsellCd = "01"; // ����
	public static String sCsellCd = "02"; // â��
	public static String sMsellCd = "03"; // ����(�̻縮)
	public static String sBsellCd = "04"; // �λ�

	// Query ID

	public static String sPayoQuery = "rsm1020_d04";
	public static String sResuQuery = "rsm1020_d05";

	public static String sSatlQuery = "sdl_pc_satlit_del";
	public static String sSatlSumQuery = "sdl_pc_satlit_sum_del";
	public static String sSatlQueryMyCat = "mycat_satlit_del";
	public static String sSellQuery = "sdl_pc_sellst_del";
	public static String sSellNEQuery = "sdl_pc_sellst_ne_del";
	public static String sSellQueryMyCat = "mycat_sellst_del";
	public static String sTelmQuery = "sdl_pc_telmp_del";
	public static String sTelmNEQuery = "sdl_pc_telmp_ne_del";
	public static String sWinnQuery = "sdl_pc_winnmp_del";
	public static String sWinnNEQuery = "sdl_pc_winnmp_ne_del";
	public static String sSdlDTQueryMyCat = "rsm1020_d07"; // MyCat-TBES_SDL_DT����

	// dao ����
	public static String rbmdao = "rbmdao"; // ������νý���
	public static String rbmjdbcdao = "rbmjdbcdao"; // 

	public String[] aMeet = new String[] { "1", "2", "3", "4" };

	public SavePCFileUpload() {

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

		// ����� ���� Ȯ��
		if (!setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS)) {
			Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
			return PosBizControlConstants.SUCCESS;
		}
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
		int nDeleteCount = 0;
		int nSize = 0;

		sStndYear = (String) ctx.get("STND_YEAR"); // �⵵ StndYear
		sDT = (String) ctx.get("DT"); // ����
		sMeetGubun = (String) ctx.get("MEET_GUBUN"); // ���� MeetGubun
		sRaceDay = (String) ctx.get("RACE_DAY"); // ������ RaceDay
		sSessionNo = (String) ctx.get("SESSION_NO"); // SessionNo
		sGtms = (String) ctx.get("G_TMS"); // ���� ȸ�� Gtms
		sGday = (String) ctx.get("G_DAY"); // ���� ���� Gday
		sCtms = (String) ctx.get("C_TMS"); // â�� ȸ�� Ctms
		sCday = (String) ctx.get("C_DAY"); // â�� ���� Cday
		sMtms = (String) ctx.get("M_TMS"); // ���� ȸ�� Mtms
		sMday = (String) ctx.get("M_DAY"); // ���� ���� Mday
		sBtms = (String) ctx.get("B_TMS"); // �λ� ȸ�� Btms
		sBday = (String) ctx.get("B_DAY"); // �λ� ���� Bday

		// �����ϴ� ���, ���� �ڵ� �迭 ����

		if (sMeetGubun.equals("003"))
			rbmdao = "rbmdao"; // "boaintradao";
		if (sMeetGubun.equals("003"))
			sSellCd = sMsellCd;

		// dsPcUp ���� ���ε� �κ�
		PosDataset ds = (PosDataset) ctx.get("dsPcUp");
		
		
		String strFILE_VERI = "002";

		int nGita = 0;
		int nKtax = 0;

		int nPayoffs = 0;
		int nResults = 0;
		int nSatlit = 0;
		int nSellst = 0;
		int nTelmp = 0;
		int nWinnmp = 0;

		// ���� ���ε� Ȯ�ο� ī��Ʈ
		int nGitaCount = 0;
		int nKtaxCount = 0;

		int nPayoffsCount = 0;
		int nResultsCount = 0;
		int nSatlitCount = 0;
		int nSellstCount = 0;
		int nTelmpCount = 0;
		int nWinnmpCount = 0;

		PosRecord record = null;
		String sDT = "";

		
		// ������ �Է� �Ǿ��� ���� ī��Ʈ ����
		PosRow[] prFileCnt = searchPCFileCount(this.getDao("rbmdao"), ctx);
		for (int i = 0; i < prFileCnt.length; i++) {
			
			strFILE_VERI = (String) prFileCnt[i].getAttribute("FILE_VERI");
			nGita = Integer.parseInt((String) prFileCnt[i]
					.getAttribute("GITA_FILE_ROW_CNT"));
			nKtax = Integer.parseInt((String) prFileCnt[i]
					.getAttribute("KTAX_FILE_ROW_CNT"));
			nPayoffs = Integer.parseInt((String) prFileCnt[i]
					.getAttribute("PAYO_FILE_ROW_CNT"));
			nResults = Integer.parseInt((String) prFileCnt[i]
					.getAttribute("RESU_FILE_ROW_CNT"));
			nSatlit = Integer.parseInt((String) prFileCnt[i]
					.getAttribute("SATL_FILE_ROW_CNT"));
			nSellst = Integer.parseInt((String) prFileCnt[i]
					.getAttribute("SELL_FILE_ROW_CNT"));
			nTelmp = Integer.parseInt((String) prFileCnt[i]
					.getAttribute("TELM_FILE_ROW_CNT"));
			nWinnmp = Integer.parseInt((String) prFileCnt[i]
					.getAttribute("WINN_FILE_ROW_CNT"));
		}

		for (int i = 0; i < ds.getRecordCount(); i++) {
			record = ds.getRecord(i);

			// 1. GITA ���� ���ε�
			String FILE_UPLOAD_NAME = record.getAttribute("FILE_UPLOAD_NAME") == null ? ""
					: (String) record.getAttribute("FILE_UPLOAD_NAME");

			if ("001".equals(record.getAttribute("FILE_CD"))
					&& !"".equals(FILE_UPLOAD_NAME)) {
				nGita = Util.nullToInt((String) record.getAttribute("ROWCNT"));
				sDT = (String) record.getAttribute("DT");

				// GITA ���� ������ �Է�
				Gita objGita = new Gita(ctx, (String) record
						.getAttribute("FILE_PATH"), this
						.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);

				// 1-2. TBJI_PC_GITA ���̺� ������ �Է�
				int dmlcount = objGita.insertGita();

				// 1-2-1. TBJI_PC_GITA�� �׸�ī�� �߸����� ����
				updateGitaGrn(sStndYear, sDT);
				
				// 1-3. TBJI_PC_FILE ���̺��� ����(����ó, ���س⵵,����, �����ڵ�)���� �̷� ����
				deleteFileUpHistory(sGita, record);

				// 1-4. TBJI_PC_FILE ���̺� �̷� �Է�.
				// ���������� Ȯ���Ǿ� ������ ���� �Է� �ȵǱ� ������ Ŭ���̾�Ʈ ī��Ʈ�� �Է�
				insertFileUpHistory(sGita, nGita, record, (String) record
						.getAttribute("FILE_PATH"), (String) record
						.getAttribute("FILE_UPLOAD_NAME"));

				// �Է� ī��Ʈ ��ȯ��Ŵ
				nGitaCount	= dmlcount;	// ���� �Է� ���� �Ǵ� ī��Ʈ
				nGita		= dmlcount;	// ��ȯ��ų ī��Ʈ
				objGita = null;

			}

			// 2. KTAX ���� ���ε�
			if ("002".equals(record.getAttribute("FILE_CD"))
					&& !"".equals(FILE_UPLOAD_NAME)) {
				nKtax = Util.nullToInt((String) record.getAttribute("ROWCNT"));
				// KTAX ���� ������ �Է�
				Ktax objKtax = new Ktax(ctx, (String) record
						.getAttribute("FILE_PATH"), this
						.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);

				// 2-1. TBRD_CFM_CNTNT ���̺� ������ �Է�
				// 1�� Ȯ�� �񱳿��� �� ���� �񱳷� �ٲ� -> �ٽ� 1�� Ȯ�� �񱳷� �ٲ�
				// int dmlTbrdCfmCntnt = objKtax.insertTbrdCfmCntnt();

				// 2-2. TBJI_PC_TAX ���̺� ������ �Է�
				int dmlcount = objKtax.insertKtax();
				
				// 2-2-1. TBJI_PC_GITA�� �׸�ī�� �߸����� ����
				updateTaxGrn(sStndYear, sDT);


				// 2-3. TBJI_PC_FILE ���̺��� ����(����ó, ���س⵵,����, �����ڵ�)���� �̷� ����
				deleteFileUpHistory(sKtax, record);

				// 2-4 TBJI_PC_FILE ���̺� �̷��Է�
				// ���������� Ȯ���Ǿ� ������ ���� �Է� �ȵǱ� ������ Ŭ���̾�Ʈ ī��Ʈ�� �Է�
				insertFileUpHistory(sKtax, nKtax, record, (String) record
						.getAttribute("FILE_PATH"), (String) record
						.getAttribute("FILE_UPLOAD_NAME"));

				// �Է� ī��Ʈ ��ȯ��Ŵ
				nKtaxCount = dmlcount;	// ���� �Է� ���� �Ǵ� ī��Ʈ
				nKtax		= dmlcount;	// ��ȯ��ų ī��Ʈ
				objKtax = null;

			}

			// 3. PAYOFFS ���� ���ε�
			if ("003".equals(record.getAttribute("FILE_CD"))
					&& !"".equals(FILE_UPLOAD_NAME)) {
				nPayoffs = Util.nullToInt((String) record
						.getAttribute("ROWCNT"));
				ArrayList aList = new ArrayList(); // ���� �����Ϳ� ArrayList
				FileReader fReader = new FileReader(); // ���� ������ �д� Ŭ����

				// 3-1. PAYOFFS ���Ͽ� �����Ͱ� �ִ��� Ȯ��
				aList = fReader.alReadFile((String) record
						.getAttribute("FILE_PATH"));
				if (aList.size() > 0) {

					// 3-2. TBJI_PC_PAYOFFS ���̺� �ش� ���� ������ ���� ����
					deletePCFileContents("rsm1020_d08", sRaceDay);
				}

				Payoffs objPayoffs = new Payoffs(ctx, (String) record
						.getAttribute("FILE_PATH"), this
						.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);

				// 3-3. TBJI_PC_PAYOFFS ���̺� ������ �Է�
				int dmlcount = objPayoffs.insertPayoffs(aList);

				// 3-4. TBJI_PC_FILE ���̺��� ����(����ó,���س⵵, ����, �����ڵ�)���� �̷� ����
				deleteFileUpHistory(sPayoffs, record);

				// 3-5. TBJI_PC_FILE ���̺� �̷� �Է�
				// ���������� Ȯ���Ǿ� ������ ���� �Է� �ȵǱ� ������ Ŭ���̾�Ʈ ī��Ʈ�� �Է�
				insertFileUpHistory(sPayoffs, nPayoffs, record, (String) record
						.getAttribute("FILE_PATH"), (String) record
						.getAttribute("FILE_UPLOAD_NAME"));

				// �Է� ī��Ʈ ��ȯ��Ŵ

				nPayoffsCount	= dmlcount;	// ���� �Է� ���� �Ǵ� ī��Ʈ
				nPayoffs		= dmlcount;	// ��ȯ��ų ī��Ʈ
				aList = null;
				fReader = null;
				objPayoffs = null;
			}

			// 4. RESULTS ���� ���ε�
			if ("004".equals(record.getAttribute("FILE_CD"))
					&& !"".equals(FILE_UPLOAD_NAME)) {
				nResults = Util.nullToInt((String) record
						.getAttribute("ROWCNT"));
				ArrayList aList = new ArrayList(); // ���� �����Ϳ� ArrayList
				FileReader fReader = new FileReader(); // ���� ������ �д� Ŭ����

				// 4-1. RESULTS ���Ͽ� �����Ͱ� �ִ��� Ȯ��
				aList = fReader.alReadFile((String) record
						.getAttribute("FILE_PATH"));
				if (aList.size() > 0) {
					// 4-2. TBJI_PC_RESULT ���̺� �ش� ���� ������ ���� ����
					deletePCFileContents("rsm1020_d09", sRaceDay);
				}

				Results objResults = new Results(ctx, (String) record
						.getAttribute("FILE_PATH"), this
						.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);

				// 4-3. TBJI_PC_RESULT ���̺� ������ �Է�
				int dmlcount = objResults.insertResults(aList);

				// 4-4. TBJI_PC_FILE ���̺��� ����(����ó,���س⵵, ����, �����ڵ�)���� �̷� ����
				deleteFileUpHistory(sResults, record);

				// 4-5. TBJI_PC_FILE ���̺� �̷� �Է�
				// ���������� Ȯ���Ǿ� ������ ���� �Է� �ȵǱ� ������ Ŭ���̾�Ʈ ī��Ʈ�� �Է�
				insertFileUpHistory(sResults, nResults, record, (String) record
						.getAttribute("FILE_PATH"), (String) record
						.getAttribute("FILE_UPLOAD_NAME"));

				// �Է� ī��Ʈ ��ȯ��Ŵ

				nResultsCount	= dmlcount;	// ���� �Է� ���� �Ǵ� ī��Ʈ
				nResults		= dmlcount;	// ��ȯ��ų ī��Ʈ
				aList = null;
				fReader = null;
				objResults = null;

			}

			// 5. SATLIT ���� ���ε�
			if ("005".equals(record.getAttribute("FILE_CD"))
					&& !"".equals(FILE_UPLOAD_NAME)) {
				nSatlit = Util
						.nullToInt((String) record.getAttribute("ROWCNT"));
				// 5-1. SATLIT ���Ͽ� �����Ͱ� �ִ��� Ȯ��
				ArrayList aList = new ArrayList(); // ���� �����Ϳ� ArrayList
				FileReader fReader = new FileReader(); // ���� ������ �д� Ŭ����
				aList = fReader.alReadFile((String) record
						.getAttribute("FILE_PATH")); // SATLIT ���� �б�
				if (aList.size() > 0) {
					// 5-2. TBJI_PC_SATLIT ���̺� �ش� ���� ������ ���� ����
					deletePCFileContents("rsm1020_d10", sRaceDay);

				}

				Satlit objSatlit = new Satlit(ctx, (String) record
						.getAttribute("FILE_PATH"), this
						.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);

				// 5-3. TBJI_PC_SATLIT ���̺� ������ �Է�
				int dmlcount = objSatlit.insertSatlit(aList);

				// 5-4. TBJI_PC_FILE ���̺��� ����(����ó,���س⵵, ����, �����ڵ�)���� �̷� ����
				deleteFileUpHistory(sSatlit, record);

				// 5-5. TBJI_PC_FILE ���̺� �̷� �Է�
				// ���������� Ȯ���Ǿ� ������ ���� �Է� �ȵǱ� ������ Ŭ���̾�Ʈ ī��Ʈ�� �Է�
				insertFileUpHistory(sSatlit, nSatlit, record, (String) record
						.getAttribute("FILE_PATH"), (String) record
						.getAttribute("FILE_UPLOAD_NAME"));

				// MyCat Data insert
				// MyCat �������� �Ķ���ʹ� ��� �ȵ�
				MyCat objMyCat = new MyCat(ctx, (String) record
						.getAttribute("FILE_PATH"), this
						.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);

				

				// 5-6. TBJI_PC_SATLIT ���̺��� ����(����,�׸�ī�� )���� �̷� ����
				deletePCFileContents("rsm1020_d16", sRaceDay);

				// 5-7. VW_MYCAT_SALES -> TBJI_PC_SATLIT ������ ����
				objMyCat.insertSatlitMyCat(this.getDao(rbmdao), sRaceDay,
						sSessionNo);

				// 5-8. TBES_SDL_DT_BAK ���� MyCat ������ ����
				deletePCFileContents("rsm1020_d22", sRaceDay);

				// 5-8. TBES_SDL_DT -> TBES_SDL_DT_BAK MyCat ������ ����
				insertData("rsm1020_i06", sRaceDay);

				// 5-9. TBES_SDL_DT ���� MyCat ������ ����
				deletePCFileContents("rsm1020_d06", sRaceDay);

				// 5-8. VW_MYCAT_SALES -> TBES_SDL_DT ������ ����
				objMyCat.insertTbesSdlDTMyCat(this.getDao(rbmdao), sRaceDay);

				// 5-9. TBJI_PC_SATLIT_SUM �� �հ赥���� ����
				deletePCFileContents("rsm1020_d14", sRaceDay);

				// 5-10. TBJI_PC_SATLIT -> TBJI_PC_SATLIT_SUM �� �հ赥���� �Է�
				insertData("sdl_pc_satlit_sum_ins", sRaceDay);

				// 5-11. TBES_SDL_DT_SUM ������ ����
				deletePCFileContents("rsm1020_d21", sRaceDay);

				// 5-12. TBES_SDL_DT_SUM �� TBES_SDL_DT �� �ִ� ������, ����, ���� ������
				// ������ �Է�
				insertData("rsm1020_i20", sRaceDay);

				// 5-13. TBES_SDL_DT_SUM �� TBES_SDL_DT �� �ִ� ������, ����, ���� ������
				// ������ �Է�
				insertData("rsm1020_i21", sRaceDay);

				// �Է� ī��Ʈ ��ȯ��Ŵ
				nSatlit = dmlcount;
				nSatlitCount = dmlcount;
				aList = null;
				fReader = null;
				objSatlit = null;
				objMyCat = null;
			}

			// 6. SELLST ���� ���ε�
			if ("006".equals(record.getAttribute("FILE_CD"))
					&& !"".equals(FILE_UPLOAD_NAME)) {
				nSellst = Util
						.nullToInt((String) record.getAttribute("ROWCNT"));
				ArrayList aList = new ArrayList(); // ���� �����Ϳ� ArrayList
				FileReader fReader = new FileReader(); // ���� ������ �д� Ŭ����
				aList = fReader.alReadFile((String) record
						.getAttribute("FILE_PATH")); 

				// 6-1. SELLST ���Ͽ� �����Ͱ��ִ��� Ȯ��
				if (aList.size() > 0) {

					// 6-2. TBJI_PC_SELLST ���̺� �ش� ���� ������ ���� ����
					deletePCFileContents("rsm1020_d11", sRaceDay);
				}

				Sellst objSellst = new Sellst(ctx, (String) record
						.getAttribute("FILE_PATH"), this
						.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);

				// 6-3. TBJI_PC_SELLST ���̺� ������ �Է�
				int dmlcount = objSellst.insertSellst(aList);

				// 6-4. TBJI_PC_FILE ���̺��� ����(����ó,���س⵵, ����, �����ڵ�)���� �̷� ����
				deleteFileUpHistory(sSellst, record);

				// 6-5. TBJI_PC_FILE ���̺� �̷� �Է�.
				// ���������� Ȯ���Ǿ� ������ ���� �Է� �ȵǱ� ������ Ŭ���̾�Ʈ ī��Ʈ�� �Է�
				insertFileUpHistory(sSellst, nSellst, record, (String) record
						.getAttribute("FILE_PATH"), (String) record
						.getAttribute("FILE_UPLOAD_NAME"));

				// MyCat Data insert
				// MyCat �������� �Ķ���ʹ� ��� �ȵ�
				MyCat objMyCat = new MyCat(ctx, (String) record
						.getAttribute("FILE_PATH"), this
						.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);

				// 6-6 TBJI_PC_SELLST MyCat ������ ����

				deletePCFileContents("rsm1020_d17", sRaceDay);

				// 6-7 VW_MYCAT_SALES -> TBJI_PC_SELLST MyCat ������ ����
				objMyCat.insertSellstMyCat(this.getDao(rbmdao), sRaceDay,
						sSessionNo);

				// �Է� ī��Ʈ ��ȯ��Ŵ
				nSellst = dmlcount;
				nSellstCount = dmlcount;
				aList = null;
				fReader = null;
				objSellst = null;
				objMyCat = null;
			}

			// 7. TELMP ���� ���ε�
			if ("007".equals(record.getAttribute("FILE_CD"))
					&& !"".equals(FILE_UPLOAD_NAME)) {
				nTelmp = Util.nullToInt((String) record.getAttribute("ROWCNT"));
				ArrayList aList = new ArrayList(); // ���� �����Ϳ� ArrayList
				FileReader fReader = new FileReader(); // ���� ������ �д� Ŭ����

				// 7-1. TELMP ���Ͽ� �����Ͱ� �ִ��� Ȯ��
				aList = fReader.alReadFile((String) record
						.getAttribute("FILE_PATH"));
				if (aList.size() > 0) {

					// 7-2. TBJI_PC_TELMP ���̺� �ش� ���� ������ ���� ����
					deleteTelmpFileContents("rsm1020_d15", sRaceDay);
				}

				Telmp objTelmp = new Telmp(ctx, (String) record
						.getAttribute("FILE_PATH"), this
						.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);

				// 7-3. TBJI_PC_TELMP ���̺� ������ �Է�
				int dmlcount = objTelmp.insertTelmp(aList);

				// 7-4. TBJI_PC_FILE ���̺��� ����(����ó,���س⵵, ����, �����ڵ�)���� �̷� ����
				deleteFileUpHistory(sTelmp, record);

				// 7-5. TBJI_PC_FILE ���̺� �̷� �Է�
				// ���������� Ȯ���Ǿ� ������ ���� �Է� �ȵǱ� ������ Ŭ���̾�Ʈ ī��Ʈ�� �Է�
				insertFileUpHistory(sTelmp, nTelmp, record, (String) record
						.getAttribute("FILE_PATH"), (String) record
						.getAttribute("FILE_UPLOAD_NAME"));

				// �Է� ī��Ʈ ��ȯ��Ŵ
				nTelmp = dmlcount;
				nTelmpCount = dmlcount;
				aList = null;
				fReader = null;
				objTelmp = null;
			}

			// 8. WINNMP ���� ���ε�
			if ("008".equals(record.getAttribute("FILE_CD"))
					&& !"".equals(FILE_UPLOAD_NAME)) {
				nWinnmp = Util
						.nullToInt((String) record.getAttribute("ROWCNT"));
				ArrayList aList = new ArrayList(); // ���� �����Ϳ� ArrayList
				FileReader fReader = new FileReader(); // ���� ������ �д� Ŭ����

				// 8-1. WINNMP ���Ͽ� �����Ͱ��ִ��� Ȯ��
				aList = fReader.alReadFile((String) record
						.getAttribute("FILE_PATH"));
				if (aList.size() > 0) {

					// 8-2. TBJI_PC_WINNMP ���̺� �ش� ���� ������ ���� ����
					deletePCFileContents("rsm1020_d13", sRaceDay);
				}

				Winnmp objWinnmp = new Winnmp(ctx, (String) record
						.getAttribute("FILE_PATH"), this
						.getRbmDao("rbmjdbcdao"), SESSION_USER_ID, sMeetGubun);

				// 8-3. TBJI_PC_WINNMP ���̺� ������ �Է�
				int dmlcount = objWinnmp.insertWinnmp(aList);

				// 8-4. TBJI_PC_FILE ���̺��� ����(����ó,���س⵵, ����, �����ڵ�)���� �̷� ����
				deleteFileUpHistory(sWinnmp, record);

				// 8-5. TBJI_PC_FILE ���̺� �̷� �Է�
				// ���������� Ȯ���Ǿ� ������ ���� �Է� �ȵǱ� ������ Ŭ���̾�Ʈ ī��Ʈ�� �Է�
				insertFileUpHistory(sWinnmp, nWinnmp, record, (String) record
						.getAttribute("FILE_PATH"), (String) record
						.getAttribute("FILE_UPLOAD_NAME"));

				// �Է� ī��Ʈ ��ȯ��Ŵ
				nWinnmp = dmlcount;
				nWinnmpCount = dmlcount;
				aList = null;
				fReader = null;
				objWinnmp = null;
			}
			continue;
		}

		// ������ ���ε� �ߴ��� Ȯ�ο�(���� ����, ���� ���� �� �� ���)

		int nFileCount = 0;
		int[] nArrFileCount = new int[8];
		nArrFileCount[0] = nGitaCount;
		nArrFileCount[1] = nKtaxCount;
		nArrFileCount[2] = nPayoffsCount;
		nArrFileCount[3] = nResultsCount;
		nArrFileCount[4] = nSatlitCount;
		nArrFileCount[5] = nSellstCount;
		nArrFileCount[6] = nTelmpCount;
		nArrFileCount[7] = nWinnmpCount;

		for (int i = 0; i < nArrFileCount.length; i++) {
			nFileCount += nArrFileCount[i];
		}
		
		

		// ���� ���ε� ���� ����
		String sDsName = "";
		String sRtnVeri = "002";

		// ���� �ø��� ���� �� ����

		if (nFileCount > 1) {

			// ���� ���� ����
			SaveVeri objVeri = new SaveVeri(ctx, this.getRbmDao("rbmjdbcdao"),
					this.getDao(rbmdao), SESSION_USER_ID);

			// ���� ���ε� Ȯ��
			strFILE_VERI = objVeri.selectVeriFile(nArrFileCount);

			// 6.1 1�� ���� ���� ����(TBES_SDL_DT,TBES_SDL_PT)
			String sVeriDTPTFst = objVeri.strVeriDTPTFst();

			// 6.2 1�� ���� ���� ���� (TBES_SDL_PT,TBJI_PC_PAYOFFS)
			String sVeriPTPayoffFst = objVeri.strVeriPTPayoffFst();

			// 6.3 2�� ���� SDL_DT SATLIT ����
			String sVeriDTSatlitSnd = objVeri.strVeriDTSatlitSnd();

			// 6.4 2�� ���� Ȯ��(SDL_DT TBJI_PC_SELLST)
			String sVeriDTSellstSnd = objVeri.strVeriDTSellstSnd();

			// 6.5 2�� ���� SDL_DT SATLIT ����
			// TELMP���� �׸�ī�� �Ǹ�ó�� �и��� �ȵǾ�(���� SELL_CD='01') : �׸�ī��� SELL_CD='01'�� ������ ��(2014.04.03)
			String sVeriDTTelmpSnd = objVeri.strVeriDTTelmpSnd();

			String sVeriDTPTPayoffFst = "002";

			String sFstVeri = "002";
			String sSndVeri = "002";
			if ("001".equals(sVeriDTPTFst) && "001".equals(sVeriPTPayoffFst)) {
				sFstVeri = "001";
			}
			if ("001".equals(sVeriDTSatlitSnd)
					&& "001".equals(sVeriDTSellstSnd)
					&& "001".equals(sVeriDTTelmpSnd)) {
				sSndVeri = "001";
			}
			
			if ("001".equals(sVeriDTPTFst) && "001".equals(sVeriPTPayoffFst)
					&& "001".equals(sVeriDTSatlitSnd)
					&& "001".equals(sVeriDTSellstSnd)
					&& "001".equals(sVeriDTTelmpSnd)) {
				sVeriDTPTPayoffFst = "001";
			}

			// 6.3 ���� ����, 1�� ���� ���� �Է�
			objVeri.insertVeriFst(record, SESSION_USER_ID, strFILE_VERI,
					sFstVeri, sSndVeri, sVeriDTPTPayoffFst);
			

			sRtnVeri = sVeriDTPTPayoffFst;
		}

		

		int nTempCnt = 0;

		// ���� �ø��� ���� �� ����
		sDsName = "dsPcUpList";

		if (ctx.get(sDsName) != null && nFileCount == 0) {

			ds = (PosDataset) ctx.get(sDsName);
			nSize = ds.getRecordCount();

			for (int i = 0; i < nSize; i++) {
				record = ds.getRecord(i);
				if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
						|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {

					strFILE_VERI = (String) record.getAttribute("FILE_VERI");

					nTempCnt = savePcFileVeri(record); // 6.4 ���� ���ε� ����
					nSaveCount = nSaveCount + nTempCnt;
					
					sRtnVeri = (String) record.getAttribute("VERI");
					
					continue;

				}
			}
		}

		Util.setReturnParam(ctx, "FILE_VERI", strFILE_VERI);

		Util.setReturnParam(ctx, "GITA_FILE_ROW_CNT", nGita + "");
		Util.setReturnParam(ctx, "KTAX_FILE_ROW_CNT", nKtax + "");
		Util.setReturnParam(ctx, "PAYO_FILE_ROW_CNT", nPayoffs + "");
		Util.setReturnParam(ctx, "RESU_FILE_ROW_CNT", nResults + "");
		Util.setReturnParam(ctx, "SATL_FILE_ROW_CNT", nSatlit + "");
		Util.setReturnParam(ctx, "SELL_FILE_ROW_CNT", nSellst + "");
		Util.setReturnParam(ctx, "TELM_FILE_ROW_CNT", nTelmp + "");
		Util.setReturnParam(ctx, "WINN_FILE_ROW_CNT", nWinnmp + "");

		
		Util.setReturnParam(ctx, "RESULT", "T"); // ���
		Util.setReturnParam(ctx, "RESULT_VERI", sRtnVeri); // ���
		
		
		
	}

	/**
	 * <p>
	 * ���� ���ε� ���� ����
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return dmlcount int update ���ڵ� ����
	 * @throws none
	 */
	protected int savePcFileVeri(PosRecord record) {
		PosParameter param = new PosParameter();

		int i = 0;
		param.setValueParamter(i++, record.getAttribute("FILE_VERI")); // ���� ����
		param.setValueParamter(i++, SESSION_USER_ID); // �����ID(�����)
		param.setValueParamter(i++, sStndYear); // ���س⵵
		param.setValueParamter(i++, sDT); // ����
		int dmlcount = this.getDao("rbmdao").update("rsm1020_u03", param);
		return dmlcount;
	}

	/**
	 * <p>
	 * ���� PC ���ϳ��� ����
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @throws none
	 */
	protected void deletePCFileContents(String QueryId, String strDay) {

		PosParameter param = new PosParameter();
		int i = 0;

		param.setWhereClauseParameter(i++, strDay); // ����

		this.getDao(rbmdao).update(QueryId, param);
	}

	protected void deleteTelmpFileContents(String QueryId, String strDay) {

		PosParameter param = new PosParameter();
		int i = 0;
		param.setWhereClauseParameter(i++, strDay); // ����

		this.getDao(rbmdao).update(QueryId, param);
	}

	

	/*
	 * RACE_DAY �� ��ȸ�ؼ� �Է�
	 */
	public void insertData(String queryID, String sRaceDay) {
		PosParameter param = new PosParameter();

		param.setWhereClauseParameter(0, sRaceDay); // ������

		this.getDao(rbmdao).update(queryID, param);
	}

	/**
	 * <p>
	 * PC ���Ͼ��ε� ���� �Է�
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return dmlcount int update ���ڵ� ����
	 * @throws none
	 */
	protected int insertFileUpHistory(String FileCd, int FILE_ROW_CNT,
			PosRecord record, String sFilePath, String sFileNm) {
		PosParameter param = new PosParameter();

		int i = 0;

		param.setValueParamter(i++, sMeetGubun); // MEET_CD ������ڵ�
		param.setValueParamter(i++, sStndYear); // STND_YEAR ���س⵵
		// param.setValueParamter(i++, sDT); // RACE_DT ������
		param.setValueParamter(i++, record.getAttribute("DT")); // DT ������
		param.setValueParamter(i++, FileCd); // FILE_CD ��������
		param.setValueParamter(i++, FILE_ROW_CNT + ""); // �Է� ����
		param.setValueParamter(i++, sFilePath); // FILE_PATH ���� ���
		param.setValueParamter(i++, sFileNm); // FILE_NM ���� �̸�
		param.setValueParamter(i++, sRaceDay); // RACE_DT ������
		param.setValueParamter(i++, searchGloInfo(sMeetGubun, "01")); // TMS
		param.setValueParamter(i++, searchGloInfo(sMeetGubun, "02")); // DAY_ORD
		param.setValueParamter(i++, sSellCd); // �߸�ó
		param.setValueParamter(i++, sSessionNo); // ���ǹ�ȣ
		param.setValueParamter(i++, SESSION_USER_ID); // �ۼ���ID
		param.setValueParamter(i++, SESSION_USER_ID); // ������ID

		int dmlcount = this.getDao(rbmdao).update("rsm1020_i01", param);

		return dmlcount;
	}

	/**
	 * <p>
	 * PC ���Ͼ��ε� ���� ����
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @throws none
	 */
	protected void deleteFileUpHistory(String FileCd, PosRecord record) {
		PosParameter param = new PosParameter();

		int i = 0;

		param.setWhereClauseParameter(i++, sMeetGubun); // MEET_CD ����ó
		param.setWhereClauseParameter(i++, sStndYear); // STND_YEAR ���س⵵
		param.setWhereClauseParameter(i++, record.getAttribute("DT")); // ����
		param.setWhereClauseParameter(i++, FileCd); // ��������

		this.getDao(rbmdao).update("rsm1020_d01", param);
	}

	public String searchGloInfo(String MeetGubun, String Gubun) {
		String sReturnVal;

		if ("01".equals(Gubun)) { // ȸ��
			if ("001".equals(sMeetGubun)) {
				sReturnVal = sGtms;
			} else {
				sReturnVal = sMtms;
			}
		} else { // ����
			if ("001".equals(sMeetGubun)) {
				sReturnVal = sGday;
			} else {
				sReturnVal = sMday;
			}
		}

		return sReturnVal;
	}

	/**
	 * <p>
	 * ������ڵ� ��ȸ
	 * </p>
	 * 
	 * @param String
	 *            MeetCd �� ������ڵ�
	 * @return rMeetCd String �� ������ڵ�
	 * @throws none
	 */
	public static String searchMeet(String MeetCd) {
		String rMeetCd = "";

		if ("1".equals(MeetCd)) {
			rMeetCd = sGmeetCd;
		} else if ("2".equals(MeetCd)) {
			rMeetCd = sCmeetCd;
		} else if ("3".equals(MeetCd)) {
			rMeetCd = sMmeetCd;
		} else if ("4".equals(MeetCd)) {
			rMeetCd = sBmeetCd;
		}

		return rMeetCd;
	}

	/**
	 * <p>
	 * �߸�ó ��ȸ
	 * </p>
	 * 
	 * @param String
	 *            AsscCd �� Association Code
	 * @return rSellCd String �� SELL_CD
	 * @throws none
	 */
	public static String searchSell(String AssoCd) {
		String rSellCd = "";

		if ("1".equals(AssoCd)) {
			rSellCd = sGsellCd;
		} else if ("2".equals(AssoCd)) {
			rSellCd = sCsellCd;
		} else if ("3".equals(AssoCd)) {
			rSellCd = sMsellCd;
		} else if ("4".equals(AssoCd)) {
			rSellCd = sBsellCd;
		}

		return rSellCd;
	}

	/**
	 * <p>
	 * ȸ����ȸ
	 * </p>
	 * 
	 * @param String
	 *            MeetCd ������ڵ�
	 * @return rTms String ȸ��
	 * @throws none
	 */
	public static String searchTms(String MeetCd) {
		String rTms = "";

		if ("1".equals(MeetCd) || "001".equals(MeetCd)) {
			rTms = sGtms;
		} else if ("2".equals(MeetCd) || "001".equals(MeetCd)) {
			rTms = sCtms;
		} else if ("3".equals(MeetCd) || "001".equals(MeetCd)) {
			rTms = sMtms;
		} else if ("4".equals(MeetCd) || "001".equals(MeetCd)) {
			rTms = sBtms;
		}

		// rTms�� Null�̰� ������ ����� ���
		if (rTms.equals("") && "001".equals(sMeetGubun)) {
			rTms = sGtms;
			
		} else if (rTms.equals("") && "003".equals(sMeetGubun)) {
			rTms = sMtms;
			
		}
		
		if (rTms.equals("") && "1".equals(MeetCd)) rTms = "91"; //������ ����� ��� ��� �ӽ�ȸ�� for telmp
		if (rTms.equals("") && "3".equals(MeetCd)) rTms = "93"; //������ ����� ��� ���� �ӽ�ȸ�� for telmp
		
		if (rTms.equals("") && "001".equals(MeetCd)) rTms = "91"; //������ ����� ��� ��� �ӽ�ȸ�� for winmp
		if (rTms.equals("") && "003".equals(MeetCd)) rTms = "93"; //������ ����� ��� ���� �ӽ�ȸ�� for winmp

	System.out.println("searchTms("+MeetCd+")="+rTms);	
			return rTms;
	}

	/**
	 * <p>
	 * ������ȸ
	 * </p>
	 * 
	 * @param String
	 *            MeetCd ������ڵ�
	 * @return rDay String ����
	 * @throws none
	 */
	public static String searchDay(String MeetCd) {
		String rDay = "";

		if ("1".equals(MeetCd) || "001".equals(MeetCd)) {
			rDay = sGday;
		} else if ("2".equals(MeetCd) || "001".equals(MeetCd)) {
			rDay = sCday;
		} else if ("3".equals(MeetCd) || "001".equals(MeetCd)) {
			rDay = sMday;
		} else if ("4".equals(MeetCd) || "001".equals(MeetCd)) {
			rDay = sBday;
		}

		// rDay Null�̰� ������ ����� ���
		if (rDay.equals("") && "001".equals(sMeetGubun)) {
			rDay = sGday;
		} else if (rDay.equals("") && "003".equals(sMeetGubun)) {
			rDay = sMday;
		}
		
		if (rDay.equals("") && ("1".equals(MeetCd) || "3".equals(MeetCd))) { //������ ����� ��� ���/���� �ӽ����� for Telmp
			 if ("".equals(sCday)) rDay = sMday;
			 else rDay = sCday;
		}
		if (rDay.equals("") && ("001".equals(MeetCd) || "003".equals(MeetCd))) { //������ ����� ��� ���/���� �ӽ����� for Winmp
			 if ("".equals(sCday)) rDay = sMday;
			 else rDay = sCday;
		}
System.out.println("searchDay("+MeetCd+")="+rDay);			
		return rDay;
	}

	/**
	 * <p>
	 * ��ǥ��, �߸�(����)�ڵ� üũ
	 * </p>
	 * 
	 * @param String
	 *            inCode ��ǥ���ڵ�/�߸�(����)�ڵ�
	 * @return rDay outCode ��ǥ���ڵ�/�߸�(����)�ڵ�
	 * @throws none
	 */
	public static String searchoutCode(String inCode) {
		String outCode = "";

		outCode = inCode;
		if (inCode.length() == 1) {
			outCode = "0" + inCode;
		}

		return outCode;
	}

	/**
	 * <p>
	 * Comm �ڵ� ��ȯ
	 * </p>
	 * 
	 * @param String
	 *            inCode ���ڵ��ȣ
	 * @return rDay outCode ���ڵ��ȣ
	 * @throws none
	 */
	public static String searchCommCode(String inCode) {
		String outCode = "";

		if (inCode.length() == 1) {
			inCode = "0" + inCode;
		}

		if ("11".equals(inCode))
			outCode = "07"; // ���� SUWON
		else if ("12".equals(inCode))
			outCode = "07"; // �߶� JUNGNANG
		else if ("13".equals(inCode))
			outCode = "07"; // �ϻ� ILSAN
		else if ("14".equals(inCode))
			outCode = "07"; // �д� BUNDANG
		else if ("15".equals(inCode))
			outCode = "07"; // ���빮 DONGDAEMUN
		else if ("16".equals(inCode))
			outCode = "07"; // ��� JANGAN
		else if ("17".equals(inCode))
			outCode = "07"; // �꺻 SANBON
		else if ("18".equals(inCode))
			outCode = "07"; // ��õ BUCHEON
		else if ("19".equals(inCode))
			outCode = "07"; // ���� GWANAK
		else if ("20".equals(inCode))
			outCode = "07"; // ���� GIREUM
		else if ("21".equals(inCode))
			outCode = "07"; // ������ YEONGDEUNGPO
		else if ("22".equals(inCode))
			outCode = "07"; // ���� DAEJEON
		else if ("23".equals(inCode))
			outCode = "07"; // ��õ INCHEON
		else if ("24".equals(inCode))
			outCode = "07"; // ���� SIHEUNG
		else if ("25".equals(inCode))
			outCode = "07"; // ���� GANGNAM
		else if ("26".equals(inCode))
			outCode = "07"; // õ�� CHEONAN
		else if ("27".equals(inCode))
			outCode = "07"; // �ø��� OLYMPIC
		else if ("28".equals(inCode))
			outCode = "07"; // ������ UIJEONGBU
		else
			outCode = inCode;

		return outCode;
	}

	/**
	 * <p>
	 * Div �ڵ� ��ȯ
	 * </p>
	 * 
	 * @param String
	 *            inCode ��Comm�ڵ��ȣ
	 * @return rDay outCode ��div �ڵ��ȣ
	 * @throws none
	 */
	public static String searchDivCode(String commCd, String divCd) {
		String outCode = "";

		if (commCd.length() == 1) {
			commCd = "0" + commCd;
		}
		if (divCd.length() == 1) {
			divCd = "0" + divCd;
		}

		if ("11".equals(commCd))
			outCode = "01"; // ���� SUWON
		else if ("12".equals(commCd))
			outCode = "02"; // �߶� JUNGNANG
		else if ("13".equals(commCd))
			outCode = "03"; // �ϻ� ILSAN
		else if ("14".equals(commCd))
			outCode = "05"; // �д� BUNDANG --> �����ڵ�� ü�躯��
		else if ("15".equals(commCd))
			outCode = "04"; // ���빮 DONGDAEMUN --> �����ڵ�� ü�躯��
		else if ("16".equals(commCd))
			outCode = "06"; // ��� JANGAN
		else if ("17".equals(commCd))
			outCode = "07"; // �꺻 SANBON
		else if ("18".equals(commCd))
			outCode = "08"; // ��õ BUCHEON
		else if ("19".equals(commCd))
			outCode = "09"; // ���� GWANAK
		else if ("20".equals(commCd))
			outCode = "10"; // ���� GIREUM
		else if ("21".equals(commCd))
			outCode = "11"; // ������ YEONGDEUNGPO
		else if ("22".equals(commCd))
			outCode = "12"; // ���� DAEJEON
		else if ("23".equals(commCd))
			outCode = "13"; // ��õ INCHEON
		else if ("24".equals(commCd))
			outCode = "14"; // ���� SIHEUNG
		else if ("25".equals(commCd))
			outCode = "15"; // ���� GANGNAM
		else if ("26".equals(commCd))
			outCode = "16"; // õ�� CHEONAN
		else if ("27".equals(commCd))
			outCode = "17"; // �ø��� OLYMPIC
		else if ("28".equals(commCd))
			outCode = "18"; // ������ UIJEONGBU
		else
			outCode = divCd;

		return outCode;
	}

	public static boolean isBranch(String inCode) {

		if (inCode.length() == 1) {
			inCode = "0" + inCode;
		}

		if ("11".equals(inCode) || "12".equals(inCode) || "13".equals(inCode)
				|| "14".equals(inCode) || "15".equals(inCode)
				|| "16".equals(inCode) || "17".equals(inCode)
				|| "18".equals(inCode) || "19".equals(inCode)
				|| "20".equals(inCode) || "21".equals(inCode)
				|| "22".equals(inCode) || "23".equals(inCode)
				|| "24".equals(inCode) || "25".equals(inCode)
				|| "26".equals(inCode) || "27".equals(inCode)
				|| "28".equals(inCode))
			return true;
		else
			return false;
	}

	/**
	 * <p>
	 * ���ֹ�ȣ 2�ڸ� ���� üũ
	 * </p>
	 * 
	 * @param String
	 *            inRaceNo ���ֹ�ȣ
	 * @return String outRaceNo ���ֹ�ȣ
	 * @throws none
	 */
	public static String searchRaceNo(String inRaceNo) {
		String outRaceNo = "";

		outRaceNo = inRaceNo;
		if (inRaceNo.length() == 1) {
			outRaceNo = "0" + inRaceNo;
		}

		return outRaceNo;
	}

	// ������ �Էµ� PC ���� ���� ī��Ʈ
	protected PosRow[] searchPCFileCount(PosGenericDao dao, PosContext ctx) {
		String sStndYear = Util.getCtxStr(ctx, "STND_YEAR");
		String sSessionNo = Util.getCtxStr(ctx, "SESSION_NO");
		String sDT = (String) ctx.get("DT"); // ����

		PosParameter param = new PosParameter();

		int i = 0;
		param.setWhereClauseParameter(i++, sStndYear); // 0: STND_YEAR ���س⵵
		param.setWhereClauseParameter(i++, sStndYear); // 1: STND_YEAR ���س⵵
		param.setWhereClauseParameter(i++, sStndYear); // 2: STND_YEAR ���س⵵
		param.setWhereClauseParameter(i++, sDT); // 3: DT

		PosRowSet prs = null;
		prs = dao.find("rsm1020_s02", param);
		PosRow pr[] = prs.getAllRow();
		return pr;
	}

	
    /**
     * <p> ��Ÿ�ҵ漼(�׸�ī��) �ڷ� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateGitaGrn(String sPayYear,String sPayDt) 
    {
    	// �׸�ī��db���� ������ �ش� ���� ��Ÿ�ҵ漼�� ������ �����Ѵ�. 
        PosParameter param = new PosParameter();
        int i = 0;        
        param.setValueParamter(i++, sPayYear+sPayDt);	//���޳����        
        int dmlcount = this.getDao("rbmdao").update("rsm1020_d02", param);

    	// �׸�ī��db���� �ش� ������ ��Ÿ�ҵ漼���� �����´�.
        param = new PosParameter();
        i = 0;        
        param.setValueParamter(i++, SESSION_USER_ID);	//������ ���
        param.setValueParamter(i++, sPayYear+sPayDt);	//���޳����        
        dmlcount += this.getDao("rbmdao").update("rsm1020_i22", param);

        // �׸�ī��db���� ������ ������ �̿��Ͽ� ������ ����ȭ�� �ش�.
        param = new PosParameter();
        i = 0;        
        param.setValueParamter(i++, SESSION_USER_ID);	//������ ���
        param.setValueParamter(i++, sPayYear+sPayDt);	//���޳����        
        dmlcount += this.getDao("rbmdao").update("rsm1020_u01", param);

        return dmlcount;
    }

    /**
     * <p> ��Ÿ�ҵ漼(�׸�ī��) �ڷ� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateTaxGrn(String sPayYear,String sPayDt) 
    {
    	// �׸�ī��db���� ������ �ش� ���� ��Ÿ�ҵ漼�� ������ �����Ѵ�. 
        PosParameter param = new PosParameter();
        int i = 0;        
        param.setValueParamter(i++, SESSION_USER_ID);	//������ ���
        param.setValueParamter(i++, sPayYear+sPayDt);	//���޳����        
        int dmlcount = this.getDao("rbmdao").update("rsm1020_u02", param);

        return dmlcount;
    }

}
