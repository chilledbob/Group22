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
<title>Gewinngemeinschaften</title>
<link rel="stylesheet" type="text/css" 	href="<c:url value="/res/css/css.css" />" />
</head>
<body>

<div id="top">
	<div id="top_content">
			<jsp:include page="../../head/head.jsp" />
		
		<div class="top_navi">
				<c:url value="customerTipManagement" var="url">
					<c:param name="uid" value="${currentUser.identifier}" />
				</c:url>
				<section><a href ="${url}">Tippverwaltung</a></section>

				<section>Gewinngemeinschaften</section>							
		</div>	
		
		
		<div class="sub_navi">
		
			<c:url value="newGroup" var="url">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<section><a href ="${url}">Erstellen</a></section>
			
			<c:url value="myGroups" var="url">
				<c:param name="uid" value="${currentUser.identifier }"/>
			</c:url>
			<section><a href="${url }">Gruppen</a></section>
			
			<section>${currentGroup.name} : Admin</section>

		</div>	
	</div>
</div>

	
<div id="middle">
	<div class="main_content_full">
		<div class="current_content">
			
			<c:url value="currentGroupViewTips_Admin" var="url2">
				<c:param name="uid" value="${currentUser.identifier}" />
				<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<c:url value="currentGroupViewApplications" var="url3">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<c:url value="closeGroup" var="url4">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<c:url value="myGroups" var="url5">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			
			<div align="center">
				<c:choose>
					<c:when test="${active }">
						<a href="${url2 }">Tipps</a>
					</c:when>
				</c:choose>
				<a href ="${url3}">Bewerbungen</a>    
				<a href ="${url5}">Abbrechen</a>
			</div>
			<p />
			<h4 align="center">- Mitglieder -</h4>
			
		<table>
				<th>Nickname</th>
				<th>Vorname</th>
				<th>Nachname</th>
				<th>Status</th>
				<th> Aktion</th>
				
				<sp:forEach items="${memberList}" var="ml">
					<tr>
						<td>${ml.identifier}</td>
						<td>${ml.getMemberData().getFirstName()}</td>
						<td>${ml.getMemberData().getLastName()}</td>
						<c:choose>
							<c:when test="${currentGroup.getGroupAdmin().equals(ml)}">
								<td>Admin</td>
								<td></td>
							</c:when>
							<c:otherwise>
								<td>Mitglied</td>
								<td>
									<c:url value="declineApplication" var="url">
										<c:param name="uid" value="${currentUser.identifier}" />
									</c:url>
									<a href ="">Entfernen</a> 
								</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</sp:forEach>
			</table>
			
			
			

		</div>		
	</div>
	
</div>

<div class="footer">
		<p>Copyright SuperLotterie ©</p>
</div>


</body>
</html>