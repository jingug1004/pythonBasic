/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02030010.activity.SaveStandardRaceCount.java
 * 파일설명		: 기준출주횟수등록
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02030010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 기준출주횟수등록를 저장(입력/수정)하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SaveStandardRaceCount extends SnisActivity
{    
    public SaveStandardRaceCount()
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
        
        sDsName = "dsOutStndRaceCnt";
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

        // 등급별기준출주횟수 저장
        sDsName = "dsOutStndRaceCnt";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // 저장
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
            	if ( (nTempCnt = updateStndRaceCnt(record)) == 0 ) {
                	nTempCnt = insertStndRaceCnt(record);
                }

            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 등급별기준출주횟수 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateStndRaceCnt(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_CNT    ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_RACE_CNT".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STND_YEAR    ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("QURT_CD      ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_GRD_CD ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_stnd_race_cnt_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> 등급별기준출주횟수 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertStndRaceCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR    ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("QURT_CD      ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_GRD_CD ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_CNT    ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_RACE_CNT".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_RT      ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_stnd_race_cnt_ib001", param);
        return dmlcount;
    }
}
