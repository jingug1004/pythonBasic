<%@ page language="java" import="java.util.*, java.sql.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%@ page import="javax.naming.*, javax.sql.*" %>
<%@ include file="comDBManager.jsp" %>
<%!  
	String getData(Vector rVector, int idx, String colname) {
		String strResult = "";
		Hashtable tHash = (Hashtable)rVector.elementAt(idx);
		strResult = (String)tHash.get(colname);
		return strResult;
	}

	int getIntData(Vector rVector, int idx, String colname) {
		int intResult = 0;
		String strResult = "";
		Hashtable tHash = (Hashtable)rVector.elementAt(idx);
		strResult = (String)tHash.get(colname);
		try {
			if(strResult != null) {
				intResult = Integer.parseInt(strResult);	
			}
		} catch(Exception e) {
			intResult = 0;
		}
		return intResult;
	}
	
	String getDataHtml(Vector rVector, int idx, String loginUser, String colname) {
		String strDataHtml = "";
		String strResult = "";
		Hashtable tHash = (Hashtable)rVector.elementAt(idx);
		
		strResult = (String)tHash.get(colname);
		if(("^^").equals(strResult)) {
			strDataHtml = "";
		} else {
			String[] afterStr = strResult.split(",");
			for(int i=0; i<afterStr.length; i++) {

				String[] finalStr = afterStr[i].split(":");
				if(finalStr.length == 3) {
				//조성문^U0005074^정보지원팀
					//strDataHtml += "<div style='display:inline;' alt='"+finalStr[1]+"'>"+finalStr[0]+"&nbsp;</div>";
					if(finalStr[1].equals(loginUser)) //로그인 사용자의 아이디 이면 
						strDataHtml += "<a href=\"javascript:deleteReg();\"><font color=blue>"+finalStr[0]+"</font>,&nbsp;</a>";	
					else
						strDataHtml += "<div style='display:inline;' alt='"+finalStr[2]+"'>"+finalStr[0]+",&nbsp;</div>";
					if(i!= 0 && (i+1)%8==0) strDataHtml += "<br>";	
						
				}	
				//strDataHtml = String.valueOf(finalStr.length);
			}
		}
		return strDataHtml;
	}

	String getMesg(String MesgId) {
		String mesg = "";
		if (MesgId == null) return mesg;
		if (MesgId == "")   return mesg;
		int mesgNo = Integer.parseInt(MesgId);
		switch(mesgNo) {
			case 101:
				mesg = "정상적으로 등록했습니다.";
				break;
			case 102:	 
				mesg = "오류가 발생했습니다.";
				break;
			case 201:
				mesg = "정상적으로 삭제했습니다.";
				break;
			case 202:	 
				mesg = "오류가 발생했습니다.";
				break;
		}
		return mesg;
	}

%>
<%
	String strUserId        = (String)session.getAttribute("EMP_NO");
	String strUserName      = (String)session.getAttribute("EMP_NM");
	String strUserGroupId   = (String)session.getAttribute("DEPT_CD");
	String strUserGroupName = (String)session.getAttribute("DEPT_NM");

	if (strUserId == null) {
	      out.println("<script >location.href='restLogin.jsp';</script>");
		  return;
	}
	String strMessage   = request.getParameter("message");
	strMessage=getMesg(strMessage);
    
    String strRsvYear = "2013";
    String strRsvTms  = "01";
        
    String strRstId	  	= "";
    String strRstSeq	= "";
    String strRstName	= "";
    String strRsvMaxNum = "";
    String strUseStrDt	= "";
    String strUseEndDt	= "";
    String strDayNum	= "";
    String strUserid	= "";
    String strUseUserName	= "";
    String strDeptCd	= "";
    String strDeptName  = "";
    String strRegCnt	= "";

    Vector rVect01 = new Vector();	// 블레스오션펜션
    
    String strVect01 = "20130101";
    
    int cnt01=0;
    
    int intUsedCnt = 0;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    try { 
//        con = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
    	Context ctx = new InitialContext();
    	if (ctx != null) {
    		DataSource ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/RBM");
    		if (ds != null) {
    			con = ds.getConnection();    			
    		}
    	}    	
        
    } catch(SQLException e) {
        out.println(e.toString());
        e.printStackTrace();   
        return;
    }
    
    
    try {
    	 //휴양소 신청현황조회
         String query = "";
         
         query  ="";
         query += " select	rsv_year, rsv_tms, rst_id, rst_seq,              ";
         query += "         max(rst_name) rst_name, max(rsv_max_num) rsv_max_num, "; 
         query += "         substr(max(use_str_dt),5,2)||'월'||substr(max(use_str_dt),7,2)||'일' use_str_dt, ";
         query += "         substr(max(use_end_dt),5,2)||'월'||substr(max(use_end_dt),7,2)||'일' use_end_dt, "; 
         query += "         max(day_num) day_num, count(user_id) reg_cnt,    ";
         query += "         substr(                                          ";   
         query += "               XMLAGG(                                    ";
         query += "                     XMLELEMENT(DD,',',user_name||':'||user_id||':'||dept_name) order by user_name  ";
         query += "                     ).EXTRACT('//text()').GETSTRINGVAL() ";
         query += "                ,2) AS user_info                          ";
         query += " from (                                                   ";
         query += "         SELECT a.rsv_year, a.rsv_tms, a.rst_id, a.rst_seq, a.rst_name, a.rsv_max_num, a.use_str_dt, a.use_end_dt, a.day_num, ";
         query += "                b.user_id, b.user_name, b.dept_cd, b.dept_name ";
         query += "         FROM   tbrf_app_rst_master a, tbrf_app_rst_detl b ";
         query += "         WHERE 1=1                                        ";
         query += "         and a.rsv_year = ?                               ";
         query += "         and a.rsv_tms  = ?                               ";
         query += "         and a.rsv_year = b.rsv_year(+)                   ";
         query += "         and a.rsv_tms = b.rsv_tms(+)                     ";
         query += "         and a.rst_id = b.rst_id(+)                       ";
         query += "         and a.rst_seq = b.rst_seq(+)                     ";
         query += "      )                                                   ";
         query += " group by rsv_year, rsv_tms, rst_id, rst_seq              ";
         query += " order by 1,2,3,4                                         ";

         pstmt = con.prepareStatement(query);

         int i =1;
         pstmt.setString(i++, strRsvYear );
         pstmt.setString(i++, strRsvTms  );

         rs = pstmt.executeQuery();

         while (rs.next()) {
			Hashtable rHash = new Hashtable();
			strRstId = rs.getString("rst_id");
			rHash.put("rstId",   strRstId);
			rHash.put("rstSeq",  rs.getString("rst_seq"));
			rHash.put("rstName", rs.getString("rst_name"));
			rHash.put("maxNum",  rs.getString("rsv_max_num"));
			rHash.put("strDt",   rs.getString("use_str_dt"));
			rHash.put("endDt",   rs.getString("use_end_dt"));
			rHash.put("dayNum",  rs.getString("day_num"));
			rHash.put("userInfo",rs.getString("user_info"));
			rHash.put("regCnt",  rs.getString("reg_cnt"));
			
			if(strVect01.equals(strRstId)) {
				rVect01.addElement(rHash);	
				cnt01++;
			}
         }
		 //System.out.println("cnt01="+cnt01);
    } catch(SQLException e) {
    	out.println(e.toString());
    } finally {
        if(con != null) try{con.close();}       catch(Exception e){}
        if(pstmt != null) try{pstmt.close();}   catch(SQLException sqle){}
	}

