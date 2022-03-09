/**
 * ȭ�鿡�� ���������� ���Ǵ� javascript �Լ��� ������ ����.
 *********************************************************
 * ����: ������� ������ ������ FTP �۾��� �������ֽʽÿ�.
 *********************************************************
 * ���� ����ϴ� ����� �����˾� ó�� �� alert, confirm ó��
 *
 */

function showErrorPopup(params) {
	alert(params);
	var errorPopup = window.open('/errorPopup.jsp?' + params, 'errorPopup', 'width=540,height=260,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no');
	errorPopup.focus();
}

/**
 * ȭ�鿡 alert ���·� �ѷ��� �޽����� �����ϴ� �迭.
 * alert�� �ܹ����� ������ �޽����̹Ƿ� ���������� �ۼ��Ѵ�.
 *
 * (�ʿ��Ͻ� ���� �Ʒ��ʿ� ���ο� �޽����� �߰����ֽñ� �ٶ��ϴ�.
 * �޽����� key�� ���� + �ĺ������� ������ ���ڿ��� ���ֽñ���)
 */
var alertMsg = new Array();
alertMsg['com_no_result'] = '��ȸ����� �����ϴ�.';
alertMsg['com_success_delete'] = '�����Ͻ� ������ ���������� �����Ǿ����ϴ�.';
alertMsg['com_success_save'] = '�Է��Ͻ� ������ ���������� ����Ǿ����ϴ�.';
/**
 * showAlert('com_no_result')�� ���� ���·� ȣ��.
 *
 * @param msg_id	alert â���� ������ �޽����� ID
 * @param msg_str	����޽����� �Բ� ǥ���ϴ� �ΰ�����
 */
function showAlert(msg_id, msg_str) {
	var msg = alertMsg[msg_id];
	if (msg != '') {
		alert(msg);
	}
}

/**
 * showAlertMessage('com_no_result', '�������� ����')�� ���� ���·� ȣ��.
 *
 * @param msg_id	alert â���� ������ �޽����� ID
 * @param msg_str	����޽����� �Բ� ǥ���ϴ� �ΰ�����
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
 * ȭ�鿡 confirm ���·� �ѷ��� �޽����� �����ϴ� �迭.
 * confirm�� yes/no�� ���� �����̹Ƿ� ���������� �ۼ��Ѵ�.
 * 
 * (�ʿ��Ͻ� ���� �Ʒ��ʿ� ���ο� �޽����� �߰����ֽñ� �ٶ��ϴ�.
 * �޽����� key�� ���� + �ĺ������� ������ ���ڿ��� ���ֽñ���)
 */
var confirmMsg = new Array();
confirmMsg['com_confirm_new'] = '�۾����� ������ ��� �սǵ˴ϴ�. �ʱ�ȭ�Ͻðڽ��ϱ�?';
confirmMsg['com_confirm_delete'] = '�����Ͻ� ������ �����Ǿ� ������ �� ���Ե˴ϴ�. �����Ͻðڽ��ϱ�?';
confirmMsg['com_confirm_save'] = '�Է��Ͻ� ������ ����˴ϴ�. �����Ͻðڽ��ϱ�?';

