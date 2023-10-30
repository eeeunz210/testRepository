<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	 .list-area>tbody tr:hover{
        	background-color: deepskyblue;
        	cursor : pointer; 
        }

</style>
</head>
<body>
	
    <!-- ../ : 상위폴더로 -->
   	<%@ include file="../common/menubar.jsp" %>

    <div class="outer">
    
        <br>
        <h2 align="center">게시판</h2>
        <!-- 글작성버튼은 로그인한 회원만 볼수 있도록 작업 -->
	    <c:if test="${ not empty loginUser }"> <%--(loginUser != null)  --%>    
	        <div align="center">
	        	<a href="${contextPath }/insert.bo" class="btn btn-info">글작성</a>
	        </div>
		</c:if>
        <br><br>
        <table class="list-area" align="center">
            <thead>
                <tr>
                    <th width="70">글 번호</th>
                    <th width="70">카테고리</th>
                    <th width="300">제목</th>
                    <th width="100">작성자</th>
                    <th width="50">조회수</th>
                    <th width="100">작성일</th>
                </tr>
            </thead>
            <tbody>
                <!-- 리스트가 비어있는 경우 -->
                <c:choose>
                	<c:when test="${empty list}">
                		<tr>
                			<td colspan='6'>게시글 없습니다.</td>
                		</tr>
                	</c:when>
                	<c:otherwise>
                <!--  목록이 존재하는 경우(반복문을 통해 list에 담겨있는 객체 하나씩 다 추출해주기) -->
                		<c:forEach items="${list}" var="b">
	                		<tr>
			                    <td>${b.boardNo }</td>
			                    <td>${b.category }</td>
			                    <td>${b.boardTitle }</td>
			                    <td>${b.boardWriter }</td>
			                    <td>${b.count }</td>
			                    <td>${b.createDate }</td>
	                		</tr>
                		</c:forEach>
            		</c:otherwise>
            	</c:choose>
            
            </tbody>
        </table>
      	
      	
        <script>
        	//글 클릭했을때 글번호를 detail.bo 로 전달하며 페이지 요청하기
        	$(function(){
        		
        		//테이블에 tbody -> tr이 클릭되었을때 해당 글번호를 추출하여 detail.bo?bno=글번호
        		$(".list-area>tbody>tr").click(function(){
        			//$(this).children().eq(0).text() : 글번호 추출
        			location.href="detail.bo?bno="+ $(this).children().eq(0).text();
        			
        		});
        		
        		
        	});
        </script>
        
        <br><br>
        <!-- 페이징바 -->
        <div align="center" class="paging-area">
			
			<!-- 이전/다음 버튼 만들기 (1페이지에서는 이전버튼 비활성화) 
				마지막 페이지에서는 다음버튼 비활성화  
			-->
			<!-- 이전 -->
			<c:choose>
				<c:when test="${pi.currentPage eq 1}">
					<button disabled>이전</button>
				</c:when>
				<c:otherwise>
					<button onclick="location.href='list.bo?currentPage=${pi.currentPage-1}'">이전</button>
				</c:otherwise>
			</c:choose>
			
			<c:forEach var="i" begin="${pi.startPage}" end="${pi.endPage }">
				
				<button onclick="location.href='list.bo?currentPage=${i}'">${i}</button>
			
			</c:forEach>
			
			<!-- 다음 -->
			<c:choose>
				<c:when test="${pi.currentPage eq pi.maxPage}">
					<button disabled>다음</button>
				</c:when>
				<c:otherwise>
					<button onclick="location.href='list.bo?currentPage=${pi.currentPage+1}'">다음</button>
				</c:otherwise>
			</c:choose>

        </div>
        
        
    </div>
</body>
</html>