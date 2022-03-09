CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_USER_RBM

IS
/******************************************************************************
- 개발자,일      : yslee 2009.08.12
- 프로그램명     : SP_IMPORT_USER_RBM
- 프로그램타입   : procedure
- 기능           : 그룹웨어 사용자정보를 경주사업관리시스템 DB에 저장한다.
- IN  인수       :

- 프로세스
    1. 기존 사용자정보 삭제
    2. 그룹웨어 정보를 조회해서 TBJK_USER 테이블에 입력


- 프로시져는 매일 밤 8시에 작동되도록 한다.(스케줄러 등록)
******************************************************************************/
    v_job_date DATE;
    v_err_code NUMBER;
    v_err_mesg VARCHAR(255);
    V_BRNC_LIST VARCHAR(500);
    

BEGIN
    DBMS_OUTPUT.ENABLE;

    SP_BTC_LOG('001','I','SP_IMPORT_USER_RBM','START','');

    -- 1. 기존 사용자 데이터 삭제
    DELETE
      FROM  TBRK_USER
     WHERE  GBN   = '005';

    -- 2. 임시 사용자중 SOSFO에 등록된 사용자 삭제
    DELETE
      FROM  TBRK_USER A
     WHERE  EXISTS (
                    SELECT 1
                      FROM HDPORTAL.V_USER_INFO@JUPITERLINK B
                     WHERE B.GROUP_ID     LIKE 'GA12%'
                       AND A.USER_ID  = B.USER_SPEC_ID
                    );

    -- 3. 그룹웨어 사용자 데이터 입력
    INSERT  INTO    TBRK_USER 
                           (GBN, USER_ID, PSWD, USER_NM, EMAIL, TEL_NO, HP_NO, FLOC, FGRADE, BRNC_CD, ASSO_CD, 
                           DEPT_CD, TEAM_CD, TEAM_DETL_CD, ASSO_NM, DEPT_NM, TEAM_NM, ORD_NO, INST_ID, INST_DT, 
                           UPDT_ID, UPDT_DT, RETIRE_GBN, DISP_ORDER, JOIN_DATE)    
          SELECT    '005' GBN,
                    USER_SPEC_ID,
                    USER_PASSWORD,
                    USER_NAME,
                    USER_EMAIL_ID||'@kspo.or.kr' USER_EMAIL,
                    USER_TEL,
                    USER_CELL_TEL,
                    USER_TITLE_NAME,
                    USER_TODO_NAME,
                    COALESCE(
                            (SELECT CD
                              FROM TBRK_SPEC_CD
                             WHERE GRP_CD   = '018'
                               AND CD_TERM2 = A.GROUP_ID
                            ),
                            (SELECT CD_TERM1
                              FROM TBRK_SPEC_CD
                              WHERE CD = A.GROUP_ID
                                AND GRP_CD   = '090'),
                            '00'
                         ) ,
                    C.ASSO_CD||'0000',
                    A.DOC_GROUP_ID,
                    A.GROUP_ID,
                    A.GROUP_SPEC_ID,
                    C.ASSO_NAME,
                    B.DOC_GROUP_NAME,
                    B.GROUP_NAME,
                    DISPLAY_ORDER,
                    '',
                    SYSDATE,
                    '',
                    SYSDATE,
                    '0', -- 재직(0:재직, 1:퇴사)
                    A.DISPLAY_ORDER,
                    A.JOIN_DATE
            FROM    HDPORTAL.V_USER_INFO@JUPITERLINK A,
                       HDPORTAL.V_GROUP@JUPITERLINK B,
                    (
                    SELECT  SUBSTR(GROUP_ID, 1, 4) ASSO_CD,
                                GROUP_NAME ASSO_NAME
                      FROM  HDPORTAL.V_GROUP@JUPITERLINK
                     WHERE  GROUP_ID LIKE '%0000') C
            WHERE A.GROUP_ID        = B.GROUP_ID
              AND A.ADD_POS_SEQNO   = '0'
              AND A.DOC_GROUP_ID    = B.DOC_GROUP_ID
              AND B.GROUP_ID     LIKE C.ASSO_CD||'%'
          --    AND (A.user_spec_id != '30179' AND A.doc_group_id != 'GA126H00')
           --   AND A.user_spec_id != '30179' 
              AND A.GROUP_ID     LIKE 'GA12%';

    COMMIT;
    SP_BTC_LOG('001','I','SP_IMPORT_USER_RBM','END','');
    
    -- 지점별 담당자 중복 여부 체크
    V_BRNC_LIST := NULL;
    WITH x AS (
            SELECT  C.CD_NM AS BRNC_NM, COUNT(*) AS CNT
            FROM   TBRK_ALARM A, TBRK_USER U, TBRK_SPEC_CD C
            WHERE   A.ALARM_CD = '008'
            AND      A.ALARM_GBN = '001'
            AND      A.RECV_ID = U.USER_ID
            AND      C.GRP_CD = '060'
            AND      U.BRNC_CD = C.CD
            GROUP BY C.CD_NM
            HAVING COUNT(*) > 1
    )
    SELECT LTRIM(SYS_CONNECT_BY_PATH(BRNC_NM,'|'),'|') || '|' AS brn_list
    INTO  V_BRNC_LIST
    FROM (
             SELECT row_number() OVER(ORDER BY BRNC_NM) rn,
             COUNT(*) OVER() AS CNT,
             BRNC_NM
             FROM x
             )
    WHERE LEVEL = CNT
    START WITH RN = 1
    CONNECT BY PRIOR RN = RN -1;         
             
    IF V_BRNC_LIST != NULL THEN
        SP_BTC_LOG('001','E','SP_IMPORT_USER_RBM','부서별 부품입고담당자 중복'||CHR(13)||V_BRNC_LIST,'');
    END IF;
    
    RETURN;


    EXCEPTION
        WHEN NO_DATA_FOUND THEN
             RETURN;
        WHEN OTHERS THEN
            v_err_code := SQLCODE();
            v_err_mesg := SQLERRM;
            ROLLBACK;
            SP_BTC_LOG('001','E','SP_IMPORT_USER_RBM','ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'');
            RETURN;
END;
/
