<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : storeListPopup.jsp
 * @메뉴명 : 엑셀 데이터 팝업
 * @최초작성일 : 2017/11/28
 * @author : 김진국
 * @수정내역 : 
--%>
<%@ include file ="/common/path.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>엑셀주문서업로드</title>
	<%@ include file ="/include/head.jsp" %>
	<!--[if lte IE 9]><script src="asset/js/IE9.js"></script><![endif]-->
	<!--[if lte IE 9]><script src="asset/js/html5.js"></script><![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=ONLINE_WEB_ROOT%>/css/ui.jqgrid.css">
	<script type="text/javascript" src="<%=ONLINE_WEB_ROOT%>/js/grid.locale-kr.js"></script>
	<script type="text/javascript" src="<%=ONLINE_WEB_ROOT%>/js/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%=ONLINE_WEB_ROOT%>/js/order.js"></script>
	<script type="text/javascript">
		/**
		*	엑셀 업로드 버튼 클릭시
		*/
		function excelUpload(){
			
			if($("#excelFile").val() == ""){
				alert("파일을 등록해주세요.");
				return;
			}
			
			var formData = new FormData();
		    formData.append("excelFile", $("input[name=excelFile]")[0].files[0]);
		    
	    	$.ajax({
	           url: "<%=ONLINE_ROOT%>/order/common/excelUpload.do",
	           processData: false,
	           contentType: false,
	           data: formData,
	           type: 'POST',
	           cache: false, 
	           success: function(data){
	        	   if(data.result == "Y"){
	        		   var rcust_id = data.rcust_id; 
	        		   var item_id = data.item_id;
	        		   
	        		   var resultTxt = "";
	        		   if(rcust_id != ""){
	        			   resultTxt = "등록되지 않은 판매처코드 : " + rcust_id;
	        		   }
	        		   
	        		   if(item_id != ""){
	        			   if(resultTxt == ""){
	        				   resultTxt = "등록되지 않은 식약처표준코드 : " + item_id;
	        			   }else{
	        				   resultTxt += "\n\n등록되지 않은 식약처표준코드 : " + item_id;
	        			   }
	        		   }
	        		   
	        		   if(resultTxt != ""){
	        		       alert(resultTxt + "\n\n확인후 진행해주세요.");
	        		   }
	        		   alert('판매처코드별로 배송지 확인 및 요청사항 입력 후 저장 하십시요.');
	        		   $(opener.location).attr("href", "javascript:excelStoreGridList();");
	        		   window.self.close();
	        	   }else{
	        		   if(data.result == "1"){
	        			   alert("엑셀파일이 아니거나 xls확장자가 아닙니다. \n\n엑셀주문서 작성방법 안내 팝업의 샘플양식을 \n\n다운로드하여 이용해주세요.");
	        		   }else if(data.result == "2"){
	        			   alert("판매처,배송지,식약처코드의 중복된 값이 있습니다. \n엑셀 데이터를 확인해주세요.");
	        		   }else{
	        			   alert("엑셀 업로드에 실패했습니다. \n다시 시도해주세요.");
	        		   }
	        	   }
	           }
	       });
		}
	</script>
</head>

<body>
	<div class="popup">
		
		<!-- ##### content start ##### -->
		<!-- window size : 600 * 200 -->
		
		<form id="frm" name="frm" method="post" enctype="multipart/form-data">
		
			<h1 class="tit">엑셀 데이터 업로드</h1>
			
			<div class="wrap_pop_search">
				<label>엑셀</label>
				<input type="file" id="excelFile" name="excelFile" style="width: 400px" />
				<button type="button" onclick="excelUpload();" id="uploadBtn" >업로드</button>
			</div>
			
			<button type="button" class="close"><span class="blind">닫기</span></button>
		
		</form>
		
		<!-- ##### content end ##### -->
	
	</div>
	<%@ include file ="/include/footer_pop.jsp" %>
</body>

</html>
