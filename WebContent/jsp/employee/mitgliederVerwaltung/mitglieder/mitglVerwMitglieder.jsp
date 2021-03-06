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
		<jsp:include page="../../../head/head.jsp" />

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
			
			<section id="currentsubpagename">Mitglieder</section>
			
			<c:url value="mitglVerw_groups" var="url">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<section><a href ="${url}">Gewinngemeinschaften</a></section>
				
			<c:url value="mitglVerw_changeReq" var="url">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<section><a href ="${url}">Änderungsanträge</a></section>
		</div>	
	</div>
</div>

		
<div id="middle">

	<div class="main_content">
		<div class="current_content">
			<table>
				<th>Nickname</th>
				<th>Vorname</th>
				<th>Nachname</th>
				<th>Status</th>
				<th> Löschen</th>
				<th> Editieren</th>
				
				<sp:forEach items="${memberList}" var="ml">
					<tr>
						<td>${ml.identifier}</td>
						<td>${ml.getMemberData().getFirstName()}</td>
						<td>${ml.getMemberData().getLastName()}</td>
						<td>${ml.getType().name()}</td>
						<td> X </td>
						<td> E </td>
					</tr>
				</sp:forEach>
			</table>
			<c:url value="mitglVerw_mitglieder" var="url">
				<c:param name="uid" value="${currentUser.identifier}" />
				<c:param name="sort" value="admin" />
			</c:url>
			<a href ="${url}">Admins</a>
			
			<c:url value="mitglVerw_mitglieder" var="url">
				<c:param name="uid" value="${currentUser.identifier}" />
				<c:param name="sort" value="employee" />
			</c:url>
			<a href ="${url}">Mitarbeiter</a>
			
			<c:url value="mitglVerw_mitglieder" var="url">
				<c:param name="uid" value="${currentUser.identifier}" />
				<c:param name="sort" value="customer" />
			</c:url>
			<a href ="${url}">Spieler</a>
			
			<c:url value="mitglVerw_mitglieder" var="url">
				<c:param name="uid" value="${currentUser.identifier}" />
				<c:param name="sort" value="notary" />
			</c:url>
			<a href ="${url}">Notare</a>
			

		</div>
	</div>

	<div class="side">

		<div id="side_calendar">
			<p>CALENDAR</p>
		</div>
		
		<div id="side_tipps">
			<p>TIPPS</p>
		</div>
	</div>

</div>

<div class="footer">
		<p>Copyright SuperLotterie ©</p>
</div>

</body>
</html>