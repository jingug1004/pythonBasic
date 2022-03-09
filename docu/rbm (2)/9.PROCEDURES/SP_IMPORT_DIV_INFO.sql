CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_DIV_INFO IS
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
    SP_BTC_LOG_SEQ('009','I','SP_IMPORT_DIV_INFO','START','', V_LOG_SEQ, V_LOG_SERL_NO);
    --DBMS_OUTPUT.PUT_LINE('1:'||TO_CHAR(V_LOG_SEQ)||','||TO_CHAR(V_LOG_SERL_NO));
        
    --  기존 자료 삭제
    DELETE FROM TBRK_SPEC_CD
    WHERE GRP_CD = '145';
    
    -- DW에서 자료 조회하여 입력
        INSERT INTO TBRK_SPEC_CD
                           (GRP_CD, CD, CD_NM, CD_TERM1, CD_TERM2, CD_TERM3, CD_TERM4, CD_TERM5, ORD_NO,  DEL_YN, INST_ID, INST_DT)                   
        SELECT '145' AS GRP_CD,
                   TRIM(TO_CHAR(SELL_CD,'00'))||TRIM(TO_CHAR(COMM_NO,'00'))||TRIM(TO_CHAR(DIV_NO,'00')) AS CD,
                   DIV_NAME AS CD_NM,
                   TRIM(TO_CHAR(SELL_CD,'00')) AS CD_TERM1,
                   TRIM(TO_CHAR(COMM_NO,'00')) AS CD_TERM2,
                   TRIM(TO_CHAR(DIV_NO,'00')) AS CD_TERM3,
                   FROM_YEAR_DATE AS CD_TERM4,
                   TO_YEAR_DATE AS CD_TERM5,
                   ROWNUM AS ORD_NO,
                   'N',
                   'admin',
                   SYSDATE
        FROM BASETOTE.CONF_DIV_INFO@DW01LINK A
        WHERE TO_CHAR(SYSDATE, 'yyyymmdd') BETWEEN FROM_YEAR_DATE AND TO_YEAR_DATE
        ORDER BY A.SELL_CD, A.COMM_NO, A.DIV_NO;
           
    COMMIT;
    
    --DBMS_OUTPUT.PUT_LINE('2:'||TO_CHAR(V_LOG_SEQ)||','||TO_CHAR(V_LOG_SERL_NO));        
    SP_BTC_LOG_SEQ('009','I','SP_IMPORT_DIV_INFO','END','', V_LOG_SEQ, V_LOG_SERL_NO);
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
        SP_BTC_LOG_SEQ('009','E','SP_IMPORT_DIV_INFO','ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'', V_LOG_SEQ, V_LOG_SERL_NO);
        
       RAISE;
END SP_IMPORT_DIV_INFO;
/
