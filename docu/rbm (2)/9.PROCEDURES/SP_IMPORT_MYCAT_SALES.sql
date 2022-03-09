CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_MYCAT_SALES( P_CUR_DATE IN VARCHAR2 )

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
    v_job_date DATE;
    v_err_code NUMBER;
    v_err_mesg VARCHAR(255);
    V_LOG_SEQ NUMBER(15);
    V_LOG_SERL_NO NUMBER(3);

BEGIN
    DBMS_OUTPUT.ENABLE;

    SELECT SQ_TBRK_BTC_LOG.NEXTVAL
      INTO V_LOG_SEQ
    FROM DUAL;
    
    V_LOG_SERL_NO := 0;    
    SP_BTC_LOG_SEQ('009','I','SP_IMPORT_MYCAT_SALES','START','ARG:'||P_CUR_DATE, V_LOG_SEQ, V_LOG_SERL_NO);

    -- 1. ����  ������ ����
    DELETE
      FROM  TBES_MYCAT_SALES
     WHERE  RACE_DAY   = P_CUR_DATE;
    
    DELETE
      FROM  TBES_MYCAT_SALES_DET
     WHERE  RACE_DAY   = P_CUR_DATE; 

    -- 2. ������ ����� �ڷ� �Է�
    INSERT INTO TBES_MYCAT_SALES
          (RACE_DAY, MEET_CD, STND_YEAR, TMS, DAY_ORD, RACE_NO, SELL_CD, COMM_NO, DIV_NO, BRNC_CD, PLACE, TOTAL_AMT, REFUND, NET_AMT, AMOUNT1, AMOUNT2, AMOUNT3, AMOUNT4, AMOUNT5, AMOUNT6, AMOUNT7, AMOUNT8, COUNT1, COUNT2, COUNT3, COUNT4, COUNT5, COUNT6, COUNT7, COUNT8, INST_DT)
     SELECT RACE_DAY,
             MEET_CD,
             STND_YEAR,
             TMS,
             DAY_ORD,
             RACE_NO,
             SELL_CD,                 --SELL_CD:01�� ���� "TOTE SYSTEM" ���� ������ ����
             COMM_NO,                          --����:01, ����:07 ==> ��� '06'���� ����
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
             SYSDATE
        FROM (        
        SELECT RACE_DAY,
                     MEET_CD,
                     STND_YEAR,
                     TMS,
                     DAY_ORD,
                     RACE_NO,
                     '01' SELL_CD,              --SELL_CD:01�� ���� "TOTE SYSTEM" ���� ������ ����
                     '06' COMM_NO,              --����:01, ����:07 ==> ��� '06'���� ����
                     DECODE (COMM_NO, '01', '00', DIV_NO) DIV_NO,  
                     DECODE(COMM_NO, '01','00',DIV_NO) AS BRNC_CD,                                          
                     --DIV_NO AS PLACE,         -- ��)�ҽ�
                     c_small_code AS PLACE,     -- ������ ��Ȯ�� ��ǥ�� Ȯ�� �ʿ�.. 2014.10.1 ������
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
                     COUNT8
                FROM V_MYCAT_SALES@CYBETLINK 
                WHERE RACE_DAY = P_CUR_DATE               
                )
    GROUP BY RACE_DAY,MEET_CD,STND_YEAR,TMS,DAY_ORD,RACE_NO,SELL_CD,COMM_NO, DIV_NO, BRNC_CD, PLACE;
    
    -- 3. ������ ����� �ڷ� �Է�(��,�ܸ��� ������ �߰�)
    INSERT INTO TBES_MYCAT_SALES_DET
          (RACE_DAY, MEET_CD, STND_YEAR, TMS, DAY_ORD, RACE_NO, SELL_CD, COMM_NO, DIV_NO, BRNC_CD, PLACE, TOTAL_AMT, REFUND, NET_AMT, AMOUNT1, AMOUNT2, AMOUNT3, AMOUNT4, AMOUNT5, AMOUNT6, AMOUNT7, AMOUNT8, COUNT1, COUNT2, COUNT3, COUNT4, COUNT5, COUNT6, COUNT7, COUNT8, CHANNEL_CD, INST_DT)
     SELECT RACE_DAY,
             MEET_CD,
             STND_YEAR,
             TMS,
             DAY_ORD,
             RACE_NO,
             SELL_CD,                 --SELL_CD:01�� ���� "TOTE SYSTEM" ���� ������ ����
             COMM_NO,                          --����:01, ����:07 ==> ��� '06'���� ����
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
                     '01' SELL_CD,              --SELL_CD:01�� ���� "TOTE SYSTEM" ���� ������ ����
                     '06' COMM_NO,              --����:01, ����:07 ==> ��� '06'���� ����
                     DECODE (COMM_NO, '01', '00', DIV_NO) DIV_NO,  
                     DECODE(COMM_NO, '01','00',DIV_NO) AS BRNC_CD,                                          
                     --DIV_NO AS PLACE,         -- ��)�ҽ�
                     c_small_code AS PLACE,     -- ������ ��Ȯ�� ��ǥ�� Ȯ�� �ʿ�.. 2014.10.1 ������
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
    SP_BTC_LOG_SEQ('009','I','SP_IMPORT_MYCAT_SALES','END','', V_LOG_SEQ, V_LOG_SERL_NO);
    RETURN;


    EXCEPTION
        WHEN OTHERS THEN
            v_err_code := SQLCODE();
            v_err_mesg := SQLERRM;
            ROLLBACK;
            SP_BTC_LOG_SEQ('009','E','SP_IMPORT_MYCAT_SALES','ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'', V_LOG_SEQ, V_LOG_SERL_NO);
            RETURN;

END;
/
