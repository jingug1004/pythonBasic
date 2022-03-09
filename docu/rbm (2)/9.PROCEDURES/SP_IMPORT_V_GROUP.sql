CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_V_GROUP

IS
/******************************************************************************
- 개발자,일      : yslee 2009.08.12
- 프로그램명     : SP_IMPORT_V_GROUP
- 프로그램타입   : procedure
- 기능           : JUPITERLINK 부서정보를 경주사업관리시스템 DB에 저장한다.
- IN  인수       :

- 프로세스
    1. 기존 부서정보를 삭제
    2. V_GROUP@JUPITERLINK 정보를 조회해서 V_GROUP 테이블에 입력


- 프로시져는 매일 밤 8시에 작동되도록 한다.(스케줄러 등록)
******************************************************************************/
    v_job_date date;
    v_err_code number;
    v_cnt number;
    v_err_mesg varchar(255);


begin
    DBMS_OUTPUT.ENABLE;

    SP_BTC_LOG('005','I','SP_IMPORT_V_GROUP','START','');

    -- 1. 기존 부서 데이터 삭제
    DELETE FROM  V_GROUP;

    -- 2. V_GROUP@JUPITERLINK 부서 데이터 입력
    INSERT  INTO V_GROUP
          SELECT     GROUP_ID
                    ,GROUP_SPEC_ID
                    ,GROUP_NAME
                    ,GROUP_OPENDATE
                    ,GROUP_INDENT
                    ,GROUP_PARENT
                    ,CHILD_CNT
                    ,GROUP_ORDER
                    ,GROUP_POSITION
                    ,DOC_GROUP_ID
                    ,DOC_GROUP_NAME
                    ,GROUP_DOC_CODE
                    ,GROUP_TEL
                    ,ORG_ID
                    ,GROUP_CHIEF
                    ,REAL_ORG_ID
                    ,GROUP_CLOSEDATE
                    ,REG_GROUP_ID
                    ,REG_GROUP_NAME
            FROM V_GROUP@JUPITERLINK;

    SELECT COUNT(1)
          INTO v_cnt
      FROM V_GROUP;

    IF v_cnt = 0 THEN
        ROLLBACK;
        SP_BTC_LOG('005','W','SP_IMPORT_V_GROUP','Import count = 0 ... rollback ','');
    ELSE
        COMMIT;
    END IF;

    SP_BTC_LOG('005','I','SP_IMPORT_V_GROUP','END','');
    RETURN;

    EXCEPTION
        WHEN OTHERS THEN
            v_err_code := SQLCODE();
            v_err_mesg := SQLERRM;
            ROllBACK;
            SP_BTC_LOG('005','E','SP_IMPORT_V_GROUP','ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'');
            RETURN;

end;
/
