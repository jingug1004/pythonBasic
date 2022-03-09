DROP VIEW USRBM.VW_LINK_CROSS;

/* Formatted on 2017-03-18 오전 10:59:01 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_LINK_CROSS
(
   RACE_DAY,
   RACE_NO,
   SELL
)
AS
   WITH A
           AS (  SELECT 4 sell_cd, B.race_day, B.race_no
                   FROM VIEW_SALES_BCRC B, VW_SDL_INFO I
                  WHERE     B.stnd_year = I.stnd_year
                        AND B.race_day = I.RACE_DAY
                        AND B.meet_cd = '001'
                        AND B.meet_cd = I.meet_cd
                        AND B.tms > '00'
                        AND b.race_no > '00'
               GROUP BY B.race_day, B.race_no
               UNION ALL
                 SELECT 2 sell_cd, B.race_day, B.race_no
                   FROM VIEW_SALES_CCRC B, VW_SDL_INFO I
                  WHERE     B.stnd_year = I.stnd_year
                        AND B.race_day = I.race_day
                        AND B.meet_cd = '001'
                        AND B.meet_cd = I.meet_cd
                        AND B.tms > '00'
                        AND b.race_no > '00'
               GROUP BY B.race_day, B.race_no)
     SELECT race_day,
            race_no,
            DECODE (SUM (sell_cd), 2, '창', 4, '부', '창부') sell
       FROM A
      WHERE 1 = 1
   --AND race_day = '20151009'
   GROUP BY race_day, race_no
   ORDER BY 1, 2;
