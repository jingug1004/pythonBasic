CREATE OR REPLACE PROCEDURE USRBM.SP_BTC_LOG (
      P_BTC_CD     IN     VARCHAR2
    , P_LOG_CD     IN     VARCHAR2
    , P_PRONAM     IN     VARCHAR2
    , P_LOG_DESC   IN     VARCHAR2
    , P_WORK_DESC  IN     VARCHAR2 )
AS

/*******************************************************************************
  - PROCEDURE NAME : SP_BTC_LOG
  - DESCRIPTION    : LOG 등록
  - REFERENCE      : TABLE(WRITE = SP_BTC_LOG)
                     SEQUENCE(READ = SQ_TBRK_BTC_LOG)
  - REFERENCED BY  : 모든 DB 배치작업 및 JAVA인터페이스 배치작업
  - IN PARAMETER   : P_BTC_CD     배치구분 (공통코드 : 045)

                     P_LOG_CD     로그구분 (공통코드 : 046 - I:정보, W:경고, E:에러)
                     P_PRONAM     프로그램 (호출프로그램- JAVA CLASS NAME OR SP NAME)
                     P_LOG_DESC   로그내용 (진행단계, 작업내역 혹은 에러 텍스트)
                     P_WORK_DESC  작업내용 (작업요약)
  - MADE BY        : KOO,NAMGON
   ------------------------------------------------------------------------------
  - MODIFICATION   : (DATE, WHO, MODIFIED CONTENTS)
  - COMMENT        :

*******************************************************************************/


  V_DATE        VARCHAR2(8)      DEFAULT TO_CHAR(SYSDATE, 'YYYYMMDD');
  V_TIME        VARCHAR2(9)      DEFAULT TO_CHAR(SYSTIMESTAMP , 'HH24MISSFF3') ;

BEGIN


  --3) 로그 생성
  INSERT INTO TBRK_BTC_LOG
            ( BTC_CD     ,LOG_DT    ,LOG_TM     ,LOG_SEQ  , LOG_SERL_NO,  LOG_CD
             ,PROGRAM    ,LOG_DESC  ,WORK_DESC
 )
       VALUES
            ( P_BTC_CD, V_DATE, V_TIME, SQ_TBRK_BTC_LOG.NEXTVAL, 1, P_LOG_CD
            , P_PRONAM, P_LOG_DESC,P_WORK_DESC);

  COMMIT ;

END SP_BTC_LOG;
/
