/******************** �޷� ����� �ڹ� ��ũ��Ʈ ********************/


/* ------------------------------------------------------------
		1. �⺻ �ɼ�
   ------------------------------------------------------------ */

	// ����
	var d_format = new Array('yyyy.mm.dd','yy.mm.dd','yyyy-mm-dd','yy-mm-dd','yyyy/mm/dd','yy/mm/dd','yyyymmdd','yymmdd');
	// ���� ����
	var day_color = new Array('red', 'black', 'black', 'black', 'black', 'black', 'blue');
	// ����, �� ǥ��
	var num_dayName = new Array(0,1,2,3,4,5,6);
	var han_dayName = new Array('��','��','ȭ','��','��','��','��');
	var eng_dayName = new Array('SUN','MON','TUE','WED','THU','FRI','SAT');
	var chi_dayName = new Array('��','��','��','�','��','��','��');
	var num_months = new Array('1','2','3','4','5','6','7','8','9','10','11','12');
	var han_months = new Array('1��', '2��', '3��', '4��', '5��', '6��', '7��', '8��', '9��', '10��', '11��', '12��');
	var eng_months = new Array('Jan','Feb','Mar','Apr','Mai','Jun','Jul','Aug','Sep','Oct','Nov','Dec');
	var chi_months = new Array('1��', '2��', '3��', '4��', '5��', '6��', '7��', '8��', '9��', '10��', '11��', '12��');
	var dayNameType = new Array('num_dayName','han_dayName','eng_dayName','chi_dayName');
	var monthsType = new Array('num_months','han_months','eng_months','chi_months');

	// ��Ÿ��
	var d_bgcolor = '#FAD9B7';
	var d_bgcolor2 = 'white';
	var d_linecolor = '#C6c4b5';
	var d_lineheight = '23px';
	var d_width = '300';
	var d_tfDateLineCol = 'black';
	var d_tfCol = '#FFFFFF';
	var d_focusCol = '#F0EDF0';


/* ------------------------------------------------------------
		2. ��Ÿ
   ------------------------------------------------------------ */

	var currentDate = new Date();
	var DOMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];		// Non-Leap year Month days..
	var lDOMonth = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];	// Leap year Month days..
	var d_formName = "form1";
	var d_name = "cal";
	var d_tfDate = "tfDate";

/* ------------------------------------------------------------
		3. Calendar ��ü ����
   ------------------------------------------------------------ */

	// Calendar ��ü
	function Calendar(newName, newFormName, newYear, newMonth, newDate, newFormat, newDayName) {
		// Calendar ��ü�� ���� �Լ� ���
		this.setDates = setDates;		// �ش���� ������ �迭 ����
		this.prevMonth = prevMonth;		// ���� ���� ����
		this.nextMonth = nextMonth;		// ���� ���� ����
		this.prevYear = prevYear;		// ���� ������ ����
		this.nextYear = nextYear;		// ���� ������ ����
		this.reload = reload;			// ������ ����� ����
		this.show = show;				// html ����
		this.setContent = setContent;	// html�� �ʵ尪 ����
		this.clearContent = clearContent;// html�� �ʵ尪 �����
		this.getYear = getYear			// ��ȸ�� ��
		this.getMonth = getMonth		// ��ȸ�� ��

		// Calendar ��ü�� �Ӽ� ����
		this.name;						// Calendar ��ü�� �̸�
		this.myYear;					// ��ȸ�� ��
		this.myMonth;					// ��ȸ�� ��(month:0~11)
		this.myDate;					// ��ȸ�� ��
		this.format;					// ����� format
		this.dayName;					// ���� format
		this.formName;					// form name
		this.method;					// form method
		this.action;					// form action
		this.tfDate;					// ��¥ �ʵ��
		this.bgcolor;					// ������ ��� Į��
		this.bgcolor2;					// ������ ��� Į��
		this.linecolor;					// �޷� ���� Į��
		this.lineheight;				// ��ĭ�� ����
		this.width;						// �޷� �ʺ�
		this.tfDateLineCol;				// ��¥ �ʵ� ���� ��
		this.tfCol;						// ���� �ʵ� ��
		this.focusCol;					// ��Ŀ�� �ʵ� ��

		// argument ����
		if (newName != null) this.name = newName;										// Calendar ��ü �̸� ����
			else this.name = d_name;
		if (newFormName != null) this.formName = newFormName;							// form �̸� ����
			else this.formName = d_formName;
		if (newYear != null && newYear.length != 0)	this.myYear = newYear;				// ��ȸ�� �� ����
			else this.myYear = currentDate.getFullYear();
		if (newMonth != null && newMonth.length != 0)	this.myMonth = newMonth - 1 ;	// ��ȸ�� �� ����
			else this.myMonth = currentDate.getMonth();
		if (newDate != null && newDate.length != 0)	this.myDate = newDate;				// ��ȸ�� �� ����
			else this.myDate = currentDate.getDate();
		if (newFormat != null && newFormat.length != 0)	this.format = newFormat;		// ��¥ �ʵ� format ����
			else this.format = d_format[0];
		if (newDayName != null)	this.dayName = eval(dayNameType[newDayName]);			// ���� format ����
			else this.dayName = eval(dayNameType[1]);

		// �⺻�� ����
		this.tfDate = d_tfDate;
		this.bgcolor = d_bgcolor;
		this.bgcolor2 = d_bgcolor2;
		this.linecolor = d_linecolor;
		this.lineheight = d_lineheight;
		this.width = d_width;
		this.tfDateLineCol = d_tfDateLineCol;
		this.tfCol = d_tfCol;
		this.focusCol = d_focusCol;
	}


