DROP VIEW USRBM.VW_PC_SATLIT2;

/* Formatted on 2017-03-18 오전 11:04:00 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_PC_SATLIT2
(
   MEET_CD,
   STND_YEAR,
   TMS,
   DAY_ORD,
   SELL_CD,
   COMM_NO,
   DIV_NO,
   RACE_NO,
   POOL_CD,
   TOTAL_AMT,
   REFUND,
   NET_AMT,
   INST_DT,
   UPDT_DT
)
AS
     SELECT                   -- 공단 본부 제공용 VIEW(2011년 형태로 자료를 제공하기 위해 지점코드 변경)
           MEET_CD,
            STND_YEAR,
            TMS,
            DAY_ORD,
            SELL_CD,
            COMM_NO,
            DIV_NO,
            RACE_NO,
            POOL_CD,
            SUM (TOTAL_AMT) TOTAL_AMT,
            SUM (REFUND) REFUND,
            SUM (NET_AMT) NET_AMT,
            MAX (inst_dt) AS INST_DT,
            MAX (UPDT_DT) AS UPDT_DT
       FROM (SELECT MEET_CD,
                    STND_YEAR,
                    TMS,
                    DAY_ORD,
                    SELL_CD,
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
                    RACE_NO,
                    POOL_CD,
                    TOTAL_AMT,
                    REFUND,
                    NET_AMT,
                    INST_DT,
                    UPDT_DT
               FROM USRBM.VW_PC_SATLIT)
   GROUP BY MEET_CD,
            STND_YEAR,
            TMS,
            DAY_ORD,
            SELL_CD,
            COMM_NO,
            DIV_NO,
            RACE_NO,
            POOL_CD;


GRANT SELECT ON USRBM.VW_PC_SATLIT2 TO ACCT;

GRANT SELECT ON USRBM.VW_PC_SATLIT2 TO INTRA;

GRANT SELECT ON USRBM.VW_PC_SATLIT2 TO VWRBM;
