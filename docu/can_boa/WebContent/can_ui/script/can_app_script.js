/*================================================================================================/
 * PROGRAM-ID                           template_app_script 
 * DESCRIPTION   :  해당 업무의 공통 트랜잭션처리                                            
 * CREATE DATE   :  2007. 10. 16                                                            
 * UPDATE DATE   :  2007. 10. 16                                                           
 * PROGRAMMER    :                                                                          
=================================================================================================*/
#include "lib::can_common_script.js";
#include "lib::can_message_script.js";


/**************************************************************************************************
*  함   수  명				: fcd_SendTransaction
*  설       명				: Transaction 수행
*  param1) SvcName			: 서비스명
*  param2) ActionName		: 액션명
*  param3) InDataset		: 입력 Dataset
*  param4) OutDataset		: 출력 Dataset
*  param5) CallbackName		: Callback 함수명
*  return Type				: boolean
*  return 내용				: true/false
**************************************************************************************************/
function fcd_SendTransaction(SvcName, InDataset, OutDataset, CallbackName, bProgress, sAccessType) {
    
    var sService = split(SvcName, ":");
    var JspSvc   = "DEFAULT_SERVER::handle.do?";
    //var Action   = "ServiceName=" + sService[0] + "&" + sService[1] + "=1";
    var Action   = "ServiceName=" + sService[0] + "&" + sService[1] + "=1&ActionName="+sService[1];
    var SvcParam = fcd_GetParam();
	
	if ( fnc_IsNull(bProgress) ) {
		fnc_ProgressPop();
	}
	
	InDataset = InDataset + " gdsUser=gdsUser";
    transaction(SvcName, JspSvc + Action, InDataset, OutDataset, SvcParam, CallbackName);
//Trace("fcd_SendTransaction::JSESSIONID="+JSESSIONID);
//trace(JspSvc + Action + SvcParam);
//trace("InDataset--->"+InDataset);
//trace("OutDataset--->"+OutDataset);
//trace("SendHttpStr["+http.SendHttpStr+"]");
//trace(http.SendContents);
//trace(http.recvContents);
	
	http.Sync = true;
    if (!fnc_IsNull(sAccessType)) {        
        var sAction = sService[0] + "[" + sService[1] + "]";
        fcd_SavePersonalAccess(sFormId, sAccessType, sAction, SvcParam);
    }
    
    if(global.GBL_PERSONALMN_CHK == "Y") {
		var sAction = sService[0] + "[" + sService[1] + "]";
		fcd_SavePersonalMnAccess(sFormId, sAccessType, sAction, SvcParam);
	}
	http.Sync = false;
}


/**************************************************************************************************
*  함   수  명				: fcd_SendPersonalTransaction
*  설       명				: Transaction 수행
*  param1) SvcName			: 서비스명
*  param2) ActionName		: 액션명
*  param3) InDataset		: 입력 Dataset
*  param4) OutDataset		: 출력 Dataset
*  param5) CallbackName		: Callback 함수명
*  return Type				: boolean
*  return 내용				: true/false
**************************************************************************************************/
 function fcd_SendPersonalTransaction(SvcName, InDataset, OutDataset, CallbackName, bProgress, sAccessType) {
    
    var sService = split(SvcName, ":");
    var JspSvc   = "DEFAULT_SERVER::handle.do?";
    var Action   = "ServiceName=" + sService[0] + "&" + sService[1] + "=1&ActionName="+sService[1]; // 접속정보 로그인용으로 추가함
    var SvcParam = fcd_GetParam();
	 
	if ( bProgress ) {
		fnc_ProgressPop(); 
	} 
	
	http.Sync = true;
	InDataset = InDataset + " gdsUser=gdsUser";
    transaction(SvcName, JspSvc + Action, InDataset, OutDataset, SvcParam, CallbackName);
	http.Sync = false;
}

/**************************************************************************************************
*  함   수  명				: fcd_GetComboDs
*  설       명				: 복수개의 공통 코드를 가져와 outDataset에 넣기 위한 Transaction 수행
*  param1) oDataSet         : 공통 코드 그룹아이디 목록을 가지고 있는 DataSet (dsInComCd)
*  return Type				: 해당 없음
*  return 내용				: 해당 없음
**************************************************************************************************/
function fcd_GetComboDs(oDataSet) {

    //Combo List를 가지고 있는 DataSet의 이름 및 OBJECT 유무 체크
    if(!isValid(oDataSet) || oDataSet.ID!="dsInComCd") {
		fnc_Message(SNIS_COM_1011, "N");
        return;
    }

	var sServiceName = "common-service:searchCode";         // 서버에 요청할 Sevice 명
	var sInDataSet   = oDataSet.ID + "=" + oDataSet.ID;     // Input DataSet 명칭
	var sOutDataSet  = "";                                  // Output DataSet 명칭

	var sDataSetName = "";                                  // DataSet Name  저장 변수
	for ( var i = 0; i < oDataSet.GetRowCount(); i++) {
		// InComCd Dataset에 등록된 OutDataset 이름을 얻어옴
        sDataSetName = oDataSet.GetColumn(i, "DSNAME");
        sOutDataSet += sDataSetName + "=" + sDataSetName + " ";
	}
//	Trace("sOutDataSet:"+sOutDataSet);
	
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
}



