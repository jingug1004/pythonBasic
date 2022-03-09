DROP VIEW USRBM.VW_PARTS;

/* Formatted on 2017-03-18 ¿ÀÀü 11:00:08 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_PARTS (DT, PARTS_CD, IN_CNT)
AS
     SELECT TO_CHAR (dt, 'yyyymmdd') AS dt, parts_cd, SUM (in_cnt) AS in_cnt
       FROM tmp_parts
      WHERE in_cnt > 0
   GROUP BY TO_CHAR (dt, 'yyyymmdd'), parts_cd;
