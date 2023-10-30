package com.kh.notice.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.notice.model.dao.NoticeDao;
import com.kh.notice.model.vo.Notice;

public class NoticeService {
	
	//공지사항 목록 조회 메소드
	public ArrayList<Notice> selectNoticeList() {
		//Conn 만들기
		Connection conn = JDBCTemplate.getConnection();
		
		//dao conn 전달하며 요청보내기 (반환받은 list 담아주기)
		ArrayList<Notice> list = new NoticeDao().selectNoticeList(conn);
		
		//조회구문은 트랜잭션 처리할 필요가 없기때문에 자원반납만 해주기 
		JDBCTemplate.close(conn);
		
		return list; //dao에게 받은 list 반환하기(Controller에게)
	}
	
	//공지사항 상세보기 
	public Notice selectNotice(int nno) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		Notice n = new NoticeDao().selectNotice(conn,nno);
		
		JDBCTemplate.close(conn);
		
		return n;
	}
	
	//상세보기전 조회수 증가 메소드
	public int increaseCount(int nno) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new NoticeDao().increaseCount(conn,nno);
		
		//DML구문이니 트랜잭션 처리하기 
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		//자원반납
		JDBCTemplate.close(conn);
		
		return result;
		
	}
	//공지글 작성 메소드
	public int insertNotice(Notice n) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new NoticeDao().insertNotice(conn,n);
		
		//dml(insert) 트랜잭션 
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	//공지글 수정메소드
	public int updateNotice(Notice n) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new NoticeDao().updateNotice(conn,n);
		
		//트랜잭션 처리 
		if(result>0) { //성공
			JDBCTemplate.commit(conn);
		}else {//실패 
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	//공지 삭제 메소드 
	
	public int deleteNotice(int nno) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new NoticeDao().deleteNotice(conn,nno);
		
		//dml 트랜잭션
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}
	

	
	
	
	
	
	
	
}