/* ------------------------------------------------------------
		4. ��¥ ���õ� �Լ� ����
   ------------------------------------------------------------ */

	// ��ȸ ����� �ϼ� ����
	function getDaysOfMonth(year, month) {
			/*
			Check for leap year ..
			1.Years evenly divisible by four are normally leap years, except for...
			2.Years also evenly divisible by 100 are not leap years, except for...
			3.Years also evenly divisible by 400 are leap years.
			*/
			if ((year % 4) == 0) {
					if ((year % 100) == 0 && (year % 400) != 0)
							return DOMonth[month];

					return lDOMonth[month];
			} else
					return DOMonth[month];
	}

	// ù��° ���� ���ϱ�
	function getFirstDay(year, month) {
		var tmpDate = new Date();
        tmpDate.setDate(1);
        tmpDate.setMonth(month);
        tmpDate.setFullYear(year);

        return tmpDate.getDay();
	}

	// ������ ���� ���ϱ�
	function getLastDay(year, month) {
		var tmpDate = new Date();
        tmpDate.setDate( getDaysOfMonth(year, month) );
        tmpDate.setMonth(month);
        tmpDate.setFullYear(year);

        return tmpDate.getDay();
	}

	// ��¥ ����(incr:-1), ����(incr:1)
	function calcMonthYear(p_Year, p_Month, incr) {
			/*
			Will return an 1-D array with 1st element being the calculated month
			and second being the calculated year
			after applying the month increment/decrement as specified by 'incr' parameter.
			'incr' will normally have 1/-1 to navigate thru the months.
			*/
			var ret_arr = new Array();

			if (incr == -1) {
					// B A C K W A R D
					if (p_Month == 0) {
							ret_arr[1] = 11;
							ret_arr[0] = parseInt(p_Year) - 1;
					}
					else {
							ret_arr[1] = parseInt(p_Month) - 1;
							ret_arr[0] = parseInt(p_Year);
					}
			} else if (incr == 1) {
					// F O R W A R D
					if (p_Month == 11) {
							ret_arr[1] = 0;
							ret_arr[0] = parseInt(p_Year) + 1;
					}
					else {
							ret_arr[1] = parseInt(p_Month) + 1;
							ret_arr[0] = parseInt(p_Year);
					}
			} else if (incr == 0) {
					// C U R R E N T
							ret_arr[1] = parseInt(p_Month);
							ret_arr[0] = parseInt(p_Year);
			}

			return ret_arr;
	}



