/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02030030.activity.SaveArrangeBacAlloc.java
 * 파일설명		: 선수별기본춪주횟수등록
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02030030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 선수별기본춪주횟수를 저장(입력/수정)하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SaveArrangeBacAlloc extends SnisActivity
{
	private String sStndYear = "";
	private String sQurtCd   = "";

    public SaveArrangeBacAlloc()
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
        
        sDsName = "dsOutBacAlloc";
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

        // 선수별기본춪주횟수저장
        sStndYear = Util.nullToStr((String) ctx.get("STND_YEAR".trim()));
        sQurtCd   = Util.nullToStr((String) ctx.get("QURT_CD  ".trim()));
        
        sDsName = "dsOutBacAlloc";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // 저장
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
            	if ( (nTempCnt = updateBacRaceAlloc(record)) == 0 ) {
                	nTempCnt = insertBacRaceAlloc(record);
                }

            	nSaveCount = nSaveCount + nTempCnt;
	        }
	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        return;
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 선수별기본춪주횟수 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateBacRaceAlloc(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Integer.toString(Util.nullToInt(Util.trim(record.getAttribute("RACE_ALLOC_CNT".trim())), 0)));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sQurtCd  );
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_NO      ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_racer_bac_race_alloc_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> 선수별기본춪주횟수 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertBacRaceAlloc(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sStndYear);
        param.setValueParamter(i++, sQurtCd  );
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO      ".trim())));
        param.setValueParamter(i++, Integer.toString(Util.nullToInt(Util.trim(record.getAttribute("RACE_ALLOC_CNT".trim())), 0)));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_racer_bac_race_alloc_ib001", param);
        return dmlcount;
    }
}
