/*================================================================================================/
 * PROGRAM-ID                           snis_common_script 
 * DESCRIPTION   :  경주사업관리시스템에서 사용하는 공통 script                                        
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
>>  Form Load 時 처리 할 업무 처리 Function
>>  (화면 Title, 화면 Information, Loaction 및 권한 처리)
>>>>  param1)  obj           : 화면 Object
>>>>  param2)  sFORMCAPTION  : 화면 Title
>>>>  param3)  sFORMLOCATION : 화면 Location
>>>>  param4)  sBUTTONLIST   : 공통버튼 Enable, Disable 여부
>>>>  param5)  sUSERAUTH     : 사용권한
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
	
	sUSERAUTH   = 'T'+sIsView+""+sInpt+'TTTT';     //초기화,조회,저장,삭제,엑셀,인쇄,닫기

	//변수 선언 및 Program 정보 Display
	var sNewButtonList  = "";                                               //사용자 버튼 권한
	obj.Window.Title    = sFORMCAPTION;
	 
	if( !IsExistVariable("GBL_WEB_ID","Global") ) {
		//화면 Title Display
		obj.stFormTitle     = obj.stMenuPath.value+"("+obj.stScrnId.value+")";  //화면 정보 Display(화면ID추가)
   }
//	obj.stFormLocation  = sFORMLOCATION;                                    //Loaction 정보 Display
  
	//화면의 기능 유무 + 사용자 권한에 대한 버튼 처리 
	for(var i=0; i < length(sBUTTONLIST); i++) {

		//권한과 기능 유무가 동일하면 해당 권한으로 처리
		if (substr(sBUTTONLIST, i, 1)==substr(sUSERAUTH, i, 1)) {
			
			sNewButtonList += substr(sUSERAUTH, i, 1);
			
		} else {
			//기능이 없는 버튼이면 권한이 있어도 False
			if (substr(sBUTTONLIST, i, 1)=='F') {
				sNewButtonList += 'F';
			//기능은 있다면 해당 기능은 사용자 권한에 의해 설정
			} else {
				sNewButtonList += decode(substr(sUSERAUTH, i, 1),'','T',substr(sUSERAUTH, i, 1));
			}
		}
	}
	//위에서 산출된 기능에 대해 Enable 처리
	if (substr(sNewButtonList,0,1)=="F") obj.divBtnLst.btnCracel.Visible  = false;  //초기화
	if (substr(sNewButtonList,1,1)=="F") obj.divBtnLst.btnSearch.Visible  = false;  //조회
	if (substr(sNewButtonList,2,1)=="F") obj.divBtnLst.btnSave.Visible    = false;  //저장
	if (substr(sNewButtonList,3,1)=="F") obj.divBtnLst.btnDelete.Visible  = false;  //삭제
	if (substr(sNewButtonList,4,1)=="F") obj.divBtnLst.btnToExcel.Visible = false;  //엑셀
	if (substr(sNewButtonList,5,1)=="F") obj.divBtnLst.btnPrint.Visible   = false;  //인쇄
	if (substr(sNewButtonList,6,1)=="F") obj.divBtnLst.btnEnd.Visible     = false;  //종료
	
	//공통버튼 위치 조정
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
>>  Message에 '@'인자 삽입
>>>>  1) sSendMessage : 메세지
>>>>  2) sArg1        : 대체할 메세지
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
>>  별도의 메시지 창을 Open하여 메시지 내용을 보여주기 위한 Function
>>>>  param1)  sMessageID    : 호출할 Message ID
>>>>  param2)  sMessageType  : 호출한 Form의 Type('확인', 'Yes : No')
>>>>  param3)  sArg1         : '^'표시에 입력할 인자1
>>>>  param4)  sArg2         : '^'표시에 입력할 인자2
>>>>  param5)  sArg3         : '^'표시에 입력할 인자3
>>>>  param6)  sArg4         : '^'표시에 입력할 인자4
>>>>  param7)  sArg5         : '^'표시에 입력할 인자5
+-------------------------------------------------------------------------------------------------*/
function fnc_Message(sMessageID, sMessageType, sArg1, sArg2, sArg3, sArg4, sArg5){

	var deviceInfo = split(GetDeviceInfo(), ";");
	var screen = split(deviceInfo[2], "x");
	var iXPos = (parseInt(screen[0]) - parseInt(464))/2;
	var iYPos = (parseInt(screen[1]) - parseInt(158))/2;
	
	/* //web에 임베디드할경우 오작동을 하므로 위 소스로 대체함.
	
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
>>  Grid에서 헤더를 클릭하면 해당 셀들을 정렬시키는 Function(ASC,DESC 토글)
>>>>  param1)  GridObj       : 그리드 콤포넌트의 이름 (예 : grdMaster)
>>>>  param2) dsObj          : 데이터셋 콤포넌트의 이름 (예 : dsGrid01)
>>>>  param3)  nCell         : 해당셀번호 (HeadClick 이벤트에서 받은 nCell을 그대로 지정)
+-------------------------------------------------------------------------------------------------*/
var CONST_ASC_MARK  = "▲";   // Ascending  Sort Mark
var CONST_DESC_MARK = "▼";   // Descending Sort MarkFxcl

function fnc_GridSort(objGrid, dsObj, nCell){

    var nSortColumn    ;
    var sColumnID      ;
    var nSubColumnCount;
    var sColumnNM      ;
    var objDs          ;
  
    nSortColumn = objGrid.GetCellProp("head", nCell, "col");
  
    /**********************************************************************
    *  Sub컬럼을 가진 경우, 서브컬럼순서로 정렬한다.
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
    
      // 오름차순으로 정렬
    if ( right(sColumnNM, 1) == CONST_ASC_MARK ) {
        objDs.Sort(sColumnID, false);
        sColumnNM = replace(sColumnNM, CONST_ASC_MARK, "");
        objGrid.SetCellProp("head", nCell, "text", sColumnNM + CONST_DESC_MARK);
    } else {
    // 내림차순 정렬
        objDs.Sort(sColumnID, true);
        sColumnNM = replace(sColumnNM, CONST_DESC_MARK, "");
        objGrid.SetCellProp("head", nCell, "text", sColumnNM + CONST_ASC_MARK);
    }
  
    /**********************************************************************
    *  다른 컬럼의 정렬표시 삭제
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
>>  Grid에서 헤더를 클릭하면 해당 셀들을 정렬시키는 Function(ASC,DESC 토글)
>>>>  param1)  GridObj       : 그리드 콤포넌트의 이름 (예 : grdMaster)
>>>>  param2) dsObj          : 데이터셋 콤포넌트의 이름 (예 : dsGrid01)
>>>>  param3)  nCell         : 해당셀번호 (HeadClick 이벤트에서 받은 nCell을 그대로 지정)
+-------------------------------------------------------------------------------------------------*/
/*
var CONST_ASC_MARK  = "▲";   // Ascending  Sort Mark
var CONST_DESC_MARK = "▼";   // Descending Sort MarkFxcl

function fnc_GridSort(Gridobj, dsObj, nCell){
	var nheadText,sflag;
	fnc_SetWaitCursor(true);

//----------------------------------------------------------------------------------------------
//  해당하는 셀의 타이틀의 소트마크를 추가 또는 변경한다.
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
//  해당하는 셀 이외의 해더 타이틀의 소트마크를 제거한다.
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
>> PopupDiv Calendar 열기 Function
>>>>  1)  param objGrid - 그리드 객체
>>>>  2)  param nRow    - 그리드 row index
>>>>  3)  param nCell   - 그리드 cell index
>>>>  4)  param chkFlg  - return Flag : true/false, 값 넘김 / Dataset에 값 할당
+-------------------------------------------------------------------------------------------------*/
var L_GrdBindDs       = ""; // Grid.BindDataset 객체
var L_GrdBindDsRow;         // Grid.BindDataset 객체 행
var L_GrdBindDsColId;       // Grid.BindDataset 객체 열
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
>>  엑셀파일로 내보내기 위한 Function
>>  그리드를 동적으로 생성 직 후 엑셀 내보내기를 수행하는 경우 사용!
>>  폼의 OnTimer 이벤트에 MiForm_OnTimer 함수가 적용되어 있어야 정상 작동함
>>>>  param1)  sGrid        : 대상 Grid ID
>>>>  param2)  nStartCell   : 시작 cell index number
>>>>  param3)  bExcYn       : 연결프로그램 실행' flag [true/false]
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
* 용도     :    그리드의 내용을 엑셀파일로 저장한다.(좀 더 다양한 Excel다운로드 기능이 요구될경우)
* Syntax   :    gfnExport(strSheet,strGrid)
* 파라미터 :
				strSheet 	- Excel Sheet 명
				strGrid 	- 그리드 Object