/**************************************************************************************************
*  함   수  명				: fcd_AddParam
*  설       명				: Transaction에 사용될 Parameter추가
*  param1) ParamName		: Parameter 명
*  param2) ParamValue		: Parameter 값
*  return Type				: 해당 없음
*  return 내용				: 해당 없음
**************************************************************************************************/
var gcan_SvcParam = Array(-1);   // Transaction에서 사용되는 Parameter

function fcd_AddParam(ParamName, ParamValue) {
    var aParam = Array(2);
    aParam[0] = ParamName;
    aParam[1] = ParamValue;

    gcan_SvcParam[length(gcan_SvcParam)] = aParam;
}



/**************************************************************************************************
*  함   수  명				: fcd_GetParam
*  설       명				: Transaction에 사용할 Parameter를 추가
*  return Type				: String
*  return 내용				: Transaction의 Parameter
**************************************************************************************************/
function fcd_GetParam() {
	var sReturn = "";
	for ( i = 0; i < length(gcan_SvcParam); i++ ) {
		sReturn += gcan_SvcParam[i][0] + "=" + quote(gcan_SvcParam[i][1]) + " ";
	}

	gcan_SvcParam = Array(-1);
    return sReturn;
}



/**************************************************************************************************
*  함   수  명				: fcd_HelpDialoge
*  설       명				: 새로운 Popup 창을 열어 종류에 따른 화면 Display
*  param1) sSearchKind		: Dialog 종류 (우편번호, 공통 코드, 부서트리)
*  param2) ParamValue		: Dialog창에 나타날 Form Variable의 초기값
*  param3) sCommonGubn		: 
*  param4) oFocusControl	: 
*  return Type				: Boolean
*  return 내용				: Dialog가 정상적으로 동작했는지 여부 
**************************************************************************************************/
function fcd_HelpDialoge(sSearchKind, sInputValue, sCommonGubn, oFocusControl) {

	var sFormName = "";              //호출할 Form ID

	switch (sSearchKind){
		case "POST" : 
			sFormName = "common::comPost.xml";
			break;
		case "CODE" :
			sFormName = "common::comCodeNm.xml";
			break;
		case "DPTR" :
			sFormName = "common::comDeptTree.xml";
			break;
		
	}

	//Help popup Open
	var sResponse = Dialog(sFormName, 
	                       " sSearchKind=" + quote(sSearchKind) +
	                       " sInputValue=" + quote(sInputValue) + 
	                       " sCommonGubn=" + quote(sCommonGubn), 
	                       500, 500, "titlebar=true",-1, -1);
	
	if (oFocusControl!=null) {
		oFocusControl.setfocus();
		GetNextComponent(true).setfocus();;
	}
	
	//결과 값 반환
	return sResponse;
}



/**************************************************************************************************
*  함   수  명				: fcd_MultiRowDisplay
*  설       명				: 새로운 Popup 창을 열어 코드 선택 화면을 Display
*  param1) oForm			: Form Object
*  param2) vREVOBJ			: 결과값을 받을 Text Object명
*  param3) sGRDTIT			: 그리드 제목
*  param4) sCUROBJ			: 현재 LOST FOCUS 오브젝트
*  param5) oDataset			: DataSet
*  return Type				: 해당 없음
*  return 내용				: 해당 없음
**************************************************************************************************/
function fcd_MultiRowDisplay(oForm, vREVOBJ, sGRDTIT, sCUROBJ, oDataset) {

	// 검색된 자료가 복수건이면 선택 화면 Open 
	if ( gdsSelectItem.RowCount() > 1 ) {
		
		var iX = 0;
		var iY = 0;
		var i  = 0;
		var iFormX = ClientToScreenX(oForm,0);
		var iFormY = ClientToScreenY(oForm,0);
		var sResponse = "";
		
		//X 좌표 구하기
		if ((oForm.Width + iFormX) >= (504 + ClientToScreenX(sCUROBJ, 0))) {
			iX = ClientToScreenX(sCUROBJ, 0) - 2;
		} else {
			iX = ClientToScreenX(sCUROBJ, 0) - (ClientToScreenX(sCUROBJ, 0) + 504 - oForm.Width - iFormX);
		}

		//Y 좌표 구하기
		if ((oForm.Height+iFormY) >= (208+ ClientToScreenY(sCUROBJ, sCUROBJ.height))) {
			iY = ClientToScreenY(sCUROBJ, sCUROBJ.height)-1;
		} else {
			iY = ClientToScreenY(sCUROBJ, 0)-216;
		}
	                       
		sResponse = oForm.Dialog("common::comChoice.xml" ,
		                         " sGrdTit=" + quote(sGRDTIT), 
		                         504, 208, "titlebar=false", iX, iY);
		if (sResponse==false) {
			sCUROBJ.value = sCUROBJ.userdata;
			for (i=0; i<Length( vREVOBJ ); i++) {
				vREVOBJ[i] = "";        //결과값을 받을 Text Object명
			}
			sCUROBJ  = "";
			return;
		}     
	}
	
	for (i=0; i<Length( vREVOBJ ); i++) {
		if (length(trim(vREVOBJ[i]))>0) object(vREVOBJ[i]) = gdsSelectItem.GetColumn(gdsSelectItem.Row , i);
		vREVOBJ[i] = "";        //결과값을 받을 Text Object명
	}
	sCUROBJ  = "";
	
}



