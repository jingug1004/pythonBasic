/*================================================================================
 * 시스템			: 편성 관리 
 * 소스파일 이름	: snis.can.cyclestd.d02000031.activity.SaveCViolScoreCode.java
 * 파일설명		: 위반점 코드
 * 작성자			: 한영택 
 * 버전			: 1.0.0
 * 생성일자		: 2008-03-14
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d02000031.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* 위반점 코드 정보 대한 등록, 수정, 삭제 한다.
* @auther 한영택 
* @version 1.0
*/
public class SaveCViolScoreCode extends SnisActivity
{    
    public SaveCViolScoreCode()
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
        
        ds    = (PosDataset) ctx.get("dsOutCViolScoreCode");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            switch (record.getType())
            {
	            case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
	            	nDeleteCount = nDeleteCount + deleteCViolScoreCode(record);
	            	break;
	            case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
	            	nSaveCount = nSaveCount + insertCViolScoreCode(record);
	            	break;	
	            case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
	            	nSaveCount = nSaveCount + updateCViolScoreCode(record);
	            	break;	
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 위반점 코드 정보 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveCViolScoreCode(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateCViolScoreCode(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertCViolScoreCode(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> 위반점 코드 정보 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateCViolScoreCode(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SEQ"));
        param.setValueParamter(i++, record.getAttribute("VIOL_NM")); 
        param.setValueParamter(i++, record.getAttribute("VIOL_SCR"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("VIOL_CD")); 
        return this.getDao("candao").update("tbdb_viol_scr_cd_ub001", param);
    }

    /**
     * <p>위반점 코드 정보 입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertCViolScoreCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SEQ")); 
        param.setValueParamter(i++, record.getAttribute("VIOL_CD"));
        param.setValueParamter(i++, record.getAttribute("VIOL_NM")); 
        param.setValueParamter(i++, record.getAttribute("VIOL_SCR")); 
        param.setValueParamter(i++, SESSION_USER_ID);
       	return this.getDao("candao").update("tbdb_viol_scr_cd_ib001", param);
    }

    /**
     * <p>위반점 코드 정보 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteCViolScoreCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("VIOL_CD")); 
        return  this.getDao("candao").update("tbdb_viol_scr_cd_db001", param);
    }
}
