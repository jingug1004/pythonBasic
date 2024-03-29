/*================================================================================
 * 시스템		: 선수관리
 * 소스파일 이름: snis.boa.racer.e03050020.activity.SaveExerciseOrganization.java
 * 파일설명		: 지정연습 훈련상태 정보 등
 * 작성자		: 김경화
 * 버전			: 1.0.0
 * 생성일자		: 2008-04-22
 * 최종수정일자	: 
 * 최종수정자	: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.racer.e03050020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 주선제외선수정보를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 김경화
* @version 1.0
*/
public class SaveTrngStat extends SnisActivity
{    
    public SaveTrngStat()
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
        
        ds    = (PosDataset) ctx.get("dsOutTrngStat");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
        	nSaveCount = nSaveCount + saveAppoExercOrgan(record);
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );   
    }

    /**
     * <p> 지정연습 훈련상태  정보  Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveAppoExercOrgan(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt =  insertExercise(record);
        return effectedRowCnt;
    }

    /**
     * <p>지정연습 훈련상태 정보  입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertExercise(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        String sRacerNo	= "";
        if (record.getAttribute("RACER_NO") == null || record.getAttribute("RACER_NO").equals("")){
        	sRacerNo	= " ";
        }else{
        	sRacerNo	= (String)record.getAttribute("RACER_NO");
        }
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, sRacerNo);
        param.setValueParamter(i++, record.getAttribute("MOT_LINE_ACCE_CD"));
        param.setValueParamter(i++, record.getAttribute("MOT_TURN_CD"));
        param.setValueParamter(i++, record.getAttribute("START_SENSE_CD"));
        param.setValueParamter(i++, record.getAttribute("PROP_REPR_CD"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
		param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, sRacerNo);
        param.setValueParamter(i++, record.getAttribute("MOT_LINE_ACCE_CD"));
        param.setValueParamter(i++, record.getAttribute("MOT_TURN_CD"));
        param.setValueParamter(i++, record.getAttribute("START_SENSE_CD"));
        param.setValueParamter(i++, record.getAttribute("PROP_REPR_CD"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbec_appo_exerc_trng_stat_uc001", param); 
    }
}