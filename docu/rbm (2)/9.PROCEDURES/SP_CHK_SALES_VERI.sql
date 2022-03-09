CREATE OR REPLACE PROCEDURE USRBM.SP_CHK_SALES_VERI IS
V_DAY    NUMBER;            -- ����(��:4, ��:5, ��:6, ��:7, ��:1)
V_MESG   VARCHAR(120);      -- ���ڸ޼���
V_RET    VARCHAR(1);        -- �����
V_SEND_YN VARCHAR(1);
V_LOG_SEQ NUMBER(15);
V_LOG_SERL_NO NUMBER(3);
V_CHOCHA_ERR_CNT NUMBER(3);
V_DW_SALES_CNT NUMBER(3);
V_PROC_SEQ NUMBER(3);

    CURSOR CUR_SALES_VERI IS
    SELECT '�����ڷ� ���� ���� ' AS MESG, 
           A.GUBUN_DESC||'-'||RACE_DAY AS RACE_INFO,
           A.GITA||','||A.KTAX||','||A.PAYO||','||A.RESU||','||A.SATL||','||A.SELL||','||A.TELM||','||A.WINN AS VERI_CNT,
           A.FILE_VERI_DT AS VERI_DT
    FROM (
           SELECT   DECODE(A.GUBUN,'001','���','003','����','����') AS GUBUN_DESC,
                    A.RACE_DAY,                     -- �������                        
                    'GITA:'||DECODE(B.GITA, NULL, '', GITA_FILE_ROW_CNT||'��') GITA,  -- GITA ���� ���ε� ���
                    'KTAX:'||DECODE(B.KTAX, NULL, '', KTAX_FILE_ROW_CNT||'��') KTAX,  -- KTAX ���� ���ε� ���
                    'PAYOFFS:'||DECODE(B.PAYO, NULL, '', PAYO_FILE_ROW_CNT||'��') PAYO,  -- PAYO ���� ���ε� ���
                    'RESULTS:'||DECODE(B.RESU, NULL, '', RESU_FILE_ROW_CNT||'��') RESU,  -- RESU ���� ���ε� ���
                    'SATLIT:'||DECODE(B.SATL, NULL, '', SATL_FILE_ROW_CNT||'��') SATL,  -- SATL ���� ���ε� ���
                    'SELLST:'||DECODE(B.SELL, NULL, '', SELL_FILE_ROW_CNT||'��') SELL,  -- SELL ���� ���ε� ���
                    'TELMP:'||DECODE(B.TELM, NULL, '', TELM_FILE_ROW_CNT||'��') TELM,  -- TELM ���� ���ε� ���
                    'WINNMP:'||DECODE(B.WINN, NULL, '', WINN_FILE_ROW_CNT||'��') WINN,  -- WINN ���� ���ε� ���            
                    NVL(C.FILE_VERI,'002') AS FILE_VERI,    -- ���� ���ε� ���
                    NVL(C.VERI,'002') AS VERI,
                    C.FILE_VERI_DT                                                  -- ���� ���ε� ��¥
            FROM
            (
                 SELECT STND_YEAR,
                        SUBSTR(RACE_DAY,5,7) AS DT,
                        RACE_DAY,        
                        MAX(DECODE(MEET_CD,'003','003','001')) AS GUBUN,
                        AVG(CASE WHEN MEET_CD = '001' THEN TMS END) AS G_TMS,
                        AVG(CASE WHEN MEET_CD = '001' THEN DAY_ORD END) AS G_DAY,
                        AVG(CASE WHEN MEET_CD = '002' THEN TMS END) AS C_TMS,
                        AVG(CASE WHEN MEET_CD = '002' THEN DAY_ORD END) AS C_DAY,
                        AVG(CASE WHEN MEET_CD = '003' THEN TMS END) AS M_TMS,
                        AVG(CASE WHEN MEET_CD = '003' THEN DAY_ORD END) AS M_DAY,
                        AVG(CASE WHEN MEET_CD = '004' THEN TMS END) AS B_TMS,
                        AVG(CASE WHEN MEET_CD = '004' THEN DAY_ORD END) AS B_DAY
                  FROM  VW_SDL_INFO                                 -- ��������                             
                 WHERE  STND_YEAR = TO_CHAR(SYSDATE, 'YYYY')                    --����  0:STND_YEAR ���س⵵
                   AND  RACE_DAY  = TO_CHAR(SYSDATE, 'YYYYMMDD')
                GROUP BY STND_YEAR, SUBSTR(RACE_DAY,5,7),RACE_DAY
                UNION ALL 
                 SELECT STND_YEAR,
                        DT,
                        '' AS RACE_DAY,
                        MEET_CD, 
                        NULL AS G_TMS,
                        NULL AS G_DAY,
                        NULL AS C_TMS,
                        NULL AS C_DAY,
                        NULL AS M_TMS,
                        NULL AS M_DAY,
                        NULL AS B_TMS,
                        NULL AS B_DAY
                  FROM  TBJI_PC_FILE                                        -- ��������                             
                 WHERE  STND_YEAR = TO_CHAR(SYSDATE, 'YYYY')            --���� 1:STND_YEAR ���س⵵
                   AND  RACE_DT   = TO_CHAR(SYSDATE, 'YYYYMMDD')
                   AND  MEET_CD     = '999'     -- ����  
                GROUP BY STND_YEAR, DT,MEET_CD
            )A,
            (
                SELECT B.MEET_CD,
                        B.STND_YEAR, 
                        B.DT,
                        B.RACE_DT,
                        MAX(CASE WHEN B.FILE_CD='001' THEN B.FILE_CD END) AS GITA,
                        MAX(CASE WHEN B.FILE_CD='001' THEN TRIM(TO_CHAR(B.FILE_CNT,'999,999,999')) END) AS GITA_FILE_ROW_CNT,
                        MAX(CASE WHEN B.FILE_CD='001' THEN B.FILE_PATH END) AS GITA_FILE_PATH,
                        MAX(CASE WHEN B.FILE_CD='001' THEN B.FILE_NM END) AS GITA_FILE_NM,
                                    
                        MAX(CASE WHEN B.FILE_CD='002' THEN B.FILE_CD END) AS KTAX,
                        MAX(CASE WHEN B.FILE_CD='002' THEN TRIM(TO_CHAR(B.FILE_CNT,'999,999,999')) END) AS KTAX_FILE_ROW_CNT,
                        MAX(CASE WHEN B.FILE_CD='002' THEN B.FILE_PATH END) AS KTAX_FILE_PATH,
                        MAX(CASE WHEN B.FILE_CD='002' THEN B.FILE_NM END) AS KTAX_FILE_NM,
                                    
                        MAX(CASE WHEN B.FILE_CD='003' THEN B.FILE_CD END) AS PAYO,
                        MAX(CASE WHEN B.FILE_CD='003' THEN TRIM(TO_CHAR(B.FILE_CNT,'999,999,999')) END) AS PAYO_FILE_ROW_CNT,
                        MAX(CASE WHEN B.FILE_CD='003' THEN B.FILE_PATH END) AS PAYO_FILE_PATH,
                        MAX(CASE WHEN B.FILE_CD='003' THEN B.FILE_NM END) AS PAYO_FILE_NM,
                                    
                        MAX(CASE WHEN B.FILE_CD='004' THEN B.FILE_CD END) AS RESU,
                        MAX(CASE WHEN B.FILE_CD='004' THEN TRIM(TO_CHAR(B.FILE_CNT,'999,999,999')) END) AS RESU_FILE_ROW_CNT,
                        MAX(CASE WHEN B.FILE_CD='004' THEN B.FILE_PATH END) AS RESU_FILE_PATH,
                        MAX(CASE WHEN B.FILE_CD='004' THEN B.FILE_NM END) AS RESU_FILE_NM,
                                    
                        MAX(CASE WHEN B.FILE_CD='005' THEN B.FILE_CD END) AS SATL,
                        MAX(CASE WHEN B.FILE_CD='005' THEN TRIM(TO_CHAR(B.FILE_CNT,'999,999,999')) END) AS SATL_FILE_ROW_CNT,
                        MAX(CASE WHEN B.FILE_CD='005' THEN B.FILE_PATH END) AS SATL_FILE_PATH,
                        MAX(CASE WHEN B.FILE_CD='005' THEN B.FILE_NM END) AS SATL_FILE_NM,
                                    
                                    
                        MAX(CASE WHEN B.FILE_CD='006' THEN B.FILE_CD END) AS SELL,
                        MAX(CASE WHEN B.FILE_CD='006' THEN TRIM(TO_CHAR(B.FILE_CNT,'999,999,999')) END) AS SELL_FILE_ROW_CNT,
                        MAX(CASE WHEN B.FILE_CD='006' THEN B.FILE_PATH END) AS SELL_FILE_PATH,
                        MAX(CASE WHEN B.FILE_CD='006' THEN B.FILE_NM END) AS SELL_FILE_NM,
                                    
                        MAX(CASE WHEN B.FILE_CD='007' THEN B.FILE_CD END) AS TELM,
                        MAX(CASE WHEN B.FILE_CD='007' THEN TRIM(TO_CHAR(B.FILE_CNT,'999,999,999')) END) AS TELM_FILE_ROW_CNT,
                        MAX(CASE WHEN B.FILE_CD='007' THEN B.FILE_PATH END) AS TELM_FILE_PATH,
                        MAX(CASE WHEN B.FILE_CD='007' THEN B.FILE_NM END) AS TELM_FILE_NM,
                                    
                        MAX(CASE WHEN B.FILE_CD='008' THEN B.FILE_CD END) AS WINN,
                        MAX(CASE WHEN B.FILE_CD='008' THEN TRIM(TO_CHAR(B.FILE_CNT,'999,999,999')) END) AS WINN_FILE_ROW_CNT,
                        MAX(CASE WHEN B.FILE_CD='008' THEN B.FILE_PATH END) AS WINN_FILE_PATH,
                        MAX(CASE WHEN B.FILE_CD='008' THEN B.FILE_NM END) AS WINN_FILE_NM
                                                   
                  FROM  TBJI_PC_FILE B
                 WHERE  1=1
                   AND B.STND_YEAR = TO_CHAR(SYSDATE, 'YYYY')           --����  2:STND_YEAR ���س⵵
                   AND RACE_DT     = TO_CHAR(SYSDATE, 'YYYYMMDD')
                 GROUP BY B.MEET_CD,
                        B.STND_YEAR, 
                        B.DT,
                        B.RACE_DT
            )B, TBJI_PC_FILE_VERI C                         
            WHERE 1=1
            AND A.GUBUN   = B.MEET_CD(+)
            AND A.STND_YEAR   = B.STND_YEAR(+)
            AND A.DT    = B.DT(+)
            AND A.GUBUN   = C.MEET_CD(+)
            AND A.STND_YEAR   = C.STND_YEAR(+)
            AND A.DT    = C.DT(+)
            ORDER BY A.DT DESC                
         ) A
    WHERE  1=1     
      AND  (FILE_VERI != '001' OR VERI != '001');

     CUR_SALES_VERI_ROW  CUR_SALES_VERI%ROWTYPE;
     
