DROP VIEW USRBM.VIEW_SALES;

/* Formatted on 2017-03-18 ¿ÀÀü 10:54:13 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VIEW_SALES
(
   RACEYY,
   RACETIMES,
   RACEDAYS,
   RACEDATE,
   RJANGCD,
   RACENO,
   TRACKCD,
   BRANCHCD,
   BRANCHNM,
   WIN,
   PLA,
   EXA,
   QUI,
   TRI,
   TOT_SALES
)
AS
     SELECT RACEYY,
            RACETIMES,
            RACEDAYS,
            RACEDATE,
            RJANGCD,
            RACENO,
            TRACKCD,
            BRANCHCD,
            BRANCHNM,
            SUM (WIN) AS WIN,
            SUM (PLA) AS PLA,
            SUM (EXA) AS EXA,
            SUM (QUI) AS QUI,
            SUM (TRI) AS TRI,
            (  NVL (SUM (WIN), 0)
             + NVL (SUM (PLA), 0)
             + NVL (SUM (EXA), 0)
             + NVL (SUM (QUI), 0)
             + NVL (SUM (TRI), 0))
               AS TOT_SALES
       FROM (SELECT D.STND_YEAR AS RACEYY,
                    TRIM (TO_CHAR (D.TMS, '00')) AS RACETIMES,
                    D.DAY_ORD AS RACEDAYS,
                    D.RACE_DAY AS RACEDATE,
                    DECODE (D.MEET_CD, '003', '099', D.MEET_CD) AS RJANGCD,
                    D.RACE_NO AS RACENO,
                    DECODE (D.SELL_CD,
                            '01', '001',
                            '03', '099',
                            TRIM (TO_CHAR (D.SELL_CD)))
                       AS TRACKCD,
                    D.COMM_NO AS BRANCHCD,
                    C.CD_NM AS BRANCHNM,
                    DECODE (POOL_CD, '001', TOTAL_AMT) AS WIN,
                    DECODE (POOL_CD, '002', TOTAL_AMT) AS PLA,
                    DECODE (POOL_CD, '004', TOTAL_AMT) AS EXA,
                    DECODE (POOL_CD, '005', TOTAL_AMT) AS QUI,
                    DECODE (POOL_CD, '006', TOTAL_AMT) AS TRI
               FROM TBES_DW_SALES D, TBRK_SPEC_CD C
              WHERE     1 = 1
                    AND D.TOTAL_AMT > 0
                    AND D.COMM_NO = C.CD(+)
                    AND C.GRP_CD(+) = '018')
   GROUP BY RACEYY,
            RACETIMES,
            RACEDAYS,
            RACEDATE,
            RJANGCD,
            RACENO,
            TRACKCD,
            BRANCHCD,
            BRANCHNM;


GRANT SELECT ON USRBM.VIEW_SALES TO BCRC;

GRANT SELECT ON USRBM.VIEW_SALES TO CCRC;
