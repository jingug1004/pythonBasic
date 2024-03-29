DROP VIEW USRBM.VIEW_SOSFO_ENTRANCE;

/* Formatted on 2017-03-18 오전 10:56:48 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VIEW_SOSFO_ENTRANCE
(
   MEET_NO,
   RACEYY,
   RACETIMES,
   INWON,
   CODE_NM
)
AS
     SELECT SUBSTR (R.MEET_CD, 3, 1) AS MEET_CD,
            R.STND_YEAR AS RACEYY,
            R.TMS AS RACETIMES,
            SUM (ENT_PRSN_NUM) AS INWON,
            C.CD_NM AS CODE_NM
       FROM TBRC_STAY_MANA S, TBRK_SPEC_CD C, VW_SDL_INFO R
      WHERE     S.BRNC_CD = C.CD
            AND C.GRP_CD IN ('018', '032')
            AND C.DEL_YN = 'N'
            AND S.RACE_DT = R.RACE_DAY
   GROUP BY R.MEET_CD,
            R.STND_YEAR,
            R.TMS,
            C.CD_NM;
COMMENT ON COLUMN USRBM.VIEW_SOSFO_ENTRANCE.MEET_NO IS '경주구분(1:경륜,2:경정)';

COMMENT ON COLUMN USRBM.VIEW_SOSFO_ENTRANCE.RACEYY IS '경주년도';

COMMENT ON COLUMN USRBM.VIEW_SOSFO_ENTRANCE.RACETIMES IS '회차';

COMMENT ON COLUMN USRBM.VIEW_SOSFO_ENTRANCE.INWON IS '입장인원수';

COMMENT ON COLUMN USRBM.VIEW_SOSFO_ENTRANCE.CODE_NM IS '지점명';


GRANT SELECT ON USRBM.VIEW_SOSFO_ENTRANCE TO ACCT;
