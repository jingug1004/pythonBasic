<%@ page import = "java.util.*" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>

<%  
	String   sInitUrl 	= request.getParameter("INITURL");
	String   sSrcUrl	= "";
	String   sRecvUser	= "";
	
	StringTokenizer ml = new StringTokenizer(sInitUrl,"^"); 
	
	System.out.println(ml .countTokens());
	
	sSrcUrl		= ml.nextToken().trim();
	sRecvUser	= ml.nextToken().trim();  
	
	if(sRecvUser.equals("tmp")) sRecvUser = "";

	
	System.out.println("sSrcUrl======================>"+sSrcUrl);
	System.out.println("sRecvUser======================>"+sRecvUser);
	String url          = "http://localhost:19080";
    String installUrl	= url+"/rbm/miInstall";
    String webAppUrl	= url+"/rbm/rbm_ui/rbm_ci_main_Win32.jsp";
    String keyValue		= "RBM";    
        
	System.out.println("rbm_ui start(install)======================>");
%>

<html>
<head>
<meta http-equiv=Content-type content="text/html; charset=euc-kr">
<SCRIPT LANGUAGE="JavaScript" src="<%=url%>/rbm/script/MiInstaller320.js"></SCRIPT>
<style>
	.f2 { font-size:12 }
	.hide { display:none }
	.show { display:block }	
</style>

<script language="vbscript">
	Function  Check_Module
	  
	  	On Error Resume Next
	  	
	   	Err.number = 0
	   	chkMsg= document.MiInstaller.Version
	  	if  Err.number = 438 Then	
			Check_Module = "false"
	  	Else
	        Check_Module = "true"
	  	End If
	End Function 
</script>

<script language="javascript">

    
	var progressColor = 'blue';	// set to progress bar color
	var pg_cell_At = 0,pg1_cell_At = 0; 
	var IsError = false;
	var proMsg, procMsg;;
	
	function page_link()
	{

		// RBM
		// 단축 아이콘 만들기
		// UBKImage : 250 * 300
		
		//BYTE가 256을 넘으면 안됨..
		// 256을 넘을 경우 MiInstaller의 property를 이용할것
	  
		var strCommand = '-V 3.2 -D Win32U -R FALSE -K RBM -L TRUE -LE TRUE -X ' + MiInstaller.StartXML + ' -U ' + MiInstaller.UpdateURL;
		//update image 바꾸기 
		strCommand = strCommand + ' -G "%Component%BK1.jpg" -BI "%Component%BK2.jpg"';
	  
		MiInstaller.MakeShortCut("%system%MiUpdater321.exe",strCommand,"<%=keyValue%>","%Component%icon2.ico","");
		
		// MiInstaller.MakeShortCut() 인자 설명------------------------
		// MakeShortCut 바로가기를 만드는 함수
		// strExeName: 바로가기를 만들 실행 파일 이름
		// strCommand: 바로가기를 만들 때 필요한 Command 정보
		// strShortcutName: 바로가기 파일 이름
		// strIConPath: 변경하려는 Icon 경로를 %alias%형태로 입력할 수 있습니다.
		// strPos: Startmenu-시작 / Desktop-바탕화면(Default)
	
		// Alias 참고 ------------------------
		// %MiPlatform%
		// %Component%
		// %system%
		// %Window%
		// %ProgramFiles%
	
	} 
	
	function fn_run()
	{
		page_link();
	    
		// 전용 브라우저 실행
		MiInstaller.Run();
		
		window.opener = self; 
		window.open('','_parent',''); 
		self.close();  
	}	
