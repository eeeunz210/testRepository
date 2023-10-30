package com.kh.notice.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.notice.model.vo.Notice;

public class NoticeDao {
	
	//기본생성자에 mapper 파일 읽어오는 작업하기
	
	private Properties prop = new Properties();
	
	public NoticeDao() {
		
		String filePath = NoticeDao.class.getResource("/db/sql/notice-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(filePath));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//공지사항 목록 조회 메소드
	public ArrayList<Notice> selectNoticeList(Connection conn) {
		//준비물 (SELECT) 
		ResultSet rset = null; //조회결과집합 받을 객체변수
		Statement stmt = null; //이미 완성된 sql문을 전달할것이기 때문에 pre 쓸필요없음
		//목록조회 결과가 여러행이 나올 수 있으니 ArrayList에 담아가기 
		ArrayList<Notice> list = new ArrayList<>();
		
		String sql = prop.getProperty("selectNoticeList");
		
		
		try {
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(sql);
			
			//여러행이 나오면 커서를 계속 움직이면서 더이상 없을때까지 조회해와 담아야하니까 
			while(rset.next()) { //조회된 다음 행이 있을때 
				//조회된 행 정보를 추출하여 Notice객체에 담아주고 그 객체를 
				//list에 추가하면 된다.
				list.add(new Notice(rset.getInt("NOTICE_NO")
								   ,rset.getString("NOTICE_TITLE")
								   ,rset.getString("USER_NAME")
								   ,rset.getInt("COUNT")
								   ,rset.getDate("CREATE_DATE")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(stmt);
		}
		return list;
	}
	
	
	//공지사항 상세조회
	public Notice selectNotice(Connection conn,int nno) {
		//select 
		Notice n = null;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("selectNotice");
		
		try {
			pstmt = conn.prepareStatement(sql);
			//위치홀더에 데이터 넣어서 완성 sql 만들기
			pstmt.setInt(1, nno);
			
			//완성된 sql구문 실행하기(결과집합 rset에 받기)
			rset = pstmt.executeQuery();
			
			//글번호(식별자)로 조회하기때문에 한행 또는 0행이 나올수밖에 없다 
			if(rset.next()) {
				n = new Notice(rset.getInt("NOTICE_NO")
							  ,rset.getString("NOTICE_TITLE")
							  ,rset.getString("NOTICE_CONTENT")
							  ,rset.getString("USER_NAME")
							  ,rset.getInt("COUNT")
							  ,rset.getDate("CREATE_DATE"));
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return n;
	}
	
	//조회수증가 메소드
	public int increaseCount(Connection conn,int nno) {
		//DML 구문 준비물 
		int result = 0; //처리된 행 수 담을 변수
		PreparedStatement pstmt = null; //sql구문 처리및 결과돌려받을 변수 
		String sql = prop.getProperty("increaseCount");
		
		try {
			//미완성 sql구문 전달하며 pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			//미완성 sql구문 완성시키기 : ? 위치홀더 채워주기 
			pstmt.setInt(1, nno); // 위치홀더에 noticeNo 넣어주기 (글번호로 조회할수있도록)
			//완성된 sql문을 실행 및 결과 받기 (결과는 처리된 행수로 돌아오기때문에 int result 변수에 담기)
			result = pstmt.executeUpdate(); // DML구문은 executeUpdate() 메소드 사용
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//자원반납 
			JDBCTemplate.close(pstmt);
		}
		
		return result;
		
	}
	
	//공지글 작성 메소드
	public int insertNotice(Connection conn,Notice n) {
		
		int result = 0;//처리된 행 수 받을 변수
		PreparedStatement pstmt = null; 
		String sql = prop.getProperty("insertNotice");
		
		try {
			//미완성 sql구문 전달하며 pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			//미완성 sql구문 완성시키기 위치홀더에 데이터 채우기
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeContent());
			//noticeWriter는 vo에 String으로 정의했기때문에 넣을때 변환하기
			pstmt.setInt(3, Integer.parseInt(n.getNoticeWriter()));
			
			//완성되었으니 실행 및 결과 받기 
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	
	//공지사항 수정 메소드
	public int updateNotice(Connection conn,Notice n) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateNotice");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			//미완성 sql 완성시키기
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeContent());
			pstmt.setInt(3, n.getNoticeNo());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	//공지삭제 메소드
	public int deleteNotice(Connection conn,int nno) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteNotice");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, nno);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
		
	}
	
	
	
	
	
	
	

}
