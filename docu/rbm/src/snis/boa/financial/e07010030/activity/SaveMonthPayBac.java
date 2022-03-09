/*================================================================================
 * 시스템			: 상금 관리 
 * 소스파일 이름	: snis.boa.MonthPayBacment.e06010010.activity.SaveMonthPayBac.java
 * 파일설명		: 월별지급기준 
 * 작성자			: 김성희 
 * 버전			: 1.0.0
 * 생성일자		: 2007-11-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.financial.e07010030.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 월별지급기준 대한 정보를 등록, 수정, 삭제 한다.
* @auther 김성희 
* @version 1.0
*/
public class SaveMonthPayBac extends SnisActivity
{    
    public SaveMonthPayBac()
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
        
        ds    = (PosDataset) ctx.get("dsOutMonthPayBac");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + saveMonthPayBac(record);
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 월별지급기준Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveMonthPayBac(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateMonthPayBac(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertMonthPayBac(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> 월별지급기준  Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateMonthPayBac(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("PMT_DT"));  
        param.setValueParamter(i++, record.getAttribute("MONTH_PRIZ_FINISH_YN"));  
        param.setValueParamter(i++, record.getAttribute("MONTH_ACT_FINISH_YN"));  
        param.setValueParamter(i++, record.getAttribute("PRIZ_RMK"));  
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("PAY_MONTH")); 
		   
        return this.getDao("boadao").update("tbeg_month_pay_bac_uf001", param);
    }
    
    /**
     * 상금 마감 처리 
     * @param stndYear	기준년도
     * @param month	상금지급월
     * @param monthPrizFinishYn	상금마감여부 
     * @return
     */
    protected int updateMonthPrizFinish(String stndYear, Integer month, String monthPrizFinishYn, String sPayDt)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, monthPrizFinishYn);  
        param.setValueParamter(i++, sPayDt);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, stndYear); 
        param.setValueParamter(i++, month);
		   
        return this.getDao("boadao").update("tbeg_month_pay_bac_uf010", param);
    }
    
    
    /**
     * 회계 마감 처리 
     * @param stndYear	기준년도
     * @param month	상금지급월
     * @param monthPrizFinishYn	상금마감여부 
     * @return
     */
    protected int updateMonthActFinish(String stndYear, Integer month, String monthActFinishYn)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, monthActFinishYn);  
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, stndYear); 
        param.setValueParamter(i++, month);
	    return this.getDao("boadao").update("tbeg_month_pay_bac_uf011", param);
    }
    
    
    /**
     * 기타 지급 내역  처리 
     * @param stndYear	기준년도
     * @param month	상금지급월
     * @param monthEtcFinishYn	기타 지급 내역 마감 여부 
     * @return
     */
    protected int updateMonthEtcFinish(String stndYear, Integer month, String monthEtcFinishYn)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, monthEtcFinishYn);  
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, stndYear); 
        param.setValueParamter(i++, month);
	    return this.getDao("boadao").update("tbeg_month_pay_bac_uf021", param);
    }

    /**
     * <p>월별지급기준 입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertMonthPayBac(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("PAY_MONTH")); 

        param.setValueParamter(i++, record.getAttribute("PMT_DT"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("PAY_MONTH")); 
        param.setValueParamter(i++, record.getAttribute("PAY_MONTH")); 
        param.setValueParamter(i++, record.getAttribute("PAY_MONTH")); 
        param.setValueParamter(i++, record.getAttribute("PMT_DT"));
        
        param.setValueParamter(i++, record.getAttribute("MONTH_PRIZ_FINISH_YN"));  
        param.setValueParamter(i++, record.getAttribute("MONTH_ACT_FINISH_YN"));  
        param.setValueParamter(i++, record.getAttribute("PRIZ_RMK"));  
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbeg_month_pay_bac_if001", param);
    }
    
    /**
     * 월 상금 마감 등록 
     * @param stndYear
     * @param month
     * @param monthPrizFinishYn
     * @return
     */
    public int saveMonthPrizFinish(String stndYear, Integer month, String monthPrizFinishYn, String sPayDt)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateMonthPrizFinish(stndYear, month, monthPrizFinishYn, sPayDt);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertMonthPrizFinish(stndYear, month, monthPrizFinishYn, sPayDt);
    	}
        return effectedRowCnt;
    }
    
    
    /**
     * 월 회계 마감 등록 
     * @param stndYear
     * @param month
     * @param monthActFinishYn
     * @return
     */
    public int saveMonthActFinish(String stndYear, Integer month, String monthActFinishYn)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateMonthActFinish(stndYear, month, monthActFinishYn);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertMonthActFinish(stndYear, month, monthActFinishYn);
    	}
        return effectedRowCnt;
    }
    
    
    
    /**
     * 월 회계 마감 등록 
     * @param stndYear
     * @param month
     * @param monthActFinishYn
     * @return
     */
    public int saveMonthEtcFinish(String stndYear, Integer month, String monthEtcFinishYn)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateMonthEtcFinish(stndYear, month, monthEtcFinishYn);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertMonthEtcFinish(stndYear, month, monthEtcFinishYn);
    	}
        return effectedRowCnt;
    }
    
    /**
     * 월 상금 마감 등록 
     * @param stndYear
     * @param month
     * @param monthPrizFinishYn
     * @return
     */
    protected int insertMonthPrizFinish(String stndYear, Integer month, String monthPrizFinishYn, String sPayDt)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, stndYear); 
        param.setValueParamter(i++, month);         
        param.setValueParamter(i++, sPayDt);  
        param.setValueParamter(i++, monthPrizFinishYn); 
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbeg_month_pay_bac_if010", param);
    }
    
    /**
     * 월 회계 마감 등록 
     * @param stndYear
     * @param month
     * @param monthActFinishYn
     * @return
     */
    protected int insertMonthActFinish(String stndYear, Integer month, String monthActFinishYn)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, stndYear); 
        param.setValueParamter(i++, month); 

        param.setValueParamter(i++, stndYear); 
        param.setValueParamter(i++, month); 
        param.setValueParamter(i++, month); 
        param.setValueParamter(i++, month); 

        param.setValueParamter(i++, monthActFinishYn);  
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbeg_month_pay_bac_if011", param);
    }
    
    
    /**
     * 기타 지급 내역  등록 마감 
     * @param stndYear
     * @param month
     * @param monthEtcFinishYn
     * @return
     */
    protected int insertMonthEtcFinish(String stndYear, Integer month, String monthEtcFinishYn)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, stndYear); 
        param.setValueParamter(i++, month); 

        param.setValueParamter(i++, stndYear); 
        param.setValueParamter(i++, month); 
        param.setValueParamter(i++, month); 
        param.setValueParamter(i++, month); 

        param.setValueParamter(i++, monthEtcFinishYn);  
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbeg_month_pay_bac_if021", param);
    }
}
