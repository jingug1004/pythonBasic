CREATE OR REPLACE PROCEDURE USRBM.SP_ALARM_SEND_SMS( P_ALARM_CD IN VARCHAR2
                                                   , P_SMS_MESG IN VARCHAR2
                                                   , P_SENR_ID  IN VARCHAR2
                                                   , P_MN_ID    IN VARCHAR2
                                                   , P_MN_NM    IN VARCHAR2 )
IS
/******************************************************************************
- ������,��      : ���缱, �밡�� �ڵ�
- ���α׷���     : SP_SEND_SMS
- ���α׷�Ÿ��   : procedure
- ���           : ������ ����ڿ��� ���� �޼����� ������.
- IN  �μ�       :

- ���μ���
    1. ����ڸ� ��ȸ�Ͽ� �ش� ����ڿ��� ���ڸ� ����


******************************************************************************/

    V_SENDER_NM VARCHAR2(255);
    V_S_HP_NO1  VARCHAR2(255);
    V_S_HP_NO2  VARCHAR2(255);
    V_S_HP_NO3  VARCHAR2(255);
    V_S_HP_NO   VARCHAR2(255);
    V_SMS_SEQ   NUMBER;

    V_ERR_CODE NUMBER;
    V_ERR_MESG VARCHAR(255);


    CURSOR CUR_SMS_REVR IS
        SELECT U.USER_ID,
               U.USER_NM,
               TRIM(REPLACE(REPLACE(HP_NO,')'),'-')) AS HP_NO,
               TRIM(SUBSTR(REPLACE(REPLACE(HP_NO,')'),'-'),1,3)) HP_NO1,
               TRIM(SUBSTR(REPLACE(REPLACE(HP_NO,')'),'-'), 4, LENGTH(REPLACE(REPLACE(HP_NO,')'),'-')) - 7))  HP_NO2,
               TRIM(SUBSTR(REPLACE(REPLACE(HP_NO,')'),'-'),-4)) HP_NO3
        FROM TBRK_ALARM A, TBRK_USER U
        WHERE A.ALARM_CD  = P_ALARM_CD  -- �˶��ڵ�(GRP_CD = '080')
          AND A.ALARM_GBN = '002' -- SMS �߼�
          AND A.RECV_ID = U.USER_ID
          AND U.HP_NO IS NOT NULL;

     CUR_SMS_REVR_ROW  CUR_SMS_REVR%ROWTYPE;

