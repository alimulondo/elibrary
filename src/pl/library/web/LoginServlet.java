package pl.library.web;

import pl.library.model.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataModel _dm = null;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws
			IOException, ServletException {
		
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
				
			_dm = DataModel.getInstance();
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet result = null;
			
			try {
				conn = _dm.getConnection();
				stmt = conn.prepareStatement("select * from users");
				result = stmt.executeQuery();
				while(result.next()) {
					if(result.getString(2).equals(user) && result.getString(3).equals(pass)) {
						_userid = result.getInt(1);
						_user = result.getString(2);
						_pass = result.getString(3);	
					}
				}
				result.close();
				stmt.close();
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			} catch(ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if(stmt != null)
						stmt.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
				try {
					if(conn != null)
						conn.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
				
			}
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
			session.setAttribute("books", _dm);
			view.forward(request, response);
		} else {
			view = request.getRequestDispatcher("index.jsp");
			String nologin = "Podałeś błędny login lub hasło!";
			request.setAttribute("nologin", nologin);
			view.forward(request, response);
		}
		
	}
}
