DROP VIEW USRBM.VW_SMSDATA;

/* Formatted on 2017-03-18 ¿ÀÀü 11:08:43 (QP5 v5.136.908.31019) */
CREATE OR REPLACE FORCE VIEW USRBM.VW_SMSDATA
(
   SEQNO,
   INDATE,
   INTIME,
   MEMBER,
   SENDID,
   SENDNAME,
   RPHONE,
   RECVNAME,
   SPHONE,
   MSG,
   URL,
   RDATE,
   RTIME,
   RESULT,
   KIND,
   ERRCODE,
   SYS_GBN,
   USERID
)
AS
   SELECT SEQNO,
          INDATE,
          INTIME,
          MEMBER,
          SENDID,
          SENDNAME,
          RPHONE1 || RPHONE2 || RPHONE3 AS rphone,
          RECVNAME,
          SPHONE1 || SPHONE2 || SPHONE3 AS sphone,
          MSG,
          URL,
          RDATE,
          RTIME,
          RESULT,
          KIND,
          ERRCODE,
          SYS_GBN,
          USERID
     FROM SMS.SMSDATA;
