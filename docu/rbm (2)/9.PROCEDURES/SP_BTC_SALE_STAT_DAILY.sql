CREATE OR REPLACE PROCEDURE USRBM.SP_BTC_SALE_STAT_DAILY

IS
/******************************************************************************
- 개발자,일      : jsshin 2014.8.19
- 프로그램명     : SP_BATCH_SALE_STAT_DAILY
- 프로그램타입   : procedure
- 기능           :  KSPO-NET에서 보여줄 통계자료를 생성
- IN  인수       :

- 프로세스
    1. 기존 통계정보 삭제
    2. 매출정보로 통계자료 생성(Insert)


- 프로시져는 매일 아침 7시에 작동되도록 한다.(스케줄러 등록)
******************************************************************************/
    V_JOB_DATE DATE;
    V_ERR_CODE NUMBER;
    V_ERR_MESG VARCHAR(255);


BEGIN
    DBMS_OUTPUT.ENABLE;

    SP_BTC_LOG('016','I','SP_BATCH_SALE_STAT_DAILY','START','');

    -- 1. 기존 회차 매출액 및 누적 매출액 삭제
    DELETE  FROM  TBRS_SALES_RSLT_SUMMRY;
    
    -- 2. 차트용 자료(회차별 매출액) 자료 삭제
    DELETE FROM TBRS_SALES_RSLT_SUMMRY_CHART;
    
    -- 3. 지점매출비교(차트용) 자료 삭제
    DELETE FROM TBRS_SALES_RSLT_SUMMRY_BRNC;
    
    -- 4. 회차 매출액 및 누적 매출액 자료 입력                          
     INSERT INTO TBRS_SALES_RSLT_SUMMRY
     (MEET_CD, TMS, THIS_TMS_AMT, PREV_TMS_AMT, TMS_DIFF_SIGN, TMS_DIFF, YEAR_PLAN, YEAR_AMT, YEAR_ARCH_RATE, PREV_YEAR_AMT, YEAR_DIFF_SIGN, YEAR_DIFF_RATE, INST_DT)     
     WITH SALES_AMT                                                                       
     AS (                                                                                                                       
         -- 당회차 매출액                                                                                                                                                                                                                            
         SELECT RN AS JOB_TYPE                                                                                                      
                  ,A.MEET_CD                                                                                                        
                  ,A.STND_YEAR                                                                                                      
                  ,A.TMS                                                                                                            
                 ,SUM(DIV_TOTAL) AS DIV_TOTAL
         FROM VW_SDL_DT_SUM_GSUM A,                                                                                                 
                 (SELECT MEET_CD, STND_YEAR, TMS, RN                                                                                
                     FROM (                                                                                                         
                             SELECT MEET_CD, STND_YEAR, TMS,                                                                        
                                    ROW_NUMBER() OVER (PARTITION BY MEET_CD ORDER BY STND_YEAR DESC, TMS DESC) AS RN                
                             FROM VW_SDL_INFO                                                                                       
                             WHERE RACE_DAY < TO_CHAR(SYSDATE - DECODE(TO_CHAR(SYSDATE, 'd'), 4, 1, 5, 2, 7, 1, 1, 2, 0), 'yyyymmdd')     
                              AND   MEET_CD IN ('001','003')                                                                        
                              AND   STND_YEAR BETWEEN TO_CHAR(SYSDATE,'YYYY') -1 AND TO_CHAR(SYSDATE,'YYYY')                        
                              GROUP BY MEET_CD, STND_YEAR, TMS                                                                      
                              )                                                                                                     
                     WHERE RN < 3 ) B                                                                                               
         WHERE A.MEET_CD = B.MEET_CD                                                                                                
         AND    A.STND_YEAR = B.STND_YEAR                                                                                           
         AND    A.TMS          = B.TMS                                                                                              
         GROUP BY  A.STND_YEAR, A.MEET_CD, A.TMS, B.RN                                                                              
         UNION ALL                                                                                                                  
         -- 당해년도 매출목표                                                                                                       
         SELECT 3 AS TMS                                                                                                            
                  ,SUBSTR(CD,5,3)                                                                                                   
                  ,SUBSTR(CD,1,4)                                                                                                   
                  ,0                                                                                                                
                  ,TO_NUMBER(CD_NM) * 1000000 AS DIV_TOTAL                                                                          
         FROM TBRK_SPEC_CD                                                                                                          
         WHERE GRP_CD = '163'                                                                                                       
         AND    CD LIKE  TO_CHAR(SYSDATE, 'YYYY')||'%'                                                                              
         UNION ALL                                                                                                                  
         -- 연간 매출누적금액                                                                                                       
         SELECT DECODE(STND_YEAR,  TO_CHAR(SYSDATE,'YYYY'), 4, 5) TMS                                                               
                  ,MEET_CD                                                                                                          
                  ,STND_YEAR                                                                                                        
                  ,0                                                                                                                
                 ,SUM(DIV_TOTAL) AS DIV_TOTAL                                                                                       
         FROM VW_SDL_DT_SUM_GSUM A                                                                                                    
         WHERE EXISTS (SELECT * 
                            FROM VW_SALES_TMS_CUM B
                            WHERE A.MEET_CD = B.MEET_CD
                            AND    A.STND_YEAR = B.STND_YEAR
                            AND    A.TMS = B.TMS)                                                                             
         GROUP BY STND_YEAR, MEET_CD                                                                                                 
         )                                                                                                                          
         SELECT MEET_CD                                                                                                             
                  ,TMS                                                                                                              
                  ,COL1 AS THIS_TMS_AMT                                                                                                             
                  ,COL2 AS PREV_TMS_AMT                                                                                                             
                  ,CASE WHEN COL1 < COL2 THEN '△'                                                                                  
                          WHEN COL1 = COL2 THEN '-'                                                                                 
                          ELSE '_'                                                                                                   
                          END AS TMS_DIFF_SIGN                                                                                             
                 ,DECODE(COL2,0,0,ROUND(ABS(COL2-COL1)/COL2*100,1)) AS TMS_DIFF                                                       
                 ,TO_CHAR(COL4,'99,999') AS YEAR_PLAN                                                                                                              
                 ,TO_CHAR(COL5,'99,999') AS YEAR_AMT                                                                                                              
                 ,DECODE(COL4,0,0,ROUND(ABS(COL5/COL4)*100,1)) AS YEAR_ARCH_RATE                                                              
                 ,TO_CHAR(COL7,'99,999') AS PREV_YEAR_AMT                                                                                                              
                  ,CASE WHEN COL5 < COL7 THEN '△'                                                                                  
                          WHEN COL5 = COL7 THEN '-'                                                                                 
                          ELSE '_'                                                                                                   
                          END AS YEAR_DIFF_SIGN                                                                                             
                 ,DECODE(COL7,0,0,ROUND(ABS(COL5-COL7)/COL7*100,1)) AS YEAR_DIFF_RATE                                               
                 ,SYSDATE                                                                                       
         FROM (                                                                                                                     
                 SELECT MEET_CD                                                                                                     
                          ,MAX(TMS) AS TMS                                                                                          
                          ,ROUND(SUM(DECODE(JOB_TYPE,1,DIV_TOTAL))/100000000,0) AS COL1                                               
                          ,ROUND(SUM(DECODE(JOB_TYPE,2,DIV_TOTAL))/100000000,0) AS COL2                                               
                          ,ROUND(SUM(DECODE(JOB_TYPE,3,DIV_TOTAL))/100000000,0) AS COL4                                               
                          ,ROUND(SUM(DECODE(JOB_TYPE,4,DIV_TOTAL))/100000000,0) AS COL5                                               
                          ,ROUND(SUM(DECODE(JOB_TYPE,5,DIV_TOTAL))/100000000,0) AS COL7                                               
                 FROM SALES_AMT                                                                                                     
                 GROUP BY MEET_CD                                                                                                   
                 )  ;
     
     
     -- 5. 차트용 자료(회차별 매출액) 자료 입력
     INSERT INTO TBRS_SALES_RSLT_SUMMRY_CHART
     ( MEET_CD, TMS, CUR_YEAR_TMS_AMT, LAST_YEAR_TMS_AMT, INST_DT ) 
     WITH X AS (                                                                                                   
      SELECT MEET_CD, STND_YEAR, MAX(TMS) AS TMS                                                                   
      FROM   VW_SDL_INFO                                                                                           
      WHERE RACE_DAY < TO_CHAR(SYSDATE, 'yyyymmdd')                                                                
      AND     MEET_CD IN ('001','003')                                                                             
      AND     STND_YEAR = TO_CHAR(SYSDATE,'YYYY')                                                                  
      GROUP BY MEET_CD, STND_YEAR                                                                                  
      )
      SELECT MEET_CD, TMS, CUR_YEAR_TMS_AMT, LAST_YEAR_TMS_AMT, SYSDATE
      FROM (                                                                                                             
              SELECT B.MEET_CD AS MEET_CD                                                                                  
                    ,B.RACE_DY ||'\n'||B.TMS ||'회' AS TMS                                                                
                    ,NVL(ROUND(SUM(CASE WHEN B.STND_YEAR=X.STND_YEAR THEN DIV_TOTAL END)/100000000),0) AS CUR_YEAR_TMS_AMT        
                    ,NVL(ROUND(SUM(CASE WHEN B.STND_YEAR=X.STND_YEAR -1 THEN DIV_TOTAL END)/100000000),0)  AS LAST_YEAR_TMS_AMT   
                    ,MAX(RACE_DAY) AS RACE_DAY
              FROM  X, (                                                                                                   
                        SELECT A.STND_YEAR, A.MEET_CD, A.TMS                                                                 
                               ,TO_CHAR(TO_DATE(RACE_DAY,'YYYYMMDD'), 'DY') AS RACE_DY                                        
                               ,TO_CHAR(TO_DATE(RACE_DAY,'YYYYMMDD')-1, 'D') AS RACE_D                                        
                               ,SUM(DIV_TOTAL) AS DIV_TOTAL
                               ,B.RACE_DAY                                                                    
                        FROM   TBES_SDL_DT_SUM A, VW_SDL_INFO B                                                            
                        WHERE  A.STND_YEAR BETWEEN TO_CHAR(SYSDATE, 'YYYY') -1 AND TO_CHAR(SYSDATE,'YYYY')                 
                        AND    A.MEET_CD IN ('001','003')                                                                     
                         AND    A.MEET_CD   = B.MEET_CD                                                                     
                        AND    A.STND_YEAR = B.STND_YEAR                                                                   
                        AND    A.TMS       = B.TMS                                                                         
                        AND    A.DAY_ORD   = B.DAY_ORD                                                                     
                        GROUP BY A.STND_YEAR, A.MEET_CD, A.TMS, B.RACE_DAY                                                 
                        ) B                                                                                                
              WHERE 1=1                                                                                                    
              AND   X.MEET_CD = B.MEET_CD                                                                                  
              AND   B.TMS BETWEEN X.TMS -3 AND X.TMS                                                                       
              AND   B.RACE_DY||B.TMS != TO_CHAR(SYSDATE, 'DY')||X.TMS                                                      
              GROUP BY B.MEET_CD, B.TMS, B.RACE_DY, B.RACE_D                                                               
              ORDER BY B.MEET_CD, B.TMS, B.RACE_D
              )
      WHERE RACE_DAY >= TO_CHAR(SYSDATE,'YYYY')||'0101' ;                                                                                 
     
    -- 6. 지점별 매출자료 (차트용) 자료 입력
    INSERT INTO TBRS_SALES_RSLT_SUMMRY_BRNC
     ( MEET_CD, COMM_NO, COMM_NM, CUR_TMS_AMT, PREV_TMS_AMT, CUR_TMS, PREV_TMS, INST_DT )
     WITH X AS (                                                                                                                      
         SELECT MEET_CD, STND_YEAR, TMS, RNUM                                                                                         
         FROM (                                                                                                                       
                 SELECT MEET_CD, STND_YEAR, TMS, ROW_NUMBER() OVER(PARTITION BY MEET_CD ORDER BY STND_YEAR DESC, TMS DESC) AS RNUM    
                 FROM  (SELECT MEET_CD, STND_YEAR, TMS                                                                                
                           FROM VW_SDL_INFO                                                                                           
                           WHERE RACE_DAY < TO_CHAR(SYSDATE - DECODE(TO_CHAR(SYSDATE, 'd'), 4,1,5,2,7,1,1,2, 0), 'yyyymmdd')          
                           GROUP BY MEET_CD, STND_YEAR, TMS)                                                                          
                 WHERE MEET_CD IN ('001','003')                                                                                       
                 )                                                                                                                    
         WHERE RNUM < 3                                                                                                               
         )                                                                                                                            
     SELECT A.MEET_CD, A.COMM_NO, C.CD_NM AS COMM_NM,                                                                                 
              ROUND(SUM(DECODE(X.RNUM, 1, DIV_TOTAL, 0))/100000000,1) AS CUR_TMS_AMT,                                            
              ROUND(SUM(DECODE(X.RNUM, 2, DIV_TOTAL, 0))/100000000,1) AS PREV_TMS_AMT,
              TRIM(TO_CHAR(MAX(X.TMS)))||'회차' AS CUR_TMS,
              TRIM(TO_CHAR(MIN(X.TMS)))||'회차' AS PREV_TMS,
              SYSDATE                                            
     FROM  (                                                                                                                          
               SELECT STND_YEAR, MEET_CD, TMS,                                                                                        
                         CASE WHEN SELL_CD IN ('02','04') THEN '29'                                                                   
                                WHEN COMM_NO < 10 THEN '01'                                                                           
                                 ELSE COMM_NO END AS COMM_NO,                                                                         
                         SUM(DIV_TOTAL) AS DIV_TOTAL                                                                                  
               FROM VW_SDL_DT_SUM_GSUM                                                                                                
               WHERE STND_YEAR BETWEEN TO_CHAR(SYSDATE,'YYYY') -1 AND TO_CHAR(SYSDATE,'YYYY')                                         
               AND    MEET_CD IN ('001','003')          
               AND    SELL_CD NOT IN ('02','04')                                                                              
               GROUP BY STND_YEAR, MEET_CD, TMS,                                                                                      
                         CASE WHEN SELL_CD IN ('02','04') THEN '29'                                                                   
                                WHEN COMM_NO < 10 THEN '01'                                                                           
                                 ELSE COMM_NO END                                                                                     
               ) A, X, TBRK_SPEC_CD C                                                                                                 
     WHERE A.MEET_CD = X.MEET_CD                                                                                                      
     AND    A.STND_YEAR = X.STND_YEAR                                                                                                 
     AND    A.TMS = X.TMS                                                                                                             
     AND    A.COMM_NO = C.CD(+)                                                                                                       
     AND    C.GRP_CD(+) = '060'                                                                                                       
     GROUP BY A.MEET_CD, A.COMM_NO, C.CD_NM                                                                                           
     ORDER BY 1, 4 DESC   ;
     
    COMMIT;
    SP_BTC_LOG('016','I','SP_BATCH_SALE_STAT_DAILY','END','');
    RETURN;


    EXCEPTION
        WHEN OTHERS THEN
            V_ERR_CODE := SQLCODE();
            V_ERR_MESG := SQLERRM;
            ROLLBACK;
            SP_BTC_LOG('016','E','SP_BATCH_SALE_STAT_DAILY','ERROR CODE:'||V_ERR_CODE || ' ERROR LOG:'||V_ERR_MESG,'');
            RETURN;

END;
/
