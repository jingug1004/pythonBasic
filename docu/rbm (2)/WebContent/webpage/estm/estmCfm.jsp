<%@ page language="java" import="java.util.*, java.sql.*,java.lang.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%@ include file="comDBManager.jsp" %>
<%
	String empNo = (String)session.getAttribute("EMP_NO");
	String empNm = (String)session.getAttribute("EMP_NM");


    String evalYear = (String)session.getAttribute("ESTM_YEAR");
    String evalNum  = (String)session.getAttribute("ESTM_NUM");
    
    String deptCd = "";
    String deptNm = "";
    
    //�����ٹ����ϰ�� ���� ���� 
    String sEstmDeptCd   = request.getParameter("estmDeptCd");  
    String sEstmDeptNm   =request.getParameter("estmDeptNm");
    
    String sExptGbnYn   =request.getParameter("exptGbnYn");
    
    if(sExptGbnYn == null) sExptGbnYn ="";
    
    if(sEstmDeptNm ==null) sEstmDeptNm="";
    sEstmDeptNm   = new String(sEstmDeptNm.getBytes("ISO8859-1"),"KSC5601");//�����ٹ����ϰ�� 

    if(sEstmDeptCd != null && !sEstmDeptCd.equals("")){
    	
        session.setAttribute("DEPT_CD",sEstmDeptCd);
        session.setAttribute("DEPT_NM",sEstmDeptNm);    	
    }
    
    
    
    if(empNm == null || empNm.equals("")){
    	 out.println("<script >location.href='estmLogin.jsp';</script>");
    }
    
    
    deptCd = (String)session.getAttribute("DEPT_CD");
    deptNm = (String)session.getAttribute("DEPT_NM");
   
%>
<%
    String sEvalYear   = evalYear;
    //String sEvalNum    = "1";
    String sEvalNum    = evalNum;
    String sEvalDept   = deptCd;
    String sEvalEmp    = empNo;


    int rowNum          =0;
    
    String sLastUpdtDtm ="";
    

%>

