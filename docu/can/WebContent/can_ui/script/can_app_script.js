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
/*
function fcd_SendTransaction(SvcName, InDataset, OutDataset, CallbackName, bProgress) {

    var sService = split(SvcName, ":");
    var JspSvc   = "DEFAULT_SERVER::handle.do?";
    var Action   = "ServiceName=" + sService[0] + "&" + sService[1] + "=1";
    var SvcParam = fcd_GetParam();
	
	if ( fnc_IsNull(bProgress) ) {
		fnc_ProgressPop();
	}

	InDataset = InDataset + " gdsUser=gdsUser";

    transaction(SvcName, JspSvc + Action, InDataset, OutDataset, SvcParam, CallbackName);
}
*/
function fcd_SendTransaction(SvcName, InDataset, OutDataset, CallbackName, bProgress, sAccessType) {

    var sService = split(SvcName, ":");
    var JspSvc   = "DEFAULT_SERVER::handle.do?";
    var Action   = "ServiceName=" + sService[0] + "&" + sService[1] + "=1";
    var SvcParam = fcd_GetParam();

		if ( fnc_IsNull(bProgress) ) {
		fnc_ProgressPop();
	  }

	http.Sync = true;
	InDataset = InDataset + " gdsUser=gdsUser";
    transaction(SvcName, JspSvc + Action, InDataset, OutDataset, SvcParam, CallbackName);
    
    if ( sAccessType != null && sAccessType != '' ) 
    {
    	fcd_SavePersonalAccess(sFormId, sAccessType, sService[0] + "&" + sService[1] + "=1", SvcParam);
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
*  함   수  명				: fcd_GetYear
*  설       명				: 기준년도 설정
*  return Type				: 해당 없음
*  return 내용				: 해당 없음
**************************************************************************************************/
function fcd_GetYear()
{

	var sServiceName = "common-service:searchYear";
    var sInDataSet   = "";
    var sOutDataSet  = "dsYear=dsYear ";
    
    http.sync = true;
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
    http.sync = false;    
}

/**************************************************************************************************
*  함   수  명				: fcd_GetRound
*  설       명				: 해당 년도의 회차조회  
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetRound(sYear)
{

	var sServiceName = "common-service:searchRound";
    var sInDataSet   = "";
    var sOutDataSet  = "dsRound=dsRound ";
    
    if ( sYear == null )return;
	fcd_AddParam(Trim("YEAR"), sYear);
    
    http.sync = true;
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
    http.sync = false;
}

/**************************************************************************************************
*  함   수  명				: fcd_GetRaceNo
*  설       명				: 해당 년도의 회차조회  
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetRaceNo(sYear, sRound)
{

	var sServiceName = "common-service:searchRaceNo";
    var sInDataSet   = "";
    var sOutDataSet  = "dsRaceNo=dsRaceNo ";
    

    if ( sYear == null )return;
	fcd_AddParam(Trim("YEAR"), sYear);
	fcd_AddParam(Trim("ROUND"), sRound);
    
    http.sync = true;
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
    http.sync = false;
}
/**************************************************************************************************
*  함   수  명				: fcd_GetRaceNo
*  설       명				: 해당 년도의 회차조회 (선두유도원용) 
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetRaceNo2(sYear, sRound)
{

	var sServiceName = "common-service:searchRaceNo2";
    var sInDataSet   = "";
    var sOutDataSet  = "dsRaceNo=dsRaceNo ";
    

    if ( sYear == null )return;
	fcd_AddParam(Trim("YEAR"), sYear);
	fcd_AddParam(Trim("ROUND"), sRound);
    
    http.sync = true;
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
    http.sync = false;
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
	if (fnc_isNull(sFormId)) return;
	
	var sServiceName = "common_login-service:savePersonalAccess";         // 서버에 요청할 Sevice 명
	var sInDataSet   = "";     // Input DataSet 명칭
	var sOutDataSet  = "";                                  // Output DataSet 명칭

	//fcd_AddParam("JOB_TYPE", 	"P");   
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