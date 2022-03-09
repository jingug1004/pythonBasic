CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_FIXEDSEAT_TRADE

IS
/******************************************************************************
- 개발자,일      : jsshin 2012.03.10
- 프로그램명     : SP_IMPORT_FIXEDSEAT_TRADE(고정좌석실 입장인원 가져오기)
- 프로그램타입   : procedure
- 기능           : 지점 및 본장의  입장료징수시스템 연계용 DB에서 자료를 가져온다.(매일 1회 실행)
- IN  인수       :

- 프로세스
    1. 연계DB에 존재하는 지점별 최종연번을 조회한다.
    2. 지점별로 최종연번 이전의 자료를 전부 가져온다.
    3. 연계DB에서 최종연번까지 자료를 삭제한다.

- 프로시져는 매일 밤 11:30시에 작동되도록 한다.(스케줄러 등록)
******************************************************************************/
    v_job_date DATE;
    v_err_code NUMBER;
    v_err_mesg VARCHAR(255);
    V_LOG_SEQ NUMBER(15);
    V_LOG_SERL_NO NUMBER(3);
    V_MAX_SEQ_NO_FS NUMBER(20);
    V_PROCESS VARCHAR(255);
            
    -- 03. 광명지정좌석실 연동 CURSOR
    CURSOR CUR_MAX_SEQ_FS_KM IS
        SELECT LOC, MAX(SEQ) AS MAX_SEQ
        FROM   TBRC_FS_STATUS_KM
        WHERE  JOB_TYPE = 'F'
        GROUP BY LOC;
           
     -- 04. 커서에서 받을 변수 선언    
     MAX_SEQ_ROW_FS_KM  CUR_MAX_SEQ_FS_KM%ROWTYPE;
     
BEGIN
    DBMS_OUTPUT.ENABLE;

    SELECT SQ_TBRK_BTC_LOG.NEXTVAL
      INTO V_LOG_SEQ
    FROM DUAL;
    
    V_LOG_SERL_NO := 0;    
    SP_BTC_LOG_SEQ('012','I','SP_IMPORT_FIXEDSEAT_TRADE','START','', V_LOG_SEQ, V_LOG_SERL_NO);

    V_PROCESS := '23';
     
    --30) 지정좌석실의 자료 가져오기 -------------------------------------
    --31) 지정좌석실의 최종 전송 정보 가져온다.
    SELECT MAX(SEQ_NO) AS MAX_SEQ_NO
    INTO    V_MAX_SEQ_NO_FS
    FROM    TBIF_FS_STATUS@TMONEY_LINK;
    V_PROCESS := '31';
    
    --32) 연계DB의 지정좌석실의 거래내역을 운영DB로 복사한다. 
    INSERT INTO TBRC_FS_TRADE
                        (SEQ_NO, COMM_NO, ISSUE_DT, ISSUE_TM, SEAT_NO, 
                         AMOUNT, AMOUNT1, UNI_CODE, UNI_SUB, IDATE)
    SELECT   SEQ_NO, COMM_NO, ISSUE_DT, ISSUE_TM, SEAT_NO, 
                  AMOUNT, AMOUNT1, UNI_CODE, UNI_SUB, SYSDATE
    FROM  TBIF_FS_ISSUE@TMONEY_LINK
    WHERE SEQ_NO <= V_MAX_SEQ_NO_FS;                
    V_PROCESS := '32';
        
    --33) 티머니연계DB의 지정좌석실  정보를 삭제한다.
     DELETE FROM TBIF_FS_ISSUE@TMONEY_LINK
            WHERE SEQ_NO <= V_MAX_SEQ_NO_FS;
     
     -- 자료 이전 건에 대해서는 우선 commit 처리한다. 
     --COMMIT; --20150226
     V_PROCESS := '33';
     
    --40) 광명 지정좌석실의 최종 전송 정보 가져온다.  -----------------------------------------
      -- 41) 연계DB에서 지점별 MAX연번을 가져온다. 
    OPEN CUR_MAX_SEQ_FS_KM;
        LOOP
            --42) 건별로 지점별 연번 조회
            FETCH CUR_MAX_SEQ_FS_KM INTO MAX_SEQ_ROW_FS_KM;
            EXIT WHEN CUR_MAX_SEQ_FS_KM%NOTFOUND;
            V_PROCESS := '41';
            
            --42) 연계DB의 지정좌석실의 거래내역을 운영DB로 복사한다. 
            INSERT INTO TBRC_FS_TRADE_KM
                        (SEQ_NO, COMM_NO, ISSUE_DT, ISSUE_TM, MENU_CD,QTY, 
                         AMOUNT, IDATE)
            SELECT   SEQ, 
                          DECODE(LOC, '80020','04','80021','05',LOC) AS COMM_NO, 
                          SALE_DATE, SUBSTR(SALE_TIME,1,4), MENU_CODE,QTY, 
                          AMT,  SYSDATE
            FROM    TBRC_FS_ISSUE_KM
            WHERE  LOC =  MAX_SEQ_ROW_FS_KM.LOC
            AND    JOB_TYPE = 'F'
            AND    SEQ  <= MAX_SEQ_ROW_FS_KM.MAX_SEQ;
            V_PROCESS := '42';
            
            --43) 티머니연계DB의 지정좌석실  정보를 삭제한다.
            DELETE FROM TBRC_FS_ISSUE_KM
                WHERE  LOC =  MAX_SEQ_ROW_FS_KM.LOC
                AND    JOB_TYPE = 'F'
                AND    SEQ  <= MAX_SEQ_ROW_FS_KM.MAX_SEQ;
        END LOOP;
        CLOSE CUR_MAX_SEQ_FS_KM;
        
         -- 자료 이전 건에 대해서는 우선 commit 처리한다. 
        COMMIT;
    
    SP_BTC_LOG_SEQ('012','I','SP_IMPORT_FIXEDSEAT_TRADE','END','', V_LOG_SEQ, V_LOG_SERL_NO);
    RETURN;


    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            
            v_err_code := SQLCODE();
            v_err_mesg := SQLERRM;
            
            SP_BTC_LOG_SEQ('012','E','SP_IMPORT_FIXEDSEAT_TRADE','[PROC:'||V_PROCESS||']'||'ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'', V_LOG_SEQ, V_LOG_SERL_NO);
            RETURN;
END;
/
