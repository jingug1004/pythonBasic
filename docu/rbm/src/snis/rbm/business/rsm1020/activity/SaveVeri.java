package snis.rbm.business.rsm1020.activity;

import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import com.posdata.glue.util.log.PosLog;
import com.posdata.glue.util.log.PosLogFactory;

import snis.rbm.common.util.RbmJdbcDao;
import snis.rbm.common.util.Util;


public class SaveVeri {
	private PosContext ctx = null;
	private RbmJdbcDao rbmjdbcDao = null;
	private PosGenericDao rbmdao=null;
	public SaveVeri() {
	}

	public SaveVeri(PosContext ctx, RbmJdbcDao rbmjdbcDao,PosGenericDao rbmdao, String session_user_id) {
		this.ctx = ctx;
		this.rbmjdbcDao = rbmjdbcDao;
		this.rbmdao= rbmdao;
	}

	// 1�� ����  DT PT ����
	public String strVeriDTPTFst() {
		String rtnKey = "";

		
		String sMeetGubun = (String) ctx.get("MEET_GUBUN"); // ���� MeetGubun
		String sJigeupDt = Util.getCtxStr(ctx, "RACE_DAY"); // ������

		PosParameter param = new PosParameter();
		int i = 0;
		param.setWhereClauseParameter(i++, sMeetGubun); // ������
		param.setWhereClauseParameter(i++, sJigeupDt); // ������
		param.setWhereClauseParameter(i++, sMeetGubun); // ������
		param.setWhereClauseParameter(i++, sJigeupDt); // ������
		PosRowSet keyRecord = this.rbmdao.find("rsm1020_sVeriDTPTFst", param);
		PosRow pr[] = keyRecord.getAllRow();
		
		int intCount =0; // ���� ���� ī��Ʈ
		for(int j=0;j<pr.length;j++)
		{
			String TF = String.valueOf(pr[j].getAttribute("TF"));
			if(!"001".equals(TF))
			{
				intCount++;
			}
		}
		if(intCount > 0)
		{
			rtnKey="002";
		}
		else
		{
			rtnKey="001";
		}
		
		return rtnKey;
	}
	
	
	
	// 1�� ����  PT PAYOFF ����
	public String strVeriPTPayoffFst() {
		String rtnKey = "";

		
		String sMeetGubun = (String) ctx.get("MEET_GUBUN"); // ���� MeetGubun
		String sJigeupDt = Util.getCtxStr(ctx, "RACE_DAY"); // ������

		PosParameter param = new PosParameter();
		int i = 0;
		
		param.setWhereClauseParameter(i++, sJigeupDt); // ������
		param.setWhereClauseParameter(i++, sJigeupDt); // ������
		
		
		
		PosRowSet keyRecord = this.rbmdao.find("rsm1020_sVeriPTPayoffFst", param);
		PosRow pr[] = keyRecord.getAllRow();
		
		int intCount =0; // ���� ���� ī��Ʈ
		for(int j=0;j<pr.length;j++)
		{
			String TF = String.valueOf(pr[j].getAttribute("TF"));
			if(!"001".equals(TF))
			{
				intCount++;
			}
		}
		if(intCount > 0|| pr.length==0 )
		{
			rtnKey="002";
		}
		else
		{
			rtnKey="001";
		}
		
		return rtnKey;
	}
	
	
	
	// 2�� ���� SDL_DT SATLIT ����
	public String strVeriDTSatlitSnd() {
		String rtnKey = "";

		
		String sMeetGubun = (String) ctx.get("MEET_GUBUN"); // ���� MeetGubun
		String sJigeupDt = Util.getCtxStr(ctx, "RACE_DAY"); // ������

		PosParameter param = new PosParameter();
		int i = 0;
		param.setWhereClauseParameter(i++, sMeetGubun); // ������
		param.setWhereClauseParameter(i++, sJigeupDt); // ������
		param.setWhereClauseParameter(i++, sMeetGubun); // ������
		param.setWhereClauseParameter(i++, sJigeupDt); // ������
		
		
		
		PosRowSet keyRecord = this.rbmdao.find("rsm1020_s05", param);
		PosRow pr[] = keyRecord.getAllRow();
		
		int intCount =0; // ���� ���� ī��Ʈ
		for(int j=0;j<pr.length;j++)
		{
			String TF = String.valueOf(pr[j].getAttribute("TF"));
			if(!"001".equals(TF))
			{
				intCount++;
			}
		}
		if(intCount > 0|| pr.length==0 )
		{
			rtnKey="002";
		}
		else
		{
			rtnKey="001";
		}
		
		return rtnKey;
	}
	
	
	// 2�� ���� Ȯ��(SDL_DT TBJI_PC_SELLST)
	public String strVeriDTSellstSnd() {
		String rtnKey = "";

		
		String sMeetGubun = (String) ctx.get("MEET_GUBUN"); // ���� MeetGubun
		String sJigeupDt = Util.getCtxStr(ctx, "RACE_DAY"); // ������

		PosParameter param = new PosParameter();
		int i = 0;
		param.setWhereClauseParameter(i++, sMeetGubun); // ������
		param.setWhereClauseParameter(i++, sJigeupDt); // ������
		param.setWhereClauseParameter(i++, sMeetGubun); // ������
		param.setWhereClauseParameter(i++, sJigeupDt); // ������
		
		
		
		PosRowSet keyRecord = this.rbmdao.find("rsm1020_s06", param);
		PosRow pr[] = keyRecord.getAllRow();
		
		int intCount =0; // ���� ���� ī��Ʈ
		for(int j=0;j<pr.length;j++)
		{
			String TF = String.valueOf(pr[j].getAttribute("TF"));
			if(!"001".equals(TF))
			{
				intCount++;
			}
		}
		if(intCount > 0|| pr.length==0 )
		{
			rtnKey="002";
		}
		else
		{
			rtnKey="001";
		}
		
		return rtnKey;
	}
	
