DROP VIEW USRBM.VW_API_PAYOFF;

/* Formatted on 2017-03-18 ���� 10:56:59 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_API_PAYOFF
(
   MEET,
   STND_YEAR,
   TMS,
   DAY_ORD,
   RACE_DAY,
   RACE_NO,
   POOL1,
   POOL2_1,
   POOL2_2,
   POOL4,
   POOL5,
   POOL6
)
AS
     SELECT DECODE (rs.meet_cd, '001', '���', '003', '����') MEET,
            rs.stnd_year,
            rs.tms,
            rs.day_ord,
            info.race_day,
            rs.race_no,
            SUM (DECODE (pool_cd, '001', payoff)) AS POOL1,
            SUM (DECODE (pool_cd || rs_seq, '0021', payoff)) AS POOL2_1,
            SUM (DECODE (pool_cd || rs_seq, '0022', payoff)) AS POOL2_2,
            SUM (DECODE (pool_cd, '004', payoff)) AS POOL4,
            SUM (DECODE (pool_cd, '005', payoff)) AS POOL5,
            SUM (DECODE (pool_cd, '006', payoff)) AS POOL6
       FROM usrbm.TBES_SDL_RS rs, usrbm.VW_SDL_INFO info
      WHERE     rs.meet_cd = info.meet_cd
            AND rs.stnd_year = info.stnd_year
            AND rs.tms = info.tms
            AND rs.day_ord = info.day_ord
            AND rs.meet_cd IN ('001', '003')
   GROUP BY rs.stnd_year,
            rs.tms,
            rs.day_ord,
            info.race_day,
            rs.race_no,
            rs.meet_cd
   ORDER BY rs.meet_cd,
            rs.stnd_year,
            rs.tms,
            rs.day_ord,
            race_no;
COMMENT ON TABLE USRBM.VW_API_PAYOFF IS '��������_���ֹ���';

COMMENT ON COLUMN USRBM.VW_API_PAYOFF.MEET IS '���ֽ���ó';

COMMENT ON COLUMN USRBM.VW_API_PAYOFF.STND_YEAR IS '�⵵';

COMMENT ON COLUMN USRBM.VW_API_PAYOFF.TMS IS 'ȸ��';

COMMENT ON COLUMN USRBM.VW_API_PAYOFF.DAY_ORD IS '����';

COMMENT ON COLUMN USRBM.VW_API_PAYOFF.RACE_DAY IS '������';

COMMENT ON COLUMN USRBM.VW_API_PAYOFF.RACE_NO IS '���ֹ�ȣ';

COMMENT ON COLUMN USRBM.VW_API_PAYOFF.POOL1 IS '�ܽ�';

COMMENT ON COLUMN USRBM.VW_API_PAYOFF.POOL2_1 IS '����1';

COMMENT ON COLUMN USRBM.VW_API_PAYOFF.POOL2_2 IS '����2';

COMMENT ON COLUMN USRBM.VW_API_PAYOFF.POOL4 IS '�ֽ�';

COMMENT ON COLUMN USRBM.VW_API_PAYOFF.POOL5 IS '����';

COMMENT ON COLUMN USRBM.VW_API_PAYOFF.POOL6 IS '�ﺹ��';


GRANT SELECT ON USRBM.VW_API_PAYOFF TO ACCT;
