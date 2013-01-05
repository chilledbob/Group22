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
			
			<c:url value="currentGroupView" var="url1">
				<c:param name="uid" value="${currentUser.identifier}" />
				<c:param name="groupName" value="${currentGroup.getName()}" />
			</c:url>
			<c:url value="currentGroupViewTips_Admin" var="url2">
				<c:param name="uid" value="${currentUser.identifier}" />
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
			<c:url value="new_weeklyLotto_GroupTip" var="url6">
				<c:param name="groupName" value="${currentGroup.name}" />
				<c:param name="uid" value="${currentUser.identifier }" />
			</c:url>
			<c:url value="new_dailyLotto_GroupTip" var="url7">
				<c:param name="groupName" value="${currentGroup.name}" />
				<c:param name="uid" value="${currentUser.identifier }" />
			</c:url>
			<c:url value="new_toto_GroupTip" var="url8">
				<c:param name="groupName" value="${currentGroup.name}" />
				<c:param name="uid" value="${currentUser.identifier }" />
			</c:url>
			
			<div align="center" style="width:90%; border-bottom:thin solid black; padding:15px;">
				<a href ="${url1}">Mitglieder</a>
				<a href ="${url3}">Bewerbungen</a>
				<a href ="${url5}">Abbrechen</a>
			</div>
			<div align="center">
				<a href="${url6 }">neuen 6 aus 49 Tip erstellen</a>
				<a href="${url7 }">neuen Nummernlottotip erstellen</a>
				<a href="${url8 }">neuen Tototip erstellen</a>
			</div>
			<p/>
			<h4 align="center">- aktuelle Gruppentips -</h4>
			<table>
				<tr>
					<td>
						Spiel
					</td>
					<td>
						Ziehungsdatum
					</td>
				</tr>
				<c:forEach var="wSTT" items="${weeklySTTList}">
				<tr>
					<td>
						6 aus 49
					</td>
					<td>
						${wSTT.getDraw().getPlanedEvaluationDate() }
					</td>
				</tr>
				</c:forEach>
			</table>
		</div>		
	</div>
	


<div class="footer">
		<p>Copyright SuperLotterie ©</p>
</div>


</body>
</html>