%>    
    
<header>
<link rel="stylesheet" href="css/default.css" type="text/css" />
<link rel="stylesheet" href="css/restmain.css" type="text/css" />
<link rel="stylesheet" href="css/restpublic.css" type="text/css" />
<link rel="stylesheet" href="css/resttable.css" type="text/css" />
<script>
	function makeReg(rstId, rstSeq) {
		if(confirm("등록하시겠습니까?")) {
			document.location.href = "restRegister.jsp?rsvYear=<%=strRsvYear%>&rsvTms=<%=strRsvTms%>&rstId="+rstId+"&rstSeq="+rstSeq+"&mobile=N";
		}

	}
	
	function deleteReg() {
		if(confirm("삭제하시겠습니까?")) {
			document.location.href = "restDelete.jsp?rsvYear=<%=strRsvYear%>&rsvTms=<%=strRsvTms%>"+"&mobile=N";
		}
	}
	
	function noticeMessage() {
		message = "<%=strMessage%>";
		if(message != "") alert(message);
	}
	noticeMessage();
	
	function goLink(flag) {
		if(flag == '01') {
			window.open('http://www.blessocean.com/','','target=_blank');
		}
	}
</script>

</header>
<body>

<br>

<table width=95%>
<tr>
<td align=right width=70%>&nbsp;
<%=strUserGroupName%> <b><%=strUserName%></b>님 접속을 환영합니다.
<!-- <%if(intUsedCnt>0){%><font color=red><b>귀하는 사용 내역이 있으므로 추가 신청이 불가능합니다.</b></font>&nbsp;&nbsp;<%}%> -->
</td>
<td width=30% align=right>
<table  cellpadding="0" cellspacing="0" class="btn">
<tr>
	<th class="btnL"></th><th class="btnC">
	<a href="restPassChange.jsp"><img src="img/btn_pass.jpg" alt="비밀번호 변경" /></a> 
    <a href="restLogOut.jsp"><img src="img/btn_logout.jpg" alt="로그아웃" />
	</th><th class="btnR"></th>
</tr>
</table>	
</td>	
<td width=20 align=right></td>
</table>

<br>

<table width="95%">
	<tr>
		<td width="100%">
			<table class='list'>
				
				<tr>
					<td colspan=3>
					<a href="javascript:goLink('01');"><img id="idVect01" src="./img/BlessOcean.png" width="227" height="70" alt="블레스오션펜션 홈페이지로 이동"></a>
					<br>신청이나 변경은 해당하는 숙박기간을 클릭하시면 됩니다. 취소시에는 본인 이름을 클릭하세요.
					</td>
				</tr>
				
				<tr>
					<th class="right_b blue" width="30%"  align=center>숙박기간</th>
					<th class="right_b blue" width="20%"  align=center>수용인원</th>
					<th class="right_b blue" width="50%" align=center>신청인원</th>
					
				</tr>
				<%
				if(rVect01 != null) {
					for(int i=0; i<cnt01; i++) {	
				%>
				<tr>	
					<%
						if(getIntData(rVect01,i,"regCnt") >= getIntData(rVect01,i,"maxNum")*5 || intUsedCnt>0) {
					%>
						<td align=center><%=getData(rVect01,i,"strDt")%><br>~<br><%=getData(rVect01,i,"endDt")%></td>
					<%
						} else {
					%>
						<td align=center><a href="javascript:makeReg('<%=strVect01%>','<%=getData(rVect01,i,"rstSeq")%>');"><b><%=getData(rVect01,i,"strDt")%>~<%=getData(rVect01,i,"endDt")%></b></a></td>
					<%
						}
					%>
					<td align=center><%=getData(rVect01,i,"maxNum")%></td>
					<td align=left  ><%=getDataHtml(rVect01,i,strUserId, "userInfo")%></td>
					
				</tr>
				<%	
					}
				}	
				%>
			</table>
		</td>
	</tr>
</table>
</body>
