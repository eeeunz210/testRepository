package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;

/**
 * Servlet implementation class MemberDeleteController
 */
@WebServlet("/delete.me")
public class MemberDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberDeleteController() {
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
		
		//input : hidden으로 숨겨서 전달한 데이터 확인
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		String userPwd = request.getParameter("userPwd");
		
		int result = new MemberService().deleteMember(userNo,userPwd);
		
		//메소드명은 deleteMember로 
		//삭제 성공시 회원탈퇴 완료 메세지를 세션에담고 세션에 있던 loginUser정보 삭제하고
		//인덱스(메인페이지)로 재요청하기 
		//실패시 errorPage로 에러메세지와 함께 포워딩(위임하기) 
		
		if(result>0) { //성공 페이지
			
			HttpSession session = request.getSession();
			
			session.setAttribute("alertMsg", "회원 탈퇴 성공");
			//로그인 정보 지워주기
			session.removeAttribute("loginUser"); //로그인정보 지우기 
			
			response.sendRedirect(request.getContextPath());
			
		}else { //실패 페이지 
			
			request.setAttribute("errorMsg", "회원 탈퇴 실패");
			
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			
		}
		
		//다한사람 
		//updatePwd 메소드명으로 비밀번호 변경해보기 
		//비밀번호변경은 변경할 비밀번호와 비밀번호 확인 값이  
		//같지 않으면 서버에 전송되지 않도록 스크립트 구현하기
		
		
		
		
		
		
	}

}
