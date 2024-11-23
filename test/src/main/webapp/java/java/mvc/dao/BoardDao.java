package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import dbconn.Dbconn;
import vo.BoardVo;

public class BoardDao {
	
	private Connection conn;
	private PreparedStatement pstmt;
	
	public BoardDao() {
		Dbconn db = new Dbconn();
		this.conn = db.getConnetcion();
	}
	
	
	
	public ArrayList<BoardVo> boardSelectAll(){
		
		ArrayList<BoardVo> alist = new ArrayList<BoardVo>();
		
		
		
		
		
		return alist;
	}
	
	public int boardInsert(BoardVo bv) {  // 왜 Vo로 받을까?
		
		int value = 0;
		
		String subject = bv.getSubject();
		String contents = bv.getContents();
		String writer = bv.getWriter();
		String password = bv.getPassword();
		
		String sql = "INSERT INTO board(originbidx,depth,level_,SUBJECT,contents,writer,password)"
				+ "value (null,0,0,?,?,?,?)";
		
		String sql2 = "update board set originbidx = (select A.maxbidx from (SELECT max(bidx) as maxbidx from board)A)"
				+"where bidx = (select A.maxbidx from (SELECT max(bidx) as maxbidx from board)A)";
		
			
		
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,subject);
			pstmt.setString(2,contents);
			pstmt.setString(3,writer);
			pstmt.setString(4,password);
			int exec = pstmt.executeUpdate();
			
			
			pstmt = conn.prepareStatement(sql2);
			int exec2 = pstmt.executeUpdate();   //실행되면 1 아니면 0
			
			conn.commit();  // 일괄처리 커밋
			
			value = exec+exec2;
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {  
			pstmt.close();
			}catch(SQLException e) {
    			e.printStackTrace();
    			
    		}
					
		}
			
		
		return value;
	}
	

}
