<%-- 
 ���α׷���: ������ �� �Ͽ��� �ϰ��޾�ҽ�û
 �ۼ���: ������, ���缱 ����
 �ۼ���: 2013. 06. 12
 �ۼ�����: �ϰ��޾�� ��û
--%>
<%@ page language="java" import="java.util.*, java.sql.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%@ include file="comDBManager.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

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
				//������^U0005074^����������
					//strDataHtml += "<div style='display:inline;' alt='"+finalStr[1]+"'>"+finalStr[0]+"&nbsp;</div>";
					if(finalStr[1].equals(loginUser)) //�α��� ������� ���̵� �̸� 
						strDataHtml += "<a href=\"javascript:deleteReg();\"><font color=blue>"+finalStr[0]+"</font>,&nbsp;</a>";	
					else
						strDataHtml += "<div style='display:inline;' alt='"+finalStr[2]+"'>"+finalStr[0]+",&nbsp;</div>";
					if(i!= 0 && (i+1)%5==0) strDataHtml += "<br>";	
						
				}	
				//strDataHtml = String.valueOf(finalStr.length);
			}
		}
		return strDataHtml;
	}
%>
<%
	String strUserId        = (String)session.getAttribute("EMP_NO");
	String strUserName      = (String)session.getAttribute("EMP_NM");
	String strUserGroupId   = (String)session.getAttribute("DEPT_CD");
	String strUserGroupName = (String)session.getAttribute("DEPT_NM");

	String strMessage   = request.getParameter("message");
    
    String strRsvYear = "2013";
    String strRsvTms  = "02";
        
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

    Vector rVect01 = new Vector();	// �����������
    
    String strVect01 = "20130201";
    
    int cnt01=0;
    
    int intUsedCnt = 0;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    try { 
        con = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
    } catch(SQLException e) {
        out.println(e.toString());
        e.printStackTrace();   
        return;
    }
    
    
    try {
    	 //�޾�� ��û��Ȳ��ȸ
         String query = "";
         
         query  ="";
         query += " select	rsv_year, rsv_tms, rst_id, rst_seq,              ";
         query += "         max(rst_name) rst_name, max(rsv_max_num) rsv_max_num, "; 
         query += "         substr(max(use_str_dt),5,2)||'��'||substr(max(use_str_dt),7,2)||'��' use_str_dt, ";
         query += "         substr(max(use_end_dt),5,2)||'��'||substr(max(use_end_dt),7,2)||'��' use_end_dt, "; 
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
         query += "         and a.rsv_year = '2013'                          ";
         query += "         and a.rsv_tms  = '02'                            ";
         query += "         and a.rsv_year = b.rsv_year(+)                   ";
         query += "         and a.rsv_tms = b.rsv_tms(+)                     ";
         query += "         and a.rst_id = b.rst_id(+)                       ";
         query += "         and a.rst_seq = b.rst_seq(+)                     ";
         query += "      )                                                   ";
         query += " group by rsv_year, rsv_tms, rst_id, rst_seq              ";
         query += " order by 1,2,3,4                                         ";

         pstmt = con.prepareStatement(query);
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
				rVect01.addElement(rHash);	//���� ���غ����õ������ȣ��
				cnt01++;
			}
         }
    } catch(SQLException e) {
    	out.println(e.toString());
	}
%>    
    
<head>
<title> ������ �� �Ͽ��� �ϰ��޾�� ��û </title>
<meta http-equiv="Content-Type" contentType="text/html; charset=euc-kr"/>
<link rel="stylesheet" href="css/default.css" type="text/css" />
</head>

<header>
<link href="css/jquery-bubble-popup-v3.css" rel="stylesheet" type="text/css" />
<script src="scripts/jquery-bubble-popup-v3.min.js" type="text/javascript"></script>
<script>
	function makeReg(rstId, rstSeq) {
		if(confirm("����Ͻðڽ��ϱ�?")) {
			document.location.href = "restRegister.jsp?rsvYear=<%=strRsvYear%>&rsvTms=<%=strRsvTms%>&rstId="+rstId+"&rstSeq="+rstSeq;
		}

	}
	
	function deleteReg() {
		if(confirm("�����Ͻðڽ��ϱ�?")) {
			document.location.href = "restDelete.jsp?rsvYear=<%=strRsvYear%>&rsvTms=<%=strRsvTms%>";
		}
	}
	
	function noticeMessage() {
		message = "<%=strMessage%>";
		if(message != "") alert(message);
	}
	noticeMessage();
	
	$(document).ready(function(){

		$('#idVect01').CreateBubblePopup({
			position : 'top',
			align	 : 'center',
			innerHtml: '����������� <br /> \
						�泲 �¾ȱ� ����� ��Ȳ�� 1526-153',
			innerHtmlStyle: {
						color:'#FFFFFF', 
						'text-align':'center'
			},
			themeName: 'all-black',
			themePath: 'jquerybubblepopup-themes'
		});
	});
	
	function goLink(flag) {
		if(flag == '01') {
			window.open('http://www.blessocean.com/','','target=_blank');
		}
	}
