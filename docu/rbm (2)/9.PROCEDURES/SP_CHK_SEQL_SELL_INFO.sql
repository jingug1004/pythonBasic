CREATE OR REPLACE PROCEDURE USRBM.SP_CHK_SEQL_SELL_INFO(
                                                     P_YEAR_TOTE   IN VARCHAR2
                                                   , P_DATE_TOTE   IN VARCHAR2
                                                   , P_STND_AMOUNT    IN NUMBER
                                                   , P_STND_TIME IN NUMBER
                                                   , P_STND_CNT IN NUMBER
                                                   , P_MEET_NO    IN NUMBER
                                                   , P_RACE_NO    IN VARCHAR2
                                                   , P_COMM_NO   IN VARCHAR2
                                                   , P_DIV_NO       IN VARCHAR2
                                                   , P_WIN_NO      IN VARCHAR2
                                                   , P_TIME_TOTE IN VARCHAR2
                                                   )
AS
/******************************************************************************
- 개발자,일      : 조성문 20161201
- 프로그램명     : SP_CHK_SEQL_SELL_INFO
- 프로그램타입   : procedure
- 기능           : DW에서 집중베팅현황(연발매)을 조회한다.
- IN  인수       : P_STND_AMOUNT (조회 기준 금액, 100000이면 100000만원 이상 연속발매 금액을 조회)
                     P_STND_TIME(연속 발매를 검색하는 시간 범위를 지정, 3이면 3초 이내 연속 발매된 금액을 지정 
                     P_STND_CNT(연속 발매에 해당하는 건 수 지정, 3이면  3건 연속 발매된  건수 지정
                     * 100000원, 3초, 3건이면 100000원씩 3초이내 3건이상 구매한 내역을 조회
- 프로세스
    1. 연발매 현황을 조회해서 테이블에 입력
******************************************************************************/
    V_ERR_CODE NUMBER;
    V_ERR_MESG VARCHAR(255);
    
    V_BET_SUMMARY VARCHAR2(1024);
    V_CNT VARCHAR2(255);
    V_TSN VARCHAR2(255);
    V_TIME_TOTE VARCHAR2(255);

    CURSOR CUR_WIN_INFO IS
        SELECT TIME_TOTE FROM VW_TF_SELL_01@DW01LINK
        WHERE 1=1
        AND YEAR_TOTE = P_YEAR_TOTE
        AND DATE_TOTE = P_DATE_TOTE
        AND ASSOC_NO = '1'
        AND AMOUNT = P_STND_AMOUNT
        AND RACE_NO = P_RACE_NO
        AND COMM_NO = P_COMM_NO
        AND DIV_NO = P_DIV_NO
        AND WIN_NO = P_WIN_NO
        AND TIME_TOTE = P_TIME_TOTE
        ORDER BY TIME_TOTE ASC;
        
    CUR_WIN_INFO_ROW  CUR_WIN_INFO%ROWTYPE;        
          
