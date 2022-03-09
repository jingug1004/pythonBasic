/**
 * 화면에서 공통적으로 사용되는 javascript 함수를 정의한 파일.
 *********************************************************
 * 주의: 덮어쓰기의 위험이 있으니 FTP 작업은 자제해주십시오.
 *********************************************************
 * 현재 담당하는 기능은 에러팝업 처리 및 alert, confirm 처리
 *
 */

function showErrorPopup(params) {
	alert(params);
	var errorPopup = window.open('/errorPopup.jsp?' + params, 'errorPopup', 'width=540,height=260,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no');
	errorPopup.focus();
}

/**
 * 화면에 alert 형태로 뿌려줄 메시지를 저장하는 배열.
 * alert은 단방향의 공지형 메시지이므로 서술형으로 작성한다.
 *
 * (필요하신 분은 아래쪽에 새로운 메시지를 추가해주시기 바랍니다.
 * 메시지의 key는 팀명 + 식별가능한 임의의 문자열로 해주시구요)
 */
var alertMsg = new Array();
alertMsg['com_no_result'] = '조회결과가 없습니다.';
alertMsg['com_success_delete'] = '선택하신 정보가 성공적으로 삭제되었습니다.';
alertMsg['com_success_save'] = '입력하신 정보가 성공적으로 저장되었습니다.';
/**
 * showAlert('com_no_result')와 같은 형태로 호출.
 *
 * @param msg_id	alert 창으로 띄우려는 메시지의 ID
 * @param msg_str	공통메시지와 함께 표시하는 부가정보
 */
function showAlert(msg_id, msg_str) {
	var msg = alertMsg[msg_id];
	if (msg != '') {
		alert(msg);
	}
}

/**
 * showAlertMessage('com_no_result', '고객정보가 없음')와 같은 형태로 호출.
 *
 * @param msg_id	alert 창으로 띄우려는 메시지의 ID
 * @param msg_str	공통메시지와 함께 표시하는 부가정보
 */
function showAlertMessage(msg_id, msg_str) {
	var msg = alertMsg[msg_id];
	if (msg_str) {
		msg += '\n(' + msg_str + ')';
	}
	if (msg != '') {
		alert(msg);
	}
}

/**
 * 화면에 confirm 형태로 뿌려줄 메시지를 저장하는 배열.
 * confirm은 yes/no를 묻는 형태이므로 질문형으로 작성한다.
 * 
 * (필요하신 분은 아래쪽에 새로운 메시지를 추가해주시기 바랍니다.
 * 메시지의 key는 팀명 + 식별가능한 임의의 문자열로 해주시구요)
 */
var confirmMsg = new Array();
confirmMsg['com_confirm_new'] = '작업중인 정보는 모두 손실됩니다. 초기화하시겠습니까?';
confirmMsg['com_confirm_delete'] = '선택하신 정보가 삭제되어 복구할 수 없게됩니다. 삭제하시겠습니까?';
confirmMsg['com_confirm_save'] = '입력하신 정보가 저장됩니다. 저장하시겠습니까?';

/**
 * 화면에 confirm 형태로 메시지를 띄우고, '확인'을 눌렀을 경우
 * 지정된 javascript 함수를 실행시켜주는 함수.
 * 함수명이 없을 경우 confirm 결과를 단순히 return하고, 함수명이 있을 경우
 * confirm 결과에 따라 해당 함수를 호출해준다.
 * showAlert('com_confirm_new', 'alert', '안녕하십니까')와 같은 형태로 호출.
 *
 * @param msg_id 		confirMsg 배열의 index
 * @param functionStr	'확인'을 눌렀을 경우 실행하고자 하는 javascript 함수의 이름
 * @param args			functionName 함수에게 전달될 인수
 */
function showConfirm(msg_id, functionStr, args) {
	var ok = true;
	if (confirmMsg[msg_id]) {
		ok = confirm(confirmMsg[msg_id]);
	} 
	if (functionStr) {
		if (ok) {
			if (args && args.length > 0) {
				eval(functionStr)(args);
			} else {
				eval(functionStr)();
			}
		}
	} else {
		return ok;
	}
}

