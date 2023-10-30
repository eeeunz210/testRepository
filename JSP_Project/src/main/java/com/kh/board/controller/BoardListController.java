package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Board;
import com.kh.common.model.vo.PageInfo;

/**
 * Servlet implementation class BoardListController
 */
@WebServlet("/list.bo")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoardListController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 게시글 목록 조회해와서 뷰페이지로 포워딩 처리 하기
		// 페이징 처리 (사용자에게 보여줄 페이지에 보여줄 게시글 개수)
		// 준비물
		int listCount; // 총 게시글 개수
		int currentPage; // 현재 페이지
		int pageLimit; // 페이지 하단에 보여질 페이징바의 최대 개수
		int boardLimit; // 한페이지에 보여질 게시글 개수

		int maxPage; // 가장 마지막 페이징바가 몇번인지 (총 페이지수)
		int startPage; // 페이지 하단에 보여질 페이징바의 시작수
		int endPage; // 페이지 하단에 보여질 페이징바의 끝수

		// listCount 현재 게시글 개수
		listCount = new BoardService().listCount(); // 게시글 개수 조회해오기
		// 현재페이지 정보(번호)
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
		// 페이지 하단에 보여질 페이징바 개수
		pageLimit = 10;
		// 한 페이지에 보여질 게시글 개수
		boardLimit = 10;

		/*
		 * maxPage : 가장 마지막 페이지가 몇번인지 (총 페이지수)
		 * 
		 * listCount와 boardLimit에 영향을 받는다.
		 * 
		 * 게시글 개수가 107개이고 한페이지에 10개씩 보여준다면 10페이지 까지는 가득 차고 나머지 한페이지에 남은 7개 게시글을 보여줘야한다
		 * 때문에 총 11개 페이지가 나오게 됨 총개수/한페이지개수 의 나머지를 올림처리하면 된다 listCount/boardLimit ceil()
		 * 
		 * listCount와 boardLimit은 int자료형이기 때문에 소수점 올림 처리를 하려면 둘중 하나는 double 변경하여 계산해야
		 * 나머지값이 나온다(실수형) 처리 후 int로 변환하여 소수점 날리고 maxPage에 담기
		 * 
		 */
		maxPage = (int) Math.ceil((double) listCount / boardLimit);

		/*
		 * startPage : 페이징바의 시작수
		 * 
		 * pageLimit이 10인 경우
		 * 
		 * currentPage startPage 1 1 2 1 ....10 1 11 11 .....20 11 21 21
		 * 
		 * 1~10 1 11~20 11 21~30 21 31~40 31 41~50 41
		 * 
		 * 
		 * 1 / 10 = 0 ....
		 * 
		 * 
		 * (currentPage - 1)/pageLimit * pageLimit + 1
		 * 
		 * 1/10 0 * 10 = 0 + 1 10/10 1 * pageLimit(10) = 10 + 1 20/10 2 * 10 = 20 + 1
		 * 31-1/10 3 * 10 = 30 + 1 41-1/10 4 * 10 = 40 + 1 51-1/10 5 * 10 = 50 + 1 현재
		 * 페이지 -1 / 페이지최대개수 * 페이지 최대개수 + 1 (currentPage - 1)/pageLimit * pageLimit + 1
		 * 
		 */
		startPage = (currentPage - 1) / pageLimit * pageLimit + 1;

		// endPage 페이징바 끝페이지
		// 1 10 / 11 20 / 21 30
		// startPage + pageLimit - 1
		endPage = startPage + pageLimit - 1;

		// 게시글 개수가 107 - maxPage == 20
		// startPage 11 + 10 -1 endPage == 21
		// 11 7
		//startPage11 ....... endPage 20
		// 현재 페이지의 페이징바 끝페이지(endPage)가 maxPage(총페이지 끝수)보다 클때
		// 끝페이지값을 maxPage값으로 바꾸기
		if (maxPage < endPage) {
			endPage = maxPage;
		}

		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);

		// 게시글 목록 조회하기(페이징처리된)
		ArrayList<Board> list = new BoardService().selectList(pi);
		
		//페이징처리를 위한 pi와 목록을 위한 list 모두 위임하기 
		
		request.setAttribute("list", list);
		request.setAttribute("pi", pi);
		

		// boardListView페이지로 요청 포워딩
		request.getRequestDispatcher("views/board/boardListView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