* 예제     :	gfnExport("test,test1234","gdList,Grid0");
                gdList그리드 내용을 "test"Sheet에 저장한다.
                Grid0그리드 내용을 "test1234"Sheet에 저장한다.
                Excel파일명은 파일대화상자에서 사용자가 지정한다.
* 리턴값   :    없음
* 참고사항 :    Export Object를 이용하여 사용자가 스크립트 상에서 Export와 관련된 기능을 제어합니다.
                자세한 내용은 도움말참조(ExportObject 항목)
* 기타     :    개발자 배포용
 
* ------------------------------------------------------------------------------------------------------------*/
function fcd_topGfnExport(strSheet,strGrid,strFileNm,objForm) {
	var ExportObject1;
	var sXML;

	if (strSheet == null || strSheet == "") {
		alert("Excel Sheet명이 없습니다!");
		return;
	}
	if (strGrid == null || strGrid == "") {
		alert("그리드가 지정되지 않았습니다!");
		return;
	}
	var vSheet 	= split(strSheet,",","webstyle");	//sheet명칭
	var vGrid 	= split(strGrid,",","webstyle");	//그리드 object

	ExportObject1 = objForm.CreateExportObject();
	ExportObject1.ExportType		= "Excel";

	objForm.Destroy("fldExl");
	//파일저장 대화상자
	objForm.Create("FileDialog","fldExl");
	//파일선택 대화상자를 연다.
	objForm.fldExl.Type = "SAVE";
	//Excel파일만 보이도록 필터한다.
	objForm.fldExl.Filter = "Excel(.xls)|*.xls|";
	objForm.fldExl.FileName = strFileNm;
	//취소를 선택하면 대화상자를 닫는다.
	if (!objForm.fldExl.open()){
		return;
	}

	ExportObject1.ExportFileName	= objForm.fldExl.FilePath + "\\" + objForm.fldExl.FileName;
	ExportObject1.ActiveSheetName   = vSheet[0];
	// ExportObject1.ExportSinglePivotColHead = false;

	//파일이 존재하지 않으면 생성한다.
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
		//지정된 엑셀파일에 Sheet를 추가한다.
		//strRange: (sheetname!Range 형태)
		//GridComp: 그리드 object
		//bExportHead: headcolumn 출력여부
		//bExportValue: value만 출력할지여부(false: 그리드 Style까지 출력)

		ExportObject1.AddExportGrid(vSheet[i] + "!" + "A1", objTmpGrd, true, false);
	}

	ExportObject1.Export(true,false); // 첫번째 progress, 두번째 excel
			ExportObject1.Save();  // <---------  이 부분 추가해주세요  파일을 바로 저장할것인지 설정.
			ExportObject1.CloseWorkbook(); // <---------  이 부분 추가해주세요  excel을 Sheet를 닫기.

	ExportObject1.Close();
	ExportObject1 = null;
}


/*-------------------------------------------------------------------------------------------------+
>>  엑셀 다운로드용 Grid Content 만들기!
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
*  함   수  명				: fcd_OpenExcelListExport
*  설       명				: 선택한 파일명을 excel 저장한다.
*  return Type				: Stirng
*  return 내용				: 파일명
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
>>  엑셀파일로 내보내기 위한 Function
>>  그리드 BindDataset 레코드 수 체크 - 0 일 경우  alert return;
>>  그리드 객체 유효성 체크 - 실패 시 alert, return;
>>>>  param1)  sGrid        : 대상 Grid ID
>>>>  param2)  nStartCell   : 시작 cell index number
>>>>  param3)  bExcYn       : 연결프로그램 실행' flag [true/false]
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
>>  멀티 그리드 엑셀파일로 내보내기 위한 Function
>>  그리드 BindDataset 레코드 수 체크 - 0 일 경우  alert return;
>>  그리드 객체 유효성 체크 - 실패 시 alert, return;
>>>>  param1)  sGrid        : 대상 Grid ID
>>>>  param2)  nStartCell   : 시작 cell index number
>>>>  param3)  bExcYn       : 연결프로그램 실행' flag [true/false]
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
>>  사용자가 선택한 파일을 서버로 업로드하는 Function
>>>>  param1)  httpfileObj  : CyHttpFile 컴포넌트의 ID
>>>>  param2)  file_url     : 사용자가 선택한 파일경로 및 파일명
>>>>                          (해당 DataSet ID.getColumn(i,"DataSet의 fileUrl ID"))
>>>>  param3)  str_param    : 사용자가 선택한 파일명
>>>>                          (해당 DataSet ID.getColumn(i,"DataSet의 fileName ID"))
>>>>  return rtn_arr
>>>>  파일업로드가 성공했을 경우 rtn_arr = [SUCC, 물리적 파일명, 물리적 파일크기] 리턴
>>>>  파일업로드가 실패했을 경우 rtn_arr = [FAIL, 에러메시지] 리턴      
+-------------------------------------------------------------------------------------------------*/
var upload_packetSize = 1024 * 1024 * 10;   // 1M : 1048576;
var download_packetSize = 1024 * 1024 * 10;

