/*================================================================================
 * 시스템		: 선수관리
 * 소스파일 이름: snis.boa.racer.e03050010.activity.SaveExerciseOrganization.java
 * 파일설명		: 지정연습편성정보 등
 * 작성자		: 김경화
 * 버전			: 1.0.0
 * 생성일자		: 2008-01-05
 * 최종수정일자	: 
 * 최종수정자	: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.racer.e03050010.activity;

import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.system.e01010220.activity.SaveProcess;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 주선제외선수정보를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 김경화
* @version 1.0
*/
public class SaveExerciseOrganization extends SnisActivity
{    
    public SaveExerciseOrganization()
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
        
        ds    = (PosDataset) ctx.get("dsOutAppoExercOrgan");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
        	nSaveCount = nSaveCount + saveAppoExercOrgan(record);
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );

    
        // 지정연습 결과 내용 저장
        ds    = (PosDataset)ctx.get("dsOutAppoExercRslt");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	nSaveCount = nSaveCount + saveAppoExercOrganRslt(record);
            }
        }        
        
        if ( ctx.get("WORK_CD") != null && ctx.get("WORK_CD").equals("025") ) {
	        // 작업로그 작성
	        //==============================================
	        HashMap hmProcess = new HashMap();
	        hmProcess.put("STND_YEAR", ctx.get("STND_YEAR"));
	        hmProcess.put("MBR_CD"   , ctx.get("MBR_CD"   ));
	        hmProcess.put("TMS"      , ctx.get("TMS"      ));
	        hmProcess.put("DAY_ORD"  , ctx.get("DAY_ORD"  ));
	        hmProcess.put("DUTY_CD"  , "003");
	        hmProcess.put("WORK_CD"  , "025");
	        hmProcess.put("PROG_STAT", "001");
            hmProcess.put("USER_ID",   SESSION_USER_ID);
	
	        SaveProcess sp = new SaveProcess();
	        sp.saveProcess(hmProcess);
	        //==============================================
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );   
    }

    /**
     * <p> 지정연습편성 정보  Save </p>
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
     * <p> 지정연습결과 내용 정보  Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveAppoExercOrganRslt(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt =  insertExerciseRslt(record);
        return effectedRowCnt;
    }
        
    /**
     * <p>지정연습편성 정보  입력</p>
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
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("RACE_REG_NO"));
        
        param.setValueParamter(i++, sRacerNo);
        param.setValueParamter(i++, record.getAttribute("MOT_NO"));
        param.setValueParamter(i++, record.getAttribute("BOAT_NO"));
        
		param.setValueParamter(i++, record.getAttribute("START_TM_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1_0"));
		
		param.setValueParamter(i++, record.getAttribute("START_TM_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2_0"));
		
		param.setValueParamter(i++, record.getAttribute("START_TM_3_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3_0"));
		
		param.setValueParamter(i++, record.getAttribute("START_TM_1"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1"));
		
		param.setValueParamter(i++, record.getAttribute("START_TM_2"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2"));
		
		param.setValueParamter(i++, record.getAttribute("START_TM_3"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3"));
		
		param.setValueParamter(i++, record.getAttribute("RMK_0")); 
        param.setValueParamter(i++, record.getAttribute("RMK")); 
        
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_3_0"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_3_0"));
		
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_1"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_1"));
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_2"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_2"));
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_3"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_3"));
		
		param.setValueParamter(i++, record.getAttribute("START_RMK"));
		param.setValueParamter(i++, record.getAttribute("START_RMK_2R"));
		param.setValueParamter(i++, SESSION_USER_ID);
		
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS")); 
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("RACE_REG_NO"));
        
        param.setValueParamter(i++, sRacerNo);
        param.setValueParamter(i++, record.getAttribute("MOT_NO"));
        param.setValueParamter(i++, record.getAttribute("BOAT_NO"));
        
		param.setValueParamter(i++, record.getAttribute("START_TM_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1_0"));
		
		param.setValueParamter(i++, record.getAttribute("START_TM_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2_0"));
		
		param.setValueParamter(i++, record.getAttribute("START_TM_3_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3_0"));
		
		param.setValueParamter(i++, record.getAttribute("START_TM_1"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1"));
		
		param.setValueParamter(i++, record.getAttribute("START_TM_2"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2"));
		
		param.setValueParamter(i++, record.getAttribute("START_TM_3"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3"));
		
		param.setValueParamter(i++, record.getAttribute("RMK_0"));
		param.setValueParamter(i++, record.getAttribute("RMK"));
		
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_3_0"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_3_0"));
		
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_1"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_1"));
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_2"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_2"));
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_3"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_3"));
		
		param.setValueParamter(i++, record.getAttribute("START_RMK"));
		param.setValueParamter(i++, record.getAttribute("START_RMK_2R"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbec_appo_exerc_organ_ic001", param); 
    }
    
    /**
     * <p>지정연습편성 정보  입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertExerciseRslt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TMS")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CNTNT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CNTNT1")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("NOTICE")));
        param.setValueParamter(i++, SESSION_USER_ID );        	
    	param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR")));
    	param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD")));
    	param.setValueParamter(i++, Util.trim(record.getAttribute("TMS")));
    	param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD")));
    	param.setValueParamter(i++, Util.trim(record.getAttribute("CNTNT")));	
    	param.setValueParamter(i++, Util.trim(record.getAttribute("CNTNT1")));	
        param.setValueParamter(i++, Util.trim(record.getAttribute("NOTICE")));
    	param.setValueParamter(i++, SESSION_USER_ID );
    	param.setValueParamter(i++, SESSION_USER_ID );
    	
 		//지정연습 결과 내용 입력
    	return this.getDao("boadao").update("tbec_appo_exerc_organ_ic002", param);
    }
}