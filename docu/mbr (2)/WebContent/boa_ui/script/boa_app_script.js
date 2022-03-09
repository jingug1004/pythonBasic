/*================================================================================================/
 * PROGRAM-ID                           template_app_script 
 * DESCRIPTION   :  해당 업무의 공통 트랜잭션처리                                            
 * CREATE DATE   :  2007. 10. 16                                                            
 * UPDATE DATE   :  2007. 10. 16                                                           
 * PROGRAMMER    :                                                                          
=================================================================================================*/
#include "lib::boa_race_script.js";
#include "lib::boa_global_script.js";

var vg_FindGrid  = "";
var vg_FindRow   = -1;
var vg_FindValue = "";
/**************************************************************************************************
*  함   수  명				: fce_SendTransaction
*  설       명				: Transaction 수행
*  param1) SvcName			: 서비스명
*  param2) ActionName		: 액션명
*  param3) InDataset		: 입력 Dataset
*  param4) OutDataset		: 출력 Dataset
*  param5) CallbackName		: Callback 함수명
*  return Type				: boolean
*  return 내용				: true/false
**************************************************************************************************/
function fce_SendTransaction(SvcName, InDataset, OutDataset, CallbackName, bProgress, sAccessType) {

    var sService = split(SvcName, ":");
    //var JspSvc   = "DEFAULT_SERVER::handle.do?";
    
    var JspSvc = "";
    
    if(global.GBL_PERSONALMN_CHK == "Y") {
		JspSvc   = "SSL_SERVER::handle.do?";
	}else{
		JspSvc   = "DEFAULT_SERVER::handle.do?";
	}
	
    var Action   = "ServiceName=" + sService[0] + "&" + sService[1] + "=1&ActionName="+sService[1];
    var SvcParam = fce_GetParam();
	
	if ( fnc_IsNull(bProgress) ) {
		fnc_ProgressPop();
	}

	InDataset = InDataset + " gdsUser=gdsUser";
    transaction(SvcName, JspSvc + Action, InDataset, OutDataset, SvcParam, CallbackName);
	//Trace("fce_SendTransaction:"+JspSvc+Action);   
	//Trace("		SvcParam:"+SvcParam);   
	//Trace("		InDataset["+InDataset+"], OutDataset["+OutDataset+"]");   
	//trace(http.recvContents);
    //http.Sync = true;
    /*
    if (!fnc_IsNull(sAccessType)) {
		var sAction = sService[0] + "[" + sService[1] + "]";
		fce_SavePersonalAccess(sFormId, sAccessType, sAction, SvcParam);
	}
	*/
	
	if(global.GBL_PERSONALMN_CHK == "Y") {
		var sAction = sService[0] + "[" + sService[1] + "]";
		if(sService[0] != "common-service"){
			fce_SavePersonalMnAccess(sFormId, sAccessType, sAction, SvcParam);
		}	
	}
	//http.Sync = false;
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
 function fce_SendPersonalTransaction(SvcName, InDataset, OutDataset, CallbackName, bProgress, sAccessType) {
    
    var sService = split(SvcName, ":");
    var JspSvc   = "DEFAULT_SERVER::handle.do?";
    var Action   = "ServiceName=" + sService[0] + "&" + sService[1] + "=1&ActionName="+sService[1]; // 접속정보 로그인용으로 추가함
    var SvcParam = fce_GetParam();
    	 
	if ( bProgress ) {
		fnc_ProgressPop(); 
	} 
	
	//http.Sync = true;
	InDataset = InDataset + " gdsUser=gdsUser";
    transaction(SvcName, JspSvc + Action, InDataset, OutDataset, SvcParam, CallbackName);
	//http.Sync = false;
}

/**************************************************************************************************
*  함   수  명				: fce_GetComboDs
*  설       명				: 복수개의 공통 코드를 가져와 outDataset에 넣기 위한 Transaction 수행
*  param1) oDataSet         : 공통 코드 그룹아이디 목록을 가지고 있는 DataSet (dsInComCd)
*  return Type				: 해당 없음
*  return 내용				: 해당 없음
**************************************************************************************************/
function fce_GetComboDs(oDataSet) {

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
	//http.Sync = true;		// 동기 처리시 오류 발생함(2014.12.24)
	fce_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
	//http.Sync = false;
}



/**************************************************************************************************
*  함   수  명				: fce_AddParam
*  설       명				: Transaction에 사용될 Parameter추가
*  param1) ParamName		: Parameter 명
*  param2) ParamValue		: Parameter 값
*  return Type				: 해당 없음
*  return 내용				: 해당 없음
**************************************************************************************************/
var gboa_SvcParam = Array(-1);   // Transaction에서 사용되는 Parameter

function fce_AddParam(ParamName, ParamValue) {
    var aParam = Array(2);
    aParam[0] = ParamName;
    aParam[1] = ParamValue;

    gboa_SvcParam[length(gboa_SvcParam)] = aParam;
}



/**************************************************************************************************
*  함   수  명				: fce_GetParam
*  설       명				: Transaction에 사용할 Parameter를 추가
*  return Type				: String
*  return 내용				: Transaction의 Parameter
**************************************************************************************************/
function fce_GetParam() {
	var sReturn = "";
	for ( i = 0; i < length(gboa_SvcParam); i++ ) {
		sReturn += gboa_SvcParam[i][0] + "=" + quote(gboa_SvcParam[i][1]) + " ";
	}

	gboa_SvcParam = Array(-1);
    return sReturn;
}



/**************************************************************************************************
*  함   수  명				: fce_HelpDialoge
*  설       명				: 새로운 Popup 창을 열어 종류에 따른 화면 Display
*  param1) sSearchKind		: Dialog 종류 (우편번호, 공통 코드, 부서트리)
*  param2) ParamValue		: Dialog창에 나타날 Form Variable의 초기값
*  param3) sCommonGubn		: 
*  param4) oFocusControl	: 
*  return Type				: Boolean
*  return 내용				: Dialog가 정상적으로 동작했는지 여부 
**************************************************************************************************/
function fce_HelpDialoge(sSearchKind, sInputValue, sCommonGubn, oFocusControl) {

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
*  함   수  명				: fce_MultiRowDisplay
*  설       명				: 새로운 Popup 창을 열어 코드 선택 화면을 Display
*  param1) oForm			: Form Object
*  param2) vREVOBJ			: 결과값을 받을 Text Object명
*  param3) sGRDTIT			: 그리드 제목
*  param4) sCUROBJ			: 현재 LOST FOCUS 오브젝트
*  param5) oDataset			: DataSet
*  return Type				: 해당 없음
*  return 내용				: 해당 없음
**************************************************************************************************/
function fce_MultiRowDisplay(oForm, vREVOBJ, sGRDTIT, sCUROBJ, oDataset) {

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
			return false;
		}     
	}
	
	for (i=0; i<Length( vREVOBJ ); i++) {
		if (length(trim(vREVOBJ[i]))>0) object(vREVOBJ[i]) = gdsSelectItem.GetColumn(gdsSelectItem.Row , i);
		vREVOBJ[i] = "";        //결과값을 받을 Text Object명
	}
	sCUROBJ  = "";
	return true;
}



