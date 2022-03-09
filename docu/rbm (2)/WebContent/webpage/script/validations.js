/**
 * ���ϸ�: validations.js
 * �ۼ���: ������ (nucha@dreamwiz.com)
 *            ����� (superbbosong@nate.com)
 * �ۼ���: 2003/11/20/�����
 *
 * ���ο� mytype�� ���� ��� ó������
 * 1. �⺻ Ÿ���̸� �Լ��� �����. ��) isNumber(�������� �Ǵ�), isEssential(�ʼ��Է��׸����� �Ǵ�)
 *    ����Ÿ���̸� �⺻ �Լ��� �̿��Ѵ�. ��) isEssNum(�ʼ��Է��׸��̸鼭 �����ʵ����� �Ǵ�)
 * 2. validDispatcher �迭�� �ʱ�ȭ�Ѵ�.
 * 3. ���� Ÿ�Կ� ���Ͽ� ������, ���������� �����Ѵ�.
 *
 * ���ǻ���: mytype=""�� ���� �ڵ�� �����ϱ� �ٶ���. ���ϴ� �ʵ� Ÿ���� ��Ȯ�� �����ؾ� �Ѵ�.
 *
 * @version 1.0.2a, 2004/04/27 - �� ������ �� ������� Ȱ��ȭ/��Ȱ��ȭ �ϴ� enableForm(myform), disableForm(myform) �߰� (������)
 *                                          - Ư�� �� refresh�ϴ� refresh(myform) �Լ� �߰�#�������� �ȵ� ��Ұ� ������ �ٽ� �������� �����.
 * @version 1.0.2,  2004/04/23 - ������ ���ڸ� �����ϴ� Ÿ�� �߰�(NUM_ENG or ENG_NUM)
 * @version 1.0.1a, 2004/04/20 - select box�� ��� checkRealTimeValid() �Լ��� Ÿ�� �ʵ��� ����.
 *                               (focus/unfocus �������� select box�� �����̴� ���� ������)
 * @version 1.0.1,  2004/04/14 - getValueOnly(obj) �Լ� �߰� (����� hkjin@daou.co.kr) 
 * @version 1.0.0d, 2004/04/13 - getValue(obj) ���ο� doSelection(fld); �� �߰�#���� ������ ��, ���� ��� ����.
 *                                          - undoSelection(fld) �Լ� �߰�.
 *                                          - ���ѷ��� �߻��Ѵٴ� ���� ���� �ϴ� ���� ��� ��� ���� ����.
 * @version 1.0.0c, 2004/04/11 - '9999/12/31' �����ϵ��� isDateFormat() �Լ� ����. �����(hkjin@daou.co.kr)
 * @version 1.0.0b, 2004/04/07 - reset(aDocument) �Լ� �߰�#document ���� ��� ���� �ʱ�ȭ��. (������)
 *                                            copyName2Value(aDocument) �Լ� �߰�#Ư�� ���� ���� �� ������� �̸��� �� �ʵ忡 �����ش�. (������)
 * @version 1.0.0a, 2004/03/26 - select-multiple Ÿ�Կ� ���� getValue() ����, �������� gDELIM ���� (������)
 * @version 1.0.0a, 2004/03/26 - isEssential()�� fld.desc �Ӽ��� �̿��� ��� �޽��� ���� (������)
 * @version 1.0.0, 2004/03/10
 */


//					<< �������� ���� >>
//form element�� �ڽ��� ���� form ������ �������� frame ������ ������ ���� �ʴ�.
//frame�� form element�� ���� ���� ���ϹǷ� �����Ͽ� �����ؾ� �Ѵ�.

var gDELIM = "`";
var gFrame; //��ȿ�� üũ�� ���ϴ� form�� �����ϴ� frame reference
var gField; //reference to the calling form element
var gMyType;
var gDateDelim = "-"

var gTimeDelim = ":" //����(0401).

var gCachedVal = null
var gIsEvent = false //update �ʿ��ұ�?

var gDevelopmentMode = true;

var validDispatcher = new Array();

