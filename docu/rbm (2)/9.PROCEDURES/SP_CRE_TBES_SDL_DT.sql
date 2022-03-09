CREATE OR REPLACE PROCEDURE USRBM.SP_CRE_TBES_SDL_DT
IS
/******************************************************************************
- 개발자,일      : KOO.NG 2011-10-13
- 프로그램명     : SP_CRE_TBES_SDL_DT
- 프로그램타입   : procedure
- 기능           : 체류인원관리 경주생성
- IN  인수       :

- 프로세스
    1. 수, 목 경정 경주 생성
    2. 금, 토, 일 경륜 경주생성


- 프로시져는 매일 아침 ? 시에 작동되도록 한다.(스케줄러 등록)
******************************************************************************/

    v_err_code number;
    v_err_mesg varchar(255);

begin

    SP_BTC_LOG('006','I','SP_CRE_TBES_SDL_DT','START','');



    -- 1. 기존 경주 삭제


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
                 0 REFUND,
                 'BUSDT' UPDT_ID,
                 SYSDATE UPDT_DT,
                 'BUSDT' INST_ID,
                 SYSDATE INST_DT
             FROM
                 (
                     SELECT
                            RACEDATE RACE_DAY, '006' MEET_CD, RACEYY STND_YEAR, TO_NUMBER (RACETIMES) TMS,
                            TO_NUMBER (RACEDAYS) DAY_ORD, RACENO RACE_NO, '04' SELL_CD, '07' COMM_NO, SUBSTR (OUTSITE, 2, 2) DIV_NO,
                            WIN + PLACE + EXACTA + QUINELLA + TRIELLA DIV_TOTAL, WIN, PLACE,
                            EXACTA, QUINELLA, TRIELLA
                        FROM BCRC.VIEW_BOATSALES@BCRCDBLINK
                        WHERE (WIN + PLACE + EXACTA + QUINELLA + TRIELLA) > 0 AND OUTSITE <> '000'
                        UNION ALL
                        SELECT
                            RACEDATE RACE_DAY, '006' MEET_CD, RACEYY STND_YEAR, TO_NUMBER (RACETIMES) TMS,
                            TO_NUMBER (RACEDAYS) DAY_ORD, RACENO RACE_NO, '02' SELL_CD, '07' COMM_NO, SUBSTR (OUTSITE, 2, 2) DIV_NO,
                            WIN + PLACE + EXACTA + QUINELLA + TRIELLA DIV_TOTAL, WIN, PLACE,
                            EXACTA, QUINELLA, TRIELLA
                        FROM CCRC.VIEW_BOATSALES@CCRCLINK
                 ) A,
                 (
                     SELECT
                          A.STND_YEAR
                         ,A.MEET_CD
                         ,A.TMS
                         ,A.DAY_ORD
                         ,A.RACE_NO
                     FROM TBES_SDL_DT A, VW_SDL_INFO B
                     WHERE 1=1
                     AND A.SELL_CD   = '04'
                     AND A.MEET_CD   = B.MEET_CD
                     AND A.STND_YEAR = B.STND_YEAR
                     AND A.TMS       = B.TMS
                     AND A.DAY_ORD   = B.DAY_ORD
                     AND B.RACE_DAY  = TO_CHAR(SYSDATE, 'YYYYMMDD')
                     GROUP BY A.STND_YEAR, A.MEET_CD,
                              A.TMS, A.DAY_ORD, A.RACE_NO
                 ) B
             WHERE 1=1
             AND A.STND_YEAR = B.STND_YEAR
             AND A.MEET_CD = B.MEET_CD
             AND A.TMS = B.TMS
             AND A.DAY_ORD = B.DAY_ORD
             AND A.RACE_NO = B.RACE_NO
             AND A.SELL_CD = '04'
         ) SRC
         ON (
                 DEST.MEET_CD  =SRC.MEET_CD
             AND DEST.STND_YEAR=SRC.STND_YEAR
             AND DEST.TMS      =SRC.TMS
             AND DEST.DAY_ORD  =SRC.DAY_ORD
             AND DEST.RACE_NO  =SRC.RACE_NO
             AND DEST.SELL_CD  =SRC.SELL_CD
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

     COMMIT;
    SP_BTC_LOG('006','I','SP_CRE_TBES_SDL_DT','END','');
    RETURN;

    EXCEPTION
        WHEN OTHERS THEN
            v_err_code := SQLCODE();
            v_err_mesg := SQLERRM;
            ROllBACK;
            SP_BTC_LOG('006','E','SP_CRE_TBES_SDL_DT','ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'');
            RETURN;

end;
/
