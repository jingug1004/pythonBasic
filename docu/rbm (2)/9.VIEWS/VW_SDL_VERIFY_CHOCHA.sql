DROP VIEW USRBM.VW_SDL_VERIFY_CHOCHA;

/* Formatted on 2017-03-18 ���� 11:08:17 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_SDL_VERIFY_CHOCHA
(
   MEET_CD,
   STND_YEAR,
   TMS,
   DAY_ORD,
   RACE_NO,
   RACE_DAY,
   POOL_TOTAL,
   DIV_TOTAL,
   SALES_TOT_CHK,
   CHOCHA_CALC,
   CHOCHA_DT_02,
   CHOCHA_DT_04,
   CHOCHA_CHK,
   REFUND
)
AS
   SELECT A.MEET_CD,
          A.STND_YEAR,
          A.TMS,
          A.DAY_ORD,
          A.RACE_NO,
          A.RACE_DAY,
          A.POOL_TOTAL,
          B.DIV_TOTAL AS DIV_TOTAL,
          A.POOL_TOTAL - B.DIV_TOTAL AS SALES_TOT_CHK,
          A.POOL_TOTAL - B.DIV_TOTAL_IN AS CHOCHA,
          B.DIV_TOTAL_02 AS CHOCHA_DT_02,
          B.DIV_TOTAL_04 AS CHOCHA_DT_04,
          A.POOL_TOTAL - B.DIV_TOTAL_IN - B.DIV_TOTAL_02 - B.DIV_TOTAL_04
             AS CHOCHA_CHK,
          B.REFUND AS REFUND
     FROM (  SELECT P.MEET_CD,
                    P.STND_YEAR,
                    P.TMS,
                    P.DAY_ORD,
                    P.RACE_NO,
                    V.RACE_DAY,
                    SUM (POOL_TOTAL) AS POOL_TOTAL,
                    SUM (REFUND) AS REFUND
               FROM TBES_SDL_PT P, VW_SDL_INFO V
              WHERE     P.MEET_CD = V.MEET_CD
                    AND P.STND_YEAR = V.STND_YEAR
                    AND P.TMS = V.TMS
                    AND P.DAY_ORD = V.DAY_ORD
           --AND V.RACE_DAY = TO_CHAR(SYSDATE -1, 'YYYYMMDD')
           GROUP BY P.MEET_CD,
                    P.STND_YEAR,
                    P.TMS,
                    P.DAY_ORD,
                    P.RACE_NO,
                    V.RACE_DAY) A,
          (  SELECT P.MEET_CD,
                    P.STND_YEAR,
                    P.TMS,
                    P.DAY_ORD,
                    P.RACE_NO,
                    V.RACE_DAY,
                    SUM (DIV_TOTAL) AS DIV_TOTAL,
                    SUM(CASE
                           WHEN P.SELL_CD IN ('01', '03') THEN DIV_TOTAL
                           ELSE 0
                        END)
                       AS DIV_TOTAL_IN,
                    SUM (CASE WHEN P.SELL_CD = '02' THEN DIV_TOTAL ELSE 0 END)
                       AS DIV_TOTAL_02,
                    SUM (CASE WHEN P.SELL_CD = '04' THEN DIV_TOTAL ELSE 0 END)
                       AS DIV_TOTAL_04,
                    SUM (REFUND) AS REFUND
               FROM TBES_SDL_DT P, VW_SDL_INFO V
              WHERE     P.MEET_CD = V.MEET_CD
                    AND P.STND_YEAR = V.STND_YEAR
                    AND P.TMS = V.TMS
                    AND P.DAY_ORD = V.DAY_ORD
           --AND V.RACE_DAY = TO_CHAR(SYSDATE -1, 'YYYYMMDD')
           GROUP BY P.MEET_CD,
                    P.STND_YEAR,
                    P.TMS,
                    P.DAY_ORD,
                    P.RACE_NO,
                    V.RACE_DAY) B
    WHERE     A.MEET_CD = B.MEET_CD
          AND A.STND_YEAR = B.STND_YEAR
          AND A.TMS = B.TMS
          AND A.DAY_ORD = B.DAY_ORD
          AND A.RACE_NO = B.RACE_NO;
COMMENT ON COLUMN USRBM.VW_SDL_VERIFY_CHOCHA.MEET_CD IS '�������ڵ�';

COMMENT ON COLUMN USRBM.VW_SDL_VERIFY_CHOCHA.STND_YEAR IS '���س⵵';

COMMENT ON COLUMN USRBM.VW_SDL_VERIFY_CHOCHA.TMS IS 'ȸ��';

COMMENT ON COLUMN USRBM.VW_SDL_VERIFY_CHOCHA.DAY_ORD IS '����';

COMMENT ON COLUMN USRBM.VW_SDL_VERIFY_CHOCHA.RACE_NO IS '���ֹ�ȣ';

COMMENT ON COLUMN USRBM.VW_SDL_VERIFY_CHOCHA.RACE_DAY IS '��������';

COMMENT ON COLUMN USRBM.VW_SDL_VERIFY_CHOCHA.POOL_TOTAL IS '�½ĺ� �����';

COMMENT ON COLUMN USRBM.VW_SDL_VERIFY_CHOCHA.DIV_TOTAL IS '������ �����';

COMMENT ON COLUMN USRBM.VW_SDL_VERIFY_CHOCHA.SALES_TOT_CHK IS '����� ����';

COMMENT ON COLUMN USRBM.VW_SDL_VERIFY_CHOCHA.CHOCHA_CALC IS '���������(���)';

COMMENT ON COLUMN USRBM.VW_SDL_VERIFY_CHOCHA.CHOCHA_DT_02 IS '���������(â��)';

COMMENT ON COLUMN USRBM.VW_SDL_VERIFY_CHOCHA.CHOCHA_DT_04 IS '���������(�λ�)';

COMMENT ON COLUMN USRBM.VW_SDL_VERIFY_CHOCHA.CHOCHA_CHK IS '��������� üũ';

COMMENT ON COLUMN USRBM.VW_SDL_VERIFY_CHOCHA.REFUND IS '����̻縮 ȯ�Ҿ�';
