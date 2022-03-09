/**
 * 파일명: validations.js
 * 작성자: 박찬우 (nucha@dreamwiz.com)
 *            김양주 (superbbosong@nate.com)
 * 작성일: 2003/11/20/목요일
 *
 * 새로운 mytype이 생길 경우 처리과정
 * 1. 기본 타입이면 함수를 만든다. 예) isNumber(숫자인지 판단), isEssential(필수입력항목인지 판단)
 *    복합타입이면 기본 함수를 이용한다. 예) isEssNum(필수입력항목이면서 숫자필드인지 판단)
 * 2. validDispatcher 배열을 초기화한다.
 * 3. 관련 타입에 대하여 포매팅, 언포매팅을 적용한다.
 *
 * 주의사항: mytype=""과 같은 코드는 제거하기 바란다. 원하는 필드 타입을 정확히 기입해야 한다.
 *
 * @version 1.0.2a, 2004/04/27 - 폼 단위로 각 구성요소 활성화/비활성화 하는 enableForm(myform), disableForm(myform) 추가 (박찬우)
 *                                          - 특정 폼 refresh하는 refresh(myform) 함수 추가#포맷팅이 안된 요소가 있으면 다시 포맷팅이 적용됨.
 * @version 1.0.2,  2004/04/23 - 영문과 숫자만 존재하는 타입 추가(NUM_ENG or ENG_NUM)
 * @version 1.0.1a, 2004/04/20 - select box의 경우 checkRealTimeValid() 함수를 타지 않도록 수정.
 *                               (focus/unfocus 과정에서 select box가 움직이는 문제 때문에)
 * @version 1.0.1,  2004/04/14 - getValueOnly(obj) 함수 추가 (진헌규 hkjin@daou.co.kr) 
 * @version 1.0.0d, 2004/04/13 - getValue(obj) 내부에 doSelection(fld); 행 추가#포맷 제거한 후, 값을 얻기 위함.
 *                                          - undoSelection(fld) 함수 추가.
 *                                          - 무한루프 발생한다는 보고에 따라 일단 위의 모든 기능 제거 했음.
 * @version 1.0.0c, 2004/04/11 - '9999/12/31' 지원하도록 isDateFormat() 함수 수정. 진헌규(hkjin@daou.co.kr)
 * @version 1.0.0b, 2004/04/07 - reset(aDocument) 함수 추가#document 내의 모든 폼을 초기화함. (박찬우)
 *                                            copyName2Value(aDocument) 함수 추가#특정 문서 내의 폼 구성요소 이름을 값 필드에 보여준다. (박찬우)
 * @version 1.0.0a, 2004/03/26 - select-multiple 타입에 대한 getValue() 구현, 전역변수 gDELIM 정의 (박찬우)
 * @version 1.0.0a, 2004/03/26 - isEssential()의 fld.desc 속성을 이용한 경고 메시지 수정 (박찬우)
 * @version 1.0.0, 2004/03/10
 */


//					<< 전역변수 선언 >>
//form element는 자신이 속한 form 정보는 가지지만 frame 정보를 가지고 있지 않다.
//frame과 form element가 서로 알지 못하므로 저장하여 재사용해야 한다.

var gDELIM = "`";
var gFrame; //유효성 체크를 원하는 form을 포함하는 frame reference
var gField; //reference to the calling form element
var gMyType;
var gDateDelim = "-"

var gTimeDelim = ":" //김경수(0401).

var gCachedVal = null
var gIsEvent = false //update 필요할까?

var gDevelopmentMode = true;

var validDispatcher = new Array();

//기본타입
validDispatcher["NUM"] = isNumber //숫자인가?
validDispatcher["NUMFORMAT"] = isNumberFormat //숫자포멧인가?
validDispatcher["EMAIL"] = isEmail //email인가?
validDispatcher["TEL"] = isTel //전화번호인가?
validDispatcher["ESS"] = isEssential //필수입력항목인가?
validDispatcher["DATE"] = isDateFormat //날짜인가?
validDispatcher["YYYYMM"] = isYyyyMmFormat //YYYYMM (김경수)
validDispatcher["ONLYNUM"] = isOnlyNumber //오직 숫자만 가능(김경수)
validDispatcher["DATETIME"] = isDateTimeFormat //날짜시분초 김경수(0401).
validDispatcher["CONTNO"] = isContNoFormat //가입계약번호 이필수(2004-04-02).
validDispatcher["ACNTNO"] = isAcntNoFormat //청구계정번호 이필수(2004-04-02).
validDispatcher["CUSTNO"] = isCustNoFormat //고객번호 이필수(2004-04-02).
validDispatcher["NUMENG"] = isNumberAndEnglish // 숫자와 영문자의 조합인가?
validDispatcher["ENGNUM"] = isNumberAndEnglish // 숫자와 영문자의 조합인가?

//필수타입
validDispatcher["ESS_NUM"] = isEssNum
validDispatcher["ESS_TEL"] = isEssTel
validDispatcher["ESS_DATE"] = isEssDate
validDispatcher["ESS_YYYYMM"] = isEssYyyyMm //(김경수)
validDispatcher["ESS_DATETIME"] = isEssDateTime //김경수(0401).
validDispatcher["ESS_ONLYNUM"] = isEssOnlyNum //(김경수)
validDispatcher["ESS_CONTNO"] = isContNo //가입계약번호 이필수(2004-04-02).
validDispatcher["ESS_ACNTNO"] = isAcntNo //청구계정번호 이필수(2004-04-02).
validDispatcher["ESS_CUSTNO"] = isCustNo //고객번호 이필수(2004-04-02).
validDispatcher["ESS_NUMENG"] = isEssNumberAndEnglish // 숫자와 영문자의 조합인가?
validDispatcher["ESS_ENGNUM"] = isEssNumberAndEnglish // 숫자와 영문자의 조합인가?


validDispatcher["RRN1"] = isRRN1
validDispatcher["RRN2"] = isRRN2

