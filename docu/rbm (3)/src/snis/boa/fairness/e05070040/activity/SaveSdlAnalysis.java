/*================================================================================
 * �ý���			: ���� ����
 * �ҽ����� �̸�	: snis.boa.fairness.e05070040.activity.SaveSdlAnalysis.java
 * ���ϼ���		: ����SDL�м� ����
 * �ۼ���			: ����ȭ
 * ����			: 1.0.0
 * ��������		: 2009-11-28
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.fairness.e05070040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SaveSdlAnalysis extends SnisActivity
{    
    public SaveSdlAnalysis()
    {
    }

    /**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
   	
    	// ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}

		PosDataset ds;
		int nSize = 0;
		String sDsName = "dsOutCsv";

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
    * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
    * @param   ctx		PosContext	�����
    * @return  SUCCESS	String		sucess ���ڿ�
    * @throws  none
    */
    protected void saveState(PosContext ctx, String raceDt, String raceNo, String poolCd) 
    {
		int nTotalCount = 0;
		int nSaveCount = 0;
		int nDeleteCount = 0;
		int nSize1 = 0;
		int nSize2 = 0;

		String sDsName1 = "";
		String sDsName2 = "";

		PosDataset ds1;
		PosDataset ds2;

		sDsName1 = "dsOutSdl";
		sDsName2 = "dsOutCsv";

		if (ctx.get(sDsName1) != null) {
			ds1 = (PosDataset) ctx.get(sDsName1);
			nSize1 = ds1.getRecordCount();

			for (int i = 0; i < nSize1; i++) {
				PosRecord record = ds1.getRecord(i);
				if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
					nDeleteCount = deleteSdlAnalysis(record);
				}
			}
		}

		if (ctx.get(sDsName2) != null) {
			ds2 = (PosDataset) ctx.get(sDsName2);
			nSize2 = ds2.getRecordCount();
			for (int i = 0; i < nSize2; i++) {
				PosRecord record = ds2.getRecord(i);
				nSaveCount = nSaveCount + mergeSdlAnalysis(record, raceDt, raceNo, poolCd);
			}
		}

		nTotalCount = nSaveCount + nDeleteCount;

		Util.setSaveCount(ctx, nTotalCount);
    }
    
    /**
     * <p> ����SDL�м� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
	protected int deleteSdlAnalysis(PosRecord record) {
		PosParameter param = new PosParameter();

		int i = 0;

		param.setWhereClauseParameter(i++, record.getAttribute("RACE_DT"));
		param.setWhereClauseParameter(i++, record.getAttribute("RACE_NO"));
		param.setWhereClauseParameter(i++, record.getAttribute("POOL_CD"));

		int dmlcount = this.getDao("boadao").delete("tbee_sdl_analysis_de001", param);

		return dmlcount;
	}



    /**
     * <p> ����SDL�м� �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int mergeSdlAnalysis(PosRecord record, String raceDt, String raceNo, String poolCd) 
    {
		PosParameter param = new PosParameter();

		int i = 0;

		String sVal = (String) record.getAttribute("AMOUNT");
		String sCnt = (String) record.getAttribute("CNT");

		String rVal = "";
		String val1 = "0";
		String val2 = "0";
		String val3 = "0";
		String val4 = "0";
		String val5 = "0";
		String val6 = "0";
		String val7 = "0";
		String val8 = "0";
		String val9 = "0";
		String val10 = "0";

		if (sVal.length() == 6) {
			if ("1".equals(sCnt)) { // 10���� �����϶�.
				val10 = "1";
			} else if ("2".equals(sCnt)) { // 10�����̰� ������ 2�϶� ������ 1�� �������� ����.
				val1 = "1";
				val9 = "1";
			} else if ("3".equals(sCnt)) { // 10�����̰� ������ 3�϶� ������ 2�� �ȸ����� ����.
				val1 = "2";
				val8 = "1";
			} else if ("4".equals(sCnt)) { // 10�����̰� ������ 4�϶� ������ 3�� ĥ������ ����
				val1 = "3";
				val7 = "1";
			} else { // 10������ = ����(���̽� �߻�����.)
				val10 = sCnt;
			}
		} else {
			rVal = sVal.substring(0, 1);
			if ("9".equals(rVal)) {
				if ("1".equals(sCnt)) {
					val9 = "1";
				} else if ("2".equals(sCnt)) {
					val1 = "1";
					val8 = "1";
				} else if ("3".equals(sCnt)) {
					val1 = "2";
					val7 = "1";
				} else if ("4".equals(sCnt)) {
					val1 = "3";
					val6 = "1";
				} else {
					val9 = sCnt;
				}
			} else if ("8".equals(rVal)) {
				if ("1".equals(sCnt)) {
					val8 = "1";
				} else if ("2".equals(sCnt)) {
					val1 = "1";
					val7 = "1";
				} else if ("3".equals(sCnt)) {
					val1 = "2";
					val6 = "1";
				} else if ("4".equals(sCnt)) {
					val1 = "3";
					val5 = "1";
				} else {
					val8 = sCnt;
				}
			} else if ("7".equals(rVal)) {
				if ("1".equals(sCnt)) {
					val7 = "1";
				} else if ("2".equals(sCnt)) {
					val1 = "1";
					val6 = "1";
				} else if ("3".equals(sCnt)) {
					val1 = "2";
					val5 = "1";
				} else if ("4".equals(sCnt)) {
					val1 = "3";
					val4 = "1";
				} else {
					val7 = sCnt;
				}
			} else if ("6".equals(rVal)) {
				if ("1".equals(sCnt)) {
					val6 = "1";
				} else if ("2".equals(sCnt)) {
					val1 = "1";
					val5 = "1";
				} else if ("3".equals(sCnt)) {
					val1 = "2";
					val4 = "1";
				} else if ("4".equals(sCnt)) {
					val1 = "3";
					val3 = "1";
				} else {
					val6 = sCnt;
				}
			} else if ("5".equals(rVal)) {
				if ("1".equals(sCnt)) {
					val5 = "1";
				} else if ("2".equals(sCnt)) {
					val1 = "1";
					val4 = "1";
				} else if ("3".equals(sCnt)) {
					val1 = "2";
					val3 = "1";
				} else if ("4".equals(sCnt)) {
					val1 = "3";
					val2 = "1";
				} else {
					val5 = sCnt;
				}
			} else if ("4".equals(rVal)) {
				if ("1".equals(sCnt)) {
					val4 = "1";
				} else if ("2".equals(sCnt)) {
					val1 = "1";
					val3 = "1";
				} else if ("3".equals(sCnt)) {
					val1 = "2";
					val2 = "1";
				} else if ("4".equals(sCnt)) {
					val1 = "4";
				}
			} else if ("3".equals(rVal)) {
				if ("1".equals(sCnt)) {
					val3 = "1";
				} else if ("2".equals(sCnt)) {
					val1 = "1";
					val2 = "1";
				} else if ("3".equals(sCnt)) {
					val1 = "3";
				}
			} else if ("2".equals(rVal)) {
				if ("1".equals(sCnt)) {
					val2 = "1";
				} else if ("2".equals(sCnt)) {
					val1 = "2";
				}
			} else if ("1".equals(rVal)) {
				val1 = "1";
			}
		}

		param.setValueParamter(i++, raceDt);
		param.setValueParamter(i++, raceNo);
		param.setValueParamter(i++, poolCd);
		param.setValueParamter(i++, record.getAttribute("WIN_NO"));
		param.setValueParamter(i++, record.getAttribute("CNT"));
		param.setValueParamter(i++, record.getAttribute("AMOUNT"));
		param.setValueParamter(i++, val10);
		param.setValueParamter(i++, val9);
		param.setValueParamter(i++, val8);
		param.setValueParamter(i++, val7);
		param.setValueParamter(i++, val6);
		param.setValueParamter(i++, val5);
		param.setValueParamter(i++, val4);
		param.setValueParamter(i++, val3);
		param.setValueParamter(i++, val2);
		param.setValueParamter(i++, val1);
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, raceDt);
		param.setValueParamter(i++, raceNo);
		param.setValueParamter(i++, poolCd);
		param.setValueParamter(i++, record.getAttribute("WIN_NO"));
		param.setValueParamter(i++, record.getAttribute("CNT"));
		param.setValueParamter(i++, record.getAttribute("AMOUNT"));
		param.setValueParamter(i++, val10);
		param.setValueParamter(i++, val9);
		param.setValueParamter(i++, val8);
		param.setValueParamter(i++, val7);
		param.setValueParamter(i++, val6);
		param.setValueParamter(i++, val5);
		param.setValueParamter(i++, val4);
		param.setValueParamter(i++, val3);
		param.setValueParamter(i++, val2);
		param.setValueParamter(i++, val1);
		param.setValueParamter(i++, SESSION_USER_ID);

        return this.getDao("boadao").update("tbee_sdl_analysis_ie001", param);
    }

}
