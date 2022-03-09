CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_TMONEY_TRADE

IS
/******************************************************************************
- ������,��      : jsshin 2012.03.10
- ���α׷���     : SP_IMPORT_TMONEY_TRADE
- ���α׷�Ÿ��   : procedure
- ���           : ���� �� ������ �����¡���ý��� ����� DB���� �ڷḦ �����´�.(���� 1ȸ ����)
- IN  �μ�       :

- ���μ���
    1. ����DB�� �����ϴ� ������ ���������� ��ȸ�Ѵ�.
    2. �������� �������� ������ �ڷḦ ���� �����´�.
    3. ����DB���� ������������ �ڷḦ �����Ѵ�.

- ���ν����� ���� �� 11:30�ÿ� �۵��ǵ��� �Ѵ�.(�����ٷ� ���)
******************************************************************************/
    V_JOB_DATE DATE;
    V_ERR_CODE NUMBER;
    V_ERR_MESG VARCHAR(255);
    V_LOG_SEQ NUMBER(15);
    V_LOG_SERL_NO NUMBER(3);
    V_MAX_SEQ_NO_FS NUMBER(20);
    V_PROCESS VARCHAR(255);
    V_STAT_START_DT VARCHAR(255);
    
    v_CheckCount Number;

    -- 01.Ƽ�Ӵ� ���� CURSOR
    CURSOR CUR_MAX_SEQ IS
         SELECT COMM_NO, DIV_NO, MAX(TRANSITION_SEQ) AS MAX_SEQ
         FROM TBIF_STATUS@TMONEY_LINK
         GROUP BY COMM_NO, DIV_NO;

    -- 02. ���� ����ǹ߸ű� ���� CURSOR
    CURSOR CUR_MAX_SEQ_KM IS
        SELECT LOC, MAX(SEQ) AS MAX_SEQ
        FROM   TBRC_FS_STATUS_KM
        WHERE  JOB_TYPE = 'E'
        GROUP BY LOC;
            
     -- 04. Ŀ������ ���� ���� ����    
     MAX_SEQ_ROW  CUR_MAX_SEQ%ROWTYPE;
     MAX_SEQ_ROW_KM  CUR_MAX_SEQ_KM%ROWTYPE;
     
