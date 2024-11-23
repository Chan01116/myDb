package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDao;
import vo.BoardVo;


@WebServlet("/BoardController")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String location;
	public BoardController(String location) {
		this.location = location;
		
	}
       
  
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String paramMethod = "";
		String url = "";
		
		if(location.equals("BoardList.aws")) {
			
			paramMethod ="F";
			url = "/board/BoardList.jsp";
			
		}else if(location.equals("BoardWrite.aws")) {
			
			paramMethod ="F";
			url = "/board/BoardWrite.jsp";
		
		
		}else if(location.equals("BoardWriteAction.aws")) {
			
			String subject = request.getParameter("subject");
			String contents = request.getParameter("contents");
			String writer = request.getParameter("writer");
			String password = request.getParameter("password");
			
			HttpSession session = request.getSession();
		
			int originbidx = Integer.parseInt(session.getAttribute("originbidx").toString());
			session.setAttribute("originbidx", originbidx);
			
			BoardVo bv = new BoardVo();
			bv.setSubject(subject);
			bv.setContents(contents);
			bv.setWriter(writer);
			bv.setPassword(password);
			bv.setOriginbidx(originbidx);
			
			
			BoardDao bd = new BoardDao();
			int value = bd.boardInsert(bv);
			
			if(value == 2) {
				paramMethod = "S";
				url = request.getContextPath()+"/board/BoardList.aws";
				
			}else {
				paramMethod = "S";
				url = request.getContextPath()+"/board/BoardWrite.aws";
				
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
