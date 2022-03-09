function checkOS()
{
	var strOS = 'VISTA';

	if( navigator.appVersion.indexOf('Windows NT 6.0') != -1)
		strOS = 'VISTA';
	else if( navigator.appVersion.indexOf('Windows NT 6.1') != -1)	//windows 7
		strOS = 'VISTA';
	else if( navigator.appVersion.indexOf('Windows 98') != -1)
		strOS = '98';
	else if( navigator.appVersion.indexOf('Windows NT 5.0') != -1)
		strOS = '2000';
	else if( navigator.appVersion.indexOf('Windows NT 5.2') != -1)
		strOS = 'XP';
  	else if( navigator.appVersion.indexOf('Windows NT 5.1') != -1)
  		strOS = 'XP';
  	return strOS;
}

function Is98()
{
  var bChk = false;
  if( navigator.appVersion.indexOf('Windows 98') != -1)
  	bChk = true;
  return bChk;
}

function Is2000()
{
  var bChk = false;
  if( navigator.appVersion.indexOf('Windows NT 5.0') != -1)
  	bChk = true;
  return bChk;
}

function Is2003()
{
  var bChk = false;
  if( navigator.appVersion.indexOf('Windows NT 5.2') != -1)
  	bChk = true;
  return bChk;
}

function IsXp()
{
  var bChk = false;
  if( navigator.appVersion.indexOf('Windows NT 5.1') != -1)
  	bChk = true;
  return bChk;
}

function IsAfterVista()
{
  var bChk = false;
  if( navigator.appVersion.indexOf('Windows NT 6.0') != -1)
  	bChk = true;
  return bChk;
}


function CreateMiInstlr(strID, Device,Version,Key)
{
	document.write('<object id="'+strID+'" classid="clsid:1A000B1F-B285-4fbf-B3CD-B50845003EBA" width="0" height="0" CodeBase="./320U/MiPlatform_Updater320_20071011_0919.cab#VERSION=2007,8,29,1" SHOWASTEXT>' +
		'<PARAM NAME="DeviceType" VALUE="'+Device+'" >' +
		'<PARAM NAME="Version" VALUE="'+Version+'" >' +
		'<PARAM NAME="key" VALUE="'+Key+'" >' +
		'</object>' );
}

function ObjectMiInstlr(strID, Device,Version,Key)
{
	document.write('<object id="'+strID+'" classid="clsid:1A000B1F-B285-4fbf-B3CD-B50845003EBA" width="0" height="0" SHOWASTEXT>' +
		'<PARAM NAME="DeviceType" VALUE="'+Device+'" >' +
		'<PARAM NAME="Version" VALUE="'+Version+'" >' +
		'<PARAM NAME="key" VALUE="'+Key+'" >' +
		'</object>' );
}
