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
			<section><a href ="newGroup">Erstellen</a></section>
			
			<c:url value="showInvitations" var="url">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<section><a href ="showInvitations">Meine Einladungen (${invCount})</a></section>
			
			<c:url value="showApplications" var="url">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<section><a href ="showApplications">Meine Bewerbungen (${applCount})</a></section>
			
			<c:url value="myGroups" var="url">
				<c:param name="uid" value="${currentUser.identifier }"/>
			</c:url>
			<section><a href="myGroups">Gruppen</a></section>
		</div>	
	</div>
</div>

	
<div id="middle">
	<div class="main_content_full">
			<div align="right">Gruppe: ${currentGroup.getName()} [Admin]</div>
			<h3 align="center">Bestätigung Schliessen </h3>
		<div class="current_content">
			
			<c:url value="currentGroupView" var="url1">
				<c:param name="uid" value="${currentUser.identifier}" />
				<c:param name="groupName" value="${currentGroup.name}" />
			</c:url>
			<c:url value="currentGroupViewTips_Admin" var="url2">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<c:url value="currentGroupViewApplications" var="url3">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<c:url value="currentGroupViewInvitations" var="url4">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<c:url value="myGroups" var="url5">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<c:url value="closeGroup" var="url6">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			
			<div align="center">
				<a href ="${url1}">Mitglieder (${currentGroup.getGroupMembers().size()})</a>
				<a href ="${url2}">Tipps</a>
				<a href ="${url3}">Bewerbungen (${applCount})</a>
				<a href ="${url4}">Einladungen (${invCount})</a> 
				<a href ="${url6}">Schliessen</a>    
				<a href ="${url5}">Abbrechen</a>
			</div>	
			<br>		
			
			<br>
			<div align="center">Sie schliessen die Gruppe :<b>${currentGroup.name}</b> ! </div>
		
			<c:url value="closeGroupConf" var="url1">
				<c:param name="uid" value="${currentUser.identifier}" />
				<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<c:url value="currentGroupView" var="url2">
				<c:param name="uid" value="${currentUser.identifier}" />
				<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<br>
			<div align="center">
				<a href ="${url1}">Bestätigen</a>  <a href ="${url2}">Abbrechen</a>
			</div>		
		</div>
	</div>
</div>

<div class="footer">
		<p>Copyright SuperLotterie ©</p>
</div>


</body>
</html>