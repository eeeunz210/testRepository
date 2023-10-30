package com.kh.member.model.service;

import java.sql.Connection;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.Member;

public class MemberService {
	
	//로그인 요청 처리 메소드
	public Member loginMember(String userId,String userPwd) {
		//서비스에서 할일 (DB 연결/트랜잭션과 관련된 처리) 
		Connection conn = JDBCTemplate.getConnection();
		
		//dao에게 요청 전달 
		Member m = new MemberDao().loginMember(conn,userId,userPwd);
		
		//select 구문이니 트랜잭션은 할 필요 없고 자원 반납 하기 
		JDBCTemplate.close(conn);		
		
		return m;
	}
	
	//회원가입 요청 처리 메소드
	public int insertMember(Member m) {
		//connection 객체생성 후 전달 받은 데이터와 함께 
		//dao에게 전달하기 
		Connection conn = JDBCTemplate.getConnection();
		
		//반환받은 처리된 행수 담기 
		int result = new MemberDao().insertMember(conn,m);
		
		//처리된 행수로 조건식을 넣어 트랜잭션 처리하기(DML구문)
		if(result>0) {//성공
			JDBCTemplate.commit(conn); //확정
		}else{ //실패
			JDBCTemplate.rollback(conn); //되돌리기
		}
		
		//트랜잭션처리까지 했으니 connection 자원 반납
		JDBCTemplate.close(conn);
		
		
		//처리된 행 수 반환하기
		return result;
	}
	
	
	//회원정보 수정 메소드
	public Member updateMember(Member m) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		//Connection과 Member dao에게 전달하기 
		
		//처리된 행 수 받기 
		int result = new MemberDao().updateMember(conn,m);
		
		//갱신된 회원정보 담을 변수
		Member updateMem = null;
		
		//처리된 행 수를 토대로 dml구문이니 트랜잭션 처리하기 (commit/rollback)
		if(result>0) { //성공
			JDBCTemplate.commit(conn); //확정
			//정보변경 성공시에는 로그인된 회원의 정보를 갱신하는 작업이 필요하다 
			//때문에 확정짓고 해당 회원의 정보를 다시 조회해온다.
			//사용자의 아이디만 가지고 회원 정보 조회하는 메소드
			updateMem = new MemberDao().selectMember(conn,m.getUserId());
		
		}else { //실패
			JDBCTemplate.rollback(conn);//되돌리기
		}
		
		//자원반납
		JDBCTemplate.close(conn);
		
		//갱신정보 반환
		return updateMem;
	}
	
	//회원 탈퇴 메소드
	public int deleteMember(int userNo,String userPwd) {
		
		//Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().deleteMember(conn,userNo,userPwd);
		
		//dml구문이니 트랜잭션 처리 
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		
		return result;
	}
	
	//비밀번호변경 메소드
	public Member updatePwd(int userNo,String userPwd,String updatePwd) {
		
		//커넥션 객체
		Connection conn = JDBCTemplate.getConnection();
		
		//dml : 비밀번호 변경작업 
		int result = new MemberDao().updatePwd(conn,userNo,userPwd,updatePwd);
		
		Member updateMem = null; //회원정보 담을 변수 
		
		//처리된 행수로 트랜잭션 처리하기 (DML)
		if(result>0) { //성공 
			JDBCTemplate.commit(conn); //확정
			//변경된 정보 조회해오기 (userNo로)
			updateMem = new MemberDao().selectMember2(conn,userNo);
		}else { //실패
			JDBCTemplate.rollback(conn);//되돌리기 
		}
		
		//트랜잭션처리 끝났으니 Connection 반납하기
		JDBCTemplate.close(conn);
		
		return updateMem;//변경된 회원정보 조회한 객체반환
		
	}

	public int checkId(String checkId) {
		Connection conn = JDBCTemplate.getConnection();
		//조회구문(select) 트랜잭션안함 
		int count = new MemberDao().checkId(conn,checkId);
		JDBCTemplate.close(conn);
		
		return count;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
