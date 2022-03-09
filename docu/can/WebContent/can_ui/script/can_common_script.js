/*================================================================================================/
 * PROGRAM-ID                           snis_common_script 
 * DESCRIPTION   :  �����������ý��ۿ��� ����ϴ� ���� script                                        
 * CREATE DATE   :  2007. 10. 16                                                            
 * UPDATE DATE   :  2007. 10. 16                                                           
 * PROGRAMMER    :                                                                          
=================================================================================================*/
#include "lib::can_message_script.js";
var ServiceMessageCode;
var ServiceMessage    ;
var SearchCount       ;
var SaveCount         ; 
var DeleteCount       ;

/*-------------------------------------------------------------------------------------------------+
>>  Form Load �� ó�� �� ���� ó�� Function
>>  (ȭ�� Title, ȭ�� Information, Loaction �� ���� ó��)
>>>>  param1)  obj           : ȭ�� Object
>>>>  param2)  sFORMCAPTION  : ȭ�� Title
>>>>  param3)  sFORMLOCATION : ȭ�� Location
>>>>  param4)  sBUTTONLIST   : �����ư Enable, Disable ����
>>>>  param5)  sUSERAUTH     : ������
+-------------------------------------------------------------------------------------------------*/
function fnc_FormLoadSetting(obj, sFORMCAPTION, sFORMLOCATION, sBUTTONLIST, sUSERAUTH) {

	http.Sync = true;
	fnc_PersonalMnChk(sFormId);
	
	//���� ���� �� Program ���� Display
	var sNewButtonList  = "";                    //����� ��ư ����
	obj.Window.Title    = sFORMCAPTION;          //ȭ�� Title Display
	obj.stFormTitle     = sFORMCAPTION;          //ȭ�� ���� Display
//	obj.stFormLocation  = sFORMLOCATION;         //Loaction ���� Display
    
	//ȭ���� ��� ���� + ����� ���ѿ� ���� ��ư ó�� 
	for(var i=0; i < length(sBUTTONLIST); i++) {

        //���Ѱ� ��� ������ �����ϸ� �ش� �������� ó��
        if (substr(sBUTTONLIST, i, 1)==substr(sUSERAUTH, i, 1)) {
			
			sNewButtonList += substr(sUSERAUTH, i, 1);
			
        } else {
			//����� ���� ��ư�̸� ������ �־ False
			if (substr(sBUTTONLIST, i, 1)=='F') {
				sNewButtonList += 'F';
			//����� �ִٸ� �ش� ����� ����� ���ѿ� ���� ����
			} else {
				sNewButtonList += decode(substr(sUSERAUTH, i, 1),'','T',substr(sUSERAUTH, i, 1));
			}
		}
	}
    //������ ����� ��ɿ� ���� Enable ó��
	if (substr(sNewButtonList,0,1)=="F") obj.divBtnLst.btnCancel.Visible  = false;  //�ʱ�ȭ
	if (substr(sNewButtonList,1,1)=="F") obj.divBtnLst.btnSave.Visible    = false;  //����
	if (substr(sNewButtonList,2,1)=="F") obj.divBtnLst.btnDelete.Visible  = false;  //����
	if (substr(sNewButtonList,3,1)=="F") obj.divBtnLst.btnToExcel.Visible = false;  //����
	if (substr(sNewButtonList,4,1)=="F") obj.divBtnLst.btnPrint.Visible   = false;  //�μ�
	if (substr(sNewButtonList,5,1)=="F") obj.divBtnLst.btnEnd.Visible     = false;  //����
	
	//�����ư ��ġ ����
	var sFirst_POS   = obj.divBtnLst.btnPrint.right;
	
	var sButton_GAP  = 2;
	var sButton_POS  = 0;
	if (obj.divBtnLst.btnPrint.Visible == false) {
		sButton_POS = sFirst_POS;
		sButton_POS -= obj.divBtnLst.btnPrint.width;
		obj.divBtnLst.btnToExcel.left = sButton_POS;
		
		sButton_POS -= obj.divBtnLst.btnToExcel.width + sButton_GAP;
		obj.divBtnLst.btnDelete.left = sButton_POS;
		
		sButton_POS -= obj.divBtnLst.btnDelete.width + sButton_GAP;
		obj.divBtnLst.btnSave.left = sButton_POS;		

		sButton_POS -= obj.divBtnLst.btnSave.width + sButton_GAP + 8;
		obj.divBtnLst.btnCancel.left = sButton_POS;			
	}

	if (obj.divBtnLst.btnToExcel.Visible == false) {
		sButton_POS  = sFirst_POS;
		
		if (obj.divBtnLst.btnPrint.Visible == true) {
			sButton_POS -= obj.divBtnLst.btnPrint.width + sButton_GAP;
			obj.divBtnLst.btnPrint.left = sButton_POS;
		}
		
		sButton_POS -= obj.divBtnLst.btnToExcel.width + sButton_GAP;
		obj.divBtnLst.btnDelete.left = sButton_POS;
		
		sButton_POS -= obj.divBtnLst.btnDelete.width + sButton_GAP;
		obj.divBtnLst.btnSave.left = sButton_POS;		

		sButton_POS -= obj.divBtnLst.btnSave.width + sButton_GAP + 8;
		obj.divBtnLst.btnCancel.left = sButton_POS;			

	}

	if (obj.divBtnLst.btnDelete.Visible == false) {
		sButton_POS  = sFirst_POS;

		if (obj.divBtnLst.btnPrint.Visible == true) {
			sButton_POS -= obj.divBtnLst.btnPrint.width + sButton_GAP;
			obj.divBtnLst.btnPrint.left = sButton_POS;		
		}
		if (obj.divBtnLst.btnToExcel.Visible == true) {
			sButton_POS -= obj.divBtnLst.btnToExcel.width + sButton_GAP;
			obj.divBtnLst.btnToExcel.left = sButton_POS;
		}
		
		sButton_POS -= obj.divBtnLst.btnDelete.width + sButton_GAP;
		obj.divBtnLst.btnSave.left = sButton_POS;		

		sButton_POS -= obj.divBtnLst.btnSave.width + sButton_GAP + 8;
		obj.divBtnLst.btnCancel.left = sButton_POS;			
	}

	if (obj.divBtnLst.btnSave.Visible == false) {
		sButton_POS  = sFirst_POS;

		if (obj.divBtnLst.btnPrint.Visible == true) {
			sButton_POS -= obj.divBtnLst.btnPrint.width + sButton_GAP;
			obj.divBtnLst.btnPrint.left = sButton_POS;		
		}
		if (obj.divBtnLst.btnToExcel.Visible == true) {
			sButton_POS -= obj.divBtnLst.btnToExcel.width + sButton_GAP;
			obj.divBtnLst.btnToExcel.left = sButton_POS;
		}
		
		if (obj.divBtnLst.btnDelete.Visible == true) {
			sButton_POS -= obj.divBtnLst.btnDelete.width + sButton_GAP;
			obj.divBtnLst.btnDelete.left = sButton_POS;
		}		
		sButton_POS -= obj.divBtnLst.btnSave.width + sButton_GAP + 8;
		obj.divBtnLst.btnCancel.left = sButton_POS;		
	}
}

/*-------------------------------------------------------------------------------------------------+
>>  Message�� '@'���� ����
>>>>  1) sSendMessage : �޼���
>>>>  2) sArg1        : ��ü�� �޼���
+-------------------------------------------------------------------------------------------------*/
function fnc_SetArgument(sSendMessage, sArg1, sArg2) {

	var sMessage = "";
	var arrSplit = array();
	var iInner   = 0;
	var sEval    = "";
	
	arrSplit=split(sSendMessage, "@");
	
	for(var i=0; i<arrSplit.Length(); i++){
		iInner=i+1;
	
		if(iInner < arrSplit.Length()){
			sEval=eval("sArg"+iInner);
		}else{
			sEval="";
		}
		
		sMessage+=arrSplit[i]+sEval;
	}

	return sMessage;
}



function fnc_SetStatus(msg, count) {
    if ( Global.FrameBottom != null || Global.FrameBottom.ed_msg != null ) {
        Global.FrameBottom.ed_msg.Text = fnc_SetArgument(msg, count);
        Global.FrameBottom.SetTimer(1000, 9000);
    }
}


