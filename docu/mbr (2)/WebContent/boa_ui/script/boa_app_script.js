/*================================================================================================/
 * PROGRAM-ID                           template_app_script 
 * DESCRIPTION   :  �ش� ������ ���� Ʈ�����ó��                                            
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
*  ��   ��  ��				: fce_SendTransaction
*  ��       ��				: Transaction ����
*  param1) SvcName			: ���񽺸�
*  param2) ActionName		: �׼Ǹ�
*  param3) InDataset		: �Է� Dataset
*  param4) OutDataset		: ��� Dataset
*  param5) CallbackName		: Callback �Լ���
*  return Type				: boolean
*  return ����				: true/false
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
 function fce_SendPersonalTransaction(SvcName, InDataset, OutDataset, CallbackName, bProgress, sAccessType) {
    
    var sService = split(SvcName, ":");
    var JspSvc   = "DEFAULT_SERVER::handle.do?";
    var Action   = "ServiceName=" + sService[0] + "&" + sService[1] + "=1&ActionName="+sService[1]; // �������� �α��ο����� �߰���
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
*  ��   ��  ��				: fce_GetComboDs
*  ��       ��				: �������� ���� �ڵ带 ������ outDataset�� �ֱ� ���� Transaction ����
*  param1) oDataSet         : ���� �ڵ� �׷���̵� ����� ������ �ִ� DataSet (dsInComCd)
*  return Type				: �ش� ����
*  return ����				: �ش� ����
**************************************************************************************************/
function fce_GetComboDs(oDataSet) {

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
	//http.Sync = true;		// ���� ó���� ���� �߻���(2014.12.24)
	fce_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
	//http.Sync = false;
}



/**************************************************************************************************
*  ��   ��  ��				: fce_AddParam
*  ��       ��				: Transaction�� ���� Parameter�߰�
*  param1) ParamName		: Parameter ��
*  param2) ParamValue		: Parameter ��
*  return Type				: �ش� ����
*  return ����				: �ش� ����
**************************************************************************************************/
var gboa_SvcParam = Array(-1);   // Transaction���� ���Ǵ� Parameter

function fce_AddParam(ParamName, ParamValue) {
    var aParam = Array(2);
    aParam[0] = ParamName;
    aParam[1] = ParamValue;

    gboa_SvcParam[length(gboa_SvcParam)] = aParam;
}



/**************************************************************************************************
*  ��   ��  ��				: fce_GetParam
*  ��       ��				: Transaction�� ����� Parameter�� �߰�
*  return Type				: String
*  return ����				: Transaction�� Parameter
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
*  ��   ��  ��				: fce_HelpDialoge
*  ��       ��				: ���ο� Popup â�� ���� ������ ���� ȭ�� Display
*  param1) sSearchKind		: Dialog ���� (�����ȣ, ���� �ڵ�, �μ�Ʈ��)
*  param2) ParamValue		: Dialogâ�� ��Ÿ�� Form Variable�� �ʱⰪ
*  param3) sCommonGubn		: 
*  param4) oFocusControl	: 
*  return Type				: Boolean
*  return ����				: Dialog�� ���������� �����ߴ��� ���� 
**************************************************************************************************/
function fce_HelpDialoge(sSearchKind, sInputValue, sCommonGubn, oFocusControl) {

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
*  ��   ��  ��				: fce_MultiRowDisplay
*  ��       ��				: ���ο� Popup â�� ���� �ڵ� ���� ȭ���� Display
*  param1) oForm			: Form Object
*  param2) vREVOBJ			: ������� ���� Text Object��
*  param3) sGRDTIT			: �׸��� ����
*  param4) sCUROBJ			: ���� LOST FOCUS ������Ʈ
*  param5) oDataset			: DataSet
*  return Type				: �ش� ����
*  return ����				: �ش� ����
**************************************************************************************************/
function fce_MultiRowDisplay(oForm, vREVOBJ, sGRDTIT, sCUROBJ, oDataset) {

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
			return false;
		}     
	}
	
	for (i=0; i<Length( vREVOBJ ); i++) {
		if (length(trim(vREVOBJ[i]))>0) object(vREVOBJ[i]) = gdsSelectItem.GetColumn(gdsSelectItem.Row , i);
		vREVOBJ[i] = "";        //������� ���� Text Object��
	}
	sCUROBJ  = "";
	return true;
}



