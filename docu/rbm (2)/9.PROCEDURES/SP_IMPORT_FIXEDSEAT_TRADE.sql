CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_FIXEDSEAT_TRADE

IS
/******************************************************************************
- ������,��      : jsshin 2012.03.10
- ���α׷���     : SP_IMPORT_FIXEDSEAT_TRADE(�����¼��� �����ο� ��������)
- ���α׷�Ÿ��   : procedure
- ���           : ���� �� ������  �����¡���ý��� ����� DB���� �ڷḦ �����´�.(���� 1ȸ ����)
- IN  �μ�       :

- ���μ���
    1. ����DB�� �����ϴ� ������ ���������� ��ȸ�Ѵ�.
    2. �������� �������� ������ �ڷḦ ���� �����´�.
    3. ����DB���� ������������ �ڷḦ �����Ѵ�.

- ���ν����� ���� �� 11:30�ÿ� �۵��ǵ��� �Ѵ�.(�����ٷ� ���)
******************************************************************************/
    v_job_date DATE;
    v_err_code NUMBER;
    v_err_mesg VARCHAR(255);
    V_LOG_SEQ NUMBER(15);
    V_LOG_SERL_NO NUMBER(3);
    V_MAX_SEQ_NO_FS NUMBER(20);
    V_PROCESS VARCHAR(255);
            
    -- 03. ���������¼��� ���� CURSOR
    CURSOR CUR_MAX_SEQ_FS_KM IS
        SELECT LOC, MAX(SEQ) AS MAX_SEQ
        FROM   TBRC_FS_STATUS_KM
        WHERE  JOB_TYPE = 'F'
        GROUP BY LOC;
           
     -- 04. Ŀ������ ���� ���� ����    
     MAX_SEQ_ROW_FS_KM  CUR_MAX_SEQ_FS_KM%ROWTYPE;
     
BEGIN
    DBMS_OUTPUT.ENABLE;

    SELECT SQ_TBRK_BTC_LOG.NEXTVAL
      INTO V_LOG_SEQ
    FROM DUAL;
    
    V_LOG_SERL_NO := 0;    
    SP_BTC_LOG_SEQ('012','I','SP_IMPORT_FIXEDSEAT_TRADE','START','', V_LOG_SEQ, V_LOG_SERL_NO);

    V_PROCESS := '23';
     
    --30) �����¼����� �ڷ� �������� -------------------------------------
    --31) �����¼����� ���� ���� ���� �����´�.
    SELECT MAX(SEQ_NO) AS MAX_SEQ_NO
    INTO    V_MAX_SEQ_NO_FS
    FROM    TBIF_FS_STATUS@TMONEY_LINK;
    V_PROCESS := '31';
    
    --32) ����DB�� �����¼����� �ŷ������� �DB�� �����Ѵ�. 
    INSERT INTO TBRC_FS_TRADE
                        (SEQ_NO, COMM_NO, ISSUE_DT, ISSUE_TM, SEAT_NO, 
                         AMOUNT, AMOUNT1, UNI_CODE, UNI_SUB, IDATE)
    SELECT   SEQ_NO, COMM_NO, ISSUE_DT, ISSUE_TM, SEAT_NO, 
                  AMOUNT, AMOUNT1, UNI_CODE, UNI_SUB, SYSDATE
    FROM  TBIF_FS_ISSUE@TMONEY_LINK
    WHERE SEQ_NO <= V_MAX_SEQ_NO_FS;                
    V_PROCESS := '32';
        
    --33) Ƽ�ӴϿ���DB�� �����¼���  ������ �����Ѵ�.
     DELETE FROM TBIF_FS_ISSUE@TMONEY_LINK
            WHERE SEQ_NO <= V_MAX_SEQ_NO_FS;
     
     -- �ڷ� ���� �ǿ� ���ؼ��� �켱 commit ó���Ѵ�. 
     --COMMIT; --20150226
     V_PROCESS := '33';
     
    --40) ���� �����¼����� ���� ���� ���� �����´�.  -----------------------------------------
      -- 41) ����DB���� ������ MAX������ �����´�. 
    OPEN CUR_MAX_SEQ_FS_KM;
        LOOP
            --42) �Ǻ��� ������ ���� ��ȸ
            FETCH CUR_MAX_SEQ_FS_KM INTO MAX_SEQ_ROW_FS_KM;
            EXIT WHEN CUR_MAX_SEQ_FS_KM%NOTFOUND;
            V_PROCESS := '41';
            
            --42) ����DB�� �����¼����� �ŷ������� �DB�� �����Ѵ�. 
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
            
            --43) Ƽ�ӴϿ���DB�� �����¼���  ������ �����Ѵ�.
            DELETE FROM TBRC_FS_ISSUE_KM
                WHERE  LOC =  MAX_SEQ_ROW_FS_KM.LOC
                AND    JOB_TYPE = 'F'
                AND    SEQ  <= MAX_SEQ_ROW_FS_KM.MAX_SEQ;
        END LOOP;
        CLOSE CUR_MAX_SEQ_FS_KM;
        
         -- �ڷ� ���� �ǿ� ���ؼ��� �켱 commit ó���Ѵ�. 
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
