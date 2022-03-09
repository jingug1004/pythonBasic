<%@ page language="java" import="java.util.*, java.sql.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%
 Connection con = null;



 String jdbcUrl ="jdbc:oracle:thin:@(DESCRIPTION= (failover=on)(Address_list=(load_balance=on)(ADDRESS=(PROTOCOL=TCP)(HOST=160.100.1.35)(PORT=1521)) (ADDRESS=(PROTOCOL=TCP)(HOST=160.100.1.36)(PORT=1521))) (CONNECT_DATA=(SERVICE_NAME=SPDM)))"; 
 String dbUser = "usrbm";
 String dbPass = "rbmpass";

 Class.forName("oracle.jdbc.driver.OracleDriver"); 
 

%>
<%!

    public  ArrayList   getResultSetToMap(ResultSet rs){
	
	    ArrayList rtnList = new ArrayList(10);
	    
	    try { 
	    	
	    	  ResultSetMetaData rsmd = rs.getMetaData();
	          
	          String[] colNames = new String[rsmd.getColumnCount()];
	          for (int j = 0; j < colNames.length; j++) {
	                colNames[j] = rsmd.getColumnName(j + 1);
	          }
	          
	          while (rs.next()) {
	      
	              HashMap map = new HashMap();
	              for (int k = 0; k < colNames.length; k++) {
	                  map.put(colNames[k], rs.getString(k + 1));
	              }
	              
	              rtnList.add(map);
	          }

	    } catch(SQLException e) {

	        e.printStackTrace();   
	 
	    }
	    
	    return rtnList;
  }

%>
