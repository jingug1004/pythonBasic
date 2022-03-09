CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_MYCAT_DIV_INFO IS
    V_LOG_SEQ NUMBER(15);
    V_LOG_SERL_NO NUMBER(3);
    v_err_code number;
    v_err_mesg varchar(255);
     
BEGIN
    
    DBMS_OUTPUT.ENABLE;
    
    SELECT SQ_TBRK_BTC_LOG.NEXTVAL
    INTO V_LOG_SEQ
    FROM DUAL;
    
    V_LOG_SERL_NO := 0;    
    SP_BTC_LOG_SEQ('009','I','SP_IMPORT_MYCAT_DIV_INFO','START','', V_LOG_SEQ, V_LOG_SERL_NO);
    --DBMS_OUTPUT.PUT_LINE('1:'||TO_CHAR(V_LOG_SEQ)||','||TO_CHAR(V_LOG_SERL_NO));
        
    
    -- 최근 1개월 자료만 가져옴(최초시 전부 가져왔음)
    MERGE INTO TBRK_SPEC_CD DEST
         USING (
             /*  old  20141010
             SELECT
                    SELL_CD|| DECODE (COMM_NO, '01', '00', DIV_NO) ||DIV_NO AS CD,
                    SELL_CD AS CD_TERM1, 
                    DECODE (COMM_NO, '01', '00', DIV_NO) AS CD_TERM2, 
                    DIV_NO  AS CD_TERM3, 
                    MIN(PLACE)   AS CD_NM,
                    SYSDATE AS CURR_DT
               FROM V_MYCAT_DIVSTAT@CYBETLINK
               WHERE RACE_DAY >= TO_CHAR(SYSDATE - 30,'YYYYMMDD')
               GROUP BY SELL_CD,  DECODE (COMM_NO, '01', '00', DIV_NO) , DIV_NO
              */
               SELECT
                    SELL_CD|| DECODE (COMM_NO, '01', '00', DIV_NO) ||C_SMALL_CODE AS CD,
                    SELL_CD AS CD_TERM1, 
                    DECODE (COMM_NO, '01', '00', DIV_NO) AS CD_TERM2, 
                    C_SMALL_CODE  AS CD_TERM3, 
                    NEW_COMM_NO AS cd_term4,
                    MIN(PLACE)   AS CD_NM,
                    SYSDATE AS CURR_DT
               FROM icra.V_MYCAT_DIVSTAT@CYBETLINK
               WHERE RACE_DAY >= TO_CHAR(SYSDATE - 30,'YYYYMMDD')
               GROUP BY SELL_CD,  DECODE (COMM_NO, '01', '00', DIV_NO) , div_no, C_SMALL_CODE, NEW_COMM_NO
         ) SRC
         ON (
                 DEST.GRP_CD   = '128'
             AND DEST.CD       = SRC.CD
         )
         WHEN MATCHED THEN
             UPDATE SET
                  DEST.CD_NM    = SRC.CD_NM
                 ,DEST.UPDT_ID  = 'BATCH'
                 ,DEST.UPDT_DT  = SRC.CURR_DT
         WHEN NOT MATCHED THEN
             INSERT (
                 DEST.GRP_CD
                ,DEST.CD
                ,DEST.CD_NM
                ,DEST.CD_TERM1
                ,DEST.CD_TERM2
                ,DEST.CD_TERM3
                ,DEST.CD_TERM4
                ,DEST.DEL_YN
                ,DEST.INST_ID
                ,DEST.INST_DT
             )
             VALUES (
                 '128',
                 SRC.CD,
                 SRC.CD_NM,
                 SRC.CD_TERM1,
                 SRC.CD_TERM2,
                 SRC.CD_TERM3,
                 SRC.CD_TERM4,
                 'N',
                 'BATCH',
                 SRC.CURR_DT
             );
           
    COMMIT;
    
    --DBMS_OUTPUT.PUT_LINE('2:'||TO_CHAR(V_LOG_SEQ)||','||TO_CHAR(V_LOG_SERL_NO));        
    SP_BTC_LOG_SEQ('009','I','SP_IMPORT_MYCAT_DIV_INFO','END','', V_LOG_SEQ, V_LOG_SERL_NO);
    RETURN;
    
   EXCEPTION
     WHEN NO_DATA_FOUND THEN
         NULL;
     WHEN OTHERS THEN
       -- Consider logging the error and then re-raise
       
        v_err_code := SQLCODE();
        v_err_mesg := SQLERRM;
        ROLLBACK;
        --DBMS_OUTPUT.PUT_LINE('3:'||TO_CHAR(V_LOG_SEQ)||','||TO_CHAR(V_LOG_SERL_NO));
        SP_BTC_LOG_SEQ('009','E','SP_IMPORT_MYCAT_DIV_INFO','ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'', V_LOG_SEQ, V_LOG_SERL_NO);
        
       RAISE;
END SP_IMPORT_MYCAT_DIV_INFO;
/
