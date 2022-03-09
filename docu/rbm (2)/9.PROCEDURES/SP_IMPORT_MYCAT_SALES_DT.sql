CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_MYCAT_SALES_DT IS
/******************************************************************************
- ������,��      : jsshin 2012.03.10
- ���α׷���     : SP_IMPORT_MYCAT_SALES_DT
- ���α׷�Ÿ��   : procedure
- ���           : �������� ����� �ڷ���踦 �����´�.
- IN  �μ�       :

- ���μ���
    1. ���� ������ ������ڷ� ����
    2. ������ DB���� �����ڷḦ ��ȸ�Ͽ� TBES_SDL_DT ���̺� �Է�


- ���ν����� ���� �� 8�ÿ� �۵��ǵ��� �Ѵ�.(�����ٷ� ���)
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
    
   -- 1) ���� Ȯ���� ������ ������ ���� ����, �׸�ī�� �� �߸űݾ�, ���� �߸űݾ��� ��ȸ�Ѵ�.
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

    -- 2) ������ �ݿ��� �ڷᰡ �����ϴ� ���
    IF V_MYCAT_BON_AMT > 0 THEN
            -- ������ �ݿ��� ���� ȯ�� �� �߰� ��������� �����ϴ� ���
            SELECT SUM(NET_AMT) MYCAT_TOT_AMT
            INTO     V_MYCAT_TOT_AMT 
            FROM   VW_MYCAT_SALES
            WHERE RACE_DAY = V_CUR_DATE
            AND      MEET_CD = V_MEET_CD
            AND      RACE_NO = V_RACE_NO;            
            IF V_MYCAT_TOT_DT = V_MYCAT_TOT_AMT THEN
                    -- 2-1) �ݾ� ���̰� ���� ���
                    --DBMS_OUTPUT.PUT_LINE('V_MYCAT_BON_AMT IS '||V_MYCAT_BON_AMT);        
                    SP_BTC_LOG_SEQ('009','I','SP_IMPORT_MYCAT_SALES_DT','END','�̹� �ݿ��Ǿ� ����('||V_RACE_NO||'����)', V_LOG_SEQ, V_LOG_SERL_NO);
                    RETURN;
            ELSE 
                    --DBMS_OUTPUT.PUT_LINE('V_MYCAT_BON_AMT IS '||V_MYCAT_BON_AMT);        
                    SP_BTC_LOG_SEQ('009','I','SP_IMPORT_MYCAT_SALES_DT','ING','�߰� �ݿ� ����('||V_RACE_NO||'���� (DT:'||V_MYCAT_TOT_DT||', MYCAT:'||V_MYCAT_TOT_AMT||'))', V_LOG_SEQ, V_LOG_SERL_NO);
            END IF;
    ELSE

            -- 3) ������ �ڷᰡ ���� ��� �׸�ī�� �ݾ��� ��ȸ
             SELECT SUM(NET_AMT) MYCAT_TOT_AMT
             INTO     V_MYCAT_TOT_AMT 
              FROM   VW_MYCAT_SALES
              WHERE RACE_DAY = V_CUR_DATE
              AND      MEET_CD = V_MEET_CD
              AND      RACE_NO = V_RACE_NO;
                 --DBMS_OUTPUT.PUT_LINE('V_MYCAT_TOT_AMT IS '||V_MYCAT_TOT_AMT);
              
            -- 4) ó�� �Է½� �ݾ��� ��ġ���� �ʴ� ��� ���� 
                 --DBMS_OUTPUT.PUT_LINE('V_MYCAT_TOT_DT['||V_MYCAT_TOT_DT||'], V_MYCAT_TOT_AMT['||V_MYCAT_TOT_AMT||']');
            IF V_MYCAT_TOT_DT <> V_MYCAT_TOT_AMT THEN
                -- ���� �α� 
                 --DBMS_OUTPUT.PUT_LINE('V_MYCAT_TOT_DT IS NOT V_MYCAT_TOT_AMT'||TO_CHAR(V_MYCAT_TOT_DT)||':'||V_MYCAT_TOT_AMT);
                SP_BTC_LOG_SEQ('009','W','SP_IMPORT_MYCAT_SALES_DT','END',V_RACE_NO||'���� �ݾ� ����ġ(DT:'||V_MYCAT_TOT_DT||', MYCAT:'||V_MYCAT_TOT_AMT||')', V_LOG_SEQ, V_LOG_SERL_NO);
                RETURN;
            END IF ;     
    
    END IF;
    
    -- 5) ����  ������ ����(�ش� ���ָ� ����)
    --DBMS_OUTPUT.PUT_LINE('DELETE TBES_SDL_DT ');
    DELETE 
      FROM  TBES_SDL_DT
     WHERE MEET_CD = V_MEET_CD
         AND  STND_YEAR = V_STND_YEAR
         AND  TMS = V_TMS
         AND  DAY_ORD = V_DAY_ORD
         AND  RACE_NO = V_RACE_NO
         AND  COMM_NO = '06';    --�׸�ī��

    -- 7) ������ ����׿� �ڷ� �Է�
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
    SP_BTC_LOG_SEQ('009','I','SP_IMPORT_MYCAT_SALES_DT','END',V_RACE_NO||'���� ����ȭ �Ϸ�', V_LOG_SEQ, V_LOG_SERL_NO);
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
