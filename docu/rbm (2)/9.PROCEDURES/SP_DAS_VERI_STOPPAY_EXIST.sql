CREATE OR REPLACE PROCEDURE USRBM.SP_DAS_VERI_STOPPAY_EXIST IS
/******************************************************************************
- ������,��      : 2011-11-11
- ���α׷���     : SP_DAS_VERI_STOPPAY_EXIST
- ���α׷�Ÿ��   : procedure
- ���           : DAS�� ������������Ÿ�� �����������̺��� �����ϴ� ����
- IN  �μ�       :

- ���μ���

- ���ν����� ���� ���� 9��30�п� �����ٸ����� �����Ѵ�.
******************************************************************************/

CURSOR STOPPAY_CURSOR IS
      -- ���ű�
        SELECT
                  ORG.PUBL_DT
                , ORG.TSN_NO
                , ORG.TIC_TYPE_CD
          FROM TBRD_STOPPAY_CNCL_CNTNT ORG
         WHERE ORG.STOPPAY_DT = TO_CHAR(SYSDATE-1,'YYYYMMDD')
           AND REGEXP_LIKE (ORG.TIC_STAS, '^(00[14])$')
           AND ORG.TIC_TYPE_CD = '002'
           AND NOT EXISTS(  SELECT 1
                             FROM
                                (
                                    SELECT
                                            A.SESS_NO
                                          , A.YEAR_TOTE || A.DATE_TOTE AS PUBL_DT
                                          , SUBSTR(A.TSN,4,1)||'-'|| SUBSTR(A.TSN,5,4)||'-'|| SUBSTR(A.TSN,9,4)||'-'|| SUBSTR(A.TSN,13,4)   AS TSN_NO
                                          , '002' AS TIC_TYPE_CD -- ���ű�
                                          , B.LOCKED_STATUS AS TIC_STAS
                                          , B.STOPPAY_DT
                                     FROM   BASETOTE.TF_VOUCHER_SELL_01@DW01LINK A
                                          , (
                                                     SELECT TSN_ORIGINATING_SESSION_NO,
                                                            YEAR_TOTE,
                                                            DATE_TOTE,
                                                            LOCKED_STATUS,
                                                            TSN,
                                                            YEAR_TOTE || DATE_TOTE AS STOPPAY_DT
                                                       FROM BASETOTE.TF_UPD_REC_LOCK_STATUS_01@DW01LINK
                                                      WHERE YEAR_TOTE = TO_CHAR(SYSDATE-1,'YYYY')
                                                        AND DATE_TOTE = TO_CHAR(SYSDATE-1,'MMDD')
                                                        AND LOCKED_STATUS = 1
                                            ) B
                                    WHERE 1=1
                                      AND A.SESS_NO   = B.TSN_ORIGINATING_SESSION_NO
                                      AND A.TSN       = B.TSN
                                      AND A.TSN       = B.TSN
                                 )   GU  WHERE GU.PUBL_DT = ORG.PUBL_DT
                                           AND GU.TSN_NO  = ORG.TSN_NO
                                           AND GU.STOPPAY_DT = ORG.STOPPAY_DT
                           )
        UNION ALL
         -- ���ֱ�
         SELECT
                  ORG.PUBL_DT
                , ORG.TSN_NO
                , ORG.TIC_TYPE_CD
          FROM TBRD_STOPPAY_CNCL_CNTNT ORG
         WHERE ORG.STOPPAY_DT = TO_CHAR(SYSDATE-1,'YYYYMMDD')
           AND REGEXP_LIKE (ORG.TIC_STAS, '^(00[14])$')
           AND ORG.TIC_TYPE_CD = '001'
           AND NOT EXISTS(  SELECT 1
                             FROM
                                (
                                    SELECT
                                            A.SESS_NO
                                          , A.YEAR_TOTE || A.DATE_TOTE AS PUBL_DT
                                          , SUBSTR(A.TSN,4,1)||'-'|| SUBSTR(A.TSN,5,4)||'-'|| SUBSTR(A.TSN,9,4)||'-'|| SUBSTR(A.TSN,13,4)   AS TSN_NO
                                          , '001' AS TIC_TYPE_CD -- ���ֱ�
                                          , B.LOCKED_STATUS AS TIC_STAS
                                          , B.STOPPAY_DT
                                     FROM   BASETOTE.TF_SELL_01@DW01LINK A
                                          , (
                                                     SELECT TSN_ORIGINATING_SESSION_NO,
                                                            YEAR_TOTE,
                                                            DATE_TOTE,
                                                            LOCKED_STATUS,
                                                            TSN,
                                                            YEAR_TOTE || DATE_TOTE AS STOPPAY_DT
                                                       FROM BASETOTE.TF_UPD_REC_LOCK_STATUS_01@DW01LINK
                                                      WHERE YEAR_TOTE = TO_CHAR(SYSDATE-1,'YYYY')
                                                        AND DATE_TOTE = TO_CHAR(SYSDATE-1,'MMDD')
                                                        AND LOCKED_STATUS = 1
                                            ) B
                                    WHERE 1=1
                                      AND A.SESS_NO   = B.TSN_ORIGINATING_SESSION_NO
                                      AND A.TSN       = B.TSN
                                      AND A.TSN       = B.TSN
                                 )   GU  WHERE GU.PUBL_DT = ORG.PUBL_DT
                                           AND GU.TSN_NO  = ORG.TSN_NO
                                           AND GU.STOPPAY_DT = ORG.STOPPAY_DT
                           );

     v_err_code number;
     v_err_mesg varchar(255);
BEGIN


      SP_BTC_LOG('004','I','SP_DAS_VERI_STOPPAY_EXIST','START','');
      -- Cursor�� FOR������ �����Ų��
       FOR STOPPAY IN STOPPAY_CURSOR LOOP

            UPDATE TBRD_STOPPAY_CNCL_CNTNT SET
                VERI_RSLT_CD    = '001'
               ,VERI_RSLT_DT    = TO_CHAR(SYSDATE,'YYYYMMDD')
               ,UPDT_ID         = 'DAS'
               ,UPDT_DT         = SYSDATE
            WHERE PUBL_DT = STOPPAY.PUBL_DT
              AND TSN_NO  = STOPPAY.TSN_NO;

       END LOOP;

       SP_BTC_LOG('004','I','SP_DAS_VERI_STOPPAY_EXIST','END','');
       COMMIT;



   EXCEPTION
    WHEN OTHERS THEN
       v_err_code := SQLCODE();
       v_err_mesg := SQLERRM;

       ROLLBACK;
       SP_BTC_LOG('004','E','SP_DAS_VERI_STOPPAY_EXIST','ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'');

       RETURN;

END SP_DAS_VERI_STOPPAY_EXIST;
/
