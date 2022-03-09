DROP VIEW USRBM.VW_EIS_BRNC_SUM;

/* Formatted on 2017-03-18 오전 10:58:39 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_EIS_BRNC_SUM
(
   MEET_CD,
   STND_YEAR,
   TMS,
   BRNC_CD,
   BRNC_NM,
   DIV_TOTAL
)
AS
     SELECT MEET_CD,                                                   -- 본장구분
            STND_YEAR,                                                   -- 년도
            TMS,                                                         -- 회차
            --COMM_NO_NEW AS BRNC_CD,                                      -- 지점
            TO_NUMBER (SUBSTR (C.CD_TERM1, 2)) AS BRNC_CD,
            C.CD_NM AS BRNC_NM,                                         -- 지점명
            SUM (DIV_TOTAL) DIV_TOTAL                                   -- 매출액
       FROM (  SELECT B.MEET_CD,
                      B.STND_YEAR,
                      B.TMS,
                      A.SELL_CD,
                      A.COMM_NO,
                      A.DIV_NO,
                      SUM (DIV_TOTAL) DIV_TOTAL,
                      CASE
                         WHEN SELL_CD IN ('01', '03')
                              AND COMM_NO IN ('01', '02', '03', '04', '08')
                         THEN
                            '01'                                   --본장(경륜,경정)
                         WHEN     SELL_CD = '01'
                              AND COMM_NO >= '11'
                              AND COMM_NO <= '28'
                         THEN
                            COMM_NO
                         WHEN     SELL_CD IN ('01', '03')
                              AND COMM_NO = '06'
                              AND DIV_NO = '00'
                         THEN
                            '01'
                         WHEN     SELL_CD IN ('01', '03')
                              AND COMM_NO = '06'
                              AND DIV_NO = '15'
                         THEN
                            '25'
                         WHEN     SELL_CD IN ('01', '03')
                              AND COMM_NO = '06'
                              AND DIV_NO = '17'
                         THEN
                            '27'
                         ELSE
                            COMM_NO
                      END
                         COMM_NO_NEW                                    --지점코드
                 FROM VW_SDL_DT_SUM A,                                   -- 지점
                                      VW_SDL_INFO B                  -- 경주일 정보
                WHERE     1 = 1
                      AND A.MEET_CD = B.MEET_CD
                      AND A.STND_YEAR = B.STND_YEAR
                      AND A.TMS = B.TMS
                      AND A.DAY_ORD = B.DAY_ORD
                      AND SELL_CD IN ('01', '03')
             -- 경륜,경정,교차,지점별 쿼리 분기
             GROUP BY B.MEET_CD,
                      B.STND_YEAR,
                      B.TMS,
                      A.SELL_CD,
                      A.COMM_NO,
                      A.DIV_NO   -- , ROLLUP(B.TMS) -- 최종 레코드의  TOTAL, SUM 생성용
                              ) X,
            TBRK_SPEC_CD C
      WHERE X.COMM_NO_NEW = C.CD(+) AND C.GRP_CD = '060'
   GROUP BY MEET_CD,
            STND_YEAR,
            TMS,
            TO_NUMBER (SUBSTR (C.CD_TERM1, 2)),
            CD_NM;
