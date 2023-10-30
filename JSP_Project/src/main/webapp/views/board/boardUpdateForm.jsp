<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	
	#update-area>table{
		border : 1px solid white;
	}
	#update-area input,#update-area textarea{
		width : 100%;
		box-sizing: border-box;
	}
	
</style>
</head>
<body>
	<%@ include file="/views/common/menubar.jsp" %>
	
	<div class="outer">
		<br>
		<h2 align="center">게시글 수정</h2>
		<br>
		
		<form action="${contextPath }/update.bo" method="post" id="update-area" enctype="multipart/form-data">
			<%--어떠한 게시글을 수정할지 알아야하기때문에 번호 보내기 --%>
			<input type="hidden" name="boardNo" value="${b.boardNo }">
			
			<table align="center" border="1">
				<tr>
					<th width="70">카테고리</th>
					<td width="70">
						<select name="category">
							<%--반복문을 이용해서 카테고리 옵션태그 뽑아주기 --%> 
							<c:forEach items="${cList}" var="c">
								<option value="${c.categoryNo }">${c.categoryName }</option>
							</c:forEach>
						</select>
						
						<script>	
							$(function(){
								//비교대상 : Board 객체에 담겨있는 category(카테고리이름)
								//select option 목록에서 똑같은 카테고리이름인 요소를 선택하기 
								var choose =  "${b.category}"; //Board 객체에 담겨있는 category(카테고리이름)
								
								$("#update-area option").each(function(){
									
									//데이터베이스에서 조회해온 게시글의 카테고리와 카테고리목록중 텍스트가 같은 요소
									if($(this).text()==choose){ // 비교대상이 같다면 
										$(this).attr("selected",true);//해당 요소객체의 selected속성을 true값으로 변경
										//선택되어짐
										return false; //break; 역할 
									}
								});
							});
						
						</script>
						
					</td>
					<th width="70">제목</th>
					<td width="350"><input type="text" name="title" value="${b.boardTitle }" required></td>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan="3">
						<textarea name="content" rows="10" style="resize:none" required>${b.boardContent }</textarea>
					</td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td colspan="3">
						<c:if test="${at != null }">
							<%--첨부파일이 있다면 해당 정보를 보여줘야한다. --%>
							${at.originName }
							<%--원본파일의 파일번호와 수정명을 서버에 전달하기(원본파일에 대한 처리를 위해) --%>
							<input type="hidden" name="originFileNo" value="${at.fileNo }">
							<input type="hidden" name="originFileName" value="${at.changeName }">
						</c:if>
						
						<input type="file" name="reUploadFile">
					</td>
				</tr>
			</table>
			<br><br>
			<div align="center">
				<button type="submit">수정하기</button>
				<button type="reset">취소</button>
			</div>
			
		</form>
		
		<br><br>
	</div>
</body>
</html>