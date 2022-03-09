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

public class SaveSuprtCont extends SnisActivity {
	
	public SaveSuprtCont(){}

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

        // 지원신청 저장
        sDsName = "dsSuprtList";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	
	           
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
		        	
	            	// 자료 저장
			        nSaveCount += saveSuptrCont(record);			        
		        }		        
	        }	        
        }        
        
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
        
        return ;
    }

    /**
     * <p> 지원신청내역 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveSuptrCont(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        
        param.setValueParamter(i++, "021");									//상태(021:결과입력)
        param.setValueParamter(i++, record.getAttribute("CONDUCT"));	   	//근태사항
        param.setValueParamter(i++, record.getAttribute("SUPRT_CONT"));	   	//지원내용
        param.setValueParamter(i++, record.getAttribute("BRNC_OPINN"));	   	//부서의견
        param.setValueParamter(i++, SESSION_USER_ID);					   	//보고서 작성자
        param.setValueParamter(i++, record.getAttribute("RSLT_APRV_ID"));	//보고서 최종결재자 사번
        param.setValueParamter(i++, record.getAttribute("RSLT_REPTR_DT"));	//보고서 작성일시
        param.setValueParamter(i++, SESSION_USER_ID);					   	//사용자ID(수정자)
        param.setValueParamter(i++, record.getAttribute("SEQ_NO"));			//지원신청 연번
                		
        int dmlcount = this.getDao("rbmdao").update("rbs6020_u03", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 지원신청내역 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteSuptr_Info(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("SEQ_NO"));	    // 지원신청내역 연번
        
        int dmlcount = this.getDao("rbmdao").update("rbs6020_d01", param);

        return dmlcount;
    }

}