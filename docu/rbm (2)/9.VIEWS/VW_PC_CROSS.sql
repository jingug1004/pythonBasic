DROP VIEW USRBM.VW_PC_CROSS;

/* Formatted on 2017-03-18 오전 11:00:30 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_PC_CROSS
(
   STND_YEAR,
   SELL,
   TMS,
   DAY_ORD,
   RACE_NO
)
AS
     SELECT stnd_year,
            DECODE (SUM (sell_cd), 2, '창', 4, '부', '창부') sell,
            tms,
            day_ord,
            race_no
       FROM TBJI_PC_PAYOFFS
      WHERE meet_cd = '001'                 --AND day_ord = '1' AND tms = '12'
                           AND pool_cd = '001' AND sell_cd <> '01'
   GROUP BY stnd_year,
            tms,
            day_ord,
            race_no;
