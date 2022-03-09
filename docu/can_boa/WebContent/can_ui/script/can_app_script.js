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
//	Trace("sOutDataSet:"+sOutDataSet);
	
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
*  ��   ��  ��				: fce_GetDayNo
*  ��       ��				: 
*  param1) sRacerPerioNo	: 
*  param2) sDatasetName		: Dataset
*  return Type				: ����
*  return ����				: ����
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
*  ��   ��  ��				: fce_GetRound
*  ��       ��				: 
*  param1) sRacerPerioNo	: 
*  param2) sDatasetName		: Dataset
*  return Type				: ����
*  return ����				: ����
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
*  ��   ��  ��				: fce_GetTeam
*  ��       ��				: 
*  param1) sRacerPerioNo	: 
*  param2) sDatasetName		: Dataset
*  return Type				: ����
*  return ����				: ����
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
    var sDocType        = "����";
    
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
    
    //���丮 ���� üũ�ؼ� ������ �������ش�.(���뽺ũ��Ʈ ȣ��(strPath))
    fnc_IsDirectory(AttFilePath);
    //===============================================================3.÷������ ����
    if(!fnc_IsNull(fileObj)) {   //�����ͼ� ID
        // Null �ƴϸ� ÷������ �����ϴ� ������ �����Ѵ�.
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
            fileObj.Export(true, false);                              // ù��° progress, �ι�° excel
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
        fnc_CreateHtmlFile(FilePath+timeStamp+".html", conObj.text);

    }else{
        // Null �̸� ��������� �����ϰ� Html���� �����ϴ� ���� ����
        fnc_CreateHtmlFile(FilePath+timeStamp+".html", subJect);
    }
    //=============================================================================

    //==============================================================5.�۽�URL ȣ��
    //�׽�Ʈ ���� : http://140.100.100.24:8088/UFWeb/public/signContact.jsp
    //�ǿ ���� : http://jupiter.sosfo.or.kr/UFWeb/sign/hwpsign/ActiveMis/signContact.jsp
    //ExecBrowser("http://140.100.100.24:8088/UFWeb/public/signContact.jsp?GUBUNID="+varArray[0]+"&BIZ=cy&JOB="+job+"&CREATTYPE=����&USER_ID="+reportId+"&SUBJECT="+subJect+"&ATTNAME="+FileName+"&SEQ="+seq);
    //2009.05.14 ����� ����� �������븮 ID�� �ھƵ�(�׽�Ʈ�� ���ؼ�)
    //ExecBrowser("http://jupiter.kspo.or.kr/UFWeb/public/signContact.jsp?GUBUNID="+timeStamp+"&BIZ=cy&JOB="+job+"&CREATTYPE="+sDocType+"&USER_ID="+reportId+"&SUBJECT="+subJect+"&ATTNAME="+FileName+"&SEQ="+seq);
    //===================================================
    
    //////////////////////////////////////// from ��������......

    var sIfData  = "";
    //sIfData += "target="     + urlEncode("giancall") ;
	//sIfData += "&SSOTOK="    + urlEncode(ssoToken) ;
	//sIfData += "&formid="    + urlEncode("00000018i") ;
	sIfData += "&empcode="   + urlEncode("U00" + reportId) ;
	sIfData += "&deptcode="  + urlEncode(gdsUser.GetColumn(0,"TEAM_CD")) ;
	sIfData += "&miskey="    + urlEncode(varArray[0]) ; 
	sIfData += "&title="     + urlEncode(subJect) ;     //����
	sIfData += "&fld_value=" + urlEncode("<html><body>"+ replace(conObj.text, "&nbsp;", "_nbsp_" ) +"</body></html>") ;//����
	sIfData += "&job="       + urlEncode(job) ; 
	sIfData += "&creatType=" + urlEncode(sDocType) ; 
	//sIfData += "&creatType=" + urlEncodeUtf8(sDocType) ; 
	sIfData += "&SEQ="       + urlEncode(seq) ; 
	sIfData += "&userpswd="  + urlEncode(gdsUser.GetColumn(0,"PSWD")) ; 
	
	// ÷������
	if( fileObj != "" && FileName != "" )
	{
		sIfData += "&fld_attach="       + urlEncodeUtf8(AttFilePath+FileName + "|" + FileName) ; 
		
	}
    // ���ڰ��翬���� ���� ��ü����.
	Create("WebBrowser","WebBrowser",'left="0" top="0" width="0" height="0" visible=false');
	//Create("WebBrowser","WebBrowser",'left="0" top="0" width="300" height="400" visible=true');
	
    this.WebBrowser.PageUrl = "http://canboa.kcycle.or.kr/canboa/callSign.jsp";    
    this.WebBrowser.PostData = sIfData ; 
    this.WebBrowser.HeadVal = "Content-Type: application/x-www-form-urlencoded";

    this.WebBrowser.Run();

}



