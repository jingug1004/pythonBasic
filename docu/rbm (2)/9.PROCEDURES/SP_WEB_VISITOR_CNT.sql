CREATE OR REPLACE PROCEDURE USRBM.sp_web_visitor_cnt IS
V_LOG_SEQ NUMBER(15);
V_LOG_SERL_NO NUMBER(3);
     
BEGIN
   
    SELECT SQ_TBRK_BTC_LOG.NEXTVAL
    INTO V_LOG_SEQ
    FROM DUAL;
    
    V_LOG_SERL_NO := 0;    
    SP_BTC_LOG_SEQ('010','I','SP_WEB_VISITOR_CNT','START','', V_LOG_SEQ, V_LOG_SERL_NO);
    
    
    -- 무조건 당일에만 실행해야 함
    MERGE INTO TBRK_WEB_STAT DEST
        USING (
            SELECT TO_CHAR(SYSDATE, 'YYYYMMDD') AS CURR_DAY, 
                   COUNT(*) AS MEMBER_CNT, 
                   SYSDATE  AS CURR_DT
            FROM   KRACEWEB.TBID_MEMBER
            WHERE  DELETE_YN = 'N'
              AND  AGREE_YN = 'Y'
        ) SRC
        ON (
                   DEST.RACE_DAY = SRC.CURR_DAY
        )
        WHEN MATCHED THEN
            UPDATE SET
                DEST.MEMBER_CNT = SRC.MEMBER_CNT
               ,DEST.UPDT_DT    = SRC.CURR_DT
        WHEN NOT MATCHED THEN
            INSERT (
                RACE_DAY
               ,MEMBER_CNT
               ,INST_DT
           )
           VALUES (
                SRC.CURR_DAY
               ,SRC.MEMBER_CNT
               ,SRC.CURR_DT
           );
           
    COMMIT;
    
    SP_BTC_LOG_SEQ('010','I','SP_WEB_VISITOR_CNT','END','', V_LOG_SEQ, V_LOG_SERL_NO);
    RETURN;
    
    
   EXCEPTION
     WHEN NO_DATA_FOUND THEN
         NULL;
     WHEN OTHERS THEN
       -- Consider logging the error and then re-raise
       SP_BTC_LOG_SEQ('010','E','SP_WEB_VISITOR_CNT',SQLERRM, '', V_LOG_SEQ, V_LOG_SERL_NO);
       RAISE;
END sp_web_visitor_cnt;
/