//	
	function pg_cell_init(icell_width,obj,cell_id_nm,tot_len) {
		var inum = 0;
		var ins_cell_str = "";	
	
		while ( inum <= tot_len ) {
			//inum += icell_width;
			//ins_cell_str += '<span id="' + cell_id_nm + '" >&nbsp;</span>';
			inum += icell_width*2 ; 
			ins_cell_str += '<span id="' + cell_id_nm + '" >&nbsp;</span><span>&nbsp;</span>';
		} 
		//ins_cell_str += '<span id="' + cell_id_nm + '" >&nbsp;</span>';
		obj.innerHTML = ins_cell_str;
	}
	


	function progress_clear(obj) {
		for (var i = 0; i < obj.length; i++) obj[i].style.backgroundColor = 'transparent';
	}
	
	function progress_update(obj,cur_cnt) {

		if ( cur_cnt >= obj.length ) cur_cnt = obj.length - 1;

		obj[cur_cnt].style.backgroundColor = progressColor;
	}
		
</script>

 
<SCRIPT LANGUAGE=javascript FOR=MiInstaller EVENT=OnStartDownLoad(VersionFileName,DownFileName,Type,TotalCnt,CurIndex)>
	//alert("OnStartDownLoad : "+DownFileName+" "+Type);
		
	if ( Type == 1 ) //EVENTCONFIG
	{
		progress_clear(progress);
	}
	else if ( Type == 2 ) //EVENTGETVERSIONCNT
	{
		TotalItemCnt = TotalCnt;
		progress_clear(progress1);
		if ( CurIndex > 1 ) {
			var before_At = pg_cell_At;
			pg_cell_At += parseInt( ( ( (CurIndex - 1)/TotalVersionFileCnt ) * progress.length ) - before_At );
			for ( var i = before_At ; i < pg_cell_At ; i++ ) progress_update(progress,i);
		}	
		pg1_cell_At = 0;
		var tpos = DownFileName.lastIndexOf("/") + 1;
		var Fname = DownFileName.substr(tpos,DownFileName.length - tpos);
		item_nm.innerHTML = "&nbsp;" + Fname;
	}
	else if ( Type == 3 ) //EVENTDOWNLOAD
	{
		var before_At = pg1_cell_At;
		pg1_cell_At += parseInt( ( ( (CurIndex - 1)/TotalItemCnt ) * progress.length ) - before_At );
		for ( var i = before_At ; i < pg1_cell_At ; i++ ) progress_update(progress1,i);
		if ( prc_msg != "" && prc_msg != null && prc_msg != "undefined" ) prc_msg.innerHTML = "&nbsp;파일 다운로드 중....";
	}
	else if ( Type == 4 ) //EVENTDISTRIBUTE
	{
		if ( prc_msg != "" && prc_msg != null && prc_msg != "undefined" ) prc_msg.innerHTML = "&nbsp;실제 경로 배포 중....";
	}	
</SCRIPT>

