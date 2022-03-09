<%@ page language="java" import="java.util.*, java.sql.*,java.lang.*,javax.sql.*,javax.naming.*,java.io.*,java.net.*" session="true" contentType="text/html;charset=euc-kr" %>
<%@ include file="comDBManager.jsp" %>
<%!
	String nullToStr(String sString, String sDefault)
	{
	    if ( sString == null || sString.equals("null") ) {
	        return sDefault;
	    }
	
	    return sString;
	}

	String getData(Vector rVector, int idx, String colname) {
		String strResult = "";
		Hashtable tHash = (Hashtable)rVector.elementAt(idx);
		strResult = (String)tHash.get(colname);
		return strResult;
	}
%>
<%
   String sMeetCd  = request.getParameter("MEET_CD");
   String sRaceDay = request.getParameter("RACE_DAY");
   
   sMeetCd  = "001";
   sRaceDay = "20161023";
%>
<% 

	Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String query ="";
    int i =1;
    
    ArrayList salesList = new ArrayList(10);  
    
    try { 
    	 Context context = new InitialContext();
         DataSource ds = (DataSource)context.lookup("java:/comp/env/jdbc/RBM");
         con = ds.getConnection();
    } catch(SQLException e) {
         
        out.println(e.toString());
        e.printStackTrace();   
        return;
    }
    
    Vector rVect001 = new Vector(); 
    Vector rVect002 = new Vector();
    Vector rVect003 = new Vector();
    Vector rVect004 = new Vector();
    int cnt001 = 0;
    int cnt002 = 0;
    int cnt003 = 0;
    int cnt004 = 0;

     try {
         
         query ="";
         query += " WITH A AS (                                                                            \n";
         query += "     SELECT                                                                             \n";
         query += "     	STND_YEAR, SELL_CD, TMS,                                                       \n";
         query += "         SUM(DECODE(STND_YEAR, SUBSTR(?,1,4),     NET_AMT,0)) N_STND_AMT,               \n";
         query += "         SUM(DECODE(STND_YEAR, SUBSTR(?,1,4)*1 -1,NET_AMT,0)) B_STND_AMT,               \n";
         query += "         SUM(DECODE(STND_YEAR, SUBSTR(?,1,4)*1 -2,NET_AMT,0)) B2_STND_AMT               \n";
         query += "     FROM VW_PC_PAYOFFS T1                                                              \n";
         if("003".equals(sMeetCd)) query += "     WHERE MEET_CD != '003'                                   \n";
         if("001".equals(sMeetCd)) query += "     WHERE MEET_CD != '003'                                   \n";
         query += " 	AND STND_YEAR >= SUBSTR(?,1,4)*1 -2                                                \n";
         query += " 	GROUP BY MEET_CD, STND_YEAR, TMS, SELL_CD                                          \n";
         query += " 	UNION ALL                                                                          \n";
         query += " 	SELECT --당일데이터 가져오기                                                       \n";
         query += "     	T1.STND_YEAR, T1.SELL_CD, T1.TMS,                                              \n";
         query += "         SUM(DECODE(T1.STND_YEAR, SUBSTR(?,1,4), T1.DIV_TOTAL,0)) N_STND_AMT,           \n";
         query += "         0 B_STND_AMT,                                                                  \n";
         query += "         0 B2_STND_AMT                                                                  \n";
         query += "     FROM TBES_SDL_DT T1, VW_SDL_INFO T2                                                \n";
         if("003".equals(sMeetCd)) query += "     WHERE T1.MEET_CD != '003'                                \n";
         if("001".equals(sMeetCd)) query += "     WHERE T1.MEET_CD != '003'                                \n";
         query += "     AND T1.MEET_CD = T2.MEET_CD                                                        \n";
         query += "     AND T1.STND_YEAR = T2.STND_YEAR                                                    \n";
         query += "     AND T1.TMS = T2.TMS                                                                \n";
         query += "     AND T1.DAY_ORD = T2.DAY_ORD                                                        \n";
         query += "     AND T2.RACE_DAY = DECODE(TO_CHAR(SYSDATE,'YYYYMMDD'),?,?,'')                       \n";
         query += " 	GROUP BY T1.MEET_CD, T1.STND_YEAR, T1.TMS, T1.SELL_CD                              \n";
         query += " )                                                                                      \n";
         query += " SELECT SELL_CD, TMS,                                                                   \n";
         query += "        ROUND(DECODE(SUM(N_STND_AMT),0,NULL,SUM(N_STND_AMT))/1000000) N_STND_AMT,       \n";
         query += "        ROUND(DECODE(SUM(B_STND_AMT),0,NULL,SUM(B_STND_AMT))/1000000) B_STND_AMT,       \n";
         query += "        ROUND(DECODE(SUM(B2_STND_AMT),0,NULL,SUM(B2_STND_AMT))/1000000) B2_STND_AMT     \n";
         query += " FROM A                                                                                 \n";
         query += " WHERE TMS IS NOT NULL                                                                  \n";
         query += " GROUP BY SELL_CD, TMS                                                                  \n";
         query += " ORDER BY SELL_CD, TMS                                                                  \n";
         
         pstmt = con.prepareStatement(query);

         int k =1;
         
         pstmt.setString(k++, sRaceDay);
         pstmt.setString(k++, sRaceDay);
         pstmt.setString(k++, sRaceDay);
         pstmt.setString(k++, sRaceDay);
         pstmt.setString(k++, sRaceDay);
         pstmt.setString(k++, sRaceDay);
         pstmt.setString(k++, sRaceDay);
         

         rs = pstmt.executeQuery();
         String tmpSellCd = "";
         while (rs.next()) {
        	 Hashtable rHash = new Hashtable();
        	 tmpSellCd = rs.getString("SELL_CD");
 			rHash.put("SELL_CD"    ,tmpSellCd);
 			rHash.put("TMS"        ,rs.getString("TMS"));
 			rHash.put("N_STND_AMT" ,nullToStr(rs.getString("N_STND_AMT"),"0"));
 			rHash.put("B_STND_AMT" ,nullToStr(rs.getString("B_STND_AMT"),"0"));
 			rHash.put("B2_STND_AMT",nullToStr(rs.getString("B2_STND_AMT"),"0"));
 			
 			if("01".equals(tmpSellCd)) {
 				rVect001.addElement(rHash);
 				cnt001++;
 			}
 			if("02".equals(tmpSellCd)) {
 				rVect002.addElement(rHash);
 				cnt002++;
 			}
 			if("03".equals(tmpSellCd)) {
 				rVect003.addElement(rHash);
 				cnt003++;
 			}
 			if("04".equals(tmpSellCd)) {
 				rVect004.addElement(rHash);
 				cnt004++;
 			}
         }
         
     } catch(SQLException e ) {
         
       out.println(e.toString());
     }

     try {
          if(rs !=null) rs.close();
          if(pstmt != null) pstmt.close();
          if(con != null) con.close();
     } catch(SQLException e) {
       out.println(e.toString());
     }

