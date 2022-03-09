<%@ page import="com.ubidcs.report.preview.*,java.io.*,java.util.*" contentType="text/html; charset=euc-kr" %>
<%
	String pageId			= request.getParameter("pageId");				//�������� (ȭ���ȣ)
	String jrf_dir			= "";											//����Ʈ ���� ��ġ.
	String jrf				= "";											//����Ʈ ���ϸ�(�ҽ�)
	String targetDirPath	= "";											//����Ʈ ����(pdf) ������ġ
	String args				= "";											//argument ����
	String export_file		= "";											//����Ʈ ���ϸ�(pdf)
	String ds				= "";											//JNDI
	String host				= "";											//WebApplication ��.
	String app				= "";											//WebApplication URL.
	String url				= "";											//WebApplication URL.
	String servlet_url1		= "";											//Form ���� URL.
	String servlet_url2		= "";											//Data ���� URL.
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
		//��� ��ϼ��� ����

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
		//jrf_dir			= "/web/waspage/usrbm/report/pdf/";		//�ű԰��
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
		//���� ��ϼ��� ����
		String imgUrl		= request.getParameter("IMG_URL");	
		String sealImgUrl	= request.getParameter("SEAL_IMG_URL");	
		String issuYear		= request.getParameter("ISSU_YEAR");	
		String seq			= request.getParameter("SEQ");
		String issuNo		= request.getParameter("ISSU_NO");
		String resNo		= request.getParameter("RES_NO");

		//jrf_dir			= "/web/waspage/can/ubireport/work/";
		//jrf_dir			= "/web/waspage/usrbm/report/pdf/";		//�ű԰��
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
		//���/���� ��õ¡�� ������

		String p_bizPlcNo	= request.getParameter("p_bizPlcNo");			//����ڵ�Ϲ�ȣ
		String p_gbn		= request.getParameter("p_gbn");				//���� ����� �׸�
		String p_payDtFr	= request.getParameter("p_payDtFr");			//����������
		String p_payDtTo	= request.getParameter("p_payDtTo");			//����������
		String p_juminNo	= request.getParameter("p_juminNo");		
		String racerNo		= request.getParameter("racerNo");				//������ȣ
		String issuSeq		= request.getParameter("issuSeq");				//�߱޹�ȣ
		String issuYear		= request.getParameter("issuYear");				//�߱޹�ȣ

		jrf_dir			= "/webapp/ifis/ifisweb/report/RF/";
		jrf				= "RF316.jrf";

		if(pageId.equals("JMO0032")){
			//���
			targetDirPath	= "/web/waspage/can/webcerti/KCYCLE_CERTI/" + year;
		}
		else if(pageId.equals("E07030052")){
			//����
			targetDirPath	= "/web/waspage/can/webcerti/KBOAT_CERTI/" + year;
		}

		//args			= "p_bizPlcNo#" + p_bizPlcNo + "#p_gbn#" + p_gbn + "#p_payDtFr#" + p_payDtFr + "#p_payDtTo#" + p_payDtTo + "#p_juminNo#" + p_juminNo + "#p_acctDivCd#p_acctUnitCd";
		args			= "p_bizPlcNo#" + p_bizPlcNo + "#p_gbn#(�ҵ��� ������)#p_payDtFr#" + p_payDtFr + "#p_payDtTo#" + p_payDtTo + "#p_juminNo#" + p_juminNo + "#p_acctDivCd#p_acctUnitCd";
		export_file		= ("IFIS_" + year + month + date + "_" + issuSeq + ".pdf").replaceAll(" ","");
		ds				= "ora10g#jdbc/ifis";							
		host			= "140.101.1.11:8080";
		app				= "ifis";
		url				= "http://" + host + "/" + app;
		servlet_url1	= url + "/Form";									//Form ���� URL.
		servlet_url2	= url + "/Result";									//Data ���� URL.
		file_url		= url;
		reportKey		= p_juminNo.split("-")[1];
	}

	//�̰� ��������...
	//targetDirPath	= "/web/waspage/can/ubireport/sample";

	//���丮 ����
	File targetDir = new File(targetDirPath);
	if(!targetDir.isDirectory()){
		targetDir.mkdir();
	}

	//String file_url = url+"/ubireport";										//����Ʈ���� ���Ǵ� �̹��� �Ǵ� ���� ������ ������ ������������ ����.
	String export_dir = targetDirPath + "/";								//���� ���� ���.
	String rtnIsSuccess = "N";												//��������

	String errMsg = "";

	try {

		if(pageId.equals("JFO0062") || pageId.equals("E03119010")){
			//�����������
			UbiViewer ubiviewer = new UbiViewer(false, false);						//��ü ����
			ubiviewer.setParameters(file_url, servlet_url1, servlet_url2, jrf_dir, jrf, ds, args, "TYPE6"); // ���� ������ ���� �⺻ ���� ����
			
			//Ȯ��
			ubiviewer.isDeflater = true;											//�������� ��� �ɼ� (ubigateway.property�� �Ӽ��� ���ƾ���)
			ubiviewer.isUnicode = false;												//�������� ��� �ɼ� (ubigateway.property�� �Ӽ��� ���ƾ���)
			ubiviewer.isBase64 = true;												//�������� ��� �ɼ� (ubigateway.property�� �Ӽ��� ���ƾ���)

			ubiviewer.isUTF8 = true;												//�������� ��� �ɼ� (ubigateway.property�� �Ӽ��� ���ƾ���)
			ubiviewer.fontRevision = true;											//���� �Ұ�.
			ubiviewer.setFontPath("/usr/jdk/jdk1.5.0_17/jre/lib/fonts/");			//WAS�� ��ġ�� ������ OS�� Non-Windows�� ��� ��Ʈ ������ �������� �κ��� ����.
			ubiviewer.setExportParams("PDF", (export_dir + export_file));			// ���� ���� ����

			ubiviewer.setExportPassword(reportKey);								//��ȣȭ

			boolean isSuccess = ubiviewer.loadReport();								// ���� ���� ����.

			if( isSuccess ) {
				rtnIsSuccess = "Y";
			}
		}
		else if(pageId.equals("JMO0032")||pageId.equals("E07030052")){
			//��õ¡�� ������
			UbiViewer ubiviewer = new UbiViewer(false, false);	// ��ü ����
			ubiviewer.isDeflater = true;						//�������� ��� �ɼ� (ubigateway.property�� �Ӽ��� ���ƾ���)
			ubiviewer.isUnicode = false;						//�������� ��� �ɼ� (ubigateway.property�� �Ӽ��� ���ƾ���)
			ubiviewer.isBase64 = true;							//�������� ��� �ɼ� (ubigateway.property�� �Ӽ��� ���ƾ���)
			ubiviewer.isUTF8 = false;							//�������� ��� �ɼ� (ubigateway.property�� �Ӽ��� ���ƾ���)
			ubiviewer.fontRevision = true;						// ���� �Ұ�.
			ubiviewer.setFontPath("/usr/jdk/jdk1.5.0_17/jre/lib/fonts/");		//WAS�� ��ġ�� ������ OS�� Non-Windows�� ��� ��Ʈ ������ �������� �κ��� ����.

			ubiviewer.setExportParams("PDF", (export_dir + export_file)); // ���� ���� ����
			ubiviewer.setParameters(file_url, servlet_url1, servlet_url2, jrf_dir, jrf, ds, args, "TYPE6"); // ���� ������ ���� �⺻ ���� ����

			ubiviewer.setExportPassword(reportKey);								//��ȣȭ

			boolean isSuccess = ubiviewer.loadReport(); // ���� ���� ����.

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
