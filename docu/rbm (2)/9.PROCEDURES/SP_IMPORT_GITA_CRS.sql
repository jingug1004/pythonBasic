CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_GITA_CRS

IS
/******************************************************************************
- ������,��      : CHOSUNGMOON 2016.07.13
- ���α׷�Ÿ��   : procedure
- ���           : â���λ� ��Ÿ�ҵ漼(GITA) �ڷḦ �����´�.
- IN  �μ�       :

- ���μ���
    1. ���� â���λ� ��Ÿ�ҵ漼 �ڷ� ����
    2. â���λ꿡�� �����ڷḦ ��ȸ�Ͽ� TBJI_PC_GITA_CRS ���̺� �Է�


- ���ν����� ���� �� 10�ÿ� �۵��ǵ��� �Ѵ�.(�����ٷ� ���)
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

    -- 1. ����  ������ ����
    DELETE
      FROM  TBJI_PC_GITA_CRS
     WHERE  JIGEUP_DT   = P_CUR_DATE;

    -- 2. â���λ� ��Ÿ�ҵ漼 �ڷ� �Է�
    INSERT INTO TBJI_PC_GITA_CRS(MEET_CD, SELL_CD, JIGEUP_DT, GITA1, GITA2, INST_ID, INST_DT)
    (
	    SELECT --â��
				DECODE(CYCLECD, '001', '001', '002', '002', '003', '004', '099', '003', '999') MEET_CD,
				'02' SELL_CD,
				PAYDATE JIGEUP_DT,
				SUM(GITA) GITA1,
				SUM(JUMIN) GITA2,
				'SYSTEM' INST_ID,
				SYSDATE INST_DT
			FROM CCRC.VIEW_TAX@CCRCLINK
			WHERE 1=1
			AND PAYDATE = P_CUR_DATE --�˻�����
			GROUP BY CYCLECD, PAYDATE
			UNION ALL
			SELECT --�λ�
				DECODE(CYCLECD, '001', '001', '002', '002', '003', '004', '099', '003', '999') MEET_CD,
				'04' SELL_CD,
				PAYDATE JIGEUP_DT,
				SUM(GITA) GITA1,
				SUM(JUMIN) GITA2,
				'SYSTEM' INST_ID,
				SYSDATE INST_DT
			FROM BCRC.VW_BUS_TAX@BCRCDBLINK
			WHERE 1=1
			AND PAYDATE = P_CUR_DATE	--�˻�����
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
            SP_ALARM_SEND_SMS( '011', 'â���λ��Ÿ�ҵ漼(GITA)�ڷῬ������'||P_CUR_DATE, 'admin', 'RSM4011', 'SP_IMPORT_GITA_CRS');
            SP_BTC_LOG_SEQ('102','E','SP_IMPORT_GITA_CRS','ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'', V_LOG_SEQ, V_LOG_SERL_NO);
            RETURN;

END;
/
