<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	
	table{
		border : 2px solid white;
	}	
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	<div class="outer">
		<br>
		<h2 align="center">사진게시글 상세보기</h2>
		<br>
		
			<table align="center" border="1">
				<tr>
					<th width="100">제목</th>
					<td colspan="3">${b.boardTitle }</td>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan="3">
						<p style="height:70px">${b.boardContent }</p>
					</td>
				</tr>
					<c:forEach items="${list }" var="at" varStatus="vs">
						<c:choose> 
							<c:when test="${vs.index eq 0 }">
								<!-- 첫번째 요소니까 대표이미지 -->
								<tr>
									<th>대표이미지</th>
									<td colspan="3" align="center">
										<img id="titleImg" 
											src="${contextPath }${at.filePath}${at.changeName}" width="250" height="170">
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<!-- 상세이미지 -->								
								<tr>
									<th>상세이미지</th>
									<td>
									<img id="contentImg${vs.count}" 
										src="${contextPath }${at.filePath}${at.changeName}" width="150" height="120">
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</c:forEach>
			</table>
			<br><br>
			
		
		<br><br>
	</div>
</body>
</html>