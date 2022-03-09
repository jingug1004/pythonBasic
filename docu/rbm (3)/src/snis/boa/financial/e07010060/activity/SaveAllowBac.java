/*================================================================================
 * 시스템			: 상금 관리 
 * 소스파일 이름	: snis.boa.AllowBacment.e06010010.activity.SaveAllowBac.java
 * 파일설명		: 수당 지급기준 
 * 작성자			: 김성희 
 * 버전			: 1.0.0
 * 생성일자		: 2007-11-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.financial.e07010060.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 수당 지급기준 대한 정보를 등록, 수정, 삭제 한다.
* @auther 김성희 
* @version 1.0
*/
public class SaveAllowBac extends SnisActivity
{    
    public SaveAllowBac()
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
        
        ds    = (PosDataset) ctx.get("dsOutAllowBac");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + saveAllowBac(record);
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 수당 지급기준Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveAllowBac(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateAllowBac(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertAllowBac(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> 수당 지급기준  Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateAllowBac(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("RUN_AMT")); 
        param.setValueParamter(i++, record.getAttribute("EVENT_AMT"));
        param.setValueParamter(i++, record.getAttribute("SAFY_AMT")); 
        param.setValueParamter(i++, record.getAttribute("WAIT_AMT_RATE")); 
        param.setValueParamter(i++, record.getAttribute("STR_AMT")); 
        param.setValueParamter(i++, record.getAttribute("NEW_RACER_AMT_RATE")); 
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        return this.getDao("boadao").update("tbeg_allow_bac_uf001", param);
    }
    
    

    /**
     * <p>수당 지급기준 입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertAllowBac(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));  
        param.setValueParamter(i++, record.getAttribute("RUN_AMT"));  
        param.setValueParamter(i++, record.getAttribute("EVENT_AMT"));
        param.setValueParamter(i++, record.getAttribute("SAFY_AMT"));  
        param.setValueParamter(i++, record.getAttribute("WAIT_AMT_RATE")); 
        param.setValueParamter(i++, record.getAttribute("STR_AMT"));  
        param.setValueParamter(i++, record.getAttribute("NEW_RACER_AMT_RATE")); 
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbeg_allow_bac_if001", param);
    }
}