/**************************************************************************************************
*  ��   ��  ��				: fce_GetSysdate
*  ��       ��				: System ��¥ �� �ð��� ��ȸ�Ѵ�.
*  param1) sFormat			: ���� ����
*  return Type				: String
*  return ����				: �ش� ��¥ �� �ð�
**************************************************************************************************/
function fce_GetSysdate(sFormat) {

	var sServiceName = "common-service:searchSysdate";             // ������ ��û�� Sevice ��
	var sInDataSet   = "";                                   // Input DataSet ��Ī
	var sOutDataSet  = "gdsSysdate=dsOutSysdate";              // Output DataSet ��Ī

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
*  ��   ��  ��				: fce_SetStndYear
*  ��       ��				: ���س⵵ ����
*  param1) oDataset			: �۾���� Dataset Object
*  param2) nStartYear       : ���۳�
*  param3) nLastYear     	: ��������
*  return Type				: �ش� ����
*  return ����				: �ش� ����
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
*  ��   ��  ��				: fce_AddCodeRow
*  ��       ��				: ���ο� Popup â�� ���� �ڵ� ���� ȭ���� Display
*  param1) oDataset			: �۾���� Dataset Object
*  param2) sCodeNameValue   : �ش� �ڵ��
*  param3) sCodeIDValue  	: �ش� �ڵ尪
*  param4) oCombo   		: Bind �� �޺� object
*  param4) bFirst   		: �߰��� �ڵ尪�� ��ġ(true : ó��)
*  return Type				: �ش� ����
*  return ����				: �ش� ����
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
*  ��   ��  ��				: fce_GetSelectFile
*  ��       ��				: ������ ���ϸ��� ���Ѵ�.
*  return Type				: Stirng
*  return ����				: ���ϸ�
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
*  ��   ��  ��				: fce_GetSelectFile
*  ��       ��				: ������ ���ϸ��� ���Ѵ�.
*  return Type				: Stirng
*  return ����				: ���ϸ�
**************************************************************************************************/
function fce_OpenExcelListExport(sExcelList) {
    var dlgRtn = Dialog("common::comExcelList.xml", "sGridList=" + sExcelList, '440','170', true, -1, -1);
}

/**************************************************************************************************
*  ��   ��  ��				: fce_GetSelectFile
*  ��       ��				: ������ ���ϸ��� ���Ѵ�.
*  return Type				: Stirng
*  return ����				: ���ϸ�
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

   // �ϰ��������� ����
   fce_SetAlarmHist(	 "002" 
					, strReceiveId
					, called_menu_id
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
*  ��   ��  ��				: fcd_SavePersonalAccess
*  ��       ��				: �������������� ���ٱ���� ����
*  param1) FormID           : ȣ���� ȭ�� ID
*  param2) AccessType       : ���� ����(S:��ȸ, U:����)
*  param3) Action Name      : ��û�� ����(Action)
*  param4) Parameter        : ���� ��û���� �Ķ����
*  return Type				: �ش� ����
*  return ����				: �ش� ����
**************************************************************************************************/
function fce_SavePersonalAccess(sFormId, sAccessType, sAction, sParam)
{
	if (fnc_isNull(sFormId)) return;
	
	var sServiceName = "common_login-service:SaveUserTrace";         // ������ ��û�� Sevice ��
	var sInDataSet   = "";     // Input DataSet ��Ī
	var sOutDataSet  = "";                                  // Output DataSet ��Ī
	
	fce_AddParam("JOB_TYPE", 	"P");   
	fce_AddParam("LOG_SEQ", 	gdsUser.GetColumn(0, "LOG_SEQ"));   
	fce_AddParam("FORM_ID", 	sFormId);   
	fce_AddParam("ACCESS_TYPE", sAccessType);   
	fce_AddParam("ACTION", 		sAction);
	fce_AddParam("PARAM", 		sParam);
	   
	fce_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
} 

/**************************************************************************************************
*  ��   ��  ��				: fce_SavePersonalMnAccess
*  ��       ��				: �������������� ���ٱ���� ����
*  param1) FormID           : ȣ���� ȭ�� ID
*  param2) AccessType       : ���� ����(S:��ȸ, U:����)
*  param3) Action Name      : ��û�� ����(Action)
*  param4) Parameter        : ���� ��û���� �Ķ����
*  return Type				: �ش� ����
*  return ����				: �ش� ����
**************************************************************************************************/
function fce_SavePersonalMnAccess(sFormId, sAccessType, sAction, sParam)
{
	//alert(sFormId);
	
	if (fnc_isNull(sFormId)) return;
	
	var sServiceName = "common_login-service:SavePersonalMnAccess";		// ������ ��û�� Sevice ��
	var sInDataSet   = "";     											// Input DataSet ��Ī
	var sOutDataSet  = "";                                  			// Output DataSet ��Ī
	
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

