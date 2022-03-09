DROP VIEW USRBM.VW_SALES_TMS_CUM;

/* Formatted on 2017-03-18 오전 11:06:02 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_SALES_TMS_CUM
(
   MEET_CD,
   STND_YEAR,
   TMS
)
AS
   WITH X
           AS (  SELECT MEET_CD, STND_YEAR, MAX (TMS) AS TMS
                   FROM VW_SDL_INFO
                  WHERE RACE_DAY <
                           TO_CHAR (
                              SYSDATE
                              - DECODE (TO_CHAR (SYSDATE, 'd'),
                                        5, 1,
                                        7, 1,
                                        1, 2,
                                        0),
                              'yyyymmdd')
                        AND MEET_CD IN ('001', '003')
                        AND STND_YEAR = TO_CHAR (SYSDATE, 'YYYY')
               GROUP BY MEET_CD, STND_YEAR)
     SELECT B.MEET_CD, B.STND_YEAR, B.TMS
       FROM VW_SDL_INFO B, X
      WHERE     B.MEET_CD = X.MEET_CD
            AND B.TMS <= X.TMS
            AND B.STND_YEAR BETWEEN X.STND_YEAR - 1 AND X.STND_YEAR
   GROUP BY B.MEET_CD, B.STND_YEAR, B.TMS
   ORDER BY 3, 1, 2;
COMMENT ON TABLE USRBM.VW_SALES_TMS_CUM IS '직전회차까지의 누적회차 리스트';

COMMENT ON COLUMN USRBM.VW_SALES_TMS_CUM.MEET_CD IS '시행처 코드';

COMMENT ON COLUMN USRBM.VW_SALES_TMS_CUM.STND_YEAR IS '기주년도';

COMMENT ON COLUMN USRBM.VW_SALES_TMS_CUM.TMS IS '회차';
