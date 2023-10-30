<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.member.model.vo.Member"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	//ContextRoot(ContextPath)를 꺼내놓기 (메소드를 이용하여)
	String contextPath = request.getContextPath();
	//로그인 정보 꺼내놓기 
	//session객체에 loginUser 객체와 alertMsg 메세지를 담아놓음 
	Member loginUser = (Member)session.getAttribute("loginUser");
	String alertMsg = (String)session.getAttribute("alertMsg");
	//로그인 전 menubar.jsp 로딩되면 loginUser == null
	//로그인 후 menubar.jsp 로딩되면 로그인한 회원정보담긴 Member객체
	//로그인 전 menubar.jsp 로딩되면 alertMsg == null
	//로그인 후 menubar.jsp 로딩되면 alertMsg == 성공메세지 
	
	//쿠키정보(쿠키 배열)
	Cookie[] cookies = request.getCookies();
	
	//쿠키배열에서 필요한 쿠키정보를 추출하기 
	//반복으로 돌려서 해당 쿠키의 이름을 찾고 그 쿠키의 값을 담아두기 
	String saveId = "";
	if(cookies!=null){
		for(Cookie c : cookies){
			
			if((c.getName()).equals("userId")){
				saveId = c.getValue();
			}
		}
	}
	
%>    
    
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    
    <!-- Popper JS -->
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

    <style>
    
    	.outer{
            background-color: skyblue;
            color: white;
            width: 1000px;
            height: 800px;
            margin: auto;
            margin-top: 50px;
        }
    
        #login-form,#user-info{
            float:right;
        }

        #user-info a{
            text-decoration: none; 
            color: black;
            font-size: 12px;
        }

        .nav-area{
            background-color: skyblue;
        }
        .menu{
            display: inline-block;
            height: 50px;
            width: 150px;
        }
        .menu a{
            text-decoration: none;
            color: white;
            font-size: 20px;
            font-weight: bold;
            display: block; /*a태그 block요소로*/
            width: 100%;
            height: 100%;
            line-height: 50px;
        }
        .menu a:hover{
            background-color: deepskyblue;
        }
    

    </style>



</head>
<body>
	<script>
		// script 내부에도 스크립틀릿과 같은 jsp요소를 쓸 수 있다.
		// 주의사항 : jsp가 먼저 읽히기 때문에 javascript 값을 java에 담을 수는 없다 (반대는 가능)
			
		var msg = '<%=alertMsg%>'; //이건 가능 (문자열 처리해주기[값자체로 나옴])
		
		//안되는것 : 자바스크립트에서 작성한것을 jsp(java)로 넘기기 
		/*
		var a = 10;
		
		<% 
			//int ab = a;
		%>
		*/
		if(msg != 'null'){ //alertMsg가 null인경우 자바스크립트에 담길때 문자열이 되기때문에
						   //비교도 문자열로 비교해야함.
			alert(msg);
			//alertMsg를 session에서 지워주지않으면 계속 메세지가 나오기때문에 한번 띄우고 지우기 
			<%session.removeAttribute("alertMsg");%>
			
		}
		
		$(function(){
			
			//쿠키 아이디값 가져오기
			
			var saveId = "<%=saveId%>";
			
			if(saveId!=""){
				$("input[name=userId]").val(saveId);
				$("input[name=saveId]").attr("checked",true);
			}
			
		});
		
		
	</script>


    <h1 align="center">Welcome Web Project</h1>

    <!--로그인 영역-->
    <div class="login-area">
        <!--로그인 전 후 화면 나누기 -->
        <!--로그인 전 -->
        <%if(loginUser == null) {%>
        <form action="<%=contextPath %>/login.me" id="login-form" method="post">
            <table>
                <tr>
                    <th>아이디 : </th>
                    <td><input type="text" name="userId" required></td>
                </tr>
                <tr>
                    <th>비밀번호 :</th>
                    <td><input type="password" name="userPwd" required></td>
                </tr>
                <tr>
                	<td colspan="2">아이디저장 : <input type="checkbox" name="saveId"> </td>
                </tr>
                <tr>
                    <th colspan="2">
                        <button type="submit">로그인</button>
                        <button type="button" onclick="enrollPage();">회원가입</button>
                    </th>
                </tr>
                
            </table>
        </form>
        <script>
        	function enrollPage(){
        		//아래와 같이 작성하면 디렉토리 구조가 노출이 되니 보안에 취약할 수 있다. 
        		//location.href="/jsp/views/member/memberEnrollForm.jsp";
        		//때문에 간단한 페이지 이동요청도 servlet을 거쳐서 요청에대한 응답페이지를 돌려받자.
        		
        		location.href="<%=contextPath%>/enrollForm.me";
        		
        	}
        
        </script>

		<%}else{ %>
        <!--로그인 후 화면-->
        <div id="user-info">
            <b><%=loginUser.getUserName() %>님 환영합니다.</b>
            <div align="center">
                <a href="<%=contextPath%>/myPage.me">마이페이지</a>
                <a href="<%=contextPath%>/logout.me">로그아웃</a>
            </div>
        </div>
	<%} %>
    </div>
    <br clear="both"> <!--float 해제 -->

    <br>
	
	
	<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
	
    <div class="nav-area" align="center">
        <!-- (.menu>a)*4 -->
        <div class="menu">
            <a href="<%=contextPath%>">HOME</a>
        </div>
        <div class="menu">
            <a href="${contextPath}/list.no">공지사항</a>
        </div>
        <div class="menu">
            <a href="${pageContext.request.contextPath }/list.bo?currentPage=1">일반게시판</a>
        </div>
        <div class="menu">
            <a href="${contextPath}/list.ph">사진게시판</a>
        </div>

    </div>
</body>
</html>