CREATE OR REPLACE PROCEDURE USRBM.SP_CHK_SEQL_SELL(
                                                     P_YEAR_TOTE   IN VARCHAR2
                                                   , P_DATE_TOTE   IN VARCHAR2
                                                   , P_STND_AMOUNT    IN NUMBER
                                                   , P_STND_TIME IN NUMBER
                                                   , P_STND_CNT IN NUMBER
                                                   )
IS
/******************************************************************************
- ������,��      : ������  20161201
- ���α׷���    : SP_CHK_SEQ_SELL_CONF_FROM_DW
- ���α׷�Ÿ�� : procedure
- ���            : DW���� ���ߺ�����Ȳ(���߸�) ��ȸ�� ���� â�������� �����´�.
- IN  �μ�       : P_STND_AMOUNT (��ȸ ���� �ݾ�, 100000�̸� 100000���� �̻� ���ӹ߸� �ݾ��� ��ȸ)
                     P_STND_TIME(���� �߸Ÿ� �˻��ϴ� �ð� ������ ����, 3�̸� 3�� �̳� ���� �߸ŵ� �ݾ��� ���� 
                     P_STND_CN
                     T(���� �߸ſ� �ش��ϴ� �� �� ����, 3�̸�  3�� ���� �߸ŵ�  �Ǽ� ����
                     100000��, 3��, 3���̸� 100000���� 3���̳� 3���̻� ������ ������ ��ȸ
- ���μ���
    1. ���߸� ��Ȳ�� ��ȸ�ؼ� ���̺� �Է�
******************************************************************************/
    V_ERR_CODE NUMBER;
    V_ERR_MESG VARCHAR(255);


    CURSOR CUR_WIN_INFO IS
        SELECT MEET_NO, RACE_NO, COMM_NO, DIV_NO, WIN_NO, TIME_TOTE FROM VW_TF_SELL_01@DW01LINK
        WHERE 1=1
        AND YEAR_TOTE = P_YEAR_TOTE
        AND DATE_TOTE = P_DATE_TOTE
        AND ASSOC_NO = '1'
        AND AMOUNT = P_STND_AMOUNT
        --AND RACE_NO = '12'    --FOR TEST
        --AND COMM_NO = '14'  --FOR TEST
        --AND DIV_NO ='5' --FOR TEST
        --AND WIN_NO = '007641' --FOR TEST
        GROUP BY  MEET_NO,  RACE_NO, COMM_NO, DIV_NO, WIN_NO, TIME_TOTE
        ORDER BY MEET_NO,  COMM_NO, DIV_NO, WIN_NO, RACE_NO,TIME_TOTE;   

    CUR_WIN_INFO_ROW  CUR_WIN_INFO%ROWTYPE;        
          
BEGIN
    DBMS_OUTPUT.ENABLE;
    OPEN CUR_WIN_INFO;
    LOOP
        FETCH CUR_WIN_INFO INTO CUR_WIN_INFO_ROW;
        EXIT WHEN CUR_WIN_INFO%NOTFOUND;
        --DBMS_OUTPUT.PUT_LINE('WIN_NO:' ||CUR_WIN_INFO_ROW.RACE_NO||CUR_WIN_INFO_ROW.TIME_TOTE);
            SP_CHK_SEQL_SELL_INFO(
                 P_YEAR_TOTE
                ,P_DATE_TOTE
                ,P_STND_AMOUNT
                ,P_STND_TIME
                ,P_STND_CNT
                ,CUR_WIN_INFO_ROW.MEET_NO
                ,CUR_WIN_INFO_ROW.RACE_NO
                ,CUR_WIN_INFO_ROW.COMM_NO
                ,CUR_WIN_INFO_ROW.DIV_NO
                ,CUR_WIN_INFO_ROW.WIN_NO
                ,CUR_WIN_INFO_ROW.TIME_TOTE
            );
            
    END LOOP;
    CLOSE CUR_WIN_INFO;   
    COMMIT;
    RETURN;
    
    EXCEPTION
        WHEN OTHERS THEN
            V_ERR_CODE := SQLCODE();
            V_ERR_MESG := SQLERRM;
            ROLLBACK;
            SP_BTC_LOG('011','E','SP_ALARM_SEND_SMS_HP_NO','ERROR CODE:'||V_ERR_CODE || ' ERROR LOG:'||V_ERR_MESG,'');
            RETURN;

END;
/
