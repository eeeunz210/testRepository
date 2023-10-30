package com.kh.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Reply;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class ReplyInsertController
 */
@WebServlet("/insertReply.bo")
public class ReplyInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReplyInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int bno = Integer.parseInt(request.getParameter("bno"));
		String content= request.getParameter("content");
		Member loginUser = (Member)request.getSession().getAttribute("loginUser"); //로그인 정보 
//		int replyWriter = loginUser.getUserNo();//댓글 작성자 번호 추출
		int replyWriter = loginUser.getUserNo();
		
		Reply r = new Reply();
		r.setRefBno(bno);
		r.setReplyContent(content);
		r.setReplyWriter(String.valueOf(replyWriter));
		
		//INSERT(DML)
		int result =  new BoardService().insertReply(r);

		//처리결과에 따른 화면 요소는 view에서 결정 
		response.getWriter().print(result);
		
		
		
	}

}
