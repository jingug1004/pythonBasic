/*================================================================================================/
 * PROGRAM-ID                           template_app_script 
 * DESCRIPTION   :  �ش� ������ ���� Ʈ�����ó�� (���ֻ�������ý���)                                           
 * CREATE DATE   :  2011. 07. 21                                                            
 * UPDATE DATE   :  2011. 07. 21                                                          
 * PROGRAMMER    :                                                                          
=================================================================================================*/
#include "lib::rbm_common_script.js";
#include "lib::rbm_message_script.js";

/*
var sComCallType = "";		//ȣ�ⱸ�� 1-button 2-edit 
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
*  ��   ��  ��				: fcd_SendTransaction
*  ��       ��				: Transaction ����
*  param1) SvcName			: ���񽺸�
*  param2) ActionName		: �׼Ǹ�
*  param3) InDataset		: �Է� Dataset
*  param4) OutDataset		: ��� Dataset
*  param5) CallbackName		: Callback �Լ���
*  return Type				: boolean
*  return ����				: true/false
**************************************************************************************************/
 function fcd_SendTransaction(SvcName, InDataset, OutDataset, CallbackName, bProgress, sAccessType) {
    
    var sService = split(SvcName, ":");
    var JspSvc   = "DEFAULT_SERVER::handle.do?";
    //var Action   = "ServiceName=" + sService[0] + "&" + sService[1] + "=1";
    var Action   = "ServiceName=" + sService[0] + "&" + sService[1] + "=1&ActionName="+sService[1]; // �������� �α��ο����� �߰���
    var SvcParam = fcd_GetParam();
	 
	if ( bProgress ) {
		fnc_ProgressPop(); 
	} 
//Trace("fcd_SendTransaction::JSESSIONID="+JSESSIONID);
//Trace(JspSvc+Action+":"+SvcParam);	
	
	http.Sync = true;
	if (GBL_DUP_LOGIN_CHK != "Y") InDataset = InDataset + " gdsUser=gdsUser";	// ���� ���Ȼ��� ������ ���� ���(���缱)
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
*  ��   ��  ��				: fcd_SendPersonalTransaction
*  ��       ��				: Transaction ����
*  param1) SvcName			: ���񽺸�
*  param2) ActionName		: �׼Ǹ�
*  param3) InDataset		: �Է� Dataset
*  param4) OutDataset		: ��� Dataset
*  param5) CallbackName		: Callback �Լ���
*  return Type				: boolean
*  return ����				: true/false
**************************************************************************************************/
 function fcd_SendPersonalTransaction(SvcName, InDataset, OutDataset, CallbackName, bProgress, sAccessType) {
    
    var sService = split(SvcName, ":");
    var JspSvc   = "DEFAULT_SERVER::handle.do?";
    var Action   = "ServiceName=" + sService[0] + "&" + sService[1] + "=1&ActionName="+sService[1]; // �������� �α��ο����� �߰���
    var SvcParam = fcd_GetParam();
	 
	if ( bProgress ) {
		fnc_ProgressPop(); 
	} 
	
	http.Sync = true;
	if (GBL_DUP_LOGIN_CHK != "Y") InDataset = InDataset + " gdsUser=gdsUser";	// ���� ���Ȼ��� ������ ���� ���(���缱)
    transaction(SvcName, JspSvc + Action, InDataset, OutDataset, SvcParam, CallbackName);
	http.Sync = false;
}