/**
 * ȭ�鿡 confirm ���·� �޽����� ����, 'Ȯ��'�� ������ ���
 * ������ javascript �Լ��� ��������ִ� �Լ�.
 * �Լ����� ���� ��� confirm ����� �ܼ��� return�ϰ�, �Լ����� ���� ���
 * confirm ����� ���� �ش� �Լ��� ȣ�����ش�.
 * showAlert('com_confirm_new', 'alert', '�ȳ��Ͻʴϱ�')�� ���� ���·� ȣ��.
 *
 * @param msg_id 		confirMsg �迭�� index
 * @param functionStr	'Ȯ��'�� ������ ��� �����ϰ��� �ϴ� javascript �Լ��� �̸�
 * @param args			functionName �Լ����� ���޵� �μ�
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
 * ȭ�鿡 confirm ���·� �޽����� ����, 'Ȯ��'�� ������ ���
 * ������ javascript �Լ��� ��������ִ� �Լ�.
 * �Լ����� ���� ��� confirm ����� �ܼ��� return�ϰ�, �Լ����� ���� ���
 * confirm ����� ���� �ش� �Լ��� ȣ�����ش�.
 * showAlert('com_confirm_new', 'alert', '�ȳ��Ͻʴϱ�')�� ���� ���·� ȣ��.
 *
 * @param msg_id 		confirMsg �迭�� index
 * @param msg_str		����޽����� ���ٿ� ����Ϸ��� �޽���
 * @param functionStr	'Ȯ��'�� ������ ��� �����ϰ��� �ϴ� javascript �Լ��� �̸�
 * @param args			functionName �Լ����� ���޵� �μ�
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
 * Text�� Textarea�� ������ element���� �ԷµǴ� backspace�� �����Ѵ�.
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


///////////////�ѱ��� ���̸� üũ/////////////////////
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
           if ((ch == "\n") || ((ch >= "��") &&  (ch <= "��")) || ((ch >="��") &&  (ch <="��")))
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
/////////////////���� ó�� trim()///////////////////////
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
/////////////////////////////SELECT �ڽ��� �ؽ�Ʈ ���� ��ġ�ϸ� ���� ///////////////////////////////////////
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
////////////////üũ�� �׸��� �ִ��� �˻�///////////////////////////////////////////////////////////////////////////
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
//////////////////////////////////////////// �ڵ� �̵�/////////////////////////////////////////////////////////////////////////
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
// �ѱ��� üũ�ϴ� �Լ�
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
/*==============================WINDOW�˾�=================================
 �Ķ��Ÿ ���۽� target���� ��ñ� �ٶ��ϴ�.
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
[Descript] �� ��¥�� ���̸� return �Ѵ�
sDate > eDate �� sDate >= eDate�� ������ (Nerb)
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
		alert("�������� ������ ���� Ů�ϴ�.. ������ : " + sDate + " ������ : " + eDate);
		return -1;
	}

	sDate.setYear = eDate.getYear;
	var daysAfter = (eDate.getTime() - sDate.getTime()) / (1000*60*60*24);
	daysAfter = Math.round(daysAfter);

	return (daysAfter+1);
}


/*====================================================================
[Descript] �� ���� ���̸� return �Ѵ� 
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
[Descript] ������ȣ�� �ùٸ��� üũ�Ѵ�.
====================================================================*/
function isAccptNoFormat(obj) {
    fld = getField(obj)
    var inputVal = getValue(fld);
    var inputStr = inputVal.toString();

	for (var i = 0; i < inputStr.length; i++) {
        var oneChar = inputStr.charAt(i)

	        if (oneChar < "0" || oneChar > "9") {
	            doSelection(fld);
	            dmbAlert("������ȣ�� �ùٷ� �Է��Ͻʽÿ�.")
	            return false;
	        }
        }

	if(inputStr != "" ){
		if(inputStr < 1 || inputStr > 999999999){
			doSelection(fld);
			dmbAlert("������ȣ�� �ùٷ� �Է��Ͻʽÿ�.")
			return false;
		}else{
			return true;
		}
	}else{
			return true;
        }	
}
////////////////////////�ֹι�ȣ ����üũ/////////////////////////////
function CheckResisterId(a,b){
    var first=a.value;
    var end=b.value;
    var chk =0;
    var yy = first.substring(0,2);
    var mm = first.substring(2,4);
    var dd = first.substring(4,6);
    var sex = end.substring(0,1);
    if (first.split(" ").join("") == "") {
        alert ('�ֹε�Ϲ�ȣ�� �Է����ּ���');
        a.focus();
        return false;
    }
    if (first.length!=6) {
        alert ('�ֹε�Ϲ�ȣ ���ڸ��� �Է��Ͻʽÿ�');
        a.focus();
        return false;
    }
    if (end.length != 7 ) {
                alert ('�ֹε�Ϲ�ȣ ���ڸ��� �Է��Ͻʽÿ�.');
                b.value="";
                b.focus();
                return false;
    }
   if (isNaN(first) || isNaN(end)) {
           alert('�ֹε�Ϲ�ȣ�� ���ڸ� �����մϴ�.');
           a.value="";
           b.value="";
           a.focus();
           return false;
   }
    if ((first.length!=6)||(mm <1||mm>12||dd<1)) {
                alert ('�ֹε�Ϲ�ȣ ���ڸ��� �߸��Ǿ����ϴ�.');
                a.value="";
                a.focus();
                return false;
    }
    if ((sex != 1 && sex !=2 && sex != 3 && sex !=4 )||(end.length != 7 )) {
                alert ('�ֹε�Ϲ�ȣ ���ڸ��� �߸��Ǿ����ϴ�.');
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
                alert ('���Ŀ� ���� �ʴ� �ֹε�Ϲ�ȣ�Դϴ�.'); // ���Ŀ� �´� �ʴ� �ֹι�ȣ �Է½� �ߴ� â
                a.value="";
                b.value="";
                a.focus();
                return false;
    }
        return true;
}

//password �ѱ�, Ư������ check
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

/* ���� ���� �ƴ��� üũ �Լ� */
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
  
			alert("���ڸ� �����մϴ�.");
			obj.value=""
			obj.focus();
			return;
		 }
	}
}

/* FLOAT ���� �ƴ��� üũ �Լ� */
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
			alert("���� ���ڰ� �ƴմϴ�.");
			obj.value=""
			obj.focus();
			return;
		 }
	}
}

/* input box �� ���� �Է½� �޸�(,)�� �����Ͽ� ǥ���ϴ� �Լ� */
function comma_auto(input_box)
{
    
	var ns = input_box.value.replace(/\,/g,""); 

	for(var i=0; i<ns.length; i++)
	{ 
//		if (ns.charAt(i)<"0" || ns.charAt(i) > "9") 
//		{
//			alert("���ڸ� �Է��Ͻʽÿ�!!");
//			return; 
//		}

		if (isNaN(ns))
		{
			alert("���ڸ� �Է��Ͻʽÿ�!!");
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