/* ------------------------------------------------------------
		5. Calendar ��ü�� �Լ� ����
   ------------------------------------------------------------ */

	// �ش���� ������ �迭 ����
	function setDates() {
		var dates = new Array();										// dates = { '&npsp;', '', 1, 2, 3, 4, ...27,.. '&npsp;' };
		var firstDay = getFirstDay(this.myYear, this.myMonth);			// ù��° ������ ���ڰ�
		var lastDay = getLastDay(this.myYear, this.myMonth);			// ������ ������ ���ڰ�
		var daysOfMonth = getDaysOfMonth(this.myYear, this.myMonth);	// 28, 29, 30, 31 �� �ϳ�
		var firstDate = 1;

		for (var i = 0; i < firstDay; i++) dates[i] = '';
		for (var i = firstDay; i < daysOfMonth + firstDay ; i++) {
			dates[i] = firstDate;
			firstDate ++;
		}

		var len = dates.length;
		for (var i = 0; i < (6-lastDay); i++) {	dates[ len + i] = '';	}
		return dates;
	}

	// ���� ���� ����
	function prevMonth() {
		var tmp = calcMonthYear(this.myYear, this.myMonth, -1);
		this.myYear = tmp[0];
		this.myMonth = tmp[1];
		this.setContent();
	}

	// ���� ���� ����
	function nextMonth() {
		var tmp = calcMonthYear(this.myYear, this.myMonth, 1);
		this.myYear = tmp[0];
		this.myMonth = tmp[1];
		this.setContent();
	}

	// ���� ������ ����
	function prevYear() {
		this.myYear -= 1;
		this.setContent();
	}

	// ���� ������ ����
	function nextYear() {
		this.myYear += 1;
		this.setContent();
	}

	// ������ ����� ����
	function reload() {
		var obj = document.frm.year.value + document.frm.month.value;

		if ( obj.length < 5) { return; }

		this.myYear = document.frm.year.value;
		this.myMonth = checkNum(document.frm.month.value) - 1;
		this.myDate = "1";
		/*
		for (var i=0; i < d_format.length; i++) {
			if (d_format[i] == this.format) {
				if ( i == 0 || i == 2 || i == 4 ) {
					this.myYear = obj.value.substring(0,4);
					this.myMonth = obj.value.substring(5,7) - 1;
					this.myDate = obj.value.substring(8);
				} else if ( i == 1 || i == 3 || i == 5 ) {
					this.myYear = setYearLength( obj.value.substring(0,2), 4);
					this.myMonth = obj.value.substring(3,5) - 1;
					this.myDate = obj.value.substring(6);
				} else if ( i == 6 ) {
					this.myYear = obj.value.substring(0,4);
					this.myMonth = obj.value.substring(4,6) - 1;
					this.myDate = obj.value.substring(6);
				} else if ( i == 7 ) {
					this.myYear = setYearLength( obj.value.substring(0,2), 4);
					this.myMonth = obj.value.substring(2,4) - 1;
					this.myDate = obj.value.substring(4);
				}
			}
		}
		*/
		this.setContent();
	}

	// html�� �ʵ尪 ����
	function setContent() {
		this.clearContent();

		var tmp = getFormatDate(this.format, this.myYear, (this.myMonth + 1), this.myDate);
		setTextField(this.formName, "year", this.myYear);


		setTextField(this.formName, "month", this.myMonth + 1);
		setTextField(this.formName, "currentDate", this.myYear + "�� " + (this.myMonth + 1) + "��");



		var dates = this.setDates();

		for ( var i = 0; i < dates.length - 1; i++) {

			for ( var j = 0; j < 7; j++) {
				setTextField(this.formName, "tf" + i, dates[i] );
				if (dates[i] == currentDate.getDate() && this.myYear == currentDate.getFullYear() && this.myMonth == currentDate.getMonth() ) setTodayStyle(this.formName, i);
				i++;
				if (i == dates.length ) break;
			}
			i--;
		}

	}

	// html�� �ʵ尪 �����
	function clearContent() {
		eval( "document."+ this.formName + ".reset();");
		var frm = eval( "document."+ this.formName);
		for (var i = 0; i < frm.elements.length ; i++) {
			frm.elements[i].style.fontWeight = 'normal';
		}
	}

	// ������� ������ format ���·� �ٲپ� ����
	function getFormatDate(format, year, month, date) {
		var gubunja = '';
		for (var i=0; i < d_format.length; i++) {
			if (d_format[i] == format) {
				if ( i == 0 || i == 2 || i == 4 ) {
					gubunja = d_format[i].substring(4,5)
					return setYearLength(year, 4) + gubunja + setLength(month, 2) + gubunja + setLength(date, 2);
				} else if ( i == 1 || i == 3 || i == 5 ) {
					gubunja = d_format[i].substring(2,3)
					return setYearLength(year, 2) + gubunja + setLength(month, 2) + gubunja + setLength(date, 2);
				} else if ( i == 6 ) {
					return setYearLength(year, 4) + setLength(month, 2) + setLength(date, 2);
				} else if ( i == 7 ) {
					return setYearLength(year, 2) + setLength(month, 2) + setLength(date, 2);
				}
			}
		}
		return 'no format';
	}

	// year �ڸ��� ���߱�
	function setYearLength(data, length) {
		data = data + '';
		if (length == data.length) {
			return data;
		} else if ( length == 4 && data.length == 2) {
			return "20" + data;
		} else if ( length == 2 && data.length == 4) {
			return data.substring(2);
		}
		return data;
	}

	// month �ڸ��� ���߱�
	function setLength(data, length) {
		for (var i = new String(data).length; i < length; i++) {
			data = '0' + data;
		}
		return data;
	}

	// ���� ����
	function setTextField(formName, name, value) {
		eval( formName + "." + name + ".value = '" + value + "';" );
	}

	// ���� ��¥ ��Ÿ��
	function setTodayStyle(formName, gb) {
		eval( formName + ".tf" + gb + ".style.fontWeight = 'bold';" );
	}

	// ��ȸ�� ��
	function getYear() {
		return this.myYear;
	}

	function getMonth() {
		return (this.myMonth + 1);
	}

	function printText(txt){
		print(txt);
	}


	// html ����
	function show() {

		//printTable(0, this.width, '0', '0', '0');
		//printTr(0, 'right');
		//	printTd(0, '300', '','','','27');
		//	printSearch(this.name);
			//printText("<img src='/img/btn_help.gif' hspace='4' border='0' onClick=MM_openBrWindow('samplepage.html','sample','scrollbars=no,width=620,height=500')></a><a href=#><img src='/img/btn_screen.gif' border='0' onMouseOver=MM_showHideLayers('disinfo','','show') onMouseOut= MM_showHideLayers('disinfo','','hide') hspace='2'></a>");
		//	printTd();
		//printTr();
		printTable();

		printTable(0, this.width, '0', '0', '0');
		printTr(0, '');
			printTd(0, '300', '','','','21');
			printText("<img src=./image/tit_bullet.gif align=absmiddle>");
			printText("<span class=subtit1>��¥ ����</span>");
			printTd();
		printTr();
		printTable();

		printBR();

		printForm(0,this.formName, this.method, this.action);

		printTable(0, this.width, '0', '0', '0');
		printTr(0, 'left');
			printTd(0, '300', '','','','20');
			printImage('./image/titbu_orange.gif','9','17','absmiddle');
			printText("<strong>��ȸ����</strong>");
		printTr();
		printTr(0,'');
			printTd(0, '300', '','','orange');
			printTd();
		printTr();
		printTr(0,'');
			printTd(0, '300', '','','orangel');
			printTd();
		printTr();
		printTable();

		//��ȸ ���� ���
		printTable(0, this.width, '0', 1, 1);
		printTr(0, 'left');
			printTd(0, '70', '','','td_g');
			printYear();
			printTd();
			printTd(0,'80','','');
			printYearInput(this.name);
			printTd();
			printTd(0, '70', '','','td_g');
			printMonth();
			printTd();
			printTd(0,'80','','');
			printMonthInput(this.name);
			printTd();
		printTr();
		printTable();

		printBR();

		printTable(0, this.width, '0', 0, 0);
		printTr(0, 'left');
			printTd(0, '300', '','','','20');
			printImage('./image/titbu_green.gif','9','17','absmiddle');
			print("<strong>��¥ ����</strong>");
			printTd();
		printTr();
		printTr(0,'');
			printTd(0, '300', '','','green');
			printTd();
		printTr();
		printTr(0,'');
			printTd(0, '300', '','','greenl');
			printTd();
		printTr();
		printTr(0,'right');
			printTd(0, '', '','','td_ce','25');
			printPrevYear(this.name);
			printPrevMonth(this.name);
			printCurrentDate(this.myYear, this.myMonth+1, this.name);
			printNextMonth(this.name);
			printNextYear(this.name);
			printTd();
		printTr();
		printTable();

		// ���� ���
		printTable(0, this.width, '0', 1, 2);
		printTr(0, 'center', 'td_g_ce');
		for (var i=0; i < 7; i++) {
			printTd(0);
			printDay(this.dayName, i);
			printTd();
		}
		printTr();
		// �޷� textfield ���
		//printTable(0, this.width, '', 0, 1, 0, 'background-color:'+ this.bgcolor2 +'; line-height:' + this.lineheight);
		for (var i = 0; i < 42; i++) {
			printTr(0, 'center', 'cursor');
			for (var j=0; j < 7; j++) {
				printTd(0,'50','','','cursor');
				printTextField( i, 2, 2, 'color:'+day_color[j] + ';font-size:12px; font-family:����,Verdana, Arial, Helvetica; cursor:hand; border-width:1px; border-color:'+this.tfCol+'; border-style:solid; background-color:'+this.tfCol+'; text-align:center', "window.returnValue= " + this.name + ".getYear() + '-' + makeDigit("+ this.name+".getMonth()) + '-' + makeDigit(this.value) ;window.close();", this.tfCol, this.focusCol);
				printTd();
				i++;
				if (i == 42 ) break;
			}
			i--;
			printTr();
		}
		printTable();
		printForm();

		// ���� �ֱ�
		this.setContent();

	}


