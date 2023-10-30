<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.notice.model.vo.Notice"%>

<%
	Notice n = (Notice)request.getAttribute("n");

%>    
    
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <style>
        #update-form>table{border: 1px solid white;}
        #update-form input,#update-form textarea{
            width: 100%; /*가로길이 부모요소에 맞추기 */
            box-sizing: border-box; /*content영역 기준으로 border까지 영역할당*/
        }
    </style>
</head>
<body>
	
	<%@include file="../common/menubar.jsp" %>
	
    <div class="outer">
        <br>
        <h2 align="center">공지사항 수정하기</h2>

        <form action="<%=contextPath %>/update.no" id="update-form" method="post">
			<!-- hidden 으로 해당 글번호 보내기 -->
			<input type="hidden" name="nno" value="<%=n.getNoticeNo()%>">
            <table align="center">
                <tr>
                    <td width="50">*제목</td>
                    <td width="350"><input type="text" name="title" value="<%=n.getNoticeTitle()%>"required></td>
                </tr>
                <tr>
                    <td>*내용</td>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <textarea name="content" rows="10" style="resize: none;" required><%=n.getNoticeContent() %></textarea>
                    </td>
                </tr>
            </table>

            <br><br>
            <div align="center">
                <button type="submit">수정하기</button>
                <button type="button" onclick="history.back();">뒤로가기</button>
                <!--history.back() : 뒤로가기(이전페이지)-->
            </div>


        </form>



    </div>


</body>
</html>