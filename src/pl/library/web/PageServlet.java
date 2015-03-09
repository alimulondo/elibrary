package pl.library.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * PageServlet Class
 * Check the url address that user want to enter
 * then send user to the selected page
 */
public class PageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
				HttpServletResponse response) throws
				IOException, ServletException {
					
		String url = request.getRequestURI();
		String[] aUrl = url.split("/");
		String link = aUrl[2];

		RequestDispatcher view = null;
		if(link.equals("kontakt"))
			view = request.getRequestDispatcher("contact.jsp");
		else if(link.equals("about"))
			view = request.getRequestDispatcher("about.jsp");
		
		view.forward(request, response);	
	}

}
