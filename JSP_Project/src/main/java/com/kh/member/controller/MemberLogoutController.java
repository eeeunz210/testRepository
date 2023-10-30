package com.kh.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MemberLogoutController
 */
@WebServlet("/logout.me")
public class MemberLogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberLogoutController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//로그아웃
		//로그인 되어있는 회원정보를 지워주면 그게 로그아웃
		//로그인 정보가 있는 객체 : session 
		//데이터 지우는 메소드 : removeAttribute("키값");
		//세션객체 얻어와서 remove하기
	
		HttpSession session = request.getSession();
		session.removeAttribute("loginUser");//loginUser키값 데이터 지우기
		
		//세션 정보 전부 지우기 (세션만료)
		//session.invalidate();
		
		
		//사용자에게는 메인화면 보여주기 (재요청) 
		response.sendRedirect(request.getContextPath());// /jsp 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
