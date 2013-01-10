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
			<section><a href ="newGroup">Erstellen</a></section>
			<section><a href ="showInvitations">Meine Einladungen (${invCount})</a></section>
			<section><a href ="showApplications">Meine Bewerbungen (${applCount})</a></section>
			<section>Gruppen</section>
		</div>	
	</div>
</div>

<div id="middle">
	<div class="main_content_full">
		<div class="current_content">
		
			<br>
			<h4 align="center">Mitglieder<sup style="text-shadow: aqua;font-size: x-small;">${currentGroup.name} | Admin</sup></h4>

			<div align="center">
				<a href ="currentGroupViewAdmin">Mitglieder (${currentGroup.getGroupMembers().size()})</a>				<a href ="currentGroupViewTips_Admin">Tipps</a>
				<a href ="currentGroupViewApplications">Bewerbungen (${groupApplCount})</a>
				<a href ="currentGroupViewInvitations">Einladungen (${groupInvCount})</a>    
				<a href ="myGroups">Abbrechen</a>
			</div>	
			<br>		
			
			<table>
				<tr>
					<th>Nickname</th>
					<th>Vorname</th>
					<th>Nachname</th>
					<th>Status</th>
					<th> Aktion</th>
				</tr>
				
				<sp:forEach items="${memberList}" var="mlItem">
					<tr>
						<td>${mlItem.identifier}</td>
						<td>${mlItem.getMemberData().getFirstName()}</td>
						<td>${mlItem.getMemberData().getLastName()}</td>
						<c:choose>
							<c:when test="${currentGroup.getGroupAdmin().equals(mlItem)}">
								<td>Admin</td>
								<td></td>
							</c:when>
							<c:otherwise>
								<td>Mitglied</td>
								<td>
									<c:url value="currentGroupViewApplications" var="url">
										<c:param name="removeUid" value="${mlItem.identifier}" />
									</c:url>
									<a href ="url">Entfernen</a> 
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