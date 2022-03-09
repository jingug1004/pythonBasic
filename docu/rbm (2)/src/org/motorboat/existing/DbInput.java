package org.motorboat.existing;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;

import org.motorboat.db.*;
import org.motorboat.mng.*;
import org.motorboat.dto.*;
import org.motorboat.common.*;
import org.motorboat.*;
	
public class DbInput
{
	static String grount_flag = "001";		//본장 장외 구분
	static String out_site_flag ="001";

	String db_system_year;		// 연도
	String db_beakinds;		// 구분종류
	String db_system_seq;		// 경주 회차
	String db_system_day;		// 경주 일차
	String db_check_flag;		// 마감 플래그

	Connection	conn;
	Statement	stmt;
	Statement	stmt1;
	ResultSet	rset;
	ResultSet	rset2;
	  
	String s;
	String query;

	int i_flag=0;  

	String db_win_count;

/* ---------------------------------------------------------------------------------------- */
/*             생성자  : 일째 , 경주회차 , 경주일차                                         */
/* ---------------------------------------------------------------------------------------- */
	public DbInput() {
 
		try {
 		
			s= " select raceyy,racetimes,daystimes from racedays where raceday = to_char(sysdate,'yyyymmdd') ";
		
			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn	=	DriverManager.getConnection("jdbc:oracle:thin:@160.100.1.45:1521:mbrrdb", "mbrdba", "mbrdba");
			//conn	=	DriverManager.getConnection("jdbc:oracle:thin:@160.100.1.45:1521:mbrrdb", "devuser", "devpass");
			stmt	=	conn.createStatement();
			rset	=	stmt.executeQuery(s);

			if(rset.next()){
				db_system_year = rset.getString(1);
				db_system_seq = rset.getString(2);	// 경주 회차
				db_system_day = rset.getString(3);	// 경주 일차  
				
			//	db_system_year = "2007";
			//	db_system_seq = "02";		// Test용 Sample
			//	db_system_day = "2";	 
				System.out.println(db_system_year+db_system_seq+db_system_day+"Call DbInput");
			} 
			
		} catch(ClassNotFoundException e) {
			System.out.println("오라클 드라이버 Error...! ");
		} catch(java.sql.SQLException e) {
			
		} catch(Exception e) {
		} finally {
			if ( rset != null ) try {rset.close();} catch (Exception e) {}
			if ( stmt != null ) try {stmt.close();} catch (Exception e) {}
			if ( conn != null ) try {conn.close();} catch (Exception e) {}
		}

	}
	
/* ---------------------------------------------------------------------------------------- */
/*                                  TM Data Proc                                            */
/* ---------------------------------------------------------------------------------------- */
/*     Input : Hashtable                                                                    */
/*     Table : eqFlag                                                                       */
/*     Proc  : 1 . 마감여부 (마감데이터 무시)                                               */
/*             2 . eqflag Table  Insert/Update                                              */  
/* ---------------------------------------------------------------------------------------- */
  	public void TmProc(Hashtable Has,int lt) {
  		
  		String et = "";
  		String nt = "";

  		try{

 		  	query  = "select flag from eqflag ";             // 플래그 체크: P => 진행  L => 마감
			query += "where raceyy ='" + db_system_year + "' ";
			query += "and racetimes = '" + db_system_seq + "' ";
			query += "and daystimes = '" + db_system_day + "' ";
			query += "and mbrcd = '" + grount_flag  + "' ";
			query += "and raceno = '" + Has.get("RN") + "'";		// RN : 경주번호
   		
			sqlOutput("TM  마감플래그 :" + query);
			Class.forName("oracle.jdbc.driver.OracleDriver");

  	 		conn	=	DriverManager.getConnection("jdbc:oracle:thin:@160.100.1.45:1521:mbrrdb", "mbrdba", "mbrdba");
			//conn	=	DriverManager.getConnection("jdbc:oracle:thin:@160.100.1.45:1521:mbrrdb", "devuser", "devpass");
 	  		stmt	=	conn.createStatement();
    			rset	=	stmt.executeQuery(query);

			conn.setAutoCommit(false);

   			if (rset.next()) {
   			
   			//20030422here	
   				if((lt > -9) && (lt < 50) && (Has.get("LT").equals("**"))) 	// lt : ET - NT (마감시간 - 현재시간)
					i_flag=3;
				else { 
					if (rset.getString(1).equals("P")||rset.getString(1).equals("X")) 
						i_flag = 1;  				  
					else 
						i_flag = 2;
				}					
			} else 
				i_flag = 0;

   		/*	//20030422comment
				if (rset.getString(1).equals("P")) 
					i_flag = 1; 	
				else 
					i_flag = 2;
			} else 
				i_flag = 0;
		*/
		
			rset.close();
			
			if (i_flag==2) {
				if(lt>0) 
					i_flag=1;
			}
			
	   		if (i_flag == 0) {                                       // insert
   				query  = "insert into eqflag values ";
         			query += "('" + db_system_year + "',";
          			query += "'" + db_system_seq + "',";
          			query += "'" + db_system_day + "',";
          			query += "'" + grount_flag  + "',";
          			query += "'" + Has.get("RN") + "',";   
	
          			if(Has.get("LT").equals("**")) 
					query += "'L',";
          			else 
					query += "'P',";

				query += "sysdate,";
          			query += "'" + Has.get("LT") + "')";       	             
   	  
   	   		} else if(i_flag == 3) { 		// 20030422here
   	  	  		query  = " update eqflag set ";
          			query += " regdate = sysdate,";
          			if(Has.get("LT").equals("**")) 
					query += "flag ='X',";
          			else
					query += "flag ='P',";
          			query += " endtime = '" + Has.get("LT") + "'" ;
          			query += " where raceyy = '" + db_system_year + "'";
          			query += " and racetimes = '" + db_system_seq + "'";
          			query += " and daystimes  = '" + db_system_day + "'";
          			query += " and mbrcd = '" + grount_flag  + "'";
          			query += " and raceno = '" + Has.get("RN") + "'";   //20030422here
   	   
   	  		} else if(i_flag == 1) {				// Update
   	  			query  = "update eqflag set ";
          			query += " regdate = sysdate,";
          			if(Has.get("LT").equals("**")) 
					query += "flag ='L',";
          			else 
					query += "flag ='P',";
          			query += " endtime = '" + Has.get("LT") + "'" ;
          			query += " where raceyy = '" + db_system_year + "'";
          			query += " and racetimes = '" + db_system_seq + "'";
          			query += " and daystimes  = '" + db_system_day + "'";
          			query += " and mbrcd = '" + grount_flag  + "'";
         			query += " and raceno = '" + Has.get("RN") + "'";
   	  		}

			sqlOutput("TM  Query:" + query);
	 	  	stmt.executeUpdate(query);
 		  	conn.commit(); 		  	

    		} catch(ClassNotFoundException e ) {
  			System.out.println("오라클 드라이버 Error...! ");
  		} catch(SQLException e) {
			try{ conn.rollback();} catch(Exception p){}
  			sqlOutput(" Query Error : " + query);
  		} catch(Exception e) { 
  			System.out.println(e.getMessage());
  		} finally {
                        if ( stmt != null ) try {stmt.close();} catch (Exception e) {}
                        if ( conn != null ) try {conn.close();} catch (Exception e) {}
                }
  	}
  	
/* ---------------------------------------------------------------------------------------- */
/*                                  PT Data Proc                                            */
/* ---------------------------------------------------------------------------------------- */
/*     Input : Hashtable                                                                    */
/*     Table : oaitotamt                                                                    */
/*     Proc  : 1 . 마감여부 (진행중일때만 처리 )                                            */
/*             2 . oaitotamt Table  Insert/Update                                           */  
/* ---------------------------------------------------------------------------------------- */
  	public void PtProc(Hashtable Has) {

	  	try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
  			conn	=	DriverManager.getConnection("jdbc:oracle:thin:@160.100.1.45:1521:mbrrdb", "mbrdba", "mbrdba");
			//conn	=	DriverManager.getConnection("jdbc:oracle:thin:@160.100.1.45:1521:mbrrdb", "devuser", "devpass");
   			stmt	=	conn.createStatement();

			conn.setAutoCommit(false);

     			query  = " select count(*) from oaitotamt ";
     			query += " where raceyy = '" + db_system_year + "'";
     			query += " and racetimes = '" + db_system_seq + "'";
     			query += " and daystimes  = '" + db_system_day + "'";
     			query += " and mbrcd = '" + grount_flag  + "'";
     			query += " and outsite = '" + out_site_flag + "'";
     			query += " and raceno = '" + Has.get("RN") + "'";
     			
			sqlOutput("PT  Count :" + query);
     			rset=stmt.executeQuery(query);
     		
			if (rset.next()) {
				i_flag = rset.getInt(1);
				rset.close();
			}
     	  		if (i_flag == 0) {                                       // insert   // 2004. 2. 5 by sdhong. 삼복승 추가
     	  			query  = " insert into oaitotamt(";
				query += " RACEYY ,";
				query += "RACETIMES,";
				query += "DAYSTIMES,";
				query += "MBRCD,";
				query += "OUTSITE,";
				query += "RACENO,";
				query += "WIN_PRICE,";
				query += "PLACE_PRICE,";
				query += "EXACTA_PRICE,";
				query += "QUINELLA_PRICE,";
				query += "TRIELLA_PRICE,";
				query += "PRICE_AMT";
				query += " ) values ";
             			query += "('" + db_system_year + "',";
             			query += "'" + db_system_seq + "',";
            		 	query += "'" + db_system_day + "',";
             			query += "'" + grount_flag  + "',";
             			query += "'" + out_site_flag + "',";
         	    		query += "'" + Has.get("RN") + "',";

         	    		if (Has.get("WF").equals("WIN"))  
					query += Has.get("TA") + ",0,0,0,0,";
         	    		else if(Has.get("WF").equals("PLC")) 
					query += "0," + Has.get("TA") + ",0,0,0,";
         	    		else if(Has.get("WF").equals("EX ")) 
					query += "0,0," + Has.get("TA") + ",0,0,";
         	    		else if(Has.get("WF").equals("QU ")) 
					query += "0,0,0," + Has.get("TA") + ",0,";
         	    		else if(Has.get("WF").equals("TLA")) 
					query += "0,0,0,0," + Has.get("TA") + ",";
				query += Has.get("TA") + " )";

     	  		} else {  
     	  			query  = "update oaitotamt set ";	 // 2004. 2. 5 by sdhong. 삼복승 추가

         	    		if (Has.get("WF").equals("WIN"))  
					query += " WIN_PRICE = "+ Has.get("TA") + " , PRICE_AMT = "+ Has.get("TA") + "+PLACE_PRICE+EXACTA_PRICE+QUINELLA_PRICE+TRIELLA_PRICE";
         	    		else if (Has.get("WF").equals("PLC")) 
					query += " PLACE_PRICE ="+ Has.get("TA") + " , PRICE_AMT = WIN_PRICE+"+ Has.get("TA") + "+EXACTA_PRICE+QUINELLA_PRICE+TRIELLA_PRICE";
         	    		else if (Has.get("WF").equals("EX ")) 
					query += " EXACTA_PRICE ="+ Has.get("TA") + " , PRICE_AMT = WIN_PRICE+PLACE_PRICE+"+ Has.get("TA") + "+QUINELLA_PRICE+TRIELLA_PRICE";
         	    		else if (Has.get("WF").equals("QU ")) 
					query += " QUINELLA_PRICE = " + Has.get("TA") + " , PRICE_AMT = WIN_PRICE+PLACE_PRICE+EXACTA_PRICE+"+ Has.get("TA") +"+TRIELLA_PRICE";
         	    		else if (Has.get("WF").equals("TLA"))
					query += " TRIELLA_PRICE = " + Has.get("TA") + " , PRICE_AMT = WIN_PRICE+PLACE_PRICE+EXACTA_PRICE+QUINELLA_PRICE+"+ Has.get("TA") ;
				else
					query += " PRICE_AMT = WIN_PRICE+PLACE_PRICE+EXACTA_PRICE+QUINELLA_PRICE+TRIELLA_PRICE";
					//*2003.03.28변경 price_amt계산시 복승식이 1분전 자료가 더해짐
					//query += " ,PRICE_AMT = WIN_PRICE+PLACE_PRICE+EXACTA_PRICE+QUINELLA_PRICE";
	
       	    			query += " where raceyy = '" + db_system_year + "'";
       	    			query += " and racetimes = '" + db_system_seq + "'";
       	    			query += " and daystimes  = '" + db_system_day + "'";
       	    			query += " and mbrcd = '" + grount_flag  + "'";
       	    			query += " and outsite = '" + out_site_flag + "'";
       	    			query += " and raceno = '" + Has.get("RN") + "'";
     	  		} // if flag
			sqlOutput("PT  Query :" + query);
   		  	stmt.executeUpdate(query);
   		  	conn.commit();   		  	

  	  	} catch(ClassNotFoundException e ){
 			System.out.println("오라클 드라이버 Error...! ");
	  	} catch(SQLException e){
			try{ conn.rollback();} catch(Exception p){}
  			sqlOutput(" Query Error : " + query);
	  	}catch(Exception e){
		}finally{
                        if ( stmt != null ) try {stmt.close();} catch (Exception e) {}
                        if ( conn != null ) try {conn.close();} catch (Exception e) {}
                }
  	}
  	
