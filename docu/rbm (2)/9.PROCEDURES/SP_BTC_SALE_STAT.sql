CREATE OR REPLACE PROCEDURE USRBM.SP_BTC_SALE_STAT

IS
/******************************************************************************
- ������,��      : jsshin 2013.11.14
- ���α׷���     : SP_BATCH_SALE_STAT
- ���α׷�Ÿ��   : procedure
- ���           :  KSPO-NET���� ������ ����ڷḦ ����
- IN  �μ�       :

- ���μ���
    1. ���� ������� ����
    2. ���������� ����ڷ� ����(Insert)


- ���ν����� ���� �� 10�ÿ� �۵��ǵ��� �Ѵ�.(�����ٷ� ���)
******************************************************************************/
    V_JOB_DATE DATE;
    V_ERR_CODE NUMBER;
    V_ERR_MESG VARCHAR(255);


BEGIN
    DBMS_OUTPUT.ENABLE;

    SP_BTC_LOG('016','I','SP_BATCH_SALE_STAT','START','');

    -- 1. ���� ������� �ֱ�ȸ�� �������� ����
    DELETE  FROM  TBRS_SALES_RSLT_RTLY;
    
    -- 2. �ֱ� 5ȸ�� �������� ����
    DELETE  FROM  TBRS_SALES_RSLT_RTLY_5TMS;
    
    -- 3. �������� ����(�ֱ�ȸ�� ��������)
     INSERT INTO TBRS_SALES_RSLT_RTLY
     (JOB_TYPE, STND_YEAR, MEET_NM, TMS, CUR_TMS_AMT,PREV_TMS_AMT, PREV_TMS_RATIO, LAST_YEAR_TMS_AMT, LAST_YEAR_TMS_RATIO,  UPDT_DT)     
     WITH X AS (                                                                                                                   
            SELECT *
            FROM (                                   
                         SELECT MEET_CD, STND_YEAR, TMS, ROW_NUMBER() OVER (PARTITION BY MEET_CD ORDER BY STND_YEAR DESC, TMS DESC) AS RNUM                                                                                    
                         FROM   VW_SDL_INFO                                                                                                            
                         WHERE (MEET_CD, STND_YEAR, TMS) NOT IN (SELECT MEET_CD, STND_YEAR, TMS FROM VW_SDL_INFO WHERE RACE_DAY = TO_CHAR(SYSDATE, 'YYYYMMDD'))                            
                         AND     MEET_CD IN ('001','003')     
                         AND     STND_YEAR BETWEEN TO_CHAR(SYSDATE,'YYYY') -1 AND TO_CHAR(SYSDATE,'YYYY')
                   )
            WHERE RNUM  = 1                                                                                                          
     )                                                                                                                             
     SELECT 1 AS JOB_TYPE                                                                                                          
           ,STND_YEAR                                                                                                              
           ,MEET_NM                                                                                                                
           ,TMS                                                                                                                    
           ,TRIM(TO_CHAR(CUR_TMS_AMT,'999,999')) AS CUR_TMS_AMT                                                                
           ,TRIM(TO_CHAR(PREV_TMS_AMT, '999,999')) AS PREV_TMS_AMT                                                                       
           ,ROUND((CUR_TMS_AMT - PREV_TMS_AMT)/PREV_TMS_AMT*100,1) AS PREV_TMS_RATIO                                               
           ,TRIM(TO_CHAR(LAST_YEAR_TMS_AMT,'999,999')) AS LAST_YEAR_TMS_AMT                                                              
           ,ROUND((CUR_TMS_AMT - LAST_YEAR_TMS_AMT)/LAST_YEAR_TMS_AMT*100,1) AS LAST_YEAR_TMS_RATIO     
           ,SYSDATE                           
     FROM (                                                                                                                        
     SELECT DECODE(B.MEET_CD,'001','���','003','����') AS MEET_NM                                                                   
           ,ROUND(SUM(CASE WHEN B.STND_YEAR=X.STND_YEAR AND B.TMS = X.TMS THEN DIV_TOTAL END)/100000000) AS CUR_TMS_AMT            
           ,ROUND(SUM(CASE WHEN B.STND_YEAR=X.STND_YEAR AND B.TMS = (X.TMS -1) THEN DIV_TOTAL END)/100000000)  AS PREV_TMS_AMT     
           ,ROUND(SUM(CASE WHEN B.STND_YEAR=X.STND_YEAR -1 AND B.TMS = X.TMS THEN DIV_TOTAL END)/100000000)  AS LAST_YEAR_TMS_AMT  
           ,MIN(X.STND_YEAR) AS STND_YEAR                                                                                          
           ,MIN(X.TMS) AS TMS                                                                                                      
     FROM  X, TBES_SDL_DT_SUM B                                                                                                    
     WHERE B.STND_YEAR BETWEEN X.STND_YEAR -1 AND X.STND_YEAR                                                                      
     AND   B.MEET_CD  IN ('001','003')                                                                                             
     AND   X.MEET_CD = B.MEET_CD                                                                                                   
     GROUP BY B.MEET_CD                                                                                                            
     ORDER BY 1                                                                                                                    
     )                                                                                                                             
     UNION ALL                                                                                                         
     SELECT 2 AS JOB_TYPE                                                                                              
           ,STND_YEAR                                                                                                  
           ,MEET_NM                                                                                                    
           ,TMS                                                                                                        
           ,TRIM(TO_CHAR(PLAN_AMT,'999,999')) AS PLAN_AMT                                                                    
           ,TRIM(TO_CHAR(CUR_CUM_AMT,'999,999')) AS CUR_CUM_AMT                                                              
           ,ROUND(CUR_CUM_AMT/PLAN_AMT*100,1) AS CUR_CUM_RATIO                                                         
           ,TRIM(TO_CHAR(LAST_YEAR_CUM_AMT,'999,999')) AS LAST_YEAR_CUM_AMT                                                  
           ,ROUND((CUR_CUM_AMT-LAST_YEAR_CUM_AMT)/LAST_YEAR_CUM_AMT*100,1) AS LAST_YEAR_CUM_RATIO         
           ,SYSDATE             
     FROM (                                                                                                            
             SELECT DECODE(B.MEET_CD,'001','���','003','����') AS MEET_NM                                               
                   ,ROUND(TO_NUMBER(CD_NM)/100) AS PLAN_AMT                                                            
                   ,ROUND(SUM(CASE WHEN B.STND_YEAR=X.STND_YEAR THEN DIV_TOTAL END)/100000000) AS CUR_CUM_AMT          
                   ,ROUND(SUM(CASE WHEN B.STND_YEAR=X.STND_YEAR -1 AND B.TMS <= X.TMS THEN DIV_TOTAL END)/100000000)   
                                                                                               AS LAST_YEAR_CUM_AMT    
                   ,MIN(X.STND_YEAR) AS STND_YEAR                                                                      
                   ,MIN(X.TMS) AS TMS                                                                                  
             FROM X                                                                                                    
                 ,TBRK_SPEC_CD C                                                                                       
                 ,TBES_SDL_DT_SUM B                                                                                    
             WHERE B.STND_YEAR BETWEEN X.STND_YEAR -1 AND X.STND_YEAR                                                  
             AND   B.MEET_CD = X.MEET_CD                                                                               
             AND   X.MEET_CD    = C.CD_TERM1                                                                           
             AND   X.STND_YEAR = C.CD_TERM2                                                                            
             AND   C.GRP_CD = '163'                                                                                    
             GROUP BY DECODE(B.MEET_CD,'001','���','003','����'), CD_NM                                                 
             ORDER BY 1                                                                                                 
             )                                                                                                          
     ORDER BY 1,3;
     
     
     -- 4. �ֱ� ������� 5ȸ�� �������� ����
     INSERT INTO TBRS_SALES_RSLT_RTLY_5TMS(MEET_CD, TMS, CUR_YEAR_TMS_AMT, LAST_YEAR_TMS_AMT,  UPDT_DT, ODR_NO)    
    SELECT MEET_CD, 
                TMS||'ȸ��',
                ROUND(DIV_TOTAL/100000000) AS CUR_YEAR_TMS_AMT,
                (SELECT ROUND(SUM(DIV_TOTAL)/100000000) FROM VW_SDL_DT_SUM WHERE MEET_CD = A.MEET_CD AND STND_YEAR = A.STND_YEAR -1 AND TMS = A.TMS) AS LAST_YEAR_TMS_AMT,
                SYSDATE,
                RNUM
    FROM (
            SELECT MEET_CD,
                        STND_YEAR,
                        TMS, 
                        STND_YEAR||TO_CHAR(TMS,'00') AS YEARTMS, 
                        SUM(DIV_TOTAL) AS DIV_TOTAL, ROW_NUMBER() OVER (PARTITION BY MEET_CD ORDER BY STND_YEAR DESC, TMS DESC) AS RNUM                                        
            FROM   VW_SDL_DT_SUM                                                                             
            WHERE MEET_CD IN ('001','003')           
            AND     (MEET_CD, STND_YEAR, TMS) NOT IN (SELECT MEET_CD, STND_YEAR, TMS FROM VW_SDL_INFO WHERE RACE_DAY = TO_CHAR(SYSDATE, 'YYYYMMDD'))         
            GROUP BY STND_YEAR, MEET_CD, TMS
            ) A
    WHERE RNUM < 6        
    ORDER BY MEET_CD, RNUM DESC;                                                                                 
     

    COMMIT;
    SP_BTC_LOG('016','I','SP_BATCH_SALE_STAT','END','');
    RETURN;


    EXCEPTION
        WHEN OTHERS THEN
            V_ERR_CODE := SQLCODE();
            V_ERR_MESG := SQLERRM;
            ROLLBACK;
            SP_BTC_LOG('016','E','SP_BATCH_SALE_STAT','ERROR CODE:'||V_ERR_CODE || ' ERROR LOG:'||V_ERR_MESG,'');
            RETURN;

END;
/
