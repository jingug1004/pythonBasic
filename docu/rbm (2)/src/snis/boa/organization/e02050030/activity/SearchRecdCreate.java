/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02050030.activity.SearchRecdCreate.java
 * 파일설명		: 회차별성적조회
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02050030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 회차별성적조회하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SearchRecdCreate extends SnisActivity
{    
	private String sStndYear = "";
	private String sMbrCd    = "";
	private String sTms      = "";
	
    public SearchRecdCreate()
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
    	int    nSaveCount   = 0; 
    	int    nDeleteCount = 0; 
        String sDsName      = "dsOutRacerRecd";

    	PosDataset ds = null;

        sStndYear = Util.nullToStr((String) ctx.get("STND_YEAR".trim()));
        sMbrCd    = Util.nullToStr((String) ctx.get("MBR_CD   ".trim()));
        sTms      = Util.nullToStr((String) ctx.get("TMS      ".trim()));
        ds    = (PosDataset) ctx.get(sDsName);
        
        // 선수성적조회
        String sResultKey = "dsOutRacerRecd";
        ctx.put(sResultKey, searchRacerRecdAccuSum(ds));
        Util.addResultKey(ctx, sResultKey);
        
        // 선수성적조회(플라잉)
        sResultKey = "dsOutRacerRecdFly";
        ctx.put(sResultKey, searchRacerRecdAccuSumFlyOnl(ds, "002"));
        Util.addResultKey(ctx, sResultKey);
        
        // 선수성적조회(온라인)
        sResultKey = "dsOutRacerRecdOnl";
        ctx.put(sResultKey, searchRacerRecdAccuSumFlyOnl(ds, "001"));
        Util.addResultKey(ctx, sResultKey);
        
        // 모터성적조회
        sResultKey = "dsOutMotRecd";
        ctx.put(sResultKey, searchMotRecdAccuSum(ds));
        Util.addResultKey(ctx, sResultKey);
        
        // 모터성적조회(플라잉)
        sResultKey = "dsOutMotRecdFly";
        ctx.put(sResultKey, searchMotRecdAccuSumFlyOnl(ds, "002"));
        Util.addResultKey(ctx, sResultKey);

        // 모터성적조회(온라인)
        sResultKey = "dsOutMotRecdOnl";
        ctx.put(sResultKey, searchMotRecdAccuSumFlyOnl(ds, "001"));
        Util.addResultKey(ctx, sResultKey);

        // 보트성적조회
        sResultKey = "dsOutBoatRecd";
        ctx.put(sResultKey, searchBoatRecdAccuSum(ds));
        Util.addResultKey(ctx, sResultKey);

        // 보트성적조회(플라잉)
        sResultKey = "dsOutBoatRecdFly";
        ctx.put(sResultKey, searchBoatRecdAccuSumFlyOnl(ds, "002"));
        Util.addResultKey(ctx, sResultKey);        

        // 보트성적조회(온라인)
        sResultKey = "dsOutBoatRecdOnl";
        ctx.put(sResultKey, searchBoatRecdAccuSumFlyOnl(ds, "001"));
        Util.addResultKey(ctx, sResultKey);

        //searchMotRecdAccuSum(ds);
        //searchBoatRecdAccuSum(ds);

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 회차별 선수 성적조회 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected PosRowSet searchRacerRecdAccuSum(PosDataset ds)
    {
    	StringBuffer sbSql = new StringBuffer();
    	
		sbSql.append("\n SELECT  /* SearchRecdCreate : searchRacerRecdAccuSum */  ");
		sbSql.append("\n         /*+ ORDERED */   																");   //속도 향상을 위한 오라클 힌트
		sbSql.append("\n           '1' CHK 																				");
		sbSql.append("\n	       , '1' ENABLE 																		");
		sbSql.append("\n         , ?                                                                                                                                STND_YEAR                       -- 기준년도             ");
		sbSql.append("\n         , ?                                                                                                                                MBR_CD                          -- 경정장코드           ");
		sbSql.append("\n         , ?                                                                                                                                TMS                             -- 회차                 ");
		sbSql.append("\n         , TRM.RACER_NO                                                                                                                     RACER_NO                        -- 선수등록번호         ");
		sbSql.append("\n         , TRM.NM_KOR                                                                                                                       ");
		sbSql.append("\n         , TRM.RACER_GRD_CD                                                                                                                 ");
		sbSql.append("\n         , TO_CHAR(SYSDATE, 'YYYY') - SUBSTR(TRD  .BIRTH_DT, 1, 4) AGE                                           							");
		sbSql.append("\n         , (SELECT MAX(TRDO.RACE_DAY) RACE_DAY FROM TBEB_RACE_DAY_ORD TRDO WHERE TRDO.STND_YEAR = ? AND TRDO.MBR_CD = ? AND TRDO.TMS = ?)   APLY_DT                         -- 적용일자             ");
		sbSql.append("\n         , TMS_6_AMU_RANK_SCR                                                                                                               TMS_6_AMU_RANK_SCR              -- 6회누적착순점        ");
		sbSql.append("\n         , TRIM(TO_CHAR(TMS_6_AMU_RANK_SCR / TMS_6_RACE_CNT, 90.99))                                                                        TMS_6_AVG_RANK_SCR              -- 6회평균착순점        ");
		sbSql.append("\n         , TMS_6_AMU_ACDNT_SCR                                                                                                              TMS_6_AMU_ACDNT_SCR             -- 6회누적사고점        ");
		sbSql.append("\n         , TRIM(TO_CHAR(TMS_6_AMU_ACDNT_SCR / TMS_6_RACE_CNT, 90.99))                                                                       TMS_6_AVG_ACDNT_SCR             -- 6회평균착순점        ");
		sbSql.append("\n         , TRIM(TO_CHAR((TMS_6_AMU_RANK_SCR - NVL(TMS_6_AMU_ACDNT_SCR, 0)) / TMS_6_RACE_CNT, 90.99))                                        TMS_6_AVG_SCR                   -- 6회평균득점          ");
		sbSql.append("\n         , TRIM(TO_CHAR((TMS_6_RANK_1) / TMS_6_RACE_CNT * 100, 990.9))                                                                      TMS_6_WIN_RATIO                 -- 6회승률              ");
		sbSql.append("\n         , TRIM(TO_CHAR((TMS_6_RANK_1 + TMS_6_RANK_2) / TMS_6_RACE_CNT * 100, 990.9))                                                       TMS_6_HIGH_RANK_RATIO           -- 6회연대율            ");
		sbSql.append("\n         , TRIM(TO_CHAR((TMS_6_RANK_1 + TMS_6_RANK_2 + TMS_6_RANK_3) / TMS_6_RACE_CNT * 100, 990.9))                                        TMS_6_HIGH_3_RANK_RATIO         -- 6회3연대율           ");
		sbSql.append("\n         , TMS_6_AVG_STAR_TM                                                                                                                TMS_6_AVG_STRT_TM               -- 6회평균ST            ");
		sbSql.append("\n         , TMS_8_RANK_1 || TMS_8_RANK_2 || TMS_8_RANK_3 || TMS_8_RANK_4 || TMS_8_RANK_5 || TMS_8_RANK_6 || TMS_8_RANK_7 || TMS_8_RANK_8     TMS_8_RANK_ORD                  -- 8경주 착순           ");
		sbSql.append("\n         , TMS_6_RACE_CNT                                                                                                                   TMS_6_RACE_CNT                  -- 6회출주횟수          ");
		sbSql.append("\n         , TMS_6_RANK_1                                                                                                                     TMS_6_RANK_1                    -- 6회1착횟수           ");
		sbSql.append("\n         , TMS_6_RANK_2                                                                                                                     TMS_6_RANK_2                    -- 6회2착횟수           ");
		sbSql.append("\n         , TMS_6_RANK_3                                                                                                                     TMS_6_RANK_3                    -- 6회3착횟수           ");
		sbSql.append("\n         , AMU_RANK_SCR                                                                                                                     AMU_RANK_SCR                    -- 누적착순점           ");
		sbSql.append("\n         , NVL(STGP.GPP_SCR, 0)                                                                                                             GPP_SCR                         -- GPP(그랑프리포인트)  ");
		sbSql.append("\n         , TRIM(TO_CHAR(AMU_RANK_SCR / RACE_CNT, 90.99))                                                                                    AVG_RANK_SCR                    -- 평균착순점           ");
		sbSql.append("\n         , AMU_ACDNT_SCR                                                                                                                    AMU_ACDNT_SCR                   -- 누적사고점           ");
		sbSql.append("\n         , TRIM(TO_CHAR(AMU_ACDNT_SCR / RACE_CNT, 90.99))                                                                                   AVG_ACDNT_SCR                   -- 평균사고점           ");
		sbSql.append("\n         , BF_ACDNT_SCR                                                                                                                     BF_AMU_ACDNT_SCR                -- 전반기사고점         ");
		sbSql.append("\n         , TRIM(TO_CHAR(BF_ACDNT_SCR / BF_RACE_CNT, 90.99))                                                                                 BF_AVG_ACDNT_SCR                  -- 평균사고점         ");
		sbSql.append("\n         , AF_ACDNT_SCR                                                                                                                     AF_AMU_ACDNT_SCR                -- 후반기사고점         ");
		sbSql.append("\n         , TRIM(TO_CHAR(AF_ACDNT_SCR / AF_RACE_CNT, 90.99))                                                                                 AF_AVG_ACDNT_SCR                  -- 평균사고점         ");
		sbSql.append("\n         , TRIM(TO_CHAR((AMU_RANK_SCR - AMU_ACDNT_SCR) / RACE_CNT, 90.99))                                                                  AVG_SCR                         -- 누적득점             ");
		sbSql.append("\n         , TRIM(TO_CHAR((RANK_1) / RACE_CNT * 100, 990.9))                                                                                  WIN_RATIO                       -- 승률                 ");
		sbSql.append("\n         , TRIM(TO_CHAR((RANK_1 + RANK_2) / RACE_CNT * 100, 990.9))                                                                         HIGH_RANK_RATIO                 -- 연대율               ");
		sbSql.append("\n         , TRIM(TO_CHAR((RANK_1 + RANK_2 + RANK_3) / RACE_CNT * 100, 990.9))                                                                HIGH_3_RANK_RATIO               -- 3연대율              ");
		sbSql.append("\n         , AVG_STAR_TM                                                                                                                      AVG_STRT_TM                     -- 평균ST               ");
		sbSql.append("\n         , RUN_CNT                                                                                                                          RUN_CNT                         -- 출전횟수             ");
		sbSql.append("\n         , RACE_DAY_CNT                                                                                                                     RACE_DAY_CNT                    -- 출전일수             ");
		sbSql.append("\n         , RACE_CNT                                                                                                                         RACE_CNT                        -- 출주횟수             ");
		sbSql.append("\n         , TOT_RACE_CNT                                                                                                                     TOT_RACE_CNT                    -- 총출주횟수           ");
		sbSql.append("\n         , BF_RACE_CNT                                                                                                                      BF_RACE_CNT                     -- 전반기출주횟수       ");
		sbSql.append("\n         , AF_RACE_CNT                                                                                                                      AF_RACE_CNT                     -- 후반기출주횟수       ");
		sbSql.append("\n         , RANK_1                                                                                                                           RANK_1_CNT                      -- 1착횟수              ");
		sbSql.append("\n         , RANK_2                                                                                                                           RANK_2_CNT                      -- 2착횟수              ");
		sbSql.append("\n         , RANK_3                                                                                                                           RANK_3_CNT                      -- 3착횟수              ");
		sbSql.append("\n         , RANK_4                                                                                                                           RANK_4_CNT                      -- 4착횟수              ");
		sbSql.append("\n         , RANK_5                                                                                                                           RANK_5_CNT                      -- 5착횟수              ");
		sbSql.append("\n         , RANK_6                                                                                                                           RANK_6_CNT                      -- 6착횟수              ");
		sbSql.append("\n         , RACE_CNT - (RANK_1 + RANK_2 + RANK_3 + RANK_4 + RANK_5 + RANK_6)                                                                 RANK_ETC_CNT                    -- 착외횟수             ");
		sbSql.append("\n         , 0                                                                                                                                DGRE_JUDG_F_CNT                 -- 등급심사 F횟수       ");
		sbSql.append("\n         , 0                                                                                                                                DGRE_JUDG_L_CNT                 -- 등습심사 L횟수       ");
		sbSql.append("\n         , BF_F_CNT                                                                                                                         BF_F_CNT                        -- 전반기F횟수          ");
		sbSql.append("\n         , AF_F_CNT                                                                                                                         AF_F_CNT                        -- 후반기F횟수          ");
		sbSql.append("\n         , BF_L_CNT                                                                                                                         BF_L_CNT                        -- 전반기L횟수          ");
		sbSql.append("\n         , AF_L_CNT                                                                                                                         AF_L_CNT                        -- 후반기L횟수          ");
		sbSql.append("\n         , ABSE_CNT                                                                                                                         ABSE_CNT                        -- 결장횟수             ");
		sbSql.append("\n         , RACE_ESC_1_CNT                                                                                                                   RACE_ESC_1_CNT                  -- 출주제외1횟수        ");
		sbSql.append("\n         , RACE_ESC_2_CNT                                                                                                                   RACE_ESC_2_CNT                  -- 출주제외2횟수        ");
		sbSql.append("\n         , FOUL_ELIM_CNT                                                                                                                    FOUL_ELIM_CNT                   -- 반칙실격횟수         ");
		sbSql.append("\n         , ELIM_CNT                                                                                                                         ELIM_CNT                        -- 실격횟수             ");
		sbSql.append("\n         , FOUL_WARN_CNT                                                                                                                    FOUL_WARN_CNT                   -- 반칙경고횟수         ");
		sbSql.append("\n         , WARN_CNT                                                                                                                         WARN_CNT                        -- 경고횟수             ");
		sbSql.append("\n         , ATTEN_CNT                                                                                                                        ATTEN_CNT                       -- 주의횟수             ");
		sbSql.append("\n         , BF_TMS                                                                                                                           BF_TMS                          -- 전회차               ");
		sbSql.append("\n         , BF_BF_TMS                                                                                                                        BF_BF_TMS                       -- 전전회차             ");
		sbSql.append("\n         , BF_BF_BF_TMS                                                                                                                     BF_BF_BF_TMS                    -- 전전전회차           ");
		sbSql.append("\n         , BF_MBR_CD                                                                                                                        BF_MBR_CD                       -- 전경정장             ");
		sbSql.append("\n         , BF_BF_MBR_CD                                                                                                                     BF_BF_MBR_CD                    -- 전전경정장           ");
		sbSql.append("\n         , BF_BF_BF_MBR_CD                                                                                                                  BF_BF_BF_MBR_CD                 -- 전전전경정장         ");
		sbSql.append("\n         , ?                                                                                                                                INST_ID                         -- 작성자ID             ");
		sbSql.append("\n         , SYSDATE                                                                                                                          INST_DTM                        -- 작성일시             ");
		sbSql.append("\n         , ?                                                                                                                                UPDT_ID                         -- 수정자ID             ");
		sbSql.append("\n         , SYSDATE                                                                                                                          UPDT_DTM                        -- 수정일시             ");
		sbSql.append("\n FROM      TBEC_RACER_MASTER TRM                                                         ");
		sbSql.append("\n         , TBEC_RACER_DETAIL TRD                                                         ");
		sbSql.append("\n         , (                                                                             ");
		sbSql.append("\n             -- 6회차 성적                                                               ");
		sbSql.append("\n             SELECT                                                                      ");
		sbSql.append("\n                      TOR.RACER_NO                                                       ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 1, 1, 0)), 0)   TMS_6_RANK_1              ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 2, 1, 0)), 0)   TMS_6_RANK_2              ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 3, 1, 0)), 0)   TMS_6_RANK_3              ");
		sbSql.append("\n                    , ROUND(STTM.TMS_6_AVG_STAR_TM, 2) TMS_6_AVG_STAR_TM                 ");
		sbSql.append("\n                    , NVL(SUM(TRS.RACE_SCR)             , 0)   TMS_6_AMU_RANK_SCR        ");
		sbSql.append("\n                    , NVL(SUM(TRVA.ACDNT_SCR)           , 0)   TMS_6_AMU_ACDNT_SCR       ");
		sbSql.append("\n                    , COUNT(*)                                 TMS_6_RACE_CNT            ");
		sbSql.append("\n             FROM     (                                                                  ");
		sbSql.append("\n                         SELECT                                                          ");
		sbSql.append("\n                                TOR.*                                                    ");
		sbSql.append("\n                         FROM   TBEB_ORGAN        TOR                                    ");
		sbSql.append("\n                         WHERE  (TOR.STND_YEAR, TOR.MBR_CD, TOR.TMS, TOR.RACER_NO) IN (  ");
		sbSql.append("\n                                                                                         SELECT                                                                                         ");
		sbSql.append("\n                                                                                                  TOR.STND_YEAR                                                                         ");
		sbSql.append("\n                                                                                                , TOR.MBR_CD                                                                            ");
		sbSql.append("\n                                                                                                , TOR.TMS                                                                               ");
		sbSql.append("\n                                                                                                , TOR.RACER_NO                                                                          ");
		sbSql.append("\n                                                                                         FROM   (                                                                                       ");
		sbSql.append("\n                                                                                                     SELECT                                                                             ");
		sbSql.append("\n                                                                                                              TOR.STND_YEAR                                                             ");
		sbSql.append("\n                                                                                                            , TOR.MBR_CD                                                                ");
		sbSql.append("\n                                                                                                            , TOR.TMS                                                                   ");
		sbSql.append("\n                                                                                                            , TOR.RACER_NO                                                              ");
		sbSql.append("\n                                                                                                            , RANK() OVER (PARTITION BY TOR.RACER_NO                                    ");
		sbSql.append("\n                                                                                                                               ORDER BY TOR.RACE_DAY DESC ) TMS_6                       ");
		sbSql.append("\n                                                                                                     FROM   (                                                                           ");
		sbSql.append("\n                                                                                                                 SELECT                                                                 ");
		sbSql.append("\n                                                                                                                          TOR.STND_YEAR                                                 ");
		sbSql.append("\n                                                                                                                        , TOR.MBR_CD                                                    ");
		sbSql.append("\n                                                                                                                        , TOR.TMS                                                       ");
		sbSql.append("\n                                                                                                                        , TOR.RACER_NO                                                  ");
		sbSql.append("\n                                                                                                                        , MAX(TOR.RACE_DAY) RACE_DAY                                    ");
		sbSql.append("\n                                                                                                                 FROM   TBEB_ORGAN        TOR                                           ");
		sbSql.append("\n                                                                                                                 WHERE  TOR .RACE_DAY    <= (                                           ");
		sbSql.append("\n                                                                                                                                                 SELECT                                 ");
		sbSql.append("\n                                                                                                                                                        MAX(TRDO.RACE_DAY) RACE_DAY     ");
		sbSql.append("\n                                                                                                                                                 FROM   TBEB_RACE_DAY_ORD TRDO          ");
		sbSql.append("\n                                                                                                                                                 WHERE  TRDO.STND_YEAR = ?              ");
		sbSql.append("\n                                                                                                                                                 AND    TRDO.MBR_CD    = ?              ");
		sbSql.append("\n                                                                                                                                                 AND    TRDO.TMS       = ?              ");
		sbSql.append("\n                                                                                                                                            )                                           ");
		sbSql.append("\n                                                                                                                 AND    TOR.ABSE_CD     <> '003'   -- 면책결장이 아닌 경우              ");
		sbSql.append("\n                                                                                                                 AND    TOR.IMMT_CLS_CD <> '003'   -- 면책이 아닌 경우                  ");
		sbSql.append("\n                                                                                                                 AND    TOR.STND_YEAR   >= ? - 1                                        ");
		sbSql.append("\n                                                                                                                 GROUP BY                                                               ");
		sbSql.append("\n                                                                                                                          TOR.STND_YEAR                                                 ");
		sbSql.append("\n                                                                                                                        , TOR.MBR_CD                                                    ");
		sbSql.append("\n                                                                                                                        , TOR.TMS                                                       ");
		sbSql.append("\n                                                                                                                        , TOR.RACER_NO                                                  ");
		sbSql.append("\n                                                                                                            ) TOR                                                                       ");
		sbSql.append("\n                                                                                                ) TOR                                                                                   ");
		sbSql.append("\n                                                                                         WHERE  TMS_6 <= 6                                                                              ");
		sbSql.append("\n                                                                                      )                                                                                                 ");
		sbSql.append("\n                      ) TOR                                                                                                                                                             ");
		sbSql.append("\n                    , (                                                                                                                                                                 ");
		sbSql.append("\n                         SELECT                                                                                                                                                         ");
		sbSql.append("\n                                  TOR.RACER_NO                                                                                                                                          ");
		sbSql.append("\n                                , NVL(SUM(DECODE(TOR.ST_MTHD_CD, '001', 0, TOR.STAR_TM))/NVL(SUM(DECODE(TOR.ST_MTHD_CD, '001', 0, 1)),1), 0) TMS_6_AVG_STAR_TM                          ");
		sbSql.append("\n                         FROM   TBEB_ORGAN        TOR                                                                                                                                   ");
		sbSql.append("\n                         WHERE  (TOR.STND_YEAR, TOR.MBR_CD, TOR.TMS, TOR.RACER_NO) IN (                                                                                                 ");
		sbSql.append("\n                                                                                         SELECT                                                                                         ");
		sbSql.append("\n                                                                                                  TOR.STND_YEAR                                                                         ");
		sbSql.append("\n                                                                                                , TOR.MBR_CD                                                                            ");
		sbSql.append("\n                                                                                                , TOR.TMS                                                                               ");
		sbSql.append("\n                                                                                                , TOR.RACER_NO                                                                          ");
		sbSql.append("\n                                                                                         FROM   (                                                                                       ");
		sbSql.append("\n                                                                                                     SELECT                                                                             ");
		sbSql.append("\n                                                                                                              TOR.STND_YEAR                                                             ");
		sbSql.append("\n                                                                                                            , TOR.MBR_CD                                                                ");
		sbSql.append("\n                                                                                                            , TOR.TMS                                                                   ");
		sbSql.append("\n                                                                                                            , TOR.RACER_NO                                                              ");
		sbSql.append("\n                                                                                                            , RANK() OVER (PARTITION BY TOR.RACER_NO                                    ");
		sbSql.append("\n                                                                                                                               ORDER BY TOR.RACE_DAY DESC ) TMS_6                       ");
		sbSql.append("\n                                                                                                     FROM   (                                                                           ");
		sbSql.append("\n                                                                                                                 SELECT                                                                 ");
		sbSql.append("\n                                                                                                                          TOR.STND_YEAR                                                 ");
		sbSql.append("\n                                                                                                                        , TOR.MBR_CD                                                    ");
		sbSql.append("\n                                                                                                                        , TOR.TMS                                                       ");
		sbSql.append("\n                                                                                                                        , TOR.RACER_NO                                                  ");
		sbSql.append("\n                                                                                                                        , MAX(TOR.RACE_DAY) RACE_DAY                                    ");
		sbSql.append("\n                                                                                                                 FROM   TBEB_ORGAN        TOR                                           ");
		sbSql.append("\n                                                                                                                 WHERE  TOR .RACE_DAY    <= (                                           ");
		sbSql.append("\n                                                                                                                                                 SELECT                                 ");
		sbSql.append("\n                                                                                                                                                        MAX(TRDO.RACE_DAY) RACE_DAY     ");
		sbSql.append("\n                                                                                                                                                 FROM   TBEB_RACE_DAY_ORD TRDO          ");
		sbSql.append("\n                                                                                                                                                 WHERE  TRDO.STND_YEAR = ?              ");
		sbSql.append("\n                                                                                                                                                 AND    TRDO.MBR_CD    = ?              ");
		sbSql.append("\n                                                                                                                                                 AND    TRDO.TMS       = ?              ");
		sbSql.append("\n                                                                                                                                            )                                           ");
		sbSql.append("\n                                                                                                                 AND    TOR.ABSE_CD      = '001'   -- 결장이 아닌 경우                  ");
		sbSql.append("\n                                                                                                                 AND    TOR.STND_YEAR   >= ? - 1                                        ");
		sbSql.append("\n                                                                                                                 GROUP BY                                                               ");
		sbSql.append("\n                                                                                                                          TOR.STND_YEAR                                                 ");
		sbSql.append("\n                                                                                                                        , TOR.MBR_CD                                                    ");
		sbSql.append("\n                                                                                                                        , TOR.TMS                                                       ");
		sbSql.append("\n                                                                                                                        , TOR.RACER_NO                                                  ");
		sbSql.append("\n                                                                                                            ) TOR                                                                       ");
		sbSql.append("\n                                                                                                ) TOR                                                                                   ");
		sbSql.append("\n                                                                                         WHERE  TMS_6 <= 6                                                                              ");
		sbSql.append("\n                                                                                      )                                                                                                 ");
		sbSql.append("\n                        GROUP BY TOR.RACER_NO                                  ");
		sbSql.append("\n                      ) STTM                                                   ");
		sbSql.append("\n                    , TBEB_RACE          TR                                    ");
		sbSql.append("\n                    , TBEB_RANK_SCR      TRS                                   ");
		sbSql.append("\n                    , (                                                        ");
		sbSql.append("\n                         SELECT                                                ");
		sbSql.append("\n 			                      TRVA.STND_YEAR                                     ");
		sbSql.append("\n 			                    , TRVA.MBR_CD                                        ");
		sbSql.append("\n 			                    , TRVA.TMS                                           ");
		sbSql.append("\n 			                    , TRVA.DAY_ORD                                       ");
		sbSql.append("\n 			                    , TRVA.RACE_NO                                       ");
		sbSql.append("\n 			                    , TRVA.RACE_REG_NO                                   ");
		sbSql.append("\n 			                    , NVL(TRVB.ACDNT_SCR, TRVA.ACDNT_SCR) ACDNT_SCR      ");
		sbSql.append("\n                         FROM     (                                            ");
		sbSql.append("\n                                    SELECT                                     ");
		sbSql.append("\n                                             TRVA.STND_YEAR                    ");
		sbSql.append("\n                                           , TRVA.MBR_CD                       ");
		sbSql.append("\n                                           , TRVA.TMS                          ");
		sbSql.append("\n                                           , TRVA.DAY_ORD                      ");
		sbSql.append("\n                                           , TRVA.RACE_NO                      ");
		sbSql.append("\n                                           , TRVA.RACE_REG_NO                  ");
		sbSql.append("\n                                           , SUM(ACDNT_SCR) ACDNT_SCR          ");
		sbSql.append("\n                                    FROM     TBED_RACE_VOIL_ACT TRVA           ");
		sbSql.append("\n                                           , TBEB_ACDNT_SCR     TAS            ");
		sbSql.append("\n                                           , TBEB_ORGAN         TOR            ");
		sbSql.append("\n                                    WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR   ");
		sbSql.append("\n                                    AND    TRVA.VOIL_CD      = TAS.VOIL_CD     ");
		sbSql.append("\n                                    AND    TRVA.STND_YEAR    = TOR.STND_YEAR   ");
		sbSql.append("\n                                    AND    TRVA.MBR_CD       = TOR.MBR_CD      ");
		sbSql.append("\n                                    AND    TRVA.TMS          = TOR.TMS         ");
		sbSql.append("\n                                    AND    TRVA.DAY_ORD      = TOR.DAY_ORD     ");
		sbSql.append("\n                                    AND    TRVA.RACE_NO      = TOR.RACE_NO     ");
		sbSql.append("\n                                    AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO ");
		sbSql.append("\n                                    AND    TOR.ABSE_CD      <> '003'           ");
		sbSql.append("\n                                    AND    TOR.IMMT_CLS_CD  <> '003'           ");
		sbSql.append("\n                                    AND    (TRVA.STND_YEAR, TRVA.MBR_CD, TRVA.TMS, TRVA.RACER_NO) IN (                                                                                             ");
		sbSql.append("\n                                                                                                        SELECT                                                                                     ");
		sbSql.append("\n                                                                                                                 TOR.STND_YEAR                                                                     ");
		sbSql.append("\n                                                                                                               , TOR.MBR_CD                                                                        ");
		sbSql.append("\n                                                                                                               , TOR.TMS                                                                           ");
		sbSql.append("\n                                                                                                               , TOR.RACER_NO                                                                      ");
		sbSql.append("\n                                                                                                        FROM   (                                                                                   ");
		sbSql.append("\n                                                                                                                    SELECT                                                                         ");
		sbSql.append("\n                                                                                                                             TOR.STND_YEAR                                                         ");
		sbSql.append("\n                                                                                                                           , TOR.MBR_CD                                                            ");
		sbSql.append("\n                                                                                                                           , TOR.TMS                                                               ");
		sbSql.append("\n                                                                                                                           , TOR.RACER_NO                                                          ");
		sbSql.append("\n                                                                                                                           , RANK() OVER (PARTITION BY TOR.RACER_NO                                ");
		sbSql.append("\n                                                                                                                                              ORDER BY TOR.RACE_DAY DESC ) TMS_6                   ");
		sbSql.append("\n                                                                                                                    FROM   (                                                                       ");
		sbSql.append("\n                                                                                                                                SELECT                                                             ");
		sbSql.append("\n                                                                                                                                         TOR.STND_YEAR                                             ");
		sbSql.append("\n                                                                                                                                       , TOR.MBR_CD                                                ");
		sbSql.append("\n                                                                                                                                       , TOR.TMS                                                   ");
		sbSql.append("\n                                                                                                                                       , TOR.RACER_NO                                              ");
		sbSql.append("\n                                                                                                                                       , MAX(TOR.RACE_DAY) RACE_DAY                                ");
		sbSql.append("\n                                                                                                                                FROM   TBEB_ORGAN        TOR                                       ");
		sbSql.append("\n                                                                                                                                WHERE  TOR .RACE_DAY    <= (                                       ");
		sbSql.append("\n                                                                                                                                                                SELECT                             ");
		sbSql.append("\n                                                                                                                                                                       MAX(TRDO.RACE_DAY) RACE_DAY ");
		sbSql.append("\n                                                                                                                                                                FROM   TBEB_RACE_DAY_ORD TRDO      ");
		sbSql.append("\n                                                                                                                                                                WHERE  TRDO.STND_YEAR = ?          ");
		sbSql.append("\n                                                                                                                                                                AND    TRDO.MBR_CD    = ?          ");
		sbSql.append("\n                                                                                                                                                                AND    TRDO.TMS       = ?          ");
		sbSql.append("\n                                                                                                                                                           )                                       ");
		sbSql.append("\n                                                                                                                                AND    TOR.ABSE_CD     <> '003'   -- 참석 경우                     ");
		sbSql.append("\n                                                                                                                                AND    TOR.IMMT_CLS_CD <> '003'    -- 면책이 아닌 경우             ");
		sbSql.append("\n                                                                                                                                AND    TOR.STND_YEAR   >= ? - 1                                    ");
		sbSql.append("\n                                                                                                                                GROUP BY                                                           ");
		sbSql.append("\n                                                                                                                                         TOR.STND_YEAR                                             ");
		sbSql.append("\n                                                                                                                                       , TOR.MBR_CD                                                ");
		sbSql.append("\n                                                                                                                                       , TOR.TMS                                                   ");
		sbSql.append("\n                                                                                                                                       , TOR.RACER_NO                                              ");
		sbSql.append("\n                                                                                                                           ) TOR                                                                   ");
		sbSql.append("\n                                                                                                               ) TOR                                                                               ");
		sbSql.append("\n                                                                                                        WHERE  TMS_6 <= 6                                                                          ");
		sbSql.append("\n                                                                                                     )                                                                                             ");
		sbSql.append("\n                                    GROUP BY                                   ");
		sbSql.append("\n                                             TRVA.STND_YEAR                    ");
		sbSql.append("\n                                           , TRVA.MBR_CD                       ");
		sbSql.append("\n                                           , TRVA.TMS                          ");
		sbSql.append("\n                                           , TRVA.DAY_ORD                      ");
		sbSql.append("\n                                           , TRVA.RACE_NO                      ");
		sbSql.append("\n                                           , TRVA.RACE_REG_NO                  ");
		sbSql.append("\n 			                     ) TRVA                                              ");
		sbSql.append("\n 			                   , (                                                   ");
		sbSql.append("\n                                    SELECT                                     ");
		sbSql.append("\n                                             TRVA.STND_YEAR                    ");
		sbSql.append("\n                                           , TRVA.MBR_CD                       ");
		sbSql.append("\n                                           , TRVA.TMS                          ");
		sbSql.append("\n                                           , TRVA.DAY_ORD                      ");
		sbSql.append("\n                                           , TRVA.RACE_NO                      ");
		sbSql.append("\n                                           , TRVA.RACE_REG_NO                  ");
		sbSql.append("\n                                           , TAS .ACDNT_SCR                    ");
		sbSql.append("\n                                    FROM     TBED_RACE_VOIL_ACT TRVA           ");
		sbSql.append("\n                                           , TBEB_ACDNT_SCR     TAS            ");
		sbSql.append("\n                                           , TBEB_ORGAN         TOR            ");
		sbSql.append("\n                                    WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR   ");
		sbSql.append("\n                                    AND    TRVA.VOIL_CD      = TAS.VOIL_CD     ");
		sbSql.append("\n                                    AND    TRVA.STND_YEAR    = TOR.STND_YEAR   ");
		sbSql.append("\n                                    AND    TRVA.MBR_CD       = TOR.MBR_CD      ");
		sbSql.append("\n                                    AND    TRVA.TMS          = TOR.TMS         ");
		sbSql.append("\n                                    AND    TRVA.DAY_ORD      = TOR.DAY_ORD     ");
		sbSql.append("\n                                    AND    TRVA.RACE_NO      = TOR.RACE_NO     ");
		sbSql.append("\n                                    AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO ");
		sbSql.append("\n                                    AND    TOR.ABSE_CD      <> '003'           ");
		sbSql.append("\n                                    AND    TOR.IMMT_CLS_CD  <> '003'           ");
		sbSql.append("\n                                    AND    TRVA.VOIL_CD     IN ('110', '120')  ");
		sbSql.append("\n                                    AND    (TRVA.STND_YEAR, TRVA.MBR_CD, TRVA.TMS, TRVA.RACER_NO) IN (                                                                                             ");
		sbSql.append("\n                                                                                                        SELECT                                                                                     ");
		sbSql.append("\n                                                                                                                 TOR.STND_YEAR                                                                     ");
		sbSql.append("\n                                                                                                               , TOR.MBR_CD                                                                        ");
		sbSql.append("\n                                                                                                               , TOR.TMS                                                                           ");
		sbSql.append("\n                                                                                                               , TOR.RACER_NO                                                                      ");
		sbSql.append("\n                                                                                                        FROM   (                                                                                   ");
		sbSql.append("\n                                                                                                                    SELECT                                                                         ");
		sbSql.append("\n                                                                                                                             TOR.STND_YEAR                                                         ");
		sbSql.append("\n                                                                                                                           , TOR.MBR_CD                                                            ");
		sbSql.append("\n                                                                                                                           , TOR.TMS                                                               ");
		sbSql.append("\n                                                                                                                           , TOR.RACER_NO                                                          ");
		sbSql.append("\n                                                                                                                           , RANK() OVER (PARTITION BY TOR.RACER_NO                                ");
		sbSql.append("\n                                                                                                                                              ORDER BY TOR.RACE_DAY DESC ) TMS_6                   ");
		sbSql.append("\n                                                                                                                    FROM   (                                                                       ");
		sbSql.append("\n                                                                                                                                SELECT                                                             ");
		sbSql.append("\n                                                                                                                                         TOR.STND_YEAR                                             ");
		sbSql.append("\n                                                                                                                                       , TOR.MBR_CD                                                ");
		sbSql.append("\n                                                                                                                                       , TOR.TMS                                                   ");
		sbSql.append("\n                                                                                                                                       , TOR.RACER_NO                                              ");
		sbSql.append("\n                                                                                                                                       , MAX(TOR.RACE_DAY) RACE_DAY                                ");
		sbSql.append("\n                                                                                                                                FROM   TBEB_ORGAN        TOR                                       ");
		sbSql.append("\n                                                                                                                                WHERE  TOR .RACE_DAY    <= (                                       ");
		sbSql.append("\n                                                                                                                                                                SELECT                             ");
		sbSql.append("\n                                                                                                                                                                       MAX(TRDO.RACE_DAY) RACE_DAY ");
		sbSql.append("\n                                                                                                                                                                FROM   TBEB_RACE_DAY_ORD TRDO      ");
		sbSql.append("\n                                                                                                                                                                WHERE  TRDO.STND_YEAR = ?          ");
		sbSql.append("\n                                                                                                                                                                AND    TRDO.MBR_CD    = ?          ");
		sbSql.append("\n                                                                                                                                                                AND    TRDO.TMS       = ?          ");
		sbSql.append("\n                                                                                                                                                           )                                       ");
		sbSql.append("\n                                                                                                                                AND    TOR.ABSE_CD     <> '003'   -- 참석 경우                     ");
		sbSql.append("\n                                                                                                                                AND    TOR.IMMT_CLS_CD <> '003'    -- 면책이 아닌 경우             ");
		sbSql.append("\n                                                                                                                                AND    TOR.STND_YEAR   >= ? - 1                                    ");
		sbSql.append("\n                                                                                                                                GROUP BY                                                           ");
		sbSql.append("\n                                                                                                                                         TOR.STND_YEAR                                             ");
		sbSql.append("\n                                                                                                                                       , TOR.MBR_CD                                                ");
		sbSql.append("\n                                                                                                                                       , TOR.TMS                                                   ");
		sbSql.append("\n                                                                                                                                       , TOR.RACER_NO                                              ");
		sbSql.append("\n                                                                                                                           ) TOR                                                                   ");
		sbSql.append("\n                                                                                                               ) TOR                                                                               ");
		sbSql.append("\n                                                                                                        WHERE  TMS_6 <= 6                                                                          ");
		sbSql.append("\n                                                                                                     )                                                                                             ");
		sbSql.append("\n                                  ) TRVB                                 ");
		sbSql.append("\n                        WHERE  TRVA.STND_YEAR   = TRVB.STND_YEAR  (+)    ");
		sbSql.append("\n 			            AND    TRVA.MBR_CD      = TRVB.MBR_CD     (+)          ");
		sbSql.append("\n 			            AND    TRVA.TMS         = TRVB.TMS        (+)          ");
		sbSql.append("\n 			            AND    TRVA.DAY_ORD     = TRVB.DAY_ORD    (+)          ");
		sbSql.append("\n 			            AND    TRVA.RACE_NO     = TRVB.RACE_NO    (+)          ");
		sbSql.append("\n 			            AND    TRVA.RACE_REG_NO = TRVB.RACE_REG_NO(+)          ");
		sbSql.append("\n                      ) TRVA                                             ");
		sbSql.append("\n             WHERE  TOR .STND_YEAR    = TR  .STND_YEAR                   ");
		sbSql.append("\n             AND    TOR .MBR_CD       = TR  .MBR_CD                      ");
		sbSql.append("\n             AND    TOR .TMS          = TR  .TMS                         ");
		sbSql.append("\n             AND    TOR .DAY_ORD      = TR  .DAY_ORD                     ");
		sbSql.append("\n             AND    TOR .RACE_NO      = TR  .RACE_NO                     ");
		sbSql.append("\n             AND    TOR .STND_YEAR    = TRS .STND_YEAR                   ");
		sbSql.append("\n             AND    TR  .RACE_DGRE_CD = TRS .RACE_DGRE_CD                ");
		sbSql.append("\n             AND    NVL(TOR .RANK,0)  = TRS .RANK                        ");
		sbSql.append("\n             AND    TOR .STND_YEAR    = TRVA.STND_YEAR  (+)              ");
		sbSql.append("\n             AND    TOR .MBR_CD       = TRVA.MBR_CD     (+)              ");
		sbSql.append("\n             AND    TOR .TMS          = TRVA.TMS        (+)              ");
		sbSql.append("\n             AND    TOR .DAY_ORD      = TRVA.DAY_ORD    (+)              ");
		sbSql.append("\n             AND    TOR .RACE_NO      = TRVA.RACE_NO    (+)              ");
		sbSql.append("\n             AND    TOR .RACE_REG_NO  = TRVA.RACE_REG_NO(+)              ");
		sbSql.append("\n             AND    TOR .RACER_NO     = STTM.RACER_NO   (+)              ");
		sbSql.append("\n             AND    TOR.ABSE_CD      <> '003'   -- 면책결장이 아닌 경우  ");
		sbSql.append("\n             AND    TOR.IMMT_CLS_CD  <> '003'   -- 면책이 아닌 경우      ");
		sbSql.append("\n             GROUP BY TOR .RACER_NO                                      ");
		sbSql.append("\n                    , STTM.TMS_6_AVG_STAR_TM                             ");
		sbSql.append("\n           ) TMS_6                                                       ");
		sbSql.append("\n         , (                                                             ");
		sbSql.append("\n             -- 8회차 성적                                               ");
		sbSql.append("\n             SELECT                                                      ");
		sbSql.append("\n                      RACER_NO                                           ");
		sbSql.append("\n                    , MIN(DECODE(TMS_8, 1, RANK)) TMS_8_RANK_1           ");
		sbSql.append("\n                    , MIN(DECODE(TMS_8, 2, RANK)) TMS_8_RANK_2           ");
		sbSql.append("\n                    , MIN(DECODE(TMS_8, 3, RANK)) TMS_8_RANK_3           ");
		sbSql.append("\n                    , MIN(DECODE(TMS_8, 4, RANK)) TMS_8_RANK_4           ");
		sbSql.append("\n                    , MIN(DECODE(TMS_8, 5, RANK)) TMS_8_RANK_5           ");
		sbSql.append("\n                    , MIN(DECODE(TMS_8, 6, RANK)) TMS_8_RANK_6           ");
		sbSql.append("\n                    , MIN(DECODE(TMS_8, 7, RANK)) TMS_8_RANK_7           ");
		sbSql.append("\n                    , MIN(DECODE(TMS_8, 8, RANK)) TMS_8_RANK_8           ");
		sbSql.append("\n             FROM   (                                                    ");
		sbSql.append("\n                         SELECT                                          ");
		sbSql.append("\n                                  TOR.STND_YEAR, TOR.MBR_CD, TOR.TMS, TOR.DAY_ORD, TOR.RACE_NO, TOR.RACE_REG_NO, TOR.RACER_NO, TOR.ABSE_CD, NVL(TOR.RANK,0) RANK  ");
		sbSql.append("\n                                , RANK() OVER (PARTITION BY TOR.RACER_NO                               ");
		sbSql.append("\n                                                   ORDER BY TOR.RACE_DAY DESC                          ");
		sbSql.append("\n		                                                  , TOR.RACE_NO  DESC ) TMS_8                      ");
		sbSql.append("\n                         FROM   TBEB_ORGAN        TOR, TBEB_RACE TR                                    ");
		sbSql.append("\n                         WHERE  TOR.ABSE_CD     <> '003'    -- 면책결장이 아닌 경우                    ");
		sbSql.append("\n                         AND    TOR.IMMT_CLS_CD <> '003'    -- 면책이 아닌 경우                        ");
		sbSql.append("\n                         AND    TR.RACE_STTS_CD = '001'     -- 경주성립만 가져옴                       ");
		sbSql.append("\n                         AND    TOR.STND_YEAR = TR.STND_YEAR                                           ");
		sbSql.append("\n                         AND    TOR.MBR_CD = TR.MBR_CD                                                 ");
		sbSql.append("\n                         AND    TOR.TMS = TR.TMS                                                       ");
		sbSql.append("\n                         AND    TOR.DAY_ORD = TR.DAY_ORD                                               ");
		sbSql.append("\n                         AND    TOR.RACE_NO = TR.RACE_NO                                               ");
		sbSql.append("\n                         AND    TOR.RACE_DAY    <= (                                                   ");
		sbSql.append("\n                                                     SELECT                                            ");
		sbSql.append("\n                                                            MAX(TRDO.RACE_DAY) RACE_DAY                ");
		sbSql.append("\n                                                     FROM   TBEB_RACE_DAY_ORD TRDO                     ");
		sbSql.append("\n                                                     WHERE  TRDO.STND_YEAR = ?                         ");
		sbSql.append("\n                                                     AND    TRDO.MBR_CD    = ?                         ");
		sbSql.append("\n                                                     AND    TRDO.TMS       = ?                         ");
		sbSql.append("\n                                                   )                                                   ");
		sbSql.append("\n                   )                                                                                   ");
		sbSql.append("\n             WHERE   TMS_8 <= 8                                                                        ");
		sbSql.append("\n             GROUP BY RACER_NO                                                                         ");
		sbSql.append("\n           ) TMS_8                                                                                     ");
		sbSql.append("\n         , (                                                                                           ");
		sbSql.append("\n             -- 년도별 성적                                                                            ");
		sbSql.append("\n             SELECT                                                                                    ");
		sbSql.append("\n                      TOR.RACER_NO                                                                     ");
		sbSql.append("\n                    , COUNT(DISTINCT(TOR.MBR_CD || TOR.TMS))                RUN_CNT                    ");
		sbSql.append("\n                    , COUNT(DISTINCT(TOR.MBR_CD || TOR.TMS || TOR.DAY_ORD)) RACE_DAY_CNT               ");
		sbSql.append("\n                    , COUNT(*)                                              RACE_CNT                   ");
		sbSql.append("\n                    , SUM(DECODE(TRST.QURT_CD, '001', 1, NULL))              BF_RACE_CNT               ");
		sbSql.append("\n                    , SUM(DECODE(TRST.QURT_CD, '002', 1, NULL))              AF_RACE_CNT               ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '001', TRVA.ACDNT_SCR, NULL)), 0) BF_ACDNT_SCR      ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '002', TRVA.ACDNT_SCR, NULL)), 0) AF_ACDNT_SCR      ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 1, 1, 0))                       , 0) RANK_1             ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 2, 1, 0))                       , 0) RANK_2             ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 3, 1, 0))                       , 0) RANK_3             ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 4, 1, 0))                       , 0) RANK_4             ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 5, 1, 0))                       , 0) RANK_5             ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 6, 1, 0))                       , 0) RANK_6             ");
		sbSql.append("\n                    , NVL(STTM.AVG_STAR_TM                                     , 0) AVG_STAR_TM        ");
		sbSql.append("\n                    , NVL(SUM(TRS.RACE_SCR)                                    , 0) AMU_RANK_SCR       ");
		sbSql.append("\n                    , NVL(SUM(TRVA.ACDNT_SCR)                                  , 0) AMU_ACDNT_SCR      ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '001', TRVA.F_CNT, NULL))    , 0) BF_F_CNT          ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '002', TRVA.F_CNT, NULL))    , 0) AF_F_CNT          ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '001', TRVA.L_CNT, NULL))    , 0) BF_L_CNT          ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '002', TRVA.L_CNT, NULL))    , 0) AF_L_CNT          ");
		sbSql.append("\n                    , NVL(SUM(TRVA.ABSE_CNT      )                             , 0) ABSE_CNT           ");
		sbSql.append("\n                    , NVL(SUM(TRVA.RACE_ESC_1_CNT)                             , 0) RACE_ESC_1_CNT     ");
		sbSql.append("\n                    , NVL(SUM(TRVA.RACE_ESC_2_CNT)                             , 0) RACE_ESC_2_CNT     ");
		sbSql.append("\n                    , NVL(SUM(TRVA.FOUL_ELIM_CNT )                             , 0) FOUL_ELIM_CNT      ");
		sbSql.append("\n                    , NVL(SUM(TRVA.ELIM_CNT      )                             , 0) ELIM_CNT           ");
		sbSql.append("\n                    , NVL(SUM(TRVA.FOUL_WARN_CNT )                             , 0) FOUL_WARN_CNT      ");
		sbSql.append("\n                    , NVL(SUM(TRVA.WARN_CNT      )                             , 0) WARN_CNT           ");
		sbSql.append("\n                    , NVL(SUM(TRVA.ATTEN_CNT     )                             , 0) ATTEN_CNT          ");
		sbSql.append("\n             FROM     TBEB_ORGAN         TOR                                                           ");
		sbSql.append("\n                    , TBEB_RACE_TMS      TRT                                                           ");
		sbSql.append("\n                    , TBEB_RACE          TR                                                            ");
		sbSql.append("\n                    , TBEB_RANK_SCR      TRS                                                           ");
		sbSql.append("\n                    , TBEB_RECD_STND_TERM TRST                                                         ");
		sbSql.append("\n                    , (                                                                                ");
		sbSql.append("\n                         SELECT                                                                        ");
		sbSql.append("\n 			                      TRVA.STND_YEAR                                                             ");
		sbSql.append("\n 			                    , TRVA.MBR_CD                                                                ");
		sbSql.append("\n 			                    , TRVA.TMS                                                                   ");
		sbSql.append("\n 			                    , TRVA.DAY_ORD                                                               ");
		sbSql.append("\n 			                    , TRVA.RACE_NO                                                               ");
		sbSql.append("\n 			                    , TRVA.RACE_REG_NO                                                           ");
		sbSql.append("\n 			                    , NVL(TRVB.ACDNT_SCR, TRVA.ACDNT_SCR) ACDNT_SCR                              ");
		sbSql.append("\n                                , TRVA.F_CNT                                                           ");
		sbSql.append("\n                                , TRVA.L_CNT                                                           ");
		sbSql.append("\n                                , TRVA.ABSE_CNT                                                        ");
		sbSql.append("\n                                , TRVA.RACE_ESC_1_CNT                                                  ");
		sbSql.append("\n                                , TRVA.RACE_ESC_2_CNT                                                  ");
		sbSql.append("\n                                , TRVA.FOUL_ELIM_CNT                                                   ");
		sbSql.append("\n                                , TRVA.ELIM_CNT                                                        ");
		sbSql.append("\n                                , TRVA.FOUL_WARN_CNT                                                   ");
		sbSql.append("\n                                , TRVA.WARN_CNT                                                        ");
		sbSql.append("\n                                , TRVA.ATTEN_CNT                                                       ");
		sbSql.append("\n                         FROM     (                                                                    ");
		sbSql.append("\n             			            SELECT                                                                   ");
		sbSql.append("\n                                             TRVA.STND_YEAR                                            ");
		sbSql.append("\n                                           , TRVA.MBR_CD                                               ");
		sbSql.append("\n                                           , TRVA.TMS                                                  ");
		sbSql.append("\n                                           , TRVA.DAY_ORD                                              ");
		sbSql.append("\n                                           , TRVA.RACE_NO                                              ");
		sbSql.append("\n                                           , TRVA.RACE_REG_NO                                          ");
		sbSql.append("\n                                           , SUM(ACDNT_SCR) ACDNT_SCR                                  ");
		sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '110', 1, NULL)) F_CNT           ");
		sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '120', 1, NULL)) L_CNT           ");
		sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '130', 1, NULL)) ABSE_CNT        ");
		sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '995', 1, NULL)) RACE_ESC_1_CNT  ");
		sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '997', 1, NULL)) RACE_ESC_2_CNT  ");
		sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '125', 1, NULL)) FOUL_ELIM_CNT   ");
		sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '140', 1, NULL)) ELIM_CNT        ");
		sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '150', 1, NULL)) FOUL_WARN_CNT   ");
		sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '210', 1, NULL)) WARN_CNT        ");
		sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '220', 1, NULL)) ATTEN_CNT       ");
		sbSql.append("\n             			            FROM     TBED_RACE_VOIL_ACT TRVA                                         ");
		sbSql.append("\n             			                   , TBEB_ACDNT_SCR     TAS                                          ");
		sbSql.append("\n                                           , TBEB_ORGAN         TOR                                    ");
		sbSql.append("\n             			            WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR                                 ");
		sbSql.append("\n             			            AND    TRVA.VOIL_CD      = TAS.VOIL_CD                                   ");
		sbSql.append("\n                                    AND    TRVA.STND_YEAR    = TOR.STND_YEAR                           ");
		sbSql.append("\n                                    AND    TRVA.MBR_CD       = TOR.MBR_CD                              ");
		sbSql.append("\n                                    AND    TRVA.TMS          = TOR.TMS                                 ");
		sbSql.append("\n                                    AND    TRVA.DAY_ORD      = TOR.DAY_ORD                             ");
		sbSql.append("\n                                    AND    TRVA.RACE_NO      = TOR.RACE_NO                             ");
		sbSql.append("\n                                    AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO                         ");
		sbSql.append("\n                                    AND    TOR.ABSE_CD      <> '003'                                   ");
		sbSql.append("\n                                    AND    TOR.IMMT_CLS_CD  <> '003'                                   ");
		sbSql.append("\n             			            AND    TRVA.STND_YEAR    = ?                                             ");
		sbSql.append("\n             			            AND    TRVA.RACE_DAY    <= (                                             ");
		sbSql.append("\n             			                                            SELECT                                   ");
		sbSql.append("\n             			                                                   MAX(TRDO.RACE_DAY) RACE_DAY       ");
		sbSql.append("\n             			                                            FROM   TBEB_RACE_DAY_ORD TRDO            ");
		sbSql.append("\n             			                                            WHERE  TRDO.STND_YEAR = ?                ");
		sbSql.append("\n             			                                            AND    TRDO.MBR_CD    = ?                ");
		sbSql.append("\n             			                                            AND    TRDO.TMS       = ?                ");
		sbSql.append("\n             			                                       )                                             ");
		sbSql.append("\n             			            GROUP BY                                                                 ");
		sbSql.append("\n             			                     TRVA.STND_YEAR                                                  ");
		sbSql.append("\n             			                   , TRVA.MBR_CD                                                     ");
		sbSql.append("\n             			                   , TRVA.TMS                                                        ");
		sbSql.append("\n             			                   , TRVA.DAY_ORD                                                    ");
		sbSql.append("\n             			                   , TRVA.RACE_NO                                                    ");
		sbSql.append("\n             			                   , TRVA.RACE_REG_NO                                                ");
		sbSql.append("\n 			                     ) TRVA                                                                      ");
		sbSql.append("\n 			                   , (                                                                           ");
		sbSql.append("\n             			            SELECT                                                                   ");
		sbSql.append("\n             			                     TRVA.STND_YEAR                                                  ");
		sbSql.append("\n             			                   , TRVA.MBR_CD                                                     ");
		sbSql.append("\n             			                   , TRVA.TMS                                                        ");
		sbSql.append("\n             			                   , TRVA.DAY_ORD                                                    ");
		sbSql.append("\n             			                   , TRVA.RACE_NO                                                    ");
		sbSql.append("\n             			                   , TRVA.RACE_REG_NO                                                ");
		sbSql.append("\n             			                   , TAS .ACDNT_SCR                                                  ");
		sbSql.append("\n             			            FROM     TBED_RACE_VOIL_ACT TRVA                                         ");
		sbSql.append("\n             			                   , TBEB_ACDNT_SCR     TAS                                          ");
		sbSql.append("\n                                           , TBEB_ORGAN         TOR                                    ");
		sbSql.append("\n             			            WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR                                 ");
		sbSql.append("\n             			            AND    TRVA.VOIL_CD      = TAS.VOIL_CD                                   ");
		sbSql.append("\n                                    AND    TRVA.STND_YEAR    = TOR.STND_YEAR                           ");
		sbSql.append("\n                                    AND    TRVA.MBR_CD       = TOR.MBR_CD                              ");
		sbSql.append("\n                                    AND    TRVA.TMS          = TOR.TMS                                 ");
		sbSql.append("\n                                    AND    TRVA.DAY_ORD      = TOR.DAY_ORD                             ");
		sbSql.append("\n                                    AND    TRVA.RACE_NO      = TOR.RACE_NO                             ");
		sbSql.append("\n                                    AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO                         ");
		sbSql.append("\n                                    AND    TOR.ABSE_CD      <> '003'                                   ");
		sbSql.append("\n                                    AND    TOR.IMMT_CLS_CD  <> '003'                                   ");
		sbSql.append("\n             			            AND    TRVA.STND_YEAR    = ?                                             ");
		sbSql.append("\n             			            AND    TRVA.RACE_DAY    <= (                                             ");
		sbSql.append("\n             			                                            SELECT                                   ");
		sbSql.append("\n             			                                                   MAX(TRDO.RACE_DAY) RACE_DAY       ");
		sbSql.append("\n             			                                            FROM   TBEB_RACE_DAY_ORD TRDO            ");
		sbSql.append("\n             			                                            WHERE  TRDO.STND_YEAR = ?                ");
		sbSql.append("\n             			                                            AND    TRDO.MBR_CD    = ?                ");
		sbSql.append("\n             			                                            AND    TRDO.TMS       = ?                ");
		sbSql.append("\n             			                                       )                                             ");
		sbSql.append("\n             			            AND    TRVA.VOIL_CD     IN ('110', '120')                                ");
		sbSql.append("\n                                  ) TRVB                                                               ");
		sbSql.append("\n                        WHERE  TRVA.STND_YEAR   = TRVB.STND_YEAR  (+)                                  ");
		sbSql.append("\n 			            AND    TRVA.MBR_CD      = TRVB.MBR_CD     (+)                                        ");
		sbSql.append("\n 			            AND    TRVA.TMS         = TRVB.TMS        (+)                                        ");
		sbSql.append("\n 			            AND    TRVA.DAY_ORD     = TRVB.DAY_ORD    (+)                                        ");
		sbSql.append("\n 			            AND    TRVA.RACE_NO     = TRVB.RACE_NO    (+)                                        ");
		sbSql.append("\n 			            AND    TRVA.RACE_REG_NO = TRVB.RACE_REG_NO(+)                                        ");
		sbSql.append("\n                      ) TRVA                                                                           ");
		sbSql.append("\n                    , (                                                                                ");
		sbSql.append("\n                         SELECT                                                                        ");
		sbSql.append("\n                                  TOR.RACER_NO                                                         ");
		sbSql.append("\n                                , ROUND(NVL(SUM(DECODE(TOR.ST_MTHD_CD, '001', 0, TOR.STAR_TM))/NVL(SUM(DECODE(TOR.ST_MTHD_CD, '001', 0, 1)),1), 0),2) AVG_STAR_TM ");
		sbSql.append("\n                         FROM   TBEB_ORGAN        TOR                                                  ");
		sbSql.append("\n                         WHERE  TOR .STND_YEAR    = ?                                                  ");
		sbSql.append("\n                         AND    TOR .RACE_DAY    <= (                                                  ");
		sbSql.append("\n                                                         SELECT                                        ");
		sbSql.append("\n                                                                MAX(TRDO.RACE_DAY) RACE_DAY            ");
		sbSql.append("\n                                                         FROM   TBEB_RACE_DAY_ORD TRDO                 ");
		sbSql.append("\n                                                         WHERE  TRDO.STND_YEAR = ?                     ");
		sbSql.append("\n                                                         AND    TRDO.MBR_CD    = ?                     ");
		sbSql.append("\n                                                         AND    TRDO.TMS       = ?                     ");
		sbSql.append("\n                                                    )                                                  ");
		sbSql.append("\n                         GROUP BY TOR.RACER_NO                                                         ");
		sbSql.append("\n                      ) STTM                                                                           ");
		sbSql.append("\n             WHERE  TOR.STND_YEAR    = TR.STND_YEAR                                                    ");
		sbSql.append("\n             AND    TOR.MBR_CD       = TR.MBR_CD                                                       ");
		sbSql.append("\n             AND    TOR.TMS          = TR.TMS                                                          ");
		sbSql.append("\n             AND    TOR.DAY_ORD      = TR.DAY_ORD                                                      ");
		sbSql.append("\n             AND    TOR.RACE_NO      = TR.RACE_NO                                                      ");
		sbSql.append("\n             AND    TOR.STND_YEAR    = TRT.STND_YEAR                                                   ");
		sbSql.append("\n             AND    TOR.MBR_CD       = TRT.MBR_CD                                                      ");
		sbSql.append("\n             AND    TOR.TMS          = TRT.TMS                                                         ");
		sbSql.append("\n             AND    TOR.STND_YEAR    = TRS.STND_YEAR                                                   ");
		sbSql.append("\n             AND    TR.RACE_DGRE_CD = TRS.RACE_DGRE_CD                                                 ");
		sbSql.append("\n             AND    NVL(TOR.RANK,0)  = TRS.RANK                                                        ");
		sbSql.append("\n             AND    TOR.STND_YEAR    = TRVA.STND_YEAR  (+)                                             ");
		sbSql.append("\n             AND    TOR.MBR_CD       = TRVA.MBR_CD     (+)                                             ");
		sbSql.append("\n             AND    TOR.TMS          = TRVA.TMS        (+)                                             ");
		sbSql.append("\n             AND    TOR.DAY_ORD      = TRVA.DAY_ORD    (+)                                             ");
		sbSql.append("\n             AND    TOR.RACE_NO      = TRVA.RACE_NO    (+)                                             ");
		sbSql.append("\n             AND    TOR.RACE_REG_NO  = TRVA.RACE_REG_NO(+)                                             ");
		sbSql.append("\n             AND    TOR.RACER_NO     = STTM.RACER_NO   (+)                                             ");
		sbSql.append("\n             AND    TOR.STND_YEAR    = TRST.STND_YEAR                                                  ");
		sbSql.append("\n             AND    TRT.STR_DT BETWEEN TRST.STR_DT                                                     ");
		sbSql.append("\n                                    AND TRST.END_DT                                                    ");
		sbSql.append("\n             AND    TOR.ABSE_CD     <> '003'   -- 면책결장이 아닌 경우                                 ");
		sbSql.append("\n             AND    TOR.IMMT_CLS_CD <> '003'   -- 면책이 아닌 경우                                     ");
		sbSql.append("\n             AND    TOR.STND_YEAR    = ?                                                               ");
		sbSql.append("\n             AND    TOR.RACE_DAY    <= (                                                               ");
		sbSql.append("\n                                             SELECT                                                    ");
		sbSql.append("\n                                                    MAX(TRDO.RACE_DAY) RACE_DAY                        ");
		sbSql.append("\n                                             FROM   TBEB_RACE_DAY_ORD TRDO                             ");
		sbSql.append("\n                                             WHERE  TRDO.STND_YEAR = ?                                 ");
		sbSql.append("\n                                             AND    TRDO.MBR_CD    = ?                                 ");
		sbSql.append("\n                                             AND    TRDO.TMS       = ?                                 ");
		sbSql.append("\n                                        )                                                              ");
		sbSql.append("\n             GROUP BY TOR.RACER_NO                                                                     ");
		sbSql.append("\n                    , STTM.AVG_STAR_TM                                                                 ");
		sbSql.append("\n           ) YEAR                                                                                      ");
		sbSql.append("\n         , (                                                                                           ");
		sbSql.append("\n             -- 전회차 경정장                                                                          ");
		sbSql.append("\n             SELECT                                                                                    ");
		sbSql.append("\n                      RACER_NO                                                                         ");
		sbSql.append("\n                    , MIN(DECODE(TMS_3, 1, TMS   )) BF_TMS                                             ");
		sbSql.append("\n                    , MIN(DECODE(TMS_3, 2, TMS   )) BF_BF_TMS                                          ");
		sbSql.append("\n                    , MIN(DECODE(TMS_3, 3, TMS   )) BF_BF_BF_TMS                                       ");
		sbSql.append("\n                    , MIN(DECODE(TMS_3, 1, MBR_CD)) BF_MBR_CD                                          ");
		sbSql.append("\n                    , MIN(DECODE(TMS_3, 2, MBR_CD)) BF_BF_MBR_CD                                       ");
		sbSql.append("\n                    , MIN(DECODE(TMS_3, 3, MBR_CD)) BF_BF_BF_MBR_CD                                    ");
		sbSql.append("\n             FROM   (                                                                                  ");
		sbSql.append("\n                         SELECT                                                                        ");
		sbSql.append("\n                                  TOR.STND_YEAR                                                        ");
		sbSql.append("\n                                , TOR.MBR_CD                                                           ");
		sbSql.append("\n                                , TOR.TMS                                                              ");
		sbSql.append("\n                                , TOR.RACER_NO                                                         ");
		sbSql.append("\n                                , RANK() OVER (PARTITION BY TOR.RACER_NO                               ");
		sbSql.append("\n                                                   ORDER BY TOR.RACE_DAY DESC ) TMS_3                  ");
		sbSql.append("\n                         FROM   (                                                                      ");
		sbSql.append("\n                                     SELECT                                                            ");
		sbSql.append("\n                                              TOR.STND_YEAR                                            ");
		sbSql.append("\n                                            , TOR.MBR_CD                                               ");
		sbSql.append("\n                                            , TOR.TMS                                                  ");
		sbSql.append("\n                                            , TOR.RACER_NO                                             ");
		sbSql.append("\n                                            , MAX(TOR.RACE_DAY) RACE_DAY                               ");
		sbSql.append("\n                                     FROM   TBEB_ORGAN        TOR                                      ");
		sbSql.append("\n                                     WHERE  TOR.RACE_DAY     <= (                                      ");
		sbSql.append("\n                                                                     SELECT                            ");
		sbSql.append("\n                                                                            MAX(TRDO.RACE_DAY) RACE_DAY");
		sbSql.append("\n                                                                     FROM   TBEB_RACE_DAY_ORD TRDO     ");
		sbSql.append("\n                                                                     WHERE  TRDO.STND_YEAR = ?         ");
		sbSql.append("\n                                                                     AND    TRDO.MBR_CD    = ?         ");
		sbSql.append("\n                                                                     AND    TRDO.TMS       = ?         ");
		sbSql.append("\n                                                                )                                      ");
		sbSql.append("\n                                     AND    TOR .ABSE_CD     <> '003'   -- 면책결장이 아닌 경우        ");
		sbSql.append("\n                                     AND    TOR .IMMT_CLS_CD <> '003'   -- 면책이 아닌 경우            ");
		sbSql.append("\n                                     GROUP BY                                                          ");
		sbSql.append("\n                                              TOR.STND_YEAR                                            ");
		sbSql.append("\n                                            , TOR.MBR_CD                                               ");
		sbSql.append("\n                                            , TOR.TMS                                                  ");
		sbSql.append("\n                                            , TOR.RACER_NO                                             ");
		sbSql.append("\n                                ) TOR                                                                  ");
		sbSql.append("\n                    )                                                                                  ");
		sbSql.append("\n             GROUP BY RACER_NO                                                                         ");
		sbSql.append("\n           ) TMS_3                                                                                     ");
		sbSql.append("\n         , (                                                                                           ");
		sbSql.append("\n             --  전체 성적                                                                             ");
		sbSql.append("\n             SELECT                                                                                    ");
		sbSql.append("\n                      RACER_NO                                                                         ");
		sbSql.append("\n                    , COUNT(*) TOT_RACE_CNT                                                            ");
		sbSql.append("\n             FROM     TBEB_ORGAN         TOR                                                           ");
		sbSql.append("\n             WHERE  TOR.RACE_DAY     <= (                                                              ");
		sbSql.append("\n                                             SELECT                                                    ");
		sbSql.append("\n                                                    MAX(TRDO.RACE_DAY) RACE_DAY                        ");
		sbSql.append("\n                                             FROM   TBEB_RACE_DAY_ORD TRDO                             ");
		sbSql.append("\n                                             WHERE  TRDO.STND_YEAR = ?                                 ");
		sbSql.append("\n                                             AND    TRDO.MBR_CD    = ?                                 ");
		sbSql.append("\n                                             AND    TRDO.TMS       = ?                                 ");
		sbSql.append("\n                                        )                                                              ");
		sbSql.append("\n             AND    TOR .STND_YEAR    = ?                                                              ");
		sbSql.append("\n             AND    TOR .ABSE_CD     <> '003'   -- 면책이 아닌 경우                                    ");
		sbSql.append("\n             GROUP BY RACER_NO                                                                         ");
		sbSql.append("\n           ) TOT                                                                                       ");
		sbSql.append("\n        , (  -- GPP 산출(연도별)                                                                       ");
		sbSql.append("\n             SELECT   TOR.RACER_NO                                                                     ");
		sbSql.append("\n                    , SUM(TGP.GPP_SCR) GPP_SCR                                                         ");
		sbSql.append("\n               FROM TBEB_ORGAN TOR, TBEB_RACE TR, TBEB_GPP_SCR TGP                                     ");
		sbSql.append("\n              WHERE TOR.STND_YEAR = TO_CHAR(SYSDATE,'YYYY')                                            ");
		sbSql.append("\n                    AND TOR.STND_YEAR = TR.STND_YEAR                                                   ");
		sbSql.append("\n                    AND TOR.TMS = TR.TMS                                                               ");
		sbSql.append("\n                    AND TOR.DAY_ORD = TR.DAY_ORD                                                       ");
		sbSql.append("\n                    AND TOR.RACE_NO = TR.RACE_NO                                                       ");
		sbSql.append("\n                    AND TOR.STND_YEAR = TGP.STND_YEAR                                                  ");
		sbSql.append("\n                    AND TOR.DAY_ORD = TGP.DAY_ORD                                                      ");
		sbSql.append("\n                    AND TOR.RANK = TGP.RANK                                                            ");
		sbSql.append("\n                    AND TGP.RACE_DGRE_CD =                                                             ");
		sbSql.append("\n						  (CASE  WHEN TR.RACE_DGRE_CD IN ('706', '707', '708', '933', '951', '953', '954') THEN '951'	    ");
		sbSql.append("\n                				 WHEN TR.RACE_DGRE_CD IN ('901', '903', '906', '952')                   THEN '952'    ");
		sbSql.append("\n                				 ELSE  TR.RACE_DGRE_CD END)																									          ");
		sbSql.append("\n                    AND (TOR.RANK > 0 OR (TOR.RANK = 0 AND TOR.IMMT_CLS_CD <> '002' AND TOR.ABSE_CD <> '001'))");
		sbSql.append("\n                    AND TOR.RACE_DAY    <= (                                                                  ");
		sbSql.append("\n                                     SELECT MAX(TRDO.RACE_DAY) RACE_DAY                                       ");
		sbSql.append("\n                                     FROM   TBEB_RACE_DAY_ORD TRDO                                            ");
		sbSql.append("\n                                     WHERE  TRDO.STND_YEAR =  ?                                               ");
		sbSql.append("\n                                     AND    TRDO.MBR_CD    =  ?                                               ");
		sbSql.append("\n                                     AND    TRDO.TMS       =  ?                                               ");
		sbSql.append("\n                        )                                                                                     ");
		sbSql.append("\n               GROUP BY TOR.RACER_NO                                                                          ");
		sbSql.append("\n          ) STGP                                                                                              ");
		sbSql.append("\n WHERE   TRM.RACER_NO = TMS_6.RACER_NO(+) ");
		sbSql.append("\n AND     TRM.RACER_NO = TMS_8.RACER_NO(+) ");
		sbSql.append("\n AND     TRM.RACER_NO = YEAR .RACER_NO(+) ");
		sbSql.append("\n AND     TRM.RACER_NO = TMS_3.RACER_NO(+) ");
		sbSql.append("\n AND     TRM.RACER_NO = TOT  .RACER_NO(+) ");
		sbSql.append("\n AND     TRM.RACER_NO = TRD  .RACER_NO(+) ");
		sbSql.append("\n AND     TRM.RACER_NO = STGP .RACER_NO(+) ");
        	
    	
        int nSize = 0;
        if ( ds != null ) 
        	nSize = ds.getRecordCount();

        if ( nSize > 0 )
        	sbSql.append("\n AND     TRM.RACER_NO IN (");
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);

            if ( i == 0 ) {
            	sbSql.append(      "'" + record.getAttribute("RACER_NO") + "' \n");
            } else { 
            	sbSql.append("," + "'" + record.getAttribute("RACER_NO") + "' \n");
            }
        }
        
        if ( nSize > 0 )
        	sbSql.append(")");
    	sbSql.append("\n ORDER BY TRM.RACER_NO                                                                                                                                               ");
        
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, "000"    );  
        param.setWhereClauseParameter(i++, "0"      );  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, SESSION_USER_ID );
        param.setWhereClauseParameter(i++, SESSION_USER_ID );
        
        // 6회차
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, sStndYear);  
        
        // 8경주
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        
        // 년도별성적
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     ); 
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );
        
        // 전회차
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        
        // 년도별  출전수
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, sStndYear);  

        // 년도별  GPP 산출
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  

        PosRowSet rowSet =   this.getDao("boadao").findByQueryStatement(sbSql.toString(), param);
        return rowSet;
    }
    
    /**
     * <p> 회차별 선수 성적조회(온라인, 플라잉) </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected PosRowSet searchRacerRecdAccuSumFlyOnl(PosDataset ds, String sStMthdCd)
    {
    	StringBuffer sbSql = new StringBuffer();
    	
		sbSql.append("\n SELECT  /* SearchRecdCreate : searchRacerRecdAccuSumFlyOnl */                                                                                                                                         	");
		sbSql.append("\n         /*+ ORDERED */          																																																																																	");   //속도 향상을 위한 오라클 힌트                                                                                                                            ");
		sbSql.append("\n           '1' CHK 																																																																																								");
		sbSql.append("\n	       , '1' ENABLE 																																																																																						");
		sbSql.append("\n         , ?                                                                                                                                STND_YEAR                       -- 기준년도           ");
		sbSql.append("\n         , ?                                                                                                                                MBR_CD                          -- 경정장코드         ");
		sbSql.append("\n         , ?                                                                                                                                TMS                             -- 회차               ");
		sbSql.append("\n         , TRM.RACER_NO                                                                                                                     RACER_NO                        -- 선수등록번호       ");
		sbSql.append("\n         , TRM.NM_KOR                                                                                                                                         																		");
		sbSql.append("\n         , TRM.RACER_GRD_CD                                                                                                                                        																");
		sbSql.append("\n         , TO_CHAR(SYSDATE, 'YYYY') - SUBSTR(TRD  .BIRTH_DT, 1, 4) AGE                                           																																									");
		sbSql.append("\n         , TRM.RACER_NO                                                                                                                     RACER_NO                        -- 선수등록번호       ");
		sbSql.append("\n         , (SELECT MAX(TRDO.RACE_DAY) RACE_DAY FROM TBEB_RACE_DAY_ORD TRDO WHERE TRDO.STND_YEAR = ? AND TRDO.MBR_CD = ? AND TRDO.TMS = ?)   APLY_DT                         -- 적용일자           ");
		sbSql.append("\n         , TMS_6_AMU_RANK_SCR                                                                                                               TMS_6_AMU_RANK_SCR              -- 6회누적착순점      ");
		sbSql.append("\n         , TRIM(TO_CHAR(TMS_6_AMU_RANK_SCR / TMS_6_RACE_CNT, 90.99))                                                                        TMS_6_AVG_RANK_SCR              -- 6회평균착순점      ");
		sbSql.append("\n         , TMS_6_AMU_ACDNT_SCR                                                                                                              TMS_6_AMU_ACDNT_SCR             -- 6회누적사고점      ");
		sbSql.append("\n         , TRIM(TO_CHAR(TMS_6_AMU_ACDNT_SCR / TMS_6_RACE_CNT, 90.99))                                                                       TMS_6_AVG_ACDNT_SCR             -- 6회평균착순점      ");
		sbSql.append("\n         , TRIM(TO_CHAR((TMS_6_AMU_RANK_SCR - NVL(TMS_6_AMU_ACDNT_SCR, 0)) / TMS_6_RACE_CNT, 90.99))                                        TMS_6_AVG_SCR                   -- 6회평균득점        ");
		sbSql.append("\n         , TRIM(TO_CHAR((TMS_6_RANK_1) / TMS_6_RACE_CNT * 100, 990.9))                                                                      TMS_6_WIN_RATIO                 -- 6회승률            ");
		sbSql.append("\n         , TRIM(TO_CHAR((TMS_6_RANK_1 + TMS_6_RANK_2) / TMS_6_RACE_CNT * 100, 990.9))                                                       TMS_6_HIGH_RANK_RATIO           -- 6회연대율          ");
		sbSql.append("\n         , TRIM(TO_CHAR((TMS_6_RANK_1 + TMS_6_RANK_2 + TMS_6_RANK_3) / TMS_6_RACE_CNT * 100, 990.9))                                        TMS_6_HIGH_3_RANK_RATIO         -- 6회3연대율         ");
		sbSql.append("\n         , TMS_6_AVG_STAR_TM                                                                                                                TMS_6_AVG_STRT_TM               -- 6회평균ST          ");
		sbSql.append("\n         , TMS_8_RANK_1 || TMS_8_RANK_2 || TMS_8_RANK_3 || TMS_8_RANK_4 || TMS_8_RANK_5 || TMS_8_RANK_6 || TMS_8_RANK_7 || TMS_8_RANK_8     TMS_8_RANK_ORD                  -- 8경주 착순         ");
		sbSql.append("\n         , TMS_6_RACE_CNT                                                                                                                   TMS_6_RACE_CNT                  -- 6회출주횟수        ");
		sbSql.append("\n         , TMS_6_RANK_1                                                                                                                     TMS_6_RANK_1                    -- 6회1착횟수         ");
		sbSql.append("\n         , TMS_6_RANK_2                                                                                                                     TMS_6_RANK_2                    -- 6회2착횟수         ");
		sbSql.append("\n         , TMS_6_RANK_3                                                                                                                     TMS_6_RANK_3                    -- 6회3착횟수         ");
		sbSql.append("\n         , AMU_RANK_SCR                                                                                                                     AMU_RANK_SCR                    -- 누적착순점         ");
		sbSql.append("\n         , NVL(STGP.GPP_SCR, 0)                                                                                                             GPP_SCR                         -- GPP(그랑프리포인트)");
		sbSql.append("\n         , TRIM(TO_CHAR(AMU_RANK_SCR / RACE_CNT, 90.99))                                                                                    AVG_RANK_SCR                    -- 평균착순점         ");
		sbSql.append("\n         , AMU_ACDNT_SCR                                                                                                                    AMU_ACDNT_SCR                   -- 누적사고점         ");
		sbSql.append("\n         , TRIM(TO_CHAR(AMU_ACDNT_SCR / RACE_CNT, 90.99))                                                                                   AVG_ACDNT_SCR                   -- 평균사고점         ");
		sbSql.append("\n         , BF_ACDNT_SCR                                                                                                                     BF_AMU_ACDNT_SCR                -- 전반기사고점       ");
		sbSql.append("\n         , TRIM(TO_CHAR(BF_ACDNT_SCR / BF_RACE_CNT, 90.99))                                                                                 BF_AVG_ACDNT_SCR                  -- 평균사고점       ");
		sbSql.append("\n         , AF_ACDNT_SCR                                                                                                                     AF_AMU_ACDNT_SCR                -- 후반기사고점       ");
		sbSql.append("\n         , TRIM(TO_CHAR(AF_ACDNT_SCR / AF_RACE_CNT, 90.99))                                                                                 AF_AVG_ACDNT_SCR                  -- 평균사고점       ");
		sbSql.append("\n         , TRIM(TO_CHAR((AMU_RANK_SCR - AMU_ACDNT_SCR) / RACE_CNT, 90.99))                                                                  AVG_SCR                         -- 누적득점           ");
		sbSql.append("\n         , TRIM(TO_CHAR((RANK_1) / RACE_CNT * 100, 990.9))                                                                                  WIN_RATIO                       -- 승률               ");
		sbSql.append("\n         , TRIM(TO_CHAR((RANK_1 + RANK_2) / RACE_CNT * 100, 990.9))                                                                         HIGH_RANK_RATIO                 -- 연대율             ");
		sbSql.append("\n         , TRIM(TO_CHAR((RANK_1 + RANK_2 + RANK_3) / RACE_CNT * 100, 990.9))                                                                HIGH_3_RANK_RATIO               -- 3연대율            ");
		sbSql.append("\n         , AVG_STAR_TM                                                                                                                      AVG_STRT_TM                     -- 평균ST             ");
		sbSql.append("\n         , RUN_CNT                                                                                                                          RUN_CNT                         -- 출전횟수           ");
		sbSql.append("\n         , RACE_DAY_CNT                                                                                                                     RACE_DAY_CNT                    -- 출전일수           ");
		sbSql.append("\n         , RACE_CNT                                                                                                                         RACE_CNT                        -- 출주횟수           ");
		sbSql.append("\n         , TOT_RACE_CNT                                                                                                                     TOT_RACE_CNT                    -- 총출주횟수         ");
		sbSql.append("\n         , BF_RACE_CNT                                                                                                                      BF_RACE_CNT                     -- 전반기출주횟수     ");
		sbSql.append("\n         , AF_RACE_CNT                                                                                                                      AF_RACE_CNT                     -- 후반기출주횟수     ");
		sbSql.append("\n         , RANK_1                                                                                                                           RANK_1_CNT                      -- 1착횟수            ");
		sbSql.append("\n         , RANK_2                                                                                                                           RANK_2_CNT                      -- 2착횟수            ");
		sbSql.append("\n         , RANK_3                                                                                                                           RANK_3_CNT                      -- 3착횟수            ");
		sbSql.append("\n         , RANK_4                                                                                                                           RANK_4_CNT                      -- 4착횟수            ");
		sbSql.append("\n         , RANK_5                                                                                                                           RANK_5_CNT                      -- 5착횟수            ");
		sbSql.append("\n         , RANK_6                                                                                                                           RANK_6_CNT                      -- 6착횟수            ");
		sbSql.append("\n         , RACE_CNT - (RANK_1 + RANK_2 + RANK_3 + RANK_4 + RANK_5 + RANK_6)                                                                 RANK_ETC_CNT                    -- 착외횟수           ");
		sbSql.append("\n         , 0                                                                                                                                DGRE_JUDG_F_CNT                 -- 등급심사 F횟수     ");
		sbSql.append("\n         , 0                                                                                                                                DGRE_JUDG_L_CNT                 -- 등습심사 L횟수     ");
		sbSql.append("\n         , BF_F_CNT                                                                                                                         BF_F_CNT                        -- 전반기F횟수        ");
		sbSql.append("\n         , AF_F_CNT                                                                                                                         AF_F_CNT                        -- 후반기F횟수        ");
		sbSql.append("\n         , BF_L_CNT                                                                                                                         BF_L_CNT                        -- 전반기L횟수        ");
		sbSql.append("\n         , AF_L_CNT                                                                                                                         AF_L_CNT                        -- 후반기L횟수        ");
		sbSql.append("\n         , ABSE_CNT                                                                                                                         ABSE_CNT                        -- 결장횟수           ");
		sbSql.append("\n         , RACE_ESC_1_CNT                                                                                                                   RACE_ESC_1_CNT                  -- 출주제외1횟수      ");
		sbSql.append("\n         , RACE_ESC_2_CNT                                                                                                                   RACE_ESC_2_CNT                  -- 출주제외2횟수      ");
		sbSql.append("\n         , FOUL_ELIM_CNT                                                                                                                    FOUL_ELIM_CNT                   -- 반칙실격횟수       ");
		sbSql.append("\n         , ELIM_CNT                                                                                                                         ELIM_CNT                        -- 실격횟수           ");
		sbSql.append("\n         , FOUL_WARN_CNT                                                                                                                    FOUL_WARN_CNT                   -- 반칙경고횟수       ");
		sbSql.append("\n         , WARN_CNT                                                                                                                         WARN_CNT                        -- 경고횟수           ");
		sbSql.append("\n         , ATTEN_CNT                                                                                                                        ATTEN_CNT                       -- 주의횟수           ");
		sbSql.append("\n         , BF_TMS                                                                                                                           BF_TMS                          -- 전회차             ");
		sbSql.append("\n         , BF_BF_TMS                                                                                                                        BF_BF_TMS                       -- 전전회차           ");
		sbSql.append("\n         , BF_BF_BF_TMS                                                                                                                     BF_BF_BF_TMS                    -- 전전전회차         ");
		sbSql.append("\n         , BF_MBR_CD                                                                                                                        BF_MBR_CD                       -- 전경정장           ");
		sbSql.append("\n         , BF_BF_MBR_CD                                                                                                                     BF_BF_MBR_CD                    -- 전전경정장         ");
		sbSql.append("\n         , BF_BF_BF_MBR_CD                                                                                                                  BF_BF_BF_MBR_CD                 -- 전전전경정장       ");
		sbSql.append("\n         , ?                                                                                                                                INST_ID                         -- 작성자ID           ");
		sbSql.append("\n         , SYSDATE                                                                                                                          INST_DTM                        -- 작성일시           ");
		sbSql.append("\n         , ?                                                                                                                                UPDT_ID                         -- 수정자ID           ");
		sbSql.append("\n         , SYSDATE                                                                                                                          UPDT_DTM                        -- 수정일시           ");
		sbSql.append("\n FROM      TBEC_RACER_MASTER TRM                                                                                                                                 ");
		sbSql.append("\n         , TBEC_RACER_DETAIL TRD                                                                                                                                 ");
		sbSql.append("\n         , (                                                                                                                                                     ");
		sbSql.append("\n             -- 6회차 성적                                                                                                                                       ");
		sbSql.append("\n             SELECT                                                                                                                                              ");
		sbSql.append("\n                      TOR.RACER_NO                                                                                                                               ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 1, 1, 0)), 0)   TMS_6_RANK_1                                                                                      ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 2, 1, 0)), 0)   TMS_6_RANK_2                                                                                      ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 3, 1, 0)), 0)   TMS_6_RANK_3                                                                                      ");
		sbSql.append("\n                    , ROUND(STTM.TMS_6_AVG_STAR_TM, 2) TMS_6_AVG_STAR_TM                                                                                         ");
		sbSql.append("\n                    , NVL(SUM(TRS.RACE_SCR)             , 0)   TMS_6_AMU_RANK_SCR                                                                                ");
		sbSql.append("\n                    , NVL(SUM(TRVA.ACDNT_SCR)           , 0)   TMS_6_AMU_ACDNT_SCR                                                                               ");
		sbSql.append("\n                    , COUNT(*)                                 TMS_6_RACE_CNT                                                                                    ");
		sbSql.append("\n             FROM     (                                                                                                                                          ");
		sbSql.append("\n                         SELECT                                                                                                                                  ");
		sbSql.append("\n                                TOR.*                                                                                                                            ");
		sbSql.append("\n                         FROM   TBEB_ORGAN        TOR                                                                                                            ");
		sbSql.append("\n                         WHERE  (TOR.STND_YEAR, TOR.MBR_CD, TOR.TMS, TOR.RACER_NO) IN (                                                                          ");
		sbSql.append("\n                                                                                         SELECT                                                                  ");
		sbSql.append("\n                                                                                                  TOR.STND_YEAR                                                  ");
		sbSql.append("\n                                                                                                , TOR.MBR_CD                                                     ");
		sbSql.append("\n                                                                                                , TOR.TMS                                                        ");
		sbSql.append("\n                                                                                                , TOR.RACER_NO                                                   ");
		sbSql.append("\n                                                                                         FROM   (                                                                ");
		sbSql.append("\n                                                                                                     SELECT                                                      ");
		sbSql.append("\n                                                                                                              TOR.STND_YEAR                                      ");
		sbSql.append("\n                                                                                                            , TOR.MBR_CD                                         ");
		sbSql.append("\n                                                                                                            , TOR.TMS                                            ");
		sbSql.append("\n                                                                                                            , TOR.RACER_NO                                       ");
		sbSql.append("\n                                                                                                            , RANK() OVER (PARTITION BY TOR.RACER_NO             ");
		sbSql.append("\n                                                                                                                               ORDER BY TOR.RACE_DAY DESC ) TMS_6");
		sbSql.append("\n                                                                                                     FROM   (                                                    ");
		sbSql.append("\n                                                                                                                 SELECT                                          ");
		sbSql.append("\n                                                                                                                          TOR.STND_YEAR                          ");
		sbSql.append("\n                                                                                                                        , TOR.MBR_CD                             ");
		sbSql.append("\n                                                                                                                        , TOR.TMS                                ");
		sbSql.append("\n                                                                                                                        , TOR.RACER_NO                           ");
		sbSql.append("\n                                                                                                                        , MAX(TOR.RACE_DAY) RACE_DAY             ");
		sbSql.append("\n                                                                                                                 FROM   TBEB_ORGAN        TOR                    ");
		sbSql.append("\n                                                                                                                 WHERE  TOR .RACE_DAY    <= (                    ");
		sbSql.append("\n                                                                                                                                                 SELECT          ");
		sbSql.append("\n                                                                                                                                                        MAX(TRDO.RACE_DAY) RACE_DAY");
		sbSql.append("\n                                                                                                                                                 FROM   TBEB_RACE_DAY_ORD TRDO     ");
		sbSql.append("\n                                                                                                                                                 WHERE  TRDO.STND_YEAR = ?         ");
		sbSql.append("\n                                                                                                                                                 AND    TRDO.MBR_CD    = ?         ");
		sbSql.append("\n                                                                                                                                                 AND    TRDO.TMS       = ?         ");
		sbSql.append("\n                                                                                                                                            )                                      ");
		sbSql.append("\n                                                                                                                 AND    TOR.ABSE_CD     <> '003'   -- 면책결장이 아닌 경우         ");
		sbSql.append("\n                                                                                                                 AND    TOR.IMMT_CLS_CD <> '003'   -- 면책이 아닌 경우             ");
		sbSql.append("\n                                                                                                                 AND    TOR.STND_YEAR   >= ? - 1                                   ");
		sbSql.append("\n                                                                                                                 AND    TOR.ST_MTHD_CD   = '"+sStMthdCd+"'                         ");
		sbSql.append("\n                                                                                                                 GROUP BY                                                          ");
		sbSql.append("\n                                                                                                                          TOR.STND_YEAR                                            ");
		sbSql.append("\n                                                                                                                        , TOR.MBR_CD                                               ");
		sbSql.append("\n                                                                                                                        , TOR.TMS                                                  ");
		sbSql.append("\n                                                                                                                        , TOR.RACER_NO                                             ");
		sbSql.append("\n                                                                                                            ) TOR                                                                  ");
		sbSql.append("\n                                                                                                ) TOR                                                             ");
		sbSql.append("\n                                                                                         WHERE  TMS_6 <= 6                                                        ");
		sbSql.append("\n                                                                                      )                                                                           ");
		sbSql.append("\n                      ) TOR                                                                                                                                       ");
		sbSql.append("\n                    , (                                                                                                                                           ");
		sbSql.append("\n                         SELECT                                                                                                                                   ");
		sbSql.append("\n                                  TOR.RACER_NO                                                                                                                    ");
		sbSql.append("\n                                , NVL(AVG(TOR.STAR_TM)              , 0)   TMS_6_AVG_STAR_TM                                                                      ");
		sbSql.append("\n                         FROM   TBEB_ORGAN        TOR                                                                                                             ");
		sbSql.append("\n                         WHERE  (TOR.STND_YEAR, TOR.MBR_CD, TOR.TMS, TOR.RACER_NO) IN (                                                                           ");
		sbSql.append("\n                                                                                         SELECT                                                                   ");
		sbSql.append("\n                                                                                                  TOR.STND_YEAR                                                   ");
		sbSql.append("\n                                                                                                , TOR.MBR_CD                                                      ");
		sbSql.append("\n                                                                                                , TOR.TMS                                                         ");
		sbSql.append("\n                                                                                                , TOR.RACER_NO                                                    ");
		sbSql.append("\n                                                                                         FROM   (                                                                 ");
		sbSql.append("\n                                                                                                     SELECT                                                       ");
		sbSql.append("\n                                                                                                              TOR.STND_YEAR                                       ");
		sbSql.append("\n                                                                                                            , TOR.MBR_CD                                          ");
		sbSql.append("\n                                                                                                            , TOR.TMS                                             ");
		sbSql.append("\n                                                                                                            , TOR.RACER_NO                                        ");
		sbSql.append("\n                                                                                                            , RANK() OVER (PARTITION BY TOR.RACER_NO              ");
		sbSql.append("\n                                                                                                                               ORDER BY TOR.RACE_DAY DESC ) TMS_6 ");
		sbSql.append("\n                                                                                                     FROM   (                                                     ");
		sbSql.append("\n                                                                                                                 SELECT                                           ");
		sbSql.append("\n                                                                                                                          TOR.STND_YEAR                           ");
		sbSql.append("\n                                                                                                                        , TOR.MBR_CD                              ");
		sbSql.append("\n                                                                                                                        , TOR.TMS                                 ");
		sbSql.append("\n                                                                                                                        , TOR.RACER_NO                            ");
		sbSql.append("\n                                                                                                                        , MAX(TOR.RACE_DAY) RACE_DAY              ");
		sbSql.append("\n                                                                                                                 FROM   TBEB_ORGAN        TOR                     ");
		sbSql.append("\n                                                                                                                 WHERE  TOR .RACE_DAY    <= (                                       ");
		sbSql.append("\n                                                                                                                                                 SELECT                             ");
		sbSql.append("\n                                                                                                                                                        MAX(TRDO.RACE_DAY) RACE_DAY ");
		sbSql.append("\n                                                                                                                                                 FROM   TBEB_RACE_DAY_ORD TRDO      ");
		sbSql.append("\n                                                                                                                                                 WHERE  TRDO.STND_YEAR = ?          ");
		sbSql.append("\n                                                                                                                                                 AND    TRDO.MBR_CD    = ?          ");
		sbSql.append("\n                                                                                                                                                 AND    TRDO.TMS       = ?          ");
		sbSql.append("\n                                                                                                                                            )                                       ");
		sbSql.append("\n                                                                                                                 AND    TOR.ABSE_CD      = '001'   -- 결장이 아닌 경우              ");
		sbSql.append("\n                                                                                                                 AND    TOR.STND_YEAR   >= ? - 1                                    ");
		sbSql.append("\n                                                                                                                 AND    TOR.ST_MTHD_CD   = '"+sStMthdCd+"'                          ");
		sbSql.append("\n                                                                                                                 GROUP BY                                                           ");
		sbSql.append("\n                                                                                                                          TOR.STND_YEAR                                             ");
		sbSql.append("\n                                                                                                                        , TOR.MBR_CD                                                ");
		sbSql.append("\n                                                                                                                        , TOR.TMS                                                   ");
		sbSql.append("\n                                                                                                                        , TOR.RACER_NO                                              ");
		sbSql.append("\n                                                                                                            ) TOR                                                                   ");
		sbSql.append("\n                                                                                                ) TOR      ");
		sbSql.append("\n                                                                                         WHERE  TMS_6 <= 6 ");
		sbSql.append("\n                                                                                      )                    ");
		sbSql.append("\n                        AND    TOR.ST_MTHD_CD   = '"+sStMthdCd+"'                                          ");
		sbSql.append("\n                        GROUP BY TOR.RACER_NO                                                              ");
		sbSql.append("\n                      ) STTM                                                                               ");
		sbSql.append("\n                    , TBEB_RACE          TR                                                                ");
		sbSql.append("\n                    , TBEB_RANK_SCR      TRS                                                               ");
		sbSql.append("\n                    , (                                                                                    ");
		sbSql.append("\n                         SELECT                                                                            ");
		sbSql.append("\n 			                      TRVA.STND_YEAR                                                                 ");
		sbSql.append("\n 			                    , TRVA.MBR_CD                                                                    ");
		sbSql.append("\n 			                    , TRVA.TMS                                                                       ");
		sbSql.append("\n 			                    , TRVA.DAY_ORD                                                                   ");
		sbSql.append("\n 			                    , TRVA.RACE_NO                                                                   ");
		sbSql.append("\n 			                    , TRVA.RACE_REG_NO                                                               ");
		sbSql.append("\n 			                    , NVL(TRVB.ACDNT_SCR, TRVA.ACDNT_SCR) ACDNT_SCR                                  ");
		sbSql.append("\n                         FROM     (                                                                        ");
		sbSql.append("\n                                    SELECT                                                                 ");
		sbSql.append("\n                                             TRVA.STND_YEAR                                                ");
		sbSql.append("\n                                           , TRVA.MBR_CD                                                   ");
		sbSql.append("\n                                           , TRVA.TMS                                                      ");
		sbSql.append("\n                                           , TRVA.DAY_ORD                                                  ");
		sbSql.append("\n                                           , TRVA.RACE_NO                                                  ");
		sbSql.append("\n                                           , TRVA.RACE_REG_NO                                              ");
		sbSql.append("\n                                           , SUM(ACDNT_SCR) ACDNT_SCR                                      ");
		sbSql.append("\n                                    FROM     TBED_RACE_VOIL_ACT TRVA                                       ");
		sbSql.append("\n                                           , TBEB_ACDNT_SCR     TAS                                        ");
		sbSql.append("\n                                           , TBEB_ORGAN         TOR                                        ");
		sbSql.append("\n                                    WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR                               ");
		sbSql.append("\n                                    AND    TRVA.VOIL_CD      = TAS.VOIL_CD                                 ");
		sbSql.append("\n                                    AND    TRVA.STND_YEAR    = TOR.STND_YEAR                               ");
		sbSql.append("\n                                    AND    TRVA.MBR_CD       = TOR.MBR_CD                                  ");
		sbSql.append("\n                                    AND    TRVA.TMS          = TOR.TMS                                     ");
		sbSql.append("\n                                    AND    TRVA.DAY_ORD      = TOR.DAY_ORD                                 ");
		sbSql.append("\n                                    AND    TRVA.RACE_NO      = TOR.RACE_NO                                 ");
		sbSql.append("\n                                    AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO                             ");
		sbSql.append("\n                                    AND    TOR.ST_MTHD_CD    = '"+sStMthdCd+"'                             ");
		sbSql.append("\n                                    AND    TOR.ABSE_CD      <> '003'                                       ");
		sbSql.append("\n                                    AND    TOR.IMMT_CLS_CD  <> '003'                                       ");
		sbSql.append("\n                                    AND    (TRVA.STND_YEAR, TRVA.MBR_CD, TRVA.TMS, TRVA.RACER_NO) IN (                                                                                             ");
		sbSql.append("\n                                                                                                        SELECT                                                                                     ");
		sbSql.append("\n                                                                                                                 TOR.STND_YEAR                                                                     ");
		sbSql.append("\n                                                                                                               , TOR.MBR_CD                                                                        ");
		sbSql.append("\n                                                                                                               , TOR.TMS                                                                           ");
		sbSql.append("\n                                                                                                               , TOR.RACER_NO                                                                      ");
		sbSql.append("\n                                                                                                        FROM   (                                                                                   ");
		sbSql.append("\n                                                                                                                    SELECT                                                                         ");
		sbSql.append("\n                                                                                                                             TOR.STND_YEAR                                                         ");
		sbSql.append("\n                                                                                                                           , TOR.MBR_CD                                                            ");
		sbSql.append("\n                                                                                                                           , TOR.TMS                                                               ");
		sbSql.append("\n                                                                                                                           , TOR.RACER_NO                                                          ");
		sbSql.append("\n                                                                                                                           , RANK() OVER (PARTITION BY TOR.RACER_NO                                ");
		sbSql.append("\n                                                                                                                                              ORDER BY TOR.RACE_DAY DESC ) TMS_6                   ");
		sbSql.append("\n                                                                                                                    FROM   (                                                                       ");
		sbSql.append("\n                                                                                                                                SELECT                                                             ");
		sbSql.append("\n                                                                                                                                         TOR.STND_YEAR                                             ");
		sbSql.append("\n                                                                                                                                       , TOR.MBR_CD                                                ");
		sbSql.append("\n                                                                                                                                       , TOR.TMS                                                   ");
		sbSql.append("\n                                                                                                                                       , TOR.RACER_NO                                              ");
		sbSql.append("\n                                                                                                                                       , MAX(TOR.RACE_DAY) RACE_DAY                                ");
		sbSql.append("\n                                                                                                                                FROM   TBEB_ORGAN        TOR                                       ");
		sbSql.append("\n                                                                                                                                WHERE  TOR .RACE_DAY    <= (                                       ");
		sbSql.append("\n                                                                                                                                                                SELECT                             ");
		sbSql.append("\n                                                                                                                                                                       MAX(TRDO.RACE_DAY) RACE_DAY ");
		sbSql.append("\n                                                                                                                                                                FROM   TBEB_RACE_DAY_ORD TRDO      ");
		sbSql.append("\n                                                                                                                                                                WHERE  TRDO.STND_YEAR = ?          ");
		sbSql.append("\n                                                                                                                                                                AND    TRDO.MBR_CD    = ?          ");
		sbSql.append("\n                                                                                                                                                                AND    TRDO.TMS       = ?          ");
		sbSql.append("\n                                                                                                                                                           )                                       ");
		sbSql.append("\n                                                                                                                                AND    TOR.ABSE_CD     <> '003'   -- 참석 경우                     ");
		sbSql.append("\n                                                                                                                                AND    TOR.IMMT_CLS_CD <> '003'    -- 면책이 아닌 경우             ");
		sbSql.append("\n                                                                                                                                AND    TOR.STND_YEAR   >= ? - 1                                    ");
		sbSql.append("\n                                                                                                                                AND    TOR.ST_MTHD_CD   = '"+sStMthdCd+"'                          ");
		sbSql.append("\n                                                                                                                                GROUP BY                ");
		sbSql.append("\n                                                                                                                                         TOR.STND_YEAR  ");
		sbSql.append("\n                                                                                                                                       , TOR.MBR_CD     ");
		sbSql.append("\n                                                                                                                                       , TOR.TMS        ");
		sbSql.append("\n                                                                                                                                       , TOR.RACER_NO   ");
		sbSql.append("\n                                                                                                                           ) TOR                        ");
		sbSql.append("\n                                                                                                               ) TOR                                    ");
		sbSql.append("\n                                                                                                        WHERE  TMS_6 <= 6                               ");
		sbSql.append("\n                                                                                                     )                                                  ");
		sbSql.append("\n                                    GROUP BY                                  ");
		sbSql.append("\n                                             TRVA.STND_YEAR                   ");
		sbSql.append("\n                                           , TRVA.MBR_CD                      ");
		sbSql.append("\n                                           , TRVA.TMS                         ");
		sbSql.append("\n                                           , TRVA.DAY_ORD                     ");
		sbSql.append("\n                                           , TRVA.RACE_NO                     ");
		sbSql.append("\n                                           , TRVA.RACE_REG_NO                 ");
		sbSql.append("\n 			                     ) TRVA                                             ");
		sbSql.append("\n 			                   , (                                                  ");
		sbSql.append("\n                                    SELECT                                    ");
		sbSql.append("\n                                             TRVA.STND_YEAR                   ");
		sbSql.append("\n                                           , TRVA.MBR_CD                      ");
		sbSql.append("\n                                           , TRVA.TMS                         ");
		sbSql.append("\n                                           , TRVA.DAY_ORD                     ");
		sbSql.append("\n                                           , TRVA.RACE_NO                     ");
		sbSql.append("\n                                           , TRVA.RACE_REG_NO                 ");
		sbSql.append("\n                                           , TAS .ACDNT_SCR                   ");
		sbSql.append("\n                                    FROM     TBED_RACE_VOIL_ACT TRVA          ");
		sbSql.append("\n                                           , TBEB_ACDNT_SCR     TAS           ");
		sbSql.append("\n                                           , TBEB_ORGAN         TOR           ");
		sbSql.append("\n                                    WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR  ");
		sbSql.append("\n                                    AND    TRVA.VOIL_CD      = TAS.VOIL_CD    ");
		sbSql.append("\n                                    AND    TRVA.STND_YEAR    = TOR.STND_YEAR  ");
		sbSql.append("\n                                    AND    TRVA.MBR_CD       = TOR.MBR_CD     ");
		sbSql.append("\n                                    AND    TRVA.TMS          = TOR.TMS        ");
		sbSql.append("\n                                    AND    TRVA.DAY_ORD      = TOR.DAY_ORD    ");
		sbSql.append("\n                                    AND    TRVA.RACE_NO      = TOR.RACE_NO    ");
		sbSql.append("\n                                    AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO");
		sbSql.append("\n                                    AND    TOR.ST_MTHD_CD    = '"+sStMthdCd+"'");
		sbSql.append("\n                                    AND    TOR.ABSE_CD      <> '003'          ");
		sbSql.append("\n                                    AND    TOR.IMMT_CLS_CD  <> '003'          ");
		sbSql.append("\n                                    AND    TRVA.VOIL_CD     IN ('110', '120') ");
		sbSql.append("\n                                    AND    (TRVA.STND_YEAR, TRVA.MBR_CD, TRVA.TMS, TRVA.RACER_NO) IN (                                                                                             ");
		sbSql.append("\n                                                                                                        SELECT                                                                                     ");
		sbSql.append("\n                                                                                                                 TOR.STND_YEAR                                                                     ");
		sbSql.append("\n                                                                                                               , TOR.MBR_CD                                                                        ");
		sbSql.append("\n                                                                                                               , TOR.TMS                                                                           ");
		sbSql.append("\n                                                                                                               , TOR.RACER_NO                                                                      ");
		sbSql.append("\n                                                                                                        FROM   (                                                                                   ");
		sbSql.append("\n                                                                                                                    SELECT                                                                         ");
		sbSql.append("\n                                                                                                                             TOR.STND_YEAR                                                         ");
		sbSql.append("\n                                                                                                                           , TOR.MBR_CD                                                            ");
		sbSql.append("\n                                                                                                                           , TOR.TMS                                                               ");
		sbSql.append("\n                                                                                                                           , TOR.RACER_NO                                                          ");
		sbSql.append("\n                                                                                                                           , RANK() OVER (PARTITION BY TOR.RACER_NO                                ");
		sbSql.append("\n                                                                                                                                              ORDER BY TOR.RACE_DAY DESC ) TMS_6                   ");
		sbSql.append("\n                                                                                                                    FROM   (                                                                       ");
		sbSql.append("\n                                                                                                                                SELECT                                                             ");
		sbSql.append("\n                                                                                                                                         TOR.STND_YEAR                                             ");
		sbSql.append("\n                                                                                                                                       , TOR.MBR_CD                                                ");
		sbSql.append("\n                                                                                                                                       , TOR.TMS                                                   ");
		sbSql.append("\n                                                                                                                                       , TOR.RACER_NO                                              ");
		sbSql.append("\n                                                                                                                                       , MAX(TOR.RACE_DAY) RACE_DAY                                ");
		sbSql.append("\n                                                                                                                                FROM   TBEB_ORGAN        TOR                                       ");
		sbSql.append("\n                                                                                                                                WHERE  TOR .RACE_DAY    <= (                                       ");
		sbSql.append("\n                                                                                                                                                                SELECT                             ");
		sbSql.append("\n                                                                                                                                                                       MAX(TRDO.RACE_DAY) RACE_DAY ");
		sbSql.append("\n                                                                                                                                                                FROM   TBEB_RACE_DAY_ORD TRDO      ");
		sbSql.append("\n                                                                                                                                                                WHERE  TRDO.STND_YEAR = ?          ");
		sbSql.append("\n                                                                                                                                                                AND    TRDO.MBR_CD    = ?          ");
		sbSql.append("\n                                                                                                                                                                AND    TRDO.TMS       = ?          ");
		sbSql.append("\n                                                                                                                                                           )                                       ");
		sbSql.append("\n                                                                                                                                AND    TOR.ABSE_CD     <> '003'   -- 참석 경우                     ");
		sbSql.append("\n                                                                                                                                AND    TOR.IMMT_CLS_CD <> '003'    -- 면책이 아닌 경우             ");
		sbSql.append("\n                                                                                                                                AND    TOR.STND_YEAR   >= ? - 1                                    ");
		sbSql.append("\n                                                                                                                                AND    TOR.ST_MTHD_CD   = '"+sStMthdCd+"'                          ");
		sbSql.append("\n                                                                                                                                GROUP BY                                                           ");
		sbSql.append("\n                                                                                                                                         TOR.STND_YEAR                                             ");
		sbSql.append("\n                                                                                                                                       , TOR.MBR_CD                                                ");
		sbSql.append("\n                                                                                                                                       , TOR.TMS                                                   ");
		sbSql.append("\n                                                                                                                                       , TOR.RACER_NO                                              ");
		sbSql.append("\n                                                                                                                           ) TOR                                                                   ");
		sbSql.append("\n                                                                                                               ) TOR                                                                               ");
		sbSql.append("\n                                                                                                        WHERE  TMS_6 <= 6                                                                          ");
		sbSql.append("\n                                                                                                     )                                                                                             ");
		sbSql.append("\n                                  ) TRVB                                                                                                                                                           ");
		sbSql.append("\n                        WHERE  TRVA.STND_YEAR   = TRVB.STND_YEAR  (+)  ");
		sbSql.append("\n 			            AND    TRVA.MBR_CD      = TRVB.MBR_CD     (+)        ");
		sbSql.append("\n 			            AND    TRVA.TMS         = TRVB.TMS        (+)        ");
		sbSql.append("\n 			            AND    TRVA.DAY_ORD     = TRVB.DAY_ORD    (+)        ");
		sbSql.append("\n 			            AND    TRVA.RACE_NO     = TRVB.RACE_NO    (+)        ");
		sbSql.append("\n 			            AND    TRVA.RACE_REG_NO = TRVB.RACE_REG_NO(+)        ");
		sbSql.append("\n                      ) TRVA                                           ");
		sbSql.append("\n             WHERE  TOR .STND_YEAR    = TR  .STND_YEAR                 ");
		sbSql.append("\n             AND    TOR .MBR_CD       = TR  .MBR_CD                    ");
		sbSql.append("\n             AND    TOR .TMS          = TR  .TMS                       ");
		sbSql.append("\n             AND    TOR .DAY_ORD      = TR  .DAY_ORD                   ");
		sbSql.append("\n             AND    TOR .RACE_NO      = TR  .RACE_NO                   ");
		sbSql.append("\n             AND    TOR .STND_YEAR    = TRS .STND_YEAR                 ");
		sbSql.append("\n             AND    TR  .RACE_DGRE_CD = TRS .RACE_DGRE_CD              ");
		sbSql.append("\n             AND    NVL(TOR .RANK,0)  = TRS .RANK                      ");
		sbSql.append("\n             AND    TOR .STND_YEAR    = TRVA.STND_YEAR  (+)            ");
		sbSql.append("\n             AND    TOR .MBR_CD       = TRVA.MBR_CD     (+)            ");
		sbSql.append("\n             AND    TOR .TMS          = TRVA.TMS        (+)            ");
		sbSql.append("\n             AND    TOR .DAY_ORD      = TRVA.DAY_ORD    (+)            ");
		sbSql.append("\n             AND    TOR .RACE_NO      = TRVA.RACE_NO    (+)            ");
		sbSql.append("\n             AND    TOR .RACE_REG_NO  = TRVA.RACE_REG_NO(+)            ");
		sbSql.append("\n             AND    TOR .RACER_NO     = STTM.RACER_NO   (+)            ");
		sbSql.append("\n             AND    TOR.ABSE_CD      <> '003'   -- 면책결장이 아닌 경우");
		sbSql.append("\n             AND    TOR.ST_MTHD_CD    = '"+sStMthdCd+"'                ");
		sbSql.append("\n             AND    TOR.IMMT_CLS_CD  <> '003'   -- 면책이 아닌 경우    ");
		sbSql.append("\n             GROUP BY TOR .RACER_NO                                    ");
		sbSql.append("\n                    , STTM.TMS_6_AVG_STAR_TM                           ");
		sbSql.append("\n           ) TMS_6                                                     ");
		sbSql.append("\n         , (                                                           ");
		sbSql.append("\n             -- 8회차 성적                                             ");
		sbSql.append("\n             SELECT                                                    ");
		sbSql.append("\n                      RACER_NO                                         ");
		sbSql.append("\n                    , MIN(DECODE(TMS_8, 1, RANK)) TMS_8_RANK_1         ");
		sbSql.append("\n                    , MIN(DECODE(TMS_8, 2, RANK)) TMS_8_RANK_2         ");
		sbSql.append("\n                    , MIN(DECODE(TMS_8, 3, RANK)) TMS_8_RANK_3         ");
		sbSql.append("\n                    , MIN(DECODE(TMS_8, 4, RANK)) TMS_8_RANK_4         ");
		sbSql.append("\n                    , MIN(DECODE(TMS_8, 5, RANK)) TMS_8_RANK_5         ");
		sbSql.append("\n                    , MIN(DECODE(TMS_8, 6, RANK)) TMS_8_RANK_6         ");
		sbSql.append("\n                    , MIN(DECODE(TMS_8, 7, RANK)) TMS_8_RANK_7         ");
		sbSql.append("\n                    , MIN(DECODE(TMS_8, 8, RANK)) TMS_8_RANK_8         ");
		sbSql.append("\n             FROM   (                                                  ");
		sbSql.append("\n                         SELECT                                        ");
		sbSql.append("\n                                  TOR.STND_YEAR, TOR.MBR_CD, TOR.TMS, TOR.DAY_ORD, TOR.RACE_NO, TOR.RACE_REG_NO, TOR.RACER_NO, TOR.ABSE_CD, NVL(TOR.RANK,0) RANK ");
		sbSql.append("\n                                , RANK() OVER (PARTITION BY TOR.RACER_NO                             ");
		sbSql.append("\n                                                   ORDER BY TOR.RACE_DAY DESC                        ");
		sbSql.append("\n		                                                  , TOR.RACE_NO  DESC ) TMS_8                    ");
		sbSql.append("\n                         FROM   TBEB_ORGAN        TOR, TBEB_RACE TR                                  ");
		sbSql.append("\n                         WHERE  TOR.ABSE_CD     <> '003'    -- 면책결장이 아닌 경우                  ");
		sbSql.append("\n                         AND    TOR.IMMT_CLS_CD <> '003'    -- 면책이 아닌 경우                      ");
		sbSql.append("\n                         AND    TR.RACE_STTS_CD = '001'     -- 경주성립만 가져옴                     ");
		sbSql.append("\n                         AND    TOR.STND_YEAR = TR.STND_YEAR                                         ");
		sbSql.append("\n                         AND    TOR.MBR_CD = TR.MBR_CD                                               ");
		sbSql.append("\n                         AND    TOR.TMS = TR.TMS                                                     ");
		sbSql.append("\n                         AND    TOR.DAY_ORD = TR.DAY_ORD                                             ");
		sbSql.append("\n                         AND    TOR.RACE_NO = TR.RACE_NO                                             ");
		sbSql.append("\n                         AND    TOR.ST_MTHD_CD = '"+sStMthdCd+"'                                     ");
		sbSql.append("\n                         AND    TOR.RACE_DAY    <= (                                                 ");
		sbSql.append("\n                                                     SELECT                                          ");
		sbSql.append("\n                                                            MAX(TRDO.RACE_DAY) RACE_DAY              ");
		sbSql.append("\n                                                     FROM   TBEB_RACE_DAY_ORD TRDO                   ");
		sbSql.append("\n                                                     WHERE  TRDO.STND_YEAR = ?                       ");
		sbSql.append("\n                                                     AND    TRDO.MBR_CD    = ?                       ");
		sbSql.append("\n                                                     AND    TRDO.TMS       = ?                       ");
		sbSql.append("\n                                                   )                                                 ");
		sbSql.append("\n                   )                                                                                 ");
		sbSql.append("\n             WHERE   TMS_8 <= 8                                                                      ");
		sbSql.append("\n             GROUP BY RACER_NO                                                                       ");
		sbSql.append("\n           ) TMS_8                                                                                   ");
		sbSql.append("\n         , (                                                                                         ");
		sbSql.append("\n             -- 년도별 성적                                                                          ");
		sbSql.append("\n             SELECT                                                                                  ");
		sbSql.append("\n                      TOR.RACER_NO                                                                   ");
		sbSql.append("\n                    , COUNT(DISTINCT(TOR.MBR_CD || TOR.TMS))                RUN_CNT                  ");
		sbSql.append("\n                    , COUNT(DISTINCT(TOR.MBR_CD || TOR.TMS || TOR.DAY_ORD)) RACE_DAY_CNT             ");
		sbSql.append("\n                    , COUNT(*)                                              RACE_CNT                 ");
		sbSql.append("\n                    , SUM(DECODE(TRST.QURT_CD, '001', 1, NULL))              BF_RACE_CNT             ");
		sbSql.append("\n                    , SUM(DECODE(TRST.QURT_CD, '002', 1, NULL))              AF_RACE_CNT             ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '001', TRVA.ACDNT_SCR, NULL)), 0) BF_ACDNT_SCR    ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '002', TRVA.ACDNT_SCR, NULL)), 0) AF_ACDNT_SCR    ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 1, 1, 0))                       , 0) RANK_1           ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 2, 1, 0))                       , 0) RANK_2           ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 3, 1, 0))                       , 0) RANK_3           ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 4, 1, 0))                       , 0) RANK_4           ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 5, 1, 0))                       , 0) RANK_5           ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 6, 1, 0))                       , 0) RANK_6           ");
		sbSql.append("\n                    , NVL(STTM.AVG_STAR_TM                                     , 0) AVG_STAR_TM      ");
		sbSql.append("\n                    , NVL(SUM(TRS.RACE_SCR)                                    , 0) AMU_RANK_SCR     ");
		sbSql.append("\n                    , NVL(SUM(TRVA.ACDNT_SCR)                                  , 0) AMU_ACDNT_SCR    ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '001', TRVA.F_CNT, NULL))    , 0) BF_F_CNT        ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '002', TRVA.F_CNT, NULL))    , 0) AF_F_CNT        ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '001', TRVA.L_CNT, NULL))    , 0) BF_L_CNT        ");
		sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '002', TRVA.L_CNT, NULL))    , 0) AF_L_CNT        ");
		sbSql.append("\n                    , NVL(SUM(TRVA.ABSE_CNT      )                             , 0) ABSE_CNT         ");
		sbSql.append("\n                    , NVL(SUM(TRVA.RACE_ESC_1_CNT)                             , 0) RACE_ESC_1_CNT   ");
		sbSql.append("\n                    , NVL(SUM(TRVA.RACE_ESC_2_CNT)                             , 0) RACE_ESC_2_CNT   ");
		sbSql.append("\n                    , NVL(SUM(TRVA.FOUL_ELIM_CNT )                             , 0) FOUL_ELIM_CNT    ");
		sbSql.append("\n                    , NVL(SUM(TRVA.ELIM_CNT      )                             , 0) ELIM_CNT         ");
		sbSql.append("\n                    , NVL(SUM(TRVA.FOUL_WARN_CNT )                             , 0) FOUL_WARN_CNT    ");
		sbSql.append("\n                    , NVL(SUM(TRVA.WARN_CNT      )                             , 0) WARN_CNT         ");
		sbSql.append("\n                    , NVL(SUM(TRVA.ATTEN_CNT     )                             , 0) ATTEN_CNT        ");
		sbSql.append("\n             FROM     TBEB_ORGAN         TOR                                                         ");
		sbSql.append("\n                    , TBEB_RACE_TMS      TRT                                                         ");
		sbSql.append("\n                    , TBEB_RACE          TR                                                          ");
		sbSql.append("\n                    , TBEB_RANK_SCR      TRS                                                         ");
		sbSql.append("\n                    , TBEB_RECD_STND_TERM TRST                                                       ");
		sbSql.append("\n                    , (                                                                              ");
		sbSql.append("\n                         SELECT                                                                      ");
		sbSql.append("\n 			                      TRVA.STND_YEAR                                                           ");
		sbSql.append("\n 			                    , TRVA.MBR_CD                                                              ");
		sbSql.append("\n 			                    , TRVA.TMS                                                                 ");
		sbSql.append("\n 			                    , TRVA.DAY_ORD                                                             ");
		sbSql.append("\n 			                    , TRVA.RACE_NO                                                             ");
		sbSql.append("\n 			                    , TRVA.RACE_REG_NO                                                         ");
		sbSql.append("\n 			                    , NVL(TRVB.ACDNT_SCR, TRVA.ACDNT_SCR) ACDNT_SCR                            ");
		sbSql.append("\n                                , TRVA.F_CNT                                                         ");
		sbSql.append("\n                                , TRVA.L_CNT                                                         ");
		sbSql.append("\n                                , TRVA.ABSE_CNT                                                      ");
		sbSql.append("\n                                , TRVA.RACE_ESC_1_CNT                                                ");
		sbSql.append("\n                                , TRVA.RACE_ESC_2_CNT                                                ");
		sbSql.append("\n                                , TRVA.FOUL_ELIM_CNT                                                 ");
		sbSql.append("\n                                , TRVA.ELIM_CNT                                                      ");
		sbSql.append("\n                                , TRVA.FOUL_WARN_CNT                                                 ");
		sbSql.append("\n                                , TRVA.WARN_CNT                                                      ");
		sbSql.append("\n                                , TRVA.ATTEN_CNT                                                     ");
		sbSql.append("\n                         FROM     (                                                                  ");
		sbSql.append("\n             			            SELECT                                                                 ");
		sbSql.append("\n                                             TRVA.STND_YEAR                                          ");
		sbSql.append("\n                                           , TRVA.MBR_CD                                             ");
		sbSql.append("\n                                           , TRVA.TMS                                                ");
		sbSql.append("\n                                           , TRVA.DAY_ORD                                            ");
		sbSql.append("\n                                           , TRVA.RACE_NO                                            ");
		sbSql.append("\n                                           , TRVA.RACE_REG_NO                                        ");
		sbSql.append("\n                                           , SUM(ACDNT_SCR) ACDNT_SCR                                ");
		sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '110', 1, NULL)) F_CNT         ");
		sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '120', 1, NULL)) L_CNT         ");
		sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '130', 1, NULL)) ABSE_CNT      ");
		sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '995', 1, NULL)) RACE_ESC_1_CNT");
		sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '997', 1, NULL)) RACE_ESC_2_CNT");
		sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '125', 1, NULL)) FOUL_ELIM_CNT ");
		sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '140', 1, NULL)) ELIM_CNT      ");
		sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '150', 1, NULL)) FOUL_WARN_CNT ");
		sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '210', 1, NULL)) WARN_CNT      ");
		sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '220', 1, NULL)) ATTEN_CNT     ");
		sbSql.append("\n             			            FROM     TBED_RACE_VOIL_ACT TRVA                                       ");
		sbSql.append("\n             			                   , TBEB_ACDNT_SCR     TAS                                        ");
		sbSql.append("\n                                           , TBEB_ORGAN         TOR                                  ");
		sbSql.append("\n             			            WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR                               ");
		sbSql.append("\n             			            AND    TRVA.VOIL_CD      = TAS.VOIL_CD                                 ");
		sbSql.append("\n                                    AND    TRVA.STND_YEAR    = TOR.STND_YEAR                         ");
		sbSql.append("\n                                    AND    TRVA.MBR_CD       = TOR.MBR_CD                            ");
		sbSql.append("\n                                    AND    TRVA.TMS          = TOR.TMS                               ");
		sbSql.append("\n                                    AND    TRVA.DAY_ORD      = TOR.DAY_ORD                           ");
		sbSql.append("\n                                    AND    TRVA.RACE_NO      = TOR.RACE_NO                           ");
		sbSql.append("\n                                    AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO                       ");
		sbSql.append("\n                                    AND    TOR.ABSE_CD      <> '003'                                 ");
		sbSql.append("\n                                    AND    TOR.IMMT_CLS_CD  <> '003'                                 ");
		sbSql.append("\n             			            AND    TRVA.STND_YEAR    = ?                                           ");
		sbSql.append("\n             			            AND    TOR.ST_MTHD_CD = '"+sStMthdCd+"'                                ");
		sbSql.append("\n             			            AND    TRVA.RACE_DAY    <= (                                           ");
		sbSql.append("\n             			                                            SELECT                                 ");
		sbSql.append("\n             			                                                   MAX(TRDO.RACE_DAY) RACE_DAY     ");
		sbSql.append("\n             			                                            FROM   TBEB_RACE_DAY_ORD TRDO          ");
		sbSql.append("\n             			                                            WHERE  TRDO.STND_YEAR = ?              ");
		sbSql.append("\n             			                                            AND    TRDO.MBR_CD    = ?              ");
		sbSql.append("\n             			                                            AND    TRDO.TMS       = ?              ");
		sbSql.append("\n             			                                       )                                           ");
		sbSql.append("\n             			            GROUP BY                                                               ");
		sbSql.append("\n             			                     TRVA.STND_YEAR                                                ");
		sbSql.append("\n             			                   , TRVA.MBR_CD                                                   ");
		sbSql.append("\n             			                   , TRVA.TMS                                                      ");
		sbSql.append("\n             			                   , TRVA.DAY_ORD                                                  ");
		sbSql.append("\n             			                   , TRVA.RACE_NO                                                  ");
		sbSql.append("\n             			                   , TRVA.RACE_REG_NO                                              ");
		sbSql.append("\n 			                     ) TRVA                                                                    ");
		sbSql.append("\n 			                   , (                                                                         ");
		sbSql.append("\n             			            SELECT                                                                 ");
		sbSql.append("\n             			                     TRVA.STND_YEAR                                                ");
		sbSql.append("\n             			                   , TRVA.MBR_CD                                                   ");
		sbSql.append("\n             			                   , TRVA.TMS                                                      ");
		sbSql.append("\n             			                   , TRVA.DAY_ORD                                                  ");
		sbSql.append("\n             			                   , TRVA.RACE_NO                                                  ");
		sbSql.append("\n             			                   , TRVA.RACE_REG_NO                                              ");
		sbSql.append("\n             			                   , TAS .ACDNT_SCR                                                ");
		sbSql.append("\n             			            FROM     TBED_RACE_VOIL_ACT TRVA                                       ");
		sbSql.append("\n             			                   , TBEB_ACDNT_SCR     TAS                                        ");
		sbSql.append("\n                                           , TBEB_ORGAN         TOR                                  ");
		sbSql.append("\n             			            WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR                               ");
		sbSql.append("\n             			            AND    TRVA.VOIL_CD      = TAS.VOIL_CD                                 ");
		sbSql.append("\n                                    AND    TRVA.STND_YEAR    = TOR.STND_YEAR                         ");
		sbSql.append("\n                                    AND    TRVA.MBR_CD       = TOR.MBR_CD                            ");
		sbSql.append("\n                                    AND    TRVA.TMS          = TOR.TMS                               ");
		sbSql.append("\n                                    AND    TRVA.DAY_ORD      = TOR.DAY_ORD                           ");
		sbSql.append("\n                                    AND    TRVA.RACE_NO      = TOR.RACE_NO                           ");
		sbSql.append("\n                                    AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO                       ");
		sbSql.append("\n                                    AND    TOR.ABSE_CD      <> '003'                                 ");
		sbSql.append("\n                                    AND    TOR.IMMT_CLS_CD  <> '003'                                 ");
		sbSql.append("\n             			            AND    TRVA.STND_YEAR    = ?                                           ");
		sbSql.append("\n             			            AND    TOR.ST_MTHD_CD    = '"+sStMthdCd+"'                             ");
		sbSql.append("\n             			            AND    TRVA.RACE_DAY    <= (                                           ");
		sbSql.append("\n             			                                            SELECT                                 ");
		sbSql.append("\n             			                                                   MAX(TRDO.RACE_DAY) RACE_DAY     ");
		sbSql.append("\n             			                                            FROM   TBEB_RACE_DAY_ORD TRDO          ");
		sbSql.append("\n             			                                            WHERE  TRDO.STND_YEAR = ?              ");
		sbSql.append("\n             			                                            AND    TRDO.MBR_CD    = ?              ");
		sbSql.append("\n             			                                            AND    TRDO.TMS       = ?              ");
		sbSql.append("\n             			                                       )                                           ");
		sbSql.append("\n             			            AND    TRVA.VOIL_CD     IN ('110', '120')                              ");
		sbSql.append("\n                                  ) TRVB                                                             ");
		sbSql.append("\n                        WHERE  TRVA.STND_YEAR   = TRVB.STND_YEAR  (+)                                ");
		sbSql.append("\n 			            AND    TRVA.MBR_CD      = TRVB.MBR_CD     (+)                                      ");
		sbSql.append("\n 			            AND    TRVA.TMS         = TRVB.TMS        (+)                                      ");
		sbSql.append("\n 			            AND    TRVA.DAY_ORD     = TRVB.DAY_ORD    (+)                                      ");
		sbSql.append("\n 			            AND    TRVA.RACE_NO     = TRVB.RACE_NO    (+)                                      ");
		sbSql.append("\n 			            AND    TRVA.RACE_REG_NO = TRVB.RACE_REG_NO(+)                                      ");
		sbSql.append("\n                      ) TRVA                                                                         ");
		sbSql.append("\n                    , (                                                                              ");
		sbSql.append("\n                         SELECT                                                                      ");
		sbSql.append("\n                                  TOR.RACER_NO                                                       ");
		sbSql.append("\n                                , NVL(ROUND(AVG(TOR.STAR_TM), 2), 0) AVG_STAR_TM                     ");
		sbSql.append("\n                         FROM   TBEB_ORGAN        TOR                                                ");
		sbSql.append("\n                         WHERE  TOR .STND_YEAR    = ?                                                ");
		sbSql.append("\n                         AND    TOR .ST_MTHD_CD   = '"+sStMthdCd+"'                                  ");
		sbSql.append("\n                         AND    TOR .RACE_DAY    <= (                                                ");
		sbSql.append("\n                                                         SELECT                                      ");
		sbSql.append("\n                                                                MAX(TRDO.RACE_DAY) RACE_DAY          ");
		sbSql.append("\n                                                         FROM   TBEB_RACE_DAY_ORD TRDO               ");
		sbSql.append("\n                                                         WHERE  TRDO.STND_YEAR = ?                   ");
		sbSql.append("\n                                                         AND    TRDO.MBR_CD    = ?                   ");
		sbSql.append("\n                                                         AND    TRDO.TMS       = ?                   ");
		sbSql.append("\n                                                    )                                                ");
		sbSql.append("\n                         GROUP BY TOR.RACER_NO                                                       ");
		sbSql.append("\n                      ) STTM                                                                         ");
		sbSql.append("\n             WHERE  TOR.STND_YEAR    = TR.STND_YEAR                                                  ");
		sbSql.append("\n             AND    TOR.MBR_CD       = TR.MBR_CD                                                     ");
		sbSql.append("\n             AND    TOR.TMS          = TR.TMS                                                        ");
		sbSql.append("\n             AND    TOR.DAY_ORD      = TR.DAY_ORD                                                    ");
		sbSql.append("\n             AND    TOR.RACE_NO      = TR.RACE_NO                                                    ");
		sbSql.append("\n             AND    TOR.STND_YEAR    = TRT.STND_YEAR                                                 ");
		sbSql.append("\n             AND    TOR.MBR_CD       = TRT.MBR_CD                                                    ");
		sbSql.append("\n             AND    TOR.TMS          = TRT.TMS                                                       ");
		sbSql.append("\n             AND    TOR.STND_YEAR    = TRS.STND_YEAR                                                 ");
		sbSql.append("\n             AND    TR.RACE_DGRE_CD = TRS.RACE_DGRE_CD                                               ");
		sbSql.append("\n             AND    NVL(TOR.RANK,0)  = TRS.RANK                                                      ");
		sbSql.append("\n             AND    TOR.STND_YEAR    = TRVA.STND_YEAR  (+)                                           ");
		sbSql.append("\n             AND    TOR.MBR_CD       = TRVA.MBR_CD     (+)                                           ");
		sbSql.append("\n             AND    TOR.TMS          = TRVA.TMS        (+)                                           ");
		sbSql.append("\n             AND    TOR.DAY_ORD      = TRVA.DAY_ORD    (+)                                           ");
		sbSql.append("\n             AND    TOR.RACE_NO      = TRVA.RACE_NO    (+)                                           ");
		sbSql.append("\n             AND    TOR.RACE_REG_NO  = TRVA.RACE_REG_NO(+)                                           ");
		sbSql.append("\n             AND    TOR.RACER_NO     = STTM.RACER_NO   (+)                                           ");
		sbSql.append("\n             AND    TOR.STND_YEAR    = TRST.STND_YEAR                                                ");
		sbSql.append("\n             AND    TRT.STR_DT BETWEEN TRST.STR_DT                                                   ");
		sbSql.append("\n                                    AND TRST.END_DT                                                  ");
		sbSql.append("\n             AND    TOR.ABSE_CD     <> '003'   -- 면책결장이 아닌 경우                               ");
		sbSql.append("\n             AND    TOR.IMMT_CLS_CD <> '003'   -- 면책이 아닌 경우                                   ");
		sbSql.append("\n             AND    TOR.STND_YEAR    = ?                                                             ");
		sbSql.append("\n             AND    TOR.ST_MTHD_CD = '"+sStMthdCd+"'                                                 ");
		sbSql.append("\n             AND    TOR.RACE_DAY    <= (                                                             ");
		sbSql.append("\n                                             SELECT                                                  ");
		sbSql.append("\n                                                    MAX(TRDO.RACE_DAY) RACE_DAY                      ");
		sbSql.append("\n                                             FROM   TBEB_RACE_DAY_ORD TRDO                           ");
		sbSql.append("\n                                             WHERE  TRDO.STND_YEAR = ?                               ");
		sbSql.append("\n                                             AND    TRDO.MBR_CD    = ?                               ");
		sbSql.append("\n                                             AND    TRDO.TMS       = ?                               ");
		sbSql.append("\n                                        )                                                            ");
		sbSql.append("\n             GROUP BY TOR.RACER_NO                                                                   ");
		sbSql.append("\n                    , STTM.AVG_STAR_TM                                                               ");
		sbSql.append("\n           ) YEAR                                                                                    ");
		sbSql.append("\n         , (                                                                                         ");
		sbSql.append("\n             -- 전회차 경정장                                                                        ");
		sbSql.append("\n             SELECT                                                                                  ");
		sbSql.append("\n                      RACER_NO                                                                       ");
		sbSql.append("\n                    , MIN(DECODE(TMS_3, 1, TMS   )) BF_TMS                                           ");
		sbSql.append("\n                    , MIN(DECODE(TMS_3, 2, TMS   )) BF_BF_TMS                                        ");
		sbSql.append("\n                    , MIN(DECODE(TMS_3, 3, TMS   )) BF_BF_BF_TMS                                     ");
		sbSql.append("\n                    , MIN(DECODE(TMS_3, 1, MBR_CD)) BF_MBR_CD                                        ");
		sbSql.append("\n                    , MIN(DECODE(TMS_3, 2, MBR_CD)) BF_BF_MBR_CD                                     ");
		sbSql.append("\n                    , MIN(DECODE(TMS_3, 3, MBR_CD)) BF_BF_BF_MBR_CD                                  ");
		sbSql.append("\n             FROM   (                                                                                ");
		sbSql.append("\n                         SELECT                                                                      ");
		sbSql.append("\n                                  TOR.STND_YEAR                                                      ");
		sbSql.append("\n                                , TOR.MBR_CD                                                         ");
		sbSql.append("\n                                , TOR.TMS                                                            ");
		sbSql.append("\n                                , TOR.RACER_NO                                                       ");
		sbSql.append("\n                                , RANK() OVER (PARTITION BY TOR.RACER_NO                             ");
		sbSql.append("\n                                                   ORDER BY TOR.RACE_DAY DESC ) TMS_3                ");
		sbSql.append("\n                         FROM   (                                                                    ");
		sbSql.append("\n                                     SELECT                                                          ");
		sbSql.append("\n                                              TOR.STND_YEAR                                          ");
		sbSql.append("\n                                            , TOR.MBR_CD                                             ");
		sbSql.append("\n                                            , TOR.TMS                                                ");
		sbSql.append("\n                                            , TOR.RACER_NO                                           ");
		sbSql.append("\n                                            , MAX(TOR.RACE_DAY) RACE_DAY                             ");
		sbSql.append("\n                                     FROM   TBEB_ORGAN        TOR                                    ");
		sbSql.append("\n                                     WHERE  TOR.RACE_DAY     <= (                                        ");
		sbSql.append("\n                                                                     SELECT                              ");
		sbSql.append("\n                                                                            MAX(TRDO.RACE_DAY) RACE_DAY  ");
		sbSql.append("\n                                                                     FROM   TBEB_RACE_DAY_ORD TRDO       ");
		sbSql.append("\n                                                                     WHERE  TRDO.STND_YEAR = ?           ");
		sbSql.append("\n                                                                     AND    TRDO.MBR_CD    = ?           ");
		sbSql.append("\n                                                                     AND    TRDO.TMS       = ?           ");
		sbSql.append("\n                                                                )                                        ");
		sbSql.append("\n                                     AND    TOR .ABSE_CD     <> '003'   -- 면책결장이 아닌 경우          ");
		sbSql.append("\n                                     AND    TOR .IMMT_CLS_CD <> '003'   -- 면책이 아닌 경우              ");
		sbSql.append("\n                                     AND    TOR .ST_MTHD_CD  = '"+sStMthdCd+"' ");
		sbSql.append("\n                                     GROUP BY                                  ");
		sbSql.append("\n                                              TOR.STND_YEAR                    ");
		sbSql.append("\n                                            , TOR.MBR_CD                       ");
		sbSql.append("\n                                            , TOR.TMS                          ");
		sbSql.append("\n                                            , TOR.RACER_NO                     ");
		sbSql.append("\n                                ) TOR                                          ");
		sbSql.append("\n                    )                                                          ");
		sbSql.append("\n             GROUP BY RACER_NO                                                 ");
		sbSql.append("\n           ) TMS_3                                                             ");
		sbSql.append("\n         , (                                                                   ");
		sbSql.append("\n             --  전체 성적                                                     ");
		sbSql.append("\n             SELECT                                                            ");
		sbSql.append("\n                      RACER_NO                                                 ");
		sbSql.append("\n                    , COUNT(*) TOT_RACE_CNT                                    ");
		sbSql.append("\n             FROM     TBEB_ORGAN         TOR                                   ");
		sbSql.append("\n             WHERE  TOR.RACE_DAY     <= (                                      ");
		sbSql.append("\n                                             SELECT                            ");
		sbSql.append("\n                                                    MAX(TRDO.RACE_DAY) RACE_DAY");
		sbSql.append("\n                                             FROM   TBEB_RACE_DAY_ORD TRDO     ");
		sbSql.append("\n                                             WHERE  TRDO.STND_YEAR = ?         ");
		sbSql.append("\n                                             AND    TRDO.MBR_CD    = ?         ");
		sbSql.append("\n                                             AND    TRDO.TMS       = ?         ");
		sbSql.append("\n                                        )                                      ");
		sbSql.append("\n             AND    TOR .STND_YEAR    = ?                                      ");
		sbSql.append("\n             AND    TOR .ABSE_CD     <> '003'   -- 면책이 아닌 경우            ");
		sbSql.append("\n             AND    TOR .ST_MTHD_CD   = '"+sStMthdCd+"'                        ");
		sbSql.append("\n             GROUP BY RACER_NO                                                 ");
		sbSql.append("\n           ) TOT                                                               ");
		sbSql.append("\n        , (  -- GPP 산출(연도별)                                               ");
		sbSql.append("\n             SELECT   TOR.RACER_NO                                             ");
		sbSql.append("\n                    , SUM(TGP.GPP_SCR) GPP_SCR                                 ");
		sbSql.append("\n               FROM TBEB_ORGAN TOR, TBEB_RACE TR, TBEB_GPP_SCR TGP             ");
		sbSql.append("\n              WHERE TOR.STND_YEAR = TO_CHAR(SYSDATE,'YYYY')                    ");
		sbSql.append("\n                    AND TOR.STND_YEAR = TR.STND_YEAR                           ");
		sbSql.append("\n                    AND TOR.TMS = TR.TMS                                       ");
		sbSql.append("\n                    AND TOR.DAY_ORD = TR.DAY_ORD                               ");
		sbSql.append("\n                    AND TOR.RACE_NO = TR.RACE_NO                               ");
		sbSql.append("\n                    AND TOR.STND_YEAR = TGP.STND_YEAR                          ");
		sbSql.append("\n                    AND TOR.DAY_ORD = TGP.DAY_ORD                              ");
		sbSql.append("\n                    AND TOR.RANK = TGP.RANK                                    ");
		sbSql.append("\n                    AND TGP.RACE_DGRE_CD =                                     ");
		sbSql.append("\n						  (CASE  WHEN TR.RACE_DGRE_CD IN ('706', '707', '708', '933', '951', '953', '954') THEN '951'     ");
		sbSql.append("\n                				 WHEN TR.RACE_DGRE_CD IN ('901', '903', '906', '952')                   THEN '952'	  ");
		sbSql.append("\n                				 ELSE  TR.RACE_DGRE_CD END)															                              ");
		sbSql.append("\n                    AND (TOR.RANK > 0 OR (TOR.RANK = 0 AND TOR.IMMT_CLS_CD <> '002' AND TOR.ABSE_CD <> '001'))");
		sbSql.append("\n                    AND TOR.RACE_DAY    <= (                                                                  ");
		sbSql.append("\n                                     SELECT MAX(TRDO.RACE_DAY) RACE_DAY                                       ");
		sbSql.append("\n                                     FROM   TBEB_RACE_DAY_ORD TRDO                                            ");
		sbSql.append("\n                                     WHERE  TRDO.STND_YEAR =  ?                                               ");
		sbSql.append("\n                                     AND    TRDO.MBR_CD    =  ?                                               ");
		sbSql.append("\n                                     AND    TRDO.TMS       =  ?                                               ");
		sbSql.append("\n                        )                                                                                     ");
		sbSql.append("\n                     AND TOR.ST_MTHD_CD = '"+sStMthdCd+"'                                                     ");
		sbSql.append("\n               GROUP BY TOR.RACER_NO                                                                          ");
		sbSql.append("\n          ) STGP                                                                                              ");
		sbSql.append("\n WHERE   TRM.RACER_NO = TMS_6.RACER_NO(+)                                                                     ");
		sbSql.append("\n AND     TRM.RACER_NO = TMS_8.RACER_NO(+)                                                                     ");
		sbSql.append("\n AND     TRM.RACER_NO = YEAR .RACER_NO(+)                                                                     ");
		sbSql.append("\n AND     TRM.RACER_NO = TMS_3.RACER_NO(+)                                                                     ");
		sbSql.append("\n AND     TRM.RACER_NO = TOT  .RACER_NO(+)                                                                     ");
		sbSql.append("\n AND     TRM.RACER_NO = TRD  .RACER_NO(+)                                                                     ");
		sbSql.append("\n AND     TRM.RACER_NO = STGP .RACER_NO(+)                                                                     ");
		sbSql.append("\n AND     TRM.RACER_NO IN ( SELECT DISTINCT RACER_NO                                                           ");
		sbSql.append("\n 		                   FROM TBEB_ORGAN                                                                    ");
		sbSql.append("\n 		                   WHERE 1=1                                                                          ");
		sbSql.append("\n 		                   AND STND_YEAR = ?                                                                  ");
		sbSql.append("\n 				           AND MBR_CD = ?                                                                     ");
		sbSql.append("\n 				           AND TMS = ?                                                                        ");
		sbSql.append("\n 				           AND ST_MTHD_CD = '"+sStMthdCd+"'                                                   ");
		sbSql.append("\n 				         )                                                                                    ");
		sbSql.append("\n AND     TMS_6_AMU_RANK_SCR IS NOT NULL                                                                       ");
    	
        	
        int nSize = 0;
        if ( ds != null ) 
        	nSize = ds.getRecordCount();

        if ( nSize > 0 )
        	sbSql.append("\n AND     TRM.RACER_NO IN (");
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);

            if ( i == 0 ) {
            	sbSql.append(      "'" + record.getAttribute("RACER_NO") + "' \n");
            } else { 
            	sbSql.append("," + "'" + record.getAttribute("RACER_NO") + "' \n");
            }
        }
        
        if ( nSize > 0 )
        	sbSql.append(")");
    	sbSql.append("\n ORDER BY TRM.RACER_NO                                                                                                                                               ");
        
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, "000"    );  
        param.setWhereClauseParameter(i++, "0"      );  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, SESSION_USER_ID );
        param.setWhereClauseParameter(i++, SESSION_USER_ID );
        
        // 6회차
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, sStndYear);  
        
        // 8경주
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        
        // 년도별성적
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     ); 
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );
        
        // 전회차
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        
        // 년도별  출전수
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, sStndYear);  

        // 년도별  GPP 산출
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        
        // 회차별 대상자 선별
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );

        PosRowSet rowSet =   this.getDao("boadao").findByQueryStatement(sbSql.toString(), param);
        return rowSet;
    }

    /**
     * <p> 회차별 모터 성적조회 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected PosRowSet searchMotRecdAccuSum(PosDataset ds)
    {
    	StringBuffer sbSql = new StringBuffer();
    	
    	sbSql.append("\n SELECT /* SearchRecdCreate : searchMotRecdAccuSum */                                                                                                                                                                    ");
    	sbSql.append("\n          DISTINCT(?)                                                                                      STND_YEAR                                                                          ");
    	sbSql.append("\n        , ?                                                                                                MBR_CD                                                                             ");
    	sbSql.append("\n        , ?                                                                                                TMS                                                                                ");
    	sbSql.append("\n        , TE   .EQUIP_NO                                                                                   MOT_NO                                                                             ");
    	sbSql.append("\n        , SUBSTR(TE   .EQUIP_NO, 6, 3)                                                                     VIEW_MOT_NO                                                                        ");
    	sbSql.append("\n        , YEAR .RUN_CNT                                                                                    RUN_CNT                                                                            ");
    	sbSql.append("\n        , YEAR .RACE_DAY_CNT                                                                               RACE_DAY_CNT                                                                       ");
    	sbSql.append("\n        , YEAR .RACE_CNT                                                                                   RACE_CNT                                                                           ");
    	sbSql.append("\n        , YEAR .RANK_1                                                                                     RANK_1_CNT                                                                         ");
    	sbSql.append("\n        , YEAR .RANK_2                                                                                     RANK_2_CNT                                                                         ");
    	sbSql.append("\n        , YEAR .RANK_3                                                                                     RANK_3_CNT                                                                         ");
    	sbSql.append("\n        , YEAR .RANK_4                                                                                     RANK_4_CNT                                                                         ");
    	sbSql.append("\n        , YEAR .RANK_5                                                                                     RANK_5_CNT                                                                         ");
    	sbSql.append("\n        , YEAR .RANK_6                                                                                     RANK_6_CNT                                                                         ");
    	sbSql.append("\n        , YEAR .AMU_RANK_SCR                                                                               AMU_RANK_SCR                                                                       ");
    	sbSql.append("\n        , YEAR .AVG_RANK_SCR                                                                               AVG_RANK_SCR                                                                       ");
    	sbSql.append("\n        , YEAR .AVG_STAR_TM                                                                                AVG_STRT_TM                                                                        ");
    	sbSql.append("\n        , TRIM(TO_CHAR((YEAR .RANK_1) / YEAR .RACE_CNT * 100, 990.9))                                      WIN_RATIO                                                                          ");
    	sbSql.append("\n        , TRIM(TO_CHAR((YEAR .RANK_1 + YEAR .RANK_2) / YEAR .RACE_CNT * 100, 990.9))                       HIGH_RANK_RATIO                                                                    ");
    	sbSql.append("\n        , TRIM(TO_CHAR((YEAR .RANK_1 + YEAR .RANK_2 + YEAR .RANK_3) / YEAR .RACE_CNT * 100, 990.9))        HIGH_3_RANK_RATIO                                                                  ");
    	sbSql.append("\n        , TMS_3.TMS_3_ITRDT_RUN_TM                                                                         TMS_3_ITRDT_RUN_TM                                                                 ");
    	sbSql.append("\n        , MOT  .AVG_ITRDT_RUN_TM                                                                           AVG_ITRDT_RUN_TM                                                                   ");
    	sbSql.append("\n        , MOT  .MAX_ITRDT_RUN_TM                                                                           MAX_ITRDT_RUN_TM                                                                   ");
    	sbSql.append("\n        , MOT  .MIN_ITRDT_RUN_TM                                                                           MIN_ITRDT_RUN_TM                                                                   ");
    	sbSql.append("\n        , MOT  .ITRDT_RUN_TM_DVTN                                                                          ITRDT_RUN_TM_DVTN                                                                  ");
    	sbSql.append("\n        , ?                                                                                                INST_ID                                                                            ");
    	sbSql.append("\n        , SYSDATE                                                                                          INST_DTM                                                                           ");
    	sbSql.append("\n        , ?                                                                                                UPDT_ID                                                                            ");
    	sbSql.append("\n        , SYSDATE                                                                                          UPDT_DTM                                                                           ");
    	sbSql.append("\n FROM     TBEF_EQUIP   TE                                                                                                                                                   ");
    	sbSql.append("\n        , (                                                                                                                                                                 ");
    	sbSql.append("\n             -- 년도별 성적                                                                                                                                                 ");
    	sbSql.append("\n             SELECT                                                                                                                                                         ");
    	sbSql.append("\n                      MOT_NO                                                                                                                                                ");
    	sbSql.append("\n                    , COUNT(DISTINCT(TOR.STND_YEAR || TOR.MBR_CD || TOR.TMS))                RUN_CNT                                                                        ");
    	sbSql.append("\n                    , COUNT(DISTINCT(TOR.STND_YEAR || TOR.MBR_CD || TOR.TMS || TOR.DAY_ORD)) RACE_DAY_CNT                                                                   ");
    	sbSql.append("\n                    , COUNT(*)                                              RACE_CNT                                                                                        ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 1, 1, 0))                        RANK_1                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 2, 1, 0))                        RANK_2                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 3, 1, 0))                        RANK_3                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 4, 1, 0))                        RANK_4                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 5, 1, 0))                        RANK_5                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 6, 1, 0))                        RANK_6                                                                                          ");
    	sbSql.append("\n                    , SUM(TRS.RACE_SCR)                                     AMU_RANK_SCR                                                                                    ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TRS.RACE_SCR), 90.99))               AVG_RANK_SCR                                                                                    ");
    	sbSql.append("\n                   , TRIM(TO_CHAR(SUM(DECODE(TOR.ST_MTHD_CD, '001', 0,TOR.STAR_TM))/NVL(SUM(DECODE(TOR.ST_MTHD_CD, '001', 0, 1)),1), 90.99))                AVG_STAR_TM           ");
    	sbSql.append("\n             FROM     TBEB_ORGAN         TOR                                                                                                                                ");
    	sbSql.append("\n                    , TBEB_RACE_TMS      TRT                                                                                                                                ");
    	sbSql.append("\n                    , TBEB_RACE          TR                                                                                                                                 ");
    	sbSql.append("\n                    , TBEB_RANK_SCR      TRS                                                                                                                                ");
    	sbSql.append("\n             WHERE  TOR .STND_YEAR      = TR  .STND_YEAR                                                                                                                    ");
    	sbSql.append("\n             AND    TOR .MBR_CD         = TR  .MBR_CD                                                                                                                       ");
    	sbSql.append("\n             AND    TOR .TMS            = TR  .TMS                                                                                                                          ");
    	sbSql.append("\n             AND    TOR .DAY_ORD        = TR  .DAY_ORD                                                                                                                      ");
    	sbSql.append("\n             AND    TOR .RACE_NO        = TR  .RACE_NO                                                                                                                      ");
    	sbSql.append("\n             AND    TOR .STND_YEAR      = TRT .STND_YEAR                                                                                                                    ");
    	sbSql.append("\n             AND    TOR .MBR_CD         = TRT .MBR_CD                                                                                                                       ");
    	sbSql.append("\n             AND    TOR .TMS            = TRT .TMS                                                                                                                          ");
    	sbSql.append("\n             AND    TOR .STND_YEAR      = TRS .STND_YEAR                                                                                                                    ");
    	sbSql.append("\n             AND    TR  .RACE_DGRE_CD   = TRS .RACE_DGRE_CD                                                                                                                 ");
    	sbSql.append("\n             AND    TOR .RANK           = TRS .RANK                                                                                                                         ");
    	sbSql.append("\n             AND    TOR .ABSE_CD       <> '003'                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .IMMT_CLS_CD   <> '003'                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .MBR_CD         = ?                                                                                                                                 ");
    	sbSql.append("\n             AND    TOR .RACE_DAY      <= (                                                                                                                                 ");
    	sbSql.append("\n                                             SELECT                                                                                                                         ");
    	sbSql.append("\n                                                    MAX(TRDO.RACE_DAY) RACE_DAY                                                                                             ");
    	sbSql.append("\n                                             FROM   TBEB_RACE_DAY_ORD TRDO                                                                                                  ");
    	sbSql.append("\n                                             WHERE  TRDO.STND_YEAR = ?                                                                                                      ");
    	sbSql.append("\n                                             AND    TRDO.MBR_CD    = ?                                                                                                      ");
    	sbSql.append("\n                                             AND    TRDO.TMS       = ?                                                                                                      ");
    	sbSql.append("\n                                          )                                                                                                                                 ");
    	sbSql.append("\n             GROUP BY MOT_NO                                                                                                                                                ");
    	sbSql.append("\n          ) YEAR                                                                                                                                                            ");
    	sbSql.append("\n        , (                                                                                                                                                                 ");
    	sbSql.append("\n             -- 3회차 성적                                                                                                                                                  ");
    	sbSql.append("\n             SELECT                                                                                                                                                         ");
    	sbSql.append("\n                      MOT_NO                                                                                                                                                ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TOR.INTRO_RUN_TM) , 90.99)) TMS_3_ITRDT_RUN_TM                                                                                       ");
    	sbSql.append("\n             FROM     TBEB_ORGAN        TOR                                                                                                                                 ");
    	sbSql.append("\n             WHERE  (TOR.STND_YEAR, TOR.MBR_CD, TOR.TMS, TOR.MOT_NO) IN (                                                                                                   ");
    	sbSql.append("\n                                                                             SELECT                                                                                         ");
    	sbSql.append("\n                                                                                      TOR.STND_YEAR                                                                         ");
    	sbSql.append("\n                                                                                    , TOR.MBR_CD                                                                            ");
    	sbSql.append("\n                                                                                    , TOR.TMS                                                                               ");
    	sbSql.append("\n                                                                                    , TOR.MOT_NO                                                                            ");
    	sbSql.append("\n                                                                             FROM   (                                                                                       ");
    	sbSql.append("\n                                                                                         SELECT                                                                             ");
    	sbSql.append("\n                                                                                                  TOR.STND_YEAR                                                             ");
    	sbSql.append("\n                                                                                                , TOR.MBR_CD                                                                ");
    	sbSql.append("\n                                                                                                , TOR.TMS                                                                   ");
    	sbSql.append("\n                                                                                                , TOR.MOT_NO                                                                ");
    	sbSql.append("\n                                                                                                , RANK() OVER (PARTITION BY TOR.MOT_NO                                      ");
    	sbSql.append("\n                                                                                                                   ORDER BY TOR.RACE_DAY DESC ) TMS_3                       ");
    	sbSql.append("\n                                                                                         FROM   (                                                                           ");
    	sbSql.append("\n                                                                                                     SELECT                                                                 ");
    	sbSql.append("\n                                                                                                              TOR.STND_YEAR                                                 ");
    	sbSql.append("\n                                                                                                            , TOR.MBR_CD                                                    ");
    	sbSql.append("\n                                                                                                            , TOR.TMS                                                       ");
    	sbSql.append("\n                                                                                                            , TOR.MOT_NO                                                    ");
    	sbSql.append("\n                                                                                                            , MAX(TOR.RACE_DAY) RACE_DAY                                    ");
    	sbSql.append("\n                                                                                                     FROM   TBEB_ORGAN        TOR                                           ");
    	sbSql.append("\n                                                                                                     WHERE  TOR .RACE_DAY    <= (                                           ");
    	sbSql.append("\n                                                                                                                                     SELECT                                 ");
    	sbSql.append("\n                                                                                                                                            MAX(TRDO.RACE_DAY) RACE_DAY     ");
    	sbSql.append("\n                                                                                                                                     FROM   TBEB_RACE_DAY_ORD TRDO          ");
    	sbSql.append("\n                                                                                                                                     WHERE  TRDO.STND_YEAR = ?              ");
    	sbSql.append("\n                                                                                                                                     AND    TRDO.MBR_CD    = ?              ");
    	sbSql.append("\n                                                                                                                                     AND    TRDO.TMS       = ?              ");
    	sbSql.append("\n                                                                                                                                )                                           ");
    	sbSql.append("\n                                                                                                     AND    TOR.MBR_CD       = ?                                            ");
    	sbSql.append("\n                                                                                                     AND    TOR.ABSE_CD     <> '003'                                        ");
    	sbSql.append("\n                                                                                                     AND    TOR.IMMT_CLS_CD <> '003'                                        ");
    	sbSql.append("\n                                                                                                     GROUP BY                                                               ");
    	sbSql.append("\n                                                                                                              TOR.STND_YEAR                                                 ");
    	sbSql.append("\n                                                                                                            , TOR.MBR_CD                                                    ");
    	sbSql.append("\n                                                                                                            , TOR.TMS                                                       ");
    	sbSql.append("\n                                                                                                            , TOR.MOT_NO                                                    ");
    	sbSql.append("\n                                                                                                ) TOR                                                                       ");
    	sbSql.append("\n                                                                                    ) TOR                                                                                   ");
    	sbSql.append("\n                                                                             WHERE TMS_3 <= 3                                                                               ");
    	sbSql.append("\n                                                                        )                                                                                                   ");
    	sbSql.append("\n             GROUP BY MOT_NO                                                                                                                                                ");
    	sbSql.append("\n          ) TMS_3                                                                                                                                                           ");
    	sbSql.append("\n        , (                                                                                                                                                                 ");
    	sbSql.append("\n             -- 평균소개타임                                                                                                                                                ");
    	sbSql.append("\n             SELECT                                                                                                                                                         ");
    	sbSql.append("\n                      MOT_NO                                                                                                                                                ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TOR.INTRO_RUN_TM) , 90.99))          AVG_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , MAX(TOR.RACE_TM)                                      MAX_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , MIN(TOR.RACE_TM)                                      MIN_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(STDDEV(TOR.INTRO_RUN_TM), 90.99))        ITRDT_RUN_TM_DVTN                                                                               ");
    	sbSql.append("\n             FROM     TBEB_ORGAN        TOR                                                                                                                                 ");
    	sbSql.append("\n             WHERE  TOR .MBR_CD         = ?                                                                                                                                 ");
    	sbSql.append("\n             AND    TOR .RACE_DAY      <= (                                                                                                                                 ");
    	sbSql.append("\n                                             SELECT                                                                                                                         ");
    	sbSql.append("\n                                                    MAX(TRDO.RACE_DAY) RACE_DAY                                                                                             ");
    	sbSql.append("\n                                             FROM   TBEB_RACE_DAY_ORD TRDO                                                                                                  ");
    	sbSql.append("\n                                             WHERE  TRDO.STND_YEAR = ?                                                                                                      ");
    	sbSql.append("\n                                             AND    TRDO.MBR_CD    = ?                                                                                                      ");
    	sbSql.append("\n                                             AND    TRDO.TMS       = ?                                                                                                      ");
    	sbSql.append("\n                                          )                                                                                                                                 ");
    	sbSql.append("\n             GROUP BY MOT_NO                                                                                                                                                ");
    	sbSql.append("\n          ) MOT                                                                                                                                                             ");
    	sbSql.append("\n WHERE  TE   .EQUIP_TPE_CD = 'M'                                                                                                                                            ");
    	sbSql.append("\n AND    TE   .EQUIP_NO     = MOT  .MOT_NO(+)                                                                                                                                ");
    	sbSql.append("\n AND    TE   .EQUIP_NO     = YEAR .MOT_NO(+)                                                                                                                                ");
    	sbSql.append("\n AND    TE   .EQUIP_NO     = TMS_3.MOT_NO(+)                                                                                                                                ");

        	
        int nSize = 0;
        if ( ds != null ) 
        	nSize = ds.getRecordCount();

        if ( nSize > 0 ) {
        	sbSql.append("\n AND    MOT.MOT_NO IN (                                        ");
        	sbSql.append("\n                         SELECT                                ");
        	sbSql.append("\n                                 TOR.MOT_NO                    ");
        	sbSql.append("\n                         FROM    TBEB_ORGAN TOR                ");
        	sbSql.append("\n                         WHERE   TOR.STND_YEAR    = ?          ");
        	sbSql.append("\n                         AND     TOR.MBR_CD       = ?          ");
        	sbSql.append("\n                         AND     TOR.TMS          = ?          ");
//        	sbSql.append("\n                         AND     TOR.ABSE_CD     <> '003'      ");
//        	sbSql.append("\n                         AND     TOR.IMMT_CLS_CD <> '003'      ");
        	sbSql.append("\n                         AND     TOR.RACER_NO IN (             ");
        }
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);

            if ( i == 0 ) {
            	sbSql.append(      "'" + record.getAttribute("RACER_NO") + "' \n");
            } else { 
            	sbSql.append("," + "'" + record.getAttribute("RACER_NO") + "' \n");
            }
        }
        
        if ( nSize > 0 ) {
        	sbSql.append("\n                                                 )             ");
        	sbSql.append("\n                      )                                        ");
        }
        
    	sbSql.append("\n ORDER BY TE   .EQUIP_NO                                                                                                                               ");

    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, SESSION_USER_ID );
        param.setWhereClauseParameter(i++, SESSION_USER_ID );
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        if ( nSize > 0 ) {
	        param.setWhereClauseParameter(i++, sStndYear);
	        param.setWhereClauseParameter(i++, sMbrCd   );
	        param.setWhereClauseParameter(i++, sTms     );
        }

        PosRowSet rowSet =   this.getDao("boadao").findByQueryStatement(sbSql.toString(), param);
        return rowSet;
    }

    /**
     * <p> 회차별 모터 성적조회(플라잉/온라인) </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected PosRowSet searchMotRecdAccuSumFlyOnl(PosDataset ds, String sStMthdCd)
    {
    	StringBuffer sbSql = new StringBuffer();
    	
    	sbSql.append("\n SELECT /* SearchRecdCreate : searchMotRecdAccuSumFlyOnl */                                                                            ");
    	sbSql.append("\n          DISTINCT(?)                                                                                      STND_YEAR             ");
    	sbSql.append("\n        , ?                                                                                                MBR_CD                ");
    	sbSql.append("\n        , ?                                                                                                TMS                   ");
    	sbSql.append("\n        , TE   .EQUIP_NO                                                                                   MOT_NO                ");
    	sbSql.append("\n        , SUBSTR(TE   .EQUIP_NO, 6, 3)                                                                     VIEW_MOT_NO           ");
    	sbSql.append("\n        , YEAR .RUN_CNT                                                                                    RUN_CNT               ");
    	sbSql.append("\n        , YEAR .RACE_DAY_CNT                                                                               RACE_DAY_CNT          ");
    	sbSql.append("\n        , YEAR .RACE_CNT                                                                                   RACE_CNT              ");
    	sbSql.append("\n        , YEAR .RANK_1                                                                                     RANK_1_CNT            ");
    	sbSql.append("\n        , YEAR .RANK_2                                                                                     RANK_2_CNT            ");
    	sbSql.append("\n        , YEAR .RANK_3                                                                                     RANK_3_CNT            ");
    	sbSql.append("\n        , YEAR .RANK_4                                                                                     RANK_4_CNT            ");
    	sbSql.append("\n        , YEAR .RANK_5                                                                                     RANK_5_CNT            ");
    	sbSql.append("\n        , YEAR .RANK_6                                                                                     RANK_6_CNT            ");
    	sbSql.append("\n        , YEAR .AMU_RANK_SCR                                                                               AMU_RANK_SCR          ");
    	sbSql.append("\n        , YEAR .AVG_RANK_SCR                                                                               AVG_RANK_SCR          ");
    	sbSql.append("\n        , YEAR .AVG_STAR_TM                                                                                AVG_STRT_TM           ");
    	sbSql.append("\n        , TRIM(TO_CHAR((YEAR .RANK_1) / YEAR .RACE_CNT * 100, 990.9))                                      WIN_RATIO             ");
    	sbSql.append("\n        , TRIM(TO_CHAR((YEAR .RANK_1 + YEAR .RANK_2) / YEAR .RACE_CNT * 100, 990.9))                       HIGH_RANK_RATIO       ");
    	sbSql.append("\n        , TRIM(TO_CHAR((YEAR .RANK_1 + YEAR .RANK_2 + YEAR .RANK_3) / YEAR .RACE_CNT * 100, 990.9))        HIGH_3_RANK_RATIO     ");
    	sbSql.append("\n        , TMS_3.TMS_3_ITRDT_RUN_TM                                                                         TMS_3_ITRDT_RUN_TM    ");
    	sbSql.append("\n        , MOT  .AVG_ITRDT_RUN_TM                                                                           AVG_ITRDT_RUN_TM      ");
    	sbSql.append("\n        , MOT  .MAX_ITRDT_RUN_TM                                                                           MAX_ITRDT_RUN_TM      ");
    	sbSql.append("\n        , MOT  .MIN_ITRDT_RUN_TM                                                                           MIN_ITRDT_RUN_TM      ");
    	sbSql.append("\n        , MOT  .ITRDT_RUN_TM_DVTN                                                                          ITRDT_RUN_TM_DVTN     ");
    	sbSql.append("\n        , ?                                                                                                INST_ID               ");
    	sbSql.append("\n        , SYSDATE                                                                                          INST_DTM              ");
    	sbSql.append("\n        , ?                                                                                                UPDT_ID               ");
    	sbSql.append("\n        , SYSDATE                                                                                          UPDT_DTM              ");
		sbSql.append("\n FROM     TBEF_EQUIP   TE                                                                                   ");
    	sbSql.append("\n        , (                                                                                                 ");
    	sbSql.append("\n             -- 년도별 성적                                                                                 ");
    	sbSql.append("\n             SELECT                                                                                         ");
    	sbSql.append("\n                      MOT_NO                                                                                ");
    	sbSql.append("\n                    , COUNT(DISTINCT(TOR.STND_YEAR || TOR.MBR_CD || TOR.TMS))                RUN_CNT        ");
    	sbSql.append("\n                    , COUNT(DISTINCT(TOR.STND_YEAR || TOR.MBR_CD || TOR.TMS || TOR.DAY_ORD)) RACE_DAY_CNT   ");
    	sbSql.append("\n                    , COUNT(*)                                              RACE_CNT                        ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 1, 1, 0))                        RANK_1                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 2, 1, 0))                        RANK_2                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 3, 1, 0))                        RANK_3                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 4, 1, 0))                        RANK_4                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 5, 1, 0))                        RANK_5                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 6, 1, 0))                        RANK_6                          ");
    	sbSql.append("\n                    , SUM(TRS.RACE_SCR)                                     AMU_RANK_SCR                    ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TRS.RACE_SCR), 90.99))               AVG_RANK_SCR                    ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TOR.STAR_TM), 90.99))                AVG_STAR_TM                     ");
    	sbSql.append("\n             FROM     TBEB_ORGAN         TOR                                                                ");
    	sbSql.append("\n                    , TBEB_RACE_TMS      TRT                                                                ");
    	sbSql.append("\n                    , TBEB_RACE          TR                                                                 ");
    	sbSql.append("\n                    , TBEB_RANK_SCR      TRS                                                                ");
    	sbSql.append("\n             WHERE  TOR .STND_YEAR      = TR  .STND_YEAR                                                    ");
    	sbSql.append("\n             AND    TOR .MBR_CD         = TR  .MBR_CD                                                       ");
    	sbSql.append("\n             AND    TOR .TMS            = TR  .TMS                                                          ");
    	sbSql.append("\n             AND    TOR .DAY_ORD        = TR  .DAY_ORD                                                      ");
    	sbSql.append("\n             AND    TOR .RACE_NO        = TR  .RACE_NO                                                      ");
    	sbSql.append("\n             AND    TOR .STND_YEAR      = TRT .STND_YEAR                                                    ");
    	sbSql.append("\n             AND    TOR .MBR_CD         = TRT .MBR_CD                                                       ");
    	sbSql.append("\n             AND    TOR .TMS            = TRT .TMS                                                          ");
    	sbSql.append("\n             AND    TOR .STND_YEAR      = TRS .STND_YEAR                                                    ");
    	sbSql.append("\n             AND    TR  .RACE_DGRE_CD   = TRS .RACE_DGRE_CD                                                 ");
    	sbSql.append("\n             AND    TOR .RANK           = TRS .RANK                                                         ");
    	sbSql.append("\n             AND    TOR .ABSE_CD       <> '003'                                                             ");
    	sbSql.append("\n             AND    TOR .IMMT_CLS_CD   <> '003'                                                             ");
    	sbSql.append("\n             AND    TOR .MBR_CD         = ?                                                                 ");
    	sbSql.append("\n             AND    TOR .RACE_DAY      <= (                                                                 ");
    	sbSql.append("\n                                             SELECT                                                         ");
    	sbSql.append("\n                                                    MAX(TRDO.RACE_DAY) RACE_DAY                             ");
    	sbSql.append("\n                                             FROM   TBEB_RACE_DAY_ORD TRDO                                  ");
    	sbSql.append("\n                                             WHERE  TRDO.STND_YEAR = ?                                      ");
    	sbSql.append("\n                                             AND    TRDO.MBR_CD    = ?                                      ");
    	sbSql.append("\n                                             AND    TRDO.TMS       = ?                                      ");
    	sbSql.append("\n                                          )                                                                 ");
    	sbSql.append("\n             AND    TOR .ST_MTHD_CD   = '"+sStMthdCd+"'                                                     ");
    	sbSql.append("\n             GROUP BY MOT_NO                                                                                ");
    	sbSql.append("\n          ) YEAR                                                                                            ");
    	sbSql.append("\n        , (                                                                                                 ");
    	sbSql.append("\n             -- 3회차 성적                                                                                  ");
    	sbSql.append("\n             SELECT                                                                                         ");
    	sbSql.append("\n                      MOT_NO                                                                                ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TOR.INTRO_RUN_TM) , 90.99)) TMS_3_ITRDT_RUN_TM                       ");
    	sbSql.append("\n             FROM     TBEB_ORGAN        TOR                                                                 ");
    	sbSql.append("\n             WHERE  (TOR.STND_YEAR, TOR.MBR_CD, TOR.TMS, TOR.MOT_NO) IN (                                   ");
    	sbSql.append("\n                                                                             SELECT                         ");
    	sbSql.append("\n                                                                                      TOR.STND_YEAR         ");
    	sbSql.append("\n                                                                                    , TOR.MBR_CD            ");
    	sbSql.append("\n                                                                                    , TOR.TMS               ");
    	sbSql.append("\n                                                                                    , TOR.MOT_NO            ");
    	sbSql.append("\n                                                                             FROM   (                       ");
    	sbSql.append("\n                                                                                         SELECT             																						");
    	sbSql.append("\n                                                                                                  TOR.STND_YEAR                                         ");
    	sbSql.append("\n                                                                                                , TOR.MBR_CD                                            ");
    	sbSql.append("\n                                                                                                , TOR.TMS                                               ");
    	sbSql.append("\n                                                                                                , TOR.MOT_NO                                            ");
    	sbSql.append("\n                                                                                                , RANK() OVER (PARTITION BY TOR.MOT_NO                  ");
    	sbSql.append("\n                                                                                                                   ORDER BY TOR.RACE_DAY DESC ) TMS_3   ");
    	sbSql.append("\n                                                                                         FROM   (                                                                           ");
    	sbSql.append("\n                                                                                                     SELECT                                                                 ");
    	sbSql.append("\n                                                                                                              TOR.STND_YEAR                                                 ");
    	sbSql.append("\n                                                                                                            , TOR.MBR_CD                                                    ");
    	sbSql.append("\n                                                                                                            , TOR.TMS                                                       ");
    	sbSql.append("\n                                                                                                            , TOR.MOT_NO                                                    ");
    	sbSql.append("\n                                                                                                            , MAX(TOR.RACE_DAY) RACE_DAY                                    ");
    	sbSql.append("\n                                                                                                     FROM   TBEB_ORGAN        TOR                                           ");
    	sbSql.append("\n                                                                                                     WHERE  TOR .RACE_DAY    <= (                                           ");
    	sbSql.append("\n                                                                                                                                     SELECT                                 ");
    	sbSql.append("\n                                                                                                                                            MAX(TRDO.RACE_DAY) RACE_DAY     ");
    	sbSql.append("\n                                                                                                                                     FROM   TBEB_RACE_DAY_ORD TRDO          ");
    	sbSql.append("\n                                                                                                                                     WHERE  TRDO.STND_YEAR = ?              ");
    	sbSql.append("\n                                                                                                                                     AND    TRDO.MBR_CD    = ?              ");
    	sbSql.append("\n                                                                                                                                     AND    TRDO.TMS       = ?              ");
    	sbSql.append("\n                                                                                                                                )                                           ");
    	sbSql.append("\n                                                                                                     AND    TOR.MBR_CD       = ?                                            ");
    	sbSql.append("\n                                                                                                     AND    TOR.ABSE_CD     <> '003'                                        ");
    	sbSql.append("\n                                                                                                     AND    TOR.IMMT_CLS_CD <> '003'                                        ");
    	sbSql.append("\n                                                                                                     AND    TOR.ST_MTHD_CD = '"+sStMthdCd+"'                                ");
    	sbSql.append("\n                                                                                                     GROUP BY                                                               ");
    	sbSql.append("\n                                                                                                              TOR.STND_YEAR                                                 ");
    	sbSql.append("\n                                                                                                            , TOR.MBR_CD                                                    ");
    	sbSql.append("\n                                                                                                            , TOR.TMS                                                       ");
    	sbSql.append("\n                                                                                                            , TOR.MOT_NO                                                    ");
    	sbSql.append("\n                                                                                                ) TOR                                                                       ");
    	sbSql.append("\n                                                                                    ) TOR                                                                                   ");
    	sbSql.append("\n                                                                             WHERE TMS_3 <= 3                                                                               ");
    	sbSql.append("\n                                                                        )                                                                                                   ");
    	sbSql.append("\n             GROUP BY MOT_NO                                                                                                                                                ");
    	sbSql.append("\n          ) TMS_3                                                                                                                                                           ");
		sbSql.append("\n        , (                                                                                      ");
    	sbSql.append("\n             -- 평균소개타임                                                                     ");
    	sbSql.append("\n             SELECT                                                                              ");
    	sbSql.append("\n                      MOT_NO                                                                     ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TOR.INTRO_RUN_TM) , 90.99))          AVG_ITRDT_RUN_TM     ");
    	sbSql.append("\n                    , MAX(TOR.RACE_TM)                                      MAX_ITRDT_RUN_TM     ");
    	sbSql.append("\n                    , MIN(TOR.RACE_TM)                                      MIN_ITRDT_RUN_TM     ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(STDDEV(TOR.INTRO_RUN_TM), 90.99))        ITRDT_RUN_TM_DVTN    ");
    	sbSql.append("\n             FROM     TBEB_ORGAN        TOR                                                      ");
    	sbSql.append("\n             WHERE  TOR .MBR_CD         = ?                                                      ");
    	sbSql.append("\n             AND    TOR .RACE_DAY      <= (                                                      ");
    	sbSql.append("\n                                             SELECT                                              ");
    	sbSql.append("\n                                                    MAX(TRDO.RACE_DAY) RACE_DAY                  ");
    	sbSql.append("\n                                             FROM   TBEB_RACE_DAY_ORD TRDO                       ");
    	sbSql.append("\n                                             WHERE  TRDO.STND_YEAR = ?                           ");
    	sbSql.append("\n                                             AND    TRDO.MBR_CD    = ?                           ");
    	sbSql.append("\n                                             AND    TRDO.TMS       = ?                           ");
    	sbSql.append("\n                                          )                                                      ");
    	sbSql.append("\n             AND    TOR.ST_MTHD_CD = '"+sStMthdCd+"'                                             ");
    	sbSql.append("\n             GROUP BY MOT_NO                                                                     ");
    	sbSql.append("\n          ) MOT                                                                                  ");
    	sbSql.append("\n WHERE  TE   .EQUIP_TPE_CD = 'M'                                                                 ");
    	sbSql.append("\n AND    TE   .EQUIP_NO     = MOT  .MOT_NO(+)                                                     ");
    	sbSql.append("\n AND    TE   .EQUIP_NO     = YEAR .MOT_NO(+)                                                     ");
    	sbSql.append("\n AND    TE   .EQUIP_NO     = TMS_3.MOT_NO(+)                                                     ");
    	
        	
        int nSize = 0;
        if ( ds != null ) 
        	nSize = ds.getRecordCount();

        if ( nSize > 0 ) {
        	sbSql.append("\n AND    MOT.MOT_NO IN (                                            ");
        	sbSql.append("\n                         SELECT                                    ");
        	sbSql.append("\n                                 TOR.MOT_NO                        ");
        	sbSql.append("\n                         FROM    TBEB_ORGAN TOR                    ");
        	sbSql.append("\n                         WHERE   TOR.STND_YEAR    = ?              ");
        	sbSql.append("\n                         AND     TOR.MBR_CD       = ?              ");
        	sbSql.append("\n                         AND     TOR.TMS          = ?              ");
        	sbSql.append("\n                         AND     TOR.ST_MTHD_CD   = '"+sStMthdCd+"'");
//        	sbSql.append("\n                         AND     TOR.ABSE_CD     <> '003'          ");
//        	sbSql.append("\n                         AND     TOR.IMMT_CLS_CD <> '003'          ");
        	sbSql.append("\n                         AND     TOR.RACER_NO IN (                 ");
        }
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);

            if ( i == 0 ) {
            	sbSql.append(      "'" + record.getAttribute("RACER_NO") + "' \n");
            } else { 
            	sbSql.append("," + "'" + record.getAttribute("RACER_NO") + "' \n");
            }
        }
        
        if ( nSize > 0 ) {
        	sbSql.append("\n                                                 )             ");
        	sbSql.append("\n                      )                                        ");
        }
        
    	sbSql.append("\n ORDER BY TE   .EQUIP_NO                                                                                                                               ");

    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, SESSION_USER_ID );
        param.setWhereClauseParameter(i++, SESSION_USER_ID );
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        if ( nSize > 0 ) {
	        param.setWhereClauseParameter(i++, sStndYear);
	        param.setWhereClauseParameter(i++, sMbrCd   );
	        param.setWhereClauseParameter(i++, sTms     );
        }

        PosRowSet rowSet =   this.getDao("boadao").findByQueryStatement(sbSql.toString(), param);
        return rowSet;
    }
    
    
    /**
     * <p> 회차별 보트 성적조회 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected PosRowSet searchBoatRecdAccuSum(PosDataset ds)
    {
    	StringBuffer sbSql = new StringBuffer();
    	
    	sbSql.append("\n SELECT  /* SearchRecdCreate : searchBoatRecdAccuSum */                                                                                                                                                                   ");
    	sbSql.append("\n          DISTINCT(?)                                                                                 STND_YEAR                                                                               ");
    	sbSql.append("\n        , ?                                                                                           MBR_CD                                                                                  ");
    	sbSql.append("\n        , ?                                                                                           TMS                                                                                     ");
    	sbSql.append("\n        , TE   .EQUIP_NO                                                                              BOAT_NO                                                                                  ");
    	sbSql.append("\n        , SUBSTR(TE   .EQUIP_NO, 6, 3)                                                                VIEW_BOAT_NO                                                                        ");
    	sbSql.append("\n        , YEAR .RUN_CNT                                                                               RUN_CNT                                                                                 ");
    	sbSql.append("\n        , YEAR .RACE_DAY_CNT                                                                          RACE_DAY_CNT                                                                            ");
    	sbSql.append("\n        , YEAR .RACE_CNT                                                                              RACE_CNT                                                                                ");
    	sbSql.append("\n        , YEAR .RANK_1                                                                                RANK_1_CNT                                                                              ");
    	sbSql.append("\n        , YEAR .RANK_2                                                                                RANK_2_CNT                                                                              ");
    	sbSql.append("\n        , YEAR .RANK_3                                                                                RANK_3_CNT                                                                              ");
    	sbSql.append("\n        , YEAR .RANK_4                                                                                RANK_4_CNT                                                                              ");
    	sbSql.append("\n        , YEAR .RANK_5                                                                                RANK_5_CNT                                                                              ");
    	sbSql.append("\n        , YEAR .RANK_6                                                                                RANK_6_CNT                                                                              ");
    	sbSql.append("\n        , YEAR .AMU_RANK_SCR                                                                          AMU_RANK_SCR                                                                            ");
    	sbSql.append("\n        , YEAR .AVG_RANK_SCR                                                                          AVG_RANK_SCR                                                                            ");
    	sbSql.append("\n        , TRIM(TO_CHAR((YEAR .RANK_1) / YEAR .RACE_CNT * 100, 990.9))                                 WIN_RATIO                                                                               ");
    	sbSql.append("\n        , TRIM(TO_CHAR((YEAR .RANK_1 + YEAR .RANK_2) / YEAR .RACE_CNT * 100, 990.9))                  HIGH_RANK_RATIO                                                                         ");
    	sbSql.append("\n        , TRIM(TO_CHAR((YEAR .RANK_1 + YEAR .RANK_2 + YEAR .RANK_3) / YEAR .RACE_CNT * 100, 990.9))   HIGH_3_RANK_RATIO                                                                       ");
    	sbSql.append("\n        , TMS_3.TMS_3_ITRDT_RUN_TM                                                                    TMS_3_ITRDT_RUN_TM                                                                      ");
    	sbSql.append("\n        , YEAR .AVG_ITRDT_RUN_TM                                                                      AVG_ITRDT_RUN_TM                                                                        ");
    	sbSql.append("\n        , YEAR .MAX_ITRDT_RUN_TM                                                                      MAX_ITRDT_RUN_TM                                                                        ");
    	sbSql.append("\n        , YEAR .MIN_ITRDT_RUN_TM                                                                      MIN_ITRDT_RUN_TM                                                                        ");
    	sbSql.append("\n        , YEAR .ITRDT_RUN_TM_DVTN                                                                     ITRDT_RUN_TM_DVTN                                                                       ");
    	sbSql.append("\n        , ?                                                                                           INST_ID                                                                                 ");
    	sbSql.append("\n        , SYSDATE                                                                                     INST_DTM                                                                                ");
    	sbSql.append("\n        , ?                                                                                           UPDT_ID                                                                                 ");
    	sbSql.append("\n        , SYSDATE                                                                                     UPDT_DTM                                                                                ");
    	sbSql.append("\n FROM     TBEF_EQUIP   TE                                                                                                                                                   ");
    	sbSql.append("\n        , (                                                                                                                                                                 ");
    	sbSql.append("\n             -- 년도별 성적                                                                                                                                                 ");
    	sbSql.append("\n             SELECT                                                                                                                                                         ");
    	sbSql.append("\n                      TOR.BOAT_NO                                                                                                                                           ");
    	sbSql.append("\n                    , COUNT(DISTINCT(TOR.STND_YEAR || TOR.MBR_CD || TOR.TMS))                RUN_CNT                                                                                         ");
    	sbSql.append("\n                    , COUNT(DISTINCT(TOR.STND_YEAR || TOR.MBR_CD || TOR.TMS || TOR.DAY_ORD)) RACE_DAY_CNT                                                                                    ");
    	sbSql.append("\n                    , COUNT(*)                                              RACE_CNT                                                                                        ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 1, 1, 0))                        RANK_1                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 2, 1, 0))                        RANK_2                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 3, 1, 0))                        RANK_3                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 4, 1, 0))                        RANK_4                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 5, 1, 0))                        RANK_5                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 6, 1, 0))                        RANK_6                                                                                          ");
    	sbSql.append("\n                    , SUM(TRS.RACE_SCR)                                     AMU_RANK_SCR                                                                                    ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TRS.RACE_SCR), 90.99))               AVG_RANK_SCR                                                                                    ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TOR.INTRO_RUN_TM) , 90.99))          AVG_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , MAX(TOR.RACE_TM)                                      MAX_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , MIN(TOR.RACE_TM)                                      MIN_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(STDDEV(TOR.INTRO_RUN_TM), 90.99))        ITRDT_RUN_TM_DVTN                                                                               ");
    	sbSql.append("\n             FROM     TBEB_ORGAN         TOR                                                                                                                                ");
    	sbSql.append("\n                    , TBEB_RACE_TMS      TRT                                                                                                                                ");
    	sbSql.append("\n                    , TBEB_RACE          TR                                                                                                                                 ");
    	sbSql.append("\n                    , TBEB_RANK_SCR      TRS                                                                                                                                ");
    	sbSql.append("\n             WHERE  TOR .STND_YEAR      = TR  .STND_YEAR                                                                                                                    ");
    	sbSql.append("\n             AND    TOR .MBR_CD         = TR  .MBR_CD                                                                                                                       ");
    	sbSql.append("\n             AND    TOR .TMS            = TR  .TMS                                                                                                                          ");
    	sbSql.append("\n             AND    TOR .DAY_ORD        = TR  .DAY_ORD                                                                                                                      ");
    	sbSql.append("\n             AND    TOR .RACE_NO        = TR  .RACE_NO                                                                                                                      ");
    	sbSql.append("\n             AND    TOR .STND_YEAR      = TRT .STND_YEAR                                                                                                                    ");
    	sbSql.append("\n             AND    TOR .MBR_CD         = TRT .MBR_CD                                                                                                                       ");
    	sbSql.append("\n             AND    TOR .TMS            = TRT .TMS                                                                                                                          ");
    	sbSql.append("\n             AND    TOR .STND_YEAR      = TRS .STND_YEAR                                                                                                                    ");
    	sbSql.append("\n             AND    TR  .RACE_DGRE_CD   = TRS .RACE_DGRE_CD                                                                                                                 ");
    	sbSql.append("\n             AND    TOR .RANK           = TRS .RANK                                                                                                                         ");
    	sbSql.append("\n             AND    TOR .ABSE_CD       <> '003'                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .IMMT_CLS_CD   <> '003'                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .MBR_CD         = ?                                                                                                                                 ");
    	sbSql.append("\n             AND    TOR .RACE_DAY      <= (                                                                                                                                 ");
    	sbSql.append("\n                                             SELECT                                                                                                                         ");
    	sbSql.append("\n                                                    MAX(TRDO.RACE_DAY) RACE_DAY                                                                                             ");
    	sbSql.append("\n                                             FROM   TBEB_RACE_DAY_ORD TRDO                                                                                                  ");
    	sbSql.append("\n                                             WHERE  TRDO.STND_YEAR = ?                                                                                                      ");
    	sbSql.append("\n                                             AND    TRDO.MBR_CD    = ?                                                                                                      ");
    	sbSql.append("\n                                             AND    TRDO.TMS       = ?                                                                                                      ");
    	sbSql.append("\n                                          )                                                                                                                                 ");
    	sbSql.append("\n             GROUP BY BOAT_NO                                                                                                                                               ");
    	sbSql.append("\n          ) YEAR                                                                                                                                                            ");
    	sbSql.append("\n        , (                                                                                                                                                                 ");
    	sbSql.append("\n             -- 3회차 성적                                                                                                                                                  ");
    	sbSql.append("\n             SELECT                                                                                                                                                         ");
    	sbSql.append("\n                      BOAT_NO                                                                                                                                               ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TOR.INTRO_RUN_TM) , 90.99)) TMS_3_ITRDT_RUN_TM                                                                                       ");
    	sbSql.append("\n             FROM     TBEB_ORGAN        TOR                                                                                                                                 ");
    	sbSql.append("\n             WHERE  (TOR.STND_YEAR, TOR.MBR_CD, TOR.TMS, TOR.BOAT_NO) IN (                                                                                                  ");
    	sbSql.append("\n                                                                             SELECT                                                                                         ");
    	sbSql.append("\n                                                                                      TOR.STND_YEAR                                                                         ");
    	sbSql.append("\n                                                                                    , TOR.MBR_CD                                                                            ");
    	sbSql.append("\n                                                                                    , TOR.TMS                                                                               ");
    	sbSql.append("\n                                                                                    , TOR.BOAT_NO                                                                           ");
    	sbSql.append("\n                                                                             FROM   (                                                                                       ");
    	sbSql.append("\n                                                                                         SELECT                                                                             ");
    	sbSql.append("\n                                                                                                  TOR.STND_YEAR                                                             ");
    	sbSql.append("\n                                                                                                , TOR.MBR_CD                                                                ");
    	sbSql.append("\n                                                                                                , TOR.TMS                                                                   ");
    	sbSql.append("\n                                                                                                , TOR.BOAT_NO                                                               ");
    	sbSql.append("\n                                                                                                , RANK() OVER (PARTITION BY TOR.BOAT_NO                                      ");
    	sbSql.append("\n                                                                                                                   ORDER BY TOR.RACE_DAY DESC ) TMS_3                       ");
    	sbSql.append("\n                                                                                         FROM   (                                                                           ");
    	sbSql.append("\n                                                                                                     SELECT                                                                 ");
    	sbSql.append("\n                                                                                                              TOR.STND_YEAR                                                 ");
    	sbSql.append("\n                                                                                                            , TOR.MBR_CD                                                    ");
    	sbSql.append("\n                                                                                                            , TOR.TMS                                                       ");
    	sbSql.append("\n                                                                                                            , TOR.BOAT_NO                                                   ");
    	sbSql.append("\n                                                                                                            , MAX(TOR.RACE_DAY) RACE_DAY                                    ");
    	sbSql.append("\n                                                                                                     FROM   TBEB_ORGAN        TOR                                           ");
    	sbSql.append("\n                                                                                                     WHERE  TOR .RACE_DAY    <= (                                           ");
    	sbSql.append("\n                                                                                                                                     SELECT                                 ");
    	sbSql.append("\n                                                                                                                                            MAX(TRDO.RACE_DAY) RACE_DAY     ");
    	sbSql.append("\n                                                                                                                                     FROM   TBEB_RACE_DAY_ORD TRDO          ");
    	sbSql.append("\n                                                                                                                                     WHERE  TRDO.STND_YEAR = ?              ");
    	sbSql.append("\n                                                                                                                                     AND    TRDO.MBR_CD    = ?              ");
    	sbSql.append("\n                                                                                                                                     AND    TRDO.TMS       = ?              ");
    	sbSql.append("\n                                                                                                                                )                                           ");
    	sbSql.append("\n                                                                                                     AND    TOR.MBR_CD       = ?                                            ");
    	sbSql.append("\n                                                                                                     AND    TOR.ABSE_CD     <> '003'                                        ");
    	sbSql.append("\n                                                                                                     AND    TOR.IMMT_CLS_CD <> '003'                                        ");
    	sbSql.append("\n                                                                                                     GROUP BY                                                               ");
    	sbSql.append("\n                                                                                                              TOR.STND_YEAR                                                 ");
    	sbSql.append("\n                                                                                                            , TOR.MBR_CD                                                    ");
    	sbSql.append("\n                                                                                                            , TOR.TMS                                                       ");
    	sbSql.append("\n                                                                                                            , TOR.BOAT_NO                                                   ");
    	sbSql.append("\n                                                                                                ) TOR                                                                       ");
    	sbSql.append("\n                                                                                    ) TOR                                                                                   ");
    	sbSql.append("\n                                                                             WHERE TMS_3 <= 3                                                                               ");
    	sbSql.append("\n                                                                        )                                                                                                   ");
    	sbSql.append("\n             GROUP BY BOAT_NO                                                                                                                                                ");
    	sbSql.append("\n          ) TMS_3                                                                                                                                                           ");
    	sbSql.append("\n        , (                                                                                                                                                                 ");
    	sbSql.append("\n             -- 평균소개타임                                                                                                                                                ");
    	sbSql.append("\n             SELECT                                                                                                                                                         ");
    	sbSql.append("\n                      BOAT_NO                                                                                                                                               ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TOR.INTRO_RUN_TM) , 90.99))          AVG_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , MAX(TOR.RACE_TM)                                      MAX_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , MIN(TOR.RACE_TM)                                      MIN_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(STDDEV(TOR.INTRO_RUN_TM), 90.99))        ITRDT_RUN_TM_DVTN                                                                               ");
    	sbSql.append("\n             FROM     TBEB_ORGAN        TOR                                                                                                                                 ");
    	sbSql.append("\n             WHERE  TOR .MBR_CD         = ?                                                                                                                                 ");
    	sbSql.append("\n             AND    TOR .RACE_DAY      <= (                                                                                                                                 ");
    	sbSql.append("\n                                             SELECT                                                                                                                         ");
    	sbSql.append("\n                                                    MAX(TRDO.RACE_DAY) RACE_DAY                                                                                             ");
    	sbSql.append("\n                                             FROM   TBEB_RACE_DAY_ORD TRDO                                                                                                  ");
    	sbSql.append("\n                                             WHERE  TRDO.STND_YEAR = ?                                                                                                      ");
    	sbSql.append("\n                                             AND    TRDO.MBR_CD    = ?                                                                                                      ");
    	sbSql.append("\n                                             AND    TRDO.TMS       = ?                                                                                                      ");
    	sbSql.append("\n                                          )                                                                                                                                 ");
    	sbSql.append("\n             GROUP BY BOAT_NO                                                                                                                                               ");
    	sbSql.append("\n          ) BOAT                                                                                                                                                            ");
    	sbSql.append("\n WHERE  TE   .EQUIP_TPE_CD = 'B'                                                                                                                                            ");
    	sbSql.append("\n AND    TE   .EQUIP_NO     = BOAT .BOAT_NO(+)                                                                                                                               ");
    	sbSql.append("\n AND    TE   .EQUIP_NO     = YEAR .BOAT_NO(+)                                                                                                                               ");
    	sbSql.append("\n AND    TE   .EQUIP_NO     = TMS_3.BOAT_NO(+)                                                                                                                               ");
        	
        int nSize = 0;
        if ( ds != null ) 
        	nSize = ds.getRecordCount();

        if ( nSize > 0 ) {
        	sbSql.append("\n AND    BOAT.BOAT_NO IN (                                        ");
        	sbSql.append("\n                         SELECT                                ");
        	sbSql.append("\n                                 TOR.BOAT_NO                 ");
        	sbSql.append("\n                         FROM    TBEB_ORGAN TOR                ");
        	sbSql.append("\n                         WHERE   TOR.STND_YEAR    = ?          ");
        	sbSql.append("\n                         AND     TOR.MBR_CD       = ?          ");
        	sbSql.append("\n                         AND     TOR.TMS          = ?          ");
//        	sbSql.append("\n                         AND     TOR.ABSE_CD     <> '003'      ");
//        	sbSql.append("\n                         AND     TOR.IMMT_CLS_CD <> '003'      ");
        	sbSql.append("\n                         AND     TOR.RACER_NO IN (             ");
        }
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);

            if ( i == 0 ) {
            	sbSql.append(      "'" + record.getAttribute("RACER_NO") + "' \n");
            } else { 
            	sbSql.append("," + "'" + record.getAttribute("RACER_NO") + "' \n");
            }
        }
        
        if ( nSize > 0 ) {
        	sbSql.append("\n                                                 )             ");
        	sbSql.append("\n                        )                                        ");
        }
        
    	sbSql.append("\n ORDER BY TE   .EQUIP_NO                                                                                                                               ");
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, SESSION_USER_ID );
        param.setWhereClauseParameter(i++, SESSION_USER_ID );
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        if ( nSize > 0 ) {
	        param.setWhereClauseParameter(i++, sStndYear);
	        param.setWhereClauseParameter(i++, sMbrCd   );
	        param.setWhereClauseParameter(i++, sTms     );
        }

        PosRowSet rowSet =   this.getDao("boadao").findByQueryStatement(sbSql.toString(), param);
        return rowSet;
    }
    
    /**
     * <p> 회차별 보트 성적조회(플라잉/온라인) </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected PosRowSet searchBoatRecdAccuSumFlyOnl(PosDataset ds, String sStMthdCd)
    {
StringBuffer sbSql = new StringBuffer();
    	
    	sbSql.append("\n SELECT  /* SearchRecdCreate : searchBoatRecdAccuSumFlyOnl */                                                                                                                                                                   ");
    	sbSql.append("\n          DISTINCT(?)                                                                                 STND_YEAR                                                                               ");
    	sbSql.append("\n        , ?                                                                                           MBR_CD                                                                                  ");
    	sbSql.append("\n        , ?                                                                                           TMS                                                                                     ");
    	sbSql.append("\n        , TE   .EQUIP_NO                                                                              BOAT_NO                                                                                  ");
    	sbSql.append("\n        , SUBSTR(TE   .EQUIP_NO, 6, 3)                                                                VIEW_BOAT_NO                                                                        ");
    	sbSql.append("\n        , YEAR .RUN_CNT                                                                               RUN_CNT                                                                                 ");
    	sbSql.append("\n        , YEAR .RACE_DAY_CNT                                                                          RACE_DAY_CNT                                                                            ");
    	sbSql.append("\n        , YEAR .RACE_CNT                                                                              RACE_CNT                                                                                ");
    	sbSql.append("\n        , YEAR .RANK_1                                                                                RANK_1_CNT                                                                              ");
    	sbSql.append("\n        , YEAR .RANK_2                                                                                RANK_2_CNT                                                                              ");
    	sbSql.append("\n        , YEAR .RANK_3                                                                                RANK_3_CNT                                                                              ");
    	sbSql.append("\n        , YEAR .RANK_4                                                                                RANK_4_CNT                                                                              ");
    	sbSql.append("\n        , YEAR .RANK_5                                                                                RANK_5_CNT                                                                              ");
    	sbSql.append("\n        , YEAR .RANK_6                                                                                RANK_6_CNT                                                                              ");
    	sbSql.append("\n        , YEAR .AMU_RANK_SCR                                                                          AMU_RANK_SCR                                                                            ");
    	sbSql.append("\n        , YEAR .AVG_RANK_SCR                                                                          AVG_RANK_SCR                                                                            ");
    	sbSql.append("\n        , TRIM(TO_CHAR((YEAR .RANK_1) / YEAR .RACE_CNT * 100, 990.9))                                 WIN_RATIO                                                                               ");
    	sbSql.append("\n        , TRIM(TO_CHAR((YEAR .RANK_1 + YEAR .RANK_2) / YEAR .RACE_CNT * 100, 990.9))                  HIGH_RANK_RATIO                                                                         ");
    	sbSql.append("\n        , TRIM(TO_CHAR((YEAR .RANK_1 + YEAR .RANK_2 + YEAR .RANK_3) / YEAR .RACE_CNT * 100, 990.9))   HIGH_3_RANK_RATIO                                                                       ");
    	sbSql.append("\n        , TMS_3.TMS_3_ITRDT_RUN_TM                                                                    TMS_3_ITRDT_RUN_TM                                                                      ");
    	sbSql.append("\n        , YEAR .AVG_ITRDT_RUN_TM                                                                      AVG_ITRDT_RUN_TM                                                                        ");
    	sbSql.append("\n        , YEAR .MAX_ITRDT_RUN_TM                                                                      MAX_ITRDT_RUN_TM                                                                        ");
    	sbSql.append("\n        , YEAR .MIN_ITRDT_RUN_TM                                                                      MIN_ITRDT_RUN_TM                                                                        ");
    	sbSql.append("\n        , YEAR .ITRDT_RUN_TM_DVTN                                                                     ITRDT_RUN_TM_DVTN                                                                       ");
    	sbSql.append("\n        , ?                                                                                           INST_ID                                                                                 ");
    	sbSql.append("\n        , SYSDATE                                                                                     INST_DTM                                                                                ");
    	sbSql.append("\n        , ?                                                                                           UPDT_ID                                                                                 ");
    	sbSql.append("\n        , SYSDATE                                                                                     UPDT_DTM                                                                                ");
    	sbSql.append("\n FROM     TBEF_EQUIP   TE                                                                                                                                                   ");
    	sbSql.append("\n        , (                                                                                                                                                                 ");
    	sbSql.append("\n             -- 년도별 성적                                                                                                                                                 ");
    	sbSql.append("\n             SELECT                                                                                                                                                         ");
    	sbSql.append("\n                      TOR.BOAT_NO                                                                                                                                           ");
    	sbSql.append("\n                    , COUNT(DISTINCT(TOR.STND_YEAR || TOR.MBR_CD || TOR.TMS))                RUN_CNT                                                                                         ");
    	sbSql.append("\n                    , COUNT(DISTINCT(TOR.STND_YEAR || TOR.MBR_CD || TOR.TMS || TOR.DAY_ORD)) RACE_DAY_CNT                                                                                    ");
    	sbSql.append("\n                    , COUNT(*)                                              RACE_CNT                                                                                        ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 1, 1, 0))                        RANK_1                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 2, 1, 0))                        RANK_2                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 3, 1, 0))                        RANK_3                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 4, 1, 0))                        RANK_4                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 5, 1, 0))                        RANK_5                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 6, 1, 0))                        RANK_6                                                                                          ");
    	sbSql.append("\n                    , SUM(TRS.RACE_SCR)                                     AMU_RANK_SCR                                                                                    ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TRS.RACE_SCR), 90.99))               AVG_RANK_SCR                                                                                    ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TOR.INTRO_RUN_TM) , 90.99))          AVG_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , MAX(TOR.RACE_TM)                                      MAX_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , MIN(TOR.RACE_TM)                                      MIN_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(STDDEV(TOR.INTRO_RUN_TM), 90.99))        ITRDT_RUN_TM_DVTN                                                                               ");
    	sbSql.append("\n             FROM     TBEB_ORGAN         TOR                                                                                                                                ");
    	sbSql.append("\n                    , TBEB_RACE_TMS      TRT                                                                                                                                ");
    	sbSql.append("\n                    , TBEB_RACE          TR                                                                                                                                 ");
    	sbSql.append("\n                    , TBEB_RANK_SCR      TRS                                                                                                                                ");
    	sbSql.append("\n             WHERE  TOR .STND_YEAR      = TR  .STND_YEAR                                                                                                                    ");
    	sbSql.append("\n             AND    TOR .MBR_CD         = TR  .MBR_CD                                                                                                                       ");
    	sbSql.append("\n             AND    TOR .TMS            = TR  .TMS                                                                                                                          ");
    	sbSql.append("\n             AND    TOR .DAY_ORD        = TR  .DAY_ORD                                                                                                                      ");
    	sbSql.append("\n             AND    TOR .RACE_NO        = TR  .RACE_NO                                                                                                                      ");
    	sbSql.append("\n             AND    TOR .STND_YEAR      = TRT .STND_YEAR                                                                                                                    ");
    	sbSql.append("\n             AND    TOR .MBR_CD         = TRT .MBR_CD                                                                                                                       ");
    	sbSql.append("\n             AND    TOR .TMS            = TRT .TMS                                                                                                                          ");
    	sbSql.append("\n             AND    TOR .STND_YEAR      = TRS .STND_YEAR                                                                                                                    ");
    	sbSql.append("\n             AND    TR  .RACE_DGRE_CD   = TRS .RACE_DGRE_CD                                                                                                                 ");
    	sbSql.append("\n             AND    TOR .RANK           = TRS .RANK                                                                                                                         ");
    	sbSql.append("\n             AND    TOR .ABSE_CD       <> '003'                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .IMMT_CLS_CD   <> '003'                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .MBR_CD         = ?                                                                                                                                 ");
    	sbSql.append("\n             AND    TOR.ST_MTHD_CD   = '"+sStMthdCd+"'                                                                                                                      ");
    	sbSql.append("\n             AND    TOR .RACE_DAY      <= (                                                                                                                                 ");
    	sbSql.append("\n                                             SELECT                                                                                                                         ");
    	sbSql.append("\n                                                    MAX(TRDO.RACE_DAY) RACE_DAY                                                                                             ");
    	sbSql.append("\n                                             FROM   TBEB_RACE_DAY_ORD TRDO                                                                                                  ");
    	sbSql.append("\n                                             WHERE  TRDO.STND_YEAR = ?                                                                                                      ");
    	sbSql.append("\n                                             AND    TRDO.MBR_CD    = ?                                                                                                      ");
    	sbSql.append("\n                                             AND    TRDO.TMS       = ?                                                                                                      ");
    	sbSql.append("\n                                          )                                                                                                                                 ");
    	sbSql.append("\n             GROUP BY BOAT_NO                                                                                                                                               ");
    	sbSql.append("\n          ) YEAR                                                                                                                                                            ");
    	sbSql.append("\n        , (                                                                                                                                                                 ");
    	sbSql.append("\n             -- 3회차 성적                                                                                                                                                  ");
    	sbSql.append("\n             SELECT                                                                                                                                                         ");
    	sbSql.append("\n                      BOAT_NO                                                                                                                                               ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TOR.INTRO_RUN_TM) , 90.99)) TMS_3_ITRDT_RUN_TM                                                                                       ");
    	sbSql.append("\n             FROM     TBEB_ORGAN        TOR                                                                                                                                 ");
    	sbSql.append("\n             WHERE  (TOR.STND_YEAR, TOR.MBR_CD, TOR.TMS, TOR.BOAT_NO) IN (                                                                                                  ");
    	sbSql.append("\n                                                                             SELECT                                                                                         ");
    	sbSql.append("\n                                                                                      TOR.STND_YEAR                                                                         ");
    	sbSql.append("\n                                                                                    , TOR.MBR_CD                                                                            ");
    	sbSql.append("\n                                                                                    , TOR.TMS                                                                               ");
    	sbSql.append("\n                                                                                    , TOR.BOAT_NO                                                                           ");
    	sbSql.append("\n                                                                             FROM   (                                                                                       ");
    	sbSql.append("\n                                                                                         SELECT                                                                             ");
    	sbSql.append("\n                                                                                                  TOR.STND_YEAR                                                             ");
    	sbSql.append("\n                                                                                                , TOR.MBR_CD                                                                ");
    	sbSql.append("\n                                                                                                , TOR.TMS                                                                   ");
    	sbSql.append("\n                                                                                                , TOR.BOAT_NO                                                               ");
    	sbSql.append("\n                                                                                                , RANK() OVER (PARTITION BY TOR.BOAT_NO                                      ");
    	sbSql.append("\n                                                                                                                   ORDER BY TOR.RACE_DAY DESC ) TMS_3                       ");
    	sbSql.append("\n                                                                                         FROM   (                                                                           ");
    	sbSql.append("\n                                                                                                     SELECT                                                                 ");
    	sbSql.append("\n                                                                                                              TOR.STND_YEAR                                                 ");
    	sbSql.append("\n                                                                                                            , TOR.MBR_CD                                                    ");
    	sbSql.append("\n                                                                                                            , TOR.TMS                                                       ");
    	sbSql.append("\n                                                                                                            , TOR.BOAT_NO                                                   ");
    	sbSql.append("\n                                                                                                            , MAX(TOR.RACE_DAY) RACE_DAY                                    ");
    	sbSql.append("\n                                                                                                     FROM   TBEB_ORGAN        TOR                                           ");
    	sbSql.append("\n                                                                                                     WHERE  TOR .RACE_DAY    <= (                                           ");
    	sbSql.append("\n                                                                                                                                     SELECT                                 ");
    	sbSql.append("\n                                                                                                                                            MAX(TRDO.RACE_DAY) RACE_DAY     ");
    	sbSql.append("\n                                                                                                                                     FROM   TBEB_RACE_DAY_ORD TRDO          ");
    	sbSql.append("\n                                                                                                                                     WHERE  TRDO.STND_YEAR = ?              ");
    	sbSql.append("\n                                                                                                                                     AND    TRDO.MBR_CD    = ?              ");
    	sbSql.append("\n                                                                                                                                     AND    TRDO.TMS       = ?              ");
    	sbSql.append("\n                                                                                                                                )                                           ");
    	sbSql.append("\n                                                                                                     AND    TOR.MBR_CD       = ?                                            ");
    	sbSql.append("\n                                                                                                     AND    TOR.ABSE_CD     <> '003'                                        ");
    	sbSql.append("\n                                                                                                     AND    TOR.IMMT_CLS_CD <> '003'                                        ");
    	sbSql.append("\n                                                                                                     AND    TOR.ST_MTHD_CD   = '"+sStMthdCd+"'                              ");
    	sbSql.append("\n                                                                                                     GROUP BY                                                               ");
    	sbSql.append("\n                                                                                                              TOR.STND_YEAR                                                 ");
    	sbSql.append("\n                                                                                                            , TOR.MBR_CD                                                    ");
    	sbSql.append("\n                                                                                                            , TOR.TMS                                                       ");
    	sbSql.append("\n                                                                                                            , TOR.BOAT_NO                                                   ");
    	sbSql.append("\n                                                                                                ) TOR                                                                       ");
    	sbSql.append("\n                                                                                    ) TOR                                                                                   ");
    	sbSql.append("\n                                                                             WHERE TMS_3 <= 3                                                                               ");
    	sbSql.append("\n                                                                        )                                                                                                   ");
    	sbSql.append("\n             GROUP BY BOAT_NO                                                                                                                                               ");
    	sbSql.append("\n          ) TMS_3                                                                                                                                                           ");
    	sbSql.append("\n        , (                                                                                                                                                                 ");
    	sbSql.append("\n             -- 평균소개타임                                                                                                                                                ");
    	sbSql.append("\n             SELECT                                                                                                                                                         ");
    	sbSql.append("\n                      BOAT_NO                                                                                                                                               ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TOR.INTRO_RUN_TM) , 90.99))          AVG_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , MAX(TOR.RACE_TM)                                      MAX_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , MIN(TOR.RACE_TM)                                      MIN_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(STDDEV(TOR.INTRO_RUN_TM), 90.99))        ITRDT_RUN_TM_DVTN                                                                               ");
    	sbSql.append("\n             FROM     TBEB_ORGAN        TOR                                                                                                                                 ");
    	sbSql.append("\n             WHERE  TOR .MBR_CD         = ?                                                                                                                                 ");
    	sbSql.append("\n             AND    TOR .ST_MTHD_CD   = '"+sStMthdCd+"'                                                                                                                      ");
    	sbSql.append("\n             AND    TOR .RACE_DAY      <= (                                                                                                                                 ");
    	sbSql.append("\n                                             SELECT                                                                                                                         ");
    	sbSql.append("\n                                                    MAX(TRDO.RACE_DAY) RACE_DAY                                                                                             ");
    	sbSql.append("\n                                             FROM   TBEB_RACE_DAY_ORD TRDO                                                                                                  ");
    	sbSql.append("\n                                             WHERE  TRDO.STND_YEAR = ?                                                                                                      ");
    	sbSql.append("\n                                             AND    TRDO.MBR_CD    = ?                                                                                                      ");
    	sbSql.append("\n                                             AND    TRDO.TMS       = ?                                                                                                      ");
    	sbSql.append("\n                                          )                                                                                                                                 ");
    	sbSql.append("\n             GROUP BY BOAT_NO                                                                                                                                               ");
    	sbSql.append("\n          ) BOAT                                                                                                                                                            ");
    	sbSql.append("\n WHERE  TE   .EQUIP_TPE_CD = 'B'                                                                                                                                            ");
    	sbSql.append("\n AND    TE   .EQUIP_NO     = BOAT .BOAT_NO(+)                                                                                                                               ");
    	sbSql.append("\n AND    TE   .EQUIP_NO     = YEAR .BOAT_NO(+)                                                                                                                               ");
    	sbSql.append("\n AND    TE   .EQUIP_NO     = TMS_3.BOAT_NO(+)                                                                                                                               ");
        	
        int nSize = 0;
        if ( ds != null ) 
        	nSize = ds.getRecordCount();

        if ( nSize > 0 ) {
        	sbSql.append("\n AND    BOAT.BOAT_NO IN (                                        ");
        	sbSql.append("\n                         SELECT                                ");
        	sbSql.append("\n                                 TOR.BOAT_NO                 ");
        	sbSql.append("\n                         FROM    TBEB_ORGAN TOR                ");
        	sbSql.append("\n                         WHERE   TOR.STND_YEAR    = ?          ");
        	sbSql.append("\n                         AND     TOR.MBR_CD       = ?          ");
        	sbSql.append("\n                         AND     TOR.TMS          = ?          ");
        	sbSql.append("\n                         AND     TOR.ST_MTHD_CD   = '"+sStMthdCd+"'");
//        	sbSql.append("\n                         AND     TOR.ABSE_CD     <> '003'      ");
//        	sbSql.append("\n                         AND     TOR.IMMT_CLS_CD <> '003'      ");
        	sbSql.append("\n                         AND     TOR.RACER_NO IN (             ");
        }
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);

            if ( i == 0 ) {
            	sbSql.append(      "'" + record.getAttribute("RACER_NO") + "' \n");
            } else { 
            	sbSql.append("," + "'" + record.getAttribute("RACER_NO") + "' \n");
            }
        }
        
        if ( nSize > 0 ) {
        	sbSql.append("\n                                                 )             ");
        	sbSql.append("\n                        )                                        ");
        }
        
    	sbSql.append("\n ORDER BY TE   .EQUIP_NO                                                                                                                               ");
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, SESSION_USER_ID );
        param.setWhereClauseParameter(i++, SESSION_USER_ID );
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sStndYear);  
        param.setWhereClauseParameter(i++, sMbrCd   );  
        param.setWhereClauseParameter(i++, sTms     );  
        if ( nSize > 0 ) {
	        param.setWhereClauseParameter(i++, sStndYear);
	        param.setWhereClauseParameter(i++, sMbrCd   );
	        param.setWhereClauseParameter(i++, sTms     );
        }

        PosRowSet rowSet =   this.getDao("boadao").findByQueryStatement(sbSql.toString(), param);
        return rowSet;
    }    
}
