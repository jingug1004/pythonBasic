/*================================================================================
 * 시스템			: 공정관리
 * 소스파일 이름	: snis.boa.fairness.e05100020.activity.SaveRaceAnal.java
 * 파일설명		: 경주분석결과등록
 * 작성자			: 정민화
 * 버전			: 1.0.0
 * 생성일자		: 2011-12-3
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.fairness.e05100050.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 착순점/사고점를 저장(입력/수정)및 삭제하는 클래스이다.
*/
public class SaveRaceEtc extends SnisActivity
{   	
    public SaveRaceEtc()
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

    	PosDataset ds;
        int nSize        = 0;

        ds    = (PosDataset) ctx.get("dsOutRaceAnal");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	// MERGE
            	nSaveCount = nSaveCount + saveRaceEtc(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
    }

    /**
     * <p> 저장 </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    protected int saveRaceEtc(PosRecord record)
    {
    	int dmlcount   = 0; 
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TMS")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MEMO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ETC")));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        	
        dmlcount = this.getDao("boadao").update("tbee_risk_mng_ie501", param);
        
        return dmlcount;  
    }
}
