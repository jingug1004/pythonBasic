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
package snis.boa.financial.e07030011.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.financial.e07010030.activity.SaveMonthPayBac;
import snis.boa.system.e01010061.activity.SaveProcessProg;

/**
* 월별 상금 대한 정보를 등록, 수정, 삭제 한다.
* @auther 김성희 
* @version 1.0
*/
public class SaveMonthPayFncEtc extends SnisActivity
{    
    public SaveMonthPayFncEtc()
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
        
        ds    = (PosDataset) ctx.get("dsOutMonthPayFncEtc");
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
             String monthEtcFinishn = (String)record.getAttribute("MONTH_ETC_FINISH_YN"); 
             new SaveMonthPayBac().saveMonthEtcFinish(stndYear, payMonth, monthEtcFinishn);
             
             //프로세스 현황 로그
             if(monthEtcFinishn.equals("Y")){
	             String title = stndYear+"년 " + payMonth +"월 기타 지급 내역 등록  마감 완료";
	             String content = stndYear+"년 " + payMonth +"월 기타 지급 내역 등록이   완료 되었습니다";
	             new SaveProcessProg().insertProcessProg("007", stndYear, "", "", title, content); 
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
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_07")); //    -- 지급항목 - 기타수당(기타항목)
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_10")); //    -- 지급공제항목 - 식대(지급)
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_12")); //    -- 지급공제항목 - 선수연금(사업자부담)
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_14")); //    -- 지급공제항목 - 상행보험료(지급)
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_15")); //    -- 지급공제항목 - 현물제공(지급)
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_16")); //    -- 지급공제항목 - 기타항목(지급)
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_19")); //    -- 지급항목 - 기타수당(훈련참가보조수당)
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_20")); //    -- 지급항목 - 기타수당(예행연습보조수당)
        param.setValueParamter(i++, record.getAttribute("AID_AMT_01")); // 	-- 공제항목 - 소득세
        param.setValueParamter(i++, record.getAttribute("AID_AMT_02")); // 	-- 공제항목 - 주민세
        param.setValueParamter(i++, record.getAttribute("AID_AMT_03")); // 	-- 공제항목 - 기타항목
        param.setValueParamter(i++, record.getAttribute("AID_AMT_10")); //    	-- 지급공제항목 - 식대(공제 )
        param.setValueParamter(i++, record.getAttribute("AID_AMT_11")); //    	-- 지급공제항목 - 상행보험료(공제)
        param.setValueParamter(i++, record.getAttribute("AID_AMT_12")); //    	-- 지급공제항목 - 선수연금(선수부담분)
        param.setValueParamter(i++, record.getAttribute("AID_AMT_13")); //    	-- 지급공제항목 - 현물제공(공제)
        param.setValueParamter(i++, record.getAttribute("AID_AMT_14")); //    	-- 지급공제항목 - 기타항목(공제)
        param.setValueParamter(i++, SESSION_USER_ID); 
			    		
        return this.getDao("boadao").update("tbeg_month_pay_fnc_etc_mf001", param);
    }
    
    
}
