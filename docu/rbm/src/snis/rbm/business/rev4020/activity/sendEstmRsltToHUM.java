/*================================================================================
 * 시스템			: 선수단 관리
 * 소스파일 이름	: snis.rbm.business.rbb5110.activity.SaveEstmRslt.java
 * 파일설명		: 입상내역 관리
 * 작성자			: 신재선
 * 버전			: 1.0.0
 * 생성일자		: 2012-11-10
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rev4020.activity;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class sendEstmRsltToHUM extends SnisActivity {
	
	public sendEstmRsltToHUM(){}

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
    	String estmTermNm="";
        
    	PosDataset ds;
        int nSize        = 0;

        // 포상금 지급기준  저장
        sDsName = "dsEvEmp";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        

	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            if (i == 0) {
		            estmTermNm = getEstmTerm(record);   
	            }
	            nSaveCount += saveEstmRslt(record, estmTermNm);
		        
	        }	  
        }
        Util.setSaveCount  (ctx, nSaveCount  );
    }

    /**
     * <p> 입상내역 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveEstmRslt(PosRecord record, String estmTermNm) 
    {    	
        PosParameter param = new PosParameter();   
        int i = 0;
                
        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));		//평가년도
        param.setValueParamter(i++, estmTermNm);							//연간, 상반기, 하반기 
        param.setValueParamter(i++, record.getAttribute("EMP_NO"));			//사번
        param.setValueParamter(i++, record.getAttribute("TOT_GRD"));		//평가결과
        param.setValueParamter(i++, SESSION_USER_ID);					   	//사용자ID(수정자)
                		
        int dmlcount = this.getDao("venisrbmdao").update("rev4020_m01", param);
                
        return dmlcount;
    }
    
    protected String getEstmTerm(PosRecord record) 
    {
        String rtnKey = "";
        
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));		//평가년도
        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));		//평가차수
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rev4020_s03",param);        
        	
        PosRow pr[] = keyRecord.getAllRow();     
       
        rtnKey = String.valueOf(pr[0].getAttribute("ESTM_TERM_NM"));

        return rtnKey;
    }


}