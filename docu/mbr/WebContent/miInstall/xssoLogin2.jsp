<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="SafeIdentity.*,java.io.*,java.net.*" %>

<%  	
	String url          = "http://mbr.kboat.or.kr";
	String installUrl	= url+"/miInstall";
	String webAppUrl	= url+"/boa_ui/boa_ci_main_Win32.xml"; 
    String keyValue		= "MBR";    

    String sRemoteAddr  = request.getRemoteAddr();
	String ssoToken     = request.getParameter("SSOTOK");
	String MenuId       = request.getParameter("MENUID");
	String sPswdEnc     = "Y";
	String sRecvUser    = "";
	String sRecvUserPW  = "";
	
	String SSOAPIKEY    = request.getParameter("368B184727E89AB69FAF");
	int apiCode			= -1;
	String apiMsg		= "";
	
	if(null == MenuId)MenuId = "";
	if(null == ssoToken){
		ssoToken = "";
		System.out.println(ssoToken);	
	}
	
    if(ssoToken != null){
    	try{
    		SSO sso = new SSO(SSOAPIKEY);
    		sso.verifyToken(ssoToken, sRemoteAddr);
    		apiCode = sso.getLastError();
    		apiMsg = sso.getLastErrorMsg();
    		
    		if(apiCode >= 0){
    			sRecvUser    = sso.getValueUserID(); //���̵�
    	    	sRecvUserPW  = sso.getValue("PWD");  //�н�����
    		} else {
    			out.println(apiCode + ":" + apiMsg);
    		}
    	}catch(Exception e){
    		out.println("[verifySSOToken1]"+e.getMessage());
    	}
    } else {
    	sRecvUser    = "";
    	sRecvUserPW  = ""; 
    }
%>

<html>
<head>
<meta http-equiv=Content-type content="text/html; charset=euc-kr">
<SCRIPT LANGUAGE="JavaScript" src="./MiInstaller320.js"></SCRIPT>
<style>
.f2 {
	font-size: 12
}

.hide {
	display: none
}

.show {
	display: block
}
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

		// MBR
		// ���� ������ �����
		// UBKImage : 250 * 300
		
		//BYTE�� 256�� ������ �ȵ�..
		// 256�� ���� ��� MiInstaller�� property�� �̿��Ұ�
	  
		var strCommand = '-V 3.2 -D Win32U -R FALSE -K MBR -L TRUE -LE TRUE -X ' + MiInstaller.StartXML + ' -U ' + MiInstaller.UpdateURL;
		//update image �ٲٱ� 
		strCommand = strCommand + ' -G "%Component%BK1.jpg" -BI "%Component%BK2.jpg"';
	  
		MiInstaller.MakeShortCut("%system%MiUpdater320.exe",strCommand,"<%=keyValue%>","%Component%icon2.ico","");
		
		// MiInstaller.MakeShortCut() ���� ����------------------------
		// MakeShortCut �ٷΰ��⸦ ����� �Լ�
		// strExeName: �ٷΰ��⸦ ���� ���� ���� �̸�
		// strCommand: �ٷΰ��⸦ ���� �� �ʿ��� Command ����
		// strShortcutName: �ٷΰ��� ���� �̸�
		// strIConPath: �����Ϸ��� Icon ��θ� %alias%���·� �Է��� �� �ֽ��ϴ�.
		// strPos: Startmenu-���� / Desktop-����ȭ��(Default)
	
		// Alias ���� ------------------------
		// %MiPlatform%
		// %Component%
		// %system%
		// %Window%
		// %ProgramFiles%
	
	} 
	
	function fn_run()
	{
		page_link();
	    
		// ���� ������ ����
		MiInstaller.Run();
		
		//self.opener=self;
		//window.close();
		
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


<SCRIPT LANGUAGE=javascript FOR=MiInstaller
	EVENT=OnStartDownLoad(VersionFileName,DownFileName,Type,TotalCnt,CurIndex)>
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
		if ( prc_msg != "" && prc_msg != null && prc_msg != "undefined" ) prc_msg.innerHTML = "&nbsp;���� �ٿ�ε� ��....";
	}
	else if ( Type == 4 ) //EVENTDISTRIBUTE
	{
		if ( prc_msg != "" && prc_msg != null && prc_msg != "undefined" ) prc_msg.innerHTML = "&nbsp;���� ��� ���� ��....";
	}	
</SCRIPT>

<SCRIPT language=JavaScript for=MiInstaller
	event=OnEndDownLoad(VersionFileName,DownFileName,Type,TotalCnt,CurIndex)>
{
		//alert("OnEndDownLoad : "+DownFileName+" "+Type);
		
		if ( Type == 1 ) //EVENTCONFIG
		{
			TotalVersionFileCnt = TotalCnt;
			for ( var i = pg_cell_At ; i < progress.length ; i++ ) progress_update(progress,i);
			for ( var j = pg1_cell_At ; j < progress1.length ; j++ ) progress_update(progress1,j);
			if ( prc_msg != "" && prc_msg != null && prc_msg != "undefined" ) prc_msg.innerHTML = "&nbsp;��ġ �Ϸ�";

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
		
			if ( prc_msg != "" && prc_msg != null && prc_msg != "undefined" ) prc_msg.innerHTML = "&nbsp;���� �ٿ�ε� ��....";
		}
		else if ( Type == 4 )//EVENTDISTRIBUTE
		{
			for ( var i = pg_cell_At ; i < progress.length ; i++ ) progress_update(progress,i);
			if ( IsError ) {
					t_err.className = "show";
					reinstall.className = "show";
			}		
			
			if ( prc_msg != "" && prc_msg != null && prc_msg != "undefined" ) prc_msg.innerHTML = "&nbsp;���� ��� ���� �Ϸ�";
			
			
			//window.location = "./start.html";
			//alert("Install Completed !! ");
		}
} 
</SCRIPT>

