/*================================================================================
 * �ý���			: ����
 * �ҽ����� �̸�	: snis.rbm.common.util.LoginSNIS.java
 * ���ϼ���		: �α���
 * �ۼ���			: �̿���
 * ����			: 1.0.0
 * ��������		: 2011-07-29
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.common.util;

import java.io.File;
import java.util.List; 
import java.util.Map;

import com.posdata.glue.biz.activity.PosActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosColumnDef;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowImpl;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.dao.vo.PosRowSetImpl;
 
/** 
 * 
 * @auther �̿���
 * @version 1.0
 */
public class LoginSNIS extends PosActivity {
	public LoginSNIS() {
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

		//logger.logInfo("USER_ID : " + ctx.get("USER_ID".trim()));
		//logger.logInfo("PSWD    : " + ctx.get("PSWD   ".trim()));
		loginUser(ctx);
		// generateReportPath(ctx);

		return PosBizControlConstants.SUCCESS;
	}

	/**
	 * <p>
	 * ��������� ��ȸ
	 * </p>
	 * 
	 * @param ctx
	 *            PosContext �����
	 * @param record
	 *            PosRecord �ڵ�׷�����
	 * @return none
	 * @throws none
	 */
	private void loginUser(PosContext ctx) {
		PosParameter param = new PosParameter();
		PosRowSet rowset;
		PosRowSet rowsetOther;
		PosRowSet rowsetOrg11;
		
		//Des des = new Des();
		String sUserId 		= (String) ctx.get("USER_ID ".trim());
		String sPswd 		= (String) ctx.get("PSWD    ".trim());
        String sPswdEnc 	= (String) ctx.get("PSWD_ENC".trim());       

		//String sPswdEn = des.Encode(sPswd);	--�� ��ȣȭ ��� 20141216
        String sPswdEn = EncryptSHA256.getEncryptSHA256(sPswd); // �űԹ��
        System.out.println("sPswd:"+sPswd);
        System.out.println("sPswdEn:"+sPswdEn);
        System.out.println("sPswdEnc:"+sPswdEnc);
		if ("Y".equals(sPswdEnc)) {	// ��ū���� ��ȣȭ�� �н����带 �ѿ����Ƿ� �ٽ� ��ȣȭ�� �ʿ� ����
        	sPswdEn    = sPswd;        	
        } 
		boolean bUserOk = false;
		int i = 0;
		
		//logger.logInfo("Encode PSWD >>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + sPswdEn);		
		// ��� �Է°��� ���̰� 5�ڸ��� �ƴ� ��� �̸����� �ν��Ͽ� �̸� ��ȸ
		if (sUserId.length() != 5) {
			i = 0;
			PosParameter paramId = new PosParameter();
			paramId.setWhereClauseParameter(i++, sUserId);
			rowset = this.getDao("rbmdao").find("tbrk_user_login_view_get_id", paramId);
			if (rowset.hasNext()) {	// ���������� �����ϴ� ��� ����� ���� ������ ������� ����
				PosRow rowUserId = rowset.next();
				sUserId = rowUserId.getAttribute("USER_ID").toString();
			} else {
				return;
			}
		}

		// �ڵ�׷찪�� �����ϸ�
		i = 0;
		param.setWhereClauseParameter(i++, sUserId);

		rowset = this.getDao("rbmdao").find("tbrk_user_login_view", param);

		// tbrk_user ���̺� ���� ���� Ȯ��
		// �������������� �Ҽ��� �ƴ� ��� ���� ���� ���ڰ���DB�� �����Ͽ� �ڷḦ ��ȸ�Ͽ� �α��� ó��
		if (rowset.hasNext()) {
			bUserOk = true;
		} else {
			// ���ְ���������� �Ҽ��� �ƴ� ���
System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>1");			
			rowsetOther = this.getDao("rbmdao").find("tbrk_user_login_view_other_org", param);
System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>2");			

			if (rowsetOther.hasNext()) {
				bUserOk = true;
				rowset = rowsetOther;
			} else {
				//��ȣ�� �ٸ� ���
				return;
			}
		}
		
		// ��� Ȯ�ν�(�����  ����)
		if (bUserOk == true) {
			PosRow row = rowset.next();
			String CraCd = "";

			// ��ȣ Ȯ�� 
			System.out.println("INPUT PSWD >>>>>>>>>>>>>>>>>>>>>>>>>> " + sPswdEn);
			System.out.println("DB PSWD >>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + row.getAttribute("PSWD".trim()));
			if (sPswd.equals("1")) {	//for test
			//if (sPswdEn.equals(row.getAttribute("PSWD".trim()))) {	//real  
					
				row.setAttribute("ISVALID", "T");
				
				// ��ǥ������ ��� ���� ���ӽ� ��ȣ ���� ��û ó��				
				if ("003".equals(row.getAttribute("GBN").toString())) {
					rowsetOther = this.getDao("rbmdao").find("emp_user_res_no", param);	
					if (rowsetOther.hasNext()) {
						PosRow rowResNo = rowsetOther.next();
						if (sPswd.equals(rowResNo.getAttribute("RES_NO").toString())) {
							row.setAttribute("CHNG_PSWD", "T");
						}
					}
				}
				ctx.setSessionUserData("USER_ID    ".trim(), row.getAttribute("USER_ID    ".trim()));
				ctx.setSessionUserData("USER_NM    ".trim(), row.getAttribute("USER_NM    ".trim()));
				ctx.setSessionUserData("TEL_NO     ".trim(), row.getAttribute("TEL_NO     ".trim()));
				if (row.getAttribute("CRA_CD") == null) {
					CraCd = "001";
					row.setAttribute("CRA_CD", CraCd);
				} else
					CraCd = (String) row.getAttribute("CRA_CD");
				ctx.setSessionUserData("CRA_CD     ".trim(), CraCd);
				
				//���������� DB�� ����
				String sLogSeq = SaveUserTrace("LOGIN", ctx, sUserId);	
				
				//�ߺ����� ���� üũ ���� �ʱ�ȭ
				String sSessionID = (String)ctx.get("JSESSIONID");
				if (sSessionID != null) SaveUserSession(sUserId, sSessionID);	
				
				ctx.setSessionUserData("LOG_SEQ".trim(), sLogSeq);	
				row.setAttribute("LOG_SEQ", sLogSeq);
				ctx.put("IS_LOGINED", "true");
			} else {	// ��ȣ�� Ʋ�� ���
				//System.out.println("DB PSWD ����ġ >>>>>>>>>>>>>>>>>>>>>>>>> "+sPswd);
				row.setAttribute("ISVALID", "F");
				logger.logInfo("USER VALIDATION CHECK ERROR (PASSWORD FAIL>>>> USER_ID[" + row.getAttribute("USER_ID".trim()) + "]");
			}
		}

		String sResultKey = "dsOutUser";
		ctx.put(sResultKey, rowset);
		Util.addResultKey(ctx, sResultKey);
		 
		// ���� db ���°� ��ȸ
		i=0;
		PosParameter paramIdOrg11 = new PosParameter();
		paramIdOrg11.setWhereClauseParameter(i++, "147");
		rowsetOrg11 = this.getDao("rbmdao").find("common_code", paramIdOrg11);
		
		sResultKey = "dsDbStatus";
		ctx.put(sResultKey, rowsetOrg11);
		Util.addResultKey(ctx, sResultKey);
	}

	/**
	 * Report��� return
	 * 
	 * @return
	 */
	public void generateReportPath(PosContext ctx) {
		PosRowSet posRowSet = null;

		// ����Ʈ ���� ��� ����
		String cName = "report_file_path";
		String FILE_SEPERATOR = File.separator;

		String winPath = "C:\\SNIS\\snis_root\\report_file\\";
		String unixPath = "/WAS/report_file/";
		String reportFilePath = System.getProperty("os.name").toUpperCase().indexOf("WIN") != -1 ? winPath : unixPath;

		// �÷� Define
		PosColumnDef[] columnDef = new PosColumnDef[1];
		columnDef[0] = new PosColumnDef(cName, 12, 100);

		// row����
		List<PosRow> rowList = new java.util.ArrayList<PosRow>();

		Map<String, String> rowMap = new java.util.HashMap<String, String>();
		rowMap.put(cName, reportFilePath);
		PosRow row = new PosRowImpl(rowMap);
		rowList.add(row);

		// rowset����
		posRowSet = new PosRowSetImpl(rowList);
		posRowSet.setColumnDefs(columnDef);

		// ����Ʈ ���� ��� �߰�
		String dsOutReportKey = "dsOutReportFilePath";
		ctx.put(dsOutReportKey, posRowSet);
		Util.addResultKey(ctx, dsOutReportKey);
	}
	
	private String SaveUserTrace(String sJobGubn, PosContext ctx, String sUserId) {
		String sQueryId = "user_trace_insert";
		String sLogSeq = "";
		int i = 0;
		
		String sUserIPAddr = (String) ctx.get("USER_IP".trim());
		String sUserID     = sUserId;
		String sOsVer = (String) ctx.get("OS_VERSION".trim());
		PosParameter param = new PosParameter();
		
		sLogSeq = Util.trim(ctx.getSessionUserData("LOG_SEQ    ".trim()));
		
		PosRowSet rowset = this.getDao("rbmdao").find("user_trace_key", param);
		
		if (rowset.hasNext()) {
			PosRow row = rowset.next();
			sLogSeq = row.getAttribute("LOG_SEQ").toString();
			
			logger.logInfo("SaveUserTrace------------------>");
			logger.logInfo("LOG_SEQ["+sLogSeq+"]");
			logger.logInfo("USER_ID["+sUserID+"]");
			logger.logInfo("USER_IP["+sUserIPAddr+"]");
			
			param.setWhereClauseParameter(i++, sLogSeq);
			param.setWhereClauseParameter(i++, sUserID);
			param.setWhereClauseParameter(i++, sUserIPAddr);
			param.setWhereClauseParameter(i++, sOsVer);

			if (!"LOGIN".equals(sJobGubn)) {
				System.out.println("LoginSNIS.SaveUserTrace: ERROR : sJobGubn -->"+sJobGubn);
			} 
			int dmlcount = this.getDao("rbmdao").update(sQueryId, param);
		} 
		return sLogSeq;
	} 
	
	private int SaveUserSession(String sUserId, String sSessionId)
	{
		if ("".equals(sSessionId)) return 0;
		//System.out.println("LoginSNIS::SaveUserSession::sUserId="+sUserId+", sSessionId="+sSessionId);
		
		int i = 0; 
		String sQueryId    = "user_login_chk_save";
		PosParameter param = new PosParameter();
		
		param.setWhereClauseParameter(i++, sUserId);
		param.setWhereClauseParameter(i++, sSessionId);
		int dmlcount = this.getDao("rbmdao").update(sQueryId, param);
		
		return dmlcount;
	}
}