	// 2�� ���� Ȯ��(SDL_DT TBJI_PC_TELMP)
	public String strVeriDTTelmpSnd() {
		String rtnKey = "";

		
		String sMeetGubun = (String) ctx.get("MEET_GUBUN"); // ���� MeetGubun
		String sJigeupDt = Util.getCtxStr(ctx, "RACE_DAY"); // ������

		PosParameter param = new PosParameter();
		int i = 0;
		param.setWhereClauseParameter(i++, sMeetGubun); // ������
		param.setWhereClauseParameter(i++, sJigeupDt); // ������
		param.setWhereClauseParameter(i++, sMeetGubun); // ������
		param.setWhereClauseParameter(i++, sJigeupDt); // ������
		
		
		
		PosRowSet keyRecord = this.rbmdao.find("rsm1020_s07", param);
		PosRow pr[] = keyRecord.getAllRow();
		
		int intCount =0; // ���� ���� ī��Ʈ
		for(int j=0;j<pr.length;j++)
		{
			String TF = String.valueOf(pr[j].getAttribute("TF"));
			if(!"001".equals(TF))
			{
				intCount++;
			}
		}
		if(intCount > 0|| pr.length==0 )
		{
			rtnKey="002";
		}
		else
		{
			rtnKey="001";
		}
		
		return rtnKey;
	}
	
	
	
	
	

	// ���� ���� Ȯ��
	public String selectVeriFile(int [] nFileCount) {
		String sRaceDay = (String) ctx.get("RACE_DAY"); // ������ RaceDay
		
		String strFILE_VERI="002";
		int intFailCount=0;
		
		
		if(!"".equals(sRaceDay)&&sRaceDay!=null)
		{
			
			for(int i=0;i<nFileCount.length;i++)
			{
				if(nFileCount[i]==0)
				{
					intFailCount++;	
				}
			
			}
		}
		else
		{
			
			for(int i=0;i<2;i++)
			{
				if(nFileCount[i]==0)
				{
					intFailCount++;	
				}
			
			}
		}
		if(intFailCount==0)
		{
			strFILE_VERI ="001";
		}
		
		

		return strFILE_VERI;
	}
	
