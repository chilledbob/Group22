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
			<section>Erstellen</section>
			<section><a href ="showInvitations">Meine Einladungen (${invCount})</a></section>
			<section><a href ="showApplications">Meine Bewerbungen (${applCount})</a></section>
			<section><a href="myGroups">Gruppen</a></section>
		</div>	
	</div>
</div>

<div id="middle">
	<div class="main_content_full">
		<div class="current_content" >
			<br>
			<h4 align="center">Gruppe erstellen<sup style="text-shadow: aqua;font-size: x-small;">${currentUser.identifier}</sup></h4>
			   
			<div align="center">${comment}</div>
			
			<form id="form" action="createNewGroup" method="post" style="border:0px;"> 
				<table>
				<tr>
					<td><label for="groupName">Name</label></td>
					<td><input name="groupName" id="groupName"  type="text" size="30" maxlength="30"/></td> 
				</tr>
				<tr>
					<td><label for="infoText">Info Text</label></td>
					<td><textarea name="infoText" id="infoText"  rows="5" cols="30"></textarea></td> 
				</tr>	
				<tr>
					<td><label for="uid">Admin</label></td>
					<td><input name="uid" id="uid" type="text" size="30" maxlength="30" readonly value="${currentUser.identifier}"/></td>
				</tr>
				
				</table>
				<div class="button" align="center">
					<button class="btn">
						<img src="<c:url value="/res/img/accept.png" />" />
					</button>
					<a href ="returnToCustomerGroups">
						<img src="<c:url value="/res/img/decline.png" />" />
					</a>
				</div>
		</form> 		
	</div>
	
	</div>
</div>

<div class="footer">
		<p>Copyright SuperLotterie ©</p>
</div>


</body>
</html>