DROP VIEW USRBM.VW_SOSFO_ENTRANCE;

/* Formatted on 2017-03-18 ¿ÀÀü 11:08:56 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_SOSFO_ENTRANCE
(
   MEET_CD,
   STND_YEAR,
   TMS,
   DAY_ORD,
   INWON,
   CODE_NM
)
AS
     SELECT s.meet_cd,
            s.stnd_year,
            s.tms,
            s.day_ord,
            SUM (A.ENT_PRSN_NUM) inwon,
            c.cD_nm
       FROM TBRC_STAY_MANA A, vw_sdl_info s, TBRK_SPEC_CD c
      WHERE     A.race_dt = TO_DATE (s.race_day, 'yyyymmdd')
            AND C.GRP_CD IN ('018', '032')
            AND C.DEL_YN = 'N'
            AND c.cd = A.BRNC_CD
   GROUP BY s.meet_cd,
            s.stnd_year,
            s.tms,
            s.day_ord,
            c.CD_nm;


GRANT SELECT ON USRBM.VW_SOSFO_ENTRANCE TO ACCT;
