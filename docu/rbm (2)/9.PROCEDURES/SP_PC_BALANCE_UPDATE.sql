CREATE OR REPLACE PROCEDURE USRBM.SP_PC_BALANCE_UPDATE(P_BEGINDAY IN TBJI_PC_BALANCE.RACE_DAY%TYPE)
IS     
    -- TBJI_PC_BALANCE 테이블의 전회불총액인 NOTYET_TOT_AMT 컬럼을 특정일자 이후에 대해 보정해준다.

    CURSOR BALCUR IS 
        SELECT RACE_DAY, NEXT_RACE_DAY
          FROM (
             SELECT
                 RACE_DAY,
                 LAG(RACE_DAY, 1, '') OVER (ORDER BY RACE_DAY DESC) NEXT_RACE_DAY
             FROM TBJI_PC_BALANCE
             WHERE RACE_DAY >= P_BEGINDAY
             GROUP BY RACE_DAY
             ORDER BY RACE_DAY
        )
        WHERE NEXT_RACE_DAY IS NOT NULL
        ;    

    BALCUR_ROW BALCUR%ROWTYPE;
                
BEGIN                       
    OPEN BALCUR;
                    
    LOOP            
        FETCH BALCUR INTO BALCUR_ROW;
        EXIT WHEN BALCUR%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE(BALCUR%ROWCOUNT || ' : ' || BALCUR_ROW.RACE_DAY || ' => ' || BALCUR_ROW.NEXT_RACE_DAY);
                                
        UPDATE TBJI_PC_BALANCE A
           SET NOTYET_TOT_AMT =    (
                   SELECT B.NOTYET_TOT_AMT - B.CYCLE_AMT - B.BOAT_AMT - B.END_AMT + B.NOTYET_AMT
                   FROM TBJI_PC_BALANCE B
                  WHERE B.TYPE_NO = A.TYPE_NO
                    AND B.SELL_CD = A.SELL_CD
                    AND B.RACE_DAY = BALCUR_ROW.RACE_DAY
                )
        WHERE A.RACE_DAY = BALCUR_ROW.NEXT_RACE_DAY;
    END LOOP;                       
    
    COMMIT WORK;    
    CLOSE BALCUR;                       
END;
/