function fnc_FileWrite(httpfileObj, sLocalFilePath, sLocalFileName, sWork)
{
    if ( sWork == null ) sWork = "";

	var rtn_arr = Array(3);	// 파일 업로드에 대한 성공 및 실패 여부와 업로드 결과를 저장하는 리턴변수
	rtn_arr[0] = "FAIL";	// 리턴변수를 파일 업로드 실패로 초기화
	
	if (length(sLocalFilePath) < 7) {	// 파일 경로 길이가 7보다 작은 경우 리턴변수 결과에 에러메시지 저장
		rtn_arr[1] = "Not Found Seleced File!";
		return rtn_arr;
	}
	
	if (length(sLocalFileName) < 1){	// 파일 경로 길이가 7보다 작은 경우 리턴변수 결과에 에러메시지 저장
		rtn_arr[1] = "Not Found CookieParam!";
		return rtn_arr;
	} 
	var write_size;
	var tot_write_size;
	var file_size;

	remote_url =  GBL_FILE_UPDNLOAD_URL+"?mode=upload&work=" + sWork +" &subDir="+SubStr(GBL_MENUID,0,3);	// 파일 업로드인 경우 mode를 upload로 설정
    //trace("fnc_FileWrite():"+remote_url);
	file_size = httpfileObj.GetFileSizeLocal(sLocalFilePath);
 
	httpfileObj.CookieParam = quote(sLocalFileName);		// 파일명을 쿠기에 넣어줌
	
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

	var rtn_val = httpfileObj.CookieParam;	// 서버로부터 받은 쿠키를 리턴 변수에 저장
	
	var str_sp = split(rtn_val,"&","webstyle");     // 쿠키를 구분자로 분리하여 저장하기 위한 변수
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

	var rtn_arr = Array(3);	// 파일 업로드에 대한 성공 및 실패 여부와 업로드 결과를 저장하는 리턴변수
	rtn_arr[0] = "FAIL";	// 리턴변수를 파일 업로드 실패로 초기화
	
	if (length(sLocalFilePath) < 7) {	// 파일 경로 길이가 7보다 작은 경우 리턴변수 결과에 에러메시지 저장
		rtn_arr[1] = "Not Found Seleced File!";
		return rtn_arr;
	}
	
	if (length(sLocalFileName) < 1){	// 파일 경로 길이가 7보다 작은 경우 리턴변수 결과에 에러메시지 저장
		rtn_arr[1] = "Not Found CookieParam!";
		return rtn_arr;
	} 
	var write_size;
	var tot_write_size;
	var file_size;


	remote_url =  GBL_FILE_UPDNLOAD_URL+"?mode=pcfileupload&sUploadRoot="+sUploadRoot+"&work=" + sWork +"&sExt="+sExt+"&subDir="+SubStr(GBL_MENUID,0,3);	// 파일 업로드인 경우 mode를 upload로 설정
	

    //trace(remote_url);
	file_size = httpfileObj.GetFileSizeLocal(sLocalFilePath);
 
	httpfileObj.CookieParam = quote(sLocalFileName);		// 파일명을 쿠기에 넣어줌
	
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

	var rtn_val = httpfileObj.CookieParam;	// 서버로부터 받은 쿠키를 리턴 변수에 저장
	
	var str_sp = split(rtn_val,"&","webstyle");     // 쿠키를 구분자로 분리하여 저장하기 위한 변수
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
>>  사용자가 선택한 파일을 서버로부터 다운로드하는 Function
>>>>  param1)  httpfileObj  : CyHttpFile 컴포넌트의 ID
>>>>  param2)  file_url     : 사용자가 로컬에 다운받고자하는 파일경로 및 파일명
>>>>                          (해당 DataSet ID.getColumn(i,"DataSet의 fileUrl ID"))
>>>>  param3)  str_param    : 사용자가 다운받고자하는 파일명
>>>>                          (해당 DataSet ID.getColumn(i,"DataSet의 fileName ID"))
>>>>  return rtn_arr
>>>>  파일다운로드가 성공했을 경우 rtn_arr = [SUCC] 리턴
>>>>  파일다운로드가 실패했을 경우 rtn_arr = [FAIL, 에러메시지] 리턴
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
*  함   수  명				: fcd_GetSelectPCFile
*  설       명				: 선택한 파일명을 취한다.
*  return Type				: Stirng
*  return 내용				: 파일명
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
>>  사용자가 선택한 파일을 서버로부터 다운로드하는 Function
>>>>  param1)  httpfileObj  : CyHttpFile 컴포넌트의 ID
>>>>  param2)  file_url     : 사용자가 로컬에 다운받고자하는 파일경로 및 파일명
>>>>                          (해당 DataSet ID.getColumn(i,"DataSet의 fileUrl ID"))
>>>>  param3)  str_param    : 사용자가 다운받고자하는 파일명
>>>>                          (해당 DataSet ID.getColumn(i,"DataSet의 fileName ID"))
>>>>  return rtn_arr
>>>>  파일다운로드가 성공했을 경우 rtn_arr = [SUCC] 리턴
>>>>  파일다운로드가 실패했을 경우 rtn_arr = [FAIL, 에러메시지] 리턴
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
>>  사용자가 선택한 파일을 서버에서 삭제하는 Function
>>>>  param1)  str_param    : 서버에 삭제하려는 물리적인 파일명
+-------------------------------------------------------------------------------------------------*/
function fnc_FileDelete(str_param)
{

	remote_url = GBL_FILE_UPDNLOAD_URL + "?mode=delete&subDir=" + SubStr(GBL_MENUID,0,3)+ "&filename=" + str_param; 
	         
    Transaction("", remote_url, "", "", "fcCallBack", false);
    
}




/******************************************************
 *  전체 Callback 
 ******************************************************/