BEGIN
    DBMS_OUTPUT.ENABLE;
    OPEN CUR_WIN_INFO;
    LOOP
        FETCH CUR_WIN_INFO INTO CUR_WIN_INFO_ROW;
        EXIT WHEN CUR_WIN_INFO%NOTFOUND;
        --DBMS_OUTPUT.PUT_LINE('TIME_TOTE:' ||P_RACE_NO||'#'||P_TIME_TOTE);
        
        V_BET_SUMMARY := ''; --초기화
        SELECT BET_SUMMARY, TSN
        INTO V_BET_SUMMARY,  V_TSN
        FROM (   
            SELECT BET_SUMMARY, MIN(TSN) TSN
            FROM
            (
                SELECT
                    TIME_TOTE, TSN, BET1_SUMMARY||'+'||BET2_SUMMARY||'+'||BET3_SUMMARY BET_SUMMARY
                FROM VW_TF_SELL_01@DW01LINK A
                WHERE 1=1
                AND YEAR_TOTE =P_YEAR_TOTE
                AND DATE_TOTE = P_DATE_TOTE
                AND ASSOC_NO = '1'
                AND AMOUNT = P_STND_AMOUNT
                AND RACE_NO = P_RACE_NO
                AND COMM_NO =P_COMM_NO
                AND DIV_NO =P_DIV_NO
                AND WIN_NO = P_WIN_NO
                AND TO_DATE(TIME_TOTE,'HH24MISS') BETWEEN TO_DATE(P_TIME_TOTE,'HH24MISS') AND TO_DATE(P_TIME_TOTE,'HH24MISS') + P_STND_TIME/86400  --시간(단위:초)
                ORDER BY TIME_TOTE ASC
            ) GROUP BY BET_SUMMARY
            HAVING COUNT(BET_SUMMARY) >= P_STND_CNT
        ) WHERE ROWNUM = 1
        ;
        
        IF LENGTHB(V_BET_SUMMARY) > 5 THEN --총 구매건수 구하기
            SELECT
                COUNT(TSN) CNT, MIN(TIME_TOTE) TIME_TOTE
                INTO V_CNT, V_TIME_TOTE
            FROM VW_TF_SELL_01@DW01LINK
            WHERE 1=1
            AND YEAR_TOTE = P_YEAR_TOTE
            AND DATE_TOTE = P_DATE_TOTE
            AND RACE_NO = P_RACE_NO
            AND COMM_NO =P_COMM_NO
            AND DIV_NO = P_DIV_NO
            AND WIN_NO = P_WIN_NO
            AND BET1_SUMMARY||'+'||BET2_SUMMARY||'+'||BET3_SUMMARY = V_BET_SUMMARY
            GROUP BY YEAR_TOTE, DATE_TOTE, RACE_NO, COMM_NO, DIV_NO, WIN_NO, BET1_SUMMARY||'+'||BET2_SUMMARY||'+'||BET3_SUMMARY;
        --DBMS_OUTPUT.PUT_LINE('RESULT:' ||V_TIME_TOTE||'#'|| V_BET_SUMMARY||'#'||V_CNT||'#'||V_TSN);

             MERGE INTO TBRD_SEQL_SELL DEST
             USING (
                SELECT
                     P_YEAR_TOTE AS YEAR_TOTE 
                    ,P_DATE_TOTE AS DATE_TOTE
                    ,P_STND_AMOUNT  AS STND_AMOUNT 
                    ,P_STND_TIME AS STND_TIME
                    ,P_STND_CNT  AS STND_CNT
                    ,P_MEET_NO    AS MEET_NO
                    ,P_RACE_NO    AS  RACE_NO
                    ,P_COMM_NO   AS COMM_NO
                    ,P_DIV_NO       AS  DIV_NO
                    ,P_WIN_NO      AS  WIN_NO
                    ,V_TIME_TOTE AS TIME_TOTE
                    ,V_TSN AS TSN
                    ,V_BET_SUMMARY AS BET_SUMMARY
                    ,V_CNT AS SEQL_CNT
                FROM  DUAL
            ) SRC
            ON (
                              DEST.YEAR_TOTE = SRC.YEAR_TOTE
                       AND DEST.DATE_TOTE = SRC.DATE_TOTE
                       AND DEST.RACE_NO = SRC.RACE_NO
                       AND DEST.COMM_NO = SRC.COMM_NO
                       AND DEST.DIV_NO = SRC.DIV_NO
                       AND DEST.WIN_NO = SRC.WIN_NO
                       AND DEST.BET_SUMMARY = SRC.BET_SUMMARY
            )
            WHEN MATCHED THEN
                UPDATE SET
                    DEST.INST_DT = SYSDATE,
                    DEST.TIME_TOTE = SRC.TIME_TOTE,
                    DEST.SEQL_CNT = SRC.SEQL_CNT
            WHEN NOT MATCHED THEN
                INSERT (
                    YEAR_TOTE
                   ,DATE_TOTE
                   ,STND_AMOUNT
                   ,STND_TIME
                   ,STND_CNT
                   ,MEET_NO
                   ,RACE_NO
                   ,COMM_NO
                   ,DIV_NO
                   ,WIN_NO
                   ,TIME_TOTE
                   ,TSN
                   ,BET_SUMMARY
                   ,INST_ID
                   ,INST_DT
                   ,SEQL_CNT
               )
               VALUES (
                    SRC.YEAR_TOTE
                   ,SRC.DATE_TOTE
                   ,SRC.STND_AMOUNT
                   ,SRC.STND_TIME
                   ,SRC.STND_CNT
                   ,SRC.MEET_NO
                   ,SRC.RACE_NO
                   ,SRC.COMM_NO
                   ,SRC.DIV_NO
                   ,SRC.WIN_NO
                   ,SRC.TIME_TOTE
                   ,SRC.TSN
                   ,SRC.BET_SUMMARY
                   ,'SYSTEM'
                   ,SYSDATE
                   ,SRC.SEQL_CNT
               ); 
        END IF;               
    END LOOP;
    CLOSE CUR_WIN_INFO;   
    COMMIT;
    RETURN;
    
    EXCEPTION
        WHEN NO_DATA_FOUND THEN NULL;
        WHEN OTHERS THEN
            V_ERR_CODE := SQLCODE();
            V_ERR_MESG := SQLERRM;
            ROLLBACK;
            SP_BTC_LOG('015','E','SP_CHK_SEQL_SELL_INFO','ERROR CODE:'||V_ERR_CODE || ' ERROR LOG:'||V_ERR_MESG,'');
            RETURN;

END;
/
