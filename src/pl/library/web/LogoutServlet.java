package pl.library.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {		
        //invalidate the session if exists
        HttpSession session = request.getSession(false);
        session.removeAttribute("user");
        session.removeAttribute("userid");
        if(session != null) {
            session.invalidate();
        }
        response.sendRedirect("index.jsp");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
						throws ServletException, IOException {	
		HttpSession session = request.getSession(false);
        session.removeAttribute("user");
        session.removeAttribute("userid");
		if(session != null) {
			session.invalidate();
		}
		response.sendRedirect("index.jsp");
	}

}