function fnc_Result(errCode, errMsg) {
	 
	fnc_ProgressClose();  //프로그래스바 닫기 

//Trace("fnc_Result::ServiceMessageCode:"+ServiceMessageCode);    
//Trace("fnc_Result::errCode:"+errCode);    
//Trace("fnc_Result::"+errCode +"|"+errMsg+"|" +ServiceMessage + "|" + ServiceMessageCode);
    //사용자예외처리에 대해서 추가방안
    if ( errCode != 0 ) {
		
		//30분이상 미사용시 로그아웃, 아닐경우 에러 메시지 표시
		if(errMsg.indexOf("### CONTEXT IS NULL ###") != -1){
			fnc_Message(SNIS_COM_1062, "N");
			exit();
		}else
		{
			fnc_Message("SERVERMSG", "N", errMsg); 
		}
		
		//중복로그인인 경우 시스템 종료
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
		fnc_SetStatus(SNIS_CRA_0003, SearchCount); //하단 상태바에 메시지 처리
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
    //사용자예외처리에 대해서 추가방안
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
		fnc_SetStatus(SNIS_CRA_0003, SearchCount); //하단 상태바에 메시지 처리
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
>>  진행중 표시 팝업 오픈하기 위한 Function
+-------------------------------------------------------------------------------------------------*/
function fnc_ProgressPop() {
    fv_nHandle = Open("common::comPopProgress.xml", "", 294, 112, "Title=false");
}



/*-------------------------------------------------------------------------------------------------+
>>  진행중 표시 팝업 Close하기 위한 Function
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
>>  Text Box Focus시 User Data Setting & SelSelect Function
>>>>  param1)  obj           : Focus를 받은 Text Object
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
>>  Grid에서 Multi로 선택된 Row Count를 반환하는 Function
>>>>  param1)  oDataSet      : 해당 DataSet
+-------------------------------------------------------------------------------------------------*/
function fnc_SelectRowCount(oDataSet) {

	var iRowCount = 0;
	
	for ( var i = 0; i < oDataSet.RowCount(); i++ ) {
		if ( oDataSet.GetSelect(i) ) iRowCount++;
	}
	return iRowCount;
}



/*-------------------------------------------------------------------------------------------------+
>>  해당 DataSet의 최소 상태로 Reset 작업하는 Function
>>>>  param1)  obj           : 취소 상태로 만들고자 하는 해당 DataSet
+-------------------------------------------------------------------------------------------------*/
function fnc_DataSetCracel(obj) {
	//만약 해당 DataSet의 변경이 있었다면 Reset
	if (obj.GetUpdate()) obj.Reset();
}



/*-------------------------------------------------------------------------------------------------+
>>  Report를 출력하기 위한 Function
>>>>  param1)  sFileName     : 출력할 레포트 파일명
>>>>  param2)  oDataSet      : 출력을 시키고자 하는 Dataset
>>>>  param3)  oFile         : 
+-------------------------------------------------------------------------------------------------*/
function fnc_ToPrint(sFileName, oDataSet, oFile) {

	//개발 기간은 DataSet의 내용을 Report  Design을 위해 XML 파일로 만든다.
	var fBuffer = oDataSet.SaveXML(oDataSet.id,"false");
    oFile.FileName = sFileName;

    if(oFile.open("w")) {			
		var len = oFile.write(fBuffer);
		oFile.close();
	}
	alert(sFileName + "\n\n경로에 해당 파일명으로 XML 자료를 저장함");
}



/*-------------------------------------------------------------------------------------------------+
>>  선택된 컬럼 삭제 Function
>>>>  param1)  dsObj         : Dataset명
>>>>  param2)  sColumnNm     : 해당 sCheckColumnNm Nm
>>>>  param3)  sValue        : 값
+-------------------------------------------------------------------------------------------------*/
function fnc_DeleteCheck(dsObj, sCheckColumnNm, sValue) {

	//선택된 Row Count
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
>>  컬럼의 해당 값이 있는 컬럼 수 리턴 Function
>>>>  param1)  dsObj         : Dataset명
>>>>  param2)  sColumnNm     : 해당 Column Nm
>>>>  param3)  sValue        : 값
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
*  설       명 : 한개의 Control 값에 대한 Null Check를 하는 Function
*  argument    : 1.controlID   : Check 대상 Control Object
*                2.controlName : Check 대상명
*  return Type : boolean
*  return 내용 : true/false
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
>>  해당 DataSet의 변경 여부가 있는지 체크하는 Function
>>>>  param1)  obj          : 체크하고자 하는 DataSet Object
+-------------------------------------------------------------------------------------------------*/
function fnc_DatasetChangeCheck(obj) {

	//DataSet에 변경이 일어났는지 여부를 반환
	if ( obj = '' ) {
		return false;
	} else {
		return obj.GetUpdate();
	}
}



/*-------------------------------------------------------------------------------------------------+
>> 인자로 받은 문자열의 Null 여부를 체크하여 그 결과를 돌려준다.
>>>>  param1)  sValue        : 문자열 값
>>>>  return : true/false - NULL / NOT NULL
+-------------------------------------------------------------------------------------------------*/
function fnc_IsNull(sValue) {
	if ( sValue == null ) return true; 
	if ( length(trim(sValue)) < 1 ) return true;

	return false;
}


/*-------------------------------------------------------------------------------------------------+
>> 인자로 받은 문자열이 Null 인 경우 넘어온 값으로 그 결과를 돌려준다.
>>>>  param1)  sValue        : 문자열 값
>>>>  param2)  sNullValue        : 문자열 값
>>>>  return :  param1, param2
+-------------------------------------------------------------------------------------------------*/
function fnc_NVL(sValue, sNullValue) {
	if ( sValue == null ) return sNullValue; 
	if ( length(trim(sValue)) < 1 ) return sNullValue;

	return sValue;
}


/************************************************************************
*  함   수  명 : fnc_IsDate
*  설       명 : 날짜형식인지 검색
*  argument    : 1.objDs  : 작업대상 Grid
*  return Type : boolean
*  return 내용 : true/false
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
*  함   수  명 : fnc_IsTime
*  설       명 : 시간형식인지 체크
*  argument    : hh24, hh24mi, hh24miss
*  return Type : boolean
*  return 내용 : true/false
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
*  함   수  명				: fnc_IsColumnDuplicate
*  설       명				: DataSet의  하나의 컬럼 레코드값들의 중복여부를 확인한다.
*  param1) oDataSet			: DataSet ID
*  param2) colID			: 비교할 컬럼명
*  return Type				: boolean
*  return 내용				: true/false
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
*  함   수  명 : fnc_IsEmail
*  설       명 : 이메일 형식인지 체크한다.
*  argument    : 1.sValue   : 이메일주소
*  return Type : boolean
*  return 내용 : true/false
************************************************************************/
function fnc_IsEmail(sValue)
{
	var sTemp   = "";
	var sRegExp = "[a-z0-9-_]+[a-z0-9.,-_]+@[a-z0-9-]+[a-z0-9.,]+\\.[a-z0-9]+";
	
	var regexp = CreateRegExp(sRegExp, "ig");
	sTemp = regexp.Exec(sValue);
	
	if ( sTemp == null || !(( sTemp.index == 0 ) && ( sTemp.length == sValue.length )) ) {
		alert("메일형식이 아닙니다.");
		return false;
	}
	
	return true;
}



