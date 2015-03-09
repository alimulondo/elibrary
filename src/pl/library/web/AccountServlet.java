package pl.library.web;

import pl.library.model.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * AccountServlet Class
 * Create or delete user account
 */
public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws
			IOException, ServletException {	
		
		//create database object
		QueryEngineModel engine = new QueryEngineModel();
		IQuery db = engine.getDbType();
		
		// create account
		if(request.getParameter("account").equals("create")) {
			
			// check if username is not null and password is correct
			// security check
			SecurityModel sm = new SecurityModel();
			if(sm.checkString(request.getParameter("username")) 
					&& sm.checkPassword(request.getParameter("password"))) {	
			
				response.setCharacterEncoding("UTF-8");

				// get request parameters for userID and password
				String user = request.getParameter("username");
				String pass = request.getParameter("password");
				//insert parameters into object array
				Object[] record = {user, pass};
				//insert record into database
				db.insert(record, "users");	
		
				RequestDispatcher view = request.getRequestDispatcher("index.jsp");
				String create = "Twoje konto zostało utworzone, możesz się zalogować.";
				request.setAttribute("create", create);
				view.forward(request, response);
			} else {
				RequestDispatcher view = request.getRequestDispatcher("account.jsp");
				String create = "Podałeś nieprawidłowe hasło lub nie podałeś nazwy użytkownika.<br>"+
								"Zapoznaj się z wytycznymi podanymi poniżej.";
				request.setAttribute("create", create);
				view.forward(request, response);
			} // end of security check
			
		  // delete account
		} else if(request.getParameter("account").equals("delete")) {
			HttpSession session = request.getSession();
			Integer userid = (Integer) session.getAttribute("userid");
			
			//insert parameters into objects array
			Object[] record = {userid};
			//delete user from database
			db.delete(record, "users");	
			
			session = request.getSession(false);
	        session.removeAttribute("user");
	        session.removeAttribute("userid");
			if(session != null) {
				session.invalidate();
			}
			
			Cookie[] cookies = request.getCookies();
			if(cookies !=null) {
				for(Cookie cookie : cookies){
				    cookie.setValue("");
				}
			}
			
			RequestDispatcher view = request.getRequestDispatcher("index.jsp");
			String create = "Twoje konto zostało usunięte.";
			request.setAttribute("create", create);
			view.forward(request, response);					
		}
		
	}
}
