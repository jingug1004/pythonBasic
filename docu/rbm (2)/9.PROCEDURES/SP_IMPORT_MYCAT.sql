CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_MYCAT

IS
/******************************************************************************
- 개발자,일      : jsshin 2012.03.10
- 프로그램명     : SP_IMPORT_mycat
- 프로그램타입   : procedure
- 기능           : 마이켓의 입출력, 매출정보 자료통계를 가져온다.
- IN  인수       :

- 프로세스
    1. 당일 마이켓 입출력자료 삭제 후 입력하는 SP_IMPORT_MYCAT_DIVSTAT 프로시저 호출
    2. 당일 마이켓 매출자료 삭제 후 입력하는 SP_IMPORT_MYCAT_SALES 프로시저 호출


- 프로시져는 매일 밤 8시에 작동되도록 한다.(스케줄러 등록)
******************************************************************************/
    v_job_date date;
    v_err_code number;
    v_err_mesg varchar(255);
    
BEGIN
    DBMS_OUTPUT.ENABLE;

    -- 투표소 정보 입력
    SP_IMPORT_DIV_INFO;
    
    -- 그린카드 투표소 정보 입력 
    SP_IMPORT_MYCAT_DIV_INFO;

    -- 그린카드 매출정보 입력
    SP_IMPORT_MYCAT_DIVSTAT(TO_CHAR(SYSDATE, 'YYYYMMDD'));
    
    SP_IMPORT_MYCAT_SALES(TO_CHAR(SYSDATE, 'YYYYMMDD'));
    
    -- 그린카드 발급건수 등 일일 통계자료 가져오기
    SP_IMPORT_MYCAT_SALES_STAT(TO_CHAR(SYSDATE, 'YYYYMMDD'));

    -- DW(원시자료분석db) 지점별 승식별 매출정보 입력
    SP_IMPORT_DW_SALES(TO_CHAR(SYSDATE, 'YYYYMMDD'));
    
    CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_MYCAT

IS
/******************************************************************************
- 개발자,일      : jsshin 2012.03.10
- 프로그램명     : SP_IMPORT_mycat
- 프로그램타입   : procedure
- 기능           : 마이켓의 입출력, 매출정보 자료통계를 가져온다.
- IN  인수       :

- 프로세스
    1. 당일 마이켓 입출력자료 삭제 후 입력하는 SP_IMPORT_MYCAT_DIVSTAT 프로시저 호출
    2. 당일 마이켓 매출자료 삭제 후 입력하는 SP_IMPORT_MYCAT_SALES 프로시저 호출


- 프로시져는 매일 밤 8시에 작동되도록 한다.(스케줄러 등록)
******************************************************************************/
    v_job_date date;
    v_err_code number;
    v_err_mesg varchar(255);
    
BEGIN
    DBMS_OUTPUT.ENABLE;

    -- 투표소 정보 입력
    SP_IMPORT_DIV_INFO;
    
    -- 그린카드 투표소 정보 입력 
    SP_IMPORT_MYCAT_DIV_INFO;

    -- 그린카드 매출정보 입력
    SP_IMPORT_MYCAT_DIVSTAT(TO_CHAR(SYSDATE, 'YYYYMMDD'));
    
    SP_IMPORT_MYCAT_SALES(TO_CHAR(SYSDATE, 'YYYYMMDD'));
    
    -- 그린카드 발급건수 등 일일 통계자료 가져오기
    SP_IMPORT_MYCAT_SALES_STAT(TO_CHAR(SYSDATE, 'YYYYMMDD'));

    -- DW(원시자료분석db) 지점별 승식별 매출정보 입력
    SP_IMPORT_DW_SALES(TO_CHAR(SYSDATE, 'YYYYMMDD'));
    
    -- 지정좌석실 매출자료 생성
    SP_IMPORT_TELMP_FIXEDSEAT(TO_CHAR(SYSDATE, 'YYYYMMDD'));
    

    RETURN;


    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            RETURN;
END;
/


    RETURN;


    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            RETURN;
END;
/