//�⺻Ÿ��
validDispatcher["NUM"] = isNumber //�����ΰ�?
validDispatcher["NUMFORMAT"] = isNumberFormat //���������ΰ�?
validDispatcher["EMAIL"] = isEmail //email�ΰ�?
validDispatcher["TEL"] = isTel //��ȭ��ȣ�ΰ�?
validDispatcher["ESS"] = isEssential //�ʼ��Է��׸��ΰ�?
validDispatcher["DATE"] = isDateFormat //��¥�ΰ�?
validDispatcher["YYYYMM"] = isYyyyMmFormat //YYYYMM (����)
validDispatcher["ONLYNUM"] = isOnlyNumber //���� ���ڸ� ����(����)
validDispatcher["DATETIME"] = isDateTimeFormat //��¥�ú��� ����(0401).
validDispatcher["CONTNO"] = isContNoFormat //���԰���ȣ ���ʼ�(2004-04-02).
validDispatcher["ACNTNO"] = isAcntNoFormat //û��������ȣ ���ʼ�(2004-04-02).
validDispatcher["CUSTNO"] = isCustNoFormat //����ȣ ���ʼ�(2004-04-02).
validDispatcher["NUMENG"] = isNumberAndEnglish // ���ڿ� �������� �����ΰ�?
validDispatcher["ENGNUM"] = isNumberAndEnglish // ���ڿ� �������� �����ΰ�?

//�ʼ�Ÿ��
validDispatcher["ESS_NUM"] = isEssNum
validDispatcher["ESS_TEL"] = isEssTel
validDispatcher["ESS_DATE"] = isEssDate
validDispatcher["ESS_YYYYMM"] = isEssYyyyMm //(����)
validDispatcher["ESS_DATETIME"] = isEssDateTime //����(0401).
validDispatcher["ESS_ONLYNUM"] = isEssOnlyNum //(����)
validDispatcher["ESS_CONTNO"] = isContNo //���԰���ȣ ���ʼ�(2004-04-02).
validDispatcher["ESS_ACNTNO"] = isAcntNo //û��������ȣ ���ʼ�(2004-04-02).
validDispatcher["ESS_CUSTNO"] = isCustNo //����ȣ ���ʼ�(2004-04-02).
validDispatcher["ESS_NUMENG"] = isEssNumberAndEnglish // ���ڿ� �������� �����ΰ�?
validDispatcher["ESS_ENGNUM"] = isEssNumberAndEnglish // ���ڿ� �������� �����ΰ�?


validDispatcher["RRN1"] = isRRN1
validDispatcher["RRN2"] = isRRN2

//����Ÿ�� �߰�(2004.03.15)
validDispatcher["BIZ_NUM_01"] = checkBizNum1
validDispatcher["BIZ_NUM_02"] = checkBizNum2
validDispatcher["FRN_NUM_01"] = checkFrnNum1
validDispatcher["FRN_NUM_02"] = checkFrnNum2
validDispatcher["BIN_NUM_01"] = checkBinNum1
validDispatcher["BIN_NUM_02"] = checkBinNum2

/*
validDispatcher["RRN"] = //�ֹε�Ϲ�ȣ�ΰ�?
*/

/*
validDispatcher["PNUM"] = new dispatcher(isPNUM)//Positive Number
validDispatcher["NNUM"] = new dispatcher(isNNUM)//Negative Number
validDispatcher["INT"] = new dispatcher(isINT)//Integer
validDispatcher["PINT"] = new dispatcher(isPINT)//Positive Integer
validDispatcher["NINT"] = new dispatcher(isNINT)//Negative Integer
validDispatcher["ENG"] = new dispatcher(isENG)//English
validDispatcher["EMAIL"] = new dispatcher(isEMAIL)//English
validDispatcher["HAN"] = new dispatcher(isHAN)//�ѱ�
validDispatcher["DATE"] = new dispatcher(isDATE)//Date(2003.11.20)
validDispatcher["TIME"] = new dispatcher(isTIME)//Time(13:20:01)
validDispatcher["DATE_TIME"] = new dispatcher(isDATE_TIME)//Date+Time
validDispatcher["ENG_NUM"] = new dispatcher(isNUM_ENG)//Number+English
//������ ������� ����?

validDispatcher["ZIP"] = new dispatcher(isZip)
*/

validDispatcher["CARD"] = isCardFormat //ī���ȣ ����(2004-04-20).
validDispatcher["ESS_CARD"] = isEssCardFormat ////ī���ȣ ����(2004-04-20).


function dmbAlert(aMsg) {
    if (gDevelopmentMode) {
		alert(aMsg);
    }
}

function viewObj(obj) {
    var myWin = window.open("", "", "");
    myWin.status = "��ü ������ ����� ���� �ð��� �ɸ� �� �ֽ��ϴ�."

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
    //myWin.status = "��ü���� ���� �Ϸ�. from ���� DMB ������Ʈ";
	myWin.status = "��ü���� ���� �Ϸ�. from KYERYONG";
}

