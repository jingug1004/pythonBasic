CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_TELMP_FIXEDSEAT( P_CUR_DATE IN VARCHAR2 )

IS
/******************************************************************************
- ������,��      : chojob 2017.03.26
- ���α׷���     : SP_IMPORT_TELMP_FIXEDSEAT
- ���α׷�Ÿ��   : procedure
- ���           : �����¼��� �߸���Ȳ �ڷḦ �����´�.(����, �׸�ī��)
- IN  �μ�       :

- ���μ���
    1. ���� ����/�׸�ī�� �߸��ڷ��� ����
    2. ������ DB���� �����ڷḦ ��ȸ�Ͽ� TBJI_PC_TELMP_FIXEDSEAT ���̺� �Է�


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
    SP_BTC_LOG_SEQ('106','I','SP_IMPORT_TELMP_FIXEDSEAT','START','ARG:'||P_CUR_DATE, V_LOG_SEQ, V_LOG_SERL_NO);

    -- 1. ����  ������ ����
    DELETE
      FROM  TBJI_PC_TELMP_FIXEDSEAT
     WHERE  RACE_DAY   = P_CUR_DATE;

    -- 2. ������ ����� �ڷ� �Է�
    INSERT INTO TBJI_PC_TELMP_FIXEDSEAT(RACE_DAY, COMM_NO, GRN_ZN_CD, WIN_NO, WIN_TYPE, AMT, NUM, INST_ID, INST_DT)
    WITH TELMP AS (
        SELECT RACE_DAY, COMM_NO, WIN_NO,  WIN_TYPE, SUM(SOLD_AMT-CNCL_AMT) AMT, SUM(SOLD_NUM-CNCL_NUM) NUM FROM (
            SELECT
                B.RACE_DAY,
                (CASE WHEN A.MEET_CD = '003' AND A.COMM_NO<'10' THEN '98'
                      WHEN A.MEET_CD != '003' AND A.COMM_NO<'10' THEN '00'
                      ELSE A.COMM_NO
                 END) COMM_NO,
                 WIN_NO,
                 WIN_TYPE,
                 SOLD_AMT,
                 CNCL_AMT,
                 SOLD_NUM,
                 CNCL_NUM
            FROM VW_PC_TELMP A, VW_SDL_INFO B
            WHERE 1=1
            AND A.STND_YEAR = B.STND_YEAR
            AND A.MEET_CD = B.MEET_CD
            AND A.MEET_CD IN ('001','003')
            AND A.TMS = B.TMS
            AND A.DAY_ORD = B.DAY_ORD
            AND A.SOLD_AMT > 0
            AND B.RACE_DAY = P_CUR_DATE /** P*/
            AND A.COMM_NO != '06'
        )
        GROUP BY RACE_DAY, COMM_NO, WIN_NO, WIN_TYPE
    ),
    MYCAT_TELMP AS (
        SELECT RACE_DAY, COMM_NO, DEV_NM, '14' WIN_TYPE, SUM(DEV_SALES_AMT) AMT, SUM(DEV_SALES_CNT) NUM FROM (
            SELECT
                RACE_DAY,
                (CASE WHEN A.MEET_CD = '003' AND A.COMM_NO<'10' THEN '98'
                      WHEN A.MEET_CD != '003' AND A.COMM_NO<'10' THEN '00'
                      ELSE A.COMM_NO
                 END) COMM_NO,
                 DEV_NM,
                 DEV_SALES_AMT,
                 DEV_SALES_CNT
            FROM TBRS_MYCAT_TELMP A
            WHERE 1=1
            AND CHNL_CD = '001'
            AND MEET_CD IN ('001','003')
            AND RACE_DAY = P_CUR_DATE /** P*/
        )
        GROUP BY RACE_DAY, COMM_NO, DEV_NM
    )

    SELECT RACE_DAY, COMM_NO, GRN_ZN_CD, WIN_NO, WIN_TYPE, SUM(AMT) AMT, SUM(NUM) NUM, 'SYSTEM', SYSDATE FROM (
        SELECT TP.RACE_DAY, TP.COMM_NO, MP.GRN_ZN_CD, TP.WIN_NO, TP.WIN_TYPE, SUM(TP.AMT) AMT, SUM(TP.NUM) NUM
        FROM TELMP TP, TBRA_WIN_NO_MAPP MP
        WHERE 1=1
        AND TP.COMM_NO = MP.COMM_NO
        AND TP.WIN_NO = MP.WIN_NO
        GROUP BY TP.RACE_DAY, TP.COMM_NO, MP.GRN_ZN_CD, TP.WIN_NO, TP.WIN_TYPE
        UNION ALL
        SELECT TP.RACE_DAY, TP.COMM_NO, MP.GRN_ZN_CD, TP.DEV_NM WIN_NO, TP.WIN_TYPE, SUM(TP.AMT) AMT, SUM(TP.NUM) NUM
        FROM MYCAT_TELMP TP, TBRA_MYCAT_NO_MAPP MP
        WHERE 1=1
        AND TP.COMM_NO = MP.COMM_NO
        AND TP.DEV_NM = MP.DEV_NM
        GROUP BY TP.RACE_DAY, TP.COMM_NO, MP.GRN_ZN_CD, TP.DEV_NM, TP.WIN_TYPE
    )
    GROUP BY RACE_DAY, COMM_NO, GRN_ZN_CD, WIN_NO, WIN_TYPE
;


    COMMIT;
    SP_BTC_LOG_SEQ('106','I','SP_IMPORT_TELMP_FIXEDSEAT','END','', V_LOG_SEQ, V_LOG_SERL_NO);
    RETURN;


    EXCEPTION
        WHEN OTHERS THEN
            v_err_code := SQLCODE();
            v_err_mesg := SQLERRM;
            ROLLBACK;
            SP_BTC_LOG_SEQ('106','E','SP_IMPORT_TELMP_FIXEDSEAT','ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'', V_LOG_SEQ, V_LOG_SERL_NO);
            RETURN;

END;
/
