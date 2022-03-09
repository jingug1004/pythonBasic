DROP VIEW USRBM.VW_SDL_DT_SUM_BRNC;

/* Formatted on 2017-03-18 ���� 11:06:52 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_SDL_DT_SUM_BRNC
(
   MEET_CD,
   STND_YEAR,
   TMS,
   BRNC_CD,
   BRNC_NM,
   DIV_TOTAL
)
AS
     SELECT MEET_CD,                 -- ���屸��   (�λ�/â�� ��� ������ ������ ����� ��ȸ�� VIEW)
            STND_YEAR,                                                   -- �⵵
            TMS,                                                         -- ȸ��
            COMM_NO_NEW AS BRNC_CD,                                      -- ����
            C.CD_NM AS BRNC_NM,                                         -- ������
            SUM (DIV_TOTAL) DIV_TOTAL                                   -- �����
       FROM (  SELECT B.MEET_CD,
                      B.STND_YEAR,
                      B.TMS,
                      A.SELL_CD,
                      A.COMM_NO,
                      A.DIV_NO,
                      SUM (DIV_TOTAL) DIV_TOTAL,
                      CASE
                         WHEN SELL_CD IN ('01', '03')
                              AND COMM_NO IN ('01', '02', '03', '04', '08')
                         THEN
                            '01'                                   --����(���,����)
                         WHEN     SELL_CD = '01'
                              AND COMM_NO >= '11'
                              AND COMM_NO <= '28'
                         THEN
                            COMM_NO
                         WHEN SELL_CD IN ('01', '03') AND COMM_NO = '06'
                         THEN
                            COMM_NO
                         WHEN SELL_CD NOT IN ('01', '03')
                         THEN
                            '29'
                         ELSE
                            COMM_NO
                      END
                         COMM_NO_NEW                                    --�����ڵ�
                 FROM VW_SDL_DT_SUM A,                                   -- ����
                                      VW_SDL_INFO B                  -- ������ ����
                WHERE     1 = 1
                      AND A.MEET_CD = B.MEET_CD
                      AND A.STND_YEAR = B.STND_YEAR
                      AND A.TMS = B.TMS
                      AND A.DAY_ORD = B.DAY_ORD
             -- ���,����,����,������ ���� �б�
             GROUP BY B.MEET_CD,
                      B.STND_YEAR,
                      B.TMS,
                      A.SELL_CD,
                      A.COMM_NO,
                      A.DIV_NO   -- , ROLLUP(B.TMS) -- ���� ���ڵ���  TOTAL, SUM ������
                              ) X,
            TBRK_SPEC_CD C
      WHERE X.COMM_NO_NEW = C.CD(+) AND C.GRP_CD = '060'
   GROUP BY MEET_CD,
            STND_YEAR,
            TMS,
            COMM_NO_NEW,
            CD_NM;
COMMENT ON TABLE USRBM.VW_SDL_DT_SUM_BRNC IS '���� �����(â����ȸ��)';

COMMENT ON COLUMN USRBM.VW_SDL_DT_SUM_BRNC.MEET_CD IS '������ ���� ';

COMMENT ON COLUMN USRBM.VW_SDL_DT_SUM_BRNC.STND_YEAR IS '���ֳ⵵ ';

COMMENT ON COLUMN USRBM.VW_SDL_DT_SUM_BRNC.TMS IS 'ȸ�� ';

COMMENT ON COLUMN USRBM.VW_SDL_DT_SUM_BRNC.BRNC_CD IS '�����ڵ� ';

COMMENT ON COLUMN USRBM.VW_SDL_DT_SUM_BRNC.BRNC_NM IS '�����ڵ�';

COMMENT ON COLUMN USRBM.VW_SDL_DT_SUM_BRNC.DIV_TOTAL IS '������ ����� ';


DROP SYNONYM CCRC.VW_SDL_DT_SUM_BRNC;

CREATE SYNONYM CCRC.VW_SDL_DT_SUM_BRNC FOR USRBM.VW_SDL_DT_SUM_BRNC;


GRANT SELECT ON USRBM.VW_SDL_DT_SUM_BRNC TO BCRC;

GRANT SELECT ON USRBM.VW_SDL_DT_SUM_BRNC TO CCRC;

GRANT SELECT ON USRBM.VW_SDL_DT_SUM_BRNC TO US_CRA;
