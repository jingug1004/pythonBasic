DROP VIEW USRBM.VIEW_SALES_BCRC;

/* Formatted on 2017-03-18 ���� 10:55:29 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VIEW_SALES_BCRC
(
   STND_YEAR,
   TMS,
   DAY_ORD,
   RACE_DAY,
   MEET_CD,
   RACE_NO,
   SELL_CD,
   COMM_NO,
   WIN,
   PLA,
   EXA,
   QUI,
   TRI,
   TOT_SALES
)
AS
   SELECT "RACEYY" STND_YEAR,
          RACETIMES TMS,
          "RACEDAYS" DAY_ORD,
          "RACEDATE" RACE_DAY,
          DECODE (RJANGCD, '003', '004', '099', '003', RJANGCD) AS MEET_CD,
          "RACENO" RACE_NO,
          DECODE (TRACKCD,
                  '003', '04',
                  '099', '03',
                  '001', '01',
                  '002', '02',
                  TRACKCD)
             AS SELL_CD,
          BRANCHCD AS COMM_NO,
          "WIN",
          "PLA",
          "EXA",
          "QUI",
          "TRI",
          (WIN + PLA + EXA + QUI + TRI) AS TOT_SALES
     FROM BCRC.VW_SALES_N@BCRCDBLINK
    WHERE RACEYY > '2012' AND BRANCHCD IS NOT NULL
   UNION ALL
   SELECT "RACEYY" STND_YEAR,
          RACETIMES TMS,
          "RACEDAYS" DAY_ORD,
          "RACEDATE" RACE_DAY,
          DECODE (RJANGCD, '003', '004', '099', '003', RJANGCD) AS MEET_CD,
          "RACENO" RACE_NO,
          DECODE (TRACKCD,
                  '003', '04',
                  '099', '03',
                  '001', '01',
                  '002', '02',
                  TRACKCD)
             AS SELL_CD,
          BRANCHCD AS COMM_NO,
          "WIN",
          "PLA",
          "EXA",
          "QUI",
          "TRI",
          (WIN + PLA + EXA + QUI + TRI) AS TOT_SALES
     FROM BCRC.VW_SALES@BCRCDBLINK
    WHERE RACEYY < '2013';
COMMENT ON TABLE USRBM.VIEW_SALES_BCRC IS '�λ� �����';

COMMENT ON COLUMN USRBM.VIEW_SALES_BCRC.STND_YEAR IS '���ֳ⵵';

COMMENT ON COLUMN USRBM.VIEW_SALES_BCRC.TMS IS 'ȸ��';

COMMENT ON COLUMN USRBM.VIEW_SALES_BCRC.DAY_ORD IS '����';

COMMENT ON COLUMN USRBM.VIEW_SALES_BCRC.RACE_DAY IS '��������';

COMMENT ON COLUMN USRBM.VIEW_SALES_BCRC.MEET_CD IS '����ó(001:����, 002:â��,003:�̻縮,004:�λ�)';

COMMENT ON COLUMN USRBM.VIEW_SALES_BCRC.RACE_NO IS '���ֹ�ȣ';

COMMENT ON COLUMN USRBM.VIEW_SALES_BCRC.SELL_CD IS '�Ǹ�ó(01:����, 02:â��,03:�̻縮,04:�λ�)';

COMMENT ON COLUMN USRBM.VIEW_SALES_BCRC.COMM_NO IS '�����ڵ�(�λ�)
    101 ����
    102 ����
    003 ����
    005 ���� ����ī��
    006 ���� ����ī��';

COMMENT ON COLUMN USRBM.VIEW_SALES_BCRC.WIN IS '�ܽ�';

COMMENT ON COLUMN USRBM.VIEW_SALES_BCRC.PLA IS '����';

COMMENT ON COLUMN USRBM.VIEW_SALES_BCRC.EXA IS '����';

COMMENT ON COLUMN USRBM.VIEW_SALES_BCRC.QUI IS '�ֽ½�';

COMMENT ON COLUMN USRBM.VIEW_SALES_BCRC.TRI IS '�ﺹ��';

COMMENT ON COLUMN USRBM.VIEW_SALES_BCRC.TOT_SALES IS '�����';