/**************************************************************************************************
*  ��   ��  ��				: fcd_SSLTransaction
*  ��       ��				: Transaction ����
*  param1) SvcName			: ���񽺸�
*  param2) ActionName		: �׼Ǹ�
*  param3) InDataset		: �Է� Dataset
*  param4) OutDataset		: ��� Dataset
*  param5) CallbackName		: Callback �Լ���
*  return Type				: boolean
*  return ����				: true/false
**************************************************************************************************/
 function fcd_SSLTransaction(SvcName, InDataset, OutDataset, CallbackName, bProgress, sAccessType) {

    var sService = split(SvcName, ":");
    var JspSvc   = "SSL_SERVER::handle.do?";
    //var Action   = "ServiceName=" + sService[0] + "&" + sService[1] + "=1";
    var Action   = "ServiceName=" + sService[0] + "&" + sService[1] + "=1&ActionName="+sService[1]; // �������� �α��ο����� �߰���
    var SvcParam = fcd_GetParam();
	 
	if ( bProgress ) {
		fnc_ProgressPop(); 
	} 
//Trace("fcd_SendTransaction::JSESSIONID="+JSESSIONID);
//Trace(JspSvc+Action+":"+SvcParam);	
		
	http.Sync = true;
	if (GBL_DUP_LOGIN_CHK != "Y") InDataset = InDataset + " gdsUser=gdsUser";	// ���� ���Ȼ��� ������ ���� ���(���缱)
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
*  ��   ��  ��				: fcd_EncTransaction
*  ��       ��				: Transaction ����
*  param1) SvcName			: ���񽺸�
*  param2) ActionName		: �׼Ǹ�
*  param3) InDataset		: �Է� Dataset
*  param4) OutDataset		: ��� Dataset
*  param5) CallbackName		: Callback �Լ���
*  return Type				: boolean
*  return ����				: true/false
**************************************************************************************************/
function fcd_EncTransaction(SvcName, InDataset, OutDataset, CallbackName, bProgress, sAccessType) {

    var sService = split(SvcName, ":");
    var JspSvc   = "DEFAULT_SERVER::handle.do?";
    //var Action   = "ServiceName=" + sService[0] + "&" + sService[1] + "=1";
    var Action   = "ServiceName=" + sService[0] + "&" + sService[1] + "=1&ActionName="+sService[1]; // �������� �α��ο����� �߰���
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
	
	//var PostData = TextToBin("PA_OBKEY=strChulYno&PA_WKFTX=strJemok&PA_ERNAM=strSabeon"); //TextToBin�� �� �־�� Post�� ���������� ���޵�
	var PostData =TextToBin("SEND="+Enc_ret,"utf-8");
	var Headers = "Content-Type: application/x-www-form-urlencoded";
	var URL = "http://160.100.51.199:19080/rbm/seed_test.jsp?SEND="+Enc_ret;
	//SEED TEST END
			
	
	http.Sync = true;
	if (GBL_DUP_LOGIN_CHK != "Y") InDataset = InDataset + " gdsUser=gdsUser";	// ���� ���Ȼ��� ������ ���� ���(���缱)
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
*  ��   ��  ��				: fcd_GetComboDs
*  ��       ��				: �������� ���� �ڵ带 ������ outDataset�� �ֱ� ���� Transaction ����
*  param1) oDataSet         : ���� �ڵ� �׷���̵� ����� ������ �ִ� DataSet (dsInComCd)
*  return Type				: �ش� ����
*  return ����				: �ش� ����
**************************************************************************************************/
function fcd_GetComboDs(oDataSet) {

    //Combo List�� ������ �ִ� DataSet�� �̸� �� OBJECT ���� üũ
    if(!isValid(oDataSet) || oDataSet.ID!="dsInComCd") {
		fnc_Message(SNIS_COM_1011, "N");
        return;
    }

	var sServiceName = "common-service:searchCode";         // ������ ��û�� Sevice ��
	var sInDataSet   = oDataSet.ID + "=" + oDataSet.ID;     // Input DataSet ��Ī
	var sOutDataSet  = "";                                  // Output DataSet ��Ī

	var sDataSetName = "";                                  // DataSet Name  ���� ����
	for ( var i = 0; i < oDataSet.GetRowCount(); i++) {
		// InComCd Dataset�� ��ϵ� OutDataset �̸��� ����
        sDataSetName = oDataSet.GetColumn(i, "DSNAME");
        sOutDataSet += sDataSetName + "=" + sDataSetName + " ";
	}
	http.sync = true;
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
	http.sync = false;
}



/**************************************************************************************************
*  ��   ��  ��				: fcd_AddParam
*  ��       ��				: Transaction�� ���� Parameter�߰�
*  param1) ParamName		: Parameter ��
*  param2) ParamValue		: Parameter ��
*  return Type				: �ش� ����
*  return ����				: �ش� ����
**************************************************************************************************/
var gcra_SvcParam = Array(-1);   // Transaction���� ���Ǵ� Parameter

function fcd_AddParam(ParamName, ParamValue) {
    var aParam = Array(2);
    aParam[0] = ParamName;
    aParam[1] = ParamValue;

    gcra_SvcParam[length(gcra_SvcParam)] = aParam;
}



/**************************************************************************************************
*  ��   ��  ��				: fcd_GetParam
*  ��       ��				: Transaction�� ����� Parameter�� �߰�
*  return Type				: String
*  return ����				: Transaction�� Parameter
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
*  ��   ��  ��				: fcd_HelpDialoge
*  ��       ��				: ���ο� Popup â�� ���� ������ ���� ȭ�� Display
*  param1) sSearchKind		: Dialog ���� (�����ȣ, ���� �ڵ�, �μ�Ʈ��)
*  param2) ParamValue		: Dialogâ�� ��Ÿ�� Form Variable�� �ʱⰪ
*  param3) sCommonGubn		: 
*  param4) oFocusControl	: 
*  return Type				: Boolean
*  return ����				: Dialog�� ���������� �����ߴ��� ���� 
**************************************************************************************************/
function fcd_HelpDialoge(sSearchKind, sInputValue, sCommonGubn, oFocusControl) {

	var sFormName = "";              //ȣ���� Form ID

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
	
	//��� �� ��ȯ
	return sResponse;
}



