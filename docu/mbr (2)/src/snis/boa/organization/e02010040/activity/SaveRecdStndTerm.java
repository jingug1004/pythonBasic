/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02010040.activity.SaveRecdStndTerm.java
 * 파일설명		: 성정반영분기등록
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02010040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 경주회차를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SaveRecdStndTerm extends SnisActivity
{    
    public SaveRecdStndTerm()
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
    	
        PosDataset ds;
        int        nSize   = 0;
        String     sDsName = "";
        
        sDsName = "dsOutRecdStndTerm";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
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
    	int    nSaveCount   = 0; 
    	int    nDeleteCount = 0; 
        String sDsName      = "";

    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        // 성적반영분기 저장
        sDsName = "dsOutRecdStndTerm";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // 삭제
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
            		nDeleteCount = nDeleteCount + deleteRecdStndTerm(record);
	            }
	        }
	        
            // 저장
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
  	              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
	  	        {
	            	if ( (nTempCnt = updateRecdStndTerm(record)) == 0 ) {
	                	nTempCnt = insertRecdStndTerm(record);
	                }
	  	        }
	            
            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 성적반영분기 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateRecdStndTerm(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_DT          ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("END_DT          ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_GRD_STR_DT".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_GRD_END_DT".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK             ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STND_YEAR     ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("QURT_CD       ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_recd_stnd_term_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> 성적반영분기 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertRecdStndTerm(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR       ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("QURT_CD         ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_DT          ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("END_DT          ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_GRD_STR_DT".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_GRD_END_DT".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK             ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_recd_stnd_term_ib001", param);
        return dmlcount;
    }
    
    /**
     * <p> 성적반영분기 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteRecdStndTerm(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STND_YEAR     ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("QURT_CD       ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_recd_stnd_term_db001", param);
        return dmlcount;
    }
}
