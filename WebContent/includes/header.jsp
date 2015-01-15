<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <link rel="stylesheet" type="text/css" href="./style.css">
<title>Twoja biblioteka online</title>
</head>
<body>


<div id="menu">
	<div id="container">
		<ul>
			<% if(session.getAttribute("user") != null) { %>
					<li><a href="logout">Wyloguj</a></li>
					<li><a href="account.jsp">Usuń konto</a></li>
					<li><a href="user.jsp">Twoja biblioteka</a></li>
			<% } %>
			<li><a href="kontakt">Kontakt</a></li>
			<li><a href="about">O mnie</a></li>		
		</ul>
	</div>
</div>	

<div id="main">
	<h1><a href="./">E-biblioteka</a></h1>

