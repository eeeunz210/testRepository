package com.kh.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.vo.Member;

/**
 * Servlet Filter implementation class LoginFilter
 */
//필터가 필요한 매핑주소가 여러개라면 {,,,,}로 나열하기 
//만일 admin에 관련한 처리를 하는 매핑주소가 xxx.ad라면 
//  -- /*.ad --
@WebFilter({"/myPage.me","/update.me","/delete.me"})
//@WebFilter("*.bo")
public class LoginFilter extends HttpFilter implements Filter {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public LoginFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//요청이 컨트롤러에게 가기전에 요청을 가로챈다 클라이언트와 컨트롤러 사이에 위치함(검열)
		HttpSession session = ((HttpServletRequest)request).getSession(); //이곳에서 제공하는 request는 servlet의 request이기 때문에 다운캐스팅을 해줘야함 
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser != null) {//로그인이 되어있다면 
			//요청을 그대로 유지 
			chain.doFilter(request, response);  
			
		}else {//로그인이 되어있지 않다면
			//흐름을 바궈주는 작업처리 
			session.setAttribute("alertMsg", "로그인이 필요한 서비스입니다. 로그인 후 이용해주세요.");
			//response 다운캐스팅
			((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath());
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