/**
 * 화면에 confirm 형태로 메시지를 띄우고, '확인'을 눌렀을 경우
 * 지정된 javascript 함수를 실행시켜주는 함수.
 * 함수명이 없을 경우 confirm 결과를 단순히 return하고, 함수명이 있을 경우
 * confirm 결과에 따라 해당 함수를 호출해준다.
 * showAlert('com_confirm_new', 'alert', '안녕하십니까')와 같은 형태로 호출.
 *
 * @param msg_id 		confirMsg 배열의 index
 * @param msg_str		공통메시지에 덧붙여 출력하려는 메시지
 * @param functionStr	'확인'을 눌렀을 경우 실행하고자 하는 javascript 함수의 이름
 * @param args			functionName 함수에게 전달될 인수
 */
function showConfirmMessage(msg_id, msg_str, functionStr, args) {
	var ok = true;
	var msg = confirmMsg[msg_id];
	if (msg_str) {
		msg += '\n(' + msg_str + ')';
	}
	if (msg != '') {
		var ok = confirm(msg);
	} 
	if (functionStr) {
		if (ok) {
			if (args && args.length > 0) {
				eval(functionStr)(args);
			} else {
				eval(functionStr)();
			}
		}
	} else {
		return ok;
	}
}

/**
 * Text와 Textarea를 제외한 element에서 입력되는 backspace는 무시한다.
 *
 */
function document.onkeydown() {
	var evnt_src = event.srcElement;
	if (event.keyCode == 8 && evnt_src.type != "text" && 
		evnt_src.tagName.toUpperCase() != "TEXTAREA") {
		event.cancelBubble = true;
		event.returnValue = false;
	}
}


