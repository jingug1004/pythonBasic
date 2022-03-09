DROP VIEW USRBM.VW_RACE_INFO_END2;

/* Formatted on 2017-03-18 오전 11:05:38 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_RACE_INFO_END2
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
           CASE WHEN C.MAX_RACE_NO = A.RACE_NO THEN '2200' ELSE A.END_TM END
              END_TM
      FROM (  SELECT MEET_CD,
                     STND_YEAR,
                     TMS,
                     DAY_ORD,
                     RACE_NO,
                     RACE_DAY,
                     STR_TM AS END_TM,
                     ROW_NUMBER ()
                        OVER (PARTITION BY RACE_DAY --ORDER BY MEET_CD DESC, RACE_NO
                                                   ORDER BY STR_TM -- 한일전 교차 중복 문제
                                                                  )
                        AS RNUM,
                     ROW_NUMBER ()
                        OVER (PARTITION BY RACE_DAY --ORDER BY MEET_CD DESC, RACE_NO
                                                   ORDER BY STR_TM -- 한일전 교차 중복 문제
                                                                  )
                     + 1
                        AS RNUM2
                FROM USRBM.VW_RACE_INFO
               WHERE RACE_DAY > '20130100'
            ORDER BY RACE_DAY,
                     STR_TM,
                     MEET_CD DESC,
                     RACE_NO) A,
           (  SELECT MEET_CD,
                     STND_YEAR,
                     TMS,
                     DAY_ORD,
                     RACE_NO,
                     RACE_DAY,
                     STR_TM AS END_TM,
                     ROW_NUMBER ()
                        OVER (PARTITION BY RACE_DAY --ORDER BY MEET_CD DESC, RACE_NO
                                                   ORDER BY STR_TM -- 한일전 교차 중복 문제
                                                                  )
                        AS RNUM,
                     ROW_NUMBER ()
                        OVER (PARTITION BY RACE_DAY --ORDER BY MEET_CD DESC, RACE_NO
                                                   ORDER BY STR_TM -- 한일전 교차 중복 문제
                                                                  )
                     + 1
                        AS RNUM2
                FROM USRBM.VW_RACE_INFO
               WHERE RACE_DAY > '20130100'
            ORDER BY RACE_DAY,
                     STR_TM,
                     MEET_CD DESC,
                     RACE_NO) B,
           (  SELECT RACE_DAY, MAX (RACE_NO) AS MAX_RACE_NO
                FROM USRBM.VW_RACE_INFO
               WHERE RACE_DAY > '20130100'
            GROUP BY RACE_DAY) C
     WHERE     A.RACE_DAY = B.RACE_DAY(+)
           AND A.RNUM = B.RNUM2(+)
           AND A.RACE_DAY = C.RACE_DAY
    UNION ALL
      SELECT DECODE (MAX (MEET_CD), '003', '003', '001'),
             STND_YEAR,
             TO_CHAR (MAX (TMS)),
             TO_CHAR (DAY_ORD),
             0 AS RACE_NO,
             RACE_DAY,
             '0800' AS STR_TM,
             '1100' AS END_TM
        FROM VW_SDL_INFO
       WHERE RACE_DAY > '20130100'
    GROUP BY STND_YEAR, DAY_ORD, RACE_DAY);
COMMENT ON TABLE USRBM.VW_RACE_INFO_END2 IS '경주 시작종료시간';

COMMENT ON COLUMN USRBM.VW_RACE_INFO_END2.MEET_CD IS '시행처코드';

COMMENT ON COLUMN USRBM.VW_RACE_INFO_END2.STND_YEAR IS '기준년도';

COMMENT ON COLUMN USRBM.VW_RACE_INFO_END2.TMS IS '회차';

COMMENT ON COLUMN USRBM.VW_RACE_INFO_END2.DAY_ORD IS '일차';

COMMENT ON COLUMN USRBM.VW_RACE_INFO_END2.RACE_NO IS '경주수';

COMMENT ON COLUMN USRBM.VW_RACE_INFO_END2.RACE_DAY IS '경주일자';

COMMENT ON COLUMN USRBM.VW_RACE_INFO_END2.STR_TM IS '시작시간';

COMMENT ON COLUMN USRBM.VW_RACE_INFO_END2.END_TM IS '종료시간';
