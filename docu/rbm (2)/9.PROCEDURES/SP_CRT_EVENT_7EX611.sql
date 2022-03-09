CREATE OR REPLACE PROCEDURE USRBM.SP_CRT_EVENT_7EX611(P_CUR_DATE VARCHAR)
 IS
  
    TYPE  VA_VCHAR IS VARRAY(7) OF VARCHAR2(10);
    EX7_CONTS VA_VCHAR;
    TYPE  VA_SLIP_SEQ IS VARRAY(7) OF VARCHAR2(14);
    SLIP_SEQ_LIST VA_SLIP_SEQ;
    
    V_SLIP_SEQ      CHAR(14);
    V_ATL_DATE      DATE;
    V_CARD_NO       VARCHAR(10);
    V_CARD_SEQ      NUMBER;
    V_RN                NUMBER; -- 경주순번
    V_RN_CNT          NUMBER; --동일경주순번별 구매 건수
    V_POS               NUMBER;
    V_STR_RACE_NO NUMBER;
    V_LATEST_ATL_DATE DATE;
    V_TOTAL_AMT     NUMBER(12);
    CNT                   NUMBER;
    V_END_TM          VARCHAR(6);
    V_EXISTS_CNT    NUMBER;
    V_FROM_SLIP_SEQ CHAR(14);
    V_TO_SLIP_SEQ CHAR(14);
    V_EVENT_SLIP_SEQ_LIST VARCHAR2(200);
    V_FROM_RACE_NO VARCHAR2(2);
    V_TO_RACE_NO VARCHAR2(2);
    
    CURSOR CUR_EVENT_7EX_DETL IS
    SELECT ROW_NUMBER() OVER(PARTITION BY CARD_NO, CARD_SEQ, RACE_NO ORDER BY SLIP_SEQ) AS RN
              ,A.*
    FROM TBRD_EVENT_7EX_DETL A
    WHERE EVENT_DT = P_CUR_DATE
    AND    EXISTS  (
            SELECT CARD_NO
            FROM (
                    SELECT CARD_NO,
                             NVL(SUM(CASE WHEN RACE_NO = '06' THEN 1 END),0) AS RACE_06_CNT,
                             NVL(SUM(CASE WHEN RACE_NO = '07' THEN 1 END),0) AS RACE_07_CNT,
                             NVL(SUM(CASE WHEN RACE_NO = '08' THEN 1 END),0) AS RACE_08_CNT,
                             NVL(SUM(CASE WHEN RACE_NO = '09' THEN 1 END),0) AS RACE_09_CNT,
                             NVL(SUM(CASE WHEN RACE_NO = '10' THEN 1 END),0) AS RACE_10_CNT,
                             NVL(SUM(CASE WHEN RACE_NO = '11' THEN 1 END),0) AS RACE_11_CNT
                    FROM TBRD_EVENT_7EX_DETL
                    WHERE EVENT_DT = P_CUR_DATE
                    AND     TO_CHAR(ATL_DATE, 'HH24MISS') <= V_END_TM
                    GROUP BY CARD_NO
                    ) B
            WHERE '|'||RACE_06_CNT||'|'||RACE_07_CNT||'|'||RACE_08_CNT||'|'||RACE_09_CNT||'|'||RACE_10_CNT||'|'||RACE_11_CNT||'|' NOT LIKE '%|0|%'
            AND   A.CARD_NO = B.CARD_NO
            )          
    AND  RACE_NO BETWEEN V_FROM_RACE_NO AND V_TO_RACE_NO       
    AND  TO_CHAR(ATL_DATE, 'HH24MISS') <= V_END_TM 
    ORDER BY CARD_NO, CARD_SEQ, RN, SLIP_SEQ; 
    
    OLD_ROW  CUR_EVENT_7EX_DETL%ROWTYPE;

