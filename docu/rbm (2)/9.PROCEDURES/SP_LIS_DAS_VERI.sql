CREATE OR REPLACE PROCEDURE USRBM.SP_LIS_DAS_VERI
(
    V_RACE_DAY IN VARCHAR2
)
IS
/******************************************************************************
- ������,��      : 2011-12-10
- ���α׷���     : SP_LIS_DAS_VERI
- ���α׷�Ÿ��   : procedure
- ���           : LIS ������ DAS �� ����
- IN  �μ�       :

- ���μ���

- ���ν����� ���� ���� 9�ÿ� �����ٸ����� �����Ѵ�.
******************************************************************************/
N_COUNT  NUMBER:=0;

v_err_code number;
v_err_mesg varchar(255);

CURSOR LIS_DAS_CURSOR IS

        SELECT  /* LIS_DAS_CURSOR */
                MEET_CD,  -- ��� ���� ���� �ڵ�
                STND_YEAR,     -- ���س⵵
                DT,            -- ����
                AA.COMM_NO,        -- ���� ���� ���� �ڵ�
                BRNC_CD,        -- ���� ���� ���� �ڵ�
                GROSS_SALES,    -- ���ݼ���
                CANCELS,         -- ��� �ݾ�
                TOT_SALES,         -- �Ǽ��Ծ�
                CASHES,            -- �����
                NET_INCOME,     -- ���Աݾ�
                DRAWS,
                RETURNS,
                BALANCE,         -- ���Աݾ�
                DAS_GROSS_SALE,    -- DAS ���ݼ���
                DAS_CANCEL,        -- DAS ��� �ݾ�
                DAS_TOT_SALE,    -- DAS �Ǽ��Ծ�
                DAS_CASHE,        -- DAS �����
                DAS_NET_INCOME,    -- DAS ���Աݾ�
                DAS_DRAW,
                DAS_RTRN,
                DAS_BALANCE,    -- DAS ���ݼ���
                CASE
                WHEN (AA.GROSS_SALES=BB.DAS_GROSS_SALE)       -- ���ݼ���
                     AND (AA.CANCELS=BB.DAS_CANCEL)           -- ��� �ݾ�
                     AND (AA.TOT_SALES=BB.DAS_TOT_SALE)      -- �Ǽ��Ծ�
                     AND (AA.CASHES=BB.DAS_CASHE)            -- �����
                     AND (AA.NET_INCOME=BB.DAS_NET_INCOME)    -- ���Աݾ�
                     AND (AA.BALANCE=BB.DAS_BALANCE)          -- ���Աݾ�
                THEN '001'
                ELSE '002'
                END AS TF,    -- ��ġ ���� �ڵ�

                CASE
                WHEN (AA.GROSS_SALES=BB.DAS_GROSS_SALE)       -- ���ݼ���
                     AND (AA.CANCELS=BB.DAS_CANCEL)           -- ��� �ݾ�
                     AND (AA.TOT_SALES=BB.DAS_TOT_SALE)      -- �Ǽ��Ծ�
                     AND (AA.CASHES=BB.DAS_CASHE)            -- �����
                     AND (AA.NET_INCOME=BB.DAS_NET_INCOME)    -- ���Աݾ�
                     AND (AA.BALANCE=BB.DAS_BALANCE)          -- ���Աݾ�
                THEN '��ġ'
                ELSE '����ġ'
                END AS TF_DESC -- ��ġ ���� ����
            FROM
            (
                SELECT
                    MEET_CD,  -- ��� ���� ���� �ڵ�
                    STND_YEAR,     -- ���س⵵
                    DT,            -- ����
                    BRNC_CD,
                    TO_NUMBER(BRNC_CD) AS COMM_NO,        -- ���� �ڵ�
                    SUM(GROSS_SALES) AS GROSS_SALES,    -- ���ݼ���
                    SUM(CANCELS) AS CANCELS,     -- ��� �ݾ�
                    SUM(TOT_SALES) AS TOT_SALES,     -- �Ǽ��Ծ�
                    SUM(CASHES) AS CASHES,        -- �����
                    SUM(NET_INCOME) AS NET_INCOME,     -- ���Աݾ�
                    SUM(DRAWS) AS DRAWS,
                    SUM(RETURNS) AS RETURNS,
                    SUM(BALANCE) AS BALANCE     -- ���Աݾ�
                FROM
                (
                   SELECT
                    MEET_CD,  -- ��� ���� ���� �ڵ�
                        STND_YEAR,     -- ���س⵵
                        DT,            -- ����
                        CASE WHEN SELL_CD='03' AND  BRNC_CD='08' THEN '98'
                              ELSE BRNC_CD
                        END BRNC_CD,        -- ���� �ڵ�
                       GROSS_SALES,    -- ���ݼ���
                       CANCELS,     -- ��� �ݾ�
                       TOT_SALES,     -- �Ǽ��Ծ�
                       CASHES,        -- �����
                       NET_INCOME,     -- ���Աݾ�
                       DRAWS,
                       RETURNS,
                       BALANCE     -- ���Աݾ�
                  FROM TBRD_LIS_FILE A -- LIS FILE ���̺�

                  WHERE 1=1
                  AND STND_YEAR = SUBSTR(V_RACE_DAY, 1,4) -- 0:STND_YEAR
                  AND DT        = SUBSTR(V_RACE_DAY, 5,4) -- 1:DT '1009'
                  AND BRNC_CD NOT IN (06)
                  ORDER BY BRNC_CD
                )AA
                GROUP BY AA.MEET_CD,  -- ��� ���� ���� �ڵ�
                        AA.STND_YEAR,     -- ���س⵵
                        AA.DT,
                        AA.BRNC_CD

            )AA,
            (

               SELECT COMM_NO,
                   SUM(GROSS_SALE) AS DAS_GROSS_SALE,
                   SUM(CANCEL)     AS DAS_CANCEL,
                   SUM(TOT_SALE)   AS DAS_TOT_SALE,
                   SUM(CASHE)      AS DAS_CASHE,
                   SUM(NET_INCOME) AS DAS_NET_INCOME,
                   SUM(DRAW)       AS DAS_DRAW,
                   SUM(RTRN)       AS DAS_RTRN,
                   SUM(BALANCE)    AS DAS_BALANCE
                FROM
                (
                SELECT
                    C.SELL_CD,
                    CASE WHEN C.SELL_CD=3 AND C.COMM_NO IN (1,2) THEN 98
                       ELSE C.COMM_NO
                       END AS COMM_NO,
                    C.COMM_NAME,
                    C.DIV_NAME,
                    D.GROSS_SALE,
                    D.CANCEL,
                    D.TOT_SALE,
                    D.CASHE,
                    D.NET_INCOME,
                    D.DRAW,
                    D.RTRN,
                    D.BALANCE
                FROM
                (
                        SELECT B.SELL_CD, B.COMM_NO, B.DIV_NO,
                                NVL(A.SOLD_AMT,0)+NVL(B.SOLD_AMT,0)		GROSS_SALE,
                                NVL(A.CANCEL_AMT,0)     CANCEL,
                                NVL(A.SOLD_AMT,0)+NVL(B.SOLD_AMT,0)-NVL(A.CANCEL_AMT,0)	TOT_SALE,
                                NVL(A.CASH_AMT,0)+NVL(B.CASH_AMT,0)	CASHE,
                                NVL(A.SOLD_AMT,0)+NVL(B.SOLD_AMT,0)-NVL(A.CANCEL_AMT,0)-NVL(A.CASH_AMT,0)-NVL(B.CASH_AMT,0)	NET_INCOME,
                                NVL(B.DRAW_AMT,0)	DRAW,
                                NVL(B.RTRN_AMT,0)	RTRN,
                                NVL(A.SOLD_AMT,0)+NVL(B.SOLD_AMT,0)-NVL(A.CANCEL_AMT,0)-NVL(A.CASH_AMT,0)-NVL(B.CASH_AMT,0)+NVL(B.DRAW_AMT,0)-NVL(B.RTRN_AMT,0) BALANCE
                        FROM
                        (
                            SELECT SELL_CD, COMM_NO, DIV_NO,
                                    NVL(SUM(SOLD_AMT),0)                                                       SOLD_AMT,
                                    SUM(NVL(CASH_AMT,0)+NVL(IRS_CASH_AMT,0))                                   CASH_AMT,
                                    SUM(NVL(SUP_CAN_AMT,0)+NVL(TOT_CAN_AMT,0)+NVL(PARTIAL_CAN_AMT,0))          CANCEL_AMT
                            FROM BASETOTE.PC_TELMP@DW01LINK
                            WHERE YEAR_DATE = V_RACE_DAY  -- 2:RACE_DAY '20111009'
                                AND WIN_TYPE IN (1,3)
                            GROUP BY SELL_CD, COMM_NO, DIV_NO) A,
                            (SELECT SELL_CD, COMM_NO, DIV_NO,
                                    NVL(SUM(SOLD_AMT),0)                                                       SOLD_AMT,
                                    NVL(SUM(CASH_AMT),0)                                                       CASH_AMT,
                                    NVL(SUM(DRAW_AMT),0)                                                       DRAW_AMT,
                                    NVL(SUM(RTRN_AMT),0)                                                       RTRN_AMT
                            FROM BASETOTE.PC_WINNMP@DW01LINK
                            WHERE YEAR_DATE = V_RACE_DAY-- 3:RACE_DAY '20111009'
                            GROUP BY SELL_CD, COMM_NO, DIV_NO) B
                            WHERE A.SELL_CD(+)=B.SELL_CD
                                AND A.COMM_NO(+)=B.COMM_NO
                                AND A.DIV_NO(+)=B.DIV_NO
                            ORDER BY B.SELL_CD, B.COMM_NO, B.DIV_NO
                        )    D,
                        BASETOTE.CONF_DIV_INFO@DW01LINK C
                        WHERE D.SELL_CD=C.SELL_CD
                           AND D.COMM_NO=C.COMM_NO
                           AND D.DIV_NO=C.DIV_NO
                           AND C.TO_YEAR_DATE>= V_RACE_DAY          --===========> �߰�
                           AND C.FROM_YEAR_DATE <= V_RACE_DAY       --===========> �߰�
			    )AA
			    GROUP BY AA.COMM_NO
			    ORDER BY AA.COMM_NO
			)BB
			WHERE AA.COMM_NO=BB.COMM_NO(+)
			ORDER BY AA.BRNC_CD;


