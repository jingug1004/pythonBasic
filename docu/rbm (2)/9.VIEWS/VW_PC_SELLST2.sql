DROP VIEW USRBM.VW_PC_SELLST2;

/* Formatted on 2017-03-18 오전 11:04:33 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_PC_SELLST2
(
   MEET_CD,
   STND_YEAR,
   TMS,
   DAY_ORD,
   SELL_CD,
   COMM_NO,
   DIV_NO,
   RACE_NO,
   SESSION_NO,
   PERF_NO,
   COUNT1,
   COUNT2,
   COUNT3,
   COUNT4,
   COUNT5,
   COUNT6,
   COUNT7,
   COUNT8,
   AMOUNT1,
   AMOUNT2,
   AMOUNT3,
   AMOUNT4,
   AMOUNT5,
   AMOUNT6,
   AMOUNT7,
   AMOUNT8,
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
            SESSION_NO,
            PERF_NO,
            SUM (COUNT1) COUNT1,
            SUM (COUNT2) COUNT2,
            SUM (COUNT3) COUNT3,
            SUM (COUNT4) COUNT4,
            SUM (COUNT5) COUNT5,
            SUM (COUNT6) COUNT6,
            SUM (COUNT7) COUNT7,
            SUM (COUNT8) COUNT8,
            SUM (AMOUNT1) AMOUNT1,
            SUM (AMOUNT2) AMOUNT2,
            SUM (AMOUNT3) AMOUNT3,
            SUM (AMOUNT4) AMOUNT4,
            SUM (AMOUNT5) AMOUNT5,
            SUM (AMOUNT6) AMOUNT6,
            SUM (AMOUNT7) AMOUNT7,
            SUM (AMOUNT8) AMOUNT8,
            MAX (inst_dt) AS inst_dt,
            MAX (updt_dt) AS updt_dt
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
                    SESSION_NO,
                    PERF_NO,
                    COUNT1,
                    COUNT2,
                    COUNT3,
                    COUNT4,
                    COUNT5,
                    COUNT6,
                    COUNT7,
                    COUNT8,
                    AMOUNT1,
                    AMOUNT2,
                    AMOUNT3,
                    AMOUNT4,
                    AMOUNT5,
                    AMOUNT6,
                    AMOUNT7,
                    AMOUNT8,
                    inst_dt,
                    updt_dt
               FROM USRBM.VW_PC_SELLST)
   GROUP BY MEET_CD,
            STND_YEAR,
            TMS,
            DAY_ORD,
            SELL_CD,
            COMM_NO,
            DIV_NO,
            RACE_NO,
            SESSION_NO,
            PERF_NO;


GRANT SELECT ON USRBM.VW_PC_SELLST2 TO ACCT;

GRANT SELECT ON USRBM.VW_PC_SELLST2 TO INTRA;

GRANT SELECT ON USRBM.VW_PC_SELLST2 TO VWRBM;
