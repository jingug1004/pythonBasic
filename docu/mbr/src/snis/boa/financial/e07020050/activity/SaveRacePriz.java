/*================================================================================
 * 시스템			: 상금 관리 
 * 소스파일 이름	: snis.boa.RacePrizment.e06010010.activity.SaveRacePriz.java
 * 파일설명		: 수당 지급기준 
 * 작성자			: 김성희 
 * 버전			: 1.0.0
 * 생성일자		: 2007-11-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.financial.e07020050.activity;

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
* 수당 지급기준 대한 정보를 등록, 수정, 삭제 한다.
* @auther 김성희 
* @version 1.0
*/
public class SaveRacePriz extends SnisActivity
{    
	public SaveRacePriz()
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
        updateTmsRacePrizStat(ctx);
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
        
        ds    = (PosDataset) ctx.get("dsOutRacePriz");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            nSaveCount = nSaveCount + saveRacePriz(record);
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    /**
     * 상금 마감 상태 Update
     * @param ctx
     */
    protected void updateTmsRacePrizStat(PosContext ctx) 
    {
    	String stndYear = (String)ctx.get("STND_YEAR");
    	String mbrCd = (String)ctx.get("MBR_CD");
    	Integer tms = new Integer((String)ctx.get("TMS"));
    	
    	PosDataset ds = (PosDataset) ctx.get("dsOutTmsRacePrizStat");
        int nSize = ds.getRecordCount();
        if(nSize>0){
        	PosRecord record = ds.getRecord(0);
        	String tmsPrizFinishn = (String)record.getAttribute("TMS_PRIZ_FINISH_YN"); 
        	updateTmsRacePrizStat(stndYear, mbrCd, tms, tmsPrizFinishn);
             
        	// 프로세스 현황 로그
//        	if(tmsPrizFinishn.equals("Y")){
//        		String title = stndYear+"년 " + tms +"회차  성적상금 마감 완료";
//        		String content = stndYear+"년 " + tms +"회차  성적상금 마감이 완료 되었습니다";
//        		new SaveProcessProg().insertProcessProg("007", stndYear, tms.toString(), "", title, content); 
//        	}

            //프로세스 진행 현황 로그(상금계산-회차별-완료/취소)
            if(Util.trim(tmsPrizFinishn).equals("Y")){
                HashMap hmProcess = new HashMap();
                hmProcess.put("STND_YEAR", stndYear);
                hmProcess.put("MBR_CD"   , mbrCd);
                hmProcess.put("TMS"      , tms);
                hmProcess.put("DUTY_CD"  , "007");
                hmProcess.put("WORK_CD"  , "070");
                hmProcess.put("PROG_STAT", "001");
                hmProcess.put("USER_ID",   SESSION_USER_ID);

                SaveProcess sp = new SaveProcess();
                sp.saveProcess(hmProcess);
            }
            else if(Util.trim(tmsPrizFinishn).equals("N")){
                HashMap hmProcess = new HashMap();
                hmProcess.put("STND_YEAR", stndYear);
                hmProcess.put("MBR_CD"   , mbrCd);
                hmProcess.put("TMS"      , tms);
                hmProcess.put("DUTY_CD"  , "007");
                hmProcess.put("WORK_CD"  , "070");
                hmProcess.put("PROG_STAT", "002");
                hmProcess.put("USER_ID",   SESSION_USER_ID);

                SaveProcess sp = new SaveProcess();
                sp.saveProcess(hmProcess);
            }
        }
    }
    
    /**
     * 상금 마감 상태 Update
     * @param stndYear
     * @param mbrCd
     * @param tms
     * @param tmsPrizFinishn
     */
    public void updateTmsRacePrizStat(String stndYear, String mbrCd, Integer tms, String tmsPrizFinishn) 
    {
    	PosParameter param = new PosParameter();
    	int i = 0;
    	param.setValueParamter(i++, tmsPrizFinishn); 
    	param.setValueParamter(i++, stndYear); 
    	param.setValueParamter(i++, mbrCd); 
    	param.setValueParamter(i++, tms); 
    	this.getDao("boadao").update("tbeg_race_priz_uf002", param);
    }
    
    /**
     * <p> 수당 지급기준Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveRacePriz(PosRecord record)
    {
    	int effectedRowCnt = updateRacePriz(record);
        return effectedRowCnt;
    }

    /**
     * <p> 수당 지급기준  Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateRacePriz(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("RANK_AMT")); 
        param.setValueParamter(i++, record.getAttribute("RUN_AMT")); 
        param.setValueParamter(i++, record.getAttribute("EVENT_AMT"));
        param.setValueParamter(i++, record.getAttribute("WAIT_AMT")); 
        param.setValueParamter(i++, record.getAttribute("SAFY_AMT")); 
        param.setValueParamter(i++, record.getAttribute("STR_AMT")); 
        param.setValueParamter(i++, record.getAttribute("WIN_AMT")); 
        param.setValueParamter(i++, record.getAttribute("ETC_AMT")); 
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("TMS")); 
        param.setValueParamter(i++, record.getAttribute("DAY_ORD")); 
        param.setValueParamter(i++, record.getAttribute("RACE_NO")); 
        param.setValueParamter(i++, record.getAttribute("RACE_REG_NO")); 
        return this.getDao("boadao").update("tbeg_race_priz_uf001", param);
    }
}