//================= << Utility Data Set, Get Handlers >>========================
//�ʵ忡 ���� �����Ѵ�.
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

//�ʵ��� ���� ����.
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
            dmbAlert("select-multiple Ÿ�Կ� ���� getValue�� �����Ǿ� ���� �ʽ��ϴ�.\n�ʿ��ϴٸ� ����������� �������ּ���.")
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
 * �ʵ带 �Է¹޾� ������ ������ ���� �����ش�.
 * unformat() �Լ��� �״�� �̿��ϱ� ���� ���� field�� ���� �ٸ� ������ �����ϰ�,
 * �� ������ unformat() �Լ��� �ѱ��.
 * 
 * @author �����(hkjin@daou.co.kr)
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
 * getValueOnly() �Լ����� �̿��ϴ� �Լ�.
 * �μ��� �־��� field�� �Ӽ��� ���� �ٸ� ������ �����Ѵ�.
 *
 * @author �����(hkjin@daou.co.kr)
 */
function createFieldClone(obj) {
	this.type = obj.type;
	this.value = getValue(obj);
	this.mytype = obj.mytype;
	return this;
}

//������Ʈ�� ���� �����Ѵ�.
//������Ʈ�� Ÿ���� select-multiple�� ��쿡�� ���迭�� �����Ѵ�.
//obj: event ��ü�̰ų� �� �������
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
        dmbAlert("���ڸ� �Է��Ͻʽÿ�.")
        return false;
    }
    
    if( !isMatchNumberFormat(obj) ){
        return false;
    }
    
    return true
}

//����(2004-04-10)
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

//����(2004-04-10)
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
            dmbAlert("�Ҽ������� �� ������\n\n"+ endType +" ° �ڸ� ������ �Է� �����մϴ�.");
            return false;
        }
    }else if( endType=='' || endType*1==0 ){
        if( endVal.length > 0 || frontType*1 < frontVal.length ){
            doSelection(fld);
            dmbAlert("���������� �� ������\n\n"+ frontType +" ° �ڸ� ������ �Է� �����մϴ�.");
            return false;
        }    
    }else if( frontType*1 < frontVal.length || endType*1 < endVal.length ){
        doSelection(fld);
        dmbAlert("���� : "+ frontType +" �ڸ�\n\n"+"�Ҽ� : "+ endType +" �ڸ�\n\n" + "���� �Է� �����մϴ�.");
        return false;
    }
    
	return true;
}
//=============================�ֹε�Ϲ�ȣ ���ڸ� 6�ڸ�=============================================//
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
        dmbAlert("�ֹε�Ϲ�ȣ�� Ȯ���Ͽ��ּ���");
        getField(obj).value="";
        return false;
    }

}
//==========================�ֹε�Ϲ�ȣ ���ڸ� 7�ڸ� + �ֹε�� check============================================///
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
        dmbAlert("�ֹε�Ϲ�ȣ�� Ȯ���Ͽ��ּ���");
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
    dmbAlert("��ȿ�� �̸��� �ּҸ� �Է��Ͻʽÿ�.\n�Է¿�(chanwoo@nucha.net)");
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
    dmbAlert("��ȭ��ȣ �Է� �����Դϴ�.\n�Է¿�: 123-4567-->1234567");
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
		dmbAlert(fld.desc + "��(��) �ʼ��Է��׸��Դϴ�.")
    } else {
        dmbAlert(fld.name + "��(��) �ʼ��Է��׸��Դϴ�.")
    }
    return false
}


// ������ �߰���.
function isYyyyMmFormat(obj) {
    var fld = getField(obj)
    var input = getValue(fld)	
	
    if( input =='' ) return true;
	
    var re = /\b((19|20)\d{2})(1[0-2]|0[1-9])\b/

    if (!re.test(input)) {
        doSelection(fld)
        dmbAlert("��¥ �Է��� �߸��Ǿ����ϴ�.\n�Է¿�: 200512");
        return false;
    }

    var year	= input.substring(0,4);
    var month	= input.substring(4,6);

    if(month > 12 || month < 1 )
    {
        doSelection(fld)		
        dmbAlert("��¥ �Է��� �߸��Ǿ����ϴ�.\n�Է¿�: 200511");
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

    //update: 13���� �Է��� �����
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
        dmbAlert("��¥ �Է��� �߸��Ǿ����ϴ�.\n�Է¿�: 20051225");
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
        dmbAlert("��¥ �Է��� �߸��Ǿ����ϴ�.\n�Է¿�: 20051125");
        return false;
    }

    return true;
}

