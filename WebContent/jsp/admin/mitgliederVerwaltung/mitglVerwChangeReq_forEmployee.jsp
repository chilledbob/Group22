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
		
			<h4 align="center">Änderungsanfragen<sup style="text-shadow: aqua;font-size: x-small;">${currentUser.getTypeAsGerman().toString()}</sup></h4>

			<div align="center">
				<c:url value="adminMember" var="url">
					<c:param name="filter" value="All" />
				</c:url>
				<a href ="${url}">Spieler</a> 
				<c:url value="adminChangeReq_forEmployee" var="url">
					<c:param name="filter" value="Customer" />
				</c:url>
				<a href ="${url}">Nutzeranfragen</a> 
				<a href ="adminCancel">Abbrechen</a>
			</div>
			<br>
			
			<table align="center">
				<tr>
					<th>Name</th>
					<th>Nutzertyp</th>
					<th>Datum</th>
					<th colspan="3">Aktion</th>
				</tr>
				<c:forEach items="${requestList}" var="rlItem" varStatus="status">
					<c:if test="${rlItem.getState().toString() == 'Unhandled'}">
						<tr>
							<td align="center">${rlItem.getMember().identifier}</td>
							<td align="center">${rlItem.getMember().getTypeAsGerman().toString()}</td>
							<td align="center"><fmt:formatDate type="date" value="${rlItem.getDate().toDate()}"></fmt:formatDate></td>
							
							<c:url value="adminShowDataReq" var="url">
								<c:param name="reqID" value="${rlItem.getId()}" />
								<c:param name="filter" value="${filter}" />
							</c:url>
							<td><a href="${url}">Anzeigen</a></td> 
							
							<c:url value="adminRefuseDataReq" var="url">
								<c:param name="reqID" value="${rlItem.getId()}" />
								<c:param name="filter" value="${filter}" />
							</c:url>
							<td><a href="${url}">Ablehnen</a></td> 
							
							<c:url value="adminAcceptDataReq" var="url">
								<c:param name="reqID" value="${rlItem.getId()}" />
								<c:param name="filter" value="${filter}" />
							</c:url>
							<td><a href="${url}">Akzeptieren</a></td> 
						</tr>
					</c:if>
				</c:forEach>			
			</table>
		
			</div>	
			<br>
		</div>
	</div>


<div class="footer">
		<p>Copyright SuperLotterie ©</p>
</div>

</body>
</html>