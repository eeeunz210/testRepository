package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberEnrollController
 */
@WebServlet("/enrollForm.me")
public class MemberEnrollController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberEnrollController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//location.href 요청 : get방식 
		
		//회원가입 페이지로 요청 위임하기(포워딩)
		//RequestDispatcher view = request.getRequestDispatcher("views/member/memberEnrollForm.jsp");
		//view.forward(request, response);
		//한줄로처리
		request.getRequestDispatcher("views/member/memberEnrollForm.jsp").forward(request, response);;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//insertMember로 진행하기
		//배열을 데이터베이스에 그대로 담을 수 없으니 (문자열 처리 후 담아주기)
		//String.join() 메소드 활용 
		//메소드명은 insertMember() 로 진행
		//회원가입 성공시 session에 회원가입 성공 메세지 담고 index페이지로 재요청
		//회원가입 실패시 request에 에러메세지 담고 errorPage로 포워딩 해보기 
		
		//1)인코딩 처리하기 
		request.setCharacterEncoding("UTF-8");
		
		//2)요청시 전달된 데이터 추출하기
		String userId = request.getParameter("userId");//필수
		String userPwd = request.getParameter("userPwd");//필수
		String userName = request.getParameter("userName");//필수
		//아래 세개는 입력을 안 할 수 있음 
		String phone = request.getParameter("phone");//빈문자열로 넘어올수있음
		String email = request.getParameter("email");//빈문자열로 넘어올수있음
		String address = request.getParameter("address");//빈문자열로 넘어올수있음
		String[] interests = request.getParameterValues("interest");//["","","",...] or null
		
		String interest = ""; 
		
		if(interests != null) {
			interest = String.join(",", interests);
		}
		
		//Member 객체에 담아서 전달하기-매개변수 생성자 만들기 (데이터가 여러가지라서)
		Member m = new Member(userId,userPwd,userName,phone,email,address,interest);
		
		//Service에 가공처리한 m 객체 전달하기 (처리된 행수 돌려받기)
		int result = new MemberService().insertMember(m);
		
		
		//처리된 행 수를 토대로 사용자에게 보여줄 응답화면 지정하기.
		if(result>0) {//성공 (성공시 화면)
			//회원가입 성공시 session에 회원가입 성공 메세지 담고 index페이지로 재요청
			HttpSession session = request.getSession();
			//menubar에 작성되어있는 알림 활용하기
			session.setAttribute("alertMsg", "회원가입 성공");
			//index페이지로 재요청 보내기 
			response.sendRedirect(request.getContextPath());
			
		}else {//실패 (실패시 화면)
			//회원가입 실패시 request에 에러메세지 담고 errorPage로 포워딩 해보기 
			request.setAttribute("errorMsg", "회원가입 실패");
			//RequestDispatcher객체에 보여줄 뷰페이지 정보(경로) 설정한뒤 위임(forward) 하기.-한줄처리
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
		}
	}

}
