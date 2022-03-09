CREATE OR REPLACE PROCEDURE USRBM.SP_CHK_SALES_MBR_CHOCHA_DIV IS
    V_MESG              VARCHAR(120);      -- ���ڸ޼���
    V_RET               VARCHAR(1);        -- �����
    V_LOG_SEQ           NUMBER(15);
    V_LOG_SERL_NO       NUMBER(3);
    V_CHOCHA_ERR_CNT    NUMBER(3);

BEGIN

    SELECT SQ_TBRK_BTC_LOG.NEXTVAL
      INTO V_LOG_SEQ
    FROM DUAL;
    
    V_LOG_SERL_NO := 0;    
    SP_BTC_LOG_SEQ('006','I','SP_CHK_SALES_MBR_CHOCHA_DIV','START','', V_LOG_SEQ, V_LOG_SERL_NO);
   

    -- ����������� �и����� �ʰų� ������ �߻��� ���
    V_CHOCHA_ERR_CNT := 0;
    SELECT  COUNT(*) AS CNT
    INTO    V_CHOCHA_ERR_CNT
    FROM    VW_SDL_VERIFY_CHOCHA
    WHERE   1=1
    AND (MEET_CD, STND_YEAR, TMS, DAY_ORD) IN 
        (SELECT MEET_CD, STND_YEAR, TMS, DAY_ORD 
         FROM   VW_SDL_INFO 
         WHERE RACE_DAY = TO_CHAR(SYSDATE,'yyyymmdd') 
         AND MEET_CD = '003')
    AND (CHOCHA_DT_04 = 0 OR CHOCHA_DT_02 = 0 OR CHOCHA_CHK != 0);   -- �����ڷḸ ��ȸ

    IF V_CHOCHA_ERR_CNT > 0 THEN
          SP_ALARM_SEND_SMS( '011', '��������� ����(���ּ�:'||V_CHOCHA_ERR_CNT||')', 'admin', 'RSY6010', 'SP_CHK_SALES_MBR_CHOCHA_DIV');
          SP_BTC_LOG_SEQ('006','E','SP_CHK_SALES_VERI','��������� ����', '', V_LOG_SEQ, V_LOG_SERL_NO);                             
    END IF;
   
    SP_BTC_LOG_SEQ('006','I','SP_CHK_SALES_MBR_CHOCHA_DIV','END','', V_LOG_SEQ, V_LOG_SERL_NO);                
    RETURN;
    
    EXCEPTION
     WHEN NO_DATA_FOUND THEN
         NULL;
     WHEN OTHERS THEN
        SP_BTC_LOG_SEQ('006','E','SP_CHK_SALES_MBR_CHOCHA_DIV',SQLERRM, '', V_LOG_SEQ, V_LOG_SERL_NO);
       -- Consider logging the error and then re-raise
       RAISE;
END SP_CHK_SALES_MBR_CHOCHA_DIV;
/
