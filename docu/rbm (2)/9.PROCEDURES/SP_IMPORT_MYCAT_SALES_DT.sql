CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_MYCAT_SALES_DT IS
/******************************************************************************
- 개발자,일      : jsshin 2012.03.10
- 프로그램명     : SP_IMPORT_MYCAT_SALES_DT
- 프로그램타입   : procedure
- 기능           : 마이켓의 입출력 자료통계를 가져온다.
- IN  인수       :

- 프로세스
    1. 당일 마이켓 입출력자료 삭제
    2. 마이켓 DB에서 당일자료를 조회하여 TBES_SDL_DT 테이블에 입력


- 프로시져는 매일 밤 8시에 작동되도록 한다.(스케줄러 등록)
******************************************************************************/
    V_JOB_DATE       DATE;
    V_ERR_CODE      NUMBER;
    V_ERR_MESG      VARCHAR(255);
    V_LOG_SEQ        NUMBER(15);
    V_LOG_SERL_NO NUMBER(3);
    V_CUR_DATE      VARCHAR(8);
    V_MEET_CD          VARCHAR(3);
    V_STND_YEAR    VARCHAR(4);
    V_TMS               VARCHAR(3);
    V_DAY_ORD        VARCHAR(3);
    V_RACE_NO        VARCHAR(4);
    V_MYCAT_BON_AMT  NUMBER(16);
    V_MYCAT_TOT_AMT NUMBER(16);
    V_MYCAT_TOT_DT  NUMBER(16);
    
