/*================================================================================================/
 * PROGRAM-ID                           template_app_script 
 * DESCRIPTION   :  해당 업무의 공통 트랜잭션처리 (경주사업관리시스템)                                           
 * CREATE DATE   :  2011. 07. 21                                                            
 * UPDATE DATE   :  2011. 07. 21                                                          
 * PROGRAMMER    :                                                                          
=================================================================================================*/
#include "lib::rbm_common_script.js";
#include "lib::rbm_message_script.js";

/*
var sComCallType = "";		//호출구분 1-button 2-edit 
var sComOwnerType = "";		//1 : edit 2 : Grid
 
var objDiv = NULL;

var	sComCodeType 	= "";
var	sComCodeGubun	= "";
var	sComVal1 = "";
var	sComVal2 = "";
var	sComVal3 = "";
var	sComVal4 = "";
var	sComVal5 = "";
*/

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
    var Action   = "ServiceName=" + sService[0] + "&" + sService[1] + "=1&ActionName="+sService[1]; // 접속정보 로그인용으로 추가함
    var SvcParam = fcd_GetParam();
	 
	if ( bProgress ) {
		fnc_ProgressPop(); 
	} 
//Trace("fcd_SendTransaction::JSESSIONID="+JSESSIONID);
//Trace(JspSvc+Action+":"+SvcParam);	
	
	http.Sync = true;
	if (GBL_DUP_LOGIN_CHK != "Y") InDataset = InDataset + " gdsUser=gdsUser";	// 향후 보안상의 이유로 삭제 요망(신재선)
    transaction(SvcName, JspSvc + Action, InDataset, OutDataset, SvcParam, CallbackName);
    
    if (fnc_IsNotNull(sAccessType)) {
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
	if (GBL_DUP_LOGIN_CHK != "Y") InDataset = InDataset + " gdsUser=gdsUser";	// 향후 보안상의 이유로 삭제 요망(신재선)
    transaction(SvcName, JspSvc + Action, InDataset, OutDataset, SvcParam, CallbackName);
	http.Sync = false;
}

