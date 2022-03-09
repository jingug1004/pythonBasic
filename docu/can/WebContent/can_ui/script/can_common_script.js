/*================================================================================================/
 * PROGRAM-ID                           snis_common_script 
 * DESCRIPTION   :  차세대정보시스템에서 사용하는 공통 script                                        
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
>>  Form Load 時 처리 할 업무 처리 Function
>>  (화면 Title, 화면 Information, Loaction 및 권한 처리)
>>>>  param1)  obj           : 화면 Object
>>>>  param2)  sFORMCAPTION  : 화면 Title
>>>>  param3)  sFORMLOCATION : 화면 Location
>>>>  param4)  sBUTTONLIST   : 공통버튼 Enable, Disable 여부
>>>>  param5)  sUSERAUTH     : 사용권한
+-------------------------------------------------------------------------------------------------*/
function fnc_FormLoadSetting(obj, sFORMCAPTION, sFORMLOCATION, sBUTTONLIST, sUSERAUTH) {

	http.Sync = true;
	fnc_PersonalMnChk(sFormId);
	
	//변수 선언 및 Program 정보 Display
	var sNewButtonList  = "";                    //사용자 버튼 권한
	obj.Window.Title    = sFORMCAPTION;          //화면 Title Display
	obj.stFormTitle     = sFORMCAPTION;          //화면 정보 Display
//	obj.stFormLocation  = sFORMLOCATION;         //Loaction 정보 Display
    
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
	if (substr(sNewButtonList,0,1)=="F") obj.divBtnLst.btnCancel.Visible  = false;  //초기화
	if (substr(sNewButtonList,1,1)=="F") obj.divBtnLst.btnSave.Visible    = false;  //저장
	if (substr(sNewButtonList,2,1)=="F") obj.divBtnLst.btnDelete.Visible  = false;  //삭제
	if (substr(sNewButtonList,3,1)=="F") obj.divBtnLst.btnToExcel.Visible = false;  //엑셀
	if (substr(sNewButtonList,4,1)=="F") obj.divBtnLst.btnPrint.Visible   = false;  //인쇄
	if (substr(sNewButtonList,5,1)=="F") obj.divBtnLst.btnEnd.Visible     = false;  //종료
	
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
>>  Message에 '@'인자 삽입
>>>>  1) sSendMessage : 메세지
>>>>  2) sArg1        : 대체할 메세지
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

/**************************************************************************************************
*  함   수  명				: fcd_OpenExcelListExport
*  설       명				: 선택한 파일명을 excel 저장한다.
*  return Type				: Stirng
*  return 내용				: 파일명
**************************************************************************************************/
function fcd_OpenExcelListExport(sExcelList) {
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

	remote_url =  GBL_FILE_UPDNLOAD_URL+"?mode=upload&work=" + sWork;	// 파일 업로드인 경우 mode를 upload로 설정
   // alert(remote_url);
	file_size = httpfileObj.GetFileSizeLocal(sLocalFilePath);
	httpfileObj.CookieParam = sLocalFileName;		// 파일명을 쿠기에 넣어줌
	
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

	var rtn_val = httpfileObj.CookieParam;	// 서버로부터 받은 쿠키를 리턴 변수에 저장
	var str_sp = split2(rtn_val,"&","^");	// 쿠키를 구분자로 분리하여 저장하기 위한 변수
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
>>  사용자가 선택한 파일을 서버에서 삭제하는 Function
>>>>  param1)  str_param    : 서버에 삭제하려는 물리적인 파일명
+-------------------------------------------------------------------------------------------------*/
function fnc_FileDelete(str_param)
{
	remote_url =  GBL_FILE_UPDNLOAD_URL+"?mode=delete filename="+str_param;
	Transaction("", remote_url, "", "", "", "");
}



/******************************************************
 *  전체 Callback 
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
    
    //중복로그인인 경우 시스템 종료
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
>>  진행중 표시 팝업 오픈하기 위한 Function
+-------------------------------------------------------------------------------------------------*/
function fnc_ProgressPop() {
    nHandle = Open("common::comPopProgress.xml", "", 294, 112, "Title=false");
}



/*-------------------------------------------------------------------------------------------------+
>>  진행중 표시 팝업 Close하기 위한 Function
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
function fnc_DataSetCancel(obj) {
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




/*-------------------------------------------------------------------------------------------------+
>>  화면 종료 시 종료 여부 및 활성화 Form DataSet 처리
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

	//변경된 자료가 있는지 체크함
	if ( bChangeYn ) {
		sMessageCode = SNIS_COM_1001;

		//화면 종료 여부 확인 
		if ( !fnc_Message(sMessageCode, "Y") ) {
			return false;    //종료하지 않고 Unload Cancel 처리 한다.
		}		
	}
	
	if ( global.FrameBottom != null ) {	
        global.FrameBottom.fcDeleteTab(sFormID);
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
				oForm.Components[i].BKColor = "#D8D8D8";
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

		if ( DataSet.GetRowType(i) != "normal" ) {

            // 체크해야 필드 수만큼 해당 값을 검사한다.
            for ( j = 0; j < MandatoryColID.length(); j++ ) {

                // 필드값에 값이 없는 경우 메세지를 호출 한 후 해당 Row번호를 return 한다.
                if ( fnc_IsNull(DataSet.GetColumn(i, MandatoryColID[j])) ) {
                    DataSet.SetColumn(i, MandatoryColID[j], trim(DataSet.GetColumn(i, MandatoryColID[j])));
					fnc_Message(SNIS_CAN_0001, "N", i + 1, MandatoryColName[j]); 
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

function fnc_RPTPrint(sSubUrl, sRptNm, sDataSet, sArg, sRptDrct, sPrintCnt, sRptType) {
    var sFileName  = "";
	
	if (sRptDrct == 0){ //수직
		sFileName  = "common::comVrtRptViewer.xml";
	} else { //수평
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
	
	Destroy("dsPersonalMn");
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
	
	Destroy("dsPersonalIdChk");
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