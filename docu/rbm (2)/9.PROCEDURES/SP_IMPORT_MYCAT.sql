CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_MYCAT

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
    v_job_date date;
    v_err_code number;
    v_err_mesg varchar(255);
    
BEGIN
    DBMS_OUTPUT.ENABLE;

    -- ��ǥ�� ���� �Է�
    SP_IMPORT_DIV_INFO;
    
    -- �׸�ī�� ��ǥ�� ���� �Է� 
    SP_IMPORT_MYCAT_DIV_INFO;

    -- �׸�ī�� �������� �Է�
    SP_IMPORT_MYCAT_DIVSTAT(TO_CHAR(SYSDATE, 'YYYYMMDD'));
    
    SP_IMPORT_MYCAT_SALES(TO_CHAR(SYSDATE, 'YYYYMMDD'));
    
    -- �׸�ī�� �߱ްǼ� �� ���� ����ڷ� ��������
    SP_IMPORT_MYCAT_SALES_STAT(TO_CHAR(SYSDATE, 'YYYYMMDD'));

    -- DW(�����ڷ�м�db) ������ �½ĺ� �������� �Է�
    SP_IMPORT_DW_SALES(TO_CHAR(SYSDATE, 'YYYYMMDD'));
    
    CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_MYCAT

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
    v_job_date date;
    v_err_code number;
    v_err_mesg varchar(255);
    
BEGIN
    DBMS_OUTPUT.ENABLE;

    -- ��ǥ�� ���� �Է�
    SP_IMPORT_DIV_INFO;
    
    -- �׸�ī�� ��ǥ�� ���� �Է� 
    SP_IMPORT_MYCAT_DIV_INFO;

    -- �׸�ī�� �������� �Է�
    SP_IMPORT_MYCAT_DIVSTAT(TO_CHAR(SYSDATE, 'YYYYMMDD'));
    
    SP_IMPORT_MYCAT_SALES(TO_CHAR(SYSDATE, 'YYYYMMDD'));
    
    -- �׸�ī�� �߱ްǼ� �� ���� ����ڷ� ��������
    SP_IMPORT_MYCAT_SALES_STAT(TO_CHAR(SYSDATE, 'YYYYMMDD'));

    -- DW(�����ڷ�м�db) ������ �½ĺ� �������� �Է�
    SP_IMPORT_DW_SALES(TO_CHAR(SYSDATE, 'YYYYMMDD'));
    
    -- �����¼��� �����ڷ� ����
    SP_IMPORT_TELMP_FIXEDSEAT(TO_CHAR(SYSDATE, 'YYYYMMDD'));
    

    RETURN;


    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            RETURN;
END;
/


    RETURN;


    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            RETURN;
END;
/