//복합타입 추가(2004.03.15)
validDispatcher["BIZ_NUM_01"] = checkBizNum1
validDispatcher["BIZ_NUM_02"] = checkBizNum2
validDispatcher["FRN_NUM_01"] = checkFrnNum1
validDispatcher["FRN_NUM_02"] = checkFrnNum2
validDispatcher["BIN_NUM_01"] = checkBinNum1
validDispatcher["BIN_NUM_02"] = checkBinNum2

/*
validDispatcher["RRN"] = //주민등록번호인가?
*/

/*
validDispatcher["PNUM"] = new dispatcher(isPNUM)//Positive Number
validDispatcher["NNUM"] = new dispatcher(isNNUM)//Negative Number
validDispatcher["INT"] = new dispatcher(isINT)//Integer
validDispatcher["PINT"] = new dispatcher(isPINT)//Positive Integer
validDispatcher["NINT"] = new dispatcher(isNINT)//Negative Integer
validDispatcher["ENG"] = new dispatcher(isENG)//English
validDispatcher["EMAIL"] = new dispatcher(isEMAIL)//English
validDispatcher["HAN"] = new dispatcher(isHAN)//한글
validDispatcher["DATE"] = new dispatcher(isDATE)//Date(2003.11.20)
validDispatcher["TIME"] = new dispatcher(isTIME)//Time(13:20:01)
validDispatcher["DATE_TIME"] = new dispatcher(isDATE_TIME)//Date+Time
validDispatcher["ENG_NUM"] = new dispatcher(isNUM_ENG)//Number+English
//순서가 상관없는 경우는?

validDispatcher["ZIP"] = new dispatcher(isZip)
*/

validDispatcher["CARD"] = isCardFormat //카드번호 김경수(2004-04-20).
validDispatcher["ESS_CARD"] = isEssCardFormat ////카드번호 김경수(2004-04-20).


function dmbAlert(aMsg) {
    if (gDevelopmentMode) {
		alert(aMsg);
    }
}

function viewObj(obj) {
    var myWin = window.open("", "", "");
    myWin.status = "객체 정보를 만들기 위한 시간이 걸릴 수 있습니다."

    myWin.document.writeln("<table>");
    myWin.document.writeln("<tbody>");
    for (var member in obj) {
        myWin.document.writeln("<tr>");
        myWin.document.writeln("<td><b>" + member + "</b></td>");
        myWin.document.writeln("<td>" + obj[member] + "</td>");
        myWin.document.writeln("</tr>");
    }
    myWin.document.writeln("</tbody>");
    myWin.document.writeln("</table>");
    //myWin.status = "객체정보 전송 완료. from 위성 DMB 프로젝트";
	myWin.status = "객체정보 전송 완료. from KYERYONG";
}

//================= << Utility Data Set, Get Handlers >>========================
//필드에 값을 세팅한다.
function setValue(fld, val) {
    switch (fld.type) {
        case "text" :
		fld.value = val;
        case "password" :
        case "textarea" :
        case "file" :
        case "hidden" :
            	fld.value = val;
            break;
        case "select-one" :
            for (var idx=0; idx < fld.options.length; idx++) {
                if (fld.options[idx].value == val) {
                    fld.selectedIndex = idx;
                    break;
                }
            }
            break;
        case "select-multiple" :
            for (var idx = 0; idx < fld.options.length; idx++) {
                var isExist = true;
                if (isExist) {//update: setValue.hasValue(fld.options[idx].value)
                    fld.options[idx].selected = true;
                }
            }
            break;
        case "radio" :
        case "checkbox" :
            if (fld.value == val) {
                fld.checked = true;
            }
            break;
    }
}

//필드의 값을 얻어낸다.
function getValue(obj) {
    //viewObj(obj)

    var fld = getField(obj);
	//doSelection(fld);


    var myValue = null;
    switch (fld.type) {
        case "text" :
        case "password" :
        case "textarea" :
        case "file" :
        case "hidden" :
            myValue = fld.value;
            break;
        case "select-one" :
            myValue = fld.options[fld.selectedIndex].value;
            break;
        case "select-multiple" :
            dmbAlert("select-multiple 타입에 대한 getValue는 구현되어 있지 않습니다.\n필요하다면 기술지원팀에 말씀해주세요.")
            myValue = new Array();
            for (var idx=0; idx<fld.options.length; idx++) {
                if (fld.options[idx].selected) {
                    myValue += gDELIM + fld.options[idx].value;
                }
            }
            break;
        case "radio" :  case "checkbox" :
            if (fld.checked) {
                myValue = fld.value;
            }
            break;
    }



    return myValue;
}

/**
 * 필드를 입력받아 포맷을 제거한 값을 돌려준다.
 * unformat() 함수를 그대로 이용하기 위해 원래 field의 값을 다른 변수에 복사하고,
 * 이 변수를 unformat() 함수에 넘긴다.
 * 
 * @author 진헌규(hkjin@daou.co.kr)
 */
function getValueOnly(obj) {
	if (obj.mytype != "undefined") {
		var fieldClone = new createFieldClone(obj);
		unformat(fieldClone);
		return fieldClone.value;
	} else {
		return obj.value;
	}
}

/**
 * getValueOnly() 함수에서 이용하는 함수.
 * 인수로 주어진 field의 속성을 갖는 다른 변수를 생성한다.
 *
 * @author 진헌규(hkjin@daou.co.kr)
 */
function createFieldClone(obj) {
	this.type = obj.type;
	this.value = getValue(obj);
	this.mytype = obj.mytype;
	return this;
}

//컴포넌트의 값을 리턴한다.
//컴포넌트의 타입이 select-multiple일 경우에는 값배열을 리턴한다.
//obj: event 객체이거나 폼 구성요소
function getField(obj) {
    //viewObj(obj)
    if (typeof(obj.cancelBubble) != "undefined") {
        gIsEvent = true
        return obj.srcElement;
    }
    else {
        gIsEvent = false
        return obj
    }
}



//========================<< Validation Check >>================================
//	Testing for a Decimal Point
// general purpose function to see if a suspected numeric input
// is a positive or negative number
function isNumber(obj) {
    var fld = getField(obj);
    var inputVal = getValue(fld);
    
    if( inputVal =='' ) return true;
        
    if( !numberValueCheck(obj) ){
        doSelection(fld)
        dmbAlert("숫자를 입력하십시오.")
        return false;
    }
    
    if( !isMatchNumberFormat(obj) ){
        return false;
    }
    
    return true
}

