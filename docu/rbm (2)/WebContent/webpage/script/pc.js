
/**
 * �ý��� ������� ������ �󿡼� ���� ��ƿ ����.
 *
 * @author  iryairya (iryairya@daesangit.com)
 * @version 1.0, 2005/02/05
 */



/*-------------------------------------------------------------------------
 f_chk_null()
 Spec	  : null Check  -  �տ���
 Argument : document.frm.input_name,"�޼���"
 Return   : boolean
 Example  : if(f_chk_null(document.frm.CheckEamil,"����") == false) return;
-------------------------------------------------------------------------*/
function f_chk_null(CheckObj, alertMSG)
{
	var chkstr = CheckObj.value; // ���� ������
	chkstr = chkstr + "";

	if (( chkstr == "") || (chkstr == null)) {
	    alert(alertMSG + "��(��) �Է��� �ֽʽÿ�.");
	    CheckObj.focus();
	    return false;
	}
	return true;
}

/*-------------------------------------------------------------------------
 f_chk_null()
 Spec	  : null Check  -  �տ���
 Argument : document.frm.input_name,"�޼���"
 Return   : boolean
 Example  : if(CheckEamil,"����") == false) return;
-------------------------------------------------------------------------*/
function f_chkstr_null(chkstr, alertMSG)
{
	chkstr = chkstr + "";

	if (( chkstr == "") || (chkstr == null)) {
	    alert(alertMSG + "��(��) �Է��� �ֽʽÿ�.");
	    return false;
	}
	return true;
}

/*-------------------------------------------------------------------------
 f_chk_null()  No-focus
 Spec	  : null Check  -  �տ���
 Argument : document.frm.input_name,"�޼���"
 Return   : boolean
 Example  : if(f_chk_null(document.frm.CheckEamil,"����") == false) return;
-------------------------------------------------------------------------*/
function f_chk_null2(CheckObj, alertMSG)
{
	var chkstr = CheckObj.value; // ���� ������
	chkstr = chkstr + "";

	if (( chkstr == "") || (chkstr == null)) {
	    alert(alertMSG + "��(��) �Է��� �ֽʽÿ�.");
	    return false;
	}
	return true;
}

/*-------------------------------------------------------------------------
 f_chk_email()
 Spec	  : Email Ȯ�� Check  -  �տ���
 Argument : document.frm.input_name
 Return   : boolean
 Example  : if(f_chk_email(document.frm.CheckEamil) == false) return;
-------------------------------------------------------------------------*/
function f_chk_email(CheckEmail) {
	num = 0;
	num_1 = 0;
	num_2 = 0;
	var email = CheckEmail.value;

	if (( email == "") || (email == null)) return true;

	for (var i=0;i<email.length;i++){
	   if (email.charAt(i) == '@')
		   num++;
	   if (email.charAt(i) == '.')
   		   num_1++;
	   if (email.charAt(i) == ' ')
   		   num_2++;
	}

	if (num != 1 || num_1 == 0) {
 	   alert("E-mail �Է��� ��Ȯ�� �Է��� �ֽʽÿ�.");
 	   CheckEmail.select();
	   return false;
	}

	if (num_2 != 0) {
 	   alert("E-mail �Է½� ���鹮��(Space)�� �����ֽʽÿ�.");
 	   CheckEmail.select();
	   return false;
	}
	return true;
}

/*-------------------------------------------------------------------------
 f_chk_radio()
 Spec	  : ���� ��ư��(Ȥ�� üũ�ڽ�) üũ�Ǿ� �ִ��� üũ  -  �տ���
 Argument : document.frm.input_name
 Return   : boolean
 Example  : if(!f_chk_radio(document.frm.radio)) return;
-------------------------------------------------------------------------*/
function f_chk_radio(input, massage) {
    if (input.length > 1) {
        for (var inx = 0; inx < input.length; inx++) {
            if (input[inx].checked) return true;
        }
    } else {
        if (input.checked) return true;
    }
	alert(massage + "(��)�� �����Ͻʽÿ�.");
    return false;
}

