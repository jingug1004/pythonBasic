<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : previewE01004.jsp
 * @메뉴명 : 결재문서 미리보기-기화기기안서
 * @최초작성일 : 2015/01/28            
 * @author : 정진묵           
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO"%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.VaporizeVO" %>
<%
	/* 기화기기안서 */
	VaporizeVO vaporizeVO = (VaporizeVO)request.getAttribute("vaporizeVO");
	List<CodeVO> sCodeList = (List<CodeVO>)request.getAttribute("sCodeList");
	
	if(vaporizeVO == null){
		vaporizeVO = new VaporizeVO();
	}
%>
<table class="tbl_vaporizer">
	<colgroup>
		<col style="width:99px">
		<col style="">
		<col style="width:99px">
		<col style="">
	</colgroup>
	<tbody>
		<tr>
			<th>시행일자</th>
			<td colspan="3" class="bdr2"><%=StringUtil.nvl(vaporizeVO.getImposition_ymd()) %></td>
		</tr>						
		<tr>
			<th>종류</th>
			<td colspan="3" class="bdr2">
				<ul>
				<%
				if(sCodeList.size()!=0){
					for(int i=0; sCodeList.size()>i; i++){
						CodeVO codeVo = new CodeVO();
						codeVo=sCodeList.get(i);
				%>
					<li <%if(i+1==sCodeList.size() || i+1==sCodeList.size()-1){%> class="mbn"<%} %>>
						<input type="radio" name="kind_cd" id="kind_cd" onclick="javascript:inner_text('<%=codeVo.getCd_nm() %>');" value="<%=codeVo.getCd()%>" <%if(StringUtil.nvl(vaporizeVO.getKind_cd()).equals(codeVo.getCd())){%>checked<%}%> disabled/>
						<label for="kind_cd"><%=codeVo.getCd_nm() %></label>
					</li>	
				<%
					}
				}
				%>
				</ul>
			</td>
		</tr>
		<tr>
			<th>1.거래처명</th>
			<td><%=RequestFilterUtil.toHtmlTagReplace("", vaporizeVO.getCust_nm()) %></td>
			<th>2.거래처코드</th>
			<td class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", vaporizeVO.getCust_cd()) %></td>
		</tr>	
		<tr>
			<th>3.대표자명</th>
			<td colspan="3" class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", vaporizeVO.getCeo_nm()) %></td>
		</tr>
		<tr>
			<th>4.기종 및 수량</th>
			<td><%=StringUtil.nvl(vaporizeVO.getModel_qty()) %></td>
			<th>5.월사용수량</th>
			<td class="bdr2"><%=StringUtil.nvl(vaporizeVO.getMonth_use_qty()) %></td>
		</tr>	
		<tr>
			<th>6.내용</th>
			<td colspan="3" class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", vaporizeVO.getContent()) %></td>
		</tr>
		<tr>
			<th>7. 특이사항</th>
			<td colspan="3" class="bdr2 ta">
				<div><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", vaporizeVO.getUnusual())) %></div>
			</td>
		</tr>	
	</tbody>
</table>