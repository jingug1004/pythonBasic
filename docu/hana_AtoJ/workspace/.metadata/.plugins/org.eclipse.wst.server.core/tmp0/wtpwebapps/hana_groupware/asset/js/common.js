/** 
 * @파일명 : common.js
 * @기능 : 공통 스크립트. 
 * @Comments : Commons.함수명() 형태로 호출.
 * 				공통적으로 적용되는 키 이벤트 및 ajax error 이벤트 설정.
 * @최초작성일 : 2014/10/17
 * @author : 김재갑  
 * @수정내역 : 
 */
var Commons = {
		
		/**
		 * 윈도우 open팝업 공통 스크립트	
		 * @param name
		 * @param url
		 * @param width
		 * @param height
		 */
		popupOpen : function(name,url,width,height){
			var sw  = screen.availWidth ;
			var sh  = screen.availHeight ;
			var top = 0;
			var left = 0;
			
			// 화면 중앙 위치
			px=(sw - width)/2 ;
			py=(sh - height)/2 ;
			
			var set  = 'top=' + py + ',left=' + px ;
			set += ',width=' + width + ',height=' + height + ',toolbar=0,resizable=1,status=0,scrollbars=1';

			var openWindow = window.open('',name,'width='+width+', height='+height+', top='+top+', left='+left+','+set);
			openWindow.location.href = url;
		},
		
		/** 금액 3자리 마다 콤마찍기
		 *	콤마를 더한 금액을 반환한다.
		 */
		addComma : function(str) {
		    str = String(str);
		    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
		},
		
		/**
		 * 브라우저 종류 체크
		 * @param void
		 */
		browserCheck : function() {
			var result = '';
			switch(navigator.appName) {
				case 'Netscape':
					result = 'FF';
					break;
				default:
					result = 'IE';
					break;
			}
			return result;
		},
		
		/**
		 * 오브젝트에 이벤트 생성
		 * @param obj 오브젝트
		 * @param evt 이벤트 이름
		 * @param fn 리턴하는 함수이름get
		 */
		addEvent : function(obj, evt, fn) {
			switch(__BR__) {
				case 'IE':
					obj.attachEvent(evt, fn);
					break;
				default:
					evt = evt.replace('on', '');
					obj.addEventListener(evt, fn, true);
					break;
			}
		},
		
		/**
		 * 세션이 끊겼을때 로그인 페이지로 
		 * @param msgType	메시지 알림 타입.
		 */
		sessionOut : function(msgType){
			if(typeof msgType != undefined && msgType == 'C'){
				if(!confirm("로그인 정보가 없습니다. 로그인 화면으로 이동하시겠습니까?")){
					return;
				}
			} else if(typeof msgType != undefined && msgType == 'A'){
				alert("로그인 정보가 없습니다. 로그인 화면으로 이동하겠습니다.");
			} 
			
			if(parent && window!=window.top){	// iframe
				top.Commons.goLogin();
			} else if(opener){	//팝업창
				if(opener.parent && opener.top && opener.top != opener && opener.parent.Commons){	//opener가 iframe
					opener.top.Commons.goLogin();
					self.close();
				} else {	// opener가 top frame
					try{
						if(opener.Commons){
							opener.Commons.goLogin();
							self.close();
						} else {
							Commons.goLogin();
						}
					}catch(e){
						Commons.goLogin();
					}
					
				}
			} else {	//top frame
				Commons.goLogin();
			}
		},
		
		/**
		 * 로그인 페이지로 이동
		 */
		goLogin : function(){
			location.href = "/hanagw/co/login/login.do";
		},
		
		/**
		 * ajax error 처리
		 * @param event	event
		 * @param jqxhr	jqxhr
		 * @param settings	settings
		 */
		ajaxError : function(event, jqxhr, settings) {
			if(jqxhr.status == '200'){/*200은 성공*/}
			else if(jqxhr.status == '403'){Commons.sessionOut('A');}
			else if(jqxhr.status == '404'){alert("요청 URL을 찾을 수 없습니다. ( " + settings.url + ")");}
			else {console.log("Commons.ajaxError : code : " + jqxhr.status + "\r\nmessage : " + jqxhr.reponseText);}
		},
		
		/**
		 * 회원정보 팝업
		 */
		memberPopup : function(emp_no){
			Commons.popupOpen('mem_pop','/hanagw/pe/member/memberPopup.do?emp_no='+emp_no, '435', '239');	
		},
		
		/**
		 * 미리보기 팝업 공통 스크립트	(팝업에서 인쇄 다시 선택)
		 * @param name
		 * @param url
		 * @param width
		 * @param height
		 */
		previewOpen : function(name,width,height){
			Commons.popupOpen(name, '/hanagw/co/common/commonPrint.do', width, height);	
		},
		
		/**
		 * 미리보기 팝업 공통 스크립트	(바로인쇄)
		 * @param name
		 * @param url
		 * @param width
		 * @param height
		 */
		directPreviewOpen : function(name,width,height){
			Commons.popupOpen(name, '/hanagw/co/common/commonPrintDirect.do', width, height);	
		},
		
		/**
		 * 미리보기 팝업 공통 스크립트	(플러그인 사용)
		 * @param name
		 * @param url
		 * @param width
		 * @param height
		 */
		pluginPreviewOpen : function(name,width,height){
			Commons.popupOpen(name, '/hanagw/co/common/commonPrintPlugin.do', width, height);	
		},
		
		/**
		 * 배열에 데이터 셋팅
		 * @param array	배열명
		 * @param value	값
		 */
		addArray : function(array, value){
			// 배열에 같은 값 존재여부 검색
			var isExist = false; // 존재여부 flag
			for (var i = 0; i < array.length; i++) { // value 위치 검색
				if (array[i] == value) {
					isExist = true;
					break;
				}
			}
			
			if (!isExist) { // 없을 경우
				array.push(value); // 배열에 현재 value 더함	
			}
		},
		
		/**
		 * XSS 필터
		 * @param content 필텅링할 데이터
		 */
		XSSfilter : function( content ) {
		    return content.replace(/&/g, '&amp;')
		      .replace(/</g, '&lt;')
		      .replace(/>/g, '&gt;')
		      .replace(/\"/g, '&quot;')
		      .replace(/\'/g, '&#39;')
		      .replace(/\//g, '&#x2F;');
		},
		
		/**
		 * 파일 전체 다운로드
		 * @param idx 인덱스
		 */
		fileDownloadAll : function(idx) {
			
			/*첨부파일 영역 하위 a태그(다운로드 url)를 실행한다*/
			if (idx < $(".attachList a").size()) {
				location.href = $(".attachList").find("a:eq("+idx+")").attr("href");
				setTimeout(function(){Commons.fileDownloadAll(idx+1);}, 500); // 0.5초 지연 후 배열의 다음 인덱스를 재귀호출
			}
		}
};
/**
 * 브라우저 구분
 */
var __BR__ = Commons.browserCheck();

$(document).ajaxError(function(event, jqxhr, settings) {
	Commons.ajaxError(event, jqxhr, settings);
});