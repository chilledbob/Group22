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
			
			<c:url value="showInvitations" var="url">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<section><a href ="${url}">Meine Einladungen (0)</a></section>
			
			<c:url value="showApplications" var="url">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<section><a href ="${url}">Meine Bewerbungen (0)</a></section>
			
			<c:url value="myGroups" var="url">
				<c:param name="uid" value="${currentUser.identifier }"/>
			</c:url>
			<section><a href="${url }">Gruppen</a></section>
			
		</div>	
	</div>
</div>

	
<div id="middle">
	<div class="main_content_full">
		<div class="current_content">

			
			<c:url value="currentGroupViewTips" var="url1">
				<c:param name="uid" value="${currentUser.identifier}" />
				<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<c:url value="currentGroupViewMember" var="url2">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<c:url value="resignGroup" var="url3">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<c:url value="myGroups" var="url4">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			
			<div align="center">
				<a href ="${url1}">Tipps</a> <a href ="${url2}">Mitglieder</a> 
				<a href ="${url3}">Austreten</a> <a href ="${url4}">Abbrechen</a>
			</div>
			<p/>
			<h4 align="center"> - Mitglieder -</h4>
			<br>
			<div align="center">Tipps anzeigen</div>
			
			
			

		</div>		
	</div>
	


<div class="footer">
		<p>Copyright SuperLotterie ©</p>
</div>


</body>
</html>