/**************************************************************************************************
*  함   수  명				: fcd_GetSysdate
*  설       명				: System 날짜 및 시간을 조회한다.
*  param1) sFormat			: 리턴 형식
*  return Type				: String
*  return 내용				: 해당 날짜 및 시간
**************************************************************************************************/
function fcd_GetSysdate(sFormat) {

	var sServiceName = "common-service:searchSysdate";       // 서버에 요청할 Sevice 명
	var sInDataSet   = "";                                   // Input DataSet 명칭
	var sOutDataSet  = "gdsSysdate=dsOutSysdate";            // Output DataSet 명칭

	fcd_AddParam("CUSTOM", sFormat);

	http.sync = true;
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "", "N");
	http.sync = false;
	
	var sReturn = "";
	switch ( sFormat ) 
	{
		case "YYYYMMDDHH24MISS" : 
		     sReturn = gdsSysdate.GEtColumn(0, "DATETIME");
		     break;
		case "YYYYMMDD"         : 
		     sReturn = gdsSysdate.GEtColumn(0, "YYYYMMDD");
		     break;
		case "HH24MISS"         : 
		     sReturn = gdsSysdate.GEtColumn(0, "TIME");
		     break;
		case "YYYY"             : 
		     sReturn = gdsSysdate.GEtColumn(0, "YEAR");
		     break;
		case "MM"               : 
		     sReturn = gdsSysdate.GEtColumn(0, "MONTH");
		     break;
		case "DD"               : 
		     sReturn = gdsSysdate.GEtColumn(0, "DAY");
		     break;
		case "HH24"             : 
		     sReturn = gdsSysdate.GEtColumn(0, "HOUR");
		     break;
		case "MI"               : 
		     sReturn = gdsSysdate.GEtColumn(0, "MINUTE");
		     break;
		case "SS"               : 
		     sReturn = gdsSysdate.GEtColumn(0, "SECOND");
		     break;
		default 			    :
		     sReturn = gdsSysdate.GEtColumn(0, "CUSTOM");
			 break;
	}

	return sReturn;
}


/**************************************************************************************************
*  함   수  명				: fcd_SetStndYear
*  설       명				: 기준년도 설정
*  param1) oDataset			: 작업대상 Dataset Object
*  param2) nStartYear       : 시작년
*  param3) nLastYear     	: 마지막년
*  return Type				: 해당 없음
*  return 내용				: 해당 없음
**************************************************************************************************/
function fcd_SetStndYear(oDataset, nStartYear, nLastYear)
{
    if ( nLastYear == null ) nLastYear = 2000;

	oDataset.Clear();
	oDataset.AddColumn("STND_YEAR");
	
	var nRow = 0;
	for ( var i = nStartYear; i >= nLastYear; i-- ) {
	    oDataset.AddRow();
	    oDataset.SetColumn(nRow, "STND_YEAR", i);
	    nRow++;
	}
}


/**************************************************************************************************
*  함   수  명				: fcd_AddCodeRow
*  설       명				: 새로운 Popup 창을 열어 코드 선택 화면을 Display
*  param1) oDataset			: 작업대상 Dataset Object
*  param2) sCodeNameValue   : 해당 코드명
*  param3) sCodeIDValue  	: 해당 코드값
*  param4) oCombo   		: Bind 될 콤보 object
*  param4) bFirst   		: 추가될 코드값의 위치(true : 처음)
*  return Type				: 해당 없음
*  return 내용				: 해당 없음
**************************************************************************************************/
function fcd_AddCodeRow(oDataset, sCodeNameValue, sCodeIDValue, oCombo, bFirst) {
    var nRow = 0;
    
	if ( !bFirst ) {
		oDataset.InsertRow(nRow);
	} else {
	    nRow = oDataset.GetRowCount();
		oDataset.AddRow();
    }
    
	oDataset.SetColumn(nRow, "CD_NM", sCodeNameValue);
	oDataset.SetColumn(nRow, "CD"   , sCodeIDValue  );

    if ( oCombo != null ) 
		oCombo.index = nRow;
}


