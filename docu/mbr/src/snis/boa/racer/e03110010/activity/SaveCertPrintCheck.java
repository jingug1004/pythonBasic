/*================================================================================
 * 시스템		: 선수관리
 * 소스파일 이름: snis.boa.racer.e03110010.activity.SaveCertPrintCheck.java
 * 파일설명		: 증명서발급여부 저장
 * 작성자		: 김경화
 * 버전			: 1.0.0
 * 생성일자		: 2008-03-07
 * 최종수정일자	: 
 * 최종수정자	: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.racer.e03110010.activity;

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

import com.posdata.glue.PosException;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 증명서발급정보를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 김경화
* @version 1.0
*/
public class SaveCertPrintCheck extends SnisActivity
{    
    public SaveCertPrintCheck()
    {
    }

    /**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	// 사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
        
    	saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutCertIssu");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	nSaveCount = nSaveCount + saveCertIssue(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 증명서발급 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveCertIssue(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	//확인여부가 "확인"일 때만 인쇄여부 저장
    	if(Util.trim(record.getAttribute("PRS_STAT_CD")).equals("002")){
    		//인쇄여부 저장
    		effectedRowCnt =  insertIssue(record);
    		//확인여부저장
    		insertIssueConfirm(record);
    		
    		//커뮤니티 신청만 pdf생성 및 sms 발송
    		if("2".equals(Util.trim(record.getAttribute("REQ_TYPE")))){   			
    			String fileName = makePdfReport(Util.trim(record.getAttribute("ISSU_YEAR")), Util.trim(record.getAttribute("SEQ")), Util.trim(record.getAttribute("ISSU_NO")), Util.trim(record.getAttribute("RES_NO")));		//PDF 파일 생성
    			insertPdfFileInfo(fileName, record);	//PDF 정보 저장
    			
    			//SMS 전송
    			String msg = "신청하신 증명서 발급이 완료되었습니다. \n(선수커뮤니티에서 출력가능 - 경정 운영팀)";
    			sendSms(Util.trim(record.getAttribute("CELPON_NO")), Util.trim(record.getAttribute("NM_KOR")), msg);
    		}
    	}
    	return effectedRowCnt;
    }

 
    /**
     * <p>증명서발급  인쇄여부  저장</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertIssue(PosRecord record) 
    {
    	
        PosParameter param = new PosParameter();
        int i = 0;
       
        param.setValueParamter(i++, Util.trim(record.getAttribute("ISSU_YN")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ISSU_NO")));        
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, Util.trim(record.getAttribute("ISSU_YEAR")));
        param.setValueParamter(i++, record.getAttribute("SEQ"));
        
        return this.getDao("boadao").update("tbec_cert_issu_history_uc002", param);
    }

    /**
     * <p>증명서발급  확인여부 저장</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertIssueConfirm(PosRecord record) 
    {
    	
        PosParameter param = new PosParameter();
        int i = 0;
        
        String rjtRs = "";
        
        if(!"002".equals(Util.trim(record.getAttribute("PRS_STAT_CD")))){
        	rjtRs = Util.trim(record.getAttribute("RJT_RS"));
        }
        
        param.setValueParamter(i++, Util.trim(record.getAttribute("PRS_STAT_CD")));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, Util.trim(record.getAttribute("PERIOD_CNTN")));		//IWORKS_SFR006 2013.12.21
        param.setValueParamter(i++, rjtRs);												//IWORKS_SFR006 2013.12.21
        param.setValueParamter(i++, Util.trim(record.getAttribute("ISSU_YEAR")));
        param.setValueParamter(i++, record.getAttribute("SEQ"));
        
        return this.getDao("boadao").update("tbec_cert_issu_history_uc001", param);
    }
    
    /**
     * <p>PDF 정보 저장</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  none
     * @throws  none
     */
    private void insertPdfFileInfo(String fileName, PosRecord record){
    	PosParameter param = new PosParameter();
        int i = 0;
       
        param.setValueParamter(i++, fileName);
        param.setValueParamter(i++, Util.trim(record.getAttribute("ISSU_YEAR")));
        param.setValueParamter(i++, record.getAttribute("SEQ"));
        
        this.getDao("boadao").update("tbec_cert_issu_history_uc003", param);
    }
    
    //PDF 생성
    private String makePdfReport(String issuYear, String seq, String issuNo, String resNo){
    	String fileName = "";
		List params = new ArrayList();
		
		params.add(new BasicNameValuePair("pageId", "E03119010"));
		params.add(new BasicNameValuePair("IMG_URL", ""));
		params.add(new BasicNameValuePair("SEAL_IMG_URL", ""));
		params.add(new BasicNameValuePair("ISSU_YEAR", issuYear));
		params.add(new BasicNameValuePair("SEQ", seq));
		params.add(new BasicNameValuePair("ISSU_NO", issuNo));
		params.add(new BasicNameValuePair("RES_NO", resNo));
		
		HttpClient client = new DefaultHttpClient();
		//HttpPost httpPost = new HttpPost("http://can.kcycle.or.kr/can/ubireport/sample/reportTest.jsp");
		HttpPost httpPost = new HttpPost("http://can.kcycle.or.kr/can/jsp/pdfReportGenerator.jsp");
		HttpResponse response = null;
		
		BufferedReader br = null;
		
		try{
			httpPost.setEntity(new UrlEncodedFormEntity(params, "EUC-KR"));
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
				throw new PosException("선수증명서를 생성하지 못했습니다. \n(생성오류)");
			}
			else{
				if("Y".equals(returnValues[0])){
					fileName = returnValues[1].trim();
				}
				else{
					throw new PosException("선수증명서를 생성하지 못했습니다. \n(생성오류)");
				}
			}
		}
		catch(PosException e){
			throw e;
		}
		catch(Exception e){
			throw new PosException("선수증명서를 생성하지 못했습니다. \n(서버오류)");
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
	private boolean sendSms(String recPhoNo, String recNm, String msg){		
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