/*================================================================================
 * 시스템			: 공정 관리
 * 소스파일 이름	: snis.boa.fairness.e05070020.activity.SaveRaceAnalysis.java
 * 파일설명		: 경주분석결과 관리
 * 작성자			: 정민화
 * 버전			: 1.0.0
 * 생성일자		: 2009-01-14
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.fairness.e05070020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SaveRaceAnalysis extends SnisActivity
{    
    public SaveRaceAnalysis()
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
/*
        PosDataset ds;
        int        nSize   = 0;
        String     sDsName = "";
        
        sDsName = "dsOutRaceAnalysis";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
*/	        
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

	    ds   		 = (PosDataset) ctx.get("dsOutRaceAnalysis");
	    nSize 		 = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	//nDeleteCount = nDeleteCount + deleteRaceAnalysis(record);
            }else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
      		        || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
            	nSaveCount = nSaveCount + mergeRaceAnalysis(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> 경주분석결과 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int mergeRaceAnalysis(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("R1_1T_1"));
        param.setValueParamter(i++, record.getAttribute("R1_1T_2"));
        param.setValueParamter(i++, record.getAttribute("R1_2T_1"));
        param.setValueParamter(i++, record.getAttribute("R1_2T_2"));
        param.setValueParamter(i++, record.getAttribute("R2_1T_1"));
        param.setValueParamter(i++, record.getAttribute("R2_1T_2"));
        param.setValueParamter(i++, record.getAttribute("R2_2T_1"));
        param.setValueParamter(i++, record.getAttribute("R2_2T_2"));
        param.setValueParamter(i++, record.getAttribute("R3_1T_1"));
        param.setValueParamter(i++, record.getAttribute("R3_1T_2"));
        param.setValueParamter(i++, record.getAttribute("R3_2T_1"));
        param.setValueParamter(i++, record.getAttribute("R3_2T_2"));
        param.setValueParamter(i++, record.getAttribute("IMG_DESC"));
        param.setValueParamter(i++, record.getAttribute("IMG_1"));
        param.setValueParamter(i++, record.getAttribute("IMG_2"));
        param.setValueParamter(i++, record.getAttribute("IMG_3"));
        param.setValueParamter(i++, record.getAttribute("IMG_4"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("R1_1T_1"));
        param.setValueParamter(i++, record.getAttribute("R1_1T_2"));
        param.setValueParamter(i++, record.getAttribute("R1_2T_1"));
        param.setValueParamter(i++, record.getAttribute("R1_2T_2"));
        param.setValueParamter(i++, record.getAttribute("R2_1T_1"));
        param.setValueParamter(i++, record.getAttribute("R2_1T_2"));
        param.setValueParamter(i++, record.getAttribute("R2_2T_1"));
        param.setValueParamter(i++, record.getAttribute("R2_2T_2"));
        param.setValueParamter(i++, record.getAttribute("R3_1T_1"));
        param.setValueParamter(i++, record.getAttribute("R3_1T_2"));
        param.setValueParamter(i++, record.getAttribute("R3_2T_1"));
        param.setValueParamter(i++, record.getAttribute("R3_2T_2"));
        param.setValueParamter(i++, record.getAttribute("IMG_DESC"));
        param.setValueParamter(i++, record.getAttribute("IMG_1"));
        param.setValueParamter(i++, record.getAttribute("IMG_2"));
        param.setValueParamter(i++, record.getAttribute("IMG_3"));
        param.setValueParamter(i++, record.getAttribute("IMG_4"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbee_race_analysis_ie001", param);
    }

}
