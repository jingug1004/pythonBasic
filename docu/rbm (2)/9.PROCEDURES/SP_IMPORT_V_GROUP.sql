CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_V_GROUP

IS
/******************************************************************************
- ������,��      : yslee 2009.08.12
- ���α׷���     : SP_IMPORT_V_GROUP
- ���α׷�Ÿ��   : procedure
- ���           : JUPITERLINK �μ������� ���ֻ�������ý��� DB�� �����Ѵ�.
- IN  �μ�       :

- ���μ���
    1. ���� �μ������� ����
    2. V_GROUP@JUPITERLINK ������ ��ȸ�ؼ� V_GROUP ���̺� �Է�


- ���ν����� ���� �� 8�ÿ� �۵��ǵ��� �Ѵ�.(�����ٷ� ���)
******************************************************************************/
    v_job_date date;
    v_err_code number;
    v_cnt number;
    v_err_mesg varchar(255);


begin
    DBMS_OUTPUT.ENABLE;

    SP_BTC_LOG('005','I','SP_IMPORT_V_GROUP','START','');

    -- 1. ���� �μ� ������ ����
    DELETE FROM  V_GROUP;

    -- 2. V_GROUP@JUPITERLINK �μ� ������ �Է�
    INSERT  INTO V_GROUP
          SELECT     GROUP_ID
                    ,GROUP_SPEC_ID
                    ,GROUP_NAME
                    ,GROUP_OPENDATE
                    ,GROUP_INDENT
                    ,GROUP_PARENT
                    ,CHILD_CNT
                    ,GROUP_ORDER
                    ,GROUP_POSITION
                    ,DOC_GROUP_ID
                    ,DOC_GROUP_NAME
                    ,GROUP_DOC_CODE
                    ,GROUP_TEL
                    ,ORG_ID
                    ,GROUP_CHIEF
                    ,REAL_ORG_ID
                    ,GROUP_CLOSEDATE
                    ,REG_GROUP_ID
                    ,REG_GROUP_NAME
            FROM V_GROUP@JUPITERLINK;

    SELECT COUNT(1)
          INTO v_cnt
      FROM V_GROUP;

    IF v_cnt = 0 THEN
        ROLLBACK;
        SP_BTC_LOG('005','W','SP_IMPORT_V_GROUP','Import count = 0 ... rollback ','');
    ELSE
        COMMIT;
    END IF;

    SP_BTC_LOG('005','I','SP_IMPORT_V_GROUP','END','');
    RETURN;

    EXCEPTION
        WHEN OTHERS THEN
            v_err_code := SQLCODE();
            v_err_mesg := SQLERRM;
            ROllBACK;
            SP_BTC_LOG('005','E','SP_IMPORT_V_GROUP','ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'');
            RETURN;

end;
/
