package snis.can.common.util;

import com.posdata.glue.biz.activity.PosActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/*================================================================================
 * 시스템			: 개인정보 접근 기록
 * 소스파일 이름	: 
 * 파일설명		: 개인정보 접근 기록
 * 작성자			: jdlee
 * 버전			: 1.0.0
 * 생성일자		: 2014-05-04
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 게시물을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 이영상
* @version 1.0
*/
public class SavePersonalAccess extends SnisActivity
{    		
	public SavePersonalAccess()
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
			
			logger.logDebug("################begin SavePersonalAccess");
			
			
    	savePersonalAccess(ctx);
    	
      return PosBizControlConstants.SUCCESS;
    }


    
    /**
	 * 개인정보 접근 기록
	 * 
	 * @return
	 */
		private void savePersonalAccess(PosContext ctx) {
			PosParameter param = new PosParameter();  
			String sQueryId    = "personal_access_insert";
			
			String sUserId     = SESSION_USER_ID;
			String sLogSeq     = (String) ctx.get("LOG_SEQ");
			String sFormId     = (String) ctx.get("FORM_ID");
			String sAccessType = (String) ctx.get("ACCESS_TYPE");
			String sAction     = (String) ctx.get("ACTION");
			String sParam      = (String) ctx.get("PARAM");
			
			int i = 0;		
			param.setValueParamter(i++, sUserId);			//
			param.setValueParamter(i++, sLogSeq);			//
			param.setValueParamter(i++, sUserId);			//
			param.setValueParamter(i++, sLogSeq);			//
			param.setValueParamter(i++, sFormId);			//
			param.setValueParamter(i++, sAccessType);		//
			param.setValueParamter(i++, sAction);			//
			param.setValueParamter(i++, sParam);			//
			
			int nSaveCount = this.getDao("candao").update(sQueryId, param);
			
			Util.setSaveCount  (ctx, nSaveCount     );		
		
		}
    
}