//김경수(2004-04-10)
function numberValueCheck(obj){
    var fld = getField(obj);
    var inputVal = getValue(fld);
    var idxPlus	 = inputVal.lastIndexOf("-");

    if (idxPlus == 0) {
        inputVal = inputVal.substring(1, inputVal.length);
    }
        
    if( inputVal =='' ) return false;
    
    oneDecimal = false
    inputStr = inputVal.toString()
    for (var i = 0; i < inputStr.length; i++) {
        var oneChar = inputStr.charAt(i)
        
        if( oneChar=="." && ( i==0 || i==inputStr.length-1 ) ){
            return false;
        }

        if (oneChar == "." && !oneDecimal) {
            oneDecimal = true
            continue
        }

        if (oneChar < "0" || oneChar > "9") {
            return false
        }
    }    
    return true;
}

//김경수(2004-04-10)
function isMatchNumberFormat(obj) {
    var fld = getField(obj);
    var inputVal = getValue(fld).replace(/\-/g,"");
    if (typeof(fld.myformat) == "undefined" || fld.myformat.replace(/\ /g,"") == "" ) {
        return true;
    }
    
    var numtype = fld.myformat.replace(/\ /g,"");
    var frontType = getFront(numtype, ".");
    var endType = getEnd(numtype, ".");
    var frontVal = getFront(inputVal, ".");
    var endVal = getEnd(inputVal, ".");

    frontType == null ? frontType = numtype*1 : "";
    endType == null ? endType =0 : "";
    frontVal == null ? frontVal = inputVal : "";
    endVal == null ? endVal = "" : "";
    
    if( frontType=='' || frontType*1==0 ){
        if( !(frontVal.length==1 && frontVal*1==0) || endType*1 < endVal.length ){
            doSelection(fld);
            dmbAlert("소수만으로 된 값으로\n\n"+ endType +" 째 자리 까지만 입력 가능합니다.");
            return false;
        }
    }else if( endType=='' || endType*1==0 ){
        if( endVal.length > 0 || frontType*1 < frontVal.length ){
            doSelection(fld);
            dmbAlert("정수만으로 된 값으로\n\n"+ frontType +" 째 자리 까지만 입력 가능합니다.");
            return false;
        }    
    }else if( frontType*1 < frontVal.length || endType*1 < endVal.length ){
        doSelection(fld);
        dmbAlert("정수 : "+ frontType +" 자리\n\n"+"소수 : "+ endType +" 자리\n\n" + "까지 입력 가능합니다.");
        return false;
    }
    
	return true;
}
//=============================주민등록번호 앞자리 6자리=============================================//
function isRRN1(obj)
{
    var fld	 = getField(obj)
    var input = getValue(fld)
    var re = /\b(\d{2})(1[0-2]|0?[1-9])(0?[1-9]|[12][0-9]|3[01])/

    if (re.test(input))
    {
        comNext(fld).focus();
        return true;
    }
    else
    {
        doSelection(fld);
        dmbAlert("주민등록번호를 확인하여주세요");
        getField(obj).value="";
        return false;
    }

}
//==========================주민등록번호 뒷자리 7자리 + 주민등록 check============================================///
function isRRN2(obj)
{
    var input	=  getValue(comPrev(getField(obj))) + getValue(getField(obj));

    //lert(input);

    var sum = 0;

    for (idx = 0, jdx = 2; jdx < 10 ; idx++, jdx++){
        sum = sum + (input.charAt(idx) * jdx);
    }

    for (idx = 8, jdx = 2; jdx < 6 ; idx++, jdx++){
        sum = sum + (input.charAt(idx) * jdx);
    }

    var nam = sum % 11;
    var checkDit = 11 - nam;

    checkDit = (checkDit >=10 ) ? checkDit - 10 : checkDit;

    if (input.charAt(12) != checkDit)
    {
        doSelection(getField(obj));
        dmbAlert("주민등록번호를 확인하여주세요");
        getField(obj).value="";
        return false;
    }
}

//Checking for Leading Minus Sign
// general purpose function to see if a suspected numeric input
// is a positive or negative integer
function isInteger(inputVal) {
    inputStr = inputVal.toString()
    for (var i = 0; i < inputStr.length; i++) {
        var oneChar = inputStr.charAt(i)
        if (i == 0 && oneChar == "-") {
            continue
        }

        if (oneChar < "0" || oneChar > "9") {
            return false
        }
    }
    return true
}


function isEmail(obj) {
    var fld = getField(obj);
    var re = /(\S+)@(\S+)\.(\S+)/
    var inputVal = getValue(fld)

    if (inputVal == '') {
	return;
    }
    if (re.test(inputVal)) {
        return true;
    }
    doSelection(fld)
    dmbAlert("유효한 이메일 주소를 입력하십시오.\n입력예(chanwoo@nucha.net)");
    return false;
}

function isTel(obj) {
    var fld = getField(obj)
    var input = getValue(fld)
    if (input == '') {
	return;
    }
    //var re = /\b(\d{2,4})[\-](\d{3,4})[\-](\d{3,4})/
	var re = /\b(\d{3,4})[\-](\d{3,4})/
    if (re.test(input)) {
        return true;
    }
    doSelection(fld)
    dmbAlert("전화번호 입력 오류입니다.\n입력예: 123-4567-->1234567");
    return false;
}

function isEssential(obj) {
    var fld = getField(obj)
    var input = getValue(fld)
    input = trim(input)
    if (input != "") {
        return true
    }
    doSelection(fld)
    
    if (fld.desc != null && fld.desc != '') {
		dmbAlert(fld.desc + "는(은) 필수입력항목입니다.")
    } else {
        dmbAlert(fld.name + "는(은) 필수입력항목입니다.")
    }
    return false
}


