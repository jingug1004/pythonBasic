<%@ page import="com.ubidcs.report.preview.*,java.io.*,java.util.*" contentType="text/html; charset=euc-kr" %>
<%
	String pageId			= request.getParameter("pageId");				//접근종류 (화면번호)
	String jrf_dir			= "";											//리포트 파일 위치.
	String jrf				= "";											//리포트 파일명(소스)
	String targetDirPath	= "";											//리포트 파일(pdf) 생성위치
	String args				= "";											//argument 정보
	String export_file		= "";											//리포트 파일명(pdf)
	String ds				= "";											//JNDI
	String host				= "";											//WebApplication 명.
	String app				= "";											//WebApplication URL.
	String url				= "";											//WebApplication URL.
	String servlet_url1		= "";											//Form 서블릿 URL.
	String servlet_url2		= "";											//Data 서블릿 URL.
	String year				= "";
	String month			= "";
	String date				= "";
	
	String reportKey		= "";
	String file_url			= "";

	Calendar cal = Calendar.getInstance();
	year = String.valueOf(cal.get(Calendar.YEAR));
	month = String.valueOf(cal.get(Calendar.MONTH)+1);
	date = String.valueOf(cal.get(Calendar.DATE));

	month	= month.length() == 1 ? "0" + month : month; 
	date	= date.length() == 1 ? "0" + date : date; 

	if(pageId.equals("JFO0062")){
		//경륜 등록선수 증명서

		String printNoView	= request.getParameter("PRINT_NO_VIEW");	
		String mainTitle	= request.getParameter("MAIN_TITLE");	
		String noTitle		= request.getParameter("NO_TITLE");	
		String preTitle		= request.getParameter("PRE_TITLE");	
		String midCont		= request.getParameter("MID_CONT");
		String printSeq		= request.getParameter("PRINT_SEQ");
		String resNo		= request.getParameter("RES_NO");
		String fromYmd		= request.getParameter("FROM_YMD");
		String toYmd		= request.getParameter("TO_YMD");
		String racerNo		= request.getParameter("RACER_NO");
		String printNo		= request.getParameter("PRINT_NO");
		String racerNm		= request.getParameter("RACER_NM");
		String nmChn		= request.getParameter("NM_CHN");
		String addr			= request.getParameter("ADDR");

		//jrf_dir			= "/web/waspage/can/ubireport/work/";
		//jrf_dir			= "/web/waspage/usrbm/report/pdf/";		//신규경로
		jrf_dir			= "/web/waspage/can/report/pdf/";
		jrf				= "JFO0062_1.jrf";
		targetDirPath	= "/web/waspage/can/webcerti/KCYCLE_CERTI/" + year;

		args			= "PRINT_NO_VIEW#"+printNoView+"#MAIN_TITLE#"+mainTitle+"#NO_TITLE#"+noTitle+"#PRE_TITLE#"+preTitle+"#MID_CONT#"+midCont+"#PRINT_SEQ#"+printSeq+"#FROM_YMD#"+fromYmd+"#TO_YMD#"+toYmd+"#RES_NO#"+resNo+"#PRINT_NO#"+printNo+"#RACER_NM#"+racerNm+"#NM_CHN#"+nmChn+"#ADDR#"+addr;
		export_file		= ("CERT_"+year+month+date+"_"+printSeq+".pdf").replaceAll(" ","");
		ds				= "CRA#jdbc/CRADS";
		host			= "can.kcycle.or.kr";
		app				= "can";
		url				= "http://" + host + "/" + app;
		servlet_url1	= url + "/UbiForm";
		servlet_url2	= url + "/UbiData";
		file_url		= url + "/ubireport";
		reportKey		= resNo.substring(6,13);
	}
	else if(pageId.equals("E03119010")){
		//경정 등록선수 증명서
		String imgUrl		= request.getParameter("IMG_URL");	
		String sealImgUrl	= request.getParameter("SEAL_IMG_URL");	
		String issuYear		= request.getParameter("ISSU_YEAR");	
		String seq			= request.getParameter("SEQ");
		String issuNo		= request.getParameter("ISSU_NO");
		String resNo		= request.getParameter("RES_NO");

		//jrf_dir			= "/web/waspage/can/ubireport/work/";
		//jrf_dir			= "/web/waspage/usrbm/report/pdf/";		//신규경로
		jrf_dir			= "/WAS/web6/report/";
		jrf				= "e03119010_racer_cert_1.jrf";
		targetDirPath	= "/web/waspage/can/webcerti/KBOAT_CERTI/" + year;

		args			= "IMG_URL#"+imgUrl+"#SEAL_IMG_URL#"+sealImgUrl+"#ISSU_YEAR#"+issuYear+"#SEQ#"+seq+"#ISSU_NO#"+issuNo;
		export_file		= ("CERT_"+year+month+date+"_"+issuNo+".pdf").replaceAll(" ","");
		ds				= "MBR#jdbc/MBR";
		host			= "can.kcycle.or.kr";
		app				= "can";
		url				= "http://" + host + "/" + app;
		servlet_url1	= url + "/UbiForm";
		servlet_url2	= url + "/UbiData";
		file_url		= url + "/ubireport";
		reportKey		= resNo.substring(6,13);
	}

	else if(pageId.equals("JMO0032")||pageId.equals("E07030052")){
		//경륜/경정 원천징수 영수증

		String p_bizPlcNo	= request.getParameter("p_bizPlcNo");			//사업자등록번호
		String p_gbn		= request.getParameter("p_gbn");				//보고서 상단의 항목
		String p_payDtFr	= request.getParameter("p_payDtFr");			//공제시작일
		String p_payDtTo	= request.getParameter("p_payDtTo");			//공제종료일
		String p_juminNo	= request.getParameter("p_juminNo");		
		String racerNo		= request.getParameter("racerNo");				//선수번호
		String issuSeq		= request.getParameter("issuSeq");				//발급번호
		String issuYear		= request.getParameter("issuYear");				//발급번호

		jrf_dir			= "/webapp/ifis/ifisweb/report/RF/";
		jrf				= "RF316.jrf";

		if(pageId.equals("JMO0032")){
			//경륜
			targetDirPath	= "/web/waspage/can/webcerti/KCYCLE_CERTI/" + year;
		}
		else if(pageId.equals("E07030052")){
			//경정
			targetDirPath	= "/web/waspage/can/webcerti/KBOAT_CERTI/" + year;
		}

		//args			= "p_bizPlcNo#" + p_bizPlcNo + "#p_gbn#" + p_gbn + "#p_payDtFr#" + p_payDtFr + "#p_payDtTo#" + p_payDtTo + "#p_juminNo#" + p_juminNo + "#p_acctDivCd#p_acctUnitCd";
		args			= "p_bizPlcNo#" + p_bizPlcNo + "#p_gbn#(소득자 보관용)#p_payDtFr#" + p_payDtFr + "#p_payDtTo#" + p_payDtTo + "#p_juminNo#" + p_juminNo + "#p_acctDivCd#p_acctUnitCd";
		export_file		= ("IFIS_" + year + month + date + "_" + issuSeq + ".pdf").replaceAll(" ","");
		ds				= "ora10g#jdbc/ifis";							
		host			= "140.101.1.11:8080";
		app				= "ifis";
		url				= "http://" + host + "/" + app;
		servlet_url1	= url + "/Form";									//Form 서블릿 URL.
		servlet_url2	= url + "/Result";									//Data 서블릿 URL.
		file_url		= url;
		reportKey		= p_juminNo.split("-")[1];
	}

	//이거 지워야함...
	//targetDirPath	= "/web/waspage/can/ubireport/sample";

	//디렉토리 생성
	File targetDir = new File(targetDirPath);
	if(!targetDir.isDirectory()){
		targetDir.mkdir();
	}

	//String file_url = url+"/ubireport";										//리포트에서 사용되는 이미지 또는 공통 아이템 정보를 가져오기위한 정보.
	String export_dir = targetDirPath + "/";								//파일 저장 경로.
	String rtnIsSuccess = "N";												//성공여부

	String errMsg = "";

	try {

		if(pageId.equals("JFO0062") || pageId.equals("E03119010")){
			//선수등록증명서
			UbiViewer ubiviewer = new UbiViewer(false, false);						//객체 생성
			ubiviewer.setParameters(file_url, servlet_url1, servlet_url2, jrf_dir, jrf, ds, args, "TYPE6"); // 파일 저장을 위한 기본 정보 설정
			
			//확정
			ubiviewer.isDeflater = true;											//서버와의 통신 옵션 (ubigateway.property의 속성과 같아야함)
			ubiviewer.isUnicode = false;												//서버와의 통신 옵션 (ubigateway.property의 속성과 같아야함)
			ubiviewer.isBase64 = true;												//서버와의 통신 옵션 (ubigateway.property의 속성과 같아야함)

			ubiviewer.isUTF8 = true;												//서버와의 통신 옵션 (ubigateway.property의 속성과 같아야함)
			ubiviewer.fontRevision = true;											//변경 불가.
			ubiviewer.setFontPath("/usr/jdk/jdk1.5.0_17/jre/lib/fonts/");			//WAS가 설치된 서버의 OS가 Non-Windows인 경우 폰트 정보를 가져오는 부분을 설정.
			ubiviewer.setExportParams("PDF", (export_dir + export_file));			// 파일 저장 설정

			ubiviewer.setExportPassword(reportKey);								//암호화

			boolean isSuccess = ubiviewer.loadReport();								// 파일 저장 수행.

			if( isSuccess ) {
				rtnIsSuccess = "Y";
			}
		}
		else if(pageId.equals("JMO0032")||pageId.equals("E07030052")){
			//원천징수 영수증
			UbiViewer ubiviewer = new UbiViewer(false, false);	// 객체 생성
			ubiviewer.isDeflater = true;						//서버와의 통신 옵션 (ubigateway.property의 속성과 같아야함)
			ubiviewer.isUnicode = false;						//서버와의 통신 옵션 (ubigateway.property의 속성과 같아야함)
			ubiviewer.isBase64 = true;							//서버와의 통신 옵션 (ubigateway.property의 속성과 같아야함)
			ubiviewer.isUTF8 = false;							//서버와의 통신 옵션 (ubigateway.property의 속성과 같아야함)
			ubiviewer.fontRevision = true;						// 변경 불가.
			ubiviewer.setFontPath("/usr/jdk/jdk1.5.0_17/jre/lib/fonts/");		//WAS가 설치된 서버의 OS가 Non-Windows인 경우 폰트 정보를 가져오는 부분을 설정.

			ubiviewer.setExportParams("PDF", (export_dir + export_file)); // 파일 저장 설정
			ubiviewer.setParameters(file_url, servlet_url1, servlet_url2, jrf_dir, jrf, ds, args, "TYPE6"); // 파일 저장을 위한 기본 정보 설정

			ubiviewer.setExportPassword(reportKey);								//암호화

			boolean isSuccess = ubiviewer.loadReport(); // 파일 저장 수행.

			if( isSuccess ) {
				rtnIsSuccess = "Y";
			}
		}
	}
	catch(Exception e) {
		errMsg = e.toString();
		e.printStackTrace();
	}
%>
<%=rtnIsSuccess%>!&%<%=year+"/"+export_file%>
