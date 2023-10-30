package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;

/**
 * Servlet implementation class PhotoBoardDetailController
 */
@WebServlet("/detail.ph")
public class PhotoBoardDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhotoBoardDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//보드번호로 게시글정보와 첨부파일정보 조회해오기
		int boardNo = Integer.parseInt(request.getParameter("bno"));
		
		//서비스 선언
		BoardService bs = new BoardService();
		
		//조회수 증가 처리 
		int result = bs.increaseCount(boardNo);
		
		HttpSession session = request.getSession();
		//조회수 증가가 성공적으로 되었다면 게시글과 첨부파일 정보 조회하기 
		if(result>0) {
			Board b = bs.selectBoard(boardNo);
			ArrayList<Attachment> list = bs.selectAttachmentList(boardNo);
			
			
			request.setAttribute("b", b);
			request.setAttribute("list", list);
			
			request.getRequestDispatcher("views/photoBoard/photoDetailView.jsp").forward(request, response);
			
			
			
		}else {//실패했으면 오류 메세지 
			session.setAttribute("alertMsg", "상세보기 실패");
			//이전페이지로
			response.sendRedirect(request.getHeader("referer"));
			
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