/**************************************************************************************************
*  ��   ��  ��				: fcd_MultiRowDisplay
*  ��       ��				: ���ο� Popup â�� ���� �ڵ� ���� ȭ���� Display
*  param1) oForm			: Form Object
*  param2) vREVOBJ			: ������� ���� Text Object��
*  param3) sGRDTIT			: �׸��� ����
*  param4) sCUROBJ			: ���� LOST FOCUS ������Ʈ
*  param5) oDataset			: DataSet
*  return Type				: �ش� ����
*  return ����				: �ش� ����
**************************************************************************************************/
function fcd_MultiRowDisplay(oForm, vREVOBJ, sGRDTIT, sCUROBJ, oDataset) {

	// �˻��� �ڷᰡ �������̸� ���� ȭ�� Open 
	if ( gdsSelectItem.RowCount() > 1 ) {
		
		var iX = 0;
		var iY = 0;
		var i  = 0;
		var iFormX = ClientToScreenX(oForm,0);
		var iFormY = ClientToScreenY(oForm,0);
		var sResponse = "";
		
		//X ��ǥ ���ϱ�
		if ((oForm.Width + iFormX) >= (504 + ClientToScreenX(sCUROBJ, 0))) {
			iX = ClientToScreenX(sCUROBJ, 0) - 2;
		} else {
			iX = ClientToScreenX(sCUROBJ, 0) - (ClientToScreenX(sCUROBJ, 0) + 504 - oForm.Width - iFormX);
		}

		//Y ��ǥ ���ϱ�
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
				vREVOBJ[i] = "";        //������� ���� Text Object��
			}
			sCUROBJ  = "";
			return;
		}     
	}
	
	for (i=0; i<Length( vREVOBJ ); i++) {
		if (length(trim(vREVOBJ[i]))>0) object(vREVOBJ[i]) = gdsSelectItem.GetColumn(gdsSelectItem.Row , i);
		vREVOBJ[i] = "";        //������� ���� Text Object��
	}
	sCUROBJ  = "";
	
}



