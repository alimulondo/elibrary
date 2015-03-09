package pl.library.web;

import pl.library.model.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


/*
 * UpdateBookServlet Class
 * Update the database with books list, user have
 * 3 options: add book to list, delete book from list
 * and sort the list of books
 */
public class UpdateBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws
			IOException, ServletException {
		
		//create database object
		QueryEngineModel engine = new QueryEngineModel();
		IQuery db = engine.getDbType();
		SecurityModel sm = new SecurityModel();
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
	
		if(request.getParameter("action").equals("addnewbook")) {
			// check if fields are not empty
			if(sm.checkString(request.getParameter("title")) &&
					sm.checkString(request.getParameter("author")) &&
					sm.checkString(request.getParameter("category")) &&
					sm.checkIsNumber(request.getParameter("price")) &&
					sm.checkString(request.getParameter("opinion")) &&
					sm.checkIsInteger(request.getParameter("userid"))) {
			
				//get parameters
				String title = request.getParameter("title");
				String author = request.getParameter("author");
				String category = request.getParameter("category");
				Double price = Double.parseDouble(request.getParameter("price"));
				String opinion = request.getParameter("opinion");	
				Integer userid = Integer.parseInt(request.getParameter("userid"));
				//insert parameters into objects array
				Object[] record = {userid,title,author,category,price,opinion};
				//insert record into database
				db.insert(record, "books");
			} else {
				String alert = "Proszę poprawnie wypełnić wszystkie pola aby dodać pozycję.";
				request.setAttribute("alert", alert);
			} // end of fields check
		
		} else if(request.getParameter("action").equals("deletebook")) {
			//check if "id" parameter is the type of Integer
			if(sm.checkIsInteger(request.getParameter("id"))) {
				//get parameters
				Integer id = Integer.parseInt(request.getParameter("id"));
				Integer userid = Integer.parseInt(request.getParameter("userid"));
				//insert parameters into objects array
				Object[] record = {id, userid};
				//delete record from database
				db.delete(record, "books");
			} else {
				String alert = "Pole ID może zawierać tylko liczby.";
				request.setAttribute("alert", alert);
			}

		} else if(request.getParameter("action").equals("sortbooks")) {
			String column = request.getParameter("column");
			request.setAttribute("column", column);
		} // end if
	
		//set attribute with database connection and send it to view
		request.setAttribute("books", db);	
		RequestDispatcher view = request.getRequestDispatcher("user.jsp");
		view.forward(request, response);	
	}
}
