/*================================================================================
 * 시스템			: 공통
 * 소스파일 이름	: snis.rbm.common.util.updateUserTrace.java
 * 파일설명		: 로그인
 * 작성자			: 이영상
 * 버전			: 1.0.0
 * 생성일자		: 2011-07-29
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.common.util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

/**
 * 
 * @auther 신재선
 * @version 1.0
 */
public class SavePersonalMnAccess extends SnisActivity {
	public SavePersonalMnAccess() {
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

    	// 사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	String sJobType = (String) ctx.get("JOB_TYPE".trim());
    	
		SavePersonalMnAccess(ctx);

		return PosBizControlConstants.SUCCESS;
	}

	private void SavePersonalMnAccess(PosContext ctx) {
        PosParameter param 		= new PosParameter();  
        String sQueryId    		= "personal_mn_access_insert";
    	
		String sLogSeq     		= (String) ctx.get("LOG_SEQ");
		String sFormId     		= (String) ctx.get("FORM_ID");
		//String sAccessType 		= (String) ctx.get("ACCESS_TYPE");
		String sAction     		= (String) ctx.get("ACTION");
		String sParam      		= (String) ctx.get("PARAM");
		String sUserIP			= (String) ctx.get("USER_IP");
		String sUserId      	= (String) ctx.get("USER_ID");
		
		logger.logInfo("SavePersonalAccess------------------>");
		logger.logInfo("FORM_ID["+sFormId+"]");
		//logger.logInfo("ACCESS_TYPE["+sAccessType+"]");
		logger.logInfo("ACTION["+sAction+"]");
		logger.logInfo("PARAM["+sParam+"]");
		logger.logInfo("USER_IP["+sUserIP+"]");
		logger.logInfo("USER_ID["+sUserId+"]");
		
    	int i = 0;		
        param.setValueParamter(i++, sLogSeq);			//
        param.setValueParamter(i++, sLogSeq);			//
        param.setValueParamter(i++, sFormId);			//
        
        String type = null;
        boolean existActionStr = sAction.indexOf("[") != -1 ? true : false;
        System.out.println("sAction = "+sAction);
        if(existActionStr){
        	type = sAction.substring( sAction.indexOf("[")+2, sAction.indexOf("[")+3);
        	if(type.equals("a")) type = "U";
        	else if(type.equals("e")) type = "S";
        	System.out.println("type is "+type);
        }
        param.setValueParamter(i++, type);			//
        
        param.setValueParamter(i++, sAction);			//
        param.setValueParamter(i++, sParam);			//
        param.setValueParamter(i++, sUserIP);			//
        param.setValueParamter(i++, sUserId);			//

        int nSaveCount = this.getDao("rbmdao").update(sQueryId, param);

        Util.setSaveCount  (ctx, nSaveCount     );		
        
	}	

}