%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Flot Examples: Selection</title>
	<link href="./css/examples.css" rel="stylesheet" type="text/css">
	<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="./js/excanvas.min.js"></script><![endif]-->
	<script language="javascript" type="text/javascript" src="./js/jquery.js"></script>
	<script language="javascript" type="text/javascript" src="./js/jquery.flot.js"></script>
	<script language="javascript" type="text/javascript" src="./js/jquery.flot.selection.js"></script>
	<script type="text/javascript">

	$(function() {

		// Shim allowing us to get the state of the check-box on jQuery versions
		// prior to 1.6, when prop was added.  The reason we don't just use attr
		// is because it doesn't work in jQuery versions 1.9.x and later.

		// TODO: Remove this once Flot's minimum supported jQuery reaches 1.6.
		if (typeof $.fn.prop != 'function') {
		    $.fn.prop = $.fn.attr;
		}

		var data = [
			<%
			if("001".equals(sMeetCd) && rVect001 != null) { //경륜광명
				if (cnt001>0) {
					out.print("{\n");
					out.print("label: \"금년광명\",\n");
					out.print("data:[\n");
					for(i=0; i<cnt001; i++) {	
						if (getData(rVect001,i,"SELL_CD").equals("01")) {
							out.print("[" + getData(rVect001,i,"TMS")+","+getData(rVect001,i,"N_STND_AMT") + "]");
							if(i < cnt001-1 ) out.print(",");
						}
					}
					out.print("]\n");
					out.print("},\n");
					
					out.print("{\n");
					out.print("label: \"전년광명\",\n");
					out.print("data:[\n");
					for(i=0; i<cnt001; i++) {	
						if (getData(rVect001,i,"SELL_CD").equals("01")) {
							out.print("[" + getData(rVect001,i,"TMS")+","+getData(rVect001,i,"B_STND_AMT") + "]");
							if(i < cnt001-1 ) out.print(",");
						}
					}
					out.print("]\n");
					out.print("},\n");
					
					out.print("{\n");
					out.print("label: \"전전년광명\",\n");
					out.print("data:[\n");
					for(i=0; i<cnt001; i++) {	
						if (getData(rVect001,i,"SELL_CD").equals("01")) {
							out.print("[" + getData(rVect001,i,"TMS")+","+getData(rVect001,i,"B2_STND_AMT") + "]");
							if(i < cnt001-1 ) out.print(",");
						}
					}
					out.print("]\n");
					out.print("},\n");
				}
				
				if (cnt002>0) {
					out.print("{\n");
					out.print("label: \"금년창원\",\n");
					out.print("data:[\n");
					for(i=0; i<cnt002; i++) {	
						if (getData(rVect002,i,"SELL_CD").equals("02")) {
							out.print("[" + getData(rVect002,i,"TMS")+","+getData(rVect002,i,"N_STND_AMT") + "]");
							if(i < cnt002-1 ) out.print(",");
						}
					}
					out.print("]\n");
					out.print("},\n");
					
					out.print("{\n");
					out.print("label: \"전년창원\",\n");
					out.print("data:[\n");
					for(i=0; i<cnt002; i++) {	
						if (getData(rVect002,i,"SELL_CD").equals("02")) {
							out.print("[" + getData(rVect002,i,"TMS")+","+getData(rVect002,i,"B_STND_AMT") + "]");
							if(i < cnt002-1 ) out.print(",");
						}
					}
					out.print("]\n");
					out.print("},\n");
					
					out.print("{\n");
					out.print("label: \"전전년창원\",\n");
					out.print("data:[\n");
					for(i=0; i<cnt002; i++) {	
						if (getData(rVect002,i,"SELL_CD").equals("02")) {
							out.print("[" + getData(rVect002,i,"TMS")+","+getData(rVect002,i,"B2_STND_AMT") + "]");
							if(i < cnt002-1 ) out.print(",");
						}
					}
					out.print("]\n");
					out.print("},\n");
				}
				
				if (cnt004>0) {
					out.print("{\n");
					out.print("label: \"금년부산\",\n");
					out.print("data:[\n");
					for(i=0; i<cnt004; i++) {	
						if (getData(rVect004,i,"SELL_CD").equals("04")) {
							out.print("[" + getData(rVect004,i,"TMS")+","+getData(rVect004,i,"N_STND_AMT") + "]");
							if(i < cnt004-1 ) out.print(",");
						}
					}
					out.print("]\n");
					out.print("},\n");
					
					out.print("{\n");
					out.print("label: \"전년부산\",\n");
					out.print("data:[\n");
					for(i=0; i<cnt004; i++) {	
						if (getData(rVect004,i,"SELL_CD").equals("04")) {
							out.print("[" + getData(rVect004,i,"TMS")+","+getData(rVect004,i,"B_STND_AMT") + "]");
							if(i < cnt004-1 ) out.print(",");
						}
					}
					out.print("]\n");
					out.print("},\n");
					
					out.print("{\n");
					out.print("label: \"전전년부산\",\n");
					out.print("data:[\n");
					for(i=0; i<cnt004; i++) {	
						if (getData(rVect004,i,"SELL_CD").equals("04")) {
							out.print("[" + getData(rVect004,i,"TMS")+","+getData(rVect004,i,"B2_STND_AMT") + "]");
							if(i < cnt004-1 ) out.print(",");
						}
					}
					out.print("]\n");
					out.print("}\n");
				}
			}
			
			
			if("003".equals(sMeetCd) && rVect003 != null) {
				if (cnt003>0) {
					out.print("{\n");
					out.print("label: \"금년미사리\",\n");
					out.print("data:[\n");
					for(i=0; i<cnt003; i++) {	
						if (getData(rVect003,i,"SELL_CD").equals("03")) {
							out.print("[" + getData(rVect003,i,"TMS")+","+getData(rVect003,i,"N_STND_AMT") + "]");
							if(i < cnt003-1 ) out.print(",");
						}
					}
					out.print("]\n");
					out.print("},\n");
					
					out.print("{\n");
					out.print("label: \"전년미사리\",\n");
					out.print("data:[\n");
					for(i=0; i<cnt003; i++) {	
						if (getData(rVect003,i,"SELL_CD").equals("03")) {
							out.print("[" + getData(rVect003,i,"TMS")+","+getData(rVect003,i,"B_STND_AMT") + "]");
							if(i < cnt003-1 ) out.print(",");
						}
					}
					out.print("]\n");
					out.print("},\n");
					
					out.print("{\n");
					out.print("label: \"전전년미사리\",\n");
					out.print("data:[\n");
					for(i=0; i<cnt003; i++) {	
						if (getData(rVect003,i,"SELL_CD").equals("03")) {
							out.print("[" + getData(rVect003,i,"TMS")+","+getData(rVect003,i,"B2_STND_AMT") + "]");
							if(i < cnt003-1 ) out.print(",");
						}
					}
					out.print("]\n");
					out.print("}\n");
				}
			}
			%>
			];

		var options = {
			series: {
				lines: {
					show: true
				},
				points: {
					show: true
				}
			},
			legend: {
				noColumns: 2
			},
			xaxis: {
				tickDecimals: 0
			},
			yaxis: {
				min: 0
			},
			selection: {
				mode: "x"
			}
		};

		var placeholder = $("#placeholder");

		placeholder.bind("plotselected", function (event, ranges) {

			$("#selection").text(ranges.xaxis.from.toFixed(1) + " to " + ranges.xaxis.to.toFixed(1));

			var zoom = $("#zoom").prop("checked");

			if (zoom) {
				$.each(plot.getXAxes(), function(_, axis) {
					var opts = axis.options;
					opts.min = ranges.xaxis.from;
					opts.max = ranges.xaxis.to;
				});
				plot.setupGrid();
				plot.draw();
				plot.clearSelection();
			}
		});

		placeholder.bind("plotunselected", function (event) {
			$("#selection").text("");
		});

		var plot = $.plot(placeholder, data, options);

		$("#clearSelection").click(function () {
			plot.clearSelection();
		});

		$("#setSelection").click(function () {
			plot.setSelection({
				xaxis: {
					from: 1994,
					to: 1995
				}
			});
		});

		// Add the Flot version string to the footer

		$("#footer").prepend("Flot " + $.plot.version + " &ndash; ");
	});

	</script>
</head>
<body width="100%">
	<div width="100%" id="content">
		<div width="100%" class="demo-container">
			<div id="placeholder" class="demo-placeholder"></div>
		</div>
	</div>
</body>
</html>