/*-------------------------------------------------------------------------------------------------+
>>  ������ �޽��� â�� Open�Ͽ� �޽��� ������ �����ֱ� ���� Function
>>>>  param1)  sMessageID    : ȣ���� Message ID
>>>>  param2)  sMessageType  : ȣ���� Form�� Type('Ȯ��', 'Yes : No')
>>>>  param3)  sArg1         : '^'ǥ�ÿ� �Է��� ����1
>>>>  param4)  sArg2         : '^'ǥ�ÿ� �Է��� ����2
>>>>  param5)  sArg3         : '^'ǥ�ÿ� �Է��� ����3
>>>>  param6)  sArg4         : '^'ǥ�ÿ� �Է��� ����4
>>>>  param7)  sArg5         : '^'ǥ�ÿ� �Է��� ����5
+-------------------------------------------------------------------------------------------------*/
function fnc_Message(sMessageID, sMessageType, sArg1, sArg2, sArg3, sArg4, sArg5){

	var iTop    = Global.window.top;
	var iLeft   = Global.window.Left;
	var iWidth  = Global.window.Width;
	var iHeight = Global.window.Height;
	
	var iXPos = iLeft + (iWidth-464)/2;    //X Position
	var iYPos = iTop  + (iHeight-158)/2;   //Y Position

	var rtn;
	
	if (sMessageID == "SERVERMSG") {
		rtn = Dialog("common::comMsgError.xml", 
					 "sMessageID=" + quote(sMessageID) +  
				     " sMessageType=" + quote(sMessageType) + 
				     " sArg1=" + quote(sArg1) + " sArg2=" + quote(sArg2) + 
				     " sArg3=" + quote(sArg3) + " sArg4=" + quote(sArg4) + 
				     " sArg5=" + quote(sArg5), 464, 158, "titlebar=true", iXPos,  iYPos);
	} else {
		rtn = Dialog("common::comMessage.xml",
					 "sMessageID=" + quote(sMessageID) +  
				     " sMessageType=" + quote(sMessageType) + 
				     " sArg1=" + quote(sArg1) + " sArg2=" + quote(sArg2) + 
				     " sArg3=" + quote(sArg3) + " sArg4=" + quote(sArg4) + 
				     " sArg5=" + quote(sArg5), 464, 158, "titlebar=true", iXPos,  iYPos);
	}
	return rtn; 
}


/*-------------------------------------------------------------------------------------------------+
>>  Grid���� ����� Ŭ���ϸ� �ش� ������ ���Ľ�Ű�� Function(ASC,DESC ���)
>>>>  param1)  GridObj       : �׸��� ������Ʈ�� �̸� (�� : grdMaster)
>>>>  param2) dsObj          : �����ͼ� ������Ʈ�� �̸� (�� : dsGrid01)
>>>>  param3)  nCell         : �ش缿��ȣ (HeadClick �̺�Ʈ���� ���� nCell�� �״�� ����)
+-------------------------------------------------------------------------------------------------*/
var CONST_ASC_MARK  = "��";   // Ascending  Sort Mark
var CONST_DESC_MARK = "��";   // Descending Sort MarkFxcl

function fnc_GridSort(objGrid, dsObj, nCell){

    var nSortColumn    ;
    var sColumnID      ;
    var nSubColumnCount;
    var sColumnNM      ;
    var objDs          ;
  
    nSortColumn = objGrid.GetCellProp("head", nCell, "col");
  
    /**********************************************************************
    *  Sub�÷��� ���� ���, �����÷������� �����Ѵ�.
    **********************************************************************/
    nSubColumnCount = objGrid.GetCellProp("head", nCell, "colspan");
    if ( nSubColumnCount > 1 ) {
        for ( i = nSortColumn; i < nSortColumn + nSubColumnCount; i++ ) {
            if ( !fnc_isNull(objGrid.GetCellProp("Body", i, "colid")) ) {
                sColumnID += objGrid.GetCellProp("Body", i, "colid") + ",";
            }
        }
    } else {
        sColumnID   = objGrid.GetCellProp("body", nSortColumn, "colid");
    }
  
    sColumnNM   = objGrid.GetCellProp("head", nCell, "text" );
    objDs       = object(objGrid.BindDataset);
    
      // ������������ ����
    if ( right(sColumnNM, 1) == CONST_ASC_MARK ) {
        objDs.Sort(sColumnID, false);
        sColumnNM = replace(sColumnNM, CONST_ASC_MARK, "");
        objGrid.SetCellProp("head", nCell, "text", sColumnNM + CONST_DESC_MARK);
    } else {
    // �������� ����
        objDs.Sort(sColumnID, true);
        sColumnNM = replace(sColumnNM, CONST_DESC_MARK, "");
        objGrid.SetCellProp("head", nCell, "text", sColumnNM + CONST_ASC_MARK);
    }
  
    /**********************************************************************
    *  �ٸ� �÷��� ����ǥ�� ����
    **********************************************************************/
    for ( i = 0; i < objGrid.GetCellCount("head"); i++ ) {
        if ( nCell <> i) {
            sColumnNM = objGrid.GetCellProp("head", i, "text");
            sColumnNM = replace(sColumnNM, CONST_ASC_MARK,  "");
            sColumnNM = replace(sColumnNM, CONST_DESC_MARK, "");
      
            objGrid.SetCellProp("head", i, "text", sColumnNM);
        }
    }
}
/*-------------------------------------------------------------------------------------------------+
>>  Grid���� ����� Ŭ���ϸ� �ش� ������ ���Ľ�Ű�� Function(ASC,DESC ���)
>>>>  param1)  GridObj       : �׸��� ������Ʈ�� �̸� (�� : grdMaster)
>>>>  param2) dsObj          : �����ͼ� ������Ʈ�� �̸� (�� : dsGrid01)
>>>>  param3)  nCell         : �ش缿��ȣ (HeadClick �̺�Ʈ���� ���� nCell�� �״�� ����)
+-------------------------------------------------------------------------------------------------*/
/*
var CONST_ASC_MARK  = "��";   // Ascending  Sort Mark
var CONST_DESC_MARK = "��";   // Descending Sort MarkFxcl

function fnc_GridSort(Gridobj, dsObj, nCell){
	var nheadText,sflag;
	fnc_SetWaitCursor(true);

//----------------------------------------------------------------------------------------------
//  �ش��ϴ� ���� Ÿ��Ʋ�� ��Ʈ��ũ�� �߰� �Ǵ� �����Ѵ�.
//----------------------------------------------------------------------------------------------
	if(right(Gridobj.GetCellProp("head",nCell,"text"),1) == CONST_ASC_MARK)	{
		dsObj.sort(Gridobj.GetCellProp("Body",nCell,"colid"),false);
		
		nheadText = Gridobj.GetCellProp("head",nCell,"text");
		nheadText = replace(nheadText,CONST_ASC_MARK,"");
		nheadText = nheadText + CONST_DESC_MARK;
		
		sflag = CONST_DESC_MARK;
	} else {
		dsObj.sort(Gridobj.GetCellProp("Body", nCell, "colid"),true);
		
		nheadText = Gridobj.GetCellProp("head", nCell, "text");
		nheadText = replace(nheadText,CONST_DESC_MARK,"");
		nheadText = nheadText + CONST_ASC_MARK;   
		
		sflag = CONST_ASC_MARK;
	}
	 
	Gridobj.SetCellProp("head", nCell, "text", nheadText);
	
//----------------------------------------------------------------------------------------------
//  �ش��ϴ� �� �̿��� �ش� Ÿ��Ʋ�� ��Ʈ��ũ�� �����Ѵ�.
//----------------------------------------------------------------------------------------------
	var sRepText="";
	for(i=0; i<Gridobj.GetColCount(); i++){
		if(nCell <> i){
			sRepText = replace(Gridobj.GetCellProp("head", i, "text"), CONST_ASC_MARK,"");
			Gridobj.SetCellProp("head", i, "text", sRepText);
			
			sRepText = replace(Gridobj.GetCellProp("head", i, "text"), CONST_DESC_MARK,"");
			Gridobj.SetCellProp("head", i, "text", sRepText);
		}
	}

	fnc_SetWaitCursor(false);
	
	return sflag;
}

*/

/*-------------------------------------------------------------------------------------------------+
>> PopupDiv Calendar ���� Function
>>>>  1)  param objGrid - �׸��� ��ü
>>>>  2)  param nRow    - �׸��� row index
>>>>  3)  param nCell   - �׸��� cell index
>>>>  4)  param chkFlg  - return Flag : true/false, �� �ѱ� / Dataset�� �� �Ҵ�
+-------------------------------------------------------------------------------------------------*/
var L_GrdBindDs       = ""; // Grid.BindDataset ��ü
var L_GrdBindDsRow;         // Grid.BindDataset ��ü ��
var L_GrdBindDsColId;       // Grid.BindDataset ��ü ��
var L_RtnVal          = ""; // Return Value
var L_RtnFlag;              // Return Falg

