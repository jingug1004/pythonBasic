/*================================================================================================/
 * PROGRAM-ID                           template_app_script 
 * DESCRIPTION   :  �ش� ������ ���� Ʈ�����ó��                                            
 * CREATE DATE   :  2007. 10. 16                                                            
 * UPDATE DATE   :  2007. 10. 16                                                           
 * PROGRAMMER    :                                                                          
=================================================================================================*/
#include "lib::can_common_script.js";
#include "lib::can_message_script.js";


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
	InDataset = InDataset + " gdsUser=gdsUser";
    transaction(SvcName, JspSvc + Action, InDataset, OutDataset, SvcParam, CallbackName);
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
	
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
}



/**************************************************************************************************
*  ��   ��  ��				: fcd_AddParam
*  ��       ��				: Transaction�� ���� Parameter�߰�
*  param1) ParamName		: Parameter ��
*  param2) ParamValue		: Parameter ��
*  return Type				: �ش� ����
*  return ����				: �ش� ����
**************************************************************************************************/
var gcan_SvcParam = Array(-1);   // Transaction���� ���Ǵ� Parameter

function fcd_AddParam(ParamName, ParamValue) {
    var aParam = Array(2);
    aParam[0] = ParamName;
    aParam[1] = ParamValue;

    gcan_SvcParam[length(gcan_SvcParam)] = aParam;
}



/**************************************************************************************************
*  ��   ��  ��				: fcd_GetParam
*  ��       ��				: Transaction�� ����� Parameter�� �߰�
*  return Type				: String
*  return ����				: Transaction�� Parameter
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
function fcd_GetSysdate(sFormat) {

	var sServiceName = "common-service:searchSysdate";       // ������ ��û�� Sevice ��
	var sInDataSet   = "";                                   // Input DataSet ��Ī
	var sOutDataSet  = "gdsSysdate=dsOutSysdate";            // Output DataSet ��Ī

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
*  ��   ��  ��				: fcd_SetStndYear
*  ��       ��				: ���س⵵ ����
*  param1) oDataset			: �۾���� Dataset Object
*  param2) nStartYear       : ���۳�
*  param3) nLastYear     	: ��������
*  return Type				: �ش� ����
*  return ����				: �ش� ����
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
*  ��   ��  ��				: fce_GetSelectFile
*  ��       ��				: ������ ���ϸ��� ���Ѵ�.
*  return Type				: Stirng
*  return ����				: ���ϸ�
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
	
	var sServiceName = "common_login-service:savePersonalAccess";         // ������ ��û�� Sevice ��
	var sInDataSet   = "";     // Input DataSet ��Ī
	var sOutDataSet  = "";                                  // Output DataSet ��Ī

	//fcd_AddParam("JOB_TYPE", 	"P");   
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