/**************************************************************************************************
*  함   수  명				: fce_GetSelectFile
*  설       명				: 선택한 파일명을 취한다.
*  return Type				: Stirng
*  return 내용				: 파일명
**************************************************************************************************/
function fcd_GetSelectFile(sType, sFileName) {
	var sReturn;
			
	if ( !IsValid(object("divBtnLst.flImage")) ){
		Create("file", "divBtnLst.flImage");
		Create("fileDialog","divBtnLst.fldImage");
	}
	
	if ( sType == null ) sType = "Open";
	divBtnLst.fldImage.Type     = sType;
	divBtnLst.fldImage.FilePath = getReg("LastDir");
	divBtnLst.fldImage.FileName = sFileName;
	
	if ( divBtnLst.fldImage.open() ) {
		divBtnLst.flImage.FileName = divBtnLst.fldImage.FilePath + "\\" + divBtnLst.fldImage.FileName;
		sReturn = Array(2);
		sReturn[0] = divBtnLst.fldImage.FilePath;
		sReturn[1] = divBtnLst.fldImage.FileName;
	} else {
		sReturn = null;
	}

	return sReturn;
}

/**************************************************************************************************
*  함   수  명				: fce_GetDayNo
*  설       명				: 
*  param1) sRacerPerioNo	: 
*  param2) sDatasetName		: Dataset
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetDayNo(sRacerPerioNo, sDatasetName)
{
	var sServiceName = "common_06500000-service:searchDayNo";
    var sInDataSet   = "";
    var sOutDataSet  = sDatasetName + "=dsDayNo ";
    
	fcd_AddParam(Trim("RACER_PERIO_NO"), sRacerPerioNo);

	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
}

/**************************************************************************************************
*  함   수  명				: fce_GetRound
*  설       명				: 
*  param1) sRacerPerioNo	: 
*  param2) sDatasetName		: Dataset
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetRound(sRacerPerioNo, sDayNo, sDatasetName)
{
	var sServiceName = "common_06500000-service:searchRound";
    var sInDataSet   = "";
    var sOutDataSet  = sDatasetName + "=dsRound ";
    
	fcd_AddParam(Trim("RACER_PERIO_NO"), sRacerPerioNo);
	fcd_AddParam(Trim("DAY_NO        "), sDayNo);

	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
}

/**************************************************************************************************
*  함   수  명				: fce_GetTeam
*  설       명				: 
*  param1) sRacerPerioNo	: 
*  param2) sDatasetName		: Dataset
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetTeam(sRacerPerioNo, sDayNo, sRound, sDatasetName)
{
	var sServiceName = "common_06500000-service:searchTeam";
    var sInDataSet   = "";
    var sOutDataSet  = sDatasetName + "=dsTeam ";
    
	fcd_AddParam(Trim("RACER_PERIO_NO"), sRacerPerioNo);
	fcd_AddParam(Trim("DAY_NO        "), sDayNo);
	fcd_AddParam(Trim("ROUND         "), sRound);

	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
}

function fcd_ExecApproval(job, seq, reportId, subJect, fileObj, conObj, fileGubun) {

	var OSVersion;
    OSVersion = trim(GetDeviceInfo("OSVERSION"));
    var sDocType        = "내부";
    
	//timeStamp  = "CANBOA"+DateTime(2005,04,04,15,22,3);
	timeStamp  = "CANBOA"+getDate()+time();
	FileName   = timeStamp+job;
    FilePath   = AliasToRealPath("%UserApp%");
    if(OSVersion == "Windows NT 6.0" || OSVersion == "Windows NT 6.1") {
        FilePath    = replace(FilePath, "LocalLow", "Local\\Temp");
    }else{
        FilePath    = replace(FilePath, "Compatible LocalLow", "Temp");
    }

    FilePath    = FilePath+timeStamp+"\\";
    AttFilePath = FilePath+"Att\\";
    
    //디렉토리 유무 체크해서 없으면 생성해준다.(공통스크립트 호출(strPath))
    fnc_IsDirectory(AttFilePath);
    //===============================================================3.첨부파일 생성
    if(!fnc_IsNull(fileObj)) {   //데이터셋 ID
        // Null 아니면 첨부파일 생성하는 로직을 수행한다.
        if(fileGubun == "PDF") {
            FileName = FileName+".pdf";
            fileObj.exportFileName = AliasToRealPath(AttFilePath+FileName);
            fileObj.ExportFile("PDF");
        }else if(fileGubun == "HWP") {
            FileName = FileName+".hwp";
            fileObj.exportFileName = AliasToRealPath(AttFilePath+FileName);
            fileObj.ExportFile("HWP");
        }else if(fileGubun == "MULTIXLS") {
			FileName = FileName+".xls";
            fileObj.ExportType		        = "Excel";
            fileObj.ExportFileName	        = AliasToRealPath(AttFilePath+FileName);
            fileObj.MakeEmptyFileWhenNotExist = true;
            fileObj.Export(true, false);                              // 첫번째 progress, 두번째 excel
            fileObj.Save();
            fileObj.Close();
            fileObj = null;
        }else{
			FileName = FileName+".xls";
            ExportObject1 = CreateExportObject();
            ExportObject1.ExportType		        = "Excel";
            ExportObject1.ExportFileName	        = AliasToRealPath(AttFilePath+FileName);
            ExportObject1.MakeEmptyFileWhenNotExist = true;
            ExportObject1.AddExportGrid("sheet1" + "!" + "A1", fileObj, true, false);
            ExportObject1.Export(true, false);                              // 첫번째 progress, 두번째 excel
            ExportObject1.Save();
            ExportObject1.CloseWorkbook();

            ExportObject1.Close();
            ExportObject1 = null;
        }

    }else{
        FileName = "";
    }
    //============================================================================

    //==============================================================4.html파일 생성
    if(!fnc_IsNull(conObj)) {   //내용 오브젝트 ID
        // Null 아니면 넘어온 객체의 내용으로 Html파일을 생성하는 로직 수행.
        fnc_CreateHtmlFile(FilePath+timeStamp+".html", conObj.text);

    }else{
        // Null 이면 문서제목과 동일하게 Html파일 생성하는 로직 수행
        fnc_CreateHtmlFile(FilePath+timeStamp+".html", subJect);
    }
    //=============================================================================

    //==============================================================5.송신URL 호출
    //테스트 서버 : http://140.100.100.24:8088/UFWeb/public/signContact.jsp
    //실운영 서버 : http://jupiter.sosfo.or.kr/UFWeb/sign/hwpsign/ActiveMis/signContact.jsp
    //ExecBrowser("http://140.100.100.24:8088/UFWeb/public/signContact.jsp?GUBUNID="+varArray[0]+"&BIZ=cy&JOB="+job+"&CREATTYPE=내부&USER_ID="+reportId+"&SUBJECT="+subJect+"&ATTNAME="+FileName+"&SEQ="+seq);
    //2009.05.14 기안자 사번을 조성문대리 ID로 박아둠(테스트를 위해서)
    //ExecBrowser("http://jupiter.kspo.or.kr/UFWeb/public/signContact.jsp?GUBUNID="+timeStamp+"&BIZ=cy&JOB="+job+"&CREATTYPE="+sDocType+"&USER_ID="+reportId+"&SUBJECT="+subJect+"&ATTNAME="+FileName+"&SEQ="+seq);
    //===================================================
    
    //////////////////////////////////////// from 경륜운영관리......

    var sIfData  = "";
    //sIfData += "target="     + urlEncode("giancall") ;
	//sIfData += "&SSOTOK="    + urlEncode(ssoToken) ;
	//sIfData += "&formid="    + urlEncode("00000018i") ;
	sIfData += "&empcode="   + urlEncode("U00" + reportId) ;
	sIfData += "&deptcode="  + urlEncode(gdsUser.GetColumn(0,"TEAM_CD")) ;
	sIfData += "&miskey="    + urlEncode(varArray[0]) ; 
	sIfData += "&title="     + urlEncode(subJect) ;     //제목
	sIfData += "&fld_value=" + urlEncode("<html><body>"+ replace(conObj.text, "&nbsp;", "_nbsp_" ) +"</body></html>") ;//본문
	sIfData += "&job="       + urlEncode(job) ; 
	sIfData += "&creatType=" + urlEncode(sDocType) ; 
	//sIfData += "&creatType=" + urlEncodeUtf8(sDocType) ; 
	sIfData += "&SEQ="       + urlEncode(seq) ; 
	sIfData += "&userpswd="  + urlEncode(gdsUser.GetColumn(0,"PSWD")) ; 
	
	// 첨부파일
	if( fileObj != "" && FileName != "" )
	{
		sIfData += "&fld_attach="       + urlEncodeUtf8(AttFilePath+FileName + "|" + FileName) ; 
		
	}
    // 전자결재연동을 위한 객체생성.
	Create("WebBrowser","WebBrowser",'left="0" top="0" width="0" height="0" visible=false');
	//Create("WebBrowser","WebBrowser",'left="0" top="0" width="300" height="400" visible=true');
	
    this.WebBrowser.PageUrl = "http://canboa.kcycle.or.kr/canboa/callSign.jsp";    
    this.WebBrowser.PostData = sIfData ; 
    this.WebBrowser.HeadVal = "Content-Type: application/x-www-form-urlencoded";

    this.WebBrowser.Run();

}



/**************************************************************************************************
*  함   수  명				: fce_GetSelectFile
*  설       명				: 선택한 파일명을 취한다.
*  return Type				: Stirng
*  return 내용				: 파일명
**************************************************************************************************/
function fcd_OpenExcelListExport(pExcelList) {
    var dlgRtn = Dialog("common::comExcelList.xml", "sGridList=" + pExcelList, '440','170', true, -1, -1);
}

