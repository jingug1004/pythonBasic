DROP VIEW USRBM.VW_PC_PAYOFFS2;

/* Formatted on 2017-03-18 오전 11:02:56 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_PC_PAYOFFS2
(
   MEET_CD,
   STND_YEAR,
   TMS,
   DAY_ORD,
   SELL_CD,
   RACE_NO,
   POOL_CD,
   NET_AMT,
   REFUND,
   COMMI_1,
   COMMI_2,
   COMMI_3,
   COMMI_4,
   COMMI_5,
   COMMI_6,
   NEGA_BREAK,
   POSI_BREAK,
   CNCL_AMT,
   INST_DT,
   UPDT_DT,
   SESSION_NO,
   PERF_NO,
   COMM_NO,
   DIV_NO
)
AS
   (SELECT B.MEET_CD,
           B.STND_YEAR,
           B.TMS,
           B.DAY_ORD,
           B.SELL_CD,
           B.RACE_NO,
           B.POOL_CD,
           B.NET_AMT,
           B.REFUND,
           B.COMMI_1,
           B.COMMI_2,
           B.COMMI_3,
           B.COMMI_4,
           B.COMMI_5,
           B.COMMI_6,
           B.NEGA_BREAK,
           B.POSI_BREAK,
           B.CNCL_AMT,
           B.INST_DT,
           NVL (B.UPDT_DT, B.INST_DT) UPDT_DT,
           NVL (TRIM (B.SESSION_NO), 0) SESSION_NO,
           NVL (TRIM (B.PERF_NO), 0) PERF_NO,
           SELL_CD AS SELL_CD,
           SELL_CD AS DIV_NO
      FROM TBJI_PC_FILE_VERI A, TBJI_PC_PAYOFFS B
     WHERE     1 = 1
           AND B.MEET_CD = A.MEET_CD
           AND B.STND_YEAR = A.STND_YEAR
           AND B.TMS = A.TMS
           AND B.DAY_ORD = A.DAY_ORD
           AND A.FILE_VERI = '001'
           AND A.VERI = '001');
COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.NEGA_BREAK IS '충당금';

COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.POSI_BREAK IS '절사금';

COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.CNCL_AMT IS '취소금액';

COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.INST_DT IS '작성일시';

COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.UPDT_DT IS '수정일시';

COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.SESSION_NO IS '세션번호';

COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.PERF_NO IS '퍼포먼스번호';

COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.MEET_CD IS '경륜장코드';

COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.STND_YEAR IS '기준년도';

COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.TMS IS '회차';

COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.DAY_ORD IS '일차';

COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.SELL_CD IS '운영처';

COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.RACE_NO IS '경주번호';

COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.POOL_CD IS '승식';

COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.NET_AMT IS '순매출액';

COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.REFUND IS '환불금액';

COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.COMMI_1 IS '수득금';

COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.COMMI_2 IS '경주세';

COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.COMMI_3 IS '교육세';

COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.COMMI_4 IS '농특세';

COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.COMMI_5 IS '기타소득세';

COMMENT ON COLUMN USRBM.VW_PC_PAYOFFS2.COMMI_6 IS '기타주민세';
