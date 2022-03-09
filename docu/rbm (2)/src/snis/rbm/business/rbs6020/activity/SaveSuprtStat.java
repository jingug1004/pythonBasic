/*================================================================================
 * 시스템			: 지원신청내역 관리
 * 소스파일 이름	: snis.rbm.business.rbs6020.activity.SaveEvntMana.java
 * 파일설명		: 기동요원를 등록하고 수정하는 기능을 수행한다.
 * 작성자			: 신재선
 * 버전			: 1.0.0
 * 생성일자		: 2012-11-28
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs6020.activity;

import java.text.SimpleDateFormat; 
import java.util.Date;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.system.rsy7010.activity.SaveAlarmHist;

public class SaveSuprtStat extends SnisActivity {
	
	public SaveSuprtStat(){}

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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0; 

		
        String StatCd = (String)ctx.get("CNCL_STAT_CD");
        if ("100".equals(StatCd) || "110".equals(StatCd) || "120".equals(StatCd)) {	// 취소요청이나 취소처리인 경우
            // 지원신청상태 저장
        	nDeleteCount += saveSuptCancel(ctx, StatCd);			        
        } else {
            // 지원신청상태 저장
            nSaveCount += saveSuptStat(ctx);			        
        }
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
        
        return ;
    }

    /**
     * <p> 지원신청내역 취소 요청 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveSuptStat(PosContext ctx) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        
        String StatCd = (String)ctx.get("STAT_CD");
        String BrncAprvId = null;        
        String MngrAprvId = null;
        String RsltAprvId = null;
        if ("002".equals(StatCd) || "012".equals(StatCd) || "022".equals(StatCd)) {	// 승인요청시 해당부서 팀장을 조회하여 설정한다.
        	PosParameter param2 = new PosParameter();
        	PosRowSet rowset;
    		i = 0;            
    		
    		String teamCd = ctx.get("TEAM_CD").toString().substring(2);
            param2.setWhereClauseParameter(i++, teamCd);			//팀코드
            
    		rowset = this.getDao("rbmdao").find("rbs6020_s03", param2);
    		if (rowset.hasNext()) {		// 지점의 지점장 조회(각 부서(실,팀)의 부서장)
				PosRow row = rowset.next();
				if ("002".equals(StatCd)) {
					BrncAprvId = (String)row.getAttribute("CAPTAIN_ID");
					//BrncAprvId = "01047";
				} else if ("012".equals(StatCd)) {
					BrncAprvId = (String)row.getAttribute("CAPTAIN_ID");
					//BrncAprvId = "01047";
				} else {
					RsltAprvId = (String)row.getAttribute("CAPTAIN_ID");
					//RsltAprvId = "01047";
				}
    		} else {
    			Util.setSvcMsgCode(ctx, "SNIS_RBM_E027");
    			return -1;
    		}
        }
        i = 0;
        param.setValueParamter(i++, (String)ctx.get("STAT_CD"));		//상태
        param.setValueParamter(i++, BrncAprvId);		//지점장 사번
        param.setValueParamter(i++, MngrAprvId);		//관리부서 결재자 사번
        param.setValueParamter(i++, RsltAprvId);		//관리부서 결재자 사번
        param.setValueParamter(i++, (String)ctx.get("STAT_CD"));		//상태
        param.setValueParamter(i++, (String)ctx.get("STAT_CD"));		//상태
        param.setValueParamter(i++, (String)ctx.get("STAT_CD"));		//상태        
        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(수정자)
        param.setValueParamter(i++, (String)ctx.get("SEQ_NO"));			//지원신청 연번
                		
        int dmlcount = this.getDao("rbmdao").update("rbs6020_u01", param);
        
        return dmlcount;
    }

    /**
     * <p> 지원신청내역 취소 요청 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveSuptCancel(PosContext ctx, String CancelStat) 
    {
    	String StatCd = null;
    	String CnclStatCd = null;
        PosParameter param = new PosParameter();   
        int i = 0;        
       
        if ("110".equals(CancelStat)) {
        	CnclStatCd = "002";
        	StatCd = "032";			// 취소
        } else if ("120".equals(CancelStat)) {
        	CnclStatCd = "000";
        } else {
        	CnclStatCd = "001";		// 취소요청
        }
        
        
        param.setValueParamter(i++, CnclStatCd);						//취소요청상태
        param.setValueParamter(i++, StatCd);							//상태
        param.setValueParamter(i++, (String)ctx.get("CNCL_RSN"));		//취소사유
        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(수정자)
        param.setValueParamter(i++, (String)ctx.get("SEQ_NO"));			//지원신청 연번
                		
        int dmlcount = this.getDao("rbmdao").update("rbs6020_u02", param);
        
        return dmlcount;
    }
}