// ����(0401).
function isDateTimeFormat(obj) {
    var fld = getField(obj)
    var input = getValue(fld)	
	
	if( input =='' ) return true;	// ����.
	
    var re = /\b((19|20)\d{2})(1[0-2]|0[1-9])(0[1-9]|1[0-9]|2[0-9]|3[0-1])(0[0-9]|1[0-9]|2[0-3])(0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])(0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])\b/

    if (!re.test(input)) {
        doSelection(fld)
        dmbAlert("��¥ �Է��� �߸��Ǿ����ϴ�.\n�Է¿�: 20040101000000");
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
        dmbAlert("��¥ �Է��� �߸��Ǿ����ϴ�.\n�Է¿�: 20040101000001");
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


//������ �߰�.
function isEssYyyyMm(obj) {
    if (isEssential(obj)) {
        if (isYyyyMmFormat(obj)) {
            return true
        }
    }

    return false
}

// ����(0401).
function isEssDateTime(obj) {
    if (isEssential(obj)) {
        if (isDateTimeFormat(obj)) {
            return true
        }
    }

    return false
}


//========================<< ���� ���ڸ� �Է°���(����). >>================================
function isOnlyNumber(obj) {
    fld = getField(obj)
    var inputVal = getValue(fld);
    var inputStr = inputVal.toString();

    for (var i = 0; i < inputStr.length; i++) {
        var oneChar = inputStr.charAt(i)

        if (oneChar < "0" || oneChar > "9") {
            doSelection(fld)
            dmbAlert("���ڸ� �Է��Ͻʽÿ�.")
            return false
        }
    }
    return true
}

// ����.
function isEssOnlyNum(obj) {
    if (isEssential(obj)) {
        if (isOnlyNumber(obj)) {
            return true
        }
    }

    return false
}

//���԰���ȣ
function isContNoFormat(obj) {
	fld = getField(obj)
	var inputVal = getValue(fld);
	var inputStr = inputVal.toString();	

	for (var i = 0; i < inputStr.length; i++) {
		var oneChar = inputStr.charAt(i)

		if (oneChar < "0" || oneChar > "9") {
			dmbAlert("���԰���ȣ�� �ùٷ� �Է��Ͻʽÿ�.")
			doSelection(fld);
			return false
		}
	}
	
   if(inputStr != "" ){
	   if(inputStr.length != 10 || inputStr < "1000000000" || inputStr >= "2000000000"){
			dmbAlert("���԰���ȣ�� �ùٷ� �Է��Ͻʽÿ�.")
			doSelection(fld);
			return false;
		}else{
			return true;
		}
   }else{
			return true;
	}
}
//���԰���ȣ
function isContNo(obj) {
    if (isEssential(obj)) {
        if (isContNoFormat(obj)) {
            return true
        }
    }

    return false
}

//û��������ȣ
function isAcntNoFormat(obj) {
    fld = getField(obj)
    var inputVal = getValue(fld);
    var inputStr = inputVal.toString();

	for (var i = 0; i < inputStr.length; i++) {
        var oneChar = inputStr.charAt(i)

        if (oneChar < "0" || oneChar > "9") {
            doSelection(fld)
            dmbAlert("û��������ȣ�� �ùٷ� �Է��Ͻʽÿ�.")
            return false
        }
    }

	if(inputStr != "" ){
		if(inputStr.length != 10 || inputStr < "2000000000" || inputStr >= "3000000000"){
			doSelection(fld);
			dmbAlert("û��������ȣ�� �ùٷ� �Է��Ͻʽÿ�.")
			return false;
		}else{
			return true;
		}
	}else{
			return true;
	}
    
}
//û��������ȣ
function isAcntNo(obj) {
    if (isEssential(obj)) {
        if (isAcntNoFormat(obj)) {
            return true
        }
    }

    return false
}


//����ȣ
function isCustNoFormat(obj) {
    fld = getField(obj)
    var inputVal = getValue(fld);
    var inputStr = inputVal.toString();

	for (var i = 0; i < inputStr.length; i++) {
        var oneChar = inputStr.charAt(i)

        if (oneChar < "0" || oneChar > "9") {
            doSelection(fld);
            dmbAlert("����ȣ�� �ùٷ� �Է��Ͻʽÿ�.")
            return false;
        }
    }

	if(inputStr != "" ){
		if(inputStr.length != 10 || inputStr < "9000000000" || inputStr > "9999999999"){
			doSelection(fld);
			dmbAlert("����ȣ�� �ùٷ� �Է��Ͻʽÿ�.")
			return false;
		}else{
			return true;
		}
	}else{
			return true;
    }
	
}
//����ȣ
function isCustNo(obj) {
    if (isEssential(obj)) {
        if (isCustNoFormat(obj)) {
            return true
        }
    }

    return false
}

//����(2004-04-20).
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
        dmbAlert("�ùٸ� ī���ȣ�� �Է��Ͻʽÿ�.");
        return false;
	}
	return true;
}