/**************************************************************************************************
*  함   수  명				: fce_GetSelectFile
*  설       명				: 선택한 파일명을 취한다.
*  return Type				: Stirng
*  return 내용				: 파일명
**************************************************************************************************/
function fcd_findRacer(asObjGrid, sRacerNm) {
    
    var nRow = -1;
    var asWorkObjGrid = Array(length(asObjGrid));
    if ( !fnc_IsNull(vg_FindGrid) ) {
        var nCol = -1;
        for ( var i = 0; i < length(asObjGrid); i++ ) {
            if ( asObjGrid[i] == vg_FindGrid ) {
                nCol = i;
                break;
            }
        }
    
        var i = 0;
        while(i < length(asObjGrid)) {
            asWorkObjGrid[i++] = asObjGrid[nCol++];
            if ( nCol >= length(asObjGrid) ) nCol = 0;
        }
    } else {
        asWorkObjGrid = asObjGrid;
    }
    
    for ( var i = 0; i < length(asWorkObjGrid); i++ ) {
        var objGrid = object(asWorkObjGrid[i]);
        var objDs   = object(objGrid.BindDataset);
        
        nRow = fnc_GetLikeValue(objDs, "NM_KOR", sRacerNm, vg_FindRow + 1);
        if ( nRow > -1 ) {
            objGrid.SetFocus();
            objDs.row = nRow;
            
            vg_FindGrid = asWorkObjGrid[i];
            vg_FindRow  = nRow;
            
            break;
        }
        
        if ( vg_FindRow > -1 ) vg_FindRow = -1;
    }
}


