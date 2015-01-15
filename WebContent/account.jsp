<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
	<% /*
		if(session.getAttribute("user") != null) {
		    response.sendRedirect("user.jsp");
		    return;
		} */
	%>
    
	<%@include file="includes/header.jsp" %>	
	
	<div id="nologin">
	<% 	if(request.getAttribute("create") != null)
			out.println(request.getAttribute("create"));
	%>
	</div>
	
	<% if(session.getAttribute("user") == null) { %>
		<form method="post" action=account>
		<fieldset id="account">
		<legend>Załóż nowe konto</legend>
			<label>Nazwa użytkownika: </label>
			<input type="text" name="username" size="30">
			<br><br>
			<label>Hasło: </label>
			<input type="password" name="password" size="30">
			<br><br>
			<input type="hidden" name="account" value="create">
			<input type="submit" value="Załóż konto">
		</fieldset>
		</form>
		
		<div id="reg">
			<p>Aby założyć konto należy podać dowolną nazwę użytkownika oraz 8-znakowe
			       hasło zawierające: małą literę, dużą literę oraz cyfrę.</p>
		</div>
	<% } %>
	
	<% if(session.getAttribute("user") != null) { 
			if(session.getAttribute("user").equals("Tester")) {
				%>
				<div id="nologin"><p>Nie możesz usunąć domyślnego użytkownika!</p></div>
		<%  } else { %>
		<form method="post" action=account>
		<fieldset id="account">
		<legend>Usuń konto</legend>
			<p>Dziękuje za skorzystanie z mojej biblioteki. Jeśli masz uwagi będę wdzięczny za podzielenie się nimi.
			Jeśli nadal chcesz usunąć konto, kliknij poniżej. Pamiętaj, że usuwając konto, usuwasz całą swoją bibliotekę.</p>
			<br><br>
			<input type="hidden" name="account" value="delete">
			<input type="submit" value="Usuń konto">
		</fieldset>
		</form>
		<% } 
		}%>
	
	<%@include file="includes/footer.jsp" %>	