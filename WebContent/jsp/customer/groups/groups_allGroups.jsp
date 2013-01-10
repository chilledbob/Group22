<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.salespoint-framework.org/web/taglib" prefix="sp" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
			<h4 align="center">Alle Gruppen<sup style="text-shadow: aqua;font-size: x-small;">${currentUser.identifier}</sup></h4>
			
			<div align="center"><a href ="myGroups">Eigene</a> <a href ="allGroups">Alle</a>  <a href ="returnToCustomerGroups">Abbrechen</a></div>
			
			<table align="center">
				<tr>
					<th>Name</th>
					<th>Admin</th>
					<th>Mitgl</th>
					<th>Status</th>
					<th>Aktion</th>
				</tr>
				<sp:forEach items="${groupList}" var="glItem">
					<tr>
						<td>${glItem.getName()}</td>
						<td>${glItem.getGroupAdmin()}</td>
						<td>${glItem.getGroupMembers().size()}</td> 
							<c:choose>
								<c:when test="${glItem.getGroupAdmin().equals(currentUser)}">
									<td>Admin</td>
									<td>
										<c:url value="currentGroupView" var="url">
											<c:param name="groupName" value="${glItem.getName()}" />
										</c:url>
										<a href="${url}">Öffnen</a> 
									</td>
								</c:when>
								<c:when test="${glItem.getGroupMembers().contains(currentUser)}">
									<td>Mitglied</td>
									<td>
										<c:url value="currentGroupView" var="url">
											<c:param name="groupName" value="${glItem.getName()}" />
										</c:url><a href="${url}">öffnen</a> 
									</td>
								</c:when>
								<c:otherwise>
									<c:if test="${glItem.testForAppl(currentUser)}">
										<td>Beworben</td>
										<td></td>
									</c:if>
									<c:if test="${glItem.testForAppl(currentUser)==false}">
										<td></td>
										<td>
											<c:url value="applyGroupMembership" var="url">
												<c:param name="groupName" value="${glItem.getName()}" />
											</c:url><a href="${url}">Bewerben</a>
										</td>
									</c:if>
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