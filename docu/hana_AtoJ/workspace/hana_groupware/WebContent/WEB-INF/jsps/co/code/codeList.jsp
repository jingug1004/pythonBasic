<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : codeList.jsp
 * @메뉴명 : 코드리스트
 * @최초작성일 : 2015/02/10            
 * @author : Jung.Jin.Muk(pc123pc@irush.co.kr)                 
 * @수정내역 : 
--%>
<%@ include file ="/common/path.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%
	List<CodeVO> codeList = (List<CodeVO>)request.getAttribute("codeList"); //코드리스트
	String searchKeyword = (String)request.getAttribute("searchKeyword"); //검색키워드
	String searchType = StringUtil.nvl((String)request.getAttribute("searchType"),"cd"); //검색타입
	
	searchKeyword = StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", searchKeyword));
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
		$(window).load(function() {
			$("#searchType > option[value=<%=searchType%>]").attr("selected","true");
		});
		
		/**
		 * 코드 검색
		 */
		function search(){
			if(formCheck.containsChars(document.getElementById("searchKeyword").value, "%")){
				 alert("특수문자를 사용할수 없습니다");
				 return;
			}
			searchForm.action="<%=GROUP_ROOT%>/co/code/codeList.do";
			searchForm.submit();
		}
		
		/**
		 * 코드 상세 iframe
		 * @param i
		 */
		function code_detail(i){
			var m_cd = $("#m_cd"+i).text();
			var s_cd = $("#s_cd"+i).text();
			
			var data = "?m_cd=" + m_cd + "&s_cd=" + s_cd;
			$.ajax({
			    type : "POST"
			    , async : true
			    , url : "<%=GROUP_ROOT%>/co/code/detailCodeAjax.do" 
			    , dataType : "json"
			    , data : {
			    			m_cd:m_cd,
				    		s_cd:s_cd
						 }
			    , success : function(result) {
			    	if(result){
			    		detail.location.href ="<%=GROUP_ROOT%>/co/code/detailCode.do"+data;
			    	}
			    }
			    , error : function(request, status, error) {
			    	Commons.sessionOut('A');
				   //  alert("code : " + request.status + "\r\nmessage : " + request.reponseText);
			    }
			    , beforeSend: function() {
				     $('#ajax_indicator').show().fadeIn('fast');
			    }
			    , complete: function() {
				     $('#ajax_indicator').fadeOut();
			    }
			});
		}
	</script>
</head>
<body>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/gnb.jsp"%>
	<div class="wrap_con">
		<div class="content">
			<%@include file="/include/location.jsp"%>
			<%@include file="/include/lnb.jsp"%>
			<div class="cont float_left">
				<h2>코드관리</h2>
				<div class="management_box">
					<div class="cont_box_l fl">
					<div>
					<form id="searchForm" name="searchForm" method="post">
						<div class="serch_box">
							<select id="searchType" name="searchType">
								<option value="cd">코드</option>
								<option value="cd_nm">코드이름</option>
							</select>
							<input type="text" id="searchKeyword" name="searchKeyword" value="<%=searchKeyword%>" onKeyPress="if(event.keyCode=='13'){goSearch(); return false;}" >
							<button type="button" onClick="javascript:search();">검색</button>
						</div>
					</form>
					</div>
					<div class="cont_box01">				
					<form id="frm" name="frm" method="post" >
						<table>
							<colgroup>
								<col width="17%" />
								<col width="18%" />
								<col width="34%" />
								<col width="18%" />
								<col width="12%" />
							</colgroup>
							<tbody>
							<tr>
								<th>코드</th>
								<th>메인코드</th>
								<th>코드명</th>
								<th>사용여부</th>
								<th class="br_none">순서</th>
							</tr>
							<%
							if(codeList.size()!=0){
								for(int i=0; codeList.size()>i; i++){
									CodeVO codeVo = new CodeVO();
									codeVo=codeList.get(i);
							%>
							<tr onclick="javascript:code_detail('<%=i%>');"  style='cursor:pointer;' class="bg_d" >
								<td id="cd<%=i%>" <%if(i+1 == codeList.size()){%>class="bb_none"<%}%>><%=codeVo.getCd()%></td>
								<td id="m_cd<%=i%>" class="fc_re<%if(i+1 == codeList.size()){%> bb_none<%}%>"><%=codeVo.getM_cd()%></td>
								<td id="s_cd<%=i%>" <%if(i+1 == codeList.size()){%>class="bb_none"<%}%> style="display: none;"><%=codeVo.getS_cd()%></td>
								<td id="cd_nm<%=i%>"<%if(i+1 == codeList.size()){%>class="bb_none"<%}%>><%=codeVo.getCd_nm()%></td>
								<td id="descr<%=i%>" <%if(i+1 == codeList.size()){%>class="bb_none"<%}%> style="display: none;"><%=codeVo.getDescr()%></td>
								<td id="use_yn<%=i%>" <%if(i+1 == codeList.size()){%>class="bb_none"<%}%>><%=codeVo.getUse_yn()%></td>
								<td id="ordering<%=i%>" class="br_none<%if(i+1 == codeList.size()){%> bb_none<%}%>"><%=codeVo.getOrdering()%></td>
							</tr>
							<%
								}
							}
							%>
							</tbody>
						</table>
						</form>
					</div>
					<div class="paging01">
						<%@ include file ="/common/paging.jsp" %>
					</div>		
					</div>
					<div class="menu_r fr">
						<iframe src="<%=GROUP_ROOT %>/co/code/detailCode.do" id="detail" name="detail" frameBorder="0" border="0" width="422px" height="495px" style="overflow:hidden"></iframe>
					</div>
				</div>
				<font color="red">※메인코드문자  O:공지/게시/쪽지, E:전자결재, H:인사관리, P:개인정보, Y:연말정산, C:공통/코드/권한/메뉴</font>
			</div>
		</div>
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>