function fnc_SetPopupDivCalendar(objGrid, nRow, nCell, chkFlg) {
    L_RtnVal         = "";
	L_GrdBindDs      = object(objGrid.BindDataset);
	L_GrdBindDsRow   = nRow;
	L_GrdBindDsColId = objGrid.GetCellProp("Body", nCell, "ColId");

	var sInitVal = L_GrdBindDs.GetColumn(L_GrdBindDsRow, L_GrdBindDsColId);

	if(chkFlg) {
        L_RtnFlag = true;
        L_RtnVal  = sInitVal;
	} else {

        L_RtnFlag = false;
    }

	var arr_val =  objGrid.GetCellRect(nRow,nCell);
	var div_x = ClientToScreenX(objGrid, arr_val[0]);
	var div_y = ClientToScreenY(objGrid, arr_val[1]);
	var div_w = arr_val[2] - arr_val[0];
	var div_h = arr_val[3] - arr_val[1];

	if(Find("PopDiv_Calendar")==null) {
		Create("PopupDiv", "PopDiv_Calendar", "width='152' height='133'");
	}

	PopDiv_Calendar.Contents = fnc_SetPopDivCalContent(sInitVal);

	PopDiv_Calendar.TrackPopup(div_x, div_y, div_w, div_h);

	return L_RtnVal;
}



/*-------------------------------------------------------------------------------------------------+
>>  �������Ϸ� �������� ���� Function
>>  �׸��带 �������� ���� �� �� ���� �������⸦ �����ϴ� ��� ���!
>>  ���� OnTimer �̺�Ʈ�� MiForm_OnTimer �Լ��� ����Ǿ� �־�� ���� �۵���
>>>>  param1)  sGrid        : ��� Grid ID
>>>>  param2)  nStartCell   : ���� cell index number
>>>>  param3)  bExcYn       : �������α׷� ����' flag [true/false]
+-------------------------------------------------------------------------------------------------*/
function fnc_OpenExcelExportD(sGridId, nStartCell, bExcYn) {
 
    var objGrdExcel = Object(sGridId);
    var objDsExcel  = Object(objGrdExcel.BindDataset);

    sDynamicGrdId = sGridId;

    if ( isValid(objGrdExcel) ) {
        if ( isValid(objDsExcel) ) {
            if ( objDsExcel.RowCount() < 1 ) {
                fnc_Message(L_COM_ALT_0003, "N");
                return;
            }
        } else {
            fnc_Message(L_COM_ALT_0002, "N", "Dataset", objGrdExcel.BindDataset);
            return;
        }

        sDynamicGrdId = sGridId;
    } else {

        fnc_Message(L_COM_ALT_0002, "N", "Grid", sGridId);
        return;
    }

    if(nStartCell>0) nDynamicStartCell = nStartCell;

    if(bExcYn!=null) bDynamicGrdId = bExcYn;

    SetTimer(L_timerEventId_11, 20);
  
}

/**************************************************************************************************
*  ��   ��  ��				: fcd_OpenExcelListExport
*  ��       ��				: ������ ���ϸ��� excel �����Ѵ�.
*  return Type				: Stirng
*  return ����				: ���ϸ�
**************************************************************************************************/
function fcd_OpenExcelListExport(sExcelList) {
    var dlgRtn = Dialog("common::comExcelList.xml", "sGridList=" + sExcelList, '440','170', true, -1, -1);
}

/*-------------------------------------------------------------------------------------------------+
>>  �������Ϸ� �������� ���� Function
>>  �׸��� BindDataset ���ڵ� �� üũ - 0 �� ���  alert return;
>>  �׸��� ��ü ��ȿ�� üũ - ���� �� alert, return;
>>>>  param1)  sGrid        : ��� Grid ID
>>>>  param2)  nStartCell   : ���� cell index number
>>>>  param3)  bExcYn       : �������α׷� ����' flag [true/false]
+-------------------------------------------------------------------------------------------------*/
function fnc_OpenExcelExport(sGridId, nStartCell, bExcYn) {
    var sArg;
    var objGrdExcel = Object(sGridId);
    var objDsExcel = Object(objGrdExcel.BindDataset);

    if(isValid(objGrdExcel)) {

        if(isValid(objDsExcel)) {

            if(objDsExcel.RowCount()<1) {

                fnc_Message(L_COM_ALT_0003, "N");
                return;
            }
        } else {

            fnc_Message(L_COM_ALT_0002, "N", "Dataset", objGrdExcel.BindDataset);
            return;
        }

        sArg = "objGridId=" + quote(sGridId);
        sArg += iif(nStartCell!=null, " nStartCell=" + quote(nStartCell), "");
        sArg += iif(bExcYn!=null, " sExcYn=" + quote(bExcYn), "");

    } else {

        fnc_Message(L_COM_ALT_0002, "N", "Grid", sGridId);
        return;
    }

    var dlgRtn = Dialog("common::comExcelDown.xml", sArg, '440','170', true, -1, -1);

    if(dlgRtn) ExecShell(L_exeFile);

    return;
}

/*-------------------------------------------------------------------------------------------------+
>>  ����ڰ� ������ ������ ������ ���ε��ϴ� Function
>>>>  param1)  httpfileObj  : CyHttpFile ������Ʈ�� ID
>>>>  param2)  file_url     : ����ڰ� ������ ���ϰ�� �� ���ϸ�
>>>>                          (�ش� DataSet ID.getColumn(i,"DataSet�� fileUrl ID"))
>>>>  param3)  str_param    : ����ڰ� ������ ���ϸ�
>>>>                          (�ش� DataSet ID.getColumn(i,"DataSet�� fileName ID"))
>>>>  return rtn_arr
>>>>  ���Ͼ��ε尡 �������� ��� rtn_arr = [SUCC, ������ ���ϸ�, ������ ����ũ��] ����
>>>>  ���Ͼ��ε尡 �������� ��� rtn_arr = [FAIL, �����޽���] ����      
+-------------------------------------------------------------------------------------------------*/
var upload_packetSize = 1024 * 1024 * 10;   // 1M : 1048576;
var download_packetSize = 1024 * 1024 * 10;

function fnc_FileWrite(httpfileObj, sLocalFilePath, sLocalFileName, sWork)
{
    if ( sWork == null ) sWork = "";

	var rtn_arr = Array(3);	// ���� ���ε忡 ���� ���� �� ���� ���ο� ���ε� ����� �����ϴ� ���Ϻ���
	rtn_arr[0] = "FAIL";	// ���Ϻ����� ���� ���ε� ���з� �ʱ�ȭ
	
	if (length(sLocalFilePath) < 7) {	// ���� ��� ���̰� 7���� ���� ��� ���Ϻ��� ����� �����޽��� ����
		rtn_arr[1] = "Not Found Seleced File!";
		return rtn_arr;
	}
	
	if (length(sLocalFileName) < 1){	// ���� ��� ���̰� 7���� ���� ��� ���Ϻ��� ����� �����޽��� ����
		rtn_arr[1] = "Not Found CookieParam!";
		return rtn_arr;
	} 
	var write_size;
	var tot_write_size;
	var file_size;

	remote_url =  GBL_FILE_UPDNLOAD_URL+"?mode=upload&work=" + sWork;	// ���� ���ε��� ��� mode�� upload�� ����
   // alert(remote_url);
	file_size = httpfileObj.GetFileSizeLocal(sLocalFilePath);
	httpfileObj.CookieParam = sLocalFileName;		// ���ϸ��� ��⿡ �־���
	
	ret = httpfileObj.OpenFile(remote_url, sLocalFilePath, "PUT");
	
	if( ret < 0 )	{
		rtn_arr[0] = "FAIL";
		rtn_arr[1] = "OpenFile Failed :: " + httpfileObj.ErrorMsg;
		return rtn_arr;
	}
	while(1) {
		write_size = httpfileObj.WriteFile(upload_packetSize);

		tot_write_size += write_size;
			
		if( write_size < upload_packetSize )	
		{
			break;
		}
	}
	httpfileObj.CloseFile();

	var rtn_val = httpfileObj.CookieParam;	// �����κ��� ���� ��Ű�� ���� ������ ����
	var str_sp = split2(rtn_val,"&","^");	// ��Ű�� �����ڷ� �и��Ͽ� �����ϱ� ���� ����
	var tmp_a;
	for ( var i = 0 ; i < str_sp.length() ; i++ )
	{
		tmp_a = str_sp[i];
	
		if (tmp_a[1] == "FAIL")
		{
			rtn_arr[0] = "FAIL";
			rtn_arr[1] = tmp_a[1];
			return;
		}
		else
		{
			rtn_arr[i+0] = tmp_a[1];
		}
	}
	return rtn_arr;
}




