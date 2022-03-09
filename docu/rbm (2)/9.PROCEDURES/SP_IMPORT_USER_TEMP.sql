CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_USER_TEMP

IS
/******************************************************************************
- ������,��      : ��ȣö 2011.12.14
- ���α׷���     : SP_IMPORT_USER_TEMP
- ���α׷�Ÿ��   : procedure
- ���           : VENUSLINK ����� ��������� ���ֻ�������ý��� DB TBRK_USER_TEMP �� �����Ѵ�.
- IN  �μ�       :

- ���μ���
    1. ���� ����� ��������� ����
    2. USHUM.VW_EMP_D12@VENUSLINK, USHUM.VW_WORK_EXCEPT_D12@VENUSLINK ������ ��ȸ�ؼ� TBRK_USER_TEMP ���̺� �Է�


- ���ν����� ���� �� 8�ÿ� �۵��ǵ��� �Ѵ�.(�����ٷ� ���)
******************************************************************************/
    v_job_date date;
    v_err_code number;
    v_cnt number;
    v_err_mesg varchar(255);
    v_exec_date date;

BEGIN

    DBMS_OUTPUT.ENABLE;
     
    SP_BTC_LOG('008','I','SP_IMPORT_USER_TEMP','START','');
    SELECT SYSDATE
    INTO     v_exec_date
    FROM   DUAL;

    -- 1. ���� �μ� ������ ����
    --DELETE FROM  TBRK_USER_TEMP;

    -- 2.  ����� ������� ������ �Է�
    MERGE INTO  TBRK_USER_TEMP DST
        USING (
            SELECT COMM_NO
                  ,EMP_NO
                  ,EMP_NM
                  ,WK_JOB_CD
                  ,RETIRE_GBN
                  ,CO_WRK_GBN
                  ,'' AS INST_ID
                  ,SYSDATE AS INST_DT
                  ,'' AS UPDT_ID
                  ,SYSDATE AS UPDT_DT
                  ,ENTR_DT
                  ,DEPT_CD
                  ,DEPT_NM
              FROM (
                       SELECT  FC_CDTERM2_CODE('018', DEPT_CD) AS COMM_NO
                                  ,EMP_NO
                                  ,EMP_NM
                                  ,WK_JJOB AS WK_JOB_CD
                                  ,HOLOFF_CLS AS RETIRE_GBN
                                  ,'001' AS CO_WRK_GBN 
                                  ,ENTR_DT
                                  ,DEPT_CD
                                  ,DEPT_NM
                         FROM  USHUM.VW_EMP_D12@VENUSLINK
                        WHERE 1 = 1
                           AND  HOLOFF_CLS = '0'  
                          --AND WK_JJOB IN ('1003', '1005', '1006') -- �߸ſ�, �߸ź���, ��ǥ����
                          
                        UNION ALL 
                                
                         SELECT FC_CDTERM2_CODE('018', A.DEPT_CD) AS COMM_NO
                                   ,A.EMP_NO
                                   ,(SELECT EMP_NM FROM USHUM.VW_EMP_D12@VENUSLINK WHERE EMP_NO = A.EMP_NO) AS EMP_NM
                                   ,A.WK_JJOB AS WK_JOB_CD
                                   ,'0' AS RETIRE_GBN
                                   ,'002' AS CO_WRK_GBN 
                                   ,NULL
                                   ,NULL
                                   ,NULL
                           FROM USHUM.VW_WORK_EXCEPT_D12@VENUSLINK A
                           WHERE 1 = 1
                            --AND A.WK_JJOB IN ('1003', '1005', '1006')   -- �߸ſ�, �߸ź���, ��ǥ����
                    ) 
              )  SRC
                ON (
                       DST.COMM_NO = SRC.COMM_NO 
                AND DST.CO_WRK_GBN = SRC.CO_WRK_GBN
                AND DST.EMP_NO = SRC.EMP_NO
                     )
                WHEN MATCHED THEN
                    UPDATE SET
                                DST.EMP_NM = SRC.EMP_NM
                              ,DST.WK_JOB_CD = SRC.WK_JOB_CD
                              ,DST.RETIRE_GBN = SRC.RETIRE_GBN
                              ,DST.UPDT_ID = 'BATCH'
                              ,DST.UPDT_DT = v_exec_date    
                              ,DST.ENTR_DT = SRC.ENTR_DT
                              ,DST.DEPT_CD = SRC.DEPT_CD
                              ,DST.DEPT_NM = SRC.DEPT_NM
               WHEN NOT MATCHED THEN
                    INSERT (COMM_NO, EMP_NO, EMP_NM, WK_JOB_CD, RETIRE_GBN, CO_WRK_GBN, INST_ID, INST_DT,ENTR_DT,DEPT_CD, DEPT_NM
                    ) VALUES (
                         SRC.COMM_NO
                        ,SRC.EMP_NO
                        ,SRC.EMP_NM
                        ,SRC.WK_JOB_CD
                        ,SRC.RETIRE_GBN
                        ,SRC.CO_WRK_GBN
                        ,'BATCH'
                        ,v_exec_date
                        ,SRC.ENTR_DT
                        ,SRC.DEPT_CD
                        ,SRC.DEPT_NM
                    );
        COMMIT;      
        
        DELETE FROM TBRK_USER_TEMP
        WHERE INST_DT != v_exec_date AND UPDT_DT != v_exec_date;

     
        SP_BTC_LOG('008','I','SP_IMPORT_USER_TEMP','END','');
        
    RETURN;
    
 
    EXCEPTION
    
        WHEN OTHERS THEN
            v_err_code := SQLCODE();
            v_err_mesg := SQLERRM;
            
            ROLLBACK;
            
            SP_BTC_LOG('008','E','SP_IMPORT_USER_TEMP','ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'');
            
            RETURN;

END;
/
