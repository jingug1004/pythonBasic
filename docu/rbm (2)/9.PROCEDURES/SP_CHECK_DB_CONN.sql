CREATE OR REPLACE PROCEDURE USRBM.SP_CHECK_DB_CONN( P_DB_NAME IN VARCHAR2 )

IS
/******************************************************************************
- 개발자,일      : jsshin 2013.01.24
- 프로그램명     : SP_CHECK_DB_CONN
- 프로그램타입   : procedure
- 기능           :  공단 본부의 db 장애 유무를 체크한다.
- IN  인수       :

- 프로세스
    1. 현재의 상태를 조회한다.
    2. 장애 유무 조회
    3. 장애시 장애횟수를 증가시킨다.
    4. 장애횟수가 기준횟수 이상이면 장애상태로 변경한다.

- 프로시져는 5분 간격으로 수행한다.
******************************************************************************/
    V_DB_NAME     VARCHAR(10);
    V_DB_STATUS VARCHAR(50);
    V_ERR_CNT     NUMBER;
    V_MAX_ERR_CNT     NUMBER;
    V_STATUS      VARCHAR(10);
    
    v_err_code NUMBER;
    v_err_mesg VARCHAR(255);
    V_LOG_SEQ NUMBER(15);
    V_LOG_SERL_NO NUMBER(3);

     
BEGIN
    DBMS_OUTPUT.ENABLE;
    
    SELECT SQ_TBRK_BTC_LOG.NEXTVAL
      INTO V_LOG_SEQ
    FROM DUAL;    
    V_LOG_SERL_NO := 0;    
    --SP_BTC_LOG_SEQ('013','I','SP_CHECK_DB_CONN','START',P_DB_NAME, V_LOG_SEQ, V_LOG_SERL_NO);

    -- 1) 현재의 상태를 조회한다.
    SELECT CD,                       -- DB명  
           CD_NM,                -- 현재 상태        
           TO_NUMBER(CD_TERM1),            -- 장애횟수
           TO_NUMBER(CD_TERM2)
    INTO   V_DB_NAME, 
           V_DB_STATUS, 
           V_ERR_CNT,
           V_MAX_ERR_CNT
    FROM TBRK_SPEC_CD
    WHERE GRP_CD = '147'
    AND   CD = NVL(P_DB_NAME,'VENUS');

    /*
    IF V_ERR_CNT = V_MAX_ERR_CNT THEN
        SP_ALARM_SEND_SMS( '011'
                                          , '본부 '||V_DB_NAME||' DB 장애 발생'
                                          , '01047'
                                          , 'RBB1080'
                                          , 'SP_CHECK_DB_CONN');
    END IF;
    */

    -- 2) 장애 유무를 조회한다. 오류시에는 EXCEPTION에서 처리
    IF V_DB_NAME = 'VENUS' THEN
        SELECT 1
        INTO     V_STATUS
        FROM DUAL@VENUSLINK_WEB;
    ELSIF V_DB_NAME = 'JUPITER' THEN
        SELECT 1
        INTO     V_STATUS
        FROM DUAL@JUPITERLINK;
        --FROM DUAL@ISPDB_TEST;
    ELSE
        RETURN;
    END IF;
    
    IF V_ERR_CNT > 0 THEN       -- 장애가 1회라도 발생한 경우 정상상태로 변경
        UPDATE TBRK_SPEC_CD
        SET       CD_NM = '0', 
                    CD_TERM1 = '0'
        WHERE  GRP_CD = '147'
            AND  CD =  NVL(P_DB_NAME,'VENUS');

        V_LOG_SERL_NO := V_LOG_SERL_NO + 1;
        SP_BTC_LOG_SEQ('013','I','SP_CHECK_DB_CONN','UPDATE','', V_LOG_SEQ, V_LOG_SERL_NO);
        
        
       IF V_ERR_CNT >= V_MAX_ERR_CNT THEN
            SP_ALARM_SEND_SMS( '011'
                                              , '본부 '||V_DB_NAME||' DB 복구완료'
                                              , 'admin'
                                              , 'RBB1080'
                                              , 'SP_CHECK_DB_CONN');
        END IF;
        
        
        COMMIT;
    END IF;
    
    --SP_BTC_LOG_SEQ('013','I','SP_CHECK_DB_CONN','END',V_DB_NAME, V_LOG_SEQ, V_LOG_SERL_NO);
        
    RETURN;


    EXCEPTION

        WHEN OTHERS THEN
            
            v_err_code := SQLCODE();
            v_err_mesg := SQLERRM;
            
            -- 3) 장애시 장애횟수를 증가시킨다.
            -- 4) 장애횟수가 기준횟수 이상이면 장애상태로 변경한다.
            UPDATE TBRK_SPEC_CD
            SET       CD_NM = CASE WHEN TO_NUMBER(CD_TERM1)>=TO_NUMBER(CD_TERM2) THEN '1' ELSE '0' END,
                        CD_TERM1 = TO_CHAR(TO_NUMBER(CD_TERM1) + 1)
            WHERE  GRP_CD = '147'
                AND  CD =  NVL(P_DB_NAME,'VENUS'); 
            
            SP_BTC_LOG_SEQ('013','E','SP_CHECK_DB_CONN',V_DB_NAME||'DB ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'', V_LOG_SEQ, V_LOG_SERL_NO);
           
            IF V_MAX_ERR_CNT = (V_ERR_CNT +1) THEN
                SP_ALARM_SEND_SMS('011', '공단본부 DB 접속오류('||V_DB_NAME||')', 'admin', 'RSY6010','알림관리');
            END IF;
             
            COMMIT;
            
            RETURN;
END;
/
