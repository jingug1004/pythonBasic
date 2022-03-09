CREATE OR REPLACE PROCEDURE USRBM.SP_CHK_LOG IS
V_MESG   VARCHAR(2000);      -- ���ڸ޼���
V_RET    VARCHAR(1);        -- �����
V_SEND_YN VARCHAR(1);
V_CHK_DATE VARCHAR(8);

    CURSOR CUR_LOG_CHECK IS
        SELECT PROGRAM||CHR(13)||CHR(10)||'('|| LOG_DESC||')'||
                 ' �����߻��Ͻ�['||TRIM(TO_CHAR(TO_NUMBER(SUBSTR(LOG_DTM,5,2)),'99'))||'.'||SUBSTR(LOG_DTM,7,2)||' '||SUBSTR(LOG_DTM,9,2)||':'||SUBSTR(LOG_DTM,11,2)||']'||CHR(13)||CHR(10) AS LOG_DESC
                    --NVL(COUNT(*), 0) AS ERR_CNT
        FROM (
                SELECT PROGRAM, LOG_DESC, MAX(LOG_DT||LOG_TM) AS LOG_DTM
                FROM TBRK_BTC_LOG
                WHERE LOG_CD = 'E'        
                AND   LOG_DT BETWEEN TO_CHAR(SYSDATE -1, 'YYYYMMDD') AND TO_CHAR(SYSDATE, 'YYYYMMDD')
                GROUP BY PROGRAM, LOG_DESC
                ORDER BY LOG_DTM
                 );

     CUR_LOG_CHECK_ROW  CUR_LOG_CHECK%ROWTYPE;
     
BEGIN

    -- �����ο� ���Է������� ����ڿ��� ���ڸ޼��� �߼� ���ν��� ȣ��
    /* 2014.10.4 : ���̻� �����ο��� �Է����� ����(�����Ѱ��� ��������)
    SELECT TO_CHAR(SYSDATE - DECODE(TO_CHAR(SYSDATE,'D'),4,3,1), 'YYYYMMDD')
    INTO   V_CHK_DATE
    FROM  DUAL;
    
    SP_CHK_STAY_MANA_YET(V_CHK_DATE);
    */
    
    
    V_MESG := '';
    OPEN CUR_LOG_CHECK;
    LOOP
        FETCH CUR_LOG_CHECK INTO CUR_LOG_CHECK_ROW;
        EXIT WHEN CUR_LOG_CHECK%NOTFOUND;
        
            V_MESG := V_MESG || CUR_LOG_CHECK_ROW.LOG_DESC;    
    END LOOP;
    CLOSE CUR_LOG_CHECK;
       
    IF LENGTH(V_MESG) > 0 THEN
        V_MESG := '��ġ�α� ����' || V_MESG;
                SP_ALARM_SEND_SMS( '011', V_MESG, 'admin', 'RSY6010', 'SP_CHK_LOG');
    END IF ;                
    
   EXCEPTION
     WHEN NO_DATA_FOUND THEN
         NULL;
     WHEN OTHERS THEN
       RAISE;
END SP_CHK_LOG;
/