///////////////한글의 길이를 체크/////////////////////
function StrLength(strIn)
{
	/*
     var strOut = 0;
     var agr = navigator.userAgent;
     var isIE4 = agr.indexOf("MSIE 4");
     var isIE5 = agr.indexOf("MSIE 5");
     var isIE6 = agr.indexOf("MSIE 6");     
     if((isIE4 != -1) || (isIE5 != -1) || (isIE6 != -1) ){
        for ( i = 0 ; i < strIn.length ; i++){
           ch = strIn.charAt(i);
           if ((ch == "\n") || ((ch >= "ㅏ") &&  (ch <= "히")) || ((ch >="ㄱ") &&  (ch <="ㅎ")))
	       strOut += 2;
           else
	       strOut += 1;
        }
     }else {
        strOut = strIn.length ;
     }
     return (strOut);
	 */
	var byteLength = 0;
	for (var inx = 0; inx <strIn.length; inx++) {
		var oneChar = escape(strIn.charAt(inx));
		if ( oneChar.length == 1 ) {
			byteLength ++;
		} else if (oneChar.indexOf("%u") != -1) {
			byteLength += 2;
		} else if (oneChar.indexOf("%") != -1) {
			byteLength += oneChar.length/3;
		}
	}

	return byteLength;
}
/////////////////////////////////////////////////////////////
/////////////////공백 처리 trim()///////////////////////
function trim(s_Word){ 
	var i_Len = s_Word.length;
	var i_StrLen = 0;
	while ((i_StrLen < i_Len) && (s_Word.charAt(i_StrLen) <= " "))
	    i_StrLen++;
	while ((i_StrLen < i_Len) && (s_Word.charAt(i_Len - 1) <= " "))
	    i_Len--;
	return ((i_StrLen > 0) || (i_Len < s_Word.length)) ? s_Word.substring(i_StrLen, i_Len) : s_Word;
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////SELECT 박스의 텍스트 값과 일치하면 선택 ///////////////////////////////////////
function select_event(obj, str) {

        var obj_length = obj.length;
        var temp = obj;
        var index;
                                
        for (index=0; index < obj_length; index++) {
                if (temp[index].value == str) {
                        temp.selectedindex = index;
                        temp.value = str;
                        select_event1(obj, index)
                        return;
                }
        }

}
function select_event1(obj, index) {
var temp = obj.selectedindex;
temp = index;
obj.value = obj[temp].value;
} 
////////////////체크된 항목이 있는지 검사///////////////////////////////////////////////////////////////////////////
function isSelected(obj) {
        var obj_length = obj.length;
        var temp = obj;
        var index;
        var returnValue=false;                                
        for (index=0; index < obj_length; index++) {
                if (temp[index].selected == true) {
                        returnValue=true;
                        break;
                }
        }
      return returnValue;
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////// 자동 이동/////////////////////////////////////////////////////////////////////////
var isNN = (navigator.appName.indexOf("Netscape")!=-1);
function autoTab(input,len, e) {
        var keyCode = (isNN) ? e.which : e.keyCode; 
        var filter = (isNN) ? [0,8,9] : [0,8,9,16,17,18,37,38,39,40,46];
        if(input.value.length >= len && !containsElement(filter,keyCode)) {
        input.value = input.value.slice(0, len);
        input.form[(getIndex(input)+1) % input.form.length].focus();
}
function containsElement(arr, ele) {
        var found = false, index = 0;
        while(!found && index < arr.length)
        if(arr[index] == ele)
        found = true;
        else
        index++;
        return found;
}
function getIndex(input) {
        var index = -1, i = 0, found = false;
        while (i < input.form.length && index == -1)
        if (input.form[i] == input)index = i;
        else i++;
        return index;
        }
        return true;
}
/*====================================================================
// 한글을 체크하는 함수
====================================================================*/
function IsHangul(value) {
    var   j;
    var    _hangul ='_-.@0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
        for(j=0;j<_hangul.length;j++){ 
        if(value == _hangul.charAt(j)) { 
            return true;
        }
        }
   return false;
}
/*==============================WINDOW팝업=================================
 파라메타 전송시 target으로 쏘시기 바랍니다.
=========================================================================*/
function newwindow(URL,width,height,name) { 
  var str;
  var sheight = screen.height; 
  var swidth = screen.width; 
   
  if (sheight < height){
     height = sheight*0.8;
  }
  
  if (swidth < width){
     width = swidth*0.8;
  }
    
  var top = (sheight-height)/2; 
  var left = (swidth-width)/2; 

  str="'toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no,";
  str=str+"width="+width;
  str=str+",height="+height+"'";
  str=str+",top="+top+"'";
  str=str+",left="+left+"'";
  var pupup=window.open(URL,name,str);
  pupup.focus();
  return;
}

/*====================================================================
[Descript] 두 날짜의 차이를 return 한다
sDate > eDate 를 sDate >= eDate로 수정함 (Nerb)
====================================================================*/
function diffDate(startDate, endDate)
{		
	var sYear = startDate.substr(0,4);
	var sMon = startDate.substr(4,2);
	var sDay = startDate.substr(6,2);
	var eYear = endDate.substr(0,4);
	var eMon = endDate.substr(4,2);
	var eDay = endDate.substr(6,2);	

	var sDate = new Date(sYear, sMon-1, sDay);
	var eDate = new Date(eYear, eMon-1, eDay);

	if (sDate > eDate)
	{
		alert("시작일이 종료일 보다 큽니다.. 시작일 : " + sDate + " 종료일 : " + eDate);
		return -1;
	}

	sDate.setYear = eDate.getYear;
	var daysAfter = (eDate.getTime() - sDate.getTime()) / (1000*60*60*24);
	daysAfter = Math.round(daysAfter);

	return (daysAfter+1);
}


/*====================================================================
[Descript] 두 달의 차이를 return 한다 
====================================================================*/
function diffMonth_noAlert(sYear, sMon, sDay, eYear, eMon, eDay )
{
	var sDate = new Date(sYear, sMon-1, sDay);
	var eDate = new Date(eYear, eMon-1, eDay);

	if (sDate > eDate)
	{
		return -1;
	}

	var diffDate = new Date();
	diffDate.setTime(eDate.getTime() - sDate.getTime());
	
	diff = ((diffDate.getYear() - 70) * 12) + diffDate.getMonth() + 1;
	 
	return (diff);
}

/*====================================================================
[Descript] 접수번호가 올바른지 체크한다.
====================================================================*/
function isAccptNoFormat(obj) {
    fld = getField(obj)
    var inputVal = getValue(fld);
    var inputStr = inputVal.toString();

	for (var i = 0; i < inputStr.length; i++) {
        var oneChar = inputStr.charAt(i)

	        if (oneChar < "0" || oneChar > "9") {
	            doSelection(fld);
	            dmbAlert("접수번호를 올바로 입력하십시오.")
	            return false;
	        }
        }

	if(inputStr != "" ){
		if(inputStr < 1 || inputStr > 999999999){
			doSelection(fld);
			dmbAlert("접수번호를 올바로 입력하십시오.")
			return false;
		}else{
			return true;
		}
	}else{
			return true;
        }	
}
////////////////////////주민번호 로직체크/////////////////////////////
function CheckResisterId(a,b){
    var first=a.value;
    var end=b.value;
    var chk =0;
    var yy = first.substring(0,2);
    var mm = first.substring(2,4);
    var dd = first.substring(4,6);
    var sex = end.substring(0,1);
    if (first.split(" ").join("") == "") {
        alert ('주민등록번호를 입력해주세요');
        a.focus();
        return false;
    }
    if (first.length!=6) {
        alert ('주민등록번호 앞자리를 입력하십시오');
        a.focus();
        return false;
    }
    if (end.length != 7 ) {
                alert ('주민등록번호 뒷자리를 입력하십시오.');
                b.value="";
                b.focus();
                return false;
    }
   if (isNaN(first) || isNaN(end)) {
           alert('주민등록번호는 숫자만 가능합니다.');
           a.value="";
           b.value="";
           a.focus();
           return false;
   }
    if ((first.length!=6)||(mm <1||mm>12||dd<1)) {
                alert ('주민등록번호 앞자리가 잘못되었습니다.');
                a.value="";
                a.focus();
                return false;
    }
    if ((sex != 1 && sex !=2 && sex != 3 && sex !=4 )||(end.length != 7 )) {
                alert ('주민등록번호 뒷자리가 잘못되었습니다.');
                b.value="";
                b.focus();
                return false;
    }
    for (var i = 0; i <=5 ; i++){
        chk = chk + ((i%8+2) * parseInt(first.substring(i,i+1)))
    }
    for (var i = 6; i <=11 ; i++){
            chk = chk + ((i%8+2) * parseInt(end.substring(i-6,i-5)))
    }
    chk = 11 - (chk %11)
    chk = chk % 10
    if (chk != end.substring(6,7)) {           
                alert ('형식에 맞지 않는 주민등록번호입니다.'); // 형식에 맞니 않는 주민번호 입력시 뜨는 창
                a.value="";
                b.value="";
                a.focus();
                return false;
    }
        return true;
}

//password 한글, 특수문자 check
function IsPassCheck(value) {
    var   j;
    var    _hangul ='!@$%^&*-_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
        for(j=0;j<_hangul.length;j++){ 
        if(value == _hangul.charAt(j)) { 
            return true;
        }
        }
   return false;
}

/* 숫자 인지 아닌지 체크 함수 */
function check_num(obj)
{
	var str="0123456789,"
	if (obj.value=="")
	{
		return;
	}
	for (i=0;i<obj.value.length;i++)
	{
		if (str.indexOf(obj.value.charAt(i))==-1)
		{
  
			alert("숫자만 가능합니다.");
			obj.value=""
			obj.focus();
			return;
		 }
	}
}

/* FLOAT 인지 아닌지 체크 함수 */
function check_float(obj)
{   
	var str="0123456789." 
	if (obj.value=="")
	{
		return;
	}
	for (i=0;i<obj.value.length;i++)
	{
		if (str.indexOf(obj.value.charAt(i))==-1)
		{
			alert("허용된 문자가 아닙니다.");
			obj.value=""
			obj.focus();
			return;
		 }
	}
}

/* input box 에 숫자 입력시 콤마(,)로 구분하여 표시하는 함수 */
function comma_auto(input_box)
{
    
	var ns = input_box.value.replace(/\,/g,""); 

	for(var i=0; i<ns.length; i++)
	{ 
//		if (ns.charAt(i)<"0" || ns.charAt(i) > "9") 
//		{
//			alert("숫자를 입력하십시요!!");
//			return; 
//		}

		if (isNaN(ns))
		{
			alert("숫자를 입력하십시요!!");
			input_box.value = "";
			input_box.focus();
			return; 
		}
	}
	
	loop_i = ns.length - 3;
	ret_str = "";
	
	if ((ns.indexOf("0") == 0)){
		input_box.value = "0" ;
		input_box.focus();
		return;
	}

	for( i = loop_i ; i >= 1 ; i = i - 3 )
	{
		ret_str = "," + ns.substring( i , i + 3 ) + ret_str
	}
	ret_str = ns.substring( 0 , i + 3 ) + ret_str
	input_box.value=ret_str
}

