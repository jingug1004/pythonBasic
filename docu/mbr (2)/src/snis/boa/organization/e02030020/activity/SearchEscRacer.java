/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02030020.activity.SearchEscRacer.java
 * 파일설명		: 등급평가주선보류조회
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02030020.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosColumnDef;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.dao.vo.PosRowSetImpl;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 등급평가주선보류조회하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SearchEscRacer extends SnisActivity
{    
	
    public SearchEscRacer()
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
    	
        searchState(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 조회시작 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void searchState(PosContext ctx) 
    {
        String sDsName      = "";
    	PosDataset ds;

        PosRowSet rowset = null; 

        // 등급평가 조회기준에 따라 각각을 조회
        sDsName = "dsOutRecdArrangeEscStnd";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);

	        int i = 0;
	        PosRecord record;
	        
	        // 출발위반
	        record = ds.getRecord(i++);
	        rowset = searchEscRacer4(record, ctx, rowset);

	        // 평균사고점
	        record = ds.getRecord(i++);
	        rowset = searchEscRacer2(record, ctx, rowset);

	        // 평균사고점 연속불량
	        record = ds.getRecord(i++);
	        rowset = searchEscRacer3(record, ctx, rowset);

	        // 성적불량
	        record = ds.getRecord(i++);
	        rowset = searchEscRacer5(record, ctx, rowset);

	        // 평균착순점
	        record = ds.getRecord(i++);
	        rowset = searchEscRacer1(record, ctx, rowset);
	        
        }
        
        // return Dataset의 column을 정의
        PosColumnDef columnDef[] = new PosColumnDef[10];

        int i = 0;
        columnDef[i++] = new PosColumnDef("RACER_NO"                  , 12, 6);
        columnDef[i++] = new PosColumnDef("RACER_GRD_CD"              , 12, 3);
        columnDef[i++] = new PosColumnDef("NM_KOR"                    , 12, 20);
        columnDef[i++] = new PosColumnDef("RACER_PERIO_NO"            , 12, 3);
        columnDef[i++] = new PosColumnDef("AVG_RANK_SCR"              , 12, 10);
        columnDef[i++] = new PosColumnDef("AVG_ACDNT_SCR"             , 12, 10);
        columnDef[i++] = new PosColumnDef("AVG_SCR"                   , 12, 10);
        columnDef[i++] = new PosColumnDef("CONT_CNT"                  , 12, 10);
        columnDef[i++] = new PosColumnDef("FL_CNT"                    , 12, 10);
        columnDef[i++] = new PosColumnDef("RSN_CD"                    , 12, 10);
        rowset.setColumnDefs(columnDef);
        
        String sResultKey = "dsOutEscRacer";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);

    }
    
    /**
     * <p> 평균착순점 제외자 조회 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected PosRowSet searchEscRacer1(PosRecord record, PosContext ctx, PosRowSet rowset) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
    	
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"   ));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"   ));
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"   ));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"   ));

        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_END_DT"));
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"));
        param.setWhereClauseParameter(i++, ctx.get("OPEN_DAY"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_END_DT"));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("QURT_CD"));
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"));
        
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STR_RACER_PERIO_NO".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("END_RACER_PERIO_NO".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STR_COND          ".trim())));
        param.setWhereClauseParameter(i++, ctx.get("A1_STND_CNT_50"   ));
        param.setWhereClauseParameter(i++, ctx.get("A2_STND_CNT_50"   ));
        param.setWhereClauseParameter(i++, ctx.get("B1_STND_CNT_50"   ));
        param.setWhereClauseParameter(i++, ctx.get("B2_STND_CNT_50"   ));
        
        rowset = addRowSet(rowset, this.getDao("boadao").find("tbec_racer_master_fb002", param));
        return rowset;
    }
    
    /**
     * <p> 평균사고점 제외자 조회 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
	protected PosRowSet searchEscRacer2(PosRecord record, PosContext ctx, PosRowSet rowset) 
	{
        PosParameter param = new PosParameter();
        int i = 0;
    	
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"   ));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"   ));
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"   ));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"   ));
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"   ));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"   ));
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"   ));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"   ));
        
        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_END_DT"));
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"));
        param.setWhereClauseParameter(i++, ctx.get("OPEN_DAY"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_END_DT"));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("QURT_CD"));
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"));
        
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STR_RACER_PERIO_NO".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("END_RACER_PERIO_NO".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STR_COND          ".trim())));
        param.setWhereClauseParameter(i++, ctx.get("A1_STND_CNT_50"   ));
        param.setWhereClauseParameter(i++, ctx.get("A2_STND_CNT_50"   ));
        param.setWhereClauseParameter(i++, ctx.get("B1_STND_CNT_50"   ));
        param.setWhereClauseParameter(i++, ctx.get("B2_STND_CNT_50"   ));
        
        rowset = addRowSet(rowset, this.getDao("boadao").find("tbec_racer_master_fb003", param));
        return rowset;
	}

    /**
     * <p> 평균사고점 연속불량 제외자 조회 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
	protected PosRowSet searchEscRacer3(PosRecord record, PosContext ctx, PosRowSet rowset) 
    {
        PosParameter paramRecdStndTerm = new PosParameter();
        PosParameter param = new PosParameter();
        int i = 0;
    	
        paramRecdStndTerm.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRecdStndTerm.setWhereClauseParameter(i++, ctx.get("QURT_CD"  ));
		
        PosRowSet rowsetRecdStndTerm = addRowSet(rowset, this.getDao("boadao").find("tbeb_recd_stnd_term_fb002", paramRecdStndTerm));
		
		PosRow row[] = rowsetRecdStndTerm.getAllRow();

        i = 0;
        int nRow = 0;
		for ( int j = 0; j < row.length; j++ ) {
	        param.setWhereClauseParameter(i++, row[j].getAttribute("STR_DT"));
	        param.setWhereClauseParameter(i++, row[j].getAttribute("END_DT"));
	        param.setWhereClauseParameter(i++, row[j].getAttribute("STR_DT"));
	        param.setWhereClauseParameter(i++, row[j].getAttribute("END_DT"));
	        param.setWhereClauseParameter(i++, row[j].getAttribute("STR_DT"));
	        param.setWhereClauseParameter(i++, row[j].getAttribute("END_DT"));
	        nRow++;
	        if ( nRow > 2 ) break;
		}
        
		for ( int k = nRow; k < 3; k++ ) {
	        param.setWhereClauseParameter(i++, "");
	        param.setWhereClauseParameter(i++, "");
	        param.setWhereClauseParameter(i++, "");
	        param.setWhereClauseParameter(i++, "");
	        param.setWhereClauseParameter(i++, "");
	        param.setWhereClauseParameter(i++, "");
		}
		
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STR_RACER_PERIO_NO".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("END_RACER_PERIO_NO".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STR_COND          ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STR_COND          ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STR_COND          ".trim())));
        
        rowset = addRowSet(rowset, this.getDao("boadao").find("tbec_racer_master_fb004", param));
        return rowset;
    }

    /**
     * <p> 출발위반 제외자 조회 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
	protected PosRowSet searchEscRacer4(PosRecord record, PosContext ctx, PosRowSet rowset) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
    	
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STR_RACER_PERIO_NO".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("END_RACER_PERIO_NO".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STR_COND          ".trim())));
        
        rowset = addRowSet(rowset, this.getDao("boadao").find("tbec_racer_master_fb005", param));
        return rowset;
    }

    /**
     * <p> 성적불량 제외자 조회 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
	protected PosRowSet searchEscRacer5(PosRecord record, PosContext ctx, PosRowSet rowset) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        StringBuffer sbSql = new StringBuffer();

    	sbSql.append("\n SELECT   /* 성적불량 제외자 searchEscRacer5 */                                                                                                          ");
    	sbSql.append("\n           RACER_NO                                                                                                 ");
    	sbSql.append("\n         , RACE_CNT                                                                                                 ");
    	sbSql.append("\n         , NM_KOR                                                                                                   ");
    	sbSql.append("\n         , RACER_GRD_CD                                                                                             ");
    	sbSql.append("\n         , RACER_PERIO_NO                                                                                           ");
    	sbSql.append("\n         , AVG_RANK_SCR                                                                                             ");
    	sbSql.append("\n         , AVG_ACDNT_SCR                                                                                            ");
    	sbSql.append("\n         , AVG_SCR                                                                                                  ");
    	sbSql.append("\n         , WIN_RATIO                                                                                                ");
    	sbSql.append("\n         , HIGH_RANK_RATIO                                                                                          ");
    	sbSql.append("\n         , HIGH_3_RANK_RATIO                                                                                        ");
    	sbSql.append("\n         , F_CNT                                                                                                    ");
    	sbSql.append("\n         , L_CNT                                                                                                    ");
    	sbSql.append("\n         , ABSE_CNT                                                                                                 ");
    	sbSql.append("\n         , RACE_ESC_1_CNT                                                                                           ");
    	sbSql.append("\n         , RACE_ESC_2_CNT                                                                                           ");
    	sbSql.append("\n         , FOUL_ELIM_CNT                                                                                            ");
    	sbSql.append("\n         , ELIM_CNT                                                                                                 ");
    	sbSql.append("\n         , FOUL_WARN_CNT                                                                                            ");
    	sbSql.append("\n         , WARN_CNT                                                                                                 ");
    	sbSql.append("\n         , ATTEN_CNT                                                                                                ");
        sbSql.append("\n         , '205' RSN_CD                                                                                             ");
    	sbSql.append("\n         , RANK                                                                                                     ");
    	sbSql.append("\n FROM    (                                                                                                          ");
    	sbSql.append("\n             SELECT                                                                                                 ");
    	sbSql.append("\n                       TOR.RACER_NO                                                                                 ");
    	sbSql.append("\n                     , TOR.RACE_CNT                                                                                 ");
    	sbSql.append("\n                     , TRM.NM_KOR                                                                                   ");
    	sbSql.append("\n                     , TRM.RACER_GRD_CD                                                                             ");
    	sbSql.append("\n                     , TRM.RACER_PERIO_NO                                                                           ");
    	sbSql.append("\n                     , ROUND(TOR.AMU_RANK_SCR  / TOR.RACE_CNT, 2)                                AVG_RANK_SCR       ");
    	sbSql.append("\n                     , ROUND(TOR.AMU_ACDNT_SCR / TOR.RACE_CNT, 2)                                AVG_ACDNT_SCR      ");
    	sbSql.append("\n                     , ROUND((TOR.AMU_RANK_SCR - TOR.AMU_ACDNT_SCR) / TOR.RACE_CNT, 2)           AVG_SCR            ");
    	sbSql.append("\n                     , ROUND((TOR.RANK_1) / TOR.RACE_CNT * 100, 1)                               WIN_RATIO          ");
    	sbSql.append("\n                     , ROUND((TOR.RANK_1 + TOR.RANK_2) / TOR.RACE_CNT * 100, 1)                  HIGH_RANK_RATIO    ");
    	sbSql.append("\n                     , ROUND((TOR.RANK_1 + TOR.RANK_2 + TOR.RANK_3) / TOR.RACE_CNT * 100, 1)     HIGH_3_RANK_RATIO  ");
    	sbSql.append("\n                     , TOR.F_CNT                                                                                    ");
    	sbSql.append("\n                     , TOR.L_CNT                                                                                    ");
    	sbSql.append("\n                     , TOR.ABSE_CNT                                                                                 ");
    	sbSql.append("\n                     , TOR.RACE_ESC_1_CNT                                                                           ");
    	sbSql.append("\n                     , TOR.RACE_ESC_2_CNT                                                                           ");
    	sbSql.append("\n                     , TOR.FOUL_ELIM_CNT                                                                            ");
    	sbSql.append("\n                     , TOR.ELIM_CNT                                                                                 ");
    	sbSql.append("\n                     , TOR.FOUL_WARN_CNT                                                                            ");
    	sbSql.append("\n                     , TOR.WARN_CNT                                                                                 ");
    	sbSql.append("\n                     , TOR.ATTEN_CNT                                                                                ");
    	sbSql.append("\n                     , RANK() OVER (ORDER BY ROUND((TOR.AMU_RANK_SCR - TOR.AMU_ACDNT_SCR)/TOR.RACE_CNT,2) ASC, ROUND(TOR.AMU_RANK_SCR/TOR.RACE_CNT,2) ASC, ROUND(TOR.AMU_ACDNT_SCR/TOR.RACE_CNT,2) DESC) RANK ");
    	sbSql.append("\n             FROM      TBEC_RACER_MASTER TRM                                                                        ");
    	sbSql.append("\n                     , (                                                                                            ");
    	sbSql.append("\n                          SELECT                                                                                    ");
    	sbSql.append("\n                                   TOR.RACER_NO                                                                     ");
    	sbSql.append("\n                                 , COUNT(*)                                                       RACE_CNT          ");
    	sbSql.append("\n                                 , NVL(SUM(DECODE(TOR.RANK, 1, 1, 0))                        , 0) RANK_1            ");
    	sbSql.append("\n                                 , NVL(SUM(DECODE(TOR.RANK, 2, 1, 0))                        , 0) RANK_2            ");
    	sbSql.append("\n                                 , NVL(SUM(DECODE(TOR.RANK, 3, 1, 0))                        , 0) RANK_3            ");
    	sbSql.append("\n                                 , NVL(SUM(DECODE(TOR.RANK, 4, 1, 0))                        , 0) RANK_4            ");
    	sbSql.append("\n                                 , NVL(SUM(DECODE(TOR.RANK, 5, 1, 0))                        , 0) RANK_5            ");
    	sbSql.append("\n                                 , NVL(SUM(DECODE(TOR.RANK, 6, 1, 0))                        , 0) RANK_6            ");
    	sbSql.append("\n                                 , NVL(SUM(TRS .RACE_SCR      )                              , 0) AMU_RANK_SCR      ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.ACDNT_SCR     )                              , 0) AMU_ACDNT_SCR     ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.F_CNT         )                              , 0) F_CNT             ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.L_CNT         )                              , 0) L_CNT             ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.ABSE_CNT      )                              , 0) ABSE_CNT          ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.RACE_ESC_1_CNT)                              , 0) RACE_ESC_1_CNT    ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.RACE_ESC_2_CNT)                              , 0) RACE_ESC_2_CNT    ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.FOUL_ELIM_CNT )                              , 0) FOUL_ELIM_CNT     ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.ELIM_CNT      )                              , 0) ELIM_CNT          ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.FOUL_WARN_CNT )                              , 0) FOUL_WARN_CNT     ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.WARN_CNT      )                              , 0) WARN_CNT          ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.ATTEN_CNT     )                              , 0) ATTEN_CNT         ");
    	sbSql.append("\n                          FROM     TBEB_ORGAN         TOR                                                           ");
    	sbSql.append("\n                                 , TBEB_RACE          TR                                                            ");
    	sbSql.append("\n                                 , TBEB_RANK_SCR      TRS                                                           ");
    	sbSql.append("\n                                 , (                                                                                ");
    	sbSql.append("\n                                      SELECT                                                                        ");
    	sbSql.append("\n                                               TRVA.STND_YEAR                                                       ");
    	sbSql.append("\n                                             , TRVA.MBR_CD                                                          ");
    	sbSql.append("\n                                             , TRVA.TMS                                                             ");
    	sbSql.append("\n                                             , TRVA.DAY_ORD                                                         ");
    	sbSql.append("\n                                             , TRVA.RACE_NO                                                         ");
    	sbSql.append("\n                                             , TRVA.RACE_REG_NO                                                     ");
    	sbSql.append("\n                                             , NVL(TRVB.ACDNT_SCR, TRVA.ACDNT_SCR) ACDNT_SCR                        ");
    	sbSql.append("\n                                             , TRVA.F_CNT                                                           ");
    	sbSql.append("\n                                             , TRVA.L_CNT                                                           ");
    	sbSql.append("\n                                             , TRVA.ABSE_CNT                                                        ");
    	sbSql.append("\n                                             , TRVA.RACE_ESC_1_CNT                                                  ");
    	sbSql.append("\n                                             , TRVA.RACE_ESC_2_CNT                                                  ");
    	sbSql.append("\n                                             , TRVA.FOUL_ELIM_CNT                                                   ");
    	sbSql.append("\n                                             , TRVA.ELIM_CNT                                                        ");
    	sbSql.append("\n                                             , TRVA.FOUL_WARN_CNT                                                   ");
    	sbSql.append("\n                                             , TRVA.WARN_CNT                                                        ");
    	sbSql.append("\n                                             , TRVA.ATTEN_CNT                                                       ");
    	sbSql.append("\n                                      FROM     (                                                                    ");
    	sbSql.append("\n                                                 SELECT                                                             ");
    	sbSql.append("\n                                                          TRVA.STND_YEAR                                            ");
    	sbSql.append("\n                                                        , TRVA.MBR_CD                                               ");
    	sbSql.append("\n                                                        , TRVA.TMS                                                  ");
    	sbSql.append("\n                                                        , TRVA.DAY_ORD                                              ");
    	sbSql.append("\n                                                        , TRVA.RACE_NO                                              ");
    	sbSql.append("\n                                                        , TRVA.RACE_REG_NO                                          ");
    	sbSql.append("\n                                                        , SUM(ACDNT_SCR) ACDNT_SCR                                  ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '110', 1, NULL)) F_CNT           ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '120', 1, NULL)) L_CNT           ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '130', 1, NULL)) ABSE_CNT        ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '995', 1, NULL)) RACE_ESC_1_CNT  ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '997', 1, NULL)) RACE_ESC_2_CNT  ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '125', 1, NULL)) FOUL_ELIM_CNT   ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '140', 1, NULL)) ELIM_CNT        ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '150', 1, NULL)) FOUL_WARN_CNT   ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '210', 1, NULL)) WARN_CNT        ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '220', 1, NULL)) ATTEN_CNT       ");
    	sbSql.append("\n                                                 FROM     TBED_RACE_VOIL_ACT TRVA                                   ");
    	sbSql.append("\n                                                        , TBEB_ACDNT_SCR     TAS                                    ");
    	sbSql.append("\n                                                        , TBEB_ORGAN         TOR                                    ");
    	sbSql.append("\n                                                 WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR                           ");
    	sbSql.append("\n                                                 AND    TRVA.VOIL_CD      = TAS.VOIL_CD                             ");
    	sbSql.append("\n                                                 AND    TRVA.STND_YEAR    = TOR.STND_YEAR                           ");
    	sbSql.append("\n                                                 AND    TRVA.MBR_CD       = TOR.MBR_CD                              ");
    	sbSql.append("\n                                                 AND    TRVA.TMS          = TOR.TMS                                 ");
    	sbSql.append("\n                                                 AND    TRVA.DAY_ORD      = TOR.DAY_ORD                             ");
    	sbSql.append("\n                                                 AND    TRVA.RACE_NO      = TOR.RACE_NO                             ");
    	sbSql.append("\n                                                 AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO                         ");
    	sbSql.append("\n                                                 AND    TOR .ABSE_CD     <> '003'                                   ");
    	sbSql.append("\n                                                 AND    TOR .IMMT_CLS_CD <> '003'                                   ");
    	sbSql.append("\n                                                 AND    TRVA.RACE_DAY      BETWEEN ?                                ");
    	sbSql.append("\n                                                                               AND ?                                ");
    	sbSql.append("\n                                                 GROUP BY                                                           ");
    	sbSql.append("\n                                                          TRVA.STND_YEAR                                            ");
    	sbSql.append("\n                                                        , TRVA.MBR_CD                                               ");
    	sbSql.append("\n                                                        , TRVA.TMS                                                  ");
    	sbSql.append("\n                                                        , TRVA.DAY_ORD                                              ");
    	sbSql.append("\n                                                        , TRVA.RACE_NO                                              ");
    	sbSql.append("\n                                                        , TRVA.RACE_REG_NO                                          ");
    	sbSql.append("\n                                              ) TRVA                                                                ");
    	sbSql.append("\n                                            , (                                                                     ");
    	sbSql.append("\n                                                 SELECT                                                             ");
    	sbSql.append("\n                                                          TRVA.STND_YEAR                                            ");
    	sbSql.append("\n                                                        , TRVA.MBR_CD                                               ");
    	sbSql.append("\n                                                        , TRVA.TMS                                                  ");
    	sbSql.append("\n                                                        , TRVA.DAY_ORD                                              ");
    	sbSql.append("\n                                                        , TRVA.RACE_NO                                              ");
    	sbSql.append("\n                                                        , TRVA.RACE_REG_NO                                          ");
    	sbSql.append("\n                                                        , TAS .ACDNT_SCR                                            ");
    	sbSql.append("\n                                                 FROM     TBED_RACE_VOIL_ACT TRVA                                   ");
    	sbSql.append("\n                                                        , TBEB_ACDNT_SCR     TAS                                    ");
    	sbSql.append("\n                                                        , TBEB_ORGAN         TOR                                    ");
    	sbSql.append("\n                                                 WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR                           ");
    	sbSql.append("\n                                                 AND    TRVA.VOIL_CD      = TAS.VOIL_CD                             ");
    	sbSql.append("\n                                                 AND    TRVA.STND_YEAR    = TOR.STND_YEAR                           ");
    	sbSql.append("\n                                                 AND    TRVA.MBR_CD       = TOR.MBR_CD                              ");
    	sbSql.append("\n                                                 AND    TRVA.TMS          = TOR.TMS                                 ");
    	sbSql.append("\n                                                 AND    TRVA.DAY_ORD      = TOR.DAY_ORD                             ");
    	sbSql.append("\n                                                 AND    TRVA.RACE_NO      = TOR.RACE_NO                             ");
    	sbSql.append("\n                                                 AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO                         ");
    	sbSql.append("\n                                                 AND    TOR .ABSE_CD     <> '003'                                   ");
    	sbSql.append("\n                                                 AND    TOR .IMMT_CLS_CD <> '003'                                   ");
    	sbSql.append("\n                                                 AND    TRVA.RACE_DAY      BETWEEN ?                                ");
    	sbSql.append("\n                                                                               AND ?                                ");
    	sbSql.append("\n                                                 AND    TRVA.VOIL_CD     IN ('110', '120')                          ");
    	sbSql.append("\n                                               ) TRVB                                                               ");
    	sbSql.append("\n                                     WHERE  TRVA.STND_YEAR   = TRVB.STND_YEAR  (+)                                  ");
    	sbSql.append("\n                                     AND    TRVA.MBR_CD      = TRVB.MBR_CD     (+)                                  ");
    	sbSql.append("\n                                     AND    TRVA.TMS         = TRVB.TMS        (+)                                  ");
    	sbSql.append("\n                                     AND    TRVA.DAY_ORD     = TRVB.DAY_ORD    (+)                                  ");
    	sbSql.append("\n                                     AND    TRVA.RACE_NO     = TRVB.RACE_NO    (+)                                  ");
    	sbSql.append("\n                                     AND    TRVA.RACE_REG_NO = TRVB.RACE_REG_NO(+)                                  ");
    	sbSql.append("\n                                   ) TRVA                                                                           ");
    	sbSql.append("\n                          WHERE  TOR .STND_YEAR    = TR  .STND_YEAR                                                 ");
    	sbSql.append("\n                          AND    TOR .MBR_CD       = TR  .MBR_CD                                                    ");
    	sbSql.append("\n                          AND    TOR .TMS          = TR  .TMS                                                       ");
    	sbSql.append("\n                          AND    TOR .DAY_ORD      = TR  .DAY_ORD                                                   ");
    	sbSql.append("\n                          AND    TOR .RACE_NO      = TR  .RACE_NO                                                   ");
    	sbSql.append("\n                          AND    TOR .STND_YEAR    = TRS .STND_YEAR                                                 ");
    	sbSql.append("\n                          AND    TR  .RACE_DGRE_CD = TRS .RACE_DGRE_CD                                              ");
    	sbSql.append("\n                          AND    NVL(TOR.RANK,0)   = TRS .RANK                                                      ");
    	sbSql.append("\n                          AND    TOR .STND_YEAR    = TRVA.STND_YEAR  (+)                                            ");
    	sbSql.append("\n                          AND    TOR .MBR_CD       = TRVA.MBR_CD     (+)                                            ");
    	sbSql.append("\n                          AND    TOR .TMS          = TRVA.TMS        (+)                                            ");
    	sbSql.append("\n                          AND    TOR .DAY_ORD      = TRVA.DAY_ORD    (+)                                            ");
    	sbSql.append("\n                          AND    TOR .RACE_NO      = TRVA.RACE_NO    (+)                                            ");
    	sbSql.append("\n                          AND    TOR .RACE_REG_NO  = TRVA.RACE_REG_NO(+)                                            ");
    	sbSql.append("\n                          AND    TOR .ABSE_CD     <> '003'                                                          ");
    	sbSql.append("\n                          AND    TOR .IMMT_CLS_CD <> '003'                                                          ");
    	sbSql.append("\n                          AND    TOR .RACE_DAY      BETWEEN ?                                                       ");
    	sbSql.append("\n                                                        AND ?                                                       ");
    	sbSql.append("\n                          GROUP BY TOR .RACER_NO                                                                    ");
    	sbSql.append("\n                      ) TOR                                                                                         ");
    	sbSql.append("\n                    , (                                                                                             ");
    	sbSql.append("\n                        SELECT                                                                                      ");
    	sbSql.append("\n                                 TRM.RACER_GRD_CD                                                                   ");
    	sbSql.append("\n                               , TRM.GRD_CNT                                                                        ");
    	sbSql.append("\n                               , TOR.RACE_CNT                                                                       ");
    	sbSql.append("\n                               , ROUND(TOR.RACE_CNT/TRM.GRD_CNT) STND_CNT                                           ");
    	sbSql.append("\n                               , ROUND(TOR.RACE_CNT/TRM.GRD_CNT/2) STND_CNT_50                                      ");
    	sbSql.append("\n                        FROM     (                                                                                  ");
    	sbSql.append("\n                                    SELECT                                                                          ");
    	sbSql.append("\n                                             TRM.RACER_GRD_CD                                                       ");
    	sbSql.append("\n                                           , COUNT(*) GRD_CNT                                                       ");
    	sbSql.append("\n                                    FROM   TBEC_RACER_MASTER TRM                                                    ");
    	sbSql.append("\n                                    WHERE  TRM.RACER_NO IN (                                                        ");
    	sbSql.append("\n                                                                SELECT                                              ");
    	sbSql.append("\n                                                                       TOR.RACER_NO                                 ");
    	sbSql.append("\n                                                                FROM   TBEB_ORGAN TOR                               ");
    	sbSql.append("\n                                                                WHERE  TOR.RACE_DAY BETWEEN ?                       ");
    	sbSql.append("\n                                                                                        AND ?                       ");
    	sbSql.append("\n                                                                GROUP BY TOR.RACER_NO                               ");
    	sbSql.append("\n                                                           )                                                        ");
    	sbSql.append("\n                                    GROUP BY TRM.RACER_GRD_CD                                                       ");
    	sbSql.append("\n                                 ) TRM                                                                              ");
    	sbSql.append("\n                               , (                                                                                  ");
    	sbSql.append("\n                                    SELECT                                                                          ");
    	sbSql.append("\n                                             TOR.RACER_GRD_CD                                                       ");
    	sbSql.append("\n                                           , COUNT(*) RACE_CNT                                                      ");
    	sbSql.append("\n                                    FROM     TBEC_RACER_MASTER TRM                                                  ");
    	sbSql.append("\n                                           , TBEB_ORGAN        TOR                                                  ");
    	sbSql.append("\n                                    WHERE  TRM.RACER_NO = TOR.RACER_NO                                              ");
    	sbSql.append("\n                                    AND    TOR.RACE_DAY BETWEEN ?                                                   ");
    	sbSql.append("\n                                                            AND ?                                                   ");
    	sbSql.append("\n                                    GROUP BY TOR.RACER_GRD_CD                                                       ");
    	sbSql.append("\n                                 ) TOR                                                                              ");
    	sbSql.append("\n                         WHERE TRM.RACER_GRD_CD = TOR.RACER_GRD_CD                                                  ");
    	sbSql.append("\n                         ORDER BY TRM.RACER_GRD_CD                                                                  ");
    	sbSql.append("\n                      ) CNT                                                                                         ");

    	sbSql.append("\n                    , (                                                                                                                       ");
    	sbSql.append("\n                         SELECT                                                                                                               ");
    	sbSql.append("\n                                  RACER_NO                                                                                                    ");
    	sbSql.append("\n                                , SUM(SANC_CNT) SANC_CNT                                                                                      ");
    	sbSql.append("\n                         FROM   (                                                                                                             ");
    	sbSql.append("\n                                     SELECT                                                                                                   ");
    	sbSql.append("\n                                              TRS.RACER_NO                                                                                    ");
    	sbSql.append("\n                                            , TRS.SANC_TERM                                                                                   ");
    	sbSql.append("\n                                            , TRS.UNIT                                                                                        ");
    	sbSql.append("\n                                            , DECODE(SUBSTR(TRG.RACER_GRD_CD, 1, 1), 'A', TRS.SANC_TERM * 3, 'B', TRS.SANC_TERM * 2) SANC_CNT ");
    	sbSql.append("\n                                     FROM     TBEE_RACER_SANC TRS                                                                             ");
    	sbSql.append("\n                                            , (                                                                                               ");
    	sbSql.append("\n                                                 SELECT                                                                                       ");
    	sbSql.append("\n                                                          TRG.RACER_NO                                                                        ");
    	sbSql.append("\n                                                        , TRG.CHG_DT                                                                          ");
    	sbSql.append("\n                                                        , TRG.RACER_GRD_CD                                                                    ");
    	sbSql.append("\n                                                        , RANK() OVER (PARTITION BY TRG.RACER_NO                                              ");
    	sbSql.append("\n                                                                           ORDER BY TRG.CHG_DT DESC ) SEQ                                     ");
    	sbSql.append("\n                                                 FROM   TBEB_RACER_GRD    TRG                                                                 ");
    	sbSql.append("\n                                                 WHERE  TRG.CHG_DT    < ?                                                                     ");
    	sbSql.append("\n                                              ) TRG                                                                                           ");
    	sbSql.append("\n                                     WHERE  TRS.RACER_NO          = TRG.RACER_NO                                                              ");
    	sbSql.append("\n                                     AND    TRG.SEQ               = 1                                                                         ");
    	sbSql.append("\n                                     AND    TRS.SANC_ACT_DT BETWEEN ?                                                                         ");
    	sbSql.append("\n                                                                AND ?                                                                         ");
    	sbSql.append("\n                                     AND    TRS.UNIT              = '001'                                                                     ");
    	sbSql.append("\n                                     UNION ALL                                                                                                ");
    	sbSql.append("\n                                     SELECT                                                                                                   ");
    	sbSql.append("\n                                              TRS.RACER_NO                                                                                    ");
    	sbSql.append("\n                                            , TRS.SANC_TERM                                                                                   ");
    	sbSql.append("\n                                            , TRS.UNIT                                                                                        ");
    	sbSql.append("\n                                            , ROUND((TRS.SANC_TERM * TSRC.STND_RACE_CNT) / ?) SANC_CNT                                        ");
    	sbSql.append("\n                                     FROM     TBEE_RACER_SANC TRS                                                                             ");
    	sbSql.append("\n                                            , (                                                                                               ");
    	sbSql.append("\n                                                 SELECT                                                                                       ");
    	sbSql.append("\n                                                          TRG.RACER_NO                                                                        ");
    	sbSql.append("\n                                                        , TRG.CHG_DT                                                                          ");
    	sbSql.append("\n                                                        , TRG.RACER_GRD_CD                                                                    ");
    	sbSql.append("\n                                                        , RANK() OVER (PARTITION BY TRG.RACER_NO                                              ");
    	sbSql.append("\n                                                                           ORDER BY TRG.CHG_DT DESC ) SEQ                                     ");
    	sbSql.append("\n                                                 FROM   TBEB_RACER_GRD    TRG                                                                 ");
    	sbSql.append("\n                                                 WHERE  TRG.CHG_DT    < ?                                                                     ");
    	sbSql.append("\n                                              ) TRG                                                                                           ");
    	sbSql.append("\n                                            , (                                                                                               ");
    	sbSql.append("\n                                                 SELECT                                                                                       ");
    	sbSql.append("\n                                                          TSRC.RACER_GRD_CD                                                                   ");
    	sbSql.append("\n                                                        , TSRC.STND_RACE_CNT                                                                  ");
    	sbSql.append("\n                                                 FROM   TBEB_STND_RACE_CNT    TSRC                                                            ");
    	sbSql.append("\n                                                 WHERE  TSRC.STND_YEAR = ?                                                                    ");
    	sbSql.append("\n                                                 AND    TSRC.QURT_CD   = ?                                                                    ");
    	sbSql.append("\n                                              ) TSRC                                                                                          ");
    	sbSql.append("\n                                     WHERE  TRS.RACER_NO          = TRG.RACER_NO                                                              ");
    	sbSql.append("\n                                     AND    TRG.SEQ               = 1                                                                         ");
    	sbSql.append("\n                                     AND    TRG.RACER_GRD_CD      = TSRC.RACER_GRD_CD                                                         ");
    	sbSql.append("\n                                     AND    TRS.SANC_ACT_DT BETWEEN ?                                                                         ");
    	sbSql.append("\n                                                                AND ?                                                                         ");
    	sbSql.append("\n                                     AND    TRS.UNIT              = '002'                                                                     ");
    	sbSql.append("\n                                )                                                                                                             ");
    	sbSql.append("\n                         GROUP BY RACER_NO                                                                                                    ");
    	sbSql.append("\n                      ) SANC                                                                                                                  ");
    	
    	
    	
    	sbSql.append("\n             WHERE  TRM.RACER_NO                                              = TOR .RACER_NO                       ");
    	sbSql.append("\n             AND    TRM.RACER_GRD_CD                                          = CNT .RACER_GRD_CD                   ");
    	sbSql.append("\n             AND    TRM.RACER_NO                                              = SANC.RACER_NO     (+)               ");
    	sbSql.append("\n             AND    TOR.RACE_CNT + NVL(SANC.SANC_CNT, 0)                     >= CNT .STND_CNT_50                    ");
    	sbSql.append("\n             AND    TRM.RACER_STAT_CD IN('001', '002')                                                              ");
        sbSql.append("\n             AND    TRM.RACER_PERIO_NO BETWEEN TO_NUMBER(?)                                                         ");
        sbSql.append("\n                                           AND TO_NUMBER(?)                                                         ");
        
        int nCount = 0;
        if (rowset != null) {
	        while( rowset.hasNext() ) {
	            PosRow row = rowset.next();
	          
	            if ( nCount == 0 ) {
	                sbSql.append("\n                 AND    TRM.RACER_NO  NOT IN (");
	                sbSql.append("'" + row.getAttribute("RACER_NO").toString() + "'");
	            } else {
	                sbSql.append(",'" + row.getAttribute("RACER_NO").toString() + "'");
	            }
	          
	            nCount++;
	        }
        }

        if ( nCount > 0 ) 
            sbSql.append("\n                                                 )");

        // 제외선수
        String sDsName = "dsOutRacerListEsc";
        if ( ctx.get(sDsName) != null ) {
        	PosDataset ds    = (PosDataset) ctx.get(sDsName);
        	if ( ds.getRecordCount() > 0 ) {
		        for ( int j = 0; i < ds.getRecordCount(); i++ ) 
		        {
		        	PosRecord recordRacerListEsc = ds.getRecord(j++);
		        	if ( j == 0 ) {
		                sbSql.append("\n                 AND    TRM.RACER_NO  NOT IN (");
		                sbSql.append("'" + recordRacerListEsc.getAttribute("RACER_NO").toString() + "'");
		        	} else {
		                sbSql.append(",'" + recordRacerListEsc.getAttribute("RACER_NO").toString() + "'");
		        	}
		        }
		        
	            sbSql.append("\n                                                 )");
        	}
        }
        
    	sbSql.append("\n        )                                                                                                           ");
    	sbSql.append("\n WHERE  RANK <= ?                                                                                                   ");    	

        param.setWhereClauseParameter(i++, ctx.get("STR_DT"   ));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"   ));
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"   ));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"   ));
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"   ));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"   ));
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"   ));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"   ));
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"   ));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"   ));
        
        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_END_DT"));
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"));
        param.setWhereClauseParameter(i++, ctx.get("OPEN_DAY"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_END_DT"));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("QURT_CD"));
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"));
        
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STR_RACER_PERIO_NO".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("END_RACER_PERIO_NO".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ESC_RACER_CNT     ".trim())));

        rowset = addRowSet(rowset, this.getDao("boadao").findByQueryStatement(sbSql.toString(), param));
        return rowset;
    }

    /**
     * <p> rowset에 addRowSet의 record를 더하여 return한다. </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
	protected PosRowSet addRowSet(PosRowSet rowset, PosRowSet addRowSet) 
    {
		ArrayList rows    = new ArrayList();
		HashMap   hmRacer = new HashMap();
		int       nRow    = 0;
		
		if ( rowset != null ) {
			PosRow row[] = rowset.getAllRow();

			for ( int i = 0; i < row.length; i++ ) {
				hmRacer.put((String) row[i].getAttribute("RACER_NO") , Integer.toString(nRow));
				rows.add(row[i]);
				nRow++; 
			}
		}
		
		if ( addRowSet != null ) {
			PosRow row[] = addRowSet.getAllRow();

			for ( int i = 0; i < row.length; i++ ) {
				if ( hmRacer.get((String) row[i].getAttribute("RACER_NO")) == null ) {
					hmRacer.put((String) row[i].getAttribute("RACER_NO") , Integer.toString(nRow));
					rows.add(row[i]);
					nRow++;
				}
			}
		}

		if ( rows.size() > 0 ) rowset = new PosRowSetImpl(rows);
		
		return rowset;
    }
}
