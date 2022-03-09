CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_MYCAT_SALES_STAT( P_CUR_DATE IN VARCHAR2 )

IS
/******************************************************************************
- ������,��      : jsshin 2012.03.10
- ���α׷���     : SP_IMPORT_MYCAT_SALES
- ���α׷�Ÿ��   : procedure
- ���           : �������� ����� �ڷ���踦 �����´�.
- IN  �μ�       :

- ���μ���
    1. ���� ������ ������ڷ� ����
    2. ������ DB���� �����ڷḦ ��ȸ�Ͽ� TBRS_MYCAT_DIVSTAT ���̺� �Է�


- ���ν����� ���� �� 8�ÿ� �۵��ǵ��� �Ѵ�.(�����ٷ� ���)
******************************************************************************/
    v_job_date date;
    v_err_code number;
    v_err_mesg varchar(255);
    V_LOG_SEQ NUMBER(15);
    V_LOG_SERL_NO NUMBER(3);

BEGIN
    DBMS_OUTPUT.ENABLE;

    SELECT SQ_TBRK_BTC_LOG.NEXTVAL
      INTO V_LOG_SEQ
    FROM DUAL;
    
    V_LOG_SERL_NO := 0;    
    SP_BTC_LOG_SEQ('009','I','SP_IMPORT_MYCAT_SALES_STAT','START','ARG:'||P_CUR_DATE, V_LOG_SEQ, V_LOG_SERL_NO);

    
    --  �׸�ī�� ī�� ���� ���� �ڷ� ��������
       -- 1) ���� �ڷ� ����
       DELETE FROM TBRS_MYCAT_SALES_STAT 
       WHERE    RACE_DAY = P_CUR_DATE;
       
        -- 2) ���� �ڷ� iNSERT
        INSERT INTO TBRS_MYCAT_SALES_STAT
                           (RACE_DAY, MEET_CD, STND_YEAR, TMS, DAY_ORD, COMM_NO, NEW_CARD_CNT, NET_AMT, NCARD_DAY, BUY_CNT)        
        SELECT B.RACE_DAY
                   ,B.MEET_CD
                   ,B.STND_YEAR
                   ,B.TMS
                   ,B.DAY_ORD
                   ,B.COMM_NO
                   ,NVL(A.NEW_CARD_COUNT, 0) AS NEW_CARD_CNT
                   ,B.NET_AMT
                   ,B.NCARD_DAY
                   ,B.BUY_CNT
        FROM (
        SELECT RACE_DAY
                   ,MEET_CD
                   ,STND_YEAR
                   ,TMS
                   ,DAY_ORD
                   ,COMM_NO
                   ,SUM(NEW_CARD_COUNT)  AS NEW_CARD_COUNT
        FROM V_MYCAT_CARD@CYBETLINK
        WHERE RACE_DAY = P_CUR_DATE
        GROUP BY RACE_DAY
                   ,MEET_CD
                   ,STND_YEAR
                   ,TMS
                   ,DAY_ORD
                   ,COMM_NO
        )A, (
        SELECT RACE_DAY
                   ,MEET_CD
                   ,STND_YEAR
                   ,TMS
                   ,DAY_ORD
                   ,COMM_NO
                   ,SUM(NET_AMT) AS NET_AMT
                   ,SUM(NCARD_DAY) AS NCARD_DAY
                   ,SUM(BUY_CNT) AS BUY_CNT
        FROM V_MYCAT_SALES2@CYBETLINK
        WHERE RACE_DAY = P_CUR_DATE
        GROUP BY RACE_DAY
                   ,MEET_CD
                   ,STND_YEAR
                   ,TMS
                   ,DAY_ORD
                   ,COMM_NO
                  ) B
        WHERE A.RACE_DAY(+)  = B.RACE_DAY 
        AND     A.MEET_CD(+) = B.MEET_CD   
        AND     A.COMM_NO(+)  = B.COMM_NO;       
        
      COMMIT;        

    --  �׸�ī��ܸ��⺰ ����ڷ� ��������
    -- 1) ���� �ڷ� ����
       DELETE FROM TBRS_MYCAT_TELMP 
       WHERE    RACE_DAY = P_CUR_DATE;
       
       
        -- 2) ���� �ڷ� iNSERT
        INSERT INTO TBRS_MYCAT_TELMP
                           (RACE_DAY, MEET_CD, CHNL_CD, COMM_NO, LOC_NM, DEV_NM, DEV_SALES_AMT, DEV_SALES_CNT)        
        SELECT  RDATE
                    ,MEET
                    ,NVL(CHN,'001')
                    ,COMM
                    ,LOC_NM
                    ,NVL(DEV_NM,'-') 
                    ,AMT
                    ,CNT
        FROM V_GREEN_WND_SELL@CYBETLINK
        WHERE RDATE = P_CUR_DATE;  
        
    COMMIT;
    SP_BTC_LOG_SEQ('009','I','SP_IMPORT_MYCAT_SALES_STAT','END','', V_LOG_SEQ, V_LOG_SERL_NO);
    RETURN;


    EXCEPTION
        WHEN OTHERS THEN
            v_err_code := SQLCODE();
            v_err_mesg := SQLERRM;
            ROLLBACK;
            SP_BTC_LOG_SEQ('009','E','SP_IMPORT_MYCAT_SALES_STAT','ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'', V_LOG_SEQ, V_LOG_SERL_NO);
            RETURN;

END;
/
