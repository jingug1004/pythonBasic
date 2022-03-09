/*================================================================================
 * 시스템			: 기동요원단 관리
 * 소스파일 이름	: snis.rbm.business.rbs6010.activity.SaveEvntMana.java
 * 파일설명		: 기동요원를 등록하고 수정하는 기능을 수행한다.
 * 작성자			: 신재선
 * 버전			: 1.0.0
 * 생성일자		: 2012-11-28
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs6010.activity;

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

public class SaveSuptrInfo extends SnisActivity {
	
	public SaveSuptrInfo(){}

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

        // 기동요원정보 저장
        sDsName = "dsSuptrList";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	// 기동요원 정보 삭제
	            	nDeleteCount = nDeleteCount + deleteSuptr_Info(record);
	            }		        

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
			        nSaveCount += saveSuptr_Info(record);
			        
		        }
		        
	        }	        
        }        
        
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> 기동요원정보 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveSuptr_Info(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("USER_ID"));	    //기동요원번호
        param.setValueParamter(i++, record.getAttribute("USER_NM"));	   		//기동요원사번
        param.setValueParamter(i++, record.getAttribute("DEL_YN"));		//기동요원명
        param.setValueParamter(i++, SESSION_USER_ID);					   	//사용자ID(수정자)
                		
        int dmlcount = this.getDao("rbmdao").update("rbs6010_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 기동요원정보 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteSuptr_Info(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("USER_ID"));	    //기동요원번호
        
        int dmlcount = this.getDao("rbmdao").update("rbs6010_d01", param);

        return dmlcount;
    }

}