/**************************************************************************************************
*  ��   ��  ��				: fcd_GetSysdate
*  ��       ��				: System ��¥ �� �ð��� ��ȸ�Ѵ�.
*  param1) sFormat			: ���� ����
*  return Type				: String
*  return ����				: �ش� ��¥ �� �ð�
**************************************************************************************************/
function fcd_GetSysdate(sFormat, sComm) {

	var sReturn = "";
	if (sComm != "SERVER") {
		sReturn = fcd_GetSysdate_local(sFormat);
		return sReturn;
	}
	var sServiceName = "common-service:searchSysdate";       // ������ ��û�� Sevice ��
	var sInDataSet   = "";                                   // Input DataSet ��Ī
	var sOutDataSet  = "gdsSysdate=dsOutSysdate";            // Output DataSet ��Ī

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
/*  PC�� �ð����� Return ************************** */
function fcd_GetSysdate_local(sFormat) {

	var sReturn;
	var sLocatTime = GetDate();   //yyyyMMddhhmmss�� 14�ڸ� ���ڿ��� ���� Return
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
*  ��   ��  ��				: fcd_SetStndYear
*  ��       ��				: ���س⵵ ����
*  param1) oDataset			: �۾���� Dataset Object
*  param2) nStartYear       : ���۳�
*  param3) nLastYear     	: ��������
*  param4) nOrderGbn		: ���ı���  1: �������� , 0 :  ��������
*  return Type				: �ش� ����
*  return ����				: �ش� ����
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
*  ��   ��  ��				: fcd_AddCodeRow
*  ��       ��				: ���ο� Popup â�� ���� �ڵ� ���� ȭ���� Display
*  param1) oDataset			: �۾���� Dataset Object
*  param2) sCodeNameValue   : �ش� �ڵ��
*  param3) sCodeIDValue  	: �ش� �ڵ尪
*  param4) oCombo   		: Bind �� �޺� object
*  param4) bFirst   		: �߰��� �ڵ尪�� ��ġ(true : ó��)
*  return Type				: �ش� ����
*  return ����				: �ش� ����
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
*  ��   ��  ��				: fcd_GetSelectFile
*  ��       ��				: ������ ���ϸ��� ���Ѵ�.
*  return Type				: Stirng
*  return ����				: ���ϸ�
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
			= "�̹���(BMP;GIF;JPEG;TIFF;PNG)|*.bmp;*.dib;*.GIF;*.JPG;*.JPEG;*.JRE;*.JFIF;*.TIF;*.TIFF;*.PNG|"
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
*  ��   ��  ��				: fcd_GetYear
*  ��       ��				: ���س⵵ ����
*  return Type				: �ش� ����
*  return ����				: �ش� ����
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
*  ��   ��  ��				: fcd_GetRound
*  ��       ��				: �ش� �⵵�� ȸ����ȸ  
*  return Type				: ����
*  return ����				: ����
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
*  ��   ��  ��				: fcd_GetRaceNo
*  ��       ��				: �ش� �⵵�� ȸ����ȸ  
*  return Type				: ����
*  return ����				: ����
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
*  ��   ��  ��				: fcd_GetRaceNo
*  ��       ��				: �ش� �⵵�� ȸ����ȸ (������������) 
*  return Type				: ����
*  return ����				: ����
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
*  ��   ��  ��				: fcd_SetRaceInfo
*  ��       ��				: private Function -�������� Combo �������� DataSet ���� �� �⺻�ڷ�Setting
*  return Type				: String
*  return ����				: return Dataset
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
*  ��   ��  ��				: fcd_MakeDsRaceInfo
*  ��       ��				: private Function -�������� Dataset ����
*  return Type				: ����
*  return ����				: ����
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
*  ��   ��  ��				: fcd_GetRaceInfo
*  ��       ��				: �������� Combo�� ����Ÿ ����
*  return Type				: ����
*  return ����				: ����
**************************************************************************************************/
function fcd_GetRaceInfo(formId, cboStndYear, cboMeetCd, cboTms, cboDayOrd, cboRaceNo, bTmsAll, varOptional) {
	var sServiceName = "common-service:searchRaceInfoEx";
    var sInDataSet   = "dsRaceInfo=dsRaceInfo ";
    var sOutDataSet  = "";	

	fcd_MakeDsRaceInfo();
	
	sOutDataSet = sOutDataSet + fcd_SetRaceInfo(formId, cboStndYear	, dsRaceInfo, 1, "");	//���ֳ⵵
	sOutDataSet = sOutDataSet + fcd_SetRaceInfo(formId, cboMeetCd	, dsRaceInfo, 2, "");	//������
	sOutDataSet = sOutDataSet + fcd_SetRaceInfo(formId, cboTms		, dsRaceInfo, 3, iif(bTmsAll, "ALL", ""));	// ȸ��
	
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
*  ��   ��  ��				: fcd_GetRaceInfoEx
*  ��       ��				: ����ȸ�� �������� Combo�� ����Ÿ ����
*  return Type				: ����
*  return ����				: ����
**************************************************************************************************/
function fcd_GetRaceInfoEx(formId, cboStndYear, cboMeetCd, cboTms, cboDayOrd, cboRaceNo, bTmsAll, varOptional) {
	var sServiceName = "common-service:searchRaceInfoEx";
    var sInDataSet   = "dsRaceInfo=dsRaceInfo ";
    var sOutDataSet  = "";	

	fcd_MakeDsRaceInfo();
	
	sOutDataSet = sOutDataSet + fcd_SetRaceInfo(formId, cboStndYear	, dsRaceInfo, 1, "");	//���ֳ⵵
	sOutDataSet = sOutDataSet + fcd_SetRaceInfo(formId, cboTms		, dsRaceInfo, 4, "");	//����ȸ��
	sOutDataSet = sOutDataSet + fcd_SetRaceInfo(formId, cboMeetCd	, dsRaceInfo, 5, "");	//������
	
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
*  ��   ��  ��				: fcd_GetTms
*  ��       ��				: ȸ��,����,���ֹ�ȣ Combo�� ����Ÿ ����
*  return Type				: ����
*  return ����				: ����
**************************************************************************************************/
function fcd_GetTms(formId, cboTms, stndYear, meetCd, bTmsAll, cboDayOrd, cboRaceNo, varOptional) {
	var sServiceName = "common-service:searchRaceInfoEx";
    var sInDataSet   = "dsRaceInfo=dsRaceInfo ";
    var sOutDataSet  = "";	
	var nRow = 0;
	fcd_MakeDsRaceInfo();
	
	sOutDataSet = sOutDataSet + fcd_SetRaceInfo(formId, cboTms	, dsRaceInfo, 3, iif(bTmsAll, "ALL", ""));	// ȸ��
	
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
*  ��   ��  ��				: fcd_GetStndTmsEx
*  ��       ��				: ȸ��,����,���ֹ�ȣ Combo�� ����Ÿ ����
*  return Type				: ����
*  return ����				: ����
**************************************************************************************************/
function fcd_GetStndTmsEx(formId, stndYear, obj, tmsStat, varOptional) {
	var sServiceName = "common-service:searchRaceInfoEx";
    var sInDataSet   = "dsRaceInfo=dsRaceInfo ";
    var sOutDataSet  = "";	
	var nRow = 0;
	
	fcd_MakeDsRaceInfo();
	
	if (obj.GetType() == "Combo") {
		sOutDataSet = sOutDataSet + fcd_SetRaceInfo(formId, obj, dsRaceInfo, 4, "");	// ȸ��
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
*  ��   ��  ��				: fcd_GetStndMeetCd
*  ��       ��				: ����ȸ�� �������� Combo�� ����Ÿ ����
*  return Type				: ����
*  return ����				: ����
**************************************************************************************************/
function fcd_GetStndMeetCd(formId, stndYear, stndTms, cboMeetCd, cboDayOrd, cboRaceNo, varOptional) {
	var sServiceName = "common-service:searchRaceInfoEx";
    var sInDataSet   = "dsRaceInfo=dsRaceInfo ";
    var sOutDataSet  = "";	

	fcd_MakeDsRaceInfo();
	
	sOutDataSet = sOutDataSet + fcd_SetRaceInfo(formId, cboMeetCd	, dsRaceInfo, 5, "");	//������
	
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
*  ��   ��  ��				: fcd_GetStndTmsEx1
*  ��       ��				: ȸ��,����,���ֹ�ȣ Combo�� ����Ÿ ����
*  return Type				: ����
*  return ����				: ����
**************************************************************************************************/
function fcd_GetStndTmsEx1(formId, stndYear, obj, cboMeetCd, cboDayOrd, cboRaceNo, varOptional) {
	var sServiceName = "common-service:searchRaceInfoEx";
    var sInDataSet   = "dsRaceInfo=dsRaceInfo ";
    var sOutDataSet  = "";	
	var nRow = 0;
	
	fcd_MakeDsRaceInfo();
	
	if (obj.GetType() == "Combo") {
		sOutDataSet = sOutDataSet + fcd_SetRaceInfo(formId, obj, dsRaceInfo, 4, "");	// ȸ��
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
*  ��   ��  ��				: fcd_GetDayOrd
*  ��       ��				: ����,���ֹ�ȣ Combo�� ����Ÿ ����
*  return Type				: ����
*  return ����				: ����
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
*  ��   ��  ��				: fcd_GetRaceNoEx
*  ��       ��				: ���ֹ�ȣ Combo�� ����Ÿ ����
*  return Type				: ����
*  return ����				: ����
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
*  ��   ��  ��				: fcd_GetTmsDate
*  ��       ��				: ȸ���� ���ֽ������� ��ȸ
*  return Type				: String
*  return ����				: ������
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
*  ��   ��  ��				: fcd_GetTmsDate
*  ��       ��				: ȸ���� ���ֽ������� ��ȸ
*  return Type				: String
*  return ����				: ������
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
*  ��   ��  ��				: fcd_GetTmsCFM
*  ��       ��				: ȸ����  Ȯ������ �� ��Ұ��ɿ��� ��ȸ
*  return Type				: Array
*  return ����				: 0:Ȯ������,1:��Ұ��ɿ���, 2:���¸�Ī, 3:Ȯ�����ɿ���, 4:��������ڵ�
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
*  ��   ��  ��				: fcd_SetTmsCFM
*  ��       ��				: Ȯ�� �� Ȯ����� ����
*  return Type				: 
*  return ����				: 
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
*  ��   ��  ��				: fcd_SetTmsCFM
*  ��       ��				: Ȯ�� �� Ȯ����� ����
*  return Type				: 
*  return ����				: 
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
*  ��   ��  ��				: fcd_ExecApproval
*  ��       ��				: ���ο� ���ڰ��� ����
*  return Type				: 
*  return ����				: 
*  �Ķ���� : ��������, ���س⵵, ȸ��, ����, ���ֹ�ȣ, ����, �����, ��������, �����ͼ�ID, ����, ����Type
**************************************************************************************************/
function fcd_ExecApproval(job, stndYear, tms, dayOrd, raceNo, seq, reportId, subJect, fileObj, conObj, fileGubun) {	
    var sServiceName    = "common-service:searchApproSeqNo";
    var sInDataSet      = "";
    var sOutDataSet     = "";
    var sDocType        = "����";
    
    var varArray        = Array(0);
    var ExportObject1;
    var FilePath;
    var AttFilePath;
    var FileName;
    var OSVersion;
    
    OSVersion = trim(GetDeviceInfo("OSVERSION"));
    
    //==============================================================1.�ʼ��Է� �� üũ
    if(fnc_IsNull(job)) {
        fnc_Message(SNIS_COM_0001, "SERVERMSG", "��������");
        return;
    }
    if(fnc_IsNull(stndYear)) {
        fnc_Message(SNIS_COM_0001, "SERVERMSG", "���س⵵");
        return;
    }
    if(fnc_IsNull(tms)) {
        fnc_Message(SNIS_COM_0001, "SERVERMSG", "ȸ��");
        return;
    }
    if(fnc_IsNull(reportId)) {
        fnc_Message(SNIS_COM_0001, "SERVERMSG", "�����");
        return;
    }
    if(fnc_IsNull(subJect)) {
        fnc_Message(SNIS_COM_0001, "SERVERMSG", "��������");
        return;
    }
    if(fnc_IsNull(dayOrd))          dayOrd="0";             //���� null���� ��� '0'���� ����
    if(fnc_IsNull(raceNo))          raceNo="00";            //���ֹ�ȣ null���� ��� '00'���� ����
    if(fnc_IsNull(fileGubun))       fileGubun="Excel";      //fileGubun ���� �������� ������ ������ �Ѵ�.
    if(job == 'EC' || job == 'CC')  sDocType="�ϰ�";        //��������Ƿ�, ����������� ���� ���������� '�ϰ�'�� �ѱ��.
    //============================================================================
    
    //==============================================================2.�Ϸù�ȣ ����&������� �Է�
    sServiceName    = "common-service:saveApproval";
    sInDataSet      = "";
    sOutDataSet     = "";
    
	fcd_AddParam("JOB", job);               //��������
	fcd_AddParam("STND_YEAR", stndYear);    //���س⵵
	fcd_AddParam("TMS", tms);               //ȸ��
	fcd_AddParam("DAY_ORD", dayOrd);        //����
	fcd_AddParam("RACE_NO", raceNo);        //���ֹ�ȣ
	fcd_AddParam("USER_ID", reportId);      //�����
	fcd_AddParam("SUBJECT", subJect);       //��������
		
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
    //���丮 ���� üũ�ؼ� ������ �������ش�.(���뽺ũ��Ʈ ȣ��(strPath))
    fnc_IsDirectory(AttFilePath);
    //===============================================================3.÷������ ����
    if(!fnc_IsNull(fileObj)) {   //�����ͼ� ID
        // Null �ƴϸ� ÷������ �����ϴ� ������ �����Ѵ�.
        
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
            ExportObject1.Export(true, false);                              // ù��° progress, �ι�° excel
            ExportObject1.Save();  
            ExportObject1.CloseWorkbook();	
            
            ExportObject1.Close();
            ExportObject1 = null;
        }
        
    }else{
        FileName = "";
    }
    //============================================================================
    
    //==============================================================4.html���� ����
    if(!fnc_IsNull(conObj)) {   //���� ������Ʈ ID
        // Null �ƴϸ� �Ѿ�� ��ü�� �������� Html������ �����ϴ� ���� ����.        
        fnc_CreateHtmlFile(FilePath+varArray[0]+".html", conObj.text);      
                
    }else{
        // Null �̸� ��������� �����ϰ� Html���� �����ϴ� ���� ����
        fnc_CreateHtmlFile(FilePath+varArray[0]+".html", subJect);
    }
    //=============================================================================    
    
    
    //==============================================================5.�۽�URL ȣ��
    //�׽�Ʈ ���� : http://140.100.100.24:8088/UFWeb/public/signContact.jsp
    //�ǿ ���� : http://jupiter.sosfo.or.kr/UFWeb/sign/hwpsign/ActiveMis/signContact.jsp
    ExecBrowser("http://140.100.100.24:8088/UFWeb/public/signContact.jsp?GUBUNID="+varArray[0]+"&BIZ=cy&JOB="+job+"&CREATTYPE=����&USER_ID="+reportId+"&SUBJECT="+subJect+"&ATTNAME="+FileName+"&SEQ="+seq);
    
    //ExecBrowser("http://jupiter.sosfo.or.kr/UFWeb/public/signContact.jsp?GUBUNID="+varArray[0]+"&BIZ=cy&JOB="+job+"&CREATTYPE="+sDocType+"&USER_ID="+reportId+"&SUBJECT="+subJect+"&ATTNAME="+FileName+"&SEQ="+seq);
    //=============================================================================
      
}


/**************************************************************************************************
*  ��   ��  ��				: fcd_SendMessage
*  ��       ��				: �޽�������
*  return Type				: 
*  return ����				: 
*  �Ķ����                 : �߽Ż����ȣ, ���Ż����ȣ, ����, ��ũ����, ����
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
    
    //1.�ʼ��Է� �� üũ
    if(fnc_IsNull(sendUser)) {
        fnc_Message(SNIS_COM_0001, "SERVERMSG", "�����»��");
        return;
    }
    if(fnc_IsNull(recvUser)) {
        fnc_Message(SNIS_COM_0001, "SERVERMSG", "�޴»��");
        return;
    }
    if(fnc_IsNull(title)) {
        fnc_Message(SNIS_COM_0001, "SERVERMSG", "����");
        return;
    }
    /*if(fnc_IsNull(linkUrl)) {
        fnc_Message(SNIS_COM_0001, "SERVERMSG", "��ũ����");
        return;
    }*/
    if(fnc_IsNull(desc)) {
        fnc_Message(SNIS_COM_0001, "SERVERMSG", "����");
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
		
		//����
        obj.Navigate(GBL_SERVER_URL+"send.jsp?SENDUSER="+sendUser+"&RECVUSER="+aRecvId[i]+"&TITLE="+title+"&LINKURL="+tmpServerURL+"&DESC="+desc);
	
        //  trace("send.jsp�� ȣ���ϴ� �κ� >>>> " + tmpServerURL);
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
    
	//����
	//obj.Navigate(GBL_SERVER_URL+"send.jsp?SENDUSER="+sendUser+"&RECVUSER="+recvUser+"&TITLE="+title+"&LINKURL="+tmpServerURL+"&DESC="+desc);
	//fcd_SetAlarmHist( "001",  replace(recvUser,"\"",""), GBL_MENUID, "", replace(title,"\"",""), replace(desc,"\"",""));
    //Trace(GBL_SERVER_URL+"send.jsp?SENDUSER="+sendUser+"&RECVUSER="+recvUser+"&TITLE="+title+"&LINKURL="+tmpServerURL+"&DESC="+desc);    
    //Trace("fcd_SetAlarmHist::");
    fcd_SetAlarmHist( "001", recvUser, GBL_MENUID, "", replace(title,"\"",""), replace(desc,"\"",""), tmpServerURL);
    //���� �׽�Ʈ��
    //obj.Navigate("http://localhost:8080/rbm/send.jsp?SENDUSER="+sendUser+"&RECVUSER="+recvUser+"&TITLE="+title+"&LINKURL= &DESC="+desc);
    
    //obj.Navigate(GBL_SERVER_URL+"send.jsp?SENDUSER="+sendUser+"&RECVUSER="+recvUser+"&TITLE="+title+"&LINKURL="+ServerURL+"&DESC="+desc);
    
    //�Ǽ��� URL
    //var pageUrl="http://rbm.kcycle.or.kr/send2.jsp?SENDUSER="+sendUser+"&RECVUSER="+recvUser+"&TITLE="+title+"&LINKURL="+ServerURL+"&DESC="+desc+"&MSGNO="+MsgNo;
	
    
    //fcd_SetAlarmHist( "001",  replace(recvUser,"\"",""), GBL_MENUID, "", replace(title,"\"",""), replace(desc,"\"",""));
    //obj.Navigate(pageUrl);
    //Trace(pageUrl);    
    
	//Destroy(sCompID);	   

}

/**************************************************************************************************
*  ��   ��  ��				: fcd_GetBzInfo()
*  ��       ��				: ��������� ��ȸ 
*  return Type				: Array  [0] : ����ڵ�Ϲ�ȣ, [1] : ���θ�, [2] : ��ǥ�ڸ�, 
*                                     [3] : ���ε�Ϲ�ȣ, [4] : �ּ�
*  return ����				: 
*  �Ķ����                 : 
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
		arrBzInfo[0] = dsBzInfo.GetColumn(0, "BZ_INFO0"); // ����ڵ�Ϲ�ȣ
		arrBzInfo[1] = dsBzInfo.GetColumn(0, "BZ_INFO1"); // ���θ�
		arrBzInfo[2] = dsBzInfo.GetColumn(0, "BZ_INFO2"); // ��ǥ�ڸ�
		arrBzInfo[3] = dsBzInfo.GetColumn(0, "BZ_INFO3"); // ���ε�Ϲ�ȣ
		arrBzInfo[4] = dsBzInfo.GetColumn(0, "BZ_INFO4"); // �ּ�
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
*  ��   ��  ��				: fcd_SendSms
*  ��       ��				: SMS����
*  return Type				: 
*  return ����				: 
*  �Ķ����                 

			strSendPhoneNumber = "";		// �۽��� ��ȣ
			strSendUserId = "";			// �۽��� ���			(��:05073)
			strSendUserName = "";		// �۽��� ����(��:ȫ�浿)	
			strReceivePhoneNumber = "";	// ������ ��ȣ, �������ΰ�� ; �� ���� (��:01020717775)
			strReceiveName = "";			// �����ڸ�, �������ΰ�� ; �� ����
			strSendSubject = "";			// ������ ����. (80Bytes����)
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
					, aRecvPh[i] 		//������ ��ȣ
					, strSendSubject
					, strSendContent
					, strSendPhoneNumber
					, strSendUserName
					, aRecvNm[i]
					, strReqTime);   
   }
   */
   var called_menu_id = "";
   
	if (strSendSubject=="����SMS����") {
		called_menu_id = "R000000";
	} else {
		called_menu_id = GBL_MENUID;
	}
   
   // �ϰ��������� ����
   fcd_SetAlarmHist(	 "002" 
					, strReceiveId
					, GBL_MENUID
					, strReceivePhoneNumber 		//������ ��ȣ
					, strSendSubject
					, strSendContent
					, null
					, strSendPhoneNumber
					, strSendUserName
					, strReceiveName
					, strReqTime);  
   
   
}


/**************************************************************************************************
*  ��   ��  ��				: fcd_SetAlarmHist
*  ��       ��				: �˶��̷� ���� 
*  return Type				: 
*  return ����				: 
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
*  ��   ��  ��				: fcd_GetRaceNo
*  ��       ��				: �ش� �⵵�� ȸ����ȸ  
*  return Type				: ����
*  return ����				: ����
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
*  ��   ��  ��				: fcd_GetAlarmDs
*  ��       ��				: �˸� ����� ��ȸ
*  return Type				: ����
*  return ����				: ����
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
*  ��   ��  ��				: fcd_SendMsgByAlarmDs
*  ��       ��				: �˸� ����� ��ȸ  (callback (fcAlarmCallBack) ����  ����/SMS ������ )
*  return Type				: ����
*  return ����				: ����
*  �Ķ����                 

	sAlarmCd 		= "";		// �˸��ڵ� (�����ڵ�  080)
	sSendUserId 	= "";		// �۽��� ���(��:05073)
	sSendUserNm 	= "";		// �۽��� ����(��:ȫ�浿)	
	sSendUserPhone 	= "";		// ������ ��ȣ, �������ΰ�� ; �� ���� (��:01020717775)
	sTitle 			= "";		// ����
	sDesc 			= "";		// ����
	sLinkMnId 		= "";		// �������۽� LINK �޴� ID
			
			
**************************************************************************************************/
function fcd_SendMsgByAlarmDs(sAlarmCd, sSendUserId, sSendUserNm, sSendUserPhone, sTitle, sDesc, sLinkMnId)
{
	gdsAlarmMng.clearData();	//�˸� ����� 
	gdsAlarmInfo.clearData();   //�˸� ����
	
	
	//SMS, ���� ���� ���� GDS ���� 
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
*  ��   ��  ��				: fcAlarmCallBack
*  ��       ��				: �˸� ����� �޼��� ����
*  return Type				: ����
*  return ����				: ����
**************************************************************************************************/
function fcAlarmCallBack(sServiceName, ErrorCode, ErrorMSG) {

	// ��ȸ �Ϸ� ��
	if ( sServiceName == "common-service:searchAlarmCd" ) {

		if(gdsAlarmMng.getRowCount()>0){
		
			var sSendUserId 	= gdsAlarmInfo.GetColumn(0, "SEND_USER_ID" );		//������ ��� ��� 
			var sSendUserNm		= gdsAlarmInfo.GetColumn(0, "SEND_USER_NM");		//������ ��� ����� 
			var sSendUserPhone 	= gdsAlarmInfo.GetColumn(0, "SEND_USER_PHONE");		//������ ��� HP��ȣ 
			
			var sTitle			= gdsAlarmInfo.GetColumn(0, "TITLE");				//���� ���� (���ڳ���)
			var sDesc 			= gdsAlarmInfo.GetColumn(0, "DESC");				//��������
			var sLinkMnId 		= gdsAlarmInfo.GetColumn(0, "LINK_MN_ID");			//LINK �޴� ID 
			
			var sSmsReceivePhone="";		//SMS ���� �����  HP��ȣ 
		    var sSmsReceiveName="";			//SMS ���� �����  �����
		    var sSmsReceiveId ="";			//SMS ���� �����  ��� 
		    
		    var sMsgReceiveId ="";			//���� ���� ����� ���
	
			for(var i = 0; i <gdsAlarmMng.GetRowCount();i++){
					
			    //���� �˸� ����� 
				if(gdsAlarmMng.GetColumn(i,"MSG_SEND_YN") == "Y"){				
					sMsgReceiveId += gdsAlarmMng.GetColumn(i,"USER_ID")+",";
				}
			
				//SMS �˸� ����� 
				if(gdsAlarmMng.GetColumn(i,"SMS_SEND_YN") == "Y"){
					sSmsReceivePhone += gdsAlarmMng.GetColumn(i,"HP_NO") +",";
					sSmsReceiveName += gdsAlarmMng.GetColumn(i,"USER_NM")+",";
					sSmsReceiveId += gdsAlarmMng.GetColumn(i,"USER_ID")+",";
				}

			}
		

			//SMS  �˸�
			if(Length(sSmsReceiveId) >0){
				  sSmsReceivePhone= sSmsReceivePhone.SubStr(0,Length(sSmsReceivePhone)-1);
				  sSmsReceiveName=sSmsReceiveName.SubStr(0,Length(sSmsReceiveName)-1);
				  sSmsReceiveId =sSmsReceiveId.SubStr(0,Length(sSmsReceiveId)-1);

				  fcd_SendSms(	sSendUserPhone 					// �۽��� ��ȣ
						, sSendUserId	    					// �۽��� ��� 
						, sSendUserNm   						// �۽��� ����
						, sSmsReceivePhone  					//������ ��ȣ
						, sSmsReceiveName	   					//�����ڸ�
						, sTitle
						, sSmsReceiveId
						, sDesc
				  );
			}

		    //���� �˸� 
	  		if(Length(sMsgReceiveId) >0){
					 sMsgReceiveId =sMsgReceiveId.SubStr(0,Length(sMsgReceiveId)-1);
					 
					 fcd_SendMessage(sSendUserId				// �۽��� ��� 
						, sMsgReceiveId 						// ������ ��� 
						, sTitle	    					    // ����
						, sLinkMnId 							// ������ ��ȣ
						, sDesc									// ���� 
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

//�����ڵ� 
var sCenterBrncCd = "00";
function fcd_GetAuthByBrnc(sBrncCd){

	
		//���� , ���������� ��쿡��  ���� ����		
		if(fcd_GetUserBrncCd() == sCenterBrncCd || sBrncCd == fcd_GetUserBrncCd()){
			return true;	
		}
		
		return false;	
		
}


function fcd_SetEnableByBrnc(Obj,sBrncCd){
		var bObjEdit = false;

		//���� , ���������� ��쿡��  ���� ����		
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
		//dataset userData �Ӽ� ����
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

// �������  ��޴��� �̹����� "New" ó�� ����
function fcd_SetRefuEntNew(bFlag) 
{
	frameTop.fcSetRefuEntNew(bFlag);	
}



/*
 * ���� Dataset�� ������ ���� return Dataset�� ī���Ѵ�.
 *
 * param dsSrc - ���� Dataset ��ü
 * param rowIdxs - ������ index, �������� ��� '^'���ڷ� �����Ѵ�. (0^1^2^3^4 ...) : Nullable
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
*  ��   ��  ��				: fcd_SavePersonalAccess
*  ��       ��				: �������������� ���ٱ���� ����
*  param1) FormID           : ȣ���� ȭ�� ID
*  param2) AccessType       : ���� ����(S:��ȸ, U:����)
*  param3) Action Name      : ��û�� ����(Action)
*  param4) Parameter        : ���� ��û���� �Ķ����
*  return Type				: �ش� ����
*  return ����				: �ش� ����
**************************************************************************************************/
function fcd_SavePersonalAccess(sFormId, sAccessType, sAction, sParam)
{
	if (fnc_isNull(sFormId)) return;
	
	var sServiceName = "common_login-service:SaveUserTrace";         // ������ ��û�� Sevice ��
	var sInDataSet   = "";     // Input DataSet ��Ī
	var sOutDataSet  = "";                                  // Output DataSet ��Ī
	
	fcd_AddParam("JOB_TYPE", 	"P");   
	fcd_AddParam("LOG_SEQ", 	gdsUser.GetColumn(0, "LOG_SEQ"));   
	fcd_AddParam("FORM_ID", 	sFormId);   
	fcd_AddParam("ACCESS_TYPE", sAccessType);   
	fcd_AddParam("ACTION", 		sAction);
	fcd_AddParam("PARAM", 		sParam);
	   
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
}

/**************************************************************************************************
*  ��   ��  ��				: fcd_SavePersonalMnAccess
*  ��       ��				: �������������� ���ٱ���� ����
*  param1) FormID           : ȣ���� ȭ�� ID
*  param2) AccessType       : ���� ����(S:��ȸ, U:����)
*  param3) Action Name      : ��û�� ����(Action)
*  param4) Parameter        : ���� ��û���� �Ķ����
*  return Type				: �ش� ����
*  return ����				: �ش� ����
**************************************************************************************************/
function fcd_SavePersonalMnAccess(sFormId, sAccessType, sAction, sParam)
{	
	if (fnc_isNull(sFormId)) return;
	
	var sServiceName = "common_login-service:SavePersonalMnAccess";		// ������ ��û�� Sevice ��
	var sInDataSet   = "";     											// Input DataSet ��Ī
	var sOutDataSet  = "";                                  			// Output DataSet ��Ī
	
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

