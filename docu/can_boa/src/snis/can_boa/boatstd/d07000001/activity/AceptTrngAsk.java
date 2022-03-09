/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02070030.activity.UpdateTrngAskStcd.java
 * 파일설명		: 선수수시훈련등록
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.boatstd.d07000001.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 착순점/사고점를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class AceptTrngAsk extends SnisActivity
{    
    public AceptTrngAsk()
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
    	String sDsName = sDsName = "dsOutTrngAsk";       

    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        if (ctx.get("JOB_TYPE").equals("TRNG_FEE_UPDATE")) {
        	sDsName = "dsOutTrngAskRacer";
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ )
	        {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
	            {
	            	nTempCnt = updateTrngAskRacer(record);
	            	nSaveCount = nSaveCount + nTempCnt;
	            }	            
	        }
        } else if ( ctx.get(sDsName) != null ) {	// 선수수시훈련 삭제 요청자료 삭제
            ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ )
	        {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
	            {
	            	nTempCnt = updateTrngAsk(record, ctx);
	            	nSaveCount = nSaveCount + nTempCnt;
	            }
	            
	            String TrngAskStatCd = (String)record.getAttribute("TRNG_ASK_STAT_CD ".trim());
	            if (ctx.get("JOB_TYPE").equals("ACEPT")) {
	            	if (TrngAskStatCd.equals("001")) {
	            		// 훈련일지 기초자료 삭제
	            		DeleteRacerTrngMst(record, ctx);
	            	} else if (TrngAskStatCd.equals("002")) {
	            		// 훈련일지 기초자료 생성
	            		InsertRacerTrngMst(record, ctx);
	            	}
	            	
	            	updateTrngAskRacerAcept(record, ctx);		// 교육비 관련 초기값으로 설정
	            }
	        
				// 훈련완료시 요청상태도 "훈련완료"로 설정(경정관리에서는 훈련완료가 "003")
		        if (ctx.get("JOB_TYPE").equals("003004") || ctx.get("JOB_TYPE").equals("004003")) {
					String sStatCd = "";
					String sTrnsAskSeq = "";
					if (ctx.get("JOB_TYPE") == "003004") {
						sStatCd = "006";
					} else if (ctx.get("JOB_TYPE") == "004003") {
						sStatCd = "004";
					}
					sTrnsAskSeq = record.getAttribute("TRNG_ASK_SEQ").toString();
					nSaveCount += updateTrngExptRacer(sStatCd, sTrnsAskSeq);
				}
	        }
        } else {
        	logger.logInfo("Dataset not found...["+sDsName+"]");
        }
        Util.setSaveCount  (ctx, nSaveCount     );
    }

    /**
     * <p> 수시훈련 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateTrngAsk(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        if (ctx.get("JOB_TYPE").equals("ACEPT")) {    		
        	param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STAT_CD ".trim()));
        } else {
        	param.setValueParamter(i++, ctx.get("TRNG_ASK_STAT_CD_CNG"));
        }
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ    ".trim()));

        int dmlcount = this.getDao("candao").update("tbeb_trng_ask_ub002", param);
        
        return dmlcount;
    }
    

	/**
     * <p> 훈련상태  수정</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		delete 레코드 개수
     * @throws  none
     */
	protected int updateTrngExptRacer(String sStatCd, String sTrngAskSeq) 
	{
		PosParameter param = new PosParameter();       					
		int i = 0;
		param.setWhereClauseParameter(i++, sStatCd);
		param.setWhereClauseParameter(i++, sTrngAskSeq);

		int dmlcount = this.getDao("boadao").update("tbeb_trng_expt_racer_uo001", param);
		return dmlcount;
	}

    /**
     * <p> 수시훈련 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateTrngAskRacer(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("TRNG_FEE_PAY_YN ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_FEE_PAY_MTHD ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_FEE_AMT".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_RMK".trim()));
        param.setValueParamter(i++, record.getAttribute("COMMUTE_YN".trim()));
        param.setValueParamter(i++, record.getAttribute("MENTO_YN".trim()));        
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO    ".trim()));
        param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ".trim()));

        int dmlcount = this.getDao("candao").update("tbeb_trng_ask_ub003", param);
        
        return dmlcount;
    }
    

    /**
     * <p> 훈련일지 자료 생성 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int InsertRacerTrngMst(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_SEQ"));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STR_DT"));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STR_DT"));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STR_DT"));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STR_DT"));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_END_DT"));
        
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_SEQ"));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STR_DT"));        
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STR_DT"));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_END_DT"));

        int dmlcount = this.getDao("candao").update("tbeb_trng_ask_ub004", param);
        
        return dmlcount;
    }


    /**
     * <p> 훈련일지 자료 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int DeleteRacerTrngMst(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;

		param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ"));
		
		int dmlcount = this.getDao("candao").update("tbdo_racer_trng_mst_d001", param);
        
        return dmlcount;
    }

    /**
     * <p> 수시훈련 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateTrngAskRacerAcept(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ    ".trim()));

        int dmlcount = this.getDao("candao").update("tbeb_trng_ask_ub005", param);
        
        return dmlcount;
    }
    
}
