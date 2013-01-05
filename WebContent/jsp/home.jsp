<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.salespoint-framework.org/web/taglib" prefix="sp" %>


<!DOCTYPE html>

<html>

	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<!-- css Datei für normale Nutzer einbinden -->
		<link rel="stylesheet" type="text/css" 	href="<c:url value="/res/css/css.css" />" />
	</head>

<body>
	<div id="top">
		<div id="top_content">

			<div id="logologin">

	<div id="logo">
		<h1 id="L">L</h1>
		<img src="<c:url value="/res/img/treflepetit.jpg" />" alt="trefle" class ="trefle" id="trefle"/>
		<h1 id="tterie">tterie</h1>
	</div>
				
	<div class="login">
		<sp:loggedIn test="true">
			<c:url value="editUser" var="url">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<table>
				<tr>
				<th id="account"><a href ="${url}">Account</a></th>
				<th id="logout"><a href ="<c:url value="/logout/" /> ">Logout</a></th>
				<th id="namevorname">
				<section id="name-vorname"> ${currentUser.getMemberData().getFirstName()}  ${currentUser.getMemberData().getLastName()}</section>
				</th>
				</tr>
			</table>
		</sp:loggedIn>
		<sp:loggedIn test="false">
			<form method="post" action="login">
				<label for="uid">Name: </label>
					<input type="text" id="uid" name="uid" size="12" maxlength="16">
				<label for="password">Passwort: </label>
					<input type="password" id="password" name="password" size="12" maxlength="16">
				<button type="submit">login</button> 
				<a style="color:black;" href ="<c:url value="register" />">registrieren</a>
			</form> 
			
		</sp:loggedIn>
	</div>
			
</div>
			
		</div>
	</div>
	
	
<div style="clear:all;"></div>			
<div id="middle">
	<div class="main_content_full">
		<div class="current_content">
			<h3 style="text-align:center;">Willkommen bei der gmb-Lotterie</h3>
			<c:forEach items="${gamelist }" var="game">
			${game.getGroupID()} | ${game.getMatchDateTime() }<br>
			</c:forEach>
		</div>
	</div>
</div>
</body>

