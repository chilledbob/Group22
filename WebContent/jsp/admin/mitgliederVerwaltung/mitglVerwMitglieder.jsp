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
			
			<h4 align="center">Mitglieder<sup style="text-shadow: aqua;font-size: x-small;">${currentUser.getTypeAsGerman().toString()}</sup></h4>

			<div align="center">
				
				<c:choose>
					<c:when test="${currentUser.getType().toString() == 'Admin'}">
						<c:url value="adminMember" var="url">
							<c:param name="filter" value="All" />
						</c:url>
						<a href ="${url}">Mitglieder</a> 
						<a href ="adminNewMember">Erstellen</a> 
						<c:url value="adminChangeReq_forAdmin" var="url">
							<c:param name="filter" value="All" />
						</c:url>
						<a href ="${url}">Nutzeranfragen</a> 
						<a href ="adminCancel">Abbrechen</a>
					</c:when> 
					<c:otherwise>
						<c:url value="adminMember" var="url">
							<c:param name="filter" value="Customer" />
						</c:url>
						<a href ="${url}">Spieler</a> 
						<c:url value="adminChangeReq_forEmployee" var="url">
							<c:param name="filter" value="Customer" />
						</c:url>
						<a href ="${url}">Nutzeranfragen</a> 
						<a href ="adminCancel">Abbrechen</a>
					</c:otherwise>
				</c:choose>	
			</div>	
			<br>	
			
			<table>
				<tr>
					<th>Nickname</th>
					<th>Vorname</th>
					<th>Nachname</th>
					<th>Status</th>
					<th colspan="2"> Aktion</th>
				</tr>
				
				<sp:forEach items="${memberList}" var="mlItem">
				<c:if test="${mlItem.getActivationState()}">
					<tr>
						<td>${mlItem.identifier}</td>
						<td>${mlItem.getMemberData().getFirstName()}</td>
						<td>${mlItem.getMemberData().getLastName()}</td>
						<td>${mlItem.getTypeAsGerman()}</td>
						<td>
						<c:if test="${!mlItem.identifier.equals(currentUser.identifier)}">
							<c:url var="url" value="adminDeleteMember">
								<c:param name="deleteUid" value="${mlItem.identifier}"/><
							</c:url> 
							<a href="${url}">Löschen</a>
						</c:if>
						</td>
						<td>
							<c:url var="url" value="adminEditMember">
								<c:param name="editUid" value="${mlItem.identifier}"/><
							</c:url> 
							<a href="${url}">Anzeigen</a>
						</td>
					</tr>
				</c:if>
				</sp:forEach>
			</table>
			
			<c:if test="${currentUser.getType().toString().equals('Admin')}">
			<div align="center">
				<c:url value="adminMember" var="url1">
					<c:param name="filter" value="Admin" />
				</c:url>
				<c:url value="adminMember" var="url2">
					<c:param name="filter" value="Employee" />
				</c:url>
				<c:url value="adminMember" var="url3">
					<c:param name="filter" value="Customer" />
				</c:url>
				<c:url value="adminMember" var="url4">
					<c:param name="filter" value="Notary" />
				</c:url>
				<c:url value="adminMember" var="url5">
					<c:param name="filter" value="All" />
				</c:url>
				
				<a href ="${url1}">Geschäftsführer</a>
				<a href ="${url2}">Mitarbeiter</a>
				<a href ="${url3}">Spieler</a>
				<a href ="${url4}">Notare</a>
				<a href ="${url5}">Alle</a>
			</div>
			</c:if>
		</div>
	</div>
</div>

<div class="footer">
		<p>Copyright SuperLotterie ©</p>
</div>

</body>
</html>