BEGIN
   RETURN;
   --DBMS_OUTPUT.ENABLE;
    SELECT SQ_TBRK_BTC_LOG.NEXTVAL, TO_CHAR(SYSDATE, 'YYYYMMDD')
      INTO V_LOG_SEQ, V_CUR_DATE
    FROM DUAL;
    
    
    V_LOG_SERL_NO := 0;    
    SP_BTC_LOG_SEQ('009','I','SP_IMPORT_MYCAT_SALES_DT','START','ARG:'||V_CUR_DATE, V_LOG_SEQ, V_LOG_SERL_NO);
    
   -- 1) 당일 확정된 지점별 매출의 최종 경주, 그린카드 총 발매금액, 본장 발매금액을 조회한다.
       SELECT MEET_CD,    
                   STND_YEAR, 
                   TMS, 
                   DAY_ORD, 
                   RACE_NO, 
                   MYCAT_TOT_DT,
                   MYCAT_BON_AMT
       INTO    V_MEET_CD, 
                   V_STND_YEAR,
                   V_TMS,
                   V_DAY_ORD, 
                   V_RACE_NO, 
                   V_MYCAT_TOT_DT,
                   V_MYCAT_BON_AMT
       FROM (                                       
                   SELECT  A.MEET_CD, A.STND_YEAR, A.TMS, A.DAY_ORD, A.RACE_NO, 
                                SUM(DIV_TOTAL) AS MYCAT_TOT_DT, 
                                NVL(SUM(DECODE(DIV_NO,'00',DIV_TOTAL)),0) AS MYCAT_BON_AMT
                   FROM TBES_SDL_DT A, VW_SDL_INFO I
                   WHERE A.MEET_CD = I.MEET_CD
                       AND A.STND_YEAR = I.STND_YEAR
                       AND A.TMS = I.TMS
                       AND A.DAY_ORD = I.DAY_ORD
                       AND I.RACE_DAY=V_CUR_DATE
                       AND A.COMM_NO = '06'                      
                  GROUP BY A.MEET_CD, A.STND_YEAR, A.TMS, A.DAY_ORD, A.RACE_NO
                  ORDER BY A.MEET_CD , A.RACE_NO DESC  
                )
      WHERE ROWNUM = 1;
         --DBMS_OUTPUT.PUT_LINE('V_RACE_NO['||V_RACE_NO||']');

    -- 2) 기존에 반영된 자료가 존재하는 경우
    IF V_MYCAT_BON_AMT > 0 THEN
            -- 기존에 반영된 이후 환불 등 추가 변경사항이 존재하는 경우
            SELECT SUM(NET_AMT) MYCAT_TOT_AMT
            INTO     V_MYCAT_TOT_AMT 
            FROM   VW_MYCAT_SALES
            WHERE RACE_DAY = V_CUR_DATE
            AND      MEET_CD = V_MEET_CD
            AND      RACE_NO = V_RACE_NO;            
            IF V_MYCAT_TOT_DT = V_MYCAT_TOT_AMT THEN
                    -- 2-1) 금액 차이가 없는 경우
                    --DBMS_OUTPUT.PUT_LINE('V_MYCAT_BON_AMT IS '||V_MYCAT_BON_AMT);        
                    SP_BTC_LOG_SEQ('009','I','SP_IMPORT_MYCAT_SALES_DT','END','이미 반영되어 있음('||V_RACE_NO||'경주)', V_LOG_SEQ, V_LOG_SERL_NO);
                    RETURN;
            ELSE 
                    --DBMS_OUTPUT.PUT_LINE('V_MYCAT_BON_AMT IS '||V_MYCAT_BON_AMT);        
                    SP_BTC_LOG_SEQ('009','I','SP_IMPORT_MYCAT_SALES_DT','ING','추가 반영 있음('||V_RACE_NO||'경주 (DT:'||V_MYCAT_TOT_DT||', MYCAT:'||V_MYCAT_TOT_AMT||'))', V_LOG_SEQ, V_LOG_SERL_NO);
            END IF;
    ELSE

            -- 3) 기존에 자료가 없는 경우 그린카드 금액을 조회
             SELECT SUM(NET_AMT) MYCAT_TOT_AMT
             INTO     V_MYCAT_TOT_AMT 
              FROM   VW_MYCAT_SALES
              WHERE RACE_DAY = V_CUR_DATE
              AND      MEET_CD = V_MEET_CD
              AND      RACE_NO = V_RACE_NO;
                 --DBMS_OUTPUT.PUT_LINE('V_MYCAT_TOT_AMT IS '||V_MYCAT_TOT_AMT);
              
            -- 4) 처음 입력시 금액이 일치하지 않는 경우 종료 
                 --DBMS_OUTPUT.PUT_LINE('V_MYCAT_TOT_DT['||V_MYCAT_TOT_DT||'], V_MYCAT_TOT_AMT['||V_MYCAT_TOT_AMT||']');
            IF V_MYCAT_TOT_DT <> V_MYCAT_TOT_AMT THEN
                -- 에러 로깅 
                 --DBMS_OUTPUT.PUT_LINE('V_MYCAT_TOT_DT IS NOT V_MYCAT_TOT_AMT'||TO_CHAR(V_MYCAT_TOT_DT)||':'||V_MYCAT_TOT_AMT);
                SP_BTC_LOG_SEQ('009','W','SP_IMPORT_MYCAT_SALES_DT','END',V_RACE_NO||'경주 금액 불일치(DT:'||V_MYCAT_TOT_DT||', MYCAT:'||V_MYCAT_TOT_AMT||')', V_LOG_SEQ, V_LOG_SERL_NO);
                RETURN;
            END IF ;     
    
    END IF;
    
    -- 5) 기존  데이터 삭제(해당 경주만 삭제)
    --DBMS_OUTPUT.PUT_LINE('DELETE TBES_SDL_DT ');
    DELETE 
      FROM  TBES_SDL_DT
     WHERE MEET_CD = V_MEET_CD
         AND  STND_YEAR = V_STND_YEAR
         AND  TMS = V_TMS
         AND  DAY_ORD = V_DAY_ORD
         AND  RACE_NO = V_RACE_NO
         AND  COMM_NO = '06';    --그린카드

    -- 7) 지점별 매출액에 자료 입력
    --DBMS_OUTPUT.PUT_LINE('INSERT TBES_SDL_DT ');
    INSERT INTO TBES_SDL_DT
                        (MEET_CD, STND_YEAR, TMS, DAY_ORD, RACE_NO, SELL_CD, COMM_NO, DIV_NO, DIV_TOTAL, REFUND, INST_ID, INST_DT)
         SELECT MEET_CD,
                     STND_YEAR,
                     TMS,
                     DAY_ORD,
                     RACE_NO,
                     SELL_CD,                 --SELL_CD:01. .. "TOTE SYSTEM" .. ... ..
                     COMM_NO,                          --..:01, ..:07 ==> .. '06'.. ..
                     DIV_NO,
                     SUM (NET_AMT) AS DIV_TOTAL,
                     SUM (REFUND) AS REFUND,
                     'BATCH',
                     SYSDATE
             FROM VW_MYCAT_SALES 
             WHERE RACE_DAY = V_CUR_DATE
                 AND  MEET_CD  = V_MEET_CD
                 AND  RACE_NO  = V_RACE_NO               
            GROUP BY MEET_CD,STND_YEAR,TMS,DAY_ORD,RACE_NO,SELL_CD,COMM_NO, DIV_NO;
            
            
         --DBMS_OUTPUT.PUT_LINE(' COMMIT.. ');
    COMMIT;
    SP_BTC_LOG_SEQ('009','I','SP_IMPORT_MYCAT_SALES_DT','END',V_RACE_NO||'경주 세분화 완료', V_LOG_SEQ, V_LOG_SERL_NO);
    RETURN;
    
    EXCEPTION
     WHEN NO_DATA_FOUND THEN
        ROLLBACK;
     WHEN OTHERS THEN
        V_ERR_CODE := SQLCODE();
        V_ERR_MESG := SQLERRM;
        ROLLBACK;
        SP_BTC_LOG_SEQ('009','E','SP_IMPORT_MYCAT_SALES_DT','ERROR',  'ERROR CODE'||V_ERR_CODE|| ',   ERROR LOG:'||V_ERR_MESG, V_LOG_SEQ, V_LOG_SERL_NO);
       -- Consider logging the error and then re-raise
END;
/
