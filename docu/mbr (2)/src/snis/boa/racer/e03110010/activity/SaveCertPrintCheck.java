/*================================================================================
 * �ý���		: ��������
 * �ҽ����� �̸�: snis.boa.racer.e03110010.activity.SaveCertPrintCheck.java
 * ���ϼ���		: �����߱޿��� ����
 * �ۼ���		: ���ȭ
 * ����			: 1.0.0
 * ��������		: 2008-03-07
 * ������������	: 
 * ����������	: 
 * ������������	: 
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
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �����߱������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���ȭ
* @version 1.0
*/
public class SaveCertPrintCheck extends SnisActivity
{    
    public SaveCertPrintCheck()
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
        
    	saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
    * @param   ctx		PosContext	�����
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
     * <p> �����߱� Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveCertIssue(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	//Ȯ�ο��ΰ� "Ȯ��"�� ���� �μ⿩�� ����
    	if(Util.trim(record.getAttribute("PRS_STAT_CD")).equals("002")){
    		//�μ⿩�� ����
    		effectedRowCnt =  insertIssue(record);
    		//Ȯ�ο�������
    		insertIssueConfirm(record);
    		
    		//Ŀ�´�Ƽ ��û�� pdf���� �� sms �߼�
    		if("2".equals(Util.trim(record.getAttribute("REQ_TYPE")))){   			
    			String fileName = makePdfReport(Util.trim(record.getAttribute("ISSU_YEAR")), Util.trim(record.getAttribute("SEQ")), Util.trim(record.getAttribute("ISSU_NO")), Util.trim(record.getAttribute("RES_NO")));		//PDF ���� ����
    			insertPdfFileInfo(fileName, record);	//PDF ���� ����
    			
    			//SMS ����
    			String msg = "��û�Ͻ� ���� �߱��� �Ϸ�Ǿ����ϴ�. \n(����Ŀ�´�Ƽ���� ��°��� - ���� ���)";
    			sendSms(Util.trim(record.getAttribute("CELPON_NO")), Util.trim(record.getAttribute("NM_KOR")), msg);
    		}
    	}
    	return effectedRowCnt;
    }

 
    /**
     * <p>�����߱�  �μ⿩��  ����</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
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
     * <p>�����߱�  Ȯ�ο��� ����</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
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
     * <p>PDF ���� ����</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
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
    
    //PDF ����
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
				throw new PosException("���������� �������� ���߽��ϴ�. \n(��������)");
			}
			else{
				if("Y".equals(returnValues[0])){
					fileName = returnValues[1].trim();
				}
				else{
					throw new PosException("���������� �������� ���߽��ϴ�. \n(��������)");
				}
			}
		}
		catch(PosException e){
			throw e;
		}
		catch(Exception e){
			throw new PosException("���������� �������� ���߽��ϴ�. \n(��������)");
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
    
    //SMS ����
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