/* ---------------------------------------------------------------------------------------- */
/*                                  PB Data Proc                                            */
/* ---------------------------------------------------------------------------------------- */
/*     Input : Hashtable                                                                    */
/*     Table : dyrate,eqrate                                                                */
/*     Proc  : 1 . 마감여부 (진행중일때만 처리 )                                            */
/*             2 . dyrate,eqrate Table  Insert/Update                                       */  
/*                 (단승,연승 : dyrate    쌍승,복승 : eqrate)                               */
/* ---------------------------------------------------------------------------------------- */
  	public void PbProc(Hashtable Has) {
  		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
  	 		conn	=	DriverManager.getConnection("jdbc:oracle:thin:@160.100.1.45:1521:mbrrdb", "mbrdba", "mbrdba");
			//conn	=	DriverManager.getConnection("jdbc:oracle:thin:@160.100.1.45:1521:mbrrdb", "devuser", "devpass");
 	  		stmt	=	conn.createStatement();
     			
     			if ((Has.get("WF").equals("WIN")) || (Has.get("WF").equals("PLC")))
     				query  = " select count(*) from dyrate ";
     			else
     				query  = " select count(*) from eqrate ";

	     			query += " where raceyy = '" + db_system_year + "'";
     				query += " and racetimes = '" + db_system_seq + "'";
   	  			query += " and daystimes  = '" + db_system_day + "'";
     				query += " and mbrcd = '" + grount_flag  + "'";
     				query += " and raceno = '" + Has.get("RN") + "'";     			

     		  		if (Has.get("WF").equals("WIN")) 
					query += " and winflag ='W'";
     		  		else if (Has.get("WF").equals("PLC")) 
					query += " and winflag ='P'";
     		  		else if (Has.get("WF").equals("EX ")) 
					query += " and winflag ='E'";
     		  		else if (Has.get("WF").equals("QU ")) 
					query += " and winflag ='Q'";
     	 		
				sqlOutput("PB  Count :" + query);
     				rset=stmt.executeQuery(query);
     	
     		  		if (rset.next()) i_flag = rset.getInt(1); 
				rset.close();
     	  			if (i_flag == 0) {
     	  				if ((Has.get("WF").equals("EX ")) || (Has.get("WF").equals("QU "))){
	     	  				query  = " insert into eqrate values ";
  	       	    				query += "('" + db_system_year + "',";
   	      	    				query += "'" + db_system_seq  + "',";
   	      	    				query += "'" + db_system_day  + "',";
   	      	    				query += "'" + grount_flag    + "',";
   	      	   				query += "'" + Has.get("RN")  + "',";
         	   
   	      	   				if (Has.get("WF").equals("EX ")) query += "'E',";
   	      	  				else if (Has.get("WF").equals("QU ")) query += "'Q',";
         	    
   	      	    				query += "'" + Has.get("B12") + "',";
   	      	    				query += "'" + Has.get("B13") + "',";
   	      	    				query += "'" + Has.get("B14") + "',";
   	      	    				query += "'" + Has.get("B15") + "',";
   	      	    				query += "'" + Has.get("B16") + "',";

   	      	    				query += "'" + Has.get("B21") + "',";
   	      	    				query += "'" + Has.get("B23") + "',";
   	      	    				query += "'" + Has.get("B24") + "',";
   	      	    				query += "'" + Has.get("B25") + "',";
   	      	    				query += "'" + Has.get("B26") + "',";

   	      	    				query += "'" + Has.get("B31") + "',";   	      	    
   	      	    				query += "'" + Has.get("B32") + "',";
   	      	    				query += "'" + Has.get("B34") + "',";
   	      	    				query += "'" + Has.get("B35") + "',";
   	      	    				query += "'" + Has.get("B36") + "',";

 	      	    				query += "'" + Has.get("B41") + "',";
	      	    				query += "'" + Has.get("B42") + "',";
   	      	    				query += "'" + Has.get("B43") + "',";
   	      	    				query += "'" + Has.get("B45") + "',";
   	      	    				query += "'" + Has.get("B46") + "',";
	
   	      	    				query += "'" + Has.get("B51") + "',";   	      	    
   	      	    				query += "'" + Has.get("B52") + "',";
   	      	    				query += "'" + Has.get("B53") + "',";
   	      	    				query += "'" + Has.get("B54") + "',";
						query += "'" + Has.get("B56") + "',";
	
						query += "'" + Has.get("B61") + "',";   	      	    
   	      	    				query += "'" + Has.get("B62") + "',";
   	      	    				query += "'" + Has.get("B63") + "',";
   	      	    				query += "'" + Has.get("B64") + "',";    	    
   	      	    				query += "'" + Has.get("B65") + "')";
   	      	  			}else{
	     	  				query  = " insert into dyrate values ";
  	       	    				query += "('" + db_system_year + "',";
   	      	    				query += "'" + db_system_seq  + "',";
   	      	    				query += "'" + db_system_day  + "',";
   	      	    				query += "'" + grount_flag    + "',";
   	      	    				query += "'" + Has.get("RN")  + "',";

	      	    				if (Has.get("WF").equals("WIN")) query += "'W',";
   	      	    				else if (Has.get("WF").equals("PLC")) query += "'P',";
	
   	      	    				query += "'" + Has.get("B11") + "',";   	      	    
   	      	    				query += "'" + Has.get("B12") + "',";
   	      	    				query += "'" + Has.get("B13") + "',";
   	      	    				query += "'" + Has.get("B14") + "',";
   	      	    				query += "'" + Has.get("B15") + "',";
   	      	    				query += "'" + Has.get("B16") + "')";
   	      	  			}
   		      	 
     	  			}else{
     		  	                                                             // Update
       	       				query  = " select count(*) from eqrate ";
       	 				query += " where raceyy = '" + db_system_year + "'";
       	 				query += " and racetimes = '" + db_system_seq + "'";
       	 				query += " and daystimes  = '" + db_system_day + "'";
       	 				query += " and mbrcd = '" + grount_flag  + "'";
       	 				query += " and raceno = '" + Has.get("RN") + "'";
       	 	  			if (Has.get("WF").equals("EX ")) 
						query += " and winflag ='E'";
       	 	  			else if (Has.get("WF").equals("QU ")) 
						query += " and winflag ='Q'";
       	 	     	 			 		  
    	  				if ((Has.get("WF").equals("EX ")) || (Has.get("WF").equals("QU "))){
      	  					query  = "update eqrate set ";
       	   	    				query += " no12 = '"+ Has.get("B12") + "',";
       	   	    				query += " no13 = '"+ Has.get("B13") + "',";
       	   	    				query += " no14 = '"+ Has.get("B14") + "',";
       	   	    				query += " no15 = '"+ Has.get("B15") + "',";
       	   	    				query += " no16 = '"+ Has.get("B16") + "',";

  		   	    			query += " no21 = '"+ Has.get("B21") + "',";
   		   	    			query += " no23 = '"+ Has.get("B23") + "',";
       		   	    			query += " no24 = '"+ Has.get("B24") + "',";
       		   	    			query += " no25 = '"+ Has.get("B25") + "',";
       		   	    			query += " no26 = '"+ Has.get("B26") + "',";
	
       		   	    			query += " no31 = '"+ Has.get("B31") + "',";
       		   	    			query += " no32 = '"+ Has.get("B32") + "',";
       		   	    			query += " no34 = '"+ Has.get("B34") + "',";
       		   	    			query += " no35 = '"+ Has.get("B35") + "',";
       		   	    			query += " no36 = '"+ Has.get("B36") + "',";
	
       		    	    			query += " no41 = '"+ Has.get("B41") + "',";
         		    			query += " no42 = '"+ Has.get("B42") + "',";
          	    				query += " no43 = '"+ Has.get("B43") + "',";
          	    				query += " no45 = '"+ Has.get("B45") + "',";
          	    				query += " no46 = '"+ Has.get("B46") + "',";

          	    				query += " no51 = '"+ Has.get("B51") + "',";
          	    				query += " no52 = '"+ Has.get("B52") + "',";
          	    				query += " no53 = '"+ Has.get("B53") + "',";
          	    				query += " no54 = '"+ Has.get("B54") + "',";
          	    				query += " no56 = '"+ Has.get("B56") + "',";

          	    				query += " no61 = '"+ Has.get("B61") + "',";          	    
          	    				query += " no62 = '"+ Has.get("B62") + "',";
 			       		      	query += " no63 = '"+ Has.get("B63") + "',";
          	    				query += " no64 = '"+ Has.get("B64") + "',";
          	    				query += " no65 = '"+ Has.get("B65") + "'";

          	    				query += " where raceyy = '" + db_system_year + "'";
          	    				query += " and racetimes = '" + db_system_seq + "'";
          	    				query += " and daystimes  = '" + db_system_day + "'";
          	    				query += " and mbrcd = '" + grount_flag  + "'";
          	    				query += " and raceno = '" + Has.get("RN") + "'";

     					  	if (Has.get("WF").equals("EX ")) query += " and winflag ='E'";
     		  				else if (Has.get("WF").equals("QU ")) query += " and winflag ='Q'";
          				} else {
      	  					query  = "update dyrate set ";          		
          	    				query += " no1 = '"+ Has.get("B11") + "',";
          	    				query += " no2 = '"+ Has.get("B12") + "',";          	    
          	    				query += " no3 = '"+ Has.get("B13") + "',";
          	    				query += " no4 = '"+ Has.get("B14") + "',";
          	    				query += " no5 = '"+ Has.get("B15") + "',";
          	    				query += " no6 = '"+ Has.get("B16") + "'";

       		    				query += " where raceyy = '" + db_system_year + "'";
      		    				query += " and racetimes = '" + db_system_seq + "'";
       		    				query += " and daystimes  = '" + db_system_day + "'";
       		    				query += " and mbrcd = '" + grount_flag  + "'";
       		    				query += " and raceno = '" + Has.get("RN") + "'";

						if (Has.get("WF").equals("WIN")) query += " and winflag ='W'";
		     		  		else if (Has.get("WF").equals("PLC")) query += " and winflag ='P'";
       					}
	     	  		} // if flag
	     		sqlOutput("PB Query : " + query);	
   	  		stmt.executeUpdate(query);
   	  		conn.commit();   		  	
    		} catch(ClassNotFoundException e ){
  			System.out.println("오라클 드라이버 Error...! ");
  		}catch(SQLException e){
			try{ conn.rollback();} catch(Exception p){}
			sqlOutput("PS : Query Error :" + query);
  		}catch(Exception e){
		}finally{
                        if ( stmt != null ) try {stmt.close();} catch (Exception e) {}
                        if ( conn != null ) try {conn.close();} catch (Exception e) {}
                }
 	}

