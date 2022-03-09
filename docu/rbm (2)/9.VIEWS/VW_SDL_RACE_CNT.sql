DROP VIEW USRBM.VW_SDL_RACE_CNT;

/* Formatted on 2017-03-18 ¿ÀÀü 11:07:36 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_SDL_RACE_CNT
(
   STND_YEAR,
   YEARMM,
   MEET_CD,
   RACE_CNT
)
AS
     SELECT stnd_year,
            SUBSTR (race_day, 1, 6) yearmm,
            meet_cd,
            COUNT ( * ) race_cnt
       FROM VW_SDL_INFO
   GROUP BY meet_cd, stnd_year, SUBSTR (race_day, 1, 6)
   ORDER BY meet_cd, stnd_year, SUBSTR (race_day, 1, 6);
