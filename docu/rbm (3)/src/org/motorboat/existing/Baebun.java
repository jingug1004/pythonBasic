package org.motorboat.existing;

/*********************************************************************
 1. �� �� �� �� : 
 2. ���� ���� : Web
 3. ���α׷� �� : Baebun.java(�������)
 4. ��       �� : ������� 
 5. ȯ       �� : 
    �� H/W : SUN Enterprise450
    �� OS : Solaris 2.6
    �� Language : Java 1.2
    �� DBMS : ORACLE 8i
 6. �������̺� : 
 7. ��      �� : 1.0
 8. ��  ��  �� : �ڻ���
 9. ��  ��  �� : 
10. ��  ��  �� :
*********************************************************************/

import java.util.*;
import java.io.*;
import java.net.*;
import java.sql.*;
//import javax.servlet.*;
//import javax.servlet.http.*;
import java.text.SimpleDateFormat;
import java.lang.String;
import java.lang.Runtime;


public class Baebun implements Runnable {
	private String dbDriver = "oracle.jdbc.driver.OracleDriver";
	private String dbURL = "jdbc:oracle:thin:@160.100.1.45:1521:mbrrdb";
	private String user = "mbrdba";
	private String password = "mbrdba";
	//private String user = "dev2005";
	//private String password = "dev2005";

	public void run()	{

	 while(true) {
	 	
	     	Calendar now = Calendar.getInstance();

	     	Connection conn = null;
	     	Statement stmt = null;
		Statement inpstmt = null;
		ResultSet rs = null;		
			

		//��¥ ���� ���� (��,ȭ,�� 19�ÿ�)
		//if ( ( ( now.get(now.DAY_OF_WEEK) == Calendar.MONDAY) ||
                //       ( now.get(now.DAY_OF_WEEK) == Calendar.TUESDAY ) || 
                //       ( now.get(now.DAY_OF_WEEK) == Calendar.WEDNESDAY ) )&& 
                //       ( now.get(now.HOUR_OF_DAY) > 20 ) ) {

			//test�� time�� ���� ��� 
				
			String raceyy    = "";
			String racetimes = "";
			String daystimes = "";
			String raceid    = "";
			String mbrcd     = "";
			
			int    baebun01  = 0;
			int    baebun02  = 0;
			int    baebun03  = 0;
			int    baebun04  = 0;
			int    baebun05  = 0;
			int    baebun06  = 0;
			int    baebun07  = 0;
				
			// �̻縮 ���� ���� �ֱ� ȸ�� Ȯ��
   			String selmaxqry = " Select MBRCD,RACEYY,DAYSTIMES,RACEID , " 
            				+  "        sum(decode(baebun,'1',1,0)) baebun01 ," 
            				+  "        sum(decode(baebun,'2',1,0)) baebun02," 
            				+  "        sum(decode(baebun,'3',1,0)) baebun03," 
            				+  "        sum(decode(baebun,'4',1,0)) baebun04," 
            				+  "        sum(decode(baebun,'5',1,0)) baebun05," 
            				+  "        sum(decode(baebun,'6',1,0)) baebun06 " 
            				+  " From ojmracrec " 
            				+  " group by MBRCD,RACEYY,DAYSTIMES,RACEID ";
            				
								
			try {

                               Class.forName(dbDriver);
                               conn = DriverManager.getConnection(dbURL,user,password);
                               stmt = conn.createStatement();
				inpstmt = conn.createStatement();

				conn.setAutoCommit(false);
				
				//��� ����� ������
				String Delqry = "";
				Delqry = " delete from OJMORDTOT ";
				inpstmt.executeUpdate(Delqry);	

				//�������						
				rs = stmt.executeQuery(selmaxqry); 

                log("��� start");

				for ( ;rs.next(); ) {
					

				   	mbrcd     = rs.getString("MBRCD").trim();  //������ڵ�
				   	raceyy    = rs.getString("raceyy").trim();   //�⵵
				   	daystimes = rs.getString("daystimes").trim(); //��°
				   	raceid    = rs.getString("RACEID").trim();   //������ȣ
				   	baebun01  = rs.getInt("baebun01");   //���1
				   	baebun02  = rs.getInt("baebun02");   //���2
				   	baebun03  = rs.getInt("baebun03");   //���3
				   	baebun04  = rs.getInt("baebun04");   //���4
				   	baebun05  = rs.getInt("baebun05");   //���5
				   	baebun06  = rs.getInt("baebun06");   //���6
						

				   	String insqry = "";
									
				   	insqry = " insert into OJMORDTOT ( RACEYY,mbrcd,RACEID,DAYSTIMeS, "
				               + " BAEBUN01,BAEBUN02,BAEBUN03,BAEBUN04,BAEBUN05,BAEBUN06 ) values ( "
					       + " '" + raceyy + "', '" + mbrcd +"', '" + raceid + "', '" + daystimes + "', "
					       +  baebun01 +"," + baebun02 + "," + baebun03 + "," + baebun04 + "," 
                                               +  baebun05 +"," + baebun06 +")";


				        inpstmt.executeUpdate(insqry);	
	
						
				} //for ( rs.next() ) {
					
				conn.commit();
		
					
				} catch (SQLException sqlexception) {
                                        log(sqlexception.toString());
					try {	conn.rollback(); } catch (Exception ee) {}
				} catch (Exception e){
					try {	conn.rollback(); } catch (Exception ee) {}
				} finally {
                                        try {
					    if ( rs != null ) rs.close();
					    if ( stmt != null ) stmt.close();
					    if ( inpstmt != null ) inpstmt.close();
					    if ( conn != null ) conn.close();
                                        } catch (Exception e) {}
				} // end try
			//} // end if
			
			try {
				//Thread.sleep(3 * 60*1000);	//3�и���
				Thread.sleep(60*60*1000);	//30�и���
				
			} catch (InterruptedException e) {}
			
			now = null;
		}	// end while
	}	// end run
	

	public static void main(String[] args)	{
		Thread Baebun = new Thread(new Baebun());
		Baebun.start();
	}
	

      public void log(String logdata) {
                try {
                        Calendar now = Calendar.getInstance();
                        int year = now.get(now.YEAR);
                        int month = now.get(now.MONTH) + 1;
                        int hours = now.get(now.HOUR);
                        int minutes = now.get(now.MINUTE);
                        int seconds = now.get(now.SECOND);

//                      int hours = now.getHours();
//                      int minutes = now.getMinutes();
//                      int seconds = now.getSeconds();
//                        FileWriter fw = new FileWriter("/mbrweb/batch/log/sdbtocdb.log", true);
                        FileWriter fw = new FileWriter("/testdb/DevDB/devuser/sdbtocdb.log", true);
                        fw.write(year + "��" + month + "��"+ hours + "�� " + minutes + "�� " + seconds + "��" + "  :  " + logdata +
"\n");
                        fw.close();
                } catch(Exception e)    {
                }// catch end.
        }

}

