DROP VIEW USRBM.VWRC_STAY_MANA_TSUM;

/* Formatted on 2017-03-18 ¿ÀÀü 11:09:56 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VWRC_STAY_MANA_TSUM
(
   MEET_CD,
   RACE_DT,
   RACE_NO,
   BRNC_CD,
   ENT_PRSN_NUM
)
AS
   SELECT MEET_CD,
          RACE_DT,
          RACE_NO,
          BRNC_CD,
          ENT_PRSN_NUM
     FROM TBRC_STAY_MANA
    WHERE RACE_DT < '20141003'
   UNION ALL
     SELECT S.MEET_CD,
            T.TRADE_DATE,
            DECODE (RACE_NO, 0, 1, RACE_NO) RACE_NO,
            BRNC_CD,
            SUM (CNT) AS ENT_PRSN_NUM
       FROM TBRC_T_TRADE_RACE_SUM T,
            (SELECT RACE_DAY,
                    C.CD AS BRNC_CD,
                    C.CD_NM AS COMM_NM,
                    MEET_CD
               FROM (  SELECT RACE_DAY, MIN (MEET_CD) AS MEET_CD
                         FROM VW_SDL_INFO
                     GROUP BY RACE_DAY) S, TBRK_SPEC_CD C
              WHERE     1 = 1
                    AND S.MEET_CD IN ('001', '003')
                    AND C.GRP_CD = '018'
                    AND C.DEL_YN = 'N'
                    AND C.CD_TERM4 LIKE
                          DECODE (S.MEET_CD, '001', '1', '%')
                          || DECODE (S.MEET_CD, '003', '1', '%')
                    AND S.RACE_DAY > '20141002') S
      WHERE 1 = 1 AND T.TRADE_DATE = S.RACE_DAY AND T.COMM_NO = S.BRNC_CD
   GROUP BY T.TRADE_DATE,
            DECODE (RACE_NO, 0, 1, RACE_NO),
            BRNC_CD,
            S.MEET_CD;
