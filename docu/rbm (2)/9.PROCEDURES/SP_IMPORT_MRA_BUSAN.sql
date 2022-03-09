CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_MRA_BUSAN

IS
/******************************************************************************
- 개발자,일      :
- 프로그램명     : SP_IMPORT_MRA_BUSAN
- 프로그램타입   : procedure
- 기능           : 부산 경정 매출액.
- IN  인수       :

- 프로세스
    
- OUTSITE(부산)       -> DIV_NO
    101 광복          -> 01
    102 서면          -> 02
    003 본장          -> 03
    005 본장 전자카드 -> 03
    006 광복 전자카드 -> 01
    
- OUTSITE(창원)       -> DIV_NO
    001 본장(현금)     -> 01
    002 김해지점(현금) -> 02
    004 전자카드(전체=본장+김해)   :향후 본장전자카드, 김해전자카드로 분리 예정
    
    

- 프로시져는 수, 목 10~19시까지 5분간격  작동되도록 한다.(스케줄러 등록)
******************************************************************************/
    P_CUR_DATE VARCHAR(8);
    P_MEET_CD VARCHAR(3);
    P_STND_YEAR VARCHAR(4);
    P_TMS           VARCHAR(3);
    P_DAY_ORD    VARCHAR(2);
    V_ERR_CODE NUMBER;
    V_ERR_MESG VARCHAR(255);
    V_LOG_SEQ NUMBER(15);
    V_LOG_SERL_NO NUMBER(3);

BEGIN
    DBMS_OUTPUT.ENABLE;
    SELECT SQ_TBRK_BTC_LOG.NEXTVAL
      INTO V_LOG_SEQ
    FROM DUAL;
    
    V_LOG_SERL_NO := 0;    
    --SP_BTC_LOG_SEQ('006','I','SP_IMPORT_MRA_BUSAN','START','', V_LOG_SEQ, V_LOG_SERL_NO);
    
    SELECT TO_CHAR(SYSDATE, 'YYYYMMDD')
    INTO   P_CUR_DATE
    FROM DUAL;
    
    SELECT MEET_CD, STND_YEAR, TMS, DAY_ORD
    INTO    P_MEET_CD, P_STND_YEAR, P_TMS, P_DAY_ORD
    FROM  VW_SDL_INFO
    WHERE  RACE_DAY = P_CUR_DATE
      AND  MEET_CD ='003';

    SP_IMPORT_MRA_BUSAN_RACE_NO(P_CUR_DATE);
   
    COMMIT;
    --SP_BTC_LOG_SEQ('006','I','SP_IMPORT_MRA_BUSAN','END','', V_LOG_SEQ, V_LOG_SERL_NO);
    
    RETURN;


    EXCEPTION
        WHEN OTHERS THEN
            V_ERR_CODE := SQLCODE();
            V_ERR_MESG := SQLERRM;
            ROLLBACK;
            SP_BTC_LOG_SEQ('006','E','SP_IMPORT_MRA_BUSAN','ERROR CODE:'||V_ERR_CODE || ' ERROR LOG:'||V_ERR_MESG,'', V_LOG_SEQ, V_LOG_SERL_NO);
            RETURN;

END;
/