/*-------------------------------------------------------------------------
 f_chk_checkBox()
 Spec	  : üũ�ڽ��� üũ�Ǿ� �ִ��� üũ  -  �տ���
 Argument : document.frm.input_name
 Return   : boolean
 Example  : if(!f_chk_checkBox(document.frm.radio)) return;
-------------------------------------------------------------------------*/
function f_chk_checkbox(input,massage) {
    return f_chk_radio(input,massage);
}


/*-------------------------------------------------------------------------
 f_chk_quantity()
 Spec	  : ���õ� üũ�ڽ��� ����� ���� ��ȯ(Ȥ�� ������ư�� ����)  -  �տ���
 Argument : document.frm.input_name
 Return   : boolean
 Example  : var count = f_chk_quantity(document.frm.checkbox);
-------------------------------------------------------------------------*/
function f_chk_quantity(input) {
var kkkk = 0;
    if (input.length > 1) {
        for (var inx = 0; inx < input.length; inx++) {
            if (input[inx].checked) {
			kkkk++;
			}
        }
    } else {
		 if (input.checked) kkkk=1;
	}
    return kkkk;
}

/*-------------------------------------------------------------------------
 f_chk_pswd()
 Spec	  : ��й�ȣ�� ��й�ȣ ��Ȯ�� �� Check  -  �տ���
 Argument : document.frm.input_name1,document.frm.input_name2
 Return   : boolean
 Example  : if(f_chk_pswd(document.frm.input_name1,document.frm.input_name2) == false) return;
-------------------------------------------------------------------------*/
function f_chk_pswd(Chkpswd1,Chkpswd2) {

	var pswd1 = Chkpswd1.value;
	var pswd2 = Chkpswd2.value;
	if(pswd1.length < 6 || pswd1.length > 12){
		alert("��й�ȣ�� 6�ڸ����� 12�ڸ� ������ �Է��ϼ���.");
        return false;
	}
	if(pswd2.length < 6 || pswd2.length > 12){
		alert("��й�ȣ�� 6�ڸ����� 12�ڸ� ������ �Է��ϼ���.");
        return false;
	}
	if ( pswd1 != pswd2 ) {
        alert("��й�ȣ Ȯ�ΰ� Ʋ���ϴ�. ��й�ȣ�� Ȯ���� �ּ���.");
        return false;
    }else
        return true;
}

/*-------------------------------------------------------------------------
 f_isEnNum()
 Spec	  : ��й�ȣ�Է� �� ������ ���� ȥ���ؾ���  -  �տ���
 Argument : num, document.frm.input_name1,document.frm.input_name2
 Return   : boolean
 Example  : <input type="text" name="field1" size="6" Maxlength="6"  OnKeyUp="f_move_field(document.frm.field1,6,document.frm.field2);">
-------------------------------------------------------------------------*/
	function f_isEnNum(obj){ 
		
		var sText = obj.value;
		sText = String(sText);
		
		var val = /^[a-zA-Z0-9]+$/.test(sText);
		if(val){
			if(/^[0-9]+$/.test(sText)){
				alert("��й�ȣ�� �Է��Ͻ� �� ������ ���ڸ� �Բ� ����� �ֽʽÿ�.");
				return false;
			}
			if(/^[a-zA-Z]+$/.test(sText)){
				alert("��й�ȣ�� �Է��Ͻ� �� ������ ���ڸ� �Բ� ����� �ֽʽÿ�.");
				return false;
			}
			return true;
		}else{
			return false;
		}
	}

/*-------------------------------------------------------------------------
 f_move_field()
 Spec	  : �ʵ��ڵ��̵�  -  �տ���
 Argument : num, document.frm.input_name1,document.frm.input_name2
 Return   : boolean
 Example  : <input type="text" name="field1" size="6" Maxlength="6"  OnKeyUp="f_move_field(document.frm.field1,6,document.frm.field2);">
-------------------------------------------------------------------------*/
function f_move_field(thisObj, num, nextObj) {
	if (thisObj.value.length == num) {
		thisObj.blur();
		nextObj.focus();
	}
}

