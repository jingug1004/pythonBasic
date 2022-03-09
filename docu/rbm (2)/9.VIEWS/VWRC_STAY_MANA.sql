DROP VIEW USRBM.VWRC_STAY_MANA;

/* Formatted on 2017-03-18 ���� 11:09:44 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VWRC_STAY_MANA
(
   MEET_CD,
   TMS,
   BRNC_CD,
   RACE_DAY,
   BRNC_NM,
   ENT_PRSN_NUM
)
AS
     SELECT                                -- �λ�/â������� ������ �����ο� VIEW(2012.2�ۼ�)
           B.MEET_CD,
            B.TMS                                                         --ȸ��
                 ,
            B.BRNC_CD                                                     --����
                     ,
            B.RACE_DAY                                                    --��¥
                      ,
            MAX (B.BRNC_NM) BRNC_NM                                      --������
                                   ,
            NVL (
               SUM(CASE
                      WHEN B.BRNC_CD = '27' AND A.OUT_ENT_PRSN_NUM > 0
                      THEN
                         A.OUT_ENT_PRSN_NUM
                      ELSE
                         A.ENT_PRSN_NUM
                   END),
               0)
               AS ENT_PRSN_NUM                                          --�����ο�
       FROM (  SELECT MEET_CD,
                      BRNC_CD,
                      RACE_DT,
                      SUM (OUT_ENT_PRSN_NUM) OUT_ENT_PRSN_NUM,
                      SUM (ENT_PRSN_NUM) ENT_PRSN_NUM
                 FROM TBRC_STAY_MANA A                                --ü���ο�����
                WHERE 1 = 1
             GROUP BY MEET_CD, BRNC_CD, RACE_DT) A,
            (SELECT C.BRNC_CD,
                    C.BRNC_NM,
                    A.TMS,
                    A.MEET_CD,
                    A.RACE_DAY,
                    C.ORD_NO
               FROM (SELECT MEET_CD,
                            STND_YEAR,
                            TMS,
                            RACE_DAY
                       FROM VW_SDL_INFO) A,
                    (SELECT CD BRNC_CD, CD_NM BRNC_NM, ORD_NO
                       FROM TBRK_SPEC_CD
                      WHERE GRP_CD = '018' AND DEL_YN = 'N'
                     UNION ALL
                     SELECT CD BRNC_CD, CD_NM BRNC_NM, ORD_NO
                       FROM TBRK_SPEC_CD
                      WHERE GRP_CD = '032' AND DEL_YN = 'N') C) B
      WHERE     A.RACE_DT(+) = B.RACE_DAY
            AND A.BRNC_CD(+) = B.BRNC_CD
            AND A.MEET_CD(+) = B.MEET_CD
   GROUP BY B.MEET_CD,
            B.RACE_DAY,
            B.TMS,
            B.BRNC_CD;
COMMENT ON TABLE USRBM.VWRC_STAY_MANA IS '�������� VIEW(2014.10.3���ʹ� TMONEY�ο����� ǥ��)';

COMMENT ON COLUMN USRBM.VWRC_STAY_MANA.MEET_CD IS '������ڵ� ';

COMMENT ON COLUMN USRBM.VWRC_STAY_MANA.TMS IS 'ȸ�� ';

COMMENT ON COLUMN USRBM.VWRC_STAY_MANA.BRNC_CD IS '�����ڵ� ';

COMMENT ON COLUMN USRBM.VWRC_STAY_MANA.RACE_DAY IS '�������� ';

COMMENT ON COLUMN USRBM.VWRC_STAY_MANA.BRNC_NM IS '������ ';

COMMENT ON COLUMN USRBM.VWRC_STAY_MANA.ENT_PRSN_NUM IS '�����ο� ';


DROP SYNONYM CCRC.VWRC_STAY_MANA;

CREATE SYNONYM CCRC.VWRC_STAY_MANA FOR USRBM.VWRC_STAY_MANA;


GRANT SELECT ON USRBM.VWRC_STAY_MANA TO BCRC;

GRANT SELECT ON USRBM.VWRC_STAY_MANA TO CCRC;
