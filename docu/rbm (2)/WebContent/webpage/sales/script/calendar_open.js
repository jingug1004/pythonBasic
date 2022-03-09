function calendar_open(date_value){
	var myOption    = 'dialogWidth:390px;dialogHeight:420 px;   center:yes; help:no; status:no; scroll:yes; resizable:no';
	var dateValue =showModalDialog('calendar.html',window,myOption);
	if (dateValue != null) {
		eval(date_value).value = dateValue;
	}

}