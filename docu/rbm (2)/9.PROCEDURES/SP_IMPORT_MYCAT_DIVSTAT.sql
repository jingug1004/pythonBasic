CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_MYCAT_DIVSTAT( P_CUR_DATE IN VARCHAR2 )

IS
/******************************************************************************
- 개발자,일      : jsshin 2012.03.10
- 프로그램명     : SP_IMPORT_mycat_divstat
- 프로그램타입   : procedure
- 기능           : 마이켓의 입출력 자료통계를 가져온다.
- IN  인수       :

- 프로세스
    1. 당일 마이켓 입출력자료 삭제
    2. 마이켓 DB에서 당일자료를 조회하여 TBRS_MYCAT_DIVSTAT 테이블에 입력


- 프로시져는 매일 밤 8시에 작동되도록 한다.(스케줄러 등록)
******************************************************************************/
    v_job_date date;
    v_err_code number;
    v_err_mesg varchar(255);
    V_LOG_SEQ NUMBER(15);
    V_LOG_SERL_NO NUMBER(3);

BEGIN
    DBMS_OUTPUT.ENABLE;

    SELECT SQ_TBRK_BTC_LOG.NEXTVAL
      INTO V_LOG_SEQ
    FROM DUAL;
    
    V_LOG_SERL_NO := 0;    
    SP_BTC_LOG_SEQ('009','I','SP_IMPORT_MYCAT_DIVSTAT','START','ARG:'||P_CUR_DATE, V_LOG_SEQ, V_LOG_SERL_NO);

    -- 1. 기존  데이터 삭제
    DELETE
      FROM  TBES_MYCAT_DIVSTAT
     WHERE  RACE_DAY   = P_CUR_DATE;

    -- 2. 마이켓 입출력 자료 입력
    INSERT INTO TBES_MYCAT_DIVSTAT(
                RACE_DAY, SELL_CD, COMM_NO, DIV_NO, OUT_AMT, IN_AMT, MILEAGE_AMT, 
                BONUS_AMT, INST_DT, UPDT_DT, PURGE_AMT,CONSIGN_AMT)
    SELECT RACE_DAY,
                 SELL_CD,
                 COMM_NO,                          --본장:01, 지점:07 ==> 모두 '06'으로 통일
                 DIV_NO,       --앞으로 지점별구분을 위해서 COMM_NO가 사용되지만, 현시스템에서는 DIV_NO를 사용
                 SUM (OUT_AMT) OUT_AMT,
                 SUM (IN_AMT) IN_AMT,
                 SUM (MILEAGE_AMT) MILEAGE_AMT,
                 SUM (BONUS_AMT) BONUS_AMT,
                 SYSDATE,
                 NULL,
                 SUM (PURGE_AMT) AS PURGE_AMT,
                 SUM(CONSIGN_AMT) CONSIGN_AMT
            FROM (SELECT RACE_DAY,
                         SELL_CD,
                         COMM_NO,
                         DIV_NO,
                         OUT_AMT,
                         IN_AMT,
                         MILEAGE_AMT,
                         BONUS_AMT,
                         PURGE_AMT,
                         CONSIGN_AMT
                    FROM V_MYCAT_DIVSTAT@CYBETLINK
                    WHERE RACE_DAY = P_CUR_DATE)
        GROUP BY RACE_DAY,SELL_CD,COMM_NO,DIV_NO;

    COMMIT;
    SP_BTC_LOG_SEQ('009','I','SP_IMPORT_MYCAT_DIVSTAT','END','', V_LOG_SEQ, V_LOG_SERL_NO);
    RETURN;


    EXCEPTION
        WHEN OTHERS THEN
            v_err_code := SQLCODE();
            v_err_mesg := SQLERRM;
            ROLLBACK;
            SP_BTC_LOG_SEQ('009','E','SP_IMPORT_MYCAT_DIVSTAT','ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'', V_LOG_SEQ, V_LOG_SERL_NO);
            RETURN;

END;
/