// 김경수가 추가함.
function isYyyyMmFormat(obj) {
    var fld = getField(obj)
    var input = getValue(fld)	
	
    if( input =='' ) return true;
	
    var re = /\b((19|20)\d{2})(1[0-2]|0[1-9])\b/

    if (!re.test(input)) {
        doSelection(fld)
        dmbAlert("날짜 입력이 잘못되었습니다.\n입력예: 200512");
        return false;
    }

    var year	= input.substring(0,4);
    var month	= input.substring(4,6);

    if(month > 12 || month < 1 )
    {
        doSelection(fld)		
        dmbAlert("날짜 입력이 잘못되었습니다.\n입력예: 200511");
        return false;
    }    
    return true;
}


function isNumberFormat(obj) {
	if (isNumber(obj)) {
		return true
    }

    return false
}


function isDateFormat(obj) {
    var fld = getField(obj)
    var input = getValue(fld)

    if (input == '') {
        return;
    }

    //update: 13월도 입력이 통과됨
    var re = /\b((19|20|99)\d{2})(1[0-2]|0?[1-9])(0?[1-9]|[12][0-9]|3[01])/

    /*
    var matchArray = re.exec(input)
    if (matchArray) {
        alert(matchArray[1]);
        alert(matchArray[2]);
        alert(matchArray[3]);
    }
    */



    if (!re.test(input)) {
        doSelection(fld)
        dmbAlert("날짜 입력이 잘못되었습니다.\n입력예: 20051225");
        return false;
    }

    var year	= input.substring(0,4);
    var month	= input.substring(4,6);
    var day		= input.substring(6,8);

    var inMonths = new Array(31,28,31,30,31,30,31,31,30,31,30,31);

    var isDay ;
    isDay	 = (month !=2 ) ? inMonths[month-1] : ((year%4 == 0 && year%100 !=0 || year%400 ==0) ? 29:28);

    if(month > 12 || month < 1 || day < 1 || day > isDay)
    {
        doSelection(fld)
        dmbAlert("날짜 입력이 잘못되었습니다.\n입력예: 20051125");
        return false;
    }

    return true;
}

// 김경수(0401).
function isDateTimeFormat(obj) {
    var fld = getField(obj)
    var input = getValue(fld)	
	
	if( input =='' ) return true;	// 김경수.
	
    var re = /\b((19|20)\d{2})(1[0-2]|0[1-9])(0[1-9]|1[0-9]|2[0-9]|3[0-1])(0[0-9]|1[0-9]|2[0-3])(0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])(0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])\b/

    if (!re.test(input)) {
        doSelection(fld)
        dmbAlert("날짜 입력이 잘못되었습니다.\n입력예: 20040101000000");
        return false;
    }

    var year	= input.substring(0,4);
    var month	= input.substring(4,6);
    var day		= input.substring(6,8);

    var inMonths = new Array(31,28,31,30,31,30,31,31,30,31,30,31);

    var isDay ;
    isDay	 = (month !=2 ) ? inMonths[month-1] : ((year%4 == 0 && year%100 !=0 || year%400 ==0) ? 29:28);

    if(month > 12 || month < 1 || day < 1 || day > isDay)
    {
        doSelection(fld)		
        dmbAlert("날짜 입력이 잘못되었습니다.\n입력예: 20040101000001");
        return false;
    }
    
    return true;
}

function isEssNum(obj) {
    if (isEssential(obj)) {
        if (isNumber(obj)) {
            return true
        }
    }

    return false
}

function isEssTel(obj) {
    if (isEssential(obj)) {
        if (isTel(obj)) {
            return true
        }
    }

    return false
}

function isEssDate(obj) {
    if (isEssential(obj)) {
        if (isDateFormat(obj)) {
            return true
        }
    }

    return false
}


//김경수가 추가.
function isEssYyyyMm(obj) {
    if (isEssential(obj)) {
        if (isYyyyMmFormat(obj)) {
            return true
        }
    }

    return false
}

// 김경수(0401).
function isEssDateTime(obj) {
    if (isEssential(obj)) {
        if (isDateTimeFormat(obj)) {
            return true
        }
    }

    return false
}


//========================<< 오직 숫자만 입력가능(김경수). >>================================
function isOnlyNumber(obj) {
    fld = getField(obj)
    var inputVal = getValue(fld);
    var inputStr = inputVal.toString();

    for (var i = 0; i < inputStr.length; i++) {
        var oneChar = inputStr.charAt(i)

        if (oneChar < "0" || oneChar > "9") {
            doSelection(fld)
            dmbAlert("숫자를 입력하십시오.")
            return false
        }
    }
    return true
}

// 김경수.
function isEssOnlyNum(obj) {
    if (isEssential(obj)) {
        if (isOnlyNumber(obj)) {
            return true
        }
    }

    return false
}

//가입계약번호
function isContNoFormat(obj) {
	fld = getField(obj)
	var inputVal = getValue(fld);
	var inputStr = inputVal.toString();	

	for (var i = 0; i < inputStr.length; i++) {
		var oneChar = inputStr.charAt(i)

		if (oneChar < "0" || oneChar > "9") {
			dmbAlert("가입계약번호를 올바로 입력하십시오.")
			doSelection(fld);
			return false
		}
	}
	
   if(inputStr != "" ){
	   if(inputStr.length != 10 || inputStr < "1000000000" || inputStr >= "2000000000"){
			dmbAlert("가입계약번호를 올바로 입력하십시오.")
			doSelection(fld);
			return false;
		}else{
			return true;
		}
   }else{
			return true;
	}
}
//가입계약번호
function isContNo(obj) {
    if (isEssential(obj)) {
        if (isContNoFormat(obj)) {
            return true
        }
    }

    return false
}

//청구계정번호
function isAcntNoFormat(obj) {
    fld = getField(obj)
    var inputVal = getValue(fld);
    var inputStr = inputVal.toString();

	for (var i = 0; i < inputStr.length; i++) {
        var oneChar = inputStr.charAt(i)

        if (oneChar < "0" || oneChar > "9") {
            doSelection(fld)
            dmbAlert("청구계정번호를 올바로 입력하십시오.")
            return false
        }
    }

	if(inputStr != "" ){
		if(inputStr.length != 10 || inputStr < "2000000000" || inputStr >= "3000000000"){
			doSelection(fld);
			dmbAlert("청구계정번호를 올바로 입력하십시오.")
			return false;
		}else{
			return true;
		}
	}else{
			return true;
	}
    
}
//청구계정번호
function isAcntNo(obj) {
    if (isEssential(obj)) {
        if (isAcntNoFormat(obj)) {
            return true
        }
    }

    return false
}


