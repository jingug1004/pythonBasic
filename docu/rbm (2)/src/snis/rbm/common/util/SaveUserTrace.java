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
public class SaveUserTrace extends SnisActivity {
	public SaveUserTrace() {
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
		
		if ("E".equals(sJobType)) {		// 비정상 종료시의 종료정보
			finishUserTrace(ctx);
		} else if ("P".equals(sJobType)) {	// 개인정보 접근 정보
			SavePersonalAccess(ctx);
		}

		return PosBizControlConstants.SUCCESS;
	}

	
	private void finishUserTrace(PosContext ctx) {
    	int nSaveCount   = 0; 
    	String sDsName   =  "dsUserTrace";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);  
	        	nTempCnt = updateUserTrace(record);	        	
		        nSaveCount = nSaveCount + nTempCnt;
	        }	        
        }        
        Util.setSaveCount  (ctx, nSaveCount     );
	}	

    
    /**
     * <p> IP 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateUserTrace(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("LOGOUT_DT"));	// 종료일시

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("LOG_SEQ"));		// 사용자접속정보Sequence

        int dmlcount = this.getDao("rbmdao").update("user_trace_finish", param);
        return dmlcount;
    }

	
	private void SavePersonalAccess(PosContext ctx) {
        PosParameter param = new PosParameter();  
        String sQueryId    = "personal_access_insert";
    	
		String sLogSeq     = (String) ctx.get("LOG_SEQ");
		String sFormId     = (String) ctx.get("FORM_ID");
		String sAccessType = (String) ctx.get("ACCESS_TYPE");
		String sAction     = (String) ctx.get("ACTION");
		String sParam      = (String) ctx.get("PARAM");
		
		logger.logInfo("SavePersonalAccess------------------>");
		logger.logInfo("FORM_ID["+sFormId+"]");
		logger.logInfo("ACCESS_TYPE["+sAccessType+"]");
		logger.logInfo("ACTION["+sAction+"]");
		logger.logInfo("PARAM["+sParam+"]");
		
    	int i = 0;		
        param.setValueParamter(i++, sLogSeq);			//
        param.setValueParamter(i++, sLogSeq);			//
        param.setValueParamter(i++, sFormId);			//
        param.setValueParamter(i++, sAccessType);		//
        param.setValueParamter(i++, sAction);			//
        param.setValueParamter(i++, sParam);			//

        int nSaveCount = this.getDao("rbmdao").update(sQueryId, param);

        Util.setSaveCount  (ctx, nSaveCount     );		
        
	}	

    	
}
