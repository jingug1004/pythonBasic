CREATE OR REPLACE PROCEDURE USRBM.SP_SEND_SALES_RESULT

IS
/******************************************************************************
- ������,��      :  ������
- ���α׷���     : SP_SDL_DAEMON_CHECK
- ���α׷�Ÿ��   : procedure
- ���           : �ϸ��� �� ������� ��ϵ� �������� SMS�� �����Ѵ�.
- IN  �μ�       :

- ���μ���
    1. ���������� ���ֺ� ���۽����� �������� 25������ SDL�ڷᰡ ����� �Ѿ�� ���� üũ�Ѵ�.
    

- ���ν����� ���� 18:00 ~ 22:00 (�����ٷ� ���)
******************************************************************************/

    V_SEND_CNT NUMBER;
    V_ERR_CODE NUMBER;
    V_MESG VARCHAR(255);
    V_ERR_MESG VARCHAR(255);
    V_THIS_DATE VARCHAR(20);
    
BEGIN

    V_SEND_CNT := 0;
    
    SELECT TO_CHAR(SYSDATE, 'YYYYMMDD')
    INTO V_THIS_DATE 
    FROM DUAL;
    
    SELECT COUNT(INST_ID)
    INTO   V_SEND_CNT
    FROM TBRK_ALARM_HIST
    WHERE 1=1
    AND TITLE = 'SP_SEND_SALES_RESULT'
    AND TO_CHAR(INST_DT, 'YYYYMMDD') = TO_CHAR(SYSDATE, 'YYYYMMDD');
   
    V_MESG := ''; 
   IF V_SEND_CNT  < 1 THEN
       WITH TIME_CHECK AS  (
            SELECT --�������� �����ð� 30�� ����
            CASE WHEN (TO_NUMBER(TO_CHAR(SYSDATE-1/24/2, 'HH24MI'))-TO_NUMBER(MAX(STR_TM))) > 0 THEN 1 ELSE 0 END FLAG
            FROM (
                SELECT MAX(STRT_TIME) STR_TM FROM  MRASYS.TBEB_RACE
                WHERE 1=1
                AND (STND_YEAR, TMS, DAY_ORD, RACE_NO) IN (
                    SELECT STND_YEAR, LPAD(TMS,2,'0'), DAY_ORD, RACE_NO
                    FROM VW_SDL_INFO
                    WHERE RACE_DAY = TO_CHAR(SYSDATE,'YYYYMMDD')
                    AND MEET_CD = '003'
                )
                UNION ALL
                SELECT MAX(STR_TM) STR_TM FROM  US_CRA.TBJC_RACE_INFO
                WHERE 1=1
                AND MEET_CD = '001'
                AND STND_YEAR||RACE_DT = TO_CHAR(SYSDATE,'YYYYMMDD')
            )
       )
        SELECT
            MEET_NM || ' ' || TMS || 'ȸ ' || DAY_ORD || '����' || CHR(13) || CHR(10) ||
            '����:'|| LTRIM(TO_CHAR(AMT,'999,999')) || CHR(13) || CHR(10) ||
            '���ֺ�:'||
            CASE WHEN TO_NUMBER(TO_LAST_WEEK_SIGN) > 0 THEN '��'
                  WHEN TO_NUMBER(TO_LAST_WEEK_SIGN) = 0  THEN '-'
                  WHEN TO_NUMBER(TO_LAST_WEEK_SIGN) < 0  THEN '��'
                  END ||
            LTRIM(TO_CHAR(TO_LAST_WEEK_USIGN,'999,999')) ||CHR(13) || CHR(10) ||
            '�����:'||
            CASE WHEN TO_NUMBER(TO_LAST_YEAR_SIGN) > 0 THEN '��'
                  WHEN TO_NUMBER(TO_LAST_YEAR_SIGN) = 0  THEN '-'
                  WHEN TO_NUMBER(TO_LAST_YEAR_SIGN) < 0  THEN '��'
                  END ||
            LTRIM(TO_CHAR(TO_LAST_YEAR_USIGN,'999,999')) || CHR(13) || CHR(10) ||
            '��ȸ:'|| LTRIM(TO_CHAR(AMT_TMS,'999,999'))
        INTO V_MESG    
        FROM (
            SELECT
                DECODE(MAX(MEET_CD),'001','���','����') MEET_NM,  MAX(TMS) TMS, MAX(DAY_ORD) DAY_ORD,
                ROUND(SUM(DECODE(GUBUN, '001', AMT, 0))/1000000) AMT,
                ROUND(SUM(DECODE(GUBUN, '004', AMT, 0))/1000000) AMT_TMS,
                ROUND((SUM(DECODE(GUBUN, '001', AMT, 0))-SUM(DECODE(GUBUN, '002', AMT, 0)))/1000000) TO_LAST_WEEK_SIGN,
                ABS(ROUND((SUM(DECODE(GUBUN, '001', AMT, 0))-SUM(DECODE(GUBUN, '002', AMT, 0)))/1000000)) TO_LAST_WEEK_USIGN,
                ROUND((SUM(DECODE(GUBUN, '001', AMT, 0))-SUM(DECODE(GUBUN, '003', AMT, 0)))/1000000) TO_LAST_YEAR_SIGN,
                ABS(ROUND((SUM(DECODE(GUBUN, '001', AMT, 0))-SUM(DECODE(GUBUN, '003', AMT, 0)))/1000000)) TO_LAST_YEAR_USIGN
            FROM (
                SELECT '001' GUBUN, T1.MEET_CD, T1.TMS, T1.DAY_ORD, SUM(DIV_TOTAL) AMT
                FROM TBES_SDL_DT T1,
                    (SELECT STND_YEAR, MEET_CD, TMS, DAY_ORD FROM VW_SDL_INFO
                     WHERE RACE_DAY = V_THIS_DATE) T2
                WHERE 1=1
                AND T1.STND_YEAR = T2.STND_YEAR
                AND T1.MEET_CD = T2.MEET_CD
                AND T1.MEET_CD IN ('001','003')
                AND T1.TMS = T2.TMS
                AND T1.DAY_ORD = T2.DAY_ORD
                GROUP BY T1.MEET_CD, T1.TMS, T1.DAY_ORD
                UNION ALL
                SELECT '004' GUBUN, T1.MEET_CD, T1.TMS, 0 DAY_ORD, SUM(DIV_TOTAL) AMT
                FROM TBES_SDL_DT T1,
                    (SELECT STND_YEAR, MEET_CD, TMS FROM VW_SDL_INFO
                     WHERE RACE_DAY = V_THIS_DATE) T2
                WHERE 1=1
                AND T1.STND_YEAR = T2.STND_YEAR
                AND T1.MEET_CD = T2.MEET_CD
                AND T1.MEET_CD IN ('001','003')
                AND T1.TMS = T2.TMS
                GROUP BY T1.MEET_CD, T1.TMS
                UNION ALL
                SELECT '002' GUBUN, '' MEET_CD, 0 TMS, 0 DAY_ORD, SUM(DIV_TOTAL) AMT
                FROM TBES_SDL_DT T1,
                    (SELECT STND_YEAR, MEET_CD, TMS-1 TMS, DAY_ORD FROM VW_SDL_INFO
                     WHERE RACE_DAY = V_THIS_DATE) T2
                WHERE 1=1
                AND T1.STND_YEAR = T2.STND_YEAR
                AND T1.MEET_CD = T2.MEET_CD
                AND T1.MEET_CD IN ('001','003')
                AND T1.TMS = T2.TMS
                AND T1.DAY_ORD = T2.DAY_ORD
                UNION ALL
                SELECT '003' GUBUN, '' MEET_CD, 0 TMS, 0 DAY_ORD, SUM(DIV_TOTAL) AMT
                FROM TBES_SDL_DT T1,
                    (SELECT STND_YEAR-1 STND_YEAR, MEET_CD, TMS, DAY_ORD FROM VW_SDL_INFO
                     WHERE RACE_DAY = V_THIS_DATE) T2
                WHERE 1=1
                AND T1.STND_YEAR = T2.STND_YEAR
                AND T1.MEET_CD = T2.MEET_CD
                AND T1.MEET_CD IN ('001','003')
                AND T1.TMS = T2.TMS
                AND T1.DAY_ORD = T2.DAY_ORD
            ) 
        ) WHERE 1=(SELECT FLAG FROM TIME_CHECK )
        ;
   END IF;
    
    IF LENGTH(V_MESG) > 40 THEN
        SP_ALARM_SEND_SMS( '026', V_MESG, 'admin', 'RSM9999', 'SP_SEND_SALES_RESULT');
        SP_BTC_LOG('026','E','SP_CHK_SDL_DEAMON','���ϸ����� ERROR',V_ERR_MESG);        
        COMMIT;
    END IF;
    
    RETURN;


    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            ROLLBACK;
            RETURN;
        WHEN OTHERS THEN
            V_ERR_CODE := SQLCODE();
            V_ERR_MESG := SQLERRM;
            ROLLBACK;
            SP_BTC_LOG('026','E','SP_SEND_SALES_RESULT','ERROR CODE:'||V_ERR_CODE || ' ERROR LOG:'||V_ERR_MESG,'');
            RETURN;

END;
/