//고객번호
function isCustNoFormat(obj) {
    fld = getField(obj)
    var inputVal = getValue(fld);
    var inputStr = inputVal.toString();

	for (var i = 0; i < inputStr.length; i++) {
        var oneChar = inputStr.charAt(i)

        if (oneChar < "0" || oneChar > "9") {
            doSelection(fld);
            dmbAlert("고객번호를 올바로 입력하십시오.")
            return false;
        }
    }

	if(inputStr != "" ){
		if(inputStr.length != 10 || inputStr < "9000000000" || inputStr > "9999999999"){
			doSelection(fld);
			dmbAlert("고객번호를 올바로 입력하십시오.")
			return false;
		}else{
			return true;
		}
	}else{
			return true;
    }
	
}
//고객번호
function isCustNo(obj) {
    if (isEssential(obj)) {
        if (isCustNoFormat(obj)) {
            return true
        }
    }

    return false
}

//김경수(2004-04-20).
function isCardFormat(obj){
    var fld = getField(obj)
    var inputVal = getValue(fld);
    var inputStr = inputVal.toString();
    
    if( inputVal==null || inputVal=='' ){
    	return true;
    }
    
    if( !isOnlyNumber(obj) ){
    	return false;
    }
    
	if( inputStr.length<14 || inputStr.length>16 ){
        doSelection(fld);
        dmbAlert("올바른 카드번호를 입력하십시오.");
        return false;
	}
	return true;
}

//김경수(2004-04-20).
function isEssCardFormat(obj) {
    if (isEssential(obj)) {
        if (isCardFormat(obj)) {
            return true
        }
    }
    return false
}

// 영문과 숫자의 조합인지 확인 (진헌규, 2004-04-23)
function isNumberAndEnglish(obj) {
	var fld = getField(obj);
    var inputVal = getValue(fld);
    
    // 영문과 숫자만 존재하는지 확인하기 위한 정규표현식
    var re = /^[0-9A-Za-z]*$/;
    if (re.test(inputVal)) {
        return true;
    } else {
        doSelection(fld);
        dmbAlert("영문과 숫자만 입력가능합니다");
        return false;
    }
}

// 영문과 숫자의 조합인지 확인 (진헌규, 2004-04-23)
function isEssNumberAndEnglish(obj) {
    if (isEssential(obj)) {
        if (isNumberAndEnglish(obj)) {
            return true
        }
    }
    return false
}

//==============<< Utility Format, Unformat Handlers >>========================
function format(obj) {
    var fld = getField(obj)
    if (typeof(fld.mytype) == "undefined") return

    switch (fld.mytype) {
        case "NUM":
        case "ESS_NUM":
            commafy(fld)
            break;
        case "DATE":
        case "ESS_DATE":
            appendDateDelim(fld)
	    break;
		// 김경수가 추가.//////////////
        case "YYYYMM":
        case "ESS_YYYYMM":
            appendYyyyMmDelim(fld)
            break;
		// 김경수가(0401) 추가.////////
        case "DATETIME":
        case "ESS_DATETIME":
            appendDateTimeDelim(fld)
            break;
		case "CONTNO":
        case "ESS_CONTNO":            
            break;
		case "ACNTNO":
        case "ESS_ACNTNO":            
            break;
		case "CUSTNO":
        case "ESS_CUSTNO":            
            break;
        // 김경수(2004-04-20)
        case "CARD":
        case "ESS_CARD":
            appendCardDelim(fld);
            break;
        case "NUMFORMAT":
            appendNUMDelim(fld)
	    break;
        ///////////////////////////////
        default:
            break;
    }
}

function unformat(obj) {
    var fld = getField(obj)
    if (typeof(fld.mytype) == "undefined")  return
    switch (fld.mytype) {
        case "NUM":
        case "ESS_NUM":
            decommafy(fld)
            break;
        case "DATE":
        case "ESS_DATE":
        case "YYYYMM":
        case "ESS_YYYYMM":
        case "DATETIME":
        case "ESS_DATETIME":
        //김경수(2004-04-20)
        case "CARD":
        case "ESS_CARD":
            removeDateDelim(fld)
            break;
		case "CONTNO":
        case "ESS_CONTNO":            
            break;
		case "ACNTNO":
        case "ESS_ACNTNO":            
            break;
		case "CUSTNO":
        case "ESS_CUSTNO":            
            break;
        case "NUMFORMAT":
            removeDateDelim(fld)
	    break;
        default:
            break;
    }
    //포커스가 왔을때
    gCachedVal = fld.value

    doSelection(fld)
}


function commafy(fld) {
    var inputVal = getValue(fld);
    var frontVal = getFront(inputVal, ".");
    var point = "."
    if (frontVal == null) {
        frontVal = inputVal
        point = ""
    }

    //김경수(2004-04-10)
    for(var i=0; i<frontVal.length; i++){
    	if(frontVal.length>1 && frontVal.substring(0,1)=="0"){
    		frontVal = frontVal.substring(1,frontVal.length);
    		i--;
    	}
    }

    var endVal = getEnd(inputVal, ".");
    if (endVal == null) {
        endVal = ""
    }

    var re = /(-?\d+)(\d{3})/
    while (re.test(frontVal)) {
        frontVal = frontVal.replace(re, "$1,$2");
    }

    setValue(fld, frontVal + point + endVal);
}

function decommafy(fld) {
    var inputVal = getValue(fld);

    var re = /,/g
    var resultVal = inputVal.replace(re,"")
    setValue(fld, resultVal)
}


function appendDateDelim(fld) {
    var input = getValue(fld)
    var re = /(\d{4})(\d{2})(\d{2})/
    if (re.test(input)) {
        setValue(fld, RegExp.$1 + gDateDelim + RegExp.$2 + gDateDelim + RegExp.$3)
    }
}

