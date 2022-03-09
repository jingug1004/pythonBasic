CREATE OR REPLACE PROCEDURE USRBM.SP_CHK_SDL_DEAMON

IS
/******************************************************************************
- 개발자,일      : 신재선, 노가다 코딩
- 프로그램명     : SP_SDL_DAEMON_CHECK
- 프로그램타입   : procedure
- 기능           : SDL데몬이 정상적으로 동작하는 지는 체크한다.
- IN  인수       :

- 프로세스
    1. 경주일정의 경주별 시작시작을 기준으로 25분전에 SDL자료가 제대로 넘어가는 지를 체크한다.
    

- 프로시져는 매일 10:45 ~ 19:00 (스케줄러 등록)
******************************************************************************/

    V_ERR_CODE NUMBER;
    V_ERR_MESG VARCHAR(255);


BEGIN
    DBMS_OUTPUT.ENABLE;

    V_ERR_MESG := '';
    
    SELECT ERR_MESG
    INTO   V_ERR_MESG
    FROM (
          SELECT  'SDL 데몬 오류 (경륜 '||A.RACE_NO||'경주)' AS ERR_MESG
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
          WHERE  A.STR_TM BETWEEN TO_CHAR(SYSDATE -5/60/24, 'HH24MI') AND TO_CHAR(SYSDATE+20/60/24, 'HH24MI') -- 발매개시 10분전에서 발매개시까지 자료가 없는 경우
             AND  C.RACE_NO IS NULL          
         UNION ALL         
          SELECT  'SDL 데몬 오류 (경정 '||A.RACE_NO||'경주)' AS ERR_MESG
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
          WHERE  A.STRT_TIME BETWEEN TO_CHAR(SYSDATE -5/60/24, 'HH24MI') AND TO_CHAR(SYSDATE+20/60/24, 'HH24MI') -- 발매개시 15분전에서 발매개시까지 자료가 없는 경우
              AND C.RACE_NO IS NULL        
         );    


    IF V_ERR_MESG  <> '' THEN
        SP_ALARM_SEND_SMS( '011', V_ERR_MESG, 'admin', 'RSY6010', 'SP_CHK_SDL_DEAMON');
        SP_BTC_LOG('011','E','SP_CHK_SDL_DEAMON','SDL데몬 ERROR',V_ERR_MESG);        
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
