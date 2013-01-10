<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.salespoint-framework.org/web/taglib" prefix="sp" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mitgliederverwaltung</title>
<link rel="stylesheet" type="text/css" 	href="<c:url value="/res/css/css.css" />" />
</head>
<body>

<div id="top">
	<div id="top_content">
		<jsp:include page="../../head/head.jsp" />

		<div class="top_navi">
			<section id="currentpagename"><div id="textcurrent">Mitgliederverwaltung</div></section>
		
			<c:url value="employeeFinanzVerw" var="url">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<section><a href ="${url}">Finanzverwaltung</a></section>
			<c:url value="employeeSpieleVerw" var="url">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<section><a href ="${url}">Spielverwaltung</a></section>
		</div>	
		
		<div class="sub_navi">
		</div>	
	</div>
</div>

<div id="middle">
	<div class="main_content_full">
		<div class="current_content">
		
			<h4 align="center">Mitglied erstellen<sup style="text-shadow: aqua;font-size: x-small;">${currentUser.getTypeAsGerman().toString()}</sup></h4>
			
			<div align="center">
				<c:url value="adminMember" var="url">
					<c:param name="filter" value="All" />
				</c:url>
				<c:choose>
					<c:when test="${currentUser.getType().toString() == 'Admin'}">
						<a href ="${url}">Mitglieder</a> 
						<a href ="adminNewMember">Erstellen</a> 
						<c:url value="adminChangeReq_forAdmin" var="url">
							<c:param name="filter" value="All" />
						</c:url>
						<a href ="${url}">Nutzeranfragen</a> 
						<a href ="adminCancel">Abbrechen</a>
					</c:when> 
					<c:otherwise>
						<a href ="${url}">Spieler</a> 
						<c:url value="adminChangeReq_forCustomer" var="url">
							<c:param name="filter" value="All" />
						</c:url>
						<a href ="${url}">Nutzeranfragen</a> 
						<a href ="adminCancel">Abbrechen</a>
					</c:otherwise>
				</c:choose>	
			</div>
			<br>

	   		<div class="comment" align="center">${comment}</div>
			<form id="form" action="adminCreateMember" method="post" style="border:0px;"> 
			<table>
				<tr>
					<td><label for="vame">Vorname</label></td>
					<td><input name="vname" id="vname"  type="text" size="30" maxlength="30" value="${vname}"/></td> 
				</tr>
					<tr>
						<td><label for="name">Nachname</label></td>
						<td><input name="nname"  id="nname"  type="text" size="30" maxlength="30" value="${nname}"/></td>
					</tr>
					<tr>
						<td><label for="uid">Nutzername</label></td>
						<td><input name="uid" id="uid" type="text" size="30" maxlength="30" value="${uid}"/></td>
					</tr>
					<tr>
						<td><label for="password">Passwort</label></td>
						<td><input name="password" id="password"  type="password" size="30" maxlength="30" value="${password}"/></td>
					</tr>
					<tr>
						<td>
							<label for="typ">Typ</label>
						</td>
						<td>
						<div align="left">
							<input  type="radio" name="typ" value="employee" checked>Mitarbeiter<br>
							<input  type="radio" name="typ" value="notary"> Notar<br>
							<input  type="radio" name="typ" value="admin"> Geschäftsführer
						</div>
						</td>
					</tr>
				</table>
				<div class="button1" align="center"><button class="btn">Erstellen</button></div>
		</form> 
	</div>
</div>
	

</div>

<div class="footer">
		<p>Copyright SuperLotterie ©</p>
</div>




</body>
</html>