/////////////////////////// 삼복승식 중간배당률(TR) 처리루틴 시작 ////////////////////////////
/* ---------------------------------------------------------------------------------------- */
/*                                  TR Data Proc                                            */
/* ---------------------------------------------------------------------------------------- */
/*     Input : Hashtable                                                                    */
/*     Table : tlrate                                                                       */
/*     Proc  : 1 . 마감여부 (진행중일때만 처리 )                                            */
/*             2 . tlrate Table  Insert/Update                                              */  
/*                 (삼복승식 중간배당률(TR) 처리 루틴 - by sdhong. 2004. 2.)                */
/* ---------------------------------------------------------------------------------------- */
  	public void TrProc(Hashtable Has)
  	{
  		try{
  			System.out.println("TR DATA ");
			Class.forName("oracle.jdbc.driver.OracleDriver");
  	 		conn	=	DriverManager.getConnection("jdbc:oracle:thin:@160.100.1.45:1521:mbrrdb", "mbrdba", "mbrdba");
			//conn	=	DriverManager.getConnection("jdbc:oracle:thin:@160.100.1.45:1521:mbrrdb", "devuser", "devpass");
 	  		stmt	=	conn.createStatement();
    			
			conn.setAutoCommit(false);

     			query  = " select count(*) from tlrate ";
	     		query += " where raceyy = '" + db_system_year + "'";
     			query += " and racetimes = '" + db_system_seq + "'";
   	  		query += " and daystimes  = '" + db_system_day + "'";
     			query += " and mbrcd = '" + grount_flag  + "'";
     			query += " and raceno = '" + Has.get("RN") + "'";     			
     	 		
			sqlOutput("TR  Count :" + query);
     			rset=stmt.executeQuery(query);
     	
     		  	if (rset.next()) 
				i_flag = rset.getInt(1); 
			rset.close();
  			//System.out.println(i_flag);
     	  		if (i_flag == 0) {
     	  			if (Has.get("WF").equals("TLA")) {
	     	  			query  = " insert into tlrate values ";
  	       	    			query += "('" + db_system_year + "',"; 
					query += "'" + db_system_seq  + "',";
   	      	    			query += "'" + db_system_day  + "',";
   	      	    			query += "'" + grount_flag    + "',";
   	      	   			query += "'" + Has.get("RN")  + "',";
         	    
   	      	    			query += "'" + Has.get("B123") + "',";
   	      	    			query += "'" + Has.get("B124") + "',";
   	      	    			query += "'" + Has.get("B125") + "',";
   	      	    			query += "'" + Has.get("B126") + "',";

   	      	    			query += "'" + Has.get("B134") + "',";
   	      	    			query += "'" + Has.get("B135") + "',";
   	      	    			query += "'" + Has.get("B136") + "',";

   	      	    			query += "'" + Has.get("B145") + "',";
   	      	    			query += "'" + Has.get("B146") + "',";

   	      	    			query += "'" + Has.get("B156") + "',";

   	      	    			query += "'" + Has.get("B234") + "',";
   	      	    			query += "'" + Has.get("B235") + "',";
   	      	    			query += "'" + Has.get("B236") + "',";

   	      	    			query += "'" + Has.get("B245") + "',";
   	      	    			query += "'" + Has.get("B246") + "',";

   	      	    			query += "'" + Has.get("B256") + "',";

   	      	    			query += "'" + Has.get("B345") + "',";
   	      	    			query += "'" + Has.get("B346") + "',";

   	      	    			query += "'" + Has.get("B356") + "',";

   	      	    			query += "'" + Has.get("B456") + "')";

   	      	  		} else {
					// 승식 코드가 TLA 가 아닐 때 에러처리 루틴 필요
				}
   		      	 
     	  		} else {
     		  	                                                             // Update
    	  			if (Has.get("WF").equals("TLA")) {
      	  				query  = "update tlrate set ";
   	      	    			query += " no123 = '" + Has.get("B123") + "',";
   	      	    			query += " no124 = '" + Has.get("B124") + "',";
   	      	    			query += " no125 = '" + Has.get("B125") + "',";
   	      	    			query += " no126 = '" + Has.get("B126") + "',";

   	      	    			query += " no134 = '" + Has.get("B134") + "',";
   	      	    			query += " no135 = '" + Has.get("B135") + "',";
   	      	    			query += " no136 = '" + Has.get("B136") + "',";

   	      	    			query += " no145 = '" + Has.get("B145") + "',";
   	      	    			query += " no146 = '" + Has.get("B146") + "',";

   	      	    			query += " no156 = '" + Has.get("B156") + "',";

   	      	    			query += " no234 = '" + Has.get("B234") + "',";
   	      	    			query += " no235 = '" + Has.get("B235") + "',";
   	      	    			query += " no236 = '" + Has.get("B236") + "',";

   	      	    			query += " no245 = '" + Has.get("B245") + "',";
   	      	    			query += " no246 = '" + Has.get("B246") + "',";

   	      	    			query += " no256 = '" + Has.get("B256") + "',";

   	      	    			query += " no345 = '" + Has.get("B345") + "',";
   	      	    			query += " no346 = '" + Has.get("B346") + "',";

   	      	    			query += " no356 = '" + Has.get("B356") + "',";

   	      	    			query += " no456 = '" + Has.get("B456") + "'";

          	    			query += " where raceyy = '" + db_system_year + "'";
          	    			query += " and racetimes = '" + db_system_seq + "'";
          	    			query += " and daystimes  = '" + db_system_day + "'";
          	    			query += " and mbrcd = '" + grount_flag  + "'";
          	    			query += " and raceno = '" + Has.get("RN") + "'";

				} else {
					// 승식 코드가 TLA 가 아닐 때 에러처리 루틴 필요
       				}

     	  		} // if flag
     			sqlOutput("PB Query : " + query);	
			stmt.executeUpdate(query);
   	  		conn.commit();   		  	

    		} catch(ClassNotFoundException e ){
  			System.out.println("오라클 드라이버 Error...! ");
  		} catch(SQLException e){
			try{ conn.rollback();} catch(Exception p){}
			sqlOutput("PS : Query Error :" + query);
  		} catch(Exception e){
		} finally{
                        if ( stmt != null ) try {stmt.close();} catch (Exception e) {}
                        if ( conn != null ) try {conn.close();} catch (Exception e) {}
                }
 	}

