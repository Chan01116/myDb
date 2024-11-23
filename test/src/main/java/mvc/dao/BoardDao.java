package mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mvc.dbconn.Dbconn;
import mvc.vo.BoardVo;

public class BoardDao {
	
	private Connection conn;
	private PreparedStatement pstmt;
	
	public BoardDao() {
		Dbconn db = new Dbconn();
		this.conn = db.getConnetcion();
	}
	
	
	
	public ArrayList<BoardVo> boardSelectAll(){
		//System.out.println("셀렉트올 다오");
		
		ArrayList<BoardVo> alist = new ArrayList<BoardVo>();
		
		
		String sql = "select * from board order by originbidx desc, depth asc limit ?,?";
		ResultSet rs = null;
		
		
		try {
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, 0);
	    	pstmt.setInt(2, 10);
	    	
	    	rs = pstmt.executeQuery();
	    	
	    	while(rs.next()) {
	    		//System.out.println("셀렉트올 들어오나요");// 커서가 다음르로 이동해서 첫글이 있느냐 물어보고 true면 진행
	    		int bidx = rs.getInt("bidx");
	    		String boardSubject = rs.getString("subject");
	    		String boardContents = rs.getString("contents");
	    		String boardWriter = rs.getString("writer");
	    		String boardWirteday = rs.getString("wirteday");
	    		int boardRecom = rs.getInt("recom");
	    		int boardViewcnt = rs.getInt("viewcnt");
	    		
	    		BoardVo bv = new BoardVo();  // 첫행부터 mv에 옮겨담기
	    		bv.setBidx(bidx);
	    		bv.setSubject(boardSubject);
	    		bv.setContents(boardContents);
	    		bv.setWriter(boardWriter);
	    		bv.setWirteday(boardWirteday);
	    		bv.setRecom(boardRecom);
	    		bv.setViewcnt(boardViewcnt);
	    		alist.add(bv);				//ArrayList객체에 하나씩 추가하고 리턴한다
	    		//System.out.println(bidx);    		
	    	}
	    	
	    	}catch(SQLException e) {
				e.printStackTrace();
				
			} finally {
				try {
					rs.close();
				pstmt.close();
				conn.close();
				}catch(SQLException e) {
	    			e.printStackTrace();
	    			
	    		}
			}
	    	   	     	
	    return alist;
	}
	
	public int boardInsert(BoardVo bv) {  // 왜 Vo로 받을까?
		
		int value = 0;
		
		String subject = bv.getSubject();
		String contents = bv.getContents();
		String writer = bv.getWriter();
		String password = bv.getPassword();
		int bidx = bv.getBidx();
		String wirteday = bv.getWirteday();
		
		String sql = "INSERT INTO board(originbidx,depth,level_,SUBJECT,contents,writer,password,wirteday,bidx)"
				+ "value (null,0,0,?,?,?,?,NOW(),?)";
		
		String sql2 = "update board set originbidx = (select A.maxbidx from (SELECT max(bidx) as maxbidx from board)A)"
				+"where bidx = (select A.maxbidx from (SELECT max(bidx) as maxbidx from board)A)";
		
			
		
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,subject);
			pstmt.setString(2,contents);
			pstmt.setString(3,writer);
			pstmt.setString(4,password);
			pstmt.setInt(5, bidx);
			
			int exec = pstmt.executeUpdate();
			
			
			pstmt = conn.prepareStatement(sql2);
			int exec2 = pstmt.executeUpdate();   //실행되면 1 아니면 0
			
			conn.commit();  // 일괄처리 커밋
			
			value = exec+exec2;
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {  
			pstmt.close();
			}catch(Exception e) {
    			e.printStackTrace();
    			
    		}
					
		}
			
		
		return value;
	}
	
	public BoardVo boardSelectOne(int bidx) {
		BoardVo bv = null;
		
		String sql = "SELECT * FROM board WHERE delyn = 'N' and bidx = ?";
		
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,bidx);
			rs = pstmt.executeQuery();
			
			if(rs.next()==true) {
				String subject = rs.getString("subject");
 				String contents = rs.getString("contents");
 				String writer = rs.getString("writer");
 				String wirteday = rs.getString("wirteday");
 				int viewcnt = rs.getInt("viewcnt");
 				int recom = rs.getInt("recom");
 				String filename = rs.getString("filename");
 				int rtnBidx = rs.getInt("bidx");
 				int originbidx = rs.getInt("originbidx");
 				int depth = rs.getInt("depth");
 				int level_ = rs.getInt("level_");
 				String password = rs.getString("password");
 				
 				
 				bv = new BoardVo();  // 객체생성해서 지역변수 bv로 담아서 리턴해서 가져간다
 				bv.setSubject(subject);
 				bv.setContents(contents);
 				bv.setWriter(writer);
 				bv.setWirteday(wirteday);
 				bv.setViewcnt(viewcnt);
 				bv.setRecom(recom);
 				bv.setFilename(filename);
 				bv.setBidx(rtnBidx);
 				bv.setOriginbidx(originbidx);
 				bv.setDepth(depth);
 				bv.setLevel_(level_);
 				bv.setPassword(password);
				
			}
			
						
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {  
			rs.close();
			pstmt.close();
			conn.close();
			}catch(SQLException e) {
    			e.printStackTrace();
    			
    		}
		}
		
		
		
		return bv;
	}
	
	
	public int boardUpdate(BoardVo bv) {
	    int value = 0;
	    
	    String sql = "UPDATE board SET subject = ?, contents = ?, writer = ?, password = ? WHERE bidx = ?";
	    
	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, bv.getSubject());
	        pstmt.setString(2, bv.getContents());
	        pstmt.setString(3, bv.getWriter());
	        pstmt.setString(4, bv.getPassword());
	        pstmt.setInt(5, bv.getBidx());
	        
	        value = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            pstmt.close();
	            conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    return value;
	}
	
	
	

}