<% 

    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    ArrayList distrList = new ArrayList(10);    
    ArrayList wkMultList = new ArrayList(10);    
    ArrayList itmList = new ArrayList(10);
 
    
    String sEndYn ="";
    String sCfmYn ="";
    String sDisableAll = "";

    String sGrd="";
    String sDistrCd="";
    String sEstmObjNum = "";
    
    String sSRate ="";
    String sARate ="";
    String sBRate ="";
    String sCRate ="";
    String sDRate ="";
    
    
    int nRate = 0;
    
    try { 
        con = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
    } catch(SQLException e) {
         
        out.println(e.toString());
        e.printStackTrace();   
        return;
    }
    
    

     try {
    	 
         //�α�  ��� 
         String query ="";
         query ="";                
         query += "   INSERT INTO TBRF_EV_OUT_LOG   ";
         query += "     (  ";
         query += "            ESTM_YEAR   ";
         query += "           ,ESTM_NUM   ";
         query += "           ,JSP_PAGE   ";
         query += "           ,EMP_NO   ";
         
         query += "           ,REMOTE_IP   ";
         query += "           ,CONN_TIME   ";
         query += "           ,SEQ   ";
         
         query += "     ) ";
         query += "    VALUES  ";
         query += "       (  ";
         
         query += "            ?   ";
         query += "           ,?   ";
         query += "           ,?   ";
         query += "           ,?   ";
         
         query += "           ,?   ";
         query += "           ,SYSDATE   ";
         query += "           ,(SELECT NVL(MAX(SEQ),0) + 1  FROM  TBRF_EV_OUT_LOG WHERE  ESTM_YEAR = ? AND ESTM_NUM = ? AND JSP_PAGE =? )   ";
         
         
         query += "      ) ";
           
         pstmt = con.prepareStatement(query);
         
         int i =1;
         
         pstmt.setString(i++, sEvalYear);
         pstmt.setString(i++, sEvalNum);
         pstmt.setString(i++, "estmCfm.jsp");
         pstmt.setString(i++, empNo);
         
         
         pstmt.setString(i++,request.getRemoteAddr());
         
         pstmt.setString(i++, sEvalYear);
         pstmt.setString(i++, sEvalNum);
         pstmt.setString(i++, "estmCfm.jsp");       
         
         pstmt.executeUpdate();
         
         
         
         //������ ���� �ð�(���ں�)
         query ="";
         query += "       SELECT    \n";
         query += "            MAX(UPDT_DT) UPDT_DT  \n";
         query += "       FROM TBRF_EV_WK_MULT   \n";
         query += "   WHERE ESTM_YEAR = ?   ";
         query += "      AND ESTM_NUM = ?   ";
         query += "      AND ESTM_DEPT = ?   ";
         query += "      AND ESTM_EMP = ?   ";
           
         pstmt = con.prepareStatement(query);

         int k =1;
         
         pstmt.setString(k++, sEvalYear);
         pstmt.setString(k++, sEvalNum);
         pstmt.setString(k++, sEvalDept);
         pstmt.setString(k++, sEvalEmp);

         rs = pstmt.executeQuery();
         
         if(rs.next()){       
             sLastUpdtDtm = rs.getString("UPDT_DT");
         }
         
         
   	   //�ο����ǥ ��ȸ 
          query ="";
          query += "   SELECT   \n";
          query += "        ESTM_YEAR  \n";
          query += "       ,ESTM_NUM  \n";
          query += "       ,DISTR_CD  \n";
          query += "       ,ESTM_OBJ_NUM  \n";
          query += "       ,S_GRD_NUM  \n";
          query += "       ,A_GRD_NUM  \n";
          query += "       ,B_GRD_NUM  \n";
          query += "       ,C_GRD_NUM  \n";
          query += "       ,D_GRD_NUM  \n";
          query += "       ,INST_ID  \n";
          query += "       ,INST_DT  \n";
          query += "       ,UPDT_ID  \n";
          query += "       ,UPDT_DT  \n";
          query += "   FROM TBRF_EV_DISTR  \n";
          query += "   WHERE ESTM_YEAR= ?  \n";
          query += "       AND ESTM_NUM= ?  \n";
          query += "       AND ESTM_OBJ_NUM = ( SELECT MAX(COUNT(ESTM_EMP))  \n";
          query += "                               FROM TBRF_EV_WK_MULT_DT A  \n";
          query += "                               WHERE ESTM_YEAR= ?  \n";
          query += "                                   AND ESTM_NUM= ?   \n";
          query += "                                   AND ESTM_DEPT = ?  \n";
          query += "                                   AND ESTM_EMP= ?  \n";
          
          query += "                                   AND EXISTS (SELECT 1 FROM TBRF_EV_WK_MULT B   \n";
          query += "                                                   WHERE B.ESTM_YEAR = A.ESTM_YEAR \n";
          query += "                                                       AND B.ESTM_NUM = A.ESTM_NUM \n";
          query += "                                                       AND B.ESTM_DEPT = A.ESTM_DEPT \n";
          query += "                                                       AND B.ESTM_EMP = A.ESTM_EMP \n";
          query += "                                                       AND B.EMP_NO = A.EMP_NO \n";
          query += "                                                       AND (B.EXPT_YN IS NULL OR NVL(B.EXPT_YN,'') <> 'Y') ) \n";
          
          query += "                                   GROUP BY ESTM_LITEM_CD)  \n";
          

         
          pstmt = con.prepareStatement(query);

        
           i =1;
          pstmt.setString(i++, sEvalYear);
          pstmt.setString(i++, sEvalNum);
          
          
          pstmt.setString(i++, sEvalYear);
          pstmt.setString(i++, sEvalNum);
          pstmt.setString(i++, sEvalDept);
          pstmt.setString(i++, sEvalEmp);
          


          rs = pstmt.executeQuery();
          
          distrList = getResultSetToMap(rs);

          
          if(distrList.size() >0){
        	  HashMap wkmap = new HashMap();
              
              wkmap = (HashMap)distrList.get(0);
              
              sEstmObjNum = (String)wkmap.get("ESTM_OBJ_NUM");
        	  
          }

          
          //�򰡸�� ��ȸ 
           query ="";
           query += "               SELECT      ";
           query += "                      A.*      ";
           query += "                     ,(SELECT EMP_NM FROM TBRF_EV_EMP    ";
           query += "                                   WHERE ESTM_YEAR =A.ESTM_YEAR    ";
           query += "                                       AND ESTM_NUM = A.ESTM_NUM   ";
           query += "                                       AND EMP_NO = A.EMP_NO  AND WRK_GBN = '001')EMP_NM     ";
           query += "                     ,(SELECT ESTM_WK_JOB FROM TBRF_EV_EMP    ";
           query += "                                   WHERE ESTM_YEAR =A.ESTM_YEAR    ";
           query += "                                       AND ESTM_NUM = A.ESTM_NUM   ";
           query += "                                       AND EMP_NO = A.EMP_NO  AND WRK_GBN = '001') WK_JOB_CD    ";
           query += "                                          ";
           query += "                     ,(SELECT WK_JOB_NM    ";
           query += "                               FROM TBRF_EV_WORK_JOB   ";
           query += "                               WHERE ESTM_YEAR =A.ESTM_YEAR    ";
           query += "                                       AND ESTM_NUM = A.ESTM_NUM   ";
           query += "                                       AND WK_JOB_CD = (SELECT ESTM_WK_JOB FROM TBRF_EV_EMP    ";
           query += "                                           WHERE ESTM_YEAR =A.ESTM_YEAR    ";
           query += "                                               AND ESTM_NUM = A.ESTM_NUM   ";
           query += "                                               AND EMP_NO = A.EMP_NO AND WRK_GBN = '001'  ) )WK_JOB_NM   ";
           query += "                     ,D.ESTM_SCR      ";
           query += "                     ,NVL(D.ESTM_SCR,0) * (SELECT MAX(ESTM_RATE)   ";
           query += "                                                   FROM TBRF_EV_LARGE_ITEM   ";
           query += "                                                   WHERE  ESTM_YEAR = A.ESTM_YEAR      ";
           query += "                                                      AND ESTM_NUM = A.ESTM_NUM      ";
           query += "                                                      AND ESTM_LITEM_CD = A.ESTM_LITEM_CD      ";
           query += "                                                      AND ESTM_ITEM_CD ='4' ) ESTM_GRD_SCR   ";
           query += "                     ,D.ESTM_GRD  ESTM_SCR_NM      ";
           query += "                     ,DECODE(A.GRD,B.S_RATE,'S',B.A_RATE,'A',B.B_RATE,'B',B.C_RATE,'C',B.D_RATE,'D','') GRD_NM     ";
           query += "                     ,A.GRD      ";
           query += "                     ,D.EXPT_YN      ";
           query += "                     ,D.EXPT_RSN      ";
           query += "                     ,D.CFM_YN      ";
           query += "                     ,A.DISTR_CD      ";
           query += "                 FROM(      ";
           query += "                     SELECT       ";
           query += "                         *     ";
           query += "                     FROM TBRF_EV_WK_MULT_DT      ";
           query += "                     WHERE ESTM_YEAR= ?       ";
           query += "                         AND ESTM_NUM= ?       ";
           query += "                         AND ESTM_DEPT = ?        ";
           query += "                         AND ESTM_EMP= ?      ";
           query += "                  ) A     ";
           query += "                  ,(   ";
           query += "                      SELECT     ";
           query += "                           ESTM_YEAR    ";
           query += "                          ,ESTM_NUM    ";
           query += "                          ,S_RATE    ";
           query += "                          ,A_RATE    ";
           query += "                          ,B_RATE    ";
           query += "                          ,C_RATE    ";
           query += "                          ,D_RATE    ";
           query += "                     FROM TBRF_EV_ITEM_GRD    ";
           query += "                    WHERE 1 = 1    ";
           query += "                      AND ESTM_YEAR = ?    ";
           query += "                      AND ESTM_NUM = ?    ";
           query += "                      AND ESTM_ITEM_CD = '4'   ";
           query += "                  )B   ";
           query += "                  , TBRF_EV_WK_MULT D      ";
           query += "                  WHERE  1 =1   ";

           query += "                         AND A.ESTM_YEAR = D.ESTM_YEAR      ";
           query += "                         AND A.ESTM_NUM = D.ESTM_NUM      ";
           query += "                         AND A.ESTM_DEPT = D.ESTM_DEPT      ";
           query += "                         AND A.EMP_NO =D.EMP_NO      ";
           query += "                         AND A.ESTM_EMP =D.ESTM_EMP    ";

           query += "                         AND A.ESTM_YEAR = B.ESTM_YEAR       ";
           query += "                         AND A.ESTM_NUM = B.ESTM_NUM     ";
           query += "                         AND A.MULT_ESTM_GRP <> 0    ";

           query += "                 ORDER BY  A.EMP_NO ,A.ESTM_LITEM_CD   ";

           

        
         pstmt = con.prepareStatement(query);

       
         i =1;

         pstmt.setString(i++, sEvalYear);
         pstmt.setString(i++, sEvalNum);
         pstmt.setString(i++, sEvalDept);
         pstmt.setString(i++, sEvalEmp);   
         
         pstmt.setString(i++, sEvalYear);
         pstmt.setString(i++, sEvalNum);
         

         rs = pstmt.executeQuery();
         
         wkMultList = getResultSetToMap(rs);
         

         if(wkMultList.size()>0){
        	 HashMap wkmap = new HashMap();
        	 
        	 wkmap = (HashMap)wkMultList.get(0);
        	 
        	 sCfmYn = (String)wkmap.get("CFM_YN");


        	 if(sCfmYn == null || sCfmYn.equals("")){
        		 sCfmYn ="N";
        		 sEndYn = "N";
        	 }else{
        		 sEndYn = sCfmYn;
        		 
        	 }
        	 
        	 sCfmYn="N";
        	 sEndYn="N";
        	 if(sEndYn.equals("Y")){
        		 sDisableAll = "disabled";
        	 }
        	
        	 
        	 sDistrCd =(String)wkmap.get("DISTR_CD");
        	 if(sDistrCd == null) sDistrCd ="";

         }
        		 
         
     

         
        //���׸�  ��ȸ 
        query ="";
		query += "       SELECT    \n";
		query += "             A.ESTM_LITEM_CD   \n";
		query += "            ,B.ESTM_LITEM_NM   \n";
		query += "            ,MAX(B.ESTM_RATE) ESTM_RATE  \n";
		query += "       FROM TBRF_EV_WK_MULT_DT A,   \n";
		query += "           TBRF_EV_LARGE_ITEM B   \n";
		query += "       WHERE 1 =1   \n";
		query += "           AND A.ESTM_YEAR = B.ESTM_YEAR   \n";
		query += "           AND A.ESTM_NUM = B.ESTM_NUM   \n";
		query += "           AND A.ESTM_LITEM_CD = B.ESTM_LITEM_CD   \n";
		query += "           AND B.ESTM_ITEM_CD ='4'  \n ";
		query += "           AND A.ESTM_YEAR= ?   \n";
		query += "           AND A.ESTM_NUM= ?   \n";
		query += "           AND A.ESTM_DEPT = ?   \n";
		query += "           AND A.ESTM_EMP= ?  \n";
		query += "       GROUP BY A.ESTM_LITEM_CD,B.ESTM_LITEM_NM   \n";
		query += "       ORDER BY A.ESTM_LITEM_CD   \n";
       
      
      
       pstmt = con.prepareStatement(query);

     
       i =1;

       pstmt.setString(i++, sEvalYear);
       pstmt.setString(i++, sEvalNum);
       pstmt.setString(i++, sEvalDept);
       pstmt.setString(i++, sEvalEmp);        
       

       rs = pstmt.executeQuery();
       
       itmList = getResultSetToMap(rs);
       
       
       
       
       
       //�������  ��ȸ 
        query ="";
		query += "       SELECT     ";
		query += "               ESTM_YEAR    ";
		query += "              ,ESTM_NUM    ";
		query += "              ,S_RATE    ";
		query += "              ,A_RATE    ";
		query += "              ,B_RATE    ";
		query += "              ,C_RATE    ";
		query += "              ,D_RATE    ";
		query += "         FROM TBRF_EV_ITEM_GRD    ";
		query += "        WHERE 1 = 1    ";
		query += "          AND ESTM_YEAR = ?   ";
		query += "          AND ESTM_NUM = ?    ";
		query += "          AND ESTM_ITEM_CD = '4'    ";
      
     
	    pstmt = con.prepareStatement(query);

    
       i =1;

       pstmt.setString(i++, sEvalYear);
       pstmt.setString(i++, sEvalNum);   
      

       rs = pstmt.executeQuery();
      
       if(rs.next()){
    	  sSRate = rs.getString("S_RATE");
    	  sARate = rs.getString("A_RATE");
    	  sBRate = rs.getString("B_RATE");
    	  sCRate = rs.getString("C_RATE");
    	  sDRate = rs.getString("D_RATE");    	   
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
     
     
     int itemSize = itmList.size();
     String sRate ="";

     if(itemSize > 0){
    	 sRate = (String)((HashMap)itmList.get(0)).get("ESTM_RATE");
    	 if(sRate == null || sRate.equals("")) sRate ="1";
    	 
     }
     
     

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title> ������ �� �Ͽ������ �򰡽ý��� </title>
<meta http-equiv="Content-Type" content="text/html; charset=eu-kr" />
<link rel="stylesheet" href="css/default.css" type="text/css" />

<![If IE 7]>
<link rel="stylesheet" href="css/ie7.css" type="text/css" />
<![endif]>
<script src="zoom.js" type="text/javascript"></script>
<script language="JavaScript" type="text/JavaScript">
     var sRate= "<%=sSRate%>" - 0;
     var aRate= "<%=sARate%>" - 0;
     var bRate= "<%=sBRate%>" - 0;
     var cRate= "<%=sCRate%>" - 0;
     var dRate= "<%=sDRate%>" - 0;
            
    function saveCfm(sCfmFlag){
	    
	    // ���׸� �ʼ��Է� üũ 	    
	    var rowNumObj = document.getElementById("rowNum");

        var rowNum= (rowNumObj.value-0) +1;
        var itmObj = document.getElementsByName("itemCd");
        var itmGrdObj;
        
        var grdScrObj;
        var totalScrObj;
        var grdObj;
        var exptYnObj;
        var exptRsnObj;
        
        var s_cnt = 0;
        var a_cnt = 0;
        var b_cnt = 0;
        var c_cnt = 0;
        var d_cnt = 0;
        
        
        var sSxprYn ="";
        for(var i =1; i < rowNum; i++){
 
            sSxprYn = (document.getElementById(i+"_exptYn")).checked;
            
            if(!sSxprYn){  //���� ����� �ƴҰ�츸 üũ 
	            for(var j =0; j <itmObj.length; j++){
	                        
	                itmGrdObj = document.getElementById(i+"_itm_"+ itmObj[j].value+"_value");
	                
	                if(itmGrdObj.value == null || itmGrdObj.value == "" || itmGrdObj.value == "undefined"){
	                    alert(i + "�࿡ ������ ���׸��� �ֽ��ϴ�.");
	                    return;
	                }
	            }
	            
	            
	            grdScrObj = document.getElementById(i+"_grdScr");
	            totalScrObj = document.getElementById(i+"_TotalScr");
	            grdObj = document.getElementById(i+"_estmScrNm");
	           
	            
	            
	            
	            if(grdScrObj.value == "" || grdScrObj < "0"){
	                alert("���� ������ �����Ǿ����ϴ�.");
	                grdScrObj.focus();
	                return;
	            }
	            
	
	            
	            if(grdObj.value == "" || grdObj < "0"){
	                alert("�򰡵�ް�   �����Ǿ����ϴ�.");
	                grdObj.focus();
	                return;
	            }
	            
	            
	            
	            
	            if(totalScrObj.value == "" || totalScrObj < "0"){
	                alert("ȯ��������  �����Ǿ����ϴ�.");
	                totalScrObj.focus();
	                return;
	            }
	      
	             if(grdObj.value =="S"){
	                 s_cnt++;
	             }else if(grdObj.value =="A"){
	                 a_cnt++;
	             }else if(grdObj.value =="B"){
	                 b_cnt++;
	             }else if(grdObj.value =="C"){
	                 c_cnt++;
	             }else if(grdObj.value =="D"){
	                 d_cnt++;
	             }
	            
	             
            }else{
                
                if(sCfmFlag =="Y"){
                  
                     exptRsn = (document.getElementById(i+"_exptRSN")).value;
                     
                    if(exptRsn == null || exptRsn =="" || exptRsn =="undefined"){
                        alert("������ ����� ���� ���� �� Ȯ�� �ٶ��ϴ�.");           
                        return;
                    }
                
                }
            
            }
        }   
     
        var aGrdArr = getDistrValue();
        
        
        if(aGrdArr[0] != s_cnt ){
            alert("S ��� �� �ο����ǥ��  �ο������� �������� �ʽ��ϴ�. ");
            return;
        }else  if(aGrdArr[1] != a_cnt ){
            alert("A ��� �� �ο����ǥ��  �ο������� �������� �ʽ��ϴ�. ");
            return;
        
        }else  if(aGrdArr[2] != b_cnt ){
            alert("B ��� �� �ο����ǥ��  �ο������� �������� �ʽ��ϴ�. ");
            return;
        }else  if(aGrdArr[3] != c_cnt ){
            alert("C ��� �� �ο����ǥ��  �ο������� �������� �ʽ��ϴ�. ");
            return;
        
        }else  if(aGrdArr[4] != d_cnt ){
           alert("D ��� �� �ο����ǥ��  �ο������� �������� �ʽ��ϴ�. ");
           return;
        }
        
        
        
     /*
        var aGrdArr = getDistrValue(); //������ �ο����ǥ ��޺� �ο�
       
       //������ üũ 
        if(aEqualGrd != null){
	        for(var i = 1; i<aEqualGrd.length;i++){
	            grdObj = document.getElementById(i+"_estmScrNm");
	            if(aEqualGrd[i] > 1){
	
	                if(grdObj.value == "S"){
	                    if(aEqualGrd[i] >aGrdArr[0]){
	                          alert("S ����� �򰡵�� ����  �ο����ǥ�� �ο����� �ʰ��Ͽ����ϴ�. ");
	                          return;
	                    }
			        }else  if(grdObj.value == "A" ){
	                    if(aEqualGrd[i] >aGrdArr[1]){
	                          alert("A ����� �򰡵�� ����  �ο����ǥ�� �ο����� �ʰ��Ͽ����ϴ�. ");
	                          return;
	                    }
			        
			        }else  if(grdObj.value == "B" ){
	
			            if(aEqualGrd[i] >aGrdArr[2]){
	                          alert("B ����� �򰡵�� ����  �ο����ǥ�� �ο����� �ʰ��Ͽ����ϴ�. ");
	                          return;
	                    }
			            
			        }else  if(grdObj.value == "C"){
	
	                    if(aEqualGrd[i] >aGrdArr[3]){
	                     
	                          alert("C ����� �򰡵�� ����  �ο����ǥ�� �ο����� �ʰ��Ͽ����ϴ�. ");
	                          return;
	                    }
			        
			        }else  if(grdObj.value == "D" ){
	                    if(aEqualGrd[i] >aGrdArr[4]){
	                     
	                          alert("D ����� �򰡵�� ����  �ο����ǥ�� �ο����� �ʰ��Ͽ����ϴ�. ");
	                          return;
	                    }
			        }
	            
	            
	            }
	        }
       
        }
      */
     
       estmForm.target = "HiddenFrame";
      
       estmForm.action="estmCfmTrans.jsp?cfmYn="+sCfmFlag+"&distrCd="+aGrdArr[5];
       estmForm.submit();
        
        
     }
    
    function fcItmOnClick(obj){

            
            var nItmSize="<%=itemSize%>";
            var sGrdScr = "";   // �������
            var sGrdScrNm = ""; // ��޸�
            var sGrdRateScr = ""; // ȯ������
            
	        var tmpStrArr = (obj.id).split("_"); 
	        
	        valueObj = document.getElementById(obj.id+"_value");
	
	        //���� ����
	        valueObj.value = obj.value;
        


           //���� ���� 
           var rowNum= tmpStrArr[0]-0;
         
           var itmObj = document.getElementsByName("itemCd");
            
           var itmGrdObj;
           var totalObj = document.getElementById(rowNum+"_TotalScr");
           var scrObj = document.getElementById(rowNum+"_grdScr");           //ȯ������
           var estmScrNmObj = document.getElementById(rowNum+"_estmScrNm");  //�򰡵��
           var avgObj = document.getElementById(rowNum+"_avg");  //�򰡵��
        
           var totalScr=0;
           
  
           for(var j =0; j <itmObj.length; j++){
                          
                itmGrdObj = document.getElementById(rowNum+"_itm_"+ itmObj[j].value+"_value");
                itmScrObj = document.getElementById(rowNum+"_itm_"+ itmObj[j].value+"_scr");
                
                if(itmGrdObj.value =="S"){
                    totalScr += sRate;
                    
                    itmScrObj.value = sRate;
                }else if(itmGrdObj.value =="A"){
                    totalScr += aRate;
                    
                    itmScrObj.value = aRate;
                }else if(itmGrdObj.value =="B"){
                    totalScr += bRate;
                    
                    itmScrObj.value = bRate;
                }else if(itmGrdObj.value =="C"){
                    totalScr += cRate;
                    
                    itmScrObj.value = cRate;
                }else if(itmGrdObj.value =="D"){
                    totalScr += dRate;
                    
                    itmScrObj.value = dRate;
                }
                
           }
    
          //��������
          totalObj.value =totalScr;
           
          //��ռ���
          avgObj.value = Math.round(totalScr/nItmSize);

          getRankArray();
      
    }
    
   
   
    function getDistrValue(){
        var tmpArr = new Array();

        var distrObj = document.getElementsByName("distr_cd");

        for(var i=0; i <distrObj.length;i++){
       
       
 
            if(distrObj[i].checked == true){
              tmpArr[0] = document.getElementById("distr_s_num_"+distrObj[i].value).value; 
              tmpArr[1] = document.getElementById("distr_a_num_"+distrObj[i].value).value;
              tmpArr[2] = document.getElementById("distr_b_num_"+distrObj[i].value).value;
              tmpArr[3] = document.getElementById("distr_c_num_"+distrObj[i].value).value;
              tmpArr[4] = document.getElementById("distr_d_num_"+distrObj[i].value).value;
              tmpArr[5] = distrObj[i].value;
            }else{
            }
        }

        return tmpArr;
    }
    
     //�ο����ǥ ��޹迭 return
      function getDistrScrArray(){
        var tmpArr = new Array();
       
        var distrObj = document.getElementsByName("distr_cd");
        var objCnt =0;
        
        var aObjDistrId =['distr_s_num','distr_a_num','distr_b_num','distr_c_num','distr_d_num'];
        var aDistrCd =['S','A','B','C','D'];

        for(var i=0; i <distrObj.length;i++){
       
       
           
            if(distrObj[i].checked == true){
            
              
                 var nCnt = 0;          
                 for(var j = 0; j<aObjDistrId.length;j++){
                
                    nCnt = (document.getElementById(aObjDistrId[j]+"_"+distrObj[i].value).value -0);
                    
                     for(var k = 0 ; k< nCnt; k++){
                         tmpArr[objCnt++] = aDistrCd[j];
                     }
   
                 }               
            }else{
            
            }
        }

        return tmpArr;
    }
    
    var aEqualGrd;
    function getRankArray(){
        /*
        var rowNumObj = document.getElementById("rowNum");
        var rowNum= (rowNumObj.value-0) +1;
        var nRtnRank = (rowNumObj.value-0);
        
        var totalObj;
        var totalValue;
        
        var tmpTotalObj;
        var tmpTotalValue;
        
        var aRtn;
        var aRtnSeq =0;
        
        for(var i = 0; i < rowNum ; i++){
           totalObj = document.getElementById(i+"_TotalScr");
           totalValue = (totalObj.value -0);

        
        }

        */
        aEqualGrd = new Array();
 
        var rowNumObj = document.getElementById("rowNum");
        var rowNum= (rowNumObj.value-0) +1;
        var nRtnRank = (rowNumObj.value-0);
        
        var totalObj;
        var totalValue;
        
        var equalObj;
        var equalValue;

        var aRank = new Array();

        var aDistrOrder =  new Array();
        aDistrOrder = getDistrScrArray();
   
        var nEqualCnt =0;
        for(var i = 1; i < rowNum ; i++){
            totalObj = document.getElementById(i+"_TotalScr");
            if(totalObj.value == null || totalObj.value =="") totalObj.value ="0";
            totalValue = (totalObj.value -0);

            aRank[i] = totalValue +"_"+i;
            
            nEqualCnt = 0;
            aEqualGrd[i] =nEqualCnt;
           
            for(var j=1;j <rowNum;j++){
                equalObj = document.getElementById(j+"_TotalScr");
	            if(equalObj.value == null || equalObj.value =="") equalObj.value ="0";
	            equalValue = (equalObj.value -0);
	           
	            if(totalValue == equalValue){

	               aEqualGrd[i] = ++nEqualCnt;

	            }
                
            }
            
        
        }

        
        for(var a=0;a<aRank.length;a++){
            for(var b=1;b <aRank.length;b++){
                if(aRank[b-1] >aRank[b]){
                    var temp;

                    temp = aRank[b-1];
                    aRank[b-1] = aRank[b];
                    aRank[b] = temp;

                }
            }
        }


        var sRank ="";

        var aTmp = "";
        
        var nTmpRow = 0;
        var nDTmpRow =0;
  
        var rnkSize =  aRank.length;
        //var rnkSize =("<%=sEstmObjNum%>" -0);

        var sGrdScr ="0";
        var sGrdRateScr="0";
        
        var scrObj ;
        var nRate = ("<%=sRate %>" - 0) / 100;
        var estmExpObj ;
        for( var i=1; i< rnkSize; i++){ // 

                sTmp = aRank[i].split("_");

                nTmpRow = (sTmp[1]-0);
                nDTmpRow =rnkSize- i -1 ;
                //nTmpRow = rnkSize -(sTmp[1]-0);

                estmExpObj = (document.getElementById(nTmpRow+"_exptYn"));
            
                if(estmExpObj.checked == false){
	                (document.getElementById(nTmpRow+"_estmScrNm")).value = aDistrOrder[nDTmpRow];
	               
	                //�򰡵�� ���� ����
	        
	                sGrdScr= getGrdByRate(aDistrOrder[nDTmpRow]);
	               
	          
		           //ȯ������ ���� 
		           
		           sGrdRateScr = sGrdScr *  nRate;
		
		           scrObj = document.getElementById(nTmpRow+"_grdScr");           //ȯ������
		         
		           scrObj.value = sGrdRateScr;
		         }
        }



        var rowNumObj = document.getElementById("rowNum");
		var rowNum= (rowNumObj.value-0) +1;
		var objFAvg ;
		var objFGrdScr ;
		var objFScrNm ;
		
		var objTAvg ;
        var objTGrdScr ;
		var objTScrNm ;
		            
		for(var i =1; i < rowNum; i++){
		   objFAvg = (document.getElementById(i+"_avg"));
		   objFGrdScr = (document.getElementById(i+"_grdScr"));
		   objFScrNm = (document.getElementById(i+"_estmScrNm"));
		  for(var j =1; j < rowNum; j++){
		     
		         objTAvg = (document.getElementById(j+"_avg"));
                 objTGrdScr = (document.getElementById(j+"_grdScr"));
                 objTScrNm = (document.getElementById(j+"_estmScrNm"));
                 
                estmExpObj = (document.getElementById(j+"_exptYn"));
            
              
		      
		         if(i != j && objFAvg.value == objTAvg.value && objFGrdScr.value != objTGrdScr.value){
		              if(estmExpObj.checked == false){
			              if( (objFGrdScr.value-0) > (objTGrdScr.value-0)){
			                  objTScrNm.value= objFScrNm.value;
			                  objTGrdScr.value = objFGrdScr.value;
			              }
		         
		               }
		         }
		  }
		}
		 





        return sRank;
    }
    
    function getGrdByRateNm(nRate){
        
        var sGrdNm="";
        if(aRate < nRate && nRate<=sRate){
   
            sGrdNm = "S";
        }else if(bRate < nRate && nRate <=aRate){
            sGrdNm = "A";
        }else if(cRate < nRate && nRate <=bRate){
            sGrdNm = "B";
        }else if(dRate < nRate && nRate <=cRate){
            sGrdNm = "C";
        }else if(nRate = dRate){
        
            sGrdNm = "D";
        }

        return sGrdNm;
    
    }
    
     function getGrdByRate(nRateNm){
        var sGrdScr = 0;
        if(nRateNm =="S"){
            sGrdScr = sRate;
        }else if(nRateNm =="A"){
            sGrdScr = aRate;
        }else if(nRateNm =="B"){
            sGrdScr = bRate;
        }else if(nRateNm =="C"){
            sGrdScr = cRate;
        }else if(nRateNm =="D"){
        
            sGrdScr = dRate;
        }

        return sGrdScr;
    
    }
    
    
    function fcChkOnClick(obj){

         var tmpStrArr = (obj.id).split("_"); 
         var valueObj = document.getElementById(tmpStrArr[0]+"_empNo");
         
         var estmScrNmObj = document.getElementById(tmpStrArr[0]+"_empNo");
         var estmScrNmObj = document.getElementById(tmpStrArr[0]+"_empNo");



         var aExptYn = document.getElementsByName("exptYn");
         
         var chkCnt = 0;
         for(var i =0; i < aExptYn.length; i++){
            if(aExptYn[i].checked){
                chkCnt++;
            }
         }

         if(chkCnt>1){
            alert("���ܴ�� ���� �ο�[1] �� �ʰ� �Ͽ����ϴ�.");
            obj.checked = false;
            return;
         }        


         estmForm.exptEmpNo.value = valueObj.value;
         
         if(obj.checked){
              estmForm.exptEmpYn.value = "Y";
         }else{
              estmForm.exptEmpYn.value = "N";
         
         }



         estmForm.target = "HiddenFrame";
         estmForm.action="estmExptTrans.jsp";
         estmForm.submit();
        
    }
    
    
    function fcExptPopup(nRow){
     
        var sEmpNo =(document.getElementById(nRow+"_empNo")).value;
    
        var w   = "500";
	    var h   = "200";
	    var url = "estmExptPopup.jsp?rowNum="+nRow+"&exptEmpNo="+sEmpNo;
	    var newWin;
	    var splashWin = window.open(url, newWin, 'resizable=1,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,top=200,left=300,width=' + w + ',height=' + h  );
	    splashWin.focus();
	    
    }
    
    
    function fcDistrOnclick(){
      getRankArray();
    }
    
    function showEmpImg(sEmpNo,sEmpNm){
        var imgPath="http://ifis.kspo.or.kr:8090/snis/files/photo/hum/id/jong_persl/"+sEmpNo+".jpg";
        var imgObj = document.getElementById("empImg");
        var empNmObj = document.getElementById("txtImgEmpNm");

        imgObj.style.display = "block";
        imgObj.src =imgPath;
        
        empNmObj.style.display = "block";
        
        empNmObj.value = sEmpNm;
        

    }
    
    
     function hideEmpImg(){
        var imgObj = document.getElementById("empImg");
        var empNmObj = document.getElementById("txtImgEmpNm");

        imgObj.style.display = "none";
        
        imgObj.src ="";
        
        
        empNmObj.value = "";
        empNmObj.style.display = "none";
        
    }
    

    function formInit(){
       var sExptGbnYn = "<%=sExptGbnYn%>";
        
       if(sExptGbnYn =="Y"){
         getRankArray();
       }
       
       
    }
</script>
</head>
<body onLoad="formInit()">
<form name="estmForm" method="post" action="">
    <input type="hidden" name="endYn" id="endYn" value="<%=sEndYn %>" >
    <input type="hidden" name="estmRate" id="estmRate" value="<%=sRate %>" >
    <input type="hidden" name="lastUpdtDtm" id="lastUpdtDtm" value="<%=sLastUpdtDtm %>" >
    
    
    <input type="hidden" name="exptEmpNo" id="exptEmpNo">
    <input type="hidden" name="exptEmpYn" id="exptEmpYn">
    
    
	<div class="wrap">
		<div class="logo">
		    <img src="img/logo.jpg" alt="KSPO ���ֻ������/������ �� �Ͽ����� �򰡽ý���" />
		    <p><a href="javscript:void(0)" onclick="zoomOut(); return false;" onfocus="blur()">
            <img src="img/btn_m.gif" alt="���"/></a> 
            <a href="javscript:void(0)" onclick="zoomIn(); return false;" onfocus="blur()"><img src="img/btn_p.gif" alt="Ȯ��"/></a> 
            <a href="passChange.jsp"><img src="img/btn_pass.jpg" alt="��й�ȣ ����" /></a> <a href="estmLogin.jsp">
            <img src="img/btn_logout.jpg" alt="�α׾ƿ�" /></a></p>
		</div>
		<div class="body">
			<div class="grade_page">
				<h1><img class="grade_page_img" src="img/tit_result.jpg" alt="�� ���� �� ��� ��� Ȯ��" /></h1>
				<div class="grade">
					<p><strong><u>�򰡴� ��к����� ��ô�Դϴ�. �������� ħ���ϴ� ���� ���� �� �ٹ����� �� �λ� � �ݿ��� ��ȹ�Դϴ�.</u></strong></p>
					<p>�� �׸񸶴� ��޿� ������� �����򰡸� �ϵ�, ����ȭ ������ ���� <strong>������ �ٸ���</strong> ���ϼ���.</p>
					<div class="gray_grade">
					        <img class="lt" src="img/box_grade_lt.jpg" alt=""/>
		                    <img class="rt" src="img/box_grade_rt.jpg" alt=""/>
		                    <img class="lb" src="img/box_grade_lb.jpg" alt=""/>
		                    <img class="rb" src="img/box_grade_rb.jpg" alt=""/>
						<ol>
					
							<li>1. �򰡴������ �����ڰ� �ִ°�� ���� �����մϴ�.</li>
                            <li>    &nbsp;&nbsp;&nbsp;(���������� ģ�� �� �������� �򰡰� �Ұ��� ��� 1�� ���� ������ �� �ֽ��ϴ�.)</li>
                            <li>2. �ο� ���ǥ�� �����մϴ�.</li>
                            <li>3. ����� ���׸� �ش��ϴ� ����� �����մϴ�.</li>
                            <li>4. �ο����ǥ�� ��޺� �ο����� ��ġ���� �ʴ� ��� ���׸� ����� �����Ͽ� ������ �ٸ��� �մϴ�.</li>
                            <li>5. �򰡸� �Ϸ��� �� �ϴ��� Ȯ�� ��ư�� �ݵ��  Ŭ���ؾ� �մϴ�.</li>
                   
						</ol>
						<span style=" padding-left:10px">
						<strong>�Ҽ�</strong> : <%=deptNm %> 
						<strong>���</strong> : <%=empNo %>&nbsp;
						<strong>����</strong> : <%=empNm %>
						</span>
						<table>
							<thead>
								<tr><th colspan="6" valign="center">�ο����ǥ</th></tr>
							</thead>
							<tbody>
								<tr valign="middle">
								    <td>����</td>
								    <td>S</td>
								    <td>A</td>
								    <td>B</td>
								    <td>C</td>
								    <td>D</td>
								 </tr>
			                 <%
				                 for(int k = 0; k< distrList.size();k++){
				                     HashMap map = new HashMap();
				                     
				                     map = (HashMap)distrList.get(k);
				                     
				                     String  tmpDistr = (String)map.get("DISTR_CD");
				                     if(tmpDistr==null) tmpDistr ="";
				             %>
				             
				                 <tr valign="middle">
                                    <td><input type="radio"    <%=sDisableAll %>  id ="distr_cd" name ="distr_cd"  value="<%=map.get("DISTR_CD")%>" <%=( (k==0)  )?"checked":"" %> <%=sDistrCd.equals(tmpDistr)?"checked":"" %> onclick="fcDistrOnclick()"></td>
                                    <td><input type="hidden"   id ="distr_s_num_<%=map.get("DISTR_CD")%>" name ="distr_s_num_<%=map.get("DISTR_CD")%>"  value="<%=map.get("S_GRD_NUM")%>"><%=map.get("S_GRD_NUM") %></td>
                                    <td><input type="hidden"   id ="distr_a_num_<%=map.get("DISTR_CD")%>" name ="distr_a_num_<%=map.get("DISTR_CD")%>"  value="<%=map.get("A_GRD_NUM")%>"><%=map.get("A_GRD_NUM") %></td>
                                    <td><input type="hidden"   id ="distr_b_num_<%=map.get("DISTR_CD")%>" name ="distr_b_num_<%=map.get("DISTR_CD")%>"  value="<%=map.get("B_GRD_NUM")%>"><%=map.get("B_GRD_NUM") %></td>
                                    <td><input type="hidden"   id ="distr_c_num_<%=map.get("DISTR_CD")%>" name ="distr_c_num_<%=map.get("DISTR_CD")%>"  value="<%=map.get("C_GRD_NUM")%>"><%=map.get("C_GRD_NUM") %></td>
                                    <td><input type="hidden"   id ="distr_d_num_<%=map.get("DISTR_CD")%>" name ="distr_d_num_<%=map.get("DISTR_CD")%>"  value="<%=map.get("D_GRD_NUM")%>"><%=map.get("D_GRD_NUM") %></td>
                                </tr>
				             <%        
				                 
				                     
				                 }
			                 %>

							</tbody>
						</table>
						<span style="clear:both; display:block;"></span>
					</div><!-- //gray_grade-->
					<img id="empImg" style="position:absolute; width:90px;height:90px;left:55%;top:22%;display:none" src="" >
					<input type="text" id="txtImgEmpNm" style="position:absolute;width:36px; font-size:10px; font-weight:bold; left:55%;top:22%;display:none;" >
					<p class="notice" style="text-align:right;">* ������ �򰡿� �ݿ����� �ʽ��ϴ�. <strong>ȯ������</strong>�� �ݿ��˴ϴ�.
					</p>
					<div class="_table">
					<table  width="<%=(365+(itmList.size()*140)) %>px">
						<thead>
							<tr>
								<th style="width:30px;">����</th>
								<th style="width:57px;">����</th>
								<th style="width:56px;">���</th>
								<th style="width:79px;">����</th>
								

						 <%
                               for(int j = 0; j< itmList.size();j++){
                                   HashMap itmmap = new HashMap();
                                   
                                   itmmap = (HashMap)itmList.get(j);
                          %>		<th style="width:140px;"><%=itmmap.get("ESTM_LITEM_NM")%>
                                      <input type="hidden" name="itemCd" value="<%=itmmap.get("ESTM_LITEM_CD") %>" />
                                    </th>
						
						 <% 
						   }
						 %>	
								
								<th style="width:34px;">���� </th>
								<th style="width:30px;">���</th>
								<th style="width:29px;">����</th>
								<th colspan="2" style="width:50px;">����</th>
								
							</tr>
						</thead>
						<tbody>
						
			      <%
			           String oldEmpNo = "";
			           rowNum = 0;
			           int tmpItemRow =0;    //������ ��ȣ (����ٲ𶧸��� �ʱ�ȭ)
			           int totalItmScr = 0;
			           String sWkGrd = "";  //�׸񺰵��
			          
			           String sGrdScr="";
			           String sEstmScr="";       //�򰡵��ȯ������
			           String sEstmGrdScr="";    
			           String sEstmScrNm ="";   //�򰡵��
			           
			           String sExptYn ="";       //���ܿ���			           
			           String sDisable ="";       //�������ɿ���
			           
			           String sExptRSN ="";      //���ܻ���
			           
			           int nAvg =0;      //���
			           
			           
                       for(int k = 0; k< wkMultList.size();k++){
                           HashMap wkmap = new HashMap();
                           
                           wkmap = (HashMap)wkMultList.get(k);
                           
                           //�׸񺰵��
                           sWkGrd= (String)wkmap.get("GRD_NM");
                           if(sWkGrd ==null) sWkGrd ="";
                           
                           //�׸񺰵��
                           sGrdScr= (String)wkmap.get("GRD");
                           if(sGrdScr ==null) sGrdScr ="0";
                           
                           
                           //�򰡵��ȯ������
                           sEstmScr= (String)wkmap.get("ESTM_SCR");
                           if(sEstmScr ==null) sEstmScr ="0";
                           
                        
                           sEstmGrdScr= (String)wkmap.get("ESTM_GRD_SCR");
                           if(sEstmGrdScr ==null) sEstmGrdScr ="0";
                         
                           //�򰡵��
                           sEstmScrNm = (String)wkmap.get("ESTM_SCR_NM");
                           if(sEstmScrNm == null) sEstmScrNm = "";
                           
                           
                           //���ܿ���
                           sExptYn  = (String)wkmap.get("EXPT_YN");
                           if(sExptYn == null || sExptYn.equals("")) sExptYn = "N";
                         
                           //���ܴ���ϰ�� disabled
                           if(sExptYn.equals("Y")){
                        	   sDisable = "disabled"; 
                        	   sWkGrd ="";
                        	   sGrdScr ="0";
                        	   sEstmScr ="0";
                        	   sEstmGrdScr ="0";
                        	   sEstmScrNm ="";
                        	   
                           }else{
                        	   sDisable ="";
                           }
                           
                           //���ܻ���
                           sExptRSN = (String)wkmap.get("EXPT_RSN");
                           if(sExptRSN == null) sExptRSN = "";
                           
                           
                           totalItmScr += Integer.parseInt(sGrdScr);
                  %>

						  <% 				      
						  if( k ==0 || !((String)wkmap.get("EMP_NO")).equals(oldEmpNo)){
							    rowNum++;
							    tmpItemRow = 0;
						
							    
						  %>
						      <tr style="height:21px">
								<td><%=rowNum%></td>
								<td onmouseover="javascript:showEmpImg('<%=wkmap.get("EMP_NO")%>','<%=wkmap.get("EMP_NM")%>')" onmouseout="javascript:hideEmpImg()" ><input type="hidden" name="empNm" id="<%=rowNum%>_empNm" value ="<%=wkmap.get("EMP_NM")%>"><%=wkmap.get("EMP_NM")%>
								    <input type="hidden" name="multEstmGrp" id="<%=rowNum%>_multEstmGrp" value ="<%=wkmap.get("MULT_ESTM_GRP")%>"></td>
								<td><input type="hidden" name="empNo" id="<%=rowNum%>_empNo" value ="<%=wkmap.get("EMP_NO")%>"><%=wkmap.get("EMP_NO")%></td>
								<td><input type="hidden" name="wkJboNm" id="<%=rowNum%>_wkJboNm" value ="<%=wkmap.get("WK_JOB_NM")%>"><%=wkmap.get("WK_JOB_NM")%></td>
								
		                 <%
						  }
						  
						  tmpItemRow++;
		                 %>

							
                   
                                <td>S<input type="radio" 
                                            <%=sDisableAll %> name="<%=rowNum%>_itm_<%=wkmap.get("ESTM_LITEM_CD")%>" id="<%=rowNum%>_itm_<%=wkmap.get("ESTM_LITEM_CD")%>" value="S" onclick="fcItmOnClick(this)" <%=(sGrdScr.equals(sSRate))?"checked":"" %> <%=sDisable %> >A<input type="radio" 
                                            <%=sDisableAll %> name="<%=rowNum%>_itm_<%=wkmap.get("ESTM_LITEM_CD")%>" id="<%=rowNum%>_itm_<%=wkmap.get("ESTM_LITEM_CD")%>" value="A" onclick="fcItmOnClick(this)" <%=(sGrdScr.equals(sARate))?"checked":"" %> <%=sDisable %> >B<input type="radio" 
                                            <%=sDisableAll %> name="<%=rowNum%>_itm_<%=wkmap.get("ESTM_LITEM_CD")%>" id="<%=rowNum%>_itm_<%=wkmap.get("ESTM_LITEM_CD")%>" value="B" onclick="fcItmOnClick(this)" <%=(sGrdScr.equals(sBRate))?"checked":"" %> <%=sDisable %> >C<input type="radio" 
                                            <%=sDisableAll %> name="<%=rowNum%>_itm_<%=wkmap.get("ESTM_LITEM_CD")%>" id="<%=rowNum%>_itm_<%=wkmap.get("ESTM_LITEM_CD")%>" value="C" onclick="fcItmOnClick(this)" <%=(sGrdScr.equals(sCRate))?"checked":"" %> <%=sDisable %> >D<input type="radio" 
                                            <%=sDisableAll %> name="<%=rowNum%>_itm_<%=wkmap.get("ESTM_LITEM_CD")%>" id="<%=rowNum%>_itm_<%=wkmap.get("ESTM_LITEM_CD")%>" value="D" onclick="fcItmOnClick(this)" <%=(sGrdScr.equals(sDRate))?"checked":"" %> <%=sDisable %> >
                                            <input type="hidden" id="<%=rowNum%>_itemValue" name="itemValue">
                                            <input type="hidden" id="<%=rowNum%>_itm_<%=wkmap.get("ESTM_LITEM_CD")%>_value" value="<%=sWkGrd %>" >
                                            <input type="hidden" id="<%=rowNum%>_itm_<%=wkmap.get("ESTM_LITEM_CD")%>_scr"  value="<%=sGrdScr %>" name="<%=rowNum%>_itm_<%=wkmap.get("ESTM_LITEM_CD")%>_scr">
                                 </td>
                        
	                        
	                       
		                  <%                     
		                 
		                  if( tmpItemRow == itemSize){
		                	  
		                	  nAvg = Math.round(totalItmScr/itemSize);
		                	  
		                	  
		                  %>								
		                  
								<td><input class="txtbox" type="hidden" <%=sDisableAll %> id="<%=rowNum%>_TotalScr"  name="TotalScr" readonly value="<%=totalItmScr %>">
								<input class="txtbox" type="text" <%=sDisableAll %> id="<%=rowNum%>_avg"  name="avg" readonly value="<%=nAvg %>"></td>
								<td><input type="text"  name = "estmScrNm" id="<%=rowNum%>_estmScrNm"  value="<%=sEstmScrNm%>" readonly></td>
								<td><input type="text"  align="right" name = "grdScr" id="<%=rowNum%>_grdScr"  value="<%=sEstmScr%>" readonly>
								<td style="width:20px"><input type="checkbox" <%=sDisableAll %> name="exptYn" id="<%=rowNum%>_exptYn" value ="Y" <%=sExptYn.equals("Y")?"checked":"" %> onclick="fcChkOnClick(this)" ></td> 
								<td  style="width:30px"><input type="hidden"   align="right" name = "exptRsn" id="<%=rowNum%>_exptRSN"  value="<%=sExptRSN%>" readonly>
								<% if(sExptYn.equals("Y")){ %><a href="javascript:fcExptPopup(<%=rowNum%>)"><img src="img/btn_reason.gif" alt="����"></a><%}else{ %> &nbsp; <%} %></td>
							  </tr>
						<%
						     totalItmScr = 0;
							}
						%>			
								
						
		             <% 
		                   oldEmpNo = (String)wkmap.get("EMP_NO");
                       } 
                     %>      
		                   
						</tbody>
					</table>
					</div>
					<input type="hidden" id="rowNum" value="<%=rowNum%>">
				</div>
				<p class="grade_page_btn"> 
				
				   <%
                     //if(!sEndYn.equals("Y")){
                   %>
				    <a href="javascript:saveCfm('N')"><img src="img/btn_tmp_save.gif" alt="����"></a>

				    <a href="javascript:saveCfm('Y')"><img src="img/btn_fixed.jpg" alt="Ȯ��"/></a>
				   <%
				     //}
				   %>
				    <a href="estmMain.jsp"><img src="img/btn_home.jpg" alt="ù ȭ��"/></a></p>
			</div><!-- //grade_page -->
		</div><!-- //body -->
		<div class="copy">
		<p>Copyright (c) <strong>���ֻ������</strong> All rights Reserved.</p>
		</div>
	</div><!-- //wrap -->
 </form>
 <iframe name="HiddenFrame" src="" width="0" height="0" frameborder="0" scrolling="no"></iframe>
</body>
</html>
