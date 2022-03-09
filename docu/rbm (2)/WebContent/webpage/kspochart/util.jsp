<%@ page language="java" import="java.util.*, java.sql.*" %>
<%!
	String nullToStr(String sString, String sDefault)
	{
	    if ( sString == null || sString.equals("null") ) {
	        return sDefault;
	    }
	
	    return sString;
	}

	int strToInt(String sString, int iDefault)
	{
		int iTemp = 0;
	    if ( sString == null || sString.equals("null") ) {
	        return iDefault;
	    } else {
	    	return Integer.parseInt(sString);
	    }
}

	String getData(Vector rVector, int idx, String colname) {
		String strResult = "";
		Hashtable tHash;
		
		try {
			tHash = (Hashtable)rVector.elementAt(idx);
			strResult = (String)tHash.get(colname);
		} catch (Exception e) {
			strResult = "";
		}
		return strResult;
	}
%>