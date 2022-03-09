DROP VIEW USRBM.VW_PC_SELLST;

/* Formatted on 2017-03-18 오전 11:04:11 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_PC_SELLST
(
   MEET_CD,
   STND_YEAR,
   TMS,
   DAY_ORD,
   SELL_CD,
   COMM_NO,
   DIV_NO,
   RACE_NO,
   SESSION_NO,
   PERF_NO,
   COUNT1,
   AMOUNT1,
   COUNT2,
   AMOUNT2,
   COUNT3,
   AMOUNT3,
   COUNT4,
   AMOUNT4,
   COUNT5,
   AMOUNT5,
   COUNT6,
   AMOUNT6,
   COUNT7,
   AMOUNT7,
   COUNT8,
   AMOUNT8,
   INST_DT,
   UPDT_DT,
   GREEN_YN
)
AS
   (SELECT B.MEET_CD,
           B.STND_YEAR,
           B.TMS,
           B.DAY_ORD,
           B.SELL_CD,
           B.COMM_NO,
           B.DIV_NO,
           B.RACE_NO,
           B.SESSION_NO,
           B.PERF_NO,
           B.COUNT1,
           B.AMOUNT1,
           B.COUNT2,
           B.AMOUNT2,
           B.COUNT3,
           B.AMOUNT3,
           B.COUNT4,
           B.AMOUNT4,
           B.COUNT5,
           B.AMOUNT5,
           B.COUNT6,
           B.AMOUNT6,
           B.COUNT7,
           B.AMOUNT7,
           B.COUNT8,
           B.AMOUNT8,
           B.INST_DT,
           NVL (B.UPDT_DT, B.INST_DT),
           CASE WHEN COMM_NO = '06' THEN 'Y' ELSE 'N' END AS GREEN_YN
      FROM TBJI_PC_FILE_VERI A, TBJI_PC_SELLST B
     WHERE     1 = 1
           AND A.MEET_CD = B.MEET_CD
           AND A.STND_YEAR = B.STND_YEAR
           AND A.TMS = B.TMS
           AND A.DAY_ORD = B.DAY_ORD
           AND A.FILE_VERI = '001'
           AND A.VERI = '001');
COMMENT ON TABLE USRBM.VW_PC_SELLST IS '금액 구간별매출액';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.MEET_CD IS '경륜장코드';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.STND_YEAR IS '기준년도';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.TMS IS '회차';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.DAY_ORD IS '일차';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.SELL_CD IS '시행처';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.COMM_NO IS '투표소';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.DIV_NO IS '발매지점';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.RACE_NO IS '경주번호';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.SESSION_NO IS '세션번호';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.PERF_NO IS '퍼포먼스번호';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.COUNT1 IS '1구간매수';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.AMOUNT1 IS '1구간금액';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.COUNT2 IS '2구간매수';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.AMOUNT2 IS '2구간금액';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.COUNT3 IS '3구간매수';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.AMOUNT3 IS '3구간금액';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.COUNT4 IS '4구간매수';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.AMOUNT4 IS '4구간금액';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.COUNT5 IS '5구간매수';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.AMOUNT5 IS '5구간금액';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.COUNT6 IS '6구간매수';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.AMOUNT6 IS '6구간금액';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.COUNT7 IS '7구간매수';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.AMOUNT7 IS '7구간금액';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.COUNT8 IS '총매수';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.AMOUNT8 IS '총금액';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.INST_DT IS '작성일시';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.UPDT_DT IS '수정일시';

COMMENT ON COLUMN USRBM.VW_PC_SELLST.GREEN_YN IS '그린카드 여부';


GRANT SELECT ON USRBM.VW_PC_SELLST TO ACCT;

GRANT SELECT ON USRBM.VW_PC_SELLST TO INTRA;

GRANT SELECT ON USRBM.VW_PC_SELLST TO VWRBM;
