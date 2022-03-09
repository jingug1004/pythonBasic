CREATE OR REPLACE PROCEDURE USRBM.SP_LOG (
      P_LOG_TEXT     IN     VARCHAR2 )
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

BEGIN




  --3) �α� ����
  INSERT INTO SJS_DEBUG
            ( SEQ, LOG, DT)
       VALUES
            ( (SELECT NVL(MAX(SEQ),0) + 1 FROM SJS_DEBUG), 
              P_LOG_TEXT,
              SYSDATE);

  COMMIT ;

END SP_LOG;
/
