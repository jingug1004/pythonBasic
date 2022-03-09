CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_RACE_TIME_INFO
IS
/******************************************************************************
- ������,��      : jsshin 2013.07.07
- ���α׷���     : SP_IMPORT_RACE_TIME_INFO
- ���α׷�Ÿ��   : procedure
- ���           :  ��� ���/���� ����ó�� ���������� �����´�.(����1ȸ ����)
- IN  �μ�       :

- ���μ���
    1. ���� ������ ����(�ֱ� 8�ϰ�)
    2. ������/���� ������ �����´�.
    3. â�� �ڷḦ �����´�.
    4. �λ� �ڷḦ �����´�.

- ���ν����� ���� �� 10:00�ÿ� �۵��ǵ��� �Ѵ�.(�����ٷ� ���)
******************************************************************************/
    v_job_date date;
    v_err_code number;
    v_err_mesg varchar(255);
    V_LOG_SEQ NUMBER(15);
    V_LOG_SERL_NO NUMBER(3);
    V_MAX_SEQ_NO_FS NUMBER(10);

BEGIN
    DBMS_OUTPUT.ENABLE;

    SELECT SQ_TBRK_BTC_LOG.NEXTVAL
      INTO V_LOG_SEQ
    FROM DUAL;
    
    V_LOG_SERL_NO := 0;    
    SP_BTC_LOG_SEQ('014','I','SP_IMPORT_RACE_TIME_INFO','START','', V_LOG_SEQ, V_LOG_SERL_NO);

   -- 1. �ֱ� �ϰ� �ڷḦ �����Ѵ�.
   DELETE FROM TBRS_RACE_INFO
   WHERE RACE_DAY BETWEEN TO_CHAR(SYSDATE -8, 'YYYYMMDD') AND TO_CHAR(SYSDATE, 'YYYYMMDD');

    -- 2. ������/���� ���������� �����´�.
    INSERT INTO TBRS_RACE_INFO
       SELECT '001' AS RCV_ORG_CD,
                   S2.MEET_CD,
              S2.STND_YEAR,
              S1.TMS,
              S2.DAY_ORD,
              S3.RACE_DAY,
              S2.RACE_NO,
              STR_TM
         FROM US_CRA.TBJB_TMS_SCHE S1, US_CRA.TBJB_TM_LEN S2, TBES_SDL_TM S3
        WHERE     0 = 0
              AND S1.MEET_CD = S2.MEET_CD
              AND S1.STND_YEAR = S2.STND_YEAR
              --AND S1.RACE_MM = S2.RACE_MM
              AND S1.TMS = S2.TMS
              AND S1.MEET_CD = S3.MEET_CD
              AND S1.STND_YEAR = S3.STND_YEAR
              AND S1.TMS = S3.TMS
              AND S2.DAY_ORD = S3.DAY_ORD
              AND S2.RACE_NO = S3.RACE_NO
              AND S1.MEET_CD <> '003'
              AND S3.RACE_DAY BETWEEN TO_CHAR(SYSDATE -8, 'YYYYMMDD') AND TO_CHAR(SYSDATE, 'YYYYMMDD')
       UNION ALL
       SELECT '003' AS RCV_ORG_CD, 
              '003' AS MEET_CD,
              R.STND_YEAR,
              TRIM (TO_CHAR (R.TMS, '00')) AS TMS,
              TRIM (TO_CHAR (R.DAY_ORD, '0')) AS DAY_ORD,
              T.RACE_DAY,
              R.RACE_NO,
              STRT_TIME
         FROM MRASYS.TBEB_RACE R, USRBM.TBES_SDL_TM T
        WHERE     T.MEET_CD = '003'
              AND R.STND_YEAR = T.STND_YEAR
              AND R.TMS = T.TMS
              AND R.DAY_ORD = T.DAY_ORD
              AND R.RACE_NO = T.RACE_NO
              AND T.RACE_DAY BETWEEN TO_CHAR(SYSDATE -8, 'YYYYMMDD') AND TO_CHAR(SYSDATE, 'YYYYMMDD');
              
    -- 3. â�� ���������� �����´�.
    INSERT INTO TBRS_RACE_INFO
    SELECT '002' AS RCV_ORG_CD,
                DECODE(TRACKCD,'003','004',TRACKCD) AS MEET_CD,
                RCYEAR,
                RCTIMES,
                RCDAYS,
                RCDATE,
                RACENO,
                PLANTIME
    FROM ccrc.view_racereal_for_cra@ccrclink
    WHERE RCTIMES NOT IN ('04B','25A','37A')
    AND RCDATE BETWEEN TO_CHAR(SYSDATE -8, 'YYYYMMDD') AND TO_CHAR(SYSDATE, 'YYYYMMDD');

    -- 4. �λ� ���������� �����´�.
    INSERT INTO TBRS_RACE_INFO
    SELECT '004' RCV_ORG_CD, 
               DECODE(CYCLENM,'����','001','â��','002','�λ�','004','003') AS MEET_CD,
               RACEYY AS STND_YEAR,
               RACETIMES AS TMS,
               RACEDYS AS DAY_ORD,
               RACEDATE AS RACE_DAY,
               RACENO AS RACE_NO,
               STARTIME AS STR_TM
    FROM bcrc.v_racetime@BCRCDBLINK A
    WHERE RACETIMES NOT LIKE '*%' AND RACETIMES NOT LIKE '$%'
    AND   RACEDATE BETWEEN TO_CHAR(SYSDATE -8, 'YYYYMMDD') AND TO_CHAR(SYSDATE, 'YYYYMMDD');

    COMMIT;
    
    SP_BTC_LOG_SEQ('014','I','SP_IMPORT_RACE_TIME_INFO','END','', V_LOG_SEQ, V_LOG_SERL_NO);
    RETURN;


    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            
            v_err_code := SQLCODE();
            v_err_mesg := SQLERRM;
            
            SP_BTC_LOG_SEQ('014','E','SP_IMPORT_RACE_TIME_INFO','ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'', V_LOG_SEQ, V_LOG_SERL_NO);
            RETURN;
END;
/
