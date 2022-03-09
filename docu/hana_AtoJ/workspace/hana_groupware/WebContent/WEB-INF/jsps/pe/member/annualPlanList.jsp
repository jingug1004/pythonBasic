<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : annualPlanList.jsp
 * @메뉴명 : 연차사용계획
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.pe.member.vo.AnnualMgrVO" %>   
<%@ include file ="/common/path.jsp" %>
<%
	StringBuffer annualPlanList = (StringBuffer)request.getAttribute("annualPlanList");	
	AnnualMgrVO annualPlan = (AnnualMgrVO)request.getAttribute("annualPlan");	
	int annualcommonDtCnt = ((Integer)request.getAttribute("annualcommonDtCnt")).intValue();	
	int annualcommonDtCntHalf = ((Integer)request.getAttribute("annualcommonDtCntHalf")).intValue();	
	int year = (Integer)request.getAttribute("year");	
	int month = (Integer)request.getAttribute("month");	
	int day = (Integer)request.getAttribute("day");
	Boolean  annualClosedYN = (Boolean)request.getAttribute("annualClosedYN");
	int startMonth = (Integer)request.getAttribute("startMonth");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	
	/**
	 * 연차 사용계획 등록 폼
	 */
	function goSelectDay(selectDay){
		if(<%=annualClosedYN%>){
			alert("등록기간이 아닙니다.");
			return;
		}else{
			$('#selectDay').bPopup({
				content:'iframe', //'ajax', 'iframe' or 'image'
				iframeAttr:'scrolling="no" frameborder="0" width="330px" height="210px"',
				follow: [true, true],
				contentContainer:'.select_content',
				modalClose: true,
	            opacity: 0.6,
	            positionStyle: 'fixed',
				loadUrl:'<%=GROUP_ROOT%>/pe/member/annualPlanFormPopup.do?rq_fr_dt='+selectDay+'&startMonth=<%=startMonth%>',
			});
		}	
	}
	
	function goDeleteSelectDay(selectDay){
		if(<%=annualClosedYN%>){
			alert("수정기간이 아닙니다.");
			return;
		}else{
			if(confirm("삭제하시겠습니까?") == true){
				document.getElementById("frm").action ="<%=GROUP_ROOT%>/pe/member/annualPlanDelete.do?rq_fr_dt="+selectDay;
				document.getElementById("frm").submit();
			}
		}	
	}
	
	/**
	 * 레이어 팝업 닫기
	 */
	function layerClose(){ 
		$('#selectDay').bPopup().close();
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
			
			<!-- ######## start ####### -->
			<form name="frm" id="frm" method="post" >
				<input type ="hidden" id="rq_fr_dt" name="rq_fr_dt" value="">
			</form>		
			<div class="cont float_left">
				<h2>연차사용계획</h2>
				<div class="wrap_plan_vacation" id="annual">
					<div class="wrap_btn annual">
						<button class="type_01" onclick="javascripr:Commons.previewOpen('annual', '803', '768');">미리보기</button>
					</div>
					<div class="wrap_form">
						<h3 class="tit">연차휴가사용계획서</h3>							
							<%
							float yr_year_used_day = 0;
							if(Float.parseFloat(annualPlan.getYr_year_used_day()) < 0){
								yr_year_used_day = Float.parseFloat(annualPlan.getYr_year_used_day());
							}
							//float reserved_day = annualPlan.getUseable_days() - annualPlan.getUsed_days() + yr_year_used_day;
							float reserved_day = annualPlan.getUseable_days() - annualPlan.getUsed_days_half() + yr_year_used_day;
							//useable_days -  used_days  + if( number( yr_year_used_day ) < 0, number( yr_year_used_day ), 0)							
							%>	
							<table>								
								<tbody>
								<tr>																
									<td>
									<dl>
										<table>								
										<tbody>
										<tr>
										<dt>부서 : <%=StringUtil.nvl(annualPlan.getDept_ko_nm()) %> &nbsp&nbsp&nbsp 성명 : <%=StringUtil.nvl(annualPlan.getEmp_ko_nm()) %></dt>									
										<dd class="clr">상기 본인은 <span><%=annualPlan.getYr_year() %></span>년도 미사용한 연차휴가 <span><%=reserved_day %></span>일에 대해 아래와</dd> 
										<dd class="clr">같이 지정일에 사용하고자 하오니 협조 부탁드립니다.</dd>										
										</tr>
										</tbody>
										</table>
									</dl>
									</td>
									<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
									</td>
									<td>
									<dl>
										<div class="tableA">
										<table>								
										<tbody>
											<tr>
												<td>발생 연차일</td>
												<td><%=annualPlan.getUseable_days()%></td>
												<td>지난해 사용일</td>
												<td><%=yr_year_used_day%></td>
																																				
											</tr>
											<tr>
												<td>상반기 개인연차</td>
												<td><%=annualPlan.getUsed_days_half()- annualcommonDtCntHalf%></td>												
												<td>상반기 공동연차</td>
												<td><%=annualcommonDtCntHalf%></td>			
											</tr>
											<tr>													
												<td>남은 연차일</td>
												<td><span class="fc_b"><%=reserved_day - (annualcommonDtCnt - annualcommonDtCntHalf)%></span></td>										
												<td>하반기 공동연차</td>
												<td><span class="fc_b"><%=annualcommonDtCnt - annualcommonDtCntHalf%></span></td>
											</tr>
										</table>
										</div>
									</dl>			
									</td>									
								</tr>								
								</tbody>
							</table>
						
						<%=annualPlanList.toString() %>
							
						<div class="wrap_sign mt30">
							<p class="date">
								<span><%= year%></span>년 <span><%= month %></span>월 <span><%= day %></span>일
							</p>
							<p class="sign">
								작성자 : <span class="name"></span>(서명 또는 인)
							</p>
						</div>
					</div>
				</div>
			</div>
			<!-- ######## end ######### -->
			
		</div>
	</div>
	
	<%@include file="/include/footer.jsp"%>
	<div id='selectDay' style='display:none; width:auto; height:auto; '>
		<div class='select_content'></div> 
	</div>
</body>
</html>