/*-------------------------------------------------------------------------------------------------+
>>  ����ڰ� ������ ������ �����κ��� �ٿ�ε��ϴ� Function
>>>>  param1)  httpfileObj  : CyHttpFile ������Ʈ�� ID
>>>>  param2)  file_url     : ����ڰ� ���ÿ� �ٿ�ް����ϴ� ���ϰ�� �� ���ϸ�
>>>>                          (�ش� DataSet ID.getColumn(i,"DataSet�� fileUrl ID"))
>>>>  param3)  str_param    : ����ڰ� �ٿ�ް����ϴ� ���ϸ�
>>>>                          (�ش� DataSet ID.getColumn(i,"DataSet�� fileName ID"))
>>>>  return rtn_arr
>>>>  ���ϴٿ�ε尡 �������� ��� rtn_arr = [SUCC] ����
>>>>  ���ϴٿ�ε尡 �������� ��� rtn_arr = [FAIL, �����޽���] ����
+-------------------------------------------------------------------------------------------------*/
function fnc_FileRead(httpfileObj, sLocalFileName, sServerFileName, nFileSize, nRow, strCol, dsObj) {

	if ( fnc_IsNull(sLocalFileName) ) {
		fnc_Message(L_COM_ALT_0038, "N");
		return false;
	}

	if ( fnc_IsNull(sServerFileName) ) {
		fnc_Message(L_COM_ALT_0014, "N");
		return false;
	}

    var sRemoteUrl = GBL_FILE_UPDNLOAD_URL+"?mode=download";
    httpfileObj.CookieParam = sServerFileName;
    var sReturn   = httpfileObj.OpenFile(sRemoteUrl, sLocalFileName, "GET");

	if( sReturn < 0 ) {
		fnc_Message(L_COM_ALT_0037, "N");
		return false;
	}
	 

    var nSize        = 0;
    var nTotalSize   = 0;
    var nReadPercent = 0;
    
    if ( dsObj != null ) {
        while(1) {
            nSize = httpfileObj.ReadFile(download_packetSize);
            nTotalSize += nSize;
            nReadPercent = truncate((toNumber(nTotalSize) / toNumber(nFileSize)) * 100, 1);
            dsObj.SetColumn(nRow, strCol, nReadPercent + "%"); 
            if( (nSize == 0) || ( nSize == -1) ) 
            {
                break;
            }
        }
    } else {
        while(1) {
            nSize = httpfileObj.ReadFile(download_packetSize);
            nTotalSize += nSize;
            if( (nSize == 0) || ( nSize == -1) ) 
            {
                break;
            }
        }
	}
	
	httpfileObj.CloseFile();
	if ( nSize == -1 )	{
		fnc_Message(L_COM_ALT_0037, "N");
		return false;
	}
	
    return true;
}



/*-------------------------------------------------------------------------------------------------+
>>  ����ڰ� ������ ������ �������� �����ϴ� Function
>>>>  param1)  str_param    : ������ �����Ϸ��� �������� ���ϸ�
+-------------------------------------------------------------------------------------------------*/
function fnc_FileDelete(str_param)
{
	remote_url =  GBL_FILE_UPDNLOAD_URL+"?mode=delete filename="+str_param;
	Transaction("", remote_url, "", "", "", "");
}



/******************************************************
 *  ��ü Callback 
 ******************************************************/
function fnc_Result(errCode, errMsg) {

    fnc_ProgressClose();
	
    if ( errCode != 0 ) {
		if(errMsg.indexOf("### CONTEXT IS NULL ###") != -1){
			fnc_Message(SNIS_COM_1038, "N");
			exit();
		}
		else{
			fnc_Message("SERVERMSG", "N", errMsg); 
			return false;
		}
    }
    
    //�ߺ��α����� ��� �ý��� ����
		if(errMsg.indexOf("DUP") != -1){
			exit();
		}

    if ( !fnc_IsNull(ServiceMessage) ) {
        fnc_Message(ServiceMessage, "N");
        ServiceMessage = NULL;
    }

    if ( !fnc_IsNull(ServiceMessageCode) ) {
        fnc_Message(eval(ServiceMessageCode), "N");
        ServiceMessageCode = NULL;
    }
    
    if ( !fnc_IsNull(SearchCount) ) {
		fnc_SetStatus(SNIS_CAN_0003, SearchCount);
        //fnc_Message(SNIS_CAN_0003, "N", SearchCount);
        SearchCount = NULL;
    }

    if ( !fnc_IsNull(SaveCount) && !fnc_IsNull(DeleteCount) ) {
        fnc_Message(SNIS_CAN_0006, "N", SaveCount, DeleteCount);
		fnc_SetStatus(SNIS_CAN_0006, SaveCount, DeleteCount);
        SaveCount = NULL;
        DeleteCount = NULL;
    } else {
		if ( !fnc_IsNull(SaveCount) ) {
			fnc_SetStatus(SNIS_CAN_0004, SaveCount);
			//fnc_Message(SNIS_CAN_0004, "N", SaveCount);
			SaveCount = NULL;
		}
	
		if ( !fnc_IsNull(DeleteCount) ) {
			fnc_SetStatus(SNIS_CAN_0005, DeleteCount);
			//fnc_Message(SNIS_CAN_0005, "N", DeleteCount);
			DeleteCount = NULL;
		}
	}

    return true;
}


/*-------------------------------------------------------------------------------------------------+
>>  ������ ǥ�� �˾� �����ϱ� ���� Function
+-------------------------------------------------------------------------------------------------*/
function fnc_ProgressPop() {
    nHandle = Open("common::comPopProgress.xml", "", 294, 112, "Title=false");
}



/*-------------------------------------------------------------------------------------------------+
>>  ������ ǥ�� �˾� Close�ϱ� ���� Function
+-------------------------------------------------------------------------------------------------*/
function fnc_ProgressClose() {
	for ( var i = 0; i < global.windows.count; i++ ) {
        if ( global.windows[i].id == "comPopProgress" ) {
            global.windows[i].close();
            fnc_SetWaitCursor(false);
        }
	}
}




/*-------------------------------------------------------------------------------------------------+
>>  Text Box Focus�� User Data Setting & SelSelect Function
>>>>  param1)  obj           : Focus�� ���� Text Object
+-------------------------------------------------------------------------------------------------*/
function fnc_EditFocus(obj) {

	obj.UserData = obj.value;
	if (ToUpper(obj.gettype()) == 'EDIT') {
		obj.AutoSelect = true; 
	} else if (ToUpper(obj.gettype()) == 'MASKEDIT') {
		obj.SetSel(0,length(obj.text));
	}
}



/*-------------------------------------------------------------------------------------------------+
>>  Grid���� Multi�� ���õ� Row Count�� ��ȯ�ϴ� Function
>>>>  param1)  oDataSet      : �ش� DataSet
+-------------------------------------------------------------------------------------------------*/
function fnc_SelectRowCount(oDataSet) {

	var iRowCount = 0;
	
	for ( var i = 0; i < oDataSet.RowCount(); i++ ) {
		if ( oDataSet.GetSelect(i) ) iRowCount++;
	}
	return iRowCount;
}



/*-------------------------------------------------------------------------------------------------+
>>  �ش� DataSet�� �ּ� ���·� Reset �۾��ϴ� Function
>>>>  param1)  obj           : ��� ���·� ������� �ϴ� �ش� DataSet
+-------------------------------------------------------------------------------------------------*/
function fnc_DataSetCancel(obj) {
	//���� �ش� DataSet�� ������ �־��ٸ� Reset
	if (obj.GetUpdate()) obj.Reset();
}



/*-------------------------------------------------------------------------------------------------+
>>  Report�� ����ϱ� ���� Function
>>>>  param1)  sFileName     : ����� ����Ʈ ���ϸ�
>>>>  param2)  oDataSet      : ����� ��Ű���� �ϴ� Dataset
>>>>  param3)  oFile         : 
+-------------------------------------------------------------------------------------------------*/
function fnc_ToPrint(sFileName, oDataSet, oFile) {

	//���� �Ⱓ�� DataSet�� ������ Report  Design�� ���� XML ���Ϸ� �����.
	var fBuffer = oDataSet.SaveXML(oDataSet.id,"false");
    oFile.FileName = sFileName;

    if(oFile.open("w")) {			
		var len = oFile.write(fBuffer);
		oFile.close();
	}
	alert(sFileName + "\n\n��ο� �ش� ���ϸ����� XML �ڷḦ ������");
}



/*-------------------------------------------------------------------------------------------------+
>>  ���õ� �÷� ���� Function
>>>>  param1)  dsObj         : Dataset��
>>>>  param2)  sColumnNm     : �ش� sCheckColumnNm Nm
>>>>  param3)  sValue        : ��
+-------------------------------------------------------------------------------------------------*/
function fnc_DeleteCheck(dsObj, sCheckColumnNm, sValue) {

	//���õ� Row Count
	var iSelectRowCount = fnc_GetColumnValueCount(dsObj, sCheckColumnNm, sValue);
	
	if ( iSelectRowCount <= 0 ) {
        fnc_Message(SNIS_COM_1003, "N");
        return false;
	}
	
	var bResult = fnc_Message(SNIS_COM_1010, "Y", iSelectRowCount);
    if ( bResult ) {
        fnc_DeleteRow(dsObj, sCheckColumnNm, sValue);
    }
    
    return bResult;
}


