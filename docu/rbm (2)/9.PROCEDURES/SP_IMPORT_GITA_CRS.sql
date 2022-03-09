CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_GITA_CRS

IS
/******************************************************************************
- 개발자,일      : CHOSUNGMOON 2016.07.13
- 프로그램타입   : procedure
- 기능           : 창원부산 기타소득세(GITA) 자료를 가져온다.
- IN  인수       :

- 프로세스
    1. 당일 창원부산 기타소득세 자료 삭제
    2. 창원부산에서 당일자료를 조회하여 TBJI_PC_GITA_CRS 테이블에 입력


- 프로시져는 매일 밤 10시에 작동되도록 한다.(스케줄러 등록)
******************************************************************************/
    v_job_date date;
    v_err_code number;
    v_err_mesg varchar(255);
    V_LOG_SEQ NUMBER(15);
    V_LOG_SERL_NO NUMBER(3);
    P_CUR_DATE VARCHAR2(8);

BEGIN

    DBMS_OUTPUT.ENABLE;

    SELECT TO_CHAR(SYSDATE, 'YYYYMMDD')
    INTO     P_CUR_DATE
    FROM    DUAL;

    SELECT SQ_TBRK_BTC_LOG.NEXTVAL
      INTO V_LOG_SEQ
    FROM DUAL;

    V_LOG_SERL_NO := 0;
    SP_BTC_LOG_SEQ('102','I','SP_IMPORT_GITA_CRS','START','ARG:'||P_CUR_DATE, V_LOG_SEQ, V_LOG_SERL_NO);

    -- 1. 기존  데이터 삭제
    DELETE
      FROM  TBJI_PC_GITA_CRS
     WHERE  JIGEUP_DT   = P_CUR_DATE;

    -- 2. 창원부산 기타소득세 자료 입력
    INSERT INTO TBJI_PC_GITA_CRS(MEET_CD, SELL_CD, JIGEUP_DT, GITA1, GITA2, INST_ID, INST_DT)
    (
	    SELECT --창원
				DECODE(CYCLECD, '001', '001', '002', '002', '003', '004', '099', '003', '999') MEET_CD,
				'02' SELL_CD,
				PAYDATE JIGEUP_DT,
				SUM(GITA) GITA1,
				SUM(JUMIN) GITA2,
				'SYSTEM' INST_ID,
				SYSDATE INST_DT
			FROM CCRC.VIEW_TAX@CCRCLINK
			WHERE 1=1
			AND PAYDATE = P_CUR_DATE --검색조건
			GROUP BY CYCLECD, PAYDATE
			UNION ALL
			SELECT --부산
				DECODE(CYCLECD, '001', '001', '002', '002', '003', '004', '099', '003', '999') MEET_CD,
				'04' SELL_CD,
				PAYDATE JIGEUP_DT,
				SUM(GITA) GITA1,
				SUM(JUMIN) GITA2,
				'SYSTEM' INST_ID,
				SYSDATE INST_DT
			FROM BCRC.VW_BUS_TAX@BCRCDBLINK
			WHERE 1=1
			AND PAYDATE = P_CUR_DATE	--검색조건
			GROUP BY CYCLECD, PAYDATE
    );

    COMMIT;
    SP_BTC_LOG_SEQ('102','I','SP_IMPORT_GITA_CRS','END','', V_LOG_SEQ, V_LOG_SERL_NO);
    RETURN;


    EXCEPTION
        WHEN OTHERS THEN
            v_err_code := SQLCODE();
            v_err_mesg := SQLERRM;
            ROLLBACK;
            SP_ALARM_SEND_SMS( '011', '창원부산기타소득세(GITA)자료연동오류'||P_CUR_DATE, 'admin', 'RSM4011', 'SP_IMPORT_GITA_CRS');
            SP_BTC_LOG_SEQ('102','E','SP_IMPORT_GITA_CRS','ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'', V_LOG_SEQ, V_LOG_SERL_NO);
            RETURN;

END;
/
