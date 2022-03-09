CREATE OR REPLACE PROCEDURE USRBM.SP_IMPORT_SDL_INFO

IS
/******************************************************************************
- ������,��      : yslee 2009.08.12
- ���α׷���     : SP_IMPORT_SDL_INFO
- ���α׷�Ÿ��   : procedure
- ���           : ���, ���� ȸ�������� �о�� �����Ѵ�.
- IN  �μ�       :

- ���μ���
    1. ��� ȸ�� ��ȸ UNION ALL ����ȸ����ȸ
    2.  ��ȸ�ؼ�  VW_SDL_INFO ���̺� �Է�


- ���ν����� ���� ��ħ 8�ÿ� �۵��ǵ��� �Ѵ�.(�����ٷ� ���)
******************************************************************************/

    v_err_code NUMBER;
    v_err_mesg VARCHAR(255);
    V_CNT NUMBER;
    V_DY   VARCHAR(255);


BEGIN
    DBMS_OUTPUT.ENABLE;

    SP_BTC_LOG('002','I','SP_IMPORT_SDL_INFO','START','');

            INSERT INTO VW_SDL_INFO
         (
             MEET_CD,
             STND_YEAR,
             TMS,
             DAY_ORD,
             RACE_DAY,
             RACE_YOIL
         )
        SELECT *
          FROM (SELECT
                     MEET_CD,
                     STND_YEAR,
                     TO_NUMBER (TMS),
                     TO_NUMBER (DAY_ORD),
                     STND_YEAR || RACE_DT RACE_DT,
                     TO_CHAR ( TO_DATE (STND_YEAR || RACE_DT, 'YYYYMMDD')
                               , 'DY'
                               , 'NLS_DATE_LANGUAGE = ENGLISH'
                             ) RACE_YOIL
                 FROM US_CRA.TBJB_CFM_ORGAN
                 WHERE 1=1
                   AND STND_YEAR = TO_CHAR(SYSDATE, 'YYYY')
                   AND RACE_DT   = TO_CHAR(SYSDATE, 'MMDD')
                 GROUP BY MEET_CD, STND_YEAR, TMS,DAY_ORD, RACE_DT

                  UNION ALL

                  SELECT '003' AS MEET_CD,
                STND_YEAR,
                TMS,
                DAY_ORD,
                RACE_DAY,
                TO_CHAR (TO_DATE (RACE_DAY, 'YYYYMMDD'),
                           'DY',
                           'NLS_DATE_LANGUAGE = ENGLISH'
                         ) DY
             FROM MRASYS.TBEB_RACE_DAY_ORD
            WHERE 1=1
              AND ORGAN_STAT_CD = '002'
              AND RACE_DAY      = TO_CHAR(SYSDATE,'YYYYMMDD')
             ) A
          WHERE NOT EXISTS (SELECT 1
                             FROM VW_SDL_INFO B
                            WHERE A.MEET_CD = B.MEET_CD
                              AND A.RACE_DT = B.RACE_DAY)
         ;
         
         
    SELECT COUNT(*) AS CNT
            , TO_CHAR(SYSDATE, 'DY'
                               , 'NLS_DATE_LANGUAGE = ENGLISH') AS DY
    INTO   V_CNT, V_DY
    FROM  VW_SDL_INFO
    WHERE RACE_DAY = TO_CHAR(SYSDATE, 'YYYYMMDD');
    
    IF V_CNT = 0 AND V_DY NOT IN ('MON','TUE') THEN
         SP_BTC_LOG('002','E','SP_IMPORT_SDL_INFO','������('||TO_CHAR(SYSDATE, 'YYYYMMDD')||') ���������� �������� �ʽ��ϴ�.(VW_SDL_INFO)','');
    END IF;
         
    COMMIT;
    SP_BTC_LOG('002','I','SP_IMPORT_SDL_INFO','END','');
    RETURN;


    EXCEPTION
        WHEN OTHERS THEN
            v_err_code := SQLCODE();
            v_err_mesg := SQLERRM;
            ROLLBACK;
            SP_BTC_LOG('002','E','SP_IMPORT_SDL_INFO','ERROR CODE:'||v_err_code || ' ERROR LOG:'||v_err_mesg,'');
            RETURN;

END;
/
