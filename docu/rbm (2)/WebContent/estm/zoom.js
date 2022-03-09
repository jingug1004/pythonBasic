/*줌인,아웃*/
var nowZoom = 100; //
//var nowHeight = 0;
var maxZoom = 200; // ִ(5)
var minZoom = 80; //

function zoomIn() {
	if (nowZoom == 90)	{
		document.body.style.cssText = "background:url(\'img/bg_line.jpg\') repeat-x;";
	} else {
		document.body.style.cssText = "background:url();";
	}
	if (nowZoom < maxZoom) {
		nowZoom += 10; //25%
//		nowHeight += 1;
	} else {
		nowZoom = maxZoom;
	}
	document.body.style.zoom = nowZoom + "%";
//	document.body.style.backgroundPositionY = nowHeight + '%';
}
function zoomOut() {
	if (nowZoom == 110)	{
		document.body.style.cssText = "background:url(\'img/bg_line.jpg\') repeat-x;";
	} else {
		document.body.style.cssText = "background:url();";
	}
	if (nowZoom> minZoom) {
		nowZoom -= 10; //25%
//		nowHeight -= 1;
	} else {
		nowZoom = minZoom;
	}
	document.body.style.zoom = nowZoom + "%";
//	document.body.style.backgroundPositionY = nowHeight + '%';
}