/////////////////////////// 삼복승식 중간배당률(TR) 처리루틴 끝 //////////////////////////////
 		
 		
/* ---------------------------------------------------------------------------------------- */
/*                                  RS Data Proc                                            */
/* ---------------------------------------------------------------------------------------- */
/*     Input : Hashtable                                                                    */
/*     Table : dydecision,eqdecision                                                        */
/*     Proc  : 1 . 마감여부 (마감일 경우만 처리 )                                           */
/*             2 . dydecision,eqdecision Table  Insert/Update                               */  
/*                 (단승,연승 : dydecision    쌍승,복승 : eqdecision)                       */
/* ---------------------------------------------------------------------------------------- */
 	public void RsProc(Hashtable Has,int WinCount)
  	{
  		try{
  			Class.forName("oracle.jdbc.driver.OracleDriver");
  	 	  	conn	=	DriverManager.getConnection("jdbc:oracle:thin:@160.100.1.45:1521:mbrrdb", "mbrdba", "mbrdba");
			//conn	=	DriverManager.getConnection("jdbc:oracle:thin:@160.100.1.45:1521:mbrrdb", "devuser", "devpass");
 	  		stmt	=	conn.createStatement();

//    			conn.setAutoCommit(false);

			// 쌍,복승식 확정배당률 - eqdecision
    			if ((Has.get("WF").equals("EX ")) || (Has.get("WF").equals("QU "))){	
   	  			for(int i=1;i<=WinCount;i++){              // WinCount 만큼 등록(동착) 
    	  				query  = " insert into eqdecision values ";
		       	 		query += "('" + db_system_year + "',";
 	      	    			query += "'" + db_system_seq  + "',";
 	      	    			query += "'" + db_system_day  + "',";
 	      	    			query += "'" + grount_flag    + "',";
 	      	    			query += "'" + Has.get("RN")  + "',";
 	      	    				
					if (Has.get("WF").equals("EX ")) 
						query += "'E',";
 	      	  			else if (Has.get("WF").equals("QU ")) 
						query += "'Q',";
 	      	    				
					query += "'" + Has.get("WN1"+i) + "',";
 	      	  			query += "'" + Has.get("WN2"+i) + "',";    	    
 	      	  			query += "'" + Has.get("BT"+i)  + "',";
 	      	  			query += "'" + Has.get("BB" +i) + "')";

					sqlOutput("RS :RS Count" + query);
 		  			stmt.executeUpdate(query); 	      	    	
				}
			// 단,연승식 확정배당률 - dydecision
			} else if ((Has.get("WF").equals("WIN")) || (Has.get("WF").equals("PLC"))) {
   	  			for(int i=1;i<=WinCount;i++){		// WinCount 만큼 등록(동착)
   	  		  		if (Has.get("WF").equals("WIN")) {
						query  = " insert into dydecision values ";
	       	 		  		query += "('" + db_system_year + "',";
 	      	    				query += "'" + db_system_seq  + "',";
 	      	    				query += "'" + db_system_day  + "',";
 	      	    				query += "'" + grount_flag    + "',";
 	      	    				query += "'" + Has.get("RN")  + "',";
 	      	    				query += "'" + Has.get("WN"+i) + "',";
 	      	    				query += "'1',";    	    
 	      	    				query += "'" + Has.get("BT"+i)  + "',";
					        query += "'"+ Has.get("BB" +i) +"',' ')";

					        sqlOutput("i 값 RS :단승 " + i + "   " + query);
	   	 		  		stmt.executeUpdate(query);
 	      	  			} else {
						query  = " select count(*) cnt from dydecision ";
         	    		  		query += " where raceyy = '" + db_system_year + "'";
          	    		        	query += " and racetimes = '" + db_system_seq + "'";
          	    				query += " and daystimes  = '" + db_system_day + "'";
          	    				query += " and mbrcd = '" + grount_flag  + "'";
          	    				query += " and raceno = '" + Has.get("RN") + "'";
          	    				query += " and baebun = '" + Has.get("WN"+i) + "'";
                                        	query += " and rank   = '1'";
                      
						sqlOutput("RS :연승체크 " + i + "   " + query);
						rset = stmt.executeQuery(query);

						i_flag = 0;	

						if (rset.next())    
							i_flag = rset.getInt("cnt");

						if (i_flag==1) {	// 연승일때 단승에 Update
							query  = "Update dydecision set ";
							query += " rank = '1',";
							query += " stateflag = '" + Has.get("BT"+i) + "',";
							query += " place = '" + Has.get("BB"+i) + "'"; 
         	    					query += " where raceyy = '" + db_system_year + "'";
          	    					query += " and racetimes = '" + db_system_seq + "'";
          	    					query += " and daystimes  = '" + db_system_day + "'";
          	    					query += " and mbrcd = '" + grount_flag  + "'";
          	    					query += " and raceno = '" + Has.get("RN") + "'";
          	    					query += " and baebun = '" + Has.get("WN"+i) + "'";
						} else {	        // 연승일때 Insert
							query  = " insert into dydecision values ";
       		 		  			query += "('" + db_system_year + "',";
      	    						query += "'" + db_system_seq  + "',";
       	    						query += "'" + db_system_day  + "',";
      	    						query += "'" + grount_flag    + "',";
      	    						query += "'" + Has.get("RN")  + "',";
      	    						query += "'" + Has.get("WN"+i) + "',";
      	    						query += "'2',";
      	    						query += "'" + Has.get("BT" + i)  + "',' ',";
							query += "'"+ Has.get("BB" +i) +"')";
						} //if end
						sqlOutput("i 값 RS :연승 " + i + "   " + query);
						stmt.executeUpdate(query); 	      	    								   			
					} //if end

				} //for end	

			// 삼복승식 확정 배당률 - tldecision
			} else if (Has.get("WF").equals("TLA")) {
   	  			for(int i=1;i<=WinCount;i++){		// WinCount 만큼 등록(동착)
    	  				query  = " insert into tldecision values ";
		       	 		query += "('" + db_system_year + "',";
 	      	    			query += "'" + db_system_seq  + "',";
 	      	    			query += "'" + db_system_day  + "',";
 	      	    			query += "'" + grount_flag    + "',";
 	      	    			query += "'" + Has.get("RN")  + "',";
					query += "'T',";
					query += "'" + Has.get("WN1"+i) + "',";
 	      	  			query += "'" + Has.get("WN2"+i) + "',";    	    
 	      	  			query += "'" + Has.get("WN3"+i) + "',";    	    
 	      	  			query += "'" + Has.get("BT"+i)  + "',";
 	      	  			query += "'" + Has.get("BB" +i) + "')";

					sqlOutput("RS :RS Count" + query);
 		  			stmt.executeUpdate(query); 	      	    	
				}
			} //if end

 	//	  	conn.commit();   		  	

		} catch(ClassNotFoundException e ){
  			System.out.println("오라클 드라이버 Error...! ");
  		} catch(SQLException e){
			//try{ conn.rollback();} catch(Exception p){}
  			sqlOutput(" Query Error : " + query + e.toString());
  		} catch(Exception e){
  		} finally{
                        if ( rset != null ) try {rset.close();} catch (Exception e) {}
                        if ( stmt != null ) try {stmt.close();} catch (Exception e) {}
                        if ( conn != null ) try {conn.close();} catch (Exception e) {}
		}
	}