<SCRIPT language=JavaScript for=MiInstaller
	event=OnProgress(DownFileName,progress,progressMax,statusText)>
{
	//prog1.innerHTML = "&nbsp;" + progress;
	//prog2.innerHTML = "&nbsp;" + progressMax;
	//prog3.innerHTML = "&nbsp;" + statusText;
} 
</SCRIPT>

<SCRIPT language=JavaScript for=MiInstaller
	event=OnError(ItemName,ErrorCode,ErrorMsg)>
		IsError = true;
		alert(ErrorMsg);
		err_msg.innerHTML += '<font class="f2" color="red">' + ItemName + '&nbsp;�ٿ�&nbsp;����&nbsp; -- ErrorCode:' + ErrorCode + ' ' + ErrorMsg + "<br>";
		t_err.className = "show";
		reinstall.className = "show";
</SCRIPT>

</head>
<BODY>
<div align="center"><img src="./images/loading_1.gif" width="178" height="40">
<table width="100" height="5" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td></td>
	</tr>
</table>
</div>
<table width="540" height="180" border="0" align="center"
	cellpadding="0" cellspacing="0" style="border: 1px solid #dadada">
	<tr>
		<td style="border: 5px solid #F0F0F0">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="200" align="center"><img src="./images/loading_2.gif" width="148" height="135"></td>
				<td>
				<table border=0>
					<tr>
						<td class="f2">��ü ���ϼ�ġ ���� ��Ȳ</td>
					</tr>
					<tr>
						<td width="250">
						<div id=pg
							style="font-size: 8pt; padding: 1px; border: 1px GROOVE silver; height: 10; overflow: hidden">
						<span id="progress">&nbsp;</span></div>
						</td>
					</tr>
					<tr>
						<td colspan=2 class="f2"><br>
						���� ���ϼ�ġ ���� ��Ȳ</td>
					</tr>
					<tr>
						<td>
						<div id=pg1
							style="font-size: 8pt; padding: 1px; border: 1px GROOVE silver; height: 10; overflow: hidden">
						<span id="progress1">&nbsp;</span></div>
						</td>
					</tr>
					<tr>
						<td>
						<table width="100" height="5" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td></td>
							</tr>
						</table>
						<table cellpadding="1" cellspacing="1">
							<tr>
								<td class="f2">������� :</td>
								<td id="item_nm" class="f2" align=left NOWRAP>&nbsp;
								<td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
						<td id=prc_msg class="f2">&nbsp;
						<td>
					</tr>
				</table>
				<table width="255" class="hide" id=t_err>
					<tr>
						<td class="f2">��ġ�� ������ �߻��� �׸�</td>
					</tr>
					<tr>
						<td id=err_msg class="f2">&nbsp;</td>
					</tr>
				</table>
				<table class="hide" id=reinstall>
					<tr>
						<td><a class="f2" href="javascript:window.location.reload();">�ڵ��缳ġ</a></td>
					</tr>
					<tr>
						<td><a class="f2" href="MiPlatform_SetupUpdater320_20071011_0919.exe">������ġ</a></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<script language="javascript">
		CreateMiInstlr("MiInstaller","Win32U","3.2","MBR");
</script>

<script language="javascript">

	var TotalVersionFileCnt;
	var TotalItemCnt;

	if ( Check_Module() == "true" )
	{
		var tmp_len = progress.offsetWidth; 
		var tmp_tot_len = pg.offsetWidth; 
		pg_cell_init(tmp_len,pg,"progress",tmp_tot_len);
		pg_cell_init(tmp_len,pg1,"progress1",tmp_tot_len);
		
		MiInstaller.DeviceType = "Win32U";
		MiInstaller.Key = "<%=keyValue%>";
		MiInstaller.Launch = true;
		MiInstaller.Width  = 1024;
		MiInstaller.Height	= 768;
		MiInstaller.Retry = 1;
		MiInstaller.Timeout = 60; 
		MiInstaller.GlobalVal = "<%=MenuId%>|<%=sRecvUser%>|<%=sRecvUserPW%>|<%=sPswdEnc%>";
		MiInstaller.OnlyOne = true;
		MiInstaller.StartXml  = "<%=webAppUrl%>";
		MiInstaller.ComponentPath	= "%UserApp%TobeSoft\\MBR\\component";
		MiInstaller.StartImage  = "";		// MiPlatform Load �̹���
		MiInstaller.UBKImage = "%component%BK1.jpg";  // ������Ʈ ����̹��� (������ 250 * 300)
    

		/***** Vista OS UAC Check *****/
		var IsUACEnabled = MiInstaller.IsUACEnabled();
		
		if(IsAfterVista())
		{
			if(IsUACEnabled==true)
			{
				if(MiInstaller.IsElevatedProcess() == true)
					IsUACEnabled = false;
					
				MiInstaller.UpdateUrl = "<%=installUrl%>/320U/update_info_VISTA.xml";
			}
			else
			{
				MiInstaller.UpdateUrl = "<%=installUrl%>/320U/update_info_VISTA_UAC.xml";
			}  
		}
		else
		{
			MiInstaller.UpdateUrl = "<%=installUrl%>/320U/update_info.xml";
		}
		
		var Bcnt = MiInstaller.ExistVersionUpCnt(); 
		if ( Bcnt )
		{
			MiInstaller.StartDownload();
		} 
		else 
		{
			fn_run();
		}
	}
	else
	{
		reinstall.className = "show";
	}			


</script>
</body>
</html>