/**************************************************************************************************
*  함   수  명				: fce_GetSysdate
*  설       명				: System 날짜 및 시간을 조회한다.
*  param1) sFormat			: 리턴 형식
*  return Type				: String
*  return 내용				: 해당 날짜 및 시간
**************************************************************************************************/
function fce_GetSysdate(sFormat) {

	var sServiceName = "common-service:searchSysdate";             // 서버에 요청할 Sevice 명
	var sInDataSet   = "";                                   // Input DataSet 명칭
	var sOutDataSet  = "gdsSysdate=dsOutSysdate";              // Output DataSet 명칭

	fce_AddParam("CUSTOM", sFormat);

	http.sync = true;
	fce_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "", "N");
	http.sync = false;
	
//	fnc_ProgressClose();

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
*  함   수  명				: fce_SetStndYear
*  설       명				: 기준년도 설정
*  param1) oDataset			: 작업대상 Dataset Object
*  param2) nStartYear       : 시작년
*  param3) nLastYear     	: 마지막년
*  return Type				: 해당 없음
*  return 내용				: 해당 없음
**************************************************************************************************/
function fce_SetStndYear(oDataset, nStartYear, nLastYear)
{
    if ( nLastYear == null ) nLastYear = 2002;

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
*  함   수  명				: fce_AddCodeRow
*  설       명				: 새로운 Popup 창을 열어 코드 선택 화면을 Display
*  param1) oDataset			: 작업대상 Dataset Object
*  param2) sCodeNameValue   : 해당 코드명
*  param3) sCodeIDValue  	: 해당 코드값
*  param4) oCombo   		: Bind 될 콤보 object
*  param4) bFirst   		: 추가될 코드값의 위치(true : 처음)
*  return Type				: 해당 없음
*  return 내용				: 해당 없음
**************************************************************************************************/
function fce_AddCodeRow(oDataset, sCodeNameValue, sCodeIDValue, oCombo, bFirst) {
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
function fce_GetSelectFile(sType, sFileName) {
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
*  함   수  명				: fce_GetSelectFile
*  설       명				: 선택한 파일명을 취한다.
*  return Type				: Stirng
*  return 내용				: 파일명
**************************************************************************************************/
function fce_OpenExcelListExport(sExcelList) {
    var dlgRtn = Dialog("common::comExcelList.xml", "sGridList=" + sExcelList, '440','170', true, -1, -1);
}

/**************************************************************************************************
*  함   수  명				: fce_GetSelectFile
*  설       명				: 선택한 파일명을 취한다.
*  return Type				: Stirng
*  return 내용				: 파일명
**************************************************************************************************/
function fce_findRacer(asObjGrid, sRacerNm) {
    
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
function fce_SendSms(strSendPhoneNumber, strSendUserId, strSendUserName, 
                     strReceivePhoneNumber,strReceiveName, strSendSubject,
                     strReceiveId, strSendContent, strReqTime, sFormId) {	
    
//    var called_menu_id = "E05040010";
       var called_menu_id = sFormId;

/*
    if (IsExistVariable("sFormId")) { 
		if (sFormId != null) called_menu_id = sFormId;
	}
*/

   // 일괄전송으로 변경
   fce_SetAlarmHist(	 "002" 
					, strReceiveId
					, called_menu_id
					, strReceivePhoneNumber 		//수신자 번호
					, strSendSubject
					, strSendContent
					, null
					, strSendPhoneNumber
					, strSendUserName
					, strReceiveName
					, strReqTime);     
   
}


/**************************************************************************************************
*  함   수  명				: fcd_SetAlarmHist
*  설       명				: 알람이력 저장 
*  return Type				: 
*  return 내용				: 
**************************************************************************************************/
function fce_SetAlarmHist( sAlarmGbn, sRecvId, sMnId, sHpNo, sTitle, sCntnt, sUrl, 
                           sSendPhNo, sSendUserNm, sRecvUserNm, sReqTime) {	
                           
	var sServiceName = "e01010180-service:saveAlarmHist";
    var sInDataSet   = "";
    var sOutDataSet  = "";	
	
	fce_AddParam("ALARM_GBN", sAlarmGbn);
	fce_AddParam("RECV_ID",sRecvId);
	fce_AddParam("MN_ID", sMnId);
	fce_AddParam("HP_NO", sHpNo);
	fce_AddParam("TITLE", sTitle); 
	fce_AddParam("CNTNT", sCntnt);
	fce_AddParam("URL",  sUrl);
	fce_AddParam("SEND_HP_NO", sSendPhNo);
	fce_AddParam("SEND_USER_NM", sSendUserNm);
	fce_AddParam("RECV_USER_NM", sRecvUserNm);
	fce_AddParam("REQ_TIME", sReqTime);

	fce_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack");
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
function fce_SavePersonalAccess(sFormId, sAccessType, sAction, sParam)
{
	if (fnc_isNull(sFormId)) return;
	
	var sServiceName = "common_login-service:SaveUserTrace";         // 서버에 요청할 Sevice 명
	var sInDataSet   = "";     // Input DataSet 명칭
	var sOutDataSet  = "";                                  // Output DataSet 명칭
	
	fce_AddParam("JOB_TYPE", 	"P");   
	fce_AddParam("LOG_SEQ", 	gdsUser.GetColumn(0, "LOG_SEQ"));   
	fce_AddParam("FORM_ID", 	sFormId);   
	fce_AddParam("ACCESS_TYPE", sAccessType);   
	fce_AddParam("ACTION", 		sAction);
	fce_AddParam("PARAM", 		sParam);
	   
	fce_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
} 

/**************************************************************************************************
*  함   수  명				: fce_SavePersonalMnAccess
*  설       명				: 개인정보파일의 접근기록을 저장
*  param1) FormID           : 호출한 화면 ID
*  param2) AccessType       : 접근 구분(S:조회, U:저장)
*  param3) Action Name      : 요청한 서비스(Action)
*  param4) Parameter        : 서비스 요청시의 파라미터
*  return Type				: 해당 없음
*  return 내용				: 해당 없음
**************************************************************************************************/
function fce_SavePersonalMnAccess(sFormId, sAccessType, sAction, sParam)
{
	//alert(sFormId);
	
	if (fnc_isNull(sFormId)) return;
	
	var sServiceName = "common_login-service:SavePersonalMnAccess";		// 서버에 요청할 Sevice 명
	var sInDataSet   = "";     											// Input DataSet 명칭
	var sOutDataSet  = "";                                  			// Output DataSet 명칭
	
	var sUserID	= gdsUser.GetColumn(0,"USER_ID");
	var sUserIp = ext_GetIPAddress();
			 
	fce_AddParam("JOB_TYPE", 		"P");    
	fce_AddParam("LOG_SEQ", 		gdsUser.GetColumn(0, "LOG_SEQ"));   
	fce_AddParam("FORM_ID", 		sFormId);   
	fce_AddParam("ACCESS_TYPE", 	sAccessType);   
	fce_AddParam("ACTION", 			sAction);
	fce_AddParam("PARAM", 			sParam);
	fce_AddParam("USER_IP", 		sUserIp);
	fce_AddParam("USER_ID", 		sUserId);
	
	fce_SendPersonalTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
}

