CREATE OR REPLACE PROCEDURE USRBM.SP_CHK_SMS_SVR IS
V_LOG_SEQ NUMBER(15);
V_LOG_SERL_NO NUMBER(3);
V_MESG VARCHAR2(200);
     
BEGIN
   
    SELECT SQ_TBRK_BTC_LOG.NEXTVAL
    INTO V_LOG_SEQ
    FROM DUAL;
    
    V_LOG_SERL_NO := 0;    
    
    -- 전날 SMS발송 상태를 체크함 (성공률이 20% 미만인 경우 에러 로그에 기록)
    SELECT 'SMS중계서버 상태확인 요망('||INDATE|| '의 SMS 발송성공율:'||TO_CHAR(ROUND(SUC_CNT / TOT_CNT * 100))||'%)' AS MESG 
    INTO    V_MESG
    FROM (
                SELECT INDATE
                           ,SUM(CASE WHEN RESULT = '2' THEN 1 ELSE 0 END) SUC_CNT
                           ,COUNT(*) TOT_CNT
                FROM SMS.SMSDATA
                WHERE INDATE = TO_CHAR(SYSDATE -1, 'YYYYMMDD')
                GROUP BY INDATE
              )
    WHERE (SUC_CNT / TOT_CNT * 10) < 2  ;
           
    IF LENGTH(V_MESG) > 0 THEN
        SP_ALARM_SEND_SMS( '011', V_MESG, 'admin', 'RBS7020', 'SP_CHK_SMS_SVR');
        SP_BTC_LOG_SEQ('011','E','SP_CHK_SMS_SVR',V_MESG, '', V_LOG_SEQ, V_LOG_SERL_NO);
    END IF ;         
    COMMIT;
    
    RETURN;
    
    
   EXCEPTION
     WHEN NO_DATA_FOUND THEN
         NULL;
     WHEN OTHERS THEN
       -- Consider logging the error and then re-raise
       SP_BTC_LOG_SEQ('011','E','SP_CHK_SMS_SVR',SQLERRM, '', V_LOG_SEQ, V_LOG_SERL_NO);
       RAISE;
END SP_CHK_SMS_SVR;
/