/**************************************************************************************************
*  ��   ��  ��				: fce_GetSelectFile
*  ��       ��				: ������ ���ϸ��� ���Ѵ�.
*  return Type				: Stirng
*  return ����				: ���ϸ�
**************************************************************************************************/
function fcd_OpenExcelListExport(pExcelList) {
    var dlgRtn = Dialog("common::comExcelList.xml", "sGridList=" + pExcelList, '440','170', true, -1, -1);
}

/**************************************************************************************************
*  ��   ��  ��				: fce_GetSelectFile
*  ��       ��				: ������ ���ϸ��� ���Ѵ�.
*  return Type				: Stirng
*  return ����				: ���ϸ�
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
*  ��   ��  ��				: fnc_GetCurRaceInfo
*  ��       ��				: �ش� �⵵�� ����ȸ���� ���� ��ȸ
*  param1) sColumnName		: �÷���
*  return Type				: ����
*  return ����				: ����
**************************************************************************************************/
function fcd_GetCurRaceInfo(sColumnName)
{
    if ( gdsRace.GetRowCount() == 0 ) return "";
    
    var nRow = fnc_GetExistValue(gdsRace, "CURDATE", "TRUE");
    if ( nRow < 0 ) nRow = 0;
    
    return gdsRace.GetColumn(nRow, sColumnName);
}

/**************************************************************************************************
*  ��   ��  ��				: fnc_GetCurRaceInfo
*  ��       ��				: �ش� �⵵�� ����ȸ���� ���� ��ȸ
*  param1) sColumnName		: �÷���
*  return Type				: ����
*  return ����				: ����
**************************************************************************************************/
function fcd_GetUserInfo(sColumnName)
{
    if ( gdsUser.GetRowCount() == 0 ) return "";
    
    var nRow = 0;
    
    return gdsUser.GetColumn(nRow, sColumnName);
}


/**************************************************************************************************
*  ��   ��  ��				: fnc_GetRaceTms
*  ��       ��				: �ش� �⵵�� ����ȸ�� ��ȸ
*  param1) sStndYear		: �ش�⵵
*  param2) sQurtCd		    : �ش�б�
*  param3) sDatasetName		: Dataset
*  return Type				: ����
*  return ����				: ����
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
*  ��   ��  ��				: fnc_GetRaceDayOrd
*  ��       ��				: �ش� �⵵�� ����ȸ���� ���� ��ȸ
*  param1) sStndYear		: �ش�⵵
*  param2) sRaceTms		    : ȸ��
*  param3) sDatasetName		: Dataset
*  return Type				: ����
*  return ����				: ����
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
*  ��   ��  ��				: fnc_GetRace
*  ��       ��				: �ش� �⵵�� ����ȸ���� ���� ��ȸ
*  param1) sStndYear		: �ش�⵵
*  param2) sRaceTms		    : ȸ��
*  param3) sRaceDayOrd		: ����
*  param4) sDatasetName		: Dataset
*  return Type				: ����
*  return ����				: ����
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
*  ��   ��  ��				: fnc_SetCurTms
*  ��       ��				: �ش� �⵵�� ����ȸ���� ���� ��ȸ
*  return Type				: ����
*  return ����				: ����
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
					, aRecvPh[i] 		//������ ��ȣ
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
					, strReceivePhoneNumber 		//������ ��ȣ
					, strSendSubject
					, strSendContent
					, strSendPhoneNumber
					, strSendUserName
					, strReceiveName);
   
   
}




/**************************************************************************************************
*  ��   ��  ��				: fcd_SetAlarmHist
*  ��       ��				: �˶��̷� ���� 
*  return Type				: 
*  return ����				: 
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
trace("fcd_SavePersonalAccess::");
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
