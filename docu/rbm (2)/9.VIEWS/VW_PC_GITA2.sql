DROP VIEW USRBM.VW_PC_GITA2;

/* Formatted on 2017-03-18 ¿ÀÀü 11:02:34 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_PC_GITA2
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
   SELECT MEET_CD,
          SELL_CD,
          TSN,
          PERF_NO,
          CASE WHEN COMM_NO > 10 THEN '07' ELSE COMM_NO END COMM_NO,
          CASE
             WHEN COMM_NO = '11' THEN '01'
             WHEN COMM_NO = '12' THEN '02'
             WHEN COMM_NO = '13' THEN '03'
             WHEN COMM_NO = '14' THEN '05'
             WHEN COMM_NO = '15' THEN '04'
             WHEN COMM_NO = '16' THEN '06'
             WHEN COMM_NO = '17' THEN '07'
             WHEN COMM_NO = '18' THEN '08'
             WHEN COMM_NO = '19' THEN '09'
             WHEN COMM_NO = '20' THEN '10'
             WHEN COMM_NO = '21' THEN '11'
             WHEN COMM_NO = '22' THEN '12'
             WHEN COMM_NO = '23' THEN '13'
             WHEN COMM_NO = '24' THEN '14'
             WHEN COMM_NO = '25' THEN '15'
             WHEN COMM_NO = '26' THEN '16'
             WHEN COMM_NO = '27' THEN '17'
             WHEN COMM_NO = '28' THEN '18'
             ELSE DIV_NO
          END
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
     FROM USRBM.VW_PC_GITA;


GRANT SELECT ON USRBM.VW_PC_GITA2 TO ACCT;

GRANT SELECT ON USRBM.VW_PC_GITA2 TO INTRA;

GRANT SELECT ON USRBM.VW_PC_GITA2 TO VWRBM;
