/*================================================================================
 * 시스템			: 심판 관리 
 * 소스파일 이름	: snis.boa.referere.e09020001.activity.SaveAcctEduRacer.java
 * 파일설명		: 선수별 사고정보 수정 
 * 작성자			: 조성문 
 * 버전			: 1.0.0
 * 생성일자		: 2016-08-06
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.referere.e09020001.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util; 

/**
* 경주결과 정보를 수정 한다.
* @auther 정의태
* @version 1.0
*/ 
public class SaveRacerViol  extends SnisActivity
{    
	
    public SaveRacerViol ()
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
        
        //경주테이블 저장	
        ds    = (PosDataset) ctx.get("dsOutRacerViol");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + updateRaceViolAct(record);
        }
    }

    /**
     * <p> 선수별 사고정보 수정 update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		insert 레코드 개수
     * @throws  none
     */
    
     protected int updateRaceViolAct(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, Util.trim(record.getAttribute("ACDNT_RISK_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ACDNT_CAUSE_CD".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TMS".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_REG_NO".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SEQ".trim())));
        
        return this.getDao("boadao").update("tbed_race_voil_act_u01", param);
    }
}