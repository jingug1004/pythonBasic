/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02070020.activity.SaveTrngExptRacer.java
 * 파일설명		: 도착점/사고점등록
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02070060.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import java.sql.CallableStatement;import java.sql.Connection;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 훈련기준코드 및 관련 양정코드를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 신재선
* @version 1.0
*/
public class SaveTrngSche extends SnisActivity
{    
    public SaveTrngSche()
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

    	PosDataset dsTrngSche;
        int nSize        = 0;
        int nTempCnt     = 0;
        
        String sJobType    = (String)ctx.get("JOB_TYPE");
    	if ("BATCH".equals(sJobType)) {
    		batchInsert(ctx);    		
    	} else {
	        dsTrngSche     = (PosDataset) ctx.get("dsTrngSche");
	        nSize = dsTrngSche.getRecordCount();
	
	        // 삭제
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = dsTrngSche.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
	            	nDeleteCount = nDeleteCount + deleteTrngSche(record);
	            } else {
	            	nSaveCount = mergeUpdateTrngSche(record);
	            }
	        }
    	}

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 훈련기준 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int mergeUpdateTrngSche(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STR_DT".trim()));
        param.setValueParamter(i++, record.getAttribute("STR_DT".trim()));
        param.setValueParamter(i++, record.getAttribute("END_DT".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_DD_NUM".trim()));
        param.setValueParamter(i++, record.getAttribute("RMK".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        
        int dmlcount = this.getDao("boadao").update("tbeb_trng_sche_mb001", param);
        
        return dmlcount;
    }

    /**
     * <p> 훈련기준 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteTrngSche(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_YEAR".trim()));
        param.setValueParamter(i++, record.getAttribute("STR_DT".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_DD_NUM".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_sche_db001", param);
        
        return dmlcount;
    }


    /**
     * <p> 훈련기준 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int batchInsert(PosContext ctx)
    {
    	CallableStatement proc = null; 
    	Connection conn = null; 
        String sTrngYear    = (String)ctx.get("TRNG_YEAR");
    	try {
    		conn = this.getDao("boadao").getDBConnection();    	
	        
	        int i = 1;
	    	proc = conn.prepareCall("{call CREATE_TRNG_SCHE(?, ?)}");
	        proc.setString(i++, sTrngYear);
	        proc.setString(i++, SESSION_USER_ID);

	        int nRslt = proc.executeUpdate();
	        
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
        return 0;
    }
    
}
