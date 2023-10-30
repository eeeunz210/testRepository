<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.notice.model.vo.Notice"%>
    
<%
	//전달받은 공지사항목록 추출하기 
	ArrayList<Notice> nlist = (ArrayList<Notice>)request.getAttribute("nlist");

%>    
    
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <style>
        .outer{
            background-color: skyblue;
            color: white;
            width: 1000px;
            height: 500px;
            margin: auto;
            margin-top: 50px;
        }

        .list-area {
            border: 1px solid white;
            text-align: center;
        }
        
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
        <h2 align="center">공지사항</h2>
        <div align="center">
        	<%if(loginUser!=null && loginUser.getUserId().equals("admin")){ %>
            	<!-- 글작성페이지로 이동 -->
            	<a href="<%=contextPath %>/insert.no" class="btn btn-info">글작성</a>
            <%} %>
        </div>

        <br><br>
        <table class="list-area" align="center">
            <thead>
                <tr>
                    <th>글 번호</th>
                    <th width="400">글 제목</th>
                    <th width="100">작성자</th>
                    <th>조회수</th>
                    <th width="100">작성일</th>
                </tr>
            </thead>
            <tbody>
                <!-- (tr>td*5)*3 -->
                <!-- 
                <tr>
                    <td>10</td>
                    <td>안녕하세요 제이름은</td>
                    <td>둘리</td>
                    <td>0</td>
                    <td>2023-10-19</td>
                </tr>
                <tr>
                    <td>6</td>
                    <td>쿠키를 만들어봤어요 </td>
                    <td>둥이맘</td>
                    <td>25</td>
                    <td>2023-10-10</td>
                </tr>
                <tr>
                    <td>5</td>
                    <td>제 자전거 보신분 바퀴만 남았어요</td>
                    <td>엄복동</td>
                    <td>2</td>
                    <td>2023-10-09</td>
                </tr>
       
                 -->
                <!-- 리스트가 비어있는 경우 -->
                <%if(nlist.isEmpty()) {%>
                	<tr>
                		<td colspan='5'>공지사항이 없습니다.</td>
                	</tr>
                <%}else{ %>
                <!--  목록이 존재하는 경우(반복문을 통해 list에 담겨있는 객체 하나씩 다 추출해주기) -->
                	<%for(Notice n : nlist) {%>
                		<tr>
		                    <td><%=n.getNoticeNo() %></td>
		                    <td><%=n.getNoticeTitle() %></td>
		                    <td><%=n.getNoticeWriter() %></td>
		                    <td><%=n.getCount() %></td>
		                    <td><%=n.getCreateDate() %></td>
                		</tr>
                	<%} %>
                <%} %>
                
            </tbody>

        </table>
    </div>
    
    
    <script>
    	// detail.no : 요청매핑값
    	// 클릭한 글번호를 detail.no?nno=글번호 로 글을 클릭했을때 요청보내기(페이지 이동)
    	$(function(){
    		
    		$(".list-area>tbody>tr").click(function(){
    			//console.log("클릭됨");
    			//현재 클릭된 요소객체 : $(this) : <tr></tr> 
    			//우리가 원하는것 tr안에 있는 td중에 글번호 td의 텍스트 
    			//console.log($(this).children().eq(0).text());
    			//console.log($(".list-area>tbody>tr").children().first().text());
    			var nno = $(this).children().eq(0).text(); //글번호 
    			
    			//요청할 url?키=벨류&키=벨류&....
    			//물음표 뒤에 내용들을 쿼리스트링 이라고 한다 - 직접 작성하여 요청해도 get방식 요청이 됨 
    			// /jsp/detail.no?nno=클릭한글번호
    			
    			location.href="<%=contextPath%>/detail.no?nno="+nno;
    			
    		});	
    		
    	});
    
    </script>
    
    
    
    
    
</body>
</html>