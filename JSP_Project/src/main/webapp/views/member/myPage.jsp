<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.outer {
	background: skyblue;
	color: white;
	width: 1000px;
	margin: auto; /* 가운데 자동 정렬 */
	margin-top: 50px; /* 위로부터 50px 아래로 여백 만들기 */
}

#mypage-form table {
	margin: auto;
}

#mypage-form input {
	margin: 5px;
}
#deleteForm,#updatePwd *{
	color : black;
}
</style>
</head>
<body>

	<%@ include file="../common/menubar.jsp"%>
	<!-- ../ : 현재 폴더로부터 한번 빠져나감 (즉, 현재 폴더로부터 한단계 상위폴더로 이동)  -->

	<%
	//menubar에서 꺼내놓았던 loginUser(Member) 정보 사용하기 
	String userId = loginUser.getUserId();
	String userName = loginUser.getUserName();
	//필수요소가 아닌 항목에 null값이 들어가면 보여줄때 ""빈값으로 보여주기 
	String phone = (loginUser.getPhone() == null) ? "" : loginUser.getPhone();
	String email = (loginUser.getEmail() == null) ? "" : loginUser.getEmail();
	String address = (loginUser.getAddress() == null) ? "" : loginUser.getAddress();
	String interest = (loginUser.getInterest() == null) ? "" : loginUser.getInterest();
	%>


	<div class="outer">

		<br>
		<h2 align="center">마이페이지</h2>

		<form id="mypage-form" action="<%=contextPath%>/update.me"
			method="post">
			<!-- menubar.jsp 를 위에서 include 했기 때문에 contextPath 변수를 가져다 쓸 수 있다. -->

			<!-- 아이디, 비밀번호, 이름, 전화번호, 이메일, 주소, 취미 -->
			<table>
				<!-- (tr>td*3)*8 -->
				<tr>
					<td>* 아이디</td>
					<td><input type="text" name="userId" maxlength="12"
						value="<%=userId%>" readonly></td>
					<td></td>
				</tr>
				<tr>
					<td>* 이름</td>
					<td><input type="text" name="userName" maxlength="6"
						value="<%=userName%>" required></td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;전화번호</td>
					<td><input type="text" name="phone" placeholder="- 포함해서 입력"
						value="<%=phone%>">
					<td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;이메일</td>
					<td><input type="email" name="email" value="<%=email%>"></td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;주소</td>
					<td><input type="text" name="address" value="<%=address%>"></td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;관심분야</td>
					<td colspan="2">
						<!-- (input[type=checkbox name=interest id= value=]+label)*6 --> <input
						type="checkbox" name="interest" id="sports" value="운동"> <label
						for="sports">운동</label> <input type="checkbox" name="interest"
						id="hiking" value="등산"> <label for="hiking">등산</label> <input
						type="checkbox" name="interest" id="fishing" value="낚시"> <label
						for="fishing">낚시</label> <br> <input type="checkbox"
						name="interest" id="cooking" value="요리"> <label
						for="cooking">요리</label> <input type="checkbox" name="interest"
						id="game" value="게임"> <label for="game">게임</label> <input
						type="checkbox" name="interest" id="movie" value="영화"> <label
						for="movie">영화</label>
					</td>
				</tr>
			</table>

			<script>
            	$(function(){
            		var interest = "<%=interest%>"; //'운동,게임,영화' or ''
					//console.log(interest);

					//console.log($("input[name=interest]").val());
					//각 요소를 순차적으로 접근하며 function 실행할수있는 문법 
					$("input[name=interest]").each(function() {
						//console.log($(this).val());
						//interest에 있는 값중 각 요소의 value값과 일치하는 값이 있다면
						//해당 요소를 checked 해주기 
						//indexof() / search() : 일치하는 인덱스를 반환 값이 없으면 -1반환 
						//includes() : 일치하는 값이 있으면 true / 없으면 fasle 
						var value = $(this).val(); //현재 접근요소의 value값 

						//현재 value값이 로그인정보중 interest에 포함되었는지 확인 
						if (interest.includes(value)) {
							//값이 있다면 현재 요소에 checked걸기
							$(this).attr("checked", true);
						}
					});
				});
			</script>


			<br> <br>

			<div align="center">
				<button type="submit" class="btn btn-warning">정보변경</button>
				<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#updatePwd">비밀번호 변경</button>
				<button type="button" class="btn btn-dark" data-toggle="modal" data-target="#deleteForm">회원탈퇴</button>
			</div>

			<br> <br>

		</form>

		<!-- 비밀번호 변경용 모달 영역 -->
		<div class="modal" id="updatePwd">
			<div class="modal-dialog">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">비밀번호 변경</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>

					<!-- Modal body -->
					<div class="modal-body" align="center">
						
						<form action="<%=contextPath %>/updatePwd.me" method="post">
							<!-- 현재 비밀번호, 변경할 비밀번호, 변경할 비밀번호 확인 -->
							<table>
								<tr>
									<td>현재 비밀번호</td>
									<td><input type="password" name="userPwd" required></td>
								</tr>
								<tr>
									<td>변경할 비밀번호</td>
									<td><input type="password" name="updatePwd" required></td>
								</tr>
								<tr>
									<td>비밀번호 확인</td>
									<td><input type="password" id="checkPwd" required></td>
								</tr>
							
							</table>
							<br>
							<button type="submit" class="btn btn-secondary" onclick="return pwdCheck();">변경하기</button>
							
						</form>
						
						<script>
							function pwdCheck(){
								//변경할 비밀번호와 비밀번호 확인값이 같은지 확인 후 
								//다르면 기존 버튼에 걸려있는 기본이벤트인 submit작업을 막아주는 처리를 할 함수
								
								var cPwd = $("input[name=updatePwd]");
								var chkPwd = $("#checkPwd");
								
								//두 요소객체의 value값이 같지 않다면 
								if(cPwd.val()!=chkPwd.val()){
									alert("변경할 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
									cPwd.select();
									
									return false; //기본이벤트 실행되지 않도록 막기
								}	
								
								
							}
						
						
						</script>
					
					</div>


				</div>
			</div>
		</div>


		<!-- 회원탈퇴용 모달 영역 -->
		<div class="modal" id="deleteForm">
			<div class="modal-dialog">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">회원탈퇴</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>

					<!-- Modal body -->
					<div class="modal-body" align="center">
						<b>탈퇴 후 복구가 불가능합니다. <br>정말로 탈퇴하시겠습니까?</b> <br><br>
						
						<form action="<%=contextPath %>/delete.me" method="post">
							<!-- 데이터 숨겨서 전송하기 -->
							<input type="hidden" name="userNo" value="<%=loginUser.getUserNo()%>" >
							<table>
								<tr>
									<td>비밀번호</td>
									<td> <input type="password" name="userPwd" required> </td>
								</tr>
							</table>
							<br>
							<button type="submit" class="btn btn-danger">탈퇴하기</button>
						
						</form>
					
					</div>

				</div>
			</div>
		</div>




	</div>


</body>
</html>