CREATE OR REPLACE PROCEDURE USRBM.SP_CHK_SEQL_SELL(
                                                     P_YEAR_TOTE   IN VARCHAR2
                                                   , P_DATE_TOTE   IN VARCHAR2
                                                   , P_STND_AMOUNT    IN NUMBER
                                                   , P_STND_TIME IN NUMBER
                                                   , P_STND_CNT IN NUMBER
                                                   )
IS
/******************************************************************************
- 개발자,일      : 조성문  20161201
- 프로그램명    : SP_CHK_SEQ_SELL_CONF_FROM_DW
- 프로그램타입 : procedure
- 기능            : DW에서 집중베팅현황(연발매) 조회를 위한 창구정보를 가져온다.
- IN  인수       : P_STND_AMOUNT (조회 기준 금액, 100000이면 100000만원 이상 연속발매 금액을 조회)
                     P_STND_TIME(연속 발매를 검색하는 시간 범위를 지정, 3이면 3초 이내 연속 발매된 금액을 지정 
                     P_STND_CN
                     T(연속 발매에 해당하는 건 수 지정, 3이면  3건 연속 발매된  건수 지정
                     100000원, 3초, 3건이면 100000원씩 3초이내 3건이상 구매한 내역을 조회
- 프로세스
    1. 연발매 현황을 조회해서 테이블에 입력
******************************************************************************/
    V_ERR_CODE NUMBER;
    V_ERR_MESG VARCHAR(255);


    CURSOR CUR_WIN_INFO IS
        SELECT MEET_NO, RACE_NO, COMM_NO, DIV_NO, WIN_NO, TIME_TOTE FROM VW_TF_SELL_01@DW01LINK
        WHERE 1=1
        AND YEAR_TOTE = P_YEAR_TOTE
        AND DATE_TOTE = P_DATE_TOTE
        AND ASSOC_NO = '1'
        AND AMOUNT = P_STND_AMOUNT
        --AND RACE_NO = '12'    --FOR TEST
        --AND COMM_NO = '14'  --FOR TEST
        --AND DIV_NO ='5' --FOR TEST
        --AND WIN_NO = '007641' --FOR TEST
        GROUP BY  MEET_NO,  RACE_NO, COMM_NO, DIV_NO, WIN_NO, TIME_TOTE
        ORDER BY MEET_NO,  COMM_NO, DIV_NO, WIN_NO, RACE_NO,TIME_TOTE;   

    CUR_WIN_INFO_ROW  CUR_WIN_INFO%ROWTYPE;        
          
BEGIN
    DBMS_OUTPUT.ENABLE;
    OPEN CUR_WIN_INFO;
    LOOP
        FETCH CUR_WIN_INFO INTO CUR_WIN_INFO_ROW;
        EXIT WHEN CUR_WIN_INFO%NOTFOUND;
        --DBMS_OUTPUT.PUT_LINE('WIN_NO:' ||CUR_WIN_INFO_ROW.RACE_NO||CUR_WIN_INFO_ROW.TIME_TOTE);
            SP_CHK_SEQL_SELL_INFO(
                 P_YEAR_TOTE
                ,P_DATE_TOTE
                ,P_STND_AMOUNT
                ,P_STND_TIME
                ,P_STND_CNT
                ,CUR_WIN_INFO_ROW.MEET_NO
                ,CUR_WIN_INFO_ROW.RACE_NO
                ,CUR_WIN_INFO_ROW.COMM_NO
                ,CUR_WIN_INFO_ROW.DIV_NO
                ,CUR_WIN_INFO_ROW.WIN_NO
                ,CUR_WIN_INFO_ROW.TIME_TOTE
            );
            
    END LOOP;
    CLOSE CUR_WIN_INFO;   
    COMMIT;
    RETURN;
    
    EXCEPTION
        WHEN OTHERS THEN
            V_ERR_CODE := SQLCODE();
            V_ERR_MESG := SQLERRM;
            ROLLBACK;
            SP_BTC_LOG('011','E','SP_ALARM_SEND_SMS_HP_NO','ERROR CODE:'||V_ERR_CODE || ' ERROR LOG:'||V_ERR_MESG,'');
            RETURN;

END;
/