/*-------------------------------------------------------------------------
 f_chk_kor()
 Spec	  : �ѱ��Է¹��� - �տ���
 Argument : document.frm.input_name
 Return   : boolean
 Example  : if(f_chk_kor(document.frm.id) == false) return;
-------------------------------------------------------------------------*/
function f_chk_kor(CheckObj, alertMSG)
{
	var rtn;
	rtn = false;

	var len = CheckObj.value.length;

	for (var j=0;j<len;j++)
	{

        var vAsc = CheckObj.value.charCodeAt(j)

		if ((vAsc > 96) && (vAsc < 124) || (vAsc > 64) && (vAsc < 91) || (vAsc > 47) && (vAsc < 58))
		{
			rtn = true;
		}
		else
		{
			rtn = false;
	    	alert(alertMSG +" �Է��� �����ڸ� �����մϴ�.");
			break;
		}
    }

    return rtn;
}


/*-------------------------------------------------------------------------
 f_chk_col()
 Spec	  : �Է��׸� �ڸ��� check  -  �տ���
 Argument : Object document.form_name.input_name, �ڸ���, String "���޽���"
 Return   : boolean
 Example  : if(f_chk_col(document.frm.id, 10, "���̵�") == false) return;
-------------------------------------------------------------------------*/
function f_chk_col(CheckObj, CheckNum, alertMSG)
{
	var chkstr = CheckObj.value; // ���� ������
	var chklen = chkstr.length;

	if (( chkstr == "") || (chkstr == null) || (chklen < CheckNum)) {
		alert(alertMSG+" "+CheckNum+" �ڸ� �̻� �Է��� �ֽʽÿ�.");
		CheckObj.focus();
		return false;
	} // if ���� ��....

	for (var j = 0; j < chklen; j++) {
		if (chkstr.substring(j, j+1) == " ") {
			alert(alertMSG+" "+CheckNum+" �ڸ� �̻� �Է��� �ֽʽÿ�.");
			CheckObj.focus();
			return false;
		}//if
	}//for ���� ��

	return true;
}

/*-------------------------------------------------------------------------
f_chk_num()
spec     : ���ڸ� �ԷµǾ��� �� �˻�. - �տ���
Araument : document.frm.input_name
Return   : boolean
Example  : if(f_chk_num(document.frm.input_name,"�ݾ�") == false) return;
-------------------------------------------------------------------------*/
function f_chk_num(stObj, alertMSG){
    var st = stObj.value;
    if (( st == "") || (st == null)) return true;

	for (var j = 0; j < st.length; j++)
		if ((st.substring(j, j+1) < "0") || (st.substring(j, j+1) > "9")) {
			alert(alertMSG + "���� ���ڸ� �Է��� �� �ֽ��ϴ�.");
			stObj.value="";
			stObj.focus();
			return false;
		}
	return true;
}

/*-------------------------------------------------------------------------
f_setRadioValue()
spec     : Radio ��ư���� �����Ѵ�. 
Araument : document.frm, �ڵ�
Return   : boolean
Example  : f_setRadioValue(form, '010001')
-------------------------------------------------------------------------*/
function f_setRadioValue(form, stdcd)
{
	var count = 0;
	var i = 0;
	while (i < form.elements.length){
		if(form.elements[i].type=="radio"){
			if (form.elements[i].name=="stdcd"){
				if (form.elements[i].value==stdcd) form.elements[i].checked = true;
			}
		}
		i ++;
	}
	return true;
}


/*-------------------------------------------------------------------------
f_getRadioValue()
spec     : üũ�Ǿ� �ִ� Radio ��ư���� �����´�.
Araument : document.frm
Return   : boolean
Example  : f_getRadioValue(form)
-------------------------------------------------------------------------*/
function f_getRadioValue(form)
{
	var count = 0;
	var i = 0;
	while (i < form.elements.length){
		if(form.elements[i].type=="radio"){
			if (form.elements[i].name=="stdcd"){
				if (form.elements[i].checked) return form.elements[i].value;
			}
		}
		i ++;
	}
	return "";
}

/*-------------------------------------------------------------------------
f_getRadioValue2()
spec     : üũ�Ǿ� �ִ� Radio ��ư���� �����´�.
Araument : document.frm.input
Return   : boolean
Example  : f_getRadioValue2(input)
-------------------------------------------------------------------------*/
function f_getRadioValue2(input)
{
	var count = 0;
	var i = 0;
	while (i < input.length){
		if(input[i].type=="radio"){
			if (input[i].checked) return input[i].value;
		}
		i ++;
	}
	return "";
}

