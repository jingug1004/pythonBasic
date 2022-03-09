CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_MRA_BUSAN_RACE_NO(
    P_CUR_DATE IN VARCHAR2) 

IS
/******************************************************************************
- ������,��      :
- ���α׷���     : SP_IMPORT_MRA_BUSAN_RACE_NO
- ���α׷�Ÿ��   : procedure
- ���           : �λ� ���� ����� ���� : ���Է��̳� �߸��� ��쿡�� �����
- IN  �μ�       :

- ���μ���
    1. ���ֺ� ���� ����� ���Է� �Ǵ� �ݾ� ���� �ڷ� ��ȸ
    2. �λ�/â�� ��� dB�� �����Ͽ� �ڷḦ �����ͼ� â��, �λ��� ������������� ���� �ش�.
    
- OUTSITE(�λ�)       -> DIV_NO
    101 ����          -> 01
    102 ����          -> 02
    003 ����          -> 03
    005 ���� ����ī�� -> 03
    006 ���� ����ī�� -> 01
    
- OUTSITE(â��)       -> DIV_NO
    001 ����(����)     -> 01
    002 ��������(����) -> 02
    004 ����ī��(��ü=����+����)  :  '12.8���� ��ü ����ī��
                                          : '12.8 ~ ���� ����ī��
    
    

- ���ν����� ��, �� 10~19�ñ��� 5�а���  �۵��ǵ��� �Ѵ�.(�����ٷ� ���)
******************************************************************************/
    V_ERR_CODE NUMBER;
    V_ERR_MESG VARCHAR(255);
    V_LOG_SEQ NUMBER(15);
    V_LOG_SERL_NO NUMBER(3);
    V_DATA_ERR VARCHAR(500);


    CURSOR CHOCHA_CURSOR IS
            SELECT  A.RACE_NO, CHOCHA_DT_02, CHOCHA_DT_04, CHOCHA_CALC, 
                         B.CHOCHA_AMT_02, B.CHOCHA_AMT_04, 
                         A.CHOCHA_CALC - B.CHOCHA_AMT_02 - B.CHOCHA_AMT_04 AS CHOCHA_VERI,
                         DECODE(A.CHOCHA_CALC - B.CHOCHA_AMT_02 - B.CHOCHA_AMT_04,0,'OK','����') AS CHOCHA_VERI_DESC,
                         C.PAYOFFS_02, C.PAYOFFS_04, 
                         CASE  WHEN CHOCHA_AMT_02 != PAYOFFS_02 THEN 'â�� ���� ����� ����'
                                 WHEN CHOCHA_AMT_04 != PAYOFFS_04 THEN '�λ� ���� ����� ����'
                                 END AS VERI_INFO 
            FROM (
                            SELECT *
                            FROM VW_SDL_VERIFY_CHOCHA
                            WHERE 1=1
                            AND (meet_cd, stnd_year, tms, day_ord) IN (SELECT meet_cd, stnd_year, tms, day_ord FROM VW_SDL_INFO WHERE race_day = P_CUR_DATE AND meet_cd = '003')
                            AND (CHOCHA_DT_04 = 0 OR CHOCHA_DT_02 = 0 OR CHOCHA_CHK != 0)   -- �����ڷḸ ��ȸ
                            ) A,
                            (
                                     -- �λ����� �����
                                    SELECT RACE_DAY, MEET_CD, STND_YEAR, TMS,
                                           DAY_ORD,  RACE_NO, 
                                           SUM(DIV_TOTAL_02) AS CHOCHA_AMT_02, 
                                           SUM(DIV_TOTAL_04) AS CHOCHA_AMT_04, 
                                           SUM(REFUND) REFUND
                                    FROM (
                                            SELECT
                                                RACEDATE RACE_DAY, '003' MEET_CD, RACEYY STND_YEAR, TO_NUMBER (RACETIMES) TMS,
                                                TO_NUMBER (RACEDAYS) DAY_ORD, RACENO RACE_NO,  0 AS DIV_TOTAL_02,
                                                WIN + PLACE + EXACTA + QUINELLA + TRIELLA DIV_TOTAL_04, REFUND
                                            FROM BCRC.VIEW_BOATSALES_N@BCRCDBLINK
                                            WHERE (WIN + PLACE + EXACTA + QUINELLA + TRIELLA) > 0 AND OUTSITE <> '000'
                                              AND RACEDATE = P_CUR_DATE
                                            UNION  ALL
                                            SELECT   -- ȯ�Ҿ�(�ջ��ڷῡ�� ������)
                                                RACEDATE RACE_DAY, '003' MEET_CD, RACEYY STND_YEAR, TO_NUMBER (RACETIMES) TMS,
                                                TO_NUMBER (RACEDAYS) DAY_ORD, RACENO RACE_NO, 
                                                0, 0 DIV_TOTAL, REFUND
                                            FROM BCRC.VIEW_BOATSALES_N@BCRCDBLINK
                                            WHERE (WIN + PLACE + EXACTA + QUINELLA + TRIELLA) > 0 AND OUTSITE = '000'
                                              AND RACEDATE =P_CUR_DATE
                                            UNION ALL
                                            -- â������� �����
                                            SELECT
                                                RACEDATE RACE_DAY, '003' MEET_CD, RACEYY STND_YEAR, TO_NUMBER (RACETIMES) TMS,
                                                TO_NUMBER (RACEDAYS) DAY_ORD, RACENO RACE_NO, 
                                                SUM(WIN + PLACE + EXACTA + QUINELLA + TRIELLA) DIV_TOTAL_02, 0 AS DIV_TOTAL_04, 0 AS REFUND
                                            FROM CCRC.VIEW_BOATSALES@CCRCLINK
                                            WHERE RACEDATE = P_CUR_DATE
                                            --AND    OUTSITE <> '001'
                                            GROUP BY RACEDATE, RACEYY, TO_NUMBER(RACETIMES), TO_NUMBER(RACEDAYS), RACENO
                                           )
                                           GROUP BY  RACE_DAY, MEET_CD, STND_YEAR, TMS,  DAY_ORD,  RACE_NO
                                    ) B
                       ,(
                        SELECT MEET_CD, STND_YEAR, TMS, DAY_ORD, RACE_NO, SUM(DECODE(SELL_CD, '02',NET_AMT,0)) AS PAYOFFS_02, SUM(DECODE(SELL_CD, '04',NET_AMT,0)) PAYOFFS_04
                        FROM TBJI_PC_PAYOFFS
                        WHERE MEET_CD = '003'
                        AND SELL_CD NOT IN ('01','03')
                        GROUP BY MEET_CD, STND_YEAR, TMS, DAY_ORD, RACE_NO
                       ) C
            WHERE A.MEET_CD = B.MEET_CD
            AND     A.STND_YEAR = B.STND_YEAR
            AND     A.TMS = B.TMS
            AND     A.DAY_ORD = B.DAY_ORD
            AND     A.RACE_NO = B.RACE_NO
            AND     A.MEET_CD = C.MEET_CD(+)
            AND     A.STND_YEAR = C.STND_YEAR(+)
            AND     A.TMS = C.TMS(+)
            AND     A.RACE_NO = C.RACE_NO(+)
            AND     A.DAY_ORD = C.DAY_ORD(+)                             
            ORDER BY 1;

