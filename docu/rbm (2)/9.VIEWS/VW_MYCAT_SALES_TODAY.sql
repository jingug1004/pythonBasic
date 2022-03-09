DROP VIEW USRBM.VW_MYCAT_SALES_TODAY;

/* Formatted on 2017-03-18 오전 10:59:56 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_MYCAT_SALES_TODAY
(
   MEET_CD,
   STND_YEAR,
   TMS,
   DAY_ORD,
   RACE_NO,
   SELL_CD,
   COMM_NO,
   DIV_NO,
   DIV_TOTAL,
   RACE_DAY
)
AS
     SELECT G.MEET_CD,
            G.STND_YEAR,
            TO_NUMBER (G.TMS) AS TMS,
            TO_NUMBER (G.DAY_ORD) AS DAY_ORD,
            G.RACE_NO,
            G.SELL_CD,
            G.COMM_NO,
            G.DIV_NO,
            SUM (G.NET_AMT) AS DIV_TOTAL,
            G.RACE_DAY
       FROM VW_MYCAT_SALES G                                     -- 계좌 지점별 매출액
      WHERE 1 = 1 AND RACE_DAY = TO_CHAR (SYSDATE, 'YYYYMMDD')
            AND EXISTS
                  (SELECT *
                     FROM TBES_SDL_DT D
                    WHERE     G.MEET_CD = D.MEET_CD
                          AND G.STND_YEAR = D.STND_YEAR
                          AND G.TMS = D.TMS
                          AND G.DAY_ORD = D.DAY_ORD
                          AND G.RACE_NO = D.RACE_NO)
   GROUP BY G.MEET_CD,
            G.STND_YEAR,
            G.TMS,
            G.DAY_ORD,
            G.RACE_NO,
            G.SELL_CD,
            G.COMM_NO,
            G.DIV_NO,
            G.RACE_DAY;