/*-------------------------------------------------------------------------
f_findZip()
spec     : �����ȣ�� �ּҸ� ã�� �Ķ���� ������ ��������. - �տ��� 2005-04-01
Araument : document.frm
Return   : boolean
Example  : f_getRadioValue(form)
-------------------------------------------------------------------------*/
function f_findZip(form,zip,add1,add2){
	var url = "/race/view/common/addFindForm.jsp?form="+form+"&zip="+zip+"&address1="+add1+"&address2="+add2;
	window.open(url,'win', 'width=380, height=327, toolbar=no, location=no, directories=no, status=no, menubar=no, resizable=yes, scrollbars=yes, copyhistory=no');
}

/*-------------------------------------------------------------------------
f_findZip()
spec     : �ֹι�ȣ �Է½� ��Ű �ڵ��̵� - �տ��� 2005-04-01
Araument : document.frm
Return   : boolean
Example  : onKeyUp="return autoTab(this, 6, event);
-------------------------------------------------------------------------*/
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

/*-------------------------------------------------------------------------
f_findZip()
spec     : �ֹι�ȣ �Է�üũ - �տ��� 2005-04-01
Araument : document.frm.inputName
Return   : boolean
Example  : f_joominChk(stObj1, stObj2);
-------------------------------------------------------------------------*/
function f_joominChk(stObj1, stObj2){
	if (stObj1.value.length != 6){
		alert("�ùٸ� �ֹε�Ϲ�ȣ�� �Է����ּ���.");
		stObj1.focus();
	}else if (stObj2.value.length != 7){
		alert("�ùٸ� �ֹε�Ϲ�ȣ�� �Է����ּ���.");
		stObj2.focus();
	} else {
		var str_serial1 = stObj1.value;
		var str_serial2 = stObj2.value;
		var digit=0
		for (var i=0;i<str_serial1.length;i++){
			var str_dig=str_serial1.substring(i,i+1);
			if (str_dig<'0' || str_dig>'9'){ 
				digit=digit+1 
			}
		}
		if ((str_serial1 == '') || ( digit != 0 )){
			alert('�߸��� �ֹε�Ϲ�ȣ�Դϴ�. �ٽ� Ȯ���Ͻð� �Է��� �ּ���.');
			stObj1.focus();
			return false;   
		}

		var digit1=0
		for (var i=0;i<str_serial2.length;i++){
			var str_dig1=str_serial2.substring(i,i+1);
			if (str_dig1<'0' || str_dig1>'9'){ 
				digit1=digit1+1 
			}
		}
		if ((str_serial2 == '') || ( digit1 != 0 )){
			alert('�߸��� �ֹε�Ϲ�ȣ�Դϴ�. �ٽ� Ȯ���Ͻð� �Է��� �ּ���.');
			stObj2.focus();
			return false;   
		}
		if (str_serial1.substring(2,3) > 1){
			alert('�߸��� �ֹε�Ϲ�ȣ�Դϴ�. �ٽ� Ȯ���Ͻð� �Է��� �ּ���.');
			stObj1.focus();
			return false;   
		}
		if (str_serial1.substring(4,5) > 3){
			alert('�߸��� �ֹε�Ϲ�ȣ�Դϴ�. �ٽ� Ȯ���Ͻð� �Է��� �ּ���.');
			stObj1.focus();
			return false;   
		} 
		if (str_serial2.substring(0,1) > 4 || str_serial2.substring(0,1) == 0){
			alert('�߸��� �ֹε�Ϲ�ȣ�Դϴ�. �ٽ� Ȯ���Ͻð� �Է��� �ּ���.');
			stObj2.focus();
			return false;   
		}
		var a1=str_serial1.substring(0,1)
		var a2=str_serial1.substring(1,2)
		var a3=str_serial1.substring(2,3)
		var a4=str_serial1.substring(3,4)
		var a5=str_serial1.substring(4,5)
		var a6=str_serial1.substring(5,6)
		var check_digit=a1*2+a2*3+a3*4+a4*5+a5*6+a6*7
		var b1=str_serial2.substring(0,1)
		var b2=str_serial2.substring(1,2)
		var b3=str_serial2.substring(2,3)
		var b4=str_serial2.substring(3,4)
		var b5=str_serial2.substring(4,5)
		var b6=str_serial2.substring(5,6)
		var b7=str_serial2.substring(6,7)
		var check_digit=check_digit+b1*8+b2*9+b3*2+b4*3+b5*4+b6*5 
		check_digit = check_digit%11
		check_digit = 11 - check_digit
		check_digit = check_digit%10
		if (check_digit != b7){
			alert('�߸��� �ֹε�Ϲ�ȣ�Դϴ�. �ٽ� Ȯ���Ͻð� �Է��� �ּ���.');
			stObj2.focus();
			return false;   
		}else{
			return true;
		}
	}
}

