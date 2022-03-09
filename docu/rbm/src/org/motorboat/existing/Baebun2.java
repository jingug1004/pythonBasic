package org.motorboat.existing;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;


public class Baebun2 implements Runnable {
    private String dbDriver = "oracle.jdbc.driver.OracleDriver";
    private String dbURL = "jdbc:oracle:thin:@192.168.2.10:1521:mbrrdb";
    private String user = "mbrdba";
    private String password = "mbrdba";


    public void run() {
        int i=0;
        while (true) {

            Log.debug("BAEBUN",this,"run start!"+ (i++));

            Connection conn = null;
            Statement stmt = null;

            try {

                Class.forName(dbDriver);
                System.out.print("Ŀ�ؼ� ����");
                conn = DriverManager.getConnection(dbURL, user, password);
                stmt = conn.createStatement();

                conn.setAutoCommit(false);

                //��� ����� ������
                Log.debug("BAEBUN",this,"���� �غ���....");
                String Delqry = " delete OJMORDTOT ";

                int j = stmt.executeUpdate(Delqry);

                Log.debug("BAEBUN",this," ���� �Ǽ� :"+j+"\n"+Delqry);

                Log.debug("BAEBUN",this,"���� �Ϸ�....");

                StringBuffer sb = new StringBuffer();
                sb.append("\n INSERT INTO OJMORDTOT ( RACEYY,MBRCD,RACEID,DAYSTIMES,  ");
                sb.append("\n BAEBUN01,BAEBUN02,BAEBUN03,BAEBUN04,BAEBUN05,BAEBUN06 ) ");
                sb.append("\n SELECT RACEYY,MBRCD,RACEID,DAYSTIMES,                   ");
                sb.append("\n         SUM(DECODE(BAEBUN,'1',1,0)) BAEBUN01 ,          ");
                sb.append("\n         SUM(DECODE(BAEBUN,'2',1,0)) BAEBUN02,           ");
                sb.append("\n         SUM(DECODE(BAEBUN,'3',1,0)) BAEBUN03,           ");
                sb.append("\n         SUM(DECODE(BAEBUN,'4',1,0)) BAEBUN04,           ");
                sb.append("\n         SUM(DECODE(BAEBUN,'5',1,0)) BAEBUN05,           ");
                sb.append("\n         SUM(DECODE(BAEBUN,'6',1,0)) BAEBUN06            ");
                sb.append("\n  FROM OJMRACREC                                         ");
                sb.append("\n  GROUP BY MBRCD,RACEYY,DAYSTIMES,RACEID                 ");

                int k = stmt.executeUpdate(sb.toString());
                Log.debug("BAEBUN",this," �Է� �Ǽ� :"+k+"\n"+sb.toString());

                conn.commit();
                conn.setAutoCommit(true);
            } catch (SQLException sqlexception) {
                System.out.println(sqlexception.toString());
                try {
                    conn.rollback();
                } catch (Exception ee) {}
            } catch (Exception e) {
                System.out.println(e.toString());
                try {
                    conn.rollback();
                } catch (Exception ee) {}
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception e) {}
            }

            try {
                //Thread.sleep(3 * 60*1000);	//3�и���
                Thread.sleep(60 * 60 * 1000); //30�и���

            } catch (InterruptedException e) {}

        } // end while
    } // end run


    public static void main(String[] args) {
        try{
            Baebun2 baebun2 = new Baebun2();
            Log.debug("BAEBUN","","1. �ν��Ͻ� ����");
            Thread Baebun = new Thread(baebun2);
            Log.debug("BAEBUN","","2. ������ ����");
            Baebun.start();
        }catch(Exception e){
            Log.error("ERROR","",e);
        }
    }

}
