CREATE OR REPLACE PROCEDURE USRBM.SP_BTC_LOG (
      P_BTC_CD     IN     VARCHAR2
    , P_LOG_CD     IN     VARCHAR2
    , P_PRONAM     IN     VARCHAR2
    , P_LOG_DESC   IN     VARCHAR2
    , P_WORK_DESC  IN     VARCHAR2 )
AS

/*******************************************************************************
  - PROCEDURE NAME : SP_BTC_LOG
  - DESCRIPTION    : LOG ���
  - REFERENCE      : TABLE(WRITE = SP_BTC_LOG)
                     SEQUENCE(READ = SQ_TBRK_BTC_LOG)
  - REFERENCED BY  : ��� DB ��ġ�۾� �� JAVA�������̽� ��ġ�۾�
  - IN PARAMETER   : P_BTC_CD     ��ġ���� (�����ڵ� : 045)

                     P_LOG_CD     �αױ��� (�����ڵ� : 046 - I:����, W:���, E:����)
                     P_PRONAM     ���α׷� (ȣ�����α׷�- JAVA CLASS NAME OR SP NAME)
                     P_LOG_DESC   �α׳��� (����ܰ�, �۾����� Ȥ�� ���� �ؽ�Ʈ)
                     P_WORK_DESC  �۾����� (�۾����)
  - MADE BY        : KOO,NAMGON
   ------------------------------------------------------------------------------
  - MODIFICATION   : (DATE, WHO, MODIFIED CONTENTS)
  - COMMENT        :

*******************************************************************************/


  V_DATE        VARCHAR2(8)      DEFAULT TO_CHAR(SYSDATE, 'YYYYMMDD');
  V_TIME        VARCHAR2(9)      DEFAULT TO_CHAR(SYSTIMESTAMP , 'HH24MISSFF3') ;

BEGIN


  --3) �α� ����
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
