DROP VIEW USRBM.VW_PC_RESULT;

/* Formatted on 2017-03-18 ���� 11:03:06 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_PC_RESULT
(
   MEET_CD,
   STND_YEAR,
   TMS,
   DAY_ORD,
   SELL_CD,
   RACE_NO,
   POOL_CD,
   RLT_NUM,
   RLT_RUN_NO,
   WIN_AMT,
   ROW_ODDS,
   UNIT_ODDS,
   INCE_TAX_RATE,
   INH_TAX_RATE,
   INST_DT,
   UPDT_DT,
   SESSION_NO,
   PERF_NO
)
AS
   (SELECT B.MEET_CD,
           B.STND_YEAR,
           B.TMS,
           B.DAY_ORD,
           B.SELL_CD,
           B.RACE_NO,
           B.POOL_CD,
           B.RLT_NUM,
           B.RLT_RUN_NO,
           B.WIN_AMT,
           B.ROW_ODDS,
           B.UNIT_ODDS,
           B.INCE_TAX_RATE,
           B.INH_TAX_RATE,
           B.INST_DT,
           NVL (B.UPDT_DT, B.INST_DT) UPDT_DT,
           NVL (TRIM (B.SESSION_NO), 0) SESSION_NO,
           NVL (TRIM (B.PERF_NO), 0) PERF_NO
      FROM TBJI_PC_FILE_VERI A, TBJI_PC_RESULT B
     WHERE     1 = 1
           AND A.MEET_CD = B.MEET_CD
           AND A.STND_YEAR = B.STND_YEAR
           AND A.TMS = B.TMS
           AND A.DAY_ORD = B.DAY_ORD
           AND A.FILE_VERI = '001'
           AND A.VERI = '001');


GRANT SELECT ON USRBM.VW_PC_RESULT TO ACCT;

GRANT SELECT ON USRBM.VW_PC_RESULT TO INTRA;

GRANT SELECT ON USRBM.VW_PC_RESULT TO VWRBM;