/**************************************************************************************************
*  함   수  명				: fnc_GetCurRaceInfo
*  설       명				: 해당 년도의 경주회차의 일차 조회
*  param1) sColumnName		: 컬럼명
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetCurRaceInfo(sColumnName)
{
    if ( gdsRace.GetRowCount() == 0 ) return "";
    
    var nRow = fnc_GetExistValue(gdsRace, "CURDATE", "TRUE");
    if ( nRow < 0 ) nRow = 0;
    
    return gdsRace.GetColumn(nRow, sColumnName);
}

/**************************************************************************************************
*  함   수  명				: fnc_GetCurRaceInfo
*  설       명				: 해당 년도의 경주회차의 일차 조회
*  param1) sColumnName		: 컬럼명
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetUserInfo(sColumnName)
{
    if ( gdsUser.GetRowCount() == 0 ) return "";
    
    var nRow = 0;
    
    return gdsUser.GetColumn(nRow, sColumnName);
}


/**************************************************************************************************
*  함   수  명				: fnc_GetRaceTms
*  설       명				: 해당 년도의 경주회차 조회
*  param1) sStndYear		: 해당년도
*  param2) sQurtCd		    : 해당분기
*  param3) sDatasetName		: Dataset
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetRaceTms(sStndYear, sMbrCd, sQurtCd, sDatasetName)
{
	var sServiceName = "common_race-service:searchRaceTms";
    var sInDataSet   = "";
    var sOutDataSet  = sDatasetName + "=dsOutSRaceTms ";
    
	fcd_AddParam(Trim("STND_YEAR"), sStndYear);
	fcd_AddParam(Trim("MBR_CD"   ), sMbrCd   );
	fcD_AddParam(Trim("QURT_CD"  ), sQurtCd  );

	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
}


/**************************************************************************************************
*  함   수  명				: fnc_GetRaceDayOrd
*  설       명				: 해당 년도의 경주회차의 일차 조회
*  param1) sStndYear		: 해당년도
*  param2) sRaceTms		    : 회차
*  param3) sDatasetName		: Dataset
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetRaceDayOrd(sStndYear, sMbrCd, sRaceTms, sDatasetName)
{
	var sServiceName = "common_race-service:searchRaceDayOrd";
    var sInDataSet   = "";
    var sOutDataSet  = sDatasetName + "=dsOutSRaceDayOrd ";
    
	fcd_AddParam(Trim("STND_YEAR"), sStndYear);
	fcd_AddParam(Trim("MBR_CD"   ), sMbrCd   );
	fcd_AddParam(Trim("TMS"      ), sRaceTms );

	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
}

/**************************************************************************************************
*  함   수  명				: fnc_GetRace
*  설       명				: 해당 년도의 경주회차의 일차 조회
*  param1) sStndYear		: 해당년도
*  param2) sRaceTms		    : 회차
*  param3) sRaceDayOrd		: 일차
*  param4) sDatasetName		: Dataset
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetRace(sStndYear, sMbrCd, sRaceTms, sRaceDayOrd, sDatasetName)
{
	var sServiceName = "common_race-service:searchRace";
    var sInDataSet   = "";
    var sOutDataSet  = sDatasetName + "=dsOutSRace ";
    
	fcd_AddParam(Trim("STND_YEAR"), sStndYear   );
	fcd_AddParam(Trim("MBR_CD"   ), sMbrCd      );
	fcd_AddParam(Trim("TMS"      ), sRaceTms    );
	fcd_AddParam(Trim("DAY_ORD"  ), sRaceDayOrd );

	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
}

/**************************************************************************************************
*  함   수  명				: fnc_SetCurTms
*  설       명				: 해당 년도의 경주회차의 일차 조회
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetCurTms(sMbrCd)
{
	var sServiceName = "common_race-service:searchCurTms";
    var sInDataSet   = "";
    var sOutDataSet  = "gdsRace=dsOutCurTms ";
    
    fcd_AddParam(Trim("MBR_CD"), GBL_MBRCD);
    
    http.sync = true;
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
    http.sync = false;
}


/*
 * 원본 Dataset의 선택행 값을 return Dataset에 카피한다.
 *
 * param dsSrc - 원본 Dataset 객체
 * param rowIdxs - 선택행 index, 여러개일 경우 '^'문자로 구분한다. (0^1^2^3^4 ...) : Nullable
 * param dsSrc - return Dataset : Nullable
 *
 * return void
 */
