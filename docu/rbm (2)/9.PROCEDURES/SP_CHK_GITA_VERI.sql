CREATE OR REPLACE PROCEDURE USRBM.SP_CHK_GITA_VERI

IS
/******************************************************************************
- 개발자,일      : CHOSUNGMOON 2016.07.13
- 프로그램타입   : procedure
- 기능           : 기타소득세(GITA) 검증.
- IN  인수       :

- 프로세스
    1. 당일 기타소득세 검증 자료 삭제
    2. DAS를 조회하여 TBJI_PC_GITA 테이블과 검증 후 TBJI_PC_GITA_VERI 테이블에 입력, 창원부산 매출건은 검증않함


- 프로시져는 매일 밤 11시에 작동되도록 한다.(스케줄러 등록)
******************************************************************************/
    v_job_date date;
    v_err_code number;
    v_err_mesg varchar(255);
    V_LOG_SEQ NUMBER(15);
    V_LOG_SERL_NO NUMBER(3);
    P_CUR_DATE VARCHAR2(8);
    V_GITA_VERI_CNT NUMBER(3);

    CURSOR CUR_GITA_VERI IS
        SELECT
            JIGEUP_DT, MEET_CD, SELL_CD, VERI
        FROM TBJI_PC_GITA_VERI
        WHERE JIGEUP_DT   = P_CUR_DATE;

        CUR_GITA_VERI_ROW  CUR_GITA_VERI%ROWTYPE;

BEGIN

    DBMS_OUTPUT.ENABLE;

    SELECT TO_CHAR(SYSDATE, 'YYYYMMDD')
    INTO     P_CUR_DATE
    FROM    DUAL;

    SELECT SQ_TBRK_BTC_LOG.NEXTVAL
      INTO V_LOG_SEQ
    FROM DUAL;

    V_LOG_SERL_NO := 0;
    SP_BTC_LOG_SEQ('103','I','SP_CHK_GITA_VERI','START','ARG:'||P_CUR_DATE, V_LOG_SEQ, V_LOG_SERL_NO);

    -- 1. 기존  데이터 삭제
    DELETE
      FROM  TBJI_PC_GITA_VERI
     WHERE  JIGEUP_DT   = TO_CHAR(SYSDATE, 'YYYYMMDD');

    -- 2. 기타소득세 검증자료 입력
    INSERT INTO TBJI_PC_GITA_VERI (
            JIGEUP_DT, MEET_CD, SELL_CD, VERI, INST_ID, INST_DT
        )
        (
            SELECT
                T1.JIGEUP_DT, T1.MEET_CD, T1.SELL_CD,
                DECODE(((T1.GITA1 - T2.GITA1) + (T1.GITA2 - T2.GITA2) + (T1.GITA_PAY - T2.GITA_PAY)), 0, '001', '002') VERI,
                'SYSTEM', SYSDATE
            FROM
            (
                SELECT
                    JIGEUP_DT, MEET_CD, SELL_CD, SUM(GITA1) GITA1, SUM(GITA2) GITA2, SUM(GITA_PAY) GITA_PAY
                FROM TBJI_PC_GITA
                WHERE 1=1
                AND JIGEUP_DT = P_CUR_DATE --조회조건
                GROUP BY JIGEUP_DT, MEET_CD, SELL_CD
            ) T1,
            (
                SELECT
                    CASH_YEAR_DATE JIGEUP_DT,
                    LPAD(SOLD_MEET_NO,3,'0') MEET_CD,
                    LPAD(SOLD_SELL_CD,2,'0') SELL_CD,
                    SUM(GITA_1) GITA1,
                    SUM(GITA_2) GITA2,
                    SUM(GITA_PAY) GITA_PAY
                FROM PC_GITA@DW01LINK
                WHERE CASH_YEAR_DATE = P_CUR_DATE --조회조건
                GROUP BY CASH_YEAR_DATE, SOLD_SELL_CD, SOLD_MEET_NO
            ) T2
            WHERE 1=1
            AND T1.JIGEUP_DT = T2.JIGEUP_DT
            AND T1.MEET_CD = T2.MEET_CD
            AND T1.SELL_CD = T2.SELL_CD
        );

    OPEN CUR_GITA_VERI;
    LOOP
        FETCH CUR_GITA_VERI INTO CUR_GITA_VERI_ROW;
        EXIT WHEN CUR_GITA_VERI%NOTFOUND;

        IF CUR_GITA_VERI_ROW.VERI = '002' THEN
            SP_ALARM_SEND_SMS( '011', '기타소득세검증오류: '||P_CUR_DATE, 'admin', 'RSM4011', 'SP_CHK_GITA_VERI');
        END IF;
        END LOOP;

   V_GITA_VERI_CNT := 0;
   SELECT COUNT(*)
   INTO V_GITA_VERI_CNT  
   FROM TBJI_PC_GITA_VERI
   WHERE JIGEUP_DT = TO_CHAR(SYSDATE, 'YYYYMMDD');
      
   IF V_GITA_VERI_CNT = 0 THEN
      SP_ALARM_SEND_SMS( '011', '기타소득세검증오류:자료없음', 'admin', 'RSM4011', 'SP_CHK_GITA_VERI');
      SP_BTC_LOG_SEQ('103','E','SP_CHK_GITA_VERI','기타소득세검증오류:자료없음', '', V_LOG_SEQ, V_LOG_SERL_NO);                             
   END IF;

    COMMIT;
    SP_BTC_LOG_SEQ('103','I','SP_CHK_GITA_VERI','END','', V_LOG_SEQ, V_LOG_SERL_NO);
    RETURN;

    EXCEPTION
        WHEN OTHERS THEN
            v_err_code := SQLCODE();
            v_err_mesg := SQLERRM;
            ROLLBACK;
            SP_ALARM_SEND_SMS( '011', '기타소득세검증오류: '||P_CUR_DATE, 'admin', 'RSM4011', 'SP_CHK_GITA_VERI');
            SP_BTC_LOG_SEQ('103','E','SP_CHK_GITA_VERI','ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'', V_LOG_SEQ, V_LOG_SERL_NO);
            RETURN;
END;
/