<SCRIPT language=JavaScript for=MiInstaller event=OnEndDownLoad(VersionFileName,DownFileName,Type,TotalCnt,CurIndex)>
{
		//alert("OnEndDownLoad : "+DownFileName+" "+Type);
		
		if ( Type == 1 ) //EVENTCONFIG
		{
			TotalVersionFileCnt = TotalCnt;
			for ( var i = pg_cell_At ; i < progress.length ; i++ ) progress_update(progress,i);
			for ( var j = pg1_cell_At ; j < progress1.length ; j++ ) progress_update(progress1,j);
			if ( prc_msg != "" && prc_msg != null && prc_msg != "undefined" ) prc_msg.innerHTML = "&nbsp;설치 완료";

			fn_run();
			
			//window.location = "./start.html";
			//alert("Install Completed !! ");
		}
		else if ( Type == 2 )//EVENTGETVERSIONCNT
		{
			if ( TotalCnt == CurIndex )
				for ( var i = pg1_cell_At ; i < progress1.length ; i++ ) progress_update(progress1,i);
			else 
			{	
				if ( CurIndex > 1 ) {
				    var before_At = pg_cell_At;
					pg_cell_At += parseInt( ( ( (CurIndex - 1)/TotalVersionFileCnt ) * progress.length ) - before_At );
		
					for ( var i = before_At ; i < pg_cell_At ; i++ ) progress_update(progress,i);
				}	
				pg1_cell_At = 0;
		//		tot_item.innerHTML = "&nbsp;" + CurIndex + "/" + TotalCnt;
				
				var tpos = DownFileName.lastIndexOf("/") + 1;
				var Fname = DownFileName.substr(tpos,DownFileName.length - tpos);
				
				item_nm.innerHTML = "&nbsp;" + Fname;
				//item_size.innerHTML = "&nbsp;" + VersionFileName;
			}

		}
		else if ( Type == 3 )//EVENTDOWNLOAD
		{
		    var before_At = pg1_cell_At;
			pg1_cell_At += parseInt( ( ( (CurIndex - 1)/TotalItemCnt ) * progress.length ) - before_At );

			for ( var i = before_At ; i < pg1_cell_At ; i++ ) progress_update(progress1,i);
		
			if ( prc_msg != "" && prc_msg != null && prc_msg != "undefined" ) prc_msg.innerHTML = "&nbsp;파일 다운로드 중....";
		}
		else if ( Type == 4 )//EVENTDISTRIBUTE
		{
			for ( var i = pg_cell_At ; i < progress.length ; i++ ) progress_update(progress,i);
			if ( IsError ) {
					t_err.className = "show";
					reinstall.className = "show";
			}		
			
			if ( prc_msg != "" && prc_msg != null && prc_msg != "undefined" ) prc_msg.innerHTML = "&nbsp;실제 경로 배포 완료";
			
			
			//window.location = "./start.html";
			//alert("Install Completed !! ");
		}
} 
</SCRIPT>

<SCRIPT language=JavaScript for=MiInstaller event=OnProgress(DownFileName,progress,progressMax,statusText)>
{
	//prog1.innerHTML = "&nbsp;" + progress;
	//prog2.innerHTML = "&nbsp;" + progressMax;
	//prog3.innerHTML = "&nbsp;" + statusText;
} 
</SCRIPT>

<SCRIPT language=JavaScript for=MiInstaller event=OnError(ItemName,ErrorCode,ErrorMsg)>
		IsError = true;
		alert(ErrorMsg);
		err_msg.innerHTML += '<font class="f2" color="red">' + ItemName + '&nbsp;다운&nbsp;실패&nbsp; -- ErrorCode:' + ErrorCode + ' ' + ErrorMsg + "<br>";
		t_err.className = "show";
		reinstall.className = "show";
</SCRIPT>

</head>
<BODY>


<script language="javascript">
		CreateMiInstlr("MiInstaller","Win32U","3.2","RBM");
</script>			
	
<script language="javascript">

	var TotalVersionFileCnt;
	var TotalItemCnt;

	if ( Check_Module() == "true" )
	{		
		MiInstaller.DeviceType = "Win32U";
		MiInstaller.Key = "<%=keyValue%>";
		MiInstaller.Launch = true;
		MiInstaller.Width  = 1024;
		MiInstaller.Height	= 768;
		MiInstaller.Retry = 1;
		MiInstaller.Timeout = 60;
		MiInstaller.GlobalVal = "<%=sSrcUrl%>|<%=sRecvUser%>|";
		MiInstaller.OnlyOne = true;
		MiInstaller.StartXml  = "<%=webAppUrl%>";
		MiInstaller.ComponentPath	= "%UserApp%TobeSoft\\RBM\\component";
		MiInstaller.StartImage  = "";		// MiPlatform Load 이미지
		MiInstaller.UBKImage = "%component%BK1.jpg";  // 업데이트 배경이미지 (사이즈 250 * 300)
		MiInstaller.UpdateUrl = "<%=installUrl%>/320U/update_info.xml";
    
		/***** Vista OS UAC Check *****/
		var IsUACEnabled = MiInstaller.IsUACEnabled();
		
		fn_run();
				
	}
	else
	{
		reinstall.className = "show";
	}			


</script>
</body>
</html>