package snis.boa.organization.e02070021.activity;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.StringTokenizer;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SaveTrngRqst extends SnisActivity{
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
		
		int nSaveCount   = 0; 
    	int nDeleteCount = 0; 

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsTrngRqst");
        nSize = ds.getRecordCount();
        
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            
            if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ){
            	nSaveCount = nSaveCount + updateTrngRqst(record);
            	
            	//SMS전송
            	if(!"200".equals(Util.trim(record.getAttribute("ACCEPT_STAT_CHK"))) && "200".equals(Util.trim(record.getAttribute("ACCEPT_STAT")))){
            		//접수
            		String msg = "신청하신 훈련이 접수되었습니다. - 경정 운영팀";
            		sendSms(Util.trim(record.getAttribute("CELPON_NO")), Util.trim(record.getAttribute("NM_KOR")), msg);
            	}
            	else if(!"300".equals(Util.trim(record.getAttribute("ACCEPT_STAT_CHK"))) && "300".equals(Util.trim(record.getAttribute("ACCEPT_STAT")))){
            		//반려
            		String msg = "신청하신 훈련이 반려되었습니다. - 경정 운영팀";
            		sendSms(Util.trim(record.getAttribute("CELPON_NO")), Util.trim(record.getAttribute("NM_KOR")), msg);
            	}
            }
        }
		
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
        
		return PosBizControlConstants.SUCCESS;
	}
	
	private int updateTrngRqst(PosRecord record){
		PosParameter param = new PosParameter();
        int i = 0;
       
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ACCEPT_STAT")));
        
        if(!"200".equals(Util.trim(record.getAttribute("ACCEPT_STAT_CHK"))) && "200".equals(Util.trim(record.getAttribute("ACCEPT_STAT")))){
        	//접수
        	param.setValueParamter(i++, SESSION_USER_ID);
        	param.setValueParamter(i++, SESSION_USER_ID);
        	param.setValueParamter(i++, SESSION_USER_ID);		//굳이 사용자명을 넘겨주지 않아도 되나 STAY가 아닌 어떤 문자열도 상관없음
        }
        else {
        	//단순저장/반려 (받아들인 값으로 기존 값을 대체해야하는지 판단)
        	param.setValueParamter(i++, "STAY_ID");
        	param.setValueParamter(i++, "STAY_ID");
        	param.setValueParamter(i++, "STAY_DT");
        }
        
        param.setValueParamter(i++, Util.trim(record.getAttribute("REJECT_REASON")));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, Util.trim(record.getAttribute("TRNG_RQST_SEQ")));
        
        return this.getDao("boadao").update("tbeb_trng_rqst_ub001", param);
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
