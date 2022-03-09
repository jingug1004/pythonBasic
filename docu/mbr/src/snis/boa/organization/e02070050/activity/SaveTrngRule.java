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
package snis.boa.organization.e02070050.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 훈련기준코드 및 관련 양정코드를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 신재선
* @version 1.0
*/
public class SaveTrngRule extends SnisActivity
{    
    public SaveTrngRule()
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
        int nSize        = 0;

        ds    = (PosDataset)ctx.get("dsTrngRule");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            logger.logInfo(record);
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

    	PosDataset dsRule;
    	PosDataset dsRuleSanc;
        int nSize        = 0;
        int nTempCnt     = 0;

        dsRule     = (PosDataset) ctx.get("dsTrngRule");
        dsRuleSanc = (PosDataset) ctx.get("dsTrngRuleSanc");
        nSize = dsRule.getRecordCount();

        // 삭제
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = dsRule.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
            	nDeleteCount = nDeleteCount + deleteTrngRule(record);
            }
        }
        
        // 수정 및 등록
        for ( int i = 0; i < nSize; i++ ) 
        {
	        PosRecord record = dsRule.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	nSaveCount = mergeUpdateTrngRule(record);
            }
        }

        // 훈련기준과 양정기준 매핑정보 저장
        nSize = dsRuleSanc.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = dsRuleSanc.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
            	nDeleteCount = nDeleteCount + deleteTrngRuleSanc(record);
            }
        }
        
        for ( int i = 0; i < nSize; i++ ) 
        {
	        PosRecord record = dsRuleSanc.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	nSaveCount = mergeUpdateTrngRuleSanc(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 훈련기준 일련번호 조회 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected String getTrngExptSeq()
    {
        PosParameter param = new PosParameter();
        PosRowSet rowset = this.getDao("boadao").find("tbeb_trng_expt_racer_fb001", param);

        String sTrngExptSeq = null;
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            sTrngExptSeq = (String) row.getAttribute("TRNG_EXPT_SEQ".trim());
        }
            
        return sTrngExptSeq;
    }


    /**
     * <p> 훈련기준 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int mergeUpdateTrngRule(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_RULE_CD".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_TYPE".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_DESC".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_TERM".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_TERM_UNIT".trim()));
        param.setValueParamter(i++, record.getAttribute("SANC_RSN_CD".trim()));
        param.setValueParamter(i++, record.getAttribute("SANC_TERM".trim()));
        param.setValueParamter(i++, record.getAttribute("SANC_TERM_UNIT".trim()));
        param.setValueParamter(i++, record.getAttribute("HEALTH_TEST_YN".trim()));
        param.setValueParamter(i++, record.getAttribute("STARTTM_TEST_YN".trim()));
        param.setValueParamter(i++, record.getAttribute("ADJST_TEST_YN".trim()));
        param.setValueParamter(i++, record.getAttribute("RMK".trim()));
        param.setValueParamter(i++, record.getAttribute("INJURY_TERM_CD".trim()));
        param.setValueParamter(i++, record.getAttribute("RULE_FRDT".trim()));
        param.setValueParamter(i++, record.getAttribute("RULE_TODT".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        
        int dmlcount = this.getDao("boadao").update("tbeb_trng_rule_u01", param);
        
        return dmlcount;
    }

    /**
     * <p> 훈련기준 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteTrngRule(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_RULE_CD".trim()));
        param.setValueParamter(i++, record.getAttribute("RULE_FRDT".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_rule_d01", param);
        
        return dmlcount;
    }

    /**
     * <p> 훈련기준별 양정기준 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteTrngRuleSanc(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_RULE_CD".trim()));
        param.setValueParamter(i++, record.getAttribute("RULE_FRDT".trim()));
        param.setValueParamter(i++, record.getAttribute("SANC_BASIS_CD".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_rule_d02", param);
        
        return dmlcount;
    }    

    /**
     * <p> 훈련기준 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int mergeUpdateTrngRuleSanc(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_RULE_CD".trim()));
        param.setValueParamter(i++, record.getAttribute("RULE_FRDT".trim()));
        param.setValueParamter(i++, record.getAttribute("SANC_BASIS_CD".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_trng_rule_u02", param);
        
        return dmlcount;
    }
    
}