function fcd_SetReturnDs(dsSrc, rowIdx, dsReturn) {

    var nRnum;
    var arrRowIdx;

    if(rowIdx==null) {
        rowIdx = dsSrc.GetCurrow();
    }

    if(dsReturn==null) {
        dsReturn = G_ds_return;
    }
    arrRowIdx = split(rowIdx, "^");

    dsReturn.Copy(dsSrc);
    dsReturn.DeleteAll();

    for(var i=0; i<length(arrRowIdx); i++) {
        nRnum = dsReturn.AppendRow();
        dsReturn.CopyRow(nRnum, dsSrc, arrRowIdx[i]);
    }
}



/**************************************************************************************************
*  함   수  명				: fcd_SendSms
*  설       명				: SMS연동
*  return Type				: 
*  return 내용				: 
*  파라메터                 

			strSendPhoneNumber = "";		// 송신자 번호
			strSendUserId = "";			// 송신자 사번			(예:05073)
			strSendUserName = "";		// 송신자 성명(예:홍길동)	
			strReceivePhoneNumber = "";	// 수신자 번호, 여러명인경우 ; 로 구분 (예:01020717775)
			strReceiveName = "";			// 수신자명, 여러명인경우 ; 로 구분
			strSendSubject = "";			// 전송할 문자. (80Bytes이하)
**************************************************************************************************/
function fcd_SendSms(strSendPhoneNumber, strSendUserId, strSendUserName, 
                     strReceivePhoneNumber,strReceiveName, strSendSubject,
                     strReceiveId, strSendContent) {	
     /*
	var sCompID;
    var obj;
    
    strSendPhoneNumber = replace(strSendPhoneNumber,"\"","");
    strSendUserId = replace(strSendUserId,"\"","");
    strSendUserName = replace(strSendUserName,"\"","");
    strReceivePhoneNumber = replace(strReceivePhoneNumber,"\"","");
    strReceiveName = replace(strReceiveName,"\"","");
    strSendSubject = replace(strSendSubject,"\"","");
    strReceiveId = replace(strReceiveId,"\"","");
    strSendContent = replace(strSendContent,"\"","");    
    
    sCompID = "AxMsie01";
    
    Destroy(sCompID);
    
 
    Create("AxMsie", sCompID);
    
    obj = object(sCompID);
    var param = "";
	param = "?strSendPhoneNumber="+replace(strSendPhoneNumber,"-","");
	param += "&strSendUserId="+strSendUserId;
	param += "&strSendUserName="+strSendUserName;
	param += "&strReceivePhoneNumber="+replace(strReceivePhoneNumber,"-","");
	param += "&strReceiveName="+strReceiveName;
	param += "&strSendSubject="+strSendSubject;
	

    //obj.Navigate(GBL_SERVER_URL+"smsSend.jsp"+param);

   //trace(GBL_SERVER_URL+"smsSend.jsp"+param);
  
   var aRecvPh = split(strReceivePhoneNumber,",","webstyle");
   var aRecvNm = split(strReceiveName,",","webstyle"); 
   var aRecvId = split(strReceiveId,",","webstyle"); 
	  
   for(var i = 0; i < aRecvPh.length;i++){
   
	  fcd_SetAlarmHist(	 "002" 
					, aRecvId[i]
					, GBL_MENUID
					, aRecvPh[i] 		//수신자 번호
					, strSendSubject
					, strSendContent
					, strSendPhoneNumber
					, strSendUserName
					, aRecvNm[i]);
   
   }
   */
   
    fcd_SetAlarmHist(	 "002" 
					, strReceiveId
					, GBL_MENUID
					, strReceivePhoneNumber 		//수신자 번호
					, strSendSubject
					, strSendContent
					, strSendPhoneNumber
					, strSendUserName
					, strReceiveName);
   
   
}




