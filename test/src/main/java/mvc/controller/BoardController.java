package mvc.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.dao.BoardDao;
import mvc.vo.BoardVo;


@WebServlet("/BoardController")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String location;
	public BoardController(String location) {
		this.location = location;
		
	}
       
  
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String paramMethod = "";
		String url = "";
		
		if(location.equals("BoardList.aws")) {
			
			BoardDao bd = new BoardDao();
			ArrayList<BoardVo> alist = bd.boardSelectAll();
			request.setAttribute("alist", alist);
			//System.out.println("컨트롤 리스트 들어왔나요");
			paramMethod ="F";
			url = "/board/BoardList.jsp";
			
		}else if(location.equals("BoardWrite.aws")) {
			
			paramMethod ="F";
			url = "/board/BoardWrite.jsp";
		
		
		}else if(location.equals("BoardWriteAction.aws")) {
			//System.out.println("boardWirteAction");
			String subject = request.getParameter("subject");
			String contents = request.getParameter("contents");
			String writer = request.getParameter("writer");
			String password = request.getParameter("password");
			String wirteday = request.getParameter("wirteday");
			
						
			BoardVo bv = new BoardVo();
			bv.setSubject(subject);
			bv.setContents(contents);
			bv.setWriter(writer);
			bv.setPassword(password);
			bv.setWirteday(wirteday);
			
			
			
			BoardDao bd2 = new BoardDao();
			int value = bd2.boardInsert(bv);
			
			if(value == 2) {
				paramMethod = "S";
				url = request.getContextPath()+"/board/BoardList.aws";
				
			}else {
				paramMethod = "S";
				url = request.getContextPath()+"/board/BoardWrite.aws";
				
			}
			
			
			
			
			
		}else if(location.equals("BoardContent.aws")) {
			//System.out.println("BoardContent.aws");
			String bidx = request.getParameter("bidx");
			int bidxInt = Integer.parseInt(bidx);
			
			
			
			BoardDao bd = new BoardDao();
			BoardVo bv = bd.boardSelectOne(bidxInt);
			
			request.setAttribute("bv", bv); 
			paramMethod ="F";  
			url = "/board/BoardContent.jsp";
		
		
		}else if(location.equals("BoardModify.aws")) {
			
			String bidxParam = request.getParameter("bidx");
		    int bidx = Integer.parseInt(bidxParam);
		    
		    BoardDao bd = new BoardDao();
		    BoardVo bv = bd.boardSelectOne(bidx); // 수정할 글의 정보를 가져옴
		    request.setAttribute("bv", bv);
			
			
			paramMethod ="F";  
			url = "/board/BoardModify.jsp";
		
		
		
		
		}else if(location.equals("BoardModifyAction.aws")) {
			 int bidx = Integer.parseInt(request.getParameter("bidx"));
			    String subject = request.getParameter("subject");
			    String contents = request.getParameter("contents");
			    String writer = request.getParameter("writer");
			    String password = request.getParameter("password");

			    // 비밀번호 검증 로직 추가
			    BoardDao bd = new BoardDao();
			    BoardVo originalPost = bd.boardSelectOne(bidx);
			    
			    if (originalPost != null && originalPost.getPassword().equals(password)) {
			        BoardVo bv = new BoardVo();
			        bv.setBidx(bidx);
			        bv.setSubject(subject);
			        bv.setContents(contents);
			        bv.setWriter(writer);
			        
			        int value = bd.boardUpdate(bv);
			        
			        if(value > 0) {
			        	System.out.println("성공");
			            paramMethod = "S";
			            url = request.getContextPath() + "/board/BoardList.aws"; // 성공 시 목록으로
			        } else {
			        	System.out.println("실패");
			            paramMethod = "F"; // 실패 시
			            url = request.getContextPath() + "/board/BoardModify.aws?bidx=" + bidx; // 실패 시 다시 수정 폼으로
			        }
			    } else {
			        // 비밀번호 불일치 시 처리
			    	System.out.println("비번불일치");
			        paramMethod = "F";
			        url = request.getContextPath() + "/board/BoardModify.aws?bidx=" + bidx; // 실패 시 다시 수정 폼으로
			    }
		}
		
		
		
		
		
		if ("F".equals(paramMethod)) {
	            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	            dispatcher.forward(request, response);
	        } else if ("S".equals(paramMethod)) {
	            response.sendRedirect(url);
	        }
		
		
	}
	
	
	
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