// 김경수가 추가.
function appendYyyyMmDelim(fld){
    var input = getValue(fld)
    var re = /(\d{4})(\d{2})/
    if (re.test(input)) {
        setValue(fld, RegExp.$1 + gDateDelim + RegExp.$2 )
    }
}

// 김경수(0401)
function appendDateTimeDelim(fld){
    var input = getValue(fld)
    var re = /(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})/
    if (re.test(input)) {
        setValue(fld, RegExp.$1 + gDateDelim + RegExp.$2+ gDateDelim + RegExp.$3 +" "+ RegExp.$4 + gTimeDelim + RegExp.$5 + gTimeDelim + RegExp.$6 )	
    }
}

function removeDateDelim(fld) {
        var inputVal = getValue(fld);
		//alert(inputVal)
        setValue(fld, removePoint(inputVal))
}

//김경수(0401) 수정.
function removePoint(inputVal) {
    var re1 = /\-/g
    var re2 = /\ /g
    var re3 = /\:/g
	var re4 = /\,/g
    var resultVal = (((inputVal.replace(re1,"")).replace(re2,"")).replace(re3,"")).replace(re4,"");	
    return resultVal
}

// 김경수(2004-04-20)
function appendCardDelim(fld){
    var input = getValue(fld)
    var re = /(\d{4})(\d{4})(\d{4})(\d{2,4})/

    if (re.test(input)) {
        setValue(fld, RegExp.$1 + gDateDelim + RegExp.$2+ gDateDelim + RegExp.$3 +gDateDelim+ RegExp.$4 );
    }
}
function appendNUMDelim(fld){
    var input = getValue(fld)
	var re = /(-?\d+)(\d{3})/;
    while (re.test(input)) {
        input = input.replace(re, "$1,$2");
    }

	setValue(fld,input);	
}

/*
function removePoint(inputVal) {
//    var re = /\./g
    var re = /\-/g
    var resultVal = inputVal.replace(re,"")
    return resultVal
}
*/

// separate function to accommodate IE timing problem
function doSelection(fld) {
    try {
        fld.focus()
    }
    catch (ex1) {
    }
    finally {
        try {
            fld.select()
        }
        catch (ex2) {
        }
    }
}

//=========================================== 다음 Control 반환 ===================================//
function comNext(obj)
{
     var fld = getField(obj);
     var fld_form = fld.form;
     for(var j = 0;j<fld_form.elements.length;j++){
         if(fld_form.elements[j].name == fld.name){
             return fld_form.elements[j+1];
             break;
         }
     }
}

//=========================================== 이전 Control 반환 ===================================//
function comPrev(obj)
{
     var fld = getField(obj);
     var fld_form = fld.form;
     for(var j = 0;j<fld_form.elements.length;j++){
         if(fld_form.elements[j].name == fld.name){
             return fld_form.elements[j-1];
             break;
         }
     }
}

//===========================================첫번째 Control 반환==================================//
function comFirst(doc) {
	//first elements 는 hidden 으로 cmd 명을 세팅 하기때문에 첫번째로 수정
    //return doc.forms[0].elements[0];
	return doc.forms[0].elements[1];
}


// ====================<< Initialize Form Element  >>===========================
//문서 문석 (문서 내의 모든 폼을 추적한다.)
function dmbOnLoad(doc) {
    var size = doc.forms.length;

    for (var i = 0; i < size; i++) {
        var eachForm = doc.forms[i]
        parseForm(eachForm);
    }
    ///bbosong update focus
    doSelection(comFirst(doc));
    return true;
}

//폼 구성요소 분석하여 real time validation check 설정
function parseForm(fom) {
    var size = fom.elements.length;
    for (var i = 0; i < size; i++) {
        var eachFld = fom.elements[i];
        format(eachFld)
        checkRealTimeValid(eachFld)
    }
}

//폼 구성요소의 onfocus, onchange, onblur 이벤트에 대한 핸들러 설정
function checkRealTimeValid(fld) {
    if (typeof(fld.mytype) == "undefined") {//is valid property?
        return false
    }
    // selectbox의 경우는 focus 처리를 하지 않음
    if (fld.type == "select-one") {
		return false
    }	

    fld.attachEvent("onfocus", unformat);
    fld.attachEvent("onblur", format);

    //소 뒷걸음치다 쥐잡 듯 발견한 방법
    //이 것으로 아래 길다란 switch 문이 제거되었다.
    //무척 기쁨. /^^
    fld.attachEvent("onchange", validDispatcher[fld.mytype])

/*
    switch (fld.mytype) {
        case "NUM":
            fld.attachEvent("onchange", validDispatcher["NUM"])//);validDispatcher["NUM"].doValidate() isNumber
    }
*/

    return false;
}

//이름이 같은 요소가 있으면 그 값을 복사한다.
function copyForm(srcForm, destForm) {

}

// 폼의 모든 구성요소에 대하여 포맷팅
function formatForm(myform) {
    var size = myform.elements.length;
    for (var i = 0; i < size; i++) {
        var eachElement = myform.elements[i];
        format(eachElement)
    }
}

// 폼의 모든 구성요소에 대하여 언포맷팅
function unformatForm(myform) {
    var size = myform.elements.length;
    for (var i = 0; i < size; i++) {
        var eachElement = myform.elements[i];
        unformat(eachElement)
    }
}



// =====<< Batch Validation Check on Form Submit >>
function isSubmit(myform) {
    //unformat

    var size = myform.elements.length;
    for (var i = 0; i < size; i++) {
        var eachElement = myform.elements[i];
        var eachVal = getValue(eachElement)
        eachVal = trim(eachVal)
        //if (eachVal == "") continue 필수입력항목인지 체크해야 할 수도 있으므로
        if(!checkBatchValid(eachElement)) {
            //doSelection(eachElement);
            return false
        }
        else {
            continue;
        }
    }

    return true;
}

//폼 전송시 유효성 일괄 체크
function checkBatchValid(obj) {
    var fld = obj;
    if (typeof(fld.mytype) == "undefined") {
        return true;
    }

    var myResult = true;

    if (fld.mytype.indexOf("ESS") == -1 && getValue(fld) == "") {
        return true;
    }

    myResult = validDispatcher[fld.mytype](obj)

    return myResult;
}

//===================<< Utility String Handlers >>=============================>
function ltrim(inputStr) {
    var re = /(\s*)/
    var matchArray = re.exec(inputStr);
    return RegExp.rightContext
}

function rtrim(inputStr) {
    var re = /(\s*)$/
    var matchArray = re.exec(inputStr);
    return RegExp.leftContext
}

function trim(inputStr) {
    var newStr = ltrim(inputStr);
    newStr = rtrim(newStr);
    return newStr
}

// extract front part of string prior to searchString
function getFront(mainStr,searchStr){
    foundOffset = mainStr.indexOf(searchStr)
    if (foundOffset == -1) {
        return null
    }
    return mainStr.substring(0,foundOffset)
}
// extract back end of string after searchString
function getEnd(mainStr,searchStr) {
    foundOffset = mainStr.indexOf(searchStr)
    if (foundOffset == -1) {
        return null
    }
    return mainStr.substring(foundOffset+searchStr.length,mainStr.length)
}
// insert insertString immediately before searchString
function insertString(mainStr,searchStr,insertStr) {
    var front = getFront(mainStr,searchStr)
    var end = getEnd(mainStr,searchStr)
    if (front != null && end != null) {
        return front + insertStr + searchStr + end
    }
    return null
}
// remove deleteString
function deleteString(mainStr,deleteStr) {
    return replaceString(mainStr,deleteStr,"")
}
// replace searchString with replaceString
function replaceString(mainStr,searchStr,replaceStr) {
    var front = getFront(mainStr,searchStr)
    var end = getEnd(mainStr,searchStr)
    if (front != null && end != null) {
        return front + replaceStr + end
    }
    return null
}

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// 식별번호 유효성 체크 추가(2004.03.15) by Kwak, Jae-Bong.
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

//---------------------------------------------------------------------
// 사업자등록번호 유효성 체크
//---------------------------------------------------------------------
function checkBizNum1(obj)
{
    var fld	 = getField(obj)
    var input = getValue(fld)
    var re = /\b(\d{2})(1[0-2]|0?[1-9])(0?[1-9]|[12][0-9]|3[01])/

	if(isNumber(fld)) {
        comNext(fld).focus();
        return true;
    }
    else {
        doSelection(fld);
        dmbAlert("사업자등록번호를 확인해 주십시요.");
        return false;
    }

}

function checkBizNum2(obj) {
	var para = getValue(comPrev(comPrev(getField(obj)))) + getValue(comPrev(getField(obj))) + getValue(getField(obj));

	var IDAdd = "137137135";
	var i = 0 ;
	var iDtot = 0 ;

	if(para.length != 10) {
		doSelection(comPrev(comPrev(getField(obj))));
		dmbAlert("사업자등록번호를 확인해 주십시요.");
		return (false);
	}

	for(i = 0; i < 9; i++) {
		iDtot = iDtot + (para.substr(i,1) * IDAdd.substr(i,1));
	}

	iDtot = iDtot + parseInt((para.substr(8,1) * 5) / 10);
	iDtot = (iDtot % 10);

	if (iDtot == 0) {
		iDtot = 0;
	} else {
		iDtot = (10 - iDtot);
	}

	if(iDtot != para.substr(9,1)) {
		doSelection(getField(obj));
		dmbAlert("사업자등록번호를 확인해 주십시요.");
		return (false);
	}
}

//---------------------------------------------------------------------
// 외국인등록번호 유효성 체크
//---------------------------------------------------------------------
function checkFrnNum1(obj)
{
    var fld	 = getField(obj)
    var input = getValue(fld)
    var re = /\b(\d{2})(1[0-2]|0?[1-9])(0?[1-9]|[12][0-9]|3[01])/

    if(re.test(input)) {
        comNext(fld).focus();
        return true;
    }
    else {
        doSelection(fld);
        dmbAlert("외국인등록번호를 확인해 주십시요.");
        return false;
    }

}

function checkFrnNum2(obj) {
	var para = getValue(comPrev(getField(obj))) + getValue(getField(obj));

	if(para.length != 13) {
		doSelection(getField(obj));
		dmbAlert("외국인등록번호를 확인해 주십시요.");
		return (false);
	}

    if((para.charAt(6) == "5") || (para.charAt(6) == "6")) {
		birthYear = "19";
	}
    else if((para.charAt(6) == "7") || (para.charAt(6) == "8")) {
		birthYear = "20";
	}
    else if((para.charAt(6) == "9") || (para.charAt(6) == "0")) {
		birthYear = "18";
	}
    else {
		doSelection(getField(obj));
		dmbAlert("외국인등록번호의 생년월일을 확인해 주십시요.");
		return (false);
	}

    birthYear += para.substr(0, 2);
    birthMonth = para.substr(2, 2) - 1;
    birthDate = para.substr(4, 2);
    birth = new Date(birthYear, birthMonth, birthDate);

    if(birth.getYear() % 100 != para.substr(0, 2)
		|| birth.getMonth() != birthMonth
		|| birth.getDate() != birthDate) {
		doSelection(getField(obj));
		dmbAlert("외국인등록번호의 생년월일을 확인해 주십시요.");
		return (false);
    }

	if(!checkFrnCheckSum(para)) {
		doSelection(getField(obj));
		dmbAlert("외국인등록번호를 확인해 주십시요.");
		return (false);
	}
}

function checkFrnCheckSum(para) {
	var IDAdd = "234567892345";
	var i=0 ;
    var iDtot=0 ;

	i = (para.substr(7,1) * 10) + para.substr(8,1)
	if((i % 2) != 0) {
		return (false);
	}

	if((eval(para.substr(11,1)) < 6) && (eval(para.substr(11,1)) > 9)) {
		return (false);
	}

	for(i=0 ; i<12 ; i++) {
		iDtot = iDtot + (para.substr(i,1) * IDAdd.substr(i,1));
	}

	iDtot = 11 - (iDtot % 11);

	if(iDtot >= 10) {
		iDtot = (iDtot - 8);
	}

    if(para.substr(12,1)== iDtot) {
    	return (true);
    }
	else {
    	return (false);
    }
}

//---------------------------------------------------------------------
// 법인번호 유효성 체크
//---------------------------------------------------------------------
function checkBinNum1(obj)
{
    var fld	 = getField(obj)
    var input = getValue(fld)
    var re = /\b(\d{2})(1[0-2]|0?[1-9])(0?[1-9]|[12][0-9]|3[01])/

    if(re.test(input)) {
        comNext(fld).focus();
        return true;
    }
    else {
        doSelection(fld);
        dmbAlert("법인번호를 확인해 주십시요.");
        return false;
    }

}

function checkBinNum2(obj) {
	var para = getValue(comPrev(getField(obj))) + getValue(getField(obj));

	var IDAdd = "121212121212";
    var i=0 ;
    var iDtot=0 ;

    if(para.length!= 13) {
		doSelection(getField(obj));
		dmbAlert("법인번호를 확인해 주십시요.");
		return(false);
	}

	/*
	if((para.length!= 13)
		|| (eval(para.substr(6,1)) != 0)) {
		doSelection(getField(obj));
		dmbAlert("법인번호를 확인해 주십시요.");
		return(false);
	}
	*/

	if((para.substr(0,1) == para.substr(1,2))
		&& (para.substr(1,2) == para.substr(2,3))
		&& (para.substr(2,3) == para.substr(3,4))
		&& (para.substr(3,4) == para.substr(4,5))
		&& (para.substr(4,5) == para.substr(5,6))
		&& (para.substr(5,6) == para.substr(6,7))
		&& (para.substr(6,7) == para.substr(7,8))
		&& (para.substr(7,8) == para.substr(8,9))
		&& (para.substr(8,9) == para.substr(9,10))
		&& (para.substr(9,10) == para.substr(10,11))
		&& (para.substr(10,11) == para.substr(11,12))
		&& (para.substr(11,12) == para.substr(12,13))) {
		doSelection(getField(obj));
		dmbAlert("법인번호를 확인해 주십시요.");
		return (false);
	}

	/*
	for(i=0 ; i<12 ; i++) {
		if(para.substr(i,1) == para.substr((i+1),1)) {
			doSelection(getField(obj));
			dmbAlert("법인번호를 확인해 주십시요.");
			return (false);
		}
	}
	*/

	for(i=0 ; i<12 ; i++) {
		iDtot = iDtot + (para.substr(i,1) * IDAdd.substr(i,1));
	}

	iDtot = (iDtot % 10);

	if (iDtot == 0) {
		iDtot = 0;
	} else {
		iDtot = (10 - iDtot);
	}

    if(para.substr(12,1) != iDtot) {
    	doSelection(getField(obj));
		dmbAlert("법인번호를 확인해 주십시요.");
		return (false);
    }
}

//===============<< 폼 제어 >>========================
//특정 document에 있는 모든 폼에 대해서 reset을 수행한다.
function reset(aDocument) {
	var size = aDocument.forms.length;

    for (var i = 0; i < size; i++) {
        var eachForm = document.forms[i]
        eachForm.reset();
    }
}

//특정 document에 있는 모든 폼의 이름을 값 필드에 보여준다.
//이 기능은 폼 구성요소의 이름을 전체적으로 알고 싶을 때 사용한다.
function copyName2Value(aDocument) {
	var sizeForm = aDocument.forms.length;

    for (var i = 0; i < sizeForm; i++) {
        var eachForm = aDocument.forms[i]
        var sizeElement = eachForm.elements.length;
		for (var j = 0; j < sizeElement; j++) {
			var eachFld = eachForm.elements[j];
			eachFld.value = eachFld.name;
		}
    }
}

//특정 폼의 모든 구성요소를 disable 시킨다.
//주의: 비활성화된 폼 구성요소는 전송되지 않는다.
function disableForm(myform) {
	var sizeElement = myform.elements.length;
	for (var j = 0; j < sizeElement; j++) {
		var eachFld = myform.elements[j];
		try {
			eachFld.disabled = true;
		}
		catch (ex) {
		}
	}
}


//특정 폼의 모든 구성요소를 enable 시킨다.
function enableForm(myform) {
	var sizeElement = myform.elements.length;
	for (var j = 0; j < sizeElement; j++) {
		var eachFld = myform.elements[j];
		try {
			eachFld.disabled = false;
		}
		catch (ex) {
		}
	}
}

//특정  폼의 구성요소 값이 mytype에 따른 포맷팅에 확신이 없을 때 호출하면 포맷팅이 됨.
function refresh(myform) {
	alert(myform);
	unformatForm(myform);
	formatForm(myform);
}

//---------------------------------------------------------------------------------
// Input Form Length Check (2004-04-27)
//
// obj		: Form Object
// name		: Form Name
// limit	: Max Length
//
// example)
// <input type="text" name="name" onblur="fromLengthChecker(this, '고객명', 40)">
//---------------------------------------------------------------------------------
function fromLengthChecker(obj, name, limit) {
	var fld = getField(obj);
	var str = obj.value;
	var count = 0;
	var len = str.length;

	for(k = 0; k < len; k++){
		temp = str.charAt(k);
	
		if(escape(str.charAt(k)).length > 4) {
			count += 2;
		}
		else {
			count++;
		}
	}

	if(eval(count) > eval(limit)) {
		alert(name+"값은 "+limit+"자리를 초과할 수 없습니다.");
		doSelection(fld);
	}
}

//----------------------------------------------------
// String Length Check (2004-04-27)
//
// str		: String Value
//
// return	: count(String Length)
//----------------------------------------------------
function stringLengthChecker(str) {
	var count = 0;
	var len = str.length;

	for(k = 0; k < len; k++){
		temp = str.charAt(k);
	
		if(escape(str.charAt(k)).length > 4) {
			count += 2;
		}
		else {
			count++;
		}
	}

	return count;
}

//------------------------------------------------------------------------------
// Only Number Key Press (2004-04-27)
//
// Using	: <input type="text" name="fldname" onkeypress="onlyNumKeyPress();">
//------------------------------------------------------------------------------
function onlyNumKeyPress() {
	if((event.keyCode < 48) || (event.keyCode > 57)) {
		event.returnValue = false;
	}
	return true;
}