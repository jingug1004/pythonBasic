DROP VIEW USRBM.VW_API_SALES;

/* Formatted on 2017-03-18 ���� 10:57:16 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_API_SALES
(
   "�⵵",
   "����",
   "����",
   "�����Ѱ�",
   "����",
   "����",
   "�߶�",
   "�ϻ�",
   "���빮",
   "�д�",
   "���",
   "�꺻",
   "��õ",
   "����",
   "����",
   "������",
   "����",
   "��õ",
   "����",
   "����",
   "õ��",
   "�ø���",
   "������",
   "����Ȱ�뺻��",
   "�׸�ī��",
   "����"
)
AS
     SELECT STND_YEAR �⵵,
            DECODE (meet_cd, '001', '���', '003', '����') ����,
            SUBSTR (RACE_DAY, 5, 2) AS ����,
            SUM (DIV_TOTAL) �����Ѱ�,
            SUM(CASE
                   WHEN MEET_CD || SELL_CD IN
                              ('00101', '00201', '00303', '00401')
                        AND COMM_NO IN ('01', '02', '03', '04', '08')
                   THEN
                      DIV_TOTAL
                END)
               ����,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '11' THEN DIV_TOTAL END)
               ����,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '12' THEN DIV_TOTAL END)
               �߶�,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '13' THEN DIV_TOTAL END)
               �ϻ�,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '15' THEN DIV_TOTAL END)
               ���빮,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '14' THEN DIV_TOTAL END)
               �д�,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '16' THEN DIV_TOTAL END)
               ���,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '17' THEN DIV_TOTAL END)
               �꺻,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '18' THEN DIV_TOTAL END)
               ��õ,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '19' THEN DIV_TOTAL END)
               ����,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '20' THEN DIV_TOTAL END)
               ����,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '21' THEN DIV_TOTAL END)
               ������,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '22' THEN DIV_TOTAL END)
               ����,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '23' THEN DIV_TOTAL END)
               ��õ,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '24' THEN DIV_TOTAL END)
               ����,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '25' THEN DIV_TOTAL END)
               ����,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '26' THEN DIV_TOTAL END)
               õ��,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '27' THEN DIV_TOTAL END)
               �ø���,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '28' THEN DIV_TOTAL END)
               ������,
            SUM(CASE
                   WHEN MEET_CD || SELL_CD IN
                              ('00103', '00203', '00301', '00403')
                        AND COMM_NO IN ('01', '02', '03', '04', '08')
                   THEN
                      DIV_TOTAL
                END)
               ����Ȱ�뺻��,
            SUM(CASE
                   WHEN SELL_CD IN ('01', '03') AND COMM_NO = '06'
                   THEN
                      DIV_TOTAL
                END)
               �׸�ī��,
            SUM (CASE WHEN SELL_CD NOT IN ('01', '03') THEN DIV_TOTAL END)
               ����
       FROM (  SELECT B.MEET_CD,
                      B.STND_YEAR,
                      B.RACE_DAY,
                      SUM (A.DIV_TOTAL) DIV_TOTAL,
                      A.SELL_CD,
                      A.COMM_NO,
                      A.DIV_NO
                 FROM VW_SDL_INFO B,                                -- ������ ����,
                                    VW_SDL_DT_SUM A                      -- ����
                WHERE     1 = 1
                      AND B.MEET_CD IN ('001', '003')
                      AND A.MEET_CD = B.MEET_CD
                      AND A.STND_YEAR = B.STND_YEAR
                      AND A.TMS = B.TMS
                      AND A.DAY_ORD = B.DAY_ORD
                      AND A.SELL_CD IN ('01', '03')
             GROUP BY B.MEET_CD,
                      B.STND_YEAR,
                      B.RACE_DAY,
                      5,
                      2,
                      A.SELL_CD,
                      A.COMM_NO,
                      A.DIV_NO
             UNION ALL
               SELECT B.MEET_CD,
                      B.STND_YEAR,
                      B.RACE_DAY,
                      SUM (A.NET_AMT) DIV_TOTAL,
                      A.SELL_CD,
                      A.SELL_CD AS COMM_NO,
                      A.SELL_CD AS DIV_NO
                 FROM VW_PC_PAYOFFS2 A,                                     --
                                       VW_SDL_INFO B                 -- ������ ����
                WHERE     1 = 1
                      AND B.MEET_CD IN ('001', '003')
                      AND A.SELL_CD NOT IN ('01', '03')
                      AND A.MEET_CD = B.MEET_CD
                      AND A.STND_YEAR = B.STND_YEAR
                      AND A.TMS = B.TMS
                      AND A.DAY_ORD = B.DAY_ORD
             GROUP BY B.MEET_CD,
                      B.STND_YEAR,
                      B.RACE_DAY,
                      5,
                      2,
                      A.SELL_CD,
                      A.COMM_NO,
                      A.DIV_NO
             ORDER BY 1, 2, 3)
   GROUP BY MEET_CD, STND_YEAR, SUBSTR (RACE_DAY, 5, 2)
   ORDER BY MEET_CD, STND_YEAR, SUBSTR (RACE_DAY, 5, 2);


GRANT SELECT ON USRBM.VW_API_SALES TO ACCT;
