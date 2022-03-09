CREATE OR REPLACE PROCEDURE USRBM.SP_CHK_SDL_DEAMON

IS
/******************************************************************************
- ������,��      : ���缱, �밡�� �ڵ�
- ���α׷���     : SP_SDL_DAEMON_CHECK
- ���α׷�Ÿ��   : procedure
- ���           : SDL������ ���������� �����ϴ� ���� üũ�Ѵ�.
- IN  �μ�       :

- ���μ���
    1. ���������� ���ֺ� ���۽����� �������� 25������ SDL�ڷᰡ ����� �Ѿ�� ���� üũ�Ѵ�.
    

- ���ν����� ���� 10:45 ~ 19:00 (�����ٷ� ���)
******************************************************************************/

    V_ERR_CODE NUMBER;
    V_ERR_MESG VARCHAR(255);


BEGIN
    DBMS_OUTPUT.ENABLE;

    V_ERR_MESG := '';
    
    SELECT ERR_MESG
    INTO   V_ERR_MESG
    FROM (
          SELECT  'SDL ���� ���� (��� '||A.RACE_NO||'����)' AS ERR_MESG
          FROM   US_CRA.TBJB_TM_LEN A INNER JOIN VW_SDL_INFO B
                    ON       A.STND_YEAR = B.STND_YEAR
                        AND A.TMS          = B.TMS
                        AND A.DAY_ORD   = B.DAY_ORD
                        AND A.MEET_CD   = B.MEET_CD
                        AND B.MEET_CD   = '001'
                        AND B.RACE_DAY  = TO_CHAR(SYSDATE, 'YYYYMMDD')
                    LEFT OUTER JOIN TBES_SDL_TM C
                    ON       A.STND_YEAR = C.STND_YEAR
                        AND '001'           = C.MEET_CD
                        AND B.MEET_CD  = C.MEET_CD
                        AND A.TMS         = C.TMS
                        AND A.DAY_ORD  = C.DAY_ORD
                        AND A.RACE_NO  = C.RACE_NO
          WHERE  A.STR_TM BETWEEN TO_CHAR(SYSDATE -5/60/24, 'HH24MI') AND TO_CHAR(SYSDATE+20/60/24, 'HH24MI') -- �߸Ű��� 10�������� �߸Ű��ñ��� �ڷᰡ ���� ���
             AND  C.RACE_NO IS NULL          
         UNION ALL         
          SELECT  'SDL ���� ���� (���� '||A.RACE_NO||'����)' AS ERR_MESG
          FROM MRASYS.TBEB_RACE A INNER JOIN VW_SDL_INFO B
                  ON        A.STND_YEAR = B.STND_YEAR
                       AND A.TMS = B.TMS
                       AND A.DAY_ORD = B.DAY_ORD
                       AND B.MEET_CD = '003'
                       AND B.RACE_DAY = TO_CHAR(SYSDATE, 'YYYYMMDD')
                  LEFT OUTER JOIN TBES_SDL_TM C
                  ON       A.STND_YEAR = C.STND_YEAR
                      AND '003' = C.MEET_CD
                      AND A.TMS = C.TMS
                      AND A.DAY_ORD = C.DAY_ORD
                      AND A.RACE_NO = C.RACE_NO
          WHERE  A.STRT_TIME BETWEEN TO_CHAR(SYSDATE -5/60/24, 'HH24MI') AND TO_CHAR(SYSDATE+20/60/24, 'HH24MI') -- �߸Ű��� 15�������� �߸Ű��ñ��� �ڷᰡ ���� ���
              AND C.RACE_NO IS NULL        
         );    


    IF V_ERR_MESG  <> '' THEN
        SP_ALARM_SEND_SMS( '011', V_ERR_MESG, 'admin', 'RSY6010', 'SP_CHK_SDL_DEAMON');
        SP_BTC_LOG('011','E','SP_CHK_SDL_DEAMON','SDL���� ERROR',V_ERR_MESG);        
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
            SP_BTC_LOG('011','E','SP_CHK_SDL_DEAMON','ERROR CODE:'||V_ERR_CODE || ' ERROR LOG:'||V_ERR_MESG,'');
            RETURN;

END;
/