/**************************************************************************************************
*  함   수  명				: fcd_SetAlarmHist
*  설       명				: 알람이력 저장 
*  return Type				: 
*  return 내용				: 
**************************************************************************************************/
function fcd_SetAlarmHist( sAlarmGbn, sRecvId, sMnId, sHpNo, sTitle, sCntnt, sSendPhNo, sSendUserNm, sRecvUserNm) {	


	var sServiceName = "common-service:saveAlarmHist";
    var sInDataSet   = "";
    var sOutDataSet  = "";	
	
	fcd_AddParam("ALARM_GBN", sAlarmGbn);
	fcd_AddParam("RECV_ID",sRecvId);
	fcd_AddParam("MN_ID", sMnId);
	fcd_AddParam("HP_NO", sHpNo);
	fcd_AddParam("TITLE", sTitle);
	fcd_AddParam("CNTNT", sCntnt);
	fcd_AddParam("SEND_HP_NO", sSendPhNo);
	fcd_AddParam("SEND_USER_NM", sSendUserNm);
	fcd_AddParam("RECV_USER_NM", sRecvUserNm);


	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
}


/**************************************************************************************************
*  함   수  명				: fcd_SavePersonalAccess
*  설       명				: 개인정보파일의 접근기록을 저장
*  param1) FormID           : 호출한 화면 ID
*  param2) AccessType       : 접근 구분(S:조회, U:저장)
*  param3) Action Name      : 요청한 서비스(Action)
*  param4) Parameter        : 서비스 요청시의 파라미터
*  return Type				: 해당 없음
*  return 내용				: 해당 없음
**************************************************************************************************/
function fcd_SavePersonalAccess(sFormId, sAccessType, sAction, sParam)
{
trace("fcd_SavePersonalAccess::");
	if (fnc_isNull(sFormId)) return;
	
	var sServiceName = "common_login-service:SaveUserTrace";         // 서버에 요청할 Sevice 명
	var sInDataSet   = "";     // Input DataSet 명칭
	var sOutDataSet  = "";                                  // Output DataSet 명칭
	
	fcd_AddParam("JOB_TYPE", 	"P");   
	fcd_AddParam("LOG_SEQ", 	gdsUser.GetColumn(0, "LOG_SEQ"));   
	fcd_AddParam("FORM_ID", 	sFormId);   
	fcd_AddParam("ACCESS_TYPE", sAccessType);   
	fcd_AddParam("ACTION", 		sAction);
	fcd_AddParam("PARAM", 		sParam);
	   
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
} 


/**************************************************************************************************
*  함   수  명				: fcd_SavePersonalMnAccess
*  설       명				: 개인정보파일의 접근기록을 저장
*  param1) FormID           : 호출한 화면 ID
*  param2) AccessType       : 접근 구분(S:조회, U:저장)
*  param3) Action Name      : 요청한 서비스(Action)
*  param4) Parameter        : 서비스 요청시의 파라미터
*  return Type				: 해당 없음
*  return 내용				: 해당 없음
**************************************************************************************************/
function fcd_SavePersonalMnAccess(sFormId, sAccessType, sAction, sParam)
{
	if (fnc_isNull(sFormId)) return;
	
	var sServiceName = "common_login-service:SavePersonalMnAccess";		// 서버에 요청할 Sevice 명
	var sInDataSet   = "";     											// Input DataSet 명칭
	var sOutDataSet  = "";                                  			// Output DataSet 명칭
	
	var sUserID	= gdsUser.GetColumn(0,"USER_ID");
	var sUserIp = ext_GetIPAddress();
			 
	fcd_AddParam("JOB_TYPE", 		"P");    
	fcd_AddParam("LOG_SEQ", 		gdsUser.GetColumn(0, "LOG_SEQ"));   
	fcd_AddParam("FORM_ID", 		sFormId);   
	fcd_AddParam("ACCESS_TYPE", 	sAccessType);   
	fcd_AddParam("ACTION", 			sAction);
	fcd_AddParam("PARAM", 			sParam);
	fcd_AddParam("USER_IP", 		sUserIp);
	fcd_AddParam("USER_ID", 		sUserId);
	
	fcd_SendPersonalTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
}
