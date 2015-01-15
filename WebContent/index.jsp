<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
	<% if(session.getAttribute("user") != null) {
		    response.sendRedirect("user.jsp");
		    return;
		} 
	%>
    
	<%@include file="includes/header.jsp" %>	
		
	<div id="nologin">

	<% 	if(request.getAttribute("nologin") != null)
			out.println(request.getAttribute("nologin"));
		else if(request.getAttribute("create") != null)
			out.println(request.getAttribute("create"));
		else if(session.getAttribute("user") != null)
			out.println("Jesteś już zalogowany.");
	%>
	</div>
	
	<form method="post" action="login">
	<fieldset id="login">
	<legend>Logowanie</legend>
		<label>Nazwa użytkownika: </label>
		<input type="text" name="username" size="30">
		<br><br>
		<label>Hasło: </label>
		<input type="password" name="password" size="30">
		<br><br>
		<input type="submit" value="Zaloguj">
	</fieldset>
	</form>

	<div id="account-link">
		<a href="account.jsp">Załóż konto</a>
	</div>
	
	<div id="reg">
		<p>Zapraszam do przetestowania prostej aplikacji webowej której zadaniem jest zarządzanie własną biblioteką książek.
		Po założeniu konta możemy się zalogować i korzystać. Aplikacja umożliwia dodawanie pozycji do biblioteki, usuwanie pozycji
		oraz sortowanie pozycji. Po przetestowaniu możemy usunąć swoje konto. Użytkownicy i pozycje biblioteki zapisywane są w bazie 
		danych MySQL.</p>
		<p>Aby założyć konto należy podać dowolną nazwę użytkownika oraz 8-znakowe hasło zawierające: małą literę, dużą literę oraz cyfrę.</p>
		<p>Domyślny użytkownik:<br>
		nazwa użytkownika: Tester<br>
		hasło: 11qqaaZZ
		</p>
	</div>
	
	<%@include file="includes/footer.jsp" %>	
