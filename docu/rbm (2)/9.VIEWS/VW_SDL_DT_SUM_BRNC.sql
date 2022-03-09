DROP VIEW USRBM.VW_SDL_DT_SUM_BRNC;

/* Formatted on 2017-03-18 오전 11:06:52 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_SDL_DT_SUM_BRNC
(
   MEET_CD,
   STND_YEAR,
   TMS,
   BRNC_CD,
   BRNC_NM,
   DIV_TOTAL
)
AS
     SELECT MEET_CD,                 -- 본장구분   (부산/창원 경륜 제공용 지점별 매출액 조회용 VIEW)
            STND_YEAR,                                                   -- 년도
            TMS,                                                         -- 회차
            COMM_NO_NEW AS BRNC_CD,                                      -- 지점
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
                         WHEN SELL_CD IN ('01', '03') AND COMM_NO = '06'
                         THEN
                            COMM_NO
                         WHEN SELL_CD NOT IN ('01', '03')
                         THEN
                            '29'
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
            COMM_NO_NEW,
            CD_NM;
COMMENT ON TABLE USRBM.VW_SDL_DT_SUM_BRNC IS '지점 매출액(창원조회용)';

COMMENT ON COLUMN USRBM.VW_SDL_DT_SUM_BRNC.MEET_CD IS '경주장 구분 ';

COMMENT ON COLUMN USRBM.VW_SDL_DT_SUM_BRNC.STND_YEAR IS '경주년도 ';

COMMENT ON COLUMN USRBM.VW_SDL_DT_SUM_BRNC.TMS IS '회차 ';

COMMENT ON COLUMN USRBM.VW_SDL_DT_SUM_BRNC.BRNC_CD IS '지점코드 ';

COMMENT ON COLUMN USRBM.VW_SDL_DT_SUM_BRNC.BRNC_NM IS '지점코드';

COMMENT ON COLUMN USRBM.VW_SDL_DT_SUM_BRNC.DIV_TOTAL IS '지점별 매출액 ';


DROP SYNONYM CCRC.VW_SDL_DT_SUM_BRNC;

CREATE SYNONYM CCRC.VW_SDL_DT_SUM_BRNC FOR USRBM.VW_SDL_DT_SUM_BRNC;


GRANT SELECT ON USRBM.VW_SDL_DT_SUM_BRNC TO BCRC;

GRANT SELECT ON USRBM.VW_SDL_DT_SUM_BRNC TO CCRC;

GRANT SELECT ON USRBM.VW_SDL_DT_SUM_BRNC TO US_CRA;