BEGIN

    SELECT SQ_TBRK_BTC_LOG.NEXTVAL
      INTO V_LOG_SEQ
    FROM DUAL;
    
    V_PROC_SEQ := 0;
    V_LOG_SERL_NO := 0;    
    SP_BTC_LOG_SEQ('006','I','SP_CHK_SALES_VERI','START','', V_LOG_SEQ, V_LOG_SERL_NO);
   
   -- ���� ��ȸ
   SELECT TO_CHAR(SYSDATE, 'D')
   INTO   V_DAY
   FROM   DUAL;   

    V_PROC_SEQ := 10;
   IF V_DAY IN (1,6,7, 4,5) THEN      -- ���,����
        OPEN CUR_SALES_VERI;
        LOOP
            FETCH CUR_SALES_VERI INTO CUR_SALES_VERI_ROW;
            V_PROC_SEQ := 11;
            EXIT WHEN CUR_SALES_VERI%NOTFOUND;
            
            V_PROC_SEQ := 12;
            SP_ALARM_SEND_SMS( '011', CUR_SALES_VERI_ROW.MESG||CUR_SALES_VERI_ROW.RACE_INFO, 'admin', 'RSY6010', 'SP_CHK_SALES_VERI');
            SP_BTC_LOG_SEQ('006','E','SP_CHK_SALES_VERI',CUR_SALES_VERI_ROW.MESG||CUR_SALES_VERI_ROW.RACE_INFO, '', V_LOG_SEQ, V_LOG_SERL_NO);                             
        END LOOP;
        CLOSE CUR_SALES_VERI;   
   END IF;
   V_PROC_SEQ := 20;
   
    SP_BTC_LOG_SEQ('006','I','SP_CHK_SALES_VERI','END','', V_LOG_SEQ, V_LOG_SERL_NO);
   
   IF V_DAY IN (4,5) THEN      -- ����
            -- ����������� �и����� �ʰų� ������ �߻��� ���
            V_CHOCHA_ERR_CNT := 0;
            V_PROC_SEQ := 21;
            SELECT  COUNT(*) AS CNT
            --SELECT RACE_DAY, SELL_CD, SUM(NET_AMT), SUM(DIV_TOTAL)
            INTO     V_CHOCHA_ERR_CNT
            FROM (
                    SELECT B.RACE_DAY
                               ,A.SELL_CD
                               ,SUM(NET_AMT) NET_AMT
                               ,0 AS DIV_TOTAL 
                    FROM VW_PC_PAYOFFS A
                            , VW_SDL_INFO B
                    WHERE A.MEET_CD = B.MEET_CD
                    AND    A.STND_YEAR = B.STND_YEAR
                    AND   A.TMS = B.TMS
                    AND  A.DAY_ORD = B.DAY_ORD
                    AND  B.MEET_CD = '003'
                    AND  A.SELL_CD IN ('02', '04')
                    AND  B.RACE_DAY = TO_CHAR(SYSDATE, 'YYYYMMDD')
                    GROUP BY B.RACE_DAY, A.SELL_CD
                    UNION ALL
                    SELECT B.RACE_DAY
                               ,A.SELL_CD
                               ,0 NET_AMT
                               ,SUM(DIV_TOTAL) DIV_TOTAL
                    FROM VW_SDL_DT_SUM A
                           , VW_SDL_INFO B
                    WHERE A.MEET_CD = B.MEET_CD
                    AND    A.STND_YEAR = B.STND_YEAR
                    AND   A.TMS = B.TMS
                    AND  A.DAY_ORD = B.DAY_ORD
                    AND  B.MEET_CD = '003'
                    AND  A.SELL_CD IN ('02', '04')
                    AND  B.RACE_DAY = TO_CHAR(SYSDATE, 'YYYYMMDD')
                    GROUP BY B.RACE_DAY, A.SELL_CD
                    )
            GROUP BY RACE_DAY, SELL_CD  
            HAVING SUM(NET_AMT) <> SUM(DIV_TOTAL);
            V_PROC_SEQ := 22;
            IF V_CHOCHA_ERR_CNT > 0 THEN
                  V_PROC_SEQ := 23;
                  SP_ALARM_SEND_SMS( '011', '��������� ����', 'admin', 'RSY6010', 'SP_CHK_SALES_VERI');
                  SP_BTC_LOG_SEQ('006','E','SP_CHK_SALES_VERI','��������� ����', '', V_LOG_SEQ, V_LOG_SERL_NO);                             
            END IF;
   END IF;
   
   V_PROC_SEQ := 30;
   -- â��, �λ�� �������� ���� ����üũ(DW���� ���� �����´�.)
   V_DW_SALES_CNT := 0;
   SELECT COUNT(*)
   INTO V_DW_SALES_CNT  
   FROM TBES_DW_SALES
   WHERE RACE_DAY = TO_CHAR(SYSDATE, 'YYYYMMDD');
   V_PROC_SEQ := 31;
   
   IF V_DW_SALES_CNT = 0 THEN
      V_PROC_SEQ := 32;
      SP_ALARM_SEND_SMS( '011', 'DW���������������� ����:�ڷ����', 'admin', 'RSY6010', 'SP_CHK_SALES_VERI');
      SP_BTC_LOG_SEQ('006','E','SP_CHK_SALES_VERI','DW���������������� ����:�ڷ����', '', V_LOG_SEQ, V_LOG_SERL_NO);                             
   END IF;
   
   RETURN;
                
   EXCEPTION
     WHEN NO_DATA_FOUND THEN
         NULL;
     WHEN OTHERS THEN
        SP_BTC_LOG_SEQ('006','E','SP_CHK_SALES_VERI','PROC:'+TO_CHAR(V_PROC_SEQ)||','||SQLERRM, '', V_LOG_SEQ, V_LOG_SERL_NO);
   
       -- Consider logging the error and then re-raise
       RAISE;
END SP_CHK_SALES_VERI;
/
