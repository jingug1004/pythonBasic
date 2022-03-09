package org.motorboat.existing;

/*********************************************************************
 1. 시 스 템 명 : 
 2. 서비스 구분 : Web
 3. 프로그램 명 : Baebun.java(배번집계)
 4. 개       요 : 배번집계 
 5. 환       경 : 
    ○ H/W : SUN Enterprise450
    ○ OS : Solaris 2.6
    ○ Language : Java 1.2
    ○ DBMS : ORACLE 8i
 6. 관련테이블 : 
 7. 버      전 : 1.0
 8. 작  성  자 : 박상훈
 9. 작  성  일 : 
10. 변  경  일 :
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
			

		//날짜 비교후 실행 (월,화,수 19시에)
		//if ( ( ( now.get(now.DAY_OF_WEEK) == Calendar.MONDAY) ||
                //       ( now.get(now.DAY_OF_WEEK) == Calendar.TUESDAY ) || 
                //       ( now.get(now.DAY_OF_WEEK) == Calendar.WEDNESDAY ) )&& 
                //       ( now.get(now.HOUR_OF_DAY) > 20 ) ) {

			//test시 time이 없는 경우 
				
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
				
			// 미사리 에서 가장 최근 회수 확인
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
				
				//모든 배번를 삭제함
				String Delqry = "";
				Delqry = " delete from OJMORDTOT ";
				inpstmt.executeUpdate(Delqry);	

				//배번집계						
				rs = stmt.executeQuery(selmaxqry); 

                log("배번 start");

				for ( ;rs.next(); ) {
					

				   	mbrcd     = rs.getString("MBRCD").trim();  //경륜장코드
				   	raceyy    = rs.getString("raceyy").trim();   //년도
				   	daystimes = rs.getString("daystimes").trim(); //일째
				   	raceid    = rs.getString("RACEID").trim();   //선수번호
				   	baebun01  = rs.getInt("baebun01");   //배번1
				   	baebun02  = rs.getInt("baebun02");   //배번2
				   	baebun03  = rs.getInt("baebun03");   //배번3
				   	baebun04  = rs.getInt("baebun04");   //배번4
				   	baebun05  = rs.getInt("baebun05");   //배번5
				   	baebun06  = rs.getInt("baebun06");   //배번6
						

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
				//Thread.sleep(3 * 60*1000);	//3분마다
				Thread.sleep(60*60*1000);	//30분마다
				
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
                        fw.write(year + "년" + month + "월"+ hours + "시 " + minutes + "분 " + seconds + "초" + "  :  " + logdata +
"\n");
                        fw.close();
                } catch(Exception e)    {
                }// catch end.
        }

}

