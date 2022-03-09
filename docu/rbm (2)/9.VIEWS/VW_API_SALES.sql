DROP VIEW USRBM.VW_API_SALES;

/* Formatted on 2017-03-18 오전 10:57:16 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_API_SALES
(
   "년도",
   "경주",
   "경기월",
   "매출총계",
   "본장",
   "수원",
   "중랑",
   "일산",
   "동대문",
   "분당",
   "장안",
   "산본",
   "부천",
   "관악",
   "성북",
   "영등포",
   "대전",
   "인천",
   "시흥",
   "강남",
   "천안",
   "올림픽",
   "의정부",
   "공동활용본장",
   "그린카드",
   "교차"
)
AS
     SELECT STND_YEAR 년도,
            DECODE (meet_cd, '001', '경륜', '003', '경정') 경주,
            SUBSTR (RACE_DAY, 5, 2) AS 경기월,
            SUM (DIV_TOTAL) 매출총계,
            SUM(CASE
                   WHEN MEET_CD || SELL_CD IN
                              ('00101', '00201', '00303', '00401')
                        AND COMM_NO IN ('01', '02', '03', '04', '08')
                   THEN
                      DIV_TOTAL
                END)
               본장,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '11' THEN DIV_TOTAL END)
               수원,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '12' THEN DIV_TOTAL END)
               중랑,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '13' THEN DIV_TOTAL END)
               일산,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '15' THEN DIV_TOTAL END)
               동대문,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '14' THEN DIV_TOTAL END)
               분당,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '16' THEN DIV_TOTAL END)
               장안,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '17' THEN DIV_TOTAL END)
               산본,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '18' THEN DIV_TOTAL END)
               부천,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '19' THEN DIV_TOTAL END)
               관악,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '20' THEN DIV_TOTAL END)
               성북,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '21' THEN DIV_TOTAL END)
               영등포,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '22' THEN DIV_TOTAL END)
               대전,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '23' THEN DIV_TOTAL END)
               인천,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '24' THEN DIV_TOTAL END)
               시흥,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '25' THEN DIV_TOTAL END)
               강남,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '26' THEN DIV_TOTAL END)
               천안,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '27' THEN DIV_TOTAL END)
               올림픽,
            SUM (
               CASE WHEN SELL_CD = '01' AND COMM_NO = '28' THEN DIV_TOTAL END)
               의정부,
            SUM(CASE
                   WHEN MEET_CD || SELL_CD IN
                              ('00103', '00203', '00301', '00403')
                        AND COMM_NO IN ('01', '02', '03', '04', '08')
                   THEN
                      DIV_TOTAL
                END)
               공동활용본장,
            SUM(CASE
                   WHEN SELL_CD IN ('01', '03') AND COMM_NO = '06'
                   THEN
                      DIV_TOTAL
                END)
               그린카드,
            SUM (CASE WHEN SELL_CD NOT IN ('01', '03') THEN DIV_TOTAL END)
               교차
       FROM (  SELECT B.MEET_CD,
                      B.STND_YEAR,
                      B.RACE_DAY,
                      SUM (A.DIV_TOTAL) DIV_TOTAL,
                      A.SELL_CD,
                      A.COMM_NO,
                      A.DIV_NO
                 FROM VW_SDL_INFO B,                                -- 경주일 정보,
                                    VW_SDL_DT_SUM A                      -- 지점
                WHERE     1 = 1
                      AND B.MEET_CD IN ('001', '003')
                      AND A.MEET_CD = B.MEET_CD
                      AND A.STND_YEAR = B.STND_YEAR
                      AND A.TMS = B.TMS
                      AND A.DAY_ORD = B.DAY_ORD
                      AND A.SELL_CD IN ('01', '03')
             GROUP BY B.MEET_CD,
                      B.STND_YEAR,
                      B.RACE_DAY,
                      5,
                      2,
                      A.SELL_CD,
                      A.COMM_NO,
                      A.DIV_NO
             UNION ALL
               SELECT B.MEET_CD,
                      B.STND_YEAR,
                      B.RACE_DAY,
                      SUM (A.NET_AMT) DIV_TOTAL,
                      A.SELL_CD,
                      A.SELL_CD AS COMM_NO,
                      A.SELL_CD AS DIV_NO
                 FROM VW_PC_PAYOFFS2 A,                                     --
                                       VW_SDL_INFO B                 -- 경주일 정보
                WHERE     1 = 1
                      AND B.MEET_CD IN ('001', '003')
                      AND A.SELL_CD NOT IN ('01', '03')
                      AND A.MEET_CD = B.MEET_CD
                      AND A.STND_YEAR = B.STND_YEAR
                      AND A.TMS = B.TMS
                      AND A.DAY_ORD = B.DAY_ORD
             GROUP BY B.MEET_CD,
                      B.STND_YEAR,
                      B.RACE_DAY,
                      5,
                      2,
                      A.SELL_CD,
                      A.COMM_NO,
                      A.DIV_NO
             ORDER BY 1, 2, 3)
   GROUP BY MEET_CD, STND_YEAR, SUBSTR (RACE_DAY, 5, 2)
   ORDER BY MEET_CD, STND_YEAR, SUBSTR (RACE_DAY, 5, 2);


GRANT SELECT ON USRBM.VW_API_SALES TO ACCT;