	// ���� ���� Ȯ��
	public String selectVeriFile() {
		
		String sStndYear = Util.getCtxStr(ctx, "STND_YEAR");
		String sDT = (String) ctx.get("DT"); // ����
		
		

		int i = 0;
		
		PosParameter param = new PosParameter();
		param.setWhereClauseParameter(i++, sStndYear); // 0.���س⵵
		param.setWhereClauseParameter(i++, sDT); // 1.��¥

		PosRowSet keyRecord = this.rbmjdbcDao.find("rsm1020_sVeriFile", param);

		PosRow pr[] = keyRecord.getAllRow();
		String RACE_DT = pr[0].getAttribute("RACE_DT")==null?"":pr[0].getAttribute("RACE_DT").toString() ; // ������
		
		
		int nFILE_001 = 0;
		int nFILE_002 = 0;
		int nFILE_003 = 0;
		int nFILE_004 = 0;
		
		int nFILE_005 = 0;
		int nFILE_006 = 0;
		int nFILE_007 = 0;
		int nFILE_008 = 0;
		String strFILE_VERI = "002";
		if (RACE_DT==null||"".equals(RACE_DT.trim())) { // �������� ���� ����
			
			for (int j = 0; j < pr.length; j++) {
				String FILE_CD = (String)pr[j].getAttribute("FILE_CD");
				int FILE_CNT = Integer.parseInt(pr[j].getAttribute("FILE_CNT").toString());
				String FILE_NM	= (String)pr[j].getAttribute("FILE_NM");
				
				if ("001".equals(FILE_CD)&& FILE_CNT > 0 && !"".equals(FILE_NM)) {// gita �� ���� ���� ���
					nFILE_001++;
					
				}
				if ("002".equals(FILE_CD)&& FILE_CNT > 0 && !"".equals(FILE_NM)) {	// ktax �� ���� ���� ���
					nFILE_002++;
					
				}
			}
			if (nFILE_001 >0 && nFILE_002 > 0) {	// gita �� ktax �� ���� �ִٸ� ����� ����
				strFILE_VERI = "001";
			}
		} else {
			
			for (int j = 0; j < pr.length; j++) {
				String FILE_CD = (String)pr[j].getAttribute("FILE_CD");
				int FILE_CNT = Integer.parseInt(pr[j].getAttribute("FILE_CNT").toString());
				String FILE_NM	= (String)pr[j].getAttribute("FILE_NM");
				if ("001".equals(FILE_CD)&& FILE_CNT > 0 && !"".equals(FILE_NM)) {	// gita �� ���� ���� ���
					nFILE_001++;
				}
				if ("002".equals(FILE_CD)&& FILE_CNT > 0 && !"".equals(FILE_NM)) {	// ktax �� ���� ���� ���
					nFILE_002++;
				}
				if ("003".equals(FILE_CD)&& FILE_CNT > 0 && !"".equals(FILE_NM)) {	// payoff �� ���� ���� ���
					nFILE_003++;
				}
				if ("004".equals(FILE_CD)&& FILE_CNT > 0 && !"".equals(FILE_NM)) {	// result �� ���� ���� ���
					nFILE_004++;
				}
				
				if ("005".equals(FILE_CD)&& FILE_CNT > 0 && !"".equals(FILE_NM)) {	// gita �� ���� ���� ���
					nFILE_005++;
				}
				if ("006".equals(FILE_CD)&& FILE_CNT > 0 && !"".equals(FILE_NM)) {	// ktax �� ���� ���� ���
					nFILE_006++;
				}
				if ("007".equals(FILE_CD)&& FILE_CNT > 0 && !"".equals(FILE_NM)) {	// payoff �� ���� ���� ���
					nFILE_007++;
				}
				if ("008".equals(FILE_CD)&& FILE_CNT > 0 && !"".equals(FILE_NM)) {	// result �� ���� ���� ���
					nFILE_008++;
				}
			}
			if (nFILE_001 > 0 && nFILE_002 > 0 && nFILE_003 > 0 && nFILE_004 > 0 && nFILE_005 > 0 && nFILE_006 > 0 && nFILE_007 > 0 && nFILE_008 > 0) {	// gita, ktax,payoff, result �� ���� �ִٸ� ����� ����
				strFILE_VERI = "001";
			}
		}
		

		return strFILE_VERI;
	}

	// 1�� ���� �Է�
	public int insertVeriFst(PosRecord record,String SESSION_USER_ID,String sFile,String sFstVeri,String sSndVeri, String sVeri) {
//		String SESSION_USER_ID = Util.trim(ctx.getSessionUserData("USER_ID".trim()));
		
		String sMeetGubun = (String) ctx.get("MEET_GUBUN"); // ���� MeetGubun
		String sStndYear = Util.getCtxStr(ctx, "STND_YEAR");
		String sMeetCd = (String)record.getAttribute("MEET_CD") ;
//		String sDT = (String) ctx.get("DT"); // ����
		PosParameter param = new PosParameter();

		int i = 0;

		param.setValueParamter(i++, sStndYear); // 0.���س⵵
		param.setValueParamter(i++, sMeetGubun); // 1.���а�		
		param.setValueParamter(i++, SavePCFileUpload.searchTms(sMeetGubun)); // 2.���а�
		param.setValueParamter(i++, SavePCFileUpload.searchDay(sMeetGubun)); // 3.���а�
		
		param.setValueParamter(i++, record.getAttribute("DT")); 		// 4.��¥
		param.setValueParamter(i++, sFile); 	// 5.���� ���ε� ���
		
		param.setValueParamter(i++, sFstVeri); 	// 6. 1�� ���� ���
		param.setValueParamter(i++, sSndVeri); 	// 7. 2�� ���� ���
		param.setValueParamter(i++, sVeri); 	// 8. ���� ���
		param.setValueParamter(i++, SESSION_USER_ID); // 9.�α��� ����� ���̵�
		param.setValueParamter(i++, SESSION_USER_ID); // 10.�α��� ����� ���̵�

		int dmlcount = this.rbmjdbcDao.update("rsm1020_mVeriFst", param);

		return dmlcount;
	}

}
