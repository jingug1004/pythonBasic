/*
filedrag.js - HTML5 File Drag & Drop demonstration
Featured on SitePoint.com
Developed by Craig Buckler (@craigbuckler) of OptimalWorks.net
*/
	
	var listNum = 0;
	var list = [];
	var file_list;	//최종으로 파일정보 담기는 배열

	// output information
	function Output(msg) {
		var messages = $("#messages");
		messages.append(msg);
	}

	// file drag hover
	function FileDragHover(e) {
		e.stopPropagation();
		e.preventDefault();
		e.target.className = (e.type == "dragover" ? "hover" : "");
	}

	// file selection
	function FileSelectHandler(e) {
		// cancel event and hover styling
		FileDragHover(e);

		// fetch FileList object
		var files;
		try{
			files = e.target.files || e.dataTransfer.files;	
		}catch(ee){
			// ie 드래그시 오류로 인해 처리
			files = e.originalEvent.dataTransfer.files;	
		}
		
		// process all File objects
		for (var i = 0, f; f = files[i]; i++) {
			ParseFile(f);
			list.push(f);
		}
		$("#fileselect").val("");
		//console.log("file list.length = " + list.length);
		//console.log(list);
	}
	
	// 글쓰기 페이지에서 파일업로드 호출 메서드
	function uploadNext() {
		console.log("file_list.length : "+file_list.length);
		for (var int = 0; int < file_list.length; int++) {
			var nextFile = file_list[int];
			multiUploadFile(nextFile);	//ajax 파일업로드
			
			//console.log("nextFile="+nextFile);
		}
	}
	
	// file upload
	function multiUploadFile(file) {
		
		var fd = new FormData(); // https://hacks.mozilla.org/2011/01/how-to-develop-a-html5-image-uploader/
		fd.append("image", file);
		
		//fd.append("seq", $('#seq').val());
		fd.append("cd", $('#cd').val());
		fd.append("filePathKind", $('#filePathKind').val());
		
		var xhr = new XMLHttpRequest();
		xhr.open("POST", "/hanagw/imgMultiFileUploadAction.do", false);
		xhr.onload = function() {
			try {
				var res = JSON.parse(xhr.responseText);			// 업로드한 파일과 게시글 매핑시 사용함.
				var fileNum = res.file_seq;
			} catch (e) {
				console.log(e);
				return this.onError();
			}
			$('#fileNum').val($('#fileNum').val()+fileNum+",");
		};
		xhr.onerror = function() {};
		xhr.upload.onprogress = function(e) {
			//this.setProgress(e.loaded / e.total);
		};
		xhr.send(fd);
	}

	// output file information
	function ParseFile(file) {
		Output(
			"<p id=\"file_" + (++listNum) + "\">" + file.name +
			" (" + file.size + " bytes)"+
			//"&nbsp;&nbsp;<a href=\"javascript:fileDelete(" + listNum + ");\">파일삭제</a></p>"
			"<button type=\"button\" class=\"type_01 ml15\" onclick=\"javascript:fileDelete(" + listNum + ");\">삭제</button>"
		);
	}

	// initialize
	function fileDragInit() {
		//file 태그 변경시 세팅
		var fileselect = $("#fileselect");
		fileselect.on('change', function(e) {
			FileSelectHandler(e);
		});
		
		//file 드래그 & 드랍 세팅
		var filedrag = $("#filedrag");
		filedrag.show();
		filedrag.on('dragenter', function(e) {
			FileDragHover(e);
		});
		filedrag.on('dragover', function(e) {
			FileDragHover(e);
		});
		filedrag.on('drop', function(e) {
			FileSelectHandler(e);
		});
		
		//메시지창
		var messages = $("#messages");
		messages.show();
	}
	
	// file delete
	function fileDelete(num) {
		
		delete list[num-1];
		$('#file_'+(num)).remove();

		//console.log("list.length=" + list.length);
		//console.log("listNum=" + listNum);
		//console.log(list);
	}
	
	// 업로드 파일 존재 여부
	function fileUploadCnt() {
		file_list = [];
		for (var int = 0; int < list.length; int++) {
			if(typeof list[int] != "undefined"){	//삭제된 파일이 아니면
				file_list.push(list[int]);	//file_list에 최종 파일 담기
			}
		}
		
		return file_list.length;
	}
