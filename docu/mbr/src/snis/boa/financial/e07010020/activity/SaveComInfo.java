/*================================================================================
 * 시스템			: 상금 관리 
 * 소스파일 이름	: snis.boa.ComInfoment.e06010010.activity.SaveComInfo.java
 * 파일설명		: 징수의무자 
 * 작성자			: 김성희 
 * 버전			: 1.0.0
 * 생성일자		: 2007-11-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.financial.e07010020.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 징수의무자 대한 정보를 등록, 수정, 삭제 한다.
* @auther 김성희 
* @version 1.0
*/
public class SaveComInfo extends SnisActivity
{    
    public SaveComInfo()
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
        
        ds    = (PosDataset) ctx.get("dsOutComInfo");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + saveComInfo(record);
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 징수의무자Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveComInfo(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateComInfo(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertComInfo(record);
    	}
    	if(record.getAttribute("TAX_RATE_CHANGE_YN").equals("Y")){
    		saveTaxRateHis(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> 징수의무자  Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateComInfo(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("COM_REG_NO"));  
        param.setValueParamter(i++, record.getAttribute("COM_NM"));  
        param.setValueParamter(i++, record.getAttribute("COM_OWNER_NM")); 
        param.setValueParamter(i++, record.getAttribute("CORP_REG_NO"));  
        param.setValueParamter(i++, record.getAttribute("COM_LOC_ADDR"));  
        param.setValueParamter(i++, record.getAttribute("COM_ADDR"));  
        param.setValueParamter(i++, record.getAttribute("JOB_TPE"));  
        param.setValueParamter(i++, record.getAttribute("JOB_KINDS"));  
        param.setValueParamter(i++, record.getAttribute("INCE_TAX_RATE"));  
        param.setValueParamter(i++, record.getAttribute("INH_TAX_RATE"));  
        param.setValueParamter(i++, record.getAttribute("APP_STR_YEAR_MONTH"));  
        param.setValueParamter(i++, record.getAttribute("APP_END_YEAR_MONTH"));  
        param.setValueParamter(i++, SESSION_USER_ID);
		   
        return this.getDao("boadao").update("tbeg_com_info_uf001", param);
    }

    /**
     * <p>징수의무자 입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertComInfo(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("COM_REG_NO"));  
        param.setValueParamter(i++, record.getAttribute("COM_NM"));  
        param.setValueParamter(i++, record.getAttribute("COM_OWNER_NM")); 
        param.setValueParamter(i++, record.getAttribute("CORP_REG_NO"));  
        param.setValueParamter(i++, record.getAttribute("COM_LOC_ADDR"));  
        param.setValueParamter(i++, record.getAttribute("COM_ADDR"));  
        param.setValueParamter(i++, record.getAttribute("JOB_TPE"));  
        param.setValueParamter(i++, record.getAttribute("JOB_KINDS"));  
        param.setValueParamter(i++, record.getAttribute("INCE_TAX_RATE"));  
        param.setValueParamter(i++, record.getAttribute("INH_TAX_RATE"));  
        param.setValueParamter(i++, record.getAttribute("APP_STR_YEAR_MONTH"));  
        param.setValueParamter(i++, record.getAttribute("STR_YEAR_MONTH"));
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbeg_com_info_if001", param);
    }
    
    /**
     * 세액 변경이력 저장 
     * @param record
     * @return
     */
    protected int saveTaxRateHis(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BEFORE_INCE_TAX_RATE"));  
        param.setValueParamter(i++, record.getAttribute("BEFORE_INH_TAX_RATE"));  
        param.setValueParamter(i++, record.getAttribute("BEFORE_APP_STR_YEAR_MONTH"));  
        param.setValueParamter(i++, record.getAttribute("APP_STR_YEAR_MONTH"));  
        param.setValueParamter(i++, record.getAttribute("BEFORE_APP_STR_YEAR_MONTH")); 
        param.setValueParamter(i++, record.getAttribute("APP_STR_YEAR_MONTH"));  
        param.setValueParamter(i++, record.getAttribute("BEFORE_APP_STR_YEAR_MONTH")); 
        param.setValueParamter(i++, SESSION_USER_ID);
		   
        return this.getDao("boadao").update("tbeg_tax_rate_his_if001", param);
    }


   
   
}