/*-------------------------------------------------------------------------
f_adultChk()
spec     : �������� üũ - �տ��� 2005-04-01
Araument : document.frm.inputName
Return   : boolean
Example  : f_adultChk(adultDate, stObj);
-------------------------------------------------------------------------*/
function f_adultChk(adultDate, stObj){
	
		var regStr = stObj.value;

		if(Number(regStr) <= 100000){ 
			alert('�̼����ڴ� ������ �� �����ϴ�.');
			return false; 
		} else if(Number(adultDate) >= Number(regStr)){ return true; }
		else {
			alert('�̼����ڴ� ������ �� �����ϴ�.');
			return false;
		}
	}

/*-------------------------------------------------------------------------
f_enterKey()
spec     : ����Ű�� ������ ���� �Ķ���ͷ� ��Ŀ���̵� - �տ��� 2005-04-01
Araument : document.frm.inputName
Return   : boolean
Example  : onkeypress="f_enterKey(stObj)"
-------------------------------------------------------------------------*/
function f_enterKey(stObj){
	if(event.keyCode == 13){
		stObj.focus();
	}
}
	
	/*-------------------------------------------------------------------------
	 f_chk_select()
	 Spec	  : select tag�� ���õ� ������ �ε����� 0�� �ƴ��� ����  -  �����
	 Argument : document.frm.input_name
	 Argument : message
	 Return   : boolean
	 Example  :f_chk_select(document.frm.checkbox, "������");
	-------------------------------------------------------------------------*/
	function f_chk_select(input, message) {
		var index = input.selectedIndex;
		if (index == 0) {
			alert(message + "(��)�� �����Ͻʽÿ�.");
			return false;
		}
	    return true;
	}

	
	/*-------------------------------------------------------------------------
	 f_chk_year()
	 Spec	  : 0���� ũ�� ������ �ڸ� �� ���� ���� ��� üũ
	 Argument : document.frm.input_name
	 Argument : message
	 Return   : boolean
	 Example  : f_chk_year(document.frm.year, 4, "���ϳ⵵");
	-------------------------------------------------------------------------*/
	function f_chk_year(Obj, num, Msg){
		var strVal = Obj.value;
		if(strVal.length > 0 && strVal.length < num){
			alert(Msg+'(��)�� �ùٸ��� �Է��ϼ���.');
			Obj.focus();
			return false;
		}
		return true;
	}
	
	/*-------------------------------------------------------------------------
	 f_ten()
	 Spec	  : ���ڸ� ���ڰ� �ԷµǸ� �տ� 0�� �ٿ� �����Ѵ�.
	 Argument : document.frm.input_name
	 Argument : message
	 Return   : boolean
	 Example  : f_ten(document.frm.month);
	-------------------------------------------------------------------------*/
	function f_ten(Obj){
		var strVal = Obj.value;
		var returnVal = '';
		if(strVal.length > 0 && strVal.length < 2){
			returnVal = "0"+strVal;
			return returnVal;
		}
		return strVal;
	}

	/*-------------------------------------------------------------------------
	 openWin()
	 Spec	  : �˻� �����̳� ��¥�� �Է��� ��� �޷��� ��� �Է��Ѵ�.
	 Argument : document.frm.input_name
	 Argument : message
	 Return   : boolean
	 Example  : openWin(obj); form �ȿ� �Ʒ� �׸��� �߰��Ѵ�.
	-------------------------------------------------------------------------*/
	var m_strCurWinXpoint = 0;				//��������= �������� ���콺 ������ X��ǥ��
	var m_strCurWinYpoint = 0;				//��������= �������� ���콺 ������ Y��ǥ��

    function openWin(argVarName) {
	    windowOption = "scrollbars=no,";
	    windowOption += "location=no,";
	    windowOption += "directories=no,";
	    windowOption += "width=188,height=185,";
	    windowOption += "left=" + m_strCurWinXpoint +",top=" + (m_strCurWinYpoint - 100);
    	window.open("/race/view/common/calendar.jsp?m_pVarName=" + argVarName,'calendar',windowOption);
    }     	
    
   function openWinAdmin(argVarName) {
	    windowOption = "scrollbars=no,";
	    windowOption += "location=no,";
	    windowOption += "directories=no,";
	    windowOption += "width=270,height=250,";
	    windowOption += "left=" + m_strCurWinXpoint +",top=" + (m_strCurWinYpoint - 100);
    	window.open("./comm/calendar.php3?m_pVarName=" + argVarName,'calendar',windowOption);
    } 
        

    function openWinTime(argVarName) {
	    windowOption = "scrollbars=no,";
	    windowOption += "location=no,";
	    windowOption += "directories=no,";
	    windowOption += "width=330,height=230,";
	    windowOption += "left=" + m_strCurWinXpoint +",top=" + (m_strCurWinYpoint - 100);
    	window.open("./comm/time.php3?m_pVarName=" + argVarName,'timer',windowOption);
    }           

    
	var IE = document.all?true:false;
	document.onmousemove = getMouseXY;    
	function getMouseXY(e) {
		if (IE) { // grab the x-y pos.s if browser is IE
			m_strCurWinXpoint = event.screenX;
			m_strCurWinYpoint = event.screenY;
		}
		
		return true;
	}		
	
	/*-------------------------------------------------------------------------
	 onlyNumberInput()
	 Spec	  : text type�� �ʵ忡 �����̿��� �����Է��� �����Ѵ�.
	 Return   : boolean
	 Example  : <input type="text" onKeyDown="javascript:onlyNumberInput();">
	-------------------------------------------------------------------------*/
	function onlyNumberInput() {
	    var code = window.event.keyCode;
	
	    if ((code > 34 && code < 41) || (code > 47 && code < 58) 
	         || (code > 95 && code < 106) || code == 8 || code == 9 
	         || code == 13 || code == 46) {
	
	        window.event.returnValue = true;
	        return;
	    }
	    alert("���ڸ� �Է��ϼ���.");
	    window.event.returnValue = false;
	}


	/*-------------------------------------------------------------------------
	 f_commaInsert(obj)
	 Spec	  : 3�ڸ� ���ڿ� �ڵ����� �޸��� �Է��Ѵ�.
	 Return   : num
	 Example  : f_commaInsert(obj)
	-------------------------------------------------------------------------*/
	function f_commaInsert(obj) {
		var re = /(-?\d+)(\d{3})/
		var num = obj;
		while (re.test(num)) {
			num = num.replace(re,"$1,$2");
		}
		return num;
	}
	

	/*-------------------------------------------------------------------------
	 f_commaRemove(obj)
	 Spec	  : 3�ڸ� ���ڿ� �ִ� �޸��� �����Ѵ�.
	 Return   : num
	 Example  : f_commaRemove(obj)
	-------------------------------------------------------------------------*/
	function f_commaRemove(obj) {
		var re = /,/g
		return obj.replace(re,"");
	}

	
	/*-------------------------------------------------------------------------
	 doBlink()
	 Spec	  : IE���� �׽������� ���� �Ӽ��� blink �±׸� �����ϰ� �Ѵ�.
	 Return   : 
	 Example  : <blink>���ڿ��� �� ���ڸ����̿� ���ڸ� �־� �ָ� �ȴ�.</blink>
	-------------------------------------------------------------------------*/
	/*
	function doBlink() {
		var blink = document.all.tags("BLINK");
		for (var i=0; i < blink.length; i++) {
		blink[i].style.visibility = (blink[i].style.visibility =="visible") ? "hidden":"visible";
		}
	}
	if (document.all) setInterval("doBlink()",300);
	*/


	/*-------------------------------------------------------------------------
	 replace(str,sstr,rstr)
	 Spec	  : Java �� replace �Լ��� ����ϰ� ����
	 Return   : 
	 Example  : 
	-------------------------------------------------------------------------*/
	function replace(str,sstr,rstr){
		return String(str).replace(new RegExp(sstr,"ig"),rstr);
	}


	/*�̿��� �˾� ����*/
	function f_agreement(){
		var winl = (screen.width - 800) / 3;
		var wint = (screen.height - 600) / 3;
		winprops = 'height=300,width=495,top='+wint+',left='+winl+',scrollbars=yes,resizable=no,menubar=no';
		win = window.open("/race/view/user/m-t.htm", "agreeMent", winprops)
		if (parseInt(navigator.appVersion) >= 4) { win.window.focus(); }
	}

	
	/*�̿��ڰ��̵� �˾� ����*/
	function f_guide(){
		var winl = (screen.width - 800) / 3;
		var wint = (screen.height - 600) / 3;
		winprops = 'height=500,width=700,top='+wint+',left='+winl+',scrollbars=yes,resizable=no,menubar=no';
		win = window.open("/race/view/guide/guide_index.htm", "agreeMent", winprops)
		if (parseInt(navigator.appVersion) >= 4) { win.window.focus(); }
	}

	// comma �ڵ��Է�
        // use : <input type="text" ... onkeyup="javascript:js_InputComma(this);"> //
        function js_InputComma(obj){
                if(window.event.keyCode==37||window.event.keyCode==39) return;
                var strValue=js_RemoveComma(obj.value);

                if(isNaN(strValue)&&strValue!="-"){
                        alert("���ڸ� �Է��ϼ���");
                        obj.value=strValue.substring(0,strValue.length-1);
                        js_InputComma(obj);
                        return strValue;
                }

                if(strValue.substr(0,1)=="-"){
                        strValue1=strValue.substr(0,1);
                        strValue=strValue.substr(1);
                }else{
                        strValue1="";
                }

                var idx=strValue.indexOf(".");
                if(idx==-1){
                        strValue2=strValue;
                        strValue3="";
                        strValue4="";
                }else{
                        strValue2=strValue.substr(0,idx);
                        strValue3=".";
                        strValue4=strValue.substr(idx+1);
                }

                var i=0;
                var j=strValue2.length;
                strValue0="";
                while(j>0){
                        i++;
                        j--;
                        if(i==4){
                                strValue0=strValue.substr(j,1)+","+strValue0;
                                i=1;
                        }else{
                                strValue0=strValue.substr(j,1)+strValue0;
                        }
                }
                strValue=strValue1+strValue0+strValue3+strValue4;

                obj.value=strValue;
        }
        
	/* --- ���ڸ� �Է� ���� (onKeyDown �̺�Ʈ) --- */
	function onlyNumberInput() {
		var code = window.event.keyCode;
		
		if ((code > 34 && code < 41) || (code > 47 && code < 58)
		     || (code > 95 && code < 106) || code == 8 || code == 9
		     || code == 46) {
		    window.event.returnValue = true;
		    return;
		}
		window.event.returnValue = false;
	}
	
	// comma ����
        function js_RemoveComma(strValue){
                var re=/,/g;
                strValue=strValue.replace(re, "");

                return strValue;
        }

        // comma �ڵ��Է�
        // use : <input type="text" ... onkeyup="javascript:js_InputComma(this);"> //
        function js_InputComma(obj){
                if(window.event.keyCode==37||window.event.keyCode==39) return;
                var strValue=js_RemoveComma(obj.value);

                if(isNaN(strValue)&&strValue!="-"){
                        alert("���ڸ� �Է��ϼ���");
                        obj.value=strValue.substring(0,strValue.length-1);
                        js_InputComma(obj);
                        return strValue;
                }

                if(strValue.substr(0,1)=="-"){
                        strValue1=strValue.substr(0,1);
                        strValue=strValue.substr(1);
                }else{
                        strValue1="";
                }

                var idx=strValue.indexOf(".");
                if(idx==-1){
                        strValue2=strValue;
                        strValue3="";
                        strValue4="";
                }else{
                        strValue2=strValue.substr(0,idx);
                        strValue3=".";
                        strValue4=strValue.substr(idx+1);
                }

                var i=0;
                var j=strValue2.length;
                strValue0="";
                while(j>0){
                        i++;
                        j--;
                        if(i==4){
                                strValue0=strValue.substr(j,1)+","+strValue0;
                                i=1;
                        }else{
                                strValue0=strValue.substr(j,1)+strValue0;
                        }
                }
                strValue=strValue1+strValue0+strValue3+strValue4;
                
                obj.value=strValue;
        }
        
    function calender2(){ 
        //strleft = "dialogleft:" + eval(window.screenLeft + window.event.clientX ) ; 
        //strtop = ";dialogtop:" + eval(window.screenTop + window.event.clientY ) ; 
        //return window.showModalDialog("./comm/cal.php","", strleft +  strtop +";dialogWidth:190px; dialogHeight:210px;scroll:no;status:no;titlebar:no;center:no;help:yes;"); 
        strleft = "left=" + eval(window.screenLeft + window.event.clientX ) + ", "; 
        strtop  = "top=" + eval(window.screenTop + window.event.clientY )  + ", "; 
        return window.open("./comm/cal.php","calwin", strleft +  strtop +"width=180, height=190");
    } 
    function is_null(item_var) { 
        if(item_var == "" || item_var == null || item_var == 'undefined' || item_var == " ") 
        return true; 
     
        return false; 
    } 
    
    function goSubmit(theUrl,meet) {
    	
    	if(document.calFrm.race_date.value.length != 8) {
    		alert("��¥�� ��Ȯ�� �Է��� �ּ���");
    		return;
    	}
    	var actionURL = "" + theUrl +"?meet="+meet+"&race_date="+ document.calFrm.race_date.value;
    	document.location = actionURL;
    	//document.form1.submit();
	}
	
	function makecheck(racedate, filecode) {
		
		var message = "�����͸� �����Ͻðڽ��ϱ�?";
		if(confirm(message)) {
			
			document.write("<form name='checkfrm'>");
			document.write("<input type=hidden name=flag >");
			document.write("<input type=hidden name=filecode >");
			document.write("<input type=hidden name=racedate >");
			document.write("</form>");
		
			document.checkfrm.flag.value = "check";
			document.checkfrm.filecode.value = filecode;
			document.checkfrm.racedate.value = racedate;
			
			document.checkfrm.action = "/pcfile/check.php";
			document.checkfrm.submit();
		} else {
			return;
		}
	}
	
	function makeuncheck(racedate, filecode) {
		var message = "Ȯ�� ��ư�� �����ø� �繫ȸ�� �ý��ۿ��� ��ȸ�� �Ұ����մϴ�.";
		if(confirm(message)) {
			
			document.write("<form name='checkfrm'>");
			document.write("<input type=hidden name=flag >");
			document.write("<input type=hidden name=filecode >");
			document.write("<input type=hidden name=racedate >");
			document.write("</form>");
		
			document.checkfrm.flag.value = "uncheck";
			document.checkfrm.filecode.value = "" + filecode;
			document.checkfrm.racedate.value = "" + racedate;
			
			document.checkfrm.action = "/pcfile/check.php";
			document.checkfrm.submit();
		} else {
			return;
		}
	}
	
	function makecheckBal(racedate, flag) {
		
		var message = "�����͸� �����Ͻðڽ��ϱ�?";
		if(confirm(message)) {
			
			document.write("<form name='checkfrm'>");
			document.write("<input type=hidden name=flag >");
			document.write("<input type=hidden name=racedate >");
			document.write("</form>");
		
			document.checkfrm.flag.value = flag;
			document.checkfrm.racedate.value = racedate;
			
			document.checkfrm.action = "/pcfile/checkBal.php";
			document.checkfrm.submit();
		} else {
			return;
		}
	}
	
	function makeuncheckBal(racedate, flag) {
		var message = "Ȯ�� ��ư�� �����ø� �繫ȸ�� �ý��ۿ��� ��ȸ�� �Ұ����մϴ�.";
		if(confirm(message)) {
			
			document.write("<form name='checkfrm'>");
			document.write("<input type=hidden name=flag >");
			document.write("<input type=hidden name=racedate >");
			document.write("</form>");
		
			document.checkfrm.flag.value = flag;
			document.checkfrm.racedate.value = "" + racedate;
			
			document.checkfrm.action = "/pcfile/checkBal.php";
			document.checkfrm.submit();
		} else {
			return;
		}
	}
