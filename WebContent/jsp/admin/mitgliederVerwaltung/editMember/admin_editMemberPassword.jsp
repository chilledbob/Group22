<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.salespoint-framework.org/web/taglib" prefix="sp" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Account</title>
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
		
		<div class="sub_navi" >
		</div>	
	</div>
</div>
		
<div id="middle">

	<div class="main_content_full">
		<div class="current_content">
		
			<h4 align="center">Passwort von ${editMember.identifier}<sup style="text-shadow: aqua;font-size: x-small;">${editMember.getTypeAsGerman().toString()}</sup></h4>  
			<div align="center">
				<c:url value="adminEditMember" var="url">
					<c:param name="editUid" value="${editMember.identifier}" />
				</c:url>
				<a href ="${url}">Nutzerdaten</a>
				<a href ="adminEditMemberPassword">Passwort</a> 
				<a href ="adminCancel">Abbrechen</a>
			</div>	
			<br>
			<div align="center">${comment}</div>
			<br>	
    	
		<form id="form" action="adminChangeMemberPassword" method="post" style="border:0px;"> 
			<table>
				<tr>
					<td><label for="oldPassword">Altes Passwort</label></td>
					<td><input name="oldPassword" id="oldPassword" type="password"/></td>
				</tr>
				<tr>
					<td><label for="newPassword">Neues Passwort</label></td>
					<td><input name="newPassword" id="newPassword" type="password"/></td>				
				</tr>
			</table>
			<div align="center" class="button"><button class="btn">Ändern</button></div>
		</form> 
		
			
	</div>
</div>
	

</div>

<div class="footer">
		<p>Copyright SuperLotterie ©</p>
</div>