BEGIN
    -- Cursor�� FOR������ �����Ų��
    SP_BTC_LOG('007','I','SP_LIS_DAS_VERI','START',' DT:'||V_RACE_DAY);


    FOR LIS_I IN LIS_DAS_CURSOR LOOP

        UPDATE  TBRD_LIS_FILE
        SET  VERI = LIS_I.TF        --  ����
            ,VERI_DT	  = SYSDATE  -- �����Ͻ�
            ,UPDT_ID      = 'SYSTEM'        -- ������ID
            ,UPDT_DT      = SYSDATE  -- �����Ͻ�
        WHERE  1=1
          AND  MEET_CD   = LIS_I.MEET_CD     -- ��� ���� ���� �ڵ�
          AND  STND_YEAR = LIS_I.STND_YEAR     -- ���س⵵
          AND  DT      = LIS_I.DT    -- ����
          AND  BRNC_CD = LIS_I.BRNC_CD    -- ���� �ڵ�
        ;



    END LOOP;
    COMMIT;
    SP_BTC_LOG('007','I','SP_LIS_DAS_VERI','END','');

    EXCEPTION
     WHEN NO_DATA_FOUND THEN
        v_err_code := SQLCODE();
        v_err_mesg := SQLERRM;
        ROLLBACK;
        SP_BTC_LOG('007','E','SP_LIS_DAS_VERI','ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'');
     WHEN OTHERS THEN
        v_err_code := SQLCODE();
        v_err_mesg := SQLERRM;
        ROLLBACK;
        SP_BTC_LOG('007','E','SP_LIS_DAS_VERI','ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'');
       -- Consider logging the error and then re-raise


END SP_LIS_DAS_VERI;
/
