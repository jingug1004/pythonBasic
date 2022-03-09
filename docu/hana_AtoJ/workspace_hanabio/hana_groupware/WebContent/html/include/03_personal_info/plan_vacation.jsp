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
				<h2>개인정보수정</h2>
				<div class="wrap_plan_vacation">
					<div class="wrap_btn">
						<button class="type_01">미리보기</button>
					</div>
					<div class="wrap_form">
						<h3 class="tit">연차휴가사용계획서</h3>
						<dl>
							<dt>부서 : </dt>
							<dd>서울로컬1호점</dd>
							
							<dt>성명 : </dt>
							<dd>박세호</dd>
							
							<dt class="blind">내용</dt>
							<dd class="clr">상기 본인은 <span>2014</span>년도 미사용한 연차휴가 <span>5</span>일에 대해 아래와 같이 지정일에 사용하고자 하오니 협조 부탁드립니다.</dd>
						</dl>
						<p>&lt;아 래&gt;</p>
						<p class="footnote">
							* 공동연차 사용일수 : <span class="fc_b">9</span>일<br />
							공동연차 일은 반드시 <span class="fc_b">O표시</span> 해주시기 바랍니다.<br />
							(O표시로 사용일자 표시)
						</p>
						
						<div class="group_calendar">
							<div class="float_l">
								<h4 class="month">7월</h4>
								<table>
									<colgroup>
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
									</colgroup>
									<thead>
										<tr>
											<th class="fc_r">일요일</th>
											<th>월요일</th>
											<th>화요일</th>
											<th>수요일</th>
											<th>목요일</th>
											<th>금요일</th>
											<th class="fc_b">토요일</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="fc_r">&nbsp;</td>
											<td>&nbsp;</td>
											<td>1</td>
											<td>2</td>
											<td>3</td>
											<td>4</td>
											<td class="fc_b">5</td>
										</tr>
										<tr>
											<td class="fc_r">6</td>
											<td>7</td>
											<td>8</td>
											<td>9</td>
											<td>10</td>
											<td>11<span class="blk">공동연차</span></td>
											<td class="fc_b">12</td>
										</tr>
										<tr>
											<td class="fc_r">13</td>
											<td>14</td>
											<td>15</td>
											<td>16</td>
											<td>17</td>
											<td>18</td>
											<td class="fc_b">19</td>
										</tr>
										<tr>
											<td class="fc_r">20</td>
											<td>21</td>
											<td>22</td>
											<td>23</td>
											<td>24</td>
											<td>25</td>
											<td class="fc_b">26</td>
										</tr>
										<tr>
											<td class="fc_r">27</td>
											<td>28</td>
											<td>29</td>
											<td>30</td>
											<td>31</td>
											<td>&nbsp;</td>
											<td class="fc_b">&nbsp;</td>
										</tr>
									</tbody>
								</table>
							</div>
							
							<div class="float_r">
								<h4 class="month">8월</h4>
								<table>
									<colgroup>
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
									</colgroup>
									<thead>
										<tr>
											<th class="fc_r">일요일</th>
											<th>월요일</th>
											<th>화요일</th>
											<th>수요일</th>
											<th>목요일</th>
											<th>금요일</th>
											<th class="fc_b">토요일</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="fc_r">&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>1</td>
											<td class="fc_b">2</td>
										</tr>
										<tr>
											<td class="fc_r">3</td>
											<td>4</td>
											<td>5</td>
											<td>6</td>
											<td>7</td>
											<td>8</td>
											<td class="fc_b">9</td>
										</tr>
										<tr>
											<td class="fc_r">10</td>
											<td>11</td>
											<td>12</td>
											<td>13</td>
											<td>14</td>
											<td>15</td>
											<td class="fc_b">16</td>
										</tr>
										<tr>
											<td class="fc_r">17</td>
											<td>18</td>
											<td>19</td>
											<td>20</td>
											<td>21</td>
											<td>22</td>
											<td class="fc_b">23</td>
										</tr>
										<tr>
											<td class="fc_r">24</td>
											<td>25</td>
											<td>26</td>
											<td>27</td>
											<td>28</td>
											<td>29</td>
											<td class="fc_b">30</td>
										</tr>
										<tr>
											<td class="fc_r">31</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td class="fc_b">&nbsp;</td>
										</tr>
									</tbody>
								</table>
							</div>
							
						</div>
						
						<div class="group_calendar">
							<div class="float_l">
								<h4 class="month">7월</h4>
								<table>
									<colgroup>
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
									</colgroup>
									<thead>
										<tr>
											<th class="fc_r">일요일</th>
											<th>월요일</th>
											<th>화요일</th>
											<th>수요일</th>
											<th>목요일</th>
											<th>금요일</th>
											<th class="fc_b">토요일</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="fc_r">&nbsp;</td>
											<td>&nbsp;</td>
											<td>1</td>
											<td>2</td>
											<td>3</td>
											<td>4</td>
											<td class="fc_b">5</td>
										</tr>
										<tr>
											<td class="fc_r">6</td>
											<td>7</td>
											<td>8</td>
											<td>9</td>
											<td>10</td>
											<td>11<span class="blk">공동연차</span></td>
											<td class="fc_b">12</td>
										</tr>
										<tr>
											<td class="fc_r">13</td>
											<td>14</td>
											<td>15</td>
											<td>16</td>
											<td>17</td>
											<td>18</td>
											<td class="fc_b">19</td>
										</tr>
										<tr>
											<td class="fc_r">20</td>
											<td>21</td>
											<td>22</td>
											<td>23</td>
											<td>24</td>
											<td>25</td>
											<td class="fc_b">26</td>
										</tr>
										<tr>
											<td class="fc_r">27</td>
											<td>28</td>
											<td>29</td>
											<td>30</td>
											<td>31</td>
											<td>&nbsp;</td>
											<td class="fc_b">&nbsp;</td>
										</tr>
									</tbody>
								</table>
							</div>
							
							<div class="float_r">
								<h4 class="month">8월</h4>
								<table>
									<colgroup>
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
									</colgroup>
									<thead>
										<tr>
											<th class="fc_r">일요일</th>
											<th>월요일</th>
											<th>화요일</th>
											<th>수요일</th>
											<th>목요일</th>
											<th>금요일</th>
											<th class="fc_b">토요일</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="fc_r">&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>1</td>
											<td class="fc_b">2</td>
										</tr>
										<tr>
											<td class="fc_r">3</td>
											<td>4</td>
											<td>5</td>
											<td>6</td>
											<td>7</td>
											<td>8</td>
											<td class="fc_b">9</td>
										</tr>
										<tr>
											<td class="fc_r">10</td>
											<td>11</td>
											<td>12</td>
											<td>13</td>
											<td>14</td>
											<td>15</td>
											<td class="fc_b">16</td>
										</tr>
										<tr>
											<td class="fc_r">17</td>
											<td>18</td>
											<td>19</td>
											<td>20</td>
											<td>21</td>
											<td>22</td>
											<td class="fc_b">23</td>
										</tr>
										<tr>
											<td class="fc_r">24</td>
											<td>25</td>
											<td>26</td>
											<td>27</td>
											<td>28</td>
											<td>29</td>
											<td class="fc_b">30</td>
										</tr>
										<tr>
											<td class="fc_r">31</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td class="fc_b">&nbsp;</td>
										</tr>
									</tbody>
								</table>
							</div>
							
						</div>
						
						<div class="group_calendar">
							<div class="float_l">
								<h4 class="month">7월</h4>
								<table>
									<colgroup>
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
									</colgroup>
									<thead>
										<tr>
											<th class="fc_r">일요일</th>
											<th>월요일</th>
											<th>화요일</th>
											<th>수요일</th>
											<th>목요일</th>
											<th>금요일</th>
											<th class="fc_b">토요일</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="fc_r">&nbsp;</td>
											<td>&nbsp;</td>
											<td>1</td>
											<td>2</td>
											<td>3</td>
											<td>4</td>
											<td class="fc_b">5</td>
										</tr>
										<tr>
											<td class="fc_r">6</td>
											<td>7</td>
											<td>8</td>
											<td>9</td>
											<td>10</td>
											<td>11<span class="blk">공동연차</span></td>
											<td class="fc_b">12</td>
										</tr>
										<tr>
											<td class="fc_r">13</td>
											<td>14</td>
											<td>15</td>
											<td>16</td>
											<td>17</td>
											<td>18</td>
											<td class="fc_b">19</td>
										</tr>
										<tr>
											<td class="fc_r">20</td>
											<td>21</td>
											<td>22</td>
											<td>23</td>
											<td>24</td>
											<td>25</td>
											<td class="fc_b">26</td>
										</tr>
										<tr>
											<td class="fc_r">27</td>
											<td>28</td>
											<td>29</td>
											<td>30</td>
											<td>31</td>
											<td>&nbsp;</td>
											<td class="fc_b">&nbsp;</td>
										</tr>
									</tbody>
								</table>
							</div>
							
							<div class="float_r">
								<h4 class="month">8월</h4>
								<table>
									<colgroup>
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
										<col style="width:70px" />
									</colgroup>
									<thead>
										<tr>
											<th class="fc_r">일요일</th>
											<th>월요일</th>
											<th>화요일</th>
											<th>수요일</th>
											<th>목요일</th>
											<th>금요일</th>
											<th class="fc_b">토요일</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="fc_r">&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>1</td>
											<td class="fc_b">2</td>
										</tr>
										<tr>
											<td class="fc_r">3</td>
											<td>4</td>
											<td>5</td>
											<td>6</td>
											<td>7</td>
											<td>8</td>
											<td class="fc_b">9</td>
										</tr>
										<tr>
											<td class="fc_r">10</td>
											<td>11</td>
											<td>12</td>
											<td>13</td>
											<td>14</td>
											<td>15</td>
											<td class="fc_b">16</td>
										</tr>
										<tr>
											<td class="fc_r">17</td>
											<td>18</td>
											<td>19</td>
											<td>20</td>
											<td>21</td>
											<td>22</td>
											<td class="fc_b">23</td>
										</tr>
										<tr>
											<td class="fc_r">24</td>
											<td>25</td>
											<td>26</td>
											<td>27</td>
											<td>28</td>
											<td>29</td>
											<td class="fc_b">30</td>
										</tr>
										<tr>
											<td class="fc_r">31</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td class="fc_b">&nbsp;</td>
										</tr>
									</tbody>
								</table>
							</div>
							
						</div>
						
						
							
						<div class="wrap_sign">
							<p class="date">
								<span>2014</span>년 <span>9</span>월 <span>24</span>일
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
	
	<%@include file="../../include/footer.jsp"%>
</body>
</html>