//����(2004-04-20).
function isEssCardFormat(obj) {
    if (isEssential(obj)) {
        if (isCardFormat(obj)) {
            return true
        }
    }
    return false
}

// ������ ������ �������� Ȯ�� (�����, 2004-04-23)
function isNumberAndEnglish(obj) {
	var fld = getField(obj);
    var inputVal = getValue(fld);
    
    // ������ ���ڸ� �����ϴ��� Ȯ���ϱ� ���� ����ǥ����
    var re = /^[0-9A-Za-z]*$/;
    if (re.test(inputVal)) {
        return true;
    } else {
        doSelection(fld);
        dmbAlert("������ ���ڸ� �Է°����մϴ�");
        return false;
    }
}

// ������ ������ �������� Ȯ�� (�����, 2004-04-23)
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
		// ������ �߰�.//////////////
        case "YYYYMM":
        case "ESS_YYYYMM":
            appendYyyyMmDelim(fld)
            break;
		// ������(0401) �߰�.////////
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
        // ����(2004-04-20)
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
        //����(2004-04-20)
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
    //��Ŀ���� ������
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

    //����(2004-04-10)
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

// ������ �߰�.
function appendYyyyMmDelim(fld){
    var input = getValue(fld)
    var re = /(\d{4})(\d{2})/
    if (re.test(input)) {
        setValue(fld, RegExp.$1 + gDateDelim + RegExp.$2 )
    }
}

// ����(0401)
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

//����(0401) ����.
function removePoint(inputVal) {
    var re1 = /\-/g
    var re2 = /\ /g
    var re3 = /\:/g
	var re4 = /\,/g
    var resultVal = (((inputVal.replace(re1,"")).replace(re2,"")).replace(re3,"")).replace(re4,"");	
    return resultVal
}

// ����(2004-04-20)
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

//=========================================== ���� Control ��ȯ ===================================//
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

//=========================================== ���� Control ��ȯ ===================================//
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

//===========================================ù��° Control ��ȯ==================================//
function comFirst(doc) {
	//first elements �� hidden ���� cmd ���� ���� �ϱ⶧���� ù��°�� ����
    //return doc.forms[0].elements[0];
	return doc.forms[0].elements[1];
}


// ====================<< Initialize Form Element  >>===========================
//���� ���� (���� ���� ��� ���� �����Ѵ�.)
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

//�� ������� �м��Ͽ� real time validation check ����
function parseForm(fom) {
    var size = fom.elements.length;
    for (var i = 0; i < size; i++) {
        var eachFld = fom.elements[i];
        format(eachFld)
        checkRealTimeValid(eachFld)
    }
}

//�� ��������� onfocus, onchange, onblur �̺�Ʈ�� ���� �ڵ鷯 ����
function checkRealTimeValid(fld) {
    if (typeof(fld.mytype) == "undefined") {//is valid property?
        return false
    }
    // selectbox�� ���� focus ó���� ���� ����
    if (fld.type == "select-one") {
		return false
    }	

    fld.attachEvent("onfocus", unformat);
    fld.attachEvent("onblur", format);

    //�� �ް���ġ�� ���� �� �߰��� ���
    //�� ������ �Ʒ� ��ٶ� switch ���� ���ŵǾ���.
    //��ô ���. /^^
    fld.attachEvent("onchange", validDispatcher[fld.mytype])

/*
    switch (fld.mytype) {
        case "NUM":
            fld.attachEvent("onchange", validDispatcher["NUM"])//);validDispatcher["NUM"].doValidate() isNumber
    }
*/

    return false;
}

//�̸��� ���� ��Ұ� ������ �� ���� �����Ѵ�.
function copyForm(srcForm, destForm) {

}

