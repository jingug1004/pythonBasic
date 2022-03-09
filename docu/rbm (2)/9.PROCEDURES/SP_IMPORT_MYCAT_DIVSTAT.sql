CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_MYCAT_DIVSTAT( P_CUR_DATE IN VARCHAR2 )

IS
/******************************************************************************
- ������,��      : jsshin 2012.03.10
- ���α׷���     : SP_IMPORT_mycat_divstat
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
    SP_BTC_LOG_SEQ('009','I','SP_IMPORT_MYCAT_DIVSTAT','START','ARG:'||P_CUR_DATE, V_LOG_SEQ, V_LOG_SERL_NO);

    -- 1. ����  ������ ����
    DELETE
      FROM  TBES_MYCAT_DIVSTAT
     WHERE  RACE_DAY   = P_CUR_DATE;

    -- 2. ������ ����� �ڷ� �Է�
    INSERT INTO TBES_MYCAT_DIVSTAT(
                RACE_DAY, SELL_CD, COMM_NO, DIV_NO, OUT_AMT, IN_AMT, MILEAGE_AMT, 
                BONUS_AMT, INST_DT, UPDT_DT, PURGE_AMT,CONSIGN_AMT)
    SELECT RACE_DAY,
                 SELL_CD,
                 COMM_NO,                          --����:01, ����:07 ==> ��� '06'���� ����
                 DIV_NO,       --������ ������������ ���ؼ� COMM_NO�� ��������, ���ý��ۿ����� DIV_NO�� ���
                 SUM (OUT_AMT) OUT_AMT,
                 SUM (IN_AMT) IN_AMT,
                 SUM (MILEAGE_AMT) MILEAGE_AMT,
                 SUM (BONUS_AMT) BONUS_AMT,
                 SYSDATE,
                 NULL,
                 SUM (PURGE_AMT) AS PURGE_AMT,
                 SUM(CONSIGN_AMT) CONSIGN_AMT
            FROM (SELECT RACE_DAY,
                         SELL_CD,
                         COMM_NO,
                         DIV_NO,
                         OUT_AMT,
                         IN_AMT,
                         MILEAGE_AMT,
                         BONUS_AMT,
                         PURGE_AMT,
                         CONSIGN_AMT
                    FROM V_MYCAT_DIVSTAT@CYBETLINK
                    WHERE RACE_DAY = P_CUR_DATE)
        GROUP BY RACE_DAY,SELL_CD,COMM_NO,DIV_NO;

    COMMIT;
    SP_BTC_LOG_SEQ('009','I','SP_IMPORT_MYCAT_DIVSTAT','END','', V_LOG_SEQ, V_LOG_SERL_NO);
    RETURN;


    EXCEPTION
        WHEN OTHERS THEN
            v_err_code := SQLCODE();
            v_err_mesg := SQLERRM;
            ROLLBACK;
            SP_BTC_LOG_SEQ('009','E','SP_IMPORT_MYCAT_DIVSTAT','ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'', V_LOG_SEQ, V_LOG_SERL_NO);
            RETURN;

END;
/