BEGIN
      DBMS_OUTPUT.ENABLE;
    
    SELECT SQ_TBRK_BTC_LOG.NEXTVAL
      INTO V_LOG_SEQ
    FROM DUAL;
    
    V_LOG_SERL_NO := 0;    
   -- SP_BTC_LOG_SEQ('006','I','SP_IMPORT_MRA_BUSAN_RACE_NO','START','', V_LOG_SEQ, V_LOG_SERL_NO);
    
        
       FOR CHOCHA IN CHOCHA_CURSOR LOOP

            IF CHOCHA.CHOCHA_VERI = 0 THEN  -- �ڷᰡ ��ġ�� ��츸 ����
                            MERGE INTO TBES_SDL_DT DEST
                             USING (
                                 SELECT
                                     A.MEET_CD,
                                     A.STND_YEAR,
                                     A.TMS,
                                     A.DAY_ORD,
                                     A.RACE_NO,
                                     A.SELL_CD,
                                     A.COMM_NO,
                                     A.DIV_NO,
                                     A.DIV_TOTAL,
                                     A.REFUND,
                                     'BUSDT' UPDT_ID,
                                     SYSDATE UPDT_DT,
                                     'BUSDT' INST_ID,
                                     SYSDATE INST_DT
                                 FROM
                                     (
                                            -- �λ����� �����
                                            SELECT RACE_DAY, MEET_CD, STND_YEAR, TMS,
                                                   DAY_ORD,  RACE_NO, SELL_CD,   COMM_NO, 
                                                   DECODE(DIV_NO, '05', '03', '06','01', DIV_NO) AS DIV_NO,
                                                   SUM(DIV_TOTAL) AS DIV_TOTAL, SUM(WIN) AS WIN, 
                                                   SUM(PLACE) AS PLACE, SUM(EXACTA) EXACTA, 
                                                   SUM(QUINELLA) AS QUINELLA, SUM(TRIELLA) AS TRIELLA, 
                                                   SUM(REFUND) REFUND
                                            FROM (
                                                    SELECT
                                                        RACEDATE RACE_DAY, '003' MEET_CD, RACEYY STND_YEAR, TO_NUMBER (RACETIMES) TMS,
                                                        TO_NUMBER (RACEDAYS) DAY_ORD, RACENO RACE_NO, '04' SELL_CD, '07' COMM_NO, SUBSTR (OUTSITE, 2, 2) DIV_NO,
                                                        WIN + PLACE + EXACTA + QUINELLA + TRIELLA DIV_TOTAL, WIN, PLACE,
                                                        EXACTA, QUINELLA, TRIELLA, REFUND
                                                    FROM BCRC.VIEW_BOATSALES_N@BCRCDBLINK
                                                    WHERE (WIN + PLACE + EXACTA + QUINELLA + TRIELLA) > 0 AND OUTSITE <> '000'
                                                      AND RACEDATE = P_CUR_DATE
                                                      AND RACENO    = CHOCHA.RACE_NO
                                                    UNION  ALL
                                                    SELECT   -- ȯ�Ҿ�(�ջ��ڷῡ�� ������)
                                                        RACEDATE RACE_DAY, '003' MEET_CD, RACEYY STND_YEAR, TO_NUMBER (RACETIMES) TMS,
                                                        TO_NUMBER (RACEDAYS) DAY_ORD, RACENO RACE_NO, '04' SELL_CD, '07' COMM_NO, '01' DIV_NO,
                                                        0 DIV_TOTAL, 0 WIN, 0 PLACE,
                                                        0 EXACTA, 0 QUINELLA, 0 TRIELLA, REFUND
                                                    FROM BCRC.VIEW_BOATSALES_N@BCRCDBLINK
                                                    WHERE (WIN + PLACE + EXACTA + QUINELLA + TRIELLA) > 0 AND OUTSITE = '000'
                                                      AND RACEDATE = P_CUR_DATE
                                                      AND RACENO    = CHOCHA.RACE_NO
                                            )
                                            GROUP BY RACE_DAY, MEET_CD, STND_YEAR, TMS,
                                                     DAY_ORD,  RACE_NO, SELL_CD, COMM_NO,  DECODE(DIV_NO, '05', '03', '06','01', DIV_NO) 
                                            UNION ALL
                                            -- â������� �����
                                            SELECT
                                                RACEDATE RACE_DAY, '003' MEET_CD, RACEYY STND_YEAR, TO_NUMBER (RACETIMES) TMS,
                                                TO_NUMBER (RACEDAYS) DAY_ORD, RACENO RACE_NO, '02' SELL_CD, '07' COMM_NO,  SUBSTR (OUTSITE, 2, 2) DIV_NO,
                                                SUM(WIN + PLACE + EXACTA + QUINELLA + TRIELLA) DIV_TOTAL, 
                                                SUM(WIN) AS WIN, SUM(PLACE) AS PLACE, SUM(EXACTA) AS EXACTA, 
                                                SUM(QUINELLA) AS QUINELLA, SUM(TRIELLA) AS TRIELLA, 0 AS REFUND
                                            FROM CCRC.VIEW_BOATSALES@CCRCLINK
                                            WHERE RACEDATE = P_CUR_DATE
                                            AND    RACENO      = CHOCHA.RACE_NO
                                            --AND    OUTSITE <> '001'
                                            GROUP BY RACEDATE, RACEYY, TO_NUMBER(RACETIMES), TO_NUMBER(RACEDAYS), RACENO, SUBSTR (OUTSITE, 2, 2)
                                     ) A
                             ) SRC
                             ON (
                                     DEST.MEET_CD  =SRC.MEET_CD
                                 AND DEST.STND_YEAR=SRC.STND_YEAR
                                 AND DEST.TMS      =SRC.TMS
                                 AND DEST.DAY_ORD  =SRC.DAY_ORD
                                 AND DEST.RACE_NO  =SRC.RACE_NO
                                 AND DEST.SELL_CD  = SRC.SELL_CD
                                 AND DEST.COMM_NO  =SRC.COMM_NO
                                 AND DEST.DIV_NO   =SRC.DIV_NO
                             )
                             WHEN MATCHED THEN
                                 UPDATE SET
                                      DEST.DIV_TOTAL = SRC.DIV_TOTAL
                                     ,DEST.UPDT_ID   = SRC.UPDT_ID
                                     ,DEST.UPDT_DT   = SRC.UPDT_DT
                             WHEN NOT MATCHED THEN
                                 INSERT (
                                     DEST.MEET_CD,
                                     DEST.STND_YEAR,
                                     DEST.TMS,
                                     DEST.DAY_ORD,
                                     DEST.RACE_NO,
                                     DEST.SELL_CD,
                                     DEST.COMM_NO,
                                     DEST.DIV_NO,
                                     DEST.DIV_TOTAL,
                                     DEST.REFUND,
                                     DEST.INST_ID,
                                     DEST.INST_DT,
                                     DEST.UPDT_ID,
                                     DEST.UPDT_DT
                                 )
                                 VALUES (
                                     SRC.MEET_CD,
                                     SRC.STND_YEAR,
                                     SRC.TMS,
                                     SRC.DAY_ORD,
                                     SRC.RACE_NO,
                                     SRC.SELL_CD,
                                     SRC.COMM_NO,
                                     SRC.DIV_NO,
                                     SRC.DIV_TOTAL,
                                     SRC.REFUND,
                                     SRC.INST_ID,
                                     SRC.INST_DT,
                                     SRC.UPDT_ID,
                                     SRC.UPDT_DT
                                 );
                    --   SP_BTC_LOG_SEQ('006','I','SP_IMPORT_MRA_BUSAN_RACE_NO','START','RACE_NO:'|| CHOCHA.RACE_NO, V_LOG_SEQ, V_LOG_SERL_NO);                                 
            ELSE 
               IF V_DATA_ERR <> ''   THEN
                         V_DATA_ERR := V_DATA_ERR || ', '||CHOCHA.RACE_NO;
               END IF;
            END IF;

       END LOOP;
       /*
       IF (LENGTH(V_DATA_ERR) > 0) THEN
            SP_BTC_LOG_SEQ('006','E','SP_IMPORT_MRA_BUSAN_RACE_NO','������������� ����Ÿ ����',V_DATA_ERR, V_LOG_SEQ, V_LOG_SERL_NO);
       END IF;
       */


    COMMIT;
   -- SP_BTC_LOG_SEQ('006','I','SP_IMPORT_MRA_BUSAN_RACE_NO','END','', V_LOG_SEQ, V_LOG_SERL_NO);
    
    RETURN;


    EXCEPTION
        WHEN OTHERS THEN
            V_ERR_CODE := SQLCODE();
            V_ERR_MESG := SQLERRM;
            ROLLBACK;
            SP_BTC_LOG_SEQ('006','E','SP_IMPORT_MRA_BUSAN_RACE_NO','ERROR CODE:'||V_ERR_CODE || ' ERROR LOG:'||V_ERR_MESG,'', V_LOG_SEQ, V_LOG_SERL_NO);
            RETURN;

END;
/
