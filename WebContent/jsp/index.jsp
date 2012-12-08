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
		<link rel="stylesheet" type="text/css" 	href="<c:url value="/res/css/style.css" />" />
	</head>

<body>
	<div id="top">
		<div id="top_content">
			<div id="logo">
				<h1>LOTTERIExxxxxxxx</h1>
			</div>
			<div class="login">
				<form method="post" action="login">
					<label for="uid">Name: </label>
					<input type="text" id="uid" name="uid" size="12" maxlength="16">
					<label for="password">Passwort: </label>
					<input type="password" id="password" name="password" size="12" maxlength="16">
					<button type="submit">login</button>
					oder   
					<a href ="<c:url value="/newUser/" />">registrieren</a>
				</form> 
			</div>
			
			
			
		</div>
	</div>
	
	
<div style="clear:all;"></div>			
<div id="middle">
	<div class="main_content">
		<div class="current_content">
			<h3 style="text-align:center;">Willkommen bei der gmb-Lotterie</h3>
			<!--<sp:forEach var="u" items="${userListe}">
				${ u.getMemberData().getAdress().getStreetName() }
			</sp:forEach>-->
		</div>
	</div>
</div>
</body>

