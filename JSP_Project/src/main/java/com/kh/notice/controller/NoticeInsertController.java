package com.kh.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeInsertController
 */
@WebServlet("/insert.no")
public class NoticeInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//enrollForm으로 요청 위임하기 
		request.getRequestDispatcher("views/notice/noticeEnrollForm.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//제목,내용,작성자번호를 추출해서 Notice객체에 담고 service - dao - db 순으로 처리후 
		request.setCharacterEncoding("UTF-8");
		String title = request.getParameter("title");//제목
		String content = request.getParameter("content");//내용
		String userNo = request.getParameter("userNo"); //로그인 회원번호
		
		Notice n = new Notice();
		n.setNoticeTitle(title);
		n.setNoticeContent(content);
		n.setNoticeWriter(userNo);
		
		//dml구문이니 처리된 행 수 돌아올것 
		int result = new NoticeService().insertNotice(n); //제목,내용,회원번호를 갖는 notice객체 전달 
		
		//돌아와서 사용자에게 보여줄 화면 지정 
		//성공했을경우 - 공지글 작성 성공메세지와함께 list.no 즉 목록 페이지를 띄워주기(alert)
		HttpSession session = request.getSession();
		if(result>0) {
			session.setAttribute("alertMsg", "공지글 작성 성공");
			//재요청(공지글 목록 요청) : /jsp/list.no 
			response.sendRedirect(request.getContextPath()+"/list.no");
		}else {
			//실패했을경우 - 에러페이지로 에러메세지와함께 보내기 or 목록페이지 띄워주면서 (실패 메세지 alert)해주기
			session.setAttribute("alertMsg", "공지 작성 실패");
			response.sendRedirect(request.getContextPath()+"/list.no");
		}
		
		
		
		//insertNotice (메소드명) 
		
		
		
	}

}
