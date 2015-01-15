<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.sql.*,pl.library.model.DataModel" %>

	<%
		//allow access only if session exists
		Integer userid = null;
		String user = null;
		if(session.getAttribute("user") == null) {
		    response.sendRedirect("index.jsp");
		    return;
		} else {
			userid = (Integer) session.getAttribute("userid");
			user = (String) session.getAttribute("user");
		}
		String sessionID = null;
		Cookie[] cookies = request.getCookies();
		if(cookies !=null) {
			for(Cookie cookie : cookies){
			    if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
			}
		}
	%>
    
	<%@include file="includes/header.jsp" %>	
	
	<h3>Witaj <%=user %>, oto twoja biblioteka:</h3>	
	
	<div id="logout">
		<form action="logout" method="post">
			<input type="submit" value="Wyloguj">
		</form>
	</div>
	
	<div id="books">
	<table>
		<tr>
			<th>Lp</th>
			<th>Tytuł</th>
			<th>Autor</th>
			<th>Kategoria</th>
			<th>Cena</th>
			<th>Opinia</th>
			<th>Data</th>
			<th>Id</th>
		</tr>
	<% 
		DataModel dm = (DataModel) session.getAttribute("books");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try {
			conn = dm.getConnection();
			if(request.getAttribute("column") != null) {
				String column = (String) request.getAttribute("column");
				stmt = conn.prepareStatement("select * from books where books_userid="+userid+" order by "+column+";");
			} else
				stmt = conn.prepareStatement("select * from books where books_userid="+userid);
			result = stmt.executeQuery();
			
			int i=1;
			while(result.next()) {
				out.println("<tr>");
				out.println("<td>"+i++ +"</td>");
				out.println("<td>"+result.getString(3)+"</td>");
				out.println("<td>"+result.getString(4)+"</td>");
				out.println("<td>"+result.getString(5)+"</td>");
				out.println("<td>"+result.getDouble(6)+"</td>");
				out.println("<td>"+result.getString(7)+"</td>");
				out.println("<td>"+result.getDate(8)+"</td>");
				out.println("<td>"+result.getInt(1)+"</td>");
				out.println("</tr>");
			}
			out.println("</table>");
			out.println("<p id=\"booknum\">Liczba książek wynosi: "+ --i +"</p>");
			
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
	%>
	</div>
	
	<form method="post" action="sort">
	<fieldset>
	<legend>Posortuj bibliotekę według:</legend>
		 <select name="column" size="1">
		<option value="title">Tytuł</option>
		<option value="author">Autor</option>
		<option value="category">Kategoria</option>
		<option value="price">Cena</option>
		<option value="date">Data</option>
		</select>
		<input type="hidden" name="action" value="sortbooks">
		<input type="submit" value="Posortuj">
		</fieldset>
	</form>
	
	
	<div id="nologin">
	<% 	// UpdateBookServlet, if fields are empty
		if(request.getAttribute("alert") != null)
			out.println(request.getAttribute("alert"));
	%>
	</div>
	
	<form method="post" action="add">
	<fieldset>
	<legend>Dodaj pozycję do biblioteki:</legend>
		<label>Tytuł książki: </label>
		<input type="text" name="title" size="30" value=""><br>
		<label>Autor: </label>
		<input type="text" name="author" size="30" value=""><br>
		<label>Kategoria: </label>
		<input type="text" name="category" size="30" value=""><br>
		<label>Cena: </label>
		<input type="text" name="price" size="30" value=""><br>
		<label>Opinia: </label>
		<input type="text" name="opinion" size="30" value=""><br>
		<input type="hidden" name="action" value="addnewbook"><br>
		<input type="hidden" name="userid" value="<%=userid %>">
		<input type="submit" value="Dodaj pozycję">
		</fieldset>
	</form>
	
	<form method="post" action="delete">
	<fieldset>
	<legend>Usuń pozycję z biblioteki:</legend>
		<label>Id pozycji: </label>
		<input type="text" name="id" size="30" value=""><br>
		<input type="hidden" name="action" value="deletebook"><br>
		<input type="hidden" name="userid" value="<%=userid %>">
		<input type="submit" value="Usuń pozycję">
	</fieldset>
	</form>
	
	<%@include file="includes/footer.jsp" %>	
	