/*-------------------------------------------------------------------------------------------------+
>>  �÷��� �ش� ���� �ִ� �÷� �� ���� Function
>>>>  param1)  dsObj         : Dataset��
>>>>  param2)  sColumnNm     : �ش� Column Nm
>>>>  param3)  sValue        : ��
+-------------------------------------------------------------------------------------------------*/
function fnc_GetColumnValueCount(dsObj, sColumnNm, sValue) {

    var nCount = 0;
	for ( var i = 0; i < dsObj.GetRowCount(); i++ ) {
        if ( dsObj.GetColumn(i, sColumnNm) == sValue ) {
            nCount++;
        }
	}
	
	return nCount;
}

/************************************************************************
*  ��       �� : �Ѱ��� Control ���� ���� Null Check�� �ϴ� Function
*  argument    : 1.controlID   : Check ��� Control Object
*                2.controlName : Check ����
*  return Type : boolean
*  return ���� : true/false
************************************************************************/
function fnc_IsMandatory(controlID, controlName)
{
    controlID.value = trim(controlID.value);
    if ( fnc_IsNull(controlID.value) )
    {
        fnc_Message(SNIS_COM_1035, "N", controlName);
        controlID.AutoSelect = true;
        controlID.setFocus();
        return false;
    }
    return true;
}



/*-------------------------------------------------------------------------------------------------+
>>  �ش� DataSet�� ���� ���ΰ� �ִ��� üũ�ϴ� Function
>>>>  param1)  obj          : üũ�ϰ��� �ϴ� DataSet Object
+-------------------------------------------------------------------------------------------------*/
function fnc_DatasetChangeCheck(obj) {

	//DataSet�� ������ �Ͼ���� ���θ� ��ȯ
	if ( obj = '' ) {
		return false;
	} else {
		return obj.GetUpdate();
	}
}



/*-------------------------------------------------------------------------------------------------+
>> ���ڷ� ���� ���ڿ��� Null ���θ� üũ�Ͽ� �� ����� �����ش�.
>>>>  param1)  sValue        : ���ڿ� ��
>>>>  return : true/false - NULL / NOT NULL
+-------------------------------------------------------------------------------------------------*/
function fnc_IsNull(sValue) {
	if ( sValue == null ) return true; 
	if ( length(trim(sValue)) < 1 ) return true;

	return false;
}



/************************************************************************
*  ��   ��  �� : fnc_IsDate
*  ��       �� : ��¥�������� �˻�
*  argument    : 1.objDs  : �۾���� Grid
*  return Type : boolean
*  return ���� : true/false
************************************************************************/
function fnc_IsDate(sDate)
{
    var isOK = false;

    sDate = fnc_GetNumber(sDate);

    if ( sDate.length != 8 ) {
        return false;
    }

    var nYear  = toInteger(sDate.substr(0, 4));
    var nMonth = toInteger(sDate.substr(4, 2));
    var nDay   = toInteger(sDate.substr(6, 2));

    if ( ( nMonth == 1 ) || ( nMonth == 3  ) || ( nMonth == 5  ) || ( nMonth == 7 )
      || ( nMonth == 8 ) || ( nMonth == 10 ) || ( nMonth == 12 ) ) {
       if ( nDay <= 31 ) {
          isOK = true;
       }
    } else if( ( nMonth == 4 ) || ( nMonth == 6 ) || ( nMonth == 9 ) || ( nMonth == 11 ) ) {
       if ( nDay <= 30 ) {
          isOK = true;
       }
    } else if ( nMonth == 2 ) {
       if ( ( nYear % 4 == 0 ) && ( nYear % 100 != 0 ) || ( nYear % 400 == 0 ) ) {
          if ( nDay <= 29 ) {
             isOK = true;
          }
       } else {
          if ( nDay <= 28 ) {
             isOK = true;
          }
       }
    }

    if ( !isOK || (nDay == 0 ) ) {
        return false;
    }

    return true;
}


/**************************************************************************************************
*  ��   ��  ��				: fnc_IsColumnDuplicate
*  ��       ��				: DataSet��  �ϳ��� �÷� ���ڵ尪���� �ߺ����θ� Ȯ���Ѵ�.
*  param1) oDataSet			: DataSet ID
*  param2) colID			: ���� �÷���
*  return Type				: boolean
*  return ����				: true/false
**************************************************************************************************/

function fnc_IsColumnDuplicate(oDataSet, colID) {
   
    var cnt = 0;
    
    if ( (oDataSet.rowcount >0) && (!fnc_IsNull(colID)) )
    {
        for(var i=0; i<oDataSet.rowcount; i++) {
            
            var originVal = oDataSet.GetColumn(i, colID); 
            for(var j=0; j<oDataSet.rowcount; j++) {
                
                var compareVal = oDataSet.GetColumn(j, colID);
                if((i != j) && (originVal == compareVal)) {
                                        	
                   cnt++;
                }
            }
        }
        
    }
    
    if(cnt > 0) {
        
        return true;
    }
    else {
        return false;
    }  
         
}	

/************************************************************************
*  ��   ��  �� : fnc_IsEmail
*  ��       �� : �̸��� �������� üũ�Ѵ�.
*  argument    : 1.sValue   : �̸����ּ�
*  return Type : boolean
*  return ���� : true/false
************************************************************************/
function fnc_IsEmail(sValue)
{
	var sTemp   = "";
	var sRegExp = "[a-z0-9-_]+[a-z0-9.,-_]+@[a-z0-9-]+[a-z0-9.,]+\\.[a-z0-9]+";
	
	var regexp = CreateRegExp(sRegExp, "ig");
	sTemp = regexp.Exec(sValue);
	
	if ( sTemp == null || !(( sTemp.index == 0 ) && ( sTemp.length == sValue.length )) ) {
		alert("���������� �ƴմϴ�.");
		return false;
	}
	
	return true;
}



/*-------------------------------------------------------------------------------------------------+
>>  �ֹι�ȣ üũ ����
>>>>  param1)  sResNum       : �ֹι�ȣ
>>>>  return :  True, False
+-------------------------------------------------------------------------------------------------*/
function fnc_ResidenceNumberCheck(sResNum){

	var sResUnit = Array(13);    // �ֹ� ��ȣ�� �ڸ������� ����
	var sResArgu = Array(13);    // ���� ��� ���� ��
	var iCalSum  = 0;            // ���Ŀ� ���� ��� ��
	var iResult  = 0;            // ���� üũ ��Ʈ ��
	
	//�ֹι�ȣ �ڸ��� üũ
	if (IsDigit(sResNum)==false) return false;
	if (length(trim(sResNum))!=13) return false;

	//�ֹ� ��ȣ�� �ڸ����� �ڸ���
	for (var i=0; i<13; i++) {
		sResUnit[i] = parseInt(substr(sResNum,  i, 1));
	}
	
	//���� �Ű����� ����
	sResArgu = [2,3,4,5,6,7,8,9,2,3,4,5];
	
	if ((sResUnit[6]>0)&&(sResUnit[6]<5)) {
	
		//���� �ֹι�ȣ üũ
		for (i=0; i<12; i++) {
			iCalSum += parseInt(sResUnit[i]) * parseInt(sResArgu[i]);
		}

		iResult = parseInt(iCalSum) % 11;
	    iResult = (11 - parseInt(iResult)) % 10;
	
	} else if ((sResUnit[6]>4)&&(sResUnit[6]<9)){
		
		//5,6,7,8�� �ܱ��� ��� ��ȣ üũ
		var iTMP = (sResUnit[7]*10) + sResUnit[8]; 
		if(iTMP % 2 != 0) return false; 
		if (sResUnit[11]<6) return false;
        
        for (i=0; i<12; i++) { 
			iResult += (parseInt(sResUnit[i]) * parseInt(sResArgu[i])); 
		} 
        
        iResult = (11 - (iResult%11)); 
        if (iResult >= 10) iResult -= 10;  
        iResult += 2; 
        if(iResult >= 10) iResult -= 10; 
	
	} else {
		
		//��Ÿ�� False
		return false;
	}
	
	//���� Check Bit ���� �Էµ� Check Bit ��
	if (iResult == sResUnit[12]) {
		return true;
	} else {
		return false;
	}
}



