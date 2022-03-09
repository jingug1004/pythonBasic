/*================================================================================
 * �ý���			: GITA ���� ���ε�
 * �ҽ����� �̸�	: snis.rbm.business.rsm1020.activity.Gita.java
 * ���ϼ���		: PC���� ���ε�
 * �ۼ���			: �̱⼮
 * ����			: 1.0.0
 * ��������		: 2011-10-05
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm1020.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import com.posdata.glue.util.log.PosLog;
import com.posdata.glue.util.log.PosLogFactory;

import snis.rbm.common.util.FileReader;
import snis.rbm.common.util.RbmJdbcDao;
import snis.rbm.common.util.Util;


public class Gita {
	protected PosLog logger;
	private PosContext ctx = null;
	private String dsGita = "";
	private String sFilePath = "";
	private RbmJdbcDao rbmjdbcdao = null;
	private String user_id = "";

	public Gita() {
	}

	public Gita(PosContext ctx, String sFilePath, RbmJdbcDao rbmjdbcdao,
			String session_user_id) {
		this.ctx = ctx;
		this.sFilePath = sFilePath;
		this.rbmjdbcdao = rbmjdbcdao;
		this.user_id = session_user_id;
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

	public int insertGita() {

		int intResult = 0; // �����
		int failure_count = 0;
		
		String sStndYear = Util.getCtxStr(ctx, "STND_YEAR");
		String sSessionNo = Util.getCtxStr(ctx, "SESSION_NO");
		String sDT = (String) ctx.get("DT"); // ����
		String sJigeupDt = sStndYear+sDT;

		ArrayList aList = new ArrayList(); // ���� �����Ϳ� ArrayList
		ArrayList alDBInsert = new ArrayList(); // Ȯ�� ���ܵ� �����Ϳ� ArrayList
		FileReader fReader = new FileReader(); // ���� ������ �д� Ŭ����
		

		aList = fReader.alReadFile(sFilePath); // ���� ������ �о����
		HashMap hMap =null;
		for (int i = 0; i < aList.size(); i++) {
			hMap = (HashMap) aList.get(i);
			if (intTbrdGitaFstCfmCntnt(rbmjdbcdao, hMap) == 0) // Ȯ���� �����ʹ� ����
			// ��Ŵ
			{
				alDBInsert.add(hMap);
			}
		}
		
		

		String[] arrQuery = new String[alDBInsert.size()];
		
		for (int i = 0; i < alDBInsert.size(); i++) {

			hMap = (HashMap) alDBInsert.get(i);

			deleteGita(rbmjdbcdao, hMap);
			

			int iWinAmt 	= Util.NVL(hMap.get("column11"),0) + Util.NVL(hMap.get("column17"),0) + Util.NVL(hMap.get("column23"),0) + Util.NVL(hMap.get("column29"),0) + Util.NVL(hMap.get("column35"),0) + Util.NVL(hMap.get("column41"),0);
			int iJegeupAmt 	= Util.NVL(hMap.get("column12"),0) + Util.NVL(hMap.get("column18"),0) + Util.NVL(hMap.get("column24"),0) + Util.NVL(hMap.get("column30"),0) + Util.NVL(hMap.get("column36"),0) + Util.NVL(hMap.get("column42"),0);
			int iGita1 		= Util.NVL(hMap.get("column13"),0) + Util.NVL(hMap.get("column19"),0) + Util.NVL(hMap.get("column25"),0) + Util.NVL(hMap.get("column31"),0) + Util.NVL(hMap.get("column37"),0) + Util.NVL(hMap.get("column43"),0);
			int iGita2		= Util.NVL(hMap.get("column14"),0) + Util.NVL(hMap.get("column20"),0) + Util.NVL(hMap.get("column26"),0) + Util.NVL(hMap.get("column32"),0) + Util.NVL(hMap.get("column38"),0) + Util.NVL(hMap.get("column44"),0);
			int iGitaPay 	= Util.NVL(hMap.get("column15"),0) + Util.NVL(hMap.get("column21"),0) + Util.NVL(hMap.get("column27"),0) + Util.NVL(hMap.get("column33"),0) + Util.NVL(hMap.get("column39"),0) + Util.NVL(hMap.get("column45"),0);
			arrQuery[i] = " INSERT INTO TBJI_PC_GITA  /* Gita.insertGita */ (         	"+
			"        MEET_CD,       "+
			"		 SELL_CD,       "+
			"		 TSN,         	"+

			"		 PAY_YEAR,      "+
			"		 PAY_MM,        "+

			"		 PERF_NO,       "+
			"		 COMM_NO,       "+
			"		 DIV_NO,        "+
			"		 REFUND,        "+
			"        SELL_AMT,      "+
			"        JIGEUP_AMT,    "+
			"        COST,         	"+
			"        GITA1,         "+
			"        GITA2,         "+
			"        GITA_PAY,      "+
			"        JIGEUP_DT,     "+
			"        INST_ID,       "+
			"        INST_DT,       "+
			"        UPDT_ID,       "+
			"        UPDT_DT        "+
			") VALUES (             "+
			"  '" + SavePCFileUpload.searchMeet((String) hMap.get("column1"))+ "' "+ 	// MEET_CD
			", '" + SavePCFileUpload.searchSell((String) hMap.get("column0"))+ "' "+ 	// SELL_CD
			", '" + hMap.get("column9") + "' "+											// TSN(asc_tsn)
			", '" + sStndYear + "' "+													// PAY_YEAR ���ֳ⵵ ���Ͽ���
			", SUBSTR('" + sDT + "',0,2) "+												// PAY_MM �ش� ��
			", '" + hMap.get("column2") + "' "+ 										// PERF_NO(perf_no)
			", '"+ SavePCFileUpload.searchoutCode((String) hMap.get("column5"))+ "' "+  // COMM_NO(cash_comm_on)
			", '"+ SavePCFileUpload.searchoutCode((String) hMap.get("column6"))+ "' "+  // DIV_NO(cash_div_no)
			", '" + hMap.get("column7") + "' "+ 	// REFUND(cash_windows)
			", '" + hMap.get("column8") + "' "+ 	// SELL_AMT(sell_windows)
			", '" + iJegeupAmt + "' "+ 	// JIGEUP_AMT(gita_pay+gita1+gita2)
			", '" + iWinAmt + "' "+ 	// COST(win_amt)
			", '" + iGita1 + "' "+ 		// GITA1(gita1)
			", '" + iGita2 + "' "+ 		// GITA2(gita2)
			", '" + iGitaPay + "' "+ 	// GITA_PAY(gita_pay)
			", '" + sJigeupDt + "' "+ 				// JIGEUP_DT
			", '" + user_id + "' "+ 				// INST_ID
			", SYSDATE"+ 							// INST_DT
			", '" + user_id + "' "+ 				// UPDT_ID
			", SYSDATE ) "; 						// UPDT_DT

			
			
			
		}
		 
		int [] insertCounts = rbmjdbcdao.executeBatch(arrQuery);
		
		
		
		if (failure_count == 0) {
			intResult = aList.size();
		} else {
			intResult = 0;
		}
		return intResult;
	}

	/**
	 * <p>
	 * PC gita���� ����
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @throws none
	 */
	public void deleteGita(PosGenericDao dao, HashMap hMap) {
		PosParameter param = new PosParameter();

		int i = 0;

		param.setWhereClauseParameter(i++, SavePCFileUpload.searchMeet((String) hMap.get("column1")));// 1.Meet(������ڵ�)
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchSell((String) hMap.get("column0")));// 5.SellCd(�߸�ó)
		param.setWhereClauseParameter(i++, hMap.get("column9")); // 6.TSN(���ֱǹ�ȣ)

		dao.update("deleteGita", param);
	}

	// ������������_Ȯ������ ���� Ȯ���� ������ ���� Ȯ����
	private int intTbrdGitaFstCfmCntnt(PosGenericDao dao, HashMap hMap) {
		PosParameter param = new PosParameter();

		String sStndYear = Util.getCtxStr(ctx, "STND_YEAR");
		String sSessionNo = Util.getCtxStr(ctx, "SESSION_NO");
		String sDT = (String) ctx.get("DT"); // ����

		int i = 0;
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchMeet((String) hMap.get("column1")));// 0.MEET_CD (������ڵ�)
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchSell((String) hMap.get("column0")));// 1.SELL_CD (�߸�ó)
		param.setWhereClauseParameter(i++, hMap.get("column9")); // 2.TSN_NO(���ֱǹ�ȣ)
		param.setWhereClauseParameter(i++, sStndYear); // 3.PAY_YEAR(�⵵)
		param.setWhereClauseParameter(i++, sDT); // 4.PAY_MM(��)

		PosRowSet keyRecord = dao.find("intTbrdGitaFstCfmCntnt", param);

		PosRow pr[] = keyRecord.getAllRow();

		int intCNT = Util.nullToInt(String.valueOf(pr[0].getAttribute("CNT")));

		return intCNT;
	}

	// TBRD_GITA_CFM_CNTNT ������ �Է�

	public int insertTbrdGitaCfmCntnt() {
		int intResult = 0; // �����

		String sStndYear = Util.getCtxStr(ctx, "STND_YEAR");
		String sSessionNo = Util.getCtxStr(ctx, "SESSION_NO");
		String sDT = (String) ctx.get("DT"); // ����
		String sJigeupDt = Util.getCtxStr(ctx, "RACE_DAY");

		ArrayList aList = new ArrayList(); // ���� �����Ϳ� ArrayList
		ArrayList alDBInsert = new ArrayList(); // Ȯ�� ���ܵ� �����Ϳ� ArrayList
		FileReader fReader = new FileReader(); // ���� ������ �д� Ŭ����

		aList = fReader.alReadFile(sFilePath); // ���� ������ �о����

		// ������ �о�鿩 DB INSERT
		HashMap hMap = null;
		for (int i = 0; i < aList.size(); i++) {
			hMap = (HashMap) aList.get(i);
			if (intTbrdGitaFstCfmCntnt(rbmjdbcdao, hMap) == 0) // Ȯ���� �����Ͱ� �ƴ� ���
			{
				alDBInsert.add(hMap);
			}
		}

		String[] arrQuery = new String[alDBInsert.size()];

		for (int i = 0; i < alDBInsert.size(); i++) {

			hMap = (HashMap) alDBInsert.get(i);

			deleteTbrdGitaCfmCntnt(rbmjdbcdao, hMap);			

			arrQuery[i] = " INSERT INTO TBRD_GITA_CFM_CNTNT /* Gita.insertTbrdGitaCfmCntnt */ (	"+
			" 	MEET_CD,                  	"+
			" 	SELL_CD,                 	"+
			" 	TSN_NO,                  	"+
			" 	PAY_YEAR,                 	"+
			" 	PAY_MM,                 	"+
			" 	CFM_CD,                 	"+
			" 	INST_ID,                  	"+
			" 	INST_DT,                  	"+
			" 	UPDT_ID,                  	"+
			" 	UPDT_DT                   	"+

			" ) VALUES (  			"+
			"  '" + SavePCFileUpload.searchMeet((String) hMap.get("column1"))+ "'"+ // MEET_CD,
			", '" + SavePCFileUpload.searchSell((String) hMap.get("column0"))+ "'"+ // SELL_CD,
			", '" + hMap.get("column9") + "'"+ 	// TSN(asc_tsn)
			", '" + sStndYear + "'"+ 			// PAY_YEAR,
			", SUBSTR('" + sDT + "',0,2)"+ 		// PAY_MM,
			", '002'"+ 							// CFM_CD,
			", '" + user_id + "' "+ 			// INST_ID,
			", SYSDATE"+ 						// INST_DT,
			", '" + user_id + "' "+ 			// INST_ID,
			", SYSDATE ) "; 					// UPDT_DT
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

	/**
	 * <p>
	 * TBRD_GITA_CFM_CNTNT ������ ����
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @throws none
	 */
	public void deleteTbrdGitaCfmCntnt(PosGenericDao dao, HashMap hMap) {
		PosParameter param = new PosParameter();
		String sStndYear = Util.getCtxStr(ctx, "STND_YEAR");
		String sSessionNo = Util.getCtxStr(ctx, "SESSION_NO");
		String sDT = (String) ctx.get("DT"); // ����

		int i = 0;
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchMeet((String) hMap.get("column1")));// 1.Meet(������ڵ�)
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchSell((String) hMap.get("column0")));// 5.SellCd(�߸�ó)

		param.setWhereClauseParameter(i++, sStndYear); // 3.PAY_YEAR(�⵵)
		param.setWhereClauseParameter(i++, sDT); // 4.PAY_MM(��)

		dao.update("deleteTbrdGitaCfmCntnt", param); // PC(gita)���� ������ ��������
		// ����Ѵ�.
	}

}
