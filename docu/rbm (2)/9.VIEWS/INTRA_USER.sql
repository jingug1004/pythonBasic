DROP VIEW USRBM.INTRA_USER;

/* Formatted on 2017-03-18 ¿ÀÀü 10:53:41 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.INTRA_USER
(
   USER_SPEC_ID,
   USER_PASSWORD,
   USER_NAME,
   USER_PHOTO,
   USER_TEL,
   USER_FAX,
   USER_EMAIL_ID,
   USER_JOB,
   USER_BIRTH_DATE,
   USER_SOLAR_LUNAR,
   USER_CELL_TEL,
   DOC_GROUP_ID,
   GROUP_ID,
   GROUP_SPEC_ID,
   UP_GROUP_NAME,
   USER_GROUP_NAME,
   USER_TITLE_NAME,
   USER_TITLE,
   USER_TODO,
   USER_TODO_NAME,
   DISP_ORDER
)
AS
   SELECT user_id AS user_spec_id,
          pswd AS user_password,
          user_nm AS user_name,
          NULL AS user_photo,
          tel_no AS user_tel,
          NULL AS user_fax,
          REPLACE (email, INSTR (email, '@')) AS user_email_id,
          NULL AS user_job,
          NULL AS user_birth_date,
          NULL AS user_solar_lunar,
          hp_no AS user_cell_tel,
          dept_cd AS doc_group_id,
          team_cd AS GROUP_ID,
          SUBSTR (dept_cd, 3) AS group_spec_id,
          dept_nm AS up_group_name,
          team_nm AS user_group_name,
          floc AS user_title_name,
          NULL AS user_title,
          NULL AS user_todo,
          NULL AS user_todo_name,
          disp_order AS disp_order
     FROM TBRK_USER
    WHERE gbn = '005';


GRANT SELECT ON USRBM.INTRA_USER TO JENNY;

GRANT SELECT ON USRBM.INTRA_USER TO KRACETEST;

GRANT SELECT ON USRBM.INTRA_USER TO KRACEWEB WITH GRANT OPTION;