/*-------------------------------------------------------------------------------------------------+
>>  ����� üũ ���� function
>>>>  param1)  strNum       : ����� ��ȣ
>>>>  return :  True, False
+-------------------------------------------------------------------------------------------------*/
function fnc_FormatCustNumber(strNum){
    strNum = replace(strNum,"-");

    if(strNum.length != 10){
        //cfn_setMsg("W","N","����ڵ�Ϲ�ȣ�� �߸��Ǿ����ϴ�.","");
        return false;
    }

    var sumMod = 0;
    sumMod += parseInt(strNum.substr(0,1));
    sumMod += parseInt(strNum.substr(1,1)) * 3  ;
    sumMod += parseInt(strNum.substr(2,1)) * 7 ;
    sumMod += parseInt(strNum.substr(3,1)) ;
    sumMod += parseInt(strNum.substr(4,1)) * 3;
    sumMod += parseInt(strNum.substr(5,1)) * 7;
    sumMod += parseInt(strNum.substr(6,1)) ;
    sumMod += parseInt(strNum.substr(7,1)) * 3;
    sumMod += parseInt(strNum.substr(8,1)) * 5;

    sumMod += Floor((parseInt(strNum.substr(8,1)) * 5 )/ 10);

    var chkSum = sumMod %10;

    if (chkSum == 0) {
		ckkSum = 0;
    } else {
		chkSum = 10 - chkSum;
    }

    if (chkSum  == parseInt(strNum.substr(9,1))) {
		//alert("���� �ùٸ���.. ");
		return true;
    } else {
		// cfn_setMsg("W","N","����ڵ�Ϲ�ȣ�� �߸��Ǿ����ϴ�.","");
        return false;
    }
    return true;
}



/******************************************************
 *  ���� ���ڷ� ���� ���� ���� �Ǵ� ���� ���� ���
 *  �⺻�� ���� ���ڸ� ����ϳ� isBefore �� true�� ��� 
 *  ���� ���ڷ� ����Ѵ�. 
 * @param  orgDate  ����� ���� ���� 
 * @param  nDays  + ���� 
 * @param   isBefore
           t rue-> ���� ���� 
            false -> ���� ���� 
 * @return  ���� YYYYMMDD ���� 
 ******************************************************/
function fnc_calDate( orgDate, nDays, isBefore) {
	var nDate = DateTime(orgDate);
	if ( isBefore) {
		nDate = nDate - nDays;
	} else {
		nDate = nDate + nDays;
	}

	return DateTime(nDate);
}





/************************************************************************
*  ��   ��  �� : fnc_GetNumber
*  ��       �� : ���ڿ����� ���ڸ� ��������
*  argument    : 1.sValue  : �ش� ���ڿ�
*  return Type : String
*  return ���� : ���ڷ� �̷���� ���ڿ�
************************************************************************/
function fnc_GetNumber(sValue)
{
    var sReturn = "";

    for ( var i = 0; i < sValue.length(); i++ ) {
        var oneChar = CharAt(sValue, i);
        if ( oneChar >= "0" && oneChar <= "9" ) {
            sReturn += oneChar;
        }
    }

    return sReturn;
}


/************************************************************************
*  ��   ��  �� : fnc_GetMonthLastDay
*  ��       �� : ���� �������� ���
*  argument    : 1.sDate  : �ش���� �� ��¥
*  return Type : number
*  return ���� : �ش� ���� ������ ��¥
************************************************************************/
function fnc_GetMonthLastDay(sDate)
{
    sDate = fnc_GetNumber(sDate);

    var asLastDay = Array(12);
    asLastDay[ 0] = "31";
    asLastDay[ 1] = "28";
    asLastDay[ 2] = "31";
    asLastDay[ 3] = "30";
    asLastDay[ 4] = "31";
    asLastDay[ 5] = "30";
    asLastDay[ 6] = "31";
    asLastDay[ 7] = "31";
    asLastDay[ 8] = "30";
    asLastDay[ 9] = "31";
    asLastDay[10] = "30";
    asLastDay[11] = "31";

    var nYear  = toInteger(sDate.substr(0, 4));
    var nMonth = toInteger(sDate.substr(4, 2));

    if ( ( nYear % 4 == 0 ) && ( nYear % 100 != 0 ) || ( nYear % 400 == 0 ) ) {
        asLastDay[1] = "29";
    }

    //return asLastDay[nMonth - 1];
    return sDate.substr(0, 6) + asLastDay[nMonth - 1];
}




/******************************************************
 *  �������� ���ϱ� 
 * @return  ����  YYYYMMDD ���� 
 ******************************************************/
function fnc_currDate() {
    return today();
}




/*-------------------------------------------------------------------------------------------------+
>>  ȭ�� ���� �� ���� ���� �� Ȱ��ȭ Form DataSet ó��
>>>>  1)  DataSet Object
+-------------------------------------------------------------------------------------------------*/
function fnc_FormUnloadCheck(obj, sFormID) {

	// if (GBL_SESSIONOFF==true) return;
	sDataset = split(obj, "|");
	var sMessageCode = "";

    var bChangeYn = false;
    
    for ( var i = 0; i < sDataset.length(); i++ ) {
        objDataset = Object(sDataset[i]);
        if ( fnc_DatasetChangeCheck(objDataset) ) {
			bChangeYn = true;
			break;
		}
    }

	//����� �ڷᰡ �ִ��� üũ��
	if ( bChangeYn ) {
		sMessageCode = SNIS_COM_1001;

		//ȭ�� ���� ���� Ȯ�� 
		if ( !fnc_Message(sMessageCode, "Y") ) {
			return false;    //�������� �ʰ� Unload Cancel ó�� �Ѵ�.
		}		
	}
	
	if ( global.FrameBottom != null ) {	
        global.FrameBottom.fcDeleteTab(sFormID);
    }
    return true;
}



/*-------------------------------------------------------------------------------------------------+
>> ���콺 Ŀ���� �۾��� ���·� �����Ѵ�.
>>>>  1)  param bFlag - true/false �۾���(�𷡽ð�)/ȭ��ǥ
+-------------------------------------------------------------------------------------------------*/
function fnc_SetWaitCursor(bFlag) {

    if(bFlag==null) {
        bFlag = false;
    }

    if(bFlag) {
        SetWaitCursor(true);
        SetCapture();
    } else {
        ReleaseCapture();
        SetWaitCursor(false);
    }
}



/*-------------------------------------------------------------------------------------------------+
>> Calendar Component Content ���� �����Ͽ� �����ش�.
>>>>  1)  param initVal - �ʱ� ��
>>>>  2)  return sRtn
+-------------------------------------------------------------------------------------------------*/
function fnc_SetPopDivCalContent(initVal) {

	var sRtn = "";

	sRtn += '<Contents>' + chr(10);
	sRtn += '<Calendar Border="Flat" ClickedBkColor="#394c5a" ClickedTextColor="white" ' + chr(10);
	sRtn += 'UseTrailingDay="FALSE" LeftMargin="2" Height="152" Id="CAL_PopupDiv" ' + chr(10);
	sRtn += 'Value="' + initVal + '"Left="0" LeftMargin="2" NullValue="&#32;" RightMargin="2" MonthOnly="TRUE" ' + chr(10);
	sRtn += 'OnDayClick="fnc_CalendarOnDayClick" SaturdayTextColor="blue" Style="cal_style1" SundayTextColor="red" ' + chr(10);
	sRtn += 'TextColorColumn="TEXTCOLOR" Top="0" Width="152"></Calendar>' + chr(10);
	sRtn += '</Contents>';

	return sRtn;
}



/**************************************************************************************************
*  ��   ��  ��				: fnc_SetBKColor
*  ��       ��				: Form���� Object�� bgcolor�� �����Ѵ�.
*  param1) oForm			: Form Object
*  return Type				: �ش� ����
*  return ����				: �ش� ����
**************************************************************************************************/
function fnc_SetBKColor(oForm) {
	for ( var i = 0 ; i < oForm.Components.Count ; i++ )
	{
		if ( ( oForm.Components[i].GetType() == "Edit"    ) 
		  || ( oForm.Components[i].GetType() == "Spin"    ) 
		  || ( oForm.Components[i].GetType() == "MaskEdit") 
		  || ( oForm.Components[i].GetType() == "TextArea") 
		  ) {
			if ( oForm.Components[i].Enable == true && oForm.Components[i].ReadOnly == false ) {
				oForm.Components[i].BKColor = "#FFFFFF";
			} else {
				oForm.Components[i].BKColor = "#D8D8D8";
			}
		}
	}
}


