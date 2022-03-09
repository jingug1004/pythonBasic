DROP VIEW USRBM.VW_BUS_SALES_CRA;

/* Formatted on 2017-03-18 ¿ÀÀü 10:57:29 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_BUS_SALES_CRA
(
   RACE_DAY,
   MEET_CD,
   STND_YEAR,
   TMS,
   DAY_ORD,
   RACE_NO,
   SELL_CD,
   COMM_NO,
   DIV_NO,
   POOL_TOTAL,
   WIN,
   PLACE,
   EXACTA,
   QUINELLA,
   TRIELLA
)
AS
   SELECT RACE_DAY,
          MEET_CD,
          STND_YEAR,
          TMS,
          DAY_ORD,
          RACE_NO,
          '04' SELL_CD,
          '01' COMM_NO,
          '01' AS DIV_NO,
          DIV_TOTAL AS POOL_TOTAL,
          WIN,
          PLACE,
          EXACTA,
          QUINELLA,
          TRIELLA
     FROM BCRC.VW_BUS_SALES@BCRCDBLINK
    WHERE MEET_CD <> '003';
