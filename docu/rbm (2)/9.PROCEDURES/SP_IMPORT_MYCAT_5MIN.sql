CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_MYCAT_5MIN

IS
/******************************************************************************
- ������,��      : jsshin 2012.03.10
- ���α׷���     : SP_IMPORT_mycat
- ���α׷�Ÿ��   : procedure
- ���           : �������� �����, �������� �ڷ���踦 �����´�.
- IN  �μ�       :

- ���μ���
    1. ���� ������ ������ڷ� ���� �� �Է��ϴ� SP_IMPORT_MYCAT_DIVSTAT ���ν��� ȣ��
    2. ���� ������ �����ڷ� ���� �� �Է��ϴ� SP_IMPORT_MYCAT_SALES ���ν��� ȣ��


- ���ν����� ���� �� 8�ÿ� �۵��ǵ��� �Ѵ�.(�����ٷ� ���)
******************************************************************************/
    v_job_date DATE;
    v_err_code NUMBER;
    v_err_mesg VARCHAR(255);
    
BEGIN
    DBMS_OUTPUT.ENABLE;

    -- �׸�ī�� �������� �Է�
    SP_IMPORT_MYCAT_SALES(TO_CHAR(SYSDATE, 'YYYYMMDD'));
    
    SP_IMPORT_MYCAT_SALES_DT;
    
    RETURN;


    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            RETURN;
END;
/