/*-------------------------------------------------------------------------------------------------+
>>  주민번호 체크 로직
>>>>  param1)  sResNum       : 주민번호
>>>>  return :  True, False
+-------------------------------------------------------------------------------------------------*/
function fnc_ResidenceNumberCheck(sResNum){

	var sResUnit = Array(13);    // 주민 번호를 자리수별로 나눔
	var sResArgu = Array(13);    // 공식 계산 인자 값
	var iCalSum  = 0;            // 공식에 따른 결과 값
	var iResult  = 0;            // 계산되 체크 비트 값
	
	//주민번호 자리수 체크
	if (IsDigit(sResNum)==false) return false;
	if (length(trim(sResNum))!=13) return false;

	//주민 번호를 자리수로 자르기
	for (var i=0; i<13; i++) {
		sResUnit[i] = parseInt(substr(sResNum,  i, 1));
	}
	
	//검증 매개변수 설정
	sResArgu = [2,3,4,5,6,7,8,9,2,3,4,5];
	
	if ((sResUnit[6]>0)&&(sResUnit[6]<5)) {
	
		//국내 주민번호 체크
		for (i=0; i<12; i++) {
			iCalSum += parseInt(sResUnit[i]) * parseInt(sResArgu[i]);
		}

		iResult = parseInt(iCalSum) % 11;
	    iResult = (11 - parseInt(iResult)) % 10;
	
	} else if ((sResUnit[6]>4)&&(sResUnit[6]<9)){
		
		//5,6,7,8은 외국인 등록 번호 체크
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
		
		//기타는 False
		return false;
	}
	
	//계산된 Check Bit 값과 입력된 Check Bit 비교
	if (iResult == sResUnit[12]) {
		return true;
	} else {
		return false;
	}
}



/*-------------------------------------------------------------------------------------------------+
>>  사업자 체크 로직 function
>>>>  param1)  strNum       : 사업자 번호
>>>>  return :  True, False
+-------------------------------------------------------------------------------------------------*/
function fnc_FormatCustNumber(strNum){
    strNum = replace(strNum,"-");

    if(strNum.length != 10){
        //cfn_setMsg("W","N","사업자등록번호가 잘못되었습니다.","");
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
		//alert("빙고 올바른거.. ");
		return true;
    } else {
		// cfn_setMsg("W","N","사업자등록번호가 잘못되었습니다.","");
        return false;
    }
    return true;
}



/******************************************************
 *  기준 일자로 부터 몇일 이전 또는 이후 일자 계산
 *  기본은 이후 일자를 계산하나 isBefore 가 true인 경우 
 *  이전 일자로 계산한다. 
 * @param  orgDate  계산할 기준 일자 
 * @param  nDays  + 몇일 
 * @param   isBefore
           t rue-> 이전 일자 
            false -> 이후 일자 
 * @return  계산된 YYYYMMDD 일자 
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
*  함   수  명 : fnc_GetNumber
*  설       명 : 문자열에서 숫자만 가져오기
*  argument    : 1.sValue  : 해당 문자열
*  return Type : String
*  return 내용 : 숫자로 이루어진 문자열
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
*  함   수  명 : fnc_Sqmt
*  설       명 : 면적(m2)을 평수로 계산해주기
*  argument    : 1.sValue  : 해당 면적(m2)
				 2.iDigit  : 반올림할 자리수
*  return Type : String
*  return 내용 : 숫자로 이루어진 문자열
************************************************************************/
function fnc_Sqmt(sValue, iDigit)
{
    var sReturn = "";

    sReturn = round(toFloat(sValue) * 0.3025, iDigit);

    return sReturn;
}

/************************************************************************
*  함   수  명 : fnc_GetMonthLastDay
*  설       명 : 월의 마지막날 계산
*  argument    : 1.sDate  : 해당월이 들어간 날짜
*  return Type : number
*  return 내용 : 해당 월의 마지막 날짜
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
 *  현재일자 구하기 
 * @return  현재  YYYYMMDD 일자 
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
>>  화면 종료 시 종료 여부 및 활성화 Form DataSet 처리
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
	
		//변경된 자료가 있는지 체크함
		if ( bChangeYn ) {
			sMessageCode = SNIS_COM_1001;
	
			//화면 종료 여부 확인 
			if ( !fnc_Message(sMessageCode, "Y") ) {
				return false;    //종료하지 않고 Unload Cracel 처리 한다.
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
>>  탭 변경 시 데이터셋 변경여부 체크
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

	//변경된 자료가 있는지 체크함
	if ( bChangeYn ) {
		if ( !fnc_Message(SNIS_RBM_A006, "Y") ) {
			return false;    //탭을 변경하지 않는다
		}
	}
	
	//데이터셋 초기화
	for ( var i = 0; i < sDataset.length(); i++ ) {
        objDataset = Object(sDataset[i]);
        objDataset.Clear();
    }
    return true;
}

/*-------------------------------------------------------------------------------------------------+
>> 마우스 커서를 작업중 상태로 유지한다.
>>>>  1)  param bFlag - true/false 작업중(모래시계)/화살표
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
>> Calendar Component Content 값을 생성하여 돌려준다.
>>>>  1)  param initVal - 초기 값
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
*  함   수  명				: fnc_SetBKColor
*  설       명				: Form내의 Object의 bgcolor을 설정한다.
*  param1) oForm			: Form Object
*  return Type				: 해당 없음
*  return 내용				: 해당 없음
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
*  함   수  명				: fnc_ValueToCode
*  설       명				: 해당이름의 그룹코드와 코드의 합성후 리턴
*  param1) objDs			: 그룹코드와 코드와 이름정보를 담은Dataset 이름
*  param2) objDs			: 비교할 값
*  param3) objDs			: 비교할 Dataset 컬럼명
*  param4) objDs			: 가져올 Dataset 그룹코드 컬럼명
*  param5) objDs			: 가져올 Dataset 세부코드 컬럼명
*  return Type				: String
*  return 내용				: 해당 없음
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
*  함   수  명				: fnc_DatasetPrint
*  설       명				: Dataset Header 및 Data 정보를 PID의 Output창에 Display
*  param1) objDs			: Display 대상 Dataset
*  return Type				: 해당 없음
*  return 내용				: 해당 없음
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
    *  Header를 출력한다.
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
    *  내용을 출력한다.
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
*  함   수  명 : fnc_DeleteRow
*  설       명 : DataSet의 strColID 컬럼내에 값이 Value값이면 해당 Row를 삭제한다.
*  argument    : 1.DataSet  : 작업대상 Dataset Object
*                2.strColID : 컬럼명 String
*                3.Value    : 해당값 Object
*                4.nStartRow: 작업 시작 Row
*  return Type : 없음
*  return 내용 : 해당없음
************************************************************************/
function fnc_DeleteRow(DataSet, strColID, strValue, nStartRow) {
    var nCnt    = 0;
    var nDelCnt = 0;

    // 시작 Row값이 없으면 DataSet의 첫 Row부터 시행한다.
    if ( nStartRow == null ) {
        nStartRow = 0;
    }

    nCnt = DataSet.GetRowCount();
    DataSet.FireEvent = false;
    // DataSet을 Loop돌면서 해당값을 찾아 해당 Row를 삭제한다.
    for ( i = nStartRow; i < nCnt; i++ ) {
        if ( DataSet.GetColumn(i - nDelCnt ,strColID) == strValue ) {
            DataSet.DeleteRow(i - nDelCnt);
            nDelCnt++;
        }
    }
    DataSet.FireEvent = true;
}

