DROP VIEW USRBM.VW_PARTS_OUT;

/* Formatted on 2017-03-18 ¿ÀÀü 11:00:20 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_PARTS_OUT
(
   PARTS_CD,
   DT,
   BRNC_CD,
   OUT_CNT,
   ACPTER_ID,
   RCVER_ID
)
AS
     SELECT parts_cd,
            TO_CHAR (dt, 'yyyymmdd') AS dt,
            brnc_cd,
            SUM (out_cnt) out_cnt,
            MIN (acpter_id) acpter_id,
            MIN (rcver_id) rcver_id
       FROM tmp_parts
      WHERE out_cnt > 0
   GROUP BY parts_cd, dt, brnc_cd;
