package com.kh.board.controller;

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
import com.kh.common.model.vo.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class PhotoBoardInsertController
 */
@WebServlet("/insert.ph")
public class PhotoBoardInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhotoBoardInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("views/photoBoard/photoEnrollForm.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		request.setCharacterEncoding("UTF-8");
		
		
		if(ServletFileUpload.isMultipartContent(request)) {
			//용량제한
			int maxSize = 10 * 1024 * 1024;
			//저장경로
			String savePath = request.getSession().getServletContext().getRealPath("/resources/uploadFiles/");
			
			
			MultipartRequest multiRequest = new MultipartRequest(request, savePath,maxSize,"UTF-8",new MyFileRenamePolicy());
			
			
			//db에 등록할 데이터 추출 
			
			//Board에 넣을 데이터 
			String title = multiRequest.getParameter("title");
			String content = multiRequest.getParameter("content");
			String boardWriter = multiRequest.getParameter("userNo");//form에서 input hidden으로 로그인 유저 번호 전달하기
			
			
			Board b = new Board();
			b.setBoardTitle(title);
			b.setBoardContent(content);
			b.setBoardWriter(boardWriter);
			
			//Attachment에 담을 데이터 
			//첨부파일은 필수로 1개는 넘어오고 여러개가 넘어올수있으니 
			//각 데이터를 담아주기 위해 list 준비
			ArrayList<Attachment> list = new ArrayList<>();
			
			//각 키값을 반복 돌리면서 요소 꺼내주기 
			
			for(int i=1; i<=4; i++) {
				//키값 
				String key = "file"+i;
				
				//키값에 해당하는 요소가 있는지 확인하기 
				if(multiRequest.getOriginalFileName(key)!=null) {
					//해당 키값에 파일이 있다면 
					//Attachment 객체 생성후 데이터 담아주기 
					//여러개가 나올 수 있으니 Attachment객체를 list에 추가하기 
					//원본명,변경이름,파일경로,파일레벨(썸네일사진/상세사진)
					
					Attachment at = new Attachment();
					at.setOriginName(multiRequest.getOriginalFileName(key));
					at.setChangeName(multiRequest.getFilesystemName(key));
					at.setFilePath("/resources/uploadFiles/");
					
					if(i==1) { //대표이미지 fileLevel == 1 
						at.setFileLevel(1);
					}else { //상세이미지 fileLevel == 2
						at.setFileLevel(2);
					}
					list.add(at); //값 추출 끝났으니 리스트에 추가하기.
					
				}
			}
			
			//서비스에 요청하기 
			
			int result = new BoardService().insertPhotoBoard(b,list);
			HttpSession session = request.getSession();
			
			if(result>0) { //성공
				//성공메세지와함께 사진게시판 목록 보여주기
				session.setAttribute("alertMsg", "사진게시글 작성 성공!");
				
				response.sendRedirect(request.getContextPath()+"/list.ph");
				
			}else {//실패
				//실패메세지와함께 사진게시판 목록 보여주기 
				session.setAttribute("alertMsg", "사진게시글 작성 실패!");
				
				response.sendRedirect(request.getContextPath()+"/list.ph");
				
			}
			
			
		}
		
		
		
		
		
		
	}

}
