package pl.library.web;

import pl.library.model.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws
			IOException, ServletException {	
		// create account
		if(request.getParameter("account").equals("create")) {
			
			// check if username is not null and password is correct
			// security check
			SecurityModel sm = new SecurityModel();
			if(sm.checkString(request.getParameter("username")) 
					&& sm.checkPassword(request.getParameter("password"))) {	
			
				// get request parameters for userID and password
				String user = request.getParameter("username");
				String pass = request.getParameter("password");
			
				response.setCharacterEncoding("UTF-8");
					
				DataModel dm = DataModel.getInstance();
				Connection conn = null;
				PreparedStatement stmt = null;
				
				try {
					conn = dm.getConnection();
					String query = "insert into users values(default, ?, ?);";
					stmt = conn.prepareStatement(query);
					stmt.setString(1, user);
					stmt.setString(2, pass);		      
				    stmt.execute("SET NAMES 'utf8'");
				    stmt.executeUpdate();			      
				    conn.close();				
				} catch(SQLException e) {
					e.printStackTrace();
				} catch(ClassNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						conn.close();
					} catch(SQLException e) {
						e.printStackTrace();
					}
				}	
		
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
			
			// get connection to database
			DataModel dm = DataModel.getInstance();
			Connection conn = null;
			PreparedStatement stmt = null;
						
			try {
				conn = dm.getConnection();	
				// first delete books associated with user than the user itself
				String query1 = "delete from books where books_userid=?;";
				String query2 = "delete from users where userid=?;";
				stmt = conn.prepareStatement(query1);	
				stmt.setInt(1, userid);
				stmt.executeUpdate();
				stmt = conn.prepareStatement(query2);
				stmt.setInt(1,  userid);
				stmt.executeUpdate();						      
				conn.close();						
			} catch(SQLException e) {
				e.printStackTrace();
			} catch(ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}	
				
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
			//response.sendRedirect("index.jsp");
			
			RequestDispatcher view = request.getRequestDispatcher("index.jsp");
			String create = "Twoje konto zostało usunięte.";
			request.setAttribute("create", create);
			view.forward(request, response);					
		}
		
	}
}