/**************************************************************************************************
*  ��   ��  ��				: fnc_ValueToCode
*  ��       ��				: �ش��̸��� �׷��ڵ�� �ڵ��� �ռ��� ����
*  param1) objDs			: �׷��ڵ�� �ڵ�� �̸������� ����Dataset �̸�
*  param2) objDs			: ���� ��
*  param3) objDs			: ���� Dataset �÷���
*  param4) objDs			: ������ Dataset �׷��ڵ� �÷���
*  param5) objDs			: ������ Dataset �����ڵ� �÷���
*  return Type				: String
*  return ����				: �ش� ����
**************************************************************************************************/
function fnc_ValueToCode( oDataSet, oVal, oColumn, oGrpCd, oSpecCd) {
    
    var newCd = null;
    
    if( (oDataSet != null) && (oVal != null) && (oColumn != null) ) {
        
        for(var i=0; i<oDataSet.rowcount; i++) {
        
            if( oDataSet.getColumn(i, oColumn) == oVal ) {
                
                newCd  = Trim(oDataSet.getColumn(i, oGrpCd));
                newCd += "_";
                newCd += Trim(oDataSet.getColumn(i, oSpecCd)); 
            }
        }
    }
    else {
    
        fnc_Message(SNIS_COM_0001, "Y");
        return;
    }
    
    return newCd;
}


/**************************************************************************************************
*  ��   ��  ��				: fnc_DatasetPrint
*  ��       ��				: Dataset Header �� Data ������ PID�� Outputâ�� Display
*  param1) objDs			: Display ��� Dataset
*  return Type				: �ش� ����
*  return ����				: �ش� ����
**************************************************************************************************/
function fnc_DatasetPrint(objDs)
{
    var objDatasetName;

    if ( IsValid(objDs) ) {
        objDatasetName = objDs.id;
    } else {
        objDatasetName = objDs;
    }

    var objDataset = object(objDatasetName);
    var nColCnt    = objDataset.ColCount();
    var nRowCnt    = objDataset.RowCount();
    var i;

    /**********************************************************************
    *  Header�� ����Ѵ�.
    **********************************************************************/
    var sHeadStr = "";
    var sDsName  = "UnKnown Dataset";

    trace(">>>> " + "---------------------------------------------------");
    trace(">>>>    Dataset (" + objDatasetName + ") Tracing   ");
    trace(">>>> " + "Col Cnt:" + nColCnt +" /  Row Cnt:" + nRowCnt);

    for ( j = 0; j < nColCnt; j++ ) {
        sHeadStr += "[" + objDataset.GetColID(j) +"] ";
    }
    trace(">>>> COL ID : " + sHeadStr);

    /**********************************************************************
    *  ������ ����Ѵ�.
    **********************************************************************/
    var sRowStr = "";
    for ( i = 0; i < nRowCnt; i++ ) {
        sRowStr = objDataset.GetRowType(i) + "::";
        for ( j = 0; j < nColCnt; j++ ) {
            sRowStr += objDataset.GetColID(j) + "[" + objDataset.GetColumn(i, objDataset.GetColID(j)) +"] ";
        }
        trace(">>>> " + sRowStr);
    }

    nRowCnt = objDataset.GetOrgBuffCount();
    for ( i = 0; i < nRowCnt; i++ ) {
        if ( objDataset.GetOrgBuffType(i) == "delete" ) {
            sRowStr = "delete::";
            for ( j = 0; j < nColCnt; j++ ) {
                sRowStr += objDataset.GetColID(j) + "[" + objDataset.GetOrgBuffColumn(i, objDataset.GetColID(j)) +"] ";
            }
            trace(">>>> " + sRowStr);
        }
    }

    trace(">>>> " + "---------------------------------------------------");
}




/************************************************************************
*  ��   ��  �� : fnc_DeleteRow
*  ��       �� : DataSet�� strColID �÷����� ���� Value���̸� �ش� Row�� �����Ѵ�.
*  argument    : 1.DataSet  : �۾���� Dataset Object
*                2.strColID : �÷��� String
*                3.Value    : �ش簪 Object
*                4.nStartRow: �۾� ���� Row
*  return Type : ����
*  return ���� : �ش����
************************************************************************/
function fnc_DeleteRow(DataSet, strColID, strValue, nStartRow) {
    var nCnt    = 0;
    var nDelCnt = 0;

    // ���� Row���� ������ DataSet�� ù Row���� �����Ѵ�.
    if ( nStartRow == null ) {
        nStartRow = 0;
    }

    nCnt = DataSet.GetRowCount();
    DataSet.FireEvent = false;
    // DataSet�� Loop���鼭 �ش簪�� ã�� �ش� Row�� �����Ѵ�.
    for ( i = nStartRow; i < nCnt; i++ ) {
        if ( DataSet.GetColumn(i - nDelCnt ,strColID) == strValue ) {
            DataSet.DeleteRow(i - nDelCnt);
            nDelCnt++;
        }
    }
    DataSet.FireEvent = true;
}

/************************************************************************
*  ��   ��  �� : fnc_ReplaceColumn
*  ��       �� : DataSet�� strColID �÷����� SourceValue���� Value������ �����Ѵ�.
*  argument    : 1.DataSet     : �۾���� Dataset Object
*                2.strColID    : �÷��� String
*                3.SourceValue : �ش簪 Object
*                4.ObjectValue : �ش簪 Object
*  return Type : ����
*  return ���� : �ش����
************************************************************************/
function fnc_ReplaceColumn(DataSet, strColID, SourceValue, ObjectValue) {
    // Dataset�� �̺�Ʈ �� RowType�� �����Ѵ�.
    DataSet.FireEvent     = false;
//    DataSet.UpdateControl = false;

    // Dataset�� Loop���鼭 �ش簪�� ã�� �����Ѵ�.
    for ( i = 0; i < DataSet.GetRowCount(); i++ ) {
        if ( trim(DataSet.GetColumn(i ,strColID)) == SourceValue ) {
            DataSet.SetColumn(i ,strColID, ObjectValue);
        }
    }

    // Dataset�� �̺�Ʈ �� RowType�� �����Ѵ�.
//    DataSet.UpdateControl = true;
    DataSet.FireEvent     = true;
}

/************************************************************************
*  ��   ��  �� : fnc_GetMandatoryViolateRow
*  ��       �� : DataSet���� �����۾��� �� Row�߿��� �ʼ��� �Է� �Ǿ�� �� �ʵ尪�� ���� ���� ���
*                �ش��ϴ� Row�� �����Ѵ�.
*  argument    : 1.DataSet             : �۾���� Dataset Object
*                2.strColID            : �÷��� String(�����÷���)
*                3.strMandatoryColID   : �ʼ��� �ԷµǾ�� �� columns(������ '||')
*                4.strMandatoryColName : �ʼ��� �ԷµǾ�� �� columns��(������ '||')
*  return Type : LONG
*  return ���� : �ش簪�� ã�� ���� DataSet�� ù��° �߰ߵ� Row��ȣ��,
*                ���� ���� ���� -1�� return
************************************************************************/
function fnc_GetMandatoryViolateRow(DataSet, strMandatoryColID, strMandatoryColName, Grid) {
    var resultRow = -1;

    // column�� �����ڷ� �����Ͽ� �����Ѵ�.
    var MandatoryColID   = split(strMandatoryColID  , "|");
    var MandatoryColName = split(strMandatoryColName, "|");

    // ���� DataSet�� Loop���鼭 �����÷��� ���õ� ���
    for ( i = 0; i < DataSet.GetRowCount(); i++ ) {

		if ( DataSet.GetRowType(i) != "normal" ) {

            // üũ�ؾ� �ʵ� ����ŭ �ش� ���� �˻��Ѵ�.
            for ( j = 0; j < MandatoryColID.length(); j++ ) {

                // �ʵ尪�� ���� ���� ��� �޼����� ȣ�� �� �� �ش� Row��ȣ�� return �Ѵ�.
                if ( fnc_IsNull(DataSet.GetColumn(i, MandatoryColID[j])) ) {
                    DataSet.SetColumn(i, MandatoryColID[j], trim(DataSet.GetColumn(i, MandatoryColID[j])));
					fnc_Message(SNIS_CAN_0001, "N", i + 1, MandatoryColName[j]); 
                    // var str_msg = (i + 1) + "��°�� " + MandatoryColName[j] + "��(��) �ʼ� �Է��׸��Դϴ�.";
                    // alert(str_msg);
                    DataSet.row = i;
     
					if ( Grid != null ) {
						Grid.SetFocus();
						Grid.SetCellPos(Grid.GetBindCellIndex("body", MandatoryColID[j]));
					}
                    return i;
				}
			}
		}
    }

    return resultRow;
}

