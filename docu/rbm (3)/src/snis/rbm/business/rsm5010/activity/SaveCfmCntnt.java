/*================================================================================
 * �ý���			: ������������
 * �ҽ����� �̸�	: snis.rbm.business.rsm5010.activity.SaveCfmCntnt
 * ���ϼ���		: ������������_Ȯ������ ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-10-07
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm5010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveCfmCntnt extends SnisActivity {

	public SaveCfmCntnt() {
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
		String sValue = (String) ctx.get("SEND_VALUE"); // ���� ȭ��� OR �̺�Ʈ
		if (sValue.equals("RSM5040")) {
			saveSndCfmCntnt(ctx);
		} else {
			saveState(ctx);
		}
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

		PosDataset ds;
		int nSize = 0;
		int nTempCnt = 0;

		String sValue = (String) ctx.get("SEND_VALUE"); // ȭ���
		String sPayYear = (String) ctx.get("PAY_YEAR"); // ���޳⵵

		sDsName = "dsPcTax";

		if (ctx.get(sDsName) != null) {
			ds = (PosDataset) ctx.get(sDsName);
			nSize = ds.getRecordCount();

			int nCnt = 0;

			for (int i = 0; i < nSize; i++) {
				PosRecord record = ds.getRecord(i);

				// 1�� Ȯ����_�Է�
				if (sValue.equals("RSM5010")) {
					record.setAttribute("FST_CFM_CD", "001");

					if (i == 0)
						nCnt = getFstCnt(record);
				}

				// 1�� Ȯ����_����
				if (sValue.equals("RSM5030")) {
					record.setAttribute("FST_CFM_CD", "001");
					if (i == 0)
						nCnt = getCrossCnt(record);

				}

				// 2�� Ȯ����_����
				if (sValue.equals("RSM5040")) {
					record.setAttribute("SND_CFM_CD", "001");

					record.setAttribute("MEET_CD", record
							.getAttribute("TAX_MEET_CD"));
					record.setAttribute("SELL_CD", record
							.getAttribute("TAX_SELL_CD"));
					record.setAttribute("TSN", record.getAttribute("TAX_TSN"));
					record.setAttribute("COMM_NO", record
							.getAttribute("TAX_COMM_NO"));

					if (i == 0) {
						// ����ġ ������ ����
						if (getValidCnt(sPayYear) > 0) {
							try {
								throw new Exception();
							} catch (Exception e) {
								this.rollbackTransaction("tx_rbm");
								Util.setSvcMsgCode(ctx, "SNIS_RBM_D005");

								return;
							}
						}
						nCnt = getSndCnt(record);
					}
				}

				// 3�� Ȯ����
				if (sValue.equals("RSM5050")) {
					record.setAttribute("THR_CFM_CD", "001");
					if (i == 0)
						nCnt = getThrCnt(record);
				}

				if (i == 0 && nCnt != nSize) {
					try {
						throw new Exception();
					} catch (Exception e) {
						this.rollbackTransaction("tx_rbm");
						Util.setSvcMsgCode(ctx, "SNIS_RBM_D002");

						return;
					}
				}

				nTempCnt = saveCfmCntnt(record);
				nSaveCount = nSaveCount + nTempCnt;
			}
		}
		Util.setSaveCount(ctx, nSaveCount);
	}

	/**
	 * <p>
	 * �ϰ� Ȯ�� ����
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return dmlcount int update ���ڵ� ����
	 * @throws none
	 */
	protected int saveSndCfmCntnt(PosContext ctx) {

		PosRowSet prs = null;
		PosRow[] pr = null;
		PosParameter param = new PosParameter();

		String PAY_YEAR = (String) ctx.get("PAY_YEAR"); // <!-- ���޳⵵ -->
		String MEET_CD = (String) ctx.get("MEET_CD"); // <!-- ������ڵ�(����ó)-->
		String SELL_CD = (String) ctx.get("SELL_CD"); // <!-- �ó�ڵ�(�Ǹ�ó)-->
		String BRNC_CD = (String) ctx.get("BRNC_CD"); // <!-- �����ڵ�-->

		// 2�� Ȯ����_����

		// ����ġ ������ ����
		if (getValidCnt(PAY_YEAR) > 0) {
			try {
				throw new Exception();
			} catch (Exception e) {
				this.rollbackTransaction("tx_rbm");
				Util.setSvcMsgCode(ctx, "SNIS_RBM_D005");
			}
		}

		int i = 0;
		param.setWhereClauseParameter(i++, PAY_YEAR);
		param.setWhereClauseParameter(i++, MEET_CD);
		param.setWhereClauseParameter(i++, SELL_CD);
		param.setWhereClauseParameter(i++, BRNC_CD);
		param.setWhereClauseParameter(i++, BRNC_CD);
		param.setWhereClauseParameter(i++, BRNC_CD);
		param.setWhereClauseParameter(i++, PAY_YEAR);
		param.setWhereClauseParameter(i++, MEET_CD);
		param.setWhereClauseParameter(i++, SELL_CD);
		param.setWhereClauseParameter(i++, BRNC_CD);
		param.setWhereClauseParameter(i++, BRNC_CD);
		param.setWhereClauseParameter(i++, BRNC_CD);

		prs = this.getDao("rbmdao").find("rsm5040_s07", param);
		pr = prs.getAllRow();

		String[] arrQuery = new String[pr.length];

		for (int prI = 0; prI < pr.length; prI++) {
			arrQuery[prI] = " MERGE INTO TBRD_CFM_CNTNT A  --������������_Ȯ������				  \n"
					+ " 		/* saveSndCfmCntnt */						  \n"
					+ "     USING									  \n"
					+ " 	    (SELECT								  \n"
					+ " 		     '"
					+ pr[prI].getAttribute("TAX_MEET_CD")
					+ "' AS MEET_CD     --������ڵ�				  \n"
					+ " 		    ,'"
					+ pr[prI].getAttribute("TAX_SELL_CD")
					+ "' AS SELL_CD     --�ó�ڵ�				  \n"
					+ " 		    ,'"
					+ pr[prI].getAttribute("TAX_TSN")
					+ "' AS TSN         --���ֱǹ�ȣ				  \n"
					+ " 		    ,'"
					+ pr[prI].getAttribute("PAY_YEAR")
					+ "' AS PAY_YEAR    --���޳⵵				  \n"
					+ " 		    ,FC_ENC('"+ pr[prI].getAttribute("CUST_SSN")+ "') AS CUST_SSN    --�ֹε�Ϲ�ȣ				  \n"
					+ " 		    								  \n"
					+ " 		    ,'"
					+ pr[prI].getAttribute("TAX_COMM_NO")
					+ "' AS BRNC_CD     --�����ڵ�				  \n"
					+ " 						,'"
					+ pr[prI].getAttribute("FST_CFM_CD")
					+ "' AS FST_CFM_CD  --1��Ȯ���ڵ�	  \n"
					+ " 						,'001' AS SND_CFM_CD  --2��Ȯ���ڵ�	  \n"
					+ " 						,'"
					+ pr[prI].getAttribute("THR_CFM_CD")
					+ "' AS THR_CFM_CD  --3��Ȯ���ڵ�	  \n"
					+ " 		    ,'"
					+ SESSION_USER_ID
					+ "' AS INST_ID     --�ۼ���					  \n"
					+ " 		    								  \n"
					+ " 		    ,'"
					+ SESSION_USER_ID
					+ "' AS UPDT_ID     --������					  \n"
					+ " 										  \n"
					+ " 	       FROM    DUAL ) B							  \n"
					+ " 	ON (									  \n"
					+ " 		A.MEET_CD  = B.MEET_CD    --������ڵ�				  \n"
					+ " 	    AND A.SELL_CD  = B.SELL_CD    --�ó�ڵ�				  \n"
					+ " 	    AND A.TSN_NO   = B.TSN        --���ֱǹ�ȣ				  \n"
					+ " 	    AND A.PAY_YEAR = B.PAY_YEAR   --���޳⵵				  \n"
					+ "     )										  \n"
					+ "     										  \n"
					+ "     WHEN MATCHED THEN								  \n"
					+ " 	UPDATE SET 								  \n"
					+ " 	     A.BRNC_CD    = B.BRNC_CD     --�����ڵ�				  \n"
					+ " 	    ,A.FST_CFM_CD = B.FST_CFM_CD  --1��Ȯ���ڵ�				  \n"
					+ " 	    ,A.SND_CFM_CD = B.SND_CFM_CD  --2��Ȯ���ڵ�				  \n"
					+ " 	    ,A.THR_CFM_CD = B.THR_CFM_CD  --3��Ȯ���ڵ�				  \n"
					+ " 	    ,A.UPDT_ID    = B.UPDT_ID     --������				  \n"
					+ " 	    									  \n"
					+ " 	    ,A.UPDT_DT    = SYSDATE       --�����Ͻ�				  \n"
					+ " 										  \n"
					+ "     WHEN NOT MATCHED THEN							  \n"
					+ " 	INSERT (								  \n"
					+ "     										  \n"
					+ " 	     A.MEET_CD      --������ڵ�					  \n"
					+ " 	    ,A.SELL_CD      --�ó�ڵ�					  \n"
					+ " 	    ,A.TSN_NO       --���ֱǹ�ȣ					  \n"
					+ " 	    ,A.PAY_YEAR     --���޳⵵						  \n"
					+ " 	    ,A.RES_NO       --�ֹε�Ϲ�ȣ					  \n"
					+ " 	    									  \n"
					+ " 	    ,A.BRNC_CD      --�����ڵ�						  \n"
					+ " 				,A.FST_CFM_CD   --1��Ȯ���ڵ�			  \n"
					+ " 				,A.SND_CFM_CD   --2��Ȯ���ڵ�			  \n"
					+ " 				,A.THR_CFM_CD   --3��Ȯ���ڵ�			  \n"
					+ " 	    ,A.INST_ID      --�ۼ���						  \n"
					+ " 	    									  \n"
					+ " 	    ,A.INST_DT      --�ۼ�����						  \n"
					+ " 	    									  \n"
					+ " 	) VALUES (								  \n"
					+ " 	     B.MEET_CD      --������ڵ�					  \n"
					+ " 	    ,B.SELL_CD      --�ó�ڵ�					  \n"
					+ " 	    ,B.TSN          --���ֱǹ�ȣ					  \n"
					+ " 	    ,B.PAY_YEAR     --���޳⵵						  \n"
					+ " 	    ,FC_ENC(B.CUST_SSN) --�ֹε�Ϲ�ȣ					  \n"
					+ " 	    									  \n"
					+ " 	    ,B.BRNC_CD      --�����ڵ�						  \n"
					+ " 	    ,B.FST_CFM_CD   --1��Ȯ���ڵ�					  \n"
					+ " 	    ,'002'          --2��Ȯ���ڵ�					  \n"
					+ " 	    ,'002'          --3��Ȯ���ڵ�					  \n"
					+ " 	    ,B.INST_ID      --�ۼ���						  \n"
					+ " 	    									  \n"
					+ " 	    ,SYSDATE        --�ۼ�����						  \n"
					+ " 	)									  \n";

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

	/**
	 * <p>
	 * ������������_Ȯ������ ����
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return dmlcount int update ���ڵ� ����
	 * @throws none
	 */
	protected int saveCfmCntnt(PosRecord record) {
		PosParameter param = new PosParameter();

		int i = 0;

		param.setValueParamter(i++, record.getAttribute("MEET_CD")); // ������ڵ�
		param.setValueParamter(i++, record.getAttribute("SELL_CD")); // �ó�ڵ�
		param.setValueParamter(i++, record.getAttribute("TSN")); // ���ֱǹ�ȣ
		param.setValueParamter(i++, record.getAttribute("PAY_YEAR")); // ���޳⵵
		param.setValueParamter(i++, record.getAttribute("CUST_SSN")); // �ֹε�Ϲ�ȣ

		param.setValueParamter(i++, record.getAttribute("COMM_NO")); // �����ڵ�
		param.setValueParamter(i++, record.getAttribute("FST_CFM_CD")); // 1��Ȯ���ڵ�
		param.setValueParamter(i++, record.getAttribute("SND_CFM_CD")); // 2��Ȯ���ڵ�
		param.setValueParamter(i++, record.getAttribute("THR_CFM_CD")); // 3��Ȯ���ڵ�
		param.setValueParamter(i++, SESSION_USER_ID); // �����ID(�ۼ���)

		param.setValueParamter(i++, SESSION_USER_ID); // �����ID(������)

		int dmlcount = this.getDao("rbmdao").update("rsm5010_m02", param);

		return dmlcount;
	}

	/**
	 * <p>
	 * ���������Է� ��ü ���� ��ȸ
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return rtnKey int ������������ ����
	 * @throws none
	 */
	protected int getFstCnt(PosRecord record) {
		PosParameter param = new PosParameter();
		int i = 0;
		String strCommNo = record.getAttribute("COMM_NO").toString();
        String strSellCd =  record.getAttribute("SELL_CD" ).toString();
        if(strSellCd==null) strSellCd="03";
        
        
		// ������ �������� ������ �ϳ��� ���⶧���� ���Խ����� ������ �Ǵ�.
		if (!strSellCd.equals("03") && (strCommNo.equals("01") || strCommNo.equals("02")
				|| strCommNo.equals("03") || strCommNo.equals("04")
				|| strCommNo.equals("08"))) {
			strCommNo = "^(0[123468])$";
		}
		
		if(strSellCd.equals("03")){
			strCommNo ="98";
		}
		
		param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR")); // ���޳⵵
		param.setWhereClauseParameter(i++, strCommNo); // �����ڵ�
		param.setWhereClauseParameter(i++, strCommNo); // �����ڵ�
		param.setWhereClauseParameter(i++, strCommNo); // �����ڵ�

		PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5010_s03", param);

		PosRow pr[] = keyRecord.getAllRow();

		String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

		return Integer.parseInt(rtnKey);
	}

	/**
	 * <p>
	 * ������������ ��ü���� ��ȸ
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return rtnKey int ������������ ����
	 * @throws none
	 */
	protected int getCrossCnt(PosRecord record) {
		PosParameter param = new PosParameter();
		int i = 0;

		param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR")); // ���޳⵵

		PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5030_s01", param);

		PosRow pr[] = keyRecord.getAllRow();

		String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

		return Integer.parseInt(rtnKey);
	}

	/**
	 * <p>
	 * ������������ ��ü���� ��ȸ
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return rtnKey int ������������ ����
	 * @throws none
	 */
	protected int getSndCnt(PosRecord record) {
		PosParameter param = new PosParameter();
		int i = 0;

		param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR")); // ���޳⵵
		param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR")); // ���޳⵵

		PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5040_s03", param);

		PosRow pr[] = keyRecord.getAllRow();

		String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

		return Integer.parseInt(rtnKey);
	}

	/**
	 * <p>
	 * ������������ ��ġ���� Ȯ��
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return rtnKey int ��ġ����( 0 : ��ġ 0�ʰ� : ����ġ)
	 * @throws none
	 */
	protected int getValidCnt(String sPayYear) {
		PosParameter param = new PosParameter();
		int i = 0;

		param.setWhereClauseParameter(i++, sPayYear); // ���޳⵵
		param.setWhereClauseParameter(i++, sPayYear); // ���޳⵵

		PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5040_s04", param);

		PosRow pr[] = keyRecord.getAllRow();

		String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

		return Integer.parseInt(rtnKey);
	}

	/**
	 * <p>
	 * ��������Ȯ�� ��ü���� ��ȸ
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return rtnKey int ������������ ����
	 * @throws none
	 */
	protected int getThrCnt(PosRecord record) {
		PosParameter param = new PosParameter();
		int i = 0;

		param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR")); // ���޳⵵

		PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5050_s01", param);

		PosRow pr[] = keyRecord.getAllRow();

		String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

		return Integer.parseInt(rtnKey);
	}
}
