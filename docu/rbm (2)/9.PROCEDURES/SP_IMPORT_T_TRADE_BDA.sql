CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_T_TRADE_BDA IS
tmpVar NUMBER;
/******************************************************************************
   NAME:       SP_IMPORT_T_TRADE_BDA
   PURPOSE:    

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0        2016/04/07   ijkim       1. Created this procedure.

   NOTES:

   Automatically available Auto Replace Keywords:
      Object Name:     SP_IMPORT_T_TRADE_BDA
      Sysdate:         2016/04/07
      Date and Time:   2016/04/07, 오후 5:13:06, and 2016/04/07 오후 5:13:06
      Username:        ijkim (set in TOAD Options, Procedure Editor)
      Table Name:       입장인원 분석을 위한 raw 자료 생성

******************************************************************************/
BEGIN
   tmpVar := 0;

--일별 지점별 자료 
    INSERT INTO TBRC_T_TRADE_BDA_RAW
    (
    SELECT comm_no, substr(min(trade_date), 1,8) enter_dt,  substr(min(trade_date), 9,4) enter_tm, min(request_fee) fee, card_id 
    FROM TBRC_T_TRADE T
    WHERE trade_date > to_char(sysdate -10, 'YYYYMMDDHH24MI')
     AND card_user_type = '4'
     AND NOT EXISTS (
                      SELECT * FROM TBRC_T_TRADE_BDA_RAW dba_raw 
                      WHERE dba_raw.comm_no = T.COMM_NO
                        AND dba_raw.enter_dt = substr(T.trade_date, 1,8)
                        AND dba_raw.card_id = T.CARD_ID
                    )
    GROUP BY comm_no, substr(trade_date,1,8), card_id, substr(trade_date,1,8)
    );

--월별 지점별  성향 분석

    DELETE FROM TBRC_T_TRADE_BDA_MONTH WHERE YEARMM = to_char(sysdate,'YYYYMM');

    INSERT INTO TBRC_T_TRADE_BDA_MONTH
    SELECT substr(enter_dt,1,6) yearmm, comm_no, meet_cd, card_id    
        , count(*) entr_cnt
        , min(enter_dt) first_enter 
        , max(enter_dt) last_enter  
        --요일     
        ,sum(CASE WHEN to_char(to_date(enter_dt,'YYYYMMDD'),'D') = '1' THEN 1 END) AS sun_cnt
        ,sum(CASE WHEN to_char(to_date(enter_dt,'YYYYMMDD'),'D') = '2' THEN 1 END) AS mon_cnt
        ,sum(CASE WHEN to_char(to_date(enter_dt,'YYYYMMDD'),'D') = '3' THEN 1 END) AS tue_cnt    
        ,sum(CASE WHEN to_char(to_date(enter_dt,'YYYYMMDD'),'D') = '4' THEN 1 END) AS wed_cnt    
        ,sum(CASE WHEN to_char(to_date(enter_dt,'YYYYMMDD'),'D') = '5' THEN 1 END) AS thr_cnt    
        ,sum(CASE WHEN to_char(to_date(enter_dt,'YYYYMMDD'),'D') = '6' THEN 1 END) AS fri_cnt    
        ,sum(CASE WHEN to_char(to_date(enter_dt,'YYYYMMDD'),'D') = '7' THEN 1 END) AS sat_cnt
        --시간
        ,sum(CASE WHEN enter_tm <  '1100' THEN 1 END) AS T10_cnt
        ,sum(CASE WHEN enter_tm >= '1100' AND enter_tm <  '1200' THEN 1 END) AS T11_cnt
        ,sum(CASE WHEN enter_tm >= '1200' AND enter_tm <  '1300' THEN 1 END) AS T12_cnt                        
        ,sum(CASE WHEN enter_tm >= '1300' AND enter_tm <  '1400' THEN 1 END) AS T13_cnt                        
        ,sum(CASE WHEN enter_tm >= '1400' AND enter_tm <  '1500' THEN 1 END) AS T14_cnt                        
        ,sum(CASE WHEN enter_tm >= '1500' AND enter_tm <  '1600' THEN 1 END) AS T15_cnt                        
        ,sum(CASE WHEN enter_tm >= '1600' AND enter_tm <  '1700' THEN 1 END) AS T16_cnt                            
        ,sum(CASE WHEN enter_tm >= '1700' AND enter_tm <  '1800' THEN 1 END) AS T17_cnt                            
        ,sum(CASE WHEN enter_tm >= '1800' AND enter_tm <  '1900' THEN 1 END) AS T18_cnt                            
        ,sum(CASE WHEN enter_tm >= '1900' AND enter_tm <  '2000' THEN 1 END) AS T19_cnt
        ,sum(CASE WHEN enter_tm >= '2000' THEN 1 END) AS T20_cnt    
    FROM TBRC_T_TRADE_BDA_RAW R RIGHT OUTER JOIN  VW_SDL_INFO I ON MEET_CD IN('001','003') AND I.RACE_DAY = R.ENTER_DT
    WHERE enter_dt LIKE to_char(sysdate,'YYYYMM')||'%'   
    --  AND card_id LIKE 'XnCyy12Ag/CA6VF1I2e6oXt0gRQjrhv63a8XDDiUqL0=%'
     GROUP BY substr(enter_dt,1,6), comm_no, meet_cd, card_id; 
 
-- 카드별 경주별 최초입장 마지막 입장
    MERGE INTO TBRC_T_TRADE_BDA_CARD_ENTER T
    using (
        SELECT card_id, meet_cd, 
            max(decode(first_rank,1,first_enter)) first_enter,
            max(decode(first_rank,1,comm_no)) first_comm_no,
            max(decode(last_rank,1,last_enter)) last_enter,
            max(decode(last_rank,1,comm_no)) last_comm_no,    
            sysdate    
        FROM (
             SELECT yearmm, card_id, meet_cd, first_enter, comm_no, last_enter
            , rank() OVER (PARTITION BY card_id, meet_cd ORDER BY first_enter) first_rank
            , rank() OVER (PARTITION BY card_id, meet_cd ORDER BY last_enter desc) last_rank 
            FROM TBRC_T_TRADE_BDA_MONTH M
            WHERE YEARMM = to_char(sysdate,'YYYYMM')
            ORDER BY meet_cd           
            )  A
        GROUP BY card_ID, meet_cd    
    ) S
    ON (T.CARD_ID = S.CARD_ID AND T.MEET_CD = S.MEET_CD) 
    WHEN MATCHED THEN 
        UPDATE SET LAST_ENTER = S.last_enter, last_comm_no =S.last_comm_no, updt_dt = sysdate 
    WHEN NOT MATCHED THEN 
        INSERT ( card_id, meet_cd, first_enter, first_comm_no, last_enter, last_comm_no, updt_dt)
        VALUES (s.card_id, s.meet_cd, s.first_enter, s.first_comm_no, s.last_enter, s.last_comm_no, sysdate);

    
    COMMIT;

   EXCEPTION
     WHEN NO_DATA_FOUND THEN
       NULL;
     WHEN OTHERS THEN
       -- Consider logging the error and then re-raise
       RAISE;
END SP_IMPORT_T_TRADE_BDA;
/
