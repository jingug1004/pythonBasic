DROP VIEW USRBM.VW_SDL_RS;

/* Formatted on 2017-03-18 ¿ÀÀü 11:07:53 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_SDL_RS
(
   MEET_CD,
   STND_YEAR,
   TMS,
   DAY_ORD,
   RACE_NO,
   POOL_CD,
   RS_SEQ,
   RUNNER_1ST,
   RUNNER_2ND,
   RUNNER_3RD,
   PAYOFF,
   STATUS,
   INST_DT,
   UPDT_DT
)
AS
   (SELECT MEET_CD,
           STND_YEAR,
           TMS,
           DAY_ORD,
           RACE_NO,
           POOL_CD,
           RS_SEQ,
           RUNNER_1ST,
           RUNNER_2ND,
           RUNNER_3RD,
           PAYOFF,
           STATUS,
           INST_DT,
           NVL (UPDT_DT, INST_DT)
      FROM TBES_SDL_RS);


GRANT SELECT ON USRBM.VW_SDL_RS TO ACCT;
