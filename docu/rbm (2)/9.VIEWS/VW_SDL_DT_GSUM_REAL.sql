DROP VIEW USRBM.VW_SDL_DT_GSUM_REAL;

/* Formatted on 2017-03-18 오전 11:06:26 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_SDL_DT_GSUM_REAL
(
   MEET_CD,
   STND_YEAR,
   TMS,
   DAY_ORD,
   SELL_CD,
   COMM_NO,
   DIV_TOTAL
)
AS
     SELECT G.MEET_CD,
            G.STND_YEAR,
            G.TMS,
            G.DAY_ORD,
            G.SELL_CD,
            G.COMM_NO,
            SUM (G.DIV_TOTAL) AS DIV_TOTAL
       FROM VW_SDL_DT_SUM_GSUM G, VW_SDL_INFO I
      WHERE     G.MEET_CD IN ('001', '003')
            AND G.MEET_CD = I.MEET_CD
            AND G.STND_YEAR = I.STND_YEAR
            AND G.TMS = I.TMS
            AND G.DAY_ORD = I.DAY_ORD
            AND I.RACE_DAY < TO_CHAR (SYSDATE, 'YYYYMMDD')
   GROUP BY G.MEET_CD,
            G.STND_YEAR,
            G.TMS,
            G.DAY_ORD,
            G.SELL_CD,
            G.COMM_NO
   UNION ALL
     SELECT D.MEET_CD,
            D.STND_YEAR,
            D.TMS,
            D.DAY_ORD,
            D.SELL_CD,
            D.COMM_NO,
            SUM (D.DIV_TOTAL)
       FROM TBES_SDL_DT D, VW_SDL_INFO I
      WHERE     D.MEET_CD IN ('001', '003')
            AND D.COMM_NO != '06'
            AND D.MEET_CD = I.MEET_CD
            AND D.STND_YEAR = I.STND_YEAR
            AND D.TMS = I.TMS
            AND D.DAY_ORD = I.DAY_ORD
            AND I.RACE_DAY = TO_CHAR (SYSDATE, 'YYYYMMDD')
   GROUP BY D.MEET_CD,
            D.STND_YEAR,
            D.TMS,
            D.DAY_ORD,
            D.SELL_CD,
            D.COMM_NO
   UNION ALL
     SELECT MEET_CD,
            STND_YEAR,
            TO_NUMBER (TMS),
            TO_NUMBER (DAY_ORD),
            SELL_CD,
            C.CD_NM2 AS COMM_NO,
            SUM (NET_AMT) AS DIV_TOTAL
       FROM TBES_MYCAT_SALES A LEFT OUTER JOIN TBRK_SPEC_CD C        -- 그린카드지점
               ON A.DIV_NO = C.CD AND C.GRP_CD = '127'
      WHERE MEET_CD IN ('001', '003')
            AND A.RACE_DAY = TO_CHAR (SYSDATE, 'YYYYMMDD')
            AND A.RACE_NO <=                      --SDL 기준 경주까지만  20150712 김일준
                  (SELECT MAX (DT.RACE_NO)
                     FROM TBES_SDL_DT DT, VW_SDL_INFO I
                    WHERE     DT.MEET_CD = I.MEET_CD
                          AND DT.STND_YEAR = I.STND_YEAR
                          AND DT.TMS = I.TMS
                          AND DT.DAY_ORD = I.DAY_ORD
                          AND I.RACE_DAY = TO_CHAR (SYSDATE, 'YYYYMMDD')
                          AND DT.MEET_CD IN ('001', '003'))
   GROUP BY MEET_CD,
            STND_YEAR,
            TMS,
            DAY_ORD,
            SELL_CD,
            C.CD_NM2;
