CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_MRA_BUSAN

IS
/******************************************************************************
- ������,��      :
- ���α׷���     : SP_IMPORT_MRA_BUSAN
- ���α׷�Ÿ��   : procedure
- ���           : �λ� ���� �����.
- IN  �μ�       :

- ���μ���
    
- OUTSITE(�λ�)       -> DIV_NO
    101 ����          -> 01
    102 ����          -> 02
    003 ����          -> 03
    005 ���� ����ī�� -> 03
    006 ���� ����ī�� -> 01
    
- OUTSITE(â��)       -> DIV_NO
    001 ����(����)     -> 01
    002 ��������(����) -> 02
    004 ����ī��(��ü=����+����)   :���� ��������ī��, ��������ī��� �и� ����
    
    

- ���ν����� ��, �� 10~19�ñ��� 5�а���  �۵��ǵ��� �Ѵ�.(�����ٷ� ���)
******************************************************************************/
    P_CUR_DATE VARCHAR(8);
    P_MEET_CD VARCHAR(3);
    P_STND_YEAR VARCHAR(4);
    P_TMS           VARCHAR(3);
    P_DAY_ORD    VARCHAR(2);
    V_ERR_CODE NUMBER;
    V_ERR_MESG VARCHAR(255);
    V_LOG_SEQ NUMBER(15);
    V_LOG_SERL_NO NUMBER(3);

BEGIN
    DBMS_OUTPUT.ENABLE;
    SELECT SQ_TBRK_BTC_LOG.NEXTVAL
      INTO V_LOG_SEQ
    FROM DUAL;
    
    V_LOG_SERL_NO := 0;    
    --SP_BTC_LOG_SEQ('006','I','SP_IMPORT_MRA_BUSAN','START','', V_LOG_SEQ, V_LOG_SERL_NO);
    
    SELECT TO_CHAR(SYSDATE, 'YYYYMMDD')
    INTO   P_CUR_DATE
    FROM DUAL;
    
    SELECT MEET_CD, STND_YEAR, TMS, DAY_ORD
    INTO    P_MEET_CD, P_STND_YEAR, P_TMS, P_DAY_ORD
    FROM  VW_SDL_INFO
    WHERE  RACE_DAY = P_CUR_DATE
      AND  MEET_CD ='003';

    SP_IMPORT_MRA_BUSAN_RACE_NO(P_CUR_DATE);
   
    COMMIT;
    --SP_BTC_LOG_SEQ('006','I','SP_IMPORT_MRA_BUSAN','END','', V_LOG_SEQ, V_LOG_SERL_NO);
    
    RETURN;


    EXCEPTION
        WHEN OTHERS THEN
            V_ERR_CODE := SQLCODE();
            V_ERR_MESG := SQLERRM;
            ROLLBACK;
            SP_BTC_LOG_SEQ('006','E','SP_IMPORT_MRA_BUSAN','ERROR CODE:'||V_ERR_CODE || ' ERROR LOG:'||V_ERR_MESG,'', V_LOG_SEQ, V_LOG_SERL_NO);
            RETURN;

END;
/
