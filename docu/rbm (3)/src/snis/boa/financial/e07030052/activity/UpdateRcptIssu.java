package snis.boa.financial.e07030052.activity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

import com.posdata.glue.PosException;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;

public class UpdateRcptIssu extends SnisActivity{
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
		// 사용자 정보 확인
		if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
		
		String issuSeq   = (String) ctx.get("ISSU_SEQ");
		String issuYear  = (String) ctx.get("ISSU_YEAR");
		String racerNo   = (String) ctx.get("RACER_NO");
		String resNo     =  (String) ctx.get("RES_NO");
		String progStat  = (String) ctx.get("PROG_STAT");
		String rjtRs     = (String) ctx.get("RJT_RS");
		String seq       = (String) ctx.get("SEQ");
		String corpRegNo = (String) ctx.get("CORP_REG_NO");
		String fileName  = "";
		
		//SMS 전송
		String recPhoNo = (String)ctx.get("CELPON_NO");
		String recNm = (String)ctx.get("NM_KOR");
		String msg = "";
		
		if("200".equals(progStat)){
			//승인
			fileName = makePdfReport(seq, issuYear, racerNo, resNo, corpRegNo);			//원천징수영수증 생성 (pdf)
			updateRcptIssuConf(ctx, issuYear, progStat, fileName, seq);						//DB처리
			
			//SMS 메세지
			msg = "신청하신 증명서발급이 완료되었습니다. \n(선수커뮤니티에서 출력가능 - 경정 선수지원팀)";
		}
		else{
			//반려
			updateRcptIssuRej(ctx, progStat, rjtRs, seq);							//DB 처리
			
			//SMS 메세지
			msg = "신청하신 증명서발급이 반려되었습니다. \n(선수커뮤니티 신청내역 참고 - 경정 선수지원팀)";
		}
		
		//SMS 발송
		if(!"".equals(recPhoNo)){
			sendSms(ctx, recPhoNo, recNm, msg);
		}
		
		return PosBizControlConstants.SUCCESS;
	}
	
	//승인처리
	private void updateRcptIssuConf(PosContext ctx, String issuYear, String progStat, String fileName, String seq) {
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, issuYear);
		param.setValueParamter(i++, progStat);
		param.setValueParamter(i++, SESSION_USER_ID.trim());
		param.setValueParamter(i++, fileName);
		param.setValueParamter(i++, SESSION_USER_ID.trim());
		param.setValueParamter(i++, seq);
		
		int dmlcount = this.getDao("boadao").update("tbeg_rcpt_issu_fu001", param);
		
		Util.setSaveCount  (ctx, dmlcount);
	}
	
	//반려처리
	private void updateRcptIssuRej(PosContext ctx, String progStat, String rjtRs, String seq) {
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, progStat);
		param.setValueParamter(i++, SESSION_USER_ID.trim());
		param.setValueParamter(i++, rjtRs);
		param.setValueParamter(i++, SESSION_USER_ID.trim());
		param.setValueParamter(i++, seq);
		
		int dmlcount = this.getDao("boadao").update("tbeg_rcpt_issu_fu002", param);
		
		Util.setSaveCount  (ctx, dmlcount);
	}
	
	//원천징수 영수증 생성
	private String makePdfReport(String issuSeq, String issuYear, String racerNo, String resNo, String corpRegNo){
		String fileName = "";
		List params = new ArrayList();
		
		params.add(new BasicNameValuePair("pageId",     "E07030052"));
		params.add(new BasicNameValuePair("p_bizPlcNo", corpRegNo));	//사업자등록번호 (경륜과 다름)
		params.add(new BasicNameValuePair("p_gbn",      "(test1)"));
		params.add(new BasicNameValuePair("p_payDtFr",  issuYear+"0101"));
		params.add(new BasicNameValuePair("p_payDtTo",  issuYear+"1231"));
		params.add(new BasicNameValuePair("p_juminNo",  resNo.substring(0,6)+"-"+resNo.substring(6,13)));
		params.add(new BasicNameValuePair("racerNo",    racerNo.trim()));
		params.add(new BasicNameValuePair("issuSeq",    issuSeq));
		params.add(new BasicNameValuePair("issuYear",   issuYear));
		
		HttpClient client = new DefaultHttpClient();
		//HttpPost httpPost = new HttpPost("http://can.kcycle.or.kr/can/ubireport/sample/reportTest.jsp");
		HttpPost httpPost = new HttpPost("http://can.kcycle.or.kr/can/jsp/pdfReportGenerator.jsp");
		HttpResponse response = null;
		
		BufferedReader br = null;
		
		try{
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			response = client.execute(httpPost);
			
			HttpEntity entity = response.getEntity();
			
			br = new BufferedReader(new InputStreamReader(entity.getContent()));
			String line = "";
			String result = "";
			
			while((line = br.readLine()) != null){
				result = result + line;
			}
			
			String[] returnValues = result.split("!&%");
			
			if(returnValues.length != 2){
				throw new PosException("사업소득원천징수영수증을 생성하지 못했습니다. \n(생성오류)");
			}
			else{
				if("Y".equals(returnValues[0])){
					fileName = returnValues[1].trim();
				}
				else{
					throw new PosException("사업소득원천징수영수증을 생성하지 못했습니다. \n(생성오류)");
				}
			}
		}
		catch(PosException e){
			throw e;
		}
		catch(Exception e){
			throw new PosException("사업소득원천징수영수증을 생성하지 못했습니다. \n(서버오류)\n\n\n"+e.toString());
		}
		finally{
			if(br != null){
				try{
					br.close();
				}
				catch(Exception ex){
					ex.printStackTrace();
				}	
			}
		}
		
		return fileName;
	}

	//SMS 전송
	private boolean sendSms(PosContext ctx, String recPhoNo, String recNm, String msg){
		String recPho1 = "";
		String recPho2 = "";
		String recPho3 = "";
		
		String senPho1 = "";
		String senPho2 = "";
		String senPho3 = "";
		
		StringTokenizer recSt = new StringTokenizer(recPhoNo, "),-");
		
		recPho1 = recSt.nextToken().trim();
		recPho2 = recSt.nextToken().trim();
		recPho3 = recSt.nextToken().trim();
		
		StringTokenizer senSt = new StringTokenizer(SESSION_TEL_NO, "),-");
		
		senPho1 = senSt.nextToken().trim();
		senPho2 = senSt.nextToken().trim();
		senPho3 = senSt.nextToken().trim();
		
		boolean isSuccess = false;
		CallableStatement proc = null;
		Connection conn = null;
	
		try{
			conn = this.getDao("boadao").getDBConnection();
			proc = conn.prepareCall("{CALL SMS.SP_SEND_SMS(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			proc.setString(1, recPho1);
			proc.setString(2, recPho2);
			proc.setString(3, recPho3);
			proc.setString(4, recNm);
			proc.setString(5, senPho1);
			proc.setString(6, senPho2);
			proc.setString(7, senPho3);
			proc.setString(8, SESSION_USER_NM);
			proc.setString(9, msg);
			proc.setString(10, "00000000");
			proc.setString(11, "00000000");
			proc.setString(12, "MRASYS");
			proc.setString(13, SESSION_USER_ID);
			
			isSuccess = proc.execute();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
		}
		
		return isSuccess;
	}
}
