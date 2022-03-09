/*================================================================================
 * 시스템			: 선수단 관리
 * 소스파일 이름	: snis.rbm.business.rbb5210.activity.SavePayAdj.java
 * 파일설명		: 급여조정인상률 관리
 * 작성자			: 신재선
 * 버전			: 1.0.0
 * 생성일자		: 2012-11-10
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs5210.activity;

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

public class SavePayAdj extends SnisActivity {
	
	public SavePayAdj(){}

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

        // 포상금 지급기준  저장
        sDsName = "dsPayAdj";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	
	            // 기존 저장내역 전체 삭제
	            nDeleteCount = nDeleteCount + deletePayAdj(record);

	            nSaveCount += savePayAdj(record);
			    
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> 급여조정인상률 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int savePayAdj(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("RACER_ID"));			//선수번호
        param.setValueParamter(i++, record.getAttribute("CNTR_YEAR"));			//계약년도
        param.setValueParamter(i++, record.getAttribute("CNTR_AMT"));			//연봉
        param.setValueParamter(i++, record.getAttribute("PAY_ADJ_CD"));			//조저상태코드
        param.setValueParamter(i++, record.getAttribute("PAY_ADJ_SCR"));  		//조정점수
        param.setValueParamter(i++, record.getAttribute("PAY_ADJ_RATE"));  		//조정인상률   
        param.setValueParamter(i++, record.getAttribute("PAY_ADJ_AMT"));  		//조정인상금액 
        param.setValueParamter(i++, SESSION_USER_ID);					   		//사용자ID(수정자)
                		
        int dmlcount = this.getDao("rbmdao").update("rbs5210_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 급여조정인상률 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deletePayAdj(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("RACER_ID"));			//선수번호
        param.setValueParamter(i++, record.getAttribute("CNTR_YEAR"));			//계약년도
        
        int dmlcount = this.getDao("rbmdao").update("rbs5210_d01", param);

        return dmlcount;
    }


}