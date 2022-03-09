/*================================================================================
 * 시스템			: 공통
 * 소스파일 이름	: snis.rbm.common.util.LoginSNIS.java
 * 파일설명		: 로그인
 * 작성자			: 이영상
 * 버전			: 1.0.0
 * 생성일자		: 2011-07-29
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
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
 * @auther 이영상
 * @version 1.0
 */
public class LoginSNIS extends PosActivity {
	public LoginSNIS() {
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

		//logger.logInfo("USER_ID : " + ctx.get("USER_ID".trim()));
		//logger.logInfo("PSWD    : " + ctx.get("PSWD   ".trim()));
		loginUser(ctx);
		// generateReportPath(ctx);

		return PosBizControlConstants.SUCCESS;
	}

	/**
	 * <p>
	 * 사용자정보 조회
	 * </p>
	 * 
	 * @param ctx
	 *            PosContext 저장소
	 * @param record
	 *            PosRecord 코드그룹정보
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

		//String sPswdEn = des.Encode(sPswd);	--구 암호화 방식 20141216
        String sPswdEn = EncryptSHA256.getEncryptSHA256(sPswd); // 신규방식
        System.out.println("sPswd:"+sPswd);
        System.out.println("sPswdEn:"+sPswdEn);
        System.out.println("sPswdEnc:"+sPswdEnc);
		if ("Y".equals(sPswdEnc)) {	// 토큰에서 암호화된 패스워드를 넘여오므로 다시 암호화할 필요 없음
        	sPswdEn    = sPswd;        	
        } 
		boolean bUserOk = false;
		int i = 0;
		
		//logger.logInfo("Encode PSWD >>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + sPswdEn);		
		// 사번 입력값의 길이가 5자리가 아닌 경우 이름으로 인식하여 이름 조회
		if (sUserId.length() != 5) {
			i = 0;
			PosParameter paramId = new PosParameter();
			paramId.setWhereClauseParameter(i++, sUserId);
			rowset = this.getDao("rbmdao").find("tbrk_user_login_view_get_id", paramId);
			if (rowset.hasNext()) {	// 동명이인이 존재하는 경우 사번이 빠른 직원의 사번으로 셋팅
				PosRow rowUserId = rowset.next();
				sUserId = rowUserId.getAttribute("USER_ID").toString();
			} else {
				return;
			}
		}

		// 코드그룹값이 존재하면
		i = 0;
		param.setWhereClauseParameter(i++, sUserId);

		rowset = this.getDao("rbmdao").find("tbrk_user_login_view", param);

		// tbrk_user 테이블에 존재 여부 확인
		// 경륜경정사업본부 소속이 아닌 경우 직접 본부 전자결재DB에 접속하여 자료를 조회하여 로그인 처리
		if (rowset.hasNext()) {
			bUserOk = true;
		} else {
			// 경주경정사업본부 소속이 아닌 경우
System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>1");			
			rowsetOther = this.getDao("rbmdao").find("tbrk_user_login_view_other_org", param);
System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>2");			

			if (rowsetOther.hasNext()) {
				bUserOk = true;
				rowset = rowsetOther;
			} else {
				//암호가 다른 경우
				return;
			}
		}
		
		// 사번 확인시(사용자  존재)
		if (bUserOk == true) {
			PosRow row = rowset.next();
			String CraCd = "";

			// 암호 확인 
			System.out.println("INPUT PSWD >>>>>>>>>>>>>>>>>>>>>>>>>> " + sPswdEn);
			System.out.println("DB PSWD >>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + row.getAttribute("PSWD".trim()));
			if (sPswd.equals("1")) {	//for test
			//if (sPswdEn.equals(row.getAttribute("PSWD".trim()))) {	//real  
					
				row.setAttribute("ISVALID", "T");
				
				// 투표소장인 경우 최초 접속시 암호 변경 요청 처리				
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
				
				//접속정보를 DB에 저장
				String sLogSeq = SaveUserTrace("LOGIN", ctx, sUserId);	
				
				//중복접속 여부 체크 로직 초기화
				String sSessionID = (String)ctx.get("JSESSIONID");
				if (sSessionID != null) SaveUserSession(sUserId, sSessionID);	
				
				ctx.setSessionUserData("LOG_SEQ".trim(), sLogSeq);	
				row.setAttribute("LOG_SEQ", sLogSeq);
				ctx.put("IS_LOGINED", "true");
			} else {	// 암호가 틀린 경우
				//System.out.println("DB PSWD 불일치 >>>>>>>>>>>>>>>>>>>>>>>>> "+sPswd);
				row.setAttribute("ISVALID", "F");
				logger.logInfo("USER VALIDATION CHECK ERROR (PASSWORD FAIL>>>> USER_ID[" + row.getAttribute("USER_ID".trim()) + "]");
			}
		}

		String sResultKey = "dsOutUser";
		ctx.put(sResultKey, rowset);
		Util.addResultKey(ctx, sResultKey);
		 
		// 공단 db 상태값 조회
		i=0;
		PosParameter paramIdOrg11 = new PosParameter();
		paramIdOrg11.setWhereClauseParameter(i++, "147");
		rowsetOrg11 = this.getDao("rbmdao").find("common_code", paramIdOrg11);
		
		sResultKey = "dsDbStatus";
		ctx.put(sResultKey, rowsetOrg11);
		Util.addResultKey(ctx, sResultKey);
	}

	/**
	 * Report경로 return
	 * 
	 * @return
	 */
	public void generateReportPath(PosContext ctx) {
		PosRowSet posRowSet = null;

		// 레포트 파일 경로 설정
		String cName = "report_file_path";
		String FILE_SEPERATOR = File.separator;

		String winPath = "C:\\SNIS\\snis_root\\report_file\\";
		String unixPath = "/WAS/report_file/";
		String reportFilePath = System.getProperty("os.name").toUpperCase().indexOf("WIN") != -1 ? winPath : unixPath;

		// 컬럼 Define
		PosColumnDef[] columnDef = new PosColumnDef[1];
		columnDef[0] = new PosColumnDef(cName, 12, 100);

		// row생성
		List<PosRow> rowList = new java.util.ArrayList<PosRow>();

		Map<String, String> rowMap = new java.util.HashMap<String, String>();
		rowMap.put(cName, reportFilePath);
		PosRow row = new PosRowImpl(rowMap);
		rowList.add(row);

		// rowset생성
		posRowSet = new PosRowSetImpl(rowList);
		posRowSet.setColumnDefs(columnDef);

		// 레포트 파일 경로 추가
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
