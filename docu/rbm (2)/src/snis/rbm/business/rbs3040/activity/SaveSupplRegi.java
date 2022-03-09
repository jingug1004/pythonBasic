/*================================================================================
 * 시스템			: 소모품대장  관리
 * 소스파일 이름	: snis.rbm.business.rbs3040.activity.SaveSupplRegi.java
 * 파일설명		: 소모품대장 저장
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-10-01
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs3040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveSupplRegi extends SnisActivity {
	
	public SaveSupplRegi(){}

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
    	
    	String sReqDt      = (String)ctx.get("REQ_DT");	   //신청일자
        String sAppliId    = (String)ctx.get("REQ_ID");  //신청자사번
        String sSeq        = (String)ctx.get("SEQ");	   //순번
        String sSupplCd    = (String)ctx.get("SUPPL_CD");  //소모품코드
        String sSignDt     = (String)ctx.get("SIGN_DT");   //서명일자
        String sRealReciId = (String)ctx.get("USER_ID");   //서명자ID
    	
        int nSignDtCheck = selectSignDt(sReqDt, sAppliId, sSeq, sSupplCd);
        
        if( nSignDtCheck == 1 ) {
        	//서명일자가 없을 경우
        	nSaveCount = saveSupplRegi(sReqDt, sAppliId, sSeq, sSupplCd, sSignDt, sRealReciId);           
        } else {
        	//서명일자가 이미 존재할 경우
        	Util.setSvcMsgCode(ctx, "SNIS_RBM_E011");     
        }
        
        Util.setSaveCount  (ctx, nSaveCount);
    }

    /**
     * <p> 소모품대장 수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveSupplRegi(String sReqDt,String sAppliId,String sSeq,String sSupplCd,String sSignDt,String sRealReciId) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, sRealReciId);		//실수령인사번
        param.setValueParamter(i++, sSignDt);			//서명일자
        param.setValueParamter(i++, SESSION_USER_ID);	//사용자ID(수정자)
        
        i = 0;
        param.setWhereClauseParameter(i++, sReqDt);			//신청일자
        param.setWhereClauseParameter(i++, sAppliId);			//신청자사번
        param.setWhereClauseParameter(i++, sSeq);				//순번
        param.setWhereClauseParameter(i++, sSupplCd);			//소모품코드
        		
        int dmlcount = this.getDao("rbmdao").update("rbs3040_u01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 소모품대장 서명일자 조회 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int selectSignDt(String sReqDt, String sAppliId, String sSeq, String sSupplCd) 
    {	
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, sReqDt);    //신청일자
        param.setWhereClauseParameter(i++, sAppliId);  //신청자ID
        param.setWhereClauseParameter(i++, sSeq);      //순번
        param.setWhereClauseParameter(i++, sSupplCd);  //소모품코드
        
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbs3040_s02", param);  
        PosRow pr[] = rtnRecord.getAllRow();        
        
        String rtnQty = String.valueOf(pr[0].getAttribute("CNT"));
        
        if( rtnQty == null )	rtnQty = "-1";
        
        return Integer.valueOf(rtnQty).intValue();
    }
}