package org.motorboat.existing;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

/**
 * 상대전적
 * <p>Title: 192.168.2.10 DB 서버</p>
 * <p>Description: 출주표 생성시 업데이트</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: www.krmra.or.kr</p>
 * @author yunkidon@sosfo.or.kr
 * @version 1.0
 */

public class Ojmrivhis2 implements Runnable {

    private String dbDriver = "oracle.jdbc.driver.OracleDriver";
    private String dbURL = "jdbc:oracle:thin:@192.168.2.10:1521:mbrrdb";
    private String user = "mbrdba";
    private String password = "mbrdba";


    public void run() {

        while (true) {
            Calendar now = Calendar.getInstance();
            Connection conn = null;
            Statement stmt = null;
            Statement selstmt = null;
            Statement selstmt2 = null;
            Statement selstmt3 = null;
            Statement selstmt4 = null;
            Statement inpstmt = null;
            Statement delstmt = null;
            ResultSet rs = null;
            ResultSet selrs = null;
            ResultSet selrs2 = null;
            ResultSet selrs3 = null;
            ResultSet selrs4 = null;

            //test시 time이 없는 경우
            String raceyy = "";
            String racetimes = "";
            String daystimes = "";

            // 가장 최근 회수 확인
            String selmaxqry = " select distinct raceyy, racetimes, daystimes "
                               + " from ojmracrec "
                               + " where raceyy||racetimes||daystimes = "
                               + "         ( select max(raceyy||racetimes||daystimes) "
                               + "           from ojmracrec "
                               + "           where mbrcd = '001' ) "
                               + " and mbrcd = '001' ";

            Log.debug("OJMRIVHIS",this,"상대전적");

            try {

                Class.forName(dbDriver);
                conn = DriverManager.getConnection(dbURL, user, password);

                stmt = conn.createStatement();
                selstmt = conn.createStatement();
                selstmt2 = conn.createStatement();
                selstmt3 = conn.createStatement();
                selstmt4 = conn.createStatement();
                inpstmt = conn.createStatement();
                delstmt = conn.createStatement();

                conn.setAutoCommit(false);


                //미사리
                Log.debug("OJMRIVHIS",this,"최대회차"+selmaxqry);
                rs = stmt.executeQuery(selmaxqry);

                if (rs.next()) {

                    raceyy = rs.getString("raceyy").trim();
                    racetimes = rs.getString("racetimes").trim();
                    daystimes = rs.getString("daystimes").trim();

                    String delqry = " delete  ojmrivhis  "
                                    + " where raceyy    = '" + raceyy + "' "
                                    + "   and racetimes = '" + racetimes + "' "
                                    + "   and daystimes = '" + daystimes + "' "
                                    + "   and mbrcd     = '001'";
                    Log.debug("OJMRIVHIS",this,delqry);
                    delstmt.executeUpdate(delqry);

                    //출전선수
                    String selqry = " select raceno, raceid "
                                    + " from ojmracrec "
                                    + " where raceyy    = '" + raceyy + "' "
                                    + "   and racetimes = '" + racetimes + "'"
                                    + "   and daystimes = '" + daystimes + "' "
                                    + "   and mbrcd     = '001' "
                                    + " order by mbrcd, raceyy, racetimes, daystimes, raceno, baebun ";
                    Log.debug("OJMRIVHIS",this,"출전선수 :\n"+selqry);
                    selrs = selstmt.executeQuery(selqry);
                    int firsti = 0;
                    for (; selrs.next(); ) {

                        String raceno = selrs.getString("raceno").trim();
                        String raceid = selrs.getString("raceid").trim();

                        //상대선수
                        String selqry2 = " select to_number(baebun) baebun ,raceno, raceid  "
                                         + " from ojmracrec "
                                         + " where raceyy    = '" + raceyy + "' "
                                         + "   and racetimes = '" + racetimes + "' "
                                         + "   and daystimes = '" + daystimes + "' "
                                         + "   and mbrcd     = '001' "
                                         + "   and raceno    = '" + raceno + "' "
                                         + " order by baebun ";
                        Log.debug("OJMRIVHIS",this,"상대선수 :\n"+selqry2);
                        selrs2 = selstmt2.executeQuery(selqry2);

                        int i = 0;
                        int baebun = 0;

                        for (; selrs2.next(); ) {
                            i++;

                            int isungcnt = 0;
                            int ipaecnt = 0;
                            int imucnt = 0;

                            String raceno2 = selrs2.getString("raceno").trim();
                            String raceid2 = selrs2.getString("raceid").trim();
                            baebun = selrs2.getInt("baebun");

                            if (!raceid.equals(raceid2)) {

                                //경주결과를 얻어옴
                                String selqry3 = " select mbrcd,RACEYY,RACETIMES,DAYSTIMeS,"
                                                 + "        RACENO,baebun,RACEID,NVL(rank,9) rank "
                                                 + " from ojmracrec "
                                                 + " where raceyy   = '" + raceyy + "' "
                                                 + "   and mbrcd    = '001' "
                                                 + "   and raceid   = '" + raceid + "' "
                                                 + "   and ( nvl(GYEOLJGB,'0') = '0' or GYEOLJGB = ' ')"
                                                 + "   and RACEYY||RACETIMES||daystimes <> '" + raceyy + racetimes + daystimes + "'";
                                Log.debug("OJMRIVHIS",this,"경주결과 :\n"+selqry3);
                                selrs3 = selstmt3.executeQuery(selqry3);

                                for (; selrs3.next(); ) {

                                    String raceyy3 = selrs3.getString("RACEYY").trim();
                                    String racetimes3 = selrs3.getString("RACETIMES").trim();
                                    String daystimes3 = selrs3.getString("DAYSTIMeS").trim();
                                    String raceno3 = selrs3.getString("RACENO").trim();
                                    int rank3 = selrs3.getInt("rank");

                                    if (rank3 == 0) {
                                        rank3 = 9;
                                    }

                                    //상대선수
                                    String selqry4 = " select mbrcd,RACEYY,RACETIMES,DAYSTIMeS,"
                                            + "   RACENO,baebun,RACEID,NVL(rank,9) rank "
                                            + " from ojmracrec "
                                            + " where RACEYY     = '" + raceyy3 + "' "
                                            + "  and  RACETIMES  = '" + racetimes3 + "' "
                                            + "  and  DAYSTIMeS  = '" + daystimes3 + "' "
                                            + "  and  RACENO     = '" + raceno3 + "' "
                                            + "  and  RACEID     = '" + raceid2 + "' "
                                            + "  and  mbrcd      = '001'"
                                            + "  and  (nvl(GYEOLJGB,'0')   = '0' or GYEOLJGB = ' ')";
                                    Log.debug("OJMRIVHIS",this,"상대선수 :\n"+selqry4);
                                    selrs4 = selstmt4.executeQuery(selqry4);

                                    if (selrs4.next()) { //상대선수가 있을 경우

                                        String raceid4 = selrs4.getString("raceid").trim();
                                        int rank4 = selrs4.getInt("rank");

                                        if (rank4 == 0) {
                                            rank4 = 9;
                                        }

                                        if (!raceid.equals(raceid4)) { //다른사람일 경우만
                                            if (rank3 != 9 && rank4 != 9) { //순위가 없을경우
                                                if (rank3 != rank4) {
                                                    ipaecnt = ipaecnt + 1;
                                                }
                                                if (rank3 < rank4) {
                                                    isungcnt = isungcnt + 1; //승
                                                }
                                            }
                                        } //if ( !raceid.equals(raceid4) )
                                    } //if ( selrs4.next() )
                                } // for ( ; selrs3.next(); ) {

                                String insqry = "";

                                insqry = " insert into ojmrivhis ( raceyy,racetimes,daystimes,"
                                         + " mbrcd,raceno, raceid,sraceid,"
                                         + " seq,sungcnt,totcnt ) values ( "
                                         + " '" + raceyy + "', '" + racetimes + "', "
                                         + " '" + daystimes + "', '001'," + " '" + raceno + "', "
                                         + " '" + raceid + "', '" + raceid2 + "', "
                                         + baebun + ", " + isungcnt + ", " + ipaecnt + " )";

                                //Log.debug("OJMRIVHIS",this,insqry);

                                inpstmt.executeUpdate(insqry);
                            } //if ( !raceid.equals(raceid2) )

                            //저장

                        } // for ( ; selrs2.next(); ) {

                    } // for ( ;selrs.next(); ) {

                } //if ( rs.next() ) {

                conn.commit();
                conn.setAutoCommit(true);

            } catch (SQLException sqlexception) {
                Log.error("OJMRIVHIS",this,sqlexception.toString());
                try {
                    conn.rollback();
                } catch (Exception ee) {}
            } catch (Exception e) {
                try {
                    conn.rollback();
                } catch (Exception ee) {}
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (Exception e) {}
                }
                if (selrs != null) {
                    try {
                        selrs.close();
                    } catch (Exception e) {}
                }
                if (selrs2 != null) {
                    try {
                        selrs2.close();
                    } catch (Exception e) {}
                }
                if (selrs3 != null) {
                    try {
                        selrs3.close();
                    } catch (Exception e) {}
                }
                if (selrs4 != null) {
                    try {
                        selrs4.close();
                    } catch (Exception e) {}
                }
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (Exception e) {}
                }
                if (selstmt != null) {
                    try {
                        selstmt.close();
                    } catch (Exception e) {}
                }
                if (selstmt2 != null) {
                    try {
                        selstmt2.close();
                    } catch (Exception e) {}
                }
                if (selstmt3 != null) {
                    try {
                        selstmt3.close();
                    } catch (Exception e) {}
                }
                if (selstmt4 != null) {
                    try {
                        selstmt4.close();
                    } catch (Exception e) {}
                }
                if (inpstmt != null) {
                    try {
                        inpstmt.close();
                    } catch (Exception e) {}
                }
                if (delstmt != null) {
                    try {
                        delstmt.close();
                    } catch (Exception e) {}
                }
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Exception e) {}
                }
            } // end try
            //} // end if

            try {
                Thread.sleep(60 * 60 * 1000); //30분마다
            } catch (InterruptedException e) {}
            now = null;
        } // end while
    } // end run

    public static void main(String[] args) {
        Thread ojmrivhis = new Thread(new Ojmrivhis2());
        ojmrivhis.start();
    }

}
