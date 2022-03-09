CREATE OR REPLACE PROCEDURE USRBM.sp_parts_adj IS
V_SEQ NUMBER(15);
v_u_seq number(15);

    CURSOR CUR_parts_adj IS
    
    SELECT parts_cd, brnc_cd, dt req_dt,  acpter_id req_no, out_cnt AS req_cnt, 
           '003' AS aprv_stas, out_cnt AS aprv_cnt, rcver_id AS APRV_OFIR_NO, '재고조정' AS req_rsn,
           dt AS brnc_in_dt, acpter_id AS brnc_in_id
    FROM   vw_parts_out
    WHERE ( parts_cd, dt, brnc_cd) NOT IN (SELECT  parts_cd, req_dt, brnc_cd FROM TBRB_PARTS_REQ_APPR);

    CUR_parts_adj_ROW  CUR_parts_adj%ROWTYPE;
     
BEGIN

    v_u_seq := 0;
    
    OPEN CUR_parts_adj;
    LOOP
        FETCH CUR_parts_adj INTO CUR_parts_adj_ROW;
        
        EXIT WHEN CUR_parts_adj%NOTFOUND;

        SELECT NVL(MAX(seq)+1,1)
        INTO   V_SEQ
        FROM   TBRB_PARTS_REQ_APPR
        WHERE  parts_cd = CUR_parts_adj_ROW.parts_cd
          AND  brnc_cd  = CUR_parts_adj_ROW.brnc_cd;
        
        v_u_seq := v_u_seq + 1;
          
        INSERT INTO TBRB_PARTS_REQ_APPR(
                     PARTS_CD, BRNC_CD, SEQ, REQ_DT, REQ_NO, 
                     REQ_CNT, APRV_STAS, APRV_CNT, APRV_OFIR_NO, REQ_RSN, 
                     BRNC_IN_DT, BRNC_IN_ID, BRNC_IN_YN, INST_ID, 
                     INST_DT)
                    VALUES(
                    CUR_parts_adj_ROW.parts_cd,
                    CUR_parts_adj_ROW.brnc_cd,
                    V_SEQ, 
                    CUR_parts_adj_ROW.REQ_DT, 
                    CUR_parts_adj_ROW.REQ_NO, 
                    CUR_parts_adj_ROW.REQ_CNT, 
                    CUR_parts_adj_ROW.APRV_STAS, 
                    CUR_parts_adj_ROW.APRV_CNT, 
                    CUR_parts_adj_ROW.APRV_OFIR_NO, 
                    CUR_parts_adj_ROW.REQ_RSN, 
                    CUR_parts_adj_ROW.BRNC_IN_DT, 
                    CUR_parts_adj_ROW.BRNC_IN_ID,
                    'Y',
                    'admin',
                    SYSDATE);
        
        
    END LOOP;
    CLOSE CUR_parts_adj;   
   
   EXCEPTION
     WHEN NO_DATA_FOUND THEN
         NULL;
     WHEN OTHERS THEN
   
       -- Consider logging the error and then re-raise
       RAISE;
END sp_parts_adj;
/