BEGIN

    -- 1) �߼����� �̸��� ��ȭ��ȣ ��ȸ
    -- 2) SMS �߼۴���� ��ȸ
    -- 3) ����ں��� SMS ���� ��ȸ
    -- 4) SMS�߼����̺� �Է�
    -- 5) �߼��̷� ���̺� �Է�

    -- 1) �߼����� �̸��� ��ȭ��ȣ ��ȸ, ������ admin
    SELECT
		USER_NM, HP_NO1, HP_NO2, HP_NO3, HP_NO
    INTO   V_SENDER_NM,
       V_S_HP_NO1,
       V_S_HP_NO2,
       V_S_HP_NO3,
       V_S_HP_NO
	FROM
	(
	    SELECT
	           USER_NM,
	           SUBSTR(REPLACE(HP_NO,'-'),1,3) HP_NO1,
	           SUBSTR(REPLACE(HP_NO,'-'), 4, LENGTH(REPLACE(HP_NO,'-')) - 7)  HP_NO2,
	           SUBSTR(REPLACE(HP_NO,'-'),-4) HP_NO3,
	           REPLACE(HP_NO,'-','') AS HP_NO

	    FROM TBRK_USER
	    WHERE USER_ID IN (P_SENR_ID, 'admin')
		ORDER BY USER_ID ASC

	)
	WHERE ROWNUM = 1
	;

    -- 2) SMS �߼۴���� ��ȸ
    OPEN CUR_SMS_REVR;
    LOOP
        FETCH CUR_SMS_REVR INTO CUR_SMS_REVR_ROW;
        EXIT WHEN CUR_SMS_REVR%NOTFOUND;

        -- 3) ����ں��� SMS ���� ��ȸ

       IF LENGTHB(P_SMS_MESG) > 90 THEN
           -- �幮���ڸ޼��� ����
           SELECT  SMS.SEQNO.NEXTVAL
           INTO    V_SMS_SEQ
           FROM    DUAL;

            -- 4) SMS�߼����̺� �Է�
            INSERT INTO SMS.MMSDATA(
                   SEQNO,     USERCODE, INTIME,  REQTIME, RECVTIME,
                   CALLPHONE, REQPHONE, SUBJECT, MSG,     FKCONTENT,
                   MEDIATYPE, RESULT,   ERRCODE)
            VALUES (V_SMS_SEQ, 'kcycle', TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), '00000000000000', NULL,
                    CUR_SMS_REVR_ROW.HP_NO, V_S_HP_NO, SUBSTRB(P_SMS_MESG,1,80), P_SMS_MESG, 0,
                    NULL, '0', 0);
       ELSE
           -- �ܹ����ڸ޼��� ����
           SELECT SMS.SQ_SMSDATA.NEXTVAL
           INTO     V_SMS_SEQ
           FROM    DUAL;

            -- 4) SMS�߼����̺� �Է�
            INSERT INTO SMS.SMSDATA(
                   SEQNO, MEMBER,
                   INDATE, INTIME,
                   SENDID, SENDNAME,
                   RPHONE1, RPHONE2, RPHONE3,
                   SPHONE1, SPHONE2, SPHONE3,
                   MSG,
                   RESULT, KIND, ERRCODE,
                   SYS_GBN, USERID)
            VALUES (V_SMS_SEQ, 0,
                    TO_CHAR(SYSDATE, 'YYYYMMDD'), TO_CHAR(SYSDATE, 'HH24MISS'),
                    'kcycle', V_SENDER_NM,
                    CUR_SMS_REVR_ROW.HP_NO1, CUR_SMS_REVR_ROW.HP_NO2, CUR_SMS_REVR_ROW.HP_NO3,
                    V_S_HP_NO1, V_S_HP_NO2, V_S_HP_NO3,
                    P_SMS_MESG,
                    '0', 'S', 0,
                    'USRBM', P_SENR_ID);
       END IF;

         -- 5) �߼��̷� ���̺� �Է�
         INSERT INTO TBRK_ALARM_HIST (
                 ALARM_GBN,
                 RECV_ID,
                 SEQ_NO,
                 MN_ID,
                 TITLE,
                 CNTNT,
                 INST_ID,
                 INST_DT,
                 HP_NO,
                 SMS_SEQ_NO)
                 VALUES (
                 '002',                     -- SMS
                 CUR_SMS_REVR_ROW.USER_ID,  -- ������ ID
                 SQ_TBRK_ALARM_HIST.NEXTVAL, -- �߼��̷� ����
                 P_MN_ID,
                 P_MN_NM,
                 P_SMS_MESG,
                 P_SENR_ID,
                 SYSDATE,
                 CUR_SMS_REVR_ROW.HP_NO,
                 V_SMS_SEQ                    -- SMS ���̺��� ����
                 );

    END LOOP;
    CLOSE CUR_SMS_REVR;
    COMMIT;
    RETURN;


    EXCEPTION

        WHEN NO_DATA_FOUND THEN
            SP_BTC_LOG('011','E','SP_ALARM_SEND_SMS','�߼����� ����� �������� �ʽ��ϴ�.(���:'||P_SENR_ID || ')','');
            RETURN;

        WHEN OTHERS THEN
            V_ERR_CODE := SQLCODE();
            V_ERR_MESG := SQLERRM;
            ROLLBACK;
            SP_BTC_LOG('011','E','SP_ALARM_SEND_SMS','ERROR CODE:'||V_ERR_CODE || ' ERROR LOG:'||V_ERR_MESG,'');
            RETURN;

END;
/
