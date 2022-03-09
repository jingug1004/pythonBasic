DROP VIEW USRBM.VW_MYCAT_INOUT;

/* Formatted on 2017-03-18 ���� 10:59:22 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_MYCAT_INOUT
(
   RACE_DAY,
   SELL_CD,
   COMM_NO,
   DIV_NO,
   OUT_AMT,
   IN_AMT,
   PURGE_AMT,
   MILEAGE_AMT,
   BONUS_AMT
)
AS
   (  SELECT RACE_DAY,
             SELL_CD,
             COMM_NO,                          --����:01, ����:07 ==> ��� '06'���� ����
             DIV_NO,       --������ ������������ ���ؼ� COMM_NO�� ��������, ���ý��ۿ����� DIV_NO�� ���
             SUM (OUT_AMT) OUT_AMT,
             SUM (IN_AMT) IN_AMT,
             SUM (PURGE_AMT) AS PURGE_AMT,
             SUM (MILEAGE_AMT) MILEAGE_AMT,
             SUM (BONUS_AMT) BONUS_AMT
        FROM (SELECT RACE_DAY,
                     SELL_CD,
                     '06' COMM_NO,
                     DECODE (COMM_NO, '01', '00', DIV_NO) DIV_NO,
                     OUT_AMT,
                     IN_AMT,
                     PURGE_AMT,
                     MILEAGE_AMT,
                     BONUS_AMT
                FROM V_MYCAT_DIVSTAT@CYBETLINK)
    GROUP BY RACE_DAY,
             SELL_CD,
             COMM_NO,
             DIV_NO);
