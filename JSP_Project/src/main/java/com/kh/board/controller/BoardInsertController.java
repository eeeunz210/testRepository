package com.kh.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.common.model.vo.MyFileRenamePolicy;
import com.kh.member.model.vo.Member;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardInsertController
 */
@WebServlet("/insert.bo")
public class BoardInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//카테고리 정보 조회해와서 위임할때 넘겨주기
		//메소드명 : selectCategoryList / VO : Category 
		ArrayList<Category> list = new BoardService().selectCategoryList();
		
		
		//request에 담아서 전달 
		request.setAttribute("cList",list);
		
		request.getRequestDispatcher("views/board/boardEnrollForm.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		/*
		String category = request.getParameter("category");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String uploadFile = request.getParameter("uploadFile");
		
		
		System.out.println(category);
		System.out.println(title);
		System.out.println(content);
		System.out.println(uploadFile);
		
		폼전송을 일반 방식이 아닌 multipart/form-data 방식으로 전송할 경우
		request로 데이터를 추출할 수 없다.
		multipart라는 객체를 이용해야함
		*/
		HttpSession session = request.getSession();
		//넘어온 데이터 타입이 multipart인지 확인하기 
		if(ServletFileUpload.isMultipartContent(request)) {
			//파일 업로드를 위한 라이브러리 cos.jar
			
			//1. 전송되는 파일을 처리할 작업 내용(전송되는 파일의 용량제한,전달된 파일을 저장할 경로)
			//1_1. 전송 파일 용량 제한 ( byte ) 
			
			//byte - kbyte - mbyte - gbyte -tbyte 
			//1kbyte == 1024byte
			
			int maxSize = 10 * 1024 * 1024; //10mb 
			
			//1_2. 전달된 파일을 저장할 서버의 경로를 알아내기
			//세션 객체에 있는 getRealPath 메소드를 사용한다.
			//현재 구동되고있는 웹애플리케이션을 기준으로 경로를 잡아야함. 
			//webapp 뒤에 오는 경로는 따로 지정을 해야된다.
			//request.getSession().getServletContext() : 웹애플리케이션 객체 
			String savePath = request.getSession().getServletContext().getRealPath("/resources/uploadFiles/");
			
			//1_3.기존 request객체로는 파일전달을 받을 수 없으니 MultipartRequest 객체로 변환하는 작업을 수행해야한다.
			//[표현법]MultipartRequest multiRequest = new MultipartRequest(request, 저장경로, 파일최대사이즈, 인코딩, 파일명객체);
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8",new MyFileRenamePolicy());
			
			
			//2.DB에 등록할 정보들 추출하기 
			//사용자가 입력한 데이터 추출하여 Board 객체에 담고 Board 테이블에 등록하기
			//사용자가 넣은 파일 정보 추출하여 Attachment 객체에 담고 Attachment 테이블에 등록하기
			
			//사용자가 전달한 데이터도 multipartRequest에서 추출해야한다.
			String category = multiRequest.getParameter("category");
			String title = multiRequest.getParameter("title");
			String content = multiRequest.getParameter("content");
			
			//작성자정보 (등록되는 정보가 userNo와 같으니 로그인정보에서 추출하기)
			String boardWriter = String.valueOf(((Member)session.getAttribute("loginUser")).getUserNo());
			
			Board b = new Board();
			b.setBoardContent(content);
			b.setBoardTitle(title);
			b.setBoardWriter(boardWriter);
			b.setCategory(category);
			
			//사용자가 파일을 업로드했다면 파일 정보를 추출하고 업로드하지 않았다면 게시글정보만 필요 
			Attachment at = null; //첨부파일이 있다면 담아줄 예정
			
			//multiRequest.getOriginalFileName() 
			// : 원본파일명을 반환하는 메소드 / 없다면 null을 반환한다. 
			if(multiRequest.getOriginalFileName("uploadFile") != null) {
				//원본파일명이 있다면 첨부파일정보 담아주기
				
				at = new Attachment();
				//원본파일명 담기
				at.setOriginName(multiRequest.getOriginalFileName("uploadFile"));
				//변경한 파일명 담기 (서버에 등록된 파일명)
				at.setChangeName(multiRequest.getFilesystemName("uploadFile"));
				//경로 담기
				at.setFilePath("/resources/uploadFiles/");
				
			}
			
			//3.서비스 요청 (게시글정보와 첨부파일 정보 전달)
			int result = new BoardService().insertBoard(b,at);
			
			if(result>0) { //성공
				session.setAttribute("alertMsg", "게시글 등록 성공");
				
				response.sendRedirect(request.getContextPath()+"/list.bo?currentPage=1");
			}else {//실패
				//게시글 등록에 실패했다면 서버에 업로드된 파일을 지워야한다.
				
				if(at!=null) {//첨부파일이 있다면 삭제 작업 
					//삭제하고자하는 파일경로로 파일객체 생성하여 delete메소드 수행
					new File(savePath+at.getChangeName()).delete();
				}
				
				session.setAttribute("alertMsg", "게시글 등록 실패");
				
				response.sendRedirect(request.getContextPath()+"/list.bo?currentPage=1");
			}
			
		}
		
		
		
	}

}
