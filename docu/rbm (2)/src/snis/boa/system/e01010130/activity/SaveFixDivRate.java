/*================================================================================
 * 시스템			: 시스템 관리 
 * 소스파일 이름	: snis.boa.system.e01010130.activity.SaveFixDivRate.java
 * 파일설명		: 승식별 확정배당률 등록
 * 작성자			: 한영택 
 * 버전			: 1.0.0
 * 생성일자		: 2008-04-24
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.system.e01010130.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 승식별 확정배당률 등록 정보 대한 등록, 수정 한다.
* @auther 한영택 
* @version 1.0
*/
public class SaveFixDivRate extends SnisActivity
{    
    public SaveFixDivRate()
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
        
        ds    = (PosDataset) ctx.get("dsOutFixDivRate");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            switch (record.getType())
            {
	            case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
	            	nDeleteCount = nDeleteCount + deleteFixDivRate(record);
	            	break;
            }
        }
        
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            switch (record.getType())
            {
	            case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
	            	nSaveCount = nSaveCount + insertFixDivRate(record);
	            	break;	
	            case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
	            	nSaveCount = nSaveCount + updateFixDivRate(record);
	            	break;	
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 승식별 확정배당률 등록 정보Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveFixDivRate(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateFixDivRate(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertFixDivRate(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> 승식별 확정배당률 등록 정보  Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateFixDivRate(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RUNNER_1ST"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_2ND"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_3RD"));
        param.setValueParamter(i++, record.getAttribute("PAYOFF"));
        param.setValueParamter(i++, record.getAttribute("STATUS"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("POOL_CD"));
        param.setValueParamter(i++, record.getAttribute("RS_SEQ"));
        
        return this.getDao("boadao").update("tbes_sdl_rs_ue001", param);
    }

    /**
     * <p>승식별 확정배당률 정보 입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertFixDivRate(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MEET_CD")); 
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("POOL_CD"));
        param.setValueParamter(i++, record.getAttribute("RS_SEQ"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_1ST"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_2ND"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_3RD"));
        param.setValueParamter(i++, record.getAttribute("PAYOFF"));
        param.setValueParamter(i++, record.getAttribute("STATUS"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbes_sdl_rs_ie001", param);
    }

    /**
     * <p>승식별 확정배당률 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteFixDivRate(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("POOL_CD"));
        param.setValueParamter(i++, record.getAttribute("RS_SEQ"));
        
        return  this.getDao("boadao").update("tbes_sdl_rs_de001", param);
    }
}
