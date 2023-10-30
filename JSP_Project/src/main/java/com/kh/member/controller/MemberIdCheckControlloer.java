package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.member.model.service.MemberService;

/**
 * Servlet implementation class MemberIdCheckControlloer
 */
@WebServlet("/idCheck.me")
public class MemberIdCheckControlloer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberIdCheckControlloer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String checkId = request.getParameter("checkId");
		
		//해당 아이디값을 데이터베이스에어 있는지 없는지 조회 처리 
		int count =  new MemberService().checkId(checkId);//해당 아이디가 존재하는지 숫자를 세어 있다면 사용못하게 없다면 사용가능하도록 
		
		//count에는 아이디가 있으면 (중복) 1 없으면 (사용가능) 0이 반환되었을 것 
		if(count>0) {//중복이라면 사용불가 
			response.getWriter().print("NNNNN");
		}else {//중복이 아님 - 사용가능 
			response.getWriter().print("NNNNY");
			
		}
		
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
