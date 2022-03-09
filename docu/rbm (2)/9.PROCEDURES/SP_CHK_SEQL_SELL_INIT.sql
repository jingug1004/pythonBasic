CREATE OR REPLACE PROCEDURE USRBM.SP_CHK_SEQL_SELL_INIT
/******************************************************************************
- ������,��      : ������  20161201
- ���α׷���    : SP_CHK_SEQL_SELL_INIT
- ���α׷�Ÿ�� : procedure
- ���            : ���ߺ�����Ȳ�� ��ȸ�ϴ� ���ν��� ȣ��
- IN  �μ�       : 
- ���μ���
******************************************************************************/
IS
  V_YEAR_TOTE VARCHAR2(255);
  V_DATE_TOTE VARCHAR2(255);
  V_STND_AMOUNT NUMBER;
  V_STND_TIME VARCHAR2(255);
  V_STND_CNT NUMBER;      

BEGIN  
    SELECT
        TO_CHAR(SYSDATE-1, 'YYYY') YEAR_TOTE
        ,TO_CHAR(SYSDATE, 'MMDD') DATE_TOTE
        ,100000    STND_AMOUNT --10����
        ,30 STND_TIME                --30�� �̳�
        ,10 STND_CNT                  --10�� �̻�
    INTO V_YEAR_TOTE, V_DATE_TOTE, V_STND_AMOUNT, V_STND_TIME, V_STND_CNT        
    FROM DUAL;     
    
    SP_CHK_SEQL_SELL(V_YEAR_TOTE, V_DATE_TOTE, V_STND_AMOUNT, V_STND_TIME, V_STND_CNT);

END SP_CHK_SEQL_SELL_INIT;
/
