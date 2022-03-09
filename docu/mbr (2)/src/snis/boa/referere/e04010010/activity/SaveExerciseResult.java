/*================================================================================
 * 시스템			: 심판 관리 
 * 소스파일 이름	: snis.boa.referere.e04010010.activity.SaveExerciseResult.java
 * 파일설명		: 심판 관리 
 * 작성자			: 정의태 
 * 버전			: 1.0.0
 * 생성일자		: 2007-12-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.referere.e04010010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 지정연습편성 정보를 등록, 수정 한다.
* @auther 정의태
* @version 1.0
*/
public class SaveExerciseResult extends SnisActivity
{    
    public SaveExerciseResult()
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
        //지정연습편성결과 저장	
        ds    = (PosDataset) ctx.get("dsOutOrgan");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + SaveExerciseResult(record);
        }

        Util.setSaveCount  (ctx, nSaveCount     );
         
        
    }

    /**
     * <p> 지정연습편성결과 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int SaveExerciseResult(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateExerciseResult(record);
    	return effectedRowCnt;
    }
     
    /**
     * <p> 지정연습편성결과  Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateExerciseResult(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("RUN_TM_0"));
        param.setValueParamter(i++, record.getAttribute("RUN_TM_0"));
        param.setValueParamter(i++, record.getAttribute("RUN_TM_0"));
        param.setValueParamter(i++, record.getAttribute("RUN_TM_0"));
        param.setValueParamter(i++, record.getAttribute("RUN_TM_0"));
        param.setValueParamter(i++, record.getAttribute("RUN_TM_0"));
        param.setValueParamter(i++, record.getAttribute("RUN_TM"));
        param.setValueParamter(i++, record.getAttribute("RUN_TM"));
        param.setValueParamter(i++, record.getAttribute("RUN_TM"));
        param.setValueParamter(i++, record.getAttribute("RUN_TM"));
        param.setValueParamter(i++, record.getAttribute("RUN_TM"));
        param.setValueParamter(i++, record.getAttribute("RUN_TM"));
        param.setValueParamter(i++, record.getAttribute("OST_TM_0"));
        param.setValueParamter(i++, record.getAttribute("OST_TM_0"));
        param.setValueParamter(i++, record.getAttribute("OST_TM_0"));
        param.setValueParamter(i++, record.getAttribute("OST_TM_0"));
        param.setValueParamter(i++, record.getAttribute("OST_TM_0"));
        param.setValueParamter(i++, record.getAttribute("OST_TM_0"));
        param.setValueParamter(i++, record.getAttribute("OST_TM"));
        param.setValueParamter(i++, record.getAttribute("OST_TM"));
        param.setValueParamter(i++, record.getAttribute("OST_TM"));
        param.setValueParamter(i++, record.getAttribute("OST_TM"));
        param.setValueParamter(i++, record.getAttribute("OST_TM"));
        param.setValueParamter(i++, record.getAttribute("OST_TM"));
        param.setValueParamter(i++, record.getAttribute("FLYING_CNT_0"));
        param.setValueParamter(i++, record.getAttribute("FLYING_CNT"));
        param.setValueParamter(i++, record.getAttribute("LATE_CNT_0"));
        param.setValueParamter(i++, record.getAttribute("LATE_CNT"));
        param.setValueParamter(i++, record.getAttribute("RMK_0"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
			    		   
	    param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
	    param.setValueParamter(i++, record.getAttribute("MBR_CD"));
	    param.setValueParamter(i++, record.getAttribute("TMS"));
	    param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
	    param.setValueParamter(i++, record.getAttribute("RACE_NO"));
	    param.setValueParamter(i++, record.getAttribute("RACER_NO"));
	    param.setValueParamter(i++, record.getAttribute("RACE_REG_NO"));
	    
        return this.getDao("boadao").update("tbec_appo_exerc_ud001", param);
    }
  
}