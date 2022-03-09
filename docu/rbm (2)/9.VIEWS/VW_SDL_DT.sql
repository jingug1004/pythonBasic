DROP VIEW USRBM.VW_SDL_DT;

/* Formatted on 2017-03-18 오전 11:06:14 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_SDL_DT
(
   MEET_CD,
   STND_YEAR,
   TMS,
   DAY_ORD,
   RACE_NO,
   SELL_CD,
   COMM_NO,
   COMM_NO_SHARE,
   DIV_NO,
   DIV_TOTAL,
   REFUND,
   INST_DT,
   UPDT_DT
)
AS
   (SELECT B.MEET_CD,
           B.STND_YEAR,
           B.TMS,
           B.DAY_ORD,
           B.RACE_NO,
           B.SELL_CD,
           B.COMM_NO,
           CASE
              WHEN B.COMM_NO < '11'
                   AND B.MEET_CD || B.SELL_CD IN ('00103', '00301')
              THEN
                 '99'
              ELSE
                 B.COMM_NO
           END
              AS COMM_NO_SHARE,
           B.DIV_NO,
           B.DIV_TOTAL,
           B.REFUND,
           B.INST_DT,
           NVL (B.UPDT_DT, B.INST_DT)
      FROM TBJI_PC_FILE_VERI A, TBES_SDL_DT B
     WHERE     1 = 1
           AND A.FILE_VERI = '001'
           AND A.VERI = '001'
           AND A.STND_YEAR = B.STND_YEAR
           AND A.MEET_CD = B.MEET_CD
           AND A.TMS = B.TMS
           AND A.DAY_ORD = B.DAY_ORD);
COMMENT ON TABLE USRBM.VW_SDL_DT IS '실시간 지점별 매출액';

COMMENT ON COLUMN USRBM.VW_SDL_DT.MEET_CD IS 'Meet Code';

COMMENT ON COLUMN USRBM.VW_SDL_DT.STND_YEAR IS '기준년도';

COMMENT ON COLUMN USRBM.VW_SDL_DT.TMS IS '회차';

COMMENT ON COLUMN USRBM.VW_SDL_DT.DAY_ORD IS '일차';

COMMENT ON COLUMN USRBM.VW_SDL_DT.RACE_NO IS '경주번호';

COMMENT ON COLUMN USRBM.VW_SDL_DT.SELL_CD IS '판매처 코드';

COMMENT ON COLUMN USRBM.VW_SDL_DT.COMM_NO IS 'Community번호';

COMMENT ON COLUMN USRBM.VW_SDL_DT.DIV_NO IS 'Division번호';

COMMENT ON COLUMN USRBM.VW_SDL_DT.DIV_TOTAL IS 'Division Total Amount';

COMMENT ON COLUMN USRBM.VW_SDL_DT.REFUND IS '환불액';

COMMENT ON COLUMN USRBM.VW_SDL_DT.INST_DT IS '작성일시';

COMMENT ON COLUMN USRBM.VW_SDL_DT.UPDT_DT IS '수정일시';


GRANT SELECT ON USRBM.VW_SDL_DT TO ACCT;

GRANT SELECT ON USRBM.VW_SDL_DT TO BCRC;

GRANT SELECT ON USRBM.VW_SDL_DT TO CCRC;

GRANT SELECT ON USRBM.VW_SDL_DT TO INTRA;

GRANT SELECT ON USRBM.VW_SDL_DT TO MRADEV;

GRANT SELECT ON USRBM.VW_SDL_DT TO MRASYS;

GRANT SELECT ON USRBM.VW_SDL_DT TO VWRBM;
