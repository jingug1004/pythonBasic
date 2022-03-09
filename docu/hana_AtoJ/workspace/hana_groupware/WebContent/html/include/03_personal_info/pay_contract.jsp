<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>
</head>
<body>
	<%@include file="../../include/header.jsp"%>
	<%@include file="../../include/gnb.jsp"%>
	
	<div class="wrap_con">
		<div class="content">
			<%@include file="../../include/location.jsp"%>
			<%@include file="../../include/lnb.jsp"%>
			
			<!-- ######## start ####### -->
			
<div class="cont float_left">
	<h2>연봉계약서</h2>
	<div class="wrap_pay_contract">
		<div class="wrap_btn">
			<button class="type_01">목록</button><!-- 11.17 추가 -->
			<button class="type_01">미리보기</button>
		</div>
		<div class="wrap_list inner">
			<h3>연봉계약서</h3>
			<p class="tit_txt01">- 본인은 연봉제 관련 제반규정과 설명 자료를 충분히 숙지하였으며, 다음과 같이 연봉계약을 체결합니다 -</p>
			<p class="tit_txt02">본인은 당사의 연봉제도에 동의하며, 능력 및 업적평가에 따라 지급되는 연봉액수에 대해서도 긍정적인 자세로 수용하고,
				    책정된 연봉을 타인에게 공표하거나 타인의 연봉에 대해서도 알려고 하지 않겠습니다. 또한 본 계약을 위반할 시는 어떠한
				    처벌도 감수하며 절대로 이의를 제기하지 않을 것을 서약합니다.
			</p>

			<p class="tit_txt03">1.연봉계약자 인적사항</p>
			<table class="type01 mt10">
				<colgroup>
					<col style="width:95px" />
					<col style="width:146px" />
					<col style="width:96px" />
					<col style="width:148px" />
					<col style="width:95px" />
					<col style="width:142px" />
				</colgroup>
				<tbody>
					<tr>
						<th>소속</th>
						<td>전산팀</td>
						<th>직급</th>
						<td>사원</td>
						<th>성명</th>
						<td>홍길동</td>
					</tr>
				</tbody>
			</table>
			<p class="tit_txt03">2.연봉계약자 내용</p>
			<div class="cont_txt">
				<p class="cont_txt_type01">1) 계약기간 : 2014.01.01 ~ 2014.12.31</p>
				<p class="cont_txt_type01">2) 연봉 계약 내용</p>
				<p class="cont_txt_type02">① 연봉 계약금액</p>
				<table class="type02 mt10">
					<colgroup>
						<col style="width:361px" />
						<col style="width:361px" />
					</colgroup>
					<thead>
						<tr>
							<th>연봉지급액</th>
							<th>해당기간 퇴직금</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>금 20,000,000 원</td>
							<td>2,000,000 원</td>
						</tr>
					</tbody>
				</table>
				<p class="cont_txt_type02">* 인센티브가 발생될 경우 별도 지급한다. </p>
				<p class="cont_txt_type02">* 연장근로수당 등의 법정 수당과 가족수당, 식대보조금은 별도 지급한다</p>
				<p class="cont_txt_type01 mt10">3) 통상임금은 상여금을 제외한 기본급여로 한다.</p>
				<p class="cont_txt_type01">(단, 상여금은 1달 만근시에만 지급한다.)</p>
				<p class="cont_txt_type01">4) 지급 방법 : 취업규칙에서 정한 지급일에 지급한다.</p>
			</div>
			<p class="tit_txt04 mt10">3.퇴직금 : <span>퇴직금은 퇴직연금에 가입 하여 퇴직연금제도에 따른다.</span></p>
			<p class="tit_txt04">4.신 분 : <span>본 계약은 상기 계약기간 동안의 임금에 대해서만 적용하며, 기타 신분과 관련된 제반사항은 취업규칙에서 정한바에 의한다.</span></p>
			<p class="tit_txt04">5.기타 근로조건 : <span>본 계약서에 명시하지 않은 사항은 취업규칙 및 사규에 의한다.</span></p>
			<p class="tit_txt04 mt10">※ 본 연봉계약을 증명하기 위해 당사자가 서명, 날인하고 각각 1부씩 보관한다. </p>
			
			<p class="date mt40"><span>2014년</span><span>04월</span><span>01일</span></p>
			
			<div class="wrap_signed mt40">
				<div class="wrap_worker">
					<p class="tit">근로자</p>
					<dl>
						<dt>소&nbsp;&nbsp;&nbsp;&nbsp;속 : </dt>
						<dd>배송과(하길)</dd>
						
						<dt>직&nbsp;&nbsp;&nbsp;&nbsp;급 : </dt>
						<dd>부장대우</dd>
						
						<dt>성&nbsp;&nbsp;&nbsp;&nbsp;명 : </dt>
						<dd><span class="name">박일선</span>인</dd>
					</dl>
				</div>
				<div class="wrap_user">
					<p class="tit">사용자</p>
					<p class="txt">
						경기도 화성시 향남읍 제약공단 3길 133<br />
						하나제약주식회사<br />
						대표이사 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="name">장사정</span> 인
					</p>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- ######## end ######### -->
			
		</div>
	</div>
	
	<%@include file="../../include/footer.jsp"%>
</body>
</html>