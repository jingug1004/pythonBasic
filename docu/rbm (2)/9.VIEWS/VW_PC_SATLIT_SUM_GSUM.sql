DROP VIEW USRBM.VW_PC_SATLIT_SUM_GSUM;

/* Formatted on 2017-03-18 ¿ÀÀü 11:03:48 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_PC_SATLIT_SUM_GSUM
(
   MEET_CD,
   STND_YEAR,
   TMS,
   DAY_ORD,
   SELL_CD,
   COMM_NO,
   DIV_NO,
   TOTAL_AMT,
   REFUND,
   NET_AMT
)
AS
     SELECT MEET_CD,
            STND_YEAR,
            TMS,
            DAY_ORD,
            SELL_CD,
            COMM_NO,
            DIV_NO,
            SUM (TOTAL_AMT) AS TOTAL_AMT,
            SUM (REFUND) AS REFUND,
            SUM (NET_AMT) AS NET_AMT
       FROM (SELECT SM.MEET_CD,
                    SM.STND_YEAR,
                    SM.TMS,
                    SM.DAY_ORD,
                    SM.SELL_CD,
                    CASE
                       WHEN SM.COMM_NO = '06' THEN C.CD_NM2
                       ELSE SM.COMM_NO
                    END
                       AS COMM_NO,
                    CASE WHEN SM.COMM_NO = '06' THEN '01' ELSE SM.DIV_NO END
                       AS DIV_NO,
                    SM.TOTAL_AMT,
                    SM.REFUND,
                    SM.NET_AMT
               FROM VW_PC_SATLIT_SUM SM, TBRK_SPEC_CD C
              WHERE 1 = 1 AND C.GRP_CD(+) = '127' AND C.CD(+) = SM.DIV_NO)
   GROUP BY MEET_CD,
            STND_YEAR,
            TMS,
            DAY_ORD,
            SELL_CD,
            COMM_NO,
            DIV_NO;
