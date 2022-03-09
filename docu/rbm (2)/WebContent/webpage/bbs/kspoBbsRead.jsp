<%@ page language="java" import="java.util.*, java.sql.*,java.lang.*,javax.sql.*,javax.naming.*,java.io.*,java.net.*" session="true" contentType="text/html;charset=euc-kr" %>
<%@ include file="comDBManager.jsp" %>
<%
   String sBbsId = request.getParameter("bbsId");
   String sDocYearMon = request.getParameter("docYearMon");
   String sDocNumber = request.getParameter("docNumber");
   
   //sBbsId="B0010831";
   //sDocYearMon ="201201";
   //sDocNumber = "141502000";
   
   
   
   String sDocWriterName = "";
   String sDocSubject = "";
   String sDocText = "";
   String sDocRegDate = "";

   
%>

<% 

	Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String query ="";
    int i =1;
    
    String [][] strAttExtentions = {
            { "wav",  "att_wav.gif"   },
            { "avi",  "att_avi.gif"   },
            { "exe",  "att_exe.gif"   },
            { "hwp",  "att_hwp.gif"   },
            { "mp3",  "att_mp3.gif"   },
            { "ppt",  "att_ppt.gif"   },
            { "xls",  "att_xls.gif"   },
            { "bmp",  "att_bmp.gif"   },
            { "mpg",  "att_mpg.gif"   },
            { "swf",  "att_swf.gif"   },
            { "zip",  "att_zip.gif"   },
            { "doc",  "att_doc.gif"   },
            { "hlp",  "att_hlp.gif"   },
            { "jpg",  "att_jpg.gif"   },
            { "txt",  "att_txt.gif"   },
            { "htm",  "att_htm.gif"   },
            { "html", "att_htm.gif"   },
            { "mov",  "att_movie.gif" },
            { "pdf",  "att_pdf.gif"   },
            { "etc",  "att_file.gif"  }
        };
    
    
    ArrayList attList = new ArrayList(10);  
    
    try { 
    	 Context context = new InitialContext();
         DataSource ds = (DataSource)context.lookup("jdbc/JUPITER");
         con = ds.getConnection();
         
         //con = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
    } catch(SQLException e) {
         
        out.println(e.toString());
        e.printStackTrace();   
        return;
    }
    
    

     try {
         

         query ="";
         query += "         SELECT     ";
         query += "               A.DOC_YEARMON    ";
         query += "              ,A.DOC_NUMBER    ";
         query += "              ,A.BBS_ID    ";
         query += "              ,A.DOC_SUBJECT    ";
         query += "              ,A.DOC_TEXT    ";
         query += "              ,A.DOC_WRITER    ";
         query += "              ,A.DOC_WRITER_NAME    ";
         query += "              ,A.DOC_ATT_CNT    ";
         query += "              ,A.DOC_SEQ    ";
         query += "              ,SUBSTR(A.DOC_REGDATE,0,8)  DOC_REGDATE   ";
         query += "              ,A.DOC_REF_CNT      ";
         query += "      FROM BBS_DOC A    ";
         query += "   WHERE BBS_ID = ?   ";
         query += "      AND DOC_YEARMON = ?  ";
         query += "      AND DOC_NUMBER  = ?  ";

           
         pstmt = con.prepareStatement(query);

         int k =1;
         
         pstmt.setString(k++, sBbsId);
         pstmt.setString(k++, sDocYearMon);
         pstmt.setString(k++, sDocNumber);
         

         rs = pstmt.executeQuery();
         
         if(rs.next()){       
            	sDocWriterName = rs.getString("DOC_WRITER_NAME");
        	    sDocSubject = rs.getString("DOC_SUBJECT");
        	    sDocText = rs.getString("DOC_TEXT");
        	    sDocRegDate = rs.getString("DOC_REGDATE");
         }
         
         
         //첨부파일         
         query ="";
         query += "         SELECT     ";
         query += "               A.DOC_YEARMON    ";
         query += "              ,A.DOC_NUMBER    ";
         query += "              ,A.ATT_FILEPATH    ";
         query += "              ,A.ATT_SUBJECT    ";
         query += "              ,A.ATT_SEQNO    ";
         //query += "              ,FN_GET_FILE_EXT_IMG(doc_yearmon, doc_number, att_seqno,'BBS') AS ATT_IMG ";
         query += "      FROM BBS_ATTACH A    ";
         query += "   WHERE DOC_YEARMON = ?  ";
         query += "      AND DOC_NUMBER  = ?  ";
         query += "   ORDER BY  ATT_SEQNO ";

           
         pstmt = con.prepareStatement(query);

          k =1;
         
         pstmt.setString(k++, sDocYearMon);
         pstmt.setString(k++, sDocNumber);
         

         rs = pstmt.executeQuery();
         
     
         attList = getResultSetToMap(rs);

  
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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title> [게시판] </title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />

<link rel=StyleSheet href="./css/public.css">
<link rel=StyleSheet href="./css/table.css">
<link rel=StyleSheet href="./css/btn.css">
<link rel=StyleSheet href="./css/menuTop.css">
<link rel=StyleSheet href="./css/jquery-ui-1.8.1.custom.css">
<SCRIPT LANGUAGE='JavaScript' SRC='./script/bbs_pub.jsc'></SCRIPT>
<script type="text/javascript" src="./script/public.jsc"></script>
<script src="./script/jquery-1.4.2.min.js"></script>
 
</head>
<body >
<OBJECT id=ActiveFileMgr border=0 name=ActiveFileMgr 
codeBase="http://jupiter.kspo.or.kr:80/UFWeb/AttVolume00/ClientPrg/ActiveFileManager.cab#version=2,2,0,21" 
classid=clsid:CF48A140-65BD-439A-9BFB-527703BD9BB6 width=0 height=0><PARAM NAME="AttachSizeLimit" VALUE="10"><PARAM NAME="AttachNumberLimit" VALUE="3"><PARAM NAME="ServerAddr" VALUE="jupiter.kspo.or.kr"><PARAM NAME="ServerFolder" VALUE="/arraydata/UFWeb/AttVolume01/Bbs/20120113"><PARAM NAME="ServerBaseFolder" VALUE="/UFWeb/AttVolume01/Bbs/20120113"><PARAM NAME="ServerUser" VALUE="008033072089125088056058"><PARAM NAME="ServerPassword" VALUE="008033072112121089058099005011056"><PARAM NAME="ServerPort" VALUE="80"></OBJECT>

<div id="bbsreadcontentArea">

<form name="bbsForm" method="post" action="">
<table align=center width="780">
		
     <table width='780' border='0' cellspacing='0' cellpadding='0' valign='top'>
	   <tr><td width='780' align='center' valign='top'><table width=780 border='0' cellspacing='0' cellpadding='0'><tr>
            <td width='15' height='34'><img src='./images/tit_r01.gif' width='15' height='34'></td>
            <td width='77' align='center' valign='bottom' background='./images/tit_bg01.gif'>
            <img src='./images/06_02tit.gif' width='73' height='16' align=absmiddle></td>
            <td width='15'><img src='./images/tit_r02.gif' width='15' height='34'></td> 
            <td width='669' align='right' bgcolor='E5F2F3' class='text11_6' style='padding-top:10px'>게시판조회 &gt;  게시조회</td> 
            <td width='14'><img src='./images/tit_r03.gif' width='14' height='34'></td> </tr></table>
    <tr>
    <td class=partBar></td>
    </tr>
    </table>

  <br>
    
    <TABLE class="view" cellpadding="0" cellspacing="0" style="width:780px" border="1">
        
        <TR>
            <TD class="fB right_b" width="15%">게&nbsp;시&nbsp;자</TD>
            <TD class="fT" width="40%">
                <span class="ameba" ><%=sDocWriterName %></span>          
            </TD>
            <TD class="fB right_b" width="15%">게&nbsp;시&nbsp;일</TD>
            <TD class="fT"><%=sDocRegDate.substring(0,4) + "."+ sDocRegDate.substring(4,6)+"."+sDocRegDate.substring(6,8)%></TD>
         </TR>
        <TR>
            <TD class="fB right_b" width="15%">제&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;목</TD>
            <TD class="fT" colspan=3><%=sDocSubject%></TD>
        </TR>
        <TR>
            
            <TD class="readText" colspan=4 width=760 background=>
				
				<style type="text/css">
				
				.VBN_42585 {WORD-BREAK: break-all; font-family:굴림;font-size:9pt;line-height:normal;color:#000000;padding-left:10;padding-right:10;padding-bottom:15;padding-top:15;}
				.VBN_42585 p, .VBN_42585 td, .VBN_42585 li{font-family:굴림;font-size:9pt;color:#000000;TEXT-DECORATION:none;line-height:normal;margin-top:2;margin-bottom:2}
				.VBN_42585 font{line-height:normal;margin-top:2;margin-bottom:2}
				.VBN_97131{font-family:굴림; font-size:9pt;} 
				</style>
				
				
				<span id=VBN_23247 style="position:relative">
					<DIV class=VBN_42585 style="WIDTH: 100%;">
                        <%=sDocText %>
					</DIV>
				</span>
            </TD>
        </TR>
        <TR>
            <TD class="fB right_b" width="15%">첨&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;부</TD>
            <TD class=fT colspan=3>
                         <%if(attList.size() >0){

				             
                        	 String sAttachPath ="";
                        	 String sAttachSubject ="";
                        	 String sAttachTxt ="";
                        	 String sAttachSeq ="";                        	 
		                     String sAttachTxtImgSrc ="";                        	 
		                      for(int k = 0; k< attList.size();k++){
		                             HashMap attmap = new HashMap();
		                             
		                             attmap = (HashMap)attList.get(k);
		                             
		                             sAttachPath    = (String)attmap.get("ATT_FILEPATH");
		                             sAttachSubject = (String)attmap.get("ATT_SUBJECT");
		                             sAttachSeq     = (String)attmap.get("ATT_SEQNO");
		                             
		                             if(sAttachPath == null)sAttachPath="";
		                             
		                             sAttachTxt = sAttachPath.substring( sAttachPath.lastIndexOf(".")+1 );
		                             
		                             sAttachTxtImgSrc="att_file.gif";
		                             for( i=0; strAttExtentions[i][0].equals("etc") == false; i++ )
		                             {
		                                 if( strAttExtentions[i][0].equals(sAttachTxt) ){
		                                	 sAttachTxtImgSrc =strAttExtentions[i][1];
		                                 }
		                             }
		                             //sAttachTxtImgSrc = (String)attmap.get("ATT_IMG");
		                             System.out.println("sAttachPath="+sAttachPath);
		                             System.out.println("sAttachSubject="+sAttachSubject);
		                             
		                             if(k>=1){
		                            	
		                            	 
				          %>
				                       <br>
				          <%}
		                  %>
				       
				            <img src='./images/<%=sAttachTxtImgSrc %>' align="absmiddle">
	                           <!-- <A href="javascript:downloadAttFile('<%=attmap.get("ATT_FILEPATH") %>')"><%=attmap.get("ATT_SUBJECT") %>  -->
	                           <A href="javascript:smartFileDownload('<%=sDocYearMon%>','<%=sDocNumber%>','<%=sAttachSeq%>','BBS');">	<%=attmap.get("ATT_SUBJECT") %>
	                        </A>
	                        
				          <%
		                      }
                          }else{
			             %>
			                 &nbsp;
			             <%
			             }
			             %>

                <br>
            </TD>
        </TR>    
    </TABLE>
    <div class="rep_line"></div>
        <table cellspacing="0" class="rep" cellpadding="0">
   </table>   

  </form>
  </div>
</body>
</html>
