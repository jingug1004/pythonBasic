CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_DW_SALES( P_DATE IN VARCHAR2 )

IS
/******************************************************************************
- 개발자,일      : jsshin 2012.03.10
- 프로그램명     : SP_IMPORT_DW_SALES
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
    V_IMPORT_CNT number(5);
    P_CUR_DATE VARCHAR(255);
BEGIN
    DBMS_OUTPUT.ENABLE;

    SELECT SQ_TBRK_BTC_LOG.NEXTVAL
      INTO V_LOG_SEQ
    FROM DUAL;
    
    IF P_DATE = NULL THEN
        SELECT TO_CHAR(SYSDATE, 'YYYYMMDD')
        INTO   P_CUR_DATE
        FROM   DUAL;
    ELSE 
        P_CUR_DATE := P_DATE;
    END IF;
    
    V_LOG_SERL_NO := 0;    
    SP_BTC_LOG_SEQ('009','I','SP_IMPORT_DW_SALES','START','ARG:'||P_CUR_DATE, V_LOG_SEQ, V_LOG_SERL_NO);

    -- 1. 기존  데이터 삭제
    DELETE
      FROM  TBES_DW_SALES
     WHERE  RACE_DAY   = P_CUR_DATE;

    -- 2. 마이켓 입출력 자료 입력
    INSERT INTO TBES_DW_SALES
           (MEET_CD, STND_YEAR, TMS, DAY_ORD, RACE_NO, SELL_CD, COMM_NO, POOL_CD, TOTAL_AMT, RACE_DAY)
     SELECT TRIM(TO_CHAR(P.MEET_CD,'000')) AS MEET_CD,
             I.STND_YEAR,
             I.TMS,
             I.DAY_ORD,
             TRIM(TO_CHAR(P.RACE_NO,'00')) AS RACENO,
             DECODE(P.SELL_CD, 1, '01', 3, '03') AS SELL_CD,                 --SELL_CD:01로 통일 "TOTE SYSTEM" 전송 데이터 기준
             TRIM(TO_CHAR(CASE WHEN SELL_CD=3 AND COMM_NO<10 THEN 98 ELSE COMM_NO END,'00')) AS COMM_NO,                          --본장:01, 지점:07 ==> 모두 '06'으로 통일
             TRIM(TO_CHAR(POOL_CD, '000')) AS POOL_CD,
             SUM (TOTAL_AMT) - SUM(NVL(refund_amt,0)) TOTAL_AMT,
             I.RACE_DAY
        FROM PC_SATLIT@DW01LINK P, VW_SDL_INFO I
        WHERE 1=1
        AND   P.YEAR_DATE = I.RACE_DAY
        AND   TRIM(TO_CHAR(P.MEET_CD,'000')) = I.MEET_CD
        AND   P.TOTAL_AMT > 0
        AND   I.RACE_DAY = P_CUR_DATE
        GROUP BY TRIM(TO_CHAR(P.MEET_CD,'000')),
             I.STND_YEAR,
             I.TMS,
             I.DAY_ORD,
             TRIM(TO_CHAR(P.RACE_NO,'00')),
             DECODE(P.SELL_CD, 1, '01', 3, '03'),
             TRIM(TO_CHAR(CASE WHEN SELL_CD=3 AND COMM_NO<10 THEN 98 ELSE COMM_NO END,'00')), 
             TRIM(TO_CHAR(POOL_CD, '000')),
             I.RACE_DAY;
            
    SELECT COUNT(*) AS CNT
    INTO   V_IMPORT_CNT
    FROM   TBES_DW_SALES
    WHERE  RACE_DAY   = P_CUR_DATE;
    
    IF V_IMPORT_CNT = 0 THEN
        SP_BTC_LOG_SEQ('009','E','SP_IMPORT_DW_SALES','END [자료 없음:'||P_CUR_DATE||']','', V_LOG_SEQ, V_LOG_SERL_NO);
        ROLLBACK;
    ELSE
        COMMIT;
        SP_BTC_LOG_SEQ('009','I','SP_IMPORT_DW_SALES','END [처리완료:'||P_CUR_DATE||':'||TO_CHAR(V_IMPORT_CNT)||'건]','', V_LOG_SEQ, V_LOG_SERL_NO);
        RETURN;
    END IF;


    EXCEPTION
        WHEN OTHERS THEN
            v_err_code := SQLCODE();
            v_err_mesg := SQLERRM;
            ROLLBACK;
            SP_BTC_LOG_SEQ('009','E','SP_IMPORT_DW_SALES','ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'', V_LOG_SEQ, V_LOG_SERL_NO);
            RETURN;

END;
/
