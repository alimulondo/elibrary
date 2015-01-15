package pl.library.web;

import pl.library.model.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.sql.*;

public class UpdateBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws
			IOException, ServletException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		DataModel dm = DataModel.getInstance();
		Connection conn = null;
		PreparedStatement stmt = null;
	
		if(request.getParameter("action").equals("addnewbook")) {
			// check if fields are not empty
			SecurityModel sm = new SecurityModel();
			if(sm.checkString(request.getParameter("title")) &&
					sm.checkString(request.getParameter("author")) &&
					sm.checkString(request.getParameter("category")) &&
					sm.checkString(request.getParameter("price")) &&
					sm.checkString(request.getParameter("opinion")) &&
					sm.checkString(request.getParameter("userid"))) {
			
				String title = request.getParameter("title");
				String author = request.getParameter("author");
				String category = request.getParameter("category");
				Double price = Double.parseDouble(request.getParameter("price"));
				String opinion = request.getParameter("opinion");	
				Integer userid = Integer.parseInt(request.getParameter("userid"));
			
				try {
					conn = dm.getConnection();
				    String query = "INSERT INTO books VALUES (default,?,?,?,?,?,?,now());";
				    stmt = conn.prepareStatement(query);
				    stmt.setInt(1,  userid);
				    stmt.setString(2,  title);
				    stmt.setString(3,  author);
				    stmt.setString(4, category);
				    stmt.setDouble(5, price);
				    stmt.setString(6, opinion);
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
			} else {
				String alert = "Proszę wypełnić wszystkie pola aby dodać pozycję.";
				request.setAttribute("alert", alert);
			} // end of fields check
		
		} else if(request.getParameter("action").equals("deletebook")) {
			Integer id = Integer.parseInt(request.getParameter("id"));	
			Integer userid = Integer.parseInt(request.getParameter("userid"));

			try {
				conn = dm.getConnection();
			    String query = "delete from books where bookid=? and books_userid=?;";
			    stmt = conn.prepareStatement(query);
			    stmt.setInt(1, id);
			    stmt.setInt(2,  userid);
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
		} else if(request.getParameter("action").equals("sortbooks")) {
			String column = request.getParameter("column");
			request.setAttribute("column", column);
		} // end if
	
		request.setAttribute("books", dm);	
		RequestDispatcher view = request.getRequestDispatcher("user.jsp");
		view.forward(request, response);	
	}
}