/************************************************************************
*  ��   ��  �� : fnc_GetExistValue
*  ��       �� : DataSet�� strColID �÷����� Value ���� ���� �ϴ��� �˻�
*  argument    : 1.DataSet   : ��ȸ�ϰ��� �ϴ� Dataset Object
*                2.strColID  : �÷��� String
*                3.strValue  : �ش簪 Object
*                4.nStartRow : ����Row
*  return Type : LONG
*  return ���� : �ش簪�� ã�� ���� DataSet�� ù��° �߰ߵ� Row��ȣ��,
*                ���� ���� ���� -1�� return
************************************************************************/
function fnc_GetExistValue(DataSet, strColID, strValue, nStartRow) {
    var resultRow = -1;

    // ����Row���� ������ ù Row���� ����
    if ( nStartRow == null ) {
        nStartRow = 0;
    }

    strValue = toUpperCase(Trim(strValue));
    // Dataset�� Loop���鼭 �ش簪�� ������ ����.
    // �ش簪�� ����
    for ( i = nStartRow; i < DataSet.GetRowCount(); i++ ) {
        if ( toUpperCase(trim(DataSet.GetColumn(i ,strColID))) == strValue ) {
            resultRow = i;
            break;
        }
    }

    // �ش簪�� ������ -1�� ����
    return resultRow;
}

/************************************************************************
*  ��   ��  �� : fnc_SetAllColumnValue
*  ��       �� : DataSet�� strColID �÷����� ��� ���� Value������ �����Ѵ�.
*  argument    : 1.DataSet  : �۾��ϰ��� �ϴ� Dataset Object
*                2.strColID : �÷��� String
*                3.Value    : �ش簪 Object
*  return Type : ����
*  return ���� : �ش����
************************************************************************/
function fnc_SetAllColumnValue(DataSet, strColID, Value) {
    // DataSet�� strColID �÷����� ��� ���� Value������ �����Ѵ�.
    for ( i = 0; i < DataSet.GetRowCount(); i++ ) {
        DataSet.SetColumn(i ,strColID, Value);
    }
}

/************************************************************************
*  ��   ��  �� : fnc_CopyDataset
*  ��       �� : FromDataSet�� strColID �÷����� Value���� ���� Row�� ToDataSet�� �����Ѵ�.
*                RowType�� �����Ѵ�.
*  argument    : 1.ToDataSet   : ���纻�� ����� Dataset Object
*                2.FromDataSet : ���� Dataset Object
*                3.strColID    : �÷��� String
*                4.Value       : �ش簪 Object
*  return Type : ����
*  return ���� : �ش����
************************************************************************/
function fnc_CopyDataset(ToDataSet, FromDataSet, strColID, Value, RowTypeColID) {

    var nRow = 0;

    // ����� Dataset�� ���� Dataset�� �÷��� ���Ͻ� �����ϸ� Row��ü�� �����Ѵ�.(Column�� ��ġ��Ų��.)
    ToDataSet.Copy(FromDataSet);
    ToDataSet.ClearData();

    if ( RowTypeColID != null ) {
        ToDataSet.AddColumn(RowTypeColID);
    }

    // FromDataSet�� Loop���鼭 �ش簪�� ã�� ToDataSet�� �����Ѵ�.
    for ( i = 0; i < FromDataSet.GetRowCount(); i++ ) {
        if ( strColID == "" || FromDataSet.GetColumn(i, strColID) == Value ) {
            ToDataSet.AddRow();
            if ( RowTypeColID != null ) {
                ToDataSet.SetColumn(nRow, RowTypeColID, SubStr(toUpperCase(FromDataSet.GetRowType(i)), 0, 1));
            }
            ToDataSet.CopyRow(nRow++, FromDataSet, i);
        }
    }
}



/*-------------------------------------------------------------------------------------------------+
>> Calendar Component OnDayClick �̺�Ʈ ó�� �Լ�
>>>>  1)  param obj - Calendar Component ��ü
>>>>  2)  param strText - ���� ��¥ �� : YYYYMMDD
>>>>  3)  return sRtn
+-------------------------------------------------------------------------------------------------*/
function fnc_CalendarOnDayClick(obj, strText) {

	if(L_RtnFlag) {

        L_RtnVal = strText;
    } else {
        L_GrdBindDs.SetColumn(L_GrdBindDsRow, L_GrdBindDsColId, strText);
    }
	PopDiv_Calendar.ClosePopup();
}

function fnc_RPTPrint(sSubUrl, sRptNm, sDataSet, sArg, sRptDrct, sPrintCnt, sRptType) {
    var sFileName  = "";
	
	if (sRptDrct == 0){ //����
		sFileName  = "common::comVrtRptViewer.xml";
	} else { //����
		sFileName  = "common::comHrzRptViewer.xml";
	}
	
	var arrArgumentList = split2(sArg, "^", ",");                        //Argument Array List

	Dialog(sFileName, " sSubUrl="  + quote(sSubUrl) +
	                  " sRptNm="   + quote(sRptNm) + 
	                  " sDataSet=" + quote(sDataSet) + 
	                  " sArg="     + quote(sArg) +
	                  " sPrintCnt="    + quote(sPrintCnt) +
	                  " sRptType="     + quote(sRptType),
	                  500, 500, "titlebar=true",-1, -1);
}


/**************************************************************************************************
*  ��   ��  ��				: fcd_PersonalMnChk
*  ��       ��				: �� �ε� �� ��������ó���޴� ���θ�  ��ȸ�Ѵ�
*  param1) FormID           : ȣ���� ȭ�� ID
*  return Type				: �ش� ����
*  return ����				: true/false
**************************************************************************************************/
function fnc_PersonalMnChk(sFormId){
	if (fnc_isNull(sFormId)) return;
	
	Create("Dataset", "dsPersonalMn");
	
	var sServiceName = "common-service:SearchPersonalMn";		// ������ ��û�� Sevice ��
	var sInDataSet   = "";										// Input DataSet ��Ī
	var sOutDataSet  = "dsPersonalMn=dsPersonalMn";				// Output DataSet ��Ī
		
	fcd_AddParam("FORM_ID", 	sFormId);   
	
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcPersonalCallBack", false);
	
	Destroy("dsPersonalMn");
}

/**************************************************************************************************
*  ��   ��  ��				: fcd_PersonalMnChk
*  ��       ��				: �� �ε� �� ��������ó���޴� ���θ�  ��ȸ�Ѵ�
*  param1) sFormId          : ȣ���� ȭ�� ID
*  param1) sUserID          : ����� ID
*  param2) sUserIp          : ����� IP
*  return Type				: �ش� ����
*  return ����				: true/false
**************************************************************************************************/
function fnc_PersonalIdChk(sFormId){

	Create("Dataset", "dsPersonalIdChk");
	
	var sServiceName = "common-service:SearchPersonalIdChk";	// ������ ��û�� Sevice ��
	var sInDataSet   = "";										// Input DataSet ��Ī
	var sOutDataSet  = "dsPersonalIdChk=dsPersonalIdChk";		// Output DataSet ��Ī
	
	var sUserID	= gdsUser.GetColumn(0,"USER_ID");
	var sUserIp = ext_GetIPAddress();
	
	fcd_AddParam("FORM_ID", 	sFormId);   
	fcd_AddParam("USER_ID", 	sUserId);   
		   
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcPersonalCallBack", false);
	
	Destroy("dsPersonalIdChk");
}

/**************************************************************************************************
>>  Transaction �� ó�� �ؾ� �� ����!
**************************************************************************************************/
function fcPersonalCallBack(sServiceName, ErrorCode, ErrorMSG) {
		
	if ( !fnc_result(ErrorCode, ErrorMSG) ) return;
	
	GBL_PERSONALMN_CHK = "N";	
	
	if(sServiceName == "common-service:SearchPersonalMn"){
		
		dsPersonalMn.addColumn("PERSONAL_DATA_MN", "String", 256);
		dsPersonalMn.addColumn("PERSONAL_MN_ID", "String", 256);
		
		if(dsPersonalMn.GetColumn(0, "PERSONAL_DATA_MN") == "Y") {
			var sFormId = dsPersonalMn.GetColumn(0, "PERSONAL_MN_ID");
			fnc_PersonalIdChk(sFormId);
			return;
		}
	}else if(sServiceName == "common-service:SearchPersonalIdChk"){
		
		var sUserIp = ext_GetIPAddress();
		
		dsPersonalIdChk.addColumn("PERSONAL_AUTH_CHK", 	"String", 256);
		dsPersonalIdChk.addColumn("USER_ID", 			"String", 256);
		dsPersonalIdChk.addColumn("PERSONAL_MN_IP", 	"String", 256);
		
		if(dsPersonalIdChk.GetColumn(0, "PERSONAL_AUTH_CHK") != "Y") {
			GBL_PERSONALMN_CHK = "N";
		}else if(dsPersonalIdChk.GetColumn(0, "PERSONAL_AUTH_CHK") == "Y") {
			GBL_PERSONALMN_CHK = "Y";
		}
		
		return;
		
	}
}

////////////////////////////////////////////////////////////////////////////////////////////end-ifis