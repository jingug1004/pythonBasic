/*================================================================================
 * 시스템			: 상금 관리 
 * 소스파일 이름	: snis.boa.MonthPayment.e06010010.activity.SaveMonthPay.java
 * 파일설명		: 월별 상금 
 * 작성자			: 김성희 
 * 버전			: 1.0.0
 * 생성일자		: 2007-11-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.financial.e07030010.activity;

import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.financial.e07010030.activity.SaveMonthPayBac;
import snis.boa.financial.e07020055.activity.SaveMonthPay;
import snis.boa.system.e01010220.activity.SaveProcess;

/**
* 월별 상금 대한 정보를 등록, 수정, 삭제 한다.
* @auther 김성희 
* @version 1.0
*/
public class SaveMonthPayEtc extends SnisActivity
{    
    public SaveMonthPayEtc()
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
        
        //세액 update
        String stndYear = (String)ctx.get("STND_YEAR");
    	Integer payMonth = new Integer((String)ctx.get("PAY_MONTH"));
        new SaveMonthPay().updateTax(stndYear, payMonth, SESSION_USER_ID);
        
        //상태 update
        updateMonthPayStat(ctx);
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
        
        ds    = (PosDataset) ctx.get("dsOutMonthPayEtc");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
        	PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + saveMonthPayEtc(record);
        }

       
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    /**
     * 상금 마감 상태 Update
     * @param ctx
     */
    protected void updateMonthPayStat(PosContext ctx) 
    {
    	String stndYear = (String)ctx.get("STND_YEAR");
    	Integer payMonth = new Integer((String)ctx.get("PAY_MONTH"));
    	
    	PosDataset ds = (PosDataset) ctx.get("dsOutMonthPayStat");
        int nSize = ds.getRecordCount();
        if(nSize>0){
        	PosRecord record = ds.getRecord(0);
        	String monthActFinishn = (String)record.getAttribute("MONTH_ACT_FINISH_YN"); 
        	new SaveMonthPayBac().saveMonthActFinish(stndYear, payMonth, monthActFinishn);
             
//        	//프로세스 현황 로그
//        	if(monthActFinishn.equals("Y")){
//        		String title = stndYear+"년 " + payMonth +"월 회계 마감 완료";
//        		String content = stndYear+"년 " + payMonth +"월 회계  완료이 완료 되었습니다";
//        		new SaveProcessProg().insertProcessProg("007", stndYear, "", "", title, content); 
//        	}

            //프로세스 진행 현황 로그(상금회계-월별-완료/취소)
            if(Util.trim(monthActFinishn).equals("Y")){
                HashMap hmProcess = new HashMap();
                hmProcess.put("STND_YEAR", stndYear);
                hmProcess.put("MBR_CD"   , "001");
                hmProcess.put("MONTH"    , payMonth);
                hmProcess.put("DUTY_CD"  , "007");
                hmProcess.put("WORK_CD"  , "075");
                hmProcess.put("PROG_STAT", "001");
                hmProcess.put("USER_ID",   SESSION_USER_ID);

                SaveProcess sp = new SaveProcess();
                sp.saveProcess(hmProcess);
            }
            else if(Util.trim(monthActFinishn).equals("N")){
                HashMap hmProcess = new HashMap();
                hmProcess.put("STND_YEAR", stndYear);
                hmProcess.put("MBR_CD"   , "001");
                hmProcess.put("MONTH"    , payMonth);
                hmProcess.put("DUTY_CD"  , "007");
                hmProcess.put("WORK_CD"  , "075");
                hmProcess.put("PROG_STAT", "002");
                hmProcess.put("USER_ID",   SESSION_USER_ID);

                SaveProcess sp = new SaveProcess();
                sp.saveProcess(hmProcess);
            }
        }
    }
        
    /**
     * <p> 월별 상금Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveMonthPayEtc(PosRecord record)
    {
    	int effectedRowCnt = mergeMonthPayEtc(record);
        return effectedRowCnt;
    }

    /**
     * <p> 월별 상금  Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int mergeMonthPayEtc(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("PAY_MONTH"));
        param.setValueParamter(i++, record.getAttribute("PAY_TPE"));
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_08")); //    -- 지급항목 - 수당 
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_09")); //		-- 지급항목 - 기타항목 
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_12")); //    -- 지급공제항목 - 선수연금(사업자분담)
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_14")); // 	-- 지급공제항목 - 상해보험료(공제)
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_15")); // 	-- 지급공제항목 - 현물제공(공제)
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_16")); //		-- 지급공제항목 - 기타항목(공제)
        param.setValueParamter(i++, record.getAttribute("AID_AMT_05")); //     -- 공제항목 - 선수연금(개인부담분)
        param.setValueParamter(i++, record.getAttribute("AID_AMT_06")); //     -- 공제항목 - 미수금 
        param.setValueParamter(i++, record.getAttribute("AID_AMT_07")); //     -- 공제항목 - 선수복 대여료 
        param.setValueParamter(i++, record.getAttribute("AID_AMT_08")); //     -- 공제항목 - 기타항목
        param.setValueParamter(i++, record.getAttribute("AID_AMT_11")); //     -- 지급공제항목 - 상해보험료(공제)
        param.setValueParamter(i++, record.getAttribute("AID_AMT_12")); //     -- 지급공제항목 - 선수연금(사업자분담)
        param.setValueParamter(i++, record.getAttribute("AID_AMT_13")); //     -- 지급공제항목 - 현물제공(공제)
        param.setValueParamter(i++, record.getAttribute("AID_AMT_14")); //     -- 지급공제항목 - 기타항목(공제)
        param.setValueParamter(i++, SESSION_USER_ID); 
        
        return this.getDao("boadao").update("tbeg_month_pay_etc_mf001", param);
    }
}
