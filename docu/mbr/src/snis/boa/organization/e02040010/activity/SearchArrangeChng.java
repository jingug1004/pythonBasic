/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02030030.activity.SaveArrange.java
 * 파일설명		: 주선 등록
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02040010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 착순점/사고점를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SearchArrangeChng extends SnisActivity
{    
    public SearchArrangeChng()
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
    	StringBuffer sbSql = new StringBuffer();
    	PosRowSet rowset;
    	
    	 /* IWORK_SFR-008 경정 선수편성 메뉴 개선. 8명까지, 경주일 3일   */

    	// 변경 가능한 선수 조회
    	// 출주 선수중에서 조회
        if ( ctx.get("ENTER_RACER").equals("1") ) {
	        sbSql.append("\n SELECT                                                                                                        ");
	        sbSql.append("\n          TRM  .RACER_NO                                                                                       ");
	        sbSql.append("\n        , TRM  .RACER_GRD_CD                                                                                   ");
	        sbSql.append("\n        , TRM  .NM_KOR                                                                                         ");
	        sbSql.append("\n        , NVL(TRBRA.RACE_ALLOC_CNT, TSRC.STND_RACE_CNT) RACE_ALLOC_CNT                                         ");
	        sbSql.append("\n        , CNT  .RACE_CNT                                                                                       ");
	        sbSql.append("\n        , TRRAS.TMS_6_AVG_RANK_SCR                                                                             ");
	        sbSql.append("\n        , TRRAS.TMS_6_AVG_SCR                                                                                  ");
	        sbSql.append("\n        , NVL(SUM(DECODE(TOR_TOT.RACE_REG_NO, 1, TOR_TOT.TOT_RUN_CNT)), 0) TOT_RUN_CNT_1                       ");
	        sbSql.append("\n        , NVL(SUM(DECODE(TOR_TOT.RACE_REG_NO, 2, TOR_TOT.TOT_RUN_CNT)), 0) TOT_RUN_CNT_2                       ");
	        sbSql.append("\n        , NVL(SUM(DECODE(TOR_TOT.RACE_REG_NO, 3, TOR_TOT.TOT_RUN_CNT)), 0) TOT_RUN_CNT_3                       ");
	        sbSql.append("\n        , NVL(SUM(DECODE(TOR_TOT.RACE_REG_NO, 4, TOR_TOT.TOT_RUN_CNT)), 0) TOT_RUN_CNT_4                       ");
	        sbSql.append("\n        , NVL(SUM(DECODE(TOR_TOT.RACE_REG_NO, 5, TOR_TOT.TOT_RUN_CNT)), 0) TOT_RUN_CNT_5                       ");
	        sbSql.append("\n        , NVL(SUM(DECODE(TOR_TOT.RACE_REG_NO, 6, TOR_TOT.TOT_RUN_CNT)), 0) TOT_RUN_CNT_6                       ");
	        sbSql.append("\n        , NVL(SUM(DECODE(TOR_TOT.RACE_REG_NO, 7, TOR_TOT.TOT_RUN_CNT)), 0) TOT_RUN_CNT_7                       ");
	        sbSql.append("\n        , NVL(SUM(DECODE(TOR_TOT.RACE_REG_NO, 8, TOR_TOT.TOT_RUN_CNT)), 0) TOT_RUN_CNT_8                       ");
	        sbSql.append("\n        , RACE1.PRE_RACE PRE_RACE1                                                                             ");
	        sbSql.append("\n        , RACE2.PRE_RACE PRE_RACE2                                                                             ");
	        sbSql.append("\n        , RACE3.PRE_RACE PRE_RACE3                                                                             ");
	        sbSql.append("\n        , TOR  .MOT_NO                                                                                         ");
	        sbSql.append("\n        , SUBSTR(TOR  .MOT_NO, 6, 3) VIEW_MOT_NO                                                               ");
	        sbSql.append("\n        , TOR  .BOAT_NO                                                                                        ");
	        sbSql.append("\n        , SUBSTR(TOR  .BOAT_NO, 6, 3) VIEW_BOAT_NO                                                             ");
	       	sbSql.append("\n FROM     TBEC_RACER_MASTER         TRM                                                                        ");
	        sbSql.append("\n        , TBEB_ORGAN                TOR                                                                        ");
	        sbSql.append("\n        , (                                                                                                    ");
	        sbSql.append("\n             SELECT                                                                                            ");
	        sbSql.append("\n                      TSRC.RACER_GRD_CD                                                                        ");
	        sbSql.append("\n                    , TSRC.STND_RACE_CNT                                                                       ");
	        sbSql.append("\n             FROM   TBEB_STND_RACE_CNT        TSRC                                                             ");
	        sbSql.append("\n             WHERE (STND_YEAR, QURT_CD) IN (                                                                   ");
	        sbSql.append("\n                                             SELECT                                                            ");
	        sbSql.append("\n                                                      STND_YEAR                                                ");
	        sbSql.append("\n                                                    , QURT_CD                                                  ");
	        sbSql.append("\n                                             FROM   TBEB_RACE_TMS                                              ");
	        sbSql.append("\n                                             WHERE  STND_YEAR      = ?                                         ");
	        sbSql.append("\n                                             AND    MBR_CD         = ?                                         ");
	        sbSql.append("\n                                             AND    TMS            = ?                                         ");
	        sbSql.append("\n                                           )                                                                   ");
	        sbSql.append("\n           ) TSRC                                                                                              ");
	        sbSql.append("\n         , (                                                                                                   ");
	        sbSql.append("\n             SELECT                                                                                            ");
	        sbSql.append("\n                      TRBRA.RACER_NO                                                                           ");
	        sbSql.append("\n                    , TRBRA.STND_YEAR                                                                          ");
	        sbSql.append("\n                    , TRBRA.QURT_CD                                                                            ");
	        sbSql.append("\n                    , TRBRA.RACE_ALLOC_CNT                                                                     ");
	        sbSql.append("\n             FROM   TBEB_RACER_BAC_RACE_ALLOC TRBRA                                                            ");
	        sbSql.append("\n             WHERE (STND_YEAR, QURT_CD) IN (                                                                   ");
	        sbSql.append("\n                                             SELECT                                                            ");
	        sbSql.append("\n                                                      STND_YEAR                                                ");
	        sbSql.append("\n                                                    , QURT_CD                                                  ");
	        sbSql.append("\n                                             FROM   TBEB_RACE_TMS                                              ");
	        sbSql.append("\n                                             WHERE  STND_YEAR      = ?                                         ");
	        sbSql.append("\n                                             AND    MBR_CD         = ?                                         ");
	        sbSql.append("\n                                             AND    TMS            = ?                                         ");
	        sbSql.append("\n                                           )                                                                   ");
	        sbSql.append("\n           ) TRBRA                                                                                             ");
	        sbSql.append("\n         , (                                                                                                   ");
	        sbSql.append("\n             SELECT                                                                                            ");
	        sbSql.append("\n                      RACER_NO                                                                                 ");
	        sbSql.append("\n                    , COUNT(*) RACE_CNT                                                                        ");
	        sbSql.append("\n             FROM   TBEB_ORGAN                                                                                 ");
	        sbSql.append("\n             WHERE  ABSE_CD     <> '003'                                                                       ");
	        sbSql.append("\n             AND    IMMT_CLS_CD <> '003'                                                                       ");
	        sbSql.append("\n             AND    (STND_YEAR, MBR_CD, TMS) IN (                                                              ");
	        sbSql.append("\n 		                                            SELECT                                                     ");
	        sbSql.append("\n 		                                                     STND_YEAR                                         ");
	        sbSql.append("\n 		                                                   , MBR_CD                                            ");
	        sbSql.append("\n 		                                                   , TMS                                               ");
	        sbSql.append("\n 		                                            FROM   TBEB_RACE_TMS                                       ");
	        sbSql.append("\n 		                                            WHERE (STND_YEAR, QURT_CD) IN (                            ");
	        sbSql.append("\n 		                                                                            SELECT                     ");
	        sbSql.append("\n 		                                                                                     STND_YEAR         ");
	        sbSql.append("\n 		                                                                                   , QURT_CD           ");
	        sbSql.append("\n 		                                                                            FROM   TBEB_RACE_TMS       ");
	        sbSql.append("\n 		                                                                            WHERE  STND_YEAR      = ?  ");
	        sbSql.append("\n 		                                                                            AND    MBR_CD         = ?  ");
	        sbSql.append("\n 		                                                                            AND    TMS            = ?  ");
	        sbSql.append("\n 		                                                                          )                            ");
	        sbSql.append("\n                                        		   )                                                           ");
	        sbSql.append("\n             GROUP BY RACER_NO                                                                                 ");
	        sbSql.append("\n          ) CNT                                                                                                ");
	        sbSql.append("\n        , (                                                                                                    ");
	        sbSql.append("\n 			SELECT *                                                                                           ");
	        sbSql.append("\n 			FROM   (                                                                                           ");
	        sbSql.append("\n 			            SELECT   RANK() OVER (PARTITION BY TRRAS.RACER_NO ORDER BY APLY_DT DESC) RANK          ");
	        sbSql.append("\n 			                   , TRRAS.*                                                                       ");
	        sbSql.append("\n 			            FROM   TBEB_RACER_RECD_ACCU_SUM TRRAS                                                  ");
	        sbSql.append("\n 			            WHERE  TRRAS.APLY_DT <= (                                                              ");
	        sbSql.append("\n 			                                        SELECT                                                     ");
	        sbSql.append("\n 			                                               MAX(TRDO.RACE_DAY) RACE_DAY                         ");
	        sbSql.append("\n 			                                        FROM   TBEB_RACE_DAY_ORD TRDO                              ");
	        sbSql.append("\n 			                                        WHERE  TRDO.STND_YEAR = ?                                  ");
	        sbSql.append("\n 			                                        AND    TRDO.MBR_CD    = ?                                  ");
	        sbSql.append("\n 			                                        AND    TRDO.TMS       = ?                                  ");
	        sbSql.append("\n 			                                    )                                                              ");
	        sbSql.append("\n 			            AND   MBR_CD = '000'                                                                   ");
	        sbSql.append("\n 			            AND   TMS    = 0                                                                       ");
	        sbSql.append("\n 			            AND   TRRAS.ST_MTHD_CD = '000' --ST방식전체설정                                        ");
	        sbSql.append("\n 			       )                                                                                           ");
	        sbSql.append("\n 			WHERE  RANK = 1                                                                                    ");
	        sbSql.append("\n          ) TRRAS                                                                                              ");
	        sbSql.append("\n        , (                                                                                                    ");
	        sbSql.append("\n            SELECT                                                                                             ");
	    	sbSql.append("\n                   RACER_NO                                                                                    ");
	    	sbSql.append("\n                 , RACE_REG_NO                                                                                 ");
	    	sbSql.append("\n                 , COUNT(*) TOT_RUN_CNT                                                                        ");
	        sbSql.append("\n            FROM   TBEB_ORGAN                                                                                  ");
	        sbSql.append("\n            WHERE  STND_YEAR    = ?                                                                            ");
	        sbSql.append("\n            AND    RACE_DAY    <= (                                                                            ");
	    	sbSql.append("\n 			                        SELECT MAX(TRDO.RACE_DAY) RACE_DAY                                         ");
	    	sbSql.append("\n 			                        FROM   TBEB_RACE_DAY_ORD TRDO                                              ");
	    	sbSql.append("\n 			                        WHERE  TRDO.STND_YEAR      = ?                                             ");
	    	sbSql.append("\n 			                        AND    TRDO.MBR_CD         = ?                                             ");
	    	sbSql.append("\n 			                        AND    TRDO.TMS            = ?                                             ");
	    	sbSql.append("\n 			                      )                                                                            ");
	    	sbSql.append("\n            GROUP BY                                                                                           ");
	    	sbSql.append("\n                   RACER_NO                                                                                    ");
	    	sbSql.append("\n                 , RACE_REG_NO                                                                                 ");
	        sbSql.append("\n          ) TOR_TOT -- 년도누적 출주수                                                                                                                                                        ");
	        sbSql.append("\n        , (                                                                                                    "); 
	        sbSql.append("\n            SELECT                                                                                             ");
	        sbSql.append("\n                     RACER_NO                                                                                  ");
	        sbSql.append("\n                   , SUBSTR(MAX(SYS_CONNECT_BY_PATH (RACE_DESC, ', ')), 2) PRE_RACE                            ");
	        sbSql.append("\n            FROM   (                                                                                           ");
	        sbSql.append("\n                        SELECT                                                                                 ");
	        sbSql.append("\n                                  TOR.RACER_NO                                                                 ");
	        sbSql.append("\n                                , TOR.RACE_NO                                                                  ");
	        sbSql.append("\n                                , TOR.RACE_REG_NO                                                              ");
	        sbSql.append("\n                                , DAY_ORD || '-' || RACE_NO || '/' || RACE_REG_NO RACE_DESC                    ");
	        sbSql.append("\n                                , RANK() OVER (PARTITION BY TOR.RACER_NO ORDER BY DAY_ORD, RACE_NO) RACE       ");
	        sbSql.append("\n                        FROM    TBEB_ORGAN TOR                                                                 ");
	        sbSql.append("\n                        WHERE   TOR.STND_YEAR = ?                                                              ");
	        sbSql.append("\n                        AND     TOR.MBR_CD    = ?                                                              ");
	        sbSql.append("\n                        AND     TOR.TMS       = ?                                                              ");
	        sbSql.append("\n                        AND     TOR.DAY_ORD   = 1                                                              ");
	        sbSql.append("\n                    )                                                                                          ");
	        sbSql.append("\n            START WITH RACE = 1                                                                                ");
	        sbSql.append("\n            CONNECT BY PRIOR RACE = RACE - 1 AND PRIOR RACER_NO = RACER_NO                                     ");
	        sbSql.append("\n            GROUP BY RACER_NO                                                                                  ");
	        sbSql.append("\n          ) RACE1                                                                                              "); 
	        sbSql.append("\n        , (                                                                                                    "); 
	        sbSql.append("\n            SELECT                                                                                             ");
	        sbSql.append("\n                     RACER_NO                                                                                  ");
	        sbSql.append("\n                   , SUBSTR(MAX(SYS_CONNECT_BY_PATH (RACE_DESC, ', ')), 2) PRE_RACE                            ");
	        sbSql.append("\n            FROM   (                                                                                           ");
	        sbSql.append("\n                        SELECT                                                                                 ");
	        sbSql.append("\n                                  TOR.RACER_NO                                                                 ");
	        sbSql.append("\n                                , TOR.RACE_NO                                                                  ");
	        sbSql.append("\n                                , TOR.RACE_REG_NO                                                              ");
	        sbSql.append("\n                                , DAY_ORD || '-' || RACE_NO || '/' || RACE_REG_NO RACE_DESC                    ");
	        sbSql.append("\n                                , RANK() OVER (PARTITION BY TOR.RACER_NO ORDER BY DAY_ORD, RACE_NO) RACE       ");
	        sbSql.append("\n                        FROM    TBEB_ORGAN TOR                                                                 ");
	        sbSql.append("\n                        WHERE   TOR.STND_YEAR = ?                                                              ");
	        sbSql.append("\n                        AND     TOR.MBR_CD    = ?                                                              ");
	        sbSql.append("\n                        AND     TOR.TMS       = ?                                                              ");
	        sbSql.append("\n                        AND     TOR.DAY_ORD   = 2                                                              ");
	        sbSql.append("\n                    )                                                                                          ");
	        sbSql.append("\n            START WITH RACE = 1                                                                                ");
	        sbSql.append("\n            CONNECT BY PRIOR RACE = RACE - 1 AND PRIOR RACER_NO = RACER_NO                                     ");
	        sbSql.append("\n            GROUP BY RACER_NO                                                                                  ");
	        sbSql.append("\n          ) RACE2                                                                                              "); 
	        sbSql.append("\n        , (                                                                                                    "); 
	        sbSql.append("\n            SELECT                                                                                             ");
	        sbSql.append("\n                     RACER_NO                                                                                  ");
	        sbSql.append("\n                   , SUBSTR(MAX(SYS_CONNECT_BY_PATH (RACE_DESC, ', ')), 2) PRE_RACE                            ");
	        sbSql.append("\n            FROM   (                                                                                           ");
	        sbSql.append("\n                        SELECT                                                                                 ");
	        sbSql.append("\n                                  TOR.RACER_NO                                                                 ");
	        sbSql.append("\n                                , TOR.RACE_NO                                                                  ");
	        sbSql.append("\n                                , TOR.RACE_REG_NO                                                              ");
	        sbSql.append("\n                                , DAY_ORD || '-' || RACE_NO || '/' || RACE_REG_NO RACE_DESC                    ");
	        sbSql.append("\n                                , RANK() OVER (PARTITION BY TOR.RACER_NO ORDER BY DAY_ORD, RACE_NO) RACE       ");
	        sbSql.append("\n                        FROM    TBEB_ORGAN TOR                                                                 ");
	        sbSql.append("\n                        WHERE   TOR.STND_YEAR = ?                                                              ");
	        sbSql.append("\n                        AND     TOR.MBR_CD    = ?                                                              ");
	        sbSql.append("\n                        AND     TOR.TMS       = ?                                                              ");
	        sbSql.append("\n                        AND     TOR.DAY_ORD   = 3                                                              ");
	        sbSql.append("\n                    )                                                                                          ");
	        sbSql.append("\n            START WITH RACE = 1                                                                                ");
	        sbSql.append("\n            CONNECT BY PRIOR RACE = RACE - 1 AND PRIOR RACER_NO = RACER_NO                                     ");
	        sbSql.append("\n            GROUP BY RACER_NO                                                                                  ");
	        sbSql.append("\n          ) RACE3                                                                                              "); 	        
	        sbSql.append("\n WHERE  TRM  .RACER_NO         = TRBRA.RACER_NO (+)                                                            ");
	        sbSql.append("\n AND    TRM  .RACER_NO         = CNT  .RACER_NO (+)                                                            ");
	        sbSql.append("\n AND    TRM  .RACER_NO         = TRRAS.RACER_NO (+)                                                            ");
	        sbSql.append("\n AND    TRM  .RACER_GRD_CD     = TSRC .RACER_GRD_CD(+)                                                         ");
	        sbSql.append("\n AND    TRM  .RACER_NO         = TOR_TOT.RACER_NO(+)                                                           ");
            sbSql.append("\n AND    TRM  .RACER_NO         = RACE1.RACER_NO(+)                                                             ");
            sbSql.append("\n AND    TRM  .RACER_NO         = RACE2.RACER_NO(+)                                                             ");
            sbSql.append("\n AND    TRM  .RACER_NO         = RACE3.RACER_NO(+)                                                             ");
	        sbSql.append("\n AND    TRM  .RACER_NO     IN (                                                                                ");
	        sbSql.append("\n                                 SELECT                                                                        ");
	        sbSql.append("\n                                          TA.RACER_NO                                                          ");
	        sbSql.append("\n                                 FROM     TBEB_ORGAN TA                                                        ");
	        sbSql.append("\n                                 WHERE  TA.STND_YEAR = ?                                                       ");
	        sbSql.append("\n                                 AND    TA.MBR_CD    = ?                                                       ");
	        sbSql.append("\n                                 AND    TA.TMS       = ?                                                       ");
	        sbSql.append("\n                                 AND    TA.DAY_ORD   = ?                                                       ");
	        sbSql.append("\n                                 AND    ( TO_NUMBER(TA.RACE_NO) < TO_NUMBER(?) - ?                             ");
	        sbSql.append("\n                                       OR TO_NUMBER(TA.RACE_NO) > TO_NUMBER(?) + ?)                            ");
	        sbSql.append("\n                              )                                                                                ");
	        sbSql.append("\n AND    TRM  .RACER_NO NOT IN (                                                                                ");
	        sbSql.append("\n                                 SELECT                                                                        ");
//	        sbSql.append("\n                                          TA.RACER_NO                                                          ");
	        sbSql.append("\n                                          NVL(TA.RACER_NO,'99-999')                                            ");
	        sbSql.append("\n                                 FROM     TBEB_ORGAN TA                                                        ");
	        sbSql.append("\n                                 WHERE  TA.STND_YEAR = ?                                                       ");
	        sbSql.append("\n                                 AND    TA.MBR_CD    = ?                                                       ");
	        sbSql.append("\n                                 AND    TA.TMS       = ?                                                       ");
	        sbSql.append("\n                                 AND    TA.DAY_ORD   = ?                                                       ");
	        sbSql.append("\n                                 GROUP BY TA.RACER_NO                                                          ");
	        sbSql.append("\n                                 HAVING COUNT(*) > 1                                                           ");
	        sbSql.append("\n                              )                                                                                ");
            sbSql.append("\n AND    TRM  .RACER_NO         = TOR.RACER_NO                                                                  ");
	        sbSql.append("\n AND    TOR  .STND_YEAR        = ?                                                                             ");
	        sbSql.append("\n AND    TOR  .MBR_CD           = ?                                                                             ");
	        sbSql.append("\n AND    TOR  .TMS              = ?                                                                             ");
	        sbSql.append("\n AND    TOR  .DAY_ORD          = ?                                                                             ");
	        
	        if ( !(ctx.get("RACE_REG_NO") == null || ctx.get("RACE_REG_NO").equals("")) ) {
		        sbSql.append("\n AND    TRM  .RACER_NO NOT IN (                                                                            ");
		        sbSql.append("\n                                 SELECT                                                                    ");
//		        sbSql.append("\n                                          TA.RACER_NO                                                      ");
		        sbSql.append("\n                                          NVL(TA.RACER_NO,'99-999')                                        ");
		        sbSql.append("\n                                 FROM     TBEB_ORGAN TA                                                    ");
		        sbSql.append("\n                                 WHERE  TA.STND_YEAR    = ?                                                ");
		        sbSql.append("\n                                 AND    TA.MBR_CD       = ?                                                ");
		        sbSql.append("\n                                 AND    TA.TMS          = ?                                                ");
//		        sbSql.append("\n                                 AND    TA.DAY_ORD      = ?                                                ");
		        sbSql.append("\n                                 AND    TA.RACE_REG_NO  = ?                                                ");
		        sbSql.append("\n                              )                                                                                ");
	        }
		    sbSql.append("\n AND    TRM  .NM_KOR        LIKE '%' || ? || '%'                                                               ");
		    sbSql.append("\n AND    TRM  .RACER_GRD_CD  LIKE NVL(?, TRM  .RACER_GRD_CD)                                                    ");
		    sbSql.append("\n AND    TRRAS.TMS_6_AVG_SCR LIKE ? || '%'                                                                      ");
		    sbSql.append("\n GROUP BY TRM  .RACER_NO                                                                                       ");
		    sbSql.append("\n        , TRM  .RACER_GRD_CD                                                                                   ");
		    sbSql.append("\n        , TRM  .NM_KOR                                                                                         ");
		    sbSql.append("\n        , NVL(TRBRA.RACE_ALLOC_CNT, TSRC.STND_RACE_CNT)                                                        ");
		    sbSql.append("\n        , CNT  .RACE_CNT                                                                                       ");
		    sbSql.append("\n        , TRRAS.TMS_6_AVG_RANK_SCR                                                                             ");
		    sbSql.append("\n        , TRRAS.TMS_6_AVG_SCR                                                                                  ");
	        sbSql.append("\n        , RACE1.PRE_RACE                                                                                       ");
	        sbSql.append("\n        , RACE2.PRE_RACE                                                                                       ");
	        sbSql.append("\n        , RACE3.PRE_RACE                                                                                       ");
	        sbSql.append("\n        , TOR  .MOT_NO                                                                                         ");
	        sbSql.append("\n        , TOR  .BOAT_NO                                                                                        ");
		    sbSql.append("\n ORDER BY TRM  .RACER_NO                                                                                       ");

	    	PosParameter param = new PosParameter();
	        int i = 0;
        
	        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));  
	        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));  
	        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));  
	        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));  
	        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));  
	        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));  
	        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));  
	        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));  
	        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));  
	        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));  
	        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));  
	        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));  
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"  ));  
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"  ));  
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"     ));  
            param.setWhereClauseParameter(i++, ctx.get("TMS"        ));
            
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"  ));  
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"     ));  
            param.setWhereClauseParameter(i++, ctx.get("TMS"        ));
            
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"  ));  
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"     ));  
            param.setWhereClauseParameter(i++, ctx.get("TMS"        )); 
            
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"  ));  
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"     ));  
            param.setWhereClauseParameter(i++, ctx.get("TMS"        ));
            
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"  ));  
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"     ));  
            param.setWhereClauseParameter(i++, ctx.get("TMS"        ));  
            param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"    ));  
            param.setWhereClauseParameter(i++, ctx.get("RACE_NO"    ));  
        	param.setWhereClauseParameter(i++, ctx.get("RACE_GAP"   ));
            param.setWhereClauseParameter(i++, ctx.get("RACE_NO"    ));  
        	param.setWhereClauseParameter(i++, ctx.get("RACE_GAP"   ));
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"  ));  
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"     ));  
            param.setWhereClauseParameter(i++, ctx.get("TMS"        ));  
            param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"    ));  
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"  ));  
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"     ));  
            param.setWhereClauseParameter(i++, ctx.get("TMS"        ));  
            param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"    ));  
	        if ( !(ctx.get("RACE_REG_NO") == null || ctx.get("RACE_REG_NO").equals("")) ) {
	            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"  ));  
	            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"     ));  
	            param.setWhereClauseParameter(i++, ctx.get("TMS"        ));  
//	            param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"    ));  
	            param.setWhereClauseParameter(i++, ctx.get("RACE_REG_NO"));  
	        }
	        param.setWhereClauseParameter(i++, ctx.get("NM_KOR"      ));  
	        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_CD"));  
	        param.setWhereClauseParameter(i++, ctx.get("AVG_SCR"     ));  

	        rowset = this.getDao("boadao").findByQueryStatement(sbSql.toString(), param);
        } else {
        	// 출주 제외선수중에서 조회
	        sbSql.append("\n SELECT                                                                                                        ");
	        sbSql.append("\n          TRM  .RACER_NO                                                                                       ");
	        sbSql.append("\n        , TRM  .RACER_GRD_CD                                                                                   ");
	        sbSql.append("\n        , TRM  .NM_KOR                                                                                         ");
	        sbSql.append("\n        , NVL(TRBRA.RACE_ALLOC_CNT, TSRC.STND_RACE_CNT) RACE_ALLOC_CNT                                         ");
	        sbSql.append("\n        , CNT  .RACE_CNT                                                                                       ");
	        sbSql.append("\n        , TRRAS.TMS_6_AVG_RANK_SCR                                                                             ");
	        sbSql.append("\n        , TRRAS.TMS_6_AVG_SCR                                                                                  ");
	        sbSql.append("\n        , NVL(SUM(DECODE(TOR_TOT.RACE_REG_NO, 1, TOR_TOT.TOT_RUN_CNT)), 0) TOT_RUN_CNT_1                       ");
	        sbSql.append("\n        , NVL(SUM(DECODE(TOR_TOT.RACE_REG_NO, 2, TOR_TOT.TOT_RUN_CNT)), 0) TOT_RUN_CNT_2                       ");
	        sbSql.append("\n        , NVL(SUM(DECODE(TOR_TOT.RACE_REG_NO, 3, TOR_TOT.TOT_RUN_CNT)), 0) TOT_RUN_CNT_3                       ");
	        sbSql.append("\n        , NVL(SUM(DECODE(TOR_TOT.RACE_REG_NO, 4, TOR_TOT.TOT_RUN_CNT)), 0) TOT_RUN_CNT_4                       ");
	        sbSql.append("\n        , NVL(SUM(DECODE(TOR_TOT.RACE_REG_NO, 5, TOR_TOT.TOT_RUN_CNT)), 0) TOT_RUN_CNT_5                       ");
	        sbSql.append("\n        , NVL(SUM(DECODE(TOR_TOT.RACE_REG_NO, 6, TOR_TOT.TOT_RUN_CNT)), 0) TOT_RUN_CNT_6                       ");
	        sbSql.append("\n        , NVL(SUM(DECODE(TOR_TOT.RACE_REG_NO, 7, TOR_TOT.TOT_RUN_CNT)), 0) TOT_RUN_CNT_7                       ");
	        sbSql.append("\n        , NVL(SUM(DECODE(TOR_TOT.RACE_REG_NO, 8, TOR_TOT.TOT_RUN_CNT)), 0) TOT_RUN_CNT_8                       ");
	       	sbSql.append("\n FROM     TBEC_RACER_MASTER         TRM                                                                        ");
	        sbSql.append("\n        , (                                                                                                    ");
	        sbSql.append("\n             SELECT                                                                                            ");
	        sbSql.append("\n                      TSRC.RACER_GRD_CD                                                                        ");
	        sbSql.append("\n                    , TSRC.STND_RACE_CNT                                                                       ");
	        sbSql.append("\n             FROM   TBEB_STND_RACE_CNT        TSRC                                                             ");
	        sbSql.append("\n             WHERE (STND_YEAR, QURT_CD) IN (                                                                   ");
	        sbSql.append("\n                                             SELECT                                                            ");
	        sbSql.append("\n                                                      STND_YEAR                                                ");
	        sbSql.append("\n                                                    , QURT_CD                                                  ");
	        sbSql.append("\n                                             FROM   TBEB_RACE_TMS                                              ");
	        sbSql.append("\n                                             WHERE  STND_YEAR      = ?                                         ");
	        sbSql.append("\n                                             AND    MBR_CD         = ?                                         ");
	        sbSql.append("\n                                             AND    TMS            = ?                                         ");
	        sbSql.append("\n                                           )                                                                   ");
	        sbSql.append("\n           ) TSRC                                                                                              ");
	        sbSql.append("\n         , (                                                                                                   ");
	        sbSql.append("\n             SELECT                                                                                            ");
	        sbSql.append("\n                      TRBRA.RACER_NO                                                                           ");
	        sbSql.append("\n                    , TRBRA.STND_YEAR                                                                          ");
	        sbSql.append("\n                    , TRBRA.QURT_CD                                                                            ");
	        sbSql.append("\n                    , TRBRA.RACE_ALLOC_CNT                                                                     ");
	        sbSql.append("\n             FROM   TBEB_RACER_BAC_RACE_ALLOC TRBRA                                                            ");
	        sbSql.append("\n             WHERE (STND_YEAR, QURT_CD) IN (                                                                   ");
	        sbSql.append("\n                                             SELECT                                                            ");
	        sbSql.append("\n                                                      STND_YEAR                                                ");
	        sbSql.append("\n                                                    , QURT_CD                                                  ");
	        sbSql.append("\n                                             FROM   TBEB_RACE_TMS                                              ");
	        sbSql.append("\n                                             WHERE  STND_YEAR      = ?                                         ");
	        sbSql.append("\n                                             AND    MBR_CD         = ?                                         ");
	        sbSql.append("\n                                             AND    TMS            = ?                                         ");
	        sbSql.append("\n                                           )                                                                   ");
	        sbSql.append("\n           ) TRBRA                                                                                             ");
	        sbSql.append("\n         , (                                                                                                   ");
	        sbSql.append("\n             SELECT                                                                                            ");
	        sbSql.append("\n                      RACER_NO                                                                                 ");
	        sbSql.append("\n                    , COUNT(*) RACE_CNT                                                                        ");
	        sbSql.append("\n             FROM   TBEB_ORGAN                                                                                 ");
	        sbSql.append("\n             WHERE  ABSE_CD     <> '003'                                                                       ");
	        sbSql.append("\n             AND    IMMT_CLS_CD <> '003'                                                                       ");
	        sbSql.append("\n             AND    (STND_YEAR, MBR_CD, TMS) IN (                                                              ");
	        sbSql.append("\n 		                                            SELECT                                                     ");
	        sbSql.append("\n 		                                                     STND_YEAR                                         ");
	        sbSql.append("\n 		                                                   , MBR_CD                                            ");
	        sbSql.append("\n 		                                                   , TMS                                               ");
	        sbSql.append("\n 		                                            FROM   TBEB_RACE_TMS                                       ");
	        sbSql.append("\n 		                                            WHERE (STND_YEAR, QURT_CD) IN (                            ");
	        sbSql.append("\n 		                                                                            SELECT                     ");
	        sbSql.append("\n 		                                                                                     STND_YEAR         ");
	        sbSql.append("\n 		                                                                                   , QURT_CD           ");
	        sbSql.append("\n 		                                                                            FROM   TBEB_RACE_TMS       ");
	        sbSql.append("\n 		                                                                            WHERE  STND_YEAR      = ?  ");
	        sbSql.append("\n 		                                                                            AND    MBR_CD         = ?  ");
	        sbSql.append("\n 		                                                                            AND    TMS            = ?  ");
	        sbSql.append("\n 		                                                                          )                            ");
	        sbSql.append("\n                                        		   )                                                           ");
	        sbSql.append("\n             GROUP BY RACER_NO                                                                                 ");
	        sbSql.append("\n          ) CNT                                                                                                ");
	        sbSql.append("\n        , (                                                                                                    ");
	        sbSql.append("\n 			SELECT *                                                                                           ");
	        sbSql.append("\n 			FROM   (                                                                                           ");
	        sbSql.append("\n 			            SELECT   RANK() OVER (PARTITION BY TRRAS.RACER_NO ORDER BY APLY_DT DESC) RANK          ");
	        sbSql.append("\n 			                   , TRRAS.*                                                                       ");
	        sbSql.append("\n 			            FROM   TBEB_RACER_RECD_ACCU_SUM TRRAS                                                  ");
	        sbSql.append("\n 			            WHERE  TRRAS.APLY_DT <= (                                                              ");
	        sbSql.append("\n 			                                        SELECT                                                     ");
	        sbSql.append("\n 			                                               MAX(TRDO.RACE_DAY) RACE_DAY                         ");
	        sbSql.append("\n 			                                        FROM   TBEB_RACE_DAY_ORD TRDO                              ");
	        sbSql.append("\n 			                                        WHERE  TRDO.STND_YEAR = ?                                  ");
	        sbSql.append("\n 			                                        AND    TRDO.MBR_CD    = ?                                  ");
	        sbSql.append("\n 			                                        AND    TRDO.TMS       = ?                                  ");
	        sbSql.append("\n 			                                    )                                                              ");
	        sbSql.append("\n 			            AND   MBR_CD = '000'                                                                   ");
	        sbSql.append("\n 			            AND   TMS    = 0                                                                       ");
	        sbSql.append("\n 			            AND   TRRAS.ST_MTHD_CD = '000' --ST방식전체설정                                        ");
	        sbSql.append("\n 			       )                                                                                           ");
	        sbSql.append("\n 			WHERE  RANK = 1                                                                                    ");
	        sbSql.append("\n          ) TRRAS                                                                                              ");
	        sbSql.append("\n        , (                                                                                                    ");
	        sbSql.append("\n            SELECT                                                                                             ");
	    	sbSql.append("\n                   RACER_NO                                                                                    ");
	    	sbSql.append("\n                 , RACE_REG_NO                                                                                 ");
	    	sbSql.append("\n                 , COUNT(*) TOT_RUN_CNT                                                                        ");
	        sbSql.append("\n            FROM   TBEB_ORGAN                                                                                  ");
	        sbSql.append("\n            WHERE  STND_YEAR    = ?                                                                            ");
	        sbSql.append("\n            AND    RACE_DAY    <= (                                                                            ");
	    	sbSql.append("\n 			                        SELECT MAX(TRDO.RACE_DAY) RACE_DAY                                         ");
	    	sbSql.append("\n 			                        FROM   TBEB_RACE_DAY_ORD TRDO                                              ");
	    	sbSql.append("\n 			                        WHERE  TRDO.STND_YEAR      = ?                                             ");
	    	sbSql.append("\n 			                        AND    TRDO.MBR_CD         = ?                                             ");
	    	sbSql.append("\n 			                        AND    TRDO.TMS            = ?                                             ");
	    	sbSql.append("\n 			                      )                                                                            ");
	    	sbSql.append("\n            GROUP BY                                                                                           ");
	    	sbSql.append("\n                   RACER_NO                                                                                    ");
	    	sbSql.append("\n                 , RACE_REG_NO                                                                                 ");
	        sbSql.append("\n          ) TOR_TOT -- 년도누적 출주수                                                                                                                                                        ");
	        sbSql.append("\n WHERE  TRM  .RACER_NO         = TRBRA.RACER_NO (+)                                                            ");
	        sbSql.append("\n AND    TRM  .RACER_NO         = CNT  .RACER_NO (+)                                                            ");
	        sbSql.append("\n AND    TRM  .RACER_NO         = TRRAS.RACER_NO (+)                                                            ");
	        sbSql.append("\n AND    TRM  .RACER_GRD_CD     = TSRC .RACER_GRD_CD(+)                                                         ");
	        sbSql.append("\n AND    TRM  .RACER_NO         = TOR_TOT.RACER_NO(+)                                                           ");
	        sbSql.append("\n AND    TRM  .RACER_NO NOT IN (                                                                                ");
	        sbSql.append("\n                                 SELECT                                                                        ");
	        sbSql.append("\n                                          TAE.RACER_NO                                                         ");
	        sbSql.append("\n                                 FROM     TBEB_RACE_TMS             TRT                                        ");
	        sbSql.append("\n                                        , TBEC_ARRANGE_ESC          TAE                                        ");
	        sbSql.append("\n                                 WHERE  TRT.STR_DT < TAE.END_DT                                                ");
	        sbSql.append("\n                                 AND    TRT.END_DT > TAE.STR_DT                                                ");
	        sbSql.append("\n                                 AND    TRT.STND_YEAR = ?                                                      ");
	        sbSql.append("\n                                 AND    TRT.MBR_CD    = ?                                                      ");
	        sbSql.append("\n                                 AND    TRT.TMS       = ?                                                      ");
	        sbSql.append("\n                              )                                                                                ");
	        sbSql.append("\n AND    TRM  .RACER_NO NOT IN (                                                                                ");
	        sbSql.append("\n                                 SELECT                                                                        ");
	        sbSql.append("\n                                          TA.RACER_NO                                                          ");
	        sbSql.append("\n                                 FROM     TBEB_ARRANGE TA                                                      ");
	        sbSql.append("\n                                 WHERE  TA.STND_YEAR = ?                                                       ");
	        sbSql.append("\n                                 AND    TA.MBR_CD    = ?                                                       ");
	        sbSql.append("\n                                 AND    TA.TMS       = ?                                                       ");
	        sbSql.append("\n                              )                                                                                ");
		    sbSql.append("\n AND    TRM  .NM_KOR        LIKE '%' || ? || '%'                                                               ");
		    sbSql.append("\n AND    TRM  .RACER_GRD_CD  LIKE NVL(?, TRM  .RACER_GRD_CD)                                                    ");
		    sbSql.append("\n AND    TRRAS.TMS_6_AVG_SCR LIKE ? || '%'                                                                      ");
		    sbSql.append("\n GROUP BY TRM  .RACER_NO                                                                                       ");
		    sbSql.append("\n        , TRM  .RACER_GRD_CD                                                                                   ");
		    sbSql.append("\n        , TRM  .NM_KOR                                                                                         ");
		    sbSql.append("\n        , NVL(TRBRA.RACE_ALLOC_CNT, TSRC.STND_RACE_CNT)                                                        ");
		    sbSql.append("\n        , CNT  .RACE_CNT                                                                                       ");
		    sbSql.append("\n        , TRRAS.TMS_6_AVG_RANK_SCR                                                                             ");
		    sbSql.append("\n        , TRRAS.TMS_6_AVG_SCR                                                                                  ");

	    	PosParameter param = new PosParameter();
	        int i = 0;
        
	        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));  
	        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));  
	        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));  
	        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));  
	        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));  
	        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));  
	        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));  
	        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));  
	        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));  
	        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));  
	        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));  
	        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));  
	        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));  
	        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));  
	        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));  
	        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));  
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"  ));  
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"     ));  
            param.setWhereClauseParameter(i++, ctx.get("TMS"        ));  
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"  ));  
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"     ));  
            param.setWhereClauseParameter(i++, ctx.get("TMS"        ));  
	        param.setWhereClauseParameter(i++, ctx.get("NM_KOR"      ));  
	        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_CD"));  
	        param.setWhereClauseParameter(i++, ctx.get("AVG_SCR"     ));  

	        rowset = this.getDao("boadao").findByQueryStatement(sbSql.toString(), param);
        }

    	String sResultKey = "dsOutRacerList";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
}