/* ---------------------------------------------------------------------------------------- */
/*                                  FN Data Proc                                            */
/* ---------------------------------------------------------------------------------------- */
/*     Input : Hashtable                                                                    */
/*     Table : fndecision
/* ---------------------------------------------------------------------------------------- */
 	public void FnProc(Hashtable Has,int n_01_cnt,int n_02_cnt,int n_03_cnt,int n_04_cnt)
  	{
  		try{
  			Class.forName("oracle.jdbc.driver.OracleDriver");
  	 	  	conn	=	DriverManager.getConnection("jdbc:oracle:thin:@160.100.1.45:1521:mbrrdb", "mbrdba", "mbrdba");
  	 	  	//conn	=	DriverManager.getConnection("jdbc:oracle:thin:@160.100.1.45:1521:mbrrdb", "devuser", "devpass");
 	  		stmt	=	conn.createStatement();
			
			int n[] = new int[4];
			String name = "";
    			conn.setAutoCommit(false);
			
			n[0] = n_01_cnt;
			n[1] = n_02_cnt;
			n[2] = n_03_cnt;
			n[3] = n_04_cnt;
				
			for(int i=1;i<=4;i++){
				name = "N"+i;
				if(n[i-1]>0){
					for(int j=1;j<=n[i-1];j++){
						query  = " insert into fndecision ";
						query += " (raceyy,racetimes,daystimes,raceno,rank,baebun)";
						query += " values (";
						query += "'" + db_system_year + "',";
						query += "'" + db_system_seq + "',";
						query += "'" + db_system_day + "',";
						query += "'" + Has.get("NO") + "',";
						query += "'" + i + "',";
						query += "'" + Has.get(name+j)+"')";
						sqlOutput("FN : " + query);
						stmt.executeUpdate(query);
					}
				}
			}								
 	  		conn.commit();   		  	
		} catch(ClassNotFoundException e ){
  			System.out.println("오라클 드라이버 Error...! ");
  		} catch(SQLException e){
			try{ conn.rollback();} catch(Exception p){}
  			sqlOutput(" Query Error : " + query);
  		} catch(Exception e){
  		} finally{
                        if ( rset != null ) try {rset.close();} catch (Exception e) {}
                        if ( stmt != null ) try {stmt.close();} catch (Exception e) {}
                        if ( conn != null ) try {conn.close();} catch (Exception e) {}
		}
 	}
 	
	public void sqlOutput(String beadang)
        {
                FileWriter fout = null;
                try
                {
                        //System.out.println(" LogWrite :");
                        //fout = new FileWriter("/testdb/query.txt", true);
                        fout = new FileWriter("/testdb/DevDB/devuser/query.txt", true);
                        fout.write(beadang.trim() + "\r\n");
                        fout.flush();
                        fout.close();
                }catch(IOException e){
                        System.out.println(" LogWrite Error : " + e.getMessage());
                }
        }
 }
