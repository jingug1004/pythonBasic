/*================================================================================
 * 시스템			: 경주특이사항
 * 소스파일 이름	: snis.rbm.business.rsm2041.activity.SaveRaceEtc.java
 * 파일설명		: 경주특이사항 저장 / 수정
 * 작성자			: 이기석
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm2041.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveRaceEtc extends SnisActivity {
	public SaveRaceEtc(){}
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

        sDsName = "dsEtc";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	nTempCnt = updateRaceEtc(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        		        
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> 경주 특이사항 수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateRaceEtc(PosRecord record) 
    {	   
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));     // 1.경륜 경정 구분 코드      
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));   // 2.기준년도
        param.setValueParamter(i++, record.getAttribute("TMS"));    	 // 3.회차
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));     // 4.일차
        param.setValueParamter(i++, record.getAttribute("SPEC_DESC"));   // 5.특이사항 내용     
        param.setValueParamter(i++, record.getAttribute("RAIN"));   	 // 6.비여부
        param.setValueParamter(i++, record.getAttribute("REST"));   	 // 7.휴일여부
        param.setValueParamter(i++, record.getAttribute("REMARKETC"));   // 8.기타 특이사항
        param.setValueParamter(i++, record.getAttribute("SUBJECTRACE"));   // 9.대상경주여부
        param.setValueParamter(i++, SESSION_USER_ID);                    // 10.로그인 사용자 아이디	
        
        
        int dmlcount = this.getDao("rbmdao").update("rsm2041_m01", param);
        
        return dmlcount;
    }
    
    
}