BEGIN
    DBMS_OUTPUT.ENABLE;

    SELECT SQ_TBRK_BTC_LOG.NEXTVAL
      INTO V_LOG_SEQ
    FROM DUAL;
    
    --V_STAT_START_DT := '20140100';
    SELECT TO_CHAR(SYSDATE -9, 'YYYYMMDD')
    INTO   V_STAT_START_DT
    FROM   DUAL;
    
    V_LOG_SERL_NO := 0;    
    SP_BTC_LOG_SEQ('012','I','SP_IMPORT_TMONEY_TRADE','START','', V_LOG_SEQ, V_LOG_SERL_NO);

    V_PROCESS := '10';
    
    -- ���۶� ���� �ڷᰡ �ִ��� Ȯ��
    SELECT   count(*)
    INTO  v_CheckCount
    FROM  TBIF_TRADE@TMONEY_LINK;
    
    SP_BTC_LOG_SEQ('012','I','SP_IMPORT_TMONEY_TRADE','Total Record pre Cnt[pre]:'||v_CheckCount,'', V_LOG_SEQ, V_LOG_SERL_NO);
    
    
    -- 10)Ƽ�Ӵ� ������������ �ڷḦ �����´�. --------------------------------------------
    OPEN CUR_MAX_SEQ;
        LOOP
            --11) �Ǻ��� ������ ���� ��ȸ
            FETCH CUR_MAX_SEQ INTO MAX_SEQ_ROW;
            EXIT WHEN CUR_MAX_SEQ%NOTFOUND;
            
            V_PROCESS := '11';

            -- 12) ����DB�� ������ �ŷ������� �DB�� �����Ѵ�. 
            INSERT INTO TBRC_T_TRADE
                                (COMM_NO, DIV_NO, TRANSITION_SEQ, MACHINE_ID, TRADE_DATE, 
                                 TERM_ID, CARD_ID, CARD_SERIAL, REQUEST_FEE, 
                                 CARD_USER_CODE, CARD_USER_TYPE, IDATE)
            SELECT   COMM_NO, DIV_NO, TRANSITION_SEQ, MACHINE_ID, TRADE_DATE, 
                                 TERM_ID, 
                                 CARD_ID, 
                                 CARD_SERIAL,  TO_NUMBER(REQUEST_FEE), 
                                 CARD_USER_CODE, CARD_USER_TYPE, SYSDATE
            FROM  TBIF_TRADE@TMONEY_LINK
            WHERE COMM_NO =  MAX_SEQ_ROW.COMM_NO
                AND  DIV_NO = MAX_SEQ_ROW.DIV_NO
                AND  TRANSITION_SEQ <=  MAX_SEQ_ROW.MAX_SEQ;
           
            V_PROCESS := '12';
            
            --201502�� Ƽ�Ӵ� �����ڷᰡ �������(�ȸ��������?) ���� �߻� !!! 
            --����Ȯ���� ���� �����ڷḦ ����س��´�. 

            --�α׵� ����
            SP_BTC_LOG_SEQ('012','I','SP_IMPORT_TMONEY_TRADE','Insert STATUS:'||MAX_SEQ_ROW.COMM_NO||'-'||MAX_SEQ_ROW.DIV_NO||'-'||MAX_SEQ_ROW.MAX_SEQ||'->'||SQL%ROWCOUNT,'', V_LOG_SEQ, V_LOG_SERL_NO);
            
            --���� �ذ� ���� �ּ� ó��  20150401
            /*
            --rbm�� ���
            INSERT INTO TBRC_T_TRADE_TRBL
                     (COMM_NO, DIV_NO, TRANSITION_SEQ, MACHINE_ID, TRADE_DATE, 
                                 TERM_ID, CARD_ID, CARD_SERIAL, REQUEST_FEE, 
                                 CARD_USER_CODE, CARD_USER_TYPE, IDATE)
            SELECT   COMM_NO, DIV_NO, TRANSITION_SEQ, MACHINE_ID, TRADE_DATE, 
                                 TERM_ID, 
                                 CARD_ID, 
                                 CARD_SERIAL,  TO_NUMBER(REQUEST_FEE), 
                                 CARD_USER_CODE, CARD_USER_TYPE, SYSDATE
            FROM  TBIF_TRADE@TMONEY_LINK
            WHERE COMM_NO =  MAX_SEQ_ROW.COMM_NO
                AND  DIV_NO = MAX_SEQ_ROW.DIV_NO
                AND  TRANSITION_SEQ <=  MAX_SEQ_ROW.MAX_SEQ;
            
            --Ƽ�ӴϿ� ���                
            INSERT INTO  TBIF_TRADE_trbl@TMONEY_LINK 
            SELECT * 
            FROM TBIF_TRADE@TMONEY_LINK
            WHERE COMM_NO =  MAX_SEQ_ROW.COMM_NO
             AND  DIV_NO = MAX_SEQ_ROW.DIV_NO
             AND  TRANSITION_SEQ <=  MAX_SEQ_ROW.MAX_SEQ;        
            */                         
            
            -- 13) ������ ����Ϸ�� �ڷ�� ����DB���� �����Ѵ�.
            DELETE 
            FROM TBIF_TRADE@TMONEY_LINK
            WHERE COMM_NO =  MAX_SEQ_ROW.COMM_NO
                AND  DIV_NO = MAX_SEQ_ROW.DIV_NO
                AND  TRANSITION_SEQ <=  MAX_SEQ_ROW.MAX_SEQ;
            
            -- ������ ���ڵ� Ȯ��
            SP_BTC_LOG_SEQ('012','I','SP_IMPORT_TMONEY_TRADE','Delete Status:'||MAX_SEQ_ROW.COMM_NO||'-'||MAX_SEQ_ROW.DIV_NO||'-'||MAX_SEQ_ROW.MAX_SEQ||'->'||SQL%ROWCOUNT,'', V_LOG_SEQ, V_LOG_SERL_NO);
                       
        END LOOP;
        CLOSE CUR_MAX_SEQ;
        
        
        -- ���� �� ���� �ڷᰡ �ִ��� Ȯ��
        SELECT   count(*)
        INTO  v_CheckCount
        FROM  TBIF_TRADE@TMONEY_LINK;
    
        SP_BTC_LOG_SEQ('012','I','SP_IMPORT_TMONEY_TRADE','Total Record Cnt[post]:'||v_CheckCount,'', V_LOG_SEQ, V_LOG_SERL_NO);
        

    V_PROCESS := '13';     
    -- 14) ��������ī�忡 ���� ������ UPDATE�Ѵ�.
    MERGE INTO TBRC_T_FREE_CARD DST
    USING (SELECT COMM_NO, DIV_NO, IDX, CARD_TYPE_ID, CARD_TYPE, CARD_MEMO, CARD_NO
                FROM   TBIF_FREE_CARD@TMONEY_LINK
               ) SRC
               ON (
                         DST.COMM_NO = SRC.COMM_NO
                  AND DST.DIV_NO     = SRC.DIV_NO 
                  AND DST.IDX           = SRC.IDX
               )
               WHEN MATCHED THEN
                         UPDATE SET
                                     DST.CARD_TYPE_ID = SRC.CARD_TYPE_ID
                                    ,DST.CARD_TYPE      = SRC.CARD_TYPE
                                    ,DST.CARD_MEMO    = KSPO_DECRYPT(SRC.CARD_MEMO)
                                    ,DST.CARD_NO         = SRC.CARD_NO
                                    ,DST.UPDT_DTM       = SYSDATE
               WHEN NOT MATCHED THEN
                         INSERT (
                                     COMM_NO
                                    ,DIV_NO
                                    ,IDX
                                    ,CARD_TYPE_ID
                                    ,CARD_TYPE
                                    ,CARD_MEMO
                                    ,CARD_NO
                                    ,INST_DTM
                                    ) VALUES (
                                     SRC.COMM_NO
                                    ,SRC.DIV_NO
                                    ,SRC.IDX
                                    ,SRC.CARD_TYPE_ID
                                    ,SRC.CARD_TYPE
                                    ,KSPO_DECRYPT(SRC.CARD_MEMO)
                                    ,SRC.CARD_NO
                                    ,SYSDATE
                                    );
    V_PROCESS := '14';
     
    --15) Ƽ�ӴϿ���DB�� ��������ī�� ������ �����Ѵ�.
     DELETE FROM TBIF_FREE_CARD@TMONEY_LINK;
     
     -- �ڷ� ���� �ǿ� ���ؼ��� �켱 commit ó���Ѵ�. 
     --COMMIT; --20150226
     
    V_PROCESS := '15';
     
    --20) ���� ����ǹ߸ű��� ���� ���� ���� �����´�.  -----------------------------------------
      -- 21) ����DB���� ������ MAX������ �����´�. 
    OPEN CUR_MAX_SEQ_KM;
        LOOP
            V_PROCESS := '21';
            --22) �Ǻ��� ������ ���� ��ȸ
            FETCH CUR_MAX_SEQ_KM INTO MAX_SEQ_ROW_KM;
            EXIT WHEN CUR_MAX_SEQ_KM%NOTFOUND;
            
            --22) ����DB�� �����¼����� �ŷ������� �DB�� �����Ѵ�. 
            INSERT INTO TBRC_T_TRADE_KM
                        (MACHINE_ID, TRANSITION_SEQ, TRADE_DATE, TRADE_TIME,
                         CNT,  FEE)
            SELECT   LOC, SEQ, SALE_DATE, SALE_TIME, QTY, AMT
            FROM     TBRC_FS_ISSUE_KM
            WHERE    LOC =  MAX_SEQ_ROW_KM.LOC
            AND      JOB_TYPE = 'E'
            AND      SEQ  <= MAX_SEQ_ROW_KM.MAX_SEQ;
            
            V_PROCESS := '22';
            --23) Ƽ�ӴϿ���DB�� �����¼���  ������ �����Ѵ�.
            DELETE FROM TBRC_FS_ISSUE_KM
                WHERE  LOC =  MAX_SEQ_ROW_KM.LOC
                AND    JOB_TYPE = 'E'
                AND    SEQ  <= MAX_SEQ_ROW_KM.MAX_SEQ;
     
        END LOOP;
        CLOSE CUR_MAX_SEQ_KM;
        
         -- �ڷ� ���� �ǿ� ���ؼ��� �켱 commit ó���Ѵ�. 
         --COMMIT; --20150226
      
        V_PROCESS := '43';
     
       
    --40) �����ο� ������� ����  --------------------------------------------------
        --41) �����ο� ��������� �����Ѵ�.
        DELETE FROM TBRC_T_TRADE_RACE_SUM
        WHERE  TRADE_DATE >= V_STAT_START_DT;
        V_PROCESS := '410';
        
        DELETE FROM TBRC_T_TRADE_SUM
        WHERE  VERI = '002'
           AND TRADE_DATE >= V_STAT_START_DT;
        V_PROCESS := '411';
    
        --42) �����ο� ��������� �����Ѵ�.
        --421)���ֺ� �����ο� ��� ����
        INSERT INTO TBRC_T_TRADE_RACE_SUM
            (TRADE_DATE, RACE_NO, COMM_NO, CARD_USER_TYPE, MEET_CD, 
             FEE, CNT, K_FEE, K_CNT, UPDT_DTM)
        WITH X AS 
        (
            SELECT SUBSTR(TRADE_DATE,1,8) AS TRADE_DATE         -- ���Ӵ�ü: ��ü�ο�(�ߺ�����)
                       ,SUBSTR(TRADE_DATE,9,4) AS TRADE_TIME
                       ,COMM_NO
                       ,'1'      AS CARD_USER_TYPE
                       ,0        AS T_FEE                                       
                       ,COUNT(*) AS T_CNT
                       ,0        AS K_FEE
                       ,0        AS K_CNT   
            FROM TBRC_T_TRADE
            WHERE 1=1
            AND    CARD_USER_TYPE = '1'
            AND   TRADE_DATE >= V_STAT_START_DT
            GROUP BY SUBSTR(TRADE_DATE,1,8),SUBSTR(TRADE_DATE,9,4), COMM_NO        
            UNION ALL                            
            SELECT SUBSTR(TRADE_DATE,1,8) AS TRADE_DATE         -- �Ϲ�: �����߻��ο�(�ߺ�����)
                       ,SUBSTR(TRADE_DATE,9,4) AS TRADE_TIME
                       ,COMM_NO
                       ,'4' AS CARD_USER_TYPE
                       ,SUM(REQUEST_FEE) AS T_FEE
                       ,COUNT(*)  AS T_CNT
                       ,0 AS K_FEE
                       ,0 AS K_CNT         
            FROM TBRC_T_TRADE
            WHERE 1=1
            AND    REQUEST_FEE > 0
            AND   TRADE_DATE >= V_STAT_START_DT
            GROUP BY SUBSTR(TRADE_DATE,1,8),SUBSTR(TRADE_DATE,9,4), COMM_NO        
            UNION ALL                                    
            SELECT SUBSTR(TRADE_DATE,1,8) AS TRADE_DATE         -- ����ī�� : �ߺ������ο�
                       ,MIN(SUBSTR(TRADE_DATE,9,4)) AS TRADE_TIME
                       ,COMM_NO
                       ,'5' AS CARD_USER_TYPE
                       ,0 AS FEE
                       ,1 AS CNT
                       ,0 AS K_FEE
                       ,0 AS K_CNT          
            FROM TBRC_T_TRADE T
            WHERE 1=1
            AND    CARD_USER_TYPE = '5'
            AND   TRADE_DATE >= V_STAT_START_DT
            GROUP BY SUBSTR(TRADE_DATE,1,8), COMM_NO, CARD_ID
            UNION ALL
            SELECT TRADE_DATE
                  ,SUBSTR(TRADE_TIME,1,4) AS TRADE_TIME
                  ,'00'
                  ,'4'
                  ,0 AS T_FEE
                  ,0 AS T_CNT
                  ,SUM(FEE) AS K_FEE
                  ,SUM(CNT) AS K_CNT
            FROM   TBRC_T_TRADE_KM
            WHERE  TRADE_DATE  >= V_STAT_START_DT
            GROUP BY TRADE_DATE, SUBSTR(TRADE_TIME,1,4)
            UNION ALL
           SELECT SDATE AS TRADE_DATE
                  ,SUBSTR(FTIME,1,4) AS TRADE_TIME
                  ,'98'
                  ,'4'
                  ,0 AS T_FEE
                  ,0 AS T_CNT
                  ,SUM(AMNT) AS K_FEE
                  ,SUM(QNTY) AS K_CNT
            FROM    TBRC_T_TRADE_MSR
            WHERE  SDATE  >= V_STAT_START_DT
            and use_cd ='10'
            GROUP BY SDATE, SUBSTR(FTIME,1,4)
        )
        SELECT TRADE_DATE, R.RACE_NO, COMM_NO,  CARD_USER_TYPE, MEET_CD, 
               SUM(T_FEE) + SUM(K_FEE) AS FEE, SUM(T_CNT)+SUM(K_CNT) AS CNT, 
               SUM(K_FEE) AS K_FEE, SUM(K_CNT) AS K_CNT, SYSDATE
        FROM X, VW_RACE_INFO_END2 R
        WHERE X.TRADE_DATE = R.RACE_DAY
        AND   X.TRADE_TIME >= R.STR_TM
        AND   X.TRADE_TIME <  R.END_TM
        AND   X.TRADE_DATE NOT IN (SELECT TRADE_DATE FROM TBRC_T_TRADE_RACE_SUM)
        GROUP BY TRADE_DATE, COMM_NO, R.RACE_NO, CARD_USER_TYPE, MEET_CD;
        V_PROCESS := '421';
        
        --422)��¥�� �����ο� ��� ����
        /** 
        INSERT INTO TBRC_T_TRADE_SUM
        SELECT TRADE_DATE, COMM_NO, CARD_USER_TYPE,SUM(FEE), SUM(CNT), '002', NULL, SYSDATE
        FROM   TBRC_T_TRADE_RACE_SUM
        WHERE  TRADE_DATE > V_STAT_START_DT;
        AND    TRADE_DATE NOT IN (SELECT TRADE_DATE FROM TBRC_T_TRADE_SUM WHERE TRADE_DATE > V_STAT_START_DT)
        GROUP BY TRADE_DATE, COMM_NO, CARD_USER_TYPE;
        **/
        INSERT INTO TBRC_T_TRADE_SUM(
                              TRADE_DATE, COMM_NO, CARD_USER_TYPE, FEE, CNT, VERI)   
        SELECT SUBSTR(TRADE_DATE,1,8) AS TRADE_DATE         -- ���Ӵ�ü: ��ü�ο�(�ߺ�����)
                   ,COMM_NO
                   ,'1' AS CARD_USER_TYPE
                   ,0 AS FEE                                       
                   ,COUNT(*)  AS CNT
                   ,'002' AS VERI         
        FROM TBRC_T_TRADE
        WHERE 1=1
        AND    CARD_USER_TYPE = '1'
     --   AND    SUBSTR(TRADE_DATE,1,8) NOT IN (SELECT TRADE_DATE FROM TBRC_T_TRADE_SUM WHERE TRADE_DATE >= V_STAT_START_DT )
        AND   TRADE_DATE >= V_STAT_START_DT
        GROUP BY SUBSTR(TRADE_DATE,1,8), COMM_NO
        UNION ALL                            
        SELECT SUBSTR(TRADE_DATE,1,8) AS TRADE_DATE         -- �Ϲ�: �����߻��ο�(�ߺ�����)
                   ,COMM_NO
                   ,'4' AS CARD_USER_TYPE
                   ,SUM(REQUEST_FEE) AS FEE
                   ,COUNT(*)  AS CNT
                   ,'002' AS VERI            
        FROM TBRC_T_TRADE
        WHERE 1=1
        AND    REQUEST_FEE > 0
     --   AND    SUBSTR(TRADE_DATE,1,8) NOT IN (SELECT TRADE_DATE FROM TBRC_T_TRADE_SUM WHERE TRADE_DATE >= V_STAT_START_DT )
        AND   TRADE_DATE >= V_STAT_START_DT
        GROUP BY SUBSTR(TRADE_DATE,1,8), COMM_NO
        UNION ALL                            
        SELECT SUBSTR(TRADE_DATE,1,8) AS TRADE_DATE         -- ����ī�� : �ߺ������ο�
                   ,COMM_NO
                   ,'5' AS CARD_USER_TYPE
                   ,0 AS FEE
                   ,COUNT(DISTINCT CARD_ID)  AS CNT
                   ,'002' AS VERI            
        FROM TBRC_T_TRADE
        WHERE 1=1
        AND    CARD_USER_TYPE = '5'
     --   AND    SUBSTR(TRADE_DATE,1,8) NOT IN (SELECT TRADE_DATE FROM TBRC_T_TRADE_SUM WHERE TRADE_DATE >= V_STAT_START_DT )
        AND   TRADE_DATE >= V_STAT_START_DT
        GROUP BY SUBSTR(TRADE_DATE,1,8), COMM_NO;
        V_PROCESS := '422';
 
        --43) ��������� �߸ű� ��������� �����Ѵ�.
        DELETE FROM TBRC_T_TRADE_KM_SUM
        WHERE    TRADE_DATE LIKE TO_CHAR(SYSDATE, 'YYYY')||'%';
        V_PROCESS := '43';
        
        --44) ��������� �߸ű� ��������� �����Ѵ�.
        INSERT INTO TBRC_T_TRADE_KM_SUM
               (TRADE_DATE, FEE, CNT, UPDT_ID, UPDT_DTM)
        SELECT TRADE_DATE, SUM(FEE), SUM(CNT), 'BATCH', SYSDATE
        FROM   TBRC_T_TRADE_KM
        WHERE  TRADE_DATE LIKE TO_CHAR(SYSDATE, 'YYYY')||'%'
        GROUP BY TRADE_DATE;
        V_PROCESS := '44';


        --45) �̻縮����� �߸ű� ��������� �����Ѵ�.
        DELETE FROM TBRC_T_TRADE_MSR_SUM
        WHERE    TRADE_DATE LIKE TO_CHAR(SYSDATE, 'YYYY')||'%';
        V_PROCESS := '45';
        
        --46) �̻縮����� �߸ű� ��������� �����Ѵ�.
        INSERT INTO TBRC_T_TRADE_MSR_SUM
               (TRADE_DATE, FEE, CNT, UPDT_ID, UPDT_DTM)
        SELECT SDATE TRADE_DATE,  sum(Amnt),  sum(qnty), 'BATCH', SYSDATE
        FROM   TBRC_T_TRADE_MSR
        WHERE  SDATE LIKE TO_CHAR(SYSDATE, 'YYYY')||'%'
           and use_cd ='10'
        GROUP BY SDATE;
        V_PROCESS := '46';


        
    COMMIT;

    SP_BTC_LOG_SEQ('012','I','SP_IMPORT_TMONEY_TRADE','END','', V_LOG_SEQ, V_LOG_SERL_NO);
    
    --�����¼����� �ڷḦ �����´�.
    SP_IMPORT_FIXEDSEAT_TRADE();
    
    RETURN;


    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            
            V_ERR_CODE := SQLCODE();
            V_ERR_MESG := SQLERRM;
            
            SP_BTC_LOG_SEQ('012','E','SP_IMPORT_TMONEY_TRADE','[PROC:'||V_PROCESS||']'||'ERROR CODE:'||V_ERR_CODE || ' ERROR LOG:'||V_ERR_MESG,'', V_LOG_SEQ, V_LOG_SERL_NO);
            RETURN;
END;
/