/**************************************************************************************************
*  함   수  명				: fcd_SSLTransaction
*  설       명				: Transaction 수행
*  param1) SvcName			: 서비스명
*  param2) ActionName		: 액션명
*  param3) InDataset		: 입력 Dataset
*  param4) OutDataset		: 출력 Dataset
*  param5) CallbackName		: Callback 함수명
*  return Type				: boolean
*  return 내용				: true/false
**************************************************************************************************/
 function fcd_SSLTransaction(SvcName, InDataset, OutDataset, CallbackName, bProgress, sAccessType) {

    var sService = split(SvcName, ":");
    var JspSvc   = "SSL_SERVER::handle.do?";
    //var Action   = "ServiceName=" + sService[0] + "&" + sService[1] + "=1";
    var Action   = "ServiceName=" + sService[0] + "&" + sService[1] + "=1&ActionName="+sService[1]; // 접속정보 로그인용으로 추가함
    var SvcParam = fcd_GetParam();
	 
	if ( bProgress ) {
		fnc_ProgressPop(); 
	} 
//Trace("fcd_SendTransaction::JSESSIONID="+JSESSIONID);
//Trace(JspSvc+Action+":"+SvcParam);	
		
	http.Sync = true;
	if (GBL_DUP_LOGIN_CHK != "Y") InDataset = InDataset + " gdsUser=gdsUser";	// 향후 보안상의 이유로 삭제 요망(신재선)
    transaction(SvcName, JspSvc + Action, InDataset, OutDataset, SvcParam, CallbackName);
    
    if (fnc_IsNotNull(sAccessType)) {
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
*  함   수  명				: fcd_EncTransaction
*  설       명				: Transaction 수행
*  param1) SvcName			: 서비스명
*  param2) ActionName		: 액션명
*  param3) InDataset		: 입력 Dataset
*  param4) OutDataset		: 출력 Dataset
*  param5) CallbackName		: Callback 함수명
*  return Type				: boolean
*  return 내용				: true/false
**************************************************************************************************/
function fcd_EncTransaction(SvcName, InDataset, OutDataset, CallbackName, bProgress, sAccessType) {

    var sService = split(SvcName, ":");
    var JspSvc   = "DEFAULT_SERVER::handle.do?";
    //var Action   = "ServiceName=" + sService[0] + "&" + sService[1] + "=1";
    var Action   = "ServiceName=" + sService[0] + "&" + sService[1] + "=1&ActionName="+sService[1]; // 접속정보 로그인용으로 추가함
    var SvcParam = fcd_GetParam();
		 
	if ( bProgress ) {
		fnc_ProgressPop(); 
	} 
//Trace("fcd_SendTransaction::JSESSIONID="+JSESSIONID);
//Trace(JspSvc+Action+":"+SvcParam);	
	
	
	//SEED TEST START
    ext_SEED_Init("seed",128,"ANSI_X.923" );    var objkey = ext_SEED_GetKey("5900635100400000111");
		
	var Orignal_Data = "1234";//"8123456_/9abcdea_234567";	var enc_data = ext_SEED_encrypt(Orignal_Data, objkey);	
	var Enc_ret = ext_Base64Encode(enc_data,"utf-8");	
	
	//var PostData = TextToBin("PA_OBKEY=strChulYno&PA_WKFTX=strJemok&PA_ERNAM=strSabeon"); //TextToBin을 해 주어야 Post로 정상적으로 전달됨
	var PostData =TextToBin("SEND="+Enc_ret,"utf-8");
	var Headers = "Content-Type: application/x-www-form-urlencoded";
	var URL = "http://160.100.51.199:19080/rbm/seed_test.jsp?SEND="+Enc_ret;
	//SEED TEST END
			
	
	http.Sync = true;
	if (GBL_DUP_LOGIN_CHK != "Y") InDataset = InDataset + " gdsUser=gdsUser";	// 향후 보안상의 이유로 삭제 요망(신재선)
    transaction(SvcName, JspSvc + Action, InDataset, OutDataset, SvcParam, CallbackName);
    
    if (fnc_IsNotNull(sAccessType)) {
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
	http.sync = true;
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
	http.sync = false;
}



/**************************************************************************************************
*  함   수  명				: fcd_AddParam
*  설       명				: Transaction에 사용될 Parameter추가
*  param1) ParamName		: Parameter 명
*  param2) ParamValue		: Parameter 값
*  return Type				: 해당 없음
*  return 내용				: 해당 없음
**************************************************************************************************/
var gcra_SvcParam = Array(-1);   // Transaction에서 사용되는 Parameter

function fcd_AddParam(ParamName, ParamValue) {
    var aParam = Array(2);
    aParam[0] = ParamName;
    aParam[1] = ParamValue;

    gcra_SvcParam[length(gcra_SvcParam)] = aParam;
}



/**************************************************************************************************
*  함   수  명				: fcd_GetParam
*  설       명				: Transaction에 사용할 Parameter를 추가
*  return Type				: String
*  return 내용				: Transaction의 Parameter
**************************************************************************************************/
function fcd_GetParam() {
	var sReturn = "";
	for ( i = 0; i < length(gcra_SvcParam); i++ ) {
		sReturn += gcra_SvcParam[i][0] + "=" + quote(gcra_SvcParam[i][1]) + " ";
	}

	gcra_SvcParam = Array(-1);
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
function fcd_GetSysdate(sFormat, sComm) {

	var sReturn = "";
	if (sComm != "SERVER") {
		sReturn = fcd_GetSysdate_local(sFormat);
		return sReturn;
	}
	var sServiceName = "common-service:searchSysdate";       // 서버에 요청할 Sevice 명
	var sInDataSet   = "";                                   // Input DataSet 명칭
	var sOutDataSet  = "gdsSysdate=dsOutSysdate";            // Output DataSet 명칭

	fcd_AddParam("CUSTOM", sFormat);

	http.sync = true;
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "", "N");
	http.sync = false;
	
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
/*  PC의 시간으로 Return ************************** */
function fcd_GetSysdate_local(sFormat) {

	var sReturn;
	var sLocatTime = GetDate();   //yyyyMMddhhmmss의 14자리 문자열로 값을 Return
	switch ( sFormat ) 
	{
		case "YYYYMMDDHH24MISS" : 
		     sReturn = sLocatTime;
		     break;
		case "YYYYMMDD"         : 
		     sReturn = mid(sLocatTime, 0, 8);
		     break;
		case "HH24MISS"         : 
		     sReturn = mid(sLocatTime, 8);
		     break;
		case "YYYY"             : 
		     sReturn = mid(sLocatTime, 0, 4);
		     break;
		case "MM"               : 
		     sReturn = mid(sLocatTime, 4, 2);
		     break;
		case "DD"               : 
		     sReturn = mid(sLocatTime, 6, 2);
		     break;
		case "HH24"             : 
		     sReturn = mid(sLocatTime, 8, 2);
		     break;
		case "MI"               : 
		     sReturn = mid(sLocatTime, 10, 2);
		     break;
		case "SS"               : 
		     sReturn = mid(sLocatTime, 12, 2);
		     break;
		default 			    :
		     sReturn = sLocatTime;
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
*  param4) nOrderGbn		: 정렬구분  1: 내림차순 , 0 :  오름차순
*  return Type				: 해당 없음
*  return 내용				: 해당 없음
**************************************************************************************************/
function fcd_SetStndYear(oDataset, nStartYear, nLastYear, nOrderGbn)
{

    if ( nLastYear == null ) nLastYear = 2000;
    
    if ( nOrderGbn == null ) nOrderGbn = 1;

	oDataset.ClearData();
	oDataset.AddColumn("STND_YEAR");
	
	var nRow = 0;
	
	if(nOrderGbn){ 
		for ( var i = nLastYear; i >= nStartYear; i-- ) {
			
			oDataset.AddRow();
			oDataset.SetColumn(nRow, "STND_YEAR", i);
			nRow++;
	    }
	}else{
		for ( var i = nStartYear; i <= nLastYear; i++ ) {
			
			oDataset.AddRow();
			oDataset.SetColumn(nRow, "STND_YEAR", i);
			nRow++;
	    }
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
*  함   수  명				: fcd_GetSelectFile
*  설       명				: 선택한 파일명을 취한다.
*  return Type				: Stirng
*  return 내용				: 파일명
**************************************************************************************************/
function fcd_GetSelectFile(sType, sFileName, sFilter) {
	var sReturn;
			
	if ( !IsValid(object("divBtnLst.flImage")) ){
		Create("file", "divBtnLst.flImage");
		Create("fileDialog","divBtnLst.fldImage");
	}
	
	if ( sType == null ) sType = "Open";
	
	if(sType == "Save"){
		//divBtnLst.fldImage.Type     = sType;
		//divBtnLst.fldImage.FilePath = getReg("LastDir");
		divBtnLst.fldImage.FileName = sFileName;
				
		//divBtnLst.flImage.FileName = divBtnLst.fldImage.FilePath + "\\" + divBtnLst.fldImage.FileName;
		sReturn = Array(2);
		sReturn[0] ="";
		sReturn[1] = sFileName;
		return sReturn;
	}else{
	
	}
	
	if( sFilter == "IMG" ) {
		divBtnLst.fldImage.Filter 
			= "이미지(BMP;GIF;JPEG;TIFF;PNG)|*.bmp;*.dib;*.GIF;*.JPG;*.JPEG;*.JRE;*.JFIF;*.TIF;*.TIFF;*.PNG|"
		    + "BMP(*.bmp)|*.bmp;*.dib|"
		    + "GIF|*.GIF|"
		    + "JPEG(*.JPG;*.JPEG;*.JRE;*.JFIF)|*.JPG;*.JPEG;*.JRE;*.JFIF|"
		    + "TIFF(*.TIF;*.TIFF)|*.TIF;*.TIFF|"
		    + "PNG(*.PNG)|*.PNG|";
	}
	
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
*  함   수  명				: fcd_SetRaceInfo
*  설       명				: private Function -경주정보 Combo 종류별로 DataSet 생성 및 기본자료Setting
*  return Type				: String
*  return 내용				: return Dataset
**************************************************************************************************/
function fcd_SetRaceInfo(formId, obj, dsObj, infoKind, selectKind) {
	var nRow = 0;
	var sComboDs = "";
	var sRet = "";
	if (obj != null) {		
		nRow = dsObj.AddRow();
		
		sComboDs = "ds" + formId + obj.Id;
		
		if ( !IsValid(object(sComboDs)) ){
			Create("Dataset", sComboDs);	
		}		
/*		
		Destroy(sComboDs);
		Create("Dataset", sComboDs);
		trace(sComboDs);
		object(sComboDs).Clear();
*/				
		obj.InnerDataset = sComboDs;
		dsObj.SetColumn(nRow, 0, sComboDs);
		dsObj.SetColumn(nRow, 1, infoKind);
		dsObj.SetColumn(nRow, 2, selectKind);

		sRet = sComboDs + "=" +sComboDs + " ";
	}
	
	return sRet;
}

/**************************************************************************************************
*  함   수  명				: fcd_MakeDsRaceInfo
*  설       명				: private Function -경주정보 Dataset 생성
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_MakeDsRaceInfo() {
	var obj = object("dsRaceInfo");
	
	if ( IsValidObject(obj) == false ){
		Create("Dataset", "dsRaceInfo");
	}
/*	
	Destroy("dsRaceInfo");
	Create("Dataset", "dsRaceInfo");	
*/		
	dsRaceInfo.Clear();
	dsRaceInfo.AddColumn("dsName");
	dsRaceInfo.AddColumn("infoKind");
	dsRaceInfo.AddColumn("selectKind");
}

/**************************************************************************************************
*  함   수  명				: fcd_GetRaceInfo
*  설       명				: 경주정보 Combo에 데이타 생성
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetRaceInfo(formId, cboStndYear, cboMeetCd, cboTms, cboDayOrd, cboRaceNo, bTmsAll, varOptional) {
	var sServiceName = "common-service:searchRaceInfoEx";
    var sInDataSet   = "dsRaceInfo=dsRaceInfo ";
    var sOutDataSet  = "";	

	fcd_MakeDsRaceInfo();
	
	sOutDataSet = sOutDataSet + fcd_SetRaceInfo(formId, cboStndYear	, dsRaceInfo, 1, "");	//경주년도
	sOutDataSet = sOutDataSet + fcd_SetRaceInfo(formId, cboMeetCd	, dsRaceInfo, 2, "");	//경륜장소
	sOutDataSet = sOutDataSet + fcd_SetRaceInfo(formId, cboTms		, dsRaceInfo, 3, iif(bTmsAll, "ALL", ""));	// 회차
	
	fcd_AddParam("SEARCH_TYPE", "0");
	fcd_AddParam("DS_IN", "dsRaceInfo");

	http.sync = true;
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
    http.sync = false;
    
    if (cboStndYear != null)	cboStndYear.Value = fnc_GetReturnParam("STND_YEAR");
    if (cboMeetCd != null) 		cboMeetCd.Value = fnc_GetReturnParam("MEET_CD");

    if (cboTms != null) { 		
		cboTms.Value = fnc_GetReturnParam("TMS");
		var dsRetDs = object("ds" + formId + cboTms.Id);    
		dsRetDs.row = cboTms.Index;
		fcd_GetDayOrd(formId, dsRetDs, cboDayOrd, cboRaceNo,  bTmsAll, varOptional);
	}
}

/**************************************************************************************************
*  함   수  명				: fcd_GetRaceInfoEx
*  설       명				: 기준회차 경주정보 Combo에 데이타 생성
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetRaceInfoEx(formId, cboStndYear, cboMeetCd, cboTms, cboDayOrd, cboRaceNo, bTmsAll, varOptional) {
	var sServiceName = "common-service:searchRaceInfoEx";
    var sInDataSet   = "dsRaceInfo=dsRaceInfo ";
    var sOutDataSet  = "";	

	fcd_MakeDsRaceInfo();
	
	sOutDataSet = sOutDataSet + fcd_SetRaceInfo(formId, cboStndYear	, dsRaceInfo, 1, "");	//경주년도
	sOutDataSet = sOutDataSet + fcd_SetRaceInfo(formId, cboTms		, dsRaceInfo, 4, "");	//기준회차
	sOutDataSet = sOutDataSet + fcd_SetRaceInfo(formId, cboMeetCd	, dsRaceInfo, 5, "");	//경륜장소
	
	fcd_AddParam("SEARCH_TYPE", "10");
	fcd_AddParam("DS_IN", "dsRaceInfo");
	fcd_AddParam("TMS_STAT", TMS_STATE_120);	

	http.sync = true;
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
    http.sync = false;
    
    if (cboStndYear != null)	cboStndYear.Value = fnc_GetReturnParam("STND_YEAR");
    if (cboTms != null) { 	
		var objDs = object(cboTms.InnerDataSet);
		objDs.Filter("CFM_YN='Y'");
		if (objDs.FindRow("STND_TMS", fnc_GetReturnParam("STND_TMS")) > -1)
			 cboTms.Value = fnc_GetReturnParam("STND_TMS");
		else cboTms.Index = 0;		
	}
    if (cboMeetCd != null) {
		cboMeetCd.Value = fnc_GetReturnParam("MEET_CD");
		var dsRetDs = object("ds" + formId + cboMeetCd.Id);    
		dsRetDs.row = cboMeetCd.Index;
		fcd_GetDayOrd(formId, dsRetDs, cboDayOrd, cboRaceNo,  bTmsAll, varOptional);
	}

}

/**************************************************************************************************
*  함   수  명				: fcd_GetTms
*  설       명				: 회차,일차,경주번호 Combo에 데이타 생성
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetTms(formId, cboTms, stndYear, meetCd, bTmsAll, cboDayOrd, cboRaceNo, varOptional) {
	var sServiceName = "common-service:searchRaceInfoEx";
    var sInDataSet   = "dsRaceInfo=dsRaceInfo ";
    var sOutDataSet  = "";	
	var nRow = 0;
	fcd_MakeDsRaceInfo();
	
	sOutDataSet = sOutDataSet + fcd_SetRaceInfo(formId, cboTms	, dsRaceInfo, 3, iif(bTmsAll, "ALL", ""));	// 회차
	
	fcd_AddParam("SEARCH_TYPE", "3");
	fcd_AddParam("DS_IN", "dsRaceInfo"); 
	fcd_AddParam("STND_YEAR", stndYear);
	fcd_AddParam("MEET_CD", iif(fnc_isNull(meetCd), GBL_MEET_CD, meetCd));
	

	http.sync = true;
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
    http.sync = false;
    
    var dsRetDs = object("ds" + formId + cboTms.Id);
	    
    cboTms.Value = fnc_GetReturnParam("TMS");
   
    dsRetDs.row = cboTms.Index;

    fcd_GetDayOrd(formId, dsRetDs, cboDayOrd, cboRaceNo, bTmsAll, varOptional);
    
}

/**************************************************************************************************
*  함   수  명				: fcd_GetStndTmsEx
*  설       명				: 회차,일차,경주번호 Combo에 데이타 생성
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetStndTmsEx(formId, stndYear, obj, tmsStat, varOptional) {
	var sServiceName = "common-service:searchRaceInfoEx";
    var sInDataSet   = "dsRaceInfo=dsRaceInfo ";
    var sOutDataSet  = "";	
	var nRow = 0;
	
	fcd_MakeDsRaceInfo();
	
	if (obj.GetType() == "Combo") {
		sOutDataSet = sOutDataSet + fcd_SetRaceInfo(formId, obj, dsRaceInfo, 4, "");	// 회차
	} else {
		obj.ClearData();
		nRow = dsRaceInfo.AddRow();
		dsRaceInfo.SetColumn(nRow, 0, obj.Id);
		dsRaceInfo.SetColumn(nRow, 1, "4");
		dsRaceInfo.SetColumn(nRow, 2, "");		
		sOutDataSet = sOutDataSet + obj.Id + "=" + obj.Id + " ";
	}		
	
	fcd_AddParam("SEARCH_TYPE", "4");
	fcd_AddParam("DS_IN", "dsRaceInfo"); 
	fcd_AddParam("STND_YEAR", stndYear);
	fcd_AddParam("TMS_STAT", tmsStat);

	http.sync = true;
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
    http.sync = false;
        	  
	var objDs = null;
	if (obj.GetType() == "Combo")
		 objDs = object(obj.InnerDataSet);
	else objDs = obj;

	if (!fnc_isNull(tmsStat))
		objDs.Filter("CFM_YN='Y'");
	if (obj.GetType() == "Combo") {
		if (objDs.FindRow("STND_TMS", fnc_GetReturnParam("STND_TMS")) > -1)
			 obj.Value = fnc_GetReturnParam("STND_TMS");
		else obj.Index = 0;
	}
}

/**************************************************************************************************
*  함   수  명				: fcd_GetStndMeetCd
*  설       명				: 기준회차 경주정보 Combo에 데이타 생성
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetStndMeetCd(formId, stndYear, stndTms, cboMeetCd, cboDayOrd, cboRaceNo, varOptional) {
	var sServiceName = "common-service:searchRaceInfoEx";
    var sInDataSet   = "dsRaceInfo=dsRaceInfo ";
    var sOutDataSet  = "";	

	fcd_MakeDsRaceInfo();
	
	sOutDataSet = sOutDataSet + fcd_SetRaceInfo(formId, cboMeetCd	, dsRaceInfo, 5, "");	//경륜장소
	
	fcd_AddParam("SEARCH_TYPE", "5");
	fcd_AddParam("DS_IN", "dsRaceInfo");
	fcd_AddParam("STND_YEAR", stndYear);	
	fcd_AddParam("STND_TMS", stndTms);	
	fcd_AddParam("TMS_STAT", TMS_STATE_120);	

	http.sync = true;
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
    http.sync = false;
    
    if (cboMeetCd != null) {
		cboMeetCd.Value = fnc_GetReturnParam("MEET_CD");
		var dsRetDs = object("ds" + formId + cboMeetCd.Id);    
		dsRetDs.row = cboMeetCd.Index;
		fcd_GetDayOrd(formId, dsRetDs, cboDayOrd, cboRaceNo,  false, varOptional);
	}

}

/**************************************************************************************************
*  함   수  명				: fcd_GetStndTmsEx1
*  설       명				: 회차,일차,경주번호 Combo에 데이타 생성
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetStndTmsEx1(formId, stndYear, obj, cboMeetCd, cboDayOrd, cboRaceNo, varOptional) {
	var sServiceName = "common-service:searchRaceInfoEx";
    var sInDataSet   = "dsRaceInfo=dsRaceInfo ";
    var sOutDataSet  = "";	
	var nRow = 0;
	
	fcd_MakeDsRaceInfo();
	
	if (obj.GetType() == "Combo") {
		sOutDataSet = sOutDataSet + fcd_SetRaceInfo(formId, obj, dsRaceInfo, 4, "");	// 회차
	} else {
		obj.ClearData();
		nRow = dsRaceInfo.AddRow();
		dsRaceInfo.SetColumn(nRow, 0, obj.Id);
		dsRaceInfo.SetColumn(nRow, 1, "4");
		dsRaceInfo.SetColumn(nRow, 2, "");		
		sOutDataSet = sOutDataSet + obj.Id + "=" + obj.Id + " ";
	}		
	
	fcd_AddParam("SEARCH_TYPE", "4");
	fcd_AddParam("DS_IN", "dsRaceInfo"); 
	fcd_AddParam("STND_YEAR", stndYear);
	fcd_AddParam("TMS_STAT", TMS_STATE_120);

	http.sync = true;
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
    http.sync = false;
    	  
	var objDs = iif(obj.GetType() == "Combo", object(obj.InnerDataSet), obj);

	objDs.Filter("CFM_YN='Y'");
	if (obj.GetType() == "Combo") {
		if (objDs.FindRow("STND_TMS", fnc_GetReturnParam("STND_TMS")) > -1)
			 obj.Value = fnc_GetReturnParam("STND_TMS");
		else obj.Index = 0;
	}
	
	if (cboMeetCd != null) {
		fcd_GetStndMeetCd(formId, stndYear, obj.Value, cboMeetCd, cboDayOrd, cboRaceNo, varOptional);
	}
}
/**************************************************************************************************
*  함   수  명				: fcd_GetDayOrd
*  설       명				: 일차,경주번호 Combo에 데이타 생성
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetDayOrd(formId, obj,  cboDayOrd, cboRaceNo, bTmsAll, varOptional) {
	var dsRetDs = null;
    if (bTmsAll != true) {
		if (cboDayOrd != null) {
			if (obj.GetType() == "Combo") {
				dsRetDs = object(obj.InnerDataset);
				dsRetDs.row = obj.Index;
			} else {
				dsRetDs = obj;
			}
			fcd_SetRaceInfo(formId, cboDayOrd	, dsRaceInfo, 4, "");
			var idx = 1;
			var dsDayOrd = object("ds" + formId + cboDayOrd.Id);
			
			dsDayOrd.ClearData();
			dsDayOrd.AddColumn("DAY_ORD");
			
			for (var i=1; i<5; i++) {
				if (!fnc_isNull(dsRetDs.GetColumn(dsRetDs.GetCurrow(), "DAY"+i+"_DT")) && 
				    ( (fnc_isNull(varOptional) && 
				       (dsRetDs.GetColumn(dsRetDs.GetCurrow(), "DAY"+i+"_REST_YN") == "0")
				      ) ||
				      (varOptional == "ALL_DAY")
				    )) {
					nRow = dsDayOrd.AddRow();
					dsDayOrd.SetColumn(nRow, "DAY_ORD", i);
				}
			}
			
			cboDayOrd.index = 0;
		}
    }
    
    fcd_GetRaceNoEx(formId, dsRetDs,  cboRaceNo, bTmsAll);
}



/**************************************************************************************************
*  함   수  명				: fcd_GetRaceNoEx
*  설       명				: 경주번호 Combo에 데이타 생성
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetRaceNoEx(formId, obj,  cboRaceNo, bTmsAll, varOptional) {
	var dsRetDs = null;
    if (bTmsAll != true) {
		if (cboRaceNo != null) {
			if (obj.GetType() == "Combo") {
				dsRetDs = object(obj.InnerDataset);
				dsRetDs.row = obj.Index;
			} else {
				dsRetDs = obj;
			}
			fcd_SetRaceInfo(formId, cboRaceNo	, dsRaceInfo, 5, "");
			var idx = 1;
			var dsRaceNo = object("ds" + formId + cboRaceNo.Id);
			var nCnt = toNumber(dsRetDs.GetColumn(dsRetDs.GetCurrow(), "DAY_RACE_CNT"));
			dsRaceNo.ClearData();
			dsRaceNo.AddColumn("RACE_NO");
			
			for (var i=0; i<nCnt; i++) {
				nRow = dsRaceNo.AddRow();
				dsRaceNo.SetColumn(nRow, "RACE_NO", LPad(ToString(i+1), "0", 2));
			}
			
			cboRaceNo.index = 0;
		}
    }
}

/**************************************************************************************************
*  함   수  명				: fcd_GetTmsDate
*  설       명				: 회차별 경주시작일자 조회
*  return Type				: String
*  return 내용				: 시작일
**************************************************************************************************/
function fcd_GetTmsDate(formId, cboTms, tms) {
    var dsRetDs = object("ds" + formId + cboTms.Id);
	for (var i=0; i<dsRetDs.GetRowCount(); i++) {
		if (ToNumber(dsRetDs.GetColumn(i, "TMS")) == ToNumber(tms)) {
			return dsRetDs.GetColumn(i, "DAY1_DT") ;
		}
	}
}

/**************************************************************************************************
*  함   수  명				: fcd_GetTmsDate
*  설       명				: 회차별 경주시작일자 조회
*  return Type				: String
*  return 내용				: 시작일
**************************************************************************************************/
function fcd_GetDayDate(formId, cboTms, tms, dayOrd) {
    var dsRetDs = object("ds" + formId + cboTms.Id);
	for (var i=0; i<dsRetDs.GetRowCount(); i++) {
		if (ToNumber(dsRetDs.GetColumn(i, "TMS")) == ToNumber(tms)) {
			return dsRetDs.GetColumn(i, "DAY"+dayOrd+"_DT") ;
		}
	}
}

function fcd_GetStndTmsDate(obj, tms) {
	var dsRetDs = null;
	var index = -1;
	if (obj.GetType() == "Combo") {
		dsRetDs = object(obj.InnerDataset);
	} else {
		dsRetDs = obj;
	}
	
	index = dsRetDs.FindRow("STND_TMS", LPad(ToString(tms), "0", 2));
	
	return dsRetDs.GetColumn(index, "CRA_STR_DT");
}

/**************************************************************************************************
*  함   수  명				: fcd_GetTmsCFM
*  설       명				: 회차별  확정여부 및 취소가능여부 조회
*  return Type				: Array
*  return 내용				: 0:확정여부,1:취소가능여부, 2:상태명칭, 3:확정가능여부, 4:현재상태코드
**************************************************************************************************/
function fcd_GetTmsCFM(stndYear, tms, statCd, dayOrd, raceNo) {	
	var sServiceName = "common-service:searchRaceInfoEx";
    var sInDataSet   = "";
    var sOutDataSet  = "";	
	
	fcd_AddParam("SEARCH_TYPE", "9");
	fcd_AddParam("STND_YEAR", stndYear);
	fcd_AddParam("TMS", tms);
	fcd_AddParam("STAT_CD", statCd);
	fcd_AddParam("DAY_ORD", dayOrd);
	fcd_AddParam("RACE_NO", raceNo);
	
	http.sync = true;
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
    http.sync = false;
    
	var varArray = Array(4);
	varArray[0] = fnc_GetReturnParam("CFM_GBN");
	varArray[1] = fnc_GetReturnParam("CANCEL_YN");
	varArray[2] = fnc_GetReturnParam("TMS_STAT_NM");
	varArray[3] = fnc_GetReturnParam("CFM_YN");
	varArray[4] = fnc_GetReturnParam("STAT_CD");
	
	return varArray;
}

/**************************************************************************************************
*  함   수  명				: fcd_SetTmsCFM
*  설       명				: 확정 및 확정취소 저장
*  return Type				: 
*  return 내용				: 
**************************************************************************************************/
function fcd_SetTmsCFM(obj, stndYear, tms, statCd, cfmYn, dayOrd, raceNo) {	

    if(!fnc_Message(SNIS_COM_1037, "Y", obj.Text)) return;

	var sServiceName = "common-service:saveTmsCfm";
    var sInDataSet   = "";
    var sOutDataSet  = "";	
	
	fcd_AddParam("STND_YEAR", stndYear);
	fcd_AddParam("TMS", tms);
	fcd_AddParam("STAT_CD", statCd);
	fcd_AddParam("CFM_YN", cfmYn);
	fcd_AddParam("DAY_ORD", dayOrd);
	fcd_AddParam("RACE_NO", raceNo);

	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
}


/**************************************************************************************************
*  함   수  명				: fcd_SetTmsCFM
*  설       명				: 확정 및 확정취소 저장
*  return Type				: 
*  return 내용				: 
**************************************************************************************************/
function fcd_SetTmsCFMEx(obj, stndYear, tms, statCd, cfmYn, dayOrd, raceNo, classNm) {	

    if(!fnc_Message(SNIS_COM_1037, "Y", obj.Text)) return;

	var sServiceName = "common-service:saveTmsCfm";
    var sInDataSet   = "";
    var sOutDataSet  = "";	
	
	fcd_AddParam("STND_YEAR", stndYear);
	fcd_AddParam("MEET_CD", GBL_MEET_CD);
	fcd_AddParam("TMS", tms);
	fcd_AddParam("STAT_CD", statCd);
	fcd_AddParam("CFM_YN", cfmYn);
	fcd_AddParam("DAY_ORD", dayOrd);
	fcd_AddParam("RACE_NO", raceNo);

//	trace(classNm);
	if (!fnc_isNull(classNm))
		fcd_AddParam("BIZ_METHOD", classNm);

	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
}

function fcd_GetTmsStat(formId, obj, tms) {
	var dsRetDs = null;
	if (obj.GetType() == "Combo") {
		dsRetDs = object("ds" + formId + obj.Id);
		return dsRetDs.GetColumn(obj.Index, "TMS_STAT");	
	} else {
		var nRow = obj.FindRow("TMS", tms);
		if (nRow > -1) 
			return obj.GetColumn(nRow, "TMS_STAT");
	}
}

function fcd_GetStndTms(formId, cboTms, tms) {
    var dsRetDs = object("ds" + formId + cboTms.Id);
	
	if (fnc_IsNull(tms)) {
		return dsRetDs.GetColumn(cboTms.Index, "STND_TMS");	
	} else {
		var nRow = dsRetDs.FindRow("TMS", tms);

		if (nRow > -1)
			 return dsRetDs.GetColumn(nRow, "STND_TMS");	
		else return "";
	}
}

function fcd_GetLocalTms(obj, meetCd) {
	var dsRetDs = null;
	var index = -1;
	if (obj.GetType() == "Combo") {
		dsRetDs = object(obj.InnerDataset);
		index = obj.Index;
	} else {
		dsRetDs = obj;
		index = obj.Row;
	}
	
	if (meetCd == "001")
		return dsRetDs.GetColumn(index, "CRA_TMS");
	else if (meetCd == "002")
		return dsRetDs.GetColumn(cboTms.Index, "CCRC_TMS");
	else if (meetCd == "004")
		return dsRetDs.GetColumn(cboTms.Index, "BCRC_TMS");
}


/**************************************************************************************************
*  함   수  명				: fcd_ExecApproval
*  설       명				: 본부와 전자결재 연동
*  return Type				: 
*  return 내용				: 
*  파라메터 : 업무구분, 기준년도, 회차, 일차, 경주번호, 순번, 기안자, 문서제목, 데이터셋ID, 내용, 파일Type
**************************************************************************************************/
function fcd_ExecApproval(job, stndYear, tms, dayOrd, raceNo, seq, reportId, subJect, fileObj, conObj, fileGubun) {	
    var sServiceName    = "common-service:searchApproSeqNo";
    var sInDataSet      = "";
    var sOutDataSet     = "";
    var sDocType        = "내부";
    
    var varArray        = Array(0);
    var ExportObject1;
    var FilePath;
    var AttFilePath;
    var FileName;
    var OSVersion;
    
    OSVersion = trim(GetDeviceInfo("OSVERSION"));
    
    //==============================================================1.필수입력 값 체크
    if(fnc_IsNull(job)) {
        fnc_Message(SNIS_COM_0001, "SERVERMSG", "업무구분");
        return;
    }
    if(fnc_IsNull(stndYear)) {
        fnc_Message(SNIS_COM_0001, "SERVERMSG", "기준년도");
        return;
    }
    if(fnc_IsNull(tms)) {
        fnc_Message(SNIS_COM_0001, "SERVERMSG", "회차");
        return;
    }
    if(fnc_IsNull(reportId)) {
        fnc_Message(SNIS_COM_0001, "SERVERMSG", "기안자");
        return;
    }
    if(fnc_IsNull(subJect)) {
        fnc_Message(SNIS_COM_0001, "SERVERMSG", "문서제목");
        return;
    }
    if(fnc_IsNull(dayOrd))          dayOrd="0";             //일차 null값일 경우 '0'으로 세팅
    if(fnc_IsNull(raceNo))          raceNo="00";            //경주번호 null값일 경우 '00'으로 세팅
    if(fnc_IsNull(fileGubun))       fileGubun="Excel";      //fileGubun 값이 없을경우는 무조건 엑셀로 한다.
    if(job == 'EC' || job == 'CC')  sDocType="일괄";        //제재심의의뢰, 낙차사고보고서일 경우는 문서종류를 '일괄'로 넘긴다.
    //============================================================================
    
    //==============================================================2.일련번호 추출&결재관리 입력
    sServiceName    = "common-service:saveApproval";
    sInDataSet      = "";
    sOutDataSet     = "";
    
	fcd_AddParam("JOB", job);               //업무구분
	fcd_AddParam("STND_YEAR", stndYear);    //기준년도
	fcd_AddParam("TMS", tms);               //회차
	fcd_AddParam("DAY_ORD", dayOrd);        //일차
	fcd_AddParam("RACE_NO", raceNo);        //경주번호
	fcd_AddParam("USER_ID", reportId);      //기안자
	fcd_AddParam("SUBJECT", subJect);       //문서제목
		
	http.sync = true;
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
    http.sync = false;    
	
	varArray[0] = trim(fnc_GetReturnParam("SEQ"));    
	FileName    = varArray[0]+job;
	//=============================================================================
    
    FilePath    = AliasToRealPath("%UserApp%");   
    if(OSVersion == "Windows NT 6.0") {
        FilePath    = replace(FilePath, "LocalLow", "Local\\Temp");
    }else{
        FilePath    = replace(FilePath, "Compatible LocalLow", "Temp");
    }
    
    FilePath    = FilePath+varArray[0]+"\\";
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
        }else if(fileGubun == "HWP"){
            FileName = FileName+".hwp";            
            fileObj.exportFileName = AliasToRealPath(AttFilePath+FileName);
            fileObj.ExportFile("HWP");
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
        fnc_CreateHtmlFile(FilePath+varArray[0]+".html", conObj.text);      
                
    }else{
        // Null 이면 문서제목과 동일하게 Html파일 생성하는 로직 수행
        fnc_CreateHtmlFile(FilePath+varArray[0]+".html", subJect);
    }
    //=============================================================================    
    
    
    //==============================================================5.송신URL 호출
    //테스트 서버 : http://140.100.100.24:8088/UFWeb/public/signContact.jsp
    //실운영 서버 : http://jupiter.sosfo.or.kr/UFWeb/sign/hwpsign/ActiveMis/signContact.jsp
    ExecBrowser("http://140.100.100.24:8088/UFWeb/public/signContact.jsp?GUBUNID="+varArray[0]+"&BIZ=cy&JOB="+job+"&CREATTYPE=내부&USER_ID="+reportId+"&SUBJECT="+subJect+"&ATTNAME="+FileName+"&SEQ="+seq);
    
    //ExecBrowser("http://jupiter.sosfo.or.kr/UFWeb/public/signContact.jsp?GUBUNID="+varArray[0]+"&BIZ=cy&JOB="+job+"&CREATTYPE="+sDocType+"&USER_ID="+reportId+"&SUBJECT="+subJect+"&ATTNAME="+FileName+"&SEQ="+seq);
    //=============================================================================
      
}


/**************************************************************************************************
*  함   수  명				: fcd_SendMessage
*  설       명				: 메신져연동
*  return Type				: 
*  return 내용				: 
*  파라메터                 : 발신사원번호, 수신사원번호, 제목, 링크정보, 내용
**************************************************************************************************/
function fcd_SendMessage(sendUser, recvUser, title, MenuId, desc, compId, MsgNo) {
    var sCompID;
    var obj;

    MenuId = replace(MenuId,"\"","");
    sendUser = replace(sendUser,"\"","");
    recvUser = replace(recvUser,"\"","");
    title = replace(title,"\"","");
    desc = replace(desc,"\"","");
    
    //var ServerURL = "http://160.100.1.17/uscra/miInstall/install_process_web.jsp?INITURL=";
    var ServerURL = GBL_SERVER_URL+"miInstall/xssoLogin2.jsp?MENUID=";
    
    //1.필수입력 값 체크
    if(fnc_IsNull(sendUser)) {
        fnc_Message(SNIS_COM_0001, "SERVERMSG", "보내는사람");
        return;
    }
    if(fnc_IsNull(recvUser)) {
        fnc_Message(SNIS_COM_0001, "SERVERMSG", "받는사람");
        return;
    }
    if(fnc_IsNull(title)) {
        fnc_Message(SNIS_COM_0001, "SERVERMSG", "제목");
        return;
    }
    /*if(fnc_IsNull(linkUrl)) {
        fnc_Message(SNIS_COM_0001, "SERVERMSG", "링크정보");
        return;
    }*/
    if(fnc_IsNull(desc)) {
        fnc_Message(SNIS_COM_0001, "SERVERMSG", "내용");
        return;
    }
    

   var aRecvId = split(recvUser,",","webstyle"); 
   
   var sTmpRecvId;
 
   var tmpnum = 0;
   var tmpServerURL = "";
   
   /*
   for(var i = 0; i < aRecvId.length;i++){

        tmpServerURL = ServerURL+ linkUrl;
		sCompID = "AxMsie"+compId+i;
		Create("AxMsie", sCompID);	   
		obj = object(sCompID);
		
		//서버
        obj.Navigate(GBL_SERVER_URL+"send.jsp?SENDUSER="+sendUser+"&RECVUSER="+aRecvId[i]+"&TITLE="+title+"&LINKURL="+tmpServerURL+"&DESC="+desc);
	
        //  trace("send.jsp를 호출하는 부분 >>>> " + tmpServerURL);
        //trace(GBL_SERVER_URL+"send.jsp?SENDUSER="+sendUser+"&RECVUSER="+aRecvId[i]+"&TITLE="+title+"&LINKURL="+tmpServerURL+"&DESC="+desc);      
	    fcd_SetAlarmHist( "001",  replace(aRecvId[i],"\"",""), GBL_MENUID, "", replace(title,"\"",""), replace(desc,"\"",""));
   }
   
	sCompID = "AxMsie"+compId+"0";

	Create("AxMsie", sCompID);   
	obj = object(sCompID);
	if (!IsValidObject(obj)) Trace("fcd_SendMessage::obj error!!");
	
    var pageUrl="http://rbm.kcycle.or.kr/send2.jsp?SENDUSER="+sendUser+"&RECVUSER="+recvUser+"&TITLE="+title+"&LINKURL="+ServerURL+"&DESC="+desc+"&MSGNO="+MsgNo;
	*/
	tmpServerURL = ServerURL+ MenuId;
    
	//서버
	//obj.Navigate(GBL_SERVER_URL+"send.jsp?SENDUSER="+sendUser+"&RECVUSER="+recvUser+"&TITLE="+title+"&LINKURL="+tmpServerURL+"&DESC="+desc);
	//fcd_SetAlarmHist( "001",  replace(recvUser,"\"",""), GBL_MENUID, "", replace(title,"\"",""), replace(desc,"\"",""));
    //Trace(GBL_SERVER_URL+"send.jsp?SENDUSER="+sendUser+"&RECVUSER="+recvUser+"&TITLE="+title+"&LINKURL="+tmpServerURL+"&DESC="+desc);    
    //Trace("fcd_SetAlarmHist::");
    fcd_SetAlarmHist( "001", recvUser, GBL_MENUID, "", replace(title,"\"",""), replace(desc,"\"",""), tmpServerURL);
    //로컬 테스트용
    //obj.Navigate("http://localhost:8080/rbm/send.jsp?SENDUSER="+sendUser+"&RECVUSER="+recvUser+"&TITLE="+title+"&LINKURL= &DESC="+desc);
    
    //obj.Navigate(GBL_SERVER_URL+"send.jsp?SENDUSER="+sendUser+"&RECVUSER="+recvUser+"&TITLE="+title+"&LINKURL="+ServerURL+"&DESC="+desc);
    
    //실서버 URL
    //var pageUrl="http://rbm.kcycle.or.kr/send2.jsp?SENDUSER="+sendUser+"&RECVUSER="+recvUser+"&TITLE="+title+"&LINKURL="+ServerURL+"&DESC="+desc+"&MSGNO="+MsgNo;
	
    
    //fcd_SetAlarmHist( "001",  replace(recvUser,"\"",""), GBL_MENUID, "", replace(title,"\"",""), replace(desc,"\"",""));
    //obj.Navigate(pageUrl);
    //Trace(pageUrl);    
    
	//Destroy(sCompID);	   

}

/**************************************************************************************************
*  함   수  명				: fcd_GetBzInfo()
*  설       명				: 사업자정보 조회 
*  return Type				: Array  [0] : 사업자등록번호, [1] : 법인명, [2] : 대표자명, 
*                                     [3] : 법인등록번호, [4] : 주소
*  return 내용				: 
*  파라메터                 : 
**************************************************************************************************/
function fcd_GetBzInfo() {
	
	Destroy("dsBzInfo");
	Create("Dataset", "dsBzInfo");	
	
	var sServiceName = "common-service:searchBzInfo";
    var sInDataSet   = "";
    var sOutDataSet  = "dsBzInfo=dsBzInfo ";     
    
	http.Sync = true;
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "", false);
	http.Sync = false;
	
	var arrBzInfo = Array(5);
	
	if(dsBzInfo.rowcount > 0) {
		arrBzInfo[0] = dsBzInfo.GetColumn(0, "BZ_INFO0"); // 사업자등록번호
		arrBzInfo[1] = dsBzInfo.GetColumn(0, "BZ_INFO1"); // 법인명
		arrBzInfo[2] = dsBzInfo.GetColumn(0, "BZ_INFO2"); // 대표자명
		arrBzInfo[3] = dsBzInfo.GetColumn(0, "BZ_INFO3"); // 법인등록번호
		arrBzInfo[4] = dsBzInfo.GetColumn(0, "BZ_INFO4"); // 주소
	} else {
		arrBzInfo[0] = "";
		arrBzInfo[1] = "";
		arrBzInfo[2] = "";
		arrBzInfo[3] = "";
		arrBzInfo[4] = "";								
	}		
				
	return arrBzInfo;
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
                     strReceiveId, strSendContent, strReqTime) {	
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
	

    obj.Navigate(GBL_SERVER_URL+"smsSend.jsp"+param);
    */
    
   //trace(GBL_SERVER_URL+"smsSend.jsp"+param);
   
	/*  
   var aRecvPh = split(strReceivePhoneNumber,";","webstyle");
   var aRecvNm = split(strReceiveName,";","webstyle"); 
   var aRecvId = split(strReceiveId,";","webstyle"); 
	  
   for(var i = 0; i < aRecvPh.length;i++){
	  idle();
	  fcd_SetAlarmHist(	 "002" 
					, aRecvId[i]
					, GBL_MENUID
					, aRecvPh[i] 		//수신자 번호
					, strSendSubject
					, strSendContent
					, strSendPhoneNumber
					, strSendUserName
					, aRecvNm[i]
					, strReqTime);   
   }
   */
   var called_menu_id = "";
   
	if (strSendSubject=="수동SMS전송") {
		called_menu_id = "R000000";
	} else {
		called_menu_id = GBL_MENUID;
	}
   
   // 일괄전송으로 변경
   fcd_SetAlarmHist(	 "002" 
					, strReceiveId
					, GBL_MENUID
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
function fcd_SetAlarmHist( sAlarmGbn, sRecvId, sMnId, sHpNo, sTitle, sCntnt, sUrl, 
                           sSendPhNo, sSendUserNm, sRecvUserNm, sReqTime) {	
                           
	var sServiceName = "rsy7010-service:saveAlarmHist";
    var sInDataSet   = "";
    var sOutDataSet  = "";	
	
	fcd_AddParam("ALARM_GBN", sAlarmGbn);
	fcd_AddParam("RECV_ID",sRecvId);
	fcd_AddParam("MN_ID", sMnId);
	fcd_AddParam("HP_NO", sHpNo);
	fcd_AddParam("TITLE", sTitle);
	fcd_AddParam("CNTNT", sCntnt);
	fcd_AddParam("URL",  sUrl);
	fcd_AddParam("SEND_HP_NO", sSendPhNo);
	fcd_AddParam("SEND_USER_NM", sSendUserNm);
	fcd_AddParam("RECV_USER_NM", sRecvUserNm);
	fcd_AddParam("REQ_TIME", sReqTime);



	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
}

/**************************************************************************************************
*  함   수  명				: fcd_GetRaceNo
*  설       명				: 해당 년도의 회차조회  
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetTmsDs(sDsId, sMeetCd, sStndYear, sRaceDay)
{

	var sServiceName = "common-service:SearchTms";
    var sInDataSet   = "";
    var sOutDataSet  = sDsId + "=dsTms ";
    

	fcd_AddParam(Trim("MEET_CD"), sMeetCd);
	fcd_AddParam(Trim("STND_YEAR"), sStndYear);
	fcd_AddParam(Trim("RACE_DAY"), sRaceDay);
    
    http.sync = true;
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
    http.sync = false;
}


/**************************************************************************************************
*  함   수  명				: fcd_GetAlarmDs
*  설       명				: 알림 담당자 조회
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcd_GetAlarmDs(sDsId, sAlarmCd)
{

	var sServiceName = "common-service:searchAlarmCd";
    var sInDataSet   = "";
    var sOutDataSet  = sDsId + "=dsAlarm ";
    

	fcd_AddParam(Trim("ALARM_CD"), sAlarmCd);


    http.sync = true;
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
    http.sync = false;
}


/**************************************************************************************************
*  함   수  명				: fcd_SendMsgByAlarmDs
*  설       명				: 알림 담당자 조회  (callback (fcAlarmCallBack) 에서  쪽지/SMS 전송함 )
*  return Type				: 없음
*  return 내용				: 없음
*  파라메터                 

	sAlarmCd 		= "";		// 알림코드 (공통코드  080)
	sSendUserId 	= "";		// 송신자 사번(예:05073)
	sSendUserNm 	= "";		// 송신자 성명(예:홍길동)	
	sSendUserPhone 	= "";		// 수신자 번호, 여러명인경우 ; 로 구분 (예:01020717775)
	sTitle 			= "";		// 제목
	sDesc 			= "";		// 내용
	sLinkMnId 		= "";		// 쪽지전송시 LINK 메뉴 ID
			
			
**************************************************************************************************/
function fcd_SendMsgByAlarmDs(sAlarmCd, sSendUserId, sSendUserNm, sSendUserPhone, sTitle, sDesc, sLinkMnId)
{
	gdsAlarmMng.clearData();	//알림 담당자 
	gdsAlarmInfo.clearData();   //알림 내용
	
	
	//SMS, 쪽지 전송 정보 GDS 설정 
	gdsAlarmInfo.AddRow();
	
	gdsAlarmInfo.SetColumn(0, "SEND_USER_ID", sSendUserId );
	gdsAlarmInfo.SetColumn(0, "SEND_USER_NM", sSendUserNm );
	gdsAlarmInfo.SetColumn(0, "SEND_USER_PHONE", sSendUserPhone);
	
	gdsAlarmInfo.SetColumn(0, "TITLE", sTitle );
	gdsAlarmInfo.SetColumn(0, "DESC", sDesc);
	gdsAlarmInfo.SetColumn(0, "LINK_MN_ID", sLinkMnId);
	

	var sServiceName = "common-service:searchAlarmCd";
    var sInDataSet   = "";
    var sOutDataSet  = "gdsAlarmMng=dsAlarm ";
    

	fcd_AddParam(Trim("ALARM_CD"), sAlarmCd);

    http.sync = true;
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcAlarmCallBack", false);
    http.sync = false;
}

/**************************************************************************************************
*  함   수  명				: fcAlarmCallBack
*  설       명				: 알림 담당자 메세지 전송
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fcAlarmCallBack(sServiceName, ErrorCode, ErrorMSG) {

	// 조회 완료 후
	if ( sServiceName == "common-service:searchAlarmCd" ) {

		if(gdsAlarmMng.getRowCount()>0){
		
			var sSendUserId 	= gdsAlarmInfo.GetColumn(0, "SEND_USER_ID" );		//보내는 사람 사번 
			var sSendUserNm		= gdsAlarmInfo.GetColumn(0, "SEND_USER_NM");		//보내는 사람 사원명 
			var sSendUserPhone 	= gdsAlarmInfo.GetColumn(0, "SEND_USER_PHONE");		//보내는 사람 HP번호 
			
			var sTitle			= gdsAlarmInfo.GetColumn(0, "TITLE");				//쪽지 제목 (문자내용)
			var sDesc 			= gdsAlarmInfo.GetColumn(0, "DESC");				//쪽지내용
			var sLinkMnId 		= gdsAlarmInfo.GetColumn(0, "LINK_MN_ID");			//LINK 메뉴 ID 
			
			var sSmsReceivePhone="";		//SMS 전송 대상자  HP번호 
		    var sSmsReceiveName="";			//SMS 전송 대상자  사원명
		    var sSmsReceiveId ="";			//SMS 전송 대상자  사번 
		    
		    var sMsgReceiveId ="";			//쪽지 전송 대상자 사번
	
			for(var i = 0; i <gdsAlarmMng.GetRowCount();i++){
					
			    //쪽지 알림 대상자 
				if(gdsAlarmMng.GetColumn(i,"MSG_SEND_YN") == "Y"){				
					sMsgReceiveId += gdsAlarmMng.GetColumn(i,"USER_ID")+",";
				}
			
				//SMS 알림 대상자 
				if(gdsAlarmMng.GetColumn(i,"SMS_SEND_YN") == "Y"){
					sSmsReceivePhone += gdsAlarmMng.GetColumn(i,"HP_NO") +",";
					sSmsReceiveName += gdsAlarmMng.GetColumn(i,"USER_NM")+",";
					sSmsReceiveId += gdsAlarmMng.GetColumn(i,"USER_ID")+",";
				}

			}
		

			//SMS  알림
			if(Length(sSmsReceiveId) >0){
				  sSmsReceivePhone= sSmsReceivePhone.SubStr(0,Length(sSmsReceivePhone)-1);
				  sSmsReceiveName=sSmsReceiveName.SubStr(0,Length(sSmsReceiveName)-1);
				  sSmsReceiveId =sSmsReceiveId.SubStr(0,Length(sSmsReceiveId)-1);

				  fcd_SendSms(	sSendUserPhone 					// 송신자 번호
						, sSendUserId	    					// 송신자 사번 
						, sSendUserNm   						// 송신자 성명
						, sSmsReceivePhone  					//수신자 번호
						, sSmsReceiveName	   					//수신자명
						, sTitle
						, sSmsReceiveId
						, sDesc
				  );
			}

		    //쪽지 알림 
	  		if(Length(sMsgReceiveId) >0){
					 sMsgReceiveId =sMsgReceiveId.SubStr(0,Length(sMsgReceiveId)-1);
					 
					 fcd_SendMessage(sSendUserId				// 송신자 사번 
						, sMsgReceiveId 						// 수신자 사번 
						, sTitle	    					    // 제목
						, sLinkMnId 							// 수신자 번호
						, sDesc									// 내용 
						, "comSendMsg"
					 );	
			}

		}
		
		
		gdsAlarmMng.ClearData();
		gdsAlarmInfo.clearData();
	}

}

function fcd_GetUserBrncCd(){
	var sUserBrncCd = gdsUser.GetColumn(0,"BRNC_CD");
	
	
	return sUserBrncCd;
}

//본점코드 
var sCenterBrncCd = "00";
function fcd_GetAuthByBrnc(sBrncCd){

	
		//본장 , 권한지점일 경우에만  수정 가능		
		if(fcd_GetUserBrncCd() == sCenterBrncCd || sBrncCd == fcd_GetUserBrncCd()){
			return true;	
		}
		
		return false;	
		
}


function fcd_SetEnableByBrnc(Obj,sBrncCd){
		var bObjEdit = false;

		//본장 , 권한지점일 경우에만  수정 가능		
		if(fcd_GetUserBrncCd() == sCenterBrncCd || sBrncCd == fcd_GetUserBrncCd()){
			bObjEdit = true;

		}else{
			bObjEdit = false;
		}

	
		if(Obj.getType() =="Grid"){		
			obj.Editable = bObjEdit;

		}else{
			obj.Enable = bObjEdit;
		}

}

function fcd_SetFormObjByBrnc(oForm, sBrncCd){

	var bObjEdit = fcd_GetAuthByBrnc(sBrncCd);

	for ( var i = 0 ; i < oForm.Components.Count ; i++ )
	{
		//dataset userData 속성 없음
		if(oForm.Components[i].getType() != "Dataset"){
	
			if ( ( oForm.Components[i].UserData == "S" )){
	
				if(oForm.Components[i].getType() =="Grid"){		
					oForm.Components[i].Editable = bObjEdit;	
				}else{			
					oForm.Components[i].Enable = bObjEdit;
				}			
			}
		}	
		
	}
}

// 입장관리  대메뉴의 이미지의 "New" 처리 여부
function fcd_SetRefuEntNew(bFlag) 
{
	frameTop.fcSetRefuEntNew(bFlag);	
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

