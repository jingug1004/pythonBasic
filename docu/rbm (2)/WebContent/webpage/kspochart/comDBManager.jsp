<%@ page language="java" import="java.util.*, java.sql.*" session="true"%>
<%!

    public  ArrayList getResultSetToMap(ResultSet rs){
	
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
  //String SDataSource = "java:jdbc/RBM"; //real
  String SDataSource = "java:/comp/env/jdbc/RBM"; //dev
%>
