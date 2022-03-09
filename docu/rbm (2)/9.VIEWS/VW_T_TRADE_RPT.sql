DROP VIEW USRBM.VW_T_TRADE_RPT;

/* Formatted on 2017-03-18 오전 11:09:21 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_T_TRADE_RPT
(
   RACE_DAY,
   COMM_NO,
   COMM_NM,
   CNT_SUM,
   PAY_CNT,
   NOT_PAY_CNT,
   REQUEST_FEE,
   SUPY_AMT,
   ADD_TAX,
   PRV_WST_TAX,
   EDU_TAX
)
AS
   SELECT                                                    /* rem4030_s01 */
         RACE_DAY,
          COMM_NO,
          C.CD_NM AS COMM_NM,
          CNT_SUM,
          PAY_CNT,
          NOT_PAY_CNT,
          REQUEST_FEE,
          ROUND (REQUEST_FEE / 1.1) SUPY_AMT,
          (REQUEST_FEE - ROUND (REQUEST_FEE / 1.1)) ADD_TAX,
          CNT_SUM * 200 AS PRV_WST_TAX,
          CNT_SUM * 60 AS EDU_TAX
     FROM (  SELECT S.RACE_DAY,
                    COMM_NO,
                    SUM (CNT) AS CNT_SUM,
                    SUM (DECODE (CARD_USER_TYPE, '4', CNT, 0)) AS PAY_CNT,
                    SUM (DECODE (CARD_USER_TYPE, '4', 0, CNT)) AS NOT_PAY_CNT,
                    SUM (DECODE (CARD_USER_TYPE, '4', FEE, 0)) AS REQUEST_FEE
               FROM TBRC_T_TRADE_SUM T,
                    (SELECT RACE_DAY, C.CD AS BRNC_CD, C.CD_NM AS COMM_NM
                       FROM VW_SDL_INFO S, TBRK_SPEC_CD C
                      WHERE     S.MEET_CD IN ('001', '003')
                            AND C.GRP_CD = '018'
                            AND C.DEL_YN = 'N'
                            AND C.CD_TERM4 LIKE
                                  DECODE (S.MEET_CD, '001', '1', '%')
                                  || DECODE (S.MEET_CD, '003', '1', '%')) S
              WHERE     S.RACE_DAY = T.TRADE_DATE
                    AND S.BRNC_CD = T.COMM_NO
                    AND t.VERI = '001'
           GROUP BY S.RACE_DAY, COMM_NO) A,
          TBRK_SPEC_CD C
    WHERE C.GRP_CD(+) = '018' AND A.COMM_NO = C.CD(+);
COMMENT ON COLUMN USRBM.VW_T_TRADE_RPT.RACE_DAY IS '경주일자';

COMMENT ON COLUMN USRBM.VW_T_TRADE_RPT.COMM_NO IS '지점번호';

COMMENT ON COLUMN USRBM.VW_T_TRADE_RPT.COMM_NM IS '지점명';

COMMENT ON COLUMN USRBM.VW_T_TRADE_RPT.CNT_SUM IS '총입장인원';

COMMENT ON COLUMN USRBM.VW_T_TRADE_RPT.PAY_CNT IS '유료입장인원';

COMMENT ON COLUMN USRBM.VW_T_TRADE_RPT.NOT_PAY_CNT IS '무료입장인원';

COMMENT ON COLUMN USRBM.VW_T_TRADE_RPT.REQUEST_FEE IS '입장금액';

COMMENT ON COLUMN USRBM.VW_T_TRADE_RPT.SUPY_AMT IS '공급가액';

COMMENT ON COLUMN USRBM.VW_T_TRADE_RPT.ADD_TAX IS '부가가치세';

COMMENT ON COLUMN USRBM.VW_T_TRADE_RPT.PRV_WST_TAX IS '개별소비세';

COMMENT ON COLUMN USRBM.VW_T_TRADE_RPT.EDU_TAX IS '교육세';