/************************************************************************
*  함   수  명 : fnc_ReplaceColumn
*  설       명 : DataSet의 strColID 컬럼내에 SourceValue값을 Value값으로 변경한다.
*  argument    : 1.DataSet     : 작업대상 Dataset Object
*                2.strColID    : 컬럼명 String
*                3.SourceValue : 해당값 Object
*                4.ObjectValue : 해당값 Object
*  return Type : 없음
*  return 내용 : 해당없음
************************************************************************/
function fnc_ReplaceColumn(DataSet, strColID, SourceValue, ObjectValue) {
    // Dataset의 이벤트 및 RowType을 해지한다.
    DataSet.FireEvent     = false;
//    DataSet.UpdateControl = false;

    // Dataset을 Loop돌면서 해당값을 찾아 변경한다.
    for ( i = 0; i < DataSet.GetRowCount(); i++ ) {
        if ( trim(DataSet.GetColumn(i ,strColID)) == SourceValue ) {
            DataSet.SetColumn(i ,strColID, ObjectValue);
        }
    }

    // Dataset의 이벤트 및 RowType을 설정한다.
//    DataSet.UpdateControl = true;
    DataSet.FireEvent     = true;
}

/************************************************************************
*  함   수  명 : fnc_GetMandatoryViolateRow
*  설       명 : DataSet에서 저장작업을 할 Row중에서 필수로 입력 되어야 할 필드값에 값이 없는 경우
*                해당하는 Row를 리턴한다.
*  argument    : 1.DataSet             : 작업대상 Dataset Object
*                2.strColID            : 컬럼명 String(선택컬럼명)
*                3.strMandatoryColID   : 필수로 입력되어야 할 columns(구분자 '||')
*                4.strMandatoryColName : 필수로 입력되어야 할 columns명(구분자 '||')
*  return Type : LONG
*  return 내용 : 해당값을 찾은 경우는 DataSet의 첫번째 발견된 Row번호를,
*                값이 없는 경우는 -1을 return
************************************************************************/
function fnc_GetMandatoryViolateRow(DataSet, strMandatoryColID, strMandatoryColName, Grid) {
    var resultRow = -1;

    // column을 구분자로 구분하여 설정한다.
    var MandatoryColID   = split(strMandatoryColID  , "|");
    var MandatoryColName = split(strMandatoryColName, "|");

    // 원본 DataSet을 Loop돌면서 선택컬럼이 선택된 경우
    for ( i = 0; i < DataSet.GetRowCount(); i++ ) {
		if ( DataSet.GetRowType(i) != "normal" && DataSet.GetRowType(i) != "logical" ) {

            // 체크해야 필드 수만큼 해당 값을 검사한다.
            for ( j = 0; j < MandatoryColID.length(); j++ ) {

                // 필드값에 값이 없는 경우 메세지를 호출 한 후 해당 Row번호를 return 한다.
                if ( fnc_IsNull(DataSet.GetColumn(i, MandatoryColID[j])) ) {
                    DataSet.SetColumn(i, MandatoryColID[j], trim(DataSet.GetColumn(i, MandatoryColID[j])));
					fnc_Message(SNIS_CRA_0001, "N", i + 1, MandatoryColName[j]); 
                    // var str_msg = (i + 1) + "번째행 " + MandatoryColName[j] + "은(는) 필수 입력항목입니다.";
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
*  함   수  명 : fnc_GetExistValue
*  설       명 : DataSet의 strColID 컬럼내에 Value 값이 존재 하는지 검사
*  argument    : 1.DataSet   : 조회하고자 하는 Dataset Object
*                2.strColID  : 컬럼명 String
*                3.strValue  : 해당값 Object
*                4.nStartRow : 시작Row       
*  return Type : LONG
*  return 내용 : 해당값을 찾은 경우는 DataSet의 첫번째 발견된 Row번호를,
*                값이 없는 경우는 -1을 return
************************************************************************/
function fnc_GetExistValue(DataSet, strColID, strValue, nStartRow) {
    var resultRow = -1;

    // 시작Row값이 없으면 첫 Row부터 시작
    if ( nStartRow == null ) {
        nStartRow = 0;
    }

    strValue = toUpperCase(Trim(strValue));
    // Dataset을 Loop돌면서 해당값이 나오면 중지.
    // 해당값을 리턴
    for ( i = nStartRow; i < DataSet.GetRowCount(); i++ ) {
        if ( toUpperCase(trim(DataSet.GetColumn(i ,strColID))) == strValue ) {
            resultRow = i;
            break;
        }
    }

    // 해당값이 없으면 -1을 리턴
    return resultRow;
}

/************************************************************************
*  함   수  명 : fnc_SetAllColumnValue
*  설       명 : DataSet의 strColID 컬럼내에 모든 값을 Value값으로 변경한다.
*  argument    : 1.DataSet  : 작업하고자 하는 Dataset Object
*                2.strColID : 컬럼명 String
*                3.Value    : 해당값 Object
*  return Type : 없음
*  return 내용 : 해당없음
************************************************************************/
function fnc_SetAllColumnValue(DataSet, strColID, Value) {
    // DataSet의 strColID 컬럼내에 모든 값을 Value값으로 변경한다.
    for ( i = 0; i < DataSet.GetRowCount(); i++ ) {
        DataSet.SetColumn(i ,strColID, Value);
    }
}

/************************************************************************
*  함   수  명 : fnc_CopyDataset
*  설       명 : FromDataSet의 strColID 컬럼내에 Value값을 같는 Row를 ToDataSet에 복사한다.
*                RowType을 변경한다.
*  argument    : 1.ToDataSet   : 복사본이 저장될 Dataset Object
*                2.FromDataSet : 원본 Dataset Object
*                3.strColID    : 컬럼명 String
*                4.Value       : 해당값 Object
*  return Type : 없음
*  return 내용 : 해당없음
************************************************************************/
function fnc_CopyDataset(ToDataSet, FromDataSet, strColID, Value, RowTypeColID) {

    var nRow = 0;

    // 저장될 Dataset을 원본 Dataset의 컬럼과 동일시 생성하며 Row전체를 삭제한다.(Column을 일치시킨다.)
    ToDataSet.Copy(FromDataSet);
    ToDataSet.ClearData();

    if ( RowTypeColID != null ) {
        ToDataSet.AddColumn(RowTypeColID);
    }

    // FromDataSet을 Loop돌면서 해당값을 찾아 ToDataSet에 복사한다.
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
>> Calendar Component OnDayClick 이베트 처리 함수
>>>>  1)  param obj - Calendar Component 객체
>>>>  2)  param strText - 선택 날짜 값 : YYYYMMDD
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
    
	if (sRptDrct == 0){ //수직
		sFileName  = "common::comVrtRptViewer.xml";
		iWidth  = 760;  		
	}  else if(sRptDrct == 2) {
		sFileName  = "common::comVrtRptViewerRevision.xml";
		iWidth  = 760;  	
	} else { //수평
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
*  함   수  명 : fnc_GetWeekOfYear
*  설       명 : 입력된 일자에 해당하는 주(Week 반환) - 일주일에 시작은 월요일 기준
*  argument    : 1.date  : 일자
*  return Type : Int
*  return 내용 : week
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
*  함   수  명 : fnc_GetReturnParam
*  설       명 : 서버에서 생성된 Parameter 값 조회
*  argument    : 1.sKey  - Parameter Key
*  return Type : String
*  return 내용 : Parameter 값
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
*  함   수  명 : fnc_SetCnfmButton
*  설       명 : 회차 상태로 확정버튼 처리
*  argument    : 1.arrStat- 상태 Array
*  argument    : 2.objBtn  - 확정버튼
*  argument    : 3.title1  - 확정시 Text
*  argument    : 4.title2  - 취소시 Text
*  return Type : String
*  return 내용 : Parameter 값
+-------------------------------------------------------------------------------------------------*/
function fnc_SetCnfmButton(arrStat, objBtn, title1, title2) {
	//0:확정여부 1:취소가능여부 2:상태명, 3:확정가능여부
	//trace(arrStat[0]+":"+arrStat[1]+":"+arrStat[2]+":"+arrStat[3]+":"+arrStat[4]);	
	objBtn.Enable = false;
	
	if (arrStat[3] == "Y" && arrStat[0] == "N") {	//확정가능 && 미확정
		objBtn.Text = title1;
		objBtn.Enable = true;
		objBtn.UserData = "확정";
	} else if (arrStat[1] == "Y") {					//취소가능
		objBtn.Text = title2;
		objBtn.Enable = true;		
		objBtn.UserData = "취소";
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
		alert("그리드를 지정하세요!");
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
*  함   수  명 : fnc_DataSizeCheck
*  설       명 : from~to 일경우 to가 from보다 크지않도록 체크한다.
*  argument    : 1.fromYear     - from년도 object
*  argument    : 2.toYear       - to년도 object
*  argument    : 3.fromTms      - from회차 object
*  argument    : 4.toTms        - to회차 object
*  argument    : 5.fromDayOrd   - from일차 object
*  argument    : 6.toDayOrd     - to일차 object
*  return Type : Boolean
*  return 내용 : True, False
+-------------------------------------------------------------------------------------------------*/
function fnc_DataSizeCheck(objFromYear, objToYear, objFromTms, objToTms, objFromDayOrd, objToDayOrd){
    //1.년도체크
    if (objFromYear == null || objToYear == null) {
        fnc_Message(SNIS_COM_0001, "Y", "년도");
        return false;
    }
    
    if (objFromYear.value > objToYear.value) {
        fnc_Message(SNIS_COM_1040, "Y", "종료년도", "시작년도");
        objToYear.SetFocus();
        return false;
    }
    
    //2.회차체크
    if (objFromTms != null && objToTms != null) {
        if (objFromYear.value > objToYear.value && objFromTms.value > objToTms.value) {
            fnc_Message(SNIS_COM_1040, "Y", "종료회차", "시작회차");
            objToTms.SetFocus();
            return false;
        }
    }
    
    //3.일차체크
    if (objFromDayOrd != null && objToDayOrd != null) {
        if (objFromYear.value > objToYear.value && objFromTms.value > objToTms.value && objFromDayOrd.value > objToDayOrd.value) {
            fnc_Message(SNIS_COM_1040, "Y", "종료일차", "시작일차");
            objToDayOrd.SetFocus();
            return false;
        }
    }    
    
    return true; 
}

/*-------------------------------------------------------------------------------------------------+
*  함   수  명 : fnc_IsDirectory
*  설       명 : 디렉토리 존재여부를 확인해서 디렉토리 생성한다.
*  argument    : strFilePath  >>> 해당 디렉토리 경로
*  return Type : 
*  return 내용 : 
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
	if( nFileCount = -1 )                           //디렉토리가 존재 하지 않는다.
	{
        divBtnLst.flImage.MakeDir(strFilePath);     //디렉토리 생성
	}	

}

/*-------------------------------------------------------------------------------------------------+
*  함   수  명 : fnc_CreateHtmlFile
*  설       명 : html 저장경로+파일명, 해당내용을 받아 html페이지 생성
*  argument    : 1.strFilePath  >>> 해당 디렉토리및 파일명
*  argument    : 2.strContents  >>> 화면에서 보여지는 내용
*  return Type : 
*  return 내용 : 
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
		var msg = "시작/종료일자를 확인하세요!.";
		fnc_Message(SNIS_CRA_B999, "N", msg);		
		return false;
	} 
	
	return true;
}


/*-------------------------------------------------------------------------------------------------+
*  함   수  명 : fnc_CreateGridPopDivRacerRec
*  설       명 : 그리드에서 선수경주기록 팝업을 생성해준다.
*  argument    : 
*  argument    : 
*  return Type : 
*  return 내용 : 
+-------------------------------------------------------------------------------------------------*/
function fnc_CreateGridPopDivRacerRec() {
	if(Find("PopDiv_RacerRec")==null) {
		Create("PopupDiv", "PopDiv_RacerRec", "width='404' height='320' border='RAISED' url='common::comRaceRecPopup.xml'");
	}	
}

/*-------------------------------------------------------------------------------------------------+
*  함   수  명 : fnc_TrackPopDivRacerRec
*  설       명 : 그리드에서 선수경주기록 팝업해준다. 
*  argument    : 
*  argument    : 
*  return Type : 
*  return 내용 : 
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
		case 0 : return "일"; break;
		case 1 : return "월"; break;
		case 2 : return "화"; break;
		case 3 : return "수"; break;
		case 4 : return "목"; break;
		case 5 : return "금"; break;
		case 6 : return "토"; break;
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

// 2차원 배열의 변수 값 확인
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
>>  헤더값 width 조절 함
+-------------------------------------------------------------------------------------------------*/
function fnc_VisibleColumn(grdHead,dsHeadYoil)
{	
	// 요일 데이타셋 초기화
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
	
	// 사용할 수 있는 컬럼만 width 값을  100 으로 만듦
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
*  함   수  명 : fnc_AprvBtn
*  설       명 : 코드값에 따라 버튼 위치와 그리드 높이를 변환시킨다(평가에서 사용) 
*  argument    : 1.sAprvEmpNo   - 승인자
*  argument    : 2.sReqEmpNo    - 요청자
*  argument    : 3.sLoginUser   - Login한 사람 ID
*  argument    : 4.sAprvCd      - 상태값
*  argument    : 5.sRMenu       - 오른쪽 컴포넌트 Bottom
*  argument    : 6.sLMenu       - 왼쪽 컴포넌트 Bottom
+-------------------------------------------------------------------------------------------------*/

function fnc_AprvBtn(sAprvEmpNo, sReqEmpNo, sLoginUser, sAprvCd, sRMenu, sLMenu, BUTTON_TOP) 
{
	// 버튼과 그리드 위치(평가)
	var R_BUTTON_TOP  = "552";
	var R_BUTTON_LEFT = "704";
	var L_BUTTON_TOP  = "552";
	var L_BUTTON_LEFT = "616";
	var GRID_Y_BOTTOM = "544";	//버튼이 있을떄 그리드의 높이
	var GRID_N_BOTTOM = "566";	//버튼이 없을떄 그리드의 높이
	
	if( sAprvEmpNo == sLoginUser ) {
		//승인자 접속 시 처리
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
		//요청자 접속 시 처리
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
*  함   수  명 : fnc_AprvBtnLoc
*  설       명 : 코드값에 따라 버튼 위치와 그리드 높이를 변환시킨다(평가에서 사용) 
*  argument    : 1.sAprvEmpNo     - 승인자
*  argument    : 2.sReqEmpNo      - 요청자
*  argument    : 3.sLoginUser     - Login한 사람 ID
*  argument    : 4.sAprvCd        - 상태값
*  argument    : 5.BUTTON_TOP     - 버튼 TOP좌표값
*  argument    : 6.R_BUTTON_LEFT  - 오른쪽 버튼의 왼쪽 좌표값
+-------------------------------------------------------------------------------------------------*/

function fnc_AprvBtnLoc(sAprvEmpNo, sReqEmpNo, sLoginUser, sAprvCd, BUTTON_TOP, R_BUTTON_LEFT) 
{
	// 버튼과 그리드 위치(평가)
	var L_BUTTON_LEFT = toInteger(R_BUTTON_LEFT) - 80 - 2;	//80 :버튼Width  4:Space

	if( sAprvEmpNo == sLoginUser ) {
		//승인자 접속 시 처리
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
		//요청자 접속 시 처리
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
*  함   수  명 : fnc_BtnVisibleInit
*  설       명 : 버튼 감추기
*  argument    : 1.ARRBTN   -  버튼 배열
+-------------------------------------------------------------------------------------------------*/
function fnc_BtnVisibleInit(ARRBTN) {
	for(var i = 0 ; i < ARRBTN.length(); i++ ) {
		ARRBTN[i].Visible = false;
	}
}

/**
 * 레포트 미리보기 팝업 오픈(GateWay 방식 - SQL을 레포트에 포함)
 *
 * param rptFile   - 레포트 파일(ID)
 * param dsParamId - 파라미터 정의 Dataset ID
 * param fullSize  - 팝업 호출시 Screen Size에 꽉찬 상태로 호출할것인지 여부
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
 * 인자로 받은 문자열의 Null 여부를 체크하여 그 결과를 돌려준다.
 *
 * param sValue - 문자열 값
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



// 윈도우OS버전 조회
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
*  함   수  명				: fcd_PersonalMnChk
*  설       명				: 폼 로드 전 개인정보처리메뉴 여부를  조회한다
*  param1) FormID           : 호출한 화면 ID
*  return Type				: 해당 없음
*  return 내용				: true/false
**************************************************************************************************/
function fnc_PersonalMnChk(sFormId){
	if (fnc_isNull(sFormId)) return;
	
	Create("Dataset", "dsPersonalMn");
	
	var sServiceName = "common-service:SearchPersonalMn";		// 서버에 요청할 Sevice 명
	var sInDataSet   = "";										// Input DataSet 명칭
	var sOutDataSet  = "dsPersonalMn=dsPersonalMn";				// Output DataSet 명칭
		
	fcd_AddParam("FORM_ID", 	sFormId);   
	
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcPersonalCallBack", false);
	
	//Destroy("dsPersonalMn");
}

/**************************************************************************************************
*  함   수  명				: fcd_PersonalMnChk
*  설       명				: 폼 로드 전 개인정보처리메뉴 여부를  조회한다
*  param1) sFormId          : 호출한 화면 ID
*  param1) sUserID          : 사용자 ID
*  param2) sUserIp          : 사용자 IP
*  return Type				: 해당 없음
*  return 내용				: true/false
**************************************************************************************************/
function fnc_PersonalIdChk(sFormId){
	
	Create("Dataset", "dsPersonalIdChk");
	
	var sServiceName = "common-service:SearchPersonalIdChk";	// 서버에 요청할 Sevice 명
	var sInDataSet   = "";										// Input DataSet 명칭
	var sOutDataSet  = "dsPersonalIdChk=dsPersonalIdChk";		// Output DataSet 명칭
	
	var sUserID	= gdsUser.GetColumn(0,"USER_ID");
	var sUserIp = ext_GetIPAddress();
	
	fcd_AddParam("FORM_ID", 	sFormId);   
	fcd_AddParam("USER_ID", 	sUserId);   
		   
	fcd_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcPersonalCallBack", false);
	
	//Destroy("dsPersonalIdChk");
}

/**************************************************************************************************
>>  Transaction 후 처리 해야 할 내용!
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
