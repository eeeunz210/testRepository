<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <style>
        #enroll-form>table{border: 1px solid white;}
        #enroll-form input,#enroll-form textarea{
            width: 100%; /*가로길이 부모요소에 맞추기 */
            box-sizing: border-box; /*content영역 기준으로 border까지 영역할당*/
        }
    </style>
</head>
<body>
	
	<%@include file="../common/menubar.jsp" %>
	
    <div class="outer">
        <br>
        <h2 align="center">공지사항 작성하기</h2>

        <form action="<%=contextPath %>/insert.no" id="enroll-form" method="post">
			<input type="hidden" name="userNo" value="<%=loginUser.getUserNo()%>">
            <table align="center">
                <tr>
                    <td width="50">*제목</td>
                    <td width="350"><input type="text" name="title" required></td>
                </tr>
                <tr>
                    <td>*내용</td>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <textarea name="content" rows="10" style="resize: none;" required></textarea>
                    </td>
                </tr>
            </table>

            <br><br>
            <div align="center">
                <button type="submit">등록하기</button>
                <button type="button" onclick="history.back();">뒤로가기</button>
                <!--history.back() : 뒤로가기(이전페이지)-->
            </div>


        </form>



    </div>


</body>
</html>