// ���� ��� ������ҿ� ���Ͽ� ������
function formatForm(myform) {
    var size = myform.elements.length;
    for (var i = 0; i < size; i++) {
        var eachElement = myform.elements[i];
        format(eachElement)
    }
}

// ���� ��� ������ҿ� ���Ͽ� ��������
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
        //if (eachVal == "") continue �ʼ��Է��׸����� üũ�ؾ� �� ���� �����Ƿ�
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

//�� ���۽� ��ȿ�� �ϰ� üũ
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
// �ĺ���ȣ ��ȿ�� üũ �߰�(2004.03.15) by Kwak, Jae-Bong.
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

//---------------------------------------------------------------------
// ����ڵ�Ϲ�ȣ ��ȿ�� üũ
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
        dmbAlert("����ڵ�Ϲ�ȣ�� Ȯ���� �ֽʽÿ�.");
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
		dmbAlert("����ڵ�Ϲ�ȣ�� Ȯ���� �ֽʽÿ�.");
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
		dmbAlert("����ڵ�Ϲ�ȣ�� Ȯ���� �ֽʽÿ�.");
		return (false);
	}
}

//---------------------------------------------------------------------
// �ܱ��ε�Ϲ�ȣ ��ȿ�� üũ
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
        dmbAlert("�ܱ��ε�Ϲ�ȣ�� Ȯ���� �ֽʽÿ�.");
        return false;
    }

}

function checkFrnNum2(obj) {
	var para = getValue(comPrev(getField(obj))) + getValue(getField(obj));

	if(para.length != 13) {
		doSelection(getField(obj));
		dmbAlert("�ܱ��ε�Ϲ�ȣ�� Ȯ���� �ֽʽÿ�.");
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
		dmbAlert("�ܱ��ε�Ϲ�ȣ�� ��������� Ȯ���� �ֽʽÿ�.");
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
		dmbAlert("�ܱ��ε�Ϲ�ȣ�� ��������� Ȯ���� �ֽʽÿ�.");
		return (false);
    }

	if(!checkFrnCheckSum(para)) {
		doSelection(getField(obj));
		dmbAlert("�ܱ��ε�Ϲ�ȣ�� Ȯ���� �ֽʽÿ�.");
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
// ���ι�ȣ ��ȿ�� üũ
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
        dmbAlert("���ι�ȣ�� Ȯ���� �ֽʽÿ�.");
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
		dmbAlert("���ι�ȣ�� Ȯ���� �ֽʽÿ�.");
		return(false);
	}

	/*
	if((para.length!= 13)
		|| (eval(para.substr(6,1)) != 0)) {
		doSelection(getField(obj));
		dmbAlert("���ι�ȣ�� Ȯ���� �ֽʽÿ�.");
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
		dmbAlert("���ι�ȣ�� Ȯ���� �ֽʽÿ�.");
		return (false);
	}

	/*
	for(i=0 ; i<12 ; i++) {
		if(para.substr(i,1) == para.substr((i+1),1)) {
			doSelection(getField(obj));
			dmbAlert("���ι�ȣ�� Ȯ���� �ֽʽÿ�.");
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
		dmbAlert("���ι�ȣ�� Ȯ���� �ֽʽÿ�.");
		return (false);
    }
}

//===============<< �� ���� >>========================
//Ư�� document�� �ִ� ��� ���� ���ؼ� reset�� �����Ѵ�.
function reset(aDocument) {
	var size = aDocument.forms.length;

    for (var i = 0; i < size; i++) {
        var eachForm = document.forms[i]
        eachForm.reset();
    }
}

//Ư�� document�� �ִ� ��� ���� �̸��� �� �ʵ忡 �����ش�.
//�� ����� �� ��������� �̸��� ��ü������ �˰� ���� �� ����Ѵ�.
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

//Ư�� ���� ��� ������Ҹ� disable ��Ų��.
//����: ��Ȱ��ȭ�� �� ������Ҵ� ���۵��� �ʴ´�.
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


//Ư�� ���� ��� ������Ҹ� enable ��Ų��.
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

//Ư��  ���� ������� ���� mytype�� ���� �����ÿ� Ȯ���� ���� �� ȣ���ϸ� �������� ��.
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
// <input type="text" name="name" onblur="fromLengthChecker(this, '����', 40)">
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
		alert(name+"���� "+limit+"�ڸ��� �ʰ��� �� �����ϴ�.");
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