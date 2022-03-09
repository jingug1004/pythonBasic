CREATE OR REPLACE PROCEDURE USRBM.SP_LOG (
      P_LOG_TEXT     IN     VARCHAR2 )
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

BEGIN




  --3) 로그 생성
  INSERT INTO SJS_DEBUG
            ( SEQ, LOG, DT)
       VALUES
            ( (SELECT NVL(MAX(SEQ),0) + 1 FROM SJS_DEBUG), 
              P_LOG_TEXT,
              SYSDATE);

  COMMIT ;

END SP_LOG;
/
