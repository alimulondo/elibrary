package pl.library.web;

import pl.library.model.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * LoginServlet Class
 * Check the identity of user and login to 
 * corresponding account if the user exists in database
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws
			IOException, ServletException {
		
		//create database object
		QueryEngineModel engine = new QueryEngineModel();
		IQuery db = engine.getDbType();
		
		String _user = "";
		String _pass = "";
		int _userid = -1;
		String user = null;
		String pass = null;
		
		// check if username is not null and password is correct
		// security check
		SecurityModel sm = new SecurityModel();
		if(sm.checkString(request.getParameter("username")) 
				&& sm.checkPassword(request.getParameter("password"))) {
			
			// get request parameters for userID and password
			user = request.getParameter("username");
			pass = request.getParameter("password");
			
			response.setCharacterEncoding("UTF-8");			
			
			Object[] userParam = db.loginCheck(user, pass);
			_userid = (Integer) userParam[0];
			_user = (String) userParam[1];
			_pass = (String) userParam[2];		
			
		} else {
			user = "false";
			pass = "false";
		}
		
		RequestDispatcher view = null;	
		if(_user.equals(user) && _pass.equals(pass)) {			
			HttpSession session = request.getSession();
			session.setAttribute("userid", _userid);
			session.setAttribute("user", _user);
			session.setMaxInactiveInterval(30*60);
			
			view = request.getRequestDispatcher("user.jsp");
			session.setAttribute("books", db);
			view.forward(request, response);
		} else {
			view = request.getRequestDispatcher("index.jsp");
			String nologin = "Podałeś błędny login lub hasło!";
			request.setAttribute("nologin", nologin);
			view.forward(request, response);
		}
		
	}
}
