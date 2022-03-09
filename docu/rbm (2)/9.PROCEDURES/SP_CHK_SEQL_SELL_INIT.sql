CREATE OR REPLACE PROCEDURE USRBM.SP_CHK_SEQL_SELL_INIT
/******************************************************************************
- 개발자,일      : 조성문  20161201
- 프로그램명    : SP_CHK_SEQL_SELL_INIT
- 프로그램타입 : procedure
- 기능            : 집중베팅현황을 조회하는 프로시저 호출
- IN  인수       : 
- 프로세스
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
        ,100000    STND_AMOUNT --10만원
        ,30 STND_TIME                --30초 이내
        ,10 STND_CNT                  --10건 이상
    INTO V_YEAR_TOTE, V_DATE_TOTE, V_STND_AMOUNT, V_STND_TIME, V_STND_CNT        
    FROM DUAL;     
    
    SP_CHK_SEQL_SELL(V_YEAR_TOTE, V_DATE_TOTE, V_STND_AMOUNT, V_STND_TIME, V_STND_CNT);

END SP_CHK_SEQL_SELL_INIT;
/
