/*================================================================================
 * 시스템			: 공통
 * 소스파일 이름	: snis.rbm.common.util.saveApproval.java
 * 파일설명		: 결재일련번호 조회 및 결재관리 저장
 * 작성자			: 이영상
 * 버전			: 1.0.0
 * 생성일자		: 2011-07-28
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.common.util;

import com.posdata.glue.biz.activity.PosActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 착순점/사고점를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 이영상
* @version 1.0
*/
public class saveApproval extends PosActivity
{    
    public saveApproval()
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
    	Util.clearReturnParam(ctx);
    	
        String sSeqNo = getSeqNo(ctx);
        
        Util.addReturnParam(ctx);
        
        //saveAppr(ctx, sSeqNo);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 결재일련번호 조회 </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    private String getSeqNo(PosContext ctx)
    {
    	String[] arrRet = {""};
        PosParameter param = new PosParameter();        
        
        String sJob 		= Util.getCtxStr(ctx, "JOB");
		String sStndYear 	= Util.getCtxStr(ctx, "STND_YEAR");		
        String sTms 		= Util.getCtxStr(ctx, "TMS");
        String sDayOrd 		= Util.getCtxStr(ctx, "DAY_ORD");
        String sRaceNo 		= Util.getCtxStr(ctx, "RACE_NO");
        String sUserId 		= Util.getCtxStr(ctx, "USER_ID");
        String sFirstSeq	= sJob+sStndYear+sTms+sDayOrd+sRaceNo+sUserId;
        
        int iParam = 0;
        param.setWhereClauseParameter(iParam++, sJob);
    	param.setWhereClauseParameter(iParam++, sStndYear);
    	param.setWhereClauseParameter(iParam++, sTms);
    	param.setWhereClauseParameter(iParam++, sDayOrd);
    	param.setWhereClauseParameter(iParam++, sRaceNo);
    	param.setWhereClauseParameter(iParam++, sUserId);
    	
    	PosRowSet prs = this.getDao("rbmdao").find("common_approval_seq_no", param);
        
    	PosRow pr[] = prs.getAllRow();    	  		
    	arrRet[0] = sFirstSeq+Util.getRosStr(pr[0], "SEQ");		
    	Util.setReturnParam(ctx, "SEQ", arrRet[0]);
    	
    	return arrRet[0].toString();
    	
    }
    
    
    /**
     * <p> 결재관리 저장 </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    private int saveAppr(PosContext ctx, String sSeqNo)
    {
    	PosParameter param = new PosParameter();        
        
        String sJob 		= Util.getCtxStr(ctx, "JOB");
		String sStndYear 	= Util.getCtxStr(ctx, "STND_YEAR");		
        String sTms 		= Util.getCtxStr(ctx, "TMS");
        String sDayOrd 		= Util.getCtxStr(ctx, "DAY_ORD");
        String sRaceNo 		= Util.getCtxStr(ctx, "RACE_NO");
        String sSeq 		= Util.getCtxStr(ctx, "SEQ");
        String sUserId 		= Util.getCtxStr(ctx, "USER_ID");
        String sSubject		= Util.getCtxStr(ctx, "SUBJECT");
                
        int iParam = 0;
        param.setValueParamter(iParam++, sSeqNo);		//일련번호
        param.setValueParamter(iParam++, sJob);			//업무구분
    	param.setValueParamter(iParam++, sStndYear);	//기준년도
    	param.setValueParamter(iParam++, sTms);			//회차
    	param.setValueParamter(iParam++, sDayOrd);		//일차
    	param.setValueParamter(iParam++, sRaceNo);		//경주번호
    	param.setValueParamter(iParam++, sSeq);			//순번
    	param.setValueParamter(iParam++, sUserId);		//기안자
    	param.setValueParamter(iParam++, sSubject);		//문서제목
    	param.setValueParamter(iParam++, sUserId);		//작성자ID
    	param.setValueParamter(iParam++, sUserId);		//수정자ID
    	
    	
    	int dmlcount = this.getDao("rbmdao").update("common_approval_ins", param);
        
    	return dmlcount;
    	
    }
}

