CREATE OR REPLACE PROCEDURE USRBM.SP_TEMP_JOB( P_CUR_DATE IN VARCHAR2 )

IS
/******************************************************************************
--임시로 사용하는 프로시저
******************************************************************************/
    v_job_date DATE;
    v_err_code NUMBER;
    v_err_mesg VARCHAR(255);
    V_LOG_SEQ NUMBER(15);
    V_LOG_SERL_NO NUMBER(3);

BEGIN
    DBMS_OUTPUT.ENABLE;

       V_LOG_SERL_NO := 0;    
    
    -- 1. 기존  데이터 삭제
    DELETE
      FROM  TBES_MYCAT_SALES_DET
     WHERE  RACE_DAY   = P_CUR_DATE;

    -- 3. 마이켓 입출력 자료 입력(앱,단말기 구분자 추가)
    INSERT INTO TBES_MYCAT_SALES_DET
          (RACE_DAY, MEET_CD, STND_YEAR, TMS, DAY_ORD, RACE_NO, SELL_CD, COMM_NO, DIV_NO, BRNC_CD, PLACE, TOTAL_AMT, REFUND, NET_AMT, AMOUNT1, AMOUNT2, AMOUNT3, AMOUNT4, AMOUNT5, AMOUNT6, AMOUNT7, AMOUNT8, COUNT1, COUNT2, COUNT3, COUNT4, COUNT5, COUNT6, COUNT7, COUNT8, CHANNEL_CD, INST_DT)
     SELECT RACE_DAY,
             MEET_CD,
             STND_YEAR,
             TMS,
             DAY_ORD,
             RACE_NO,
             SELL_CD,                 --SELL_CD:01로 통일 "TOTE SYSTEM" 전송 데이터 기준
             COMM_NO,                          --본장:01, 지점:07 ==> 모두 '06'으로 통일
             DIV_NO,
             BRNC_CD,
             PLACE,
             SUM (TOTAL_AMT) TOTAL_AMT,
             SUM (REFUND) REFUND,
             SUM (NET_AMT) NET_AMT,
             SUM (AMOUNT1) / 100 AMOUNT1,
             SUM (AMOUNT2) / 100 AMOUNT2,
             SUM (AMOUNT3) / 100 AMOUNT3,
             SUM (AMOUNT4) / 100 AMOUNT4,
             SUM (AMOUNT5) / 100 AMOUNT5,
             SUM (AMOUNT6) / 100 AMOUNT6,
             SUM (AMOUNT7) / 100 AMOUNT7,
             SUM (AMOUNT8) / 100 AMOUNT8,
             SUM (COUNT1) COUNT1,
             SUM (COUNT2) COUNT2,
             SUM (COUNT3) COUNT3,
             SUM (COUNT4) COUNT4,
             SUM (COUNT5) COUNT5,
             SUM (COUNT6) COUNT6,
             SUM (COUNT7) COUNT7,
             SUM (COUNT8) COUNT8,
             CHANNEL_CD,
             SYSDATE
        FROM (        
        SELECT RACE_DAY,
                     MEET_CD,
                     STND_YEAR,
                     TMS,
                     DAY_ORD,
                     RACE_NO,
                     '01' SELL_CD,              --SELL_CD:01로 통일 "TOTE SYSTEM" 전송 데이터 기준
                     '06' COMM_NO,              --본장:01, 지점:07 ==> 모두 '06'으로 통일
                     DECODE (COMM_NO, '01', '00', DIV_NO) DIV_NO,  
                     DECODE(COMM_NO, '01','00',DIV_NO) AS BRNC_CD,                                          
                     --DIV_NO AS PLACE,         -- 구)소스
                     c_small_code AS PLACE,     -- 지점내 정확한 투표소 확인 필요.. 2014.10.1 김일준
                     TOTAL_AMT,
                     REFUND,
                     NET_AMT,
                     AMOUNT1,
                     AMOUNT2,
                     AMOUNT3,
                     AMOUNT4,
                     AMOUNT5,
                     AMOUNT6,
                     AMOUNT7,
                     AMOUNT8,
                     COUNT1,
                     COUNT2,
                     COUNT3,
                     COUNT4,
                     COUNT5,
                     COUNT6,
                     COUNT7,
                     COUNT8,
                     CHANNEL_CD
                FROM V_MYCAT_SALES_DET@CYBETLINK 
                WHERE RACE_DAY = P_CUR_DATE               
                )
    GROUP BY RACE_DAY,MEET_CD,STND_YEAR,TMS,DAY_ORD,RACE_NO,SELL_CD,COMM_NO, DIV_NO, BRNC_CD, PLACE,CHANNEL_CD;
    COMMIT;
    RETURN;


    EXCEPTION
        WHEN OTHERS THEN
            v_err_code := SQLCODE();
            v_err_mesg := SQLERRM;
            ROLLBACK;
            RETURN;

END;
/
