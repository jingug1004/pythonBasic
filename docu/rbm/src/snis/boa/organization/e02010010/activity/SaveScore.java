/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02010010.activity.SaveScore.java
 * 파일설명		: 착순점/사고점등록
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02010010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 착순점/사고점를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SaveScore extends SnisActivity
{    
	protected String sStndYear      = "";
	
    public SaveScore()
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

        ds    = (PosDataset)ctx.get("dsOutRankScr");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            logger.logInfo(record);
        }
        
        ds    = (PosDataset)ctx.get("dsOutGppScr");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            logger.logInfo(record);
        }
        
        ds    = (PosDataset)ctx.get("dsOutAcdntScr");
        nSize = ds.getRecordCount();
        for (int i = 0; i < nSize; i++ ) 
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

    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sStndYear = (String) ctx.get("STND_YEAR");

        // 착순점 저장
        ds    = (PosDataset) ctx.get("dsOutRankScr");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
                // delete
            	nDeleteCount = nDeleteCount + deleteRankScr(record);
            }
        }

        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
                // update
            	nSaveCount = nSaveCount + saveRankScr(record);
            }
        }

        // GPP 저장
        ds    = (PosDataset) ctx.get("dsOutGppScr");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
                // delete
            	nDeleteCount = nDeleteCount + deleteGppScr(record);
            }
        }

        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
                // update
            	nSaveCount = nSaveCount + saveGppScr(record);
            }
        }

        // 사고점 저장
        ds    = (PosDataset)ctx.get("dsOutAcdntScr");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
                // delete
            	nDeleteCount = nDeleteCount + deleteAcdntScr(record);
            }
        }
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
                if ( (nTempCnt = updateAcdntScr(record)) == 0 ) {
                	nTempCnt = insertAcdntScr(record);
                }

            	nSaveCount = nSaveCount + nTempCnt;
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 착순점 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveRankScr(PosRecord record)
    {
    	/*
        IWORK_SFR-008 경정 선수편성 메뉴 개선 2013-12-07 8착까지 추가 수정.
        */
    	int maxRank = 8;
    	for ( int i = 0; i <= maxRank; i++ ) {
            if ( updateRankScr(record, i) == 0 ) {
            	insertRankScr(record, i);
            }
    	}
        
        return 1;
    }

    /**
     * <p> 착순점 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateRankScr(PosRecord record, int nRank)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RANK_" + nRank));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, sStndYear                           );
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_DGRE_CD".trim())));
        param.setWhereClauseParameter(i++, Integer.toString(nRank));

        int dmlcount = this.getDao("boadao").update("tbeb_rank_scr_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> 착순점 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertRankScr(PosRecord record, int nRank) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sStndYear                           );
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_DGRE_CD".trim())));
        param.setValueParamter(i++, Integer.toString(nRank));
        param.setValueParamter(i++, record.getAttribute("RANK_" + nRank));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_rank_scr_ib001", param);
        return dmlcount;
    }

    /**
     * <p> 착순점 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int deleteRankScr(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sStndYear                           );
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_DGRE_CD".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_rank_scr_db001", param);
        return dmlcount;
    }

    /**
     * <p> GPP Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveGppScr(PosRecord record)
    {
    	int maxRank = 8;
    	for ( int i = 0; i <= maxRank; i++ ) {
            if ( updateGppScr(record, i) == 0 ) {
            	insertGppScr(record, i);
            }
    	}
        
        return 1;
    }

    /**
     * <p> Gpp Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateGppScr(PosRecord record, int nRank)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RANK_" + nRank));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, sStndYear                           );
        param.setWhereClauseParameter(i++, record.getAttribute("DAY_ORD"));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_DGRE_CD".trim())));
        param.setWhereClauseParameter(i++, Integer.toString(nRank));

        int dmlcount = this.getDao("boadao").update("tbeb_gpp_scr_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> GPP 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertGppScr(PosRecord record, int nRank) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sStndYear                           );
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_DGRE_CD".trim())));
        param.setValueParamter(i++, Integer.toString(nRank));
        param.setValueParamter(i++, record.getAttribute("RANK_" + nRank));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_gpp_scr_ib001", param);
        return dmlcount;
    }

    /**
     * <p> GPP 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int deleteGppScr(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sStndYear                           );
        param.setWhereClauseParameter(i++, record.getAttribute("DAY_ORD"));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_DGRE_CD".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_gpp_scr_db001", param);
        return dmlcount;
    }
    
    
    /**
     * <p> 사고점 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateAcdntScr(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("VOIL_DESC".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ACDNT_SCR".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);

        i = 0;
        param.setWhereClauseParameter(i++, sStndYear                    );
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("VOIL_CD".trim())));
        
        int dmlcount = this.getDao("boadao").update("tbeb_acdnt_scr_ub001", param);
        return dmlcount;
    }

    /**
     * <p> 사고점 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertAcdntScr(PosRecord record) 
    {
        // insert
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sStndYear                    );
        param.setValueParamter(i++, Util.trim(record.getAttribute("VOIL_CD  ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("VOIL_DESC".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ACDNT_SCR".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("boadao").insert("tbeb_acdnt_scr_ib001", param);
        return dmlcount;
    }

    /**
     * <p> 사고점 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteAcdntScr(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sStndYear                    );
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("VOIL_CD".trim())));
        
        int dmlcount = this.getDao("boadao").delete("tbeb_acdnt_scr_db001", param);
        return dmlcount;
    }
}
