DROP VIEW USRBM.VW_SDL_SUM_DAY;

/* Formatted on 2017-03-18 ¿ÀÀü 11:08:04 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_SDL_SUM_DAY
(
   STND_YEAR,
   TMS,
   MEET_CD,
   DAY_ORD,
   RACE_NO,
   AMT
)
AS
     SELECT stnd_year,
            tms,
            meet_cd,
            day_ord,
            race_no,
            SUM (div_total) amt
       FROM TBES_SDL_DT
      WHERE stnd_year = '2014'            --AND tms = '48' AND meet_cd = '001'
   GROUP BY day_ord,
            race_no,
            stnd_year,
            tms,
            meet_cd
   ORDER BY 1, 2;


GRANT SELECT ON USRBM.VW_SDL_SUM_DAY TO US_CRA;