</script>

</header>
<body>

<br>

<table width="1024">
<tr>
<td align=right width=900>&nbsp;
<%=strUserGroupName%> <b><%=strUserName%></b>�� ������ ȯ���մϴ�.
<!-- <%if(intUsedCnt>0){%><font color=red><b>���ϴ� ��� ������ �����Ƿ� �߰� ��û�� �Ұ����մϴ�.</b></font>&nbsp;&nbsp;<%}%> -->
</td>
<td width=50 align=right>
<table  cellpadding="0" cellspacing="0" class="btn">
<tr>
	<th class="btnL"></th><th class="btnC"><a href='javascript:this.close();'>�ݱ�</a></th><th class="btnR"></th>
</tr>
</table>	
</td>	
<td width=20 align=right></td>
</table>

<br>

<table width="1024">
	<tr>
		<td width="342">
			<table class='list'>
				
				<tr>
					<td colspan=3>
					<a href="javascript:goLink('01');"><img id="idVect01" src="./img/button_01.jpg" width="236" height="71" alt="���� ���غ����õ������ȣ��"></a>
					</td>
					<td colspan=3>
					<a href="javascript:goLink('02');"><img id="idVect02" src="./img/button_02.jpg" width="236" height="71" alt="�¾� �ϴð��ٴٻ���"></a>
					</td>
					<td colspan=3>
					<a href="javascript:goLink('03');"><img id="idVect03" src="./img/button_03.jpg" width="236" height="71" alt="���� ���쳪���Ǹ���Ʈ"></a>
					</td>
				</tr>
				
				<tr>
					<th class="right_b blue" width="70" align=center>����<br>�Ⱓ</th>
					<th class="right_b blue" width="35"  align=center>����<br>�ο�</th>
					<th class="right_b blue" width="237" align=center>��û<br>�ο�</th>
					
					<th class="right_b blue" width="70" align=center>����<br>�Ⱓ</th>
					<th class="right_b blue" width="35"  align=center>����<br>�ο�</th>
					<th class="right_b blue" width="237" align=center>��û<br>�ο�</th>
					
					<th class="right_b blue" width="70" align=center>����<br>�Ⱓ</th>
					<th class="right_b blue" width="35"  align=center>����<br>�ο�</th>
					<th class="right_b blue" width="237" align=center>��û<br>�ο�</th>
				</tr>
				<%
				if(rVect01 != null) {
					for(int i=0; i<cnt01; i++) {	//���� ���� ���ڸ� ������ �ִ� ���ڸ� ���� 
				%>
				<tr>	
					<%
						if(getIntData(rVect01,i,"regCnt") >= getIntData(rVect01,i,"maxNum")*5 || intUsedCnt>0) {
					%>
						<td align=center><%=getData(rVect01,i,"strDt")%><br>~<br><%=getData(rVect01,i,"endDt")%></td>
					<%
						} else {
					%>
						<td align=center><a href="javascript:makeReg('<%=strVect01%>','<%=getData(rVect01,i,"rstSeq")%>');"><b><%=getData(rVect01,i,"strDt")%><br>~<br><%=getData(rVect01,i,"endDt")%></b></a></td>
					<%
						}
					%>
					<td align=center><%=getData(rVect01,i,"maxNum")%></td>
					<td align=left  ><%=getDataHtml(rVect01,i,strUserId, "userInfo")%></td>
					
						<td align=center><br>~<br></td>
						<td align=center><br>~<br></td>
					<td align=center></td>
					<td align=left  ></td>
					
						<td align=center><br>~<br></td>
						<td align=center><br>~<br></td>
					<td align=center></td>
					<td align=left  ></td>
					
					
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
