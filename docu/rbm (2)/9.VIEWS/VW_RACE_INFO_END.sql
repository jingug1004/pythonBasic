DROP VIEW USRBM.VW_RACE_INFO_END;

/* Formatted on 2017-03-18 오전 11:05:27 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_RACE_INFO_END
(
   MEET_CD,
   STND_YEAR,
   TMS,
   DAY_ORD,
   RACE_NO,
   RACE_DAY,
   STR_TM,
   END_TM
)
AS
   (SELECT DECODE (A.MEET_CD, '003', '003', '001') AS MEET_CD,
           A.STND_YEAR,
           A.TMS,
           A.DAY_ORD,
           ROW_NUMBER () OVER (PARTITION BY A.RACE_DAY ORDER BY A.END_TM)
              AS RACE_NO,
           A.RACE_DAY,
           NVL (B.END_TM, '1100') AS STR_TM,
           A.END_TM
      FROM (  SELECT MEET_CD,
                     STND_YEAR,
                     TMS,
                     DAY_ORD,
                     RACE_NO,
                     RACE_DAY,
                     STR_TM AS END_TM,
                     ROW_NUMBER ()
                        OVER (PARTITION BY RACE_DAY
                              ORDER BY MEET_CD DESC, RACE_NO)
                        AS RNUM,
                     ROW_NUMBER ()
                        OVER (PARTITION BY RACE_DAY
                              ORDER BY MEET_CD DESC, RACE_NO)
                     + 1
                        AS RNUM2
                FROM USRBM.VW_RACE_INFO
               WHERE RACE_DAY > '20130100'
            ORDER BY RACE_DAY, MEET_CD DESC, RACE_NO) A,
           (  SELECT MEET_CD,
                     STND_YEAR,
                     TMS,
                     DAY_ORD,
                     RACE_NO,
                     RACE_DAY,
                     STR_TM AS END_TM,
                     ROW_NUMBER ()
                        OVER (PARTITION BY RACE_DAY
                              ORDER BY MEET_CD DESC, RACE_NO)
                        AS RNUM,
                     ROW_NUMBER ()
                        OVER (PARTITION BY RACE_DAY
                              ORDER BY MEET_CD DESC, RACE_NO)
                     + 1
                        AS RNUM2
                FROM USRBM.VW_RACE_INFO
               WHERE RACE_DAY > '20130100'
            ORDER BY RACE_DAY, MEET_CD DESC, RACE_NO) B
     WHERE A.RACE_DAY = B.RACE_DAY(+) AND A.RNUM = B.RNUM2(+)
    UNION ALL
      SELECT DECODE (MAX (MEET_CD), '003', '003', '001'),
             STND_YEAR,
             TO_CHAR (MAX (TMS)),
             TO_CHAR (DAY_ORD),
             0 AS RACE_NO,
             RACE_DAY,
             '0900' AS STR_TM,
             '1100' AS END_TM
        FROM VW_SDL_INFO
       WHERE RACE_DAY > '20130100'
    GROUP BY STND_YEAR, DAY_ORD, RACE_DAY);
COMMENT ON TABLE USRBM.VW_RACE_INFO_END IS '경주 시작종료시간';

COMMENT ON COLUMN USRBM.VW_RACE_INFO_END.MEET_CD IS '시행처코드';

COMMENT ON COLUMN USRBM.VW_RACE_INFO_END.STND_YEAR IS '기준년도';

COMMENT ON COLUMN USRBM.VW_RACE_INFO_END.TMS IS '회차';

COMMENT ON COLUMN USRBM.VW_RACE_INFO_END.DAY_ORD IS '일차';

COMMENT ON COLUMN USRBM.VW_RACE_INFO_END.RACE_NO IS '경주수';

COMMENT ON COLUMN USRBM.VW_RACE_INFO_END.RACE_DAY IS '경주일자';

COMMENT ON COLUMN USRBM.VW_RACE_INFO_END.STR_TM IS '시작시간';

COMMENT ON COLUMN USRBM.VW_RACE_INFO_END.END_TM IS '종료시간';
