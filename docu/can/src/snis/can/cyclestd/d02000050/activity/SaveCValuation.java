/*================================================================================
 * 시스템			: 편성 관리 
 * 소스파일 이름	: snis.can.cyclestd.d02000050.activity.SaveCValuation.java
 * 파일설명		: 기록평가기준
 * 작성자			: 한영택 
 * 버전			: 1.0.0
 * 생성일자		: 2008-03-17
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d02000050.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* 기록평가기준 정보 대한 등록, 수정, 삭제 한다.
* @auther 한영택 
* @version 1.0
*/
public class SaveCValuation extends SnisActivity
{    
    public SaveCValuation()
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
        
        ds    = (PosDataset) ctx.get("dsOutCValuation");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            switch (record.getType())
            {
	            case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
	            	nDeleteCount = nDeleteCount + deleteCValuation(record);
	            	break;
	            case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
	            	nSaveCount = nSaveCount + insertCValuation(record);
	            	break;	
	            case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
	            	nSaveCount = nSaveCount + updateCValuation(record);
	            	break;	
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 기록평가기준 정보 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveCValuation(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateCValuation(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertCValuation(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> 선수등급별 고유점수 정보 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateCValuation(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("GRD"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RACELEN")); 
        param.setValueParamter(i++, record.getAttribute("FROM_TIME"));
        param.setValueParamter(i++, record.getAttribute("TO_TIME"));
        return this.getDao("candao").update("tbdb_indv_rec_basis_ub001", param);
    }

    /**
     * <p>기록평가기준 정보 입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertCValuation(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACELEN")); 
        param.setValueParamter(i++, record.getAttribute("FROM_TIME"));
        param.setValueParamter(i++, record.getAttribute("TO_TIME"));
        param.setValueParamter(i++, record.getAttribute("GRD"));
        param.setValueParamter(i++, SESSION_USER_ID);
       	return this.getDao("candao").update("tbdb_indv_rec_basis_ib001", param);
    }

    /**
     * <p>기록평가기준 정보 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteCValuation(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACELEN")); 
        param.setValueParamter(i++, record.getAttribute("FROM_TIME"));
        param.setValueParamter(i++, record.getAttribute("TO_TIME"));
        return  this.getDao("candao").update("tbdb_indv_rec_basis_db001", param);
    }
}