/* ------------------------------------------------------------
		6. HTML ���� �Լ� ����
   ------------------------------------------------------------ */
	// ���� HTML ����
	function print(str) {
		document.writeln( str );
	}

	// Form tag
	function printForm(gb, formName, method, action) {
		if (gb == null) {
			print("</form>");
		} else if(gb == 0) {
			print("<form name='" + formName + "' method='" + method + "' action='"+ action +"'>");
		}
	}

	// Table Tag
	function printTable(gb, width, border, cellspacing, cellpadding) {
		if (gb == null) {
			print("</table>");

		} else if(gb == 0) {
			//print("<table width='"+ width +"' height='"+ height +"' border='"+ border +"' cellspacing='"+ cellspacing +"' cellpadding='"+ cellpadding +"' style='"+ style +"'>");
			print("<table width='"+ width + "' border='"+ border +"' cellspacing='"+ cellspacing +"' cellpadding='"+ cellpadding +"' class ='tab'>");
		}
	}

	// Tr Tag
	function printTr(gb, align, d_class) {
		if (gb == null) {
			print("</tr>");
		} else if(gb == 0) {
			print("<tr align='"+ align +"' class ='"+ d_class +"' bgcolor='#ffffff'>");
		}
	}

	// Td Tag
	function printTd(gb, width,colspan, id, d_class,height) {
		if (gb == null) {
			print("</td>");
		} else if(gb == 0) {
			print("<td width='"+ width +"' class ='"+ d_class +"' colspan='"+ colspan +"' id='"+ id +"' height= ' " + height + " '>");
		}
	}

	// ��¥ �ʵ�

	function printDateTF(calObj, name, size, maxlength, style) {
		print("<input type='text' name='" + name + "' size='"+ size +"' maxlength='"+ maxlength +"' style='"+ style +"' onFocus='this.select()' onKeyUp='if (event.keyCode == 13) { "+ calObj +".reload(this);this.select(); }' class='cursor'>");
	}


	// ��¥ ��ȸ ��ư
	function printSearch(calObj) {
		print("<img src='./image/btn_src02.gif' hspace='2' border='0' onclick='javascript:"+calObj+".reload();' style = this.style.cursor='hand';>");
	}

	//
	function printYear(){
		print("��");
	}

	function printMonth(){
		print("��");
	}

	function printYearInput(calObj){
		print("&nbsp;&nbsp;<INPUT name='year' maxlength='4' type='text' class='input' style='width:60px'>");
	}

	function printMonthInput(calObj){
		print("&nbsp;&nbsp;<INPUT name='month' type='text' maxlength='2'  class='input' style='width:60px'>");

	}

	// ���� (gb: 0 ~ 6) ===> �� ~ ��
	function printDay(dayName, gb) {
		print( dayName[gb] );
	}

	// ���� �ʵ�(gb: 0 ~ 41)

	function printTextField(gb, size, maxlength, style, event, tfCol, focusCol) {
		print ("<input type='text'  class='cursor' name='tf" + gb + "' size='"+ size +"' maxlength='"+ maxlength + "' style='" + style + "' readonly tabindex=-1 onClick=\""+ event +"\" this.style.borderColor='"+ focusCol+"';\" onMouseOut=\"this.style.backgroundColor = '"+ tfCol+"';this.style.borderColor='"+ tfCol+"'\">");
	}

	function printImage(src,width,height,align){
		print("<img src='" + src + "' width='" + width + "' height ='" + height + "' align = '" + align + "' border='0'>");
	}

	function printCurrentDate(yyyy, mm, calObj){
		var on_click = "";//"window.returnValue= " + calObj + ".getYear() + '-' + makeDigit("+ calObj + ".getMonth());window.close();"
//		var on_mouseover = "this.style.color=#008080";
		var on_mouseover = "";
		var style = "color:#4040ff; text-decoration:underline; padding-left:3px; padding-right:3px; width:70px; font-size:11px; font-family:����,Verdana, Arial, Helvetica;  border-width:1px; border-color: #ffffff; border-style:solid;";
		print("<input type='text' name='currentDate' value="+yyyy+"��" + mm + "�� style='" + style + "' readonly onClick=\"" + on_click + "\" onMouseOver=\"" + on_mouseover + "\">");
	}

	// ������ ��ũ
	function printPrevMonth(calObj) {
		print("<img src='/webpage/image/btn_pre_m.gif' class='cursor' hspace=0 align='absmiddle' width='50' height='18' onClick='javascript:"+calObj+".prevMonth();'>");
	}

	// ������ ��ũ
	function printNextMonth(calObj) {
		print("<img src='./image/btn_next_m.gif' class='cursor' hspace=0 align='absmiddle' width='50' height='18' onClick='javascript:"+calObj+".nextMonth();'>");
	}

	// ������ ��ũ
	function printPrevYear(calObj) {
		print("<img src='./image/btn_pre_y.gif' class='cursor' hspace=0 align='absmiddle' width='54' height='18' onclick='javascript:"+calObj+".prevYear();'>");
	}

	// ������ ��ũ
	function printNextYear(calObj) {
		print("<img src='./image/btn_next_y.gif' class='cursor' hspace=0 align='absmiddle' width='54' height='18' onClick='javascript:"+calObj+".nextYear();'>");
	}

	function printBR(){
		print("<br>");
	}

	function checkNum(num){
		var result;
		if (num.length > 1 && num.charAt(0) == "0"){
			result = num.charAt(1);
		}
		else{
			result = num;
		}
		return result;
	}

	function makeDigit(num) {
		var result = String(num)
		//var mynum = parseInt(num)

		if (result.length < 2) {
			result = "0" + num
		}

		return result
	}

