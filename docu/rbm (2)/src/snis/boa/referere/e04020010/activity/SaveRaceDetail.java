/*================================================================================
 * 시스템			: 심판 관리 
 * 소스파일 이름	: snis.boa.referere.e04020010.activity.SaveRaceDetail.java
 * 파일설명		: 심판 관리 
 * 작성자			: 정의태 
 * 버전			: 1.0.0
 * 생성일자		: 2007-12-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.referere.e04020010.activity;

import java.math.BigDecimal;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 경주결과 정보를 수정 한다.
* @auther 정의태
* @version 1.0
*/
public class SaveRaceDetail extends SnisActivity
{    
	
    public SaveRaceDetail()
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
    	int nDeleteCount = 0; 

    	PosDataset ds;
        int nSize        = 0;
        
        //경주테이블 저장	
        ds    = (PosDataset) ctx.get("dsOutRace");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + SaveRaceDetailResult(record);
        }
    }
    /**
     * <p> 경주테이블 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int SaveRaceDetailResult(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateRaceDetailResult(record);
    	return effectedRowCnt;
    }
    
    /**
     * <p> 경주테이블  Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    
     protected int updateRaceDetailResult(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACE_STTS_CD"));
        param.setValueParamter(i++, record.getAttribute("RACE_STTS2_CD"));
        param.setValueParamter(i++, record.getAttribute("RACE_STTS_DESC"));
        param.setValueParamter(i++, record.getAttribute("WEATH_CD"));
        param.setValueParamter(i++, record.getAttribute("TEMPT"));
        param.setValueParamter(i++, record.getAttribute("WIND_SPD"));
        param.setValueParamter(i++, record.getAttribute("WIND_DIRC_CD"));
        param.setValueParamter(i++, record.getAttribute("WATER_TEMPT"));
        param.setValueParamter(i++, record.getAttribute("WAVE"));
        param.setValueParamter(i++, record.getAttribute("WATER_LVL"));
        param.setValueParamter(i++, record.getAttribute("HUMIDITY"));
        param.setValueParamter(i++, record.getAttribute("STRT_TIME"));
        param.setValueParamter(i++, record.getAttribute("DIS_NEED_TIME"));
        param.setValueParamter(i++, record.getAttribute("MNG"));
        param.setValueParamter(i++, record.getAttribute("MNG_FILE_URL"));
        param.setValueParamter(i++, record.getAttribute("REFE_HEAD"));
        param.setValueParamter(i++, record.getAttribute("REFE_HEAD_FILE_URL"));
        param.setValueParamter(i++, record.getAttribute("INSP_CMAN"));
        param.setValueParamter(i++, record.getAttribute("INSP_CMAN_FILE_URL"));
        param.setValueParamter(i++, SESSION_USER_ID);
			    		   
	    param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
	    param.setValueParamter(i++, record.getAttribute("MBR_CD"));
	    param.setValueParamter(i++, record.getAttribute("TMS"));
	    param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
	    param.setValueParamter(i++, record.getAttribute("RACE_NO"));
	    
	    param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
	    param.setValueParamter(i++, record.getAttribute("MBR_CD"));
	    param.setValueParamter(i++, record.getAttribute("TMS"));
        return this.getDao("boadao").update("tbec_appo_race_result_ud001", param);
    }
     
}