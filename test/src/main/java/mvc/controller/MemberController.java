package mvc.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/MemberController")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String location;
	public MemberController(String location) {
		this.location = location;
		
	}   
   
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String paramMethod = "";
		String url = "";
		
		if(location.equals("MemberList.aws")) {
			
			paramMethod ="F";
			url = "/board/MemberList.jsp";
			
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