BEGIN             

   SP_LOG('BEGIN');
   
   -- 시작경주번호
   V_STR_RACE_NO := 6;
   V_FROM_RACE_NO := '06';
   V_TO_RACE_NO     := '11';
   
   -- 1-1)5경주 발매마감시간 조회
    SELECT NVL(TO_CHAR(D_CCSB_DATE,'HH24MISS'), SUBSTR(C_START_TIME,1,2)||SUBSTR(C_START_TIME,4,2)) AS END_TM
    INTO    V_END_TM
    FROM   T_RACE_INFO@CYBETLINK
    WHERE C_RACE_DATE = P_CUR_DATE
    AND     C_MEET_NO = '001'
    AND     C_RACE_NO = V_FROM_RACE_NO;
   SP_LOG('1-1)SELECT  V_END_TM : '||V_END_TM);
   
    --1-2) 이전에 가져온 최종 연번을 조회
    SELECT NVL(MAX(MAX_SLIP_SEQ),'0')
    INTO    V_FROM_SLIP_SEQ
    FROM   TBRD_EVENT_7EX_DETL_MAX
    WHERE EVENT_DT = P_CUR_DATE
    AND     MIN_RACE_NO = TRIM(TO_CHAR(V_STR_RACE_NO,'00'));
   SP_LOG('1-2)SELECT V_FROM_SLIP_SEQ FROM MAX: '||V_FROM_SLIP_SEQ);
   
   -- 1-3) 현재 그린카드DB에 있는 최종 그린카드 연번을 조회한다.
    V_TO_SLIP_SEQ := '-';
    SELECT NVL(MAX(C_SLIP_SEQ),'-')
    INTO    V_TO_SLIP_SEQ
    FROM   EXA7@IBETLINK 
    WHERE TO_CHAR(D_ATL_DATE,  'YYYYMMDD') = P_CUR_DATE
    AND    TO_CHAR(D_ATL_DATE,  'HH24MISS') <= V_END_TM
    AND    C_RACE_NO BETWEEN V_FROM_RACE_NO AND V_TO_RACE_NO;
   SP_LOG('1-3)SELECT V_TO_SLIP_SEQ FROM IBET: '||V_TO_SLIP_SEQ);    
    
   -- 2-1)그린카드 DB에서 자료 가져오기
   SP_LOG('1-4)SELECT V_TO_SLIP_SEQ FROM IBET: '||P_CUR_DATE||','||V_END_TM||','||V_FROM_SLIP_SEQ||','||V_TO_SLIP_SEQ);  
   INSERT INTO TBRD_EVENT_7EX_DETL(
                   EVENT_DT, SLIP_SEQ, ATL_DATE, CARD_NO, CARD_SEQ, 
                   MAIN_CODE, MAIN_DESC, MEET_CD, RACE_NO, SLIP_INFO, 
                   STAKE, PAYOFF_AMT, RETURN_AMT, 
                   WINNING_AMT, SLIP_STATUS)
    SELECT TO_CHAR(D_ATL_DATE,'YYYYMMDD') AS EVENT_DT,
             C_SLIP_SEQ,
           D_ATL_DATE,
           C_CARD_NO,
           N_CARD_SEQ,
           C_MAIN_CODE,
           S_DESC,
           C_MEET_NO,
           C_RACE_NO,
           S_SLIP_INFO AS SLIP_INFO,
           N_STAKE,
           N_PAYOFF_AMT,
           N_RETURN_AMT,
           N_WINNING_AMT,
           C_SLIP_STATUS
    FROM EXA7@IBETLINK 
    WHERE TO_CHAR(D_ATL_DATE,  'YYYYMMDD') = P_CUR_DATE
    AND    TO_CHAR(D_ATL_DATE,  'HH24MISS') <= V_END_TM
    AND    C_RACE_NO BETWEEN V_FROM_RACE_NO AND V_TO_RACE_NO
    AND    C_SLIP_SEQ >  V_FROM_SLIP_SEQ
    AND    C_SLIP_SEQ <= V_TO_SLIP_SEQ;
                      
    -- 2-2) 가져온 최종 그린카드연번을 저장한다.
    SP_LOG('2-2)SAVE V_TO_SLIP_SEQ : '||V_TO_SLIP_SEQ);    
    IF V_TO_SLIP_SEQ != '-' THEN
        INSERT INTO TBRD_EVENT_7EX_DETL_MAX (
                        EVENT_DT, MIN_RACE_NO, MAX_SLIP_SEQ, LAST_IMPT_DT)
        VALUES(P_CUR_DATE,  TRIM(TO_CHAR(V_STR_RACE_NO,'00')), V_TO_SLIP_SEQ, SYSDATE);
    ELSE
        SP_LOG('2-3)가져올 자료가 없음');
    END IF;
                                           
   -- 3. 가져온 자료에서 7쌍승에 해당하는 사람을 이벤트 테이블에 넣는다.               
   -- 3-1. 당일 7쌍승 이벤트에 참여한 내역을 조회하여 이벤트 테이블에 넣는다.
   -- 동일 경주에 구매내역이 여러개 존재하는 경우 최초 구매내역만을 인정한다.
   V_CARD_NO := NULL;
   V_CARD_SEQ := 999;
   V_RN_CNT    := 0;
   
    EX7_CONTS := VA_VCHAR();
    EX7_CONTS.EXTEND(6);
    SLIP_SEQ_LIST := VA_SLIP_SEQ();
    SLIP_SEQ_LIST.EXTEND(6);
    FOR CNT IN 1..6 LOOP
        EX7_CONTS(CNT) := '-';
        SLIP_SEQ_LIST(CNT) := '';
    END LOOP;
   
    SP_LOG('3-0)LOOP START');
       FOR ROW IN CUR_EVENT_7EX_DETL LOOP
           SP_LOG('3-1-0)V_CARD_NO='||V_CARD_NO);
           SP_LOG('3-1-0)V_CARD_SEQ='||V_CARD_SEQ);
           SP_LOG('3-1-0)V_RN='||V_RN);
           SP_LOG('3-1-0)ROW.CARD_NO='||ROW.CARD_NO);
           SP_LOG('3-1-0)ROW.CARD_SEQ='||ROW.CARD_SEQ);
           SP_LOG('3-1-0)ROW.RN='||ROW.RN);
           
            IF V_CARD_NO <> ROW.CARD_NO OR V_CARD_SEQ <> ROW.CARD_SEQ OR V_RN <> ROW.RN THEN
                SP_LOG('3-2-1)카드가 변경 되거나 중북 응모'||OLD_ROW.CARD_NO ||' SAVED');
                
                -- 카드번호 변경되면 저장
                -- 동일카드번호인 경우 구매내역을 저장(두번째 경주인 경우 저장 안함)
                SP_LOG('3-2-2)V_RN_CNT= '||TO_CHAR(V_RN_CNT));
                IF V_RN_CNT = 6 THEN                
                    --3-2) 기존에 자료가 있는 경우에는 입력하지 않음
                    V_EXISTS_CNT := 0;
                    SELECT COUNT(*) AS CNT
                    INTO    V_EXISTS_CNT
                    FROM   TBRD_EVENT_7EX
                    WHERE  EVENT_DT = OLD_ROW.EVENT_DT
                    AND      CARD_NO  = OLD_ROW.CARD_NO
                    AND      CARD_SEQ = OLD_ROW.CARD_SEQ
                    AND      MIN_SLIP_SEQ = OLD_ROW.SLIP_SEQ;
                    SP_LOG('3-3)기존자료 존재건수:'||TO_CHAR(V_EXISTS_CNT));
                    
                    IF V_EXISTS_CNT = 0 THEN                    
                        SP_LOG('3-4)INSERT:'||OLD_ROW.EVENT_DT||','||OLD_ROW.SLIP_SEQ||','||OLD_ROW.CARD_NO);
                        SP_LOG('3-4-1)OLD_ROW.MAIN_CODE:'||OLD_ROW.MAIN_CODE);
                        -- 응모내역에 저장
                        INSERT INTO TBRD_EVENT_7EX
                           (EVENT_DT, MIN_SLIP_SEQ, EVENT_TM, 
                            COMM_NO, PLACE_NO, PLACE_NM, CARD_NO, CARD_SEQ, MEET_CD, 
                            MIN_RACE_NO, EX1, EX2, EX3, EX4, EX5, EX6, BUY_AMT, 
                            PRT_CNT, SLIP_STATUS, RMK, UPDT_ID, UPDT_DT)
                        VALUES (OLD_ROW.EVENT_DT, OLD_ROW.SLIP_SEQ, TO_CHAR(V_LATEST_ATL_DATE,'HH24MISS'),
                            FC_GET_GRN_PLC_COM(OLD_ROW.MAIN_CODE), OLD_ROW.MAIN_CODE, OLD_ROW.MAIN_DESC,  OLD_ROW.CARD_NO, OLD_ROW.CARD_SEQ, OLD_ROW.MEET_CD,
                            TRIM(TO_CHAR(V_STR_RACE_NO,'00')), EX7_CONTS(1), EX7_CONTS(2), EX7_CONTS(3), 
                            EX7_CONTS(4), EX7_CONTS(5), EX7_CONTS(6),  V_TOTAL_AMT,
                            0, '001', '','PROC',SYSDATE);
                            
                        SP_LOG('3-4-2)참여세부내역에 응모번호 저장');
                        SP_LOG('3-4-3)V_EVENT_SLIP_SEQ_LIST:'||V_EVENT_SLIP_SEQ_LIST);
                        -- 참여세부내역에도 응모번호를 저장
                        FOR CNT IN 1..6 LOOP
                            UPDATE TBRD_EVENT_7EX_DETL
                            SET      EVENT_SLIP_SEQ = OLD_ROW.SLIP_SEQ
                            WHERE  EVENT_DT = OLD_ROW.EVENT_DT
                            AND      CARD_NO  = OLD_ROW.CARD_NO
                            AND      CARD_SEQ = OLD_ROW.CARD_SEQ
                            AND      SLIP_SEQ  = SLIP_SEQ_LIST(CNT);                            
                        END LOOP;                 
                    END IF;
                        
                    SP_LOG('3-5)자료 입력:INSERT');
                END IF;
                SP_LOG('3-6)변수 초기화');
                
                OLD_ROW := ROW;                
                V_CARD_NO := ROW.CARD_NO;
                V_CARD_SEQ := ROW.CARD_SEQ;
                V_RN      := ROW.RN;
                V_TOTAL_AMT := 0;
                V_LATEST_ATL_DATE := NULL;
                V_RN_CNT  := 0;
                V_EVENT_SLIP_SEQ_LIST := '';
                
                FOR CNT IN 1..6 LOOP
                    EX7_CONTS(CNT) := '-';
                    SLIP_SEQ_LIST(CNT) := '';
                END LOOP;                 
                
                SP_LOG('3-7)카드변경 처리 완료');
            END IF;
            
            V_POS := TO_NUMBER(ROW.RACE_NO) - V_STR_RACE_NO + 1;     
            SP_LOG('3-8)SAVE BUFFER : V_POS= '||TO_CHAR(V_POS)||'RACE_NO='||ROW.RACE_NO);
              
            IF V_TOTAL_AMT = 0 THEN
                V_SLIP_SEQ := ROW.SLIP_SEQ;
            END IF;
            V_RN_CNT                := V_RN_CNT + 1;
            EX7_CONTS(V_POS)   := ROW.SLIP_INFO;
            SLIP_SEQ_LIST(V_POS) := ROW.SLIP_SEQ;
            V_TOTAL_AMT          := V_TOTAL_AMT + ROW.STAKE;
            V_LATEST_ATL_DATE := ROW.ATL_DATE;
                
            SP_LOG('3-9)PROCESSED 1 RECORD');

       END LOOP;                                             

       SP_LOG('3-10)======END LOOP====== ');
            
        -- 마지막 자료를 저장한다.
        SP_LOG('3-10-1)V_RN_CNT= '||TO_CHAR(V_RN_CNT));
        IF V_RN_CNT =  6 THEN
            -- 기존에 자료가 있는 경우에는 입력하지 않음
            V_EXISTS_CNT := 0;
            SELECT COUNT(*) AS CNT
            INTO    V_EXISTS_CNT
            FROM   TBRD_EVENT_7EX
            WHERE  EVENT_DT = OLD_ROW.EVENT_DT
            AND      CARD_NO  = OLD_ROW.CARD_NO
            AND      CARD_SEQ = OLD_ROW.CARD_SEQ
            AND      MIN_SLIP_SEQ = OLD_ROW.SLIP_SEQ;
            SP_LOG('3-11)기존자료 존재건수:'||TO_CHAR(V_EXISTS_CNT));
                    
            -- 응모내역에 존재하지 않은 경우
            IF V_EXISTS_CNT = 0 THEN
                    
                SP_LOG('3-12)INSERT:'||OLD_ROW.EVENT_DT||','||OLD_ROW.SLIP_SEQ);
                -- 응모내역에 저장
                INSERT INTO TBRD_EVENT_7EX
                   (EVENT_DT, MIN_SLIP_SEQ, EVENT_TM, 
                    COMM_NO, PLACE_NO, PLACE_NM, CARD_NO, CARD_SEQ, MEET_CD, 
                    MIN_RACE_NO, EX1, EX2, EX3, EX4, EX5, EX6, BUY_AMT, 
                    PRT_CNT, SLIP_STATUS, RMK, UPDT_ID, UPDT_DT)
                VALUES (OLD_ROW.EVENT_DT, OLD_ROW.SLIP_SEQ, TO_CHAR(V_LATEST_ATL_DATE,'HH24MISS'),
                    FC_GET_GRN_PLC_COM(OLD_ROW.MAIN_CODE), OLD_ROW.MAIN_CODE, OLD_ROW.MAIN_DESC,  OLD_ROW.CARD_NO, OLD_ROW.CARD_SEQ, OLD_ROW.MEET_CD,
                    TRIM(TO_CHAR(V_STR_RACE_NO,'00')), EX7_CONTS(1), EX7_CONTS(2), EX7_CONTS(3), 
                    EX7_CONTS(4), EX7_CONTS(5), EX7_CONTS(6), V_TOTAL_AMT,
                    0, '001', '', 'PROC', SYSDATE);
                    
                SP_LOG('3-13-1)참여세부내역에 응모번호 저장');
                SP_LOG('3-13-2)V_EVENT_SLIP_SEQ_LIST:'||V_EVENT_SLIP_SEQ_LIST);
                --참여세부내역에도 응모번호를 저장
                FOR CNT IN 1..6 LOOP
                    UPDATE TBRD_EVENT_7EX_DETL
                    SET      EVENT_SLIP_SEQ = OLD_ROW.SLIP_SEQ
                    WHERE  EVENT_DT = OLD_ROW.EVENT_DT
                    AND      CARD_NO  = OLD_ROW.CARD_NO
                    AND      CARD_SEQ = OLD_ROW.CARD_SEQ
                    AND      SLIP_SEQ  = SLIP_SEQ_LIST(CNT);                            
                END LOOP;                 
            END IF;
        END IF;
                
    COMMIT;    
    SP_LOG('=====COMMIT====== ');
    
   RETURN;
                
   EXCEPTION
     WHEN NO_DATA_FOUND THEN
              
           SP_LOG('E-1)NO DATA FOUND.');
     WHEN OTHERS THEN
             SP_LOG('E-2'||SQLERRM);
             
            ROLLBACK;            
            SP_LOG('=====ROLLBACK====== ');
            
            RETURN;
END SP_CRT_EVENT_7EX611;
/
