DROP VIEW USRBM.VW_PC_GITA;

/* Formatted on 2017-03-18 ¿ÀÀü 11:00:41 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_PC_GITA
(
   MEET_CD,
   SELL_CD,
   TSN,
   PERF_NO,
   COMM_NO,
   DIV_NO,
   REFUND,
   SELL_AMT,
   JIGEUP_AMT,
   COST,
   GITA1,
   GITA2,
   GITA_PAY,
   JIGEUP_DT,
   INST_DT,
   UPDT_DT
)
AS
   (SELECT MEET_CD,
           SELL_CD,
           TSN,
           PERF_NO,
           COMM_NO,
           DIV_NO,
           REFUND,
           SELL_AMT,
           JIGEUP_AMT,
           COST,
           GITA1,
           GITA2,
           GITA_PAY,
           JIGEUP_DT,
           INST_DT,
           NVL (UPDT_DT, INST_DT)
      FROM TBJI_PC_GITA);


GRANT SELECT ON USRBM.VW_PC_GITA TO ACCT;
