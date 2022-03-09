/*================================================================================================/
 * PROGRAM-ID                           snis_common_script 
 * DESCRIPTION   :  ���ֻ�������ý��ۿ��� ����ϴ� ���� script                                        
 * CREATE DATE   :  2011. 07. 21                                                         
 * UPDATE DATE   :  2011. 07. 21                                                          
 * PROGRAMMER    :                                                                          
=================================================================================================*/
#include "lib::rbm_message_script.js";
#include "lib::rbm_app_script.js";
var ServiceMessageCode;
var ServiceMessage    ;
var SearchCount       ;
var SaveCount         ; 
var DeleteCount       ;

var DIV_CODE_URL = "frm_rsy::divCodeSearch.xml";


var ENTER_KEY = 13;
var fv_nHandle;

//New Script File
var gv_ScrollMargin = 16;
var gv_DefaultSize = 12;
var gv_MDIWidth = 773;
var gv_MDIHeight = 583;
var gLocalTempDir = "C:\\temprbm";

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
	
	//http.Sync = true;
	
	var PARENT_FORM_CHK = substr(obj.parent+"", 0, 6);
	if(PARENT_FORM_CHK != "form::"){
		//fnc_PersonalMnChk(sFormId);
		if(Indexof(gdsUser.GetColumn(0, "MN_ID"), sFormId) != -1)
		{
			fnc_PersonalIdChk(sFormId);
		}
	}
	
	sUSERAUTH   = 'T'+sIsView+""+sInpt+'TTTT';     //�ʱ�ȭ,��ȸ,����,����,����,�μ�,�ݱ�

	//���� ���� �� Program ���� Display
	var sNewButtonList  = "";                                               //����� ��ư ����
	obj.Window.Title    = sFORMCAPTION;
	 
	if( !IsExistVariable("GBL_WEB_ID","Global") ) {
		//ȭ�� Title Display
		obj.stFormTitle     = obj.stMenuPath.value+"("+obj.stScrnId.value+")";  //ȭ�� ���� Display(ȭ��ID�߰�)
   }
//	obj.stFormLocation  = sFORMLOCATION;                                    //Loaction ���� Display
  
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
	if (substr(sNewButtonList,0,1)=="F") obj.divBtnLst.btnCracel.Visible  = false;  //�ʱ�ȭ
	if (substr(sNewButtonList,1,1)=="F") obj.divBtnLst.btnSearch.Visible  = false;  //��ȸ
	if (substr(sNewButtonList,2,1)=="F") obj.divBtnLst.btnSave.Visible    = false;  //����
	if (substr(sNewButtonList,3,1)=="F") obj.divBtnLst.btnDelete.Visible  = false;  //����
	if (substr(sNewButtonList,4,1)=="F") obj.divBtnLst.btnToExcel.Visible = false;  //����
	if (substr(sNewButtonList,5,1)=="F") obj.divBtnLst.btnPrint.Visible   = false;  //�μ�
	if (substr(sNewButtonList,6,1)=="F") obj.divBtnLst.btnEnd.Visible     = false;  //����
	
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
				
		sButton_POS -= obj.divBtnLst.btnSave.width + sButton_GAP;
		obj.divBtnLst.btnSearch.left = sButton_POS;		

		sButton_POS -= obj.divBtnLst.btnSearch.width + sButton_GAP + 8;
		obj.divBtnLst.btnCracel.left = sButton_POS;			
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
			
		sButton_POS -= obj.divBtnLst.btnSave.width + sButton_GAP;
		obj.divBtnLst.btnSearch.left = sButton_POS;		

		sButton_POS -= obj.divBtnLst.btnSearch.width + sButton_GAP + 8;
		obj.divBtnLst.btnCracel.left = sButton_POS;			

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
			
		sButton_POS -= obj.divBtnLst.btnSave.width + sButton_GAP;
		obj.divBtnLst.btnSearch.left = sButton_POS;		

		sButton_POS -= obj.divBtnLst.btnSearch.width + sButton_GAP + 8;
		obj.divBtnLst.btnCracel.left = sButton_POS;			
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
		sButton_POS -= obj.divBtnLst.btnSave.width + sButton_GAP;
		obj.divBtnLst.btnSearch.left = sButton_POS;		
		
		sButton_POS -= obj.divBtnLst.btnSearch.width + sButton_GAP + 8;
		obj.divBtnLst.btnCracel.left = sButton_POS;		
	}
	
}

/*-------------------------------------------------------------------------------------------------+
>>  Message�� '@'���� ����
>>>>  1) sSendMessage : �޼���
>>>>  2) sArg1        : ��ü�� �޼���
+-------------------------------------------------------------------------------------------------*/
function fnc_SetArgument(sSendMessage, sArg1, sArg2, sArg3, sArg4, sArg5) {

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
    if ( Global.FrameBottom != null || ed_msg != null ) {
        ed_msg.Text = fnc_SetArgument(msg, count);
       
        //SetTimer(1000, 9000);
    }
}

function fnc_SetStatus2(msg, count1, count2) {
    if ( Global.FrameBottom != null || ed_msg != null ) {
        ed_msg.Text = fnc_SetArgument(msg, count1, count2);
       // SetTimer(1000, 9000);
        
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

	var deviceInfo = split(GetDeviceInfo(), ";");
	var screen = split(deviceInfo[2], "x");
	var iXPos = (parseInt(screen[0]) - parseInt(464))/2;
	var iYPos = (parseInt(screen[1]) - parseInt(158))/2;
	
	/* //web�� �Ӻ�����Ұ�� ���۵��� �ϹǷ� �� �ҽ��� ��ü��.
	
	var iTop    = Global.window.top;
	var iLeft   = Global.window.Left;
	var iWidth  = Global.window.Width;
	var iHeight = Global.window.Height;
	
	var iXPos = iLeft + (iWidth-464)/2;    //X Position
	var iYPos = iTop  + (iHeight-158)/2;   //Y Position
	*/
	var rtn;
//	Trace("fnc_Message:"+sMessageID);
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

function fnc_GridAllChk(objGrid, nCell){

	var dsObj = object(objGrid.BindDataset);
	if(nCell == null ) nCell=0;

	if (objGrid.GetCellProp("head",nCell,"text") == 1){
	  if(objGrid.GetCellProp("head",nCell,"edit") =="checkbox"){
			objGrid.SetCellProp("head",nCell,"text", "0");
			for ( var i = 0; i< dsObj.getRowCount(); i++) {
				dsObj.SetColumn(i, "CHK" , 0);
			}
	  }
	} else {
		if(objGrid.GetCellProp("head",nCell,"edit") =="checkbox"){
			objGrid.SetCellProp("head",nCell,"text", "1");
			for ( var i = 0; i< dsObj.getRowCount(); i++) {
		
			   if(objGrid.GetCellProp("body",nCell,"edit") != "none"){
					dsObj.SetColumn(i, "CHK" , 1);
			   }
			}
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

 
/* ------------------------------------------------------------------------------------------------------------
* �뵵     :    �׸����� ������ �������Ϸ� �����Ѵ�.(�� �� �پ��� Excel�ٿ�ε� ����� �䱸�ɰ��)
* Syntax   :    gfnExport(strSheet,strGrid)
* �Ķ���� :
				strSheet 	- Excel Sheet ��
				strGrid 	- �׸��� Object
* ����     :	gfnExport("test,test1234","gdList,Grid0");
                gdList�׸��� ������ "test"Sheet�� �����Ѵ�.
                Grid0�׸��� ������ "test1234"Sheet�� �����Ѵ�.
                Excel���ϸ��� ���ϴ�ȭ���ڿ��� ����ڰ� �����Ѵ�.
* ���ϰ�   :    ����
* ������� :    Export Object�� �̿��Ͽ� ����ڰ� ��ũ��Ʈ �󿡼� Export�� ���õ� ����� �����մϴ�.
                �ڼ��� ������ ��������(ExportObject �׸�)
* ��Ÿ     :    ������ ������
 
* ------------------------------------------------------------------------------------------------------------*/
function fcd_topGfnExport(strSheet,strGrid,strFileNm,objForm) {
	var ExportObject1;
	var sXML;

	if (strSheet == null || strSheet == "") {
		alert("Excel Sheet���� �����ϴ�!");
		return;
	}
	if (strGrid == null || strGrid == "") {
		alert("�׸��尡 �������� �ʾҽ��ϴ�!");
		return;
	}
	var vSheet 	= split(strSheet,",","webstyle");	//sheet��Ī
	var vGrid 	= split(strGrid,",","webstyle");	//�׸��� object

	ExportObject1 = objForm.CreateExportObject();
	ExportObject1.ExportType		= "Excel";

	objForm.Destroy("fldExl");
	//�������� ��ȭ����
	objForm.Create("FileDialog","fldExl");
	//���ϼ��� ��ȭ���ڸ� ����.
	objForm.fldExl.Type = "SAVE";
	//Excel���ϸ� ���̵��� �����Ѵ�.
	objForm.fldExl.Filter = "Excel(.xls)|*.xls|";
	objForm.fldExl.FileName = strFileNm;
	//��Ҹ� �����ϸ� ��ȭ���ڸ� �ݴ´�.
	if (!objForm.fldExl.open()){
		return;
	}

	ExportObject1.ExportFileName	= objForm.fldExl.FilePath + "\\" + objForm.fldExl.FileName;
	ExportObject1.ActiveSheetName   = vSheet[0];
	// ExportObject1.ExportSinglePivotColHead = false;

	//������ �������� ������ �����Ѵ�.
	ExportObject1.MakeEmptyFileWhenNotExist = true;
	
	var nStartIdx = 0;
	var aGrd = 0;
	var objTmpGrd;
	for (var i=0;i<vSheet.length;i++) {
		 aGrd = split(vGrid[i],"|","webstyle");
		 
	     if(aGrd.length>1) {
			objTmpGrd = fcdSetGridContent(objForm.object(aGrd[0]),aGrd[1]);
			
	     }else{
			objTmpGrd = objForm.object(aGrd[0]);
	     }
		//������ �������Ͽ� Sheet�� �߰��Ѵ�.
		//strRange: (sheetname!Range ����)
		//GridComp: �׸��� object
		//bExportHead: headcolumn ��¿���
		//bExportValue: value�� �����������(false: �׸��� Style���� ���)

		ExportObject1.AddExportGrid(vSheet[i] + "!" + "A1", objTmpGrd, true, false);
	}

	ExportObject1.Export(true,false); // ù��° progress, �ι�° excel
			ExportObject1.Save();  // <---------  �� �κ� �߰����ּ���  ������ �ٷ� �����Ұ����� ����.
			ExportObject1.CloseWorkbook(); // <---------  �� �κ� �߰����ּ���  excel�� Sheet�� �ݱ�.

	ExportObject1.Close();
	ExportObject1 = null;
}


/*-------------------------------------------------------------------------------------------------+
>>  ���� �ٿ�ε�� Grid Content �����!
+-------------------------------------------------------------------------------------------------*/
function fcdSetGridContent(objGrd,nStartIdx)
{
	if(nStartIdx==0) return;
	
	var grdId =objGrd.id;
	
	//var objDS = Object((sOrgGridNM).object.BindDataset);
	if(Find("grd_excel"+objGrd.id)==null) {
		Create("Grid","grd_excel"+grdId);
	}

	var grdExcelObj =Object("grd_excel"+grdId);
	var nCellCnt;
	var sContent = objGrd.GetCurContents();
	var sConvContent = "";

	var nStartColumnsPos = pos(sContent,"<columns>");
	var nEndColumnsPos = pos(sContent,"</columns>");

	var nStartHeadPos = pos(sContent,"<head>");
	var nEndHeadPos = pos(sContent,"</head>");

	var nStartBodyPos = pos(sContent,"<body>");
	var nEndBodyPos = pos(sContent,"</body>");

	var nStartSummaryPos = pos(sContent,"<summary>");
	var nEndSummaryPos = pos(sContent,"</summary>");

	var sSplit,nSplitCnt,i,sPartContents;

	sConvContent += "<contents>"+"\n";

	if(nStartColumnsPos!=-1) { // <columns></columns>

        sPartContents = mid(sContent,nStartColumnsPos,nEndColumnsPos - nStartColumnsPos+length("</COLUMNS>"));
        sSplit = split(sPartContents,"\n",true,false);
        nSplitCnt = length(sSplit);
        nCellCnt = nSplitCnt - nStartIdx - 2;

        for(i=0; i<nSplitCnt; i++)
            if(i==0 || i>nStartIdx) sConvContent += sSplit[i] + "\n";

        sConvContent += "\n";
    }

    if(nStartHeadPos!=-1) { // <head></head>

        sPartContents = mid(sContent,nStartHeadPos,nEndHeadPos - nStartHeadPos+length("</HEAD>"));
        sSplit = split(sPartContents,"\n",true,false);
        nSplitCnt = length(sSplit);

        for(i=0; i<nSplitCnt; i++)
            if(i==0 || i>nStartIdx) sConvContent += sSplit[i] + "\n";

        sConvContent += "\n";
    }

	if(nStartBodyPos!=-1) { // <body></body>

        sPartContents = mid (sContent,nStartBodyPos,nEndBodyPos - nStartBodyPos+length("</BODY>"));
        sSplit = split(sPartContents,"\n",true,false);
        nSplitCnt = length(sSplit);

        for(i=0; i<nSplitCnt; i++)
            if(i==0 || i>nStartIdx) sConvContent += sSplit[i] + "\n";

        sConvContent += "\n";
    }

	if(nStartSummaryPos!=-1) {

        sPartContents = mid (sContent,nStartSummaryPos,nEndSummaryPos - nStartSummaryPos+length("</SUMMARY>"));	
        sSplit = split(sPartContents,"\n",true,false);
        nSplitCnt = length(sSplit);

        for(i=0;i<nSplitCnt;i++)
            if(i==0 || i>nStartIdx) sConvContent += sSplit[i] + "\n";

        sConvContent += "\n";
    }

	sConvContent += "</contents>";

	//objDS.FireEvent = false;
	grdExcelObj.Contents = sConvContent;
	//objDS.FireEvent = true;

	for(i=0;i<nCellCnt;i++)
	{
		if(nStartHeadPos!=-1) grdExcelObj.SetCellProp("head",i,"col",i);
		if(nStartBodyPos!=-1) grdExcelObj.SetCellProp("body",i,"col",i);
		if(nStartSummaryPos!=-1) grdExcelObj.SetCellProp("summary",i,"col",i);
	}
	
	


    grdExcelObj.BindDataset = objGrd.BindDataset;

    return grdExcelObj;
}



/**************************************************************************************************
*  ��   ��  ��				: fcd_OpenExcelListExport
*  ��       ��				: ������ ���ϸ��� excel �����Ѵ�.
*  return Type				: Stirng
*  return ����				: ���ϸ�
**************************************************************************************************/
function fcd_OpenExcelListExport(sExcelList,nStartCell) {
    var asGridList = split(sExcelList, "|");
    if ( asGridList.length() == 1 ) {
       
        fnc_OpenExcelExport(asGridList[0],nStartCell);
	}
	
	var sFormId = tab_Menu.GetItem(nNewindex).id;
	http.Sync = true;
	GBL_PERSONALMN_CHK = "N";	
	fnc_PersonalMnChk(sFormId);
	http.Sync = false;
	
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
    
    var sFormId = tab_Menu.GetItem(nNewindex).id;
	http.Sync = true;
	GBL_PERSONALMN_CHK = "N";	
	fnc_PersonalMnChk(sFormId);
	http.Sync = false;
	
    var sArg;
    aGridId = split(sGridId, ":","webstyle");
    sGridId = aGridId[0];
    
    var sExcelFileNm = "";
    
    if(aGridId.length > 2){
		sExcelFileNm = aGridId[2];
    }
    
  
     
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
        sArg += iif(Length(sExcelFileNm)>0, " sExcelFileNm=" + quote(sExcelFileNm), "");


    } else {

        fnc_Message(L_COM_ALT_0002, "N", "Grid", sGridId);
        return;
    }
	
    var dlgRtn = Dialog("common::comExcelDown.xml", sArg, '440','170', true, -1, -1);

    if(dlgRtn) ExecShell(L_exeFile);

    return;
}





/*-------------------------------------------------------------------------------------------------+
>>  ��Ƽ �׸��� �������Ϸ� �������� ���� Function
>>  �׸��� BindDataset ���ڵ� �� üũ - 0 �� ���  alert return;
>>  �׸��� ��ü ��ȿ�� üũ - ���� �� alert, return;
>>>>  param1)  sGrid        : ��� Grid ID
>>>>  param2)  nStartCell   : ���� cell index number
>>>>  param3)  bExcYn       : �������α׷� ����' flag [true/false]
+-------------------------------------------------------------------------------------------------*/
function fnc_OpenMultiExcelExport(sGridId, sFileNm) {
    
    var sFormId = tab_Menu.GetItem(nNewindex).id;
	http.Sync = true;
	GBL_PERSONALMN_CHK = "N";	
	fnc_PersonalMnChk(sFormId);
	http.Sync = false;
	
    var sArg;
    
    var arrGrdExcel	= split2(sGridId,"|",":");
    var arrDsExcel	= array(arrGrdExcel.length);
    
    var nGrdFail = 0;
    var nDsFail =0;
    
    for(var i=0;i<arrGrdExcel.length;i++)
    {
		arrGrdExcel[i] = Object(arrGrdExcel[i][0]);		
		arrDsExcel[i] = Object(arrGrdExcel[i].BindDataset);
		
		if(!isValid(arrGrdExcel[i])) 
		{			
			nGrdFail++;
		}
		if(!isValid(arrDsExcel[i])) 
		{
			nDsFail++;
		}
    }
	
	
    if(nGrdFail==0) {
        if(nDsFail==0) {
        	/*
			for(var i=0;i<arrDsExcel.length;i++)
			{
				if(arrDsExcel[i].RowCount()<1)
				{
					fnc_Message(L_COM_ALT_0003, "N");
					return;
				}
			}
			*/

        } else {

            fnc_Message(L_COM_ALT_0002, "N", "Dataset", objGrdExcel.BindDataset);
            return;
        }

        sArg = "objGridId=" + quote(sGridId);
        sArg += iif(sFileNm!=null, " sFileNm=" + quote(sFileNm), "");
//        sArg += iif(nStartCell!=null, " nStartCell=" + quote(nStartCell), "");
//        sArg += iif(bExcYn!=null, " sExcYn=" + quote(bExcYn), "");
        
    } else {

        fnc_Message(L_COM_ALT_0002, "N", "Grid", sGridId);
        return;
    }
    
    
    var dlgRtn = Dialog("common::comMultiExcelDown.xml", sArg, '440','170', true, -1, -1);

//    if(dlgRtn) ExecShell(L_exeFile);

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

	remote_url =  GBL_FILE_UPDNLOAD_URL+"?mode=upload&work=" + sWork +" &subDir="+SubStr(GBL_MENUID,0,3);	// ���� ���ε��� ��� mode�� upload�� ����
    //trace("fnc_FileWrite():"+remote_url);
	file_size = httpfileObj.GetFileSizeLocal(sLocalFilePath);
 
	httpfileObj.CookieParam = quote(sLocalFileName);		// ���ϸ��� ��⿡ �־���
	
	//trace(sLocalFilePath);
	//trace(sLocalFileName);
	
	ret = httpfileObj.OpenFile(remote_url, sLocalFilePath, "PUT");
	//trace(ret);
	if( ret < 0 )	{
		rtn_arr[0] = "FAIL";
		rtn_arr[1] = "OpenFile Failed :: " + httpfileObj.ErrorMsg;
		return rtn_arr;
	}
	while(1) {
        //trace("in while");
		write_size = httpfileObj.WriteFile(upload_packetSize);
        //trace("size --> " + write_size);
		tot_write_size += write_size;
			
		if( write_size < upload_packetSize )	
		{
			break;
		}
	}
	httpfileObj.CloseFile();

	var rtn_val = httpfileObj.CookieParam;	// �����κ��� ���� ��Ű�� ���� ������ ����
	
	var str_sp = split(rtn_val,"&","webstyle");     // ��Ű�� �����ڷ� �и��Ͽ� �����ϱ� ���� ����
	var tmp_a;
	//trace("rtn_val >>> " + rtn_val);
	
	for ( var i = 0 ; i < str_sp.length() ; i++ )
	{
		tmp_a =split(str_sp[i],"^","webstyle");
	
		if (tmp_a[1] == "FAIL")
		{
			rtn_arr[0] = "FAIL";
			rtn_arr[1] = Replace(tmp_a[1],"\"","");
			return;
		}
		else
		{
			rtn_arr[i+0] = Replace(tmp_a[1],"\"","");
		}
	}

	return rtn_arr;
}



function fnc_PCFileUploader(httpfileObj, sLocalFilePath, sLocalFileName,sUploadRoot, sWork,sExt)
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


	remote_url =  GBL_FILE_UPDNLOAD_URL+"?mode=pcfileupload&sUploadRoot="+sUploadRoot+"&work=" + sWork +"&sExt="+sExt+"&subDir="+SubStr(GBL_MENUID,0,3);	// ���� ���ε��� ��� mode�� upload�� ����
	

    //trace(remote_url);
	file_size = httpfileObj.GetFileSizeLocal(sLocalFilePath);
 
	httpfileObj.CookieParam = quote(sLocalFileName);		// ���ϸ��� ��⿡ �־���
	
	//trace(sLocalFilePath);
	//trace(sLocalFileName);
	
	ret = httpfileObj.OpenFile(remote_url, sLocalFilePath, "PUT");
	//trace(ret);
	if( ret < 0 )	{
		rtn_arr[0] = "FAIL";
		rtn_arr[1] = "OpenFile Failed :: " + httpfileObj.ErrorMsg;
		return rtn_arr;
	}
	while(1) {
        //trace("in while");
		write_size = httpfileObj.WriteFile(upload_packetSize);
        //trace("size --> " + write_size);
		tot_write_size += write_size;
			
		if( write_size < upload_packetSize )	
		{
			break;
		}
	}
	httpfileObj.CloseFile();

	var rtn_val = httpfileObj.CookieParam;	// �����κ��� ���� ��Ű�� ���� ������ ����
	
	var str_sp = split(rtn_val,"&","webstyle");     // ��Ű�� �����ڷ� �и��Ͽ� �����ϱ� ���� ����
	var tmp_a;
	//trace("rtn_val >>> " + rtn_val);
	
	for ( var i = 0 ; i < str_sp.length() ; i++ )
	{
		tmp_a =split(str_sp[i],"^","webstyle");
	
		if (tmp_a[1] == "FAIL")
		{
			rtn_arr[0] = "FAIL";
			rtn_arr[1] = Replace(tmp_a[1],"\"","");
			return;
		}
		else
		{
			rtn_arr[i+0] = Replace(tmp_a[1],"\"","");
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
function fnc_PCFileRead(httpfileObj, sLocalFileName, sServerFileName, nFileSize, nRow, strCol, dsObj) {
	
	if ( fnc_IsNull(sLocalFileName) ) {
		fnc_Message(L_COM_ALT_0038, "N");
		return false;
	}

	if ( fnc_IsNull(sServerFileName) ) {
		fnc_Message(L_COM_ALT_0014, "N");
		return false;
	}

    var sRemoteUrl = GBL_FILE_UPDNLOAD_URL+"?mode=download&sServerFileName="+sServerFileName;
    
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

/**************************************************************************************************
*  ��   ��  ��				: fcd_GetSelectPCFile
*  ��       ��				: ������ ���ϸ��� ���Ѵ�.
*  return Type				: Stirng
*  return ����				: ���ϸ�
**************************************************************************************************/
function fcd_GetSelectPCFile(sType, sFileName) {
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
function fnc_FileRead(httpfileObj, sLocalFileName, sServerFileName, nFileSize, nRow, strCol, dsObj, viewer) {
/*
	var sFileName = sLocalFileName;	
	var nIdx = IndexOf(sFileName, ".");

	var gLocalTempDir= "Temp";
	
	if (httpfileObj.RemoveDir(gLocalTempDir, true) == false) Trace("Remove dir error : " + gLocalTempDir);
	if (!httpfileObj.IsExistFile(gLocalTempDir)) httpfileObj.MakeDir(gLocalTempDir);
	
	gTempFileName = sFileName;
	lFcOut_ExecTransaction("GetAttchFile");





	httpfileObj.FileName = sServerFileName;
	httpfileObj.Open("wb");

	httpfileObj.WriteBinary("", 1);
	httpfileObj.Close();

	var sFile =sServerFileName;
	ExecShell( sFile );
*/	

   


	//sLocalFileName = replace(sLocalFileName,"\\","");
	
	//if(!IsValid(object("file_attch"))) {
		Create("File","file_attch");
	//}
	

	if (file_attch.RemoveDir(gLocalTempDir, true) == false) {
		//Trace("Remove dir error : " + gLocalTempDir);
	}
	if (!file_attch.IsExistFile(gLocalTempDir)) file_attch.MakeDir(gLocalTempDir);

	Destroy("file_attch");
	
	sLocalFileName = gLocalTempDir+sLocalFileName;
	
	if ( fnc_IsNull(sLocalFileName) ) {
		fnc_Message(L_COM_ALT_0038, "N");
		return false;
	}

	if ( fnc_IsNull(sServerFileName) ) {
		fnc_Message(L_COM_ALT_0014, "N");
		return false;
	}

    var sRemoteUrl = GBL_FILE_UPDNLOAD_URL+"?mode=download&sServerFileName="+sServerFileName;
    
        
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
	if (fnc_isnull(viewer))	ExecShell(sLocalFileName);
	else execProc(viewer, sLocalFileName);

    return true;
}



/*-------------------------------------------------------------------------------------------------+
>>  ����ڰ� ������ ������ �������� �����ϴ� Function
>>>>  param1)  str_param    : ������ �����Ϸ��� �������� ���ϸ�
+-------------------------------------------------------------------------------------------------*/
function fnc_FileDelete(str_param)
{

	remote_url = GBL_FILE_UPDNLOAD_URL + "?mode=delete&subDir=" + SubStr(GBL_MENUID,0,3)+ "&filename=" + str_param; 
	         
    Transaction("", remote_url, "", "", "fcCallBack", false);
    
}




/******************************************************
 *  ��ü Callback 
 ******************************************************/
function fnc_Result(errCode, errMsg) {
	 
	fnc_ProgressClose();  //���α׷����� �ݱ� 

//Trace("fnc_Result::ServiceMessageCode:"+ServiceMessageCode);    
//Trace("fnc_Result::errCode:"+errCode);    
//Trace("fnc_Result::"+errCode +"|"+errMsg+"|" +ServiceMessage + "|" + ServiceMessageCode);
    //����ڿ���ó���� ���ؼ� �߰����
    if ( errCode != 0 ) {
		
		//30���̻� �̻��� �α׾ƿ�, �ƴҰ�� ���� �޽��� ǥ��
		if(errMsg.indexOf("### CONTEXT IS NULL ###") != -1){
			fnc_Message(SNIS_COM_1062, "N");
			exit();
		}else
		{
			fnc_Message("SERVERMSG", "N", errMsg); 
		}
		
		//�ߺ��α����� ��� �ý��� ����
		if(errMsg.indexOf("DUP") != -1){
			exit();
		}
		
		return false;
    }

    if ( !fnc_IsNull(ServiceMessage) ) {
        fnc_Message(ServiceMessage, "N");
        ServiceMessage = NULL;
    }
    if ( !fnc_IsNull(ServiceMessageCode) ) {
        fnc_Message(eval(ServiceMessageCode), "N");
        if (GBL_DUP_LOGIN_CHK == "Y") {
			if (ServiceMessageCode == "L_COM_ALT_0028") exit(); // 2013.5.12
		}
        if (ServiceMessageCode == "SNIS_RBM_E026" || ServiceMessageCode == "SNIS_RBM_E028") {
			ServiceMessageCode = NULL;
			return false;		
		}
		ServiceMessageCode = NULL;
    }
    
    if ( !fnc_IsNull(SearchCount) ) {
		fnc_SetStatus(SNIS_CRA_0003, SearchCount); //�ϴ� ���¹ٿ� �޽��� ó��
        //fnc_Message(SNIS_CRA_0003, "N", SearchCount);
        SearchCount = NULL;
    }

    if ( !fnc_IsNull(SaveCount) && !fnc_IsNull(DeleteCount) ) {
        fnc_Message(SNIS_CRA_0006, "N", SaveCount, DeleteCount);
		fnc_SetStatus(SNIS_CRA_0006, SaveCount, DeleteCount);
        SaveCount = NULL;
        DeleteCount = NULL;
    } else {
		if ( !fnc_IsNull(SaveCount) ) {
			fnc_SetStatus(SNIS_CRA_0004, SaveCount);
			//fnc_Message(SNIS_CRA_0004, "N", SaveCount);
			SaveCount = NULL;
		}
	
		if ( !fnc_IsNull(DeleteCount) ) {
			fnc_SetStatus(SNIS_CRA_0005, DeleteCount);
			//fnc_Message(SNIS_CRA_0005, "N", DeleteCount);
			DeleteCount = NULL;
		}
	}

    return true;
}


/******************************************************
 *   Callback  (progress bar x )
 ******************************************************/
function fnc_ResultNP(errCode, errMsg) {


	//trace(errCode +":--:"+errMsg+":--:" +ServiceMessage + ":--:" + ServiceMessageCode);
    //����ڿ���ó���� ���ؼ� �߰����
    if ( errCode != 0 ) {
		fnc_Message("SERVERMSG", "N", errMsg); 
		return false;
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
		fnc_SetStatus(SNIS_CRA_0003, SearchCount); //�ϴ� ���¹ٿ� �޽��� ó��
        //fnc_Message(SNIS_CRA_0003, "N", SearchCount);
        SearchCount = NULL;
    }

    if ( !fnc_IsNull(SaveCount) && !fnc_IsNull(DeleteCount) ) {
        fnc_Message(SNIS_CRA_0006, "N", SaveCount, DeleteCount);
		fnc_SetStatus(SNIS_CRA_0006, SaveCount, DeleteCount);
        SaveCount = NULL;
        DeleteCount = NULL;
    } else {
		if ( !fnc_IsNull(SaveCount) ) {
			fnc_SetStatus(SNIS_CRA_0004, SaveCount);
			//fnc_Message(SNIS_CRA_0004, "N", SaveCount);
			SaveCount = NULL;
		}
	
		if ( !fnc_IsNull(DeleteCount) ) {
			fnc_SetStatus(SNIS_CRA_0005, DeleteCount);
			//fnc_Message(SNIS_CRA_0005, "N", DeleteCount);
			DeleteCount = NULL;
		}
	}

    return true;
}


/*-------------------------------------------------------------------------------------------------+
>>  ������ ǥ�� �˾� �����ϱ� ���� Function
+-------------------------------------------------------------------------------------------------*/
function fnc_ProgressPop() {
    fv_nHandle = Open("common::comPopProgress.xml", "", 294, 112, "Title=false");
}



/*-------------------------------------------------------------------------------------------------+
>>  ������ ǥ�� �˾� Close�ϱ� ���� Function
+-------------------------------------------------------------------------------------------------*/
function fnc_ProgressClose() {
	var objfrm = GetFormFromHandle(fv_nHandle);	
	fnc_SetWaitCursor(false);
	if( objfrm != null)	objfrm.Close();
	
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
function fnc_DataSetCracel(obj) {
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


/*-------------------------------------------------------------------------------------------------+
>> ���ڷ� ���� ���ڿ��� Null �� ��� �Ѿ�� ������ �� ����� �����ش�.
>>>>  param1)  sValue        : ���ڿ� ��
>>>>  param2)  sNullValue        : ���ڿ� ��
>>>>  return :  param1, param2
+-------------------------------------------------------------------------------------------------*/
function fnc_NVL(sValue, sNullValue) {
	if ( sValue == null ) return sNullValue; 
	if ( length(trim(sValue)) < 1 ) return sNullValue;

	return sValue;
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

/************************************************************************
*  ��   ��  �� : fnc_IsTime
*  ��       �� : �ð��������� üũ
*  argument    : hh24, hh24mi, hh24miss
*  return Type : boolean
*  return ���� : true/false
************************************************************************/
function fnc_IsTime(sTime) {
    var isOK = true;

	var nHH = 0;
	var nMI = 0;
	var nSS = 0;

    if (sTime.length == 6) {
		nHH = toInteger(sTime.substr(0, 2));
		nMI = toInteger(sTime.substr(2, 2));
		nSS = toInteger(sTime.substr(4, 2));
    } else if (sTime.length == 4) {
		nHH = toInteger(sTime.substr(0, 2));
		nMI = toInteger(sTime.substr(2, 2));
    } else if (sTime.length == 2) {
		nHH = toInteger(sTime.substr(0, 2));
    } else {
		isOK = false;
    }

	if (nHH > 23) {
		isOK = false;
	} else if (nMI > 59) {
		isOK = false;
	} else if (nSS > 59) {
		isOK = false;
	}

    return isOK;
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
*  ��   ��  �� : fnc_Sqmt
*  ��       �� : ����(m2)�� ����� ������ֱ�
*  argument    : 1.sValue  : �ش� ����(m2)
				 2.iDigit  : �ݿø��� �ڸ���
*  return Type : String
*  return ���� : ���ڷ� �̷���� ���ڿ�
************************************************************************/
function fnc_Sqmt(sValue, iDigit)
{
    var sReturn = "";

    sReturn = round(toFloat(sValue) * 0.3025, iDigit);

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


function fnc_DateFormat(argDate, argFormat) {
	var formatDate = "";

	if (fnc_IsNull(argDate)) return "";

	if (fnc_IsNull(argFormat)) argFormat = "-";

	// argDate = clearSpecialChar(argDate);

	if (argDate.length == 8) {
		formatDate = argDate.substr(0, 4) + argFormat +
					 argDate.substr(4, 2)  + argFormat +
					 argDate.substr(6, 2);
	} else if (argDate.length == 6) {
		formatDate = argDate.substr(0, 4) + argFormat +
					 argDate.substr(4, 2);
	} else {
		formatDate = argDate;
	}

	return formatDate;
}


/*-------------------------------------------------------------------------------------------------+
>>  ȭ�� ���� �� ���� ���� �� Ȱ��ȭ Form DataSet ó��
>>>>  1)  DataSet Object
+-------------------------------------------------------------------------------------------------*/
function fnc_FormUnloadCheck(obj, sFormID, sFormID2, sFormID3, sFormID4, sFormID5) {
    if(GBL_LOGOUT_GBN =="Y"){
		
    }else{

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
				return false;    //�������� �ʰ� Unload Cracel ó�� �Ѵ�.
			}		
		}
	}
	
	
	if ( global.FrameBottom != null ) {	
        global.FrameBottom.fcDeleteTab(sFormID);
    }
    if ( global.FrameBottom != null ) {	
        global.FrameBottom.fcDeleteTab(sFormID2);
    }
    if ( global.FrameBottom != null ) {	
        global.FrameBottom.fcDeleteTab(sFormID3);
    }
    if ( global.FrameBottom != null ) {	
        global.FrameBottom.fcDeleteTab(sFormID4);
    }
    if ( global.FrameBottom != null ) {	
        global.FrameBottom.fcDeleteTab(sFormID5);
    }
    return true;
}


/*-------------------------------------------------------------------------------------------------+
>>  �� ���� �� �����ͼ� ���濩�� üũ
>>>>  1)  DataSet Object
+-------------------------------------------------------------------------------------------------*/
function fnc_TabDsUpdateCheck(obj) {

	sDataset = split(obj, "|");

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
		if ( !fnc_Message(SNIS_RBM_A006, "Y") ) {
			return false;    //���� �������� �ʴ´�
		}
	}
	
	//�����ͼ� �ʱ�ȭ
	for ( var i = 0; i < sDataset.length(); i++ ) {
        objDataset = Object(sDataset[i]);
        objDataset.Clear();
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
				oForm.Components[i].BKColor = "#dcdcdc";
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
		if ( DataSet.GetRowType(i) != "normal" && DataSet.GetRowType(i) != "logical" ) {

            // üũ�ؾ� �ʵ� ����ŭ �ش� ���� �˻��Ѵ�.
            for ( j = 0; j < MandatoryColID.length(); j++ ) {

                // �ʵ尪�� ���� ���� ��� �޼����� ȣ�� �� �� �ش� Row��ȣ�� return �Ѵ�.
                if ( fnc_IsNull(DataSet.GetColumn(i, MandatoryColID[j])) ) {
                    DataSet.SetColumn(i, MandatoryColID[j], trim(DataSet.GetColumn(i, MandatoryColID[j])));
					fnc_Message(SNIS_CRA_0001, "N", i + 1, MandatoryColName[j]); 
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

function fnc_RPTPrint(sSubUrl, sRptNm, sDataSet, sArg, sRptDrct, sPrintCnt, sRptType, sPageNo) {
    var sFileName  = "";
    var iWidth     = 1014;
    var iHeight    = 630;
    
	if (sRptDrct == 0){ //����
		sFileName  = "common::comVrtRptViewer.xml";
		iWidth  = 760;  		
	}  else if(sRptDrct == 2) {
		sFileName  = "common::comVrtRptViewerRevision.xml";
		iWidth  = 760;  	
	} else { //����
		sFileName  = "common::comHrzRptViewer.xml";
	}
	
	var arrArgumentList = split2(sArg, "^", ",");                        //Argument Array List

	Dialog(sFileName, " sSubUrl="  + quote(sSubUrl) +
	                  " sRptNm="   + quote(sRptNm) + 
	                  " sDataSet=" + quote(sDataSet) + 
	                  " sArg="     + quote(sArg) +
	                  " sPrintCnt="    + quote(sPrintCnt) +
	                  " sRptType="     + quote(sRptType) +
	                  " sPageNo=" + quote(sPageNo) +
	                  " fSQLCallFlag=false",
	                  iWidth, iHeight, "Scroll=true,titlebar=true,Resize=true,Autosize=false", -1, -1);
}

//
function fnc_SetReturnDs(dsSrc, rowIdx, dsReturn) {

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

/*-------------------------------------------------------------------------------------------------+
*  ��   ��  �� : fnc_GetWeekOfYear
*  ��       �� : �Էµ� ���ڿ� �ش��ϴ� ��(Week ��ȯ) - �����Ͽ� ������ ������ ����
*  argument    : 1.date  : ����
*  return Type : Int
*  return ���� : week
+-------------------------------------------------------------------------------------------------*/
function fnc_GetWeekOfYear(date) {
	var sYear = SubStr(date, 0, 4);
	var sSDate = "";
	var sEDate = "";
	
	if ( !IsValid(object("calTemp")) ){
		Create("Calendar", "calTemp");
		calTemp.visible = false;
	}
	
	calTemp.Text = sYear + "0101";
	
	sSDate = AddDate(calTemp.GetStartOfWeek, 1);
	sEDate = AddDate(calTemp.GetEndOfWeek, 1);
	
	var nWeek = 1;
	for (nWeek = 1; nWeek < 56; nWeek++) {	
		if (date >= sSDate  && date <= sEDate) break;		
		sSDate = AddDate(sSDate, 7);
		sEDate = AddDate(sEDate, 7);
	}
	
	return nWeek;
}

/*-------------------------------------------------------------------------------------------------+
*  ��   ��  �� : fnc_GetReturnParam
*  ��       �� : �������� ������ Parameter �� ��ȸ
*  argument    : 1.sKey  - Parameter Key
*  return Type : String
*  return ���� : Parameter ��
+-------------------------------------------------------------------------------------------------*/
function fnc_GetReturnParam(sKey) {
	var sRet = "";
	var arrRet = split(GBL_RET_VALUE, "^");	
	for (var i=0; i<arrRet.length; i++) {
		var arrKeys = split(arrRet[i], "=");
		if (arrKeys[0] == sKey) {
			sRet = arrKeys[1];
			break;
		}
	}

	return sRet;

}

/*-------------------------------------------------------------------------------------------------+
*  ��   ��  �� : fnc_SetCnfmButton
*  ��       �� : ȸ�� ���·� Ȯ����ư ó��
*  argument    : 1.arrStat- ���� Array
*  argument    : 2.objBtn  - Ȯ����ư
*  argument    : 3.title1  - Ȯ���� Text
*  argument    : 4.title2  - ��ҽ� Text
*  return Type : String
*  return ���� : Parameter ��
+-------------------------------------------------------------------------------------------------*/
function fnc_SetCnfmButton(arrStat, objBtn, title1, title2) {
	//0:Ȯ������ 1:��Ұ��ɿ��� 2:���¸�, 3:Ȯ�����ɿ���
	//trace(arrStat[0]+":"+arrStat[1]+":"+arrStat[2]+":"+arrStat[3]+":"+arrStat[4]);	
	objBtn.Enable = false;
	
	if (arrStat[3] == "Y" && arrStat[0] == "N") {	//Ȯ������ && ��Ȯ��
		objBtn.Text = title1;
		objBtn.Enable = true;
		objBtn.UserData = "Ȯ��";
	} else if (arrStat[1] == "Y") {					//��Ұ���
		objBtn.Text = title2;
		objBtn.Enable = true;		
		objBtn.UserData = "���";
	} else if (arrStat[0] == "Y" && arrStat[1] == "N") {
		objBtn.Text = title2;		
	}
}

function fnc_CreateGridDivCode(obj, cdCol) {	
	var divId = "div_" + obj.Id + "_" + cdCol;
	this.Create("Div", divId, 'Visible="false" ');
	
	var objDiv = object(divId);
	objDiv.url = DIV_CODE_URL;
	return objDiv;
}


function fnc_SetGridDivCode(obj, codeType, codeGubun, dsId, cdCol, nameCol, val1, val2, val3, val4, val5) {		
	var objDiv = object( "div_" + obj.Id + "_" + cdCol);

	objDiv.setDivGridInit(codeType, codeGubun, val1, val2, val3, val4, val5); 
	objDiv.setDivDataset(dsId, cdCol, nameCol); 
}

function fnc_GetGridDivCode(obj, cdCol, val1, val2, val3, val4, val5) {
	if (ToUpper(obj.GetType()) == "DATASET") {	
		alert("�׸��带 �����ϼ���!");
		return;
	}

	var objDiv = object( "div_" + obj.Id + "_" + cdCol);

	if (!fnc_isNull(val1))
		objDiv.setDivOptionParamInit(val1, val2, val3, val4, val5);
	objDiv.edCode_OnKillFocus(objDiv.edCode);
}

function fnc_ShowGridDivCode(obj, cdCol, val1, val2, val3, val4, val5) {
	var objDiv = object( "div_" + obj.Id + "_" + cdCol);
	if (!fnc_isNull(val1))
		objDiv.setDivOptionParamInit(val1, val2, val3, val4, val5);
	objDiv.fcHelpDialoge(objDiv.btnCode, 0, 0);
}

/*-------------------------------------------------------------------------------------------------+
*  ��   ��  �� : fnc_DataSizeCheck
*  ��       �� : from~to �ϰ�� to�� from���� ũ���ʵ��� üũ�Ѵ�.
*  argument    : 1.fromYear     - from�⵵ object
*  argument    : 2.toYear       - to�⵵ object
*  argument    : 3.fromTms      - fromȸ�� object
*  argument    : 4.toTms        - toȸ�� object
*  argument    : 5.fromDayOrd   - from���� object
*  argument    : 6.toDayOrd     - to���� object
*  return Type : Boolean
*  return ���� : True, False
+-------------------------------------------------------------------------------------------------*/
function fnc_DataSizeCheck(objFromYear, objToYear, objFromTms, objToTms, objFromDayOrd, objToDayOrd){
    //1.�⵵üũ
    if (objFromYear == null || objToYear == null) {
        fnc_Message(SNIS_COM_0001, "Y", "�⵵");
        return false;
    }
    
    if (objFromYear.value > objToYear.value) {
        fnc_Message(SNIS_COM_1040, "Y", "����⵵", "���۳⵵");
        objToYear.SetFocus();
        return false;
    }
    
    //2.ȸ��üũ
    if (objFromTms != null && objToTms != null) {
        if (objFromYear.value > objToYear.value && objFromTms.value > objToTms.value) {
            fnc_Message(SNIS_COM_1040, "Y", "����ȸ��", "����ȸ��");
            objToTms.SetFocus();
            return false;
        }
    }
    
    //3.����üũ
    if (objFromDayOrd != null && objToDayOrd != null) {
        if (objFromYear.value > objToYear.value && objFromTms.value > objToTms.value && objFromDayOrd.value > objToDayOrd.value) {
            fnc_Message(SNIS_COM_1040, "Y", "��������", "��������");
            objToDayOrd.SetFocus();
            return false;
        }
    }    
    
    return true; 
}

/*-------------------------------------------------------------------------------------------------+
*  ��   ��  �� : fnc_IsDirectory
*  ��       �� : ���丮 ���翩�θ� Ȯ���ؼ� ���丮 �����Ѵ�.
*  argument    : strFilePath  >>> �ش� ���丮 ���
*  return Type : 
*  return ���� : 
+-------------------------------------------------------------------------------------------------*/
function fnc_IsDirectory(strFilePath){
    if ( !IsValid(object("divBtnLst.flImage")) ){
        Create("file", "divBtnLst.flImage");
		Create("fileDialog","divBtnLst.fldImage");
	}
	
	if ( !IsValid(object("dsTmp")) ){
        Create("Dataset", "dsTmp");	
	}
	
	var nFileCount = divBtnLst.fldImage.GetFileList( dsTmp, strFilePath, "*.*" );
	if( nFileCount = -1 )                           //���丮�� ���� ���� �ʴ´�.
	{
        divBtnLst.flImage.MakeDir(strFilePath);     //���丮 ����
	}	

}

/*-------------------------------------------------------------------------------------------------+
*  ��   ��  �� : fnc_CreateHtmlFile
*  ��       �� : html ������+���ϸ�, �ش系���� �޾� html������ ����
*  argument    : 1.strFilePath  >>> �ش� ���丮�� ���ϸ�
*  argument    : 2.strContents  >>> ȭ�鿡�� �������� ����
*  return Type : 
*  return ���� : 
+-------------------------------------------------------------------------------------------------*/
function fnc_CreateHtmlFile(strFilePath, strContents){
	var strHtml = "";
    
    strHtml =  "<HTML>";
    strHtml += "<HEAD>";
    strHtml += "<meta http-equiv='Content-Type' content='text/html; charset=EUC-KR'>";
    strHtml += "</HEAD>";
    strHtml += "<BODY>";
    strHtml += strContents;
    strHtml += "</BODY>";
    strHtml += "</HTML>";
   
   divBtnLst.flImage.FileName = strFilePath;
   divBtnLst.flImage.delete();
   if( divBtnLst.flImage.open("a+") ) 
	{	        
		var len = divBtnLst.flImage.write(strHtml);
		divBtnLst.flImage.close();
	} 
        
}

function fnc_CheckDateBetween(fDate, tDate) {
	if (fDate > tDate) {
		var msg = "����/�������ڸ� Ȯ���ϼ���!.";
		fnc_Message(SNIS_CRA_B999, "N", msg);		
		return false;
	} 
	
	return true;
}


/*-------------------------------------------------------------------------------------------------+
*  ��   ��  �� : fnc_CreateGridPopDivRacerRec
*  ��       �� : �׸��忡�� �������ֱ�� �˾��� �������ش�.
*  argument    : 
*  argument    : 
*  return Type : 
*  return ���� : 
+-------------------------------------------------------------------------------------------------*/
function fnc_CreateGridPopDivRacerRec() {
	if(Find("PopDiv_RacerRec")==null) {
		Create("PopupDiv", "PopDiv_RacerRec", "width='404' height='320' border='RAISED' url='common::comRaceRecPopup.xml'");
	}	
}

/*-------------------------------------------------------------------------------------------------+
*  ��   ��  �� : fnc_TrackPopDivRacerRec
*  ��       �� : �׸��忡�� �������ֱ�� �˾����ش�. 
*  argument    : 
*  argument    : 
*  return Type : 
*  return ���� : 
+-------------------------------------------------------------------------------------------------*/
function fnc_TrackPopDivRacerRec(gridObj, nRow, nCell) {
	
	PopDiv_RacerRec.fcInit();
	
	var arr_val =  gridObj.GetCellRect(nRow,3);
	var div_x = ClientToScreenX(gridObj, arr_val[0]);
	var div_y = ClientToScreenY(gridObj, arr_val[1]);
	var div_w = arr_val[2] - arr_val[0];
	var div_h = arr_val[3] - arr_val[1];	
		
	var ds = object(gridObj.BindDataset);	
	ds.row = nRow;
	gridObj.SetFocus();	

	PopDiv_RacerRec.racerNo = ds.GetColumn(nRow, "RACER_NO");
	PopDiv_RacerRec.stndYear = ds.GetColumn(nRow, "STND_YEAR");
	PopDiv_RacerRec.meetCd = ds.GetColumn(nRow, "MEET_CD");
	PopDiv_RacerRec.tms = ds.GetColumn(nRow, "TMS");
	PopDiv_RacerRec.dayOrd = ds.GetColumn(nRow, "DAY_ORD");
	PopDiv_RacerRec.raceNo = ds.GetColumn(nRow, "RACE_NO");	
	
	PopDiv_RacerRec.fcSearch();
	PopDiv_RacerRec.fcSelectedParentDsRow(ds, nRow);
		
	PopDiv_RacerRec.TrackPopup(div_x, div_y, div_w, div_h);	

	//Open("common::comRaceRecPopup.xml", "",div_w,div_h, "Title=false TitleBar=false" ,div_x,div_y);
}

function fnc_ShowRacerRec(gridObj, stndYear, racerNo, nRow, nCell) {
	var sArgs = "";
	sArgs   = " STND_YEAR=" + quote(stndYear) + 
			  " RACER_NO=" + quote(racerNo) + 
			  " POPUP_WIDTH=" + quote(800) + 
			  " POPUP_HEIGHT=" + quote(560) ;
			  
	if (gridObj != NULL && gridObj.GetType() == "Grid")  {
		var arr_val =  gridObj.GetCellRect(nRow,nCell + 1);
		var div_x = ClientToScreenX(gridObj, arr_val[0]);			
		sArgs += " POPUP_LEFT=" + quote(div_x) ;
		
		var ds = object(gridObj.BindDataset);
		if (ds != NULL) {
			ds.row = nRow;
			gridObj.SetFocus();
		} else {
			gridObj.SetFocus();		
		}
		
	} else {
		sArgs += " POPUP_LEFT=" + quote(-1);
	}
	
	Dialog("frm_jfo::JFO0033.xml", sArgs, 560, 800, "");
	
}

function fnc_ShowRacerInfo(gridObj, racerNm, nRow, nCell) {
	var sArgs = "";
	sArgs   = " RACER_NM=" + quote(racerNm) ;
			  
	if (gridObj != NULL && gridObj.GetType() == "Grid")  {
		var arr_val =  gridObj.GetCellRect(nRow,nCell + 1);
		var div_x = ClientToScreenX(gridObj, arr_val[0]);			
		
		var ds = object(gridObj.BindDataset);
		if (ds != NULL) {
			ds.row = nRow;
			gridObj.SetFocus();
		} else {
			gridObj.SetFocus();		
		}		
	}
	
	Dialog("frm_jpl::JPL0011.xml", sArgs, 560, 800, "");
}



function fnc_GetWeekOfDay(sDate) {
	var nIdx = GetDay(sDate);
	switch (nIdx) {
		case 0 : return "��"; break;
		case 1 : return "��"; break;
		case 2 : return "ȭ"; break;
		case 3 : return "��"; break;
		case 4 : return "��"; break;
		case 5 : return "��"; break;
		case 6 : return "��"; break;
	}
}

////////////////////////////////////////////////////////////////////////////////////////////end-ifis
























function gfn_AddResizeForm(obj, bOrgIsMin)
{
	var nFormWidth = obj.width;
	var nFormHeight = obj.height;

	var nFormLeft = 0;

	var nFormTop = 0;
	var oForm = obj;

	var nIdx = -1;


 
	oForm.OnSize = "gfn_FormResizeEvent";

	// if (toLower(oForm.getType()) == "tabpage" && oForm.getform().PreLoad == true) {
	// } else {
		// oForm.Visible = false;
	// }



	if(toLower(oForm.getType()) != "form") {

		nFormLeft = oForm.left;

		nFormTop = oForm.top;

	}



	oForm.AddVariable("gOnLoad", false, "Local");


	oForm.AddVariable("lv_ArrComp", array());

	oForm.AddVariable("lv_ArrOrgH", array());

	oForm.AddVariable("lv_ArrOrgW", array());

	oForm.AddVariable("lv_ArrOrgX", array());
	oForm.AddVariable("lv_ArrOrgY", array());
	oForm.AddVariable("lv_ArrOrgLst", array());

	oForm.AddVariable("lv_ArrOrgCnts", array());
	oForm.AddVariable("lv_OrgH", 0);
	oForm.AddVariable("lv_OrgW", 0);
	oForm.AddVariable("lb_OrgIsMin", true);
	oForm.AddVariable("lb_ReSetSize", false);



	//Trace("mdi status: "+oForm.MDIStatus);
	if (obj.GetType() == "Form") {

		oForm.lv_OrgW = gv_MDIWidth;

		oForm.lv_OrgH = gv_MDIHeight;

	} else {

		oForm.lv_OrgW = nFormWidth;

		oForm.lv_OrgH = nFormHeight;

	}





	if(bOrgIsMin != null) oForm.lb_OrgIsMin = bOrgIsMin;

	if(oForm.lb_OrgIsMin == false) gv_ScrollMargin = 0;
	oForm.lv_ArrComp = gfnGetComponents(oForm, "", oForm.lv_ArrComp);


	for(var i = 0; i < oForm.lv_ArrComp.length; i++) {
		var oComp = oForm.object(oForm.lv_ArrComp[i]);
		nIdx++;
		if(isValidObject(oComp) == false) continue;
		var sComptype = toLower(oComp.getType());


		var nLeft = 0;

		var nTop = 0;



		if(sComptype != "form") {

			nLeft = oComp.Left;

			nTop = oComp.top;

		}


		oForm.lv_ArrOrgH[nIdx] = oComp.Height;
		oForm.lv_ArrOrgW[nIdx] = oComp.Width;
		oForm.lv_ArrOrgX[nIdx] = nLeft;
		oForm.lv_ArrOrgY[nIdx] = nTop;

		if(sComptype == "grid") {
			oForm.lv_ArrOrgCnts[nIdx] = gfn_GetRateContents(oComp, oComp.Contents, 1, -1);
			oForm.lv_ArrOrgLst[nIdx] = oComp.HeadHeight + ":" + oComp.RowHeight + ":";
		} else if(sCompType == "radio") {
			oForm.lv_ArrOrg3[nIdx] = oComp.Contents;
		} else if(sCompType == "jedit")
		{ 
		    oComp.Readonly = true;
 		}
	}

	oForm.gOnLoad=true;

	if (toUpper(obj.GetType())=="FORM") {
		if ((obj.width==785 && obj.height==587) || (obj.width==785 && obj.height==588)) {
		} else {
			gfn_FormResizeEvent(oForm, nFormWidth, nFormHeight, "");
		}
	}
 
}


function gfn_FormResizeEvent(obj,nCx,nCy,sState)

{

	if((nCx != null && nCx <= 4) || (nCy != null && nCy <= 4)) return;

	if(sState == "Minimize") return;

	if(obj.gOnLoad != true) return;



	var oForm = obj;
    oForm.redraw=false;
	var nWidth;
	var nHeight;
	var nLeft;
	var nTop;
	var nScrollB = 0;
	var nScrollR = 0;
	var nRateX = 1;
	var nRateY = 1;
	var bOrgIsMin = oForm.lb_OrgIsMin;

	var nScrollMargin = gv_ScrollMargin;
 
	if(nCx == null) nCx = oForm.Width;
	if(nCy == null) nCy = oForm.Height;

	if(oForm.lb_ReSetSize) {
		bOrgIsMin = false;
		nScrollMargin = 0;
	}

	if(nCx < oForm.lv_OrgW
	|| ( (nCy < oForm.lv_OrgH) && (nCx - gv_ScrollMargin < oForm.lv_OrgW) ) ) {
		nScrollB = nScrollMargin;
	}

	if(nCy - nScrollB < oForm.lv_OrgH) nScrollR = nScrollMargin;

	nRateX = (nCx - nScrollR) / oForm.lv_OrgW;
	nRateY = (nCy - nScrollB) / oForm.lv_OrgH;


	if(bOrgIsMin && nCx < oForm.lv_OrgW) nRateX = 1;
	if(bOrgIsMin && nCy < oForm.lv_OrgH) nRateY = 1;

	if(oForm.lb_ReSetSize) {
		//Trace("@@ SetResize::" + oForm.id + "::" + nCx + "::" +  nCy + "\n" + oForm.lv_ArrComp);
		oForm.lv_OrgW = nCx;
		oForm.lv_OrgH = nCy;
	}
	oForm.SetScroll(0,0);
	//trace("############### rate ::" + nRateX + ":" + nRateY + ":" + nRateP);
	for(var i = 0; i < oForm.lv_ArrComp.length; i++) {
		var oComp = oForm.object(oForm.lv_ArrComp[i]);
		if(isValidObject(oComp) == false) continue;
		var sComptype = toLower(oComp.getType());
		if(sComptype != "shape" && sCompType != "axmsie") {
			if(sCompType == "grid") {
				var arrLst = split(oForm.lv_ArrOrgLst[i], ":");
 
			}
		}

		nLeft = oForm.lv_ArrOrgX[i] * nRateX;
		nTop = oForm.lv_ArrOrgY[i] * nRateY;


		if(sCompType == "button" && oComp.ImageID != "") {

			nWidth = oForm.lv_ArrOrgW[i];

			nHeight = oForm.lv_ArrOrgH[i];

		} else {

			nWidth = oForm.lv_ArrOrgW[i] * nRateX;

			if(nWidth < 0) nWidth = 0;



			nHeight = oForm.lv_ArrOrgH[i] * nRateY;

			if(nHeight < 0) nHeight = 0;

		}

		if(toUpper(sComptype) != "TABPAGE") oComp.MoveWindow(nLeft,nTop,nWidth,nHeight);

		if(oForm.lb_ReSetSize) {
			oForm.lv_ArrOrgX[i] = nLeft;
			oForm.lv_ArrOrgY[i] = nTop;
			oForm.lv_ArrOrgW[i] = nWidth;
			oForm.lv_ArrOrgH[i] = nHeight;
		}
	}
	oForm.redraw=true;
	oForm.ResizeScroll();

	for(var i = 0; i < oForm.lv_ArrComp.length; i++) {
		var oComp = oForm.object(oForm.lv_ArrComp[i]);
		if(isValidObject(oComp) == false) continue;
		var sComptype = toLower(oComp.getType());
		if(sComptype != "shape" && sCompType != "axmsie") {
			if(sCompType == "grid") {
				var arrLst = split(oForm.lv_ArrOrgLst[i], ":");
 
			}
		}

		nLeft = oForm.lv_ArrOrgX[i] * nRateX;
		nTop = oForm.lv_ArrOrgY[i] * nRateY;


		if(sCompType == "button" && oComp.ImageID != "") {

			nWidth = oForm.lv_ArrOrgW[i];

			nHeight = oForm.lv_ArrOrgH[i];

		} else {

			nWidth = oForm.lv_ArrOrgW[i] * nRateX;

			if(nWidth < 0) nWidth = 0;



			nHeight = oForm.lv_ArrOrgH[i] * nRateY;

			if(nHeight < 0) nHeight = 0;

		}



		if(toUpper(sComptype) != "TABPAGE"){
		    oComp.left=nLeft;
		    oComp.top=nTop;
		    oComp.width=nWidth;
		    oComp.height=nHeight;
		    //trace(oComp.id + " : " + oComp.width + " : " + oComp.right + " : " + oComp.height);
		}
	}
	oForm.ResizeScroll(); 
}


function gfnGetComponents(oForm, sCompPath, arrComp)
{
	for( var i= 0;  i < oForm.Components.Count(); i++)
	{
		if(i%10==0) idle();	// 2010.9.3

		var objType = toLower(oForm.Components[i].getType());
		if(objType == "dataset" || objType == "popupdiv" || objType == "file" || objType == "filedialog") continue;
		arrComp[arrComp.length()] = sCompPath + oForm.Components[i].id;

		if(oForm.Components[i].IsComposite()
		&& trim(oForm.Components[i].Contents) != "<Contents></Contents>")
		{
			arrComp = gfnGetComponents(oForm.Components[i], sCompPath + oForm.Components[i].id + ".", arrComp);
		}
	}

	return arrComp;
}


function gfn_ChangeContents(oForm, sCompPath) {
	for(var i = 0; i < oForm.lv_ArrComp.length; i++) {
		if(toLower(sCompPath) == toLower(oForm.lv_ArrComp[i])) {
			var oComp = oForm.object(sCompPath);
			oForm.lv_ArrOrgCnts[i] = gfn_GetRateContents(oComp, oComp.Contents, 1, -1);
		}
	}
}

function gfn_GetRateContents(oComp, sOrgText, nRateX) {
	if(sOrgText == "") return "";

	var _PROP = " width=";
	var sRtnText = "";
	var nPos = pos(toLower(sOrgText), _PROP);
	var nEndPos = 0;
	var sQuote = "";

	sRtnText = "";
	while(nPos > 0 ) {	//&& (nRateP != -1 || i != 0)) {
		nPos += length(_PROP) + 1;
		sRtnText += mid(sOrgText, nEndPos, nPos - nEndPos);

		sQuote = mid(sOrgText, nPos - 1, 1);
		nEndPos = pos(sOrgText, sQuote, nPos + 1);

		sRtnText += (toNumber(mid(sOrgText, nPos, nEndPos - nPos)) * nRateX);
		nPos = pos(toLower(sOrgText), _PROP, nPos + 1);
	}
	sRtnText += mid(sOrgText, nEndPos);
	return sRtnText;
}

// 2���� �迭�� ���� �� Ȯ��
function fnc_Array2Value(arr2Param,strParam)
{
	var strResult = "";
	for(var i=0;i<arr2Param.length;i++)
	{	
		for(var j=0;j<arr2Param[i].length;j++)
		{
			if(arr2Param[i][0]==strParam)
			{
				strResult = arr2Param[i][1];
			}
		}
	}
	return strResult;
}



/*-------------------------------------------------------------------------------------------------+
>>  ����� width ���� ��
+-------------------------------------------------------------------------------------------------*/
function fnc_VisibleColumn(grdHead,dsHeadYoil)
{	
	// ���� ����Ÿ�� �ʱ�ȭ
	for(var i=0;i<dsHeadYoil.rowcount;i++)
	{
		dsHeadYoil.SetColumn(i,"CD_VISIBLE","0");
	}
	
	var sCD = "";
	for(var i=0;i<dsHeadYoil.rowcount;i++)
	{
		for(var j=0;j<dsRaceYoil.rowcount;j++)
		{
			if(dsRaceYoil.GetColumn(j,"RACE_YOIL")==dsHeadYoil.GetColumn(i,"CD_NM"))
			{	
				if(sCD=="")
				{					
					sCD = dsHeadYoil.GetColumn(i,"CD");
				}
				else
				{					
					sCD += ","+dsHeadYoil.GetColumn(i,"CD");
				}
				
			}
		}
	}
	
	
	var arrCd= split(sCD,",");
	
	// ����� �� �ִ� �÷��� width ����  100 ���� ����
	for(var i=0;i<dsHeadYoil.rowcount;i++)
	{
		
		for(var j=0;j<arrCd.length;j++)
		{			
			if(dsHeadYoil.GetColumn(i,"CD")==arrCd[j])
			{
				dsHeadYoil.SetColumn(i,"CD_VISIBLE","1");
			}
		}
	}
	
	
	for(var i=0;i<dsHeadYoil.rowcount;i++)
	{
		if(dsHeadYoil.GetColumn(i,"CD_VISIBLE")=='1')
		{		
			grdHead.SetColProp(parseInt(dsHeadYoil.GetColumn(i,"CD")),"width",100);
		}
		else
		{
			grdHead.SetColProp(parseInt(dsHeadYoil.GetColumn(i,"CD")),"width",0);
		}
	}
	
}



/*-------------------------------------------------------------------------------------------------+
*  ��   ��  �� : fnc_AprvBtn
*  ��       �� : �ڵ尪�� ���� ��ư ��ġ�� �׸��� ���̸� ��ȯ��Ų��(�򰡿��� ���) 
*  argument    : 1.sAprvEmpNo   - ������
*  argument    : 2.sReqEmpNo    - ��û��
*  argument    : 3.sLoginUser   - Login�� ��� ID
*  argument    : 4.sAprvCd      - ���°�
*  argument    : 5.sRMenu       - ������ ������Ʈ Bottom
*  argument    : 6.sLMenu       - ���� ������Ʈ Bottom
+-------------------------------------------------------------------------------------------------*/

function fnc_AprvBtn(sAprvEmpNo, sReqEmpNo, sLoginUser, sAprvCd, sRMenu, sLMenu, BUTTON_TOP) 
{
	// ��ư�� �׸��� ��ġ(��)
	var R_BUTTON_TOP  = "552";
	var R_BUTTON_LEFT = "704";
	var L_BUTTON_TOP  = "552";
	var L_BUTTON_LEFT = "616";
	var GRID_Y_BOTTOM = "544";	//��ư�� ������ �׸����� ����
	var GRID_N_BOTTOM = "566";	//��ư�� ������ �׸����� ����
	
	if( sAprvEmpNo == sLoginUser ) {
		//������ ���� �� ó��
		switch( sAprvCd ) {
			case "001" :
				sRMenu.Bottom = GRID_N_BOTTOM;
				sLMenu.Bottom = GRID_N_BOTTOM;
				break;
			case "002" :
				aprvBackBtn.Top  = R_BUTTON_TOP;
				aprvBackBtn.Left = R_BUTTON_LEFT;
				aprvOkBtn  .Top  = L_BUTTON_TOP;
				aprvOkBtn  .Left = L_BUTTON_LEFT;
				sRMenu.Bottom = GRID_Y_BOTTOM;
				sLMenu.Bottom = GRID_Y_BOTTOM;
									 
				aprvOkBtn  .Visible = true;
				aprvBackBtn.Visible = true;
				break;
			case "003" :				
				aprvCanCelBtn.Top  = R_BUTTON_TOP;
				aprvCanCelBtn.Left = R_BUTTON_LEFT;
				sRMenu.Bottom = GRID_Y_BOTTOM;
				sLMenu.Bottom = GRID_Y_BOTTOM;
				
				aprvCanCelBtn.Visible = true;
				break;
		}
	} else if(  sReqEmpNo == sLoginUser ) {
		//��û�� ���� �� ó��
		switch( sAprvCd ) {
			case "001" :
				aprvReqBtn.Top  = R_BUTTON_TOP;
				aprvReqBtn.Left = R_BUTTON_LEFT;
				sRMenu.Bottom = GRID_Y_BOTTOM;
				sLMenu.Bottom = GRID_Y_BOTTOM;
				
				aprvReqBtn.Visible = true;
				break;
			case "002" :
				aprvCallBtn.Top  = R_BUTTON_TOP;
				aprvCallBtn.Left = R_BUTTON_LEFT;
				sRMenu.Bottom = GRID_Y_BOTTOM;
				sLMenu.Bottom = GRID_Y_BOTTOM;
				
				aprvCallBtn.Visible = true;
				break;
			case "003" :
				sRMenu.Bottom = GRID_N_BOTTOM;
				sLMenu.Bottom = GRID_N_BOTTOM;
				break;
		}
	} else {
		sRMenu.Bottom = GRID_N_BOTTOM;
		sLMenu.Bottom = GRID_N_BOTTOM;
	}
}

/*-------------------------------------------------------------------------------------------------+
*  ��   ��  �� : fnc_AprvBtnLoc
*  ��       �� : �ڵ尪�� ���� ��ư ��ġ�� �׸��� ���̸� ��ȯ��Ų��(�򰡿��� ���) 
*  argument    : 1.sAprvEmpNo     - ������
*  argument    : 2.sReqEmpNo      - ��û��
*  argument    : 3.sLoginUser     - Login�� ��� ID
*  argument    : 4.sAprvCd        - ���°�
*  argument    : 5.BUTTON_TOP     - ��ư TOP��ǥ��
*  argument    : 6.R_BUTTON_LEFT  - ������ ��ư�� ���� ��ǥ��
+-------------------------------------------------------------------------------------------------*/

function fnc_AprvBtnLoc(sAprvEmpNo, sReqEmpNo, sLoginUser, sAprvCd, BUTTON_TOP, R_BUTTON_LEFT) 
{
	// ��ư�� �׸��� ��ġ(��)
	var L_BUTTON_LEFT = toInteger(R_BUTTON_LEFT) - 80 - 2;	//80 :��ưWidth  4:Space

	if( sAprvEmpNo == sLoginUser ) {
		//������ ���� �� ó��
		switch( sAprvCd ) {
			case "001" :
				break;
			case "002" :
				aprvBackBtn.Top  = BUTTON_TOP;
				aprvBackBtn.Left = R_BUTTON_LEFT;
				aprvOkBtn  .Top  = BUTTON_TOP;
				aprvOkBtn  .Left = L_BUTTON_LEFT;
									 
				aprvOkBtn  .Visible = true;
				aprvBackBtn.Visible = true;
				break;
			case "003" :				
				aprvCanCelBtn.Top  = BUTTON_TOP;
				aprvCanCelBtn.Left = R_BUTTON_LEFT;
				
				aprvCanCelBtn.Visible = true;
				break;
		}
	} else if(  sReqEmpNo == sLoginUser ) {
		//��û�� ���� �� ó��
		switch( sAprvCd ) {
			case "001" :
				aprvReqBtn.Top  = BUTTON_TOP;
				aprvReqBtn.Left = R_BUTTON_LEFT;
				
				aprvReqBtn.Visible = true;
				break;
			case "002" :
				aprvCallBtn.Top  = BUTTON_TOP;
				aprvCallBtn.Left = R_BUTTON_LEFT;
				
				aprvCallBtn.Visible = true;
				break;
			case "003" :
				break;
		}
	}
}

/*-------------------------------------------------------------------------------------------------+
*  ��   ��  �� : fnc_BtnVisibleInit
*  ��       �� : ��ư ���߱�
*  argument    : 1.ARRBTN   -  ��ư �迭
+-------------------------------------------------------------------------------------------------*/
function fnc_BtnVisibleInit(ARRBTN) {
	for(var i = 0 ; i < ARRBTN.length(); i++ ) {
		ARRBTN[i].Visible = false;
	}
}

/**
 * ����Ʈ �̸����� �˾� ����(GateWay ��� - SQL�� ����Ʈ�� ����)
 *
 * param rptFile   - ����Ʈ ����(ID)
 * param dsParamId - �Ķ���� ���� Dataset ID
 * param fullSize  - �˾� ȣ��� Screen Size�� ���� ���·� ȣ���Ұ����� ����
 * return void
 */
function fnc_ReportPreView(rptFile, paramDsId, sRptDrct, fullSize)
{
    var nWidth = 1024;
    var nHeight = 715;
    var nPosX = 0;
    var nPosY = 0;
    var bFullSize;
    var sFileName="";

    var fSQLCallFlag;

    if(rptFile == null || fnc_IsNull(rptFile)) {
        fnc_Message(SNIS_RBM_0007, "rptFile");
        return;
    }

    if (fullSize == null) {
        bFullSize = false;
    } else {
        bFullSize = true;
    }
    
    
    if (fullSize) {
        nWidth  = getDeviceInfo("CXScreen") - 8;
        nHeight = getDeviceInfo("CYScreen") - 55;
        nPosX = (getDeviceInfo("CXScreen")-nWidth-6)/2;
        nPosY = (getDeviceInfo("CYScreen")-nHeight-56)/2;
    }

    var sArg = "sRptNm=" + quote(rptFile);

    if(fnc_IsNotNull(paramDsId)) {
        sArg += " paramDsId=" + quote(paramDsId);
    }
    
    sArg += " sSubUrl=" + quote(mid(RptFile,0,3));	
    sArg += " fSQLCallFlag=true";    
    sArg += " AutoPrint='N'";
        
    if (sRptDrct == "N") {
		sFileName = "common::comHrzRptViewer.xml";
	} else {
		sFileName = "common::comVrtRptViewer.xml";
		nWidth  = 760;  		
	}
	
	open("common::comVrtRptViewer.xml", sArg, nWidth, nHeight, "AutoSize=false Resize=true", nPosX, nPosY);
	
	
    return;
}

/*
 * ���ڷ� ���� ���ڿ��� Null ���θ� üũ�Ͽ� �� ����� �����ش�.
 *
 * param sValue - ���ڿ� ��
 *
 * return true/false - Not Null / Null
 */
function fnc_IsNotNull(sValue)
{
	var bRtn = false;

	if(length(trim(sValue))>0) bRtn = true;
	else bRtn = false;

	return bRtn;
}


function fnc_OpenHelp()
{
    //trace("fnc_OpenHelp()");
	var bIsOpen = false;
	var i;
	var nActHandle;

	for (i=0; i < global.windows.count; i++) {
		if (global.windows[i].id == "comHelpPopup") {
			bIsOpen = true;
			nActHandle = i;
			break;
		}
	}

	if (!bIsOpen) {
		Open("common::comHelpPopup.xml", "", 1020, 700, "Title=true CloseFlag=true","50","10");
	} else {
		//global.windows[nActHandle].fcGetSplcUserYN();
		global.windows[nActHandle].setHelpId(GBL_MENUID);
		global.windows[nActHandle].SetFocus();
	}
}

function fnc_putComma(str)
{
//Trace(lengthb(str));
	var idec = indexof(str, '.');
	var strDec = "";
	if(idec >= 0) {
		strDec = substr(str, idec, length(str));
		str    = substr(str, 0, idec);
	} 
	
//Trace(length(str));
	if( str == null || str == "" ) return "";
	var i, ret_str, c_pos;
	var len=0;
	len = length(ToString(str));
	ret_str = "";
	c_pos = ((len%3)+2)%3;
	
	for( i = 0 ; i < len ; i++ ) {
		ret_str += CharAt(str, i);
		if( i%3 == c_pos ) {
			if( i != (len-1) ) 
				ret_str += ",";
		}
	}
	
	return ret_str + strDec;
} 



// ������OS���� ��ȸ
function fnc_GetOSVersionName(OSVer) {
	var sOSVersion = "";
	if  (OSVer == "Windows NT 4.0") {
		sOSVersion = "Windows NT";
	} else if (OSVer == "Windows NT 5.0") {
		sOSVersion = "Windows 2000";
	} else if (OSVer == "Windows NT 5.1") {
		sOSVersion = "Windows XP";
	} else if (OSVer == "Windows NT 5.2") {
		sOSVersion = "Windows 2003";
	} else if (OSVer == "Windows NT 6.0") {
		sOSVersion = "Windows Vista";
	} else if (OSVer == "Windows NT 6.1") {
		sOSVersion = "Windows 7";
	} else if (OSVer == "Windows NT 6.2") {
		sOSVersion = "Windows 8";
	} else if (OSVer == "Windows NT 6.3") {
		sOSVersion = "Windows 8.1";
	} else {
		sOSVersion = OSVer;
	} 
	return sOSVersion;
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
	
	//Destroy("dsPersonalMn");
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
	
	//Destroy("dsPersonalIdChk");
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
		}
		
		Destroy("dsPersonalMn");
		return;
		
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
		
		Destroy("dsPersonalIdChk");
		return;
		
	}
}
