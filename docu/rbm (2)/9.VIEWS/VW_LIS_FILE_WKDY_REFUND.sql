DROP VIEW USRBM.VW_LIS_FILE_WKDY_REFUND;

/* Formatted on 2017-03-18 오전 10:59:11 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_LIS_FILE_WKDY_REFUND
(
   RACE_DAY,
   BRNC_CD,
   BRNC_NM,
   CASHES
)
AS
     SELECT STND_YEAR || DT AS RACE_DAY,
            BRNC_CD,
            C.CD_NM AS BRNC_NM,
            CASHES
       FROM TBRD_LIS_FILE LF, TBRK_SPEC_CD C
      WHERE STND_YEAR || DT IN
                  (SELECT B.RACE_DAY
                     FROM (  SELECT RACE_DAY, SUM (CYCLE_AMT) AS CYCLE_AMT
                               FROM TBJI_PC_BALANCE
                              WHERE CYCLE_AMT > 0
                                    AND SELL_CD IN ('01', '02', '04', '09')
                           GROUP BY RACE_DAY) B,
                          (  SELECT STND_YEAR || DT AS RACE_DAY,
                                    SUM (CASHES) AS CASHES
                               FROM TBRD_LIS_FILE
                              WHERE 1 = 1 --and BRNC_CD NOT IN ('00','01','02','03','04','06','08','98')
                                         AND BRNC_CD NOT IN ('98')
                                    AND ( (meet_cd = '003'
                                           AND brnc_cd IN
                                                    (SELECT cd
                                                       FROM TBRK_SPEC_CD
                                                      WHERE grp_cd = '060'
                                                            AND cd_term4 LIKE
                                                                  '10%'))
                                         OR meet_cd = '001')
                                    AND GROSS_SALES = 0
                                    AND BALANCE = 0              -- 20140514추가
                                    AND CASHES > 0
                           GROUP BY STND_YEAR || DT) L
                    WHERE B.RACE_DAY = L.RACE_DAY AND B.CYCLE_AMT = L.CASHES)
            AND LF.BRNC_CD = C.CD
            AND C.GRP_CD = '018'
            AND GROSS_SALES = 0
            AND BALANCE = 0                                      -- 20140514추가
            AND CASHES > 0
            AND ( (meet_cd = '003'
                   AND brnc_cd IN
                            (SELECT cd
                               FROM TBRK_SPEC_CD
                              WHERE grp_cd = '060' AND cd_term4 LIKE '10%'))
                 OR meet_cd = '001')
   ORDER BY STND_YEAR, DT;


GRANT SELECT ON USRBM.VW_LIS_FILE_WKDY_REFUND TO ACCT;

GRANT SELECT ON USRBM.VW_LIS_FILE_WKDY_REFUND TO PUBLIC;
