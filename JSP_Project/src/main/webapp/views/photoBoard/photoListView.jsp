<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.list-area {
		width: 760px;
		margin: auto;
	}
	.thumbnail {
		border : 1px solid white;
		width : 220px;
		display : inline-block;
		margin : 15px;
	}
	
	.thumbnail:hover{
		cursor : pointer;
		opacity : 0.7;
	}
	

</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>	
	<div class="outer">
		<h1 align="center">사진 게시판 </h1>
		<br>
		<div align="center">
			<button onclick="location.href='${contextPath}/insert.ph'">글작성</button>
		</div>
		
		<div class="list-area">
			<!-- 게시글 없는경우 -->
			<c:choose>
				<c:when test="${empty phList }">
					조회된 게시글이 없습니다.
				</c:when>
				<c:otherwise>
				
					<c:forEach items="${phList }" var="ph">
						<div class="thumbnail">
							<input type ="hidden" value="${ph.boardNo }">
							<!--/jsp/resources/uploadFiles/파일이름.jpg -->
							<img src="${contextPath }${ph.imgsrc}" width="200px" height="150px">
							<p>
								No.${ph.boardNo } ${ph.boardTitle }<br>
								조회수 : ${ph.count }
							</p>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
	
	   <script>
        	//글 클릭했을때 글번호를 detail.bo 로 전달하며 페이지 요청하기
        	$(function(){
        		
        		//테이블에 tbody -> tr이 클릭되었을때 해당 글번호를 추출하여 detail.bo?bno=글번호
        		$(".thumbnail").click(function(){
        			//$(this).children().eq(0).text() : 글번호 추출
        			location.href="detail.ph?bno="+ $(this).children().eq(0).val();
        			
        		});
        		
        		
        	});
        </script>	
	
	</div>
	
	
	
</body>
</html>