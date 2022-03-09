/*
 * ================================================================================
 * 시스템 : 
 * 관리 소스파일 이름 : snis.rbm.system.rsm1070.activity.SaveSalesRefund.java 파일
 * 설명 : 환불액 관리
 * 작성자 : 김은정
 * 버전 : 1.0.0 
 * 생성일자 : 2011- 
 * 최종수정일자 : 
 * 최종수정자 : 
 * 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.business.rsm1120.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SavePrintCnt extends SnisActivity {

	public SavePrintCnt(){
		
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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        String sEventDt = (String)ctx.get("EVENT_DT");
        String sGrnCardNo = (String)ctx.get("CARD_NO");
        String sGrnCardSeq = (String)ctx.get("CARD_SEQ");
        String sMinSlipSeq = (String)ctx.get("MIN_SLIP_SEQ");
        
        nTempCnt = savePrintCnt(sEventDt, sGrnCardNo, sGrnCardSeq, sMinSlipSeq);
		        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> 삼진아웃활동결과  입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int savePrintCnt(String sEventDt, String sGrnCardNo, String sGrnCardSeq, String sMinSlipSeq) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, SESSION_USER_ID);	// 출력자ID 
        param.setValueParamter(i++, sEventDt);			// 응모일자
        param.setValueParamter(i++, sGrnCardNo);		// 그린카드번호
        param.setValueParamter(i++, sGrnCardSeq);		// 그린카드 연번
                
        int dmlcount = this.getDao("rbmdao").update("rsm1120_u01", param);
        
        